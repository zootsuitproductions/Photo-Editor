package hw05.imageedits;

/**
 * Represents a GreyScale linear color transformation. This transforms the pixels of an IImage into
 * the correct pixels that now have a grey tone.
 */
public class GreyScaleTransformation extends AbstractTransparentLinearColorTransformation {

  /**
   * Constructs a GreyScaleTransformation object that has the correct transformation matrix assigned
   * to it that will correctly transform an IImage's pixels into pixels that will produce the
   * same image in a grey tone.
   */
  public GreyScaleTransformation() {
    super(new float[][]{{.2126F, .7152F, .0722F},
        {.2126F, .7152F, .0722F},
        {.2126F, .7152F, .0722F}});
  }

}
