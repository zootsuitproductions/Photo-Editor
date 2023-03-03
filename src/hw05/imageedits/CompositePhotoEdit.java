package hw05.imageedits;

import hw05.image.IImage;

/**
 * Represents a CompositePhotoEdit which allows for a client to apply two transformations at once
 * to an IImage and link them together in order to create even more chains of IImage edits.
 *
 */
public class CompositePhotoEdit implements IPhotoEdit {

  private final IPhotoEdit transformation1;
  private final IPhotoEdit transformation2;

  /**
   * Constructs a CompositePhotoEdit object that applies multiple photoEdits to the same
   * IImage at the same time.
   *
   * @param transformation1 The first photoEdit that will be applied to the IImage
   * @param transformation2 The second photoEdit that will be applied to the IImage
   * @throws IllegalArgumentException this exception is thrown when either of the provided photo
   *                                  edits are null
   */
  public CompositePhotoEdit(IPhotoEdit transformation1, IPhotoEdit transformation2) {
    if (transformation1 == null || transformation2 == null) {
      throw new IllegalArgumentException("The provided photo edits cannot be null!");
    }
    this.transformation1 = transformation1;
    this.transformation2 = transformation2;
  }

  @Override
  public IImage apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("The image cannot be null!");
    }
    return this.transformation2.apply(this.transformation1.apply(image));
  }
}
