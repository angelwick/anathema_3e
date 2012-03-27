package net.sf.anathema.character.equipment.impl.character.model.stats.modification;

public class RangeModification implements IStatsModification {

  private BaseMaterial material;

  public RangeModification(BaseMaterial material) {
    this.material = material;
  }

  @Override
  public int getModifiedValue(int input, WeaponStatsType type) {
    int modificationFactor = getModificationFactor();
    if (type == WeaponStatsType.Bow || type == WeaponStatsType.Thrown_BowBonuses) {
      return input + 50 * modificationFactor;
    }
    if (type == WeaponStatsType.Thrown) {
      return input + 10 * modificationFactor;
    }
    return input;
  }

  private int getModificationFactor() {
    if (material.isMoonsilverBased()) {
      return 2;
    }
    if (material.isJadeBased() || material.isOrichalcumBased()) {
      return 1;
    }
    return 0;
  }
}