import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import hw05.pixel.ITransparentPixel;
import hw05.pixel.TransparentPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the TransparentPixel class: encompassed are test that make sure that
 * transparent pixels have the correct functionality and that their alpha values work properly.
 */
public class TransparentPixelTest {
  private ITransparentPixel negativeRedPixel;
  private ITransparentPixel negativeGreenPixel;
  private ITransparentPixel negativeBluePixel;
  private ITransparentPixel negativeAlphaPixel;
  private ITransparentPixel allNegativePixel;

  private ITransparentPixel wayTooLargeRedPixel;
  private ITransparentPixel wayTooLargeGreenPixel;
  private ITransparentPixel wayTooLargeBluePixel;
  private ITransparentPixel wayTooLargeAlphaPixel;
  private ITransparentPixel allPixelWaysToLarge;

  private ITransparentPixel mixOfWayTooLargeAndNegative;

  private ITransparentPixel normalPixelValues;

  private ITransparentPixel allMinPixelValues;
  private ITransparentPixel allMaxPixelValues;
  private ITransparentPixel allSameValidValues;

  @Before
  public void setup() {
    negativeRedPixel = new TransparentPixel(-4, 5 , 6, 4);
    negativeGreenPixel = new TransparentPixel(5, -6 ,7,4);
    negativeBluePixel = new TransparentPixel(5, 6 ,-7,4);
    negativeAlphaPixel = new TransparentPixel(5, 6 ,7,-4);
    allNegativePixel = new TransparentPixel(-6, -10 ,-7, -4);

    wayTooLargeRedPixel = new TransparentPixel(300, 30, 56, 40);
    wayTooLargeGreenPixel = new TransparentPixel(40, 300, 56, 40);
    wayTooLargeBluePixel = new TransparentPixel(30, 30, 560, 40);
    wayTooLargeAlphaPixel = new TransparentPixel(40, 50, 60, 600);
    allPixelWaysToLarge = new TransparentPixel(300, 400, 500, 600);

    mixOfWayTooLargeAndNegative = new TransparentPixel(-30, 300, -3, 500);

    normalPixelValues = new TransparentPixel(40, 60 ,80, 40);

    allMinPixelValues = new TransparentPixel(0,0,0, 0);
    allMaxPixelValues = new TransparentPixel(255,255,255, 255);
    allSameValidValues = new TransparentPixel(4,4,4, 4);

  }

  //tests when the red value of a transparent pixel is automatically clamped because it is negative
  @Test
  public void testNegativeRedPixelValueClamped() {
    assertTrue(negativeRedPixel.getRed() != -4);
    assertTrue(negativeRedPixel.getRed() == 0);
  }

  //tests when the green value of a transparent pixel is automatically clamped because it is
  //negative
  @Test
  public void testNegativeGreenPixelValueClamped() {
    assertTrue(negativeGreenPixel.getGreen() != -6);
    assertTrue(negativeGreenPixel.getGreen() == 0);
  }

  //tests when the blue value of a transparent pixel is automatically clamped because it is negative
  @Test
  public void testNegativeBluePixelValueClamped() {
    assertTrue(negativeBluePixel.getBlue() != -7);
    assertTrue(negativeBluePixel.getBlue() == 0);
  }

  //tests when the alpha value of a transparent pixel is automatically clamped because it is
  //negative
  @Test
  public void testNegativeAlphaPixelValueClamped() {
    assertTrue(negativeAlphaPixel.getAlpha() != -4);
    assertTrue(negativeAlphaPixel.getAlpha() == 0);
  }

  //tests when every value in a transparent pixel is automatically clamped because they are all
  //negative
  @Test
  public void testAllNegativePixelValuesClamped() {
    assertTrue(allNegativePixel.getRed() != -6);
    assertTrue(allNegativePixel.getRed() == 0);

    assertTrue(allNegativePixel.getGreen() != -10);
    assertTrue(allNegativePixel.getGreen() == 0);

    assertTrue(allNegativePixel.getBlue() != -7);
    assertTrue(allNegativePixel.getBlue() == 0);

    assertTrue(allNegativePixel.getAlpha() != -4);
    assertTrue(allNegativePixel.getAlpha() == 0);
  }


