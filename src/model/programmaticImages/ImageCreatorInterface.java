package model.programmaticimages;

import java.util.ArrayList;
import model.image.ImageInterface;

/**
 * Interface is used to build images programmatically, where each implement class is a type of
 * programmatic image.
 */
public interface ImageCreatorInterface {

  /**
   * Creates an image programmatically based off of the parameters passed.
   *
   * @param width     width of the image
   * @param height    height of the image
   * @param tileSize  size of each tile in the image
   * @param colorList list of colors that are in the image
   * @return an Image that contains all of the correct rgb values in their respective 2D arrays
   * @throws IllegalArgumentException if the color list is null or not of the correct size ofr
   *     the image, or if any of the int params are negative
   */
  ImageInterface createImageRepresentation(int width, int height, int tileSize,
      ArrayList<ColorEnum> colorList) throws IllegalArgumentException;
}
