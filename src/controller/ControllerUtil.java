package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import model.ILayer;
import model.Layer;
import model.files.FileTypeCreator;
import model.files.IFileType;
import model.image.Image;
import model.image.ImageInterface;

/**
 * The class is used a utility class to de-clutter and distribute what we consider controller design
 * universal methods so we do not want to write them in a particular implementation
 * of controller interface.
 */
public class ControllerUtil {

  /**
   * Exports the image passed with the name passed and as the specified file type.
   *
   * @param fileType export file type of our image
   * @param fileName the name of the file post exporting
   * @param img the image we are exporting
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public void exportFile(IFileType fileType, String fileName, ImageInterface img)
      throws IllegalArgumentException {
    if (fileType == null || fileName == null || img == null) {
      throw new IllegalArgumentException("Null parameters given.");
    }
    try {
      fileType.exportFile(fileName, img);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Imports the file indicated by the name passed and of the indicated file type.
   *
   * @param fileType the filetype of the importing file
   * @param fileName the name of the file we are importing
   * @return our image representation of the file that we imported
   * @throws IllegalArgumentException if any of the params are negative
   */
  public ImageInterface importFile(IFileType fileType, String fileName)
      throws IllegalArgumentException, IOException {
    if (fileType == null || fileName == null) {
      throw new IllegalArgumentException("Null parameter given.");
    }
    return fileType.importFile(fileName);
  }

  /**
   * Exports all of the images to the folder indicated by the given string, and the fields that are
   * to be exported are passed as a list of layers.
   *
   * @param folderName the name of the exporting folder location
   * @param files the list of layers to be exported
   * @throws IllegalArgumentException if at least one params is null
   */
  public void exportAll(String folderName, List<ILayer> files) {
    if (folderName == null || files == null) {
      throw new IllegalArgumentException("At least one param was null.");
    }
    int counter = 1;
    StringBuilder textFileContents = new StringBuilder();
    for (ILayer l : files) {
      textFileContents.append("Layer " + counter + " File: "
          + files.get(counter - 1).getFileName() + " Visibility: " + l.getVisibility() + "\n");
      String exportFile = l.getFileName();
      int periodInd = exportFile.indexOf(".");
      IFileType exportFileType =
          FileTypeCreator.createBasedOnString(exportFile.substring(
          periodInd + 1));
      this.exportFile(exportFileType, folderName + "/" + exportFile, l.getImage());
      counter++;
    }
    try {
      new FileOutputStream(folderName + "/" + "locations.txt").write(
          textFileContents.toString().getBytes());
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The file name was invalid.");
    } catch (IOException e) {
      throw new IllegalStateException("There was an input/output error.");
    }
  }

  /**
   * Allows us to import multiple files at once by calling on a folder and then reading from
   * a locations.txt file so we know which layer the files go to respectively.
   *
   * @param folderName the folder location of the import
   * @return a list of layers that mirror the layers in the import folder with the correct
   *     ordering from the locations.txt file
   * @throws IllegalArgumentException if the folder name is null or the file is not found
   */
  public List<ILayer> importAll(String folderName) throws IllegalArgumentException, IOException {
    if (folderName == null) {
      throw new IllegalArgumentException("The folder name can not be null.");
    }
    Scanner sc;
    ArrayList<ILayer> layerList = new ArrayList<>();
    try {
      sc = new Scanner(new FileInputStream(folderName + "/locations.txt"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + folderName + " not found!");
    }
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String in = sc.nextLine().toLowerCase();
      Scanner lineScan = new Scanner(in);
      String nextInLine = "";
      String file = "";
      ImageInterface img = new Image(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
      boolean visible = false;
      while (lineScan.hasNext()) {
        nextInLine = lineScan.next().toLowerCase();

        if (nextInLine.equals("file:")) {
          file = lineScan.next();
          int indexAfterDot = file.lastIndexOf(".") + 1;
          IFileType fileType = FileTypeCreator.createBasedOnString(file.substring(indexAfterDot));
          img = this.importFile(fileType, folderName + "/" + file);
        }
        if (nextInLine.equals("visibility:")) {
          String visibility = lineScan.next();
          visible = Boolean.parseBoolean(visibility);

        }
      }
      Layer lastLayerAdded = new Layer(img, file, visible);
      layerList.add(lastLayerAdded);
    }
    return layerList;
  }
}
