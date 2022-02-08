package model.files;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import model.image.Image;
import model.image.ImageInterface;

/**
 * Abstracts the importing and exporting methods since some file types are able to be imported
 * and exported in an identical fashion due to how Java is able to read some files the same as it
 * does others, for example: JPEG and PNG are able to be imported and exported in the same exact
 * manner but said method does not work with PPM file type.
 */
public class AbstractComplexFileType implements IFileType {

  protected String fileType;

  @Override
  public void exportFile(String fileName, ImageInterface img)
      throws IllegalArgumentException, IOException {
    BufferedImage bufferedImage = new BufferedImage(img.getImageWidth(), img.getImageHeight(),
        BufferedImage.TYPE_INT_RGB);

    // fill the rows with the data at the indicated location of (x,y)
    for (int x = 0; x < img.getImageWidth(); x++) { // rows
      for (int y = 0; y < img.getImageHeight(); y++) { // "columns"
        int redAt = img.getRedAt(x,y);
        int greenAt = img.getGreenAt(x,y);
        int blueAt = img.getBlueAt(x,y);
        bufferedImage.setRGB(x,y,new Color(redAt, greenAt, blueAt).getRGB());
      }
    }

    try {
      ImageIO.write(bufferedImage, this.fileType, new FileOutputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Could not export file because the file path could not be "
          + "found.");
    }
  }

  @Override
  public ImageInterface importFile(String fileName) throws IllegalArgumentException, IOException {
    FileInputStream in;
    BufferedImage bufferedImage = new BufferedImage(100, 100,
        BufferedImage.TYPE_INT_RGB);

    try {
      in = new FileInputStream(fileName);
      bufferedImage = ImageIO.read(in);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Could not find file.");
    }

    int height = bufferedImage.getHeight();
    int width = bufferedImage.getWidth();

    List<List<Integer>> redChannel = new ArrayList<>();
    List<List<Integer>> greenChannel = new ArrayList<>();
    List<List<Integer>> blueChannel = new ArrayList<>();

    // fill the rows with the data at the indicated location of (x,y)
    for (int x = 0; x < width; x++) { // rows
      // establishes the rows of lists
      redChannel.add(new ArrayList<>());
      greenChannel.add(new ArrayList<>());
      blueChannel.add(new ArrayList<>());
      for (int y = 0; y < height; y++) { // "columns"
        // fills in the values in the columns
        redChannel.get(x).add(new Color(bufferedImage.getRGB(x, y)).getRed());
        greenChannel.get(x).add(new Color(bufferedImage.getRGB(x, y)).getGreen());
        blueChannel.get(x).add(new Color(bufferedImage.getRGB(x, y)).getBlue());
      }
    }

    return new Image(redChannel, greenChannel, blueChannel);
  }
}
