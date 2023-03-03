package hw06.view;

import hw05.IImmutablePhotoEditorModel;
import hw05.image.IImage;
import hw06.ILayer;
import java.util.List;

/**
 * Represents the IImmutableLayeredImageEditorModel. This model represents all of the functionality
 * for the ILayeredImageEditorModel that does not allow for a client to mutate the current
 * layered image that exists in the model. This prevents outside clients from accessing the
 * layered image in the model and accidentally mutating it. This interface is useful for views
 * as we do not want to give that client the functionality to mutate the layered image,
 * but only view it.
 */
public interface IImmutableLayeredImageEditorModel extends IImmutablePhotoEditorModel {

  /**
   * Returns if a specific layer in the layered image, identified by the layer name, is visible
   * or not.
   *
   * @param layerName The layer name of the layer in the layered image that a client wants to check
   *                  if it is visible or not.
   * @return Returns if a specific layer in the layered image is visible or not.
   * @throws IllegalArgumentException throws this exception if the provided layer name is null
   *                                  or if it does not correspond to a layer that exists in the
   *                                  layered image.
   */
  boolean isVisible(String layerName);

  /**
   * Gets the name of the layer in the layered image that is the "current".
   *
   * @return Returns the name of the layer in the image that is "current".
   * @throws IllegalArgumentException throws this exception if there are no images inside of the
   *                                  layered image when called.
   */
  String getCurrentLayerName();

  /**
   * Gets the image of the layer in the layered image that is the "current".
   *
   * @return Returns the image of the layer in the image that is "current".
   * @throws IllegalArgumentException throws this exception if there are no images inside of the
   *                                  layered image when called.
   */
  IImage getCurrentLayerImage();

  /**
   * Returns a deep copy of the layer in the layered image that the "current".
   *
   * @return Returns a deep copy of the "current" layer in the layered image.
   * @throws IllegalArgumentException throws this exception if there are no images inside of the
   *                                  layered image when called.
   */
  ILayer getCurrentLayerCopy();

  /**
   * Returns a list of all the layers that exist inside of the layered image.
   *
   * @return Returns a list of all the layers that exist inside of the layered image.
   */
  List<ILayer> getLayers();
}
