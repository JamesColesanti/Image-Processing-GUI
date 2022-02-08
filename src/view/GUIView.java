package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import model.IFilterModelState;
import model.image.ImageInterface;

/**
 * The GUI view creates our interactive GUI which can be used to interact with our system and
 * perform image editing functionality, filtering, and image exporting and importing with different
 * file types.
 */
public class GUIView extends JFrame implements IGUIFunctionality {
  //show an image with a scrollbar
  private JPanel imagePanel;
  private JLabel imageLabel;
  private IFilterModelState<ImageInterface> model;

  /**
   * Constructs a GUIView that displays information based on the given model. The given
   * ActionListener is set as the ActionListener for all buttons in the GUI.
   *
   * @param model the view-model for the view so that we can deal with strictly observing model
   *              functionality
   * @param actionListener the ActionListener for all buttons in this GUI
   */
  public GUIView(IFilterModelState<ImageInterface> model, ActionListener actionListener) {
    super();
    this.model = model;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Display the window.
    this.setSize(1000, 1000);
    this.setVisible(true);

    setTitle("Image Editing Program");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setJMenuBar(this.createMenuBar(actionListener));
    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);

    this.imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane();

    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(100, 600));
    imagePanel.add(imageScrollPane);
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));

    this.createContentPane();
  }

  @Override
  public void createContentPane() {
    if (this.model.getCurrentTopmostVisible() != null
        && this.model.getCurrentTopmostVisible().getImage() != null) {
      this.imagePanel.setBorder(BorderFactory.createTitledBorder(
          this.model.getCurrentTopmostVisible().getFileName()));
      ImageInterface img = this.model.getCurrentTopmostVisible().getImage();

      BufferedImage bufferedImage = new BufferedImage(img.getImageWidth(), img.getImageHeight(),
          BufferedImage.TYPE_INT_RGB);

      // fill the rows with the data at the indicated location of (x,y)
      for (int x = 0; x < img.getImageWidth(); x++) { // rows
        for (int y = 0; y < img.getImageHeight(); y++) { // "columns"
          int redAt = img.getRedAt(x, y);
          int greenAt = img.getGreenAt(x, y);
          int blueAt = img.getBlueAt(x, y);
          bufferedImage.setRGB(x, y, new Color(redAt, greenAt, blueAt).getRGB());
        }
      }

      this.imageLabel.setIcon(new ImageIcon(bufferedImage));

    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Helps to create a JMenuItem.
   *
   * @param nameOfMenuItem the name of the JMenuItem
   * @param description    the accessible description of the JMenuItem
   * @param actionCommand  the action command of the JMenuItem
   * @return the JMenuItem we created
   */
  private JMenuItem createJMenuItem(String nameOfMenuItem, String description,
      String actionCommand, ActionListener actionListener) {
    JMenuItem menuItem = new JMenuItem(nameOfMenuItem);
    menuItem.getAccessibleContext().setAccessibleDescription(description);
    menuItem.setActionCommand(actionCommand);
    menuItem.addActionListener(actionListener);
    return menuItem;
  }

  /**
   * Helps to create a JMenu item.
   *
   * @param nameOfMenu    the name of the JMenu item
   * @param actionCommand the action command that should be set to the JMenu
   * @param description   the accessible description of the JMenu
   * @return the JMenu we wish to create based off of the parameters passed
   */
  private JMenu createJMenu(String nameOfMenu, String actionCommand, String description) {
    JMenu menu = new JMenu(nameOfMenu);
    menu.setActionCommand(actionCommand);
    menu.getAccessibleContext().setAccessibleDescription(description);
    return menu;
  }

  /**
   * Creates the JMenu bar.
   *
   * @param actionListener the action listener that we will assign to all of the JMenuItems that we
   *                       make
   * @return the JMenuBar for the GUI
   */
  private JMenuBar createMenuBar(ActionListener actionListener) {
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    //Create the menu bar.
    menuBar = new JMenuBar();

    // effect menu
    menu = this.createJMenu("Effects", "Effect clicked",
        "The effect menu.");
    menuBar.add(menu);

    // downscale button
    menuItem = this.createJMenuItem("Downscale",
        "This applies the downscale feature to all layers.",
        "downscale the image", actionListener);
    menu.add(menuItem);

    // filter menu
    menu = this.createJMenu("Filters", "Filter clicked",
        "The filter menu.");
    menuBar.add(menu);

    // blur button
    menuItem = this.createJMenuItem("Blur",
        "This blurs the to the top most visible layer.", "blur the image",
        actionListener);
    menu.add(menuItem);

    // sharpen button
    menuItem = this.createJMenuItem("Sharpen",
        "This sharpens the to the top most visible layer.",
        "sharpen the image", actionListener);
    menu.add(menuItem);

    // sepia button
    menuItem = this.createJMenuItem("Sepia",
        "This applies the sepia filter to the top most visible layer.",
        "sepia the image", actionListener);
    menu.add(menuItem);

    // monochrome button
    menuItem = this.createJMenuItem("Monochrome",
        "This applies the monochrome filter to the top most visible layer.",
        "monochrome the image",
        actionListener);
    menu.add(menuItem);

    // mosaic button
    menuItem = this.createJMenuItem("Mosaic",
        "This applies the mosaic filter to the top most visible layer.",
        "mosaic the image", actionListener);
    menu.add(menuItem);

    // Load actions menu
    menu = this.createJMenu("Load Actions", "Load Actions clicked",
        "The load actions menu.");
    menuBar.add(menu);

    //a group of JMenuItems
    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    JMenuItem fileOpenButton = new JMenuItem("Load");
    fileOpenButton.setActionCommand("open file");
    fileOpenButton.addActionListener(actionListener);
    fileOpenPanel.add(fileOpenButton);
    JLabel fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenPanel.add(fileOpenDisplay);
    menu.add(fileOpenButton);

    //a group of JMenuItems
    //file open
    JPanel fileOpenAllPanel = new JPanel();
    fileOpenAllPanel.setLayout(new FlowLayout());
    JMenuItem fileOpenAllButton = new JMenuItem("Load All");
    fileOpenAllButton.setActionCommand("open all files");
    fileOpenAllButton.addActionListener(actionListener);
    fileOpenAllPanel.add(fileOpenAllButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenAllPanel.add(fileOpenDisplay);
    menu.add(fileOpenAllButton);

    //a group of JMenuItems
    //file open
    JPanel fileLoadScriptPanel = new JPanel();
    fileOpenAllPanel.setLayout(new FlowLayout());
    JMenuItem fileLoadScriptButton = new JMenuItem("Load Script");
    fileLoadScriptButton.setActionCommand("load script");
    fileLoadScriptButton.addActionListener(actionListener);
    fileLoadScriptPanel.add(fileLoadScriptButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileLoadScriptPanel.add(fileOpenDisplay);
    menu.add(fileLoadScriptButton);

    // Save actions menu
    menu = this.createJMenu("Save Actions", "Save clicked",
        "The save actions menu.");
    menuBar.add(menu);

    // name a layer button
    menuItem = this.createJMenuItem("Name Layer",
        "Gives a name to the topmost visible layer", "name layer",
        actionListener);
    menu.add(menuItem);

    // save button
    menuItem = this.createJMenuItem("Save",
        "This saves an image", "save the image", actionListener);
    menu.add(menuItem);

    // save all button
    menuItem = this.createJMenuItem("Save all",
        "This saves a folder of images to a folder.", "save all layers", actionListener);
    menu.add(menuItem);

    // Layer actions menu
    menu = this.createJMenu("Layer Actions", "Layer Actions clicked",
        "The layer actions menu.");
    menuBar.add(menu);

    // invisible button
    menuItem = this.createJMenuItem("Invisible",
        "This invisible loads an image.", "invisible clicked", actionListener);
    menu.add(menuItem);

    // visible button
    menuItem = this.createJMenuItem("Visible",
        "This makes a layer visible.", "visible clicked", actionListener);
    menu.add(menuItem);

    // current button
    menuItem = this.createJMenuItem("Current",
        "This sets a layer to the current layer.", "current clicked", actionListener);
    menu.add(menuItem);

    // create button
    menuItem = this.createJMenuItem("Create",
        "This creates a layer.", "create clicked", actionListener);
    menu.add(menuItem);

    // remove button
    menuItem = this.createJMenuItem("Remove",
        "This removes a layer.", "remove clicked", actionListener);
    menu.add(menuItem);

    // Programmatic actions menu
    menu = this.createJMenu("Programmatic", "Programmatic clicked",
        "The programmatic actions menu.");
    menuBar.add(menu);

    // checkerboard button
    menuItem = this.createJMenuItem("Checkerboard",
        "This creates a checkerboard image.", "checkerboard clicked",
        actionListener);
    menu.add(menuItem);

    return menuBar;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(this, message, "Warning",
        JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public String inputDisplayHelper(String message) {
    JLabel inputDisplay = new JLabel();
    inputDisplay.setText(JOptionPane.showInputDialog(message));
    return inputDisplay.getText();
  }
}
