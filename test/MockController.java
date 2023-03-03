import hw07.controller.IViewListener;
import java.io.IOException;

/**
 * Mock Controller class used for testing our view and controller.
 */
public class MockController implements IViewListener {

  private final Appendable log;

  /**
   * Constructs a MockController.
   * @param log the log to append to.
   */
  public MockController(Appendable log) {
    this.log = log;
  }

  @Override
  public void handleBlur() {
    try {
      log.append("handleBlur");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleSepia() {
    try {
      log.append("handleSepia");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleGreyscale() {
    try {
      log.append("handleGreyscale");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleSharpen() {
    try {
      log.append("handleSharpen");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleCopyCurrentLayer(String newLayerName) {
    try {
      log.append("handleCopyCurrentLayer");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleCreateLayer(String newLayerName) {
    try {
      log.append("handleCreateLayer");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleRemoveLayer() {
    try {
      log.append("handleRemoveLayer");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public int handleSetCurrent(String layerName) {
    try {
      log.append("handleSetCurrent");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
    return 0;
  }

  @Override
  public void handleToggleVisibility() {
    try {
      log.append("handleToggleVisibility");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleCreateRainbow() {
    try {
      log.append("handleCreateRainbow");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleLoadImage(String filePath) {
    try {
      log.append("handleLoadImage");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleExportTopImage(String filePath) {
    try {
      log.append("handleExportTopImage");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleSaveLayers(String folderPath) {
    try {
      log.append("handleSaveLayers");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleLoadEntireImage(String filePath) {
    try {
      log.append("handleLoadEntireImage");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }

  @Override
  public void handleScript(String filePath) {
    try {
      log.append("handleScript");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to append to the log");
    }
  }
}
