package hw06.controller;

import hw05.imageedits.SepiaTransformation;
import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a SepiaCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the SepiaCommand. This SepiaCommand applies the
 * sepia filter to the "current" layer inside of the layered image.
 */
public class SepiaCommand extends AbstractLayerCommand {

  /**
   * Constructs a SepiaCommand object that takes in the model that the SepiaCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  SepiaCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }
    try {
      layeredModel.applyEditToCurrentLayer(new SepiaTransformation());
      write("Applied sepia to the current layer successfully\n");
    } catch (IllegalArgumentException e) {
      write(e.getMessage() + "\n");
    }

  }
}
