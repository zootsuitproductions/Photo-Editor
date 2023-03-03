import static org.junit.Assert.assertEquals;

import hw06.ILayeredImageEditorModel;
import hw06.LayeredImageEditorModel;
import hw07.GraphicalLayeredImageView;
import hw07.controller.GraphicalController;
import hw07.controller.IViewListener;
import org.junit.Test;

/**
 * Test class for GraphicalController, uses a mock model class
 * to ensure input is passed properly into the model.
 */

public class GraphicalControllerTest {

  @Test (expected = IllegalArgumentException.class)
  public void testConstructor() {
    new GraphicalController(null, new GraphicalLayeredImageView());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    new GraphicalController(new LayeredImageEditorModel(), null);
  }


  @Test
  public void testHandleSetCurrent() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleSetCurrent("hi");

    assertEquals(modelLog.toString(), "setCurrentLayer(hi)\n"
        + "getLayers()\n"
        + "getCurrentLayerCopy()\n"
        + "getLayers()\n");
    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
  }

  @Test
  public void testBlurButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleBlur();

    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "applyEditToCurrentLayer(hw05.imageedits.BlurFilter)\n"
        + "getLayers()\n");

  }

  @Test
  public void testSharpenButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleSharpen();



    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "applyEditToCurrentLayer(hw05.imageedits.SharpenFilter)\n"
        + "getLayers()\n");

  }

  @Test
  public void testSepiaButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleSepia();



    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "applyEditToCurrentLayer("
        + "hw05.imageedits.SepiaTransformation)\n"
        + "getLayers()\n");

  }

  @Test
  public void testGreyscaleButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleGreyscale();


    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "applyEditToCurrentLayer("
        + "hw05.imageedits.GreyScaleTransformation)\n"
        + "getLayers()\n");

  }

  @Test
  public void testCreateRainbowButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleCreateRainbow();


    assertEquals(log.toString(), "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "getCurrentLayerName()\n"
        + "getLayers()\n"
        + "replaceImage(getCurrentLayerName(), image: getCurrentLayerName())\n"
        + "getLayers()\n");

  }

  @Test
  public void testRemoveButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleRemoveLayer();


    assertEquals(log.toString(), "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "getCurrentLayerName()\n"
        + "removeImage(getCurrentLayerName())\n"
        + "getNumPhotos()\n"
        + "getLayers()\n");

  }

  @Test
  public void testCreateLayer() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);
    ILayeredImageEditorModel model = new LayeredImageEditorModel();
    assertEquals(model.getNumPhotos(), 0);
    new GraphicalController(model, view).handleCreateLayer("Hi");


    assertEquals(log.toString(), "Updated layers to: [◆ Layer 1: Hi]\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(model.getNumPhotos(), 1);
  }

  @Test
  public void testCreateCopyButton() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleCopyCurrentLayer("Copy layer name");


    assertEquals(log.toString(), "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "addCopyCurrentLayer(Copy layer name)\n"
        + "getNumPhotos()\n"
        + "getLayers()\n");

  }

  @Test
  public void testScriptButtonClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleScript("file.txt");


    assertEquals(log.toString(), "Error: Could not read from the file, or it doesn't exist\n");
    assertEquals(modelLog.toString(), "");
  }

  @Test
  public void testScriptButtonClick2() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleScript("./res/jar_and_scripts/HW07Script.txt");

    assertEquals(log.toString(), "Success: The script was processed successfully!\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "getLayers()\n"
        + "loadEntireLayeredImage(./res/script1layeredimage/imageLocations.txt)\n"
        + "getLayers()\n"
        + "addImage(layer4, image: null)\n"
        + "getLayers()\n"
        + "addImage(layer5, image: null)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer2)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer3)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer4)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(?)\n"
        + "getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerTransparent(getCurrentLayerName())\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "removeImage(layer4)\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer3)\n"
        + "getLayers()\n"
        + "addCopyCurrentLayer(layer3Copy)\n"
        + "getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerTransparent(getCurrentLayerName())\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerTransparent(getCurrentLayerName())\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerVisible(getCurrentLayerName())\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer3Copy)\n"
        + "getLayers()\n"
        + "getCurrentLayerName()\n"
        + "getLayers()\n"
        + "replaceImage(getCurrentLayerName(), image: getCurrentLayerName())\n"
        + "getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.BlurFilter)\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer3)\n"
        + "getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.SepiaTransformation)\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer2)\n"
        + "getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.SharpenFilter)\n"
        + "getLayers()\n"
        + "setCurrentLayer(layer1)\n"
        + "getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.GreyScaleTransformation)\n"
        + "getLayers()\n"
        + "exportTopImage(hw05.exportimages.PNGExportManager)\n"
        + "getLayers()\n"
        + "saveLayeredImage(script1layeredimage)\n"
        + "getLayers()\n"
        + "getNumPhotos()\n"
        + "getLayers()\n");
  }

  @Test
  public void testLoadImageButtonClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleLoadImage("file.png");

    assertEquals(log.toString(), "Error: File not found\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "getNumPhotos()\n"
        + "getLayers()\n");
  }

  @Test
  public void testExportButtonClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleExportTopImage("saved-as.png");

    assertEquals(log.toString(), "Success!: Successfully exported "
        + "the top visible image as saved-as.png\n");
    assertEquals(modelLog.toString(), "exportTopImage(hw05.exportimages.PNGExportManager)\n");
  }

  @Test
  public void testSaveLayersButtonClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleSaveLayers("folder-name");

    assertEquals(log.toString(), "Success!: Successfully saved the "
        + "layered image to folder-name\n");
    assertEquals(modelLog.toString(), "saveLayeredImage(folder-name)\n");
  }

  @Test
  public void testLoadLayeredImageClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleLoadEntireImage("file.txt");

    assertEquals(log.toString(), "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "loadEntireLayeredImage(file.txt)\n"
        + "getNumPhotos()\n"
        + "getLayers()\n");
  }

  @Test
  public void testToggleVisibilityClick() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    Appendable modelLog = new StringBuilder();
    ILayeredImageEditorModel model = new MockLayeredImageEditorModel(modelLog);
    new GraphicalController(model, view).handleToggleVisibility();

    assertEquals(log.toString(), "Error: Must have a layer selected!\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");
    assertEquals(modelLog.toString(), "getCurrentLayerCopy()\n"
        + "getNumPhotos()\n"
        + "getLayers()\n");
  }

  @Test
  public void testToggleVisibilityClick2() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    ILayeredImageEditorModel model = new LayeredImageEditorModel();
    IViewListener listener = new GraphicalController(model, view);

    listener.handleToggleVisibility();


    assertEquals(log.toString(), "Error: The current index of the model "
        + "is out of bounds for the amount of layers that are in this layered image! "
        + "There are no images inside of the layered image.\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n");

    model.addImage("Hi",null);

    listener.handleToggleVisibility();

    assertEquals(log.toString(), "Error: The current index of the model "
        + "is out of bounds for the amount of layers that are in this layered image! "
        + "There are no images inside of the layered image.\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: Hi]\n"
        + "Refreshed the top image to that of the model\n");

    listener.handleToggleVisibility();

    assertEquals(log.toString(), "Error: The current index of the model "
        + "is out of bounds for the amount of layers that are in this layered image! "
        + "There are no images inside of the layered image.\n"
        + "Updated layers to: []\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: Hi]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◆ Layer 1: Hi]\n"
        + "Refreshed the top image to that of the model\n");

  }

  @Test
  public void testViewMultipleLayers() {
    Appendable log = new StringBuilder();
    MockView view = new MockView(log);

    ILayeredImageEditorModel model = new LayeredImageEditorModel();
    IViewListener listener = new GraphicalController(model, view);

    listener.handleCreateLayer("l1");
    listener.handleCreateLayer("l2");
    listener.handleCreateLayer("this is layer 3");
    listener.handleToggleVisibility();
    listener.handleSetCurrent("l2");
    listener.handleToggleVisibility();

    assertEquals(log.toString(), "Updated layers to: [◆ Layer 1: l1]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◆ Layer 1: l1, ◆ Layer 2: l2]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◆ Layer 1: l1, ◆ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: l1, ◆ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: l1, ◇ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n");

    listener.handleToggleVisibility();

    assertEquals(log.toString(), "Updated layers to: [◆ Layer 1: l1]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◆ Layer 1: l1, ◆ Layer 2: l2]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◆ Layer 1: l1, ◆ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: l1, ◆ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: l1, ◇ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n"
        + "Updated layers to: [◇ Layer 1: l1, ◆ Layer 2: l2, ◆ Layer 3: this is layer 3]\n"
        + "Refreshed the top image to that of the model\n");
  }

  @Test
  public void testStartEditor() {
    Appendable log = new StringBuilder();
    new GraphicalController(new LayeredImageEditorModel(), new MockView(log)).startEditor();
    assertEquals(log.toString(), "started the view\n");
  }


}