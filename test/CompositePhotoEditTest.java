import static org.junit.Assert.assertEquals;

import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.imageedits.BlurFilter;
import hw05.imageedits.CompositePhotoEdit;
import hw05.imageedits.GreyScaleTransformation;
import hw05.imageedits.IPhotoEdit;
import hw05.imageedits.SepiaTransformation;
import hw05.imageedits.SharpenFilter;
import hw05.pixel.Pixel;
import org.junit.Test;

/**
 * Tests class for the CompositePhotoEdit class: encompassed are tests that show that the
 * CompositePhotoEdit is able to make two photo edits on a valid IImage and it has
 * the correct functionality.
 */
public class CompositePhotoEditTest extends AbstractPhotoEditTest {

  @Override
  protected IPhotoEdit createEdit() {
    return new CompositePhotoEdit(new BlurFilter(), new SepiaTransformation());
  }

  @Test
  public void testOperationsApplied() {
    IImage compositeFiltered = filter.apply(i7x7);
    IImage check = new SepiaTransformation().apply(new BlurFilter().apply(i7x7));

    assertEquals(compositeFiltered, check);
  }

  @Test
  public void testMultipleChained() {
    IImage compositeFiltered = new CompositePhotoEdit(
        new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
        new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())).apply(i7x7);

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7))));

    assertEquals(compositeFiltered, check);
  }

  @Test
  public void testMultipleChainedPPM() {
    IImage compositeFiltered = new CompositePhotoEdit(
        new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
        new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())).apply(i7x7ppm);

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7ppm))));

    assertEquals(compositeFiltered, check);
  }

  @Test
  public void testSameFilterTwice() {
    IImage compositeFiltered = new CompositePhotoEdit(
        new BlurFilter(), new BlurFilter()).apply(i7x7);

    IImage check = new BlurFilter().apply(
        new BlurFilter().apply(i7x7));

    assertEquals(compositeFiltered, check);
  }

  @Test
  public void testClampingTo255WhenLargerThan255() {
    IImage filtered = filter.apply(new CheckerBoardImageGenerator(
        20,2, new Pixel(255, 255,255),
        new Pixel(255, 255,255)).generateImage("white"));
    assertEquals(filtered.getPixelAt(4, 5), new Pixel(255, 255, 239));
  }

  @Test
  public void testClampingToZeroWhenNegative() {
    IImage filtered = filter.apply(i1x1);
    assertEquals(filtered.getPixelAt(0, 0), new Pixel(0, 0, 0));
  }
}