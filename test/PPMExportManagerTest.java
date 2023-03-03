import static org.junit.Assert.assertEquals;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.PPMExportManager;
import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.image.RainbowImageGenerator;
import hw05.importimages.IOManager;
import hw05.importimages.PPMImportManager;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.Test;

/**
 * Tests class for the PPMExportManager class: encompassed are tests that show that the
 * PPMExportManager is able to export valid IImages properly into the PPM format and
 * has the correct functionality.
 */
public class PPMExportManagerTest {
  private IImage fileTest1 = new RainbowImageGenerator(5,5, 30).generateImage("hi.ppm");

  @Test(expected = IllegalArgumentException.class)
  public void testExportNullImage() {
    new PPMExportManager("test.ppm").export(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportEmptyFileName() {
    new PPMExportManager("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportNullFileName() {
    new PPMExportManager((String) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportNonPpmFileName() {
    new PPMExportManager("file.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportBadWriter() {
    new PPMExportManager((Writer) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportTooShortFileName() {
    new PPMExportManager("abc");
  }

  @Test
  public void testWriterOneBluePixel() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1,1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(board);

    assertEquals(writer.toString(), "P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }

  @Test
  public void testExportString() {
    Writer writer = new StringWriter();
    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(fileTest1);
    assertEquals("P3\n"
        + "5 5\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
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
        + "60\n"
        + "30\n"
        + "60\n"
        + "90\n"
        + "30\n"
        + "90\n"
        + "120\n"
        + "30\n"
        + "120\n"
        + "150\n"
        + "60\n"
        + "0\n"
        + "60\n"
        + "60\n"
        + "30\n"
        + "90\n"
        + "60\n"
        + "60\n"
        + "120\n"
        + "60\n"
        + "90\n"
        + "150\n"
        + "60\n"
        + "120\n"
        + "180\n"
        + "90\n"
        + "0\n"
        + "90\n"
        + "90\n"
        + "30\n"
        + "120\n"
        + "90\n"
        + "60\n"
        + "150\n"
        + "90\n"
        + "90\n"
        + "180\n"
        + "90\n"
        + "120\n"
        + "210\n"
        + "120\n"
        + "0\n"
        + "120\n"
        + "120\n"
        + "30\n"
        + "150\n"
        + "120\n"
        + "60\n"
        + "180\n"
        + "120\n"
        + "90\n"
        + "210\n"
        + "120\n"
        + "120\n"
        + "240\n", writer.toString());
  }

  @Test
  public void testExportSmallCheckerBoard() {
    Writer writer = new StringWriter();
    IImage board =
        new CheckerBoardImageGenerator(1,3, Color.BLACK,Color.WHITE).generateImage("board.ppm");

    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(board);
    assertEquals("P3\n"
        + "3 3\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n", writer.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportMultipleImagesUsingSameManager() {
    Writer writer = new StringWriter();
    IImage board =
        new CheckerBoardImageGenerator(1,3, Color.BLACK,Color.WHITE).generateImage("board.ppm");

    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(board);
    exportManager.export(fileTest1);
  }

  @Test
  public void testWriterAfterFailedExport() {
    Writer writer = new StringWriter();
    IImage board =
        new CheckerBoardImageGenerator(1,3, Color.BLACK,Color.WHITE).generateImage("board.ppm");

    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(board);
    try {
      exportManager.export(fileTest1);
    } catch (IllegalArgumentException e) {
      //empty
    }
    assertEquals(writer.toString(), "P3\n"
        + "3 3\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n");
  }

  @Test
  public void testImportAndExportSameImage() {
    String ppmData = "P3\n"
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
        + "40\n";

    IOManager importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream((ppmData).getBytes()));

    Writer writer = new StringWriter();
    IImage img = importManager.apply();

    IImageExportManager exportManager = new PPMExportManager(writer);
    exportManager.export(img);

    assertEquals(writer.toString(), ppmData);
  }

}