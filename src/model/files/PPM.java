package model.files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.image.Image;
import model.image.ImageInterface;

/**
 * The PPM class represents the PPM file type. If we want to add functionality for other file types
 * then we simply create a new class called jpeg, png, etc. whatever is desired, and implement
 * IFileType. This allows the system to be easily expandable and open to future feature additions.
 * The PPM class allows us to perform all operations on the export and import files as PPM files,
 * since, by nature of the methods being called on the PPM class, we assume the file type we wish to
 * use is of type PPM.
 */
public class PPM implements IFileType {

  /**
   * Exports the file that we have created either from a file type or programmatically created. We
   * are in the PPM implementing class so we will export a file with name fileName and contents of
   * fileData, as passed. If there is no file already named fileName then we create a new file else
   * we overwrite.
   *
   * @param fileName name of the file when we export
   * @param img the image we are exporting
   * @throws IllegalArgumentException if either fileName or fileData are passed, or if the file name
   *                                  is not possible
   * @throws IllegalStateException    if there is an input-output error
   */
  @Override
  public void exportFile(String fileName, ImageInterface img) throws IllegalArgumentException,
      IllegalStateException {
    if (img == null || fileName == null) {
      throw new IllegalArgumentException("The file data and file name can not be null.");
    }
    try {
      new FileOutputStream(fileName).write(img.createPPMString().getBytes());
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The file name was invalid: " + fileName);
    } catch (IOException e) {
      throw new IllegalStateException("There was an input/output error.");
    }
  }

  /**
   * Imports a file and returns the ImageInterface that represents the import file indicated by the
   * file name. We are in the PPM class so the import file is expected to be of file type PPM.
   *
   * @param fileName the name of the file with root in the project ie: "src/Koala.ppm"
   * @return the ImageInterface representation of the image indicated by the file name
   * @throws IllegalArgumentException if the file name is null or the file can not be found
   */

  @Override
  public Image importFile(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("File name can not be null.");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<Integer>> redChannel = new ArrayList<>();
    List<List<Integer>> greenChannel = new ArrayList<>();
    List<List<Integer>> blueChannel = new ArrayList<>();

    // fill the rows with the data at the indicated location of (j,i)
    for (int i = 0; i < height; i++) { // rows
      // establishes the rows of lists
      redChannel.add(new ArrayList<>());
      greenChannel.add(new ArrayList<>());
      blueChannel.add(new ArrayList<>());
      for (int j = 0; j < width; j++) { // "columns"
        // fills in the values in the columns
        redChannel.get(i).add(sc.nextInt());
        greenChannel.get(i).add(sc.nextInt());
        blueChannel.get(i).add(sc.nextInt());
      }
    }
    return new Image(redChannel, greenChannel, blueChannel);
  }
}