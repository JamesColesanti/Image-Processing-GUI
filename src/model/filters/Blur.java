package model.filters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the blurring filter. The blur filter well, blurs the image.
 * The class contains a method called apply method which is a design choice such that adding
 * new filters means creating a new class which implements IFilter instead of crowding the model
 * class.
 */
public class Blur extends AbstractSquareMatrixMath {

  /**
   * Constructor for Blur, sets the dimension field to 3 since the operating matrix is a 3x3.
   */
  public Blur() {
    this.dimension = 3;
    this.shifter = 1;
  }

  /**
   * Sets the preconditions for blur or sharpen or any other future similar type of arithmetic
   * matrices.
   *
   * @param x the x coordinate of the pixel we are modifying
   * @param y the y coordinate of the pixel we are modifying
   * @param width the width of the image we are modifying
   * @param height the height of the image we are modifying
   */
  @Override
  protected void preConditions(int x, int y, int width, int height) {
    // we initialize every run so we can "reset" the transpose so to speak
    this.transposeMatrix = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(.0625, .125, .0625)),
        new ArrayList<>(Arrays.asList(.125, .25, .125)),
        new ArrayList<>(Arrays.asList(.0625, .125, .0625))));
    // then we can guarantee that second to top most row of the transpose will not be used
    if (x == 0) {
      this.trimSideLeftRight(0);
    }
    // then we can guarantee that the second bottom most row of the transpose will not be used
    if (x == (width - 1)) {
      this.trimSideLeftRight(2);
    }
    // then we can guarantee that the second to left most column of the transpose will not be used
    if (y == 0) {
      this.trimTopBottomList(0);
    }
    // then we can guarantee that the second to right most column of the transpose will not be used
    if (y == (height - 1)) {
      this.trimTopBottomList(2);
    }
  }

}


