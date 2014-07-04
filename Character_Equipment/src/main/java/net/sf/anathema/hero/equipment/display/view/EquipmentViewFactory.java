package net.sf.anathema.hero.equipment.display.view;

import net.sf.anathema.hero.equipment.display.presenter.EquipmentView;
import net.sf.anathema.hero.framework.display.SubViewFactory;
import net.sf.anathema.platform.fx.Stylesheet;
import net.sf.anathema.platform.initialization.Produces;

@Produces(EquipmentView.class)
public class EquipmentViewFactory implements SubViewFactory {
  @SuppressWarnings("unchecked")
  @Override
  public <T> T create() {
    FxEquipmentView fxView = new FxEquipmentView();
    new Stylesheet("skin/platform/tooltip.css").applyToParent(fxView.getNode());
    new Stylesheet("skin/equipment/items.css").applyToParent(fxView.getNode());
    return (T) fxView;
  }
}