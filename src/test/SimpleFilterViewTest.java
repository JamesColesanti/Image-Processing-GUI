import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import view.IFilterView;
import view.SimpleFilterView;

/**
 * Test class for the SimpleFilterView class.
 */
public class SimpleFilterViewTest {

  IFilterView testView;
  Appendable testAp;

  @Before
  public void initData() {
    testAp = new StringBuilder();
    testView = new SimpleFilterView(testAp);
  }

  /*
  Constructor tests
   */

  // tests that an exception is thrown when a null appendable is passed
  @Test(expected = IllegalArgumentException.class)
  public void nullConstructorParam() {
    testView = new SimpleFilterView(null);
  }


  /*
  Tests for renderMessage
   */

  // tests that an exception is thrown when a null string is passed
  @Test(expected = IllegalArgumentException.class)
  public void renderMessageNullMessage() {
    try {
      testView.renderMessage(null);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  // tests that a message is successfully outputted
  @Test
  public void validMessageToString() {
    try {
      testView.renderMessage("The message was received");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(this.testAp.toString().equals("The message was received\n"));
  }

  // tests that an empty string is successfully outputted
  @Test
  public void emptyStringValidMessageToString() {
    try {
      testView.renderMessage("");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(this.testAp.toString().equals("\n"));
  }

  // tests that an IOException is thrown
  @Test
  public void ioExceptionView() {
    this.testAp = new IOExceptionSimulation();
    this.testView = new SimpleFilterView(this.testAp);
    try {
      this.testView.renderMessage("Test");
    } catch (IOException e) {
      assertEquals("Attempt to render message failed.", e.getLocalizedMessage());
    }
  }
}