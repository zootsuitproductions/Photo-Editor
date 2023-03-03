package hw05.exportimages;

import hw05.image.IImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Represents a PPMExportManager that is able to export an IImage into a PPM formatted file for
 * the client if they desire.
 */
public class PPMExportManager implements IImageExportManager {

  protected final Writer fileWriter;

  //note: changed from last homework, added assignment in constructor
  private boolean hasExported;

  /**
   * Constructs a PPMExportManager that writes a given file name.
   * @param fileName the name of the file to be written
   * @throws IllegalArgumentException throws this exception if the file name is null or is
   *                                  not the valid ppm format
   */
  public PPMExportManager(String fileName) {
    //changed:
    hasExported = false;

    if (fileName == null || fileName.equals("")) {
      throw new IllegalArgumentException("Filename or image cannot be null");
    }
    if (fileName.length() < 4 || !fileName.endsWith(".ppm")) {
      throw new IllegalArgumentException("File must be in ppm format");
    }
    try {
      fileWriter = new BufferedWriter(new FileWriter(fileName));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not create file");
    }
  }

  /**
   * Constructs a PPMExportManager that writes to the given writer.
   * @param writer the object to write to.
   * @throws IllegalArgumentException throws this exception if the writer is null
   */
  public PPMExportManager(Writer writer) {
    //changed:
    hasExported = false;
    if (writer == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    fileWriter = writer;
  }

  @Override
  public void export(IImage image) {
    if (hasExported) {
      throw new IllegalArgumentException("Have already exported a file using this manager");
    }
    hasExported = true;
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }


    try {
      fileWriter.append("P3");
      fileWriter.append("\n");
      fileWriter.append(Integer.toString(image.getPixelWidth()));
      fileWriter.append(" ");
      fileWriter.append(Integer.toString(image.getPixelHeight()));
      fileWriter.append("\n");
      fileWriter.append(Integer.toString(255));
      fileWriter.append("\n");


      for (int i = 0; i < image.getPixelHeight(); i++) {
        for (int j = 0; j < image.getPixelWidth(); j++) {
          fileWriter.append(Integer.toString(image.getPixelAt(i, j).getRed()));
          fileWriter.append("\n");
          fileWriter.append(Integer.toString(image.getPixelAt(i, j).getGreen()));
          fileWriter.append("\n");
          fileWriter.append(Integer.toString(image.getPixelAt(i, j).getBlue()));
          fileWriter.append("\n");
        }
      }

      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("The file could not be created");
    }
  }
}
