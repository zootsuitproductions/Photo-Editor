package hw07;

import hw07.controller.IViewListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represents the GraphicalLayeredImageView which provides a graphical view of a layered image
 * functionality that allows for users to interact with the view to edit their desired layered
 * image.
 */
public class GraphicalLayeredImageView extends JFrame implements IView, ActionListener,
    ListSelectionListener {

  //buttons
  private final JButton saveLayersButton;

  private final JButton exportTopImageButton;

  private final JButton removeLayerButton;

  private final JButton createRainbow;

  private final JButton loadImage;


  private final JButton sepia;
  private final JButton blur;
  private final JButton sharpen;
  private final JButton greyscale;

  private final JButton toggleVisibility;

  private final JButton script;

  private final JPanel mainPanel;

  private final JList<String> listOfLayer;

  protected final List<IViewListener> listeners;

  //image creation

  private final JLabel picLabel;


  //hashmap for event command pattern
  private final Map<String, IActionEvent> actionEvents;

  /**
   * Constructs the GraphicalLayeredImageView with the proper graphical elements to create the view
   * that will allow for clients to interact with the view and ask for their desired things to be
   * done to their layered image.
   */
  public GraphicalLayeredImageView() {
    super();
    setSize(new Dimension(500, 600));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Layered Image Editor");
    setLayout(new BorderLayout());

    JPanel imagePanel = new JPanel();
    ImageIcon img = new ImageIcon();
    listeners = new ArrayList<>();

    //initialize buttons
    saveLayersButton = new JButton("Save");
    JButton createCopyOfLayer = new JButton("Duplicate Current Layer");

    exportTopImageButton = new JButton("Export The Top Image");
    removeLayerButton = new JButton("Remove Layer");
    createRainbow = new JButton("Insert Rainbow Image");
    loadImage = new JButton("Load Image Into Layer");
    sepia = new JButton("Sepia");
    blur = new JButton("Blur");
    sharpen = new JButton("Sharpen");
    greyscale = new JButton("Greyscale");
    toggleVisibility = new JButton("Toggle Visibility");
    script = new JButton("Insert Script");

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BorderLayout());
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //MENU (note: we made all our fields final, so they must be initialized in the constructor)
    initMenu();

    //NORTH SIDE PANEL:
    initNorthPanel();

    //WEST SIDE PANEL:
    initWestPanel();

    //EAST SIDE PANEL:

    //CreateLayerDisplay
    JPanel dialogBoxesPanel = new JPanel();
    JPanel inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(inputDialogPanel);
    JButton createLayerButton = new JButton("Create Layer");
    createLayerButton.setActionCommand("createLayerInput");
    inputDialogPanel.add(createLayerButton);
    createLayerButton.addActionListener(this);

    //createCopyDisplay

    inputDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(inputDialogPanel);
    createCopyOfLayer.setActionCommand("createCopyLayer");
    inputDialogPanel.add(createCopyOfLayer);

    createCopyOfLayer.addActionListener(this);

    //SelectionListCode
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Click A Layer To Edit:"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(selectionListPanel, BorderLayout.EAST);

    DefaultListModel<String> dataForListOfStrings = new DefaultListModel<>();

    listOfLayer = new JList<>(dataForListOfStrings);
    listOfLayer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    listOfLayer.addListSelectionListener(this);
    selectionListPanel.add(listOfLayer);
    selectionListPanel.add(inputDialogPanel);

    //CENTER DISPLAY

    //image widget

    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Top Layer Image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));

    picLabel = new JLabel(img);

    JScrollPane pane = new JScrollPane(picLabel);
    pane.setPreferredSize(new Dimension(500, 600));

    imagePanel.add(pane);
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    //action event construction
    actionEvents = new HashMap<>();
    initActionEvents();

    pack();

  }

  private void initMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu sMenu = new JMenu("File");
    menuBar.add(sMenu);

    JMenuItem saveLayersMenuItem = new JMenuItem("Save Layers");
    saveLayersMenuItem.addActionListener(this);
    saveLayersMenuItem.setActionCommand("saveLayers");
    sMenu.add(saveLayersMenuItem);

    JMenuItem exportTopImageMenuItem = new JMenuItem("Export Top Image");
    exportTopImageMenuItem.addActionListener(this);
    exportTopImageMenuItem.setActionCommand("exportTopImage");
    sMenu.add(exportTopImageMenuItem);

    JMenuItem loadEntireExistingImageMenuItem = new JMenuItem("Open Existing Project");
    loadEntireExistingImageMenuItem.addActionListener(this);
    loadEntireExistingImageMenuItem.setActionCommand("loadLayeredImage");
    sMenu.add(loadEntireExistingImageMenuItem);

    JMenuItem loadImageMenuItem = new JMenuItem("Load Image Into Layer");
    loadImageMenuItem.addActionListener(this);
    loadImageMenuItem.setActionCommand("loadImage");
    sMenu.add(loadImageMenuItem);

    JMenuItem scriptMenuItem = new JMenuItem("Insert Script");
    scriptMenuItem.addActionListener(this);
    scriptMenuItem.setActionCommand("script");
    sMenu.add(scriptMenuItem);

    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    JMenuItem sepiaMenuItem = new JMenuItem("Sepia");
    sepiaMenuItem.addActionListener(this);
    sepiaMenuItem.setActionCommand("sepia");
    editMenu.add(sepiaMenuItem);

    JMenuItem sharpenMenuItem = new JMenuItem("Sharpen");
    sharpenMenuItem.addActionListener(this);
    sharpenMenuItem.setActionCommand("sharpen");
    editMenu.add(sharpenMenuItem);

    JMenuItem blurMenuItem = new JMenuItem("Blur");
    blurMenuItem.addActionListener(this);
    blurMenuItem.setActionCommand("blur");
    editMenu.add(blurMenuItem);

    JMenuItem greyscaleMenuItem = new JMenuItem("Greyscale");
    greyscaleMenuItem.addActionListener(this);
    greyscaleMenuItem.setActionCommand("greyscale");
    editMenu.add(greyscaleMenuItem);

    JMenu layerMenu = new JMenu("Layer");
    menuBar.add(layerMenu);

    JMenuItem createRainbowMenuItem = new JMenuItem("Insert Rainbow Image");
    createRainbowMenuItem.addActionListener(this);
    createRainbowMenuItem.setActionCommand("createRainbow");
    layerMenu.add(createRainbowMenuItem);

    JMenuItem removeLayerMenuItem = new JMenuItem("Remove Layer");
    removeLayerMenuItem.addActionListener(this);
    removeLayerMenuItem.setActionCommand("removeButton");
    layerMenu.add(removeLayerMenuItem);

    JMenuItem createLayerMenuItem = new JMenuItem("Create Layer");
    createLayerMenuItem.addActionListener(this);
    createLayerMenuItem.setActionCommand("createLayerInput");
    layerMenu.add(createLayerMenuItem);

    JMenuItem createCopyLayerMenuItem = new JMenuItem("Duplicate Current Layer");
    createCopyLayerMenuItem.addActionListener(this);
    createCopyLayerMenuItem.setActionCommand("createCopyLayer");
    layerMenu.add(createCopyLayerMenuItem);

    JMenuItem toggleVisibilityMenuItem = new JMenuItem("Toggle Visibility");
    toggleVisibilityMenuItem.addActionListener(this);
    toggleVisibilityMenuItem.setActionCommand("toggleVisibility");
    layerMenu.add(toggleVisibilityMenuItem);

    JMenuItem setCurrentLayerMenuItem = new JMenuItem("Set Current Layer");
    setCurrentLayerMenuItem.addActionListener(this);
    setCurrentLayerMenuItem.setActionCommand("setCurrent");
    layerMenu.add(setCurrentLayerMenuItem);

    this.setJMenuBar(menuBar);
  }

  private void initActionEvents() {
    actionEvents.putIfAbsent("setCurrent", new ActionEventSetCurrent());
    actionEvents.putIfAbsent("sepia", new SepiaActionEvent());
    actionEvents.putIfAbsent("sharpen", new SharpenActionEvent());
    actionEvents.putIfAbsent("blur", new BlurActionEvent());
    actionEvents.putIfAbsent("greyscale", new GreyscaleActionEvent());
    actionEvents.putIfAbsent("createRainbow", new CreateRainbowActionEvent());
    actionEvents.putIfAbsent("removeButton", new RemoveActionEvent());
    actionEvents.putIfAbsent("createLayerInput", new CreateLayerActionEvent());
    actionEvents.putIfAbsent("createCopyLayer", new CreateCopyLayerActionEvent());
    actionEvents.putIfAbsent("toggleVisibility", new ToggleVisibilityActionEvent());
    actionEvents.putIfAbsent("script", new ScriptActionEvent());
    actionEvents.putIfAbsent("loadImage", new LoadImageActionEvent());
    actionEvents.putIfAbsent("exportTopImage", new ExportTopImageActionEvent());
    actionEvents.putIfAbsent("saveLayers", new SaveLayersImageActionEvent());
    actionEvents.putIfAbsent("loadLayeredImage", new LoadLayeredImageActionEvent());
  }

  private void initWestPanel() {
    JPanel westSidePanel = new JPanel();
    westSidePanel.setLayout(new BoxLayout(westSidePanel, BoxLayout.PAGE_AXIS));
    westSidePanel.setBorder(BorderFactory.createTitledBorder("Layered Image Commands"));
    mainPanel.add(westSidePanel, BorderLayout.WEST);

    //sepia
    sepia.setActionCommand("sepia");
    sepia.addActionListener(this);
    westSidePanel.add(sepia);

    //sharpen
    sharpen.setActionCommand("sharpen");
    sharpen.addActionListener(this);
    westSidePanel.add(sharpen);

    //blur
    blur.setActionCommand("blur");
    blur.addActionListener(this);
    westSidePanel.add(blur);

    //greyscale
    greyscale.setActionCommand("greyscale");
    greyscale.addActionListener(this);
    westSidePanel.add(greyscale);

    //createRainbow
    createRainbow.setActionCommand("createRainbow");
    createRainbow.addActionListener(this);
    westSidePanel.add(createRainbow);

    //toggleVisibility
    toggleVisibility.setActionCommand("toggleVisibility");
    toggleVisibility.addActionListener(this);
    westSidePanel.add(toggleVisibility);

    //removeButtonDisplay
    removeLayerButton.setActionCommand("removeButton");
    removeLayerButton.addActionListener(this);
    westSidePanel.add(removeLayerButton);
  }

  private void initNorthPanel() {

    JButton loadEntireImageButton = new JButton("Open Existing Project");

    JPanel northSidePanel = new JPanel();
    northSidePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    mainPanel.add(northSidePanel, BorderLayout.NORTH);

    //loadEntireLayeredImageIn
    loadEntireImageButton.setActionCommand("loadLayeredImage");
    loadEntireImageButton.addActionListener(this);
    northSidePanel.add(loadEntireImageButton);

    //saveLayeredImage
    saveLayersButton.setActionCommand("saveLayers");
    saveLayersButton.addActionListener(this);
    northSidePanel.add(saveLayersButton);

    //exportTopImage - add the functionality for it
    exportTopImageButton.setActionCommand("exportTopImage");
    exportTopImageButton.addActionListener(this);
    northSidePanel.add(exportTopImageButton);

    //load image
    loadImage.setActionCommand("loadImage");
    loadImage.addActionListener(this);
    northSidePanel.add(loadImage);

    //insert script
    script.setActionCommand("script");
    script.addActionListener(this);
    northSidePanel.add(script);
  }

  @Override
  public void setLayers(List<String> layerNames) {
    if (layerNames == null) {
      throw new IllegalArgumentException("The passed in layer names cannot be null!");
    }

    int selected = listOfLayer.getSelectedIndex();

    String[] updatedData = new String[layerNames.size()];
    for (int i = 0; i < layerNames.size(); i++) {
      if (layerNames.get(i) == null) {
        throw new IllegalArgumentException("The layers inside of the layer list cannot be null!");
      }
      updatedData[i] = layerNames.get(i);
    }

    listOfLayer.setListData(updatedData);

    listOfLayer.setSelectedIndex(selected);
  }


  @Override
  public void setTopImage(BufferedImage img) {
    if (img == null) {
      picLabel.setIcon(new ImageIcon());
    } else {
      picLabel.setIcon(new ImageIcon(img));
    }

  }

  @Override
  public void addViewEventListener(IViewListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("The listener you want to add cannot be null!");
    }
    listeners.add(listener);
  }

  @Override
  public void promptMessage(String message, String title) {
    if (message == null || title == null) {
      throw new IllegalArgumentException("The provided message or title cannot be null!");
    }

    if (message.equals("") || title.equals("")) {
      throw new IllegalArgumentException("The provided message or title cannot be empty!");
    }

    JOptionPane.showMessageDialog(GraphicalLayeredImageView.this,
        message, title, JOptionPane.PLAIN_MESSAGE);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("The action event cannot be null!");
    }

    IActionEvent event = actionEvents.getOrDefault(e.getActionCommand(), null);

    if (event != null) {
      event.apply();
    } else {
      throw new IllegalStateException("The action performed is null!");
    }
  }


  /**
   * Represents an IActionEvent interface that provides the correct functionality for the function
   * objects that implement this interface and provided the correct actions for when specific action
   * events are triggered by user input from the graphical view.
   */
  private interface IActionEvent {

    /**
     * Carries out the correct functionality that should occur when the specific function object is
     * called due to the action event that is triggered by the specific user interaction that a
     * client has with the graphical view.
     */
    void apply();
  }

  /**
   * Represents an ActionEventSetCurrent function object that carries out the correct action when a
   * client triggers the set current action event by their activity in the graphical view.
   */
  private class ActionEventSetCurrent implements IActionEvent {

    @Override
    public void apply() {
      String selected =
          JOptionPane.showInputDialog("Please enter the name of the layer you want to select!");
      for (IViewListener listener : listeners) {
        int index = listener.handleSetCurrent(selected);
        if (index != -1) {
          listOfLayer.setSelectedIndex(index);
        }
      }
    }
  }

  /**
   * Represents an CreateLayerActionEvent function object that carries out the correct action when a
   * client triggers the create layer action event by their activity in the graphical view.
   */
  private class CreateLayerActionEvent implements IActionEvent {

    @Override
    public void apply() {
      String layerName =
          JOptionPane
              .showInputDialog("Please enter the name of the layer you want to create!");
      for (IViewListener listener : listeners) {
        listener.handleCreateLayer(layerName);
      }
    }
  }

  /**
   * Represents an CreateRainbowActionEvent function object that carries out the correct action when
   * a client triggers the create rainbow action event by their activity in the graphical view.
   */
  private class CreateRainbowActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleCreateRainbow();
      }
    }
  }

  /**
   * Represents an ToggleVisibilityActionEvent function object that carries out the correct action
   * when a client triggers the toggle visibility action event by their activity in the graphical
   * view.
   */
  private class ToggleVisibilityActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleToggleVisibility();
      }
    }
  }

  /**
   * Represents an SepiaActionEvent function object that carries out the correct action when a
   * client triggers the sepia action event by their activity in the graphical view.
   */
  private class SepiaActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleSepia();
      }
    }
  }

  /**
   * Represents an BlurActionEvent function object that carries out the correct action when a client
   * triggers the blur action event by their activity in the graphical view.
   */
  private class BlurActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleBlur();
      }
    }
  }

  /**
   * Represents an RemoveActionEvent function object that carries out the correct action when a
   * client triggers the remove action event by their activity in the graphical view.
   */
  private class RemoveActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleRemoveLayer();
      }
    }
  }

  /**
   * Represents an GreyscaleActionEvent function object that carries out the correct action when a
   * client triggers the greyscale action event by their activity in the graphical view.
   */
  private class GreyscaleActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleGreyscale();
      }
    }
  }

  /**
   * Represents an SharpenActionEvent function object that carries out the correct action when a
   * client triggers the sharpen action event by their activity in the graphical view.
   */
  private class SharpenActionEvent implements IActionEvent {

    @Override
    public void apply() {
      for (IViewListener listener : listeners) {
        listener.handleSharpen();
      }
    }
  }

  /**
   * Represents an CreateCopyLayerActionEvent function object that carries out the correct action
   * when a client triggers the create copy layer action event by their activity in the graphical
   * view.
   */
  private class CreateCopyLayerActionEvent implements IActionEvent {

    @Override
    public void apply() {
      String name =
          JOptionPane.showInputDialog("Please enter the name you want for your new copied layer!");
      for (IViewListener listener : listeners) {
        listener.handleCopyCurrentLayer(name);
      }
    }
  }

  /**
   * Represents an LoadImageActionEvent function object that carries out the correct action when a
   * client triggers the laod image action event by their activity in the graphical view.
   */
  private class LoadImageActionEvent implements IActionEvent {

    @Override
    public void apply() {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "JPG, PNG, and PPM Images", "jpg", "png", "ppm");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(GraphicalLayeredImageView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        for (IViewListener listener : listeners) {
          listener.handleLoadImage(f.getPath());
        }
      }
    }
  }


  /**
   * Represents an ExportTopImageActionEvent function object that carries out the correct action
   * when a client triggers the export top image action event by their activity in the graphical
   * view.
   */
  private class ExportTopImageActionEvent implements IActionEvent {

    @Override
    public void apply() {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(GraphicalLayeredImageView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        for (IViewListener listener : listeners) {
          listener.handleExportTopImage(f.getPath());
        }
      }
    }
  }

  /**
   * Represents an LoadLayeredImageActionEvent function object that carries out the correct action
   * when a client triggers the load entire layered image action event by their activity in the
   * graphical view.
   */
  private class LoadLayeredImageActionEvent implements IActionEvent {

    @Override
    public void apply() {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "TXT file", "txt");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(GraphicalLayeredImageView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        for (IViewListener listener : listeners) {
          listener.handleLoadEntireImage(f.getPath());
        }
      }
    }
  }

  /**
   * Represents an SaveLayersImageActionEvent function object that carries out the correct action
   * when a client triggers the save layered image action event by their activity in the graphical
   * view.
   */
  private class SaveLayersImageActionEvent implements IActionEvent {

    @Override
    public void apply() {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(GraphicalLayeredImageView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        for (IViewListener listener : listeners) {
          listener.handleSaveLayers(f.getPath());
        }
      }
    }
  }

  /**
   * Represents an ScriptActionEvent function object that carries out the correct action when a
   * client triggers the script action event by their activity in the graphical view.
   */
  private class ScriptActionEvent implements IActionEvent {

    @Override
    public void apply() {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "TXT file", "txt");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(GraphicalLayeredImageView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        for (IViewListener listener : listeners) {
          listener.handleScript(f.getPath());
        }
      }
    }
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {

    String selectedValue = listOfLayer.getSelectedValue();
    if (selectedValue == null) {
      return;
    }
    String[] tokens = selectedValue.split(": ");
    if (tokens.length < 2) {
      throw new IllegalStateException("Layer string should always have a colon in it");
    }
    String layerName = "";
    for (int i = 1; i < tokens.length; i++) {
      layerName += tokens[i];
    }

    for (IViewListener listener : listeners) {
      listener.handleSetCurrent(layerName);
    }
  }

  @Override
  public void startView() {
    this.setVisible(true);
  }

}



