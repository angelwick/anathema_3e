package net.sf.anathema.character.library.trait;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.anathema.character.generic.template.points.IFavorableTraitCreationPoints;
import net.sf.anathema.lib.collection.ListOrderedSet;

public abstract class AbstractFavorableTraitCostCalculator implements IFavorableTraitCostCalculator {

  private final Map<IFavorableTrait, FavorableTraitCost> costsByTrait = new HashMap<IFavorableTrait, FavorableTraitCost>();
  private int favoredPicksSpent;
  private int favoredDotSum = 0;
  private int generalDotSum = 0;
  private final IAdditionalTraitBonusPointManagement additionalPools;
  private final IFavorableTraitCreationPoints points;
  private final IFavorableTrait[] traits;

  public AbstractFavorableTraitCostCalculator(
      IAdditionalTraitBonusPointManagement additionalPools,
      IFavorableTraitCreationPoints points,
      IFavorableTrait[] traits) {
    this.additionalPools = additionalPools;
    this.points = points;
    this.traits = traits;
  }

  protected void clear() {
    favoredPicksSpent = 0;
    favoredDotSum = 0;
    generalDotSum = 0;
    costsByTrait.clear();
  }
  
  public void calculateCosts() {
    clear();
    Set<IFavorableTrait> sortedTraits = sortTraitsByStatus();
    for (IFavorableTrait trait : sortedTraits) {
      int costFactor = getCostFactor(trait);
      FavorableTraitCost cost;
      if (trait.getFavorization().isCasteOrFavored()) {
        cost = handleFavoredTrait(trait, costFactor);
      }
      else {
        cost = handleGeneralTrait(trait, costFactor);
      }
      additionalPools.spendOn(trait, cost.getBonusCost());
      costsByTrait.put(trait, cost);
    }
  }

  private FavorableTraitCost handleGeneralTrait(IFavorableTrait trait, int bonusPointCostFactor) {
    int pointsToAdd = Math.min(trait.getCalculationValue(), 3);
    int generalDotsSpent = 0;
    int bonusPointsSpent = 0;
    if (getFreePointsSpent(false) < getDefaultDotCount()) {
      int remainingGeneralPoints = getDefaultDotCount() - getFreePointsSpent(false);
      generalDotsSpent = Math.min(remainingGeneralPoints, pointsToAdd);
      increaseGeneralDotSum(generalDotsSpent);
      pointsToAdd -= generalDotsSpent;
    }
    if (pointsToAdd > 0) {
      bonusPointsSpent += pointsToAdd * bonusPointCostFactor;
    }
    bonusPointsSpent += Math.max(trait.getCalculationValue() - 3, 0) * bonusPointCostFactor;
    return new FavorableTraitCost(bonusPointsSpent, generalDotsSpent, 0);
  }

  private FavorableTraitCost handleFavoredTrait(IFavorableTrait trait, int bonusPointCostFactor) {
    if (trait.getFavorization().isFavored()) {
      increaseFavoredPicksSpent();
    }
    int pointsToAdd = Math.min(trait.getCalculationValue(), 3);
    int favoredDotsSpent = 0;
    int generalDotsSpent = 0;
    int bonusPointsSpent = 0;
    if (getFreePointsSpent(true) < getFavoredDotCount()) {
      int remainingFavoredPoints = getFavoredDotCount() - getFreePointsSpent(true);
      favoredDotsSpent = Math.min(remainingFavoredPoints, pointsToAdd);
      increaseFavoredDotSum(favoredDotsSpent);
      pointsToAdd -= favoredDotsSpent;
    }
    if (pointsToAdd > 0) {
      if (getFreePointsSpent(false) < getDefaultDotCount()) {
        int remainingGeneralPoints = getDefaultDotCount() - getFreePointsSpent(false);
        generalDotsSpent = Math.min(remainingGeneralPoints, pointsToAdd);
        increaseGeneralDotSum(generalDotsSpent);
        pointsToAdd -= generalDotsSpent;
      }
    }
    if (pointsToAdd > 0) {
      bonusPointsSpent += pointsToAdd * bonusPointCostFactor;
    }
    bonusPointsSpent += Math.max(trait.getCalculationValue() - 3, 0) * bonusPointCostFactor;
    return new FavorableTraitCost(bonusPointsSpent, generalDotsSpent, favoredDotsSpent);
  }

  protected abstract int getCostFactor(IFavorableTrait trait);

  public int getFreePointsSpent(boolean favored) {
    return favored ? favoredDotSum : generalDotSum;
  }

  public FavorableTraitCost getCosts(IFavorableTrait trait) {
    return costsByTrait.get(trait);
  }

  protected Set<IFavorableTrait> sortTraitsByStatus() {
    Set<IFavorableTrait> orderedTraits = new ListOrderedSet<IFavorableTrait>();
    for (IFavorableTrait trait : traits) {
      if (!trait.getFavorization().isCasteOrFavored()) {
        orderedTraits.add(trait);
      }
    }
    for (IFavorableTrait trait : traits) {
      if (!orderedTraits.contains(trait)) {
        orderedTraits.add(trait);
      }
    }
    return orderedTraits;
  }

  protected void increaseFavoredPicksSpent() {
    favoredPicksSpent++;
  }

  public int getFavoredPicksSpent() {
    return favoredPicksSpent;
  }

  protected void increaseFavoredDotSum(int favoredDotsSpent) {
    favoredDotSum += favoredDotsSpent;
  }

  protected void increaseGeneralDotSum(int generalDotsSpent) {
    generalDotSum += generalDotsSpent;
  }

  public int getBonusPointsSpent() {
    int bonusPointSum = 0;
    for (FavorableTraitCost cost : costsByTrait.values()) {
      bonusPointSum += cost.getBonusCost();
    }
    return bonusPointSum;
  }

  private int getDefaultDotCount() {
    return points.getDefaultDotCount();
  }

  private int getFavoredDotCount() {
    return points.getFavoredDotCount();
  }
}