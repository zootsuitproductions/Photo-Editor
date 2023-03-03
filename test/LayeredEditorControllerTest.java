import static org.junit.Assert.assertEquals;

import hw05.image.RainbowImageGenerator;
import hw06.ILayeredImageEditorModel;
import hw06.LayeredImageEditorModel;
import hw06.controller.ILayeredEditorController;
import hw06.controller.LayeredEditorController;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of LayeredEditorController.
 */

public class LayeredEditorControllerTest {

  private ILayeredImageEditorModel model;

  @Before
  public void setUp() {
    this.model = new LayeredImageEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    new LayeredEditorController(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException1() {
    new LayeredEditorController(new StringReader(""), new StringBuilder(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    new LayeredEditorController(null, new StringBuilder(), new LayeredImageEditorModel());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    new LayeredEditorController(new StringReader(""), null, new LayeredImageEditorModel());
  }

  @Test
  public void testBadReadable() {
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(
        new MockReadable(), output, mock);
    controller.startEditor();
    assertEquals(output.toString(),
        "Type 'script' to run a script of commands, or type 'interactive' "
            + "to input to the console\n"
            + "Layered Image: \n"
            + "\n");
    assertEquals(commandLog.toString(), "getLayers()\n");
  }

  @Test
  public void testStartEditInvalidInput() {
    Readable input = new StringReader("q");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(output.toString(),
        "Type 'script' to run a script of commands, "
            + "or type 'interactive' to input to the console\n"
            + "Invalid input: q\n"
            + "Layered Image: \n"
            + "\n");
  }

  @Test
  public void testStartEditCreateLayer() {
    Readable input = new StringReader("createLayer interactive createLayer layer 1");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(
        "Type 'script' to run a script of commands, "
            + "or type 'interactive' to input to the console\n"
            + "Invalid input: createLayer\n"
            + "Layered Image: \n"
            + "\n"
            + "created a new layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: 1\n"
            + "Layered Image: \n"
            + "Layer 1: layer | visible: true | image file: no image\n"
            + "\n",
        output.toString());
    assertEquals(model.getCurrentLayerName(), "layer");
    assertEquals(model.getCurrentLayerImage(), null);
    assertEquals(model.getCurrentLayerCopy().isVisible(), true);
  }

  @Test
  public void testStartEditWithScriptWithoutFile() {
    Readable input = new StringReader("AAAAsa aa script");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(
        "Type 'script' to run a script of commands, "
            + "or type 'interactive' to input to the console\n"
            + "Invalid input: AAAAsa\n"
            + "Invalid input: aa\n"
            + "Type the name of the script file you would like to run, which should end in .txt\n"
            + "Layered Image: \n"
            + "\n",
        output.toString());
  }

  @Test
  public void testStartEditWithScriptWithFile() {
    Readable input = new StringReader("AAAAsa aa script abc.png "
        + "./res/jar_and_scripts/HW07Script.txt FROm here on THIS ISNT READ!!");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(
        "Type 'script' to run a script of commands, "
            + "or type 'interactive' to input to the console\n"
            + "Invalid input: AAAAsa\n"
            + "Invalid input: aa\n"
            + "Type the name of the script file you would like to run, "
            + "which should end in .txt\n"
            + "Invalid input: abc.png\n"
            + "Layered Image: \n"
            + "\n"
            + "Loaded the layers into the editor successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "\n"
            + "created a new layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "\n"
            + "created a new layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "File not found\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer2 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "File not found\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer3 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "File not found\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer4 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "File not found\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: ERRORRRRRR\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: AAAAAA\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: setCCurent\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "This layer name does not exist in the layered image!\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: true | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Made the current layer transparent successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ."
            + "/res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: c\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ."
            + "/res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: ???!!\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./r"
            + "es/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: !\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./re"
            + "s/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: N\\t\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: "
            + "./res/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: NAKS\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./re"
            + "s/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: \\n\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./re"
            + "s/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: a\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./re"
            + "s/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: $)%(&^%$#$%^&\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./r"
            + "es/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer4 | visible: false | image file: no image\n"
            + "Layer 6: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Removed the layer successfully!\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer3 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "You cannot add an Image with an Id that already exists in the layered image.\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Made the current layer transparent successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: false | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: layer1\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: false | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Made the current layer transparent successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: false | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: layer2\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: false | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res"
            + "/script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Made the current layer visible successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res/"
            + "script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Invalid input: layer2\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res/"
            + "script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer3Copy as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: ./res/"
            + "script1layeredimage/layer3Copy.png\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Loaded the rainbow image into the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Applied blur to the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer3 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Image must be non null\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer2 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Image must be non null\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "set layer1 as the current layer successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "Image must be non null\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "exported the image successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n"
            + "saved the layered image successfully\n"
            + "Layered Image: \n"
            + "Layer 1: layer1 | visible: true | image file: no image\n"
            + "Layer 2: layer2 | visible: true | image file: no image\n"
            + "Layer 3: layer3 | visible: true | image file: no image\n"
            + "Layer 4: layer3Copy | visible: true | image file: layer3Copy\n"
            + "Layer 5: layer5 | visible: true | image file: no image\n"
            + "\n",
        output.toString());
  }

  @Test
  public void testBlur() {
    Readable input = new StringReader("interactive blur");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Applied blur to the current layer successfully\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.BlurFilter)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSharpen() {
    Readable input = new StringReader("interactive sharpen");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Applied sharpening to the current layer successfully\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.SharpenFilter)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSepia() {
    Readable input = new StringReader("interactive sepia");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Applied sepia to the current layer successfully\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.SepiaTransformation)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testGreyscale() {
    Readable input = new StringReader("interactive greyscale");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Applied greyscale to the current layer successfully\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.GreyScaleTransformation)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testBadCommand() {
    Readable input = new StringReader("interactive greyScale A g aaa !MF");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid input: greyScale\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid input: A\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid input: g\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid input: aaa\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid input: !MF\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testCreateLayerNoName() {
    Readable input = new StringReader("interactive createLayer");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testCreateLayerName() {
    Readable input = new StringReader("interactive createLayer LAYER_NAME");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "created a new layer successfully\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "addImage(LAYER_NAME, image: null)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSetCurrentLayerName() {
    Readable input = new StringReader("interactive setCurrent LAYER_NAME1");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "setCurrentLayer(LAYER_NAME1)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSaveLayers() {
    Readable input = new StringReader("interactive saveLayers");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadNoImage() {
    Readable input = new StringReader("interactive load");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadInvalidImage() {
    Readable input = new StringReader("interactive load image.jep");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadValidButNonExistantImage() {
    Readable input = new StringReader("interactive load image.jpg");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "File not found\n"
        + "Layered Image: \n"
        + "\n", output.toString());
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadRealImagePNG() {
    Readable input = new StringReader("interactive load ./ExportTests/test1.png");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run a "
        + "script of commands, or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Loaded the image into the current layer successfully\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "replaceImage(getCurrentLayerName(), image: ./ExportTests/test1.png)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadRealImageJPEG() {
    Readable input = new StringReader("interactive load ./ExportTests/test1.jpeg");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run a "
        + "script of commands, or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Loaded the image into the current layer successfully\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "replaceImage(getCurrentLayerName(), image: ./ExportTests/test1.jpeg)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadLayeredImageNoName() {
    Readable input = new StringReader("interactive loadLayeredImage");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run a "
        + "script of commands, or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadLayeredImageWithName() {
    Readable input = new StringReader("interactive loadLayeredImage hello.txt");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run "
        + "a script of commands, or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Loaded the layers into the editor successfully\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "loadEntireLayeredImage(hello.txt)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadRealImageInvalidPPM() {
    Readable input = new StringReader("interactive load ./res/malformed.ppm");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Invalid PPM file: plain RAW file should begin with P3\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testLoadRealImageValidPPM() {
    Readable input = new StringReader("interactive load ./res/fileTest2.ppm");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run "
        + "a script of commands, or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "Loaded the image into the current layer successfully\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "replaceImage(getCurrentLayerName(), image: ./res/fileTest2.ppm)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSaveLayersFolderName() {
    Readable input = new StringReader("interactive saveLayers folder_name");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "saveLayeredImage(folder_name)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSetTransparent() {
    Readable input = new StringReader("interactive setTransparent");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerTransparent(getCurrentLayerName())\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testSetVisible() {
    Readable input = new StringReader("interactive setVisible");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "setLayerVisible(getCurrentLayerName())\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImage() {
    Readable input = new StringReader("interactive exportLayerImage");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImageNamePNG() {
    Readable input = new StringReader("interactive exportLayerImage image-name.png");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "exportTopImage(hw05.exportimages.PNGExportManager)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImageNameJPEG() {
    Readable input = new StringReader("interactive exportLayerImage image-name.jpeg");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "exportTopImage(hw05.exportimages.JPEGExportManager)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImageNameJPG() {
    Readable input = new StringReader("interactive exportLayerImage image-name.jpg");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "exportTopImage(hw05.exportimages.JPEGExportManager)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImageNamePPM() {
    Readable input = new StringReader("interactive exportLayerImage image-name.ppm");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "exportTopImage(hw05.exportimages.PPMExportManager)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testExportLayerImageNameBadFormat() {
    Readable input = new StringReader("interactive exportLayerImage image-name.jpeeeg");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testCreateRainbow() {
    Readable input = new StringReader("interactive createRainbow");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getCurrentLayerName()\n"
        + "getLayers()\n"
        + "replaceImage(getCurrentLayerName(), image: getCurrentLayerName())\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testCreateRainbowWithoutLayers2() {
    Readable input = new StringReader("interactive createRainbow");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(output.toString(), "Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n"
        + "Layered Image: \n"
        + "\n"
        + "The current index of the model is out of bounds for the amount "
        + "of layers that are in this layered image! There are no images "
        + "inside of the the layered image.\n"
        + "Layered Image: \n"
        + "\n");
    assertEquals(model.getNumPhotos(), 0);
  }

  @Test
  public void testCreateRainbow3() {
    Readable input = new StringReader("interactive createLayer l1 createRainbow");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(model.getImage("l1").getPixel2dArray(),
        new RainbowImageGenerator(300,500,4)
            .generateImage("l1").getPixel2dArray());
  }

  @Test
  public void testAddCopyOfCurrentLayer() {
    Readable input = new StringReader("interactive addCopyOfCurrentLayer");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testAddCopyOfCurrentLayerName() {
    Readable input = new StringReader("interactive addCopyOfCurrentLayer layer_copy");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "addCopyCurrentLayer(layer_copy)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testRemove() {
    Readable input = new StringReader("interactive remove");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testRemoveName() {
    Readable input = new StringReader("interactive remove layer_name");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("getLayers()\n"
        + "removeImage(layer_name)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testMultipleCommands() {
    Readable input = new StringReader("interactive remove layer_name "
        + "createLayer new_layer \n \t !!AH ???? setCurrent hello blur "
        + "exportLayerImage hi.p hello.png exportLayerImage hello.png");
    Appendable output = new StringBuilder();
    Appendable commandLog = new StringBuilder();
    ILayeredImageEditorModel mock = new MockLayeredImageEditorModel(commandLog);
    ILayeredEditorController controller = new LayeredEditorController(input, output, mock);
    controller.startEditor();
    assertEquals("Type 'script' to run a script of commands, "
            + "or type 'interactive' to input to the console\n"
            + "Layered Image: \n"
            + "\n"
            + "Removed the layer successfully!\n"
            + "Layered Image: \n"
            + "\n"
            + "created a new layer successfully\n"
            + "Layered Image: \n"
            + "\n"
            + "Invalid input: !!AH\n"
            + "Layered Image: \n"
            + "\n"
            + "Invalid input: ????\n"
            + "Layered Image: \n"
            + "\n"
            + "set hello as the current layer successfully\n"
            + "Layered Image: \n"
            + "\n"
            + "Applied blur to the current layer successfully\n"
            + "Layered Image: \n"
            + "\n"
            + "Invalid ending file format for file exporting!\n"
            + "Layered Image: \n"
            + "\n"
            + "Invalid input: hello.png\n"
            + "Layered Image: \n"
            + "\n"
            + "exported the image successfully\n"
            + "Layered Image: \n"
            + "\n",
        output.toString());
    assertEquals("getLayers()\n"
        + "removeImage(layer_name)\n"
        + "getLayers()\n"
        + "addImage(new_layer, image: null)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "setCurrentLayer(hello)\n"
        + "getLayers()\n"
        + "applyEditToCurrentLayer(hw05.imageedits.BlurFilter)\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "getLayers()\n"
        + "exportTopImage(hw05.exportimages.PNGExportManager)\n"
        + "getLayers()\n", commandLog.toString());
  }

  @Test
  public void testStartEditCreateLayerUpdateVisibility() {
    Readable input = new StringReader("interactive createLayer layer 1 setTransparent");
    Appendable output = new StringBuilder();
    ILayeredEditorController controller = new LayeredEditorController(input, output, model);
    controller.startEditor();
    assertEquals(model.getCurrentLayerName(), "layer");
    assertEquals(model.getCurrentLayerImage(), null);
    assertEquals(model.getCurrentLayerCopy().isVisible(), false);
  }

}