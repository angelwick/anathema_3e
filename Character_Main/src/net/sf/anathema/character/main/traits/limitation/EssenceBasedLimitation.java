package net.sf.anathema.character.main.traits.limitation;

import net.sf.anathema.character.main.template.ITraitLimitation;
import net.sf.anathema.character.main.traits.ValuedTraitType;
import net.sf.anathema.character.main.traits.types.OtherTraitType;
import net.sf.anathema.hero.othertraits.OtherTraitModel;
import net.sf.anathema.hero.othertraits.OtherTraitModelFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.exception.UnreachableCodeReachedException;

public class EssenceBasedLimitation implements ITraitLimitation {

  @Override
  public int getAbsoluteLimit(Hero hero) {
    OtherTraitModel otherTraitModel = getOtherTraitModel(hero);
    ITraitLimitation essenceLimitation = otherTraitModel.getEssenceLimitation();
    int essenceMaximum = essenceLimitation.getAbsoluteLimit(hero);
    return Math.max(essenceMaximum, 5);
  }

  @Override
  public int getCurrentMaximum(Hero hero, boolean modified) {
    OtherTraitModel otherTraitModel = getOtherTraitModel(hero);
    ValuedTraitType essence = otherTraitModel.getTrait(OtherTraitType.Essence);
    int currentEssence = Math.min(essence.getCurrentValue(), otherTraitModel.getEssenceCap(modified));
    int currentEssenceValue = Math.max(currentEssence, 5);
    return Math.min(getAbsoluteLimit(hero), currentEssenceValue);
  }

  private OtherTraitModel getOtherTraitModel(Hero hero) {
    return OtherTraitModelFetcher.fetch(hero);
  }

  @Override
  public EssenceBasedLimitation clone() {
    try {
      return (EssenceBasedLimitation) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new UnreachableCodeReachedException(e);
    }
  }
}