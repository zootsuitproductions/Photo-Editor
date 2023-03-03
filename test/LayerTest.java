import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import hw05.image.Image;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import hw06.ILayer;
import hw06.Layer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for LayerTest: encompassed are test that thoroughly test the functionality of the
 * Layer class and make sure that the Layers properly hold their images, names, and visibility
 * status.
 */
public class LayerTest {

  private final List<List<IPixel>> pixelList1 = new ArrayList<>();


  @Before
  public void initData() {
    pixelList1.add(new ArrayList<>(Arrays.asList(new Pixel(3, 4, 5))));
  }

  //tests when the layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructorNullName() {
    ILayer l1 = new Layer(null, new Image(pixelList1, "hello"));
  }

  //tests when the layer name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructorEmptyName() {
    ILayer l1 = new Layer("", new Image(pixelList1, "hello"));
  }

  //tests when the layer name is valid but it has a null image and shows that this is okay!
  @Test
  public void testLayerConstructorNullImage() {
    ILayer l1 = new Layer("hello", null);
    assertNull(l1.getImage());
  }

  //tests when second constructor when the layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2NullName() {
    ILayer l1 = new Layer(null, new Image(pixelList1, "hello"), true);
  }

  //tests when the second constructor layer name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2EmptyName() {
    ILayer l1 = new Layer("", new Image(pixelList1, "hello"), true);
  }

  //tests second constructor when the layer name is valid but it has a null image and shows that
  // this is okay!
  @Test
  public void testLayerConstructor2NullImage() {
    ILayer l1 = new Layer("hello", null, false);
    assertNull(l1.getImage());
  }

  //tests for isVisibile()


  //tests isVisible when layer is not visible
  @Test
  public void testIsVisibleLayerNotVisible() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hlo"), false);
    assertFalse(l1.isVisible());
  }

  //tests isVisible when layer is visible
  @Test
  public void testIsVisibleLayerWhenVisible() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hlo"), true);
    assertTrue(l1.isVisible());
  }

  //tests for getName()


  //tests getName when the name is possible
  @Test
  public void testGetNameValid() {
    ILayer l1 = new Layer("Hello", new Image(pixelList1, "hello"));
    assertEquals("Hello", l1.getName());
  }

  //tests for getImage()


  //tests getImage when the image is null
  @Test
  public void testGetImageNull() {
    ILayer layer = new Layer("Hello", null);
    assertNull(layer.getImage());
  }

  //tests getImage when the image is empty
  @Test(expected = IllegalArgumentException.class)
  public void testGetImageEmptyPixels() {
    List<List<IPixel>> pixelList = new ArrayList<>();
    ILayer layer = new Layer("Hello", new Image(pixelList, "hello"));
    assertEquals(new Image(pixelList, "hello"),layer.getImage());
  }

  //tests getImage when the image is valid and has pixels
  @Test
  public void testGetImageValidImageSomePixels() {
    ILayer layer = new Layer("Hello", new Image(pixelList1, "hello"));
    assertEquals(new Image(pixelList1, "hello"), layer.getImage());
  }

  //tests for setVisibility()


  //tests when you set a transparent image to visible
  @Test
  public void testTransparentToVisible() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "yo"), false);
    l1.setVisibility(true);
    assertTrue(l1.isVisible());
  }

  //tests when you set a transparent image to transparent
  @Test
  public void testTransparentToTransparent() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "yo"), false);
    l1.setVisibility(false);
    assertFalse(l1.isVisible());
  }


  //tests when you set a visible image to transparent
  @Test
  public void testVisibleToTransparent() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "yo"), true);
    l1.setVisibility(false);
    assertFalse(l1.isVisible());
  }

  //tests when you set a visible image to visible
  @Test
  public void testVisibleToVisible() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "yo"), true);
    l1.setVisibility(true);
    assertTrue(l1.isVisible());
  }

  //tests for layer to string


  //tests to String when layer is visible and everything else valid
  @Test
  public void testVisibleValidLayerToString() {
    ILayer layer = new Layer("hello", new Image(pixelList1, "hello.pmm"),
        true);
    assertEquals("hello | visible: true | image file: hello.pmm", layer.toString());
  }

  //tests to String when layer is transparent and everything else valid
  @Test
  public void testTransparentValidLayerToString() {
    ILayer layer = new Layer("hello", new Image(pixelList1, "hello.pmm"), false);
    assertEquals("hello | visible: false | image file: hello.pmm", layer.toString());
  }

  //tests to String when layer is transparent and the image is null
  @Test
  public void testTransparentNullImageLayerToString() {
    ILayer layer = new Layer("hello", null, false);
    assertEquals("hello | visible: false | image file: no image", layer.toString());
  }

  //tests to String when layer is visible and the image is null
  @Test
  public void testVisibleNullImageLayerToString() {
    ILayer layer = new Layer("hello", null, true);
    assertEquals("hello | visible: true | image file: no image", layer.toString());
  }

  //tests for the overridden equals method()


  //tests equals with two layers that are different objects but the same contents
  @Test
  public void testEqualsDifferentObjectSameLayer() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hello"), true);
    ILayer l2 = new Layer("hello", new Image(pixelList1, "hello"), true);
    assertEquals(l1, l2);
  }

  //tests equals with two layers that are the same objects
  @Test
  public void testEqualsSameLayerObjects() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hello"), true);
    assertTrue(l1.equals(l1));
  }

  //tests equals with two layers that are different objects but the same contents except visibility
  //level
  @Test
  public void testEqualsDifferentObjectSameLayerExceptVisibilityLevel() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hello"), true);
    ILayer l2 = new Layer("hello", new Image(pixelList1, "hello"), false);
    assertFalse(l1.equals(l2));
  }

  //tests equals with two layers that are different objects and have different contents
  @Test
  public void testEqualsDifferentObjectDifferentLayerConetns() {
    ILayer l1 = new Layer("hi", new Image(pixelList1, "hello"), true);
    ILayer l2 = new Layer("yo", new Image(pixelList1, "yo"), false);
    assertFalse(l1.equals(l2));
  }

  //tests for overridden hashcode method


  //tests hashcode when two different layer objects are equal
  @Test
  public void testHashCodeTwoDifferentLayerObjectAreEqual() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hello"), true);
    ILayer l2 = new Layer("hello", new Image(pixelList1, "hello"), true);
    assertEquals(l1, l2);

    assertTrue(l1.hashCode() == l2.hashCode());
  }

  //tests hashcode when the same layer objects are equal
  @Test
  public void testHashCodeSameLayerObjectAreEqual() {
    ILayer l1 = new Layer("hello", new Image(pixelList1, "hello"), true);
    assertEquals(l1, l1);

    assertTrue(l1.hashCode() == l1.hashCode());
  }


}