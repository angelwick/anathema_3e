package net.sf.anathema.hero.spells.display.view;

import net.sf.anathema.hero.framework.display.SubViewFactory;
import net.sf.anathema.hero.spells.display.presenter.SpellView;
import net.sf.anathema.library.fx.Stylesheet;
import net.sf.anathema.platform.initialization.Produces;

@Produces(SpellView.class)
public class SpellViewFactory implements SubViewFactory {
  @SuppressWarnings("unchecked")
  @Override
  public <T> T create() {
    FxSpellView fxView = new FxSpellView();
    new Stylesheet("skin/platform/tooltip.css").applyToParent(fxView.getNode());
    return (T) fxView;
  }
}