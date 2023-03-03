import static org.junit.Assert.assertEquals;

import hw05.image.IImage;
import hw05.image.RainbowImageGenerator;
import hw05.importimages.IOManager;
import hw05.importimages.PPMImportManager;
import hw05.pixel.Pixel;
import java.io.ByteArrayInputStream;
import org.junit.Test;

/**
 * Tests class for the PPMImportManager class: encompassed are tests that show that the
 * PPMImportManager is able to import valid images in the PPM format and is able to turn them into
 * valid images that the model can use.
 */
public class PPMImportManagerTest {

  private IOManager importManager;
  private IImage fileTest1 = new RainbowImageGenerator(
      5, 5, 30).generateImage("./res/fileTest1.ppm");

  @Test(expected = IllegalArgumentException.class)
  public void testNullFileName() {
    importManager = new PPMImportManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyFileName() {
    importManager = new PPMImportManager("");
  }

  @Test
  public void testImport() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    assertEquals(importManager.apply(), fileTest1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformededFileImportNonP3() {
    importManager = new PPMImportManager("./res/malformed.ppm");
    importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportNonExistantFile() {
    importManager = new PPMImportManager("hello??");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportExistingFileWrongFormat() {
    importManager = new PPMImportManager("./res/malformed.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShortName() {
    importManager = new PPMImportManager("xt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedFileValuesNotMultOf3() {
    importManager = new PPMImportManager("./res/malformedNotMult3.ppm");
    importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedFileImageDimensionsDontMatch() {
    importManager = new PPMImportManager("./res/malformedBadDimensions.ppm");
    importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingSameFileTwiceThrows() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    importManager.apply();
    importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingEmptyImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "0 0\n"
            + "255").getBytes()));
    importManager.apply();
  }

  @Test
  public void testImporting1PixelImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelHeight(), 1);
    assertEquals(image.getPixelWidth(), 1);
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 10, 130));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelNegativeDimensionsImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "-1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelNegativeDimensionsImage1() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 -21\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test
  public void testImporting1PixelNegativeValues() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "-140\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 0, 130));
  }

  @Test
  public void testImporting1PixelTooLargeValues() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1 \n"
            + "255\n"
            + "0\n"
            + "340\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 255, 130));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelDecimalValues() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "255\n"
            + "0.0\n"
            + "340\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 255, 130));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMaxColorValue() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "256\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingEmpty() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingNonP3() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("p3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingNonP31() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("hello3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelDimensionDontMatch() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 2\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelWrongDimension2() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "2 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IImage image = importManager.apply();
  }

  @Test
  public void testImporting3x2() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n"
            + "40\n").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelWidth(), 3);
    assertEquals(image.getPixelHeight(), 2);
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 30, 30));
    assertEquals(image.getPixelAt(0, 1), new Pixel(0, 60, 60));
    assertEquals(image.getPixelAt(0, 2), new Pixel(0, 90, 90));

    assertEquals(image.getPixelAt(1, 0), new Pixel(0, 120, 120));
    assertEquals(image.getPixelAt(1, 1), new Pixel(30, 0, 30));
    assertEquals(image.getPixelAt(1, 2), new Pixel(30, 30, 40));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingNonMult3DataValues() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n").getBytes()));
    IImage image = importManager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImporting3x2WithRandomChars() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255 afnakf sd ksn dksk \n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60 sndpo naf\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n"
            + "40\n fanfl FLNO ae !#$% N!L").getBytes()));
    IImage image = importManager.apply();
  }

  @Test
  public void testImporting3x2WithRandomCharsAtEnd() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n"
            + "40\n fanfl FLNO ae !#$% N!L").getBytes()));
    IImage image = importManager.apply();
    assertEquals(image.getPixelWidth(), 3);
    assertEquals(image.getPixelHeight(), 2);
    assertEquals(image.getPixelAt(0, 0), new Pixel(0, 30, 30));
    assertEquals(image.getPixelAt(0, 1), new Pixel(0, 60, 60));
    assertEquals(image.getPixelAt(0, 2), new Pixel(0, 90, 90));

    assertEquals(image.getPixelAt(1, 0), new Pixel(0, 120, 120));
    assertEquals(image.getPixelAt(1, 1), new Pixel(30, 0, 30));
    assertEquals(image.getPixelAt(1, 2), new Pixel(30, 30, 40));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputStream() {
    importManager = new PPMImportManager("file.ppm",
        null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFile() {
    importManager = new PPMImportManager(null,
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n"
            + "40\n fanfl FLNO ae !#$% N!L").getBytes()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadFileName() {
    importManager = new PPMImportManager(".tx",
        new ByteArrayInputStream(("P3\n"
            + "3 2\n"
            + "255\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "0\n"
            + "60\n"
            + "60\n"
            + "0\n"
            + "90\n"
            + "90\n"
            + "0\n"
            + "120\n"
            + "120\n"
            + "30\n"
            + "0\n"
            + "30\n"
            + "30\n"
            + "30\n"
            + "40\n fanfl FLNO ae !#$% N!L").getBytes()));
  }
}