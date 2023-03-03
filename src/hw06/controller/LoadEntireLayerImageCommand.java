package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Function object that can load a saved layer image text file.
 * This command will cause every existing layer in the editor to be
 * deleted and replaced by the loaded layers, so be sure to save before loading.
 */

public class LoadEntireLayerImageCommand extends AbstractLayerCommand {

  public LoadEntireLayerImageCommand(ILayeredImageEditorModel layeredModel,
      ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }

    if (scan.hasNext()) {
      String fileName = scan.next();

      try {
        this.layeredModel.loadEntireLayeredImage(fileName);
        write("Loaded the layers into the editor successfully\n");
      } catch (IllegalArgumentException e) {
        write(e.getMessage() + "\n");
      }

    }
  }
}
