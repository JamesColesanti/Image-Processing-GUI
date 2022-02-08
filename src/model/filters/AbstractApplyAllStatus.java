package model.filters;

public abstract class AbstractApplyAllStatus implements IEffect {

  protected boolean applyToAll;

  @Override
  public boolean determineApplyToAllStatus() {
    return this.applyToAll;
  }
}
