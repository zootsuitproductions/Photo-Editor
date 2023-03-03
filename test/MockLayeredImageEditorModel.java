import hw05.exportimages.IImageExportManager;
import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import hw06.ILayer;
import hw06.ILayeredImageEditorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Mock class for testing the controller. All Methods report that they were called, along with
 * their supplied input, to the Appendable log.
 */
public class MockLayeredImageEditorModel implements ILayeredImageEditorModel {

  private final Appendable log;

  public MockLayeredImageEditorModel(Appendable out) {
    this.log = out;
  }

  @Override
  public IImage applyPhotoEdit(String photoId, IPhotoEdit photoEdit) {
    try {
      log.append("applyPhotoEdit(" + photoId + ", " + photoEdit.getClass().getName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return null;
  }

  @Override
  public IImage getImage(String id) {
    try {
      log.append("getImage(" + id + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return null;
  }

  @Override
  public int getNumPhotos() {
    try {
      log.append("getNumPhotos()\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return 0;
  }

  @Override
  public Set<String> getPhotoIds() {
    try {
      log.append("getPhotoIds()\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return null;
  }

  @Override
  public void addImage(String id, IImage image) {
    try {
      if (image == null) {
        log.append("addImage(" + id + ", image: null)\n");
      } else {
        log.append("addImage(" + id + ", image: " + image.getFileName() + ")\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void removeImage(String id) {
    try {
      log.append("removeImage(" + id + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void replaceImage(String id, IImage image) {
    try {
      log.append("replaceImage(" + id + ", image: " + image.getFileName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void exportImage(String id, IImageExportManager exportManager) {
    try {
      log.append("exportImage(" + id + ", " + exportManager.getClass().getName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void applyEditToCurrentLayer(IPhotoEdit edit) {
    try {
      log.append("applyEditToCurrentLayer(" + edit.getClass().getName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void setCurrentLayer(String layerName) {
    try {
      log.append("setCurrentLayer(" + layerName + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void applyEditToLayer(String layerName, IPhotoEdit photoEdit) {
    try {
      log.append("applyEditToLayer(" + layerName + ", " + photoEdit.getClass().getName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void setLayerVisible(String layerName) {
    try {
      log.append("setLayerVisible(" + layerName + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void setLayerTransparent(String layerName) {
    try {
      log.append("setLayerTransparent(" + layerName + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void saveLayeredImage(String folder) {
    try {
      log.append("saveLayeredImage(" + folder + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void addCopyLayer(String idOfImage, String newIdForImageCopy) {
    try {
      log.append("addCopyLayer(" + idOfImage + ", " + newIdForImageCopy + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void addCopyCurrentLayer(String newIdForImageCopy) {
    try {
      log.append("addCopyCurrentLayer(" + newIdForImageCopy + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void exportTopImage(IImageExportManager manager) {
    try {
      log.append("exportTopImage(" + manager.getClass().getName() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public void loadEntireLayeredImage(String textFileName) throws IllegalArgumentException {
    try {
      log.append("loadEntireLayeredImage(" + textFileName + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
  }

  @Override
  public boolean isVisible(String layerName) {
    try {
      log.append("exportTopImage(" + layerName + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return true;
  }

  @Override
  public String getCurrentLayerName() {
    try {
      log.append("getCurrentLayerName(" + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return "getCurrentLayerName(" + ")";
  }

  @Override
  public IImage getCurrentLayerImage() {
    try {
      log.append("getCurrentLayerImage(" + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return null;
  }

  @Override
  public ILayer getCurrentLayerCopy() {
    try {
      log.append("getCurrentLayerCopy(" + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return null;
  }

  @Override
  public List<ILayer> getLayers() {
    try {
      log.append("getLayers(" + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to append to the mock log");
    }
    return new ArrayList<>();
  }

}
