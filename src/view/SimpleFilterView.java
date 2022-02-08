package view;

import java.io.IOException;

/**
 * Represents our simple filter program view where as of the simple version we are only rendering
 * a message, hence the simple nature of our view class.
 */
public class SimpleFilterView implements IFilterView {
  private Appendable ap;

  /**
   * Constructor.
   *
   * @param ap the appendable field
   */
  public SimpleFilterView(Appendable ap) {
    if (ap == null) {
      throw new IllegalArgumentException("Null parameter was passed.");
    }
    this.ap = ap;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("Null parameter was passed.");
    }
    try {
      this.ap.append(message + "\n");
    }
    catch (IOException ex) {
      throw new IOException("Attempt to render message failed.");
    }
  }
}

