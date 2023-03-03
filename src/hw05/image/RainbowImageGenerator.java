package hw05.image;

import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementing class of IImage generator, which can generate images of rainbow colors
 * of the provided dimensions.
 */
public class RainbowImageGenerator implements IImageGenerator {
  private final int height;
  private final int width;
  private final int rainbowScale;

  /**
   * Constructs a RainbowImageGenerator.
   * @param height the image height
   * @param width the image width
   * @param rainbowScale the relative difference in color between each pixel
   */
  public RainbowImageGenerator(int width, int height, int rainbowScale) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and heigh cannot be negative");
    }

    this.height = height;
    this.width = width;
    this.rainbowScale = rainbowScale;
  }

  @Override
  public IImage generateImage(String filename) {
    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("Filename must be non-null and non-empty");
    }
    List<List<IPixel>> pixelList = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        rowList.add(new Pixel(
            (i * rainbowScale) % 255,
            (j * rainbowScale) % 255,
            ((i + j) * rainbowScale) % 255));
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, filename);
  }
}
