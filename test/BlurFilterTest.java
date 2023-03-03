import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.imageedits.BlurFilter;
import hw05.imageedits.IPhotoEdit;
import hw05.pixel.Pixel;
import org.junit.Test;

/**
 * Tests class for the BlurFilter class: encompassed are tests that show that the blur photo edit
 * option has the correct functionality and is able to successfully blur an IImage.
 */
public class BlurFilterTest extends AbstractPhotoEditTest {

  @Test
  public void testSinglePixelImage() {
    IImage img1 = new CheckerBoardImageGenerator(
        1,1, new Pixel(40, 10, 60),
        new Pixel(40, 250, 79)).generateImage("1x1");
    IImage blurred = filter.apply(img1);
    assertEquals(blurred.getPixelAt(0,0).getRed(),
        Math.round(img1.getPixelAt(0,0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(0,0).getGreen(),
        Math.round(img1.getPixelAt(0,0).getGreen() * 0.25F));
    assertEquals(blurred.getPixelAt(0,0).getBlue(),
        Math.round(img1.getPixelAt(0,0).getBlue() * 0.25F));
  }

  @Test
  public void test1x2() {
    IImage blurred = filter.apply(i1x2);

    assertEquals(i1x2.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1x2.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1x2.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1x2.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1x2.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1x2.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1x2.getPixelAt(1, 0).getGreen() * 0.25F));
  }

  //testing that the pixels off the board are not taken into account
  // by the calculation of this filter
  @Test
  public void test2x2CornerMath() {
    IImage blurred = filter.apply(i2x2);
    assertEquals(blurred.getPixelAt(1, 1).getRed(),
        Math.round((i2x2.getPixelAt(0, 0).getRed() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getRed() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getRed() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getRed() * 0.25F)));

    assertEquals(blurred.getPixelAt(1, 1).getGreen(),
        Math.round((i2x2.getPixelAt(0, 0).getGreen() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getGreen() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getGreen() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getGreen() * 0.25F)));

    assertEquals(blurred.getPixelAt(1, 1).getBlue(),
        Math.round((i2x2.getPixelAt(0, 0).getBlue() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getBlue() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getBlue() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getBlue() * 0.25F)));
  }

  @Test
  public void test3x3EdgeMath() {
    IImage blurred = filter.apply(i3x3);
    assertEquals(blurred.getPixelAt(2, 1).getRed(),
        Math.round((i3x3.getPixelAt(1, 0).getRed() * 0.0625F)
            + (i3x3.getPixelAt(1, 1).getRed() * 0.125F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.0625F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.125F)));
  }

  @Test
  public void testAppliedToAllPixels2x2() {
    IImage blurred = filter.apply(i2x2);
    assertEquals(blurred.getPixelAt(0,0),new Pixel(6,6,11));
    assertEquals(blurred.getPixelAt(0,1),new Pixel(6,11,17));
    assertEquals(blurred.getPixelAt(1,0),new Pixel(11,6,17));
    assertEquals(blurred.getPixelAt(1,1),new Pixel(11,11,23));
  }

  @Test
  public void test7x7CompleteMath() {
    IImage blurred = filter.apply(i7x7);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  @Test
  public void test7x7PPMCompleteMath() {
    IImage blurred = filter.apply(i7x7ppm);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  @Test
  public void testCheckerBoardCompleteMath() {
    IImage board =
        new CheckerBoardImageGenerator(
            3,4, new Pixel(140, 53,21),
            new Pixel(601, 4,335)).generateImage("board");
    IImage blurred = filter.apply(board);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (board.getPixelAt(2, 2).getRed() * 0.0625F)
                + (board.getPixelAt(2, 3).getRed() * 0.125F)
                + (board.getPixelAt(2, 4).getRed() * 0.0625F)
                + (board.getPixelAt(3, 2).getRed() * 0.125F)
                + (board.getPixelAt(3, 3).getRed() * 0.25F)
                + (board.getPixelAt(3, 4).getRed() * 0.125F)
                + (board.getPixelAt(4, 2).getRed() * 0.0625F)
                + (board.getPixelAt(4, 3).getRed() * 0.125F)
                + (board.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (board.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(2, 3).getGreen() * 0.125F)
                + (board.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (board.getPixelAt(3, 2).getGreen() * 0.125F)
                + (board.getPixelAt(3, 3).getGreen() * 0.25F)
                + (board.getPixelAt(3, 4).getGreen() * 0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(4, 3).getGreen() * 0.125F)
                + (board.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (board.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(2, 3).getBlue() * 0.125F)
                + (board.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (board.getPixelAt(3, 2).getBlue() * 0.125F)
                + (board.getPixelAt(3, 3).getBlue() * 0.25F)
                + (board.getPixelAt(3, 4).getBlue() * 0.125F)
                + (board.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(4, 3).getBlue() * 0.125F)
                + (board.getPixelAt(4, 4).getBlue() * 0.0625F)));

  }

  @Test
  public void testClampingTo255WhenLargerThan255() {
    IImage blurred = filter.apply(new CheckerBoardImageGenerator(
        20,2, new Pixel(255, 255,255),
        new Pixel(255, 255,255)).generateImage("white"));
    assertEquals(blurred.getPixelAt(4, 5), new Pixel(255, 255, 255));
  }

  @Test
  public void testApplyingSameFilterTwice() {
    IImage board = new CheckerBoardImageGenerator(
        1,5,  new Pixel(255, 20,20),
        new Pixel(35, 255,23)).generateImage("board");
    IImage blurred = filter.apply(board);
    IImage doubleBlurred = filter.apply(blurred);

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (board.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(2, 3).getBlue() * 0.125F)
                + (board.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (board.getPixelAt(3, 2).getBlue() * 0.125F)
                + (board.getPixelAt(3, 3).getBlue() * 0.25F)
                + (board.getPixelAt(3, 4).getBlue() * 0.125F)
                + (board.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(4, 3).getBlue() * 0.125F)
                + (board.getPixelAt(4, 4).getBlue() * 0.0625F)));

    assertEquals(doubleBlurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (blurred.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(2, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (blurred.getPixelAt(3, 2).getGreen() * 0.125F)
                + (blurred.getPixelAt(3, 3).getGreen() * 0.25F)
                + (blurred.getPixelAt(3, 4).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(4, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 4).getGreen() * 0.0625F)));

  }

  @Test
  public void testClampingToZeroWhenNegative() {
    IImage blurred = filter.apply(i1x1);
    assertEquals(blurred.getPixelAt(0, 0), new Pixel(0, 0, 0));
  }

  @Override
  protected IPhotoEdit createEdit() {
    return new BlurFilter();
  }

  //test multiple applications of same filter

  //todo: ^^
}