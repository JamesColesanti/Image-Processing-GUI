import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.filters.Downscale;
import model.filters.IEffect;
import model.filters.IMosaic;
import model.filters.Mosaic;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

public class DownscaleTest {

  private IEffect downscale;
  private ImageInterface downscaleTestImage1;
  private ImageInterface downscaleTestImage2;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0, 198, 109)),
        new ArrayList<>(Arrays.asList(13, 200, 0, 1, 2)),
        new ArrayList<>(Arrays.asList(255, 180, 0, 187, 162))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0, 3, 4)),
        new ArrayList<>(Arrays.asList(128, 63, 0, 6, 21)),
        new ArrayList<>(Arrays.asList(11, 89, 0, 182, 163))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21, 12, 253)),
        new ArrayList<>(Arrays.asList(120, 89, 0, 98, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255, 81, 74))));
    this.downscale = new Downscale(3, 3);
    this.downscaleTestImage1 = this.downscale.applyEffect(new Image(red, green, blue));
    this.downscale = new Downscale(3, 2);
    this.downscaleTestImage2 = this.downscale.applyEffect(new Image(red, green, blue));
  }

  // Test for dimensions of downscaled image
  @Test
  public void testDownscaleDimensions() {
    assertEquals(3, this.downscaleTestImage1.getImageHeight());
    assertEquals(3, this.downscaleTestImage1.getImageWidth());

    assertEquals(2, this.downscaleTestImage2.getImageHeight());
    assertEquals(3, this.downscaleTestImage2.getImageWidth());
  }

  // Tests that downscaled top left pixel is correct color
  @Test
  public void downscaleTopLeftPixel() {
    assertEquals(120, this.downscaleTestImage1.getRedAt(0, 0));
    assertEquals(255, this.downscaleTestImage1.getGreenAt(0, 0));
    assertEquals(120, this.downscaleTestImage1.getBlueAt(0, 0));

    assertEquals(120, this.downscaleTestImage2.getRedAt(0, 0));
    assertEquals(255, this.downscaleTestImage2.getGreenAt(0, 0));
    assertEquals(120, this.downscaleTestImage2.getBlueAt(0, 0));
  }

  // Tests that downscaled top middle pixel is correct color
  @Test
  public void downscaleTopMiddlePixel() {
    assertEquals(13, this.downscaleTestImage1.getRedAt(1, 0));
    assertEquals(128, this.downscaleTestImage1.getGreenAt(1, 0));
    assertEquals(120, this.downscaleTestImage1.getBlueAt(1, 0));

    assertEquals(13, this.downscaleTestImage2.getRedAt(1, 0));
    assertEquals(128, this.downscaleTestImage2.getGreenAt(1, 0));
    assertEquals(120, this.downscaleTestImage2.getBlueAt(1, 0));
  }

  // Tests that downscaled top middle pixel is correct color
  @Test
  public void downscaleMiddlePixel() {
    assertEquals(200, this.downscaleTestImage1.getRedAt(1, 1));
    assertEquals(63, this.downscaleTestImage1.getGreenAt(1, 1));
    assertEquals(89, this.downscaleTestImage1.getBlueAt(1, 1));
  }

  // Tests that downscaled bottom right pixel is correct color
  @Test
  public void downscaleBottomRightPixel() {
    assertEquals(187, this.downscaleTestImage1.getRedAt(2, 2));
    assertEquals(182, this.downscaleTestImage1.getGreenAt(2, 2));
    assertEquals(81, this.downscaleTestImage1.getBlueAt(2, 2));

    assertEquals(0, this.downscaleTestImage2.getRedAt(2, 1));
    assertEquals(0, this.downscaleTestImage2.getGreenAt(2, 1));
    assertEquals(255, this.downscaleTestImage2.getBlueAt(2, 1));
  }

  // Tests that an exception is thrown when a negative width is given
  @Test(expected = IllegalArgumentException.class)
  public void downscaleNegativeWidth() {
    new Downscale(-1, 10);
  }

  // Tests that an exception is thrown when a negative height is given
  @Test(expected = IllegalArgumentException.class)
  public void downscaleNegativeHeight() {
    new Downscale(10, -1);
  }

  // Tests that an exception is thrown when a null image is given
  @Test(expected = IllegalArgumentException.class)
  public void downscaleNullImage() {
    new Downscale(10, 10).applyEffect(null);
  }

  // Tests that an exception is thrown when an attempt is made to increase an image's size
  @Test(expected = IllegalArgumentException.class)
  public void downscaleIncreaseImageSize() {
    new Downscale(10, 10).applyEffect(this.downscaleTestImage1);
  }

}
