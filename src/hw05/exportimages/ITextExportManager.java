package hw05.exportimages;

import java.util.List;

/**
 * Represents the ITextExportManager interface which provides the functionality for
 * being able to create .txt files in different types of ways for a client.
 */
public interface ITextExportManager {

  /**
   * Organizes a provided list of different types of filePath Strings into a .txt file
   * which shows all of the filePath strings in the provided list in an informative manner.
   *
   * @param fileList A list of Strings that represent different types of file paths.
   * @throws IllegalArgumentException throws this exception if the fileList is null.
   */
  void export(List<String> fileList);
}
