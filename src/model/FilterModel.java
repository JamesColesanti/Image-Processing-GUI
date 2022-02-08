package model;

import java.util.ArrayList;
import java.util.List;
import model.filters.IEffect;
import model.filters.IFilter;
import model.image.ImageInterface;
import model.programmaticimages.ColorEnum;
import model.programmaticimages.ImageCreatorInterface;
import model.programmaticimages.ProgrammaticImageCreator;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;

/**
 * Represents the  model class that will deal with all the functionality in our program, blind to
 * when it is called and how it is used. Filter Model has an image field representing the current
 * image the user is working on, as well as a Stack field that holds the original image as well
 * as any new images that are produced as modifications to this original image.
 */
public class FilterModel implements IComplexEffectModel<ImageInterface> {

  private ImageInterface currentImage;
  private List<ILayer> imageHistory;
  private int currLayer;

  /**
   * Constructor.
   *
   * @param imageHistory the image history of this model
   */
  public FilterModel(List<ILayer> imageHistory) {
    if (imageHistory == null) {
      throw new IllegalArgumentException("The image history can not be null.");
    }
    this.imageHistory = imageHistory;
    this.currLayer = 0;
  }

  @Override
  public void applyFilter(IFilter filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("Input can not be null.");
    }
    ImageInterface filteredImage = this.currentImage.copy().filter(filter);
    this.setImage(filteredImage);
  }

  @Override
  public void applyComplexEffect(IEffect effect) throws IllegalArgumentException {
    if (effect == null) {
      throw new IllegalArgumentException("Null effect given.");
    }
    if (effect.determineApplyToAllStatus()) {
      for (int i = 0; i < this.imageHistory.size(); i++) {
        ImageInterface layerImage = this.imageHistory.get(i).getImage().copy();
        ImageInterface layerImageFiltered = effect.applyEffect(layerImage);
        this.imageHistory.get(i).setImage(layerImageFiltered);
      }
      this.setImage(this.imageHistory.get(this.currLayer).getImage());
    } else {
      ImageInterface filteredImage = effect.applyEffect(this.currentImage.copy());
      this.setImage(filteredImage);
    }
  }

  @Override
  public void save(String name) {
    this.getCurrentTopmostVisible().setName(name);
  }

  @Override
  public void setImage(ImageInterface img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Null image given");
    }
    this.currentImage = img.copy();
    this.imageHistory.get(this.currLayer).setImage(img);
  }

  @Override
  public void createImage(ProgrammaticImageType imageTypeEnum, int width, int height, int tileSize,
      ArrayList<ColorEnum> colorSettings) throws IllegalArgumentException {
    if (imageTypeEnum == null || colorSettings == null) {
      throw new IllegalArgumentException("Null parameter given.");
    }
    if (width < 0 || height < 0 || tileSize < 0) {
      throw new IllegalArgumentException("Negative parameter given.");
    }
    if (tileSize > width || tileSize > height) {
      throw new IllegalArgumentException("Tile size is too big.");
    }
    ImageCreatorInterface imageType = ProgrammaticImageCreator.create(imageTypeEnum);
    this.addLayer();
    this.setImage(imageType.createImageRepresentation(width, height, tileSize, colorSettings));
  }

  @Override
  public ImageInterface getCurrentImage() {
    if (this.currentImage == null) {
      throw new IllegalStateException("No images have been added to the model yet.");
    }
    return this.currentImage;
  }

  @Override
  public ImageInterface getImageAt(int index) throws IllegalArgumentException {
    if (index < 0 || index >= this.imageHistory.size()) {
      throw new IllegalArgumentException("Given index is out of bounds.");
    }
    return this.imageHistory.get(index).getImage();
  }

  @Override
  public void addLayer() {
    this.imageHistory.add(new Layer());
    if (this.imageHistory.size() != 1) {
      this.currLayer++;
    }
  }

  @Override
  public void removeLayer() {
    this.imageHistory.remove(this.imageHistory.size() - 1);
  }

  @Override
  public void updateImgToCurrentLayer(int current) throws IllegalArgumentException {
    if (current < 0 || current >= this.imageHistory.size()) {
      throw new IllegalArgumentException("The layer index passed was invalid.");
    }
    this.currentImage = this.imageHistory.get(current).getImage();
    this.currLayer = current;
  }

  @Override
  public List<ILayer> getLayers() {
    return this.imageHistory;
  }

  @Override
  public void setAllLayers(List<ILayer> layerList) {
    this.imageHistory = layerList;
  }

  @Override
  public void adjustVisibility(int layerInd, boolean visibility) {
    if (layerInd < 0 || layerInd >= this.imageHistory.size()) {
      throw new IllegalArgumentException("The layer index passed was invalid.");
    }
    this.imageHistory.get(layerInd).setVisible(visibility);
  }

  @Override
  public ILayer getCurrentTopmostVisible() throws IllegalStateException {
    for (int i = this.imageHistory.size() - 1; i >= 0; i--) {
      ILayer potentialTopmostVisibleLayer = this.imageHistory.get(i);
      if (potentialTopmostVisibleLayer.getVisibility()) {
        return potentialTopmostVisibleLayer;
      }
    }
    return null;
  }
}
