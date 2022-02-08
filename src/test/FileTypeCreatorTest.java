import static org.junit.Assert.assertEquals;

import model.files.FileTypeCreator;
import model.files.IFileType;
import model.files.JPEG;
import model.files.PNG;
import model.files.PPM;
import org.junit.Test;

/**
 * Test class for FileTypeCreator.
 */
public class FileTypeCreatorTest {

  /*
  Tests for the createBasedOnStringMethod()
   */

  // Tests that an exception is thrown for a null string
  @Test(expected = IllegalArgumentException.class)
  public void createBasedOnStringExceptionWithNull() {
    FileTypeCreator.createBasedOnString(null);
  }

  // Tests that an exception is thrown for an empty string
  @Test(expected = IllegalArgumentException.class)
  public void createBasedOnStringExceptionWithEmpty() {
    FileTypeCreator.createBasedOnString(null);
  }

  // Tests that an exception is thrown for an invalid string
  @Test(expected = IllegalArgumentException.class)
  public void createBasedOnStringExceptionWithInvalid() {
    FileTypeCreator.createBasedOnString("txt");
  }

  // Testing that passing "ppm" works
  @Test
  public void createBasedOnStringValidWithPPM() {
    IFileType ppmCreateTest = FileTypeCreator.createBasedOnString("ppm");
    assertEquals(PPM.class, ppmCreateTest.getClass());
    // tests that case does not matter
    ppmCreateTest = FileTypeCreator.createBasedOnString("PpM");
    assertEquals(PPM.class, ppmCreateTest.getClass());
  }

  // Testing that passing "jpeg" or "jpg" works
  @Test
  public void createBasedOnStringValidWithJPEG() {
    IFileType ppmCreateTest = FileTypeCreator.createBasedOnString("jpeg");
    assertEquals(JPEG.class, ppmCreateTest.getClass());
    ppmCreateTest = FileTypeCreator.createBasedOnString("jpg");
    assertEquals(JPEG.class, ppmCreateTest.getClass());

    // testing that case does not matter
    ppmCreateTest = FileTypeCreator.createBasedOnString("jPEG");
    assertEquals(JPEG.class, ppmCreateTest.getClass());
    ppmCreateTest = FileTypeCreator.createBasedOnString("JPg");
    assertEquals(JPEG.class, ppmCreateTest.getClass());

  }

  // Testing that passing "png" works
  @Test
  public void createBasedOnStringValidWithPNG() {
    IFileType ppmCreateTest = FileTypeCreator.createBasedOnString("png");
    assertEquals(PNG.class, ppmCreateTest.getClass());
    // test that case does not matter
    ppmCreateTest = FileTypeCreator.createBasedOnString("pNg");
    assertEquals(PNG.class, ppmCreateTest.getClass());
  }


}