import hw07.GraphicalLayeredImageView;
import hw07.IView;
import hw07.controller.IViewListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Mock view class used to test our view and controller.
 */

public class MockView extends GraphicalLayeredImageView implements IView {

  private final Appendable log;

  /**
   * Constructs a MockView.
   * @param log the log to append to.
   */

  public MockView(Appendable log) {
    super();
    this.log = log;
  }

  @Override
  public void promptMessage(String message, String title) {
    try {
      log.append(title + ": " + message + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to the log");
    }
  }

  @Override
  public void setLayers(List<String> name) {
    try {
      log.append("Updated layers to: " + name.toString() + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to the log");
    }
  }

  @Override
  public void setTopImage(BufferedImage img) {
    try {
      log.append("Refreshed the top image to that of the model\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to the log");
    }
  }

  @Override
  public void startView() {
    try {
      log.append("started the view\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to the log");
    }
  }

  /**
   * Triggers a Set Current Button Click.
   */

  public void triggerSetCurrentClick(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleSetCurrent(layerName);
    }
  }

  /**
   * Triggers a Blur Button Click.
   */
  public void triggerBlurButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "blur"));
  }

  /**
   * Triggers a Sepia Button Click.
   */

  public void triggerSepiaButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "sepia"));
  }

  /**
   * Triggers a Sharpen Button Click.
   */

  public void triggerSharpenButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "sharpen"));
  }

  /**
   * Triggers a Greyscale Button Click.
   */

  public void triggerGreyscaleButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "greyscale"));
  }

  /**
   * Triggers a Create Rainbow Button Click.
   */

  public void triggerCreateRainbowButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "createRainbow"));
  }

  /**
   * Triggers a Remove Button Click.
   */

  public void triggerRemoveButtonClick() {
    super.actionPerformed(new ActionEvent(this, 0, "removeButton"));
  }

  /**
   * Triggers a Create Layer Button Click.
   */

  public void triggerCreateLayerButtonClick(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleCreateLayer(layerName);
    }
  }

  /**
   * Triggers a Create Copy Layer Button Click.
   */

  public void triggerCreateCopyLayerClick(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleCopyCurrentLayer(layerName);
    }
  }

  /**
   * Triggers a Script Button Click.
   */

  public void triggerScriptButtonClick(String fileName) {
    for (IViewListener listener : listeners) {
      listener.handleScript(fileName);
    }
  }

  /**
   * Triggers a Load Image Button Click.
   */

  public void triggerLoadImageClick(String fileName) {
    for (IViewListener listener : listeners) {
      listener.handleLoadImage(fileName);
    }
  }

  /**
   * Triggers a Export Button Click.
   */

  public void triggerExportTopImageClick(String fileName) {
    for (IViewListener listener : listeners) {
      listener.handleExportTopImage(fileName);
    }
  }

  /**
   * Triggers a Save Button Click.
   */

  public void triggerSaveLayersClick(String folderName) {
    for (IViewListener listener : listeners) {
      listener.handleSaveLayers(folderName);
    }
  }

  /**
   * Triggers a Load Project Button Click.
   */

  public void triggerLoadLayeredImageClick(String fileName) {
    for (IViewListener listener : listeners) {
      listener.handleLoadEntireImage(fileName);
    }
  }

  /**
   * Triggers a Toggle Visibility Button Click.
   */

  public void triggerToggleVisibilityClick() {
    for (IViewListener listener : listeners) {
      listener.handleToggleVisibility();
    }
  }
}
