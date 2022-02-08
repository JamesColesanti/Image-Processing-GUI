package model.filters;

import static model.image.Image.clamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.image.ImageInterface;

/**
 * The abstract class is used to abstract filters with the same founding matrix principles where
 * the operating matrix is square and the new pixel is defined as a constant multiplied by the
 * surrounding values indicated in the operating matrix.
 */
public abstract class AbstractSquareMatrixMath implements IFilter {

  protected ArrayList<ArrayList<Double>> transposeMatrix;
  protected int dimension;
  protected int shifter;

  /**
   * Sets the preconditions for blur or sharpen or any other future similar type of arithmetic
   * matrices.
   *
   * @param x the x coordinate of the pixel we are modifying
   * @param y the y coordinate of the pixel we are modifying
   * @param width the width of the image we are modifying
   * @param height the height of the image we are modifying
   */
  protected abstract void preConditions(int x, int y, int width, int height);

  /**
   * Returns a list of three integers where the first item is the new Red value, followed by the new
   * Green and Blue value at the (x,y) pixel index. The method alows for all of the filters to
   * preform their respective pixel filtering then return the same format of new Red, new Green, and
   * new Blue so we are able to delegate the individual filtering act to the class whose name sake
   * is that of its respective filter.
   *
   * @param x      the x coordinate of the pixel location we want to filter
   * @param y      the y coordinate of the pixel location we want to filter
   * @param img    the img we are filtering
   * @return an array list where the first integer is the new Red pixel value at (x,y) after filter,
   *     followed by the new Blue pixel value at (x,y), then finally the new Green pixel value at
   *     (x,y)
   */
  @Override
  public List<Integer> newColorValsAt(int x, int y, ImageInterface img)
      throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("The image can not be null.");
    }
    int width = img.getImageWidth();
    int height = img.getImageHeight();
    if (x < 0 || y < 0 || x >= width || y >= height) {
      throw new IllegalArgumentException("There is no pixel at the given x,y.");
    }
    // we call the preconditions to get the system ready to execute the method
    this.preConditions(x, y, width, height);
    // now calculate what the value at the index should be
    int newValRed = 0;
    int newValGreen = 0;
    int newValBlue = 0;
    for (int xt = 0; xt < this.dimension; xt++) {
      for (int yt = 0; yt < this.dimension; yt++) {
        double transposeValAtJI = this.transposeMatrix.get(xt).get(yt);
        // since in any null pointer case we have already set the corresponding overhang position
        // to 0.0 in the transposition matrix and if it is 0.0 we dont care anyways we crush two
        // birds with one stone
        if (transposeValAtJI != 0.0) {
          newValRed += transposeValAtJI * (double) img.getRedAt(x - shifter + xt,
              y - shifter + yt);
          newValGreen += transposeValAtJI * (double) img.getGreenAt(x - shifter + xt,
              y - shifter + yt);
          newValBlue += transposeValAtJI * (double) img.getBlueAt(x - shifter + xt,
              y - shifter + yt);
        }
      }
    }
    // we return a list of three items which are the new red, green, and blue values at the
    // indices which we passed to the method
    return new ArrayList<>(Arrays.asList(clamp(newValRed), clamp(newValGreen),
        clamp(newValBlue)));
  }

  /**
   * Goes through the set transpose and sets all the values at the x coordinate to 0.0 since there
   * is no pixel there so we set the value to 0.0 so we do not accidentally try to perform
   * arithmetic and result in a NullPointerException.
   *
   * @param x all the values in the transpose set that has the indicated x value will be set to 0.0
   */
  protected void trimTopBottomList(int x) {
    for (int i = 0; i < this.dimension; i++) {
      this.transposeMatrix.get(i).set(x, 0.0);
    }
  }

  /**
   * Goes through the set transpose and sets all the values at the y coordinate to 0.0 since there
   * is no pixel there so we set the value to 0.0 so we do not accidentally try to perform
   * arithmetic and result in a NullPointerException.
   *
   * @param y all the values in the transpose set that has the indicated y value will be set to 0.0
   */
  protected void trimSideLeftRight(int y) {
    for (int i = 0; i < this.dimension; i++) {
      this.transposeMatrix.get(y).set(i, 0.0);
    }
  }
}