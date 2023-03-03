package hw05.image;

import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a programmatic image generator that creates a checkerboard IImage.
 * This generator creates the correct pixels that will visualize a checkerboard for the client.
 * The created checkerboard is square-shaped.
 */
public class CheckerBoardImageGenerator implements IImageGenerator {

  private final int squareSize;
  private final int boardSize;
  private final IPixel color1;
  private final IPixel color2;

  /**
   * Constructs a CheckerBoardImageGenerator object which allows for the client to ask
   * for a programmatically created checkerBoard IImage that can be exported for them if they
   * so please. The created checkerboard is square-shaped.
   *
   * @param squareSize The size that each square will be in the checker board.
   * @param boardSize The length/or width of the board. Does not matter which one because the
   *                  board will always be a perfect square.
   * @param color1 The first pixel color that will be representing half of the squares in the board
   * @param color2 The second pixel color that will be representing the other half of the squares
   *               in the board.
   * @throws IllegalArgumentException throws this exception when the provided colors are null or
   *                                  the provided square size and board size are illegal values.
   */

  public CheckerBoardImageGenerator(int squareSize, int boardSize, IPixel color1, IPixel color2) {
    if (color1 == null || color2 == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    if (squareSize <= 0 || boardSize <= 0) {
      throw new IllegalArgumentException("Square and board size must be > 0");
    }

    this.squareSize = squareSize;
    this.boardSize = boardSize;
    this.color1 = color1;
    this.color2 = color2;
  }

  /**
   * Constructs a CheckerBoardImageGenerator object which allows for the client to ask
   * for a programmatically created checkerBoard IImage that can be exported for them if they
   * so please. The created checkerboard is square-shaped.
   *
   * @param squareSize The size that each square will be in the checker board.
   * @param boardSize The length/or width of the board. Does not matter which one because the
   *                  board will always be a perfect square.
   * @param color1 The first color that will be representing half of the squares in the board
   * @param color2 The second color that will be representing the other half of the squares in the
   *               board.
   * @throws IllegalArgumentException throws this exception when the provided colors are null or
   *                                  the provided square size and board size are illegal values.
   */

  public CheckerBoardImageGenerator(int squareSize, int boardSize, Color color1, Color color2) {
    this(squareSize, boardSize,
        new Pixel(color1.getRed(), color1.getGreen(), color1.getBlue()),
        new Pixel(color2.getRed(), color2.getGreen(), color2.getBlue()));
  }

  @Override
  public IImage generateImage(String fileName) {
    if (fileName == null) {
      throw new IllegalArgumentException("The file name cannot be null!");
    }

    List<List<IPixel>> pixelList = new ArrayList<>();
    for (int i = 0; i < squareSize * boardSize; i ++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < squareSize * boardSize; j ++) {
        if ((i / squareSize + j / squareSize) % 2 == 0) {
          rowList.add(color2);
        } else {
          rowList.add(color1);
        }
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, fileName);
  }

}
