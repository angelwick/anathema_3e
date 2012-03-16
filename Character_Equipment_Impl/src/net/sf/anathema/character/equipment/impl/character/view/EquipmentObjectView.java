package net.sf.anathema.character.equipment.impl.character.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.disy.commons.core.model.BooleanModel;
import net.disy.commons.swing.action.ActionWidgetFactory;
import net.disy.commons.swing.action.SmartToggleAction;
import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.sf.anathema.character.equipment.character.view.IEquipmentObjectView;
import net.sf.anathema.character.library.taskpane.ITaskPaneGroupView;
import net.sf.anathema.lib.gui.GuiUtilities;

import com.l2fprod.common.swing.JTaskPaneGroup;

public class EquipmentObjectView implements IEquipmentObjectView, ITaskPaneGroupView {

  private final JTaskPaneGroup taskGroup = new JTaskPaneGroup();
  private final JLabel descriptionLabel = new JLabel();
  private final Map<BooleanModel, JCheckBox> boxes = new HashMap<BooleanModel, JCheckBox>();
  private final Map<BooleanModel, JPanel> boxPanels = new HashMap<BooleanModel, JPanel>();

  public EquipmentObjectView() {
    taskGroup.add(descriptionLabel);
  }

  public void setItemTitle(String title) {
    taskGroup.setTitle(title);
  }

  public void setItemDescription(String text) {
    descriptionLabel.setText(text);
    GuiUtilities.revalidate(taskGroup);
  }

  public BooleanModel addStats(String description) {
    BooleanModel isSelectedModel = new BooleanModel();
    JCheckBox box = ActionWidgetFactory.createCheckBox(new SmartToggleAction(isSelectedModel, description));
    boxes.put(isSelectedModel, box);
    
    GridDialogLayout layout = new GridDialogLayout(1, false);
    JPanel panel = new JPanel(layout);
    
    panel.add(box);
    taskGroup.add(panel);
    boxPanels.put(isSelectedModel, panel);
    
    return isSelectedModel;
  }
  
  public BooleanModel addOptionFlag(BooleanModel base, String description) {
	BooleanModel isSelectedModel = new BooleanModel();
	JPanel basePanel = boxPanels.get(base);
	if (basePanel != null)
	{
		JPanel optionPanel = new JPanel(new GridDialogLayout(2, false));
		optionPanel.add(new JLabel("   ..."));
		JCheckBox box = ActionWidgetFactory.createCheckBox(new SmartToggleAction(isSelectedModel, description));
		boxes.put(isSelectedModel, box);
		optionPanel.add(box);
		basePanel.add(optionPanel);
	}
	return isSelectedModel;
  }
  
  public void updateStatText(BooleanModel model, String newText)
  {
	  boxes.get(model).setText(newText);
  }
  
  public void setEnabled(BooleanModel model, boolean enabled)
  {
	  boxes.get(model).setEnabled(enabled);
  }

  public JTaskPaneGroup getTaskGroup() {
    return taskGroup;
  }

  public void addAction(Action action) {
    taskGroup.add(action);
  }
}