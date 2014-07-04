package net.sf.anathema.character.equipment.dummy;

import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.character.model.stats.AbstractStats;
import net.sf.anathema.equipment.core.ItemCost;
import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.equipment.core.MaterialComposition;
import net.sf.anathema.hero.equipment.sheet.content.stats.ArtifactAttuneType;
import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IEquipmentStats;
import net.sf.anathema.library.event.ChangeListener;

import java.util.ArrayList;
import java.util.List;

public class DummyEquipmentItem extends AbstractStats implements IEquipmentItem {

  private final List<IEquipmentStats> allEquipments = new ArrayList<>();
  private final List<IEquipmentStats> printed = new ArrayList<>();
  private final String name;
  private final String description;

  public DummyEquipmentItem(String title, String description) {
    this.name = title;
    this.description = description;
  }

  public void addEquipment(IEquipmentStats equipment) {
    this.allEquipments.add(equipment);
  }

  @Override
  public IEquipmentStats[] getStats() {
    return allEquipments.toArray(new IEquipmentStats[allEquipments.size()]);
  }

  @Override
  public String getTemplateId() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public ItemCost getCost() {
    return null;
  }

  @Override
  public void setPersonalization(String title, String description) {
    // nothing to do
  }

  @Override
  public void setPersonalization(IEquipmentItem item) {
    // nothing to do
  }

  @Override
  public void setPrintEnabled(IEquipmentStats equipment, boolean enabled) {
    printed.add(equipment);
  }

  @Override
  public boolean isPrintEnabled(IEquipmentStats stats) {
    return printed.contains(stats);
  }

  @Override
  public void setUnprinted() {
    //nothing to do    
  }

  @Override
  public void setPrinted(String printedStatId) {
    //nothing to do
  }

  @Override
  public MagicalMaterial getMaterial() {
    return null;
  }

  @Override
  public MaterialComposition getMaterialComposition() {
    return MaterialComposition.None;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    // nothing to do
  }

  @Override
  public void removeChangeListener(ChangeListener listener) {
    // nothing to do
  }

  @Override
  public void setTitle(String title) {
    // nothing to do
  }

  @Override
  public void setDescription(String description) {
    // nothing to do
  }

  @Override
  public ArtifactAttuneType getAttunementState() {
    return null;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public IEquipmentStats getStat(String name) {
    for (IEquipmentStats stats : allEquipments) {
      if (stats.getId().equals(name)) {
        return stats;
      }
    }
    return null;
  }

  @Override
  public boolean representsItemForUseInCombat() {
    return false;
  }

  @Override
  public String getBaseDescription() {
    return getDescription();
  }

  @Override
  public String getTitle() {
    return getTemplateId();
  }
}