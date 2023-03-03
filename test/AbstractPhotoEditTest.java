import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import hw05.image.IImage;
import hw05.image.Image;
import hw05.image.RainbowImageGenerator;
import hw05.imageedits.IPhotoEdit;
import hw05.importimages.PPMImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import org.junit.Test;

/**
 * Abstract testing class that provides tests and examples of Photo Edits.
 */

public abstract class AbstractPhotoEditTest {
  protected IPhotoEdit filter = createEdit();
  protected IImage i1x1 = new RainbowImageGenerator(
      1,1, 30).generateImage("1x1.ppm");
  protected IImage i1x2 = new RainbowImageGenerator(
      1,2, 30).generateImage("1x2.ppm");
  protected IImage i2x2 = new RainbowImageGenerator(
      2,2, 30).generateImage("2x2.ppm");
  protected IImage i3x3 = new RainbowImageGenerator(
      3,3, 30).generateImage("3x3.ppm");
  protected IImage i7x7 = new RainbowImageGenerator(
      7,7, 100).generateImage("10x7.ppm");
  protected IImage i7x7ppm = new PPMImportManager("./res/i7x7.ppm").apply();

  protected abstract IPhotoEdit createEdit();

  @Test
  public void testOriginalIsntMutatedByFiltering() {
    IImage i1 = new Image(i3x3.getPixel2dArray(), "hi.ppm");
    IImage i1copy = new Image(i3x3.getPixel2dArray(), "hi.ppm");
    filter.apply(i1);

    assertEquals(i1, i1copy);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    filter.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidImage() {
    filter.apply(new Image(null, "hi.ppm"));
  }

  @Test
  public void test1x1BlackPixel() {
    assertEquals(filter.apply(i1x1), i1x1);
  }

  @Test
  public void testAppliedToAllPixels() {
    IImage filtered = filter.apply(i7x7);
    for (int i = 0; i < filtered.getPixelHeight(); i ++) {
      for (int j = 0; j < filtered.getPixelWidth(); j ++) {
        IPixel pixel = filtered.getPixelAt(i, j);
        if (!pixel.equals(new Pixel(0,0,0))
            && !pixel.equals(new Pixel(255,255,255))) {
          assertNotEquals(pixel, i7x7.getPixelAt(i,j));
        }
      }
    }
  }

  @Test
  public void testAppliedToAllPixelsPPM() {
    IImage filtered = filter.apply(i7x7ppm);
    for (int i = 0; i < filtered.getPixelHeight(); i ++) {
      for (int j = 0; j < filtered.getPixelWidth(); j ++) {
        IPixel pixel = filtered.getPixelAt(i, j);
        if (!pixel.equals(new Pixel(0,0,0))
            && !pixel.equals(new Pixel(255,255,255))) {
          assertNotEquals(pixel, i7x7.getPixelAt(i,j));
        }
      }
    }
  }
}
