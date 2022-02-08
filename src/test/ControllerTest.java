import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.ControllerUtil;
import controller.FilterController;
import controller.IFilterController;
import java.awt.image.BufferedImage;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import model.FilterModel;
import model.IComplexEffectModel;
import model.IFilterModel;
import model.ILayer;
import model.files.PPM;
import model.filters.Blur;
import model.filters.Downscale;
import model.filters.IEffect;
import model.filters.IMosaic;
import model.filters.Monochrome;
import model.filters.Mosaic;
import model.filters.Sepia;
import model.filters.Sharpen;
import model.image.Image;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ImageCreatorInterface;
import model.programmaticimages.ProgrammaticImageCreator;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import org.junit.Before;
import org.junit.Test;
import view.IFilterView;
import view.SimpleFilterView;

/**
 * Test class for the controller class.
 */
public class ControllerTest {

  IComplexEffectModel<ImageInterface> model;
  IFilterController<ImageInterface> controller;
  IFilterView view;
  Readable rd;
  Appendable ap;
  StringBuilder str;

  ImageCreatorInterface imageType;
  ImageInterface checkerBoard;

  BufferedImage bufferedImage;

  List<List<Integer>> red;
  List<List<Integer>> blue;
  List<List<Integer>> green;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    this.model = new FilterModel(new ArrayList<>());
    this.ap = new CharArrayWriter();
    this.str = new StringBuilder();
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.view = new SimpleFilterView(this.ap);
    this.controller = new FilterController(this.model, this.rd, this.ap);

    this.imageType = ProgrammaticImageCreator.create(ProgrammaticImageType.CHECKER_BOARD);
    this.checkerBoard = this.imageType.createImageRepresentation(3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    this.bufferedImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

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
  }

  // test for user inputting "q"
  @Test
  public void testQLowercase() {
    this.str.append("q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals("\n"
        + "Commands:\n"
        + "q or Q: quit\n"
        + "blur: blurs current image\n"
        + "sepia: applies sepia to current image\n"
        + "sharpen: sharpens current image\n"
        + "monochrome: applies monochrome to the current image\n"
        + "create: adds a new empty layer\n"
        + "current INT: int corresponds to the layer that we want to make visible and top\n"
        + "load FOLDER/.../FOLDER/FILENAME.FILETYPE: loads the file into the lowest empty layer\n"
        + "save FILENAME.FILETYPE: saves the current image and exports\n"
        + "export-all FOLDER/.../FOLDER/: exports all of the layers to the location with a "
        + "locations.txt file\n"
        + "import-all FOLDER/.../FOLDER/: imports all of the in the folder based off of the "
        + "locations.txt file in the specified folder\n"
        + "invisible INDEX(1 indexing): sets the layer at the index to invisible\n"
        + "visible INDEX(1 indexing): sets the layer at the index to visible\n"
        + "The program was quit.\n", this.ap.toString());
  }

  // test for user inputting "q"
  @Test
  public void testQUppercase() {
    this.str.append("Q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals("\n"
        + "Commands:\n"
        + "q or Q: quit\n"
        + "blur: blurs current image\n"
        + "sepia: applies sepia to current image\n"
        + "sharpen: sharpens current image\n"
        + "monochrome: applies monochrome to the current image\n"
        + "create: adds a new empty layer\n"
        + "current INT: int corresponds to the layer that we want to make visible and top\n"
        + "load FOLDER/.../FOLDER/FILENAME.FILETYPE: loads the file into the lowest empty layer\n"
        + "save FILENAME.FILETYPE: saves the current image and exports\n"
        + "export-all FOLDER/.../FOLDER/: exports all of the layers to the location with a "
        + "locations.txt file\n"
        + "import-all FOLDER/.../FOLDER/: imports all of the in the folder based off of the "
        + "locations.txt file in the specified folder\n"
        + "invisible INDEX(1 indexing): sets the layer at the index to invisible\n"
        + "visible INDEX(1 indexing): sets the layer at the index to visible\n"
        + "The program was quit.\n", this.ap.toString());
  }

