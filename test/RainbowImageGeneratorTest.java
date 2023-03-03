import static org.junit.Assert.assertEquals;

import hw05.image.IImage;
import hw05.image.IImageGenerator;
import hw05.image.RainbowImageGenerator;
import hw05.pixel.Pixel;
import org.junit.Test;

/**
 * Tests class for the RainbowImageGeneratorTest class: encompassed are tests that show that the
 * RainbowImageGeneratorTest is able to generate color images for testing purposes.
 */
public class RainbowImageGeneratorTest {

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalWidth() {
    new RainbowImageGenerator(0,10, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalHeight() {
    new RainbowImageGenerator(20,-2, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName() {
    IImageGenerator gen = new RainbowImageGenerator(10, 10, 30);
    gen.generateImage("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName1() {
    IImageGenerator gen =  new RainbowImageGenerator(10, 10, 30);
    gen.generateImage(null);
  }

  @Test
  public void testSmallRainbow() {
    IImageGenerator gen =  new RainbowImageGenerator(2, 2, 30);
    IImage board = gen.generateImage("rainbow");
    assertEquals(board.getPixelAt(0,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(1,1), new Pixel(30,30,60));

    assertEquals(board.getPixelAt(1,0), new Pixel(30,0,30));
    assertEquals(board.getPixelAt(0,1), new Pixel(0,30,30));
  }

  @Test
  public void testGoingOver255() {
    IImageGenerator gen =  new RainbowImageGenerator(2, 3, 300);
    IImage board = gen.generateImage("rainbow");
    assertEquals(board.getPixelAt(0,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(1,1), new Pixel(45, 45, 90));
    assertEquals(board.getPixelAt(2,1), new Pixel(90, 45, 135));

    assertEquals(board.getPixelAt(1,0), new Pixel(45, 0, 45));
    assertEquals(board.getPixelAt(0,1), new Pixel(0,45,45));

    assertEquals(board.getPixelAt(2,0), new Pixel(90, 0, 90));
  }
}
