package hw05.importimages;

import hw05.image.IImage;
import hw05.image.Image;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Represents the JPEGImportManager that gives a client the ability to import a JPEG or
 * JPG file into the model and turn it into a valid IImage that can be edited to their desire.
 */
public class JPEGImportManager implements IOManager {

  private final String filename;
  private final InputStream inputStream;

  /**
   * Constructs a JPEGImportManager that takes in a JPEG file name and is able to to
   * turn it into a correct IImage for the model if the file is valid.
   *
   * @param filename The JPEG file name that the client wants to turn into an IImage
   * @throws IllegalArgumentException throws this exception if the file name is invalid, either
   *                                  it is null or it is not the valid jpeg format
   */
  public JPEGImportManager(String filename) {
    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    if (!filename.endsWith(".jpeg") && !filename.endsWith(".jpg")) {
      throw new IllegalArgumentException("File must be in jpeg format");
    }
    this.filename = filename;

    try {
      this.inputStream = new FileInputStream(filename);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
  }

  @Override
  public IImage apply() throws IllegalArgumentException {
    BufferedImage img;
    try {
      img = ImageIO.read(inputStream);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read image");
    }

    if (img == null) {
      throw new IllegalArgumentException("Invalid file");
    }

    List<List<IPixel>> pixelList = new ArrayList<>();
    for (int i = 0; i < img.getHeight(); i ++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < img.getWidth(); j ++) {
        Color pixelColor = new Color(img.getRGB(j,i), false);
        rowList.add(new Pixel(pixelColor.getRed(),
            pixelColor.getGreen(), pixelColor.getBlue()));
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, filename);
  }
}
