package hw07;

import hw07.controller.IViewListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Represents the IView interface that provides the correct functionality for implementing view
 * classes that allows for views to receive useful information that it needs to produce
 * the correct visual output that the client needs to edit their layered image properly.
 */
public interface IView {

  /**
   * Updates the visual representation of the layered image in the view depending on the
   * current layered image state that is passed into the view from an outside informational
   * source.
   *
   * @param name A lists of all the of layer names of the layers that are currently inside of the
   *             layered image.
   * @throws IllegalArgumentException throws this exception if the layer names passed in are null
   */
  void setLayers(List<String> name);

  /**
   * Updates the visual representation of the top most visible image in the current layered
   * image and makes sure to set the top most visible image properly due to the actions that
   * client carries out in the graphical view.
   *
   * @param img The new top most visible image that needs to be updated
   *
   */
  void setTopImage(BufferedImage img);

  /**
   * Allows for a new IViewListener to be added to a list of all IViewListeners that listen to the
   * actions that occur to all of the views that implement this interface. This allows for more
   * controllers to use this created graphical view and carry out whatever actions they want
   * based on the called events.
   *
   * @param listener The new listener that can be added to the listeners that listen to the
   *                 actions of the views that implement this IView interface.
   * @throws IllegalArgumentException throws this exception if the if passed in listener is null.
   */
  void addViewEventListener(IViewListener listener);

  /**
   * Visualizes a specific message that is requested to be altered to the client who is using
   * the graphical view to edit their layered image. These messages are usually confirmation
   * of successful operations or error messages if something went wrong.
   *
   * @param message The specific message that will be outputted to the client using the view.
   * @param title The title of the message that will be outputted to the client using the view.
   * @throws IllegalArgumentException throws this exception if the title and message are null or
   *                                  empty.
   */
  void promptMessage(String message, String title);

  /**
   * Makes the view visible.
   */
  void startView();
}
