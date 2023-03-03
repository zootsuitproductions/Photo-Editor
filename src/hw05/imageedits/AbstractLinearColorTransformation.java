package hw05.imageedits;

import hw05.image.IImage;
import hw05.pixel.IPixel;
import hw05.image.Image;
import hw05.pixel.Pixel;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a linear color transformation which is a color transformation where the final red,
 * green and blue values of a pixel are linear combinations of its initial red, green and
 * blue values.
 */
public abstract class AbstractLinearColorTransformation implements IPhotoEdit {

  // INVARIANT: transformation matrix is a 2D array, which has 3 inner arrays of length 3.
  protected final float[][] transformationMatrix;

  /**
   * Constructs an AbstractLinearColorTransformation by taking in the appropriate
   * transformation matrix and applying it to the pixels of an hw05.image.IImage that the client
   * wants to be color transformed.
   *
   * @param transformationMatrix A matrix of values that every pixel in a provided image will be
   *                             multiplied by in order to create the correct color transformation
   *                             on the object.
   * @throws IllegalArgumentException throws this exception when the passed in transformation
   *                                  matrix is null or does not have 3X3 dimensions.
   */
  public AbstractLinearColorTransformation(float[][] transformationMatrix) {
    this.transformationMatrix = this.checkMatrix(transformationMatrix);
  }

  /**
   * Checks the transformation matrix that is passed into the color transformation and makes
   * sure that it is not null and has the correct 3X3 dimensions.
   *
   * @param transformationMatrix The transformation matrix that helps transform the pixels of an
   *                             image to the correct colors in order to produce the correct
   *                             color transformation result.
   * @return Returns the transformation matrix if it is valid.
   * @throws IllegalArgumentException throws this exception if the given transformation matrix is
   *                                  null or does not have the correct 3x3 dimensions.
   */
  protected float[][] checkMatrix(float[][] transformationMatrix) {
    if (transformationMatrix == null) {
      throw new IllegalArgumentException("Array cannot be null");
    }
    if (transformationMatrix.length != 3) {
      throw new IllegalArgumentException("2D Array must be 3x3");
    }

    for (int i = 0; i < transformationMatrix.length; i ++) {
      if (transformationMatrix[i].length != 3) {
        throw new IllegalArgumentException("2D Array must be 3x3");
      }
    }
    return transformationMatrix;
  }

  @Override
  public IImage apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image must be non null");
    }
    List<List<IPixel>> pixelList = new ArrayList<>();
    for (int i = 0; i < image.getPixelHeight(); i ++) {
      List<IPixel> rowList = new ArrayList<>();
      for (int j = 0; j < image.getPixelWidth(); j ++) {
        rowList.add(this.applyToPixelAt(i, j, image));
      }
      pixelList.add(rowList);
    }
    return new Image(pixelList, image.getFileName());
  }

  /**
   * Applies the transformation matrix's color transformation to a single pixel at a
   * specific place in the 2d list of pixels of an image. This color transformation multiplies
   * the pixels to the correct values in order to produce the desired result.
   *
   * @param x The row coordinate of the specific pixel that needs to be edited.
   * @param y The column coordinate of the specific pixel that needs to be edited.
   * @param image The specific image who's pixels are being edited for the color transformation.
   * @return Returns the pixel when it color values are done being color transformed correctly.
   * @throws IllegalArgumentException throws this exception if the provided image is null or if
   *                                  the x and y values are negative.
   */
  protected IPixel applyToPixelAt(int x, int y, IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("hw05.image.Image must be non null");
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("You cannot have a negative pixel position!");
    }

    IPixel p1 = image.getPixelAt(x, y);
    float greenValue = p1.getGreen();
    float blueValue = p1.getBlue();
    float redValue = p1.getRed();

    int greenSum = 0;
    int blueSum = 0;
    int redSum = 0;


    redSum = multiplyRow(redValue, greenValue, blueValue, transformationMatrix[0]);
    greenSum = multiplyRow(redValue, greenValue, blueValue, transformationMatrix[1]);
    blueSum = multiplyRow(redValue, greenValue, blueValue, transformationMatrix[2]);

    return new Pixel(redSum, greenSum, blueSum);
  }

  /**
   * Correctly adjusts the values of one of the pixel color values by multiplying the values from
   * one row of the transformation matrix that is provided.
   *
   * @param redV The current red value of the pixel in the IImage
   * @param greenV The current green value of the pixel in the IImage
   * @param blueV The current blue value of the pixel in the IImage
   * @param matrixRow A row from the transformation matrix that is provided that provides
   *                  the values that should be multiplied in order to create the correct new
   *                  pixel color value.
   * @return Returns the value of multiplying the matrix row values by their corresponding
   *         original pixel color values and adding them all up together.
   * @throws IllegalArgumentException throws this exception if the provided matrixRow is null.
   */
  protected int multiplyRow(float redV, float greenV, float blueV, float[] matrixRow) {
    if (matrixRow == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    return Math.round(matrixRow[0] * redV + matrixRow[1] * greenV + matrixRow[2] * blueV);
  }
}
