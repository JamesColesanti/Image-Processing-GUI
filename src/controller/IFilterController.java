package controller;

/**
 * The controller interface which allows the program to be dynamic and work with different
 * types of controllers that we make rather than be class dependent.
 * @param <K> for potential future additions for the view to observe the images or other
 *     possible additional functionality
 */
public interface IFilterController<K> {

  /**
   * The go method executes and runs the program compiling the controller model view
   * configuration and beginning the textual interactions. capability.
   */
  void runProgram() throws IllegalArgumentException;
}
