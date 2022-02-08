package model.programmaticimages;

import java.util.ArrayList;
import java.util.List;
import model.image.Image;
import model.image.ImageInterface;

/**
 * Class used to create a checkerboard image. This class implements the ImageCreatorInterface so
 * that the method can be used to create an image representation for a checkerboard.
 */
public class CheckerBoard implements ImageCreatorInterface {

  /**
   * Creates a checker board in an ImageInterface form by generating all of the necessary RGB
   * channels.
   *
   * @param width width of the checker board
   * @param height height of the checker board
   * @param tileSize size of each tile in the checker board
   * @param colorList list of colors that are in the checker board, can only be of size 2
   * @return an Image that contains all of the correct rgb values in their respective 2D arrays
   * @throws IllegalArgumentException if the color list is null or not of size 2, or if any of
   *     the int params are negative
   */
  @Override
  public ImageInterface createImageRepresentation(int width, int height, int tileSize,
      ArrayList<ColorEnum> colorList) throws IllegalArgumentException {
    if (colorList == null || colorList.size() != 2) {
      throw new IllegalArgumentException("The color list was invalid.");
    }
    if (width < 0 || height < 0 || tileSize < 0) {
      throw new IllegalArgumentException("Negative parameter given.");
    }
    ColorEnum color1 = colorList.get(0);
    ColorEnum color2 = colorList.get(1);

    List<List<Integer>> redChannel = new ArrayList<>();
    List<List<Integer>> greenChannel = new ArrayList<>();
    List<List<Integer>> blueChannel = new ArrayList<>();

    ColorEnum tile;
    for (int x = 0; x < width; x++) {
      int num;
      if (((x / tileSize) + 1) % 2 == 0) {
        num = 0;
      } else {
        num = 1;
      }
      redChannel.add(new ArrayList<>());
      greenChannel.add(new ArrayList<>());
      blueChannel.add(new ArrayList<>());
      for (int y = 0; y < height; y++) {
        if (((y / tileSize) + 1) % 2 == num) {
          tile = color2;
        }
        else {
          tile = color1;
        }
        redChannel.get(x).add(tile.getRed());
        greenChannel.get(x).add(tile.getGreen());
        blueChannel.get(x).add(tile.getBlue());
        /*
        // tileSize == 100
        00/100 = (0+1) % 2 == 0; // White false
        99/100 = (0+1) % 2 == 0; // White false
        100/100 = (1+1) % 2 == 0; // Black true
        199/100 = (1+1) % 2 == 0; // Black true
        200/100 = (2+1) % 2 == 0; // White false
        299/100 = (2+1) % 2 == 0; // White false
        300/100 = (3+1) % 2 == 0; // Black true
        399/100 = (3+1) % 2 == 0; // Black true
        400/100 = (4+1) % 2 == 0; // White false
        499/100 = (4+1) % 2 == 0; // White false

         */
      }
    }
    ImageInterface checkerBoard = new Image(redChannel, greenChannel, blueChannel);
    return checkerBoard;
  }


}
