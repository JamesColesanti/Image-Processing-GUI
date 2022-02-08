import static org.junit.Assert.assertEquals;

import controller.GUIController;
import java.awt.event.ActionEvent;
import model.MockModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the GUIController.
 */
public class GUIControllerTest {
  // we can not use IFilterController since we would have to then cast in every method
  private GUIController testController;
  private MockModel mock;

  @Before
  public void initData() {
    this.mock = new MockModel();
  //  this.testController = new GUIController(mock);
  }

  /*
  Tests for actionPerformed
   */

  // test that "blur the image" sends the correct data to the model
  @Test
  public void guiBlurCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "blur the image"));
    assertEquals("Method Called: applyFilter(); Filter: Blur",
        this.mock.toString());
  }


  // test that "sharpen the image" sends the correct data to the model
  @Test
  public void guiSharpenCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "sharpen the image"));
    assertEquals("Method Called: applyFilter(); Filter: Sharpen",
        this.mock.toString());
  }

  // test that "sepia the image" sends the correct data to the model
  @Test
  public void guiSepiaCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "sepia the image"));
    assertEquals("Method Called: applyFilter(); Filter: Sepia",
        this.mock.toString());
  }

  // test that "monochrome the image" sends the correct data to the model
  @Test
  public void guiMonochromeCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "monochrome the image"));
    assertEquals("Method Called: applyFilter(); Filter: Monochrome",
        this.mock.toString());
  }




  // Test that "mosaic the image" sends the correct data to the model
  @Test
  public void guiMosaicCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "mosaic the image"));
    assertEquals("Method Called: applyFilter(); Filter: Mosaic",
     this.mock.toString());
  }

  // Test that "downscale the image" sends the correct data to the model
  @Test
  public void guiDownscaleCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "downscale the image"));
    assertEquals("Method Called: applyFilter(); Filter: Downscale",
     this.mock.toString());
  }

  // test that "remove clicked" sends the correct data to the model
  @Test
  public void guiRemoveCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "remove clicked"));
    assertEquals("Method Called: removeLayer(); Params: N/A",
        this.mock.toString());
  }

  // test that "create clicked" sends the correct data to the model
  @Test
  public void guiCreateCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "create clicked"));
    assertEquals("Method Called: addLayer(); Params: N/A",
        this.mock.toString());
  }

  // test that "current clicked" sends the correct data to the model
  @Test
  public void guiCurrentCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "current clicked"));
    assertEquals("Method Called: current; Params: Current: 1",
        this.mock.toString());
  }

  // test that "visible clicked" sends the correct data to the model
  @Test
  public void guiVisibleCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "visible clicked"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "invisible clicked" sends the correct data to the model
  @Test
  public void guiInvisibleCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "invisible clicked"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "checkerboard clicked" sends the correct data to the model
  @Test
  public void guiCheckerboardCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "checkerboard clicked"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "open file" sends the correct data to the model
  @Test
  public void guiOpenFileCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "open file clicked"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "open all files" sends the correct data to the model
  @Test
  public void guiOpenAllFilesCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "open all files"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "name layer" sends the correct data to the model
  @Test
  public void guiNameLayerCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "name layer"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "save the image" sends the correct data to the model
  @Test
  public void guiSaveImageCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "save the image"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "save all layers" sends the correct data to the model
  @Test
  public void guiSaveAllLayersCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "save all layers"));
    assertEquals("",
        this.mock.toString());
  }

  // test that "load script" sends the correct data to the model
  @Test
  public void guiLoadScriptCallTest() {
    this.testController.actionPerformed(
        new ActionEvent(this, 1, "load script"));
    assertEquals("",
        this.mock.toString());
  }


}
