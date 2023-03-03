package hw06.controller;

import hw06.ILayeredImageEditorModel;
import hw06.view.ILayeredImageView;
import hw06.view.LayeredImageView;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the LayeredEditorController which reads user input from the client and
 * uses the given ILayeredImageEditorModel to carry out the specific instructions provided
 * by the client. This input can be read in many ways, for example, as user input from the console
 * or user input in the form a script in a file.
 */
public class LayeredEditorController implements ILayeredEditorController {

  private final Readable readable;
  private final Map<String, ILayerCommand> layerCommands;
  private final ILayeredImageView view;

  /**
   * Constructs a LayeredEditorController which takes in client input and carefully
   * carries out the given commands onto the provided ILayeredImageEditorModel.
   *
   * @param readable The readable object that the controller reads the user input from.
   * @param appendable The appendable object that the controller outputs useful messages
   *                   to and outputs the state of the current layer image being edited.
   * @param layeredImageModel The ILayeredImageEditorModel that is used by the controller to
   *                           carry out the correct user specifications.
   * @throws IllegalArgumentException throws this exception when either the readable, appendable,
   *                                  or editor model are null.
   */
  public LayeredEditorController(Readable readable, Appendable appendable,
      ILayeredImageEditorModel layeredImageModel) {
    if (readable == null || appendable == null || layeredImageModel == null) {
      throw new IllegalArgumentException("The args cannot be null!");
    }

    this.readable = readable;
    this.view = new LayeredImageView(layeredImageModel, appendable);
    this.layerCommands = new HashMap();

    this.layerCommands.putIfAbsent("blur", new BlurCommand(layeredImageModel, this.view));
    this.layerCommands.putIfAbsent("sepia", new SepiaCommand(layeredImageModel, this.view));
    this.layerCommands.putIfAbsent("sharpen", new SharpenCommand(layeredImageModel, this.view));
    this.layerCommands.putIfAbsent("greyscale", new GreyScaleCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("createLayer", new CreateLayerCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("setCurrent", new SetCurrentCommand(layeredImageModel, this.view));
    this.layerCommands.putIfAbsent("load", new UploadImageCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("saveLayers", new SaveLayersCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("setTransparent", new SetTransparentCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("setVisible", new SetVisibleCommand(layeredImageModel, this.view));
    this.layerCommands.putIfAbsent("addCopyOfCurrentLayer",
        new CopyCurrentLayerCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("exportLayerImage", new ExportImageCommand(layeredImageModel, this.view));

    this.layerCommands
        .putIfAbsent("createRainbow", new CreateRainbowCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("remove", new RemoveCommand(layeredImageModel, this.view));
    this.layerCommands
        .putIfAbsent("loadLayeredImage",
            new LoadEntireLayerImageCommand(layeredImageModel, this.view));
  }


  /**
   * Writes the specific message to the appendable object that is provided to the controller.
   *
   * @param message The message that is written to the appendable object provided to the controller.
   * @throws IllegalArgumentException throws this exception when the the message fails to write to
   *                                  the appendable or if the message is null.
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

  /**
   * Displays the current state of the layers to the appendable object that is provided to the
   * controller.
   * @throws IllegalArgumentException throws this exception when the message fails to write to
   *                                  the appendable.
   */
  protected void displayLayers() {
    try {
      this.view.renderLayers();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to the view.");
    }
  }

  @Override
  public void startEditor() {
    Scanner scan;

    scan = new Scanner(readable);
    scan = getInputType(scan);

    this.displayLayers();
    this.write("\n");
    while (scan.hasNext()) {
      String next = scan.next();

      ILayerCommand command = layerCommands.getOrDefault(next, null);

      if (command == null) {
        this.write("Invalid input: " + next + "\n");
      } else {
        command.apply(scan);
      }
      this.displayLayers();
      this.write("\n");
    }
  }

  //runs the program appropriately based on the next input to the scanner
  // (either "script" or "interactive" for each kind of input)
  private Scanner getInputType(Scanner scan) {
    this.write("Type 'script' to run a script of commands, "
        + "or type 'interactive' to input to the console\n");
    while (scan.hasNext()) {
      String next = scan.next();
      switch (next) {
        case "script":
          return getFileScanner(scan);
        case "interactive":
          return scan;
        default:
          this.write("Invalid input: " + next + "\n");
          break;
      }
    }
    return scan;
  }

  //returns a scanner from the next valid filename that is supplied,
  // after the user selects to run a script.
  private Scanner getFileScanner(Scanner scan) {
    this.write("Type the name of the script file you would like to run, "
        + "which should end in .txt\n");
    while (scan.hasNext()) {
      String next = scan.next();
      if (checkIfFile(next)) {
        try {
          return new Scanner(new FileReader(next));
        } catch (IOException e) {
          this.write("The script file provided is not valid\n");
        }
      } else {
        this.write("Invalid input: " + next + "\n");
      }
    }
    return scan;
  }

  //determines if the given filename is a valid text file.
  private boolean checkIfFile(String fileName) {
    boolean checker = false;
    if (fileName == null) {
      checker = false;
    }

    if (fileName.endsWith(".txt")) {
      checker = true;
    }
    return checker;
  }
}



