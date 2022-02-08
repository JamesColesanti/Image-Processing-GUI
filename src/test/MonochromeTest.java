import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.files.PPM;
import model.filters.IFilter;
import model.filters.Monochrome;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Monochrome. This class tests that when images are filtered using the Monochrome
 * function object, the monochrome filter is properly applied. Each new pixel in the image should
 * have a new color value determined by the transpose matrix and the math done in the Monochrome
 * class.
 */
public class MonochromeTest {

  private IFilter monochrome;
  private Image monochromeTestImage;
  private ImageInterface monochromeImportedTest;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(13, 200, 0)),
        new ArrayList<>(Arrays.asList(255, 180, 0))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0)),
        new ArrayList<>(Arrays.asList(128, 63, 0)),
        new ArrayList<>(Arrays.asList(11, 89, 0))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21)),
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255))));
    this.monochromeImportedTest = new PPM().importFile("res/TestingImage.ppm");
    this.monochromeTestImage = new Image(red, green, blue);
    this.monochrome = new Monochrome();
  }

  /*
  Tests for newColorValsAt()
   */

  // Tests that an exception is thrown when a negative x is given
  @Test(expected = IllegalArgumentException.class)
  public void monochromeNegativeX() {
    monochrome.newColorValsAt(-1, 0,  this.monochromeImportedTest);
  }

  // Tests that an exception is thrown when a negative y is given
  @Test(expected = IllegalArgumentException.class)
  public void monochromeNegativeY() {
    monochrome.newColorValsAt(1, -1,  this.monochromeImportedTest);
  }

  // Tests that an exception is thrown when a negative x and y are given
  @Test(expected = IllegalArgumentException.class)
  public void monochromeNegativeXAndY() {
    monochrome.newColorValsAt(-3, -9,  this.monochromeImportedTest);
  }

  // Tests that an exception is thrown when a x out of of the width is given
  @Test(expected = IllegalArgumentException.class)
  public void monochromeTooBigY() {
    monochrome.newColorValsAt(0, 3,  this.monochromeTestImage);
  }

  // Tests that an exception is thrown when a x out of of the height is given
  @Test(expected = IllegalArgumentException.class)
  public void monochromeTooBigX() {
    monochrome.newColorValsAt(3, 0,  this.monochromeTestImage);
  }

  // Tests that an exception is thrown when passed a null image
  @Test(expected = IllegalArgumentException.class)
  public void monochromeNullImageWasPassed() {
    monochrome.newColorValsAt(0, 0,  null);
  }

  // Tests that when monochrome is called on the top left pixel it works as expected
  @Test
  public void monochromeCornerPixels() {
    assertEquals(2,
        monochrome.newColorValsAt(0, 2, this.monochromeTestImage).get(0).intValue());
    assertEquals(18,
        monochrome.newColorValsAt(2, 2, this.monochromeTestImage).get(1).intValue());
    assertEquals(217,
        monochrome.newColorValsAt(0, 0, this.monochromeTestImage).get(2).intValue());
    assertEquals(69,
        monochrome.newColorValsAt(2, 0, this.monochromeTestImage).get(0).intValue());
  }

  // Tests that when monochrome is called on the top left pixel it works as expected with a ppm
  @Test
  public void monochromeCornerPixelsTestImage() {
    assertEquals(211,
        monochrome.newColorValsAt(0, this.monochromeImportedTest.getImageHeight() - 1,
            this.monochromeImportedTest).get(0).intValue());
    assertEquals(28,
        monochrome.newColorValsAt(this.monochromeImportedTest.getImageWidth() - 1,
            this.monochromeImportedTest.getImageHeight() - 1,
            this.monochromeImportedTest).get(1).intValue());
    assertEquals(12,
        monochrome.newColorValsAt(0, 0, this.monochromeImportedTest).get(2).intValue());
    assertEquals(11,
        monochrome.newColorValsAt(this.monochromeImportedTest.getImageWidth() - 1, 0,
            this.monochromeImportedTest).get(0).intValue());
  }

  // Tests that when monochrome is called on the top middle pixel it works as expected
  @Test
  public void monochromePixelInMiddleOfSide() {
    assertEquals(103,
        monochrome.newColorValsAt(1, 0, this.monochromeTestImage).get(0).intValue());
    assertEquals(0,
        monochrome.newColorValsAt(1, 2, this.monochromeTestImage).get(1).intValue());
    assertEquals(217,
        monochrome.newColorValsAt(0, 0, this.monochromeTestImage).get(2).intValue());
    assertEquals(69,
        monochrome.newColorValsAt(2, 0, this.monochromeTestImage).get(0).intValue());
  }

  // Tests that when monochrome is called on the top left pixel it works as expected with a ppm
  @Test
  public void monochromePixelsInMiddleOfSideTestImage() {
    assertEquals(10,
        monochrome.newColorValsAt(13,0,
            this.monochromeImportedTest).get(0).intValue());
    assertEquals(183,
        monochrome.newColorValsAt(0,30,
            this.monochromeImportedTest).get(1).intValue());
    assertEquals(22,
        monochrome.newColorValsAt(20, this.monochromeImportedTest.getImageHeight() - 1,
            this.monochromeImportedTest).get(2).intValue());
    assertEquals(42,
        monochrome.newColorValsAt(this.monochromeImportedTest.getImageWidth() - 1, 20,
            this.monochromeImportedTest).get(0).intValue());
  }

  // Tests that when monochrome is called on the middle pixel it works as expected
  @Test
  public void monochromeMiddlePix() {
    assertEquals(94,
        monochrome.newColorValsAt(1, 1, this.monochromeTestImage).get(1).intValue());
  }

  // Tests that when monochrome is called on the middle pixel it works as expected
  @Test
  public void monochromeMiddlePixTestImage() {
    assertEquals(83,
        monochrome.newColorValsAt(20, 20, this.monochromeImportedTest).get(2).intValue());
  }
}