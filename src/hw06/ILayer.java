package hw06;

import hw05.image.IImage;

/**
 * Represents an ILayer interface. This interface provides the functionality for an ILayer that
 * exists inside of a layered image. ILayer's are stacked on top of each other in the
 * LayeredImageEditorModel to create a layered image.
 */
public interface ILayer {

  /**
   * Returns if a layer is visible or not.
   *
   * @return Returns if a layer is visible or not.
   */
  boolean isVisible();

  /**
   * Returns the name of a layer.
   *
   * @return Returns the name of a layer.
   */
  String getName();

  /**
   * Returns the image that exists inside of the layer.
   *
   * @return Returns the image that exists inside of the layer.
   */
  IImage getImage();

  /**
   * Allows for the client to set the visibility of the layer as either true or false.
   *
   * @param isVisible The boolean value that the client wants to set the layer's visibility to.
   */
  void setVisibility(boolean isVisible);
}
