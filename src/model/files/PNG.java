package model.files;

/**
 * Represents the PNG file type. PNG extends AbstractComplexFileType so its methods
 * will be preformed in the abstract class unless overridden.
 */
public class PNG extends AbstractComplexFileType {

  // constructor
  public PNG() {
    this.fileType = "png";
  }
}
