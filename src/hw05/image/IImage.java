package hw05.image;

import hw05.pixel.IPixel;
import java.util.List;

/**
 * This is the interface for IImage. This interface represents the different types of IImages
 * that the model can handle and the operations that can be used on these valid IImage types.
 */
public interface IImage {

  /**
   * Gets the specific pixel at the specific row and column that exists in the double list of pixels
   * that every image has.
   *
   * @param row The specific row in the double list of pixels
   * @param collum The specific column in the double list of pixels
   * @return Returns the pixel at the specific row and column location in an image's double list of
   *         pixels.
   * @throws IllegalArgumentException throws this exception if the specific row and column do
   *                                  not exist in the image's double list of pixels
   */
  IPixel getPixelAt(int row, int collum) throws IllegalArgumentException;

  /**
   * Gets an image's 2D list of pixels.
   *
   * @return Returns an image's 2d list of pixels.
   */
  List<List<IPixel>> getPixel2dArray();

  /**
   * Gets an image's width by looking at the length of each inner list of pixels.
   *
   * @return Returns a specific image's width
   */
  int getPixelWidth();

  /**
   * Gets an image's width by looking at how many list of pixels exist in an image's 2D list of
   * pixels.
   *
   * @return Returns a specific image's width.
   */
  int getPixelHeight();

  /**
   * Gets the an image's file name that is has assigned to it.
   *
   * @return Returns an image's file name.
   */
  String getFileName();
}
