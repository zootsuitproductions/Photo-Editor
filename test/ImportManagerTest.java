import static org.junit.Assert.assertEquals;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.JPEGExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Tests the functionality of JPEGImportManager and PNGImportManager.
 */
public class ImportManagerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testPNGNull() {
    new PNGImportManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testJPEGNull() {
    new JPEGImportManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testJPEGBadFile() {
    new JPEGImportManager("notReal.jpeg").apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPNGBadFile() {
    new PNGImportManager("notReal.png").apply();
  }

  @Test
  public void testImportingPNG() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./ExportTests/test1.png");

    IImage rainbow = new CheckerBoardImageGenerator(
        40,4, Color.WHITE, Color.BLUE).generateImage("e");
    pngExport.export(rainbow);

    importManager = new PNGImportManager("./ExportTests/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingPNGTwice() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./ExportTests/test1.png");

    IImage rainbow = new CheckerBoardImageGenerator(
        40,4, Color.WHITE, Color.BLUE).generateImage("e");
    pngExport.export(rainbow);

    importManager = new PNGImportManager("./ExportTests/test1.png");
    IImage imported = importManager.apply();
    IImage imported1 = importManager.apply();

  }

  @Test
  public void testImportingJPEG() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager jpegExport = new JPEGExportManager("./ExportTests/test1.jpeg");

    IImage rainbow = new CheckerBoardImageGenerator(
        1,2, Color.WHITE, Color.BLUE).generateImage("e");
    jpegExport.export(rainbow);

    importManager = new JPEGImportManager("./ExportTests/test1.jpeg");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), new ArrayList<>(new ArrayList<>(Arrays.asList(
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(19, 18, 145), new Pixel(237, 236, 255))),
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(231, 230, 255), new Pixel(9, 8, 135)))))));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportingJPEGTwice() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager jpegExport = new JPEGExportManager("./ExportTests/test1.jpeg");

    IImage rainbow = new CheckerBoardImageGenerator(
        1,2, Color.WHITE, Color.BLUE).generateImage("e");
    jpegExport.export(rainbow);

    importManager = new JPEGImportManager("./ExportTests/test1.jpeg");
    IImage imported = importManager.apply();
    IImage imported1 = importManager.apply();

  }
}