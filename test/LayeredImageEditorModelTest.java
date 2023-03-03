import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import hw05.exportimages.IImageExportManager;
import hw05.exportimages.JPEGExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.exportimages.PPMExportManager;
import hw05.image.CheckerBoardImageGenerator;
import hw05.image.IImage;
import hw05.image.IImageGenerator;
import hw05.image.Image;
import hw05.image.RainbowImageGenerator;
import hw05.imageedits.BlurFilter;
import hw05.imageedits.CompositePhotoEdit;
import hw05.imageedits.GreyScaleTransformation;
import hw05.imageedits.IPhotoEdit;
import hw05.imageedits.SepiaTransformation;
import hw05.imageedits.SharpenFilter;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.importimages.PPMImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import hw06.ILayeredImageEditorModel;
import hw06.Layer;
import hw06.LayeredImageEditorModel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the LayeredImageEditorClass: encompassed are tests that make sure that the
 * layered image editor model has the correct functionality and works properly.
 */
public class LayeredImageEditorModelTest {

  private final IImage fileTest3 = new RainbowImageGenerator(5, 5,
      30).generateImage("hi.ppm");
  private final IOManager fileTest1 = new PPMImportManager("./res/fileTest1.ppm");

  private final IImage jpegTestImage = new JPEGImportManager("./res/test1.jpeg").apply();
  private final IImage pngTestImage = new PNGImportManager("./res/test1.png").apply();

  private final IImageGenerator checkerBoardGenerate1 = new CheckerBoardImageGenerator(2,
      10, new Pixel(0, 0, 0), new Pixel(255, 255, 255));
  private final IImage i1x1 = new RainbowImageGenerator(1, 1,
      30).generateImage("1x1.ppm");
  private final IImage i1x2 = new RainbowImageGenerator(1, 2,
      30).generateImage("1x2.ppm");
  private final IImage i2x2 = new RainbowImageGenerator(2, 2,
      30).generateImage("2x2.ppm");
  private final IImage i7x7 = new RainbowImageGenerator(7, 7,
      100).generateImage("10x7.ppm");
  private final IImage i7x7ppm = new PPMImportManager("./res/i7x7.ppm").apply();

  private ILayeredImageEditorModel layeredImageEditorModel;
  private List<List<IPixel>> pixelList;

  private final IPhotoEdit blurEdit = new BlurFilter();
  private final IPhotoEdit sharpenEdit = new SharpenFilter();
  private final IPhotoEdit sepiaEdit = new SepiaTransformation();
  private final IPhotoEdit greyEdit = new GreyScaleTransformation();


  @Before
  public void setup() {
    layeredImageEditorModel = new LayeredImageEditorModel();
    pixelList = new ArrayList<>();
    pixelList.add(new ArrayList<>(Arrays.asList(new Pixel(4, 5, 6),
        new Pixel(6, 7, 8))));

  }

  
  //tests for addImage "layer"


