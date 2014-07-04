package net.sf.anathema.hero.equipment.sheet.content;

import net.sf.anathema.hero.sheet.pdf.content.ReportContentFactory;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.initialization.Produces;

@Produces(ArmourContent.class)
public class ArmourContentFactory implements ReportContentFactory<ArmourContent> {

  private Resources resources;

  public ArmourContentFactory(Resources resources) {
    this.resources = resources;
  }

  @Override
  public ArmourContent create(ReportSession session) {
    return new ArmourContent(session.getHero(), resources);
  }
}
