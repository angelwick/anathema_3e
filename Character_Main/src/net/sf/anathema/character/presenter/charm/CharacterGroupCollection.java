package net.sf.anathema.character.presenter.charm;

import net.sf.anathema.character.generic.magic.charms.ICharmGroup;
import net.sf.anathema.charmtree.presenter.CharmGroupCollection;

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