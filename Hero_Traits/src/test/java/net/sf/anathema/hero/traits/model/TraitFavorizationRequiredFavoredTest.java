package net.sf.anathema.hero.traits.model;

import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.traits.dummy.DummyTrait;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TraitFavorizationRequiredFavoredTest {

  private DummyTrait trait;

  @Before
  public void setUp() throws Exception {
    this.trait = new DummyTrait(AbilityType.Performance);
  }

  private TraitFavorization createObjectUnderTest(boolean isRequiredFavored) {
    DummyHero dummyHero = new DummyHero();
    return new TraitFavorization(dummyHero, null, new FriendlyIncrementChecker(), trait, isRequiredFavored);
  }

  @Test
  public void testCreationWithIsRequiredFavoredTrue() throws Exception {
    TraitFavorization favorization = createObjectUnderTest(true);
    assertEquals(FavorableState.Favored, favorization.getFavorableState());
  }

  @Test
  public void testCreationWithIsRequiredFavoredFalse() throws Exception {
    TraitFavorization favorization = createObjectUnderTest(false);
    assertEquals(FavorableState.Default, favorization.getFavorableState());
  }

  @Test
  public void testSetFavoredFalseOnRequiredFavoredResultsInFavored() throws Exception {
    TraitFavorization favorization = createObjectUnderTest(true);
    favorization.setFavored(false);
    assertEquals(FavorableState.Favored, favorization.getFavorableState());
  }

  @Test(expected = IllegalStateException.class)
  public void testSetCasteNotAllowedForRequiredFavored() throws Exception {
    DummyHero dummyHero = new DummyHero();
    final TraitFavorization favorization = new TraitFavorization(dummyHero, null, new GrumpyIncrementChecker(), trait, true);
    favorization.setCaste(true);
  }
}