package hw06.controller;

/**
 * Represents an ILayeredEditorController for the ILayeredImageEditorModel that takes in
 * client user data in order to create unique layered images that the client desires.
 * A client can pass in their input through a Readable object, either through typing in their
 * input to the console as a script, or inputting a file with their desired script there as well.
 */
public interface ILayeredEditorController {

  /**
   * Reads the clients input and edits the ILayeredImageEditorModel accordingly to
   * the specifications provided. The editor can read from any Readable source, whether it be
   * input from the console, or input read from a script in a file.
   */
  void startEditor();

}
