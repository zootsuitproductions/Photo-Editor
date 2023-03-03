package hw05;

import hw05.exportimages.IImageExportManager;
import hw05.image.IImage;

/**
 * This is the interface for the IPhotoEditorModel. This interface allows for a client
 * to make different types of photo edits to the different photos that they may import
 * into the model. The model allows for as many images as the client wants to be added
 * to the model for editing purposes.New images are automatically created by the model
 * when a user wants to make an edit, however, they have a choice to
 * replace the old photo or still keep it.
 */
public interface IPhotoEditorModel extends IImmutablePhotoEditorModel {


  /**
   * Adds an image to the model that can be be edited however the client would like for it
   * to be edited. The image is kept track of in the model using its photoId.
   *
   * @param id The id of the photo that helps the model keep track of the specific photo that
   *           the client wants to add to the model.
   * @param image The image that the client wants to add to the model.
   * @throws IllegalArgumentException this exception is thrown when the photoId or IImage
   *                                  are null or when the provided photoId already exists inside
   *                                  of the model.
   */
  void addImage(String id, IImage image);


  /**
   * Removes the specified photo from the model. The clients chooses which image they want to
   * remove from the model through the specific photoId.
   *
   * @param id The specific photoId of the image that the client wants to remove.
   * @throws IllegalArgumentException this exception is thrown when the photoId is null or when the
   *                                   provided photoId does not exist inside of the model.
   */
  void removeImage(String id);

  /**
   * Identifies a specific photo in the model that the client asks for with the photoId and
   * then replaces that particular photo with a new desired image that the client wants
   * in its place instead.
   *
   * @param id The photoId of the image that is being replaced.
   * @param image The image that client wants to replace a different image with.
   * @throws IllegalArgumentException this exception is thrown when the photoId or IImage
   *                                  are null or when the provided photoId does not exist inside
   *                                  of the model.
   */
  void replaceImage(String id, IImage image);

  /**
   * Exports a specific IImage that a client identifies through an IImage's photoId.
   * The IImage is exported for the client in PPM format.
   *
   * @param id The photoId of the hw05.image.IImage that the client wants to export.
   * @throws IllegalArgumentException this exception is thrown when the photoId is null or when the
   *                                   provided photoId does not exist inside of the model.
   */
  void exportImage(String id, IImageExportManager exportManager);


}
