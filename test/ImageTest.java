import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import hw05.image.IImage;
import hw05.image.Image;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Tests class for the Image class: encompassed are tests that show that an image has the
 * correct functionality and is able to be edited.
 */
public class ImageTest {


  //tests when the image has a null set of pixels
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullListOfPixels() {
    IImage nullListOfPixels = new Image(null, "hello");
  }

  //tests when the image has a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFileName() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(40, 50, 60)))));
    IImage nullListFileName = new Image(pixelList, null);
  }

  //tests when the image has a null file name and a null list of pixels
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFileNameAndNullListOfPixels() {
    IImage nullListOfPixelsAndNullFileName = new Image(null, null);
  }

  //tests when the image has an empty pixelList
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyListOfPixels() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList())));
    IImage emptyListOfPixels = new Image(pixelList, "hello");
  }

  //test when the pixelList of an image does not have the same length of each list of inner pixels
  //you cannot have an image that does not have full dimensions or gaps
  @Test(expected = IllegalArgumentException.class)
  public void testListOfPixelsNotFullImage() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(4,7,8))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixelsDoesNotHaveFullListDimensions = new Image(pixelList, "hello");
  }

  //tests when the first row of the pixelList is of size zero and then the rest of the rows
  //have pixels and are of equal length
  @Test(expected = IllegalArgumentException.class)
  public void testListOfPixelsEmptyFirstListThenEqualSizeListsAfter() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList()),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6))),
            new ArrayList<>(Arrays.asList(new Pixel(4,50,64))),
            new ArrayList<>(Arrays.asList(new Pixel(42,5,34)))));
    IImage listOfPixelsFirstListEmptyRestAreOfEqualLength = new Image(pixelList, "hello");
  }

  //tests when the first of the pixelList has a populated list but the rest of the rows are empty
  @Test(expected = IllegalArgumentException.class)
  public void testListOfPixelsFirstListPopulatedWhileRestAreEmpty() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6))),
            new ArrayList<>(Arrays.asList()),
            new ArrayList<>(Arrays.asList())));
    IImage listOfPixelsFirstListPopulatedRestAreEmpty = new Image(pixelList, "hello");
  }

  //tests when the list of lists of pixels has only one list of pixels in it
  @Test
  public void testOnlyOneListOfPixels() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    assertTrue(listOfPixels.getPixel2dArray().size() == 1);
    assertTrue(listOfPixels.getPixelHeight() == 1);
    assertTrue(listOfPixels.getPixelWidth() == 1);
  }

  //tests to shows a valid image
  @Test
  public void testValidImage() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(50, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,10,6),
                new Pixel(30, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,5,6),
                new Pixel(55, 10, 5)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    assertTrue(listOfPixels.getPixel2dArray().size() == 3);
    assertTrue(listOfPixels.getPixelHeight() == 3);
    assertTrue(listOfPixels.getPixelWidth() == 2);
  }

  //tests to shows a valid image with all min pixel values
  @Test
  public void testValidImageWithAllMinPixelValues() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(0,0,0),
            new Pixel(0, 0, 0))),
            new ArrayList<>(Arrays.asList(new Pixel(0,0,0),
                new Pixel(0, 0, 0))),
            new ArrayList<>(Arrays.asList(new Pixel(0,0,0),
                new Pixel(0, 0, 0)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    assertTrue(listOfPixels.getPixel2dArray().size() == 3);
    assertTrue(listOfPixels.getPixelHeight() == 3);
    assertTrue(listOfPixels.getPixelWidth() == 2);
  }

  //tests to shows a valid image with all max pixel
  @Test
  public void testValidImageAllMaxPixelValues() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(255,255,255),
            new Pixel(255, 255, 255))),
            new ArrayList<>(Arrays.asList(new Pixel(255,255,255),
                new Pixel(255, 255, 255))),
            new ArrayList<>(Arrays.asList(new Pixel(255,255,255),
                new Pixel(255, 255, 255)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    assertTrue(listOfPixels.getPixel2dArray().size() == 3);
    assertTrue(listOfPixels.getPixelHeight() == 3);
    assertTrue(listOfPixels.getPixelWidth() == 2);
  }

  //tests to shows a valid image with all of the same pixel values
  @Test
  public void testValidImageAllSamePixelValues() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    assertTrue(listOfPixels.getPixel2dArray().size() == 3);
    assertTrue(listOfPixels.getPixelHeight() == 3);
    assertTrue(listOfPixels.getPixelWidth() == 2);
  }


  //getPixelAtTests!!!!


  //tries to get a PixelAtANegativeRowPosition
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtNegativeRowPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(-1, 1);
  }

  //tries to get a PixelAtANegativeColumnPosition
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtNegativeColumnPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(1, -1);
  }

  //tries to get a PixelAtANegativeRowAndColumnPosition
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtNegativeRowAndColumnPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(-1, -1);
  }

  //tries to get a PixelAt an out of bounds RowPosition
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtOutOfBoundsRowPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(4, 1);
  }

  //tries to get a PixelAt an out of bounds ColumnPosition
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtOutOfBoundsColumnPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(1, 10);
  }

  //tries to get a PixelAt an out of bounds RowAndColumn Position
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtOutOfBoundsRowAndColumnPosition() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    listOfPixels.getPixelAt(10, 10);
  }

  //tests a valid getPixelAt example
  @Test
  public void testValidGetPixelAtExample() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
            new Pixel(4, 4 ,4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4))),
            new ArrayList<>(Arrays.asList(new Pixel(4,4,4),
                new Pixel(4, 4, 4)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    IPixel pixel = listOfPixels.getPixelAt(1, 1);
    IPixel pixel2 = new Pixel(4,4,4);
    assertEquals(pixel, pixel2);
  }

  //tests a valid getPixelAt another valid example
  @Test
  public void testValidGetPixelAtAnotherExample() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(50, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,10,6),
                new Pixel(30, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,5,6),
                new Pixel(55, 10, 5)))));
    IImage listOfPixels = new Image(pixelList, "hello");
    IPixel pixel = listOfPixels.getPixelAt(2, 1);
    IPixel pixel2 = new Pixel(55,10,5);
    assertEquals(pixel, pixel2);
  }



  //getPixel2DArrayTests



  //tests getPixel2DArray multiple inside lists of pixels
  @Test
  public void testValidGet2DPixelArrayValidMultipleLists() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(50, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,10,6),
                new Pixel(30, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,5,6),
                new Pixel(55, 10, 5)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertEquals(new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(50, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,10,6),
                new Pixel(30, 0, 5))),
            new ArrayList<>(Arrays.asList(new Pixel(5,5,6),
                new Pixel(55, 10, 5)))))
        , listOfPixelsImage.getPixel2dArray());
  }

  //tests getPixel2DArray only one list of pixels inside of the image with more than one pixel
  @Test
  public void testValidGet2DPixelArrayValidOnlyOneListMoreThanOnePixel() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(50, 0, 5)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertEquals(new ArrayList<>(Arrays.
            asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(50, 0, 5)))))
        , listOfPixelsImage.getPixel2dArray());
  }

  //tests getPixel2DArray only one list of pixels inside of the image with only one pixel
  @Test
  public void testValidGet2DPixelArrayValidOnlyOneListOnlyOnePixel() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertEquals(new ArrayList<>(Arrays.
            asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))))
        , listOfPixelsImage.getPixel2dArray());
  }



  //tests for getPixelWidth


  //tests getPixelWidthWhenThereIsOnlyOnePixelPerList
  @Test
  public void testGetPixelWidthOnePixelPerList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelWidth() == 1);
  }

  //tests getPixelWidthWhenThereIsOnlyOnePixelPerListAndOnlyOneInnerList
  @Test
  public void testGetPixelWidthOnePixelPerListOnlyOneInnerList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelWidth() == 1);
  }

  //tests getPixelWidthWhenThereAreMultiplePixelsInTheList
  @Test
  public void testGetPixelWidthMultiplePixesPerList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelWidth() == 3);
  }

  //tests getPixelWidthWhenThereAreMultiplePixelsButOnlyOneInnerList
  @Test
  public void testGetPixelWidthMultiplePixelsButOnlyOneInnerList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelWidth() == 3);
  }



  //tests for getPixelHeight

  //tests getPixelHeightWhenThereIsOnlyInnerArrayListAndOnlyOnePixel
  @Test
  public void testGetPixelHeightWithOnlyOneInnerArrayList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelHeight() == 1);
  }

  //tests getPixelHeightWhenThereIsOnlyInnerArrayListAndMoreThanOnePixel
  @Test
  public void testGetPixelHeightWithOnlyOneInnerArrayListAndMultiplePixels() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelHeight() == 1);
  }

  //tests getPixelHeightWhenThereAreMultipleInnerArrayListAndOnlyOnePixel
  @Test
  public void testGetPixelHeightWithMultipleInnerArrayListAndOnlyOnePixelEach() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6))),
            new ArrayList<>(Arrays.asList(new Pixel(40,50,3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,2,3)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelHeight() == 3);
  }

  //tests getPixelHeightWhenThereAreMultipleInnerArrayListAndOnlyMiultiplePixelsInEach
  @Test
  public void testGetPixelHeightWithMultipleInnerArrayListAndMultiplePixelsInEach() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "hello");
    assertTrue(listOfPixelsImage.getPixelHeight() == 3);
  }


  //tests for getFileName

  //tests getFileNameEmptyString
  @Test(expected = IllegalArgumentException.class)
  public void testGetFileNameEmptyString() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "");
    assertEquals("", listOfPixelsImage.getFileName());
  }

  //tests getFileNameNormalName
  @Test
  public void testGetFileNameNormalName() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "Koala.ppm");
    assertEquals("Koala.ppm", listOfPixelsImage.getFileName());
  }

  //tests getFileName as numbers
  @Test
  public void testGetFileNameNumbers() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "1323");
    assertEquals("1323", listOfPixelsImage.getFileName());
  }

  //tests getFileName with spaces
  @Test
  public void testGetFileNameWithSpaces() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "13 2 3");
    assertEquals("13 2 3", listOfPixelsImage.getFileName());
  }

  //tests getFileName with not number and letter characters
  @Test
  public void testGetFileNameWithNonNumberAndLetterCharacters() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage = new Image(pixelList, "@!@1");
    assertEquals("@!@1", listOfPixelsImage.getFileName());
  }


  //equals tests


  //tests for equals when two images have different pixel lists but the same file name
  @Test
  public void testEqualsSameFileNameDifferentPixelList() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "hello");

    List<List<IPixel>> pixelList2 = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage2 = new Image(pixelList2, "hello");

    assertNotEquals(listOfPixelsImage1, listOfPixelsImage2);
  }


  //tests for equals when two images have the same pixel lists but different file names
  @Test
  public void testEqualsDifferentFileNamesSamePixelLists() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "helloYo");

    List<List<IPixel>> pixelList2 = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage2 = new Image(pixelList2, "hello");

    assertNotEquals(listOfPixelsImage1, listOfPixelsImage2);
  }

  //tests for equals when two images have the same content but are different objects
  @Test
  public void testEqualsSameContentDifferentObjects() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "hello");

    List<List<IPixel>> pixelList2 = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage2 = new Image(pixelList2, "hello");

    assertEquals(listOfPixelsImage1, listOfPixelsImage2);
  }

  //tests for equals when two images have the same content and are the same object
  @Test
  public void testEqualsSameContentSameObjects() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "hello");

    assertEquals(listOfPixelsImage1, listOfPixelsImage1);
  }


  //hashcode tests

  //tests hashcode when two different images are equal
  @Test
  public void testHashCodeSameContentDifferentObjects() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "hello");

    List<List<IPixel>> pixelList2 = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage2 = new Image(pixelList2, "hello");

    assertEquals(listOfPixelsImage1, listOfPixelsImage2);

    assertTrue(listOfPixelsImage1.hashCode() == listOfPixelsImage2.hashCode());
  }

  //tests hashcode when the same images are equal
  @Test
  public void testHashCodeSameContentSameObjects() {
    List<List<IPixel>> pixelList = new ArrayList<>(Arrays.
        asList(new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
            new Pixel(40, 50, 3),
            new Pixel(40, 2, 3))),
            new ArrayList<>(Arrays.asList(new Pixel(4,5,6),
                new Pixel(40, 50, 3),
                new Pixel(40, 2, 3)))));
    IImage listOfPixelsImage1 = new Image(pixelList, "hello");


    assertEquals(listOfPixelsImage1, listOfPixelsImage1);

    assertTrue(listOfPixelsImage1.hashCode() == listOfPixelsImage1.hashCode());
  }

}