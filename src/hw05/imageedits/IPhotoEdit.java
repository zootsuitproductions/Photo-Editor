package hw05.imageedits;

import hw05.image.IImage;

/**
 * This is the interface for an IPhotoEdit. An IPhotoEdit represents any type of photo edit that can
 * be done to an image, for example, a color transformation or filter. Instead of altering the
 * existing photo, an IPhotoEdit creates a new photo that has the desired edits.
 */
public interface IPhotoEdit {

  /**
   * Applies the specific type of IPhotoEdit to an image that is specified by the client. This
   * method does not alter the passed in image, instead, it creates a new image with the desired
   * edits.
   *
   * @param image The specific image that the user wants to edit and change.
   * @return Returns a new image with the specific edits that the client wanted to be done.
   * @throws IllegalArgumentException throws this exception if the passed in IImage is null.
   */
  IImage apply(IImage image);
}
