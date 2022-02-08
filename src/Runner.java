import controller.FilterController;
import controller.GUIController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import model.FilterModel;
import model.IComplexEffectModel;

/**
 * This class contains the main method and executes the program.
 */
public class Runner {

  /**
   * Main method used to run the application manually.
   *
   * @param args the input from the user
   */
  public static void main(String[] args) {

    String command = args[0];

    IComplexEffectModel model = new FilterModel(new ArrayList<>());
    if (args[0].equals("-script")) {
      try {
        new FilterController(model, new FileReader(args[1]), System.out).runProgram();
        System.exit(0);
      } catch (FileNotFoundException e) {
        System.out.print("Invalid file given.");
      }
    } else if (command.equals("-text")) {
      try {
        new FilterController(model, new InputStreamReader(System.in), System.out).runProgram();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
    } else if (command.equals("-interactive")) {
      GUIController gui = new GUIController(model);
    } else {
      System.out.print("Invalid command given.");
      System.exit(0);
    }


  }
}

