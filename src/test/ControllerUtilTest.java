import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.ControllerUtil;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import model.FilterModel;
import model.IFilterModel;
import model.ILayer;
import model.files.FileTypeCreator;
import model.files.IFileType;
import model.files.JPEG;
import model.files.PNG;
import model.files.PPM;
import model.image.Image;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ImageCreatorInterface;
import model.programmaticimages.ProgrammaticImageCreator;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the controller util class.
 */
public class ControllerUtilTest {

  ControllerUtil util;
  IFilterModel<ImageInterface> model;
  ImageCreatorInterface imageType;
  ImageInterface checkerBoard;

  ImageInterface testImage;
  List<List<Integer>> red;
  List<List<Integer>> blue;
  List<List<Integer>> green;

  BufferedImage bufferedImage;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    this.util = new ControllerUtil();
    this.model = new FilterModel(new ArrayList<ILayer>());
    this.imageType = ProgrammaticImageCreator.create(ProgrammaticImageType.CHECKER_BOARD);
    this.checkerBoard = this.imageType.createImageRepresentation(100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));

    this.red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(13, 200, 0)),
        new ArrayList<>(Arrays.asList(255, 180, 0))));
    this.green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0)),
        new ArrayList<>(Arrays.asList(128, 63, 0)),
        new ArrayList<>(Arrays.asList(11, 89, 0))));
    this.blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21)),
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255))));
    this.testImage = new Image(red, blue, green);

    this.bufferedImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
  }

  // test for exporting a PPM file
  @Test
  public void testExportFilePPM() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PPM(), "res/TestExport.ppm", modelImage);

    // read from the file and generate the output in a StringBuilder
    FileInputStream readTestFile;
    StringBuilder actualOutput = new StringBuilder();
    try {
      readTestFile = new FileInputStream("res/TestExport.ppm");

      int readStatus = 0;
      while (readStatus != -1) {
        readStatus = readTestFile.read();
        if (readStatus != -1) {
          Character charToBeRead = (char) readStatus;
          actualOutput.append(charToBeRead);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // expected string found in a file
    String expectedOutput = this.checkerBoard.createPPMString();

    // assertion to confirm the correct contents were exported in the file
    assertEquals(expectedOutput, actualOutput.toString());
  }

  // test for exporting a JPEG file
  @Test
  public void testExportFileJPEG() {
    // add a layer to the model
    this.model.addLayer();
    // set model image
    this.model.setImage(this.testImage);
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new JPEG(), "res/TestExport.jpeg", modelImage);

    // read from the file
    FileInputStream readTestFile;
    try {
      readTestFile = new FileInputStream("res/TestExport.jpeg");
      this.bufferedImage = ImageIO.read(readTestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(-7314508, this.bufferedImage.getRGB(0,0));
    assertEquals(-7972438, this.bufferedImage.getRGB(0,1));
    assertEquals(-16766208, this.bufferedImage.getRGB(0,2));
    assertEquals(-8432989, this.bufferedImage.getRGB(1,0));
    assertEquals(-9419884, this.bufferedImage.getRGB(1,1));
    assertEquals(-16764926, this.bufferedImage.getRGB(1,2));
    assertEquals(-5864654, this.bufferedImage.getRGB(2,0));
    assertEquals(-5930447, this.bufferedImage.getRGB(2,1));
    assertEquals(-16521216, this.bufferedImage.getRGB(2,2));
  }

  // test for exporting a PNG file
  @Test
  public void testExportFilePNG() {
    // add a layer to the model
    this.model.addLayer();
    // set model image
    this.model.setImage(this.testImage);
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PNG(), "res/TestExport.png", modelImage);

    // read from the file
    FileInputStream readTestFile;
    try {
      readTestFile = new FileInputStream("res/TestExport.png");
      this.bufferedImage = ImageIO.read(readTestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(-8881921, this.bufferedImage.getRGB(0,0));
    assertEquals(-10921639, this.bufferedImage.getRGB(0,1));
    assertEquals(-16771840, this.bufferedImage.getRGB(0,2));
    assertEquals(-15894400, this.bufferedImage.getRGB(1,0));
    assertEquals(-3647169, this.bufferedImage.getRGB(1,1));
    assertEquals(-16777216, this.bufferedImage.getRGB(1,2));
    assertEquals(-40181, this.bufferedImage.getRGB(2,0));
    assertEquals(-4957863, this.bufferedImage.getRGB(2,1));
    assertEquals(-16711936, this.bufferedImage.getRGB(2,2));
  }

  // test for exception when calling exportFile with a null file type
  @Test (expected = IllegalArgumentException.class)
  public void testExportFileNullFileType() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // attempt to export file (throws exception)
    this.util.exportFile(null, "res/TestExport.ppm", modelImage);
  }

  // test for exception when calling exportFile with a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testExportFileNullFileName() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // attempt to export file (throws exception)
    this.util.exportFile(new PPM(), null, modelImage);
  }

  // test for exception when calling exportFile with a null image
  @Test(expected = IllegalArgumentException.class)
  public void testExportFileNullImage() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // attempt to export file (throws exception)
    this.util.exportFile(new PPM(), "res/TextExport.ppm", null);
  }

  // test for importing a PPM file
  @Test
  public void testImportFilePPM() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PPM(), "res/TestImport.ppm", modelImage);

    // Read from the file and generate the output in a StringBuilder. The string that is
    // generated is what we expect the string that comes from the imported file to be.
    FileInputStream readTestFile;
    StringBuilder expectedOutput = new StringBuilder();
    try {
      readTestFile = new FileInputStream("res/TestImport.ppm");

      int readStatus = 0;
      while (readStatus != -1) {
        readStatus = readTestFile.read();
        if (readStatus != -1) {
          Character charToBeRead = (char) readStatus;
          expectedOutput.append(charToBeRead);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // import file
    IFileType fileType = FileTypeCreator.createBasedOnString("ppm");
    ImageInterface importedImage = null;
    try {
      importedImage = fileType.importFile("res/TestExport.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // string generated from imported file
    String actualOutput = importedImage.createPPMString();

    // assertion to confirm the correct contents were imported from the file
    assertEquals(expectedOutput.toString(), actualOutput);
  }

  // test for importing a JPEG file
  @Test
  public void testImportFileJPEG() {
    // add a layer to the model
    this.model.addLayer();
    // set model image
    this.model.setImage(this.testImage);
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new JPEG(), "res/TestImport.jpeg", modelImage);

    try {
      this.testImage = new JPEG().importFile("res/TestImport.jpeg");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(144, this.testImage.getRedAt(0, 0));
    assertEquals(232, this.testImage.getGreenAt(2, 2));
    assertEquals(163, this.testImage.getBlueAt(1, 0));
    assertEquals(112, this.testImage.getRedAt(1, 1));
    assertEquals(131, this.testImage.getGreenAt(2, 0));
    assertEquals(49, this.testImage.getBlueAt(2, 1));
    assertEquals(0, this.testImage.getRedAt(1, 2));
    assertEquals(89, this.testImage.getGreenAt(0, 1));
    assertEquals(0, this.testImage.getBlueAt(0, 2));
  }

  // test for importing a PNG file
  @Test
  public void testImportFilePNG() {
    // add a layer to the model
    this.model.addLayer();
    // set model image
    this.model.setImage(this.testImage);
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PNG(), "res/TestImport.png", modelImage);

    try {
      this.testImage = new PNG().importFile("res/TestImport.png");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(120, this.testImage.getRedAt(0, 0));
    assertEquals(255, this.testImage.getGreenAt(2, 2));
    assertEquals(128, this.testImage.getBlueAt(1, 0));
    assertEquals(200, this.testImage.getRedAt(1, 1));
    assertEquals(99, this.testImage.getGreenAt(2, 0));
    assertEquals(89, this.testImage.getBlueAt(2, 1));
    assertEquals(0, this.testImage.getRedAt(1, 2));
    assertEquals(89, this.testImage.getGreenAt(0, 1));
    assertEquals(0, this.testImage.getBlueAt(0, 2));
  }

  // test for exception when calling importFile with a null file type
  @Test(expected = IllegalArgumentException.class)
  public void testImportFileNullFileType() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PPM(), "res/TestImport.ppm", modelImage);
    // attempt to import file (throws exception)
    try {
      this.util.importFile(null, "res/TestImport.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // test for exception when calling exportFile with a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testImportFileNullFileName() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // get the current image
    ImageInterface modelImage = this.model.getCurrentImage();
    // export file
    this.util.exportFile(new PPM(), "res/TestImport.ppm", modelImage);
    // attempt to import file (throws exception)
    try {
      this.util.importFile(new PPM(), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // test for exportAll
  @Test
  public void testExportAll() {
    // create first checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // save first image as PPM
    this.model.save("CheckerboardExportAllTest.ppm");
    // create second checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // save second image as JPEG
    this.model.save("CheckerboardExportAllTest.jpeg");
    // create third checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // save third image as PNG
    this.model.save("CheckerboardExportAllTest.png");

    // create checkerboard image to compare to
    this.checkerBoard = this.imageType.createImageRepresentation(3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));

    new ControllerUtil().exportAll("res/ExportAllTestFolder", this.model.getLayers());

    // check PPM file

    // read from the file and generate the output in a StringBuilder
    FileInputStream readTestFile;
    StringBuilder actualOutput = new StringBuilder();
    try {
      readTestFile = new FileInputStream("res/ExportAllTestFolder/CheckerboardExportAllTest"
          + ".ppm");

      int readStatus = 0;
      while (readStatus != -1) {
        readStatus = readTestFile.read();
        if (readStatus != -1) {
          Character charToBeRead = (char) readStatus;
          actualOutput.append(charToBeRead);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // expected string found in a file
    String expectedOutput = this.checkerBoard.createPPMString();

    // assertion to confirm the correct contents were exported in the file
    assertEquals(expectedOutput, actualOutput.toString());

    // check PNG file

    try {
      readTestFile = new FileInputStream("res/ExportAllTestFolder/CheckerboardExportAllTest"
          + ".png");
      this.bufferedImage = ImageIO.read(readTestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(-1, this.bufferedImage.getRGB(0,0));
    assertEquals(-16777216, this.bufferedImage.getRGB(0,1));
    assertEquals(-1, this.bufferedImage.getRGB(0,2));
    assertEquals(-16777216, this.bufferedImage.getRGB(1,0));
    assertEquals(-1, this.bufferedImage.getRGB(1,1));
    assertEquals(-16777216, this.bufferedImage.getRGB(1,2));
    assertEquals(-1, this.bufferedImage.getRGB(2,0));
    assertEquals(-16777216, this.bufferedImage.getRGB(2,1));
    assertEquals(-1, this.bufferedImage.getRGB(2,2));

    // check JPEG file

    try {
      readTestFile = new FileInputStream("res/ExportAllTestFolder/CheckerboardExportAllTest"
          + ".jpeg");
      this.bufferedImage = ImageIO.read(readTestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(-1, this.bufferedImage.getRGB(0,0));
    assertEquals(-16777216, this.bufferedImage.getRGB(0,1));
    assertEquals(-263173, this.bufferedImage.getRGB(0,2));
    assertEquals(-16777216, this.bufferedImage.getRGB(1,0));
    assertEquals(-1, this.bufferedImage.getRGB(1,1));
    assertEquals(-15856114, this.bufferedImage.getRGB(1,2));
    assertEquals(-1, this.bufferedImage.getRGB(2,0));
    assertEquals(-16185079, this.bufferedImage.getRGB(2,1));
    assertEquals(-394759, this.bufferedImage.getRGB(2,2));

    // check locations text file

    try {
      actualOutput = new StringBuilder();
      readTestFile = new FileInputStream("res/ExportAllTestFolder/locations.txt");

      int readStatus = 0;
      while (readStatus != -1) {
        readStatus = readTestFile.read();
        if (readStatus != -1) {
          Character charToBeRead = (char) readStatus;
          actualOutput.append(charToBeRead);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("Layer 1 File: CheckerboardExportAllTest.ppm Visibility: true\n"
        + "Layer 2 File: CheckerboardExportAllTest.jpeg Visibility: true\n"
        + "Layer 3 File: CheckerboardExportAllTest.png Visibility: true\n",
        actualOutput.toString());
  }

  // test for importAll
  @Test
  public void testImportAll() {
    // import folder of images
    List<ILayer> layers = new ArrayList<>();
    try {
      layers = new ControllerUtil().importAll("res/ExportAllTestFolder");
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.model = new FilterModel(layers);

    this.red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 251)),
        new ArrayList<>(Arrays.asList(0, 255, 14)),
        new ArrayList<>(Arrays.asList(255, 9, 249))));
    this.green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 251)),
        new ArrayList<>(Arrays.asList(0,255, 14)),
        new ArrayList<>(Arrays.asList(255, 9, 249))));
    this.blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 251)),
        new ArrayList<>(Arrays.asList(0, 255, 14)),
        new ArrayList<>(Arrays.asList(255, 9, 249))));
    ImageInterface checkerBoardAdjusted = new Image(this.red, this.green, this.blue);

    // check layers in model to make sure files were imported correctly
    assertEquals(3, this.model.getLayers().size());

    assertEquals(this.imageType.createImageRepresentation(3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE))),
        this.model.getImageAt(0));
    // TODO MADE CHANGE
    assertTrue(this.model.getLayers().get(0).getVisibility());

    assertEquals(checkerBoardAdjusted, this.model.getImageAt(1));
    assertTrue(this.model.getLayers().get(1).getVisibility());

    assertEquals(this.imageType.createImageRepresentation(3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE))),
        this.model.getImageAt(2));
    assertTrue(this.model.getLayers().get(2).getVisibility());
  }
}
