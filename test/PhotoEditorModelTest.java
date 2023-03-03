import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import hw05.IPhotoEditorModel;
import hw05.PhotoEditorModel;
import hw05.exportimages.IImageExportManager;
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
import hw05.importimages.PPMImportManager;
import hw05.pixel.IPixel;
import hw05.pixel.Pixel;
import java.awt.Color;
import java.io.ByteArrayInputStream;
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
 * Tests class for the PhotoEditorModel class: encompassed are tests that show that the PhotoEditor
 * Model has the correct functionality, meaning it can import, export, store, and filter images
 * appropriately.
 */
public class PhotoEditorModelTest {

  private final IImage fileTest3 = new RainbowImageGenerator(5, 5,
      30).generateImage("hi.ppm");
  private IOManager importManager;
  private final IOManager fileTest1 = new PPMImportManager("./res/fileTest1.ppm");
  private final IOManager fileTest2 = new PPMImportManager("./res/fileTest2.ppm");

  private final IImageGenerator checkerBoardGenerate1 = new CheckerBoardImageGenerator(2,
      10, new Pixel(0,0,0), new Pixel(255,255,255));
  private final IImageGenerator checkerBoardGenerate2 = new CheckerBoardImageGenerator(3,
      20, new Pixel(0,0,0), new Pixel(255, 200, 0));

  private final IImage i1x1 = new RainbowImageGenerator(1, 1,
      30).generateImage("1x1.ppm");
  private final IImage i1x2 = new RainbowImageGenerator(1, 2,
      30).generateImage("1x2.ppm");
  private final IImage i2x2 = new RainbowImageGenerator(2, 2,
      30).generateImage("2x2.ppm");
  private final IImage i3x3 = new RainbowImageGenerator(3, 3,
      30).generateImage("3x3.ppm");
  private final IImage i7x7 = new RainbowImageGenerator(7, 7,
      100).generateImage("10x7.ppm");
  private final IImage i7x7ppm = new PPMImportManager("./res/i7x7.ppm").apply();

  private IPhotoEditorModel photoEditorModel1;
  private List<List<IPixel>> pixelList;
  private List<List<IPixel>> emptyPixelList;
  private List<List<IPixel>> invalidDimensionsPixelList;

  private final IPhotoEdit compositeEdit = new CompositePhotoEdit(new BlurFilter(),
      new SepiaTransformation());
  private final IPhotoEdit blurEdit = new BlurFilter();
  private final IPhotoEdit sharpenEdit = new SharpenFilter();
  private final IPhotoEdit sepiaEdit = new SepiaTransformation();
  private final IPhotoEdit greyEdit = new GreyScaleTransformation();


  @Before
  public void setup() {
    photoEditorModel1 = new PhotoEditorModel();
    pixelList = new ArrayList<>();
    pixelList.add(new ArrayList<>(Arrays.asList(new Pixel(4, 5, 6),
        new Pixel(6, 7, 8))));
    emptyPixelList = new ArrayList<>();
    invalidDimensionsPixelList = new ArrayList<>();
    invalidDimensionsPixelList
        .add(new ArrayList<>(Arrays.asList(new Pixel(3, 4, 5), new Pixel(1,
            2, 4))));
    invalidDimensionsPixelList.add(new ArrayList<>(Arrays.asList(new Pixel(3, 5,
        6))));
  }

