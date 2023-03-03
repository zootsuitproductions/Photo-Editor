import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.imageedits.GreyScaleTransformation;
import hw05.imageedits.IPhotoEdit;
import hw05.pixel.Pixel;
import org.junit.Test;

/**
 * Tests class for the GreyScaleTransformation class: encompassed are tests that show that the
 * GreyScale color transformation has the correct functionality and is able to successfully
 * implements its color transformation on an IImage.
 */
public class GreyScaleTransformationTest extends AbstractPhotoEditTest {

  @Override
  protected IPhotoEdit createEdit() {
    return new GreyScaleTransformation();
  }

  @Test
  public void testSinglePixelImage() {
    IImage img1 = new CheckerBoardImageGenerator(
        1,1,new Pixel(40, 10, 60),
        new Pixel(40, 250, 79)).generateImage("1x1");
    IImage gray = filter.apply(img1);
    for (int i = 0; i < gray.getPixelHeight(); i ++) {
      for (int j = 0; j < gray.getPixelWidth(); j ++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  @Test
  public void testSinglePixel7x7() {
    IImage gray = filter.apply(i7x7);
    assertEquals(gray.getPixelAt(4, 4).getRed(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));

    assertEquals(gray.getPixelAt(4, 4).getGreen(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));

    assertEquals(gray.getPixelAt(4, 4).getBlue(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));
  }

  @Test
  public void testAllPixels() {
    IImage gray = filter.apply(i7x7);
    for (int i = 0; i < gray.getPixelHeight(); i ++) {
      for (int j = 0; j < gray.getPixelWidth(); j ++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  @Test
  public void testAllPixelsPPM() {
    IImage gray = filter.apply(i7x7ppm);
    for (int i = 0; i < gray.getPixelHeight(); i ++) {
      for (int j = 0; j < gray.getPixelWidth(); j ++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  @Test
  public void testClampingTo255WhenLargerThan255() {
    IImage gray = filter.apply(new CheckerBoardImageGenerator(
        20,2, new Pixel(255, 255,255),
        new Pixel(255, 255,255)).generateImage("white"));
    assertEquals(gray.getPixelAt(4, 5), new Pixel(255, 255, 255));
  }

  @Test
  public void testClampingToZeroWhenNegative() {
    IImage gray = filter.apply(i1x1);
    assertEquals(gray.getPixelAt(0, 0), new Pixel(0, 0, 0));
  }

  @Test
  public void testApplyingSameTransformationTwice() {
    IImage board = new CheckerBoardImageGenerator(
        1, 7, new Pixel(25, 215,25),
        new Pixel(20, 234,100))
        .generateImage("board");
    IImage gray = filter.apply(board);
    IImage doubleGray = filter.apply(gray);
    assertEquals(gray, doubleGray);
  }
}