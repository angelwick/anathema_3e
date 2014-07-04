package net.sf.anathema.hero.charms.model;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.model.learn.CharmLearnAdapter;
import net.sf.anathema.library.change.ChangeAnnouncer;
import net.sf.anathema.library.change.ChangeFlavor;

public final class CharacterChangeCharmListener extends CharmLearnAdapter {

  private final ChangeAnnouncer announcer;

  public CharacterChangeCharmListener(ChangeAnnouncer announcer) {
    this.announcer = announcer;
  }

  @Override
  public void charmForgotten(Charm charm) {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }

  @Override
  public void charmLearned(Charm charm) {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }

  @Override
  public void recalculateRequested() {
    announcer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }
}