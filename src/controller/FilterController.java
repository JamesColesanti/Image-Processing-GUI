package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import model.FilterModel;
import model.IComplexEffectModel;
import model.IFilterModel;
import model.ILayer;
import model.files.FileTypeCreator;
import model.files.IFileType;
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
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import view.IFilterView;
import view.SimpleFilterView;

/**
 * The controller class will deal with deciphering users input and then decided which methods to
 * call in the model. The model will act like a blind method machine and then the view field of
 * the controller will be used to relay out to the user.
 */
public class FilterController implements IFilterController<ImageInterface> {

  private IComplexEffectModel model;
  private IFilterView view;
  private Scanner scan;

  /**
   * Constructor.
   *
   * @param model the model in the controller
   * @param rd the readable which will be used to define the scanner field
   * @param ap the appendable field which will be used to define the view field
   */
  public FilterController(IComplexEffectModel model, Readable rd, Appendable ap) {
    this.model = model;
    this.scan = new Scanner(rd);
    this.view = new SimpleFilterView(ap);
  }

  @Override
  public void runProgram() {
    try {
      this.view.renderMessage("\nCommands:\n"
          + "q or Q: quit\n"
          + "blur: blurs current image\n"
          + "sepia: applies sepia to current image\n"
          + "sharpen: sharpens current image\n"
          + "monochrome: applies monochrome to the current image\n"
          + "mosaic: applies mosaic to the current image\n"
          + "downscale: downscales all layers\n"
          + "create: adds a new empty layer\n"
          + "current INT: int corresponds to the layer that we want to make visible and top\n"
          + "load FOLDER/.../FOLDER/FILENAME.FILETYPE: loads the file into the lowest empty layer\n"
          + "save FILENAME.FILETYPE: saves the current image and exports\n"
          + "export-all FOLDER/.../FOLDER/: exports all of the layers to the location with a "
          + "locations.txt file\n"
          + "import-all FOLDER/.../FOLDER/: imports all of the in the folder based off of the "
          + "locations.txt file in the specified folder\n"
          + "invisible INDEX(1 indexing): sets the layer at the index to invisible\n"
          + "visible INDEX(1 indexing): sets the layer at the index to visible");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    while (this.scan.hasNext()) {
      String in = this.scan.next();
      System.out.println(in);
      try {
        // purpose of switch: create the appropriate command object based on the given user input
        switch (in.toLowerCase()) {
          case "q":
            this.view.renderMessage("The program was quit.");
            return;
          case "Q":
            this.view.renderMessage("The program was quit.");
            return;
          case "blur":
            this.model.applyFilter(new Blur());
            break;
          case "sepia":
            this.model.applyFilter(new Sepia());
            break;
          case "sharpen":
            this.model.applyFilter(new Sharpen());
            break;
          case "monochrome":
            this.model.applyFilter(new Monochrome());
            break;
          case "mosaic":
            int seeds = this.currentHelper(0);
            IMosaic mosaic = new Mosaic();
            mosaic.initializeSeeds(seeds, this.model.getCurrentImage(), new Random(), new Random());
            this.model.applyFilter(mosaic);
            break;
          case "downscale":
            int newWidth = this.currentHelper(0);
            int newHeight = this.currentHelper(0);
            IEffect downscale = new Downscale(newWidth, newHeight);
            this.model.applyComplexEffect(downscale);
            break;
          case "create":
            this.model.addLayer();
            break;
          case "remove":
            this.model.removeLayer();
            break;
          case "current":
            int current = this.currentHelper(1);
            if (current == -2) {
              return;
            }
            this.model.updateImgToCurrentLayer(current);
            break;
          case "load":
            String importFile = this.scan.next();
            int periodInd = importFile.indexOf(".");
            IFileType importFileType = FileTypeCreator.createBasedOnString(importFile.substring(
                periodInd + 1));
            ImageInterface imgToBeSet = new Image(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());
            try {
              imgToBeSet = new ControllerUtil().importFile(importFileType, importFile);
            } catch (FileNotFoundException ex) {
              this.view.renderMessage(String.format("File could not be found.", in));
            }
            this.model.setImage(imgToBeSet);
            break;
          case "save":
            String exportFileName = this.scan.next();
            periodInd = exportFileName.indexOf(".");
            IFileType exportFileType = FileTypeCreator.createBasedOnString(exportFileName.substring(
                periodInd + 1));
            this.model.save(exportFileName);
            try {
              new ControllerUtil().exportFile(exportFileType, exportFileName,
                  this.model.getCurrentTopmostVisible().getImage());
            } catch (IllegalArgumentException ex) {
              this.view.renderMessage(String.format("File could not be saved.", in));
            }
            break;
          case "export-all":
            String folderNameExport = "";
            if (this.scan.hasNext()) {
              folderNameExport = this.scan.next();
            }
            else {
              this.view.renderMessage("There was no next.");
            }
            new ControllerUtil().exportAll(folderNameExport, this.model.getLayers());
            break;
          case "import-all":
            String folderNameImport = "";
            if (this.scan.hasNext()) {
              folderNameImport = this.scan.next();
            }
            else {
              this.view.renderMessage("There was no next.");
            }
            List<ILayer> layers = new ControllerUtil().importAll(folderNameImport);
            this.model = new FilterModel(layers);
            break;
          case "invisible":
            current = this.currentHelper(1);
            if (current == -2) {
              return;
            }
            this.model.adjustVisibility(current, false);
            break;
          case "visible":
            current = this.currentHelper(1);
            if (current == -2) {
              return;
            }
            this.model.adjustVisibility(current, true);
            break;
          case "checkerboard":
            current = this.currentHelper(0);
            if (current == -2) {
              this.view.renderMessage("The program was quit.");
              return;
            }
            int width = current;
            int height = this.currentHelper(0);
            int tileSize = this.currentHelper(0);
            this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, width, height, tileSize,
                new ArrayList<ColorEnum>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
            break;
          default:
            this.view.renderMessage(String.format("Unknown command %s", in));
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Returns the int that was passed directly after the command which initiated the call
   * of this method. And checks if the next item is a indicating a quit case we return -2.
   *
   * @return the integer version of the next item in the scanner
   * @throws IOException if there is an error during the input or output process
   */
  private int currentHelper(int adjuster) throws IOException {
    String next = "";
    try {
      next = this.scan.next();
      if (next.equals("q") || next.equals("Q")) {
        return -2;
      }
      return Integer.parseInt(next) - adjuster;
      // the number of the layer is now in 0 indexing
    } catch (NumberFormatException e) {
      this.view.renderMessage("The number passed as the current was not an integer");
    }
    return -1;
  }
}

