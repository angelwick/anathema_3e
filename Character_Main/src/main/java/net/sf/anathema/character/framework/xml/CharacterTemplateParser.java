package net.sf.anathema.character.framework.xml;

import net.sf.anathema.character.framework.ICharacterTemplateRegistryCollection;
import net.sf.anathema.character.framework.type.CharacterTypes;
import net.sf.anathema.character.framework.xml.core.AbstractXmlTemplateParser;
import net.sf.anathema.hero.template.TemplateType;
import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Element;

public class CharacterTemplateParser extends AbstractXmlTemplateParser<GenericCharacterTemplate> {

  private CharacterTypes characterTypes;

  public CharacterTemplateParser(CharacterTypes characterTypes, ICharacterTemplateRegistryCollection registryCollection) {
    super(registryCollection.getCharacterTemplateRegistry());
    this.characterTypes = characterTypes;
  }

  @Override
  protected GenericCharacterTemplate createNewBasicTemplate() {
    return new GenericCharacterTemplate();
  }

  @Override
  public GenericCharacterTemplate parseTemplate(Element element) throws PersistenceException {
    GenericCharacterTemplate characterTemplate = new GenericCharacterTemplate();
    updateTemplateType(element, characterTemplate);
    parseModels(element, characterTemplate);
    return characterTemplate;
  }

  private void updateTemplateType(Element element, GenericCharacterTemplate characterTemplate) throws PersistenceException {
    TemplateType templateType = new TemplateTypeParser(characterTypes).parse(element);
    characterTemplate.setTemplateType(templateType);
  }

  private void parseModels(Element element, GenericCharacterTemplate characterTemplate) throws PersistenceException {
    Element modelsElement = element.element("models");
    if (modelsElement == null) {
      return;
    }
    for (Object modelElement : modelsElement.elements()) {
      String modelId = ((Element) modelElement).attributeValue("id");
      String modelTemplateId = ((Element) modelElement).attributeValue("template");
      characterTemplate.addModel(modelId, modelTemplateId);
    }
  }
}