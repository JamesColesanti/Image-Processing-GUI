package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ILayer;
import model.files.FileTypeCreator;
import model.filters.Blur;
import model.filters.Downscale;
import model.IComplexEffectModel;
import model.filters.IEffect;
import model.filters.IFilter;
import model.filters.IMosaic;
import model.filters.Monochrome;
import model.filters.Mosaic;
import model.filters.Sepia;
import model.filters.Sharpen;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import view.GUIView;

/**
 * The controller for the GUI which primarily acts as the action listener for our purposes of this
 * specific class implementation. From the action listener we use command line design to the carry
 * out the calls to the model and view as needed.
 */
public class GUIController implements ActionListener, IFilterController {
  private GUIView view;
  private IComplexEffectModel<ImageInterface> model;

  /**
   * Constructor, the model is passed and we will initialize the view in the constructor,
   * rather than pass since we are only every going to use the GUIVIEW with this instance of
   * GUIController.
   *
   * @param model the model in the GUIController.
   */
  public GUIController(IComplexEffectModel<ImageInterface> model) {
    super();
    this.view = new GUIView(model, this);
    this.model = model;
  }

  @Override
  public void runProgram() throws IllegalArgumentException {
    // run program is used for textual versions but in the GUI instance we do not cair for the
    // method
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "blur the image":
        this.filterClickedNotMosaic(new Blur());
        break;
      case "sharpen the image":
        this.filterClickedNotMosaic(new Sharpen());
        break;
      case "sepia the image":
        this.filterClickedNotMosaic(new Sepia());
        break;
      case "monochrome the image":
        this.filterClickedNotMosaic(new Monochrome());
        break;
      case "mosaic the image":
        this.mosaicHelp();
        break;
      case "downscale the image":
        this.downscaleHelp();
        break;
      case "remove clicked":
        this.model.removeLayer();
        break;
      case "create clicked":
        this.model.addLayer();
        break;
      case "current clicked":
        this.currentHelp();
        break;
      case "visible clicked":
        this.visibleHelp(true, "Enter the index of the layer you want to make visible.");
        break;
      case "invisible clicked":
        this.visibleHelp(false, "Enter the index of the layer you want to make invisible.");
        break;
      case "checkerboard clicked":
        this.checkerBoardClicker();
        break;
      case "open file":
        this.openFileHelp();
        break;
      case "open all files":
        this.openAllFilesHelp();
        break;
      case "name layer":
        this.nameLayerHelp();
        break;
      case "save the image":
        this.saveImageHelp();
        break;
      case "save all layers":
        this.saveAllLayerHelp();
        break;
      case "load script":
        this.loadScriptHelp();
        break;
      default:
        try {
          this.view.renderMessage("An unknown action was preformed");
        } catch (IOException e3) {
          e3.printStackTrace();
        }
    }
    this.view.createContentPane();
    this.view.revalidate();
    this.view.repaint();
  }


  /**
   * Helps perform the mosaic action after being called from the action listener.
   */
  private void mosaicHelp() {
    int seeds = this.receiveInt("Enter tile number of seeds", 0);
    IMosaic mosaic = new Mosaic();
    mosaic.initializeSeeds(seeds, this.model.getCurrentTopmostVisible().getImage(),
        new Random(), new Random());
    if (seeds != -1) {
      this.model.applyFilter(mosaic);
    }
  }

  /**
   * Helps perform the downscale action after being called from the action listener.
   */
  private void downscaleHelp() {
    int newWidth = this.receiveInt("Enter new width", 0);
    int newHeight = this.receiveInt("Enter new height", 0);
    IEffect downscale = new Downscale(newWidth, newHeight);
    this.model.applyComplexEffect(downscale);
  }

  /**
   * Helps perform the current layer action after being called from the action listener.
   */
  private void currentHelp() {
    int pile = this.receiveIndex("Enter the index of the layer you want to make current.");
    if (pile != -1) {
      this.model.updateImgToCurrentLayer(pile - 1);
    }
  }

  /**
   * Helps perform the visible layer action after being called from the action listener.
   */
  private void visibleHelp(boolean settingBool, String message) {
    int pile = this.receiveIndex(message);
    if (pile != -1) {
      this.model.adjustVisibility(pile - 1, settingBool);
    }
  }

  /**
   * Helps perform the open file action after being called from the action listener.
   */
  private void openFileHelp() {
    if (this.model.getLayers().size() <= 0) {
      JOptionPane.showMessageDialog(this.view, "There is no layer to load to.",
          "Warning", JOptionPane.WARNING_MESSAGE);
    }
    else {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
          "JPG", "jpg", "jpeg");
      fchooser.setFileFilter(filter2);
      filter2 = new FileNameExtensionFilter(
          "PNG", "png");
      fchooser.setFileFilter(filter2);
      filter2 = new FileNameExtensionFilter(
          "PPM", "ppm");
      fchooser.setFileFilter(filter2);
      int retvalue = fchooser.showOpenDialog(this.view);
      File f = null;
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        f = fchooser.getSelectedFile();
      }
      String name2 = "";
      if (f != null) {
        name2 = f.getName();
      }
      String fileType2 = fchooser.getFileFilter().getDescription().toLowerCase();
      this.model.save(name2);
      try {
        this.model.setImage(new ControllerUtil().importFile(
            // file type
            FileTypeCreator.createBasedOnString(fileType2),
            // name
            f.getAbsolutePath()));
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  /**
   * Helps to perform the open all files method call from the controller.
   */
  private void openAllFilesHelp() {
    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retvalue = fchooser.showOpenDialog(this.view);
    File f = null;
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
    }
    try {
      List<ILayer> layerList = new ControllerUtil().importAll(f.getAbsolutePath());
      this.model.setAllLayers(layerList);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  /**
   * Helps to perform the name layer method call from the controller.
   */
  private void nameLayerHelp() {
    this.model.save(this.view.inputDisplayHelper("Enter name of layer (name must end in"
        + "either .ppm, .jpg, .png to indicate file type)."));
  }

  /**
   * Helps to perform the save image method call from the controller.
   */
  private void saveImageHelp() {
    final JFileChooser fchooser2 = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG", "jpg");
    fchooser2.setFileFilter(filter);
    filter = new FileNameExtensionFilter("PNG", "png");
    fchooser2.setFileFilter(filter);
    filter = new FileNameExtensionFilter("PPM", "ppm");
    fchooser2.setFileFilter(filter);
    String fileName = "";
    int retvalue2 = fchooser2.showSaveDialog(this.view);
    if (retvalue2 == JFileChooser.APPROVE_OPTION) {
      File f = fchooser2.getSelectedFile();
      fileName = f.getAbsolutePath();
      this.model.save(f.getName());
    }
    String fileType = fchooser2.getFileFilter().getDescription().toLowerCase();

    new ControllerUtil().exportFile(
        // file type
        FileTypeCreator.createBasedOnString(fileType),
        // name
        fileName + "." + fileType,
        // image
        this.model.getCurrentTopmostVisible().getImage());
  }

  /**
   * Helps to perform the save all layers method call from the controller.
   */
  private void saveAllLayerHelp() {
    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retvalue = fchooser.showOpenDialog(this.view);
    File f = null;
    String folderName = "";
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
      folderName = f.getAbsolutePath();
    }

    new ControllerUtil().exportAll(folderName, this.model.getLayers());
  }

  /**
   * Helps to perform the load file method call from the controller.
   */
  private void loadScriptHelp() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
        "TXT", "txt");
    fchooser.setFileFilter(filter2);
    int retvalue = fchooser.showOpenDialog(this.view);
    File f = null;
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
    }
    try {
      new FilterController(this.model, new FileReader(f.getAbsolutePath()),
          System.out).runProgram();
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    }
  }

  /**
   * A private helper method uses for every filter other than mosaic. The method applies
   * the given filter to the current top most visible image and if there is no
   * top most visible image then we show a warning message.
   *
   * @param filter the filter we are using, the filter will not be mosaic
   */
  private void filterClickedNotMosaic(IFilter filter) {
    try {
      this.model.applyFilter(filter);
    }
    catch (NullPointerException e) {
      try {
        this.view.renderMessage("There is no image to filter");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  /**
   * Prompts the user for data about the checkerboard they want to create.
   */
  private void checkerBoardClicker() {
    int widthOfCheckerboard = this.receiveInt("Enter width of checkerboard", 1);
    if (widthOfCheckerboard != -1) {
      int heightOfCheckerboard = this.receiveInt("Enter height of checkerboard", 1);
      if (heightOfCheckerboard != -1) {
        int tileSizeOfCheckerboard = this.receiveInt("Enter tile size of checkerboard", 1);
        if (tileSizeOfCheckerboard != -1) {
          this.model.createImage(ProgrammaticImageType.CHECKER_BOARD,
              widthOfCheckerboard, heightOfCheckerboard, tileSizeOfCheckerboard,
              new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
        }
      }
    }
  }

  /**
   * Returns the int that the user inputs and the bound is the lower bound inclusive that the
   * user is allowed to input and the message is what is printed.
   *
   * @param message the message that is displayed for the input
   * @param bound the lower bound of the int input that we ask the user for
   * @return the int that the user inputted, or if the input is not valid then -1 is returned
   */
  private int receiveInt(String message, int bound) {
    int pile = -1;
    try {
      pile = Integer.parseInt(this.view.inputDisplayHelper(message));
      if (pile >= bound) {
        return pile;
      } else {
        throw new IllegalArgumentException("The number was 0 or less");
      }
    } catch (IllegalArgumentException e1) {
      try {
        this.view.renderMessage("Invalid input");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return -1;
  }

  /**
   * Creates an input window and gets index input such that the smallest value is 1 and the
   * largest is the number of layers in the multilayer image.
   *
   * @param message the message to be produced if the input is invalid
   * @return the index inputted by the user, if the input is invalid then -1 is returned
   */
  private int receiveIndex(String message) {
    try {
      return this.validPile(this.view.inputDisplayHelper(message));
    } catch (IllegalArgumentException e1) {
      JOptionPane
          .showMessageDialog(this.view,
              "Invalid input", "Warning",
              JOptionPane.WARNING_MESSAGE);
    }
    return -1;
  }

  /**
   * Confirms that the user inputted a valid pile input.
   *
   * @param pileInStr the user input
   * @return -1 if the user input is invalid ie: not a number or the number is not a valid pile,
   *     else the inputted number
   * @throws IllegalArgumentException if the user input is not a number or of invalid range
   */
  private int validPile(String pileInStr) throws IllegalArgumentException {
    int ret = -1;
    try {
      ret = Integer.parseInt(pileInStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The input was invalid.");
    }
    if (ret < 1 || ret > this.model.getLayers().size()) {
      throw new IllegalArgumentException("The input was invalid.");
    }
    return ret;
  }
}
