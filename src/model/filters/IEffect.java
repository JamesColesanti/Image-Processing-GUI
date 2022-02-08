package model.filters;

import model.image.ImageInterface;

/**
 * Interface for effects that are not necessarily filters but still features that can be used on
 * images. These effects can be applied to an individual layer or multiple layers.
 */
public interface IEffect {

  /**
   * Determines if the effect can be applied to multiple layers or just one layer.
   * @return true if the effect can be applied to multiple layers, false otherwise
   */
  boolean determineApplyToAllStatus();

  /**
   * Returns a new image with the effect applied.
   * @param originalImage the original image the effect is to be applied to
   * @return the new image with the effect applied
   * @throws IllegalArgumentException if the given originalImage is null
   */
  ImageInterface applyEffect(ImageInterface originalImage) throws IllegalArgumentException;
}
