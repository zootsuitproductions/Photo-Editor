import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import hw05.imageedits.SharpenFilter;
import hw05.pixel.Pixel;
import java.awt.Color;
import org.junit.Test;

/**
 * Tests class for the SharpenFilter class: encompassed are tests that show that the SharpenFilter
 * is able to take valid IImages and apply the sharpen filter appropriately to them.
 */
public class SharpenFilterTest extends AbstractPhotoEditTest {

  @Override
  protected IPhotoEdit createEdit() {
    return new SharpenFilter();
  }

  @Test
  public void testSinglePixelImage() {
    IImage img1 = new CheckerBoardImageGenerator(
        1,1,Color.CYAN,Color.CYAN).generateImage("1x1");
    IImage sharpened = filter.apply(img1);
    assertEquals(sharpened.getPixelAt(0,0).getRed(),
        Math.round(img1.getPixelAt(0,0).getRed() * 1F));
    assertEquals(sharpened.getPixelAt(0,0).getGreen(),
        Math.round(img1.getPixelAt(0,0).getGreen() * 1F));
    assertEquals(sharpened.getPixelAt(0,0).getBlue(),
        Math.round(img1.getPixelAt(0,0).getBlue() * 1F));
  }

  @Test
  public void test1x2() {
    IImage sharpened = filter.apply(i1x2);

    assertEquals(i1x2.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(sharpened.getPixelAt(0, 0).getRed(),
        Math.round((i1x2.getPixelAt(1, 0).getRed() * 0.25F)));
    assertEquals(sharpened.getPixelAt(0, 0).getBlue(),
        Math.round((i1x2.getPixelAt(1, 0).getBlue() * 0.25F)));
    assertEquals(sharpened.getPixelAt(0, 0).getGreen(),
        Math.round((i1x2.getPixelAt(1, 0).getGreen() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 0).getRed(),
        (i1x2.getPixelAt(1, 0).getRed()));
    assertEquals(sharpened.getPixelAt(1, 0).getBlue(),
        (i1x2.getPixelAt(1, 0).getBlue()));
    assertEquals(sharpened.getPixelAt(1, 0).getGreen(),
        (i1x2.getPixelAt(1, 0).getGreen()));
  }

  @Test
  public void test2x2CornerMath() {
    IImage sharpened = filter.apply(i2x2);
    assertEquals(sharpened.getPixelAt(1, 1).getRed(),
        Math.round((i2x2.getPixelAt(0, 0).getRed() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getRed() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getRed() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getRed() * 1F)));

    assertEquals(sharpened.getPixelAt(1, 1).getGreen(),
        Math.round((i2x2.getPixelAt(0, 0).getGreen() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getGreen() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getGreen() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getGreen() * 1F)));

    assertEquals(sharpened.getPixelAt(1, 1).getBlue(),
        Math.round((i2x2.getPixelAt(0, 0).getBlue() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getBlue() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getBlue() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getBlue() * 1F)));
  }

  @Test
  public void test3x3CornerMath() {
    IImage sharpened = filter.apply(i3x3);
    assertEquals(sharpened.getPixelAt(2, 2).getRed(),
        Math.round((i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 1F)));
  }

  @Test
  public void test3x3EdgeMath() {
    IImage sharpened = filter.apply(i3x3);
    assertEquals(sharpened.getPixelAt(2, 1).getRed(),
        Math.round((i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 1F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.25F)));
  }

  @Test
  public void test3x3MathCenter() {
    IImage sharpened = filter.apply(i3x3);
    assertEquals(sharpened.getPixelAt(1, 1).getRed(),
        Math.round((i3x3.getPixelAt(0, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getRed() * 1F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 1).getGreen(),
        Math.round((i3x3.getPixelAt(0, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getGreen() * 1F)
            + (i3x3.getPixelAt(1, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getGreen() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 1).getBlue(),
        Math.round((i3x3.getPixelAt(0, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getBlue() * 1F)
            + (i3x3.getPixelAt(1, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getBlue() * 0.25F)));
  }

  @Test
  public void test3x3MathCorner() {
    IImage sharpened = filter.apply(i3x3);
    assertEquals(sharpened.getPixelAt(2, 2).getRed(),
        Math.round((i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 1F)));

    assertEquals(sharpened.getPixelAt(2, 2).getGreen(),
        Math.round((i3x3.getPixelAt(0, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getGreen() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getGreen() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getGreen() * 1F)));

    assertEquals(sharpened.getPixelAt(2, 2).getBlue(),
        Math.round((i3x3.getPixelAt(0, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getBlue() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getBlue() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getBlue() * 1F)));
  }

  @Test
  public void test7x7CompleteMath() {
    IImage sharpened = filter.apply(i7x7);
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getRed() * 1F)
                + (i7x7.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getGreen() * 1F)
                + (i7x7.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 1F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getBlue() * -0.125F)));
  }

  @Test
  public void test7x7CompleteMathPPM() {
    IImage sharpened = filter.apply(i7x7ppm);
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7ppm.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 3).getRed() * 1F)
                + (i7x7ppm.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7ppm.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 3).getGreen() * 1F)
                + (i7x7ppm.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(), 255);
    assertEquals(true, 255 < Math.round(
            (i7x7ppm.getPixelAt(1, 1).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 2).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 3).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 4).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 5).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 1).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 2).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 3).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 4).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 5).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 1).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 2).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 3).getBlue() * 1F)
                + (i7x7ppm.getPixelAt(3, 4).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 5).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 1).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 3).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 4).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 5).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 1).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 2).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 3).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 4).getBlue() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 5).getBlue() * -0.125F)));
  }

  @Test
  public void testClampingToZeroWhenNegative() {
    IImage sharpened = filter.apply(i3x3);
    assertEquals(sharpened.getPixelAt(0, 0), new Pixel(0, 0, 0));
  }

  @Test
  public void testClampingTo255WhenLargerThan255() {
    IImage sharpened = filter.apply(new CheckerBoardImageGenerator(
        20,2, Color.WHITE, Color.WHITE).generateImage("white"));
    assertEquals(sharpened.getPixelAt(30, 30), new Pixel(255, 255,255));
  }

  @Test
  public void testApplyingSameFilterTwice() {
    IImage board = new CheckerBoardImageGenerator(
        1,7, Color.DARK_GRAY, Color.GREEN).generateImage("board");
    IImage sharpened = filter.apply(board);
    IImage doubleSharp = filter.apply(sharpened);

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (board.getPixelAt(1, 1).getGreen() * -0.125F)
                + (board.getPixelAt(1, 2).getGreen() * -0.125F)
                + (board.getPixelAt(1, 3).getGreen() * -0.125F)
                + (board.getPixelAt(1, 4).getGreen() * -0.125F)
                + (board.getPixelAt(1, 5).getGreen() * -0.125F)
                + (board.getPixelAt(2, 1).getGreen() * -0.125F)
                + (board.getPixelAt(2, 2).getGreen() * 0.25F)
                + (board.getPixelAt(2, 3).getGreen() * 0.25F)
                + (board.getPixelAt(2, 4).getGreen() * 0.25F)
                + (board.getPixelAt(2, 5).getGreen() * -0.125F)
                + (board.getPixelAt(3, 1).getGreen() * -0.125F)
                + (board.getPixelAt(3, 2).getGreen() * 0.25F)
                + (board.getPixelAt(3, 3).getGreen() * 1F)
                + (board.getPixelAt(3, 4).getGreen() * 0.25F)
                + (board.getPixelAt(3, 5).getGreen() * -0.125F)
                + (board.getPixelAt(4, 1).getGreen() * -0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.25F)
                + (board.getPixelAt(4, 3).getGreen() * 0.25F)
                + (board.getPixelAt(4, 4).getGreen() * 0.25F)
                + (board.getPixelAt(4, 5).getGreen() * -0.125F)
                + (board.getPixelAt(5, 1).getGreen() * -0.125F)
                + (board.getPixelAt(5, 2).getGreen() * -0.125F)
                + (board.getPixelAt(5, 3).getGreen() * -0.125F)
                + (board.getPixelAt(5, 4).getGreen() * -0.125F)
                + (board.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(doubleSharp.getPixelAt(3, 3).getGreen(),
        Math.round(
            (sharpened.getPixelAt(1, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 2).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 3).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 4).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(2, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(2, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 3).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(3, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(3, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(3, 3).getGreen() * 1F)
                + (sharpened.getPixelAt(3, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(3, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(4, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(4, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 3).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 2).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 3).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 4).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 5).getGreen() * -0.125F)));
  }

}