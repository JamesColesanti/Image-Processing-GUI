import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import model.filters.IPosn;
import model.filters.ISeed;
import model.filters.Posn;
import model.filters.Seed;
import model.filters.SeedComparator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Seed class, the Seed comparator, and the Posn class.
 */
public class SeedPosnAndComparatorTest {
  private ISeed testSeed;

  @Before
  public void initData() {
    this.testSeed = new Seed(8, 55);
  }

  /*
  Tests for Seed class
   */

  /*
  Tests for getXOfSeed
   */

  // tests that the x of ths seed is returned with valid data
  @Test
  public void getXWorksValidSeed() {
    assertEquals(8,this.testSeed.getXOfSeed());
  }

  /*
  Tests for getYOfSeed
   */

  // tests that the y of ths seed is returned with valid data
  @Test
  public void getYWorksValidSeed() {
    assertEquals(55,this.testSeed.getYOfSeed());
  }

  /*
  Tests for addRed
   */

  // tests that add red adds a value just to the redValues list
  @Test
  public void addRedValueWorks() {
    this.testSeed.setAverages();
    assertEquals(-1, this.testSeed.getFinalRedVal());
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(80);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(90);
    this.testSeed.setAverages();
    assertEquals(20, this.testSeed.getFinalRedVal());
  }

  /*
  Tests for addGreen
   */

  // tests that add green adds a value just to the greenValues list
  @Test
  public void addGreenValueWorks() {
    this.testSeed.setAverages();
    assertEquals(-1, this.testSeed.getFinalGreenVal());
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(80);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(151, this.testSeed.getFinalGreenVal());
  }

  /*
  Tests for addBlue
   */

  // tests that add blue adds a value just to the greenValues list
  @Test
  public void addBlueValueWorks() {
    this.testSeed.setAverages();
    assertEquals(-1, this.testSeed.getFinalBlueVal());
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(81);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(60, this.testSeed.getFinalBlueVal());
  }

  /*
  Tests for setAverages
   */

  // tests set averages returns -1 when the lists are empty
  @Test
  public void setAverageEmptyValues() {
    this.testSeed.setAverages();
    assertEquals(-1, this.testSeed.getFinalRedVal());
    assertEquals(-1, this.testSeed.getFinalGreenVal());
    assertEquals(-1, this.testSeed.getFinalBlueVal());
  }

  // tests that set average works with non empty lists
  @Test
  public void setAverageWorksWithNonEmptyList() {
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(81);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(20, this.testSeed.getFinalRedVal());
    assertEquals(152, this.testSeed.getFinalGreenVal());
    assertEquals(60, this.testSeed.getFinalBlueVal());
  }

  /*
  Tests for getFinalRedVal
   */

  // tests that getFinalRedValValid works with valid data
  @Test
  public void getFinalRedValValid() {
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(81);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(20, this.testSeed.getFinalRedVal());
  }

  /*
  Tests for getFinalGreenVal
   */

  // tests that getFinalBlueValValid works with valid data
  @Test
  public void getFinalBlueValValid() {
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(81);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(152, this.testSeed.getFinalGreenVal());
  }

  /*
  Tests for getFinalBlueVal
   */

  // tests that getFinalBlueVal works with valid data
  @Test
  public void getFinalGreenValValid() {
    this.testSeed.addRed(10);
    this.testSeed.addRed(20);
    this.testSeed.addRed(30);
    this.testSeed.addGreen(81);
    this.testSeed.addGreen(120);
    this.testSeed.addGreen(255);
    this.testSeed.addBlue(30);
    this.testSeed.addBlue(40);
    this.testSeed.addBlue(110);
    this.testSeed.setAverages();
    assertEquals(60, this.testSeed.getFinalBlueVal());
  }

  /*
  Test for SeedComparator
   */

  /*
  Test for compare
   */

  // tests that SeedComparator works as expected
  @Test
  public void seedComparatorWorks() {
    Comparator<ISeed> seedComparator = new SeedComparator(20);
    List<ISeed> testSeedList = new ArrayList<>(Arrays.asList(
        new Seed(9,8), new Seed(0,0), new Seed(8,8),
        new Seed(9,7), new Seed(9, 9), new Seed(10, 9)
    ));
    testSeedList.sort(seedComparator);

    assertEquals(0,testSeedList.get(0).getXOfSeed());
    assertEquals(0,testSeedList.get(0).getYOfSeed());

    assertEquals(9,testSeedList.get(1).getXOfSeed());
    assertEquals(7,testSeedList.get(1).getYOfSeed());

    assertEquals(8,testSeedList.get(2).getXOfSeed());
    assertEquals(8,testSeedList.get(2).getYOfSeed());

    assertEquals(9,testSeedList.get(3).getXOfSeed());
    assertEquals(8,testSeedList.get(3).getYOfSeed());

    assertEquals(9,testSeedList.get(4).getXOfSeed());
    assertEquals(9,testSeedList.get(4).getYOfSeed());

    assertEquals(10,testSeedList.get(5).getXOfSeed());
    assertEquals(9,testSeedList.get(5).getYOfSeed());
  }

  /*
  Posn tests
   */

  /*
  Tests for getX
   */

  // tests that getX works with valid data
  @Test
  public void testGetXValidData() {
    IPosn testPosn = new Posn(87, 120, testSeed);
    assertEquals(87, testPosn.getX());
  }

  /*
  Tests for getY
   */

  // tests that getY works with valid data
  @Test
  public void testGetYValidData() {
    IPosn testPosn = new Posn(85, 129, testSeed);
    assertEquals(129, testPosn.getY());
  }

  /*
  Tests for getSeed
   */

  // tests that getSeed works with valid data
  @Test
  public void testGetSeedValidData() {
    Posn testPosn = new Posn(85, 120, new Seed(12, 255));
    ISeed resSeed = testPosn.getSeed();
    assertEquals(12, resSeed.getXOfSeed());
    assertEquals(255, resSeed.getYOfSeed());
    resSeed.setAverages();
    assertEquals(-1, resSeed.getFinalRedVal());
    assertEquals(-1, resSeed.getFinalGreenVal());
    assertEquals(-1, resSeed.getFinalBlueVal());
  }

  /*
  Tests for equals
   */

  // tests that equals is properly overridden
  @Test
  public void equalsOverridenProperlyPosn() {
    IPosn testPosn = new Posn(17, 89, testSeed);
    IPosn testPosn1 = new Posn(17, 89, testSeed);
    IPosn testPosn2 = new Posn(18, 90, testSeed);
    assertTrue(testPosn.equals(testPosn));
    assertTrue(testPosn.equals(testPosn1));
    assertFalse(testPosn.equals(testPosn2));

  }


}
