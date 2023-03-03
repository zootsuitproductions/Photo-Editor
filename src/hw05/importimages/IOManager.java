package hw05.importimages;

import hw05.image.IImage;

/**
 * This is an interface for the IOManager. This interface represents the means that different types
 * of elements can use to try and import an image into the model. One example of a valid import
 * element is a file.
 */
public interface IOManager {

  /**
   * Transforms a specific type of image element into an IImage that the model can recognize and
   * use for different types of photo edits.
   *
   * @return Returns an IImage that was created by the method from the specific image element
   *         that a client used to import their image.
   * @throws IllegalArgumentException throws this exception when their is an invalid element that
   *                                  is used to try and import the image to the model.
   */
  IImage apply() throws IllegalArgumentException;
}