  // test for user inputting "create"
  @Test
  public void testCreate() {
    this.str.append("create q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals(1, this.model.getLayers().size());
  }

  // test for user inputting "load"
  @Test
  public void testLoad() {
    this.str.append("create load res/ExportAllTestFolder/CheckerboardExportAllTest.png q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals(this.checkerBoard, this.model.getCurrentImage());
  }

  // test for user inputting "current"
  @Test
  public void testCurrent() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "blur"
  @Test
  public void testBlur() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png blur q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Blur());

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "sharpen"
  @Test
  public void testSharpen() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sharpen q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Sharpen());

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "monochrome"
  @Test
  public void testMonochrome() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png monochrome q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Monochrome());

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "sepia"
  @Test
  public void testSepia() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sepia q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Sepia());

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "mosaic"
  @Test
  public void testMosaic() {
    this.str.append("create checkerboard 3 3 1 mosaic 2 q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    IMosaic mosaic = new Mosaic();
    mosaic.initializeSeeds(2, this.checkerBoard, new Random(10), new Random(10));
    this.checkerBoard = this.checkerBoard.filter(mosaic);

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "downscale"
  @Test
  public void testDownscale() {
    this.str.append("checkerboard 100 100 10 checkerboard 100 100 10 current 1 downscale 10 10 q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.imageType.createImageRepresentation(100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    IEffect downscale = new Downscale(10, 10);
    this.checkerBoard = downscale.applyEffect(this.checkerBoard);

    assertEquals(this.checkerBoard, this.model.getImageAt(0));

    this.checkerBoard = this.imageType.createImageRepresentation(100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    downscale = new Downscale(10, 10);
    this.checkerBoard = downscale.applyEffect(this.checkerBoard);

    assertEquals(this.checkerBoard, this.model.getImageAt(1));
  }

  // test for user inputting "save"
  @Test
  public void testSave() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sepia save CheckerboardExportAllFromController.ppm q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Sepia());

    ImageInterface modelImage = null;
    try {
      modelImage = new ControllerUtil().importFile(new PPM(),
          "CheckerboardExportAllFromController.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(this.checkerBoard, modelImage);
  }

  // test for user inputting "export-all"
  @Test
  public void testExportAll() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sepia save CheckerboardExportAllFromController.ppm invisible 2 current 1 "
        + "load res/ExportAllTestFolder/CheckerboardExportAllTest.png save CheckerboardExport"
        + "AllFromController.png export-all res/ExportAllThroughController q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Sepia());

    // check PPM file

    // read from the file and generate the output in a StringBuilder
    FileInputStream readTestFile;
    StringBuilder actualOutput = new StringBuilder();
    try {
      readTestFile = new FileInputStream("res/ExportAllThroughController/CheckerboardExport"
          + "AllFromController.ppm");

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
      readTestFile = new FileInputStream("res/ExportAllThroughController/CheckerboardExport"
          + "AllFromController.png");
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

  // test for user inputting "import-all"
  @Test
  public void testImportAll() {
    this.str.append("import-all res/ExportAllTestFolder q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    this.checkerBoard = this.checkerBoard.filter(new Sepia());

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
    assertTrue(this.model.getLayers().get(0).getVisibility());

    assertEquals(checkerBoardAdjusted, this.model.getImageAt(1));
    assertTrue(this.model.getLayers().get(1).getVisibility());

    assertEquals(this.imageType.createImageRepresentation(3, 3, 1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE))),
        this.model.getImageAt(2));
    assertTrue(this.model.getLayers().get(2).getVisibility());
  }

  // test for user inputting "visible"
  @Test
  public void testVisible() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sepia save CheckerboardExportAllFromController.ppm invisible 2 "
        + "current 1 load res/ExportAllTestFolder/CheckerboardExportAllTest.png save "
        + "CheckerboardExportAllFromController.png  visible 2 export-all res/ExportAllThrough"
        + "Controller q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertTrue(this.model.getLayers().get(0).getVisibility());
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  // test for user inputting "invisible"
  @Test
  public void testInvisible() {
    this.str.append("create create current 2 load res/ExportAllTestFolder/CheckerboardExportAll"
        + "Test.png sepia save CheckerboardExportAllFromController.ppm invisible 2 current 1 "
        + "load res/ExportAllTestFolder/CheckerboardExportAllTest.png save CheckerboardExport"
        + "AllFromController.png export-all res/ExportAllThroughController q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertTrue(this.model.getLayers().get(0).getVisibility());
    assertFalse(this.model.getLayers().get(1).getVisibility());
  }

  // test for user inputting "checkerboard"
  @Test
  public void testCheckerboard() {
    this.str.append("checkerboard 3 3 1 q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals(this.checkerBoard, this.model.getCurrentImage());
  }

  // test for user inputting unknown command
  @Test
  public void testUnknownCommand() {
    this.str.append("rotate Q");
    this.rd = new CharArrayReader(this.str.toString().toCharArray());
    this.controller = new FilterController(this.model, this.rd, this.ap);
    this.view = new SimpleFilterView(this.ap);
    this.controller.runProgram();

    assertEquals("\n"
        + "Commands:\n"
        + "q or Q: quit\n"
        + "blur: blurs current image\n"
        + "sepia: applies sepia to current image\n"
        + "sharpen: sharpens current image\n"
        + "monochrome: applies monochrome to the current image\n"
        + "create: adds a new empty layer\n"
        + "current INT: int corresponds to the layer that we want to make visible and top\n"
        + "load FOLDER/.../FOLDER/FILENAME.FILETYPE: loads the file into the lowest empty layer\n"
        + "save FILENAME.FILETYPE: saves the current image and exports\n"
        + "export-all FOLDER/.../FOLDER/: exports all of the layers to the location with a "
        + "locations.txt file\n"
        + "import-all FOLDER/.../FOLDER/: imports all of the in the folder based off of the "
        + "locations.txt file in the specified folder\n"
        + "invisible INDEX(1 indexing): sets the layer at the index to invisible\n"
        + "visible INDEX(1 indexing): sets the layer at the index to visible\n"
        + "Unknown command rotate\n"
        + "The program was quit.\n", this.ap.toString());
  }
}
