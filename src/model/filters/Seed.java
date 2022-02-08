package model.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a randomly generated seed in a mosaic image. This seed contains a list of red,
 * green, and blue values which are all the pixel values that are in "range" of the seed as in
 * all of the values of the pixels that are closest to this seed. The seed also contains the
 * final value that should be for all of the pixels closest to this seed.
 */
public class Seed implements ISeed {

  private final int x;
  private final int y;
  private final List<Integer> redValues;
  private final List<Integer> greenValues;
  private final List<Integer> blueValues;
  private int finalRedVal;
  private int finalGreenVal;
  private int finalBlueVal;

  /**
   * Constructor.
   *
   * @param x the x coordinate of the seed
   * @param y the y coordinate of the seed
   */
  public Seed(int x, int y) {
    this.x = x;
    this.y = y;
    this.redValues = new ArrayList<>();
    this.greenValues = new ArrayList<>();
    this.blueValues = new ArrayList<>();
    this.finalRedVal = -1;
    this.finalGreenVal = -1;
    this.finalBlueVal = -1;
  }

  @Override
  public int getFinalRedVal() {
    return this.finalRedVal;
  }

  @Override
  public int getFinalGreenVal() {
    return this.finalGreenVal;
  }

  @Override
  public int getFinalBlueVal() {
    return this.finalBlueVal;
  }

  @Override
  public int getXOfSeed() {
    return this.x;
  }

  @Override
  public int getYOfSeed() {
    return this.y;
  }

  @Override
  public void addRed(int newRed) {
    this.redValues.add(newRed);
  }

  @Override
  public void addGreen(int newGreen) {
    this.greenValues.add(newGreen);
  }

  @Override
  public void addBlue(int newBlue) {
    this.blueValues.add(newBlue);
  }

  @Override
  public void setAverages() {
    int redSum = 0;
    int greenSum = 0;
    int blueSum = 0;
    int totalVals = this.redValues.size();
    if (this.redValues.size() != 0) {
      for (int i = 0; i < totalVals; i++) {
        redSum += this.redValues.get(i);
        greenSum += this.greenValues.get(i);
        blueSum += this.blueValues.get(i);
      }
      this.finalRedVal = redSum / totalVals;
      this.finalGreenVal = greenSum / totalVals;
      this.finalBlueVal = blueSum / totalVals;
    }
  }
}
