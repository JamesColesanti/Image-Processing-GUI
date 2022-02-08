import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.filters.Blur;
import model.filters.Monochrome;
import model.filters.Sepia;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for image. This class tests that images can be created and filtered properly.
 */
public class ImageTest {

  List<List<Integer>> red;
  List<List<Integer>> green;
  List<List<Integer>> blue;

  ImageInterface blurTestImage;
  ImageInterface blurTestImageFiltered;

  ImageInterface monoTestImage;
  ImageInterface monoTestImageFiltered;

  ImageInterface sepiaTestImage;
  ImageInterface sepiaTestImageFiltered;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    this.red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(13, 200, 0)),
        new ArrayList<>(Arrays.asList(255, 180, 0))));
    this.green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0)),
        new ArrayList<>(Arrays.asList(128, 63, 0)),
        new ArrayList<>(Arrays.asList(11, 89, 0))));
    this.blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21)),
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255))));

    this.blurTestImage = new Image(red, green, blue);
    this.monoTestImage = new Image(red, green, blue);
    this.sepiaTestImage = new Image(red, green, blue);
  }

  /*
  Tests for filter()
   */

  // Testing that blur blurs an image
  @Test
  public void blurCorrectlyBlurs3x3() {
    this.blurTestImageFiltered = this.blurTestImage.filter(new Blur());
    // top left case
    assertEquals(54, this.blurTestImageFiltered.getRedAt(0,0));
    // middle case
    assertEquals(68, this.blurTestImageFiltered.getGreenAt(1,1));
    // bottom right edge case
    assertEquals(79, this.blurTestImageFiltered.getBlueAt(2, 2));
    // middle left
    assertEquals(90, this.blurTestImageFiltered.getRedAt(1,0));
    // middle right
    assertEquals(17, this.blurTestImageFiltered.getGreenAt(1,2));
    // middle top
    assertEquals(57, this.blurTestImageFiltered.getBlueAt(0,1));
    // middle bottom
    assertEquals(101, this.blurTestImageFiltered.getRedAt(2, 1));

    // showing that ex1Image is not changed by the filter method
    // top left case
    assertEquals(120, this.blurTestImage.getRedAt(0,0));
    // middle case
    assertEquals(63, this.blurTestImage.getGreenAt(1,1));
    // bottom right edge case
    assertEquals(255, this.blurTestImage.getBlueAt(2, 2));
    // middle left
    assertEquals(13, this.blurTestImage.getRedAt(1,0));
    // middle right
    assertEquals(0, this.blurTestImage.getGreenAt(1,2));
    // middle top
    assertEquals(89, this.blurTestImage.getBlueAt(0,1));
    // middle bottom
    assertEquals(180, this.blurTestImage.getRedAt(2, 1));
  }

  // test for creating a monochrome image
  @Test
  public void monochromeTest() {
    this.monoTestImageFiltered = this.monoTestImage.filter(new Monochrome());
    // top left case
    assertEquals(217, this.monoTestImageFiltered.getRedAt(0,0));
    // middle case
    assertEquals(94, this.monoTestImageFiltered.getGreenAt(1,1));
    // bottom right edge case
    assertEquals(18, this.monoTestImageFiltered.getBlueAt(2, 2));
    // middle left
    assertEquals(103, this.monoTestImageFiltered.getRedAt(1,0));
    // middle right
    assertEquals(0, this.monoTestImageFiltered.getGreenAt(1,2));
    // middle top
    assertEquals(89, monoTestImageFiltered.getBlueAt(0,1));
    // middle bottom
    assertEquals(108, monoTestImageFiltered.getRedAt(2, 1));
  }

  // test for creating a sepia image
  @Test
  public void sepiaTest() {
    this.sepiaTestImageFiltered = this.sepiaTestImage.filter(new Sepia());
    // top left case
    assertEquals(255, this.sepiaTestImageFiltered.getRedAt(0,0));
    // middle case
    assertEquals(128, this.sepiaTestImageFiltered.getGreenAt(1,1));
    // bottom right edge case
    assertEquals(33, this.sepiaTestImageFiltered.getBlueAt(2, 2));
    // middle left
    assertEquals(126, this.sepiaTestImageFiltered.getRedAt(1,0));
    // middle right
    assertEquals(0, this.sepiaTestImageFiltered.getGreenAt(1,2));
    // middle top
    assertEquals(83, this.sepiaTestImageFiltered.getBlueAt(0,1));
    // middle bottom
    assertEquals(156, this.sepiaTestImageFiltered.getRedAt(2, 1));
  }

  // test for creating a sepia image
  @Test(expected = IllegalArgumentException.class)
  public void exceptionForFilterWithNull() {
    this.sepiaTestImageFiltered = this.sepiaTestImage.filter(null);
  }

  /*
  Test for copy()
   */

  // test for copy
  @Test
  public void testCopy() {
    ImageInterface copyImage = new Image(this.red, this.green, this.blue);

    assertEquals(copyImage, this.blurTestImage.copy());
  }

  /*
  Tests for getRedAt()
   */

  // test for a valid call to getRedAt
  @Test
  public void testGetRedAt() {
    assertEquals(120, this.blurTestImage.getRedAt(0, 0));
    assertEquals(13, this.blurTestImage.getRedAt(1, 0));
    assertEquals(255, this.blurTestImage.getRedAt(2, 0));
    assertEquals(89, this.blurTestImage.getRedAt(0, 1));
    assertEquals(200, this.blurTestImage.getRedAt(1, 1));
    assertEquals(180, this.blurTestImage.getRedAt(2, 1));
    assertEquals(0, this.blurTestImage.getRedAt(0, 2));
    assertEquals(0, this.blurTestImage.getRedAt(1, 2));
    assertEquals(0, this.blurTestImage.getRedAt(2, 2));
  }

  /*
  Tests for getGreenAt()
   */

  // test for a valid call to getGreenAt
  @Test
  public void testGetGreenAt() {
    assertEquals(255, this.blurTestImage.getGreenAt(0, 0));
    assertEquals(128, this.blurTestImage.getGreenAt(1, 0));
    assertEquals(11, this.blurTestImage.getGreenAt(2, 0));
    assertEquals(89, this.blurTestImage.getGreenAt(0, 1));
    assertEquals(63, this.blurTestImage.getGreenAt(1, 1));
    assertEquals(89, this.blurTestImage.getGreenAt(2, 1));
    assertEquals(0, this.blurTestImage.getGreenAt(0, 2));
    assertEquals(0, this.blurTestImage.getGreenAt(1, 2));
    assertEquals(0, this.blurTestImage.getGreenAt(2, 2));
  }

  /*
  Tests for getBlueAt()
   */

  // test for a valid call to getBlueAt
  @Test
  public void testGetBlueAt() {
    assertEquals(120, this.blurTestImage.getBlueAt(0, 0));
    assertEquals(120, this.blurTestImage.getBlueAt(1, 0));
    assertEquals(99, this.blurTestImage.getBlueAt(2, 0));
    assertEquals(89, this.blurTestImage.getBlueAt(0, 1));
    assertEquals(89, this.blurTestImage.getBlueAt(1, 1));
    assertEquals(89, this.blurTestImage.getBlueAt(2, 1));
    assertEquals(21, this.blurTestImage.getBlueAt(0, 2));
    assertEquals(0, this.blurTestImage.getBlueAt(1, 2));
    assertEquals(255, this.blurTestImage.getBlueAt(2, 2));
  }

  /*
  Tests for create PPM string
   */

  // test for a valid call to createFileString
  @Test
  public void testPPMFileString() {
    String blurStringOutput = "P3\n3 3\n255\n120\n255\n120\n89\n89\n89\n0\n0\n21\n13\n128\n120\n"
        + "200\n63\n89\n0\n0\n0\n255\n11\n99\n180\n89\n89\n0\n0\n255";
    assertEquals(blurStringOutput, this.blurTestImage.createPPMString());
  }

  /*
  Tests for equals overide
   */

  // test for overridden equals returning true
  @Test
  public void testEqualsTrue() {
    ImageInterface newImage = new Image(this.red, this.green, this.blue);

    assertEquals(newImage, this.blurTestImage);
  }

  // test for overridden equals returning false
  @Test
  public void testEqualsFalse() {
    ImageInterface newImage = new Image(this.red, this.green, this.red);

    assertNotEquals(newImage, this.blurTestImage);
  }

  /*
  Tests for hashCode()
   */

  // test for hashCode returning the same integer for two equal Images
  @Test
  public void testHashCodeSameImage() {
    ImageInterface newImage = new Image(this.red, this.green, this.blue);

    assertEquals(newImage.hashCode(), this.blurTestImage.hashCode());
  }

  // test for hashCode returning a different integer for two different Images
  @Test
  public void testHashCodeDifferentImage() {
    ImageInterface newImage = new Image(this.red, this.green, this.red);

    assertNotEquals(newImage.hashCode(), this.blurTestImage.hashCode());
  }

  /*
  Tests for clamp()
   */

  // tests that a negative number is clamped to 0
  @Test
  public void clampToZero() {
    assertEquals(0, Image.clamp(-10));
  }

  // test that a number over 255 is clamped to 255
  @Test
  public void clampTo255() {
    assertEquals(255, Image.clamp(298));
  }

  // tests that a number in [0,255] is unchanged
  @Test
  public void clampNoChange() {
    assertEquals(128, Image.clamp(128));
  }

  /*
  Tests for getWidth()
   */

  // tests that getWidth is right with a created image
  @Test
  public void getWidthTestSmallImage() {
    assertEquals(3, this.blurTestImage.getImageWidth());
  }

  /*
  Tests for getHeight()
   */

  // tests that getHeight is right with a created image
  @Test
  public void getHeightTestSmallImage() {
    assertEquals(3, this.blurTestImage.getImageHeight());
  }

  /*
  Tests for toString()
   */

  // tests an empty string is returned for an empty image
  @Test
  public void toStringEmptyImage() {
    ImageInterface emptyImage = new Image(new ArrayList<>(),
        new ArrayList<>(), new ArrayList<>());
    assertEquals("", emptyImage.toString());
  }

  // tests that to string works for an image
  @Test
  public void toStringValidImage() {
    assertEquals(
        "120\n255\n120\n"
            + "89\n89\n89\n"
            + "0\n0\n21\n"
            + "13\n128\n120\n"
            + "200\n63\n89\n"
            + "0\n0\n0\n"
            + "255\n11\n99\n"
            + "180\n89\n89\n"
            + "0\n0\n255",
        this.blurTestImage.toString());
  }

}
