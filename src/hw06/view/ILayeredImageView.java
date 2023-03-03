package hw06.view;

import java.io.IOException;

/**
 * Represents the interface for the ILayeredImageView which provides the functionality that
 * the view produces for the client. The interface provides the client with the ability to
 * textually view the state of the layered image and render it.
 */
public interface ILayeredImageView {

  /**
   * Renders the specific string message onto the view's appendable object and tries to
   * output it to the client so they can see the message.
   *
   * @param message The message that is appended and outputted to the client.
   * @throws IllegalArgumentException throws this exception if the provided message is null
   * @throws IOException throws this exception if the appendable fails to transmit the message
   */
  void renderMessage(String message) throws IOException;

  /**
   * Renders the textual representation of the model's current layered image and appends
   * it to the view's appendable object in order for the client to see the current layered
   * image state.
   *
   * @throws IOException throws this exception if the appendable fails to transmit the state of the
   *                     layered image.
   */
  void renderLayers() throws IOException;
}
