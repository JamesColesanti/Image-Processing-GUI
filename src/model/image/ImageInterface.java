package model.image;

import java.util.ArrayList;
import java.util.List;
import model.filters.IFilter;

/**
 * The interface represents an Image storing implementation, if we chose later to create a new
 * way to store image then we can create a new class and implement the ImageInterface.
 */
public interface ImageInterface {

  /**
   * Applies the filter to this image and returns the image while also adding the new image to the
   * stack of versions.
   *
   * @param filter the instance of IFilter which indicates which filter we want to apply to this img
   * @return this img after being filtered by the filter indicated by the filter parameter
   * @throws IllegalArgumentException if the filter passed is null
   */
  ImageInterface filter(IFilter filter) throws IllegalArgumentException;

  /**
   * Returns a deep copy of this image, making sure that all the fields are true copies and not
   * compiled of references.
   *
   * @return a copy of this image
   */
  ImageInterface copy();

  /**
   * Gets the value of the red pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the red pixel we want to get the value of
   * @param y the y coordinate of the red pixel we want to get the value of
   * @return the value of the red pixel at (x,y)
   */
  int getRedAt(int x, int y);

  /**
   * Gets the value of the green pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the green pixel we want to get the value of
   * @param y the y coordinate of the green pixel we want to get the value of
   * @return the value of the green pixel at (x,y)
   */
  int getGreenAt(int x, int y);

  /**
   * Gets the value of the blue pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the blue pixel we want to get the value of
   * @param y the y coordinate of the blue pixel we want to get the value of
   * @return the value of the blue pixel at (x,y)
   */
  int getBlueAt(int x, int y);

  /**
   * Creates a String that represents the contents of this image in PPM format.
   *
   * @return a string that represents the contents of this image in PPM format
   */
  String createPPMString();

  /**
   * Gets the width of the image.
   *
   * @return the width of the image in pixels
   */
  int getImageWidth();

  /**
   * Gets the height of the image.
   *
   * @return the height of the image in pixels
   */
  int getImageHeight();
}
