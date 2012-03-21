package net.sf.anathema.character.generic.dummy.template;

import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.impl.template.magic.DefaultMartialArtsRules;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.MartialArtsLevel;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;
import net.sf.anathema.character.generic.template.magic.ICharmTemplate;
import net.sf.anathema.character.generic.template.magic.IMartialArtsRules;
import net.sf.anathema.character.generic.template.magic.IUniqueCharmType;

public class DummyCharmTemplate implements ICharmTemplate {

  public ICharm[] getCharms(IExaltedRuleSet rules) {
    return new ICharm[0];
  }
  
  public ICharm[] getUniqueCharms(IExaltedRuleSet rules) {
	return new ICharm[0];
  }

  public ICharm[] getMartialArtsCharms(IExaltedRuleSet rules) {
    return new ICharm[0];
  }

  public boolean canLearnCharms(IExaltedRuleSet rules) {
    return false;
  }

  public IMartialArtsRules getMartialArtsRules() {
    DefaultMartialArtsRules defaultMartialArtsRules = new DefaultMartialArtsRules(MartialArtsLevel.Mortal);
    defaultMartialArtsRules.setHighLevelAtCreation(true);
    return defaultMartialArtsRules;
  }

  @Override
  public boolean hasUniqueCharms() {
    return false;
  }

  public boolean isAllowedAlienCharms(ICasteType caste) {
    return false;
  }

  @Override
  public IUniqueCharmType getUniqueCharmType() {
	  return null;
  }
}