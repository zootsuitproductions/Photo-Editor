package hw06;

import hw05.image.IImage;
import java.util.Objects;

/**
 * Represents a Layer that holds an image, a name, and visibility status.
 * Layers can be stacked on top of each other in order to make a layered image.
 */
public class Layer implements ILayer {
  private final String layerName;
  private final IImage image;
  private boolean isVisible;

  /**
   * Constructs a Layer object that represents a layer that exists inside of a layered image.
   * This Layer object can be stacked on other Layer objects to create a layered image.
   *
   * @param layerName The name of the layer.
   * @param image The image that a layer contains.
   * @throws IllegalArgumentException throws this exception if the layer name is null or empty
   */
  public Layer(String layerName, IImage image) {
    if (layerName == null || layerName.equals("")) {
      throw new IllegalArgumentException("Args cannot be null/empty");
    }
    this.layerName = layerName;
    this.image = image;
    this.isVisible = true;
  }

  /**
   * Constructs a Layer object that represents a layer that exists inside of a layered image.
   * This Layer object can be stacked on other Layer objects to create a layered image.
   *
   * @param layerName The name of the layer.
   * @param image The image that a layer contains.
   * @param isVisible Is the image in the layer visible or not?
   * @throws IllegalArgumentException throws this exception if the layer name is null or empty
   */
  public Layer(String layerName, IImage image, boolean isVisible) {
    if (layerName == null || layerName.equals("")) {
      throw new IllegalArgumentException("Args cannot be null/empty");
    }
    this.layerName = layerName;
    this.image = image;
    this.isVisible = isVisible;
  }

  @Override
  public boolean isVisible() {
    return this.isVisible;
  }

  @Override
  public String getName() {
    return this.layerName;
  }

  @Override
  public IImage getImage() {
    return this.image;
  }

  @Override
  public void setVisibility(boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
  public String toString() {
    String filename = "no image";
    if (image != null) {
      filename = image.getFileName();
    }
    return layerName + " | visible: " + isVisible + " | image file: " + filename;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Layer)) {
      return false;
    }
    Layer layer = (Layer) that;

    return layer.getName().equals(this.layerName)
        && checkImages(layer.getImage(), this.image)
        && layer.isVisible() == this.isVisible;
  }

  /**
   * Checks the equality of two separate layers' images when they are both null to avoid a
   * NullPointerException being thrown.
   *
   * @param image1 The first image that is being checked for null
   * @param image2 The second image that is being checked for null
   * @return Returns if two images are equal, even if they are both null
   */
  private boolean checkImages(IImage image1, IImage image2) {
    if (image1 == null && image2 == null) {
      return true;
    }
    if (image1 == null && image2 != null) {
      return false;
    }
    if (image2 == null && image1 != null) {
      return false;
    }
    return image1.equals(image2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(layerName, image, isVisible);
  }
}
