package hw05.importimages;

import hw05.image.IImage;
import hw05.image.Image;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the PPMImportManager that gives a client the ability to import a PPM file into the
 * model and turn it into a valid IImage that can be edited to their desire.
 */
public class PPMImportManager implements IOManager {

  private final String filename;
  private final InputStream inputStream;

  /**
   * Constructs a PPMImportManager that takes in a PPM file name and is able to to
   * turn it into a correct IImage for the model if the file is valid.
   *
   * @param filename The PPM file name that the client wants to turn into an IImage
   * @throws IllegalArgumentException throws this exception if the file name is not the correct
   *                                  ppm format or is null
   *
   */
  public PPMImportManager(String filename) {
    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    if (filename.length() < 4 || !filename.endsWith(".ppm")) {
      throw new IllegalArgumentException("File must be in ppm format");
    }
    this.filename = filename;

    try {
      this.inputStream = new FileInputStream(filename);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
  }

  /**
   * Constructs a PPMImportManager using a supplied input stream and filename.
   *
   * @param filename The PPM file name that the client wants to turn into an IImage
   * @param stream The input stream to read from
   * @throws IllegalArgumentException throws this exception if the file name is not the correct
   *                                  ppm format or is null
   */
  public PPMImportManager(String filename, InputStream stream) {
    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    if (filename.length() < 4 || !filename.endsWith(".ppm")) {
      throw new IllegalArgumentException("File must be in ppm format");
    }
    this.filename = filename;

    if (stream == null) {
      throw new IllegalArgumentException("Input stream cannot be null");
    }
    this.inputStream = stream;
  }

  @Override
  public IImage apply() throws IllegalArgumentException {

    Scanner sc = new Scanner(this.inputStream);

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    if (!sc.hasNext()) {
      throw new IllegalArgumentException("Invalid PPM file: empty file");
    }
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = getNextInt(sc);
    int height = getNextInt(sc);
    int maxValue = getNextInt(sc);

    if (maxValue != 255) {
      throw new IllegalArgumentException("Max value must be 255");
    }
    List<List<IPixel>> pixelList = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = getNextInt(sc);
        int g = getNextInt(sc);
        int b = getNextInt(sc);
        IPixel pixel1 = new Pixel(r, g, b);
        rowList.add(pixel1);
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, filename);
  }

  /**
   * Gets the next integer value in the scanner if the provided scanner has a next.
   *
   * @param scanner The provided scanner that is being used to try and read the provided
   *                file that the client provided.
   * @return Returns the next integer in the provided scanner if possible.
   * @throws IllegalArgumentException throws this exception if the provided scanner is null or
   *         if the provided scanner does not have a next integer value.
   */
  private int getNextInt(Scanner scanner) throws IllegalArgumentException {
    if (scanner == null) {
      throw new IllegalArgumentException("Must be non null");
    }
    if (scanner.hasNextInt()) {
      return scanner.nextInt();
    } else {
      throw new IllegalArgumentException("Ran out of input in file");
    }
  }
}
