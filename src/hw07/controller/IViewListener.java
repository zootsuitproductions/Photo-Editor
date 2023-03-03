package hw07.controller;

/**
 * Represents the IViewListener that provides the functionality that all IViewListeners need to
 * handle and carry out. This IViewListener is a specific listener that listens for a variety of
 * different events and carries out the correct actions when appropriate.
 */
public interface IViewListener {

  /**
   * Handles the case where the blur action event has been triggered and carries out the
   * correct actions that need to occur to produce the correct blur effects. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   */
  void handleBlur();

  /**
   * Handles the case where the sepia action event has been triggered and carries out the
   * correct actions that need to occur to produce the correct sepia effects. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   */
  void handleSepia();

  /**
   * Handles the case where the greyscale action event has been triggered and carries out the
   * correct actions that need to occur to produce the correct greyscale effects. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   */
  void handleGreyscale();

  /**
   * Handles the case where the sharpen action event has been triggered and carries out the
   * correct actions that need to occur to produce the correct sharpen effects. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   */
  void handleSharpen();

  /**
   * Handles the case where the copy current layer action event has been triggered and
   * carries out the correct actions that need to occur to copy the current layer properly.
   * If the action cannot be carried out appropriately, an error message will be passed to the
   * view and outputted to notify the client of the error.
   *
   * @param newLayerName The new layer name that will be placed on the new copied layer.
   */
  void handleCopyCurrentLayer(String newLayerName);


  /**
   * Handles the case where the create layer event has been triggered and carries out the
   * correct actions that need to occur to produce the new layer correctly. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   *
   * @param newLayerName The layer name for the new layer that is being created
   */
  void handleCreateLayer(String newLayerName);


  /**
   * Handles the case where the remove action event has been triggered and carries out the
   * correct actions that need to occur to remove the current layer properly. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   */
  void handleRemoveLayer();


  /**
   * Handles the case where the set current action event has been triggered and carries out the
   * correct actions that need to occur to set a specific layer as current properly. If the action
   * cannot be carried out appropriately, an error message will be passed to the view and outputted
   * to notify the client of the error.
   *
   * @param layerName The layer name of a layer that a client wants to make current
   * @return Returns -1 if the the layerName that is provided by the client does not point to
   *         an actual layer in the layered image or returns the index of the layer in the layered
   *         image that has the same layer name as the one provided.
   */
  int handleSetCurrent(String layerName);


  /**
   * Handles the case where the toggle visibility action event has been triggered and carries
   * out the correct actions that need to occur to toggle the visibility of the current layer
   * properly. If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   */
  void handleToggleVisibility();


  /**
   * Handles the case where the create rainbow action event has been triggered and carries out the
   * correct actions that need to occur to load in a rainbow image into the current layer.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   */
  void handleCreateRainbow();


  /**
   * Handles the case where the load image action event has been triggered and carries out the
   * correct actions that need to occur load an image into the current layer properly.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   *
   * @param filePath The file path of the image that will be loaded into the current layer.
   */
  void handleLoadImage(String filePath);

  /**
   * Handles the case where the export top image action event has been triggered and carries out the
   * correct actions that need to occur to export the image in the top most visible layer properly.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   *
   * @param filePath The file name of where the top most visible image will be exported to.
   */
  void handleExportTopImage(String filePath);

  /**
   * Handles the case where the save layers action event has been triggered and carries out the
   * correct actions that need to occur to save all of the layers in the layered image properly.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   *
   * @param folderPath The folder name of where all of the images will be saved with the given
   *                   informational text file of all the destinations of the image files.
   */
  void handleSaveLayers(String folderPath);

  /**
   * Handles the case where the load entire image event has been triggered and carries out the
   * correct actions that need to occur to load an entire layered image text file into the image.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   *
   * @param filePath The text file path that holds the destinations of all of the images that
   *                 need to be loaded into the layered image.
   */
  void handleLoadEntireImage(String filePath);

  /**
   * Handles the case where script event has been triggered and carries out the
   * correct actions that need to occur to load a script text file into the image.
   * If the action cannot be carried out appropriately, an error message will be passed
   * to the view and outputted to notify the client of the error.
   *
   * @param filePath The text file path of the script that holds all of the commands that a user
   *                 wants to carry out on the layered image.
   */
  void handleScript(String filePath);


}
