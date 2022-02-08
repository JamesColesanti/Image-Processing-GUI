import static org.junit.Assert.assertEquals;

import java.io.IOException;
import model.files.IFileType;
import model.files.PPM;
import model.image.ImageInterface;
import org.junit.Test;

/**
 * Test class for the PPM class. This class tests that files can be properly exported and imported
 * in PPM format.
 */
public class PPMTest {

  private IFileType ppm = new PPM();

  /*
   Tests for exportFile
   */
  /*
  // tests that export file throws an illegal argument exception when file name is null
  @Test(expected = IllegalArgumentException.class)
  public void exportButFileNameIsNull() {
  //  this.ppm.exportFile(null, "test");
  }

  // tests that export file throws an illegal argument exception when file data is null
  @Test(expected = IllegalArgumentException.class)
  public void exportButFileDataIsNull() {
    this.ppm.exportFile("Koala", null);
  }

  // tests that export file throws an illegal argument exception when file name and data are null
  @Test (expected = IllegalArgumentException.class)
  public void exportButFileDataAndNameAreNull() {
    this.ppm.exportFile(null, null);
  }

  // TODO tests that an IOException is caught and an IllegalStateException is thrown
  // TODO export file throws an illegal argument exception when catching a FileNotFoundException
  // TODO tests that a file is successfully exported


  /*
   Tests for importFile
   */

  // tests that an exception is thrown when the file name is null
  @Test (expected = IllegalArgumentException.class)
  public void importNullFileName() {
    try {
      this.ppm.importFile(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // tests that an exception is thrown when the file can not be found
  @Test (expected = IllegalArgumentException.class)
  public void importAbsentFileName() {
    try {
      this.ppm.importFile("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // test to check that the first 3 red channel values are correct
  @Test
  public void firstThreeRed() {
    ImageInterface img = null;
    try {
      img = this.ppm.importFile("res/TestingImage.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(12 ,img.getRedAt(0,0));
    assertEquals(11 ,img.getRedAt(0,1));
    assertEquals(9 ,img.getRedAt(2,0));
  }

  // test to check that the first 3 green channel values are correct
  @Test
  public void firstThreeGreen() {
    ImageInterface img = null;
    try {
      img = this.ppm.importFile("res/TestingImage.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(12 ,img.getGreenAt(0,0));
    assertEquals(11 ,img.getGreenAt(0,1));
    assertEquals(9 ,img.getGreenAt(2,0));
  }

  // test to check that the first 3 blue channel values are correct
  @Test
  public void firstThreeBlue() {
    ImageInterface img = null;
    try {
      img = this.ppm.importFile("res/TestingImage.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(10 ,img.getBlueAt(0,0));
    assertEquals(9 ,img.getBlueAt(0,1));
    assertEquals(9 ,img.getBlueAt(2,0));
  }
}