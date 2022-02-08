package model.programmaticimages;

/**
 * The enumeration represents all of the colors our system has available, based off of a standard
 * rainbow: Red, orange, yellow, green, blue, purple, then with black and white additionally. All
 * of the enums have a r, g, and b field which houses their r, g, and b pixel color value
 * respectively. If we want to create a new color we simply add one with the correct pixel code
 * to the enumeration.
 */
public enum ColorEnum {
  WHITE(255,255, 255), BLACK(0,0,0), RED(255, 0, 0),
  ORANGE(255, 153, 0), YELLOW(255, 255,0), GREEN(0,255,0),
  BLUE(0,0,255), PURPLE(102, 0, 204);

  private final int r;
  private final int g;
  private final int b;

  /**
   * Constructor for the ColorEnum.
   *
   * @param r the value of the red pixel color field
   * @param g the value of the green pixel color field
   * @param b the value of the blue pixel color field
   */
  ColorEnum(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Returns the red value for this color.
   * @return an integer representing the red value for this color
   */
  public int getRed() {
    return this.r;
  }

  /**
   * Returns the green value for this color.
   * @return an integer representing the green value for this color
   */
  public int getGreen() {
    return this.g;
  }

  /**
   * Returns the blue value for this color.
   * @return an integer representing the blue value for this color
   */
  public int getBlue() {
    return this.b;
  }
}
