package net.sf.anathema.hero.magic.charm.prerequisite;

import net.sf.anathema.charm.old.attribute.MagicAttribute;
import net.sf.anathema.hero.magic.charm.Charm;

public interface CharmPrerequisiteVisitor {

  void requiresMagicAttributes(MagicAttribute attribute, int count);

  void requiresCharm(Charm prerequisite);

  void requiresCharmFromSelection(Charm[] prerequisites, int threshold);
}
