import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.ILayer;
import model.Layer;
import model.filters.Downscale;
import model.IComplexEffectModel;
import model.filters.IEffect;
import model.filters.IMosaic;
import model.filters.Mosaic;
import model.programmaticimages.ColorEnum;
import model.FilterModel;
import model.filters.Blur;
import model.filters.Monochrome;
import model.filters.Sharpen;
import model.filters.Sepia;
import model.image.ImageInterface;
import model.image.Image;
import model.programmaticimages.ImageCreatorInterface;
import model.programmaticimages.ProgrammaticImageCreator;
import model.programmaticimages.ProgrammaticImageCreator.ProgrammaticImageType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the model. This class tests that models can properly maintain current images,
 * filter those images, and keep track of old and new images. The model must also be able to
 * properly import and export files when asked to.
 */
public class ModelTest {

  IComplexEffectModel<ImageInterface> model;
  ImageCreatorInterface imageType;
  ImageInterface checkerBoard;
  ImageInterface checkerBoard2;

  // initialization of data to be used in tests
  @Before
  public void initData() {
    this.model = new FilterModel(new ArrayList<>());
    this.imageType = ProgrammaticImageCreator.create(ProgrammaticImageType.CHECKER_BOARD);
    this.checkerBoard = imageType.createImageRepresentation(100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    this.checkerBoard2 = imageType.createImageRepresentation(100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.RED, ColorEnum.BLACK)));
  }

  /*
  Tests for the constructor
   */

  // test that an IllegalArgumentException is thrown when a null image history is passed
  @Test(expected = IllegalArgumentException.class)
  public void nullFilterModelArgument() {
    model = new FilterModel(null);
  }

  /*
  Tests for applyFilter
   */

  // tests that an IllegalArgumentException is thrown when a null filter type is passed
  @Test(expected = IllegalArgumentException.class)
  public void applyFilterNullFilterType() {
    this.model.applyFilter(null);
  }

  // test for blurring an image
  @Test
  public void testApplyFilterBlur() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // blur image
    this.model.applyFilter(new Blur());

    // extract the blurred checkerboard from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the checkerboard filtered outside the model are the
    // same as the color values at each pixel in the checkerboard filtered inside the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        List<Integer> newColors = new Blur().newColorValsAt(x, y, this.checkerBoard);
        assertEquals(newColors.get(0).intValue(), modelImageTest.getRedAt(x, y));
        assertEquals(newColors.get(1).intValue(), modelImageTest.getGreenAt(x, y));
        assertEquals(newColors.get(2).intValue(), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for sharpening an image
  @Test
  public void testApplyFilterSharpen() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // sharpen image
    this.model.applyFilter(new Sharpen());

    // extract the sharpened checkerboard from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the checkerboard filtered outside the model are the
    // same as the color values at each pixel in the checkerboard filtered inside the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        List<Integer> newColors = new Sharpen().newColorValsAt(x, y, this.checkerBoard);
        assertEquals(newColors.get(0).intValue(), modelImageTest.getRedAt(x, y));
        assertEquals(newColors.get(1).intValue(), modelImageTest.getGreenAt(x, y));
        assertEquals(newColors.get(2).intValue(), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for applying the monochrome filter to an image
  @Test
  public void testApplyFilterMonochrome() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply monochrome filter
    this.model.applyFilter(new Monochrome());

    // extract the monochrome checkerboard from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the checkerboard filtered outside the model are the
    // same as the color values at each pixel in the checkerboard filtered inside the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        List<Integer> newColors = new Monochrome().newColorValsAt(x, y, this.checkerBoard);
        assertEquals(newColors.get(0).intValue(), modelImageTest.getRedAt(x, y));
        assertEquals(newColors.get(1).intValue(), modelImageTest.getGreenAt(x, y));
        assertEquals(newColors.get(2).intValue(), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for applying the sepia filter to an image
  @Test
  public void testApplyFilterSepia() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply sepia filter
    this.model.applyFilter(new Sepia());

    // extract the sepia checkerboard from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the checkerboard filtered outside the model are the
    // same as the color values at each pixel in the checkerboard filtered inside the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        List<Integer> newColors = new Sepia().newColorValsAt(x, y, this.checkerBoard);
        assertEquals(newColors.get(0).intValue(), modelImageTest.getRedAt(x, y));
        assertEquals(newColors.get(1).intValue(), modelImageTest.getGreenAt(x, y));
        assertEquals(newColors.get(2).intValue(), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for applying the mosaic filter to an image
  @Test
  public void testApplyFilterMosaic() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply mosaic filter to image
    IMosaic mosaic = new Mosaic();
    mosaic.initializeSeeds(10,this.model.getCurrentImage(), new Random(10),
        new Random(10));
    this.model.applyFilter(mosaic);

    // extract the mosaic checkerboard from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the checkerboard filtered outside the model are the
    // same as the color values at each pixel in the checkerboard filtered inside the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        IMosaic mosaic2 = new Mosaic();
        mosaic2.initializeSeeds(10, this.checkerBoard, new Random(10),
            new Random(10));
        List<Integer> newColors = mosaic2.newColorValsAt(x, y, this.checkerBoard);
        assertEquals(newColors.get(0).intValue(), modelImageTest.getRedAt(x, y));
        assertEquals(newColors.get(1).intValue(), modelImageTest.getGreenAt(x, y));
        assertEquals(newColors.get(2).intValue(), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for downscaling an image
  @Test
  public void testApplyDownscaleEffect() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // create second checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.RED, ColorEnum.BLACK)));
    // apply mosaic filter to image (will be applied to all layers)
    IEffect downscale = new Downscale(50, 50);
    this.model.applyComplexEffect(downscale);

    // extract the downscaled checkerboard2 from the model
    ImageInterface modelImageTest1 = this.model.getImageAt(0);
    ImageInterface modelImageTest2 = this.model.getImageAt(1);

    ImageInterface downscaledLayer1 = null;
    ImageInterface downscaledLayer2 = null;
    // see if the color values at each pixel in the checkerboard downscaled outside the model are
    // the same as the color values at each pixel in the checkerboard downscaled inside the model
    for (int x = 0; x < 50; x++) {
      for (int y = 0; y < 50; y++) {
        IEffect downscale2 = new Downscale(50, 50);
        downscaledLayer1 = downscale2.applyEffect(this.checkerBoard);
        downscaledLayer2 = downscale2.applyEffect(this.checkerBoard2);
        assertEquals(downscaledLayer1, modelImageTest1);
        assertEquals(downscaledLayer1.getGreenAt(x, y), modelImageTest1.getGreenAt(x, y));
        assertEquals(downscaledLayer1.getBlueAt(x, y), modelImageTest1.getBlueAt(x, y));

        assertEquals(downscaledLayer2.getRedAt(x, y), modelImageTest2.getRedAt(x, y));
        assertEquals(downscaledLayer2.getGreenAt(x, y), modelImageTest2.getGreenAt(x, y));
        assertEquals(downscaledLayer2.getBlueAt(x, y), modelImageTest2.getBlueAt(x, y));
      }
    }

    // check dimensions of images
    assertEquals(downscaledLayer1.getImageWidth(), modelImageTest1.getImageWidth());
    assertEquals(downscaledLayer2.getImageHeight(), modelImageTest2.getImageHeight());
  }

  // test for an exception when calling applyFilter with a null filter
  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterNullParameter() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // attempt to apply a null filter (throws exception)
    this.model.applyFilter(null);
  }

  // test for an exception when calling applyComplexEffect with a null effect
  @Test(expected = IllegalArgumentException.class)
  public void testApplyComplexEffectNullParameter() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // attempt to apply a null filter (throws exception)
    this.model.applyComplexEffect(null);
  }

  /*
  Tests for save
   */

  // test for calling save and getting the filtered image after a filter has been applied
  @Test
  public void testSave() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply sepia filter
    this.model.applyFilter(new Sepia());
    // save image
    this.model.save("CheckerBoardSepia");

    // extract the second image (the one with the sepia filter) in the stack of image history from
    // the model
    ImageInterface modelImageTest = this.model.getImageAt(0);

    // apply sepia filter
    ImageInterface blurredCheckerBoardExpected = this.checkerBoard.filter(new Sepia());

    // see if the color values at each pixel in the original checkerboard are the same as the
    // color values at each pixel in the checkerboard that came from the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        assertEquals(blurredCheckerBoardExpected.getRedAt(x, y), modelImageTest.getRedAt(x, y));
        assertEquals(blurredCheckerBoardExpected.getGreenAt(x, y), modelImageTest.getGreenAt(x, y));
        assertEquals(blurredCheckerBoardExpected.getBlueAt(x, y), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  /*
  Tests for setImage
   */

  // test for setImage properly setting the current image to the given one
  @Test
  public void testSetImage() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply sepia filter
    this.model.applyFilter(new Sepia());
    // set current image to new image
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(13, 200, 0)),
        new ArrayList<>(Arrays.asList(255, 180, 0))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0)),
        new ArrayList<>(Arrays.asList(128, 63, 0)),
        new ArrayList<>(Arrays.asList(11, 89, 0))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21)),
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255))));
    ImageInterface testImage = new Image(red, green, blue);

    // set the current image in the model to testImage
    this.model.setImage(testImage);

    // extract the current image
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the original checkerboard are the same as the
    // color values at each pixel in the checkerboard that came from the model
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(testImage.getRedAt(x, y), modelImageTest.getRedAt(x, y));
        assertEquals(testImage.getGreenAt(x, y), modelImageTest.getGreenAt(x, y));
        assertEquals(testImage.getBlueAt(x, y), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for setImage throwing an exception when given a null image
  @Test(expected = IllegalArgumentException.class)
  public void testSetImageException() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply sepia filter
    this.model.applyFilter(new Sepia());
    // attempt to set the current image to a null image (throws an exception)
    this.model.setImage(null);
  }

  /*
  Tests for create image
   */

  // test for creating a checkerboard
  @Test
  public void testCreateImage() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));

    // extract the current image from the model
    ImageInterface modelImageTest = this.model.getCurrentImage();

    // see if the color values at each pixel in the original checkerboard are the same as the
    // color values at each pixel in the checkerboard that came from the model
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        assertEquals(this.checkerBoard.getRedAt(x, y), modelImageTest.getRedAt(x, y));
        assertEquals(this.checkerBoard.getGreenAt(x, y), modelImageTest.getGreenAt(x, y));
        assertEquals(this.checkerBoard.getBlueAt(x, y), modelImageTest.getBlueAt(x, y));
      }
    }
  }

  // test for createImage throwing an exception when it is called with a null ProgrammaticImageType
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNullImageType() {
    // attempt to create an image with a null ProgrammaticImageType (throws exception)
    this.model.createImage(null, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  // test for createImage throwing an exception when it is called with a null colorSettings list
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNullColorSettingsList() {
    // attempt to create an image with a null colorSettings list (throws exception)
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        null);
  }

  // test for createImage throwing an exception when it is called with a negative image width
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNegativeImageWidth() {
    // attempt to create an image with a negative image width
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, -1, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  // test for createImage throwing an exception when it is called with a negative image height
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNegativeImageHeight() {
    // attempt to create an image with a negative image height
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, -1, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  // test for createImage throwing an exception when it is called with a negative tileSize
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNegativeTileSize() {
    // attempt to create an image with a negative image width
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, -1,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  // test for createImage throwing an exception when it is called with a tileSize larger than the
  // width
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageTileSizeLargerThanWidth() {
    // attempt to create an image with a negative image width
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 1, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  // test for createImage throwing an exception when it is called with a tileSize larger than the
  // height
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageTileSizeLargerThanHeight() {
    // attempt to create an image with a negative image width
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 1, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
  }

  /*
  Tests for getCurrentImage()
   */

  // test for calling getCurrentImage after an image has been added to the model
  @Test
  public void testGetCurrentImageValid() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));

    assertEquals(this.checkerBoard, this.model.getCurrentImage());
  }

  // test for an exception when calling getCurrentImage before an image has been added to the model
  @Test (expected = IllegalStateException.class)
  public void testGetCurrentImageException() {
    this.model.getCurrentImage();
  }

  /*
  Tests for getImageAt()
   */

  // test for calling getImageAt after an image has been saved to the model
  @Test
  public void testGetImageAtValid() {
    // create checkerboard image
    this.model.createImage(ProgrammaticImageType.CHECKER_BOARD, 100, 100, 10,
        new ArrayList<>(Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE)));
    // apply sepia filter to checkerboard
    this.model.applyFilter(new Sepia());
    // save image
    this.model.save("CheckerboardSepia");

    // apply sepia filter to checkerboard
    ImageInterface checkerBoardFiltered = this.checkerBoard.filter(new Sepia());

    assertEquals(checkerBoardFiltered, this.model.getImageAt(0));
  }

  // test for an exception when calling getImageAt when no images have been saved
  @Test (expected = IllegalArgumentException.class)
  public void testGetImageAtExceptionNoImagesInStack() {
    // attempt to get first image in stack (throws exception because stack is empty)
    this.model.getImageAt(0);
  }

  /*
  Tests for addLayer
   */

  // test that a layer is added
  @Test
  public void addLayerProperlyAddsLayer4To5() {
    this.model = new FilterModel(new ArrayList<>(Arrays.asList(
        new Layer(), new Layer(), new Layer(), new Layer()
    )));
    assertEquals(4, this.model.getLayers().size());
    this.model.addLayer();
    assertEquals(5, this.model.getLayers().size());
  }

  /*
  Tests for updateImgToCurrentLayer
   */

  // tests that an IllegalArgumentException is thrown when the index is too small
  @Test(expected = IllegalArgumentException.class)
  public void updateImgToCurrentLayerLayerIndexTooSmall() {
    this.model.addLayer();
    this.model.addLayer();
    this.model.updateImgToCurrentLayer(-1);
  }

  // tests that an IllegalArgumentException is thrown when the index is too large
  @Test(expected = IllegalArgumentException.class)
  public void updateImgToCurrentLayerLayerIndexTooBig() {
    this.model.addLayer();
    this.model.addLayer();
    this.model.updateImgToCurrentLayer(2);
  }

  // test that the image is updated
  @Test
  public void updateImgToCurrentLayerProperImgUpdate() {
    List<List<Integer>> red = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(13, 200, 0)),
        new ArrayList<>(Arrays.asList(255, 180, 0))));
    List<List<Integer>> green = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 89, 0)),
        new ArrayList<>(Arrays.asList(128, 63, 0)),
        new ArrayList<>(Arrays.asList(11, 89, 0))));
    List<List<Integer>> blue = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(120, 89, 21)),
        new ArrayList<>(Arrays.asList(120, 89, 0)),
        new ArrayList<>(Arrays.asList(99, 89, 255))));
    List<List<Integer>> red2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(12, 8, 10)),
        new ArrayList<>(Arrays.asList(1, 20, 2)),
        new ArrayList<>(Arrays.asList(55, 80, 100))));
    List<List<Integer>> green2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(155, 99, 90)),
        new ArrayList<>(Arrays.asList(18, 6, 230)),
        new ArrayList<>(Arrays.asList(14, 89, 1))));
    List<List<Integer>> blue2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(10, 89, 2)),
        new ArrayList<>(Arrays.asList(12, 89, 0)),
        new ArrayList<>(Arrays.asList(9, 89, 25))));
    ImageInterface image1 = new Image(red, green, blue);
    ImageInterface image2 = new Image(red2, green2, blue2);

    this.model = new FilterModel(new ArrayList<>());
    this.model.addLayer();
    this.model.setImage(image1);
    this.model.addLayer();
    this.model.setImage(image2);
    assertTrue(image1.equals(this.model.getImageAt(0)));
    assertTrue(image2.equals(this.model.getImageAt(1)));
    assertTrue(image2.equals(this.model.getCurrentImage()));
    this.model.updateImgToCurrentLayer(0);
    assertTrue(image1.equals(this.model.getCurrentImage()));
  }

  /*
  Tests for getLayers
   */

  // tests that the contents of the layers are in fact returned
  @Test
  public void getLayersWorks() {
    this.model.addLayer();
    this.model.addLayer();
    List<ILayer> theLayers = this.model.getLayers();
    assertEquals(2, theLayers.size());
    assertTrue(theLayers.get(0).getVisibility());
    assertTrue(theLayers.get(1).getVisibility());
    assertEquals(null, theLayers.get(0).getImage());
    assertEquals(null, theLayers.get(1).getImage());
    assertEquals(null, theLayers.get(0).getFileName());
    assertEquals(null, theLayers.get(1).getFileName());

  }


  /*
  Tests for adjustVisibility
   */

  // tests that an IllegalArgumentException is thrown when the index is too small\
  @Test(expected = IllegalArgumentException.class)
  public void adjustVisibilityIndexTooSmall() {
    this.model.addLayer();
    this.model.addLayer();
    this.model.adjustVisibility(-1, true);
  }

  // tests that an IllegalArgumentException is thrown when the index is too large
  @Test(expected = IllegalArgumentException.class)
  public void adjustVisibilityIndexTooBig() {
    this.model.addLayer();
    this.model.addLayer();
    this.model.adjustVisibility(2, false);
  }

}
