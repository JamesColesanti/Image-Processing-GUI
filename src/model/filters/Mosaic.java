package model.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.image.ImageInterface;

/**
 * Represents the mosaic filter which filters an image based on grouping random pixels together
 * and then finding the average value of the seeds closest to the seed and finding the average r
 * g and b values and setting all of the pixels closest to said pixel to that average.
 */
public class Mosaic implements IMosaic {
  private int seeds;
  private List<ISeed> seedList;
  private List<List<IPosn>> posnList;

  /**
   * Constructor.
   */
  public Mosaic() {
    this.seeds = -1;
    this.seedList = null;
    this.posnList = null;
  }

  @Override
  public List<Integer> newColorValsAt(int x, int y, ImageInterface img)
      throws IllegalArgumentException {

    if (img == null) {
      throw new IllegalArgumentException("Null parameter given.");
    }
    if (x < 0 || y < 0 || x >= img.getImageWidth() || y >= img.getImageHeight()) {
      throw new IllegalArgumentException("Given index out of bounds.");
    }

    if (this.seedList.size() == 0) {
      return new ArrayList<>(Arrays.asList(
          img.getRedAt(x, y),
          img.getGreenAt(x, y),
          img.getBlueAt(x, y)));
    }

    ISeed currentSeed = this.posnList.get(x).get(y).getSeed();

    return new ArrayList<>(Arrays.asList(
        currentSeed.getFinalRedVal(),
        currentSeed.getFinalGreenVal(),
        currentSeed.getFinalBlueVal()));
  }

  /**
   * Initializes the seed list and the Posn list in this mosaic filter.
   *
   * @param seeds the number of seeds in the mosaic
   * @param img the image we are applying the mosaic filter on
   */
  @Override
  public void initializeSeeds(int seeds, ImageInterface img, Random randX, Random randY) {
    if (seeds >= 0) {
      this.seeds = seeds;
    } else {
      throw new IllegalArgumentException("The number of seeds was less than 0");
    }
    this.seedList = new ArrayList<>();
    this.posnList = new ArrayList<>();
    for (int x = 0; x < img.getImageWidth(); x++) { // x values
      this.posnList.add(new ArrayList<>());
      for (int y = 0; y < img.getImageHeight(); y++) {
        this.posnList.get(x).add(null);
      }
    }
    this.initializeSeedsHelper(img, randX, randY);
  }

  /**
   * Helps to initialize the seed lists based on the image passed.
   *
   * @param img the image that we are mosaic
   * @param randX the random object used to generate random x coordinates for seeds
   * @param randY the random object used to generate random y coordinates for seeds
   */
  private void initializeSeedsHelper(ImageInterface img, Random randX, Random randY) {
    int width = img.getImageWidth();
    int height = img.getImageHeight();

    for (int i = 0; i < this.seeds; i++) {
      int x = randX.nextInt(width);
      // make random x between 0 and width - 1
      int y = randY.nextInt(height);
      // make random y between 0 and height - seedList.
      this.seedList.add(new Seed(x, y));
    }

    if (this.seedList.size() == 0) {
      return;
    }

    int sizeOfSeedList = this.seedList.size();
    for (int xOfPixel = 0; xOfPixel < width; xOfPixel++) {
      for (int yOfPixel = 0; yOfPixel < height; yOfPixel++) {
        // for processing the image pixel by pixel
        this.seedList.sort(new SeedComparator(img.getImageWidth()));
        int ratio = height * width / sizeOfSeedList;
        int ratioPlus = (xOfPixel + yOfPixel * width) / ratio;
        int lowerBound = ratioPlus - sizeOfSeedList / 4;
        int upperBound = ratioPlus + sizeOfSeedList / 4;

        if (lowerBound < 0 || lowerBound >= sizeOfSeedList) {
          lowerBound = 0;
        }
        if (upperBound >= sizeOfSeedList || upperBound < 0) {
          upperBound = sizeOfSeedList;
        }

        ISeed currentSeed = this.seedList.get(lowerBound);
        for (int i = lowerBound; i < upperBound; i++) {
          ISeed eval = this.seedList.get(i);

          double distToEvalSeed = this.distanceToSeed(eval.getXOfSeed(),
              eval.getYOfSeed(), xOfPixel, yOfPixel);

          double distToCurrSeed = this.distanceToSeed(currentSeed.getXOfSeed(),
              currentSeed.getYOfSeed(), xOfPixel, yOfPixel);

          if (distToEvalSeed < distToCurrSeed) {
            currentSeed = this.seedList.get(i);
          }
        }
        currentSeed.addRed(img.getRedAt(xOfPixel, yOfPixel));
        currentSeed.addGreen(img.getGreenAt(xOfPixel, yOfPixel));
        currentSeed.addBlue(img.getBlueAt(xOfPixel, yOfPixel));
        IPosn p = new Posn(xOfPixel, yOfPixel, currentSeed);
        this.posnList.get(xOfPixel).set(yOfPixel, p);
      }
    }

    for (ISeed s : this.seedList) {
      s.setAverages();
    }
  }

  /**
   * Calculates the distance to a point and returns teh distance.
   *
   * @param xOfSeed the x coordinate of the seed
   * @param yOfSeed the y coordinate of the seed
   * @param xOfPixel the x coordinate of the pixel
   * @param yOfPixel the y coordinate of the pixel
   * @return the distance from the seed to the pixel
   */
  private double distanceToSeed(int xOfSeed, int yOfSeed, int xOfPixel, int yOfPixel) {
    return Math.sqrt(Math.pow((xOfSeed - xOfPixel), 2) + Math.pow((yOfSeed - yOfPixel), 2));
  }
}



