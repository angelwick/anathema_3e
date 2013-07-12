package net.sf.anathema.hero.advance.experience;

import net.sf.anathema.hero.advance.experience.models.CharmExperienceModel;
import net.sf.anathema.hero.advance.experience.models.MiscellaneousExperienceModel;
import net.sf.anathema.hero.advance.experience.models.SpellExperienceModel;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.points.HeroModelExperienceCalculator;
import net.sf.anathema.hero.points.PointModelFetcher;
import net.sf.anathema.hero.points.PointsModel;
import net.sf.anathema.hero.points.overview.IValueModel;

import java.util.ArrayList;
import java.util.List;

public class HardCodedExperiencePointManagement implements ExperiencePointManagement {

  private final PointCostCalculator calculator;
  private final Hero hero;

  public HardCodedExperiencePointManagement(Hero hero) {
    this.hero = hero;
    this.calculator = new ExperiencePointCostCalculator(hero.getTemplate().getExperienceCost());
  }

  @Override
  public List<IValueModel<Integer>> getAllModels() {
    final List<IValueModel<Integer>> allModels = new ArrayList<>();
    // todo (sandra): Sorting
    allModels.add(getCharmModel());
    allModels.add(getSpellModel());
    allModels.add(getMiscModel());
    PointsModel pointsModel = PointModelFetcher.fetch(hero);
    for (IValueModel<Integer>  model : pointsModel.getExperienceOverviewModels()) {
      allModels.add(model);
    }
    return allModels;
  }

  private IValueModel<Integer> getCharmModel() {
    return new CharmExperienceModel(calculator, hero);
  }

  @Override
  public int getMiscGain() {
    int total = 0;
    PointsModel pointsModel = PointModelFetcher.fetch(hero);
    for (HeroModelExperienceCalculator experienceCalculator : pointsModel.getExperienceCalculators()) {
      total += experienceCalculator.calculateGain();
    }
    return total;
  }

  private IValueModel<Integer> getMiscModel() {
    return new MiscellaneousExperienceModel(hero);
  }

  private IValueModel<Integer> getSpellModel() {
    return new SpellExperienceModel(hero, calculator);
  }

  @Override
  public int getTotalCosts() {
    int experienceCosts = 0;
    for (IValueModel<Integer> model : getAllModels()) {
      experienceCosts += model.getValue();
    }
    return experienceCosts;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (IValueModel<Integer> model : getAllModels()) {
      builder.append(model.getCategoryId());
      builder.append(": ");
      builder.append(model.getValue());
    }
    builder.append("Overall: ");
    builder.append(getTotalCosts());
    return builder.toString();
  }
}