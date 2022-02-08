package view;

/**
 * The IGUIFunctionality extends the existing functionality of IFilterView and adds additional
 * functionality without changing the old implementation. This new method is GUI specific so
 * should not be in the more general IFilterView interface thus we make the design choice to
 * create a new interface.
 */
public interface IGUIFunctionality extends IFilterView {

  /**
   * Creates the content pane of the GUI.
   */
  void createContentPane();

  /**
   * Helps to display a message and return what the user inputs.
   *
   * @param message the message to be displayed
   * @return the input from the user after being prompted with the message
   */
  String inputDisplayHelper(String message);

}
