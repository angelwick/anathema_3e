package net.sf.anathema.hero.display.fx.perspective;

import javafx.scene.Node;
import net.sf.anathema.hero.framework.perspective.model.CharacterIdentifier;
import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.fx.view.FxStack;
import org.tbee.javafx.scene.layout.MigPane;

public class StackView {

  private final MigPane viewPanel = new MigPane(LayoutUtils.fillWithoutInsets());
  private final FxStack stack = new FxStack(viewPanel);

  public void showView(CharacterIdentifier identifier) {
    stack.show(identifier);
  }

  public void addView(CharacterIdentifier identifier, NodeHolder node) {
    stack.add(identifier, node.getNode());
    stack.show(identifier);
  }

  public Node getComponent() {
    return viewPanel;
  }
}