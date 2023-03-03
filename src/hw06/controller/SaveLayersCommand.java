package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a SaveLayersCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the saveLayersCommand. This SaveLayersCommand
 * saves all of the images in the layers in the layered image to the same folder
 * specified by the client to the PNG format.
 */
public class SaveLayersCommand extends AbstractLayerCommand {

  /**
   * Constructs a SaveLayersCommand object that takes in the model that the saveLayersCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  SaveLayersCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }
    if (scan.hasNext()) {
      String folder = scan.next();
      try {
        layeredModel.saveLayeredImage(folder);
        write("saved the layered image successfully\n");
      } catch (IllegalArgumentException e) {
        write(e.getMessage() + "\n");
      }
    }

  }
}
