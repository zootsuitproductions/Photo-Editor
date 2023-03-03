import static org.junit.Assert.assertEquals;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.ITextExportManager;
import hw05.exportimages.JPEGExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.exportimages.TextFileExportManager;
import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.image.RainbowImageGenerator;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Test class for PNGExportManager, JPEGExportManager, and TextFileExportManager.
 */
public class ExportManagerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileNamePNG() {
    new PNGExportManager("NotPng.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileNameTxt() {
    new TextFileExportManager("Nottxt.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileNameJPEG() {
    new JPEGExportManager("Nottxt.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportingTxtNull() {
    File theDir = new File("ExportTests");
    theDir.mkdir();
    ITextExportManager textExport = new TextFileExportManager("test1.txt");

    textExport.export(null);
  }

  @Test
  public void testExportingTxtEmpty() {
    BufferedReader reader ;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    ITextExportManager textExport = new TextFileExportManager("./ExportTests/test1.txt");

    List<String> linesToExport = new ArrayList<>();
    textExport.export(linesToExport);

    try {
      reader = new BufferedReader(new FileReader("./ExportTests/test1.txt"));
      assertEquals(reader.readLine(), null);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read");
    }

  }

  @Test
  public void testExportingTxt() {
    BufferedReader reader;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    ITextExportManager textExport = new TextFileExportManager("./ExportTests/test1.txt");

    List<String> linesToExport = new ArrayList<>(
        Arrays.asList("hello line 1", "this is line 2", "line 3 !"));
    textExport.export(linesToExport);

    try {
      reader = new BufferedReader(new FileReader("./ExportTests/test1.txt"));
      assertEquals(reader.readLine(), "hello line 1");
      assertEquals(reader.readLine(), "this is line 2");
      assertEquals(reader.readLine(), "line 3 !");
      assertEquals(reader.readLine(), null);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read");
    }

  }

  @Test
  public void testExportingPNG() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./ExportTests/test1.png");

    IImage rainbow = new RainbowImageGenerator(2,2,2).generateImage("e");
    pngExport.export(rainbow);

    importManager = new PNGImportManager("./ExportTests/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

  }

  @Test
  public void testExportingPNGTwice() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./ExportTests/test1.png");

    IImage rainbow = new RainbowImageGenerator(40,5,80).generateImage("e");
    pngExport.export(rainbow);
    pngExport.export(rainbow);

    importManager = new PNGImportManager("./ExportTests/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportingPNGNullImage() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./ExportTests/test1.png");

    pngExport.export(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportingJPEGNullImage() {
    IOManager importManager;
    File theDir = new File("ExportTests");
    theDir.mkdir();
    IImageExportManager jpegExport = new JPEGExportManager("./ExportTests/testnull.jpeg");

    jpegExport.export(null);

  }

  @Test
  public void testExportingJPEG() {
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



}