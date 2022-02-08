package model.filters;

import java.util.List;
import model.image.ImageInterface;

/**
 * The IFilter interface allows the system to have one method newColorValsAt which returns an
 * Arraylist of the new Red, Green, and Blue pixel value at the index indicated by the parameters .
 * The interface allows for new filters to easily be added by simply implementing IFilter than
 * implementing the newColorValsAt method accordingly to the specific filter case. We made this
 * design decision to let dynamic dispatch de-clutter and abstract our code.
 */
public interface IFilter {

  /**
   * Returns a list of three integers where the first item is the new Red value, followed by the new
   * Green and Blue value at the (x,y) pixel index. The method allows for all of the filters to
   * preform their respective pixel filtering then return the same format of new Red, new Green, and
   * new Blue so we are able to delegate the individual filtering act to the class whose name sake
   * is that of its respective filter.
   *
   * @param x   the x coordinate of the pixel location we want to filter
   * @param y   the y coordinate of the pixel location we want to filter
   * @param img the img we are filtering
   * @return an array list where the first integer is the new Red pixel value at (x,y) after filter,
   *         followed by the new Blue pixel value at (x,y), then finally the new Green pixel
   *         value at (x,y)
   * @throws IllegalArgumentException if img is null or if the x and y are out of range
   */
  List<Integer> newColorValsAt(int x, int y, ImageInterface img)
      throws IllegalArgumentException;

}
