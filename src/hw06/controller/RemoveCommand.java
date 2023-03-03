package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a RemoveCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the removeCommand. This RemoveCommand
 * removes the specific layer that the client ask to be removed from the layered image model.
 */
public class RemoveCommand extends AbstractLayerCommand {

  /**
   * Constructs a RemoveCommand object that takes in the model that the RemoveCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  RemoveCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
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
        layeredModel.removeImage(layerName);
        write("Removed the layer successfully!\n");
      } catch (IllegalArgumentException e) {
        write(e.getMessage() + "\n");
      }
    }
  }
}
