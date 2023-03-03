package hw05.imageedits;

import hw05.image.IImage;
import hw05.pixel.IPixel;
import hw05.pixel.ITransparentPixel;
import hw05.pixel.TransparentPixel;


/**
 * Represents a transparent pixel-imaged linear color transformation which is a color transformation
 * where the final red, green, blue, and alpha values of a pixel are linear combinations of its
 * initial red, green, blue, and alpha values.
 */
public abstract class AbstractTransparentLinearColorTransformation extends
    AbstractLinearColorTransformation {

  /**
   * Constructs an AbstractTransparentLinearColorTransformation by taking in the appropriate
   * transformation matrix and applying it to the transparent pixels of an IImage that the client
   * wants to be color transformed.
   *
   * @param transformationMatrix A matrix of values that every pixel in a provided image will be
   *                             multiplied by in order to create the correct color transformation
   *                             on the object.
   * @throws IllegalArgumentException throws this exception when the passed in transformation matrix
   *                                  is null or does not have 3X3 dimensions.
   */
  public AbstractTransparentLinearColorTransformation(float[][] transformationMatrix) {
    super(transformationMatrix);
  }

  @Override
  protected IPixel applyToPixelAt(int x, int y, IImage image) {
    try {
      ITransparentPixel pixel = (ITransparentPixel) image.getPixelAt(x, y);
      IPixel transformed = super.applyToPixelAt(x, y, image);
      return new TransparentPixel(transformed.getRed(), transformed.getGreen(),
          transformed.getBlue(), pixel.getAlpha());
    } catch (ClassCastException e) {
      return super.applyToPixelAt(x, y, image);
    }
  }
}
