package model.programmaticimages;

/**
 * Factory class used to create instances of image creator objects. Defines a public enum used to
 * determine which function object should be created.
 */
public class ProgrammaticImageCreator {

  /**
   * Enumeration defining types of programmable images currently supported by the model.
   */
  public enum ProgrammaticImageType {
    CHECKER_BOARD;
  }

  /**
   * Factory method used to create an instance of the ImageCreatorInterface.
   * @param imageType the type of programmable image that is to be created
   * @return an object of type ImageCreatorInterface
   * @throws IllegalArgumentException if an invalid ProgrammaticImageType is given
   */
  public static ImageCreatorInterface create(ProgrammaticImageType imageType)
      throws IllegalArgumentException {
    if (imageType == ProgrammaticImageType.CHECKER_BOARD) {
      return new CheckerBoard();
    }
    throw new IllegalArgumentException("Invalid GameType given.");
  }
}
