package net.sf.anathema.hero.thaumaturgy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.dummy.DummyHeroEnvironment;
import net.sf.anathema.hero.experience.model.ExperienceModel;
import net.sf.anathema.hero.experience.model.ExperienceModelImpl;
import net.sf.anathema.hero.thaumaturgy.advance.ThaumaturgyExperienceModel;
import net.sf.anathema.hero.thaumaturgy.model.ThaumaturgyModel;
import net.sf.anathema.hero.thaumaturgy.model.ThaumaturgyModelImpl;
import net.sf.anathema.hero.thaumaturgy.model.ThaumaturgyProvider;
import net.sf.anathema.hero.thaumaturgy.model.ThaumaturgyRitual;
import net.sf.anathema.hero.traits.dummy.DummyTraitModel;
import net.sf.anathema.library.model.OptionalTraitReference;
import net.sf.anathema.points.model.PointModelImpl;
import net.sf.anathema.points.model.PointsModel;
import net.sf.anathema.points.template.PointsTemplate;

import org.junit.Before;
import org.junit.Test;

public class ThaumaturgyTests {
	
	DummyHero hero;
	ThaumaturgyModel model;
	PointsModel points;
	ExperienceModel xp;
	
	
	@Before
  public void setUp() throws Exception {
    hero = new DummyHero();
    
    model = new ThaumaturgyModelImpl();
    hero.addModel(model);
    
    points = new PointModelImpl(new PointsTemplate());
    hero.addModel(points);
    
    DummyTraitModel traits = new DummyTraitModel();
    hero.addModel(traits);
    
    DummyHeroEnvironment dummyEnvironment = new DummyHeroEnvironment();
    DummyRitualProvider provider = new DummyRitualProvider();
    dummyEnvironment.dataSets.add(provider);
    
    xp = new ExperienceModelImpl();
    xp.setExperienced(true);
    hero.addModel(xp);
    
    model.initialize(dummyEnvironment, hero);
  }
	
	@Test
	public void verifyNoThaumaturgyIfNotGranted() {
		model.selectFirstOption();
		assertThat(model.isEntryAllowed(), is(false));
	}
	
	@Test
	public void verifyThaumaturgyLearnableIfGranted() {
		model.addThaumaturgyProvider(getAllowsThaumaturgyProvider());
		model.selectFirstOption();
		assertThat(model.isEntryAllowed(), is(true));
	}
	
	@Test
	public void verifyThaumaturgyBasicCost() {
		model.addThaumaturgyProvider(getAllowsThaumaturgyProvider());
		model.setSelectedTraitOption(getOption("Second Bread"));
		model.commitSelection();
		assertThat(points.getExperiencePointManagement().getTotalCosts(),
				is(ThaumaturgyExperienceModel.BASIC_RITUAL_COST));
	}
	
	@Test
	public void verifyThaumaturgyAdvancedCost() {
		model.addThaumaturgyProvider(getAllowsThaumaturgyProvider());
		model.setSelectedTraitOption(getOption("Speak To Ozashun"));
		model.commitSelection();
		assertThat(points.getExperiencePointManagement().getTotalCosts(),
				is(ThaumaturgyExperienceModel.ADVANCED_RITUAL_COST));
	}
	
	@Test
	public void verifyThaumaturgyFreeRitualDiscountCost() {
		model.addThaumaturgyProvider(getFreeRitualProvider());
		model.setSelectedTraitOption(getOption("Second Bread"));
		model.commitSelection();
		model.setSelectedTraitOption(getOption("Read The Tea Leaves"));
		model.commitSelection();
		model.setSelectedTraitOption(getOption("Speak To Ozashun"));
		model.commitSelection();
		assertThat(points.getExperiencePointManagement().getTotalCosts(),
				is(2 * ThaumaturgyExperienceModel.BASIC_RITUAL_COST));
	}
	
	private ThaumaturgyRitual getOption(String name) {
		return model.findOptionByReference(new OptionalTraitReference(name));
	}
	
	private ThaumaturgyProvider getAllowsThaumaturgyProvider() {
		return new TestThaumaturgyProvider(true, 0);
	}
	
	private ThaumaturgyProvider getFreeRitualProvider() {
		return new TestThaumaturgyProvider(true, 1);
	}
	
	private class TestThaumaturgyProvider implements ThaumaturgyProvider {
		private final boolean grantsThaumaturgy;
		private final int grantsFreeRituals;
		
		public TestThaumaturgyProvider(boolean grants, int freeRituals) {
			this.grantsThaumaturgy = grants;
			this.grantsFreeRituals = freeRituals;
		}
		
		@Override
		public boolean grantsThaumaturgy() {
			return grantsThaumaturgy;
		}
		@Override
		public int numberOfRitualsProvided() {
			return grantsFreeRituals;
		}
	}
}