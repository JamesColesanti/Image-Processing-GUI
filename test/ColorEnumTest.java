import static org.junit.Assert.assertEquals;

import model.programmaticimages.ColorEnum;
import org.junit.Test;

/**
 * Test class for ColorEnum. This class tests that when attempting to use its color enumerations,
 * the correct color values are passed.
 */
public class ColorEnumTest {

  // tests that getRed works for all colors
  @Test
  public void getRedAllColor() {
    assertEquals(255, ColorEnum.WHITE.getRed());
    assertEquals(0, ColorEnum.BLUE.getRed());
    assertEquals(255, ColorEnum.RED.getRed());
    assertEquals(255, ColorEnum.ORANGE.getRed());
    assertEquals(255, ColorEnum.YELLOW.getRed());
    assertEquals(0, ColorEnum.GREEN.getRed());
    assertEquals(0, ColorEnum.BLUE.getRed());
    assertEquals(102, ColorEnum.PURPLE.getRed());
  }

  // tests that getGreen works for all colors
  @Test
  public void getGreenAllColor() {
    assertEquals(255, ColorEnum.WHITE.getGreen());
    assertEquals(0, ColorEnum.BLUE.getGreen());
    assertEquals(0, ColorEnum.RED.getGreen());
    assertEquals(153, ColorEnum.ORANGE.getGreen());
    assertEquals(255, ColorEnum.YELLOW.getGreen());
    assertEquals(255, ColorEnum.GREEN.getGreen());
    assertEquals(0, ColorEnum.BLUE.getGreen());
    assertEquals(0, ColorEnum.PURPLE.getGreen());
  }

  // tests that getBlue works for all colors
  @Test
  public void getBlueAllColor() {
    assertEquals(255, ColorEnum.WHITE.getBlue());
    assertEquals(255, ColorEnum.BLUE.getBlue());
    assertEquals(0, ColorEnum.RED.getBlue());
    assertEquals(0, ColorEnum.ORANGE.getBlue());
    assertEquals(0, ColorEnum.YELLOW.getBlue());
    assertEquals(0, ColorEnum.GREEN.getBlue());
    assertEquals(255, ColorEnum.BLUE.getBlue());
    assertEquals(204, ColorEnum.PURPLE.getBlue());
  }
}