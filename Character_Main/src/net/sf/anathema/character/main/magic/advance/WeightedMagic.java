package net.sf.anathema.character.main.magic.advance;

import net.sf.anathema.character.main.magic.model.magic.Magic;
import net.sf.anathema.lib.compare.WeightedObject;

public class WeightedMagic extends WeightedObject<Magic> {

  public WeightedMagic(Magic magic, int weight) {
    super(magic, weight);
  }
}