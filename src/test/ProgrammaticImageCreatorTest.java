import static org.junit.Assert.assertEquals;

import model.programmaticimages.CheckerBoard;
import model.programmaticimages.ProgrammaticImageCreator;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import org.junit.Test;

/**
 * Testing class for the ProgrammaticImageCreator class. This class tests that the correct
 * ProgrammaticImage function object is created based on the ProgrammaticImageType enum that is
 * passed to the create method.
 */
public class ProgrammaticImageCreatorTest {

  // tests that an exception is thrown when the enum passed is null
  @Test(expected = IllegalArgumentException.class)
  public void createWithNull() {
    ProgrammaticImageCreator.create(null);
  }

  // tests that a new CheckerBoard is created when indicated
  @Test
  public void createWithCheckerBoard() {
    assertEquals(CheckerBoard.class,
        ProgrammaticImageCreator.create(ProgrammaticImageType.CHECKER_BOARD).getClass());
  }
}