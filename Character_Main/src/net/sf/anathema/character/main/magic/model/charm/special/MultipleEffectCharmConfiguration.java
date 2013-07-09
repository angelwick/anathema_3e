package net.sf.anathema.character.main.magic.model.charm.special;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.charmtree.ICharmLearnableArbitrator;
import net.sf.anathema.character.main.magic.model.charm.CharmSpecialist;
import net.sf.anathema.lib.control.ChangeListener;
import org.jmock.example.announcer.Announcer;

public class MultipleEffectCharmConfiguration implements IMultipleEffectCharmConfiguration {
  private final Charm charm;
  private final SubEffects subeffects;
  private final Announcer<ISpecialCharmLearnListener> control = Announcer.to(ISpecialCharmLearnListener.class);

  public MultipleEffectCharmConfiguration(CharmSpecialist specialist, Charm charm, IMultipleEffectCharm visited,
                                          ICharmLearnableArbitrator arbitrator) {
    this.charm = charm;
    this.subeffects = visited.buildSubeffects(specialist, arbitrator, charm);
    for (ISubeffect subeffect : subeffects) {
      subeffect.addChangeListener(new ChangeListener() {
        @Override
        public void changeOccurred() {
          fireLearnCountChanged();
        }
      });
    }
  }

  @Override
  public void forget() {
    for (ISubeffect effect : subeffects) {
      effect.setLearned(false);
    }
  }

  @Override
  public void learn(boolean experienced) {
    ISubeffect firstEffect = subeffects.getEffects()[0];
    if (experienced && getCurrentLearnCount() == 0) {
      firstEffect.setExperienceLearned(true);
    } else if (!experienced && getCreationLearnCount() == 0) {
      firstEffect.setCreationLearned(true);
    }
  }

  private void fireLearnCountChanged() {
    control.announce().learnCountChanged(getCurrentLearnCount());
  }

  @Override
  public void addSpecialCharmLearnListener(ISpecialCharmLearnListener listener) {
    control.addListener(listener);
  }

  @Override
  public Charm getCharm() {
    return charm;
  }

  @Override
  public ISubeffect[] getEffects() {
    return subeffects.getEffects();
  }

  @Override
  public ISubeffect getEffectById(final String id) {
    return subeffects.getById(id);
  }

  @Override
  public int getCreationLearnCount() {
    int sum = 0;
    for (ISubeffect subeffect : subeffects) {
      if (subeffect.isCreationLearned()) {
        sum++;
      }
    }
    return sum;
  }

  @Override
  public int getCurrentLearnCount() {
    int sum = 0;
    for (ISubeffect subeffect : subeffects) {
      if (subeffect.isLearned()) {
        sum++;
      }
    }
    return sum;
  }

  protected SubEffects getSubeffects() {
    return subeffects;
  }
}