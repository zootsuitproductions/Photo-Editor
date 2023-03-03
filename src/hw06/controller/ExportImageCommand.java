package hw06.controller;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.JPEGExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.exportimages.PPMExportManager;
import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import java.util.Scanner;

/**
 * Represents a ExportImageCommand that represents a function object whose whole purpose is too
 * handle when a client calls upon the exportImageCommand. This ExportImageCommand
 * tries to export the top most visible image that is inside of the given layered image model.
 * Nothing is exported if there is no top most visible image that is inside of the model.
 */
public class ExportImageCommand extends AbstractLayerCommand {

  /**
   * Constructs a ExportImageCommand object that takes in the model that the exportImageCommand
   * action can be called upon and carried out. The view is passed in so that useful messages can be
   * sent back to the client depending on the success of the command actions.
   *
   * @param layeredModel The ILayeredImageEditorModel that the command actions are carried out on
   * @param view The view that helps transmit the useful messages to the client.
   * @throws IllegalArgumentException throws this exception when either the view or model are null
   */
  ExportImageCommand(ILayeredImageEditorModel layeredModel, ILayeredImageView view) {
    super(layeredModel, view);
  }

  @Override
  public void apply(Scanner scan) {
    if (scan == null ) {
      throw new IllegalArgumentException("Sanner cannot be null");
    }
    if (scan.hasNext()) {
      String fileName = scan.next();

      String[] tokens = fileName.split("\\.");
      if (tokens.length < 2) {
        write("Invalid file format for exporting!\n");
      }


      IImageExportManager manager = null;

      String fileType = tokens[tokens.length - 1].toLowerCase();

      switch (fileType) {
        case "ppm":
          manager = new PPMExportManager(fileName);
          break;
        case "png":
          manager = new PNGExportManager(fileName);
          break;
        case "jpeg":
          manager = new JPEGExportManager(fileName);
          break;
        case "jpg":
          manager = new JPEGExportManager(fileName);
          break;
        default:
          write("Invalid ending file format for file exporting!\n");
          return;
      }

      try {
        layeredModel.exportTopImage(manager);
        write("exported the image successfully\n");
      } catch (IllegalArgumentException e) {
        write(e.getMessage() + "\n");
      }
    }
  }
}
