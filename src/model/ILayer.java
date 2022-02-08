package model;

import model.image.ImageInterface;

/**
 * The layer interface represents a general layer of a potentially multi-layered image,
 * the layer design versus just adding fields to the image class allows for more adaptable
 * implementation of the layers concept.
 */
public interface ILayer {

  /**
   * Returns the image field. While we know this is not ideal practice this is one of
   * the design trad offs that we made in order to generalize our representation of a layer
   * rather then crowd the image class with fields and methods that could alter in future
   * versions of the program, making us ill-equipped for new and unexpected features.
   *
   * @return a this image
   */
  ImageInterface getImage();

  /**
   * The name of the image in this layer.
   *
   * @return the name of the image in this layer
   */
  String getFileName();

  /**
   * Switches the current visibility of the layer.
   */
  void toggleVisibility();

  /**
   * Sets the name of the image in this layer to the string passed.
   */
  void setName(String name);

  /**
   * Sets the image in this layer to the image passed.
   * @param img the image to be set to this layer
   */
  void setImage(ImageInterface img);

  /**
   * Returns the boolean value of the visiblity of this layer.
   */
  boolean getVisibility();

  /**
   * Sets the visibility of this layer to that of the boolean passed.
   *
   * @param visible the new visibility of this layer
   */
  void setVisible(boolean visible);

}
