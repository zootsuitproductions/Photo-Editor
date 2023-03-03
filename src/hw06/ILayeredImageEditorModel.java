package hw06;

import hw05.IPhotoEditorModel;
import hw05.exportimages.IImageExportManager;
import hw05.imageedits.IPhotoEdit;
import hw06.view.IImmutableLayeredImageEditorModel;


/**
 * Represents the ILayeredImageEditorModel that allows for a client to create an image that has
 * multiple layers of images. Every image is stacked on top of each other and a client still has the
 * functionality to make edits to individual images inside of the layered image. This is the
 * interface for the ILayeredImageEditorModel but only contains the mutable methods that model holds
 * for the purpose of not giving clients (such as the view) access to mutable methods that the model
 * holds. With this model, a client can apply edits to individual layers, make layers current, make
 * layers visible and transparent, save a layered image, add copies of a current layer, add blank
 * layers, and export the top most visible image in a layered image.
 */
public interface ILayeredImageEditorModel extends IImmutableLayeredImageEditorModel,
    IPhotoEditorModel {

  /**
   * Applies the given IPhotoEdit to the image that resides in the current layer of the layered
   * image. The current layer in the image can be manipulated by the client.
   *
   * @param edit The specific edit the client wants to apply to a layer in the layered image.
   * @throws IllegalArgumentException throws this exception if the current index is invalid in the
   *                                  layered list or of the passed in edit is null.
   */
  void applyEditToCurrentLayer(IPhotoEdit edit);

  /**
   * Sets the layer found from the given layer name as the current layer in the layered image.
   *
   * @param layerName The layer name of the layer in the image that will be set to current.
   * @throws IllegalArgumentException throw this exception if this layer name is null or if the
   *                                  layer name does not exist in the layered image
   */
  void setCurrentLayer(String layerName);

  /**
   * Allows for a client to apply a specified photoEdit to a specific layer in the image that is
   * found by the provided layer name.
   *
   * @param layerName The layer name that identifies a layer in the image that a client wants to
   *                  apply an edit too.
   * @param photoEdit The photo edit that the client wants to apply onto the identified layer from
   *                  the layer name
   * @throws IllegalArgumentException throws this exception if the layer name/photo edit is null or
   *                                  if no layer exists in the layered image with that layer name.
   */
  void applyEditToLayer(String layerName, IPhotoEdit photoEdit);

  /**
   * Allows for a client to set a specific layer in the layered image as visible.
   *
   * @param layerName The layer name of the layer in the layered image that the client wants to make
   *                  visible
   * @throws IllegalArgumentException throws this exception if the layer name does not correspond to
   *                                  a layer that exists in the layered image.
   */
  void setLayerVisible(String layerName);


  /**
   * Allows for a client to set a specific layer in the layered image as transparent.
   *
   * @param layerName The layer name of the layer in the layered image that the client wants to make
   *                  transparent
   * @throws IllegalArgumentException throws this exception if the layer name does not correspond to
   *                                  a layer that exists in the layered image.
   */
  void setLayerTransparent(String layerName);

  /**
   * Allows for a client to save all of the images that are inside of the layered image to a
   * specific folder name that they want them to be in. Inside of the of the folders will be all of
   * the images and a text file that describes the location of all the images for the client.
   *
   * @param folder The name of the folder that the client wants for their saved layered image.
   * @throws IllegalArgumentException throws this exception if the the provided folder name is null
   *                                  or if the folder name already exists.
   */
  void saveLayeredImage(String folder);

  /**
   * Adds a copy of an existing layer in the layered image to the layered image.
   *
   * @param idOfImage         The layer name of the layer that the client wants to add a copy of.
   * @param newIdForImageCopy The new layer name for the copy layer that will be created.
   * @throws IllegalArgumentException throws this exception if the given layer name for the copied
   *                                  layer and the layer name for the new layer are null or if the
   *                                  layer name for the copied layer does not exist in the layered
   *                                  image.
   */
  void addCopyLayer(String idOfImage, String newIdForImageCopy);

  /**
   * Adds a copy of the "current" layer in the layered image to the layered image.
   *
   * @param newIdForImageCopy The new layer name for the copied current layer that will be created.
   * @throws IllegalArgumentException throws this exception if the layer name for the new layer is
   *                                  null or if the layer name for the copied layer does not exist
   *                                  in the layered image.
   */
  void addCopyCurrentLayer(String newIdForImageCopy);

  /**
   * Exports the top most visible image in the layered image to the provided file format through the
   * IImageExportManager chosen by the client.
   *
   * @param manager The IImageExportManager that the client chooses to use to export the top most
   *                visible image in the layered image.
   * @throws IllegalArgumentException throws this exception if the import manager is null, if there
   *                                  are no images in the layered images, or if there are no
   *                                  visible images in the layered image.
   */
  void exportTopImage(IImageExportManager manager);

  /**
   * Loads a saved layered image into the model for editing, given the file name of
   * the text file. The file needs to be in the same format as the one exported
   * by saveLayerImage, and specifies the file locations of each image in the layered image.
   *
   * @param textFileName the name of the text file to load
   * @throws IllegalArgumentException if the name is null, the file provided is invalid,
   *                                  or it cannot be read.
   */
  void loadEntireLayeredImage(String textFileName) throws IllegalArgumentException;

}
