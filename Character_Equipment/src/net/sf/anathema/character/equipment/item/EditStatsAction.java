package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.equipment.item.model.IEquipmentStatsCreationFactory;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.framework.view.SwingApplicationFrame;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.character.equipment.item.view.ToolListView;
import net.sf.anathema.lib.resources.IResources;

import java.util.ArrayList;
import java.util.List;

public class EditStatsAction {
  private final IEquipmentStatsCreationFactory factory;
  private final IResources resources;
  private final IEquipmentTemplateEditModel editModel;

  public EditStatsAction(IResources resources, IEquipmentTemplateEditModel editModel,
                         IEquipmentStatsCreationFactory factory) {
    this.resources = resources;
    this.editModel = editModel;
    this.factory = factory;
  }

  public void addToolTo(final ToolListView<IEquipmentStats> statsListView) {
    final Tool tool = statsListView.addTool();
    tool.setIcon("icons/ButtonEdit16.png");
    tool.setTooltip(resources.getString("Equipment.Creation.Stats.EditActionTooltip"));
    tool.setCommand(new Command() {
      @Override
      public void execute() {
        IEquipmentStats selectedStats = statsListView.getSelectedItems()[0];
        List<String> definedNames = new ArrayList<>();
        for (IEquipmentStats stats : editModel.getStats()) {
          if (stats == selectedStats) {
            continue;
          }
          definedNames.add(stats.getName().getId());
        }
        String[] nameArray = definedNames.toArray(new String[definedNames.size()]);
        MaterialComposition materialComposition = editModel.getMaterialComposition();
        IEquipmentStats equipmentStats = factory.editStats(SwingApplicationFrame.getParentComponent(), resources, nameArray, selectedStats, materialComposition);
        if (equipmentStats == null) {
          return;
        }
        editModel.replaceStatistics(selectedStats, equipmentStats);
      }
    });
    statsListView.addListSelectionListener(new Runnable() {
      @Override
      public void run() {
        updateEnabled(statsListView, tool);
      }
    });
    updateEnabled(statsListView, tool);
  }

  private void updateEnabled(ToolListView<IEquipmentStats> statsListView, Tool tool) {
    if (statsListView.getSelectedItems().length == 1) {
      tool.enable();
    } else {
      tool.disable();
    }
  }
}