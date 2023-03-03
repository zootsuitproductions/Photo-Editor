package hw05.pixel;

/**
 * Represents a Pixel that is an important part of creating visual images. Pixels contain a
 * combination of different colors, that when combined, are able to create
 * a larger image that people can use and see.
 */
public interface IPixel {

  /**
   * Gets the red value that a pixel uses to create an image.
   *
   * @return Returns the red value of a pixel that it uses for image creation
   */
  int getRed();

  /**
   * Gets the green value that a pixel uses to create an image.
   *
   * @return Returns the green value of a pixel that it uses for image creation
   */
  int getGreen();

  /**
   * Gets the blue value that a pixel uses to create an image.
   *
   * @return Returns the blue value of a pixel that it uses for image creation
   */
  int getBlue();

}
