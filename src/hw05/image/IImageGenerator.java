package hw05.image;

import hw05.image.IImage;

/**
 * This is the interface for the IImageGenerator. This interface gives the client the ability
 * to ask for a programmatically created image themself, instead of supplying and importing
 * their own image into the model.
 */
public interface IImageGenerator {

  /**
   * Generates an IImage programmatically for the client, so they do not have to import
   * their own image into the model.
   *
   * @param filename The desired filename that the client will want this programmatically created
   *                 photo to have.
   * @return Returns the programmatically created IImage that the client wants for the model.
   * @throws IllegalArgumentException throws this exception if the provided file name is null.
   */
  IImage generateImage(String filename);
}
