package model;

import java.util.ArrayList;
import java.util.List;
import model.filters.Blur;
import model.filters.Downscale;
import model.filters.IEffect;
import model.filters.IFilter;
import model.filters.Monochrome;
import model.filters.Mosaic;
import model.filters.Sepia;
import model.filters.Sharpen;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;

/**
 * Represents a mock model for testing purposes.
 */
public class MockModel implements IComplexEffectModel<ImageInterface> {
  private StringBuilder log = new StringBuilder();

  @Override
  public void applyFilter(IFilter filter) throws IllegalArgumentException {
    if (filter instanceof Blur) {
      log.append("Method Called: applyFilter(); Filter: Blur");
    }
    else if (filter instanceof Downscale) {
      log.append("Method Called: applyFilter(); Filter: Downscale");
    }
    else if (filter instanceof Monochrome) {
      log.append("Method Called: applyFilter(); Filter: Monochrome");
    }
    else if (filter instanceof Mosaic) {
      log.append("Method Called: applyFilter(); Filter: Mosaic");
    }
    else if (filter instanceof Sepia) {
      log.append("Method Called: applyFilter(); Filter: Sepia");
    }
    else if (filter instanceof Sharpen) {
      log.append("Method Called: applyFilter(); Filter: Sharpen");
    }
  }

  @Override
  public void save(String name) {
    log.append(name);
  }

  @Override
  public void setImage(ImageInterface img) throws IllegalArgumentException {
    log.append(img.toString());
  }

  @Override
  public void createImage(ProgrammaticImageType imageTypeEnum, int width, int height, int tileSize,
      ArrayList<ColorEnum> colorSettings) throws IllegalArgumentException {

  }

  @Override
  public ImageInterface getCurrentImage() throws IllegalStateException {
    return null;
  }

  @Override
  public void addLayer() {
    log.append("Method Called: addLayer(); Params: N/A");
  }

  @Override
  public void removeLayer() {
    log.append("Method Called: removeLayer(); Params: N/A");
  }

  @Override
  public void updateImgToCurrentLayer(int current) throws IllegalArgumentException {
    log.append("Method Called: current; Params: Current: " + current);
  }

  @Override
  public List<ILayer> getLayers() {
    return null;
  }

  @Override
  public void setAllLayers(List<ILayer> layerList) throws IllegalArgumentException {

  }

  @Override
  public void adjustVisibility(int layerInd, boolean visibility) throws IllegalArgumentException {
    this.log.append("Method Called: adjustVisibility; Param: layerInd: " + layerInd +
        ", visibility: " + visibility);
  }

  @Override
  public ImageInterface getImageAt(int index) throws IllegalArgumentException {
    this.log.append("Method Called: getImageAt; Param: " + index);
    return null;
  }

  @Override
  public ILayer getCurrentTopmostVisible() throws IllegalStateException {
    this.log.append("Method Called: getCurrentTopmostVisible");
    return null;
  }

  @Override
  public String toString() {
    return this.log.toString();
  }

  @Override
  public void applyComplexEffect(IEffect effect) {
    if (effect instanceof Downscale) {
      this.log.append("Downscale");
    } else {
      this.log.append("null");
    }
  }
}
