package model.filters;

/**
 * Represents the interface for a Posn which is an X Y coordinate association with a seed,
 * the interface allows us to make method signatures clear and allow later functionality of
 * Posn.
 */
public interface IPosn {

  /**
   * Gets the x coordinate of this Posn.
   *
   * @return the x coordinate of this Posn
   */
  int getX();

  /**
   * Gets the y coordinate of this Posn.
   *
   * @return the y coordinate of this Posn
   */
  int getY();

  /**
   * Gets the Seed in this Posn.
   *
   * @return the Seed in this Posn
   */
  ISeed getSeed();
}
