package net.sf.anathema.character.main.magic.description;

import com.google.common.base.Function;
import net.sf.anathema.character.main.magic.model.magic.Magic;
import net.sf.anathema.lib.resources.Resources;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.transform;

public class AggregatedCharmDescriptionProvider implements MagicDescriptionProvider {

  private final List<MagicDescriptionProvider> providerList = new ArrayList<>();

  public AggregatedCharmDescriptionProvider(Resources resources) {
    providerList.add(new ShortMagicDescriptionProvider(resources));
  }

  public void addProvider(MagicDescriptionProvider provider) {
    providerList.add(0, provider);
  }

  @Override
  public MagicDescription getCharmDescription(Magic magic) {
    List<MagicDescription> descriptions = transform(providerList, new ToDescription(magic));
    return new AggregatedMagicDescription(descriptions);
  }

  private static class ToDescription implements Function<MagicDescriptionProvider, MagicDescription> {
    private final Magic magic;

    public ToDescription(Magic magic) {
      this.magic = magic;
    }

    @Override
    public MagicDescription apply(MagicDescriptionProvider input) {
      return input.getCharmDescription(magic);
    }
  }
}
