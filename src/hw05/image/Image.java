package hw05.image;

import hw05.pixel.IPixel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Image that can be edited by the client and exported to the location of their
 * choice. An image consists of a list of pixels that makes up it visual contents and its fileName.
 */
public class Image implements IImage {

  private final List<List<IPixel>> pixelList;
  private final String fileName;

  /**
   * Constructs an Image object that contains its list of IPixels that contain the color values
   * that are combined in order to create the visual representation of the image and its
   * file name.
   *
   * @param pixelList The list of pixels that makes up an Image.
   * @param fileName The file name of the image.
   * @throws IllegalArgumentException throws this exception if the pixelList or fileName are null
   *                                  or if the provided pixelList is invalid.
   */
  public Image(List<List<IPixel>> pixelList, String fileName) {
    if (pixelList == null || fileName == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }

    if (fileName.equals("")) {
      throw new IllegalArgumentException("The fileName cannot be empty!");
    }

    checkConsistentCollumnSize(pixelList);

    this.pixelList = pixelList;

    this.fileName = fileName;
  }

  /**
   * Checks to see if the image's pixelList is invalid or not. An invalid pixelList is a pixelList
   * that is null, is empty, has no pixels in one of its list or has an uneven amount of pixels
   * in each of the list of pixels that exists in the image.
   *
   * @param pixelList The pixel list that exists in an image.
   * @throws IllegalArgumentException throws this exception if the pixelList is invalid.
   */
  protected void checkConsistentCollumnSize(List<List<IPixel>> pixelList)
      throws IllegalArgumentException {
    if (pixelList == null) {
      throw new IllegalArgumentException("pixelList cannot be null");
    }
    if (pixelList.size() <= 0) {
      throw new IllegalArgumentException("pixelList cannot be empty");
    }
    if (pixelList.get(0).size() <= 0) {
      throw new IllegalArgumentException("pixelList cannot be empty");
    }

    if (pixelList.size() > 1) {
      for (int i = 0; i < pixelList.size() - 1; i ++) {
        if (pixelList.get(i).size() != pixelList.get(i + 1).size()) {
          throw new IllegalArgumentException("Invalid pixel array column dimensions");
        }
      }
    }
  }

  @Override
  public IPixel getPixelAt(int row, int collum) throws IllegalArgumentException {
    try {
      return this.pixelList.get(row).get(collum);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Pixel not found");
    }
  }

  @Override
  public List<List<IPixel>> getPixel2dArray() {
    List<List<IPixel>> list2d = new ArrayList<>();

    for (int i = 0; i < this.pixelList.size(); i ++) {
      List<IPixel> list1d = new ArrayList<>();
      for (int j = 0; j < this.pixelList.get(i).size(); j ++) {
        list1d.add(this.pixelList.get(i).get(j));
      }
      list2d.add(list1d);
    }
    return list2d;
  }

  @Override
  public int getPixelWidth() {
    return this.pixelList.get(0).size();
  }

  @Override
  public int getPixelHeight() {
    return this.pixelList.size();
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Image)) {
      return false;
    }
    Image pixel = (Image) that;

    return pixel.getPixel2dArray().equals(this.getPixel2dArray())
        && pixel.getFileName() == this.getFileName()
        && pixel.getPixelHeight() == this.getPixelHeight()
        && pixel.getPixelWidth() == this.getPixelWidth();
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, pixelList);
  }

}