  //tests when the red value of a transparent pixel is automatically clamped because it is too large
  @Test
  public void testWayTooLargeRedPixelValueClamped() {
    assertTrue(wayTooLargeRedPixel.getRed() != 300);
    assertTrue(wayTooLargeRedPixel.getRed() == 255);
  }

  //tests when the green value of a transparent pixel is automatically clamped because it is too
  //large
  @Test
  public void testWayTooLargeGreenPixelValueClamped() {
    assertTrue(wayTooLargeGreenPixel.getGreen() != 300);
    assertTrue(wayTooLargeGreenPixel.getGreen() == 255);
  }

  //tests when the blue value of a transparent pixel is automatically clamped because it is too
  //large
  @Test
  public void testWayTooLargeBluePixelValueClamped() {
    assertTrue(wayTooLargeBluePixel.getBlue() != 560);
    assertTrue(wayTooLargeBluePixel.getBlue() == 255);
  }

  //tests when the alpha value of a transparent pixel is automatically clamped because it is too
  //large
  @Test
  public void testWayTooLargeAlphaPixelValueClamped() {
    assertTrue(wayTooLargeBluePixel.getBlue() != 600);
    assertTrue(wayTooLargeBluePixel.getBlue() == 255);
  }

  //tests when all the values of a transparent pixel are automatically clamped because they are too
  //large
  @Test
  public void testAllPixelValuesWayTooLargeValueClamped() {
    assertTrue(allPixelWaysToLarge.getRed() != 300);
    assertTrue(allPixelWaysToLarge.getRed() == 255);

    assertTrue(allPixelWaysToLarge.getGreen() != 400);
    assertTrue(allPixelWaysToLarge.getGreen() == 255);

    assertTrue(allPixelWaysToLarge.getBlue() != 500);
    assertTrue(allPixelWaysToLarge.getBlue() == 255);

    assertTrue(allPixelWaysToLarge.getAlpha() != 600);
    assertTrue(allPixelWaysToLarge.getAlpha() == 255);
  }

  //tests when a transparent pixel is clamped because it is has both some values that are negative
  //and way too large
  @Test
  public void somePixelValuesAreNegativeWhileOthersAreTooLarge() {
    assertTrue(mixOfWayTooLargeAndNegative.getRed() != -30);
    assertTrue(mixOfWayTooLargeAndNegative.getRed() == 0);

    assertTrue(mixOfWayTooLargeAndNegative.getGreen() != 300);
    assertTrue(mixOfWayTooLargeAndNegative.getGreen() == 255);

    assertTrue(mixOfWayTooLargeAndNegative.getBlue() != -3);
    assertTrue(mixOfWayTooLargeAndNegative.getBlue() == 0);

    assertTrue(mixOfWayTooLargeAndNegative.getAlpha() != 500);
    assertTrue(mixOfWayTooLargeAndNegative.getAlpha() == 255);
  }

  //tests when a transparent pixel has normal values and shows no values are clamped
  @Test
  public void allNormalPixelValues() {
    assertTrue(normalPixelValues.getRed() == 40);
    assertTrue(normalPixelValues.getGreen() == 60);
    assertTrue(normalPixelValues.getBlue() == 80);
    assertTrue(normalPixelValues.getAlpha() == 40);
  }

  //tests when there are all zero min transparent pixel and shows no values are clamped
  @Test
  public void allMinValuePixelsShowNoChange() {
    assertTrue(allMinPixelValues.getRed() == 0);
    assertTrue(allMinPixelValues.getGreen() == 0);
    assertTrue(allMinPixelValues.getBlue() == 0);
    assertTrue(allMinPixelValues.getAlpha() == 0);
  }

  //tests when all transparent pixel values are the max and shows no values are clamped
  @Test
  public void allMaxValuePixelsShowNoChange() {
    assertTrue(allMaxPixelValues.getRed() == 255);
    assertTrue(allMaxPixelValues.getGreen() == 255);
    assertTrue(allMaxPixelValues.getBlue() == 255);
    assertTrue(allMaxPixelValues.getAlpha() == 255);
  }

