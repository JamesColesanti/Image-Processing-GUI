package model;

import model.image.ImageInterface;

/**
 * This interface represents different operations that a filter model must support to return
 * various aspects of its state. This interface does not provide any operations to mutate the state
 * of a filter model.
 *
 * @param <K> the Image type
 */
public interface IFilterModelState<K> {

  /**
   * Get the image at the given index in the model's image history.
   *
   * @param index the index at which the desired image is
   * @return the image at the given index
   */
  K getImageAt(int index) throws IllegalArgumentException;

  /**
   * Returns a copy of the layer that is top most and visible.
   *
   * @return the layer that is top most and visible
   * @throws IllegalStateException if there is no top most visible layer
   */
  ILayer getCurrentTopmostVisible() throws IllegalStateException;
}
