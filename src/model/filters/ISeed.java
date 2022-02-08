package model.filters;

/**
 * Represents the interface for a seed which is a randomly generated point in a mosaic image,
 * the interface allows us to make method signatures clear and allow later functionality of
 * seed.
 */
public interface ISeed {

  /**
   * Returns the final red val of the seed.
   *
   * @return the final red val of the seed
   */
  int getFinalRedVal();

  /**
   * Returns the final green val of the seed.
   *
   * @return the final green val of the seed
   */
  int getFinalGreenVal();

  /**
   * Returns the final blue val of the seed.
   *
   * @return the final blue val of the seed
   */
  int getFinalBlueVal();

  /**
   * Returns the x coordinate of the seed.
   *
   * @return x coordinate of the seed
   */
  int getXOfSeed();

  /**
   * Returns the y coordinate of the seed.
   *
   * @return y coordinate of the seed
   */
  int getYOfSeed();

  /**
   * Adds the red value to the list of blue values.
   *
   * @param newRed the red value to be added to the list
   */
  void addRed(int newRed);

  /**
   * Adds the green value to the list of blue values.
   *
   * @param newGreen the green value to be added to the list
   */
  void addGreen(int newGreen);

  /**
   * Adds the blue value to the list of blue values.
   *
   * @param newBlue the blue value to be added to the list
   */
  void addBlue(int newBlue);

  /**
   * Sets the final r, g, and b values by taking the average values of the items in the list.
   */
  void setAverages();



}
