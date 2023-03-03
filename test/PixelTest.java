import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests class for the Pixel class: encompassed are tests that show that the Pixel class
 * has the correct functionality and is able to store its color values appropriately.
 */
public class PixelTest {

  private IPixel negativeRedPixel;
  private IPixel negativeGreenPixel;
  private IPixel negativeBluePixel;
  private IPixel allNegativePixel;

  private IPixel wayTooLargeRedPixel;
  private IPixel wayTooLargeGreenPixel;
  private IPixel wayTooLargeBluePixel;
  private IPixel allPixelWaysToLarge;

  private IPixel mixOfWayTooLargeAndNegative;

  private IPixel normalPixelValues;

  private IPixel allMinPixelValues;
  private IPixel allMaxPixelValues;
  private IPixel allSameValidValues;

  @Before
  public void setup() {
    negativeRedPixel = new Pixel(-4, 5 , 6);
    negativeGreenPixel = new Pixel(5, -6 ,7);
    negativeBluePixel = new Pixel(5, 6 ,-7);
    allNegativePixel = new Pixel(-6, -10 ,-7);

    wayTooLargeRedPixel = new Pixel(300, 30, 56);
    wayTooLargeGreenPixel = new Pixel(40, 300, 56);
    wayTooLargeBluePixel = new Pixel(30, 30, 560);
    allPixelWaysToLarge = new Pixel(300, 400, 500);

    mixOfWayTooLargeAndNegative = new Pixel(-30, 300, -3);

    normalPixelValues = new Pixel(40, 60 ,80);

    allMinPixelValues = new Pixel(0,0,0);
    allMaxPixelValues = new Pixel(255,255,255);
    allSameValidValues = new Pixel(4,4,4);

  }

  //tests when the red value of a pixel is automatically clamped because it is negative
  @Test
  public void testNegativeRedPixelValueClamped() {
    assertTrue(negativeRedPixel.getRed() != -4);
    assertTrue(negativeRedPixel.getRed() == 0);
  }

  //tests when the green value of a pixel is automatically clamped because it is negative
  @Test
  public void testNegativeGreenPixelValueClamped() {
    assertTrue(negativeGreenPixel.getGreen() != -6);
    assertTrue(negativeGreenPixel.getGreen() == 0);
  }

  //tests when the blue value of a pixel is automatically clamped because it is negative
  @Test
  public void testNegativeBluePixelValueClamped() {
    assertTrue(negativeBluePixel.getBlue() != -7);
    assertTrue(negativeBluePixel.getBlue() == 0);
  }

  //tests when every value in a pixel is automatically clamped because they are all negative
  @Test
  public void testAllNegativePixelValuesClamped() {
    assertTrue(allNegativePixel.getRed() != -6);
    assertTrue(allNegativePixel.getRed() == 0);

    assertTrue(allNegativePixel.getGreen() != -10);
    assertTrue(allNegativePixel.getGreen() == 0);

    assertTrue(allNegativePixel.getBlue() != -7);
    assertTrue(allNegativePixel.getBlue() == 0);
  }


  //tests when the red value of a pixel is automatically clamped because it is too large
  @Test
  public void testWayTooLargeRedPixelValueClamped() {
    assertTrue(wayTooLargeRedPixel.getRed() != 300);
    assertTrue(wayTooLargeRedPixel.getRed() == 255);
  }

  //tests when the green value of a pixel is automatically clamped because it is too large
  @Test
  public void testWayTooLargeGreenPixelValueClamped() {
    assertTrue(wayTooLargeGreenPixel.getGreen() != 300);
    assertTrue(wayTooLargeGreenPixel.getGreen() == 255);
  }

  //tests when the blue value of a pixel is automatically clamped because it is too large
  @Test
  public void testWayTooLargeBluePixelValueClamped() {
    assertTrue(wayTooLargeBluePixel.getBlue() != 560);
    assertTrue(wayTooLargeBluePixel.getBlue() == 255);
  }

  //tests when all the values of a pixel are automatically clamped because they are too large
  @Test
  public void testAllPixelValuesWayTooLargeValueClamped() {
    assertTrue(allPixelWaysToLarge.getRed() != 300);
    assertTrue(allPixelWaysToLarge.getRed() == 255);

    assertTrue(allPixelWaysToLarge.getGreen() != 400);
    assertTrue(allPixelWaysToLarge.getGreen() == 255);

    assertTrue(allPixelWaysToLarge.getBlue() != 500);
    assertTrue(allPixelWaysToLarge.getBlue() == 255);
  }

  //tests when a pixel is clamped because it is has both some values that are negative and way too
  //large
  @Test
  public void somePixelValuesAreNegativeWhileOthersAreTooLarge() {
    assertTrue(mixOfWayTooLargeAndNegative.getRed() != -30);
    assertTrue(mixOfWayTooLargeAndNegative.getRed() == 0);

    assertTrue(mixOfWayTooLargeAndNegative.getGreen() != 300);
    assertTrue(mixOfWayTooLargeAndNegative.getGreen() == 255);

    assertTrue(mixOfWayTooLargeAndNegative.getBlue() != -3);
    assertTrue(mixOfWayTooLargeAndNegative.getBlue() == 0);
  }

  //tests when a pixel has normal values and shows no values are clamped
  @Test
  public void allNormalPixelValues() {
    assertTrue(normalPixelValues.getRed() == 40);
    assertTrue(normalPixelValues.getGreen() == 60);
    assertTrue(normalPixelValues.getBlue() == 80);
  }

  //tests when there are all zero min pixel and shows no values are clamped
  @Test
  public void allMinValuePixelsShowNoChange() {
    assertTrue(allMinPixelValues.getRed() == 0);
    assertTrue(allMinPixelValues.getGreen() == 0);
    assertTrue(allMinPixelValues.getBlue() == 0);
  }

