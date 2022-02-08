import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.files.PPM;
import model.filters.Blur;
import model.filters.IFilter;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Blur. This class tests that when images are filtered using the Blur function
 * object, they are properly blurred. Each new pixel in the image should have a new color value
 * determined by the transpose matrix and the math done in the Blur class.
 */
public class BlurTest {

  private IFilter blur;
  private ImageInterface blurTestImage;
  private ImageInterface blurImportedTest;

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
    this.blurImportedTest = new PPM().importFile("res/TestingImage.ppm");
    this.blurTestImage = new Image(red, green, blue);
    this.blur = new Blur();
  }

  /*
  Tests for newColorValsAt()
   */

  // Tests that an exception is thrown when a negative x is given
  @Test(expected = IllegalArgumentException.class)
  public void blurNegativeX() {
    blur.newColorValsAt(-1, 0,  this.blurTestImage);
  }

  // Tests that an exception is thrown when a negative y is given
  @Test(expected = IllegalArgumentException.class)
  public void blurNegativeY() {
    blur.newColorValsAt(1, -1,  this.blurTestImage);
  }

  // Tests that an exception is thrown when a negative x and y are given
  @Test(expected = IllegalArgumentException.class)
  public void blurNegativeXAndY() {
    blur.newColorValsAt(-3, -9,  this.blurTestImage);
  }

  // Tests that an exception is thrown when a x out of of the width is given
  @Test(expected = IllegalArgumentException.class)
  public void blurTooBigY() {
    blur.newColorValsAt(0, 3,  this.blurTestImage);
  }

  // Tests that an exception is thrown when a x out of of the height is given
  @Test(expected = IllegalArgumentException.class)
  public void blurTooBigX() {
    blur.newColorValsAt(3, 0,  this.blurTestImage);
  }

  // Tests that an exception is thrown when passed a null image
  @Test(expected = IllegalArgumentException.class)
  public void blurNullImageWasPassed() {
    blur.newColorValsAt(0, 0,  null);
  }

  // Tests that when blur is called on the top left pixel it works as expected
  @Test
  public void blurCornerPixels() {
    assertEquals(23,
        blur.newColorValsAt(0, 2, this.blurTestImage).get(0).intValue());
    assertEquals(14,
        blur.newColorValsAt(2, 2, this.blurTestImage).get(1).intValue());
    assertEquals(61,
        blur.newColorValsAt(0, 0, this.blurTestImage).get(2).intValue());
    assertEquals(98,
        blur.newColorValsAt(2, 0, this.blurTestImage).get(0).intValue());
  }

  // Tests that when blur is called on the top left pixel it works as expected with a ppm
  @Test
  public void blurCornerPixelsTestImage() {
    assertEquals(135,
        blur.newColorValsAt(0, this.blurImportedTest.getImageHeight() - 1,
            this.blurImportedTest).get(0).intValue());
    assertEquals(32,
        blur.newColorValsAt(this.blurImportedTest.getImageWidth() - 1,
            this.blurImportedTest.getImageHeight() - 1,
            this.blurImportedTest).get(1).intValue());
    assertEquals(4,
        blur.newColorValsAt(0, 0, this.blurImportedTest).get(2).intValue());
    assertEquals(4,
        blur.newColorValsAt(this.blurImportedTest.getImageWidth() - 1, 0,
            this.blurImportedTest).get(0).intValue());
  }

  // Tests that when blur is called on the top middle pixel it works as expected
  @Test
  public void blurPixelInMiddleOfSide() {
    assertEquals(90,
        blur.newColorValsAt(1, 0, this.blurTestImage).get(0).intValue());
    assertEquals(17,
        blur.newColorValsAt(1, 2, this.blurTestImage).get(1).intValue());
    assertEquals(61,
        blur.newColorValsAt(0, 0, this.blurTestImage).get(2).intValue());
    assertEquals(98,
        blur.newColorValsAt(2, 0, this.blurTestImage).get(0).intValue());
  }

  // Tests that when blur is called on the top left pixel it works as expected with a ppm
  @Test
  public void blurPixelsInMiddleOfSideTestImage() {
    assertEquals(5,
        blur.newColorValsAt(13,0,
            this.blurImportedTest).get(0).intValue());
    assertEquals(116,
        blur.newColorValsAt(0,30,
            this.blurImportedTest).get(1).intValue());
    assertEquals(7,
        blur.newColorValsAt(20, this.blurImportedTest.getImageHeight() - 1,
            this.blurImportedTest).get(2).intValue());
    assertEquals(77,
        blur.newColorValsAt(this.blurImportedTest.getImageWidth() - 1, 20,
            this.blurImportedTest).get(0).intValue());
  }

  // Tests that when blur is called on the middle pixel it works as expected
  @Test
  public void blurMiddlePix() {
    assertEquals(68,
        blur.newColorValsAt(1, 1, this.blurTestImage).get(1).intValue());
  }

  // Tests that when blur is called on the middle pixel it works as expected
  @Test
  public void blurMiddlePixTestImage() {
    assertEquals(18,
        blur.newColorValsAt(20, 20, this.blurImportedTest).get(2).intValue());
  }
}