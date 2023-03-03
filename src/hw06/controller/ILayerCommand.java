package hw06.controller;

import java.util.Scanner;

/**
 * Represents the ILayerCommand interface which describes the functionality that a layer command
 * has for a layered image object. This interface provides functionality that helps
 * the ILayeredEditorController apply the correct actions to the given ILayeredImageEditorModel
 * when a specific command is requested by the client.
 */
public interface ILayerCommand {

  /**
   * Reads the input from the given scanner and applies the correct actions that the command
   * requires.
   *
   * @param scan The scanner that the method reads from to carry out the specific actions asked
   *             for by the client.
   * @throws IllegalArgumentException throws this exception when the scanner is null
   */
  void apply(Scanner scan);
}
