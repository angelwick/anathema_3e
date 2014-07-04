package net.sf.anathema.hero.charms.model.special.oxbody;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.model.special.ISpecialCharmLearnListener;
import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.health.model.IHealthLevelProvider;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.model.IncrementChecker;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.TraitType;
import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OxBodyTechniqueSpecialsImpl implements OxBodyTechniqueSpecials {

  private final Announcer<ISpecialCharmLearnListener> control = Announcer.to(ISpecialCharmLearnListener.class);
  private final IncrementChecker incrementChecker;
  private final OxBodyCategory[] categories;
  private final Charm oxBodyTechnique;
  private final IHealthLevelProvider healthLevelProvider;

  public OxBodyTechniqueSpecialsImpl(final Hero hero, Charm oxBodyTechnique, final TraitType[] relevantTraits,
                                     final OxBodyTechniqueArbitrator arbitrator, IOxBodyTechniqueCharm properties) {
    this.oxBodyTechnique = oxBodyTechnique;
    incrementChecker = increment -> {
      int minTrait = Integer.MAX_VALUE;
      for (TraitType type : relevantTraits) {
        TraitModel traits = TraitModelFetcher.fetch(hero);
        Trait trait = traits.getTrait(type);
        minTrait = Math.min(minTrait, trait.getCurrentValue());
      }
      return increment < 0 || (arbitrator.isIncrementAllowed(increment) && getCurrentLearnCount() + increment <= minTrait);
    };
    categories = createOxBodyCategories(hero, properties);
    for (OxBodyCategory category : categories) {
      category.addCurrentValueListener(newValue -> fireLearnCountChanged(getCurrentLearnCount()));
    }
    this.healthLevelProvider = new OxBodyTechniqueHealthLevelProvider(categories);
  }

  private OxBodyCategory[] createOxBodyCategories(Hero hero, IOxBodyTechniqueCharm properties) {
    Set<String> ids = properties.getHealthLevels().keySet();
    List<OxBodyCategory> categoryList = new ArrayList<>();
    for (String id : ids) {
      HealthLevelType[] levels = properties.getHealthLevels().get(id);
      OxBodyCategory category = new OxBodyCategory(hero, levels, id, incrementChecker);
      categoryList.add(category);
    }
    return categoryList.toArray(new OxBodyCategory[categoryList.size()]);
  }

  @Override
  public void forget() {
    for (OxBodyCategory category : getCategories()) {
      category.setCurrentValue(0);
    }
  }

  @Override
  public void learn(boolean experienced) {
    OxBodyCategory trait = getCategories()[0];
    if (experienced && getCurrentLearnCount() == 0) {
      trait.setExperiencedValue(1);
    } else if (!experienced && getCreationLearnCount() == 0) {
      trait.setCreationValue(1);
    }
  }

  @Override
  public OxBodyCategory[] getCategories() {
    return categories;
  }

  @Override
  public int getCreationLearnCount() {
    int sum = 0;
    for (OxBodyCategory category : getCategories()) {
      sum += category.getCreationValue();
    }
    return sum;
  }

  @Override
  public int getCurrentLearnCount() {
    int sum = 0;
    for (OxBodyCategory category : getCategories()) {
      sum += category.getCurrentValue();
    }
    return sum;
  }

  @Override
  public void addSpecialCharmLearnListener(ISpecialCharmLearnListener listener) {
    control.addListener(listener);
  }

  private void fireLearnCountChanged(int learnCount) {
    control.announce().learnCountChanged(learnCount);
  }

  @Override
  public Charm getCharm() {
    return oxBodyTechnique;
  }

  @Override
  public OxBodyCategory getCategoryById(String id) {
    for (OxBodyCategory category : categories) {
      if (category.getId().equals(id)) {
        return category;
      }
    }
    throw new IllegalArgumentException("No ox body category found with id " + id);
  }

  @Override
  public IHealthLevelProvider getHealthLevelProvider() {
    return healthLevelProvider;
  }
}