  //tests when all transparent pixel values are the same and shows no values are clamped
  @Test
  public void allSamePixelsShowNoChange() {
    assertTrue(allSameValidValues.getRed() == 4);
    assertTrue(allSameValidValues.getGreen() == 4);
    assertTrue(allSameValidValues.getBlue() == 4);
    assertTrue(allSameValidValues.getAlpha() == 4);
  }


  //getRedTests

  //tests when getRed is called on a normal transparent pixel value
  @Test
  public void testGetRedNormalPixel() {
    assertTrue(normalPixelValues.getRed() == 40);
  }

  //tests when getRed is called on a negative transparent pixel value
  @Test
  public void testGetRedNegativePixel() {
    assertTrue(negativeRedPixel.getRed() == 0);
  }

  //tests when getRed is called on a way too large transparent pixel value
  @Test
  public void testGetRedWayTooLargePixel() {

    assertTrue(wayTooLargeRedPixel.getRed() == 255);
  }

  //tests when getRed is called on a min value transparent pixel
  @Test
  public void testGetRedMinValuePixel() {
    assertTrue(allMinPixelValues.getRed() == 0);
  }

  //tests when getRed is called on a max value transparent pixel
  @Test
  public void testGetRedMaxValuePixel() {
    assertTrue(allMaxPixelValues.getRed() == 255);
  }

  //getGreenTests

  //tests when getGreen is called on a normal transparent Pixel
  @Test
  public void testGetGreenNormalPixel() {

    assertTrue(normalPixelValues.getGreen() == 60);
  }

  //tests when getGreen is called on a negative transparent pixel value
  @Test
  public void testGetGreenNegativePixel() {

    assertTrue(negativeGreenPixel.getGreen() == 0);
  }

  //tests when getGreen is called on a way too large transparent pixel value
  @Test
  public void testGetGreenWayTooLargePixel() {
    assertTrue(wayTooLargeGreenPixel.getGreen() == 255);
  }

  //tests when getGreen is called on a min value transparent pixel
  @Test
  public void testGetGreenMinValuePixel() {

    assertTrue(allMinPixelValues.getGreen() == 0);
  }

  //tests when getGreen is called on a max value transparent pixel
  @Test
  public void testGetGreenMaxValuePixel() {
    assertTrue(allMaxPixelValues.getGreen() == 255);
  }

  //getBlueTests

  //tests when getBlue is called on a normal transparent pixel value
  @Test
  public void testGetBlueNormalPixel() {
    assertTrue(normalPixelValues.getBlue() == 80);
  }

  //tests when getBlue is called on a negative transparent pixel value
  @Test
  public void testGetBlueNegativePixel() {
    assertTrue(negativeBluePixel.getBlue() == 0);
  }

  //tests when getBlue is called on a way too large transparent pixel value
  @Test
  public void testGetBlueWayTooLargePixel() {

    assertTrue(wayTooLargeBluePixel.getBlue() == 255);
  }

  //tests when getBlue is called on a min value transparent pixel
  @Test
  public void testGetBlueMinValuePixel() {
    assertTrue(allMinPixelValues.getBlue() == 0);
  }

  //tests when getBlue is called on a max value transparent pixel
  @Test
  public void testGetBlueMaxValuePixel() {

    assertTrue(allMaxPixelValues.getBlue() == 255);
  }


  //getAlphaTests

  //tests when getBlue is called on a normal transparent pixel value
  @Test
  public void testGetAlphaNormalPixel() {
    assertTrue(normalPixelValues.getAlpha() == 40);
  }

  //tests when getBlue is called on a negative transparent pixel value
  @Test
  public void testGetAlphaNegativePixel() {
    assertTrue(negativeAlphaPixel.getAlpha() == 0);
  }

  //tests when getBlue is called on a way too large transparent pixel value
  @Test
  public void testGetAlphaWayTooLargePixel() {
    assertTrue(wayTooLargeAlphaPixel.getAlpha() == 255);
  }

