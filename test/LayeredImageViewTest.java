import static org.junit.Assert.assertEquals;

import hw05.image.RainbowImageGenerator;
import hw06.ILayeredImageEditorModel;
import hw06.LayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import hw06.view.LayeredImageView;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for LayeredImageView.
 */
public class LayeredImageViewTest {
  
  protected ILayeredImageEditorModel model;
  protected ILayeredImageView view;

  @Before
  public void setUp() throws Exception {
    this.model = new LayeredImageEditorModel();
    this.view = new LayeredImageView(this.model, System.out);
  }

  @Test
  public void testRenderLayersEmptyListOfLayers() {
    StringBuilder output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    try {
      view.renderLayers();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }

    assertEquals("Layered Image: \n", output.toString());
  }

  @Test
  public void testRenderLayersTwice() {
    StringBuilder output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    try {
      view.renderLayers();
      view.renderLayers();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }

    assertEquals("Layered Image: \n"
        + "Layered Image: \n", output.toString());
  }


  @Test
  public void testrenderLayer() {
    StringBuilder output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);

    model.addImage("Layer 1", null);
    try {
      view.renderLayers();
      view.renderLayers();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }

    assertEquals("Layered Image: \n"
        + "Layer 1: Layer 1 | visible: true | image file: no image\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 1 | visible: true | image file: no image\n", output.toString());
  }

  @Test
  public void testUpdatingLayerContents() {
    StringBuilder output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    model.addImage("Layer 1", null);
    try {
      view.renderLayers();
      model.setLayerTransparent("Layer 1");
      view.renderLayers();
      model.replaceImage("Layer 1", new RainbowImageGenerator(1,1,1).generateImage("test.rainbow"));
      view.renderLayers();
      model.addImage("Layer 2", null);
      view.renderLayers();
      model.removeImage("Layer 1");
      view.renderLayers();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }

    assertEquals("Layered Image: \n"
        + "Layer 1: Layer 1 | visible: true | image file: no image\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 1 | visible: false | image file: no image\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 1 | visible: false | image file: test.rainbow\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 1 | visible: false | image file: test.rainbow\n"
        + "Layer 2: Layer 2 | visible: true | image file: no image\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 2 | visible: true | image file: no image\n", output.toString());
  }

  @Test
  public void testRenderMessage() {
    Appendable output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);

    try {
      view.renderMessage("Hello World");
      model.addImage("Layer 1", null);
      view.renderMessage("Hi World\n");
      view.renderLayers();
      view.renderMessage("Hello World");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }
    assertEquals("Hello WorldHi World\n"
        + "Layered Image: \n"
        + "Layer 1: Layer 1 | visible: true | image file: no image\n"
        + "Hello World", output.toString());

  }

  @Test
  public void testRenderImageAndMessage() {
    Appendable output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    try {
      view.renderMessage("Hello World");
      view.renderMessage("Hi World\n");
      view.renderMessage("Hello World");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }
    assertEquals("Hello WorldHi World\nHello World", output.toString());

  }

  @Test(expected = IOException.class)
  public void testRenderMessageException() throws IOException {
    Appendable output = new ErrorAppendable();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    view.renderMessage("Hello World");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNull() {
    StringBuilder output = new StringBuilder();
    ILayeredImageView view = new LayeredImageView(this.model, output);
    try {
      view.renderMessage(null);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render");
    }
  }


}