  //tests when all pixel values are the max and shows no values are clamped
  @Test
  public void allMaxValuePixelsShowNoChange() {
    assertTrue(allMaxPixelValues.getRed() == 255);
    assertTrue(allMaxPixelValues.getGreen() == 255);
    assertTrue(allMaxPixelValues.getBlue() == 255);
  }

  //tests when all pixel values are the same and shows no values are clamped
  @Test
  public void allSamePixelsShowNoChange() {
    assertTrue(allSameValidValues.getRed() == 4);
    assertTrue(allSameValidValues.getGreen() == 4);
    assertTrue(allSameValidValues.getBlue() == 4);
  }


  //getRedTests

  //tests when getRed is called on a normal pixel value
  @Test
  public void testGetRedNormalPixel() {
    assertTrue(normalPixelValues.getRed() == 40);
  }

  //tests when getRed is called on a negative pixel value
  @Test
  public void testGetRedNegativePixel() {
    assertTrue(negativeRedPixel.getRed() == 0);
  }

  //tests when getRed is called on a way too large pixel value
  @Test
  public void testGetRedWayTooLargePixel() {
    assertTrue(wayTooLargeRedPixel.getRed() == 255);
  }

  //tests when getRed is called on a min value pixel
  @Test
  public void testGetRedMinValuePixel() {
    assertTrue(allMinPixelValues.getRed() == 0);
  }

  //tests when getRed is called on a max value pixel
  @Test
  public void testGetRedMaxValuePixel() {
    assertTrue(allMaxPixelValues.getRed() == 255);
  }

  //getGreenTests

  //tests when getGreen is called on a normal Pixel
  @Test
  public void testGetGreenNormalPixel() {
    assertTrue(normalPixelValues.getGreen() == 60);
  }

  //tests when getGreen is called on a negative pixel value
  @Test
  public void testGetGreenNegativePixel() {
    assertTrue(negativeGreenPixel.getGreen() == 0);
  }

  //tests when getGreen is called on a way too large pixel value
  @Test
  public void testGetGreenWayTooLargePixel() {
    assertTrue(wayTooLargeGreenPixel.getGreen() == 255);
  }

  //tests when getGreen is called on a min value pixel
  @Test
  public void testGetGreenMinValuePixel() {
    assertTrue(allMinPixelValues.getGreen() == 0);
  }

  //tests when getGreen is called on a max value pixel
  @Test
  public void testGetGreenMaxValuePixel() {
    assertTrue(allMaxPixelValues.getGreen() == 255);
  }

  //getBlueTests

  //tests when getBlue is called on a normal pixel value
  @Test
  public void testGetBlueNormalPixel() {
    assertTrue(normalPixelValues.getBlue() == 80);
  }

  //tests when getBlue is called on a negative pixel value
  @Test
  public void testGetBlueNegativePixel() {
    assertTrue(negativeBluePixel.getBlue() == 0);
  }

  //tests when getBlue is called on a way too large pixel value
  @Test
  public void testGetBlueWayTooLargePixel() {
    assertTrue(wayTooLargeBluePixel.getBlue() == 255);
  }

  //tests when getBlue is called on a min value pixel
  @Test
  public void testGetBlueMinValuePixel() {
    assertTrue(allMinPixelValues.getBlue() == 0);
  }

  //tests when getBlue is called on a max value pixel
  @Test
  public void testGetBlueMaxValuePixel() {
    assertTrue(allMaxPixelValues.getBlue() == 255);
  }


  //tests for the equals() method

  //tests when pixels have the same red values but different other values
  @Test
  public void testEqualsSameRedDifferentOtherValues() {
    IPixel p1 = new Pixel(3, 4, 5);
    IPixel p2 = new Pixel(3, 5, 8);
    assertNotEquals(p1, p2);
  }

  //tests when pixels have the same green values but different other values
  @Test
  public void testEqualsSameGreenDifferentOtherValues() {
    IPixel p1 = new Pixel(4, 5, 5);
    IPixel p2 = new Pixel(3, 5, 8);
    assertNotEquals(p1, p2);
  }

  //tests when pixels have the same blue values but different other values
  @Test
  public void testEqualsSameBlueDifferentOtherValues() {
    IPixel p1 = new Pixel(4, 4, 8);
    IPixel p2 = new Pixel(3, 5, 8);
    assertNotEquals(p1, p2);
  }


  //tests equals when pixels have the same content but are different object
  @Test
  public void testEqualsSameContentButDifferentPixelObjects() {
    IPixel p1 = new Pixel(4, 4, 8);
    IPixel p2 = new Pixel(4, 4, 8);
    assertEquals(p1, p2);
    assertEquals(p2, p1);
  }

  //tests equals when pixels have the same content and are the same object
  @Test
  public void testEqualsSameContentAndSasmePixelObjects() {
    IPixel p1 = new Pixel(4, 4, 8);
    assertEquals(p1, p1);

    IPixel p2 = new Pixel(5, 4, 40);
    assertEquals(p2, p2);
  }


  //tests for the hashcode method


  //tests hashcode when two different pixel objects are equal
  @Test
  public void testHashCodeTwoDifferentPixelObjectEqual() {
    IPixel p1 = new Pixel(4, 4, 8);
    IPixel p2 = new Pixel(4, 4, 8);
    assertEquals(p1, p2);
    assertEquals(p2, p1);

    assertTrue(p1.hashCode() == p2.hashCode());
  }

  //tests hashcode when the same pixel objects are equal
  @Test
  public void testHashCodeSamePixelObjectEqual() {
    IPixel p1 = new Pixel(4, 4, 8);
    assertEquals(p1, p1);

    assertTrue(p1.hashCode() == p1.hashCode());
  }

}