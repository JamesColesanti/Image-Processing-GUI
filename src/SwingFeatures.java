import controller.GUIController;
import java.util.ArrayList;
import model.FilterModel;
import model.IComplexEffectModel;
import model.image.ImageInterface;


/**
 * This example shows the different user interface elements in Java Swing.
 * Please use these examples as guidelines only to see how to use them. This
 * example has not been designed very well, it is only meant to illustrate code
 * snippets.
 *
 * Feel free to try out different modifications to see how the program changes
 */

public class SwingFeatures {

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  private static void createAndShowGUI() {
    //Create and set up the content pane.
    IComplexEffectModel<ImageInterface> model = new FilterModel(new ArrayList<>());
    GUIController gui = new GUIController(model);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}
