package hw05.imageedits;

import hw05.image.IImage;
import hw05.pixel.IPixel;
import hw05.image.Image;
import hw05.pixel.Pixel;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the AbstractFilter class. This is a filtering operation that occurs on an IImage
 * which uses a kernel that runs through every single pixel in an image and changes it by
 * altering values of the color in the pixel depending om which ColorChannel the filter is
 * meant to change.
 */
public abstract class AbstractFilter implements IPhotoEdit {

  protected final Kernel kernel;

  /**
   * Constructs an AbstractFilter with a specific kernel that identifies how big large
   * the kernel is and what are the values that are going to be used in order to create
   * the desired filters.
   *
   * @param kernel A kernel that signals the dimensions of the filter and the values that exist
   *               inside of the array of values.
   * @throws IllegalArgumentException throws this exception when the kernel is null, the kernel is
   *                                  is empty, or if the provided filter does not have odd
   *                                  dimensions.
   */
  public AbstractFilter(Kernel kernel) {
    this.kernel = checkKernel(kernel);
  }

  protected Kernel checkKernel(Kernel kernel) {
    if (kernel == null) {
      throw new IllegalArgumentException("kernel cannot be null");
    }
    if (kernel.getHeight() == 0 || kernel.getWidth() == 0) {
      throw new IllegalArgumentException("Kernel cannot be empty");
    }
    if (kernel.getHeight() % 2 == 0 || kernel.getWidth() % 2 == 0) {
      throw new IllegalArgumentException("You need to have odd dimensions!");
    }
    if (kernel.getHeight() != kernel.getWidth()) {
      throw new IllegalArgumentException("Your kernel needs to be a perfect square!");
    }
    return kernel;
  }

  @Override
  public IImage apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image must be non null");
    }

    List<List<IPixel>> pixelList = new ArrayList<>();
    for (int i = 0; i < image.getPixelHeight(); i++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < image.getPixelWidth(); j++) {
        rowList.add(this.applyToPixelAt(i, j, image));
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, image.getFileName());
  }

  /**
   * Applies the specific calculations that are needed to create the new Pixel value
   * which will create the correct filter edit that was asked for. The specific calculations are
   * applied to the pixel which is at the given x and y coordinates of the provided IImage.
   *
   * @param x The row coordinate of the desired pixel in the provided IImage
   * @param y The column coordinate of the desired pixel in the provided IImage
   * @param image The IImage that the client is asking to filter.
   * @return Returns the new Pixel that has been filtered correctly
   * @throws IllegalArgumentException throws this exception if the image is null or the x and y
   *                                  values are negative.
   */
  protected IPixel applyToPixelAt(int x, int y, IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("You cannot have a negative pixel position!");
    }

    List<IPixel> surroundingPixels = this.getSurroundingPixels(x, y, image);

    float redSum = 0;
    float greenSum = 0;
    float blueSum = 0;

    float[] kernelMatrix = this.kernel.getKernelData(null);

    for (int i = 0; i < kernel.getWidth() * kernel.getHeight(); i++) {
      redSum += surroundingPixels.get(i).getRed() * kernelMatrix[i];
      greenSum += surroundingPixels.get(i).getGreen() * kernelMatrix[i];
      blueSum += surroundingPixels.get(i).getBlue() * kernelMatrix[i];
    }

    return new Pixel(Math.round(redSum), Math.round(greenSum), Math.round(blueSum));
  }

  /**
   * Gets the surrounding matrix of Pixels that corresponds with the
   * kernel for this filter operation, in row major order. If a pixel is off the edge
   * of an image, populates the matrix with a pixel with all values 0.
   *
   * @param x The row that the central pixel in the filter is inside of
   * @param y The column that the central pixel in the filter is inside of
   * @param image The image that is being edited by the filter.
   * @return Returns the list of pixels that is correlated
   *         to the filter matrix that is applied to a specific pixel in an image.
   * @throws IllegalArgumentException throws this exception if the image is null or the provided
   *                                  x and y values are invalid.
   */
  protected List<IPixel> getSurroundingPixels(int x, int y, IImage image) {

    if (image == null) {
      throw new IllegalArgumentException("Args cannot be null");
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("You cannot have a negative pixel position!");
    }
    List<IPixel> pixelMatrix = new ArrayList<>();

    for (int i = 0; i < this.kernel.getHeight(); i++) {
      for (int j = 0; j < this.kernel.getWidth(); j++) {
        int newY = y + i - kernel.getYOrigin();
        int newX = x + j - kernel.getXOrigin();
        try {
          pixelMatrix.add(image.getPixelAt(newX, newY));
        } catch (IllegalArgumentException e) {
          pixelMatrix.add(new Pixel(0, 0, 0));
        }
      }
    }
    return pixelMatrix;
  }
}
