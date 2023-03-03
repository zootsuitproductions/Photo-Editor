package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a CreateLayerCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the createLayerCommand. This createLayerCommand
 * adds a new layer to the model, automatically with a blank null image inside of it.
 */
public class CreateLayerCommand extends AbstractLayerCommand {

  /**
   * Constructs a CreateLayerCommand object that takes in the model that the CreateLayerCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  CreateLayerCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }

    if (scan.hasNext()) {
      String layerName = scan.next();
      try {
        layeredModel.addImage(layerName, null);
        write("created a new layer successfully\n");
      } catch (IllegalArgumentException e) {
        write(e.getMessage() + "\n");
      }
    }
  }
}
