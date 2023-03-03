package hw06;


import hw05.exportimages.IImageExportManager;
import hw05.exportimages.PNGExportManager;
import hw05.exportimages.TextFileExportManager;
import hw05.image.IImage;
import hw05.imageedits.IPhotoEdit;
import hw05.importimages.IOManager;
import hw05.importimages.JPEGImportManager;
import hw05.importimages.PNGImportManager;
import hw05.importimages.PPMImportManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents the LayeredImageEditorModel which is a model that allows for multiple images to be
 * added on top of one another. Together, these stacked images created a layered image that has a
 * lot of the same functionality that normal images do. Every stacked image is represented by a
 * layer, which houses the name of the layer and the image.
 */
public class LayeredImageEditorModel implements ILayeredImageEditorModel {

  private final List<ILayer> layerList;
  private int current = 0;

  /**
   * Creates a LayeredImageEditorModel that represents a layered image that contains
   * images stacked on top of each other. When initially created, there are no layers inside of
   * layered image model.
   */
  public LayeredImageEditorModel() {
    layerList = new ArrayList<>();
  }

  @Override
  public void addImage(String id, IImage image) {
    if (id == null) {
      throw new IllegalArgumentException("The string id for the image you want to add cannot "
          + "be null");
    }
    if (id.equals("")) {
      throw new IllegalArgumentException("The string id for the image you want to add cannot "
          + "be empty");
    }

    checkListId(id);

    ILayer layer1 = new Layer(id, image);

    if (compatibleDimensions(image)) {
      this.layerList.add(layer1);
    } else {
      throw new IllegalArgumentException("You cannot add this layer to the layered image "
          + "because it has dimensions that are not equal to the layers already in the image.");
    }

  }

  @Override
  public void addCopyLayer(String idOfImage, String newIdForImageCopy) {
    if (idOfImage == null || newIdForImageCopy == null || idOfImage.equals("")
        || newIdForImageCopy.equals("")) {
      throw new IllegalArgumentException("Invalid id arguments for adding a copy layer!");
    }

    ILayer layerInImage = getLayerFromName(idOfImage);

    checkListId(newIdForImageCopy);

    ILayer layer = new Layer(newIdForImageCopy, layerInImage.getImage(),
        getCurrentLayerCopy().isVisible());
    this.layerList.add(layer);
  }

  @Override
  public void addCopyCurrentLayer(String newIdForImageCopy) {
    if (newIdForImageCopy == null || newIdForImageCopy.equals("")) {
      throw new IllegalArgumentException("The new id for the copy image you want to create "
          + "cannot be empty or null");
    }

    IImage currentImage = getCurrentLayerImage();

    checkListId(newIdForImageCopy);
    ILayer layer = new Layer(newIdForImageCopy, currentImage, getCurrentLayerCopy().isVisible());
    this.layerList.add(layer);

  }

  @Override
  public List<ILayer> getLayers() {
    List<ILayer> layerCopy = new ArrayList<>();
    for (ILayer layer : layerList) {
      layerCopy.add(new Layer(layer.getName(), layer.getImage(), layer.isVisible()));
    }
    return layerCopy;
  }

  @Override
  public void exportTopImage(IImageExportManager manager) {
    if (manager == null) {
      throw new IllegalArgumentException("The import manager you are using to export "
          + "your image cannot be null!");
    }
    if (layerList.size() <= 0) {
      throw new IllegalArgumentException("Cannot export an image from a layered image with no "
          + "layers");
    }

    boolean exported = false;

    for (ILayer layer : layerList) {
      if (layer.isVisible() && layer.getImage() != null) {
        manager.export(layer.getImage());
        exported = true;
        break;
      }
    }

    if (!exported) {
      throw new IllegalArgumentException("There are no visible layers in the layered image, "
          + "meaning that nothing can be exported");
    }
  }

