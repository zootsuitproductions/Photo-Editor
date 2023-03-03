import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.image.IImageGenerator;
import hw05.pixel.Pixel;
import java.awt.Color;
import org.junit.Test;

/**
 * Tests class for the CheckerBoardImageGenerator class: encompassed are tests that show that the
 * CheckerBoardImageGenerator is able to generate a valid checkerboard IImage and is able to
 * used throughout the model and can have edits made to it.
 */
public class CheckerBoardImageGeneratorTest {

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalSquareSize() {
    new CheckerBoardImageGenerator(0,20,
        new Pixel(255, 255,255),
        new Pixel(255, 55,255));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoardSize() {
    new CheckerBoardImageGenerator(10,-20,
        new Pixel(255, 255,255),
        new Pixel(255, 55,255));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalc1() {
    new CheckerBoardImageGenerator(
        10,-20, null,
        new Pixel(255, 255,255));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalc2() {
    new CheckerBoardImageGenerator(
        10,30,
        new Pixel(255, 255,255), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        30,5, new Pixel(255, 255,255),
        new Pixel(255, 55,255));
    gen.generateImage("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName1() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        30,5, new Pixel(255, 255,255),
        new Pixel(255, 55,255));
    gen.generateImage(null);
  }

  @Test
  public void testTinyBoard() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        1,2, new Pixel(0, 0,0),
        new Pixel(255, 255,255));
    IImage board = gen.generateImage("board");
    assertEquals(board.getPixelAt(0,0), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(1,1), new Pixel(255,255,255));

    assertEquals(board.getPixelAt(1,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(0,1), new Pixel(0,0,0));
  }

  @Test
  public void testTinyBoardColor() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        1,2, Color.black, Color.white);
    IImage board = gen.generateImage("board");
    assertEquals(board.getPixelAt(0,0), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(1,1), new Pixel(255,255,255));

    assertEquals(board.getPixelAt(1,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(0,1), new Pixel(0,0,0));
  }

  @Test
  public void test2SquareSizeBoard() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        2,2, new Pixel(0, 0,0),
        new Pixel(255, 255,255));
    IImage board = gen.generateImage("board");
    assertEquals(board.getPixelHeight(), 4);
    assertEquals(board.getPixelWidth(), 4);

    assertEquals(board.getPixelAt(0,0), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(0,1), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(1,0), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(1,1), new Pixel(255,255,255));

    assertEquals(board.getPixelAt(2,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(2,1), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(3,0), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(3,1), new Pixel(0,0,0));

    assertEquals(board.getPixelAt(2,2), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(2,3), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(3,2), new Pixel(255,255,255));
    assertEquals(board.getPixelAt(3,3), new Pixel(255,255,255));

    assertEquals(board.getPixelAt(0,2), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(0,3), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(1,2), new Pixel(0,0,0));
    assertEquals(board.getPixelAt(1,3), new Pixel(0,0,0));
  }

  @Test
  public void testLargeBoard() {
    IImageGenerator gen = new CheckerBoardImageGenerator(
        20,8, new Pixel(255, 0,0),
        new Pixel(0, 0,255));
    IImage board = gen.generateImage("boardBig");
    for (int i = 0; i < board.getPixelHeight(); i++) {
      for (int j = 0; j < board.getPixelWidth(); j++) {
        if (((i / 20) + (j / 20)) % 2 == 0) {
          assertEquals(board.getPixelAt(i, j), new Pixel(0,0,255));
        } else {
          assertEquals(board.getPixelAt(i, j), new Pixel(255,0,0));
        }
      }
    }
  }
}