  //tests the no parameter constructor of the PhotoEditorModel and shows that a map is created
  @Test
  public void testSingleConstructorMapCreation() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    assertTrue(photoEditorModel1.getPhotoIds().size() == 0);
  }

  //test the single parameter constructor with a null array of Managers
  @Test(expected = IllegalArgumentException.class)
  public void testSingleParameterConstructorNullManagerArguments() {
    IPhotoEditorModel nullSingleParameterPhotoEditorModel = new PhotoEditorModel(null);
  }

  //tests the single parameter constructor and you pass in one valid IOManager
  @Test
  public void testSingleParameterConstructorSingleValidManagerArgument() {
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(fileTest1);
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 1);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().size() == 1);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest1.ppm"));
  }

  //tests the single parameter constructor and you pass in multiple valid IOManager
  @Test
  public void testSingleParameterConstructorMultipleValidManagerArgument() {
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(fileTest1, fileTest2);
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().size() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest1.ppm"));
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest2.ppm"));
  }

  //tests the single parameter constructor and you pass in some valid IOManagers, but one is null
  @Test(expected = IllegalArgumentException.class)
  public void testSingleParameterConstructorMultipleManagerArgumentOneIsNull() {
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(fileTest1,
        null);
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().size() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest1.ppm"));
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest2.ppm"));
  }

  //tests the single parameter constructor and you pass in some valid IOManagers, but the file is
  //does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testSingleParameterConstructorMultipleManagerArgumentFileDoesNotExist() {
    IOManager fileNonExistent = new PPMImportManager("blahfile.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(fileNonExistent);
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().size() == 2);
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest1.ppm"));
    assertTrue(singleParameterPhotoEditorModel.getPhotoIds().contains("./res/fileTest2.ppm"));
  }

  //tests the single parameter constructor and you pass in an IOManager with a null file Name
  @Test(expected = IllegalArgumentException.class)
  public void testSingleParameterConstructorIOManagerNullFileName() {
    importManager = new PPMImportManager(null);
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with an empty file Name
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyFileName() {
    importManager = new PPMImportManager("");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a validIOManager
  @Test
  public void testImport() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 1);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //does not start with P3
  @Test(expected = IllegalArgumentException.class)
  public void testMalformedFileImportNonP3() {
    importManager = new PPMImportManager("./res/malformed.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testImportNonExistantFile() {
    importManager = new PPMImportManager("hello??");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //that exists but is not PPM
  @Test(expected = IllegalArgumentException.class)
  public void testImportExistingFileWrongFormat() {
    importManager = new PPMImportManager("./res/malformed.txt");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //name that is too short
  @Test(expected = IllegalArgumentException.class)
  public void testShortName() {
    importManager = new PPMImportManager("xt");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //name that has the valid format but has an amount of pixels that is not a multiple of 3
  @Test(expected = IllegalArgumentException.class)
  public void testMalformedFileValuesNotMultOf3() {
    importManager = new PPMImportManager("./res/malformedNotMult3.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in a invalid IOManger with a file that
  //name that has the valid format but has an amount of pixels that does not have valid dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testMalformedFileImageDimensionsDontMatch() {
    importManager = new PPMImportManager("./res/malformedBadDimensions.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in two IOManagers that are importing
  //the same exact photo file
  @Test(expected = IllegalArgumentException.class)
  public void testImportingSameFileTwiceThrows() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager,
        importManager);

  }

  //tests the single parameter constructor and you pass in an IOManager that imports an empty
  //image
  @Test(expected = IllegalArgumentException.class)
  public void testImportingEmptyImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "0 0\n"
            + "255").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that imports
  //only one pixel image
  @Test
  public void testImporting1PixelImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelHeight(),
        1);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelWidth(),
        1);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        0),
        new Pixel(0, 10, 130));
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 1);
  }

  //tests the single parameter constructor and you pass in an IOManager that has the invalid
  //max pixel limit in the file
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMaxColorValue() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "256\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that is a completely
  //empty file
  @Test(expected = IllegalArgumentException.class)
  public void testImportingEmpty() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that does not have
  //the capital P in P3
  @Test(expected = IllegalArgumentException.class)
  public void testImportingNonP3() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("p3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that has an actual word
  //instead of P in the P3 location of the file
  @Test(expected = IllegalArgumentException.class)
  public void testImportingNonP31() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("hello3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that says it has
  //invalid dimensions in the dimension specifier part of the file
  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelDimensionDontMatch() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 2\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager that says it has
  //invalid dimensions in the dimension specifier part of the file again
  @Test(expected = IllegalArgumentException.class)
  public void testImporting1PixelWrongDimension2() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "2 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with a valid
  //3X2 file
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelWidth(),
        3);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelHeight(),
        2);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        0),
        new Pixel(0, 30, 30));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        1),
        new Pixel(0, 60, 60));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        2),
        new Pixel(0, 90, 90));

    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        0),
        new Pixel(0, 120, 120));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        1),
        new Pixel(30, 0, 30));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        2),
        new Pixel(30, 30, 40));
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 1);
  }

  //tests the single parameter constructor and you pass in an IOManager with a file that holds
  //pixel values that are not a multiple of 3
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with a file that holds
  //pixel values that are not numbers
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with a valid file
  //that at the end of the list of pixels allows random characters
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelWidth(),
        3);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelHeight(),
        2);
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        0),
        new Pixel(0, 30, 30));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        1),
        new Pixel(0, 60, 60));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(0,
        2),
        new Pixel(0, 90, 90));

    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        0),
        new Pixel(0, 120, 120));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        1),
        new Pixel(30, 0, 30));
    assertEquals(singleParameterPhotoEditorModel.getImage("file.ppm").getPixelAt(1,
        2),
        new Pixel(30, 30, 40));
    assertTrue(singleParameterPhotoEditorModel.getNumPhotos() == 1);
  }

  //tests the single parameter constructor and you pass in an IOManager with an imput
  //stream that is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullInputStream() {
    importManager = new PPMImportManager("file.ppm",
        null);
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with a null fileName
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //tests the single parameter constructor and you pass in an IOManager with a fileName
  //that is not ppm
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
    IPhotoEditorModel singleParameterPhotoEditorModel = new PhotoEditorModel(importManager);
  }

  //apply photoEdits test

  //tests when you apply a null photoEdit
  @Test(expected = IllegalArgumentException.class)
  public void testNullPhotoEdit() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.applyPhotoEdit("smallImage.ppm", null);
  }

  //tests when you apply a photoEdit but the photoId does not exist in the model
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditIDNotInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.applyPhotoEdit("hello.ppm", new BlurFilter());
  }

  //tests when you apply a photoEdit but the photoId is null
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditIDIsNull() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.applyPhotoEdit(null, new BlurFilter());
  }

  //tests when you apply a photoEdit but the photoId is empty
  @Test(expected = IllegalArgumentException.class)
  public void testPhotoEditIDIsEmpty() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.applyPhotoEdit("", new BlurFilter());
  }

  //tests when you apply a blur photoEdit to a programmed image
  @Test
  public void testPhotoEditBlurFilterToProgramGeneratedImage() {
    photoEditorModel1.addImage("smallImage.ppm", i2x2);
    IImage blurredImage = photoEditorModel1.applyPhotoEdit("smallImage.ppm",
        new BlurFilter());

    assertEquals(blurredImage.getPixelAt(1, 1).getRed(),
        Math.round(
            (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0, 0).getRed()
                * 0.0625F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                0).getRed() * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
                1).getRed() * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                1).getRed()
                * 0.25F)));

    assertEquals(blurredImage.getPixelAt(1, 1).getGreen(),
        Math.round(
            (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0, 0).getGreen()
                * 0.0625F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                0).getGreen()
                * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
                1).getGreen()
                * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                1).getGreen()
                * 0.25F)));

    assertEquals(blurredImage.getPixelAt(1, 1).getBlue(),
        Math.round(
            (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0, 0).getBlue()
                * 0.0625F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                0).getBlue() * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
                1).getBlue() * 0.125F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                1).getBlue()
                * 0.25F)));
  }

  //test when you apply a sharpen photo edit to a programmed image
  @Test
  public void testPhotoEditSharpenFilterToProgramGeneratedImage() {
    photoEditorModel1.addImage("smallImage.ppm", i2x2);
    IImage sharpenedImage = photoEditorModel1.applyPhotoEdit("smallImage.ppm",
        new SharpenFilter());

    assertEquals(sharpenedImage.getPixelAt(1, 1).getRed(),
        Math.round((photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
            0).getRed() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
            0).getRed() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
            1).getRed() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
            1).getRed() * 1F)));

    assertEquals(sharpenedImage.getPixelAt(1, 1).getGreen(),
        Math.round(
            (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
                0).getGreen() * 0.25F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                0).getGreen() * 0.25F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
                1).getGreen() * 0.25F)
                + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
                1).getGreen() * 1F)));

    assertEquals(sharpenedImage.getPixelAt(1, 1).getBlue(),
        Math.round((photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
            0).getBlue() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
            0).getBlue() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(0,
            1).getBlue() * 0.25F)
            + (photoEditorModel1.getImage("smallImage.ppm").getPixelAt(1,
            1).getBlue() * 1F)));
  }

  //test when you apply a sepia color transformation to a programmed image
  @Test
  public void testPhotoEditSepiaColorTransformationProgrammedImage() {
    photoEditorModel1.addImage("smallImage.ppm", i7x7);
    IImage sepiaImage = photoEditorModel1
        .applyPhotoEdit("smallImage.ppm", new SepiaTransformation());

    for (int i = 0; i < sepiaImage.getPixelHeight(); i++) {
      for (int j = 0; j < sepiaImage.getPixelWidth(); j++) {
        if (sepiaImage.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepiaImage.getPixelAt(i, j).getRed(),
              Math.round(
                  photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed()
                      * 0.393F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                      * 0.769F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                      * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(
                  photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed()
                      * 0.393F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                      * 0.769F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                      * 0.189F)));
        }

        if (sepiaImage.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepiaImage.getPixelAt(i, j).getGreen(),
              Math.round(
                  photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed()
                      * 0.349F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                      * 0.686F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                      * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(
                  photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed()
                      * 0.393F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                      * 0.769F
                      + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                      * 0.189F)));
        }

        assertEquals(sepiaImage.getPixelAt(i, j).getBlue(),
            Math.round(
                photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed()
                    * 0.272F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                    * 0.534F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                    * 0.131F));
      }
    }
  }


  //test when you apply a grey scale color transformation to a programmed image
  @Test
  public void testPhotoEditGreyScaleColorTransformationProgrammedImage() {
    photoEditorModel1.addImage("smallImage.ppm", i7x7);
    IImage grayImage = photoEditorModel1
        .applyPhotoEdit("smallImage.ppm", new GreyScaleTransformation());
    for (int i = 0; i < grayImage.getPixelHeight(); i++) {
      for (int j = 0; j < grayImage.getPixelWidth(); j++) {
        assertEquals(grayImage.getPixelAt(i, j).getRed(),
            Math.round(
                photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed() * 0.2126F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getGreen(),
            Math.round(
                photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed() * 0.2126F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                    * 0.0722F));

        assertEquals(grayImage.getPixelAt(i, j).getBlue(),
            Math.round(
                photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getRed() * 0.2126F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getGreen()
                    * 0.7152F
                    + photoEditorModel1.getImage("smallImage.ppm").getPixelAt(i, j).getBlue()
                    * 0.0722F));
      }
    }
  }


  //tests when you apply a composite transformation on a programmed image
  @Test
  public void testPhotoEditCompositeColorTransformationProgrammedImage() {
    photoEditorModel1.addImage("smallImage.ppm", i7x7);
    IImage compositeImage = photoEditorModel1
        .applyPhotoEdit("smallImage.ppm", new CompositePhotoEdit(
            new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
            new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7))));

    assertEquals(compositeImage, check);
  }

  //tests when you apply a composite transformation on an imported image

  //tests when a composite edit is applied on a 7X7 programmatic image
  @Test
  public void testOperationsAppliedComposite() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage composite = photoEditorModel1.applyPhotoEdit("hello", compositeEdit);
    IImage check = new SepiaTransformation().apply(new BlurFilter().apply(i7x7));

    assertEquals(composite, check);
  }

  //tests when you apply multiple chained composite programmatic image transformations
  @Test
  public void testMultipleChainedComposite() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage composite = photoEditorModel1.applyPhotoEdit("hello",
        new CompositePhotoEdit(new CompositePhotoEdit(new GreyScaleTransformation(),
            new SepiaTransformation()),
            new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7))));

    assertEquals(composite, check);
  }

  //tests when you chained multiplePPM imported images
  @Test
  public void testMultipleChainedPPMComposite() {
    photoEditorModel1.addImage("hello", i7x7ppm);
    IImage composite = photoEditorModel1.applyPhotoEdit("hello",
        new CompositePhotoEdit(
            new CompositePhotoEdit(new GreyScaleTransformation(), new SepiaTransformation()),
            new CompositePhotoEdit(new SharpenFilter(), new BlurFilter())));

    IImage check = new BlurFilter().apply(
        new SharpenFilter().apply(
            new SepiaTransformation().apply(
                new GreyScaleTransformation().apply(i7x7ppm))));

    assertEquals(composite, check);
  }

  //tests when you use the same filter twice on an image
  @Test
  public void testSameFilterTwiceComposite() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage composite = photoEditorModel1.applyPhotoEdit("hello",
        new CompositePhotoEdit(new BlurFilter(), new BlurFilter()));

    IImage check = new BlurFilter().apply(
        new BlurFilter().apply(i7x7));

    assertEquals(composite, check);
  }

  //all blur filter tests here

  //tests when you use the blur filter on a single PixelImage
  @Test
  public void testSinglePixelImageBlurred() {
    IImage img1 = new CheckerBoardImageGenerator(1, 1,
        new Pixel(0,0,0),
        new Pixel(255, 200, 0)).generateImage("1x1");
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1,
            1, new Pixel(0,0,0),
            new Pixel(255, 200, 0)).generateImage("1x1"));
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(img1.getPixelAt(0, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(img1.getPixelAt(0, 0).getGreen() * 0.25F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(img1.getPixelAt(0, 0).getBlue() * 0.25F));
  }

  //tests when you use the blur filter on a 1X2Image
  @Test
  public void test1x2Blurred() {
    photoEditorModel1.addImage("hello", i1x2);

    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);

    assertEquals(i1x2.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(blurred.getPixelAt(0, 0).getRed(),
        Math.round(i1x2.getPixelAt(1, 0).getRed() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getBlue(),
        Math.round(i1x2.getPixelAt(1, 0).getBlue() * 0.125F));
    assertEquals(blurred.getPixelAt(0, 0).getGreen(),
        Math.round(i1x2.getPixelAt(1, 0).getGreen() * 0.125F));

    assertEquals(blurred.getPixelAt(1, 0).getRed(),
        Math.round(i1x2.getPixelAt(1, 0).getRed() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getBlue(),
        Math.round(i1x2.getPixelAt(1, 0).getBlue() * 0.25F));
    assertEquals(blurred.getPixelAt(1, 0).getGreen(),
        Math.round(i1x2.getPixelAt(1, 0).getGreen() * 0.25F));
  }

  //testing that the pixels off the board are not taken into account
  // by the calculation of this filter
  @Test
  public void test2x2CornerMathBlurred() {
    photoEditorModel1.addImage("hello", i2x2);
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);

    assertEquals(blurred.getPixelAt(1, 1).getRed(),
        Math.round((i2x2.getPixelAt(0, 0).getRed() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getRed() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getRed() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getRed() * 0.25F)));

    assertEquals(blurred.getPixelAt(1, 1).getGreen(),
        Math.round((i2x2.getPixelAt(0, 0).getGreen() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getGreen() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getGreen() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getGreen() * 0.25F)));

    assertEquals(blurred.getPixelAt(1, 1).getBlue(),
        Math.round((i2x2.getPixelAt(0, 0).getBlue() * 0.0625F)
            + (i2x2.getPixelAt(1, 0).getBlue() * 0.125F)
            + (i2x2.getPixelAt(0, 1).getBlue() * 0.125F)
            + (i2x2.getPixelAt(1, 1).getBlue() * 0.25F)));
  }

  //tests when you blur a 3X3 filter and make sure that the edge cases are handled properly
  @Test
  public void test3x3EdgeMathBlurred() {
    photoEditorModel1.addImage("hello", i3x3);
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(2, 1).getRed(),
        Math.round((i3x3.getPixelAt(1, 0).getRed() * 0.0625F)
            + (i3x3.getPixelAt(1, 1).getRed() * 0.125F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.0625F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.125F)));
  }

  //tests to see that all the pixels are changed properly when an image is blurred
  @Test
  public void testAppliedToAllPixels2x2Blurred() {
    photoEditorModel1.addImage("hello", i2x2);
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(0, 0), new Pixel(6, 6, 11));
    assertEquals(blurred.getPixelAt(0, 1), new Pixel(6, 11, 17));
    assertEquals(blurred.getPixelAt(1, 0), new Pixel(11, 6, 17));
    assertEquals(blurred.getPixelAt(1, 1), new Pixel(11, 11, 23));
  }

  //tests to see that the math is correct in a blur filter when applied to an image
  @Test
  public void test7x7CompleteMathBlurred() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7x7.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7x7.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7x7.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7x7.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7x7.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7x7.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  //tests when a PPM imported image is blurred correctly
  @Test
  public void test7x7PPMCompleteMath() {
    photoEditorModel1.addImage("hello", i7x7ppm);
    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    assertEquals(blurred.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getRed() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getRed() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getRed() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getGreen() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getGreen() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getGreen() * 0.0625F)));

    assertEquals(blurred.getPixelAt(3, 3).getBlue(),
        Math.round(
            (i7x7ppm.getPixelAt(2, 2).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(2, 3).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(2, 4).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(3, 2).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(3, 3).getBlue() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 4).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getBlue() * 0.0625F)
                + (i7x7ppm.getPixelAt(4, 3).getBlue() * 0.125F)
                + (i7x7ppm.getPixelAt(4, 4).getBlue() * 0.0625F)));
  }

  //tests when a checkerboard is blurred correctly
  @Test
  public void testCheckerBoardCompleteMathBlurred() {
    IImage board = new CheckerBoardImageGenerator(3,
        4, new Pixel(0,0,0),
        new Pixel(255, 200, 0)).generateImage("board");

    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(
            3, 4, new Pixel(0,0,0),
            new Pixel(255, 200, 0)).generateImage("board"));

    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
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

  //tests when a blur filter is applied twice on the same image
  @Test
  public void testApplyingSameFilterTwiceBlurred() {
    IImage board = new CheckerBoardImageGenerator(1, 5,
        new Pixel(0,0,0),
        new Pixel(255, 200, 0)).generateImage("board");
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1, 5, new Pixel(0,0,0),
            new Pixel(255, 200, 0)).generateImage("board"));

    IImage blurred = photoEditorModel1.applyPhotoEdit("hello", blurEdit);
    photoEditorModel1.addImage("hi", blurred);
    IImage doubleBlurred = photoEditorModel1.applyPhotoEdit("hi", new BlurFilter());

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

    assertEquals(doubleBlurred.getPixelAt(3, 3).getGreen(),
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

  //all sharpen filter tests here


  //tests when a singlePixel image is sharpened
  @Test
  public void testSinglePixelImageSharpen() {
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1, 1, Color.CYAN,
            Color.CYAN).generateImage("1x1"));
    IImage img1 = new CheckerBoardImageGenerator(1, 1, Color.CYAN,
        Color.CYAN).generateImage("1x1");
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(0, 0).getRed(),
        Math.round(img1.getPixelAt(0, 0).getRed() * 1F));
    assertEquals(sharpened.getPixelAt(0, 0).getGreen(),
        Math.round(img1.getPixelAt(0, 0).getGreen() * 1F));
    assertEquals(sharpened.getPixelAt(0, 0).getBlue(),
        Math.round(img1.getPixelAt(0, 0).getBlue() * 1F));
  }

  //tests when a 1X2 image is sharpened
  @Test
  public void test1x2Sharpen() {
    photoEditorModel1.addImage("hello", i1x2);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);

    assertEquals(i1x2.getPixelAt(0, 0).getRed(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getGreen(), 0);
    assertEquals(i1x2.getPixelAt(0, 0).getBlue(), 0);

    assertEquals(sharpened.getPixelAt(0, 0).getRed(),
        Math.round((i1x2.getPixelAt(1, 0).getRed() * 0.25F)));
    assertEquals(sharpened.getPixelAt(0, 0).getBlue(),
        Math.round((i1x2.getPixelAt(1, 0).getBlue() * 0.25F)));
    assertEquals(sharpened.getPixelAt(0, 0).getGreen(),
        Math.round((i1x2.getPixelAt(1, 0).getGreen() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 0).getRed(),
        (i1x2.getPixelAt(1, 0).getRed()));
    assertEquals(sharpened.getPixelAt(1, 0).getBlue(),
        (i1x2.getPixelAt(1, 0).getBlue()));
    assertEquals(sharpened.getPixelAt(1, 0).getGreen(),
        (i1x2.getPixelAt(1, 0).getGreen()));
  }

  //tests the math is handled correctly on the corners of a sharpen filter
  @Test
  public void test2x2CornerMathSharpen() {
    photoEditorModel1.addImage("hello", i2x2);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(1, 1).getRed(),
        Math.round((i2x2.getPixelAt(0, 0).getRed() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getRed() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getRed() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getRed() * 1F)));

    assertEquals(sharpened.getPixelAt(1, 1).getGreen(),
        Math.round((i2x2.getPixelAt(0, 0).getGreen() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getGreen() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getGreen() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getGreen() * 1F)));

    assertEquals(sharpened.getPixelAt(1, 1).getBlue(),
        Math.round((i2x2.getPixelAt(0, 0).getBlue() * 0.25F)
            + (i2x2.getPixelAt(1, 0).getBlue() * 0.25F)
            + (i2x2.getPixelAt(0, 1).getBlue() * 0.25F)
            + (i2x2.getPixelAt(1, 1).getBlue() * 1F)));
  }

  //tests the corner math for a 3X3 image sharpen filer
  @Test
  public void test3x3CornerMathSharpen() {
    photoEditorModel1.addImage("hello", i3x3);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(2, 2).getRed(),
        Math.round((i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 1F)));
  }

  //tests the edge math for a 3X3 image sharpen filter
  @Test
  public void test3x3EdgeMathSharpen() {
    photoEditorModel1.addImage("hello", i3x3);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(2, 1).getRed(),
        Math.round((i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 1F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.25F)));
  }

  //tests the center math for a sharpened 3x3 image
  @Test
  public void test3x3MathCenterSharpen() {
    photoEditorModel1.addImage("hello", i3x3);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(1, 1).getRed(),
        Math.round((i3x3.getPixelAt(0, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getRed() * 1F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 1).getGreen(),
        Math.round((i3x3.getPixelAt(0, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getGreen() * 1F)
            + (i3x3.getPixelAt(1, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getGreen() * 0.25F)));

    assertEquals(sharpened.getPixelAt(1, 1).getBlue(),
        Math.round((i3x3.getPixelAt(0, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(0, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(0, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 1).getBlue() * 1F)
            + (i3x3.getPixelAt(1, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getBlue() * 0.25F)));
  }

  //test for the the corner of an image when it is sharpened
  @Test
  public void test3x3MathCornerSharpen() {
    photoEditorModel1.addImage("hello", i3x3);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(2, 2).getRed(),
        Math.round((i3x3.getPixelAt(0, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getRed() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getRed() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getRed() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getRed() * 1F)));

    assertEquals(sharpened.getPixelAt(2, 2).getGreen(),
        Math.round((i3x3.getPixelAt(0, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getGreen() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getGreen() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getGreen() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getGreen() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getGreen() * 1F)));

    assertEquals(sharpened.getPixelAt(2, 2).getBlue(),
        Math.round((i3x3.getPixelAt(0, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(0, 1).getBlue() * -0.125F)
            + (i3x3.getPixelAt(0, 2).getBlue() * -0.125F)
            + (i3x3.getPixelAt(1, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(1, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(1, 2).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 0).getBlue() * -0.125F)
            + (i3x3.getPixelAt(2, 1).getBlue() * 0.25F)
            + (i3x3.getPixelAt(2, 2).getBlue() * 1F)));
  }

  //tests the complete math for a 7X7 image sharpened
  @Test
  public void test7x7CompleteMathSharpened() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getRed() * 1F)
                + (i7x7.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7x7.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 3).getGreen() * 1F)
                + (i7x7.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7x7.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7x7.getPixelAt(5, 5).getGreen() * -0.125F)));

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

  //tests when a PPM imported image is sharpened
  @Test
  public void test7x7CompleteMathPPMSharpened() {
    photoEditorModel1.addImage("hello", i7x7ppm);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    assertEquals(sharpened.getPixelAt(3, 3).getRed(),
        Math.round(
            (i7x7ppm.getPixelAt(1, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 2).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 3).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 4).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 3).getRed() * 1F)
                + (i7x7ppm.getPixelAt(3, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 3).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 4).getRed() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 5).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 1).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 2).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 3).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 4).getRed() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 5).getRed() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (i7x7ppm.getPixelAt(1, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 2).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 3).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 4).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(1, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(2, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(2, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(3, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 3).getGreen() * 1F)
                + (i7x7ppm.getPixelAt(3, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(3, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(4, 2).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 3).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 4).getGreen() * 0.25F)
                + (i7x7ppm.getPixelAt(4, 5).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 1).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 2).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 3).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 4).getGreen() * -0.125F)
                + (i7x7ppm.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(sharpened.getPixelAt(3, 3).getBlue(), 255);
    assertEquals(true, 255 < Math.round(
        (i7x7ppm.getPixelAt(1, 1).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(1, 2).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(1, 3).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(1, 4).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(1, 5).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(2, 1).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(2, 2).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(2, 3).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(2, 4).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(2, 5).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(3, 1).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(3, 2).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(3, 3).getBlue() * 1F)
            + (i7x7ppm.getPixelAt(3, 4).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(3, 5).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(4, 1).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(4, 2).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(4, 3).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(4, 4).getBlue() * 0.25F)
            + (i7x7ppm.getPixelAt(4, 5).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(5, 1).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(5, 2).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(5, 3).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(5, 4).getBlue() * -0.125F)
            + (i7x7ppm.getPixelAt(5, 5).getBlue() * -0.125F)));
  }


  //tests when a sharpen filter is applied twice to an image
  @Test
  public void testApplyingSameFilterTwiceSharpen() {
    IImage board = new CheckerBoardImageGenerator(1, 7, Color.DARK_GRAY,
        Color.GREEN)
        .generateImage("board");
    photoEditorModel1.addImage("hello", board);
    IImage sharpened = photoEditorModel1.applyPhotoEdit("hello", sharpenEdit);
    photoEditorModel1.addImage("hi", sharpened);
    IImage doubleSharp = photoEditorModel1.applyPhotoEdit("hi", sharpenEdit);

    assertEquals(sharpened.getPixelAt(3, 3).getGreen(),
        Math.round(
            (board.getPixelAt(1, 1).getGreen() * -0.125F)
                + (board.getPixelAt(1, 2).getGreen() * -0.125F)
                + (board.getPixelAt(1, 3).getGreen() * -0.125F)
                + (board.getPixelAt(1, 4).getGreen() * -0.125F)
                + (board.getPixelAt(1, 5).getGreen() * -0.125F)
                + (board.getPixelAt(2, 1).getGreen() * -0.125F)
                + (board.getPixelAt(2, 2).getGreen() * 0.25F)
                + (board.getPixelAt(2, 3).getGreen() * 0.25F)
                + (board.getPixelAt(2, 4).getGreen() * 0.25F)
                + (board.getPixelAt(2, 5).getGreen() * -0.125F)
                + (board.getPixelAt(3, 1).getGreen() * -0.125F)
                + (board.getPixelAt(3, 2).getGreen() * 0.25F)
                + (board.getPixelAt(3, 3).getGreen() * 1F)
                + (board.getPixelAt(3, 4).getGreen() * 0.25F)
                + (board.getPixelAt(3, 5).getGreen() * -0.125F)
                + (board.getPixelAt(4, 1).getGreen() * -0.125F)
                + (board.getPixelAt(4, 2).getGreen() * 0.25F)
                + (board.getPixelAt(4, 3).getGreen() * 0.25F)
                + (board.getPixelAt(4, 4).getGreen() * 0.25F)
                + (board.getPixelAt(4, 5).getGreen() * -0.125F)
                + (board.getPixelAt(5, 1).getGreen() * -0.125F)
                + (board.getPixelAt(5, 2).getGreen() * -0.125F)
                + (board.getPixelAt(5, 3).getGreen() * -0.125F)
                + (board.getPixelAt(5, 4).getGreen() * -0.125F)
                + (board.getPixelAt(5, 5).getGreen() * -0.125F)));

    assertEquals(doubleSharp.getPixelAt(3, 3).getGreen(),
        Math.round(
            (sharpened.getPixelAt(1, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 2).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 3).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 4).getGreen() * -0.125F)
                + (sharpened.getPixelAt(1, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(2, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(2, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 3).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(2, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(3, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(3, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(3, 3).getGreen() * 1F)
                + (sharpened.getPixelAt(3, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(3, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(4, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(4, 2).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 3).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 4).getGreen() * 0.25F)
                + (sharpened.getPixelAt(4, 5).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 1).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 2).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 3).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 4).getGreen() * -0.125F)
                + (sharpened.getPixelAt(5, 5).getGreen() * -0.125F)));
  }

  //all sepia tests here

  //tests when a single pixel image is hit with the sepia transformation
  @Test
  public void testSinglePixelImageSepia() {
    IImage img1 = new CheckerBoardImageGenerator(1, 1, Color.CYAN,
        Color.CYAN).generateImage("1x1");
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1, 1, Color.CYAN,
            Color.CYAN).generateImage("1x1"));
    IImage sepia = photoEditorModel1.applyPhotoEdit("hello", sepiaEdit);
    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(img1.getPixelAt(i, j).getRed() * 0.349F
                  + img1.getPixelAt(i, j).getGreen() * 0.686F
                  + img1.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(img1.getPixelAt(i, j).getRed() * 0.393F
                  + img1.getPixelAt(i, j).getGreen() * 0.769F
                  + img1.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.272F
                + img1.getPixelAt(i, j).getGreen() * 0.534F
                + img1.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //tests when a 7X7 image is hit with the sepia transformation
  @Test
  public void test7x7Sepia() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage sepia = photoEditorModel1.applyPhotoEdit("hello", sepiaEdit);

    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7x7.getPixelAt(i, j).getRed() * 0.349F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.686F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.272F
                + i7x7.getPixelAt(i, j).getGreen() * 0.534F
                + i7x7.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //tests when a PPM imported image is hit with a sepia transformation
  @Test
  public void test7x7PPMSepia() {
    photoEditorModel1.addImage("hello", i7x7ppm);
    IImage sepia = photoEditorModel1.applyPhotoEdit("hello", sepiaEdit);

    for (int i = 0; i < sepia.getPixelHeight(); i++) {
      for (int j = 0; j < sepia.getPixelWidth(); j++) {
        if (sepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getRed(),
              Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (sepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(sepia.getPixelAt(i, j).getGreen(),
              Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.349F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.686F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.393F
                  + i7x7ppm.getPixelAt(i, j).getGreen() * 0.769F
                  + i7x7ppm.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(sepia.getPixelAt(i, j).getBlue(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.272F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.534F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //tests when a sepia transformation is applied twice to the same image
  @Test
  public void testApplyingSameTransformationTwiceSepia() {
    IImage board = new CheckerBoardImageGenerator(1, 7, Color.DARK_GRAY,
        Color.GREEN)
        .generateImage("board");
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1, 7, Color.DARK_GRAY, Color.GREEN)
            .generateImage("board"));
    IImage sepia = photoEditorModel1.applyPhotoEdit("hello", sepiaEdit);
    photoEditorModel1.addImage("hi", sepia);
    IImage doubleSepia = photoEditorModel1.applyPhotoEdit("hi", sepiaEdit);

    for (int i = 0; i < doubleSepia.getPixelHeight(); i++) {
      for (int j = 0; j < doubleSepia.getPixelWidth(); j++) {
        if (doubleSepia.getPixelAt(i, j).getRed() != 255) {
          assertEquals(doubleSepia.getPixelAt(i, j).getRed(),
              Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F));
        } else {
          assertEquals(true,
              (255 <= Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        if (doubleSepia.getPixelAt(i, j).getGreen() != 255) {
          assertEquals(doubleSepia.getPixelAt(i, j).getGreen(),
              Math.round(sepia.getPixelAt(i, j).getRed() * 0.349F
                  + sepia.getPixelAt(i, j).getGreen() * 0.686F
                  + sepia.getPixelAt(i, j).getBlue() * 0.168F));
        } else {
          assertEquals(true,
              (255 <= Math.round(sepia.getPixelAt(i, j).getRed() * 0.393F
                  + sepia.getPixelAt(i, j).getGreen() * 0.769F
                  + sepia.getPixelAt(i, j).getBlue() * 0.189F)));
        }

        assertEquals(doubleSepia.getPixelAt(i, j).getBlue(),
            Math.round(sepia.getPixelAt(i, j).getRed() * 0.272F
                + sepia.getPixelAt(i, j).getGreen() * 0.534F
                + sepia.getPixelAt(i, j).getBlue() * 0.131F));
      }
    }
  }

  //all greyScale tests here

  //tests when a single pixel image is hit with a grey transformation
  @Test
  public void testSinglePixelImageGrey() {
    IImage img1 = new CheckerBoardImageGenerator(1,
        1, Color.CYAN, Color.CYAN).generateImage("1x1");
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1,
            1, Color.CYAN, Color.CYAN).generateImage("1x1"));
    IImage gray = photoEditorModel1.applyPhotoEdit("hello", greyEdit);
    for (int i = 0; i < gray.getPixelHeight(); i++) {
      for (int j = 0; j < gray.getPixelWidth(); j++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(img1.getPixelAt(i, j).getRed() * 0.2126F
                + img1.getPixelAt(i, j).getGreen() * 0.7152F
                + img1.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  //tests when a 7x7 image is hit with a grey transformation
  @Test
  public void testSinglePixel7xGrey() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage gray = photoEditorModel1.applyPhotoEdit("hello", greyEdit);
    assertEquals(gray.getPixelAt(4, 4).getRed(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));

    assertEquals(gray.getPixelAt(4, 4).getGreen(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));

    assertEquals(gray.getPixelAt(4, 4).getBlue(),
        Math.round(i7x7.getPixelAt(4, 4).getRed() * 0.2126F
            + i7x7.getPixelAt(4, 4).getGreen() * 0.7152F
            + i7x7.getPixelAt(4, 4).getBlue() * 0.0722F));
  }

  //test to see that all the pixel in an image are adjust correctly when a grey transformation
  //is applied
  @Test
  public void testAllPixelsGrey() {
    photoEditorModel1.addImage("hello", i7x7);
    IImage gray = photoEditorModel1.applyPhotoEdit("hello", greyEdit);
    for (int i = 0; i < gray.getPixelHeight(); i++) {
      for (int j = 0; j < gray.getPixelWidth(); j++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(i7x7.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  //tests when a PPM imported image is hit with a grey transformation
  @Test
  public void testAllPixelsPPMGrey() {
    photoEditorModel1.addImage("hello", i7x7ppm);
    IImage gray = photoEditorModel1.applyPhotoEdit("hello", greyEdit);
    for (int i = 0; i < gray.getPixelHeight(); i++) {
      for (int j = 0; j < gray.getPixelWidth(); j++) {
        assertEquals(gray.getPixelAt(i, j).getRed(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getGreen(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));

        assertEquals(gray.getPixelAt(i, j).getBlue(),
            Math.round(i7x7ppm.getPixelAt(i, j).getRed() * 0.2126F
                + i7x7ppm.getPixelAt(i, j).getGreen() * 0.7152F
                + i7x7ppm.getPixelAt(i, j).getBlue() * 0.0722F));
      }
    }
  }

  //test when an image is transformed twice by the grey transformation
  @Test
  public void testApplyingSameTransformationTwiceGrey() {
    photoEditorModel1.addImage("hello",
        new CheckerBoardImageGenerator(1, 7, Color.DARK_GRAY, Color.GREEN)
            .generateImage("board"));
    IImage gray = photoEditorModel1.applyPhotoEdit("hello", greyEdit);
    photoEditorModel1.addImage("hi", gray);
    IImage doubleGray = photoEditorModel1.applyPhotoEdit("hi", greyEdit);
    assertEquals(gray, doubleGray);
  }

  //addImage tests


  //tests when you want to add an image to the model but it has an empty String id
  @Test(expected = IllegalArgumentException.class)
  public void testAddImageButEmptyStringID() {
    photoEditorModel1.addImage("", new Image(pixelList, "hello"));
  }

  //tests when you want to add an image to the model but it is a null image
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullImage() {
    photoEditorModel1.addImage("Koala.ppm", null);
  }

  //tests when you want to add an image to the model but it has a null String Id name
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullStringIdName() {
    photoEditorModel1.addImage(null, new Image(pixelList, "Hello"));
  }

  //tests when you want to add an image to the model but the image you want to add has an empty
  //fileName
  @Test(expected = IllegalArgumentException.class)
  public void testAddNormalImageWithEmptyStringFileName() {
    photoEditorModel1.addImage("hi", new Image(pixelList, ""));
  }

  //tests when you want to add an image to the model but the image you want to add has a null file
  //name
  @Test(expected = IllegalArgumentException.class)
  public void testAddNormalImageWithNullStringFileName() {
    photoEditorModel1.addImage("hi", new Image(pixelList, null));
  }

  //tests when you want to add an image to the model but the image you want to add has a null pixel
  //list
  @Test(expected = IllegalArgumentException.class)
  public void testAddNormalImageWithNullPixelList() {
    photoEditorModel1.addImage("hi", new Image(null, "hi"));
  }

  //tests when you want to add an image to the model but the image you want to add has an empty
  //list of pixels
  @Test(expected = IllegalArgumentException.class)
  public void testAddNormalImageEmptyListOfPixels() {
    photoEditorModel1.addImage("hi", new Image(emptyPixelList, "hello"));
  }

  //tests when you want to add an image to the model but the image you want to add does not
  //have valid equal list of pixel dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testAddNormalImageInvalidDimensionsListOfPixels() {
    photoEditorModel1.addImage("hello", new Image(invalidDimensionsPixelList, "hi"));
  }

  //tests when you want to add an image to the model but an image with its id already exists in the
  //model
  @Test(expected = IllegalArgumentException.class)
  public void testAddExistingImageId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList,
        "smallImageCopy"));
  }

  //tests when you do one valid add to the model created image of the normal programmed image
  //format
  @Test
  public void testOneValidAddedImageNormalImageFormat() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    assertTrue(photoEditorModel1.getPhotoIds().size() == 1);
  }

  //tests when you do multiple valid image adds to the model created images of the normal
  //programmed image format
  @Test
  public void testMultipleValidAddedImageNormalImageFormat() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("sameSmallImage.ppm", new Image(pixelList,
        "sameSmallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
    assertTrue(photoEditorModel1.getPhotoIds().size() == 2);
  }


  //add an IOManager with a null file Name
  @Test(expected = IllegalArgumentException.class)
  public void testAddIOManagerNullFileName() {
    importManager = new PPMImportManager(null);
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding an IOManager with an empty file Name
  @Test(expected = IllegalArgumentException.class)
  public void testAddEmptyFileName() {
    importManager = new PPMImportManager("");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a validIOManager
  @Test
  public void testAddValidImport() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    photoEditorModel1.addImage("hello", importManager.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //add in a invalid IOManger with a file that does not start with P3
  @Test(expected = IllegalArgumentException.class)
  public void testAddMalformedFileImportNonP3() {
    importManager = new PPMImportManager("./res/malformed.ppm");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a invalid IOManger with a file that
  //does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportNonExistentFile() {
    importManager = new PPMImportManager("hello??");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a invalid IOManger with a file that that exists but is not PPM
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportExistingFileWrongFormat() {
    importManager = new PPMImportManager("./res/malformed.txt");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a invalid IOManger with a file that name that is too short
  @Test(expected = IllegalArgumentException.class)
  public void testAddShortName() {
    importManager = new PPMImportManager("xt");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a invalid IOManger with a file that
  //name that has the valid format but has an amount of pixels that is not a multiple of 3
  @Test(expected = IllegalArgumentException.class)
  public void testAddMalformedFileValuesNotMultOf3() {
    importManager = new PPMImportManager("./res/malformedNotMult3.ppm");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in a invalid IOManger with a file that
  //name that has the valid format but has an amount of pixels that does not have valid dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testAddMalformedFileImageDimensionsDontMatch() {
    importManager = new PPMImportManager("./res/malformedBadDimensions.ppm");
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in two IOManagers that are importing
  //the same exact photo file
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingSameFileTwiceThrows() {
    importManager = new PPMImportManager("./res/fileTest1.ppm");
    photoEditorModel1.addImage("hello", importManager.apply());
    photoEditorModel1.addImage("yo", importManager.apply());

  }

  //tests adding in an IOManager that imports an empty
  //image
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingEmptyImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "0 0\n"
            + "255").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that imports
  //only one pixel image
  @Test
  public void testAddImporting1PixelImage() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
    assertEquals(photoEditorModel1.getImage("hello").getPixelHeight(), 1);
    assertEquals(photoEditorModel1.getImage("hello").getPixelWidth(), 1);
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 0),
        new Pixel(0, 10, 130));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests adding in an IOManager that has the invalid
  //max pixel limit in the file
  @Test(expected = IllegalArgumentException.class)
  public void testAddInvalidMaxColorValue() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 1\n"
            + "256\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that is a completely
  //empty file
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingEmpty() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that does not have
  //the capital P in P3
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingNonP3() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("p3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that has an actual word
  //instead of P in the P3 location of the file
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingNonP31() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("hello3\n"
            + "1 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that says it has
  //invalid dimensions in the dimension specifier part of the file
  @Test(expected = IllegalArgumentException.class)
  public void testAddImporting1PixelDimensionDontMatch() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "1 2\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager that says it has
  //invalid dimensions in the dimension specifier part of the file again
  @Test(expected = IllegalArgumentException.class)
  public void testAddImporting1PixelWrongDimension2() {
    importManager = new PPMImportManager("file.ppm",
        new ByteArrayInputStream(("P3\n"
            + "2 1\n"
            + "255\n"
            + "0\n"
            + "10\n"
            + "130").getBytes()));
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding  in an IOManager with a valid
  //3X2 file
  @Test
  public void testAddImporting3x2() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
    assertEquals(photoEditorModel1.getImage("hello").getPixelWidth(), 3);
    assertEquals(photoEditorModel1.getImage("hello").getPixelHeight(), 2);
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 0),
        new Pixel(0, 30, 30));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 1),
        new Pixel(0, 60, 60));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 2),
        new Pixel(0, 90, 90));

    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 0),
        new Pixel(0, 120, 120));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 1),
        new Pixel(30, 0, 30));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 2),
        new Pixel(30, 30, 40));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests adding in an IOManager with a file that holds
  //pixel values that are not a multiple of 3
  @Test(expected = IllegalArgumentException.class)
  public void testAddImportingNonMult3DataValues() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager with a file that holds
  //pixel values that are not numbers
  @Test(expected = IllegalArgumentException.class)
  public void testAddImporting3x2WithRandomChars() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager with a valid file
  //that at the end of the list of pixels allows random characters
  @Test
  public void testAddImporting3x2WithRandomCharsAtEnd() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
    assertEquals(photoEditorModel1.getImage("hello").getPixelWidth(), 3);
    assertEquals(photoEditorModel1.getImage("hello").getPixelHeight(), 2);
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 0),
        new Pixel(0, 30, 30));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 1),
        new Pixel(0, 60, 60));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(0, 2),
        new Pixel(0, 90, 90));

    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 0),
        new Pixel(0, 120, 120));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 1),
        new Pixel(30, 0, 30));
    assertEquals(photoEditorModel1.getImage("hello").getPixelAt(1, 2),
        new Pixel(30, 30, 40));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests adding in an IOManager with an input
  //stream that is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullInputStream() {
    importManager = new PPMImportManager("file.ppm",
        null);
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager with a null fileName
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullFile() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests adding in an IOManager with a fileName
  //that is not ppm
  @Test(expected = IllegalArgumentException.class)
  public void testAddBadFileName() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
  }

  //tests when you import/add a PPM image into the model with a non existent file name
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageFileThatDoesNotExist() {
    photoEditorModel1.addImage("smallImage.ppm", new PPMImportManager("hello").apply());
  }

  //tests when you import/add a PPM image into the model with an empty file name
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageEmptyFileName() {
    photoEditorModel1.addImage("smallImage.ppm", new PPMImportManager("").apply());
  }

  //tests when you import/add a PPM image into the model with a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageNullFileName() {
    photoEditorModel1.addImage("smallImage.ppm", new PPMImportManager(null).apply());
  }

  //tests when you import/add a PPM image into the model with a null string id
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageNullStringId() {
    photoEditorModel1.addImage(null, new PPMImportManager("Hello").apply());
  }

  //tests when you import/add a PPM image into the model with an empty string id
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageEmptyStringId() {
    photoEditorModel1.addImage("", new PPMImportManager("Hello").apply());
  }

  //tests when you import/add a PPM image into the model with a
  //string id that is already in the model
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageIdAlreadyInModel() {
    photoEditorModel1.addImage("hi", new Image(pixelList, "hi"));
    photoEditorModel1.addImage("hi", new PPMImportManager("Hello").apply());
  }

  //tests when you import/add a PPM image into the model but it is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddingAnImportedImageButItIsNull() {
    photoEditorModel1.addImage("hi",
        null);
  }

  //tests when you import/add a PPM image into the model in a valid manner
  @Test
  public void testAddingAnImportedImagePPMValid() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("smallImage.ppm", fileTest1.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you import/add multiple PPM image into the model in a valid manner
  @Test
  public void testAddingMultipleImportedImagesPPMValid() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("smallImage.ppm", fileTest1.apply());
    photoEditorModel1.addImage("smallImage1.ppm", fileTest2.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
  }


  //tests when you add a programmatically generated image into the model but it has a null fileName
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageToModelButNullFileName() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm", checkerBoardGenerate1.generateImage(null));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically generated image
  // into the model but it has an empty fileName
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageToModelButEmptyFileName() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm", checkerBoardGenerate1.generateImage(""));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically generated checkerboard image into the model but
  // it has a negative square size
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageCheckerBoardToModelButItHasNegativeSquareSize() {
    IImageGenerator checkerBoardGenerateNegativeSquareSize =
        new CheckerBoardImageGenerator(-4, 20, Color.BLACK, Color.ORANGE);
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
            checkerBoardGenerateNegativeSquareSize.generateImage("hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically generated checkerboard image into the model but
  // it has a zero square size
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageCheckerBoardToModelButItHasZeroSquareSize() {
    IImageGenerator checkerBoardGenerateNegativeZeroSquareSize =
        new CheckerBoardImageGenerator(0, 20, Color.BLACK, Color.ORANGE);
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
            checkerBoardGenerateNegativeZeroSquareSize.generateImage("hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically generated checkerboard image into the model but
  // it has a a negative board size
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageCheckerBoardToModelButItHasNegativeBoardSize() {
    IImageGenerator checkerBoardGenerateNegativeBoardSize =
        new CheckerBoardImageGenerator(4, -4, Color.BLACK, Color.ORANGE);
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
            checkerBoardGenerateNegativeBoardSize.generateImage("hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically generated checkerboard image into the model but
  // it has a zero board size
  @Test(expected = IllegalArgumentException.class)
  public void testAddProgrammaticallyGeneratedImageCheckerBoardToModelButItHasZeroBoardSize() {
    IImageGenerator checkerBoardGenerateZeroBoardSize =
        new CheckerBoardImageGenerator(4, 0, Color.BLACK, Color.ORANGE);
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
        checkerBoardGenerateZeroBoardSize.generateImage("hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a valid programmatically (checkerboard) generated image into the model
  @Test
  public void testAddProgrammaticallyGeneratedImageToModel() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
        checkerBoardGenerate1.generateImage("hello.pmm"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when you add a programmatically (checkerboard) generated image into the model
  @Test
  public void testAddMultipleProgrammaticallyGeneratedImageToModel() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
        checkerBoardGenerate1.generateImage("hello.pmm"));
    photoEditorModel1.addImage("track.pmm",
        checkerBoardGenerate2.generateImage("track.pmm"));
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
  }

  //tests when you add a programmatic image, a imported image, and a generated image to the model
  @Test
  public void testAddAllTypesOfImagesToModel() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("hello.pmm",
        checkerBoardGenerate1.generateImage("hello.pmm"));
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.addImage("simple", fileTest1.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 3);
  }

  //tests for getImage

  //tests when the string id the client wants to get is empty
  @Test(expected = IllegalArgumentException.class)
  public void testGetImageEmptyStringId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.getImage("");
  }

  //tests when the string id the client wants to get is null
  @Test(expected = IllegalArgumentException.class)
  public void testGetImageNullId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.getImage(null);
  }

  //tests when the image a client wants to get is not in the model
  @Test(expected = IllegalArgumentException.class)
  public void testGetImageButTheImageDoesNotExistInTheModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.getImage("hello");
  }

  //tests valid case of getting an image
  @Test
  public void testGetImageValidCaseOneAttempt() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertEquals(new Image(pixelList, "smallImage"),
        photoEditorModel1.getImage("smallImage.ppm"));

  }

  //tests valid case of getting multiple images
  @Test
  public void testGetMultipleImagesValidCase() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("sameSmallImage.ppm",
        new Image(pixelList, "sameSmallImage"));
    assertEquals(new Image(pixelList, "smallImage"),
        photoEditorModel1.getImage("smallImage.ppm"));
    assertEquals(new Image(pixelList, "sameSmallImage"),
        photoEditorModel1.getImage("sameSmallImage.ppm"));
  }

  //tests a valid case of getting an image that was imported
  @Test
  public void testGetImportedImageValidCaseOneAttempt() {
    IImage file = fileTest1.apply();
    photoEditorModel1.addImage("smallImage.ppm", file);
    assertEquals(file, photoEditorModel1.getImage("smallImage.ppm"));
  }

  //tests a valid case of getting multiple image that were imported
  @Test
  public void testGetImportedMultipleImagesValidCaseOneAttempt() {
    IImage file = fileTest1.apply();
    IImage file2 = fileTest2.apply();
    photoEditorModel1.addImage("smallImage.ppm", file);
    photoEditorModel1.addImage("smallSimilarImage.ppm", file2);
    assertEquals(file, photoEditorModel1.getImage("smallImage.ppm"));
    assertEquals(file2, photoEditorModel1.getImage("smallSimilarImage.ppm"));
  }

  //tests a valid case of getting an image that was programmatically generated
  @Test
  public void testGetProgramCreatedImageValidCaseOneAttempt() {
    IImage file = checkerBoardGenerate1.generateImage("hello");
    photoEditorModel1.addImage("smallImage.ppm",
        checkerBoardGenerate1.generateImage("hello"));
    assertEquals(file, photoEditorModel1.getImage("smallImage.ppm"));
  }

  //tests a valid case of getting multiple images that were programmatically generated
  @Test
  public void testGetProgramCreatedMultipleImagesValidCaseOneAttempt() {
    IImage file = checkerBoardGenerate1.generateImage("hello");
    IImage file2 = checkerBoardGenerate2.generateImage("hi");
    photoEditorModel1.addImage("smallImage.ppm",
        checkerBoardGenerate1.generateImage("hello"));
    photoEditorModel1.addImage("smallSimilarImage.ppm",
        checkerBoardGenerate2.generateImage("hi"));
    assertEquals(file, photoEditorModel1.getImage("smallImage.ppm"));
    assertEquals(file2, photoEditorModel1.getImage("smallSimilarImage.ppm"));
  }

  //remove images tests

  //tests when a client wants to remove an image but uses an empty StringId
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyStringID() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.removeImage("");
  }

  //tests when a client wants to remove an image but uses a null Id
  @Test(expected = IllegalArgumentException.class)
  public void testNullIdRemoveImage() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.removeImage(null);
  }

  //tests when a client wants to remove an image but uses an id that is not in the model
  @Test(expected = IllegalArgumentException.class)
  public void testIDNotInTheModelRemoveImage() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.removeImage("hello");
  }

  //tests when a client removes an image successfully
  @Test
  public void testRemoveImagesSuccessfully() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests when a client removes multiple images successfully
  @Test
  public void testRemoveMultiplesImagesSuccessfully() {
    photoEditorModel1.addImage("smallImage.ppm",
        new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("sameSmallImage.ppm",
        new Image(pixelList, "sameSmallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("sameSmallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests a valid case of removing an image that was imported
  @Test
  public void testRemoveImportedImageValidCaseOneAttempt() {
    photoEditorModel1.addImage("smallImage.ppm", fileTest1.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests a valid case of removing multiple image that were imported
  @Test
  public void testRemoveImportedMultipleImagesValidCaseOneAttempt() {

    assertTrue(photoEditorModel1.getNumPhotos() == 0);
    photoEditorModel1.addImage("smallImage.ppm", fileTest1.apply());
    photoEditorModel1.addImage("smallSimilarImage.ppm", fileTest2.apply());
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallSimilarImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests a valid case of removing an image that was programmatically generated
  @Test
  public void testRemoveProgramCreatedImageValidCaseOneAttempt() {
    photoEditorModel1.addImage("smallImage.ppm",
        checkerBoardGenerate1.generateImage("hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests a valid case of removing multiple images that were programmatically generated
  @Test
  public void testRemoveProgramCreatedMultipleImagesValidCaseOneAttempt() {
    photoEditorModel1.addImage("smallImage.ppm",
        checkerBoardGenerate1.generateImage("hello"));
    photoEditorModel1.addImage("smallSimilarImage.ppm",
        checkerBoardGenerate2.generateImage("hi"));
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallSimilarImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //replace image tests

  //tests when the client wants to replace an image but provides an emptyString Id
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageEmptyStringId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("", new Image(pixelList, "hello"));
  }

  //tests when the client wants to replace an image but provides an image with an empty pixelList
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageEmptyPixelList() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("smallImage.ppm",
        new Image(emptyPixelList, "hello"));
  }

  //tests when the client wants to replace an image but provides an image with an invalid pixelList
  //dimension
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageInvalidPixelListDimensions() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("smallImage.ppm",
            new Image(invalidDimensionsPixelList, "hello"));
  }

  //tests when the client wants to replace an image but provides a null Id
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageNullId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage(null, new Image(pixelList, "hello"));
  }

  //tests when the client wants to replace an image but provides a null image
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageNullImage() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("smallImage.ppm", null);
  }

  //tests when the client wants to replace an image but provides a string id that does not exist in
  //the model
  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageStringIdNotValidInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("yo", new Image(pixelList, "hello"));
  }

  //test a valid image replacement
  @Test
  public void testReplaceImageValidCase() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.replaceImage("smallImage.ppm", new Image(pixelList, "yo"));
    assertTrue(photoEditorModel1.getImage("smallImage.ppm").equals(new Image(pixelList,
        "yo")));
  }

  //tests multiple valid image replacements
  @Test
  public void testReplaceMutlipleImageValidCase() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("replaced", new Image(pixelList, "sameSmallImage"));

    photoEditorModel1.replaceImage("smallImage.ppm", new Image(pixelList, "yo"));
    photoEditorModel1.replaceImage("replaced", new Image(pixelList, "hello"));

    assertTrue(photoEditorModel1.getImage("smallImage.ppm").equals(new Image(pixelList,
        "yo")));
    assertTrue(photoEditorModel1.getImage("replaced").equals(new Image(pixelList,
        "hello")));
  }

  //tests a valid case of replacing an image that was imported
  @Test
  public void testReplacingImportedImageValidCaseOneAttempt() {

    IImage file2 = fileTest2.apply();

    photoEditorModel1.addImage("smallImage.ppm", fileTest1.apply());

    photoEditorModel1.replaceImage("smallImage.ppm", file2);

    assertEquals(photoEditorModel1.getImage("smallImage.ppm"), file2);
  }

  //tests a valid case of replacing an image that was imported but the ImportManager
  //has a null
  @Test(expected = IllegalArgumentException.class)
  public void testReplacingImportedImageImportManagerHasNullFileName() {
    IOManager manager = new PPMImportManager(null);
    IImage file2 = manager.apply();

    photoEditorModel1.addImage("smallImage.ppm", manager.apply());

    photoEditorModel1.replaceImage("smallImage.ppm", fileTest2.apply());

    assertEquals(photoEditorModel1.getImage("smallImage.ppm"), file2);
  }

  //tests a valid case of replacing an image that was imported but the ImportManager
  //has an empty file name
  @Test(expected = IllegalArgumentException.class)
  public void testReplacingImportedImageImportManagerHasEmptyFileName() {
    IOManager manager = new PPMImportManager("");
    IImage file2 = manager.apply();

    photoEditorModel1.addImage("smallImage.ppm", manager.apply());

    photoEditorModel1.replaceImage("smallImage.ppm", fileTest2.apply());

    assertEquals(photoEditorModel1.getImage("smallImage.ppm"), file2);
  }

  //tests a valid case of replacing an image that was imported but the ImportManager
  //has a non existent file name
  @Test(expected = IllegalArgumentException.class)
  public void testReplacingImportedImageImportManagerHasNonExistentFileName() {
    IOManager manager = new PPMImportManager("non-existent");
    IImage file2 = manager.apply();

    photoEditorModel1.addImage("smallImage.ppm", manager.apply());

    photoEditorModel1.replaceImage("smallImage.ppm", fileTest2.apply());

    assertEquals(photoEditorModel1.getImage("smallImage.ppm"), file2);
  }

  //tests a valid case of replacing an image that was programmatically generated
  @Test
  public void testReplacingProgramCreatedImageValidCaseOneAttempt() {

    IImage file2 = checkerBoardGenerate2.generateImage("hi");

    photoEditorModel1.addImage("smallImage.ppm",
        checkerBoardGenerate1.generateImage("hello"));

    photoEditorModel1.replaceImage("smallImage.ppm",
        checkerBoardGenerate2.generateImage("hi"));

    assertEquals(photoEditorModel1.getImage("smallImage.ppm"), file2);
  }

  //export image tests

  //tests when a client uses a null id to export the image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullStringId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.exportImage(null, new PPMExportManager("hello"));
  }

  //tests when a client uses a null export manager to export the image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullExportManager() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.exportImage("smallImage.ppm", null);
  }

  //tests when a client uses an empty String id to export the image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageEmptyStringId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.exportImage("", new PPMExportManager("hello"));
  }

  //tests when a client uses an export manager with an empty String file name+
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullFileName() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.exportImage("smallImage.ppm", new PPMExportManager(""));
  }

  //tests when a client uses a String id to export the image that does not exist in the model
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNonExistentStringId() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.exportImage("hello", new PPMExportManager("hello"));
  }


  //tests exporting when the exported image is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportNullImage() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage(null, new PPMExportManager("test.ppm"));
  }

  //tests exporting when the exportManager fileName is empty
  @Test(expected = IllegalArgumentException.class)
  public void testExportEmptyFileName() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage("hello", new PPMExportManager(""));
  }

  //tests exporting when the exportManager fileName is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportNullFileName() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage("hello", new PPMExportManager((String) null));
  }

  //tests exporting when the exportManager fileName does not have a ppm file name
  @Test(expected = IllegalArgumentException.class)
  public void testExportNonPpmFileName() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage("hello", new PPMExportManager("file.txt"));
  }

  //tests exporting when the exportManager writer is null
  @Test(expected = IllegalArgumentException.class)
  public void testExportBadWriter() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage("hello", new PPMExportManager((Writer) null));
  }

  //tests exporting when the exportManager fileName is too short
  @Test(expected = IllegalArgumentException.class)
  public void testExportTooShortFileName() {
    photoEditorModel1.addImage("hello", new Image(pixelList, "hi"));
    photoEditorModel1.exportImage("hello", new PPMExportManager("abc"));
  }

  //tests exporting when the image is just one small programmatically created pixel
  @Test
  public void testWriterOneBluePixel() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(
        1, 1, Color.BLUE, Color.BLUE).generateImage("blue.ppm");

    photoEditorModel1.addImage("hello", board);
    photoEditorModel1.exportImage("hello", new PPMExportManager(writer));

    assertEquals(writer.toString(), "P3\n"
        + "1 1\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }

  //tests exporting when the image is a bigger programmatically created image
  @Test
  public void testExportString() {
    Writer writer = new StringWriter();
    photoEditorModel1.addImage("hello", fileTest3);
    photoEditorModel1.exportImage("hello", new PPMExportManager(writer));
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

  //tests exporting when the image is a checkBoard
  @Test
  public void testExportSmallCheckerBoard() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(1, 3, Color.BLACK,
        Color.WHITE).generateImage("board.ppm");

    photoEditorModel1.addImage("hello", board);
    photoEditorModel1.exportImage("hello", new PPMExportManager(writer));
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

  //tests exporting multiple images using the same manager
  @Test(expected = IllegalArgumentException.class)
  public void testExportMultipleImagesUsingSameManager() {
    Writer writer = new StringWriter();
    IImageExportManager manager = new PPMExportManager(writer);
    IImage board = new CheckerBoardImageGenerator(1, 3, Color.BLACK, Color.WHITE)
        .generateImage("board.ppm");

    photoEditorModel1.addImage("hello", board);
    photoEditorModel1.addImage("hi", fileTest3);
    photoEditorModel1.exportImage("hello", manager);
    photoEditorModel1.exportImage("hi", manager);

  }

  //tests that nothing is added to the writer when an export fails to the same export manager
  @Test
  public void testWriterAfterFailedExport() {
    Writer writer = new StringWriter();
    IImage board = new CheckerBoardImageGenerator(1, 3, Color.BLACK, Color.WHITE)
        .generateImage("board.ppm");

    photoEditorModel1.addImage("hello", board);
    photoEditorModel1.addImage("hi", fileTest3);

    IImageExportManager imageExportManager = new PPMExportManager(writer);

    photoEditorModel1.exportImage("hello", imageExportManager);
    try {
      photoEditorModel1.exportImage("hi", imageExportManager);
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

  //tests importing and exporting the same image
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
    photoEditorModel1.addImage("hello", importManager.apply());
    photoEditorModel1.exportImage("hello", new PPMExportManager(writer)
    );

    assertEquals(writer.toString(), ppmData);
  }


  //exporting an image that was imported as file into the model
  @Test
  public void testImportFileImageFromModel() {
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
    photoEditorModel1.addImage("hello", importManager.apply());
    photoEditorModel1.exportImage("hello", new PPMExportManager(writer)
    );

    assertEquals(writer.toString(), ppmData);
  }

  //getNumPhotos tests


  //tests when there are no photos in the model
  @Test
  public void testGetNumPhotosNoPhotosInModel() {
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests when there is one photo in the model
  @Test
  public void testGetNumPhotosOnePhotoInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //tests when there are multiple photos in the model
  @Test
  public void testGetNumPhotosMultiplePhotosInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("replaced", new Image(pixelList, "sameSmallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 2);
  }

  //tests getNumPhotos when a photo is added then removed
  @Test
  public void testGetNumPhotosPhotoAddedAndRemovedInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.removeImage("smallImage.ppm");
    assertTrue(photoEditorModel1.getNumPhotos() == 0);
  }

  //tests getNumPhotos when a photo is added then replaced
  @Test
  public void testGetNumPhotosPhotoAddedAndReplacedInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
    photoEditorModel1.replaceImage("smallImage.ppm", new Image(pixelList, "hello"));
    assertTrue(photoEditorModel1.getNumPhotos() == 1);
  }

  //getNumPhotoId's tests


  //tests getNumPhotoId's when there are no photos in the model
  @Test
  public void testGetPhotoIdsNoPhotosInModel() {
    assertTrue(photoEditorModel1.getPhotoIds().size() == 0);
  }

  //tests getNumPhotoId's when there is one photo in the model
  @Test
  public void testGetPhotoIdsOnePhotoInModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    assertTrue(photoEditorModel1.getPhotoIds().size() == 1);
    Set<String> idSet = new HashSet<>();
    idSet.add("smallImage.ppm");
    assertEquals(idSet, photoEditorModel1.getPhotoIds());
  }

  //tests getNumPhotoId's when there are multiple photos in the model
  @Test
  public void testGetPhotoIdsMultiplePhotosInTheModel() {
    photoEditorModel1.addImage("smallImage.ppm", new Image(pixelList, "smallImage"));
    photoEditorModel1.addImage("sameSmallImage.ppm",
        new Image(pixelList, "sameSmallImage"));
    assertTrue(photoEditorModel1.getPhotoIds().size() == 2);

    Set<String> idSet = new HashSet<>();
    idSet.add("smallImage.ppm");
    idSet.add("sameSmallImage.ppm");
    assertEquals(idSet, photoEditorModel1.getPhotoIds());
  }
}