  @Override
  public void loadEntireLayeredImage(String textFileName) throws IllegalArgumentException {
    if (textFileName == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(textFileName));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read from the file, or it doesn't exist");
    }
    Scanner scan = new Scanner(reader);
    List<ILayer> layersToAdd = new ArrayList<>();
    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      String[] tokens = line.split(" \\| ");
      if (tokens.length != 3) {
        throw new IllegalArgumentException("A line inside the text file is not formatted properly. "
            + "Must be 3 tokens");
      } else {
        ILayer layer = new Layer(tokens[0], generateImageFromFile(tokens[2]));
        if (tokens[1].endsWith("true")) {
          layer.setVisibility(true);
          layersToAdd.add(layer);
        } else if (tokens[1].endsWith("false")) {
          layer.setVisibility(false);
          layersToAdd.add(layer);
        } else {
          throw new IllegalArgumentException(
              "A line inside the text file is not formatted properly.");
        }
      }
    }
    for (ILayer layer : getLayers()) {
      removeImage(layer.getName());
    }
    for (ILayer layer : layersToAdd) {
      this.addImage(layer.getName(), layer.getImage());
      if (layer.isVisible()) {
        this.setLayerVisible(layer.getName());
      } else {
        this.setLayerTransparent(layer.getName());
      }
    }
  }

  //creates an IImage from a filename of either png, jpeg, or ppm format
  private IImage generateImageFromFile(String fileName) {
    if (fileName.equals("no image file")) {
      return null;
    }
    String[] tokens = fileName.split("\\.");
    if (tokens.length < 2) {
      throw new IllegalArgumentException("Invalid file format for uploading!\n");
    }

    IOManager manager;

    String fileType = tokens[tokens.length - 1].toLowerCase();

    switch (fileType) {
      case "ppm":
        try {
          manager = new PPMImportManager(fileName);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Image file not found\n");
        }
        break;
      case "png":
        try {
          manager = new PNGImportManager(fileName);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Image file not found\n");
        }
        break;
      case "jpeg":
      case "jpg":
        try {
          manager = new JPEGImportManager(fileName);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Image file not found\n");
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid ending file format for file uploading!\n");
    }

    if (manager != null) {
      return manager.apply();
    } else {
      throw new IllegalArgumentException("Could not read the image file");
    }
  }

  /**
   * Checks if the given layer name corresponds to a layer that is inside of the layered image.
   *
   * @param id The layer name that the client wants to check
   * @throws IllegalArgumentException throws this exception if the the given layer name is not
   *                                  inside of the layered image or if the layer name is null.
   */
  private void checkListId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The name of the layer cannot be null!");
    }
    for (ILayer layer : layerList) {
      if (layer.getName().equals(id)) {
        throw new IllegalArgumentException("You cannot add an Image with an Id that already exists "
            + "in the layered image.");
      }
    }
  }

  /**
   * Gets a copy of the layer in the layered image that corresponds with the given layer name.
   *
   * @param layerName The layer name of the layer that the client wants to get
   * @return Returns a layer in the layered image that corresponds with the given layer name
   * @throws IllegalArgumentException throws this exception if the given layer name does not exist
   *                                  in the layered image or if it is null.
   */
  protected ILayer getLayerFromName(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layerName cannot be null!");
    }
    for (ILayer layer : layerList) {
      if (layer.getName().equals(layerName)) {
        return new Layer(layer.getName(), layer.getImage(), layer.isVisible());
      }
    }
    throw new IllegalArgumentException("No layer exists in the layered image with that "
        + "specific layer name.");
  }

  /**
   * Gets the actual reference to the layer in the layered image that corresponds with the given
   * layer name.
   *
   * @param layerName The layer name of the layer that the client wants to get
   * @return Returns the actual reference of layer in the layered image that corresponds with the
   *         given layer name
   * @throws IllegalArgumentException throws this exception if the given layer name does not exist
   *                                  in the layered image or if it is null.
   */
  private ILayer getLayerReferenceFromName(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layerName cannot be null!");
    }
    for (ILayer layer : layerList) {
      if (layer.getName().equals(layerName)) {
        return layer;
      }
    }
    throw new IllegalArgumentException("No layer exists in the layered image with that "
        + "specific layer name.");
  }


  @Override
  public void removeImage(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The name of the layer you want to remove "
          + "cannot be null!");
    }

    for (ILayer layer : layerList) {
      if (layer.getName().equals(id)) {
        layerList.remove(layer);
        return;
      }
    }
    throw new IllegalArgumentException("The name is not inside of the layered image!");
  }

  /**
   * Gets the index of the layer with the specific layer name that exists in the layered image.
   *
   * @param id The layer name that a client wants to get the index of.
   * @return Returns the index of the layer with the specific layer name that exists in the layered
   *         image.
   * @throws IllegalArgumentException throws this exception if the passed in layer name is null or
   *                                  if the layer name does not exist in the layered image.
   */
  protected int getIndex(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The name of the layer cannot be null!");
    }
    for (int i = 0; i < layerList.size(); i++) {
      if (layerList.get(i).getName().equals(id)) {
        return i;
      }
    }
    throw new IllegalArgumentException("This layer name does not exist in the layered image!");
  }

  @Override
  public void replaceImage(String id, IImage image) {
    if (image == null || id == null) {
      throw new IllegalArgumentException("The provided new image or layer name cannot be null!");
    }

    ILayer layer1 = getLayerFromName(id);

    if (this.compatibleDimensions(image)) {
      int imageIndex = getIndex(id);
      ILayer layer = new Layer(id, image, layer1.isVisible());
      layerList.set(imageIndex, layer);
    } else {
      throw new IllegalArgumentException("The provided image does not have the correct dimensions "
          + "to be added to the layered image. It needs to have the same dimensions as the images "
          + "inside of the layered image.");
    }
  }

  /**
   * Checks to see if the passed in image has compatible dimensions to be added to the layered
   * images. Images in a layered image need to have the same dimensions if they are not null.
   *
   * @param image The image that a client wants to check for the validity of its dimensions.
   * @return Returns if the image has the same dimensions as the layers in the layered image.
   * @throws IllegalArgumentException throws this exception if the image is null
   */
  private boolean compatibleDimensions(IImage image) {
    if (image == null) {
      return true;
    }
    if (layerList.size() != 0) {
      for (ILayer layer : layerList) {
        if (layer.getImage() != null) {
          return layer.getImage().getPixelWidth() == image.getPixelWidth()
              && layer.getImage().getPixelHeight() == image.getPixelHeight();
        }
      }
    }
    return true;
  }

  @Override
  public void exportImage(String id, IImageExportManager exportManager) {
    if (id == null || exportManager == null) {
      throw new IllegalArgumentException("The name of the layer and the export manager cannot "
          + "be null!");
    }
    ILayer layer = getLayerFromName(id);
    exportManager.export(layer.getImage());
  }


  @Override
  public void applyEditToCurrentLayer(IPhotoEdit edit) {
    if (edit == null) {
      throw new IllegalArgumentException("The edit you want to apply cannot be null!");
    }
    try {
      ILayer layer = layerList.get(current);
      replaceImage(layer.getName(), edit.apply(layer.getImage()));
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The current index of the model is out of bounds "
          + "for the amount of layers that are in this layered image!");
    }
  }

  @Override
  public void setCurrentLayer(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The name of the layer cannot be null!");
    }
    current = getIndex(id);
  }

  @Override
  public void applyEditToLayer(String id, IPhotoEdit photoEdit) {
    if (id == null || photoEdit == null) {
      throw new IllegalArgumentException("The name of the layer and photoEdit you want to apply "
          + "cannot be null");
    }
    ILayer layer = getLayerFromName(id);
    replaceImage(id, photoEdit.apply(layer.getImage()));
  }

  @Override
  public void setLayerVisible(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The provided layer name cannot be null!");
    }
    ILayer layer = getLayerReferenceFromName(id);
    layer.setVisibility(true);
  }

  @Override
  public void setLayerTransparent(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The provided layer name cannot be null!");
    }
    layerList.get(getIndex(id)).setVisibility(false);
  }

  @Override
  public boolean isVisible(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layer name cannot be null!");
    }

    try {
      getLayerFromName(layerName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The layer name you provided does not exist!");
    }

    ILayer layer = getLayerFromName(layerName);

    return layer.isVisible();
  }

  @Override
  public void saveLayeredImage(String folder) {
    if (folder == null) {
      throw new IllegalArgumentException("The provided folder name for the files of the images"
          + "cannot be null");
    }

    List<String> fileTracker = new ArrayList<>();

    File theDir = new File("./" + folder);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }

    for (ILayer layer : layerList) {

      if (layer.getImage() != null) {
        new PNGExportManager("./" + folder + "/" + layer.getName() + ".png")
            .export(layer.getImage());
        fileTracker.add(
            layer.getName() + " | isVisible: " + layer.isVisible() + " | ./" + folder + "/" + layer
                .getName() + ".png");
      } else {
        fileTracker
            .add(layer.getName() + " | isVisible: " + layer.isVisible() + " | no image file");
      }

    }
    new TextFileExportManager("./" + folder + "/imageLocations.txt").export(fileTracker);
  }

  @Override
  public String getCurrentLayerName() {
    try {
      ILayer layer = layerList.get(current);
      return layer.getName();
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The current index of the model is out of bounds "
          + "for the amount of layers that are in this layered image! There are no images "
          + "inside of the the layered image.");
    }

  }

  @Override
  public IImage getCurrentLayerImage() {
    try {
      ILayer layer = layerList.get(current);
      return layer.getImage();
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The current index of the model is out of bounds "
          + "for the amount of layers that are in this layered image! There are no images "
          + "inside of the layered image.");
    }
  }

  @Override
  public ILayer getCurrentLayerCopy() {
    try {
      ILayer layer = layerList.get(current);
      return new Layer(layer.getName(), layer.getImage(), layer.isVisible());
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The current index of the model is out of bounds "
          + "for the amount of layers that are in this layered image! There are no images "
          + "inside of the layered image.");
    }
  }

  @Override
  public IImage applyPhotoEdit(String id, IPhotoEdit photoEdit) {
    if (id == null || photoEdit == null) {
      throw new IllegalArgumentException("The id cannot be null and the chosen type of "
          + "photo edit cannot be null either!");
    }
    ILayer layer = getLayerFromName(id);
    return photoEdit.apply(layer.getImage());
  }



  @Override
  public IImage getImage(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The layer name cannot be null!");
    }
    ILayer layer = getLayerFromName(id);
    return layer.getImage();
  }

  @Override
  public int getNumPhotos() {
    return layerList.size();
  }

  @Override
  public Set<String> getPhotoIds() {
    Set<String> photoIds = new HashSet<>();
    for (ILayer layer : layerList) {
      photoIds.add(layer.getName());
    }
    return photoIds;
  }
}
