package model.files;

import java.util.Locale;

/**
 * The creator file takes in a FileType enumeration then FileTypeCreator allows the system to be
 * easily adaptable to more file types than just PPM and the extra complexity will pay off in a
 * far greater reduction in complexity else where if we eventually add more file types.
 */
public class FileTypeCreator {

  /**
   * Creates an instance of IFileType based off of the string that is passed.
   *
   * @param type the type of file we will create
   * @return an instance of IFileType indicated by the inputted string
   * @throws IllegalArgumentException if the string passed is null
   */
  public static IFileType createBasedOnString(String type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Parameter passed was null.");
    }
    String adjustedType = type.toLowerCase(Locale.ROOT);
    if (adjustedType.equals("ppm")) {
      return new PPM();
    } else if (adjustedType.equals("jpeg") || adjustedType.equals("jpg")) {
      return new JPEG();
    } else if (adjustedType.equals("png")) {
      return new PNG();
    }
    throw new IllegalArgumentException("Invalid file type given");
  }
}
