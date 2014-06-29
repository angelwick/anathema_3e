package net.sf.anathema.herotype.solar.model.curse;

import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface LimitBreakModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("GreatCurse");

  LimitBreak getLimitBreak();
}