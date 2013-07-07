package net.sf.anathema.character.main.presenter.magic;

import net.sf.anathema.character.main.magic.model.charm.ICharmGroup;
import net.sf.anathema.character.main.magic.display.presenter.CharmGroupCollection;

public class CharacterGroupCollection implements CharmGroupCollection {
  private CharacterCharmModel model;

  public CharacterGroupCollection(CharacterCharmModel model) {
    this.model = model;
  }

  @Override
  public ICharmGroup[] getCharmGroups() {
    return model.getCharmConfiguration().getAllGroups();
  }
}