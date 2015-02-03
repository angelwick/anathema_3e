package net.sf.anathema.hero.display.fx.perspective.content;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import net.miginfocom.layout.CC;
import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.fx.Stylesheet;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.fx.view.FxStack;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import org.tbee.javafx.scene.layout.MigPane;

public class HeroMultipleContentView implements MultipleContentView {
  private final MigPane contentPane = new MigPane(LayoutUtils.fillWithoutInsets().wrapAfter(1).gridGap("0", "0"));
  private MultipleContentInitializer[] initializers;
  private final FxStack stack;
  private boolean isEmpty = true;

  public HeroMultipleContentView(MigPane viewPanel, MultipleContentInitializer... initializers) {
    this.initializers = initializers;
    this.stack = new FxStack(viewPanel);
    new Stylesheet("skin/character/charactersubview.css").applyToParent(contentPane);
  }

  @Override
  public void addView(NodeHolder view, ContentProperties properties) {
    String name = properties.getName();
    Node fxContainer = createContainer(view, name);
    stack.add(new SimpleIdentifier(name), fxContainer);
    Hyperlink trigger = new Hyperlink(name);
    trigger.getStyleClass().add("character-subview-selector");
    trigger.setOnAction(actionEvent -> stack.show(new SimpleIdentifier(name)));
    contentPane.add(trigger);
    isEmpty = false;
  }

  @Override
  public void finishInitialization() {
    if (isEmpty) {
      return;
    }
    for (MultipleContentInitializer initializer : initializers) {
      initializer.finishInitialization(contentPane);
    }
  }

  private Node createContainer(NodeHolder content, String name) {
    MigPane viewComponent = new MigPane(LayoutUtils.fillWithoutInsets().wrapAfter(1));
    MigPane titlePane = new MigPane(LayoutUtils.fillWithoutInsets());
    Label title = new Label(name);
    title.setStyle("-fx-font-weight: bold");
    titlePane.add(title);
    titlePane.add(new Separator(), new CC().pushX().growX());
    viewComponent.add(titlePane, new CC().pushX().growX());
    viewComponent.add(content.getNode(), new CC().push().grow());
    return viewComponent;
  }
}