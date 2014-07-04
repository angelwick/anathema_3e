package net.sf.anathema.herotype.solar.model.curse;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.text.ITextualDescription;
import net.sf.anathema.library.text.SimpleTextualDescription;

public class DescriptiveLimitBreakImpl extends LimitBreakImpl implements DescriptiveLimitBreak {

  private final ITextualDescription description = new SimpleTextualDescription("");
  private final ITextualDescription limitBreak = new SimpleTextualDescription("");

  public DescriptiveLimitBreakImpl(Hero hero) {
    super(hero);
  }

  @Override
  public ITextualDescription getDescription() {
    return description;
  }

  @Override
  public ITextualDescription getLimitBreak() {
    return limitBreak;
  }
}