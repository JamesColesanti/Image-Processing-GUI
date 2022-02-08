import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.filters.IMosaic;
import model.filters.Mosaic;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

public class MosaicTest {

  private IMosaic mosaic;
  private ImageInterface mosaicTestImage;

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
    this.mosaicTestImage = new Image(red, green, blue);
    this.mosaic = new Mosaic();
    this.mosaic.initializeSeeds(3, this.mosaicTestImage, new Random(10), new Random(10));
  }

  // Tests that when mosaic is called on the top left pixel it works as expected
  @Test
  public void mosaicCornerPixels() {
    assertEquals(0,
        this.mosaic.newColorValsAt(0, 2, this.mosaicTestImage).get(0).intValue());
    assertEquals(0,
        this.mosaic.newColorValsAt(2, 2, this.mosaicTestImage).get(1).intValue());
    assertEquals(113,
        this.mosaic.newColorValsAt(0, 0, this.mosaicTestImage).get(2).intValue());
    assertEquals(129,
        this.mosaic.newColorValsAt(2, 0, this.mosaicTestImage).get(0).intValue());
  }

  // Tests that when mosaic is called on the top middle pixel it works as expected
  @Test
  public void mosaicPixelInMiddleOfSide() {
    assertEquals(129,
        this.mosaic.newColorValsAt(1, 0, this.mosaicTestImage).get(0).intValue());
    assertEquals(0,
        this.mosaic.newColorValsAt(1, 2, this.mosaicTestImage).get(1).intValue());
    assertEquals(113,
        this.mosaic.newColorValsAt(0, 0, this.mosaicTestImage).get(2).intValue());
    assertEquals(129,
        this.mosaic.newColorValsAt(2, 0, this.mosaicTestImage).get(0).intValue());
  }

  // Tests that when mosaic is called on the middle pixel it works as expected
  @Test
  public void mosaicMiddlePix() {
    assertEquals(80,
        this.mosaic.newColorValsAt(1, 1, this.mosaicTestImage).get(1).intValue());
  }

  // Tests that an exception is thrown when a negative x is given
  @Test(expected = IllegalArgumentException.class)
  public void mosaicNegativeX() {
    this.mosaic.newColorValsAt(-1, 0,  this.mosaicTestImage);
  }

  // Tests that an exception is thrown when a negative y is given
  @Test(expected = IllegalArgumentException.class)
  public void mosaicNegativeY() {
    this.mosaic.newColorValsAt(1, -1,  this.mosaicTestImage);
  }

  // Tests that an exception is thrown when a negative x and y are given
  @Test(expected = IllegalArgumentException.class)
  public void mosaicNegativeXAndY() {
    this.mosaic.newColorValsAt(-3, -9,  this.mosaicTestImage);
  }

  // Tests that an exception is thrown when a x out of of the width is given
  @Test(expected = IllegalArgumentException.class)
  public void mosaicTooBigY() {
    this.mosaic.newColorValsAt(0, 3,  this.mosaicTestImage);
  }

  // Tests that an exception is thrown when a x out of of the height is given
  @Test(expected = IllegalArgumentException.class)
  public void mosaicTooBigX() {
    this.mosaic.newColorValsAt(3, 0,  this.mosaicTestImage);
  }

  // Tests that an exception is thrown when passed a null image
  @Test(expected = IllegalArgumentException.class)
  public void mosaicNullImageWasPassed() {
    this.mosaic.newColorValsAt(0, 0,  null);
  }
}
