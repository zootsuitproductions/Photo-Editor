package hw07.controller;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.JPEGExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.exportimages.PPMExportManager;
import hw05.image.IImage;
import hw05.image.RainbowImageGenerator;
import hw05.imageedits.BlurFilter;
import hw05.imageedits.GreyScaleTransformation;
import hw05.imageedits.SepiaTransformation;
import hw05.imageedits.SharpenFilter;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.importimages.PPMImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.ITransparentPixel;
import hw06.ILayer;
import hw06.ILayeredImageEditorModel;
import hw06.controller.ILayeredEditorController;
import hw06.controller.LayeredEditorController;
import hw07.IView;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Graphical controller which handles the events that occur when a user interacts
 * with the GraphicalLayeredImageView. The GraphicalLayeredImageView is a GUI and not a textual
 * view.
 */
public class GraphicalController implements IViewListener, ILayeredEditorController {

  private final ILayeredImageEditorModel model;
  private final IView view;

  /**
   * Constructs a Graphical Controller with an ILayeredImageEditorModel and an IView that represents
   * the view for the GUI.
   *
   * @param model The ILayeredImageEditorModel that the controller updates from the input from the
   *              view.
   * @param view  The view that takes in the user input and passes in the correct information to the
   *              controller.
   * @throws IllegalArgumentException throws this exception when the model or view are null.
   */
  public GraphicalController(ILayeredImageEditorModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("The model or view cannot be null!");
    }
    this.model = model;
    this.view = view;
    view.addViewEventListener(this);
  }

  @Override
  public void handleBlur() {
    try {
      model.applyEditToCurrentLayer(new BlurFilter());
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleSepia() {
    try {
      model.applyEditToCurrentLayer(new SepiaTransformation());
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleGreyscale() {
    try {
      model.applyEditToCurrentLayer(new GreyScaleTransformation());
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleSharpen() {
    try {
      model.applyEditToCurrentLayer(new SharpenFilter());
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleCopyCurrentLayer(String newLayerName) {
    try {
      model.addCopyCurrentLayer(newLayerName);
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleCreateLayer(String newLayerName) {
    try {
      model.addImage(newLayerName, null);
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }

    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleRemoveLayer() {
    try {
      String currentLayerName = model.getCurrentLayerName();
      model.removeImage(currentLayerName);
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }

    sendLayersToView();
    view.setTopImage(getTopImageOfModel());

  }

  /**
   * Sends the current visual representation of the layers in the model to the view so the graphical
   * view can output it to the user.
   */
  private void sendLayersToView() {
    List<String> layers = new ArrayList<>();
    for (int i = 1; i <= model.getNumPhotos(); i++) {
      ILayer layer = model.getLayers().get(i - 1);
      if (layer.isVisible()) {
        layers.add("◆ Layer " + i + ": " + layer.getName());
      } else {
        layers.add("◇ Layer " + i + ": " + layer.getName());
      }
    }
    view.setLayers(layers);
  }


  @Override
  public int handleSetCurrent(String layerName) {
    int currentIndex = -1;
    try {
      model.setCurrentLayer(layerName);
      currentIndex = model.getLayers().indexOf(model.getCurrentLayerCopy());
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    view.setTopImage(getTopImageOfModel());
    return currentIndex;
  }

  @Override
  public void handleToggleVisibility() {
    try {
      ILayer layer = this.model.getCurrentLayerCopy();
      if (layer == null) {
        throw new IllegalArgumentException("Must have a layer selected!");
      } else if (layer.isVisible()) {
        this.model.setLayerTransparent(layer.getName());
      } else {
        this.model.setLayerVisible(layer.getName());
      }
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }

    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleCreateRainbow() {
    int dimension1 = 300;
    int dimension2 = 500;

    try {
      String layerName = this.model.getCurrentLayerName();
      List<ILayer> layerList = this.model.getLayers();

      for (ILayer layer : layerList) {
        if (layer.getImage() != null) {
          dimension1 = layer.getImage().getPixelWidth();
          dimension2 = layer.getImage().getPixelHeight();
          break;
        }
      }

      IImage rainbow = new RainbowImageGenerator(
          dimension1, dimension2, 4).generateImage(layerName);
      this.model.replaceImage(layerName, rainbow);
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }

    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleLoadImage(String fileName) {
    if (fileName == null) {
      view.promptMessage("The provided file name is null!", "Error");
      return;
    }

    String[] tokens = fileName.split("\\.");
    if (tokens.length < 2) {
      view.promptMessage("Invalid file format for uploading!", "Error");
      return;
    }

    IOManager manager = null;

    String fileType = tokens[tokens.length - 1].toLowerCase();

    switch (fileType) {
      case "ppm":
        try {
          manager = new PPMImportManager(fileName);
        } catch (IllegalArgumentException e) {
          view.promptMessage("File not found", "Error");
        }
        break;
      case "png":
        try {
          manager = new PNGImportManager(fileName);
        } catch (IllegalArgumentException e) {
          view.promptMessage("File not found", "Error");
        }
        break;
      case "jpeg":
      case "jpg":
        try {
          manager = new JPEGImportManager(fileName);
        } catch (IllegalArgumentException e) {
          view.promptMessage("File not found", "Error");
        }
        break;
      default:
        view.promptMessage("Invalid file format for uploading!", "Error");
        break;
    }

    if (manager != null) {
      try {
        String layerName = this.model.getCurrentLayerName();
        IImage image = manager.apply();
        this.model.replaceImage(layerName, image);
      } catch (IllegalArgumentException e) {
        view.promptMessage(e.getMessage(), "Error");
      }
    }

    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleExportTopImage(String fileName) {
    if (fileName == null) {
      view.promptMessage("The file name cannot be null!", "Error");
      return;
    }

    String[] tokens = fileName.split("\\.");
    if (tokens.length < 2) {
      view.promptMessage("Invalid file format for exporting!", "Error");
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
        view.promptMessage("Invalid file format for exporting!", "Error");
        return;
    }

    try {
      model.exportTopImage(manager);
      view.promptMessage("Successfully exported the top visible image as " + fileName,
          "Success!");
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }

  }

  @Override
  public void handleSaveLayers(String folderPath) {
    try {
      model.saveLayeredImage(folderPath);
      view.promptMessage("Successfully saved the layered image to " + folderPath,
          "Success!");
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
  }

  @Override
  public void handleLoadEntireImage(String filePath) {
    try {
      model.loadEntireLayeredImage(filePath);
    } catch (IllegalArgumentException e) {
      view.promptMessage(e.getMessage(), "Error");
    }
    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  @Override
  public void handleScript(String filePath) {
    //maybe added by me?
    if (filePath == null) {
      view.promptMessage("The script file cannot be null!", "Error");
      return;
    }

    if (!filePath.endsWith(".txt")) {
      view.promptMessage("The script file needs to end with .txt", "Error");
      return;
    }

    BufferedReader reader;
    StringBuilder builder = new StringBuilder("interactive ");
    try {
      reader = new BufferedReader(new FileReader(filePath));
      String line = reader.readLine();
      while (line != null) {
        builder.append(line);
        builder.append("\n");
        line = reader.readLine();
      }
    } catch (IOException e) {
      view.promptMessage("Could not read from the file, or it doesn't exist", "Error");
      return;
    }

    StringReader finalReader = new StringReader(builder.toString());
    Appendable appendable = System.out;
    ILayeredEditorController controller = new LayeredEditorController(finalReader, appendable,
        model);
    try {
      controller.startEditor();
    } catch (IllegalStateException e) {
      view.promptMessage(e.getMessage(), "Error");
      return;
    } catch (IllegalArgumentException f) {
      view.promptMessage(f.getMessage(), "Error");
      return;
    }
    view.promptMessage("The script was processed successfully!", "Success");
    sendLayersToView();
    view.setTopImage(getTopImageOfModel());
  }

  /**
   * Converts the top most visible image in the model into a buffered image and returns it.
   *
   * @return Returns the top most visible image in the model as a buffered image or null if there
   *         are no visible images.
   */
  private BufferedImage getTopImageOfModel() {

    for (ILayer layer : model.getLayers()) {
      if (layer.isVisible() && layer.getImage() != null) {
        return convertToBufferedImage(layer.getImage());
      }
    }
    return null;
  }

  /**
   * Converts an image that is passed in to a buffered image with specific types of pixels depending
   * on if the image needs to handle transparent values or not.
   *
   * @param image The image that is converted into a buffered image.
   * @return Returns the passed in image as a buffered image.
   */
  private BufferedImage convertToBufferedImage(IImage image) {
    if (image == null) {
      return null;
    }
    BufferedImage bufferedImage;
    if (image.getPixelAt(0, 0) instanceof ITransparentPixel) {
      bufferedImage = new BufferedImage(image.getPixelWidth(), image.getPixelHeight(),
          BufferedImage.TYPE_INT_ARGB);
    } else {
      bufferedImage = new BufferedImage(image.getPixelWidth(), image.getPixelHeight(),
          BufferedImage.TYPE_INT_RGB);
    }

    for (int i = 0; i < image.getPixelHeight(); i++) {
      for (int j = 0; j < image.getPixelWidth(); j++) {
        try {
          ITransparentPixel pixel = (ITransparentPixel) image.getPixelAt(i, j);

          int col = (pixel.getAlpha() << 24) | (pixel.getRed() << 16)
              | (pixel.getGreen() << 8) | pixel.getBlue();

          bufferedImage.setRGB(j, i, col);
        } catch (ClassCastException e) {
          IPixel pixel = image.getPixelAt(i, j);

          int col = (pixel.getRed() << 16)
              | (pixel.getGreen() << 8) | pixel.getBlue();

          bufferedImage.setRGB(j, i, col);
        }
      }
    }
    return bufferedImage;
  }

  @Override
  public void startEditor() {
    view.startView();
  }
}
