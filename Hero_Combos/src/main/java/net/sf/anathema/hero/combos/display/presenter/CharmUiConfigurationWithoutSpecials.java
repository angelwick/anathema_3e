package net.sf.anathema.hero.combos.display.presenter;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.display.MagicDisplayLabeler;
import net.sf.anathema.hero.charms.display.tooltip.CharmTooltipBuilder;
import net.sf.anathema.library.presenter.AbstractUIConfiguration;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;

public class CharmUiConfigurationWithoutSpecials extends AbstractUIConfiguration<Charm> {
  private final Resources resources;
  private final CharmTooltipBuilder charmInfoStringProvider;

  public CharmUiConfigurationWithoutSpecials(Resources resources, CharmTooltipBuilder charmInfoStringProvider) {
    this.resources = resources;
    this.charmInfoStringProvider = charmInfoStringProvider;
  }

  @Override
  protected String labelForExistingValue(Charm value) {
    return new MagicDisplayLabeler(resources).getLabelForMagic(value);
  }

  @Override
  protected void configureTooltipForExistingValue(Charm value, ConfigurableTooltip configurableTooltip) {
    charmInfoStringProvider.configureTooltipWithoutSpecials(value, configurableTooltip);
  }
}