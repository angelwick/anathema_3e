package net.sf.anathema.hero.application.repositorytree;

import net.sf.anathema.hero.application.item.HeroReferenceScanner;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.framework.HeroEnvironmentExtractor;
import net.sf.anathema.hero.platform.JsonHeroReferenceScanner;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.frame.ApplicationModel;
import net.sf.anathema.platform.item.ForItemType;
import net.sf.anathema.platform.item.IItemTypeViewProperties;
import net.sf.anathema.platform.item.ItemTypePresentationFactory;
import net.sf.anathema.platform.repository.IRepositoryFileResolver;

import static net.sf.anathema.hero.application.itemtype.CharacterItemType.CHARACTER_ITEM_TYPE_ID;
import static net.sf.anathema.hero.application.itemtype.CharacterItemTypeRetrieval.retrieveCharacterItemType;

@ForItemType(CHARACTER_ITEM_TYPE_ID)
public class HeroItemTypePresentationFactory implements ItemTypePresentationFactory {

  @Override
  public IItemTypeViewProperties createItemTypeCreationProperties(ApplicationModel anathemaModel, Resources resources) {
    HeroEnvironment generics = HeroEnvironmentExtractor.getGenerics(anathemaModel);
    IRepositoryFileResolver fileResolver = anathemaModel.getRepository().getRepositoryFileResolver();
    HeroReferenceScanner scanner = new JsonHeroReferenceScanner(generics.getCharacterTypes(), fileResolver);
    return new HeroViewProperties(retrieveCharacterItemType(), resources, scanner);
  }
}