  //tests when trying to add a layer to a model but the name is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullNameLayer() {
    layeredImageEditorModel.addImage(null, new Image(pixelList, "hello"));
  }

  //tests when trying to add a layer to a model but the name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testAddEmptyNameLayer() {
    layeredImageEditorModel.addImage("", new Image(pixelList, "hello"));
  }

  //test when trying to add a layer to the model but the layer name already exists
  @Test(expected = IllegalArgumentException.class)
  public void testAddExistingLayerName() {
    layeredImageEditorModel.addImage("Hello", new Image(pixelList, "hello"));
    layeredImageEditorModel.addImage("Hello", new Image(pixelList, "yo"));
  }

  //test that you can add any dimension image layer size when there is only a null image
  //in the layer
  @Test
  public void testAddAnyDimensionsSizeWithNullImageLayerOnlyInside() {
    layeredImageEditorModel.addImage("Hello", null);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addImage("hi", new Image(pixelList, "yo"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //test that you can add any dimension image layer size when there is only a null image
  //in the layer
  @Test
  public void testAddAnyNullBlankImageEvenThoughItDoesntHaveRightDimensions() {
    layeredImageEditorModel.addImage("hi", new Image(pixelList, "yo"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addImage("Hello", null);
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //test an invalid case when you try and add a new layer but it does not have the same image
  //dimensions as the current image in the list
  @Test(expected = IllegalArgumentException.class)
  public void testCannotAddLayerBecOfIllegalDimensions() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addImage("New",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5), new Pixel(3, 4, 5))))), "hello"));
  }

  //tests a valid case of adding a new layer to an empty layered image
  @Test
  public void testValidAddToEmptyLayeredImage() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }

  //tests a valid case of adding a new layer to a populated layered image, adding many images
  //to the layered image
  @Test
  public void testValidAddToFullLayeredImage() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addImage("ki",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            5, 6))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            2, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }

  //tests trying to add a blank image (null) image to the layered image
  @Test
  public void testValidAddBlankImage() {
    layeredImageEditorModel.addImage("Hello", null);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }


  //tests trying to add a blank image (null) image to the layered image that has images in it
  //already
  @Test
  public void testValidAddBlankImageWithOtherLayersInside() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addImage("ki",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            5, 6))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.addImage("hi", null);
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }


  //tests trying to add a blank image (null) image to the layered image and adding in copies
  //of it after
  @Test
  public void testValidAddBlankImageAndCopiesOfBlankImage() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.addImage("hi", null);
    layeredImageEditorModel.addCopyCurrentLayer("copyBlank");
    layeredImageEditorModel.addCopyCurrentLayer("copyBlank2");
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }


  //tests adding a programmatically created image to the model
  @Test
  public void testValidAddProgrammaticalyCreatedImage() {
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");
    layeredImageEditorModel.addImage("hello", board);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    assertEquals(layeredImageEditorModel.getLayers().get(0), new Layer("hello", board));
  }

  //tests adding an image to the model that is imported in as a PPM
  @Test
  public void testValidAddImageToLayerModelThatWasImportedPPM() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.addImage("Hello", fileTest1.apply());
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }

  //tests adding an image to the model that is a checkerboard image
  @Test
  public void testValidAddImageToLayerModelCheckerBoard() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }


  //tests adding an image to the model that is imported in as a JPEG
  @Test
  public void testValidAddImageToLayerModelThatWasImportedJPEG() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);

    IOManager importManager;
    File theDir = new File("AddTest");
    theDir.mkdir();
    IImageExportManager jpegExport = new JPEGExportManager("./AddTest/test1.jpeg");

    IImage rainbow = new CheckerBoardImageGenerator(1, 2, Color.WHITE,
        Color.BLUE)
        .generateImage("e");
    jpegExport.export(rainbow);

    importManager = new JPEGImportManager("./AddTest/test1.jpeg");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), new ArrayList<>(new ArrayList<>(Arrays.asList(
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(19, 18, 145), new Pixel(237, 236, 255))),
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(231, 230, 255), new Pixel(9, 8, 135)))))));
    layeredImageEditorModel.addImage("Hello", jpegTestImage);

    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }

  //tests adding an image to the model that is imported in as a PNG
  @Test
  public void testValidAddImageToLayerModelThatWasImportedPNG() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);

    IOManager importManager;
    File theDir = new File("AddTest");
    theDir.mkdir();
    IImageExportManager pngExport = new PNGExportManager("./AddTest/test1.png");

    IImage rainbow = new CheckerBoardImageGenerator(40, 4, Color.WHITE,
        Color.BLUE)
        .generateImage("e");
    pngExport.export(rainbow);

    importManager = new PNGImportManager("./AddTest/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

    layeredImageEditorModel.addImage("Hello", pngTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
  }

  //tests for addCopyLayer()


  //tests adding a copy layer but the layer name you write to want to copy is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerNullLayerNameOfExistingLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer(null, "yo");
  }

  //tests adding a copy layer but the layer name you choose for the layer to copy is empty
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerEmptyLayerNameOfExistingLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer("", "yo");
  }

  //tests adding a copy layer but the layer name you want for the new copied layer is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerNullLayerNameForNewLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer("Hello", null);
  }

  //tests adding a copy layer but the layer name you want for the new copied layer is empty
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerEmptyLayerNameForNewLayerName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer("Hello", "");
  }

  //tests adding a copy layer but the layer name of the layer you want to copy does not exist in
  //the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerNonExistentLayerNameInModel() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer("yo", "Hi");
  }

  //tests adding a copy layer but the layer name you want for the new copied layer
  //already exists in the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerNewLayerNameAlreadyExistsInModel() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    layeredImageEditorModel.addCopyLayer("Hello", "Hello");
  }

  //tests a valid case of adding a copy layer to the layered image
  @Test
  public void testValidCase() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            4, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Crazy");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests a valid case of adding a copy layer to the layered image but the copied image has a
  //a blank image (null)
  @Test
  public void testValidCaseAddedACopyOfBlankImage() {
    layeredImageEditorModel.addImage("Hello", null);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Crazy");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests a valid case of adding multiple layer copies to the layered image
  @Test
  public void testValidCaseAddingMultipleLayerCopies() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    layeredImageEditorModel.addImage("Me",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            2, 4))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
    layeredImageEditorModel.addCopyLayer("Different", "Glow");
    layeredImageEditorModel.addCopyLayer("Me", "Man U");
    assertTrue(layeredImageEditorModel.getLayers().size() == 5);
  }

  //tests when you try to add a copy of a layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyLayerNoLayersInList() {
    layeredImageEditorModel.addCopyLayer("yo", "Hello");
  }

  //tests when you try to add a copy of a layer and the image was imported as a PNG
  @Test
  public void testAddCopyLayerPNG() {
    layeredImageEditorModel.addImage("Hello", pngTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the image was imported as a Jpeg
  @Test
  public void testAddCopyLayerJPEG() {
    layeredImageEditorModel.addImage("Hello", jpegTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the image is a generated checkerboard
  @Test
  public void testAddCopyLayerCheckerBoard() {
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the layer is not the top one
  @Test
  public void testAddCopyLayerNotTopOne() {
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    layeredImageEditorModel.addImage("Hi", checkerBoardGenerate1.generateImage("yo"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.setCurrentLayer("Hi");
    layeredImageEditorModel.addCopyLayer("Hi", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }

  //tests for addCopyCurrentLayer


  //tests when you try to add a copy of the current layer but the new layer name you want is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyCurrentLayerNullNewName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    layeredImageEditorModel.addCopyCurrentLayer(null);
  }

  //tests when you try to add a copy of the current layer but the new layer name you want is empty
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyCurrentLayerEmptyNewName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    layeredImageEditorModel.addCopyCurrentLayer("");
  }

  //tests when you try to add a copy of the current layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyCurrentLayerNoLayersInList() {
    layeredImageEditorModel.addCopyCurrentLayer("Hello");
  }

  //tests when you try to add a copy of the current layer but the new name you want for your
  //layer already exists in the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddCopyCurrentLayerNewNameAlreadyExists() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    layeredImageEditorModel.addCopyCurrentLayer("Different");
  }

  //tests a valid case of adding a layer that is a copy of the current layer
  @Test
  public void testValidCaseOfAddingCopyOfCurrentLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.addCopyCurrentLayer("Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }

  //tests a valid case of adding multiple layers that are copies of the current layer
  @Test
  public void testValidCaseOfAddingMultipleCopyOfCurrentLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.addCopyCurrentLayer("Yo");
    layeredImageEditorModel.addCopyCurrentLayer("Yo version 2");
    assertTrue(layeredImageEditorModel.getLayers().size() == 4);
  }

  //tests when you try to add a copy of the current layer and the image was imported as a PNG
  @Test
  public void testAddCopyCurrentLayerPNG() {
    layeredImageEditorModel.addImage("Hello", pngTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the image was imported as a Jpeg
  @Test
  public void testAddCopyCurrentLayerJPEG() {
    layeredImageEditorModel.addImage("Hello", jpegTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the image was a checkerboard
  @Test
  public void testAddCopyCurrentLayerCheckerBoard() {
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.addCopyLayer("Hello", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
  }

  //tests when you try to add a copy of a layer and the layer is not the top one
  @Test
  public void testAddCopyCurrentLayerNotTopOne() {
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    layeredImageEditorModel.addImage("Hi", checkerBoardGenerate1.generateImage("yo"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    layeredImageEditorModel.setCurrentLayer("Hi");
    layeredImageEditorModel.addCopyLayer("Hi", "Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
  }

  //test for getLayers

  //test get layers when there are no layers inside of the layered image
  @Test
  public void testGetLayersEmptyLayeredImage() {
    layeredImageEditorModel.addImage("hello", new Image(pixelList, "yo"));
    layeredImageEditorModel.removeImage("hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    assertEquals(new ArrayList<>(), layeredImageEditorModel.getLayers());
  }

  //test get layers when there is only one layer in the layered image
  @Test
  public void testGetLayersOneLayer() {
    layeredImageEditorModel.addImage("Hello", new Image(pixelList, "yo"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    assertEquals(new ArrayList<>(Arrays.asList(new Layer("Hello",
            new Image(pixelList, "yo")))),
        layeredImageEditorModel.getLayers());
  }

  //test get layers when there are multiple layers in the layered image
  @Test
  public void testGetLayersMultipleLayers() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Different",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    assertEquals(new ArrayList<>(Arrays.asList(new Layer("Hello",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                3, 5))))), "hello")), new Layer("Different",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                1, 5))))), "hello")))),
        layeredImageEditorModel.getLayers());
  }

  //tests for exportTopImage -


  //test for exporting the top most visible image but the export manager you use is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportTopImageNullExportManager() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.exportTopImage(null);
  }

  //test for exporting the top image when there are no layers in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testExportTopImageNoLayersInLayeredImage() {
    layeredImageEditorModel.exportTopImage(new PPMExportManager("hello.ppm"));
  }

  //test for exporting the top image but there are no visible layers in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testExportTopImageNoVisibleLayersInImage() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Hello");
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Hi");
    layeredImageEditorModel.exportTopImage(new PPMExportManager("hello.ppm"));
  }

  //test for exporting the top image where there are visible layers but they are all null
  @Test(expected = IllegalArgumentException.class)
  public void testExportTopImageVisibleLayersButAllImagesInLayersAreNull() {
    layeredImageEditorModel.addImage("Hello", null);
    layeredImageEditorModel.addImage("Yo", null);
    layeredImageEditorModel.addImage("Hi", null);
    layeredImageEditorModel.exportTopImage(new PPMExportManager("hello.ppm"));
  }

  //test for exporting the top image and it is valid and the image is at the top
  @Test
  public void testExportPPMTopImageVisibleValidAndExportTopLayer() {
    Writer writer = new StringWriter();
    layeredImageEditorModel.addImage("Hello", fileTest3);
    layeredImageEditorModel.exportTopImage(new PPMExportManager(writer));
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

  //test for exporting the top image and it is valid and the image is not at the top
  //because the top image was not visible so it had to search down for the next visible layer
  @Test
  public void testExportPPMTopImageVisibleValidAndExportLayerThatIsNotTheTop() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Hello");
    layeredImageEditorModel.addImage("OneP", board);

    layeredImageEditorModel.exportTopImage(new PPMExportManager(writer));

    assertEquals(writer.toString(), "P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }


  //exporting a top image that was added through an PPMImportManager
  @Test
  public void testExportImageLayerThatWasAddedThroughImportManager() {
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
    layeredImageEditorModel.addImage("hello", importManager.apply());
    layeredImageEditorModel.exportImage("hello", new PPMExportManager(writer));

    assertEquals(writer.toString(), ppmData);

  }

  //exporting a top image that is a programmatic image (checkerboard) to PPM
  @Test
  public void testExportImageProgrammatic() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    layeredImageEditorModel.addImage("OneP", board);

    layeredImageEditorModel.exportTopImage(new PPMExportManager(writer));

    assertEquals(writer.toString(), "P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }

  //exporting a top image that using a PPM ExportManager
  @Test
  public void testExportImageToPPMFormat() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    layeredImageEditorModel.addImage("OneP", board);

    layeredImageEditorModel.exportTopImage(new PPMExportManager(writer));

    assertEquals(writer.toString(), "P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }


  //exporting a top image that using a PNG ExportManager (to png format)
  @Test
  public void testExportTopImageToPNGFormat() {

    IOManager importManager;
    File theDir = new File("ExportTopImageTestModelVersion");
    theDir.mkdir();

    IImageExportManager pngExport =
        new PNGExportManager("./ExportTopImageTestModelVersion/test1.png");

    IImage rainbow =
        new RainbowImageGenerator(40, 5, 80).generateImage("e");

    layeredImageEditorModel.addImage("OneP", rainbow);

    layeredImageEditorModel.exportTopImage(pngExport);

    importManager = new PNGImportManager("./ExportTopImageTestModelVersion/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

  }

  //exporting a top image that using a JPEG ExportManager
  @Test
  public void testExportTopImageToJPEGFormat() {

    IOManager importManager;
    File theDir = new File("ExportTopImageTestModelVersion");
    theDir.mkdir();

    IImageExportManager pngExport =
        new JPEGExportManager("./ExportTopImageTestModelVersion/test1.jpeg");
    IImage rainbow = new CheckerBoardImageGenerator(1, 2, Color.WHITE,
        Color.BLUE)
        .generateImage("e");

    layeredImageEditorModel.addImage("OneP", rainbow);

    layeredImageEditorModel.exportTopImage(pngExport);

    importManager = new JPEGImportManager("./ExportTopImageTestModelVersion/test1.jpeg");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), new ArrayList<>(new ArrayList<>(Arrays.asList(
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(19, 18, 145), new Pixel(237, 236, 255))),
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(231, 230, 255), new Pixel(9, 8, 135)))))));

  }

  //tests for removeImage


  //try to remove a layer but you provide a null layer name
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullLayerName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.removeImage(null);
  }

  //try to remove a layer but you provide a layer name that is non-existent in the model
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNonExistentLayerName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.removeImage("hi");
  }

  //successfully remove a layer from the top of a layered image
  @Test
  public void testRemoveLayerValidTopLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    assertFalse(layeredImageEditorModel.getLayers().get(0).equals(new Layer("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"))));
  }

  //successfully remove a layer from the middle of a layered image
  @Test
  public void testRemoveLayerValidMiddleOfLayeredImage() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
    layeredImageEditorModel.removeImage("Yo");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);
    assertFalse(layeredImageEditorModel.getLayers().get(1).equals(new Layer("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"))));
  }

  //successfully remove a layer from the the bottom of a layered image
  @Test
  public void testRemoveLayerValidEndOfLayeredImage() {
    boolean success = false;
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 3);
    layeredImageEditorModel.removeImage("Hi");
    assertTrue(layeredImageEditorModel.getLayers().size() == 2);

    try {
      layeredImageEditorModel.getLayers().get(2);
    } catch (IndexOutOfBoundsException e) {
      success = true;
    }

    assertTrue(success);
  }


  //tests when you try to remove a layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNoLayersInModel() {
    layeredImageEditorModel.removeImage("Hello");
  }

  //tests when you remove a programmatic image from the layer
  @Test
  public void testRemoveLayerCheckerBoardImage() {
    layeredImageEditorModel.addImage("Hello",
        checkerBoardGenerate1.generateImage("checker"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
  }

  //tests when you remove a Ppm imported image from the layer
  @Test
  public void testRemoveLayerPPMImported() {
    layeredImageEditorModel.addImage("Hello", i7x7ppm);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
  }

  //tests when you remove a Jpeg imported image from the layer
  @Test
  public void testRemoveLayerJPEGImported() {
    layeredImageEditorModel.addImage("Hello", jpegTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
  }

  //tests when you remove a PNg imported image from the layer
  @Test
  public void testRemoveLayerPNGImported() {
    layeredImageEditorModel.addImage("Hello", pngTestImage);
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
  }

  //tests when you remove a checkerboard imported image from the layer
  @Test
  public void testRemoveLayerCheckerBoardImported() {
    layeredImageEditorModel.addImage("Hello", checkerBoardGenerate1.generateImage("hello"));
    assertTrue(layeredImageEditorModel.getLayers().size() == 1);
    layeredImageEditorModel.removeImage("Hello");
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
  }

  //tests for replace image


  //tests when you try to replace an image but the name of the layer you provide is null
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceLayerNullLayerNameProvided() {
    layeredImageEditorModel.addImage("Hello", i1x2);
    layeredImageEditorModel.replaceImage(null,
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
  }

  //tests when you try to replace an image but the image you provide is null
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceLayerNullImageProvided() {
    layeredImageEditorModel.addImage("Hello", i1x2);
    layeredImageEditorModel.replaceImage("Hello", null);
  }

  //tests when you try to replace an image but the layer name you provided does not exist in the
  //model
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceLayerNameDoesNotExistInModel() {
    layeredImageEditorModel.addImage("Hello", i1x2);
    layeredImageEditorModel.replaceImage("Yo", i2x2);
  }


  //tests when you want to replace a layer but the new image does not have the correct dimensions
  //as the other images in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceLayerButNewImageDoesNotHaveCorrectDimensions() {
    layeredImageEditorModel.addImage("Hello", i1x2);
    layeredImageEditorModel.addImage("Yo", i1x2);
    layeredImageEditorModel.replaceImage("Yo", i2x2);
  }

  //tests a valid case when you replace a layer with a valid new image with other
  //layers in the layered image
  @Test
  public void testValidReplaceLayerCaseMultipleLayers() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.replaceImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    assertEquals(layeredImageEditorModel.getLayers().get(1),
        new Layer("Yo",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                1, 1))))), "hello")));
  }

  //tests a valid case when you replace a layer with a valid new image when there is only one
  //layer, the dimensions do no matter in this case.
  @Test
  public void testValidReplaceLayerCaseOnlyOneLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.replaceImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    assertEquals(layeredImageEditorModel.getLayers().get(0),
        new Layer("Hello",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                1, 1))))), "hello")));
  }


  //tests when you try to replace an image but there are no layers in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageNoLayersInModel() {
    layeredImageEditorModel.replaceImage("Hello", i1x1);
  }

  //tests for export image


  //tests exporting an image from a layer to a file format but the provided layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportingImageNullLayerName() {
    layeredImageEditorModel.addImage("hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage(null, new PPMExportManager("hello.ppm"));
  }

  //tests exporting an image from a layer but it is blank/null
  @Test(expected = IllegalArgumentException.class)
  public void testExportingImageNullImage() {
    layeredImageEditorModel.addImage("hello", null);
    layeredImageEditorModel.exportImage("hello", new PPMExportManager("hello.ppm"));
  }


  //tests exporting an image from a layer to a file format but the provided ExportManager is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportingImageNullExportManager() {
    layeredImageEditorModel.addImage("hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage("hello", null);
  }

  //tests exporting an image from a layer to a file format but the provided layer name does not
  //exist in the model.
  @Test(expected = IllegalArgumentException.class)
  public void testExportingImageLayerNameNotInLayeredImage() {
    layeredImageEditorModel.addImage("hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage("yo", new PPMExportManager("hello.ppm"));
  }

  //tests when you try to export an image but there are no layers to expot in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNoLayers() {
    layeredImageEditorModel.exportImage("Hello", new PPMExportManager("hello.ppm"));
  }


  //tests exporting a programmatic image from the model (checkerboard)
  @Test
  public void testExportingProgrammaticImageFromLayer() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(1, 3, Color.BLACK,
        Color.WHITE).generateImage("board.ppm");
    layeredImageEditorModel.addImage("hello", board);
    layeredImageEditorModel.exportImage("hello", new PPMExportManager(writer));

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


  //tests exporting an image that was put in through an ImportManager file PPM (imported)
  @Test
  public void testExportingInputManagerFileImage() {
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
    layeredImageEditorModel.addImage("hello", importManager.apply());
    layeredImageEditorModel.exportImage("hello", new PPMExportManager(writer));

    assertEquals(writer.toString(), ppmData);
  }


  //tests exporting the top layer from a layered image
  @Test
  public void testExportingTopLayerImage() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");
    layeredImageEditorModel.addImage("hello", board);
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage("hello", new PPMExportManager(writer));

    assertEquals("P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n", writer.toString());
  }

  //tests exporting the middle layer from a layered image
  @Test
  public void testExportingMiddleLayerImage() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.addImage("hello", board);
    layeredImageEditorModel.addImage("hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            2, 3))))), "hello"));
    layeredImageEditorModel.exportImage("hello", new PPMExportManager(writer));

    assertEquals("P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n", writer.toString());
  }


  //tests exporting the bottom layer from a layered image
  @Test
  public void testExportingBottomLayerImage() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage("yo", new PPMExportManager(writer));
    layeredImageEditorModel.addImage("hello", board);

    assertEquals("P3\n"
        + "1 1\n"
        + "255\n"
        + "3\n"
        + "1\n"
        + "1\n", writer.toString());
  }


  //tests exporting with the PPM Exporter
  @Test
  public void testExportingLayerImageWithPPMExporter() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.exportImage("yo", new PPMExportManager(writer));
    layeredImageEditorModel.addImage("hello", board);

    assertEquals("P3\n"
        + "1 1\n"
        + "255\n"
        + "3\n"
        + "1\n"
        + "1\n", writer.toString());
  }


  //exporting a top image that using a PNG ExportManager (to png format)
  @Test
  public void testExportImageToPNGFormat() {

    IOManager importManager;
    File theDir = new File("ExportTopImageTestModelVersion");
    theDir.mkdir();

    IImageExportManager pngExport =
        new PNGExportManager("./ExportTopImageTestModelVersion/test1.png");

    IImage rainbow =
        new RainbowImageGenerator(40, 5, 80).generateImage("e");

    layeredImageEditorModel.addImage("OneP", rainbow);

    layeredImageEditorModel.exportImage("OneP", pngExport);

    importManager = new PNGImportManager("./ExportTopImageTestModelVersion/test1.png");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), rainbow.getPixel2dArray());

  }

  //exporting a top image that using a JPEG ExportManager
  @Test
  public void testExportImageToJPEGFormat() {

    IOManager importManager;
    File theDir = new File("ExportTopImageTestModelVersion");
    theDir.mkdir();

    IImageExportManager pngExport =
        new JPEGExportManager("./ExportTopImageTestModelVersion/test1.jpeg");
    IImage rainbow =
        new CheckerBoardImageGenerator(1, 2, Color.WHITE, Color.BLUE)
        .generateImage("e");

    layeredImageEditorModel.addImage("OneP", rainbow);

    layeredImageEditorModel.exportImage("OneP", pngExport);

    importManager = new JPEGImportManager("./ExportTopImageTestModelVersion/test1.jpeg");
    IImage imported = importManager.apply();

    assertEquals(imported.getPixel2dArray(), new ArrayList<>(new ArrayList<>(Arrays.asList(
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(19, 18, 145), new Pixel(237, 236, 255))),
        new ArrayList<IPixel>(Arrays.asList(
            new Pixel(231, 230, 255), new Pixel(9, 8, 135)))))));

  }

  //tests for applyEditToCurrentLayer


  //when you want to apply an edit to the current layer but it is null
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditNull() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.applyEditToCurrentLayer(null);
  }


  //when you want to apply an edit to the current layer but there are no layers
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditCurrentLayerButNoLayers() {
    layeredImageEditorModel.applyEditToCurrentLayer(new SepiaTransformation());
  }

  //when you want to apply an edit to the current layer but the image is null
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditCurrentLayerButNullBlankImage() {
    layeredImageEditorModel.addImage("hello", null);
    layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);
  }


  //succesfully apply a sepia transformation to the current layer
  @Test
  public void test7x7SepiaApplyCurrentLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToCurrentLayer(sepiaEdit);
    IImage sepia = layeredImageEditorModel.getImage("hello");

    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.349F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.686F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.272F
                + i7X7Copy.getPixelAt(i, j).getGreen() * 0.534F
                + i7X7Copy.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //succesfully apply a grey scale transformation to the current layer
  @Test
  public void testPhotoEditGreyScaleColorTransformationCurrentLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    layeredImageEditorModel.applyEditToCurrentLayer(greyEdit);
    IImage grayImage = layeredImageEditorModel.getImage("smallImage.ppm");
    for (int i = 0; i < grayImage.getPixelHeight(); i++) {
      for (int j = 0; j < grayImage.getPixelWidth(); j++) {
        assertEquals(grayImage.getPixelAt(i, j).getRed(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getGreen(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getBlue(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));
      }
    }
  }

  //successfully apply a composite photo edit to the current layer
  @Test
  public void testPhotoEditCompositeCurrentLayer() {
    IImage i7x7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    layeredImageEditorModel.applyEditToCurrentLayer(new CompositePhotoEdit(
        new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
        new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));
    IImage compositeImage = layeredImageEditorModel.getImage("smallImage.ppm");

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7Copy))));

    assertEquals(compositeImage, check);
  }


  //succesfully apply a blur filter to the current layer
  @Test
  public void test7x7BlurFilterCurrentLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }


  //succesfully apply a sharpen filter to the current layer
  @Test
  public void test7x7CompleteMathSharpenedCurrentLayerApplyPhotoEdit() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToCurrentLayer(sharpenEdit);
    IImage sharpened = layeredImageEditorModel.getImage("hello");
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 1F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getBlue() * -0.125F)));
  }


  //tests when a progrmamatic current layer image is edited (checkerBoard image)
  @Test
  public void testCheckerBoardCompleteMathBlurredCurrentLayerApplyPhotoEdit() {
    IImage board = new CheckerBoardImageGenerator(3,
        4, new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");

    layeredImageEditorModel.addImage("hello",
        board);
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3).getRed(),
        Math.round(
            (board.getPixelAt(2, 2).getRed() * 0.0625F)
                + (board.getPixelAt(2, 3).getRed() * 0.125F)
                + (board.getPixelAt(2, 4).getRed() * 0.0625F)
                + (board.getPixelAt(3, 2).getRed() * 0.125F)
                + (board.getPixelAt(3, 3).getRed() * 0.25F)
                + (board.getPixelAt(3, 4).getRed() * 0.125F)
                + (board.getPixelAt(4, 2).getRed() * 0.0625F)
                + (board.getPixelAt(4, 3).getRed() * 0.125F)
                + (board.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (board.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(2, 3).getGreen() * 0.125F)
                + (board.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (board.getPixelAt(3, 2).getGreen() * 0.125F)
                + (board.getPixelAt(3, 3).getGreen() * 0.25F)
                + (board.getPixelAt(3, 4).getGreen() * 0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(4, 3).getGreen() * 0.125F)
                + (board.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (board.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(2, 3).getBlue() * 0.125F)
                + (board.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (board.getPixelAt(3, 2).getBlue() * 0.125F)
                + (board.getPixelAt(3, 3).getBlue() * 0.25F)
                + (board.getPixelAt(3, 4).getBlue() * 0.125F)
                + (board.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(4, 3).getBlue() * 0.125F)
                + (board.getPixelAt(4, 4).getBlue() * 0.0625F)));

  }

  //tests when an imported current layer image is edited (PPM Blur)
  @Test
  public void test7x7PPMCompleteMathCurrentLayerPhotoEdit() {
    IImage i7X7PPMCopy = new Image(i7x7ppm.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7ppm);
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  //blur edit applied twice on the same current layer
  @Test
  public void testApplyingSameFilterTwiceCurrentLayerPhotoEdit() {

    IImage board = new CheckerBoardImageGenerator(1, 5,
        new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");
    layeredImageEditorModel.addImage("hello", board);
    IImage boardCopy = new Image(board.getPixel2dArray(), board.getFileName());

    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
    IImage blurred = new Image(layeredImageEditorModel.getImage("hello").getPixel2dArray(),
        layeredImageEditorModel.getImage("hello").getFileName());
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (boardCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (boardCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (blurred.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(2, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (blurred.getPixelAt(3, 2).getGreen() * 0.125F)
                + (blurred.getPixelAt(3, 3).getGreen() * 0.25F)
                + (blurred.getPixelAt(3, 4).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(4, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 4).getGreen() * 0.0625F)));

  }

  //edit when the current layer is not at the top of the layered image
  @Test
  public void testApplyingEditToCurrentLayerWhenItIsNotAtTheTop() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.setCurrentLayer("hello");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
    IImage blurred = layeredImageEditorModel.getImage("hello");

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //tests trying to apply a photoEdit but the current layer is blank/null
  @Test(expected = IllegalArgumentException.class)
  public void testApplyingEditToCurrentLayerBlankImage() {
    layeredImageEditorModel.addImage("hi", null);
    layeredImageEditorModel.applyEditToCurrentLayer(blurEdit);
  }




  //tests for setCurrent


  //trying to make a layer current but you provide a null layer name
  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNullLayerName() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer(null);
  }

  //trying to make a layer current but you provide a layer name that does not exist in the model
  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerNameDoesNotExistInImage() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Not here");
  }

  //trying to make a layer current but there are no layers in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerNoLayersInImage() {
    layeredImageEditorModel.setCurrentLayer("Not here");
  }

  //making a layer current in a vaild manner
  @Test
  public void testSetCurrentLayerMiddleValid() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Yo");
    assertEquals(new Layer("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //making the bottom layer in the layered image the current
  @Test
  public void testSetCurrentLayerBottomValid() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Yo");
    assertEquals(new Layer("Yo",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //making the top layer that is already current again
  @Test
  public void testSetCurrentLayerTopCurrentAgainValid() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 5))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Yo");
    assertEquals(new Layer("Yo",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests for applyEditToLayer


  //when you want to apply an edit to a specific layer but the layer name you give is null
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerNullLayerName() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.applyEditToLayer(null, blurEdit);
  }

  //when you want to apply an edit to a specific layer but the layer name you give is empty
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerEmptyLayerName() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.applyEditToLayer("", blurEdit);
  }

  //when you want to apply an edit to a specific layer but the layer name you give does not exist
  //in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerLayerNameDoesNotExist() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.applyEditToLayer("not here", blurEdit);
  }

  //when you want to apply an edit to a specific layer but you provide a null photo edit
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerNullEdit() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.applyEditToLayer("yo", null);
  }

  //when you want to apply an edit to a specific layer but there are no layers
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerButNoLayers() {
    layeredImageEditorModel.applyEditToLayer("yo", blurEdit);
  }

  //when you want to apply an edit to a specific layer but the image there is blank
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditToSpecificLayerButItIsBlank() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.addImage("yo", null);
    layeredImageEditorModel.applyEditToLayer("yo", blurEdit);
  }


  //successfully apply a sepia transformation to a specific layer
  @Test
  public void test7x7SepiaApplySpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToLayer("hello", sepiaEdit);
    IImage sepia = layeredImageEditorModel.getImage("hello");

    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.349F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.686F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.272F
                + i7X7Copy.getPixelAt(i, j).getGreen() * 0.534F
                + i7X7Copy.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //succesfully apply a grey scale transformation to a specific layer
  @Test
  public void testPhotoEditGreyScaleColorTransformationSpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    layeredImageEditorModel.applyEditToLayer("smallImage.ppm", greyEdit);
    IImage grayImage = layeredImageEditorModel.getImage("smallImage.ppm");
    for (int i = 0; i < grayImage.getPixelHeight(); i++) {
      for (int j = 0; j < grayImage.getPixelWidth(); j++) {
        assertEquals(grayImage.getPixelAt(i, j).getRed(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getGreen(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getBlue(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));
      }
    }
  }

  //successfully apply a composite photo edit to a specific layer
  @Test
  public void testPhotoEditCompositeSpecificLayer() {
    IImage i7x7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    layeredImageEditorModel.applyEditToLayer("smallImage.ppm", new CompositePhotoEdit(
        new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
        new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));
    IImage compositeImage = layeredImageEditorModel.getImage("smallImage.ppm");

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7Copy))));

    assertEquals(compositeImage, check);
  }


  //succesfully apply a blur filter to a specific layer
  @Test
  public void test7x7BlurFilterSpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }


  //succesfully apply a sharpen filter to a specific layer
  @Test
  public void test7x7CompleteMathSharpenedSpecificLayerApplyPhotoEdit() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    layeredImageEditorModel.applyEditToLayer("hello", sharpenEdit);
    IImage sharpened = layeredImageEditorModel.getImage("hello");
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 1F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getBlue() * -0.125F)));
  }


  //tests when a progrmamatic specific layer image is edited (checkerBoard image)
  @Test
  public void testCheckerBoardCompleteMathBlurredSpecificLayerApplyPhotoEdit() {
    IImage board = new CheckerBoardImageGenerator(3,
        4, new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");

    layeredImageEditorModel.addImage("hello",
        board);
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3).getRed(),
        Math.round(
            (board.getPixelAt(2, 2).getRed() * 0.0625F)
                + (board.getPixelAt(2, 3).getRed() * 0.125F)
                + (board.getPixelAt(2, 4).getRed() * 0.0625F)
                + (board.getPixelAt(3, 2).getRed() * 0.125F)
                + (board.getPixelAt(3, 3).getRed() * 0.25F)
                + (board.getPixelAt(3, 4).getRed() * 0.125F)
                + (board.getPixelAt(4, 2).getRed() * 0.0625F)
                + (board.getPixelAt(4, 3).getRed() * 0.125F)
                + (board.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (board.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(2, 3).getGreen() * 0.125F)
                + (board.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (board.getPixelAt(3, 2).getGreen() * 0.125F)
                + (board.getPixelAt(3, 3).getGreen() * 0.25F)
                + (board.getPixelAt(3, 4).getGreen() * 0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(4, 3).getGreen() * 0.125F)
                + (board.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (board.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(2, 3).getBlue() * 0.125F)
                + (board.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (board.getPixelAt(3, 2).getBlue() * 0.125F)
                + (board.getPixelAt(3, 3).getBlue() * 0.25F)
                + (board.getPixelAt(3, 4).getBlue() * 0.125F)
                + (board.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(4, 3).getBlue() * 0.125F)
                + (board.getPixelAt(4, 4).getBlue() * 0.0625F)));

  }

  //tests when an imported specific layer image is edited (PPM Blur)
  @Test
  public void test7x7PPMCompleteMathSpecificLayerPhotoEdit() {
    IImage i7X7PPMCopy = new Image(i7x7ppm.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7ppm);
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getRed(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getBlue(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  //blur edit applied twice on the same specific layer
  @Test
  public void testApplyingSameFilterTwiceSpecificLayerPhotoEdit() {

    IImage board = new CheckerBoardImageGenerator(1, 5,
        new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");
    layeredImageEditorModel.addImage("hello", board);
    IImage boardCopy = new Image(board.getPixel2dArray(), board.getFileName());

    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    IImage blurred = new Image(layeredImageEditorModel.getImage("hello").getPixel2dArray(),
        layeredImageEditorModel.getImage("hello").getFileName());
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (boardCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (boardCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));

    assertEquals(layeredImageEditorModel.getImage("hello").getPixelAt(3, 3)
            .getGreen(),
        Math.round(
            (blurred.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(2, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (blurred.getPixelAt(3, 2).getGreen() * 0.125F)
                + (blurred.getPixelAt(3, 3).getGreen() * 0.25F)
                + (blurred.getPixelAt(3, 4).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(4, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 4).getGreen() * 0.0625F)));

  }

  //edit when the specific layer is at the top of the layered image
  @Test
  public void testApplyingEditToSpecificLayerWhenTopOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.addImage("same", i1x2);
    layeredImageEditorModel.setCurrentLayer("hi");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    layeredImageEditorModel.applyEditToLayer("hi", blurEdit);
    IImage blurred = layeredImageEditorModel.getImage("hi");

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //edit when the specific layer is at the bottom of the layered image
  @Test
  public void testApplyingEditToSpecificLayerWhenMiddleOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.addImage("bottom", i1x2);
    layeredImageEditorModel.setCurrentLayer("hello");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    IImage blurred = layeredImageEditorModel.getImage("hello");

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //edit when the specific layer is at the bottom of the layered image
  @Test
  public void testApplyingEditToSpecificLayerWhenBottomOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.setCurrentLayer("hello");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    layeredImageEditorModel.applyEditToLayer("hello", blurEdit);
    IImage blurred = layeredImageEditorModel.getImage("hello");

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //tests for setLayerVisible


  //tests when someone wants to set a layer visibile but the layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testSetVisibleLayerNameNull() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerVisible(null);
  }

  //tests when someone wants to set a layer visibile but the layer name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testSetVisibleLayerNameEmpty() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerVisible("");
  }

  //tests when someone wants to set a layer visibile but the layer name does not exist in the image
  @Test(expected = IllegalArgumentException.class)
  public void testSetVisibleLayerNameDoesNotExist() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerVisible("hello");
  }


  //tests when someone wants to set a layer visibile and the layer is already visible
  @Test
  public void testSetVisibleLayerValidAlreadyVisible() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }


  //tests when someone wants to set a layer visible and the layer is transparent currently
  @Test
  public void testSetVisibleLayerValidLayerStartsTransparent() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }

  //tests when someone wants to set a blank layer to visible
  @Test
  public void testSetVisibleLayerValidBlankLayerStartsTransparent() {
    layeredImageEditorModel.addImage("Yo", null);
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }


  //tests when someone wants to set the top layer to visible
  @Test
  public void testSetVisibleLayerValidTopLayer() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }

  //tests when someone wants to set a middle layer to visible
  @Test
  public void testSetVisibleLayerValidMiddleLayer() {
    layeredImageEditorModel.addImage("HI",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getLayers().get(1).isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getLayers().get(1).isVisible());
  }

  //tests when someone wants to set a bottom layer to visible
  @Test
  public void testSetVisibleLayerValidBottomLayer() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getLayers().get(2).isVisible());
    layeredImageEditorModel.setLayerVisible("Yo");
    assertTrue(layeredImageEditorModel.getLayers().get(2).isVisible());
  }

  //tests for setLayerTransparent


  //tests when someone wants to set a layer transparent but the layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testSetTransparentLayerNameNull() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent(null);
  }

  //tests when someone wants to set a layer transparent but the layer name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testSetTransparentLayerNameEmpty() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("");
  }

  //tests when someone wants to set a layer transparent but the layer name does not exist in the
  //image
  @Test(expected = IllegalArgumentException.class)
  public void testSetTransparentLayerNameDoesNotExist() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("hello");
  }


  //tests when someone wants to set a layer transparent and the layer is currently visible
  @Test
  public void testSetTransparentLayerValidAlreadyVisible() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertTrue(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }


  //tests when someone wants to set a layer transparent and the layer is transparent currently
  @Test
  public void testSetTransparentLayerValidLayerStartsTransparent() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }

  //tests when someone wants to set a blank layer to transparent
  @Test
  public void testSetTransparentLayerValidBlankLayerStartsVisible() {
    layeredImageEditorModel.addImage("Yo", null);
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());

  }


  //tests when someone wants to set the top layer to transparent
  @Test
  public void testSetTransparentLayerValidTopLayer() {
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getCurrentLayerCopy().isVisible());
  }

  //tests when someone wants to set a middle layer to transparent
  @Test
  public void testSetTransparentLayerValidMiddleLayer() {
    layeredImageEditorModel.addImage("HI",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getLayers().get(1).isVisible());
  }

  //tests when someone wants to set a bottom layer to transparent
  @Test
  public void testSetTransparentLayerValidBottomLayer() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Yo");
    assertFalse(layeredImageEditorModel.getLayers().get(2).isVisible());
  }

  //tests for isVisible


  //tests when someone provides a null layer name to check if its visible
  @Test(expected = IllegalArgumentException.class)
  public void testIsVisibleNullLayerName() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.isVisible(null);
  }


  //tests when someone provides a non existent layer name to check if its visible
  @Test(expected = IllegalArgumentException.class)
  public void testIsVisibleLayerNameDoesNotExist() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.isVisible("Hi");
  }

  //tests when someone provides an empty layer name to check if its visible
  @Test(expected = IllegalArgumentException.class)
  public void testIsVisibleLayerNameEmpty() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.isVisible("");
  }

  //tests when someone provides a valid layer and the layer is visible
  @Test
  public void testIsVisibleLayerValidLayerIsVisible() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerVisible("Top");
    assertTrue(layeredImageEditorModel.isVisible("Top"));
  }

  //tests when someone provides a valid layer and the layer is transparent
  @Test
  public void testIsVisibleLayerValidLayerIsTransparent() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setLayerTransparent("Top");
    assertFalse(layeredImageEditorModel.isVisible("Top"));
  }

  //tests when someone provides a valid layer but the image is blank and it is visible
  @Test
  public void testIsVisibleBlankLayerValidLayerIsVisible() {
    layeredImageEditorModel.addImage("Top", null);
    layeredImageEditorModel.setLayerVisible("Top");
    assertTrue(layeredImageEditorModel.isVisible("Top"));
  }

  //tests when someone provides a valid layer but the image is blank and not visible
  @Test
  public void testIsVisibleBlankLayerValidLayerNotVisible() {
    layeredImageEditorModel.addImage("Top", null);
    layeredImageEditorModel.setLayerTransparent("Top");
    assertFalse(layeredImageEditorModel.isVisible("Top"));
  }

  //tests for saving a layer


  //valid case of saving multiple images and getting the proper text file
  @Test
  public void testValidSaveLayersCase() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests");
    theDir.mkdir();

    layeredImageEditorModel.addImage("hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("lastOne",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));

    layeredImageEditorModel.saveLayeredImage("Tests");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imageLocations.txt\n"
        + "hello.png\n"
        + "yo.png\n"
        + "lastOne.png\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("hello | isVisible: true | ./Tests/hello.png\n"
        + "yo | isVisible: true | ./Tests/yo.png\n"
        + "lastOne | isVisible: true | ./Tests/lastOne.png\n", contents);
  }


  //saving a layered image when the images in the layered image are all blank
  @Test
  public void testValidSaveLayerAllBlank() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests1");
    theDir.mkdir();

    layeredImageEditorModel.addImage("hello", null);
    layeredImageEditorModel.addImage("yo", null);
    layeredImageEditorModel.addImage("Hi", null);

    layeredImageEditorModel.saveLayeredImage("Tests1");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("hello | isVisible: true | no image file\n"
        + "yo | isVisible: true | no image file\n"
        + "Hi | isVisible: true | no image file\n", contents);
  }

  //saving a layered image when the images in the layered image are blank and same are not
  @Test
  public void testValidSaveLayersSomeBlankSomeNot() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests2");
    theDir.mkdir();

    layeredImageEditorModel.addImage("hello", null);
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("lastOne",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));

    layeredImageEditorModel.saveLayeredImage("Tests2");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imageLocations.txt\n"
        + "yo.png\n"
        + "lastOne.png\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("hello | isVisible: true | no image file\n"
        + "yo | isVisible: true | ./Tests2/yo.png\n"
        + "lastOne | isVisible: true | ./Tests2/lastOne.png\n", contents);
  }


  //saving a layered image when there are no layers in the image
  @Test
  public void testValidSaveLayersNoLayersInTheImage() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests10");
    theDir.mkdir();

    layeredImageEditorModel.saveLayeredImage("Tests10");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("", contents);
  }


  //saving a layered image when the images are programmed images
  @Test
  public void testValidSaveLayersProgrammedImages() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests4");
    theDir.mkdir();

    layeredImageEditorModel.addImage("Hello", i2x2);
    layeredImageEditorModel.addImage("HelloV2", i2x2);
    layeredImageEditorModel.addImage("HelloV3", i2x2);

    layeredImageEditorModel.saveLayeredImage("Tests4");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imageLocations.txt\n"
        + "Hello.png\n"
        + "HelloV3.png\n"
        + "HelloV2.png\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("Hello | isVisible: true | ./Tests4/Hello.png\n"
        + "HelloV2 | isVisible: true | ./Tests4/HelloV2.png\n"
        + "HelloV3 | isVisible: true | ./Tests4/HelloV3.png\n", contents);
  }

  //saving a layered image when the images are imported images
  @Test
  public void testValidSaveLayersImportedImages() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests6");
    theDir.mkdir();

    layeredImageEditorModel.addImage("imported", i7x7ppm);
    layeredImageEditorModel.addImage("imported1", i7x7ppm);
    layeredImageEditorModel.addImage("imported2", i7x7ppm);

    layeredImageEditorModel.saveLayeredImage("Tests6");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imported2.png\n"
        + "imported1.png\n"
        + "imported.png\n"
        + "imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("imported | isVisible: true | ./Tests6/imported.png\n"
        + "imported1 | isVisible: true | ./Tests6/imported1.png\n"
        + "imported2 | isVisible: true | ./Tests6/imported2.png\n", contents);
  }

  //saving a layered image when all the images are not visible
  @Test
  public void testValidSaveLayersAllNotVisible() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests7");
    theDir.mkdir();

    layeredImageEditorModel.addImage("imported", i7x7ppm);
    layeredImageEditorModel.setLayerTransparent("imported");
    layeredImageEditorModel.addImage("imported1", i7x7ppm);
    layeredImageEditorModel.setLayerTransparent("imported1");
    layeredImageEditorModel.addImage("imported2", i7x7ppm);
    layeredImageEditorModel.setLayerTransparent("imported2");

    layeredImageEditorModel.saveLayeredImage("Tests7");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imported2.png\n"
        + "imported1.png\n"
        + "imported.png\n"
        + "imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("imported | isVisible: false | ./Tests7/imported.png\n"
        + "imported1 | isVisible: false | ./Tests7/imported1.png\n"
        + "imported2 | isVisible: false | ./Tests7/imported2.png\n", contents);
  }


  //saving a layered image when all the images are imported as jpeg
  @Test
  public void testValidSaveLayersAllJPEGIMmported() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests12");
    theDir.mkdir();

    layeredImageEditorModel.addImage("imported", jpegTestImage);
    layeredImageEditorModel.setLayerTransparent("imported");
    layeredImageEditorModel.addImage("imported1", jpegTestImage);
    layeredImageEditorModel.setLayerTransparent("imported1");
    layeredImageEditorModel.addImage("imported2", jpegTestImage);
    layeredImageEditorModel.setLayerTransparent("imported2");

    layeredImageEditorModel.saveLayeredImage("Tests12");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imported2.png\n"
        + "imported1.png\n"
        + "imported.png\n"
        + "imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("imported | isVisible: false | ./Tests12/imported.png\n"
        + "imported1 | isVisible: false | ./Tests12/imported1.png\n"
        + "imported2 | isVisible: false | ./Tests12/imported2.png\n", contents);
  }

  //saving a layered image when all the images are imported as png
  @Test
  public void testValidSaveLayersAllPNGIMmported() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests13");
    theDir.mkdir();

    layeredImageEditorModel.addImage("imported", pngTestImage);
    layeredImageEditorModel.setLayerTransparent("imported");
    layeredImageEditorModel.addImage("imported1", pngTestImage);
    layeredImageEditorModel.setLayerTransparent("imported1");
    layeredImageEditorModel.addImage("imported2", pngTestImage);
    layeredImageEditorModel.setLayerTransparent("imported2");

    layeredImageEditorModel.saveLayeredImage("Tests13");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imported2.png\n"
        + "imported1.png\n"
        + "imported.png\n"
        + "imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("imported | isVisible: false | ./Tests13/imported.png\n"
        + "imported1 | isVisible: false | ./Tests13/imported1.png\n"
        + "imported2 | isVisible: false | ./Tests13/imported2.png\n", contents);
  }

  //saving a layered image when some of the images are visible and some are not
  @Test
  public void testValidSaveLayersSomeNotVisible() {
    Writer writer = new StringWriter();
    BufferedReader reader = null;
    File theDir = new File("Tests8");
    theDir.mkdir();

    layeredImageEditorModel.addImage("imported", i7x7ppm);
    layeredImageEditorModel.setLayerTransparent("imported");
    layeredImageEditorModel.addImage("imported1", i7x7ppm);
    layeredImageEditorModel.addImage("imported2", i7x7ppm);

    layeredImageEditorModel.saveLayeredImage("Tests8");

    for (File file : theDir.listFiles()) {
      try {
        if (file.getName().endsWith(".txt")) {
          reader = new BufferedReader(new FileReader(file));
        }
        writer.write(file.getName() + "\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write");
      }
    }

    assertEquals("imported2.png\n"
        + "imported1.png\n"
        + "imported.png\n"
        + "imageLocations.txt\n", writer.toString());

    String contents = "";
    try {
      for (int i = 0; i < layeredImageEditorModel.getLayers().size(); i++) {
        contents += reader.readLine() + "\n";
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read!");
    }
    assertEquals("imported | isVisible: false | ./Tests8/imported.png\n"
        + "imported1 | isVisible: true | ./Tests8/imported1.png\n"
        + "imported2 | isVisible: true | ./Tests8/imported2.png\n", contents);
  }

  //tests for getting the current layer name


  //trying to get the name of the current layer but there are no layers in the layered image
  @Test(expected = IllegalArgumentException.class)
  public void testGetCurrentLayerNameNoLayers() {
    assertEquals("", layeredImageEditorModel.getCurrentLayerName());
  }

  //trying to get the name of the current layer when the current layer is at the top
  @Test
  public void testGetCurrentLayerNameTopCurrentLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals("Hello", layeredImageEditorModel.getCurrentLayerName());
  }

  //trying to get the name of the current layer when  the current layer is in the middle
  @Test
  public void testGetCurrentLayerNameMiddleCurrentLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Middle");
    assertEquals("Middle", layeredImageEditorModel.getCurrentLayerName());
  }

  //trying to get the name of the current layer when  the current layer is at the bottom
  @Test
  public void testGetCurrentLayerNameBottomCurrentLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Bottom");
    assertEquals("Bottom", layeredImageEditorModel.getCurrentLayerName());
  }

  //tests for getCurrentLayerImage


  //trying to get the image of the current layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void getCurrentLayerImageNoLayers() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.getCurrentLayerImage();
  }


  //trying to get the image of the current layer when the current layer is at the top
  @Test
  public void getCurrentLayerImageTopLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getCurrentLayerImage());
  }

  //trying to get the image of the current layer when the current layer is in the middle
  @Test
  public void getCurrentLayerImageMiddleLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Middle");
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getCurrentLayerImage());
  }

  //trying to get the image of the current layer when the current layer is at the bottom
  @Test
  public void getCurrentLayerImageBottomLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Bottom");
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getCurrentLayerImage());
  }


  //trying to get the image of the current layer but the image is null
  @Test
  public void getCurrentLayerImageLayerHasNullImage() {
    layeredImageEditorModel.addImage("Hello", null);
    assertNull(layeredImageEditorModel.getCurrentLayerImage());
  }

  //trying to get the image of the current layer and the image is programmatic (checkerboard)
  @Test
  public void getCurrentLayerImageProgrammatic() {
    layeredImageEditorModel.addImage("Hello",
        checkerBoardGenerate1.generateImage("hello"));
    assertEquals(new CheckerBoardImageGenerator(2,
            10,
            new Pixel(0, 0, 0),
            new Pixel(255, 255, 255)).generateImage("hello"),
        layeredImageEditorModel.getCurrentLayerImage());
  }

  //trying to get the image of the current layer but the image was imported in to the model
  @Test
  public void getCurrentLayerImageImported() {
    layeredImageEditorModel.addImage("Hello", i7x7ppm);
    assertEquals(new Image(i7x7ppm.getPixel2dArray(), i7x7ppm.getFileName()),
        layeredImageEditorModel.getCurrentLayerImage());
  }

  //tests for getCurrentLayerCopy


  //tests when you want to get the current layer but there are no layers
  @Test(expected = IllegalArgumentException.class)
  public void testGetCurrentLayerNoLayers() {
    layeredImageEditorModel.getCurrentLayerCopy();
  }


  //tests when you want to get the current layer when it is at the top of the image
  @Test
  public void testGetCurrentLayerTopLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(new Layer("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests when you want to get the current layer when it is in the middle of the image
  @Test
  public void testGetCurrentLayerMiddleLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Middle");
    assertEquals(new Layer("Middle",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests when you want to get the current layer when it is at the bottom of the image
  @Test
  public void testGetCurrentLayerBottomLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Bottom");
    assertEquals(new Layer("Bottom",
            new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
                3, 4))))), "hello")),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests when you want to get the current layer but it is blank
  @Test
  public void testGetCurrentLayerBlank() {
    layeredImageEditorModel.addImage("Hello", null);
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(new Layer("Hello", null),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests when you want to get the current layer but it has a programmatic image in it
  @Test
  public void testGetCurrentLayerProgrammaticImage() {
    layeredImageEditorModel.addImage("Hello", i7x7);
    assertEquals(new Layer("Hello", i7x7), layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests when you want to get the current layer but it has an imported image in it
  @Test
  public void testGetCurrentLayerImportedImage() {
    layeredImageEditorModel.addImage("Hello", i7x7ppm);
    assertEquals(new Layer("Hello", i7x7ppm),
        layeredImageEditorModel.getCurrentLayerCopy());
  }

  //tests for applyPhotoEdit


  //you want to apply a photoEdit on a layer but the provided layer name is null
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButLayerNameIsNull() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.applyPhotoEdit(null, blurEdit);
  }

  //you want to apply a photoEdit on a layer but the provided layer name is empty
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButLayerNameIsEmpty() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.applyPhotoEdit("", blurEdit);
  }

  //you want to apply a photoEdit on a layer but the provided photoEdit is null
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButPhotoEditIsNull() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.applyPhotoEdit("Top", null);
  }

  //you want to apply a photoEdit on a layer but the provided layer name does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButLayerNameDoesNotExist() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.applyPhotoEdit("Hello", blurEdit);
  }

  //you want to apply a photoEdit on a layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButNoLayersInModel() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.applyPhotoEdit("Top", blurEdit);
  }


  //when you want to apply an photo edit to a specific layer but the image there is blank
  @Test(expected = IllegalArgumentException.class)
  public void testApplyPhotoEditButLayerIsBlank() {
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            1, 1))))), "hello"));
    layeredImageEditorModel.addImage("yo", null);
    layeredImageEditorModel.applyPhotoEdit("yo", blurEdit);
  }


  //successfully apply a sepia transformation to a specific layer
  @Test
  public void test7x7SepiaApplyPhotoEditSpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    IImage sepia = layeredImageEditorModel.applyPhotoEdit("hello", sepiaEdit);

    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.349F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.686F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.393F
                  + i7X7Copy.getPixelAt(i, j).getGreen() * 0.769F
                  + i7X7Copy.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7X7Copy.getPixelAt(i, j).getRed() * 0.272F
                + i7X7Copy.getPixelAt(i, j).getGreen() * 0.534F
                + i7X7Copy.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //successfully apply a grey scale transformation to a specific layer
  @Test
  public void testApplyPhotoEditGreyScaleColorTransformationSpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    IImage grayImage = layeredImageEditorModel.applyPhotoEdit("smallImage.ppm", greyEdit);
    for (int i = 0; i < grayImage.getPixelHeight(); i++) {
      for (int j = 0; j < grayImage.getPixelWidth(); j++) {
        assertEquals(grayImage.getPixelAt(i, j).getRed(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getGreen(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getBlue(),
            Math.round(
                i7X7Copy.getPixelAt(i, j).getRed() * 0.2126F
                    + i7X7Copy.getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + i7X7Copy.getPixelAt(i, j).getBlue()
                    * 0.0722F));
      }
    }
  }

  //successfully apply a composite photo edit to a specific layer
  @Test
  public void testApplyPhotoEditCompositeSpecificLayer() {
    IImage i7x7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("smallImage.ppm", i7x7);
    IImage compositeImage = layeredImageEditorModel.applyPhotoEdit("smallImage.ppm",
        new CompositePhotoEdit(new CompositePhotoEdit(new GreyScaleTransformation(),
            new SepiaTransformation()),
            new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7Copy))));

    assertEquals(compositeImage, check);
  }


  //succesfully apply a blur filter to a specific layer
  @Test
  public void test7x7ApplyPhotoEditBlurFilterSpecificLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7X7Copy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7Copy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7Copy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }


  //succesfully apply a sharpen filter to a specific layer
  @Test
  public void test7x7CompleteMathSharpenedApplyPhotoEditToLayer() {
    IImage i7X7Copy = new Image(i7x7.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7);
    IImage sharpened = layeredImageEditorModel.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getRed() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7X7Copy.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 3).getGreen() * 1F)
                + (i7X7Copy.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7X7Copy.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7X7Copy.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 1F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getBlue() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getBlue() * -0.125F)));
  }


  //tests when a progrmamatic specific layer image is edited (checkerBoard image)
  @Test
  public void testCheckerBoardCompleteMathBlurredApplyPhotoEdit() {
    IImage board = new CheckerBoardImageGenerator(3,
        4, new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");

    layeredImageEditorModel.addImage("hello",
        board);
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (board.getPixelAt(2, 2).getRed() * 0.0625F)
                + (board.getPixelAt(2, 3).getRed() * 0.125F)
                + (board.getPixelAt(2, 4).getRed() * 0.0625F)
                + (board.getPixelAt(3, 2).getRed() * 0.125F)
                + (board.getPixelAt(3, 3).getRed() * 0.25F)
                + (board.getPixelAt(3, 4).getRed() * 0.125F)
                + (board.getPixelAt(4, 2).getRed() * 0.0625F)
                + (board.getPixelAt(4, 3).getRed() * 0.125F)
                + (board.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (board.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(2, 3).getGreen() * 0.125F)
                + (board.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (board.getPixelAt(3, 2).getGreen() * 0.125F)
                + (board.getPixelAt(3, 3).getGreen() * 0.25F)
                + (board.getPixelAt(3, 4).getGreen() * 0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (board.getPixelAt(4, 3).getGreen() * 0.125F)
                + (board.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (board.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(2, 3).getBlue() * 0.125F)
                + (board.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (board.getPixelAt(3, 2).getBlue() * 0.125F)
                + (board.getPixelAt(3, 3).getBlue() * 0.25F)
                + (board.getPixelAt(3, 4).getBlue() * 0.125F)
                + (board.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (board.getPixelAt(4, 3).getBlue() * 0.125F)
                + (board.getPixelAt(4, 4).getBlue() * 0.0625F)));

  }

  //tests when an imported specific layer image is edited (PPM Blur)
  @Test
  public void test7x7PPMCompleteMathSpecificLayerApplyPhotoEdit() {
    IImage i7X7PPMCopy = new Image(i7x7ppm.getPixel2dArray(), i7x7ppm.getFileName());
    layeredImageEditorModel.addImage("hello", i7x7ppm);
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7X7PPMCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7X7PPMCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7X7PPMCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7X7PPMCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  //blur edit applied twice on the same specific layer
  @Test
  public void testApplyingSameFilterTwiceSpecificLayerApplyPhotoEdit() {

    IImage board = new CheckerBoardImageGenerator(1, 5,
        new Pixel(0, 0, 0),
        new Pixel(255, 200, 0)).generateImage("board");
    layeredImageEditorModel.addImage("hello", board);
    IImage boardCopy = new Image(board.getPixel2dArray(), board.getFileName());

    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);
    layeredImageEditorModel.addImage("blurred", blurred);
    IImage realBlurred = layeredImageEditorModel.applyPhotoEdit("blurred", blurEdit);

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (boardCopy.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(2, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(3, 2).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(3, 3).getBlue() * 0.25F)
                + (boardCopy.getPixelAt(3, 4).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (boardCopy.getPixelAt(4, 3).getBlue() * 0.125F)
                + (boardCopy.getPixelAt(4, 4).getBlue() * 0.0625F)));

    assertEquals(realBlurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (blurred.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(2, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (blurred.getPixelAt(3, 2).getGreen() * 0.125F)
                + (blurred.getPixelAt(3, 3).getGreen() * 0.25F)
                + (blurred.getPixelAt(3, 4).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (blurred.getPixelAt(4, 3).getGreen() * 0.125F)
                + (blurred.getPixelAt(4, 4).getGreen() * 0.0625F)));

  }

  //edit when the specific layer is at the top of the layered image
  @Test
  public void testApplyingPhotoEditToSpecificLayerWhenTopOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.addImage("same", i1x2);
    layeredImageEditorModel.setCurrentLayer("hi");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hi", blurEdit);

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //edit when the specific layer is at the middle of the layered image
  @Test
  public void testApplyingPhotoEditToSpecificLayerWhenMiddleOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.addImage("bottom", i1x2);
    layeredImageEditorModel.setCurrentLayer("hello");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //edit when the specific layer is at the bottom of the layered image
  @Test
  public void testApplyingPhotoEditToSpecificLayerWhenBottomOfImage() {

    layeredImageEditorModel.addImage("hi", i1x2);
    layeredImageEditorModel.addImage("hello", i1x2);
    layeredImageEditorModel.setCurrentLayer("hello");

    IImage i1X2Copy = new Image(i1x2.getPixel2dArray(), i1x2.getFileName());
    IImage blurred = layeredImageEditorModel.applyPhotoEdit("hello", blurEdit);

    assertEquals(i1X2Copy.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1X2Copy.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1X2Copy.getPixelAt(1, 0).getGreen() * 0.25F));

  }

  //test for getImage()


  //trying to get the image of a layer but there are no layers in the model
  @Test(expected = IllegalArgumentException.class)
  public void getImageNoLayers() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    layeredImageEditorModel.getImage("Hello");
  }

  //trying to get the image of a layer but there layer name you provide is null
  @Test(expected = IllegalArgumentException.class)
  public void getImageNullLayerName() {
    layeredImageEditorModel.getImage(null);
  }

  //trying to get the image of a layer but there layer name you provide is empty
  @Test(expected = IllegalArgumentException.class)
  public void getImageEmptyLayerName() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.getImage("");
  }

  //trying to get the image of a layer but there layer name you provide does not exist
  @Test(expected = IllegalArgumentException.class)
  public void getImageNonExistentLayerName() {
    layeredImageEditorModel.addImage("Top",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.getImage("Yo");
  }


  //trying to get the image of the layer when the layer is at the top
  @Test
  public void getImageTopLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getImage("Hello"));
  }

  //trying to get the image of the  layer when the layer is in the middle
  @Test
  public void getImageMiddleLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Middle");
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getImage("Middle"));
  }

  //trying to get the image of the layer when the layer is at the bottom
  @Test
  public void getLayerImageBottomLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Middle",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Bottom",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.setCurrentLayer("Bottom");
    assertEquals(new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
        new Pixel(3, 3, 4))))), "hello"),
        layeredImageEditorModel.getImage("Bottom"));
  }


  //trying to get the image of thelayer but the image is null/blank
  @Test
  public void getImageLayerHasNullImage() {
    layeredImageEditorModel.addImage("Hello", null);
    assertNull(layeredImageEditorModel.getCurrentLayerImage());
  }

  //trying to get the image of the layer and the image is programmatic (checkerboard)
  @Test
  public void getLayerImageProgrammatic() {
    layeredImageEditorModel.addImage("Hello",
        checkerBoardGenerate1.generateImage("hello"));
    assertEquals(new CheckerBoardImageGenerator(2,
            10,
            new Pixel(0, 0, 0),
            new Pixel(255, 255, 255)).generateImage("hello"),
        layeredImageEditorModel.getImage("Hello"));
  }

  //trying to get the image of the layer but the image was imported in to the model
  @Test
  public void getLayerImageImported() {
    layeredImageEditorModel.addImage("Hello", i7x7ppm);
    assertEquals(new Image(i7x7ppm.getPixel2dArray(), i7x7ppm.getFileName()),
        layeredImageEditorModel.getImage("Hello"));
  }

  //tests for getNumPhotos


  //trying to get the number of photos in a layered image when there are no layers
  @Test
  public void testGetNumPhotosNoLayers() {
    assertTrue(layeredImageEditorModel.getLayers().size() == 0);
    assertTrue(layeredImageEditorModel.getNumPhotos() == 0);
  }

  //trying to get the number of photos in a layered image when there is one layer
  @Test
  public void testGetNumPhotosOneLayer() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertTrue(layeredImageEditorModel.getNumPhotos() == 1);
  }


  //trying to get the number of photos in a layered image when there are multiple layers
  @Test
  public void testGetNumPhotosMultipleLayers() {
    layeredImageEditorModel.addImage("Hello",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("Hi",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertTrue(layeredImageEditorModel.getNumPhotos() == 3);
  }

  //tests getPhotoIds


  //tests when you want to get all of the layer names but there are no layers in the model
  @Test
  public void testGetLayerIdsNoLayers() {
    Set<String> set = new HashSet<>();
    assertEquals(set, layeredImageEditorModel.getPhotoIds());
  }

  //tests when you want to get all of the layer names but there is only one layer in the model
  @Test
  public void testGetLayerIdsOneLayers() {
    Set<String> set = new HashSet<>();
    set.add("yo");
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(set, layeredImageEditorModel.getPhotoIds());
  }

  //tests when you want to get all of the layer names but there are multiple layers in the model
  @Test
  public void testGetLayerIdsMultipleLayers() {
    Set<String> set = new HashSet<>();
    set.add("yo");
    set.add("second");
    set.add("third");
    layeredImageEditorModel.addImage("yo",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("second",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    layeredImageEditorModel.addImage("third",
        new Image(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(new Pixel(3,
            3, 4))))), "hello"));
    assertEquals(set, layeredImageEditorModel.getPhotoIds());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testLoadEntireLayeredImageNull() {
    layeredImageEditorModel.loadEntireLayeredImage(null);
  }

  @Test
  public void testLoadEntireLayeredImage() {
    assertEquals(layeredImageEditorModel.getNumPhotos(), 0);
    layeredImageEditorModel.loadEntireLayeredImage(
        "./res/savedlayerimagetest/imageLocations.txt");
    assertEquals(layeredImageEditorModel.getNumPhotos(), 4);
    assertEquals(layeredImageEditorModel.getImage("layer1"), null);
    assertEquals(layeredImageEditorModel.getImage("layer2"), null);
    assertEquals(layeredImageEditorModel.getImage("layer3"), null);

    assertEquals(layeredImageEditorModel.getImage("layer3Copy").getPixel2dArray(),
        new PNGImportManager("./res/savedlayerimagetest/layer3Copy.png")
            .apply().getPixel2dArray());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadEntireLayeredImageInvalidPhotoFilenameInTheTextFile() {
    layeredImageEditorModel.loadEntireLayeredImage(
        "./res/savedlayerimagetest2/imageLocations.txt");
  }

  @Test
  public void testLoadEntireLayeredRemovesExistingLayers() {
    layeredImageEditorModel.addImage("hi", new PNGImportManager(
        "./res/savedlayerimagetest/layer3Copy.png").apply());
    assertEquals(layeredImageEditorModel.getNumPhotos(), 1);
    layeredImageEditorModel.loadEntireLayeredImage(
        "./res/savedlayerimagetest/imageLocations.txt");
    assertEquals(layeredImageEditorModel.getNumPhotos(), 4);
    assertEquals(layeredImageEditorModel.getImage("layer1"), null);
    assertEquals(layeredImageEditorModel.getImage("layer2"), null);
    assertEquals(layeredImageEditorModel.getImage("layer3"), null);

    assertEquals(layeredImageEditorModel.getImage("layer3Copy").getPixel2dArray(),
        new PNGImportManager("./res/savedlayerimagetest/layer3Copy.png")
            .apply().getPixel2dArray());

  }

}