package model.filters;

import java.util.Comparator;

/**
 * Used to compare this seed to another seed and determine which is first based off of the x y
 * coordinate of the seed.
 */
public class SeedComparator implements Comparator<ISeed> {

  private int width;

  /**
   * Constructor.
   *
   * @param width the width of the image this will be used in our comparison calculation
   */
  public SeedComparator(int width) {
    this.width = width;
  }

  /**
   * Compares the two given seeds based on the following computation: x-value + y-value * width of
   * image.
   * @param o1 the first seed that is being compared
   * @param o2 the second seed that is being compared
   * @return -1 if o1 has a lower computation value, 1 if o2 has a lower computation value, or 0
   * if the computation values are equal
   */
  @Override
  public int compare(ISeed o1, ISeed o2) {
    int o1Val = o1.getXOfSeed() + o1.getYOfSeed() * width;
    int o2Val = o2.getXOfSeed() + o2.getYOfSeed() * width;
    if (o1Val < o2Val) {
      return -1;
    } else if (o1Val > o2Val) {
      return 1;
    } else {
      return 0;
    }
  }
}
