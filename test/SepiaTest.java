import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.files.PPM;
import model.filters.IFilter;
import model.filters.Sepia;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Sepia. This class tests that when images are filtered using the Sepia
 * function object, the sepia filter is properly applied. Each new pixel in the image should
 * have a new color value determined by the transpose matrix and the math done in the Sepia
 * class.
 */
public class SepiaTest {

  private IFilter sepia;
  private Image sepiaTestImage;
  private ImageInterface sepiaImportedTest;

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
    this.sepiaImportedTest = new PPM().importFile("res/TestingImage.ppm");
    this.sepiaTestImage = new Image(red, green, blue);
    this.sepia = new Sepia();
  }

  /*
  Tests for newColorValsAt()
   */

  // Tests that an exception is thrown when a negative x is given
  @Test(expected = IllegalArgumentException.class)
  public void sepiaNegativeX() {
    sepia.newColorValsAt(-1, 0,  this.sepiaImportedTest);
  }

  // Tests that an exception is thrown when a negative y is given
  @Test(expected = IllegalArgumentException.class)
  public void sepiaNegativeY() {
    sepia.newColorValsAt(1, -1,  this.sepiaImportedTest);
  }

  // Tests that an exception is thrown when a negative x and y are given
  @Test(expected = IllegalArgumentException.class)
  public void sepiaNegativeXAndY() {
    sepia.newColorValsAt(-3, -9,  this.sepiaImportedTest);
  }

  // Tests that an exception is thrown when a x out of of the width is given
  @Test(expected = IllegalArgumentException.class)
  public void sepiaTooBigY() {
    sepia.newColorValsAt(0, 3,  this.sepiaTestImage);
  }

  // Tests that an exception is thrown when a x out of of the height is given
  @Test(expected = IllegalArgumentException.class)
  public void sepiaTooBigX() {
    sepia.newColorValsAt(3, 0,  this.sepiaTestImage);
  }

  // Tests that an exception is thrown when passed a null image
  @Test(expected = IllegalArgumentException.class)
  public void sepiaNullImageWasPassed() {
    sepia.newColorValsAt(0, 0,  null);
  }

  // Tests that when sepia is called on the top left pixel it works as expected
  @Test
  public void sepiaCornerPixels() {
    assertEquals(4,
        sepia.newColorValsAt(0, 2, this.sepiaTestImage).get(0).intValue());
    assertEquals(43,
        sepia.newColorValsAt(2, 2, this.sepiaTestImage).get(1).intValue());
    assertEquals(185,
        sepia.newColorValsAt(0, 0, this.sepiaTestImage).get(2).intValue());
    assertEquals(127,
        sepia.newColorValsAt(2, 0, this.sepiaTestImage).get(0).intValue());
  }

  // Tests that when sepia is called on the top left pixel it works as expected with a ppm
  @Test
  public void sepiaCornerPixelsTestImage() {
    assertEquals(255,
        sepia.newColorValsAt(0, this.sepiaImportedTest.getImageHeight() - 1,
            this.sepiaImportedTest).get(0).intValue());
    assertEquals(35,
        sepia.newColorValsAt(this.sepiaImportedTest.getImageWidth() - 1,
            this.sepiaImportedTest.getImageHeight() - 1,
            this.sepiaImportedTest).get(1).intValue());
    assertEquals(11,
        sepia.newColorValsAt(0, 0, this.sepiaImportedTest).get(2).intValue());
    assertEquals(14,
        sepia.newColorValsAt(this.sepiaImportedTest.getImageWidth() - 1, 0,
            this.sepiaImportedTest).get(0).intValue());
  }

  // Tests that when sepia is called on the top middle pixel it works as expected
  @Test
  public void sepiaPixelInMiddleOfSide() {
    assertEquals(126,
        sepia.newColorValsAt(1, 0, this.sepiaTestImage).get(0).intValue());
    assertEquals(0,
        sepia.newColorValsAt(1, 2, this.sepiaTestImage).get(1).intValue());
    assertEquals(185,
        sepia.newColorValsAt(0, 0, this.sepiaTestImage).get(2).intValue());
    assertEquals(127,
        sepia.newColorValsAt(2, 0, this.sepiaTestImage).get(0).intValue());
  }

  // Tests that when sepia is called on the top left pixel it works as expected with a ppm
  @Test
  public void sepiaPixelsInMiddleOfSideTestImage() {
    assertEquals(13,
        sepia.newColorValsAt(13,0,
            this.sepiaImportedTest).get(0).intValue());
    assertEquals(211,
        sepia.newColorValsAt(0,30,
            this.sepiaImportedTest).get(1).intValue());
    assertEquals(21,
        sepia.newColorValsAt(20, this.sepiaImportedTest.getImageHeight() - 1,
            this.sepiaImportedTest).get(2).intValue());
    assertEquals(61,
        sepia.newColorValsAt(this.sepiaImportedTest.getImageWidth() - 1, 20,
            this.sepiaImportedTest).get(0).intValue());
  }

  // Tests that when sepia is called on the middle pixel it works as expected
  @Test
  public void sepiaMiddlePix() {
    assertEquals(128,
        sepia.newColorValsAt(1, 1, this.sepiaTestImage).get(1).intValue());
  }

  // Tests that when sepia is called on the middle pixel it works as expected
  @Test
  public void sepiaMiddlePixTestImage() {
    assertEquals(79,
        sepia.newColorValsAt(20, 20, this.sepiaImportedTest).get(2).intValue());
  }
}