package net.sf.anathema.character.generic.impl.magic.persistence;

import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.ATTRIB_EXALT;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_COST;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_DURATION;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_PERMANENT;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_PREREQUISITE_LIST;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_TEMPORARY;

import net.sf.anathema.character.generic.impl.magic.Charm;
import net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.CharmAttributeBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.CharmTypeBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.ComboRulesBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.CostListBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.DurationBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.GroupStringBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.ICostListBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.IIdStringBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.SourceBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.prerequisite.IAttributeRequirementBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.prerequisite.ITraitPrerequisitesBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.prerequisite.PrerequisiteListBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.prerequisite.CharmPrerequisiteList;
import net.sf.anathema.character.generic.magic.charms.CharmException;
import net.sf.anathema.character.generic.magic.charms.ICharmAttribute;
import net.sf.anathema.character.generic.magic.charms.IComboRestrictions;
import net.sf.anathema.character.generic.magic.charms.duration.IDuration;
import net.sf.anathema.character.generic.magic.charms.type.ICharmTypeModel;
import net.sf.anathema.character.generic.magic.general.ICostList;
import net.sf.anathema.character.generic.magic.general.IMagicSource;
import net.sf.anathema.character.generic.magic.general.IPermanentCostList;
import net.sf.anathema.character.generic.traits.IGenericTrait;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;

import org.dom4j.Element;

public class CharmBuilder implements ICharmBuilder {

  private final CharmTypeBuilder charmTypeBuilder = new CharmTypeBuilder();
  private final ICostListBuilder costListBuilder = new CostListBuilder();
  private final DurationBuilder durationBuilder = new DurationBuilder();
  private final GroupStringBuilder groupBuilder = new GroupStringBuilder();
  private final ComboRulesBuilder comboBuilder = new ComboRulesBuilder();
  private final SourceBuilder sourceBuilder = new SourceBuilder();
  private final CharmAttributeBuilder attributeBuilder = new CharmAttributeBuilder();
  private final IIdStringBuilder idBuilder;
  private final ITraitPrerequisitesBuilder traitsBuilder;
  private final IAttributeRequirementBuilder attributeRequirementsBuilder;

  public CharmBuilder(
      IIdStringBuilder idBuilder,
      ITraitPrerequisitesBuilder traitsBuilder,
      IAttributeRequirementBuilder attributeRequirementsBuilder) {
    this.idBuilder = idBuilder;
    this.traitsBuilder = traitsBuilder;
    this.attributeRequirementsBuilder = attributeRequirementsBuilder;
  }

  public Charm buildCharm(Element charmElement) throws PersistenceException {
    String id = idBuilder.build(charmElement);
    CharacterType characterType = getCharacterType(charmElement, id);
    ICostList temporaryCost;
    IPermanentCostList permanentCost;
    try {
      Element costElement = charmElement.element(TAG_COST);
      temporaryCost = costListBuilder.buildTemporaryCostList(costElement.element(TAG_TEMPORARY));
      permanentCost = costListBuilder.buildPermanentCostList(costElement.element(TAG_PERMANENT));
    }
    catch (PersistenceException e) {
      throw new CharmException("Error building costlist for charm " + id, e); //$NON-NLS-1$
    }
    IComboRestrictions comboRules;
    try {
      comboRules = comboBuilder.buildComboRules(charmElement);
    }
    catch (IllegalArgumentException e) {
      throw new CharmException("Error in Charm " + id, e); //$NON-NLS-1$
    }
    IDuration duration = null;
    try {
      duration = durationBuilder.buildDuration(charmElement.element(TAG_DURATION));
    }
    catch (PersistenceException e) {
      throw new CharmException("Error in Charm duration " + id, e); //$NON-NLS-1$
    }
    ICharmTypeModel charmTypeModel = charmTypeBuilder.build(charmElement);
    IMagicSource[] sources = sourceBuilder.buildSourceList(charmElement);
    CharmPrerequisiteList prerequisiteList = getPrerequisites(charmElement, id);
    IGenericTrait[] prerequisites = prerequisiteList.getPrerequisites();
    final IGenericTrait primaryPrerequisite = (prerequisites.length != 0) ? prerequisites[0] : null;
    String group = groupBuilder.build(charmElement, primaryPrerequisite);
    Charm charm = new Charm(
        characterType,
        id,
        group,
        prerequisiteList,
        temporaryCost,
        permanentCost,
        comboRules,
        duration,
        charmTypeModel,
        sources);
    for (ICharmAttribute attribute : attributeBuilder.buildCharmAttributes(charmElement, primaryPrerequisite)) {
      charm.addCharmAttribute(attribute);
    }
    loadSpecialLearning(charmElement, charm);
    return charm;
  }

  private CharmPrerequisiteList getPrerequisites(Element charmElement, String id) throws CharmException {
    CharmPrerequisiteList prerequisiteList;
    try {
      Element prerequisiteListElement = ElementUtilities.getRequiredElement(charmElement, TAG_PREREQUISITE_LIST);
      prerequisiteList = new PrerequisiteListBuilder(traitsBuilder, attributeRequirementsBuilder).buildPrerequisiteList(prerequisiteListElement);
    }
    catch (PersistenceException e) {
      throw new CharmException("Error in Charm " + id, e); //$NON-NLS-1$
    }
    return prerequisiteList;
  }

  private CharacterType getCharacterType(Element charmElement, String id) throws CharmException {
    String typeAttribute = charmElement.attributeValue(ATTRIB_EXALT);
    CharacterType characterType;
    try {
      characterType = CharacterType.getById(typeAttribute);
    }
    catch (IllegalArgumentException e) {
      throw new CharmException("No chararacter type given for Charm: " + id, e); //$NON-NLS-1$
    }
    return characterType;
  }

  private void loadSpecialLearning(Element charmElement, Charm charm) {
    Element learningElement = charmElement.element(ICharmXMLConstants.TAG_LEARNING);
    if (learningElement == null) {
      return;
    }
    for (Element favoredElement : ElementUtilities.elements(learningElement, ICharmXMLConstants.ATTRB_FAVORED)) {
      String casteId = favoredElement.attributeValue(ICharmXMLConstants.TAG_CASTE);
      charm.addFavoredCasteId(casteId);
    }
  }
}