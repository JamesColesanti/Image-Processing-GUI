import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import model.programmaticimages.ColorEnum;
import model.image.ImageInterface;
import model.programmaticimages.CheckerBoard;
import model.programmaticimages.ImageCreatorInterface;
import org.junit.Test;

/**
 * Test class for CheckerBoardCreator. This class tests that checkerboards are properly created
 * when the create method is called.
 */
public class CheckerBoardCreatorTest {

  ImageCreatorInterface checker = new CheckerBoard();

  /*
  Tests for createImageRepresentation
   */

  // Tests that an exception is thrown if the color list is null
  @Test(expected = IllegalArgumentException.class)
  public void colorListIsNull() {
    this.checker.createImageRepresentation(20, 20, 2, null);
  }

  // Tests that an exception is thrown if the color list is too small
  @Test(expected = IllegalArgumentException.class)
  public void colorListIsTooSmall() {
    this.checker.createImageRepresentation(20, 20, 2,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE)));
  }

  // Tests that an exception is thrown if the color list is too large
  @Test(expected = IllegalArgumentException.class)
  public void colorListIsTooBig() {
    this.checker.createImageRepresentation(20, 20, 2,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK, ColorEnum.GREEN)));
  }

  // Tests that an exception is thrown if the width is negative
  @Test(expected = IllegalArgumentException.class)
  public void colorNegativeWidth() {
    this.checker.createImageRepresentation(-20, 20, 2,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
  }

  // Tests that an exception is thrown if the height is negative
  @Test(expected = IllegalArgumentException.class)
  public void colorNegativeHeight() {
    this.checker.createImageRepresentation(20, -20, 2,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
  }

  // Tests that an exception is thrown if the tile size is negative
  @Test(expected = IllegalArgumentException.class)
  public void colorNegativeTileSize() {
    this.checker.createImageRepresentation(20, 20, -2,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
  }

  // Tests that the top right corner is of the correct color
  @Test
  public void colorTopLeft() {
    ImageInterface checkerBoard = this.checker.createImageRepresentation(1000, 1000, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
    assertEquals(0, checkerBoard.getRedAt(0, 0));
    assertEquals(0, checkerBoard.getGreenAt(0, 0));
    assertEquals(0, checkerBoard.getBlueAt(0, 0));
  }

  // Tests that at the vertical tile change the color is correctly changed
  @Test
  public void colorAtChangeHorizontal() {
    ImageInterface checkerBoard = this.checker.createImageRepresentation(1000, 1000, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
    assertEquals(255, checkerBoard.getRedAt(10, 0));
    assertEquals(255, checkerBoard.getGreenAt(10, 0));
    assertEquals(255, checkerBoard.getBlueAt(10, 0));
  }

  // Tests that at the horizontal tile change the color is correctly changed
  @Test
  public void colorAtChangeVertical() {
    ImageInterface checkerBoard = this.checker.createImageRepresentation(1000, 1000, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
    assertEquals(255, checkerBoard.getRedAt(0, 10));
    assertEquals(255, checkerBoard.getGreenAt(0, 10));
    assertEquals(255, checkerBoard.getBlueAt(0, 10));
  }

  // Tests that the image is of the correct width
  @Test
  public void checkerBoardImageWidth() {
    ImageInterface checkerBoard = this.checker.createImageRepresentation(1000, 1000, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
    assertEquals(255, checkerBoard.getRedAt(10,0));
    assertEquals(255, checkerBoard.getGreenAt(10,0));
    assertEquals(255, checkerBoard.getBlueAt(10,0));
  }

  // Tests that the image is of the correct height
  @Test
  public void checkerBoardImageHeight() {
    ImageInterface checkerBoard = this.checker.createImageRepresentation(500, 1000, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.WHITE, ColorEnum.BLACK)));
    assertEquals(500, checkerBoard.getImageWidth());
    assertEquals(1000, checkerBoard.getImageHeight());
  }

}