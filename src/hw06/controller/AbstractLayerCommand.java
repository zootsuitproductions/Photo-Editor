package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the AbstractLayerCommand which is an abstract class that holds all of the
 * common functionality that all ILayer commands have. All abstract layer commands
 * use the same view and model reference for their actions.
 */
public abstract class AbstractLayerCommand implements ILayerCommand {

  protected final ILayeredImageEditorModel layeredModel;
  protected final ILayeredImageView view;


  /**
   * Constructs the AbstractLayerCommand which holds the same model and view object that
   * all the layer commands have in common.
   *
   * @param layeredModel The model that the commands utilize to carry out their specific actions.
   * @param view The view that the commands utilize to append current game state and messages to.
   * @throws IllegalArgumentException throws this exception when the model and the view are null.
   */
  AbstractLayerCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    if (layeredModel == null || view == null) {
      throw new IllegalArgumentException("The args cannot be null!");
    }
    this.layeredModel = layeredModel;
    this.view = view;
  }

  /**
   * Writes the specific message to the view which is then broadcast to the client.
   *
   * @param message The specific message that is sent to the view and broadcast to the client.
   * @throws IllegalArgumentException throws this exception if the message is null
   * @throws IllegalStateException throws this exception if the message fails to be written.
   */
  protected void write(String message) {
    if (message == null) {
      throw new IllegalArgumentException("The message cannot be null!");
    }

    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to the view.");
    }
  }

  @Override
  public abstract void apply(Scanner scan);
}
