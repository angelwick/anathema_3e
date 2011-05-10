package net.sf.anathema.character.equipment.impl.character.model.stats;

import net.sf.anathema.character.generic.equipment.ArtifactAttuneType;
import net.sf.anathema.character.generic.equipment.IArtifactStats;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

public class ArtifactStatsDecorator implements IArtifactStats
{
	
	  private IArtifactStats stats;
	  private ArtifactAttuneType type;
	  private IIdentificate name;

	  public ArtifactStatsDecorator(IArtifactStats stats, ArtifactAttuneType type) {
	    this.stats = stats;
	    this.type = type;
	    this.name = stats.getName();
	  }

	  public ArtifactStatsDecorator(IArtifactStats stats, String name) {
	    this.stats = stats;
	    this.type = ArtifactAttuneType.Unattuned;
	    this.name = new Identificate(name);
	  }
	  
	  public Integer getAttuneCost()
	  {
		  switch (type)
		  {
		  default:
		  case Unattuned: return 0;
		  case PartiallyAttuned: return stats.getAttuneCost();
		  case FullyAttuned: return stats.getAttuneCost();
		  case UnharmoniousAttuned: return 2 * stats.getAttuneCost();
		  }
	  }
	  
		public ArtifactAttuneType getAttuneType()
		{
			return type;
		}

	  public IEquipmentStats[] getViews() {
	    return new IEquipmentStats[] { this };
	  }

	  public IIdentificate getName() {
	    return name;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (!(obj instanceof ArtifactStatsDecorator)) {
	      return false;
	    }
	    ArtifactStatsDecorator view = (ArtifactStatsDecorator) obj;
	    return view.stats.equals(stats) && view.type == type;
	  }

	  @Override
	  public int hashCode() {
	    return stats.hashCode();
	  }

	  @Override
	  public String getId() {
	    return name.getId(); //$NON-NLS-1$
	  }
}
