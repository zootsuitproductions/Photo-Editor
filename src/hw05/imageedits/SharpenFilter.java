package hw05.imageedits;

import java.awt.image.Kernel;

/**
 * Represents the SharpenFilter. Sharpening accentuates edges
 * (the boundaries between regions of high contrast), thereby giving the image a "sharper" look.
 */
public class SharpenFilter extends AbstractTransparentFilter {

  /**
   * Constructs a SharpenFilterObject with a 5X5 kernel that uses an array with the correct
   * values that need to be applied to every single ColorChannel of every pixel in the
   * IImage.
   */
  public SharpenFilter() {
    super(new Kernel(5,5, new float[]{
        -0.125F, -0.125F, -0.125F, -0.125F, -0.125F,
        -0.125F, 0.25F, 0.25F, 0.25F, -0.125F,
        -0.125F, 0.25F, 1F, 0.25F, -0.125F,
        -0.125F, 0.25F, 0.25F, 0.25F, -0.125F,
        -0.125F, -0.125F, -0.125F, -0.125F, -0.125F}));
  }

}
