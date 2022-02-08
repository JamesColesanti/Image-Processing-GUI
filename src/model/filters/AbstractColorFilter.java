package model.filters;

import static model.image.Image.clamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.image.ImageInterface;

/**
 * Abstract class for filters involving changes in color. Since the action of applying a color
 * change to an image only changes in terms of the transpose matrix used, each color filter can use
 * the same code. Implementing classes will simply set the transpose matrix to what it should be to
 * apply that specific filter. Right now the only known color filters are monochrome and sepia, but
 * as we learn about new ones new classes can extend this class to accommodate them.
 */
public abstract class AbstractColorFilter implements IFilter {

  protected ArrayList<ArrayList<Double>> transpose;

  /**
   * Sets the transpose field to the appropriate transpose matrix to apply a certain filter.
   * Extending classes must implement this method to return this matrix.
   *
   * @return a transpose matrix used to apply a color filter
   */
  protected abstract ArrayList<ArrayList<Double>> setTranspose();

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
    int newValRed = this.getNewValue(0, x, y, img);
    int newValGreen = this.getNewValue(1, x, y, img);
    int newValBlue = this.getNewValue(2, x, y, img);

    return new ArrayList<Integer>(Arrays.asList(newValRed, newValGreen, newValBlue));
  }

  /**
   * Gets a new color value after applying the transpose matrix. This is used to computer the new
   * red, green, or blue value depending on the given row in the transpose matrix.
   *
   * @param transposeRow the row in the transpose matrix being used (0 for finding new red value,
   *     1 for green, and 2 for blue)
   * @param x the column in the channel for the pixel
   * @param y the row in the channel for the pixel
   * @param img the image the filter is being applied to
   * @return the new pixel value at (x,y) in img
   */
  private int getNewValue(int transposeRow, int x, int y, ImageInterface img) {
    return clamp(this.getTransposeValAt(0, transposeRow) * img.getRedAt(x, y)
        + this.getTransposeValAt(1, transposeRow) * img.getGreenAt(x, y)
        + this.getTransposeValAt(2, transposeRow) * img.getBlueAt(x, y));
  }

  /**
   * Gets the value in the transpose matrix at the given row and column.
   * @param x the column for the value
   * @param y the row for the value
   * @return the value in the transpose matrix
   */
  private double getTransposeValAt(int x, int y) {
    return this.transpose.get(x).get(y);
  }
}
