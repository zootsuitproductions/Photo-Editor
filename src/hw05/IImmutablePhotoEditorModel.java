package hw05;

import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import java.util.Set;


/**
 * This is the interface for the IPhotoEditorModel, but only the for the functions that are not
 * mutable. We crated this version of the model in order to prevent any clients from being able
 * to mutate the stae of the model, if a model object was being passed through into
 * another function as the argument. The normal PhotoEditor model holds the mutable functions for
 * this model and would allow for clients to actually change the state of the model. This
 * design gives us flexibility when passing around model objects in other functions!
 */
public interface IImmutablePhotoEditorModel {

  /**
   * Applies the specific photo edit to the specific photo that the client signals to be edited
   * by the photoId of the of the added photo. The photo edit does not replace the image that
   * is asked to be edited, instead, the method returns a new IImage with the correct changes.
   *
   * @param photoId The photoId that the desired edited photo has attached to it.
   * @param photoEdit The specific type of edit that the client wants to make to their photo.
   * @return Returns a new image with the new specified edit that the client asked for.
   * @throws IllegalArgumentException this exception is thrown when the photoId or photoEdit
   *                                  are null or when the provided photoId does not exist inside
   *                                  of the model.
   */
  IImage applyPhotoEdit(String photoId, IPhotoEdit photoEdit);



  /**
   * Gets the specific image from the model that a user wants to be returned. The client chooses
   * which image they want to be returned through the specific photoID.
   *
   * @param id The photoId of the specific image that the clients wants the model to return.
   * @return Returns an image from the model that the client identifies with the photoId.
   * @throws IllegalArgumentException this exception is thrown when the photoId is null or when the
   *                                  provided photoId does not exist inside of the model.
   */
  IImage getImage(String id);


  /**
   * Returns the amount of photos that exist in the model that are available for photo edits
   * or any of the other functionality available in the model.
   *
   * @return Returns the amount of photos that exist inside of the model.
   */
  int getNumPhotos();

  /**
   * Returns all of the photoId's that exist for every single IImage inside of the model.
   *
   * @return Returns all of the IImage representative photoId's that exist inside of the model.
   */
  Set<String> getPhotoIds();

}
