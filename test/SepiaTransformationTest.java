import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import hw05.imageedits.SepiaTransformation;
import hw05.pixel.Pixel;
import java.awt.Color;
import org.junit.Test;

/**
 * Tests class for the SepiaTransformation class: encompassed are tests that show that the
 * SepiaTransformation is able to take valid IIMage and apply the Sepia color transformation
 * correctly to the image.
 */
public class SepiaTransformationTest extends AbstractPhotoEditTest {

  @Override
  protected IPhotoEdit createEdit() {
    return new SepiaTransformation();
  }

  @Test
  public void testSinglePixelImage() {
    IImage img1 = new CheckerBoardImageGenerator(1,1,Color.CYAN,Color.CYAN).generateImage("1x1");
    IImage sepia = filter.apply(img1);
    for (int i = 0; i < sepia.getPixelHeight(); i ++) {
      for (int j = 0; j < sepia.getPixelWidth(); j ++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(img1.getPixelAt(i, j).getRed() * 0.349F
                  + img1.getPixelAt(i, j).getGreen() * 0.686F
                  + img1.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.272F
                + img1.getPixelAt(i, j).getGreen() * 0.534F
                + img1.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  @Test
  public void test7x7() {
    IImage sepia = filter.apply(i7x7);

    for (int i = 0; i < sepia.getPixelHeight(); i ++) {
      for (int j = 0; j < sepia.getPixelWidth(); j ++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7x7.getPixelAt(i, j).getRed() * 0.349F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.686F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.272F
                + i7x7.getPixelAt(i, j).getGreen() * 0.534F
                + i7x7.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  @Test
  public void test7x7PPM() {
    IImage sepia = filter.apply(i7x7ppm);

    for (int i = 0; i < sepia.getPixelHeight(); i ++) {
      for (int j = 0; j < sepia.getPixelWidth(); j ++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.349F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.686F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.272F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.534F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  @Test
  public void testClampingTo255WhenLargerThan255() {
    IImage sepia = filter.apply(new CheckerBoardImageGenerator(
        20,2, Color.WHITE, Color.WHITE).generateImage("white"));
    assertEquals(sepia.getPixelAt(4, 5), new Pixel(255, 255, 239));
  }

  @Test
  public void testClampingToZeroWhenNegative() {
    IImage sepia = filter.apply(i1x1);
    assertEquals(sepia.getPixelAt(0, 0), new Pixel(0, 0, 0));
  }

  @Test
  public void testApplyingSameTransformationTwice() {
    IImage board = new CheckerBoardImageGenerator(
        1, 7, Color.DARK_GRAY, Color.GREEN)
        .generateImage("board");
    IImage sepia = filter.apply(board);
    IImage doubleSepia = filter.apply(sepia);

    for (int i = 0; i < doubleSepia.getPixelHeight(); i++) {
      for (int j = 0; j < doubleSepia.getPixelWidth(); j++) {
        if (doubleSepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(doubleSepia.getPixelAt(i, j).getRed(),
              Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (doubleSepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(doubleSepia.getPixelAt(i, j).getGreen(),
              Math.round(sepia.getPixelAt(i, j).getRed() * 0.349F
                  + sepia.getPixelAt(i, j).getGreen() * 0.686F
                  + sepia.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(doubleSepia.getPixelAt(i, j).getBlue(),
            Math.round(sepia.getPixelAt(i, j).getRed() * 0.272F
                + sepia.getPixelAt(i, j).getGreen() * 0.534F
                + sepia.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }
}