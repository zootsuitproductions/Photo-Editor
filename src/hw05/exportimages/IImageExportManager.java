package hw05.exportimages;

import hw05.image.IImage;

/**
 * This is the interface for the IImageExportManager. This interface represents a manager
 * that holds different places/ways an IImage can be exported for the client to see the results
 * from the models transformations/edits if they were asked for.
 */
public interface IImageExportManager {

  /**
   * Exports the desired IImage from the model that is identified through its photoId, to the
   * client desired location based upon which type of IImageExportManager a client decides to
   * use for their image export.
   *
   * @throws IllegalArgumentException throws this exception if the provided file name is null.
   */
  void export(IImage image);
}
