package hw05;

import hw05.exportimages.IImageExportManager;
import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import hw05.importimages.IOManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents the PhotoEditorModel that allows for images to be imported into the model
 * and edited to the clients liking. The client can perform a series of different types of
 * photo edits to the IImages that they use in the model. This model allows for clients to add in
 * as many images as they would like into the model and they are kept track of inside of the
 * model.
 */
public class PhotoEditorModel implements IPhotoEditorModel {

  protected final Map<String, IImage> images;

  /**
   * Constructs a PhotoEditor model with a hashmap that maps each image that is imported into
   * the model to its file name. This way a client can easily access their image by the images
   * file name.
   */
  public PhotoEditorModel() {
    this.images = new HashMap<>();
  }

  /**
   * Constructs a PhotoEditor model with the same hashmap functionality as the constructor above.
   * However, this constructor allows for a client to insert multiple imported image
   * arguments into the model, not just one. Each of these imported images is again mapped to its
   * file name.
   * @param managers The multiple imported image arguments that a client wants to pass into the
   *                 model.
   * @throws IllegalArgumentException this exception is thrown if the imported images are null.
   */
  public PhotoEditorModel(IOManager... managers) {
    this();

    if (managers == null) {
      throw new IllegalArgumentException("You cannot pass in a null image!");
    }

    for (int i = 0; i < managers.length; i++) {
      if (managers[i] == null) {
        throw new IllegalArgumentException("You cannot have a null IOManager passed in!");
      }
      IImage image = managers[i].apply();
      this.images.put(image.getFileName(), image);
    }
  }

  @Override
  public IImage applyPhotoEdit(String id, IPhotoEdit photoEdit) {
    checkExistingId(id);
    if (photoEdit == null) {
      throw new IllegalArgumentException("PhotoEdit must be non-null");
    }

    return photoEdit.apply(this.images.get(id));
  }

  @Override
  public void addImage(String id, IImage image) {
    if (id == null || image == null) {
      throw new IllegalArgumentException("Arguments must be non-null");
    }
    if (id.equals("")) {
      throw new IllegalArgumentException("The id cannot be empty!");
    }
    if (this.images.containsKey(id)) {
      throw new IllegalArgumentException("This id is already being used!");
    }
    this.images.putIfAbsent(id, image);
  }

  @Override
  public IImage getImage(String id) {
    checkExistingId(id);
    return this.images.get(id);
  }

  @Override
  public void removeImage(String id) {
    checkExistingId(id);
    this.images.remove(id);
  }

  @Override
  public void replaceImage(String id, IImage image) {
    checkExistingId(id);
    if (image == null) {
      throw new IllegalArgumentException("Image must be non-null");
    }
    this.images.replace(id, image);
  }

  @Override
  public void exportImage(String id, IImageExportManager exportManager) {
    if (exportManager == null) {
      throw new IllegalArgumentException("The export manager cannot be null!");
    }
    checkExistingId(id);
    exportManager.export(this.images.get(id));
  }

  @Override
  public int getNumPhotos() {
    return this.images.size();
  }

  @Override
  public Set<String> getPhotoIds() {
    return this.images.keySet();
  }


  /**
   * Checks to see if the passed in ID already exists in the models hashmap of image id's.
   *
   * @param id The specific image id that is being checked.
   * @throws IllegalArgumentException throws this exception if the provided id is null or the
   *                                  specific id is already inside of the model.
   */
  private void checkExistingId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Id must be non-null");
    }
    if (id.equals("")) {
      throw new IllegalArgumentException("The id cannot be empty!");
    }
    if (!this.images.containsKey(id)) {
      throw new IllegalArgumentException("Invalid photo id");
    }
  }

}
