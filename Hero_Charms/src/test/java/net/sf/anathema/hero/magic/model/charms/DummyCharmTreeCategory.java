package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.hero.charms.model.options.CharmOptionCheck;
import net.sf.anathema.hero.charms.model.options.CharmTreeCategoryImpl;
import net.sf.anathema.hero.magic.charm.Charm;

public class DummyCharmTreeCategory {

  public static CharmTreeCategoryImpl Create(CategoryReference category, Charm... charms) {
    CharmOptionCheck optionCheck = charm -> true;
    return new CharmTreeCategoryImpl(optionCheck, charms, category);
  }
}