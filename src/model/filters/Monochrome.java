package model.filters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the monochrome filter. The monochrome filter will result in a grey scale looking
 * image. The class contains a method called apply method which is a design choice such that adding
 * new filters means creating a new class which implements IFilter instead of crowding the model
 * class.
 */
public class Monochrome extends AbstractColorFilter {
  /**
   * Constructor for the monochrome class.
   */
  public Monochrome() {
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
        new ArrayList<>(Arrays.asList(.2126,.2126,.2126)),
        new ArrayList<>(Arrays.asList(.7152, .7152, .7152)),
        new ArrayList<>(Arrays.asList(.0722, .0722, .0722))));
  }
}
