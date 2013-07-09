package net.sf.anathema.character.main.magic.model.charms;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.charm.MartialArtsLevel;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.text.MessageFormat;

import static net.sf.anathema.character.main.magic.model.charm.CharmAttributeList.FORM_ATTRIBUTE;

public class MartialArtsUtilities {

  public static final SimpleIdentifier MARTIAL_ARTS = new SimpleIdentifier(AbilityType.MartialArts.name());

  public static boolean isMartialArtsCharm(Charm charm) {
    return charm.hasAttribute(MARTIAL_ARTS);
  }

  public static boolean isFormCharm(Charm charm) {
    return charm.hasAttribute(FORM_ATTRIBUTE);
  }

  public static MartialArtsLevel getLevel(Charm charm) {
    if (!isMartialArtsCharm(charm)) {
      return null;
    }
    for (MartialArtsLevel level : MartialArtsLevel.values()) {
      if (charm.hasAttribute(new SimpleIdentifier(level.name()))) {
        return level;
      }
    }
    String pattern = "Martial Arts Charm without level: {0}. Please ensure it has a Martial Arts level as a 'charmAttribute'.";
    String message = MessageFormat.format(pattern, charm.getId());
    throw new IllegalStateException(message);
  }

  public static boolean hasLevel(MartialArtsLevel level, Charm charm) {
    return charm.hasAttribute(new SimpleIdentifier(level.name()));
  }
}