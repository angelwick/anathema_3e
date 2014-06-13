package net.sf.anathema.hero.equipment.display.view;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.hero.equipment.display.presenter.EquipmentObjectView;
import net.sf.anathema.hero.equipment.display.presenter.EquipmentPersonalizationProperties;
import net.sf.anathema.hero.equipment.display.presenter.PersonalizationEditView;
import net.sf.anathema.hero.equipment.display.presenter.StatsView;
import net.sf.anathema.hero.equipment.display.view.personalization.FxPersonalizationEditView;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.gui.layout.LayoutUtils;
import net.sf.anathema.platform.fx.FxTextView;
import net.sf.anathema.platform.tool.FxButtonTool;
import org.tbee.javafx.scene.layout.MigPane;

public class FxEquipmentItemView implements EquipmentObjectView {
  private final TitledPane border = new TitledPane();
  private final FxTextView titleLabel = FxTextView.SingleLine("");
  private final FxTextView descriptionLabel = FxTextView.SingleLine("");
  private final MigPane elementPane = new MigPane(LayoutUtils.fillWithoutInsets().wrapAfter(1));
  private final MigPane buttonPane = new MigPane(LayoutUtils.fillWithoutInsets());
  private final MigPane body = new MigPane(new LC().fill().wrapAfter(1));

  public FxEquipmentItemView() {
    body.add(titleLabel.getNode(), new CC().grow());
    body.add(descriptionLabel.getNode(), new CC().grow());
    body.add(elementPane);
    body.add(buttonPane);
    border.setContent(body);
    disablePersonalization();
  }

  @Override
  public void setItemTitle(String title) {
    titleLabel.setText(title);
  }

  @Override
  public void setItemDescription(String text) {
    descriptionLabel.setText(text);
  }

  @Override
  public void clear() {
    setItemTitle("");
    setItemDescription("");
    clearStatsAndActions();
  }

  @Override
  public void clearStatsAndActions() {
    elementPane.getChildren().clear();
    buttonPane.getChildren().clear();
  }

  @Override
  public StatsView addStats(String description) {
    FxStatsView statsView = new FxStatsView(description);
    elementPane.add(statsView.getNode());
    return statsView;
  }

  @Override
  public Tool addAction() {
    FxButtonTool tool = FxButtonTool.ForToolbar();
    buttonPane.add(tool.getNode());
    return tool;
  }

  @Override
  public PersonalizationEditView startEditingPersonalization(EquipmentPersonalizationProperties properties) {
    return new FxPersonalizationEditView(body.getScene().getWindow(), properties);
  }

  @Override
  public void enablePersonalization() {
    titleLabel.setEnabled(true);
    descriptionLabel.setEnabled(true);
  }

  @Override
  public void disablePersonalization() {
    titleLabel.setEnabled(false);
    descriptionLabel.setEnabled(false);
  }

  public Node getNode() {
    return border;
  }
}