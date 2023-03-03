package hw05.exportimages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Represents the TextFileExportManager that creates a text file that can have any type of
 * input inside of it and exports it when it is finished being populated.
 */
public class TextFileExportManager implements ITextExportManager {

  protected final Writer fileWriter;

  /**
   * Constructs a TextFileExportManager object that takes in the clients desired filename
   * and creates a text file with the client desired input.
   *
   * @param fileName The file name of the text file that will be created by the manager.
   * @throws IllegalArgumentException throws this exception if the file name is null or
   *                                  is not a valid text file type.
   */
  public TextFileExportManager(String fileName) {
    if (fileName == null || fileName.equals("")) {
      throw new IllegalArgumentException("Filename or image cannot be null");
    }
    if (!fileName.endsWith(".txt")) {
      throw new IllegalArgumentException("File must be in txt format");
    }
    try {
      fileWriter = new BufferedWriter(new FileWriter(fileName));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not create file");
    }
  }

  /**
   * Constructs a TextFileExportManager object that takes in a writer that will be used
   * to append all of the desired client input into the text file.
   *
   * @throws IllegalArgumentException throws this exception if the provided writer is null
   */
  public TextFileExportManager(Writer writer) {
    if (writer == null) {
      throw new IllegalArgumentException("The writer cannot be null");
    }
    fileWriter = writer;
  }


  @Override
  public void export(List<String> fileList) {
    if (fileList == null) {
      throw new IllegalArgumentException("The file list cannot be null!");
    }

    try {
      for (String file : fileList) {
        fileWriter.append(file + "\n");
      }
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not append to the file");
    }
  }
}
