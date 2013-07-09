package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.combos.ComboRestrictions;
import net.sf.anathema.character.main.magic.model.charm.duration.SimpleDuration;
import net.sf.anathema.character.main.magic.model.charm.type.CharmType;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.character.main.traits.types.AttributeType;
import net.sf.anathema.character.main.traits.types.ValuedTraitType;
import net.sf.anathema.character.main.dummy.DummyCharmUtilities;
import net.sf.anathema.character.main.magic.model.combos.ComboArbitrator;
import net.sf.anathema.character.main.magic.model.combos.IComboRules;

public abstract class AbstractComboRulesTestCase {

  private IComboRules rules = new ComboArbitrator() {

    @Override
    protected boolean isCharmLegalByRules(Charm charm) {
      return charm.getDuration() == SimpleDuration.INSTANT_DURATION;
    }
  };

  protected IComboRules getRules() {
    return rules;
  }

  protected boolean comboSameAbilityCharms(CharmType type1, CharmType type2) {
    Charm charm1 = DummyCharmUtilities.createCharm(type1, new ValuedTraitType(AbilityType.Archery, 3));
    Charm charm2 = DummyCharmUtilities.createCharm(type2, new ValuedTraitType(AbilityType.Archery, 3));
    return rules.isComboLegal(charm1, charm2);
  }

  protected boolean comboDifferentAbilityCharms(CharmType type1, CharmType type2) {
    Charm charm1 = DummyCharmUtilities.createCharm(type1, new ValuedTraitType(AbilityType.Bureaucracy, 3));
    Charm charm2 = DummyCharmUtilities.createCharm(type2, new ValuedTraitType(AbilityType.Archery, 3));
    return rules.isComboLegal(charm1, charm2);
  }

  protected boolean comboDifferentAttributeCharms(CharmType type1, CharmType type2) {
    Charm charm1 = DummyCharmUtilities.createCharm(type1, new ValuedTraitType(AttributeType.Intelligence, 3));
    Charm charm2 = DummyCharmUtilities.createCharm(type2, new ValuedTraitType(AttributeType.Charisma, 3));
    return rules.isComboLegal(charm1, charm2);
  }

  protected boolean comboAbilityAttributeCharms(CharmType type1, CharmType type2) {
    Charm charm1 = DummyCharmUtilities.createCharm(type1, new ValuedTraitType(AbilityType.Performance, 3));
    Charm charm2 = DummyCharmUtilities.createCharm(type2, new ValuedTraitType(AttributeType.Charisma, 3));
    return rules.isComboLegal(charm1, charm2);
  }

  protected boolean comboAllAbilitiesCharmWithAbility(CharmType type1, CharmType type2) {
    Charm charm1 = DummyCharmUtilities.createCharm(type1, new ComboRestrictions(true, ""), new ValuedTraitType(AbilityType.Performance, 3));
    Charm charm2 = DummyCharmUtilities.createCharm(type2, new ValuedTraitType(AbilityType.Archery, 3));
    return rules.isComboLegal(charm1, charm2);
  }
}