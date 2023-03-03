package hw05.imageedits;

/**
 * Represents a SepiaImage linear color transformation. This transforms the pixels of an IImage into
 * the correct pixels that now have a reddish brown Sepia tone.
 */
public class SepiaTransformation extends AbstractTransparentLinearColorTransformation {

  /**
   * Constructs a SepiaTransformation object that has the correct transformation matrix assigned
   * to it that will correctly transform an IImage's pixels into pixels that will produce the
   * same image in a reddish brown Sepia tone.
   */
  public SepiaTransformation() {
    super(new float[][]{{.393F, .769F, .189F},
        {.349F, .686F, .168F},
        {.272F, .534F, .131F}});
  }
}
