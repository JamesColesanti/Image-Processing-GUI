package model;

import model.image.ImageInterface;

/**
 * This class represents a layer in a multi layer image or a single layer in a single layer image
 * . The class has a name which is the most recent name of the image, so the name of the image at
 * the time of import or save. We decided to make a layer class rather than just add the name and
 * visible field to our image class since we wanted to remove clutter from our image class and
 * make a layer (in general terms not this class instance) be dependent on a particular image
 * class.
 */
public class Layer implements ILayer {
  private String fileName;
  private ImageInterface img;
  private boolean visible;

  /**
   * Constructor that sets the visibility to true and makes a new layer.
   */
  public Layer() {
    this.visible = true;
  }

  /**
   * Constructor that takes in an image and file name and sets the visibility to true.
   *
   * @param img the image in this layer
   * @param fileName the name of the image in this layer
   */
  public Layer(ImageInterface img, String fileName) {
    if (img == null || fileName == null) {
      throw new IllegalArgumentException("The parameters passed can not be null in the "
          + "constructor.");
    }
    this.fileName = fileName;
    this.img = img;
    this.visible = true;
  }

  /**
   * Convenience constructor.
   *
   * @param img the image in this layer
   * @param fileName the name of the image in this layer
   * @param visible the visibility of this layer
   */
  public Layer(ImageInterface img, String fileName, boolean visible) {
    if (img == null || fileName == null) {
      throw new IllegalArgumentException("The parameters passed can not be null in the "
          + "constructor.");
    }
    this.fileName = fileName;
    this.img = img;
    this.visible = visible;
  }

  @Override
  public ImageInterface getImage() {
    return this.img;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  @Override
  public void toggleVisibility() {
    this.visible = !this.visible;
  }

  @Override
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("The parameters passed can not be null in the "
          + "constructor.");
    }
    this.fileName = name;
  }

  @Override
  public void setImage(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("The parameters passed can not be null in the "
          + "constructor.");
    }
    this.img = img;
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
