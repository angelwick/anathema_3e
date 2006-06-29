package net.sf.anathema.character.impl.module;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.ICharacterGenericsExtension;
import net.sf.anathema.character.generic.framework.configuration.ICharacterPreferencesConstants;
import net.sf.anathema.character.impl.module.preferences.RulesetPreferenceElement;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.extension.IAnathemaExtension;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.module.AbstractAnathemaModule;
import net.sf.anathema.framework.module.PreferencesElementsExtensionPoint;
import net.sf.anathema.framework.view.IAnathemaView;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.resources.IResources;

public class CharacterModule extends AbstractAnathemaModule {

  @Override
  public void initPresentation(IResources resources, IAnathemaModel model, IAnathemaView view) {
    super.initPresentation(resources, model, view);
    ICharacterGenerics characterGenerics = getCharacterGenerics(model);
    IItemType characterItemType = model.getItemTypeRegistry().getById(
        ExaltedCharacterItemTypeConfiguration.CHARACTER_ITEM_TYPE_ID);
    new CharacterModulePresenter(model, view, resources, characterItemType, characterGenerics);
    new CharacterPerformanceTuner(model, resources).startTuning(characterGenerics, characterItemType);
  }

  @Override
  public void fillPresentationExtensionPoints(
      IRegistry<String, IAnathemaExtension> extensionPointRegistry,
      IAnathemaModel model,
      IResources resources,
      IAnathemaView view) {
    super.fillPresentationExtensionPoints(extensionPointRegistry, model, resources, view);
    PreferencesElementsExtensionPoint preferencesPoint = (PreferencesElementsExtensionPoint) extensionPointRegistry.get(PreferencesElementsExtensionPoint.ID);
    preferencesPoint.register(ICharacterPreferencesConstants.RULESET_PREFERENCE, new RulesetPreferenceElement());
  }

  private final ICharacterGenerics getCharacterGenerics(IAnathemaModel anathemaModel) {
    IRegistry<String, IAnathemaExtension> extensionPointRegistry = anathemaModel.getExtensionPointRegistry();
    ICharacterGenericsExtension extension = (ICharacterGenericsExtension) extensionPointRegistry.get(ICharacterGenericsExtension.ID);
    return extension.getCharacterGenerics();
  }
}