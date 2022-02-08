package model.files;

import java.io.IOException;
import model.image.ImageInterface;

/**
 * Represents the file type of the image that we are working with, example: PPM. As of hw05 we are
 * only dealing with PPM file types when it comes to import, export, and filtering but IFileType
 * allows us to add another file type by simply implementing IFileType.
 */
public interface IFileType {

  /**
   * Exports the file that we have created either from a file type or programmatically created.
   * IFileType has implementing classes which will export a file type indicated by the name of the
   * class such that if we want to add another exporting file type we simply create a new class
   * which implements IFileType.
   *
   * @param fileName name of the file when we export
   * @param img the image we are exporting
   * @throws IllegalArgumentException if either fileName or fileData are passed, or if the file
   *     name is not possible, or if there is an input-output error
   */
  void exportFile(String fileName, ImageInterface img)
      throws IllegalArgumentException, IOException;

  /**
   * Imports a file and returns the ImageInterface that represents the import file indicated by the
   * file name. Depening on which implementing class the method corresponds to what the file type of
   * the import is such that it is easy to add future functionality for other file types.
   *
   * @param fileName the name of the file with root in the project ie: "src/Koala.ppm"
   * @return the ImageInterface representation of the image indicated by the file name
   * @throws IllegalArgumentException if the file name is null or the file can not be found
   */
  ImageInterface importFile(String fileName) throws IllegalArgumentException, IOException;
}
