package net.sf.anathema.hero.spells.display.presenter;

import net.sf.anathema.hero.charms.display.magic.MagicLearnProperties;
import net.sf.anathema.hero.charms.display.magic.MagicLearnView;
import net.sf.anathema.hero.spells.data.CircleType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.library.event.ObjectChangedListener;

import java.util.Collection;

public interface SpellView {

  void addCircleSelection(Collection<Identifier> circles, SpellViewProperties properties);

  void showSelectedCircle(CircleType newValue);

  MagicLearnView addMagicLearnView(MagicLearnProperties properties);

  void addCircleSelectionListener(ObjectChangedListener<CircleType> listener);
}