  //tests when getBlue is called on a min value transparent pixel
  @Test
  public void testGetAlphaMinValuePixel() {
    assertTrue(allMinPixelValues.getAlpha() == 0);
  }

  //tests when getBlue is called on a max value transparent pixel
  @Test
  public void testGetAlphaMaxValuePixel() {
    assertTrue(allMaxPixelValues.getAlpha() == 255);
  }



  //test for the toString() method all valid transparent pixel values
  @Test
  public void testToStringValidPixel() {
    assertEquals("40, 60, 80, 40",normalPixelValues.toString());
  }

  //test for the toString() method all negative transparent pixel values
  @Test
  public void testToStringAllZero() {
    assertEquals("0, 0, 0, 0", allMinPixelValues.toString());
  }

  //test for the toString() method all maximum transparent pixel values
  @Test
  public void testToStringValidAllMax() {
    assertEquals("255, 255, 255, 255",allMaxPixelValues.toString());
  }




  //tests for the equals() method

  //tests when transparent pixels have the same red values but different other values
  @Test
  public void testEqualsSameRedDifferentOtherValues() {
    ITransparentPixel p1 = new TransparentPixel(3, 4, 5, 4);
    ITransparentPixel p2 = new TransparentPixel(3, 5, 6, 7);
    assertNotEquals(p1, p2);
  }

  //tests when transparent pixels have the same green values but different other values
  @Test
  public void testEqualsSameGreenDifferentOtherValues() {
    ITransparentPixel p1 = new TransparentPixel(3, 5, 5, 4);
    ITransparentPixel p2 = new TransparentPixel(2, 5, 6, 7);
    assertNotEquals(p1, p2);
  }

  //tests when transparent pixels have the same blue values but different other values
  @Test
  public void testEqualsSameBlueDifferentOtherValues() {
    ITransparentPixel p1 = new TransparentPixel(3, 3, 6, 4);
    ITransparentPixel p2 = new TransparentPixel(2, 5, 6, 7);
    assertNotEquals(p1, p2);
  }

  //tests when transparent pixels have the same alpha values but different other values
  @Test
  public void testEqualsSameAlphaDifferentOtherValues() {
    ITransparentPixel p1 = new TransparentPixel(3, 3, 5, 7);
    ITransparentPixel p2 = new TransparentPixel(2, 5, 6, 7);
    assertNotEquals(p1, p2);
  }


  //tests equals when transparent pixels have the same content but are different object
  @Test
  public void testEqualsSameContentButDifferentPixelObjects() {
    ITransparentPixel p1 = new TransparentPixel(3, 5, 6, 7);
    ITransparentPixel p2 = new TransparentPixel(3, 5, 6, 7);
    assertEquals(p1, p2);
    assertEquals(p2, p1);
  }

  //tests equals when transparent pixels have the same content and are the same object
  @Test
  public void testEqualsSameContentAndSamePixelObjects() {
    ITransparentPixel p1 = new TransparentPixel(3, 5, 6, 7);
    assertEquals(p1, p1);

    ITransparentPixel p2 = new TransparentPixel(3, 5, 6, 7);
    assertEquals(p2, p2);
  }


  //tests for the hashcode method


  //tests hashcode when two different transparent pixel objects are equal
  @Test
  public void testHashCodeTwoDifferentPixelObjectEqual() {
    ITransparentPixel p1 = new TransparentPixel(3, 5, 6, 7);
    ITransparentPixel p2 = new TransparentPixel(3, 5, 6, 7);
    assertEquals(p1, p2);
    assertEquals(p2, p1);

    assertTrue(p1.hashCode() == p2.hashCode());
  }

  //tests hashcode when the same transparent pixel objects are equal
  @Test
  public void testHashCodeSamePixelObjectEqual() {
    ITransparentPixel p1 = new TransparentPixel(3, 5, 6, 7);
    assertEquals(p1, p1);

    assertTrue(p1.hashCode() == p1.hashCode());
  }

}