import hw06.ILayeredImageEditorModel;
import hw06.LayeredImageEditorModel;
import hw06.controller.ILayeredEditorController;
import hw06.controller.LayeredEditorController;
import hw07.GraphicalLayeredImageView;
import hw07.controller.GraphicalController;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Main class that runs the layered photo editor program in the way that is specified by the
 * client: interactive, script, or text.
 */

public class Main {

  /**
   * Constructs the main method that runs the layered image program in the way that the client
   * specifies.
   *
   * @param args Arguments that the client can pass in to signal which type of way they want
   *             the program to be run: interactive, script, or texts.
   * @throws IllegalArgumentException throws this exception if the passed in arguments are not
   *                                  valid and do not correlate to the correct identifiers for
   *                                  which type of way the program should be run.
   */
  public static void main(String[] args) {

    if (args.length > 2) {
      throw new IllegalArgumentException(
          "You cannot have more than two arguments to run this program!");
    }

    if (args.length <= 0) {
      throw new IllegalArgumentException("You need to pass in an argument into the main runner"
          + " for the program!");
    }

    switch (args[0]) {
      case "-script":
        if (args.length != 2) {
          throw new IllegalArgumentException("You need to have a file path after requesting the "
              + "script version of the program!");
        }

        Appendable appendable = System.out;
        StringReader readable = new StringReader("script " + args[1]);
        ILayeredImageEditorModel model = new LayeredImageEditorModel();
        ILayeredEditorController controller = new LayeredEditorController(readable, appendable,
            model);
        controller.startEditor();
        break;
      case "-text":
        Appendable appendable2 = System.out;
        Readable readable2 = new InputStreamReader(System.in);
        ILayeredImageEditorModel model2 = new LayeredImageEditorModel();
        ILayeredEditorController controller2 = new LayeredEditorController(readable2, appendable2,
            model2);
        controller2.startEditor();
        break;
      case "-interactive":
        ILayeredImageEditorModel graphicalModel = new LayeredImageEditorModel();
        GraphicalLayeredImageView view = new GraphicalLayeredImageView();
        GraphicalController graphicalController = new GraphicalController(graphicalModel, view);
        graphicalController.startEditor();
        break;
      default:
        throw new IllegalArgumentException("You did not provide a correct argument for the "
            + "program!");
    }
  }
}
