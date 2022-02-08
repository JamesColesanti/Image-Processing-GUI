package view;

import java.io.IOException;

/**
 * Represents the view of our program which will deal with displaying our program and sending any
 * output to our appendable.
 */
public interface IFilterView {

  /**
   * Renders the message to the Appendable of whatever string message is passed to the method.
   * The method deals with communicating to the user.
   *
   * @param message the message we want to print
   * @throws IOException if there is an issue with importing or exporting
   */
  void renderMessage(String message) throws IOException;
}
