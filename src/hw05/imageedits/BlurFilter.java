package hw05.imageedits;

import java.awt.image.Kernel;

/**
 * Represents the BlurFilter. This filter blurs a provided IImage object. Blurring an object makes
 * the image appear fuzzier and more difficult to visualize. An image becomes foggy and loses
 * it sharpness when it becomes blurry.
 */
public class BlurFilter extends AbstractTransparentFilter {

  /**
   * Constructs a BlurFilter object with a 3X3 kernel that uses an array with the correct
   * values that need to be applied to every single ColorChannel of every pixel in the
   * IImage.
   */
  public BlurFilter() {
    super(new Kernel(3,3, new float[]{
        0.0625F, 0.125F, 0.0625F,
        0.125F, 0.25F, 0.125F,
        0.0625F, 0.125F, 0.0625F}));
  }
}
