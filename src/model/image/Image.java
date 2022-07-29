package model.image;

import java.util.ArrayList;
import java.util.List;
import model.filters.IFilter;

/**
 * Image class represents an image with 3 2D arrays as fields that store all the values of
 * the red, blue, and green pixel data in the 2D arrays mirroring the board itself, such that all
 * of the RGB pixel values are mapped to the pixel location on the game board. Our choice to
 * store RGB will allow us to easily adopt other file types since at the core of all images is
 * RGB pixels.
 */
public class Image implements ImageInterface {
  public List<List<Integer>> redChannel;
  private List<List<Integer>> greenChannel;
  private List<List<Integer>> blueChannel;

  /**
   * Constructor for the image class.
   *
   * @param redChannel the 2D array that represents the image but only in terms of the red pixels
   * @param greenChannel the 2D array that represents the image but only in terms of the green
   *                     pixels
   * @param blueChannel the 2D array that represents the image but only in terms of the blue pixels
   */
  public Image(List<List<Integer>> redChannel, List<List<Integer>> greenChannel,
      List<List<Integer>> blueChannel) {
    if (redChannel == null || greenChannel == null || blueChannel == null) {
      throw new IllegalArgumentException("Null parameter given.");
    }
    this.redChannel = redChannel;
    this.greenChannel = greenChannel;
    this.blueChannel = blueChannel;
  }

  /**
   * Creates a copy of this image and returns it, making sure to return a deep copy not a reference.
   *
   * @return a deep copy of this image
   */
  @Override
  public ImageInterface copy() {
    List<List<Integer>> redCopy = new ArrayList<>();
    List<List<Integer>> blueCopy = new ArrayList<>();
    List<List<Integer>> greenCopy = new ArrayList<>();
    for (int x = 0; x < this.redChannel.size(); x++) { // x values
      redCopy.add(new ArrayList<>());
      greenCopy.add(new ArrayList<>());
      blueCopy.add(new ArrayList<>());
      for (int y = 0; y < this.redChannel.get(0).size(); y++) { // y values
        redCopy.get(x).add(this.redChannel.get(x).get(y).intValue());
        greenCopy.get(x).add(this.greenChannel.get(x).get(y).intValue());
        blueCopy.get(x).add(this.blueChannel.get(x).get(y).intValue());
      }
    }
    return new Image(redCopy, greenCopy, blueCopy);
  }

  /**
   * Gets the value of the red pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the red pixel we want to get the value of
   * @param y the y coordinate of the red pixel we want to get the value of
   * @return the value of the red pixel at (x,y)
   */
  @Override
  public int getRedAt(int x, int y) {
    return this.redChannel.get(x).get(y);
  }

  /**
   * Gets the value of the green pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the green pixel we want to get the value of
   * @param y the y coordinate of the green pixel we want to get the value of
   * @return the value of the green pixel at (x,y)
   */
  @Override
  public int getGreenAt(int x, int y) {
    return this.greenChannel.get(x).get(y);
  }

  /**
   * Gets the value of the blue pixel in this image at the (x,y) passed.
   *
   * @param x the x coordinate of the blue pixel we want to get the value of
   * @param y the y coordinate of the blue pixel we want to get the value of
   * @return the value of the blue pixel at (x,y)
   */
  @Override
  public int getBlueAt(int x, int y) {
    return this.blueChannel.get(x).get(y);
  }

  /**
   * Applies the filter to this image and returns the image while also adding the new image to the
   * stack of versions.
   *
   * @param filter the instance of IFilter which indicates which filter we want to apply to this img
   * @return this img after being filtered by the filter indicated by the filter parameter
   * @throws IllegalArgumentException if the filter passed is null
   */
  @Override
  public ImageInterface filter(IFilter filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("The filter passed was null.");
    }
    int width = this.redChannel.size();
    int height = this.redChannel.get(0).size();
    int newRed;
    int newGreen;
    int newBlue;
    Image originalImg = (Image) this.copy();
    Image copyOfThis = (Image) this.copy();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        List<Integer> newVals = filter.newColorValsAt(x, y, originalImg);

        newRed = clamp(newVals.get(0));
        newGreen = clamp(newVals.get(1));
        newBlue = clamp(newVals.get(2));
        copyOfThis.redChannel.get(x).set(y, newRed);
        copyOfThis.greenChannel.get(x).set(y, newGreen);
        copyOfThis.blueChannel.get(x).set(y, newBlue);
      }
    }

    return copyOfThis;
  }

  /**
   * Static method that clamps the values of the pixel to at most 255 and at least 0, else the
   * value remains as it was passed. Since we have thus finished arithmetic we can now convert it
   * to an int where as before it was a double to retain precision through the arithmetic.
   *
   * @param value the value we are checking if we need to clamp
   * @return the value clamped down to 255, up to 0, or staying unchanged depending on the value
   *     passed
   */
  public static int clamp(double value) {
    if (value > 255.0) {
      return 255;
    } else if (value < 0.0) {
      return 0;
    } else {
      return (int) (value + 0.5);
    }
  }

  /**
   * Overwrites toString to return the contents of this image's color values in string form.
   *
   * @return the contents of this image's color values in string form
   */
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    int width = this.blueChannel.size();
    int height = 0;
    if (width > 0) {
      height = this.blueChannel.get(0).size();
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        output.append(this.redChannel.get(x).get(y).intValue() + "\n");
        output.append(this.greenChannel.get(x).get(y).intValue() + "\n");
        output.append(this.blueChannel.get(x).get(y).intValue());
        if (!(x == width - 1 && y == height - 1)) {
          output.append("\n");
        }
      }
    }
    return output.toString();
  }

  /**
   * Creates a String that represents the contents of this image in file format.
   *
   * @return a string that represents the contents of this image in file format
   */
  @Override
  public String createPPMString() {
    StringBuilder output = new StringBuilder();
    int width = this.blueChannel.size();
    int height = this.blueChannel.get(0).size();
    output.append("P3\n");
    output.append(height + " " + width + "\n" + 255 + "\n");
    output.append(this.toString());
    return output.toString();
  }

  /**
   * Gets the width of the image.
   *
   * @return the width of the image in pixels
   */
  @Override
  public int getImageWidth() {
    return this.redChannel.size();
  }

  /**
   * Gets the height of the image.
   *
   * @return the height of the image in pixels
   */
  @Override
  public int getImageHeight() {
    return this.redChannel.get(0).size();
  }

  /**
   * Overrides equals to return true if two Images have the same three color channels.
   * @param other the Image this Image is being compared to
   * @return true if the two Images have the same three color channels, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Image)) {
      return false;
    }

    Image i = (Image) other;
    return this.redChannel.equals(i.redChannel) && this.greenChannel.equals(i.greenChannel)
        && this.blueChannel.equals(i.blueChannel);
  }

  /**
   * Overrides hashCode to return a hashCode that is the sum of the hash codes of each channel.
   * @return an integer representing the hashCode for this Image
   */
  @Override
  public int hashCode() {
    return this.redChannel.hashCode() + this.greenChannel.hashCode() + this.blueChannel.hashCode();
  }

  public List<List<Integer>> getRedChannel() {
    return this.redChannel;
  }
}
