package net.sf.anathema.character.solar.reporting.content;

import com.google.common.base.Strings;
import net.sf.anathema.character.library.virtueflaw.model.DescriptiveVirtueFlaw;
import net.sf.anathema.character.library.virtueflaw.model.DescriptiveVirtueFlawModel;
import net.sf.anathema.character.library.virtueflaw.model.GreatCurseFetcher;
import net.sf.anathema.character.reporting.pdf.content.AbstractSubBoxContent;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.lang.StringUtilities;
import net.sf.anathema.lib.resources.Resources;

public class VirtueFlawContent extends AbstractSubBoxContent {

  private Hero hero;

  protected VirtueFlawContent(Hero hero, Resources resources) {
    super(resources);
    this.hero = hero;
  }

  @Override
  public String getHeaderKey() {
    return "GreatCurse.Solar";
  }

  public String getVirtueFlawName() {
    return getVirtueFlawModel().getName().getText();
  }

  public int getLimitValue() {
    return getVirtueFlawModel().getLimitTrait().getCurrentValue();
  }

  public String getLimitBreakCondition() {
    return getVirtueFlawModel().getLimitBreak().getText();
  }

  public boolean isNameDefined() {
    return !StringUtilities.isNullOrTrimmedEmpty(getVirtueFlawName());
  }

  public boolean isConditionDefined() {
    return !Strings.isNullOrEmpty(getLimitBreakCondition());
  }

  private DescriptiveVirtueFlaw getVirtueFlawModel() {
    return ((DescriptiveVirtueFlawModel) GreatCurseFetcher.fetch(hero)).getVirtueFlaw();
  }
}
