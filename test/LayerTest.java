import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.ILayer;
import model.Layer;
import model.image.Image;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the layer class.
 */
public class LayerTest {
  ILayer testLayer;

  @Before
  public void initData() {
    testLayer = new Layer();
  }

  /*
  Constructor tests
   */

  // tests that when a null img is passed an IllegalArgumentException is thrown
  @Test(expected = IllegalArgumentException.class)
  public void nullImageForLayer() {
    this.testLayer = new Layer(null, "sky");
  }

  // tests that when a null fileName is passed an IllegalArgumentException is thrown
  @Test(expected = IllegalArgumentException.class)
  public void nullFileNameForLayer() {
    this.testLayer = new Layer(new Image(
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()), null);

  }

  // tests that an empty layer constructor makes a null fileName, img, and true visibility
  @Test
  public void emptyConstructorTest() {
    assertEquals(null, testLayer.getFileName());
    assertTrue(testLayer.getVisibility());
  }

  /*
  Test for getImage
   */

  // tests the getImage returns the image
  @Test
  public void getImageValid() {
    this.testLayer = new Layer(new Image(
        new ArrayList<>(1),
        new ArrayList<>(2),
        new ArrayList<>(3)), "blue sky");
    assertEquals(new Image(
        new ArrayList<>(1),
        new ArrayList<>(2),
        new ArrayList<>(3)), this.testLayer.getImage());
  }

  // test that getImage returns the image with a larger image
  @Test
  public void getImageValidBigImage() {
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0, 90)),
        new ArrayList<>(Arrays.asList(13, 200, 0, 20)),
        new ArrayList<>(Arrays.asList(255, 180, 0, 3))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0, 1)),
        new ArrayList<>(Arrays.asList(128, 63, 0, 90)),
        new ArrayList<>(Arrays.asList(11, 89, 0, 1))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21, 4)),
        new ArrayList<>(Arrays.asList(120, 89, 0, 8)),
        new ArrayList<>(Arrays.asList(99, 89, 255, 9))));

    ImageInterface testImage = new Image(red, green, blue);
    ImageInterface testImageCopy = testImage.copy();
    testLayer = new Layer(testImage, "tester", false);
    assertTrue(testImageCopy.equals(testLayer.getImage()));
  }

  /*
  Test for getFileName
   */

  // tests that getFileName returns the file name
  @Test
  public void getFileNameValid() {
    this.testLayer = new Layer(new Image(
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()), "blue sky");
    assertTrue(testLayer.getFileName().equals("blue sky"));
  }

  /*
  Test for toggleVisibility
   */

  // tests that toggleVisibility swaps the visibility
  @Test
  public void toggleVisbilityWorks() {
    assertTrue(this.testLayer.getVisibility());
    this.testLayer.toggleVisibility();
    assertFalse(this.testLayer.getVisibility());
    this.testLayer.toggleVisibility();
    assertTrue(this.testLayer.getVisibility());
  }

  /*
  Test for set name
   */

  // tests that an illegal argument exception is thrown when the name passed in null
  @Test(expected = IllegalArgumentException.class)
  public void nullNameSetName() {
    this.testLayer.setName(null);
  }

  // tests that the empty string is a valid name
  @Test
  public void emptyNameSetName() {
    this.testLayer.setName("");
    assertTrue(this.testLayer.getFileName().equals(""));
  }

  // tests that setName works with an ordinary string
  @Test
  public void normalNameSetName() {
    this.testLayer.setName("Biggest");
    assertTrue(this.testLayer.getFileName().equals("Biggest"));
  }

  /*
  Test for setImage
   */

  // tests that if the image interface passed is null that we throw an IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void nullImageSetImage() {
    this.testLayer.setImage(null);
  }

  // test that this img is set to the image passed
  @Test
  public void validSetImage() {
    this.testLayer.setImage(new Image(
        new ArrayList<>(1),
        new ArrayList<>(2),
        new ArrayList<>(3)));
    assertTrue(this.testLayer.getImage().equals(new Image(
        new ArrayList<>(1),
        new ArrayList<>(2),
        new ArrayList<>(3))));
  }

  /*
  Tests for getVisibility
   */

  // tests that getVisibility returns true for a new Layer()
  @Test
  public void getVisibilityTrueVis() {
    assertTrue(this.testLayer.getVisibility());
  }

  // test that getVisibility works for a Layer with false visibility
  @Test
  public void getVisibilityFalseVis() {
    this.testLayer.setVisible(false);
    assertFalse(this.testLayer.getVisibility());
  }

  /*
  Tests for setVisible
   */

  // tests that setVisible sets a false to true
  @Test
  public void setVisibleTrueVis() {
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89)),
        new ArrayList<>(Arrays.asList(13, 200)),
        new ArrayList<>(Arrays.asList(255, 180))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89)),
        new ArrayList<>(Arrays.asList(128, 63)),
        new ArrayList<>(Arrays.asList(11, 89))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89)),
        new ArrayList<>(Arrays.asList(120, 89)),
        new ArrayList<>(Arrays.asList(99, 89))));

    ImageInterface testImage = new Image(red, green, blue);

    testLayer = new Layer(testImage, "testerImage", false);

    assertFalse(this.testLayer.getVisibility());
    this.testLayer.setVisible(true);
    assertTrue(this.testLayer.getVisibility());
  }

  // test that setVisible sets a true to false
  @Test
  public void setVisibleFalseVis() {
    assertTrue(this.testLayer.getVisibility());
    this.testLayer.setVisible(false);
    assertFalse(this.testLayer.getVisibility());
  }

  // test that setVisible does effectively nothing if this visibility is
  // the same as the boolean passed
  @Test
  public void setVisibleSameBool() {
    assertTrue(this.testLayer.getVisibility());
    this.testLayer.setVisible(true);
    assertTrue(this.testLayer.getVisibility());

    this.testLayer.setVisible(false);

    assertFalse(this.testLayer.getVisibility());
    this.testLayer.setVisible(false);
    assertFalse(this.testLayer.getVisibility());
  }


}