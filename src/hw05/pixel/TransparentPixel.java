package hw05.pixel;

import java.util.Objects;

/**
 * Represents a TransparentPixel that can be used inside of an image format that needs to be
 * transparent. This pixel object has a new alpha value that helps the pixel produce transparent
 * images when put together with many other pixels.
 */

public class TransparentPixel extends Pixel implements ITransparentPixel {

  private final int alpha;

  /**
   * Constructs a TransparentPixel object that takes in a red, green, blue, and alpha value in order
   * to create a pixel that has the ability to be transparent and work with image formats that
   * require transparency pixel values.
   *
   * @param red   The red color value of the pixel.
   * @param green The green color value of the pixel.
   * @param blue  The blue color value of the pixel.
   * @param alpha The alpha color value of the pixel.
   */
  public TransparentPixel(int red, int green, int blue, int alpha) {
    super(red, green, blue);
    this.alpha = super.clampValue(alpha);
  }

  @Override
  public int getAlpha() {
    return this.alpha;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof TransparentPixel)) {

      if (that instanceof IPixel) {
        IPixel pixel = (IPixel) that;
        return pixel.getRed() == this.getRed()
            && pixel.getBlue() == this.getBlue()
            && pixel.getGreen() == this.getGreen()
            && (255 == this.alpha);
      }
      return false;
    }

    TransparentPixel pixel = (TransparentPixel) that;

    return pixel.getRed() == this.getRed()
        && pixel.getBlue() == this.getBlue()
        && pixel.getGreen() == this.getGreen()
        && pixel.getAlpha() == this.alpha;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getRed(), this.getGreen(), this.getBlue(), alpha);
  }

  @Override
  public String toString() {
    return Integer.toString(this.getRed()) + ", " + Integer.toString(this.getGreen()) + ", " +
        Integer.toString(this.getBlue()) + ", " + Integer.toString(alpha);
  }
}
