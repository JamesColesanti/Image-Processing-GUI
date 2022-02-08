package model.filters;

import java.util.Random;
import model.image.ImageInterface;

/**
 * The IMosaic interface is used to add extra functionality to the IFilter interface while
 * maintaining all of the old functionality in IFilter.
 */
public interface IMosaic extends IFilter {

  /**
   * Initializes the seeds in the mosaic so that we can "pre-load" the image in the mosaic class
   * so that when we then call the newColorValsAt we are simply sourcing from our mosaic.
   *
   * @param seeds the number of seeds in the mosaic image
   * @param img the image we are mosaic
   * @throws IllegalArgumentException if the img param is null
   */
  void initializeSeeds(int seeds, ImageInterface img, Random randX, Random randY);
}
