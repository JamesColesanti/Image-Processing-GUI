package model.filters;

/**
 * Represents a Posn which has an x and y coordinate and also a seed in this Posn.
 */
public class Posn implements IPosn {

  private final int x;
  private final int y;
  private ISeed seed;

  /**
   * Constructor.
   *
   * @param x the x coordinate of this Posn
   * @param y the Y coordinate of this Posn
   * @param seed the Seed in this Posn
   */
  public Posn(int x, int y, ISeed seed) {
    this.x = x;
    this.y = y;
    this.seed = seed;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public ISeed getSeed() {
    return this.seed;
  }

  /**
   * Overrides equals to return true if two Posns have the same x and y coordinates.
   * @param other the other Object that this Posn is being compared to
   * @return true if the other object is a posn and has the same x and y coordinates as this Posn
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    } else if (!(other instanceof Posn)) {
      return false;
    }
    IPosn o = (Posn) other;
    return this.x == o.getX() && this.y == o.getY();
  }

  /**
   * Overrides hashCode to return the same hashCode for two Posns that are equal based on their x
   * and y coordinates.
   * @return the hashCode for this Posn
   */
  @Override
  public int hashCode() {
    return x + y;
  }
}
