package model.files;

/**
 * Represents the JPEG file type, all of the methods regarding JPEG are compatible with
 * JPG as well so we do not need to make an entirely separate class. JPEG extends
 * AbstractComplexFileType so its methods will be preformed in the abstract class unless overridden.
 */
public class JPEG extends AbstractComplexFileType {

  // constructor
  public JPEG() {
    this.fileType = "jpeg";
  }
}
