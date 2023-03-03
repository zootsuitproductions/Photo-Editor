package hw05.pixel;

/**
 * Represents the ITransparentPixel interface which introduces the new functionality that a
 * transparent pixel has. The new functionality surrounds the new alpha value that the
 * transparent pixel value contains in order to produce the transparent image effect.
 */
public interface ITransparentPixel extends IPixel {

  /**
   * Returns the alpha value of a transparent pixel.
   *
   * @return Returns the alpha value of a transparent pixel.
   */
  int getAlpha();
}
