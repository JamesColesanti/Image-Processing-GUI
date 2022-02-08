package model.filters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the sharpen filter. The sharpen filter accentuates edges giving the image a "sharper"
 * look. The class contains an apply method which is a design choice such that adding new filters
 * means creating a new class which implements IFilter instead of crowding the model class.
 */
public class Sharpen extends AbstractSquareMatrixMath {

  /**
   * Constructor for Sharpen, sets the dimension field to 5 since the operating matrix is a 5x5
   * and the shifter is 2 since the closest edge is 2 away from the center of the matrix.
   */
  public Sharpen() {
    this.dimension = 5;
    this.shifter = 2;
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
    this.transposeMatrix = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, .25, .25, .25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, .25, 1.0, .25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, .25, .25, .25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125))));
    if (x == 1 || x == 0) {
      this.trimSideLeftRight(0);
    }
    // then we can guarantee that second to top most row of the transpose will not be used
    if (x == 0) {
      this.trimSideLeftRight(1);
    }
    // then we can guarantee that the second to left most column of the transpose will not be used
    if (x == (width - 2) || x == (width - 1)) {
      this.trimSideLeftRight(4);
    }
    // then we can guarantee that the second bottom most row of the transpose will not be used
    if (x == (width - 1)) {
      this.trimSideLeftRight(3);
    }
    // then we can guarantee that second to top most row of the transpose will not be used
    if (y == 1 || y == 0) {
      this.trimTopBottomList(0);
    }
    // then we can guarantee that the second to left most column of the transpose will not be used
    if (y == 0) {
      this.trimTopBottomList(1);
    }
    // then we can guarantee that the second bottom most row of the transpose will not be used
    if (y == (height - 2) || y == (height - 1)) {
      this.trimTopBottomList(4);
    }
    // then we can guarantee that the second to right most column of the transpose will not be used
    if (y == (height - 1)) {
      this.trimTopBottomList(3);
    }
  }
}
