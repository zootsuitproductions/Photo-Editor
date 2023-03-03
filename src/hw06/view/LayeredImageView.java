package hw06.view;

import hw06.ILayer;
import java.io.IOException;
import java.util.List;

/**
 * Represents the LayeredImageView that provides a textual view for
 * an IImmutableLayeredImageEditorModel. This view only has access to the non-mutable methods
 * of the model so it does not have the ability to mutate the passed in model.
 */
public class LayeredImageView implements ILayeredImageView {

  private final IImmutableLayeredImageEditorModel model;
  private final Appendable out;

  /**
   * Constructs a LayeredImageView object that takes in the model and appends the state
   * of the layered image or any other types of messages to the client.
   *
   * @param model The IImmutableLayeredImageEditorModel that is being analyzed and visually
   *              outputted to the client.
   * @param out The appendable to which the state of the model is appended to and transmitted to
   *            the client through.
   * @throws IllegalArgumentException throws this exception if the passed in model or appendable
   *                                  are null.
   */
  public LayeredImageView(IImmutableLayeredImageEditorModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Args cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * Returns the textual representation of the model's current layered image in a String format.
   *
   * @return Returns the textual view of the model's current layered image and the different
   *         attributes of the layer.
   */
  protected String layerToString() {
    StringBuilder layeredImage = new StringBuilder();
    List<ILayer> layers = model.getLayers();
    layeredImage.append("Layered Image: \n");
    for (int i = 0; i < layers.size(); i ++) {
      layeredImage.append("Layer "
          + Integer.toString(i + 1)
          + ": " + layers.get(i).toString() + "\n");
    }
    return layeredImage.toString();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("The message cannot be null!");
    }
    out.append(message);
  }

  @Override
  public void renderLayers() throws IOException {
    out.append(this.layerToString());
  }
}
