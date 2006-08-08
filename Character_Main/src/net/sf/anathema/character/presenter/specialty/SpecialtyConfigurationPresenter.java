package net.sf.anathema.character.presenter.specialty;

import javax.swing.Icon;

import net.sf.anathema.character.generic.IBasicCharacterData;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.DedicatedCharacterChangeAdapter;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterListening;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.library.trait.presenter.AbstractTraitPresenter;
import net.sf.anathema.character.library.trait.specialties.ISpecialtyConfiguration;
import net.sf.anathema.character.library.trait.specialty.ISpecialtiesContainer;
import net.sf.anathema.character.library.trait.specialty.ISpecialty;
import net.sf.anathema.character.library.trait.specialty.ISpecialtyListener;
import net.sf.anathema.character.view.ISpecialtyView;
import net.sf.anathema.character.view.basic.IButtonControlledComboEditView;
import net.sf.anathema.framework.presenter.resources.BasicUi;
import net.sf.anathema.framework.view.AbstractSelectCellRenderer;
import net.sf.anathema.lib.collection.IdentityMapping;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.control.objectvalue.ITwoObjectsValueChangedListener;
import net.sf.anathema.lib.resources.IResources;

public class SpecialtyConfigurationPresenter extends AbstractTraitPresenter {

  private final IdentityMapping<ISpecialty, ISpecialtyView> viewsBySpecialty = new IdentityMapping<ISpecialty, ISpecialtyView>();

  private final ISpecialtyListener specialtyListener = new ISpecialtyListener() {

    public void specialtyAdded(ISpecialty specialty) {
      addSpecialtyView(specialty);
    }

    public void specialtyRemoved(ISpecialty specialty) {
      removeSpecialtyView(specialty);
    }

    public void specialtyValueChanged() {
      // Nothing to do
    }
  };

  private final IResources resources;
  private final ISpecialtyConfigurationView configurationView;
  private final IBasicCharacterData basicCharacterData;
  private final ICharacterListening characterListening;
  private final ISpecialtyConfiguration specialtyManagement;

  public SpecialtyConfigurationPresenter(
      ISpecialtyConfiguration specialtyManagement,
      IBasicCharacterData basicCharacterData,
      ICharacterListening characterListening,
      ISpecialtyConfigurationView configurationView,
      IResources resources) {
    this.specialtyManagement = specialtyManagement;
    this.basicCharacterData = basicCharacterData;
    this.characterListening = characterListening;
    this.configurationView = configurationView;
    this.resources = resources;
  }

  private void initTraitListening() {
    for (ITraitType traitType : getAllTraitsTypes()) {
      getSpecialtyContainerType(traitType).addSpecialtyListener(specialtyListener);
    }
  }

  private ISpecialtiesContainer getSpecialtyContainerType(ITraitType traitType) {
    return specialtyManagement.getSpecialtiesContainer(traitType);
  }

  private void resetSpecialtyView(final IButtonControlledComboEditView<ITraitType> specialtySelectionView) {
    specialtySelectionView.setText(""); //$NON-NLS-1$
    specialtySelectionView.setSelectedObject(null);
  }

  public void initPresentation() {
    initTraitListening();
    Icon addIcon = new BasicUi(resources).getMediumAddIcon();
    final IButtonControlledComboEditView<ITraitType> specialtySelectionView = configurationView.addSpecialtySelectionView(
        resources.getString("SpecialtyConfigurationView.SelectionCombo.Label"), //$NON-NLS-1$
        getAllTraitsTypes(),
        new AbstractSelectCellRenderer(resources) {
          @Override
          protected Object getCustomizedDisplayValue(Object value) {
            return resources.getString(((ITraitType) value).getId());
          }
        },
        addIcon);
    specialtySelectionView.addObjectSelectionChangedListener(new ITwoObjectsValueChangedListener<ITraitType, String>() {
      public void valueChanged(ITraitType oldValue1, String oldValue2, ITraitType newTraitType, String newSpecialtyName) {
        if (!newSpecialtyName.equals("")) { //$NON-NLS-1$
          getSpecialtyContainerType(newTraitType).addSpecialty(newSpecialtyName);
          resetSpecialtyView(specialtySelectionView);
        }
      }
    });
    resetSpecialtyView(specialtySelectionView);
    for (ITraitType traitType : getAllTraitsTypes()) {
      for (ISpecialty specialty : getSpecialtyContainerType(traitType).getSpecialties()) {
        addSpecialtyView(specialty);
      }
    }
    initExperiencedListening();
  }

  private void initExperiencedListening() {
    characterListening.addChangeListener(new DedicatedCharacterChangeAdapter() {
      @Override
      public void experiencedChanged(boolean experienced) {
        updateSpecialtyViewButtons();
      }
    });
    updateSpecialtyViewButtons();
  }

  protected void removeSpecialtyView(ISpecialty specialty) {
    ISpecialtyView view = viewsBySpecialty.get(specialty);
    viewsBySpecialty.remove(specialty);
    view.delete();
  }

  private ITraitType[] getAllTraitsTypes() {
    return specialtyManagement.getAllTraitTypes();
  }

  private void updateSpecialtyViewButtons() {
    for (ITraitType trait : getAllTraitsTypes()) {
      for (ISpecialty specialty : getSpecialtyContainerType(trait).getSpecialties()) {
        ISpecialtyView view = viewsBySpecialty.get(specialty);
        view.setDeleteButtonEnabled(specialty.getCreationValue() == 0 || !basicCharacterData.isExperienced());
      }
    }
  }

  private void addSpecialtyView(final ISpecialty specialty) {
    String traitTypeId = specialty.getBasicTrait().getType().getId();
    String traitName = resources.getString(traitTypeId);
    String specialtyName = specialty.getName();
    Icon deleteIcon = new BasicUi(resources).getMediumRemoveIcon();
    final ISpecialtyView specialtyView = configurationView.addSpecialtyView(
        traitName,
        specialtyName,
        deleteIcon,
        specialty.getCurrentValue(),
        specialty.getMaximalValue());
    addModelValueListener(specialty, specialtyView);
    addViewValueListener(specialtyView, specialty);
    specialtyView.addDeleteListener(new IChangeListener() {
      public void changeOccured() {
        ITraitType traitType = specialty.getBasicTrait().getType();
        getSpecialtyContainerType(traitType).removeSpecialty(specialty);
      }
    });
    viewsBySpecialty.put(specialty, specialtyView);
  }
}
