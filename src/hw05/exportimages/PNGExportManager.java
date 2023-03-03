package hw05.exportimages;

import hw05.image.IImage;
import hw05.pixel.IPixel;
import hw05.pixel.ITransparentPixel;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * Represents a PNGExportManager that is able to export an IImage into a PNG formatted file for the
 * client if they desire.
 */
public class PNGExportManager implements IImageExportManager {

  protected final OutputStream stream;

  /**
   * Constructs a PNGExportManager that is able to export an image to the specified PNG formatted
   * file name.
   *
   * @param fileName the name of the file to be written
   * @throws IllegalArgumentException throws this exception if the file name is null or if it is not
   *                                  the correct png format.
   */
  public PNGExportManager(String fileName) {
    if (fileName == null || fileName.equals("")) {
      throw new IllegalArgumentException("Filename or image cannot be null");
    }
    if (fileName.length() < 4 || !fileName.endsWith(".png")) {
      throw new IllegalArgumentException("File must be in png format");
    }
    try {
      stream = new FileOutputStream(fileName);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not create file");
    }
  }

  @Override
  public void export(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    BufferedImage bufferedImage;
    if (image.getPixelAt(0, 0) instanceof ITransparentPixel) {
      bufferedImage = new BufferedImage(image.getPixelWidth(), image.getPixelHeight(),
          BufferedImage.TYPE_INT_ARGB);
    } else {
      bufferedImage = new BufferedImage(image.getPixelWidth(), image.getPixelHeight(),
          BufferedImage.TYPE_INT_RGB);
    }

    for (int i = 0; i < image.getPixelHeight(); i++) {
      for (int j = 0; j < image.getPixelWidth(); j++) {
        try {
          ITransparentPixel pixel = (ITransparentPixel) image.getPixelAt(i, j);

          int col = (pixel.getAlpha() << 24) | (pixel.getRed() << 16)
              | (pixel.getGreen() << 8) | pixel.getBlue();

          bufferedImage.setRGB(j, i, col);
        } catch (ClassCastException e) {
          IPixel pixel = image.getPixelAt(i, j);

          int col = (pixel.getRed() << 16)
              | (pixel.getGreen() << 8) | pixel.getBlue();

          bufferedImage.setRGB(j, i, col);
        }
      }
    }
    try {
      ImageIO.write(bufferedImage, "png", stream);
    } catch (IOException e) {
      throw new IllegalArgumentException("outputting failed");
    }
  }
}
