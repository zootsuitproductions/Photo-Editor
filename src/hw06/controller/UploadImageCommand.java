package hw06.controller;

import hw05.image.IImage;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.importimages.PPMImportManager;
import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a UploadImageCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the UploadImageCommand. This UploadImageCommand allows
 * for a user to upload their desired image into the "current" layer of the layered image model.
 */
public class UploadImageCommand extends AbstractLayerCommand {

  /**
   * Constructs a UploadImageCommand object that takes in the model that the
   * UploadImageCommand action can be called upon and carried out. The view is passed in so
   * that useful messages can be sent back to the client depending on the success of the command
   * actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  UploadImageCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null) {
      throw new IllegalArgumentException("The scanner cannot be null!");
    }

    if (scan.hasNext()) {
      String fileName = scan.next();

      String[] tokens = fileName.split("\\.");
      if (tokens.length < 2) {
        write("Invalid file format for uploading!\n");
        return;
      }

      IOManager manager = null;

      String fileType = tokens[tokens.length - 1].toLowerCase();

      switch (fileType) {
        case "ppm":
          try {
            manager = new PPMImportManager(fileName);
          } catch (IllegalArgumentException e) {
            write("File not found\n");
          }
          break;
        case "png":
          try {
            manager = new PNGImportManager(fileName);
          } catch (IllegalArgumentException e) {
            write("File not found\n");
          }
          break;
        case "jpeg":
        case "jpg":
          try {
            manager = new JPEGImportManager(fileName);
          } catch (IllegalArgumentException e) {
            write("File not found\n");
          }
          break;
        default:
          write("Invalid ending file format for file uploading!\n");
          break;
      }
      if (manager != null) {
        try {
          String layerName = this.layeredModel.getCurrentLayerName();
          IImage image = manager.apply();
          this.layeredModel.replaceImage(layerName, image);
          write("Loaded the image into the current layer successfully\n");
        } catch (IllegalArgumentException e) {
          write(e.getMessage() + "\n");
        }
      }
    }
  }
}
