package hw05.imageedits;

import hw05.image.IImage;
import hw05.pixel.IPixel;
import hw05.pixel.ITransparentPixel;
import hw05.pixel.TransparentPixel;
import java.awt.image.Kernel;


/**
 * Represents the AbstractTransparentFilter class. This is a filtering operation that occurs on an
 * IImage which uses a kernel that runs through every single transparent pixel in an image and
 * changes it by altering values of the color in the transparent pixel depending om which specific
 * color pixel value the filter is meant to change.
 */
public abstract class AbstractTransparentFilter extends AbstractFilter {

  /**
   * Constructs an AbstractTransparentFilter with a specific kernel that identifies how large the
   * kernel is and what are the values that are going to be used in order to create the desired
   * filters.
   *
   * @param kernel A kernel that signals the dimensions of the filter and the values that exist
   *               inside of the array of values.
   * @throws IllegalArgumentException throws this exception when the kernel is null, the kernel is
   *                                  is empty, or if the provided filter does not have odd
   *                                  dimensions.
   */
  public AbstractTransparentFilter(Kernel kernel) {
    super(kernel);
  }

  @Override
  protected IPixel applyToPixelAt(int x, int y, IImage image) {
    try {
      ITransparentPixel pixel = (ITransparentPixel) image.getPixelAt(x, y);
      IPixel transformed = super.applyToPixelAt(x, y, image);
      return new TransparentPixel(
          transformed.getRed(), transformed.getGreen(), transformed.getBlue(), pixel.getAlpha());
    } catch (ClassCastException e) {
      return super.applyToPixelAt(x, y, image);
    }
  }
}
