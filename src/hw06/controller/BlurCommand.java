package hw06.controller;

import hw05.imageedits.BlurFilter;
import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Constructs a BlurCommand that represents a function object whose whole purpose is too handle
 * when a client calls upon the blurring command. This blurring command applies the blur
 * filter onto the current layer in the given model.
 */
public class BlurCommand extends AbstractLayerCommand {

  /**
   * Constructs a BlurCommand object that takes in the model that the blur action can be
   * called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  BlurCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }
    try {
      layeredModel.applyEditToCurrentLayer(new BlurFilter());
      write("Applied blur to the current layer successfully" + "\n");
    } catch (IllegalArgumentException e) {
      write(e.getMessage() + "\n");
    }
  }
}
