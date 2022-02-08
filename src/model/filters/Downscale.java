package model.filters;

import java.util.ArrayList;
import java.util.List;
import model.image.Image;
import model.image.ImageInterface;

/**
 * Represents the downscaling filter where an image or a multi layer image has all of the images
 * downscaled to a new height and width dimension with compression techniques. This class can not
 * upscale an image but only downscale.
 */
public class Downscale extends AbstractApplyAllStatus {
  private final int newWidth;
  private final int newHeight;

  /**
   * Constructor.
   *
   * @param newWidth the width of the image after downscaling
   * @param newHeight the height of the image after downscaling
   */
  public Downscale(int newWidth, int newHeight) {
    if (newWidth < 0 || newHeight < 0) {
      throw new IllegalArgumentException("Negative parameter given");
    }
    this.applyToAll = true;
    this.newWidth = newWidth;
    this.newHeight = newHeight;
  }


  /**
   * Downscales the old image field and creates and stores the new downscaled image in the
   * new image field.
   *
   * @return the old image now downscaled
   */
  private ImageInterface downscaleImage(ImageInterface originalImage)
      throws IllegalArgumentException {
    if (originalImage == null) {
      throw new IllegalArgumentException("Null image given.");
    }
    if (originalImage.getImageWidth() < this.newWidth
        || originalImage.getImageHeight() < this.newHeight) {
      throw new IllegalArgumentException("Invalid downscale attempted");
    }
    List<List<Integer>> red = new ArrayList<>();
    List<List<Integer>> green = new ArrayList<>();
    List<List<Integer>> blue = new ArrayList<>();

    // initialize the image to the new desired height and width
    for (int x = 0; x < newWidth; x++) {
      red.add(new ArrayList<>());
      green.add(new ArrayList<>());
      blue.add(new ArrayList<>());
      for (int y = 0; y < newHeight; y++) {
        red.get(x).add(null);
        green.get(x).add(null);
        blue.get(x).add(null);
      }
    }

    for (int x = 0; x < newWidth; x++) {
      for (int y = 0; y < newHeight; y++) {
        double xOfOld = (double) x * originalImage.getImageWidth() / newWidth;
        double yOfOld = (double) y * originalImage.getImageHeight() / newHeight;
        int cpRed;
        int cpGreen;
        int cpBlue;
        if (xOfOld == Math.floor(xOfOld) || yOfOld == Math.floor(yOfOld)) {
          // we know it is an integer
          int xOfOldInt = (int) xOfOld;
          int yOfOldInt = (int) yOfOld;
          cpRed = originalImage.getRedAt(xOfOldInt, yOfOldInt);
          cpGreen = originalImage.getGreenAt(xOfOldInt, yOfOldInt);
          cpBlue = originalImage.getBlueAt(xOfOldInt, yOfOldInt);
        }
        else {
          cpRed = this.cpValue(
              xOfOld,
              yOfOld,
              originalImage.getRedAt((int) Math.floor(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getRedAt((int) Math.ceil(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getRedAt((int) Math.floor(xOfOld), (int) Math.ceil(yOfOld)),
              originalImage.getRedAt((int) Math.ceil(xOfOld), (int) Math.ceil(yOfOld)));
          cpGreen = this.cpValue(
              xOfOld,
              yOfOld,
              originalImage.getGreenAt((int) Math.floor(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getGreenAt((int) Math.ceil(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getGreenAt((int) Math.floor(xOfOld), (int) Math.ceil(yOfOld)),
              originalImage.getGreenAt((int) Math.ceil(xOfOld), (int) Math.ceil(yOfOld)));
          cpBlue = this.cpValue(
              xOfOld,
              yOfOld,
              originalImage.getBlueAt((int) Math.floor(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getBlueAt((int) Math.ceil(xOfOld), (int) Math.floor(yOfOld)),
              originalImage.getBlueAt((int) Math.floor(xOfOld), (int) Math.ceil(yOfOld)),
              originalImage.getBlueAt((int) Math.ceil(xOfOld), (int) Math.ceil(yOfOld)));
        }
        red.get(x).set(y, cpRed);
        green.get(x).set(y, cpGreen);
        blue.get(x).set(y, cpBlue);
      }
    }
    return new Image(red, green, blue);
  }

  /**
   * Gets the color of the pixel at the old x and y.
   *
   * @param xOld x coordinate of the pixel in the old image
   * @param yOld y coordinate of the pixel in the old image
   * @param ca color of the pixel at point a
   * @param cb color of the pixel at point b
   * @param cc color of the pixel at point c
   * @param cd color of the pixel at point d
   * @return the new color value at what was the old x and y
   */
  private int cpValue(double xOld, double yOld, int ca, int cb, int cc, int cd) {
    int m = (int) (cb*(xOld - Math.floor(xOld)) + ca*(Math.ceil(xOld) - xOld));
    int n = (int) (cd*(xOld - Math.floor(xOld)) + cc*(Math.ceil(xOld) - xOld));
    return (int) (n*(yOld - Math.floor(yOld)) + m*(Math.ceil(yOld) - yOld));
  }

  @Override
  public ImageInterface applyEffect(ImageInterface originalImage) throws IllegalArgumentException {
    if (originalImage == null) {
      throw new IllegalArgumentException("Null image given.");
    }
    return this.downscaleImage(originalImage);
  }
}
