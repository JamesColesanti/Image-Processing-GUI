package model;

import model.filters.IEffect;

/**
 * The interface is used to allow us to add the new functionality to the existing model interface
 * without changing our old implementation. This is a design choice to mitigate extensive change to
 * existing interface while functionality performing the same.
 */
public interface IComplexEffectModel<K> extends IFilterModel<K> {

  /**
   * Applies the effect on this single or multi layer image model representation.
   *
   * @param effect the effect we are applying to the multi or single layered image
   */
  void applyComplexEffect(IEffect effect);

}
