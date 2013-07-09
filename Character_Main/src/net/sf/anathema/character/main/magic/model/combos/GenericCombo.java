package net.sf.anathema.character.main.magic.model.combos;

import net.sf.anathema.character.main.magic.model.charm.Charm;

public class GenericCombo implements IGenericCombo {

  private final ICombo combo;

  public GenericCombo(ICombo combo) {
    this.combo = combo;
  }

  @Override
  public String getName() {
    return combo.getName().getText();
  }

  @Override
  public Charm[] getCharms() {
    return combo.getCharms();
  }
}