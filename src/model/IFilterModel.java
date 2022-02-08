package model;

import java.util.ArrayList;
import java.util.List;
import model.filters.IFilter;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;

/**
 * This is the interface of the FilterModel. It will contain all of the signatures for our
 * implementations. The model will handle all of the computations blind to when and why it is being
 * called. The interface here allows the code to be understood from a high level without having to
 * delve into complex implementations of the methods.
 */
public interface IFilterModel<K> extends IFilterModelState<K> {

  /**
   * Applies the given filter to the current image.
   *
   * @param filter the filter that we are using
   * @throws IllegalArgumentException if the given filter is null
   */
  void applyFilter(IFilter filter) throws IllegalArgumentException;

  /**
   * Sets the name to the top most visible image.
   */
  void save(String name);

  /**
   * Set the current image in the model to the given image.
   *
   * @param img the new image being set in the model
   * @throws IllegalArgumentException if the given image is null
   */
  void setImage(K img) throws IllegalArgumentException;

  /**
   * Creates the image programmatically based off of the instance of the enumeration which is
   * passed.
   *
   * @param imageTypeEnum enum that indicates which image to make programmatically
   * @param width the width of the image we will create
   * @param height the height of the image we will create
   * @param tileSize the tile size of the image we will create
   * @param colorSettings a list of the colors we will use in the image
   * @throws IllegalArgumentException if the given image type enum or list of color enums is null,
   *                                  or if the given width, height, or tileSize are negative
   */
  void createImage(ProgrammaticImageType imageTypeEnum, int width, int height, int tileSize,
      ArrayList<ColorEnum> colorSettings) throws IllegalArgumentException;

  /**
   * Get the current image in the model. The current image is the image that the user is currently
   * looking at. It has not been saved yet.
   * @return the current image
   * @throws IllegalStateException if no images have been added to the model yet
   */
  ImageInterface getCurrentImage() throws IllegalStateException;

  /**
   * Adds a new null layer to the multilayer image we are working with.
   */
  void addLayer();

  /**
   * Adds a new null layer to the multilayer image we are working with.
   */
  void removeLayer();

  /**
   * Updates the this img field to the correct layer.
   *
   * @param current the index of the layer that this img should be of
   * @throws IllegalArgumentException if current is an invalid layer index
   */
  void updateImgToCurrentLayer(int current) throws IllegalArgumentException;

  /**
   * Returns a copy of the list of layers.
   *
   * @return a copy of the list of layers
   */
  List<ILayer> getLayers();

  void setAllLayers(List<ILayer> layerList);

  /**
   * Adjusts the visibility of the layer indicated by the index passed and sets the
   * visibility to that of the boolean passed.
   *
   * @param layerInd the index of the layer that we are going to adjust the visibility of
   * @param visibility the new visibility of the layer indicated
   * @throws IllegalArgumentException if the layer index is invalid
   */
  void adjustVisibility(int layerInd, boolean visibility) throws IllegalArgumentException;
}
