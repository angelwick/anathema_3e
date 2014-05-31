package net.sf.anathema.fx.hero.perspective;

import net.sf.anathema.hero.creation.CharacterTemplateCreator;
import net.sf.anathema.hero.framework.perspective.CharacterButtonDto;
import net.sf.anathema.hero.framework.perspective.CharacterGridView;
import net.sf.anathema.hero.framework.perspective.Selector;
import net.sf.anathema.hero.framework.perspective.model.CharacterIdentifier;
import net.sf.anathema.platform.fx.Navigation;
import net.sf.anathema.platform.view.InteractionView;
import net.sf.anathema.platform.view.MenuTool;

public class FxCharacterNavigation extends Navigation implements InteractionView, CharacterGridView {
  private CharacterGridFxView gridView;

  public FxCharacterNavigation() {
    gridView = new CharacterGridFxView();
    addContainerToNavigation(gridView.getNode());
  }


  @Override
  public void addButton(final CharacterButtonDto dto, final Selector<CharacterIdentifier> characterSelector) {
    gridView.addButton(dto, characterSelector);
  }

  @Override
  public void selectButton(final CharacterIdentifier identifier) {
    gridView.selectButton(identifier);
  }

  @Override
  public void updateButton(final CharacterButtonDto dto) {
    gridView.updateButton(dto);
  }

  @Override
  public CharacterTemplateCreator createNewCharacter() {
    return gridView.createNewCharacter();
  }

  @Override
  public MenuTool addMenuTool() {
    FxMenuButtonTool tool = FxMenuButtonTool.ForToolbar();
    addTool(tool);
    return tool;
  }
}
