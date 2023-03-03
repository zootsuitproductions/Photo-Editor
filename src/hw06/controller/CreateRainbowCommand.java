package hw06.controller;

import hw05.image.IImage;
import hw05.image.RainbowImageGenerator;
import hw06.ILayer;
import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a CreateRainbowCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the createRainbowCommand. This CreateRainbowCommand
 * creates a new programmatically created RainbowImage for the client specific layer inside of the
 * model. The rainbow image that is created is a set image every time. We decide the
 * inputs for the user and adjust the dimensions to fit the images that are already inside of the
 * model.
 */
public class CreateRainbowCommand extends AbstractLayerCommand {

  /**
   * Constructs a CreateRainbowCommand object that takes in the model that the createRainbowCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  CreateRainbowCommand(ILayeredImageEditorModel layeredModel,
      ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }

    int dimension1 = 300;
    int dimension2 = 500;

    try {
      String layerName = this.layeredModel.getCurrentLayerName();
      List<ILayer> layerList = this.layeredModel.getLayers();

      for (ILayer layer: layerList) {
        if (layer.getImage() != null) {
          dimension1 = layer.getImage().getPixelWidth();
          dimension2 = layer.getImage().getPixelHeight();
          break;
        }
      }

      IImage rainbow = new RainbowImageGenerator(
          dimension1, dimension2, 4).generateImage(layerName);
      this.layeredModel.replaceImage(layerName, rainbow);
      write("Loaded the rainbow image into the current layer successfully\n");
    } catch (IllegalArgumentException e) {
      write(e.getMessage() + "\n");
    }
  }
}
