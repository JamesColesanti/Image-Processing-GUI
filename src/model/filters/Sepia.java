package model.filters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the sepia filter. The sepia filter will result in a reddish brown tone similar to
 * old 19th and early 20th century photography. The class contains a method called apply method
 * which is a design choice such that adding new filters means creating a new class which
 * implements IFilter instead of crowding the model class.
 */
public class Sepia extends AbstractColorFilter {

  /**
   * Constructor for the Sepia class.
   */
  public Sepia() {
    this.transpose = this.setTranspose();
  }

  /**
   * Sets the transpose field to the appropriate transpose matrix to apply a certain filter.
   * Extending classes must implement this method to return this matrix.
   *
   * @return a transpose matrix used to apply a color filter
   */
  @Override
  protected ArrayList<ArrayList<Double>> setTranspose() {
    return new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(.393, .349, .272)),
        new ArrayList<>(Arrays.asList(.769, .686, .534)),
        new ArrayList<>(Arrays.asList(.189, .168, .131))));
  }
}
