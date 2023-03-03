package hw05.pixel;

import hw05.pixel.IPixel;
import hw05.pixel.IPixelConstants;
import java.util.Objects;

/**
 * Represents a pixel that exists inside of an IImage. A pixel contains a red, green, and blue
 * value that signals the type of color that a pixel shows when it is culminated inside of
 * an image.
 */
public class Pixel implements IPixel, IPixelConstants {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a Pixel object that contains a red value, green value, and blue value that
   * are used to help visually create an image.
   *
   * @param red The red value of the pixel
   * @param green The green value of the pixel
   * @param blue The blue value of the pixel
   */
  public Pixel(int red, int green, int blue) {
    this.red = clampValue(red);
    this.green = clampValue(green);
    this.blue = clampValue(blue);
  }

  /**
   * Clamps the value that is passed into the function. If a value is greater than the max,
   * then it will be assigned to the max value. If a value is smaller than the min, then
   * it will be assigned to the min value.
   *
   * @param value The value that is being clamped.
   * @return Returns the value after it is clamped.
   */
  //made protected to call in TransparentPixel class
  protected int clampValue(int value) {
    if (value > MAX_VALUE) {
      return MAX_VALUE;
    } else if (value < MIN_VALUE) {
      return MIN_VALUE;
    } else {
      return value;
    }
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public int getBlue() {
    return blue;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Pixel)) {
      return false;
    }
    Pixel pixel = (Pixel) that;

    return pixel.getRed() == this.getRed()
        && pixel.getBlue() == this.getBlue()
        && pixel.getGreen() == this.getGreen();
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  @Override
  public String toString() {
    return Integer.toString(red) + ", " + Integer.toString(green) + ", " + Integer.toString(blue);
  }
}

