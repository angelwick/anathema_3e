package net.sf.anathema.hero.display.fx.perspective;

import javafx.scene.Node;
import net.sf.anathema.hero.framework.perspective.CharacterGridView;
import net.sf.anathema.library.interaction.view.InteractionView;
import net.sf.anathema.platform.fx.environment.UiEnvironment;
import net.sf.anathema.platform.fx.perspective.PerspectivePane;

public class CharacterSystemView {

  private final PerspectivePane pane = new PerspectivePane();
  private final StackView stackView = new StackView();
  private final FxCharacterNavigation navigation;

  public CharacterSystemView(UiEnvironment uiEnvironment) {
    this.navigation = new FxCharacterNavigation(uiEnvironment);
    pane.setNavigationComponent(navigation.getNode());
    pane.setContentComponent(stackView.getComponent());
  }

  public InteractionView getInteractionView() {
    return navigation;
  }

  public CharacterGridView getGridView() {
    return navigation;
  }

  public StackView getStackView() {
    return stackView;
  }

  public Node getNode() {
    return pane.getNode();
  }
}