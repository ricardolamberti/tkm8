package pss.core.ui.components;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.RootPaneContainer;

import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;

public class JPssComponentFactory {

//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  //
//  //   STATIC VARIABLES
//  //
//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  private static JPssComponentFactory instance;
//  public static final String PROPERTY_IS_SKINEABLE = "SKIN_IS_SKINEABLE";
//
//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  //
//  //   CONSTRUCTORS
//  //
//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  protected JPssComponentFactory() {
//  }
//
//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  //
//  //   METHODS
//  //
//  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  //
//  // singleton managing methods
//  //
//  public static JPssComponentFactory getInstance() {
//    if (JPssComponentFactory.instance == null) {
//      JPssComponentFactory.instance = new JPssComponentFactory();
//    }
//    return JPssComponentFactory.instance;
//  }
//
//  //
//  //  factory methods
//  //
//  public JPssPanel createPanel(boolean zIsSkineable) {
//    JPssPanel oPanel = new JPssPanel();
//    this.configureDefaults(oPanel, zIsSkineable);
//    return oPanel;
//  }
//  public JPssLayeredPanel createLayeredPanel(boolean zIsSkineable) {
//    JPssLayeredPanel oPanel = new JPssLayeredPanel();
//    this.configureDefaults(oPanel, zIsSkineable);
//    return oPanel;
//  }
//  public JPssLabel createLabel(boolean zIsSkineable) {
//    JPssLabel oLabel = new JPssLabel();
//    this.configureDefaults(oLabel, zIsSkineable);
//    return oLabel;
//  }
//  public JTextArea createTextArea(boolean zIsSkineable) {
//    JTextArea oText = new JTextArea();
//    this.configureDefaults(oText, zIsSkineable);
//    return oText;
//  }
//  public JViewport createViewport(boolean zIsSkineable) {
//    JViewport oView = new JViewport();
//    this.configureDefaults(oView, zIsSkineable);
//    return oView;
//  }
////  public JPssWindow createWindow(boolean zIsSkineable) {
////    JPssWindow oWindow = JPssWindow.createTopLevelWindow();
////    this.basicConfigureWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
////  public JPssWindow createWindow(String title, boolean zIsSkineable) {
////    JPssWindow oWindow = JPssWindow.createTopLevelWindow(title);
////    this.basicConfigureWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
////  public JPssWindow createLayeredWindow(String title, boolean zIsSkineable) {
////    JPssWindow oWindow = JPssWindow.createTopLevelWindow(title);
////    this.basicConfigureLayeredWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
////  public JPssFrame createFrame(boolean zIsSkineable) {
////    JPssFrame oFrame = new JPssFrame();
////    this.basicConfigureWindow(oFrame, zIsSkineable);
////    return oFrame;
////  }
////  public JPssFrame createFrame(String title, boolean zIsSkineable) {
////    JPssFrame oFrame = new JPssFrame(title);
////    this.basicConfigureWindow(oFrame, zIsSkineable);
////    return oFrame;
////  }
////  public JPssFrame createLayeredFrame(String title, boolean zIsSkineable) {
////    JPssFrame oFrame = new JPssFrame(title);
////    this.basicConfigureLayeredWindow(oFrame, zIsSkineable);
////    return oFrame;
////  }
////  public JPssWindow createLayeredDesktopWindow(String title, boolean zIsSkineable) {
////    JPssWindow oWindow = JPssWindow.createDesktopWindow(title);
////    this.basicConfigureLayeredWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
////  public JPssWindow createDesktopWindow(String title, boolean zIsSkineable) {
////    JPssWindow oWindow = JPssWindow.createDesktopWindow(title);
////    this.basicConfigureWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
////  public JPssInternalFrame createInternalFrame(boolean zIsSkineable) {
////    JPssInternalFrame oWindow = new JPssInternalFrame();
////    this.configureDefaults(oWindow, zIsSkineable);
////    JPssPanel contentPane = this.createPanel(zIsSkineable);
////    oWindow.setContentPane(contentPane);
////    return oWindow;
////  }
////  public JPssDialog createDialog(boolean zIsSkineable) {
////    JPssDialog oWindow = new JPssDialog();
////    this.basicConfigureWindow(oWindow, zIsSkineable);
////    return oWindow;
////  }
//  public JPssTree createTree(boolean zIsSkineable) {
//    JPssTree oTree = new JPssTree();
//    this.configureDefaults(oTree, zIsSkineable);
//    return oTree;
//  }
//  public JPssScrollPane createScrollPane(boolean zIsSkineable) {
//    JPssScrollPane oScrollPane = new JPssScrollPane();
//    this.configureDefaults(oScrollPane, zIsSkineable);
//    this.configureDefaults(oScrollPane.getViewport(), zIsSkineable);
//    oScrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
//    return oScrollPane;
//  }
//  public JPssSplitPane createSplitPane(boolean zIsSkineable) {
//    JPssSplitPane oSplitPane = new JPssSplitPane();
//    this.configureDefaults(oSplitPane, zIsSkineable);
//    oSplitPane.setAutoscrolls(true);
//    oSplitPane.setOneTouchExpandable(true);
//    return oSplitPane;
//  }
//  public JPssButton createButton(boolean zIsSkineable) {
//    JPssButton oButton = new JPssButton();
//    this.configureDefaults(oButton, zIsSkineable);
//    oButton.setAntialiasing(false);
//    return oButton;
//  }
////  public JBaseMenu createMenuBar(boolean zIsSkineable) {
////    JBaseMenu oMenu = new JBaseMenu();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssMenuItem createMenuItem(boolean zIsSkineable) {
////    JPssMenuItem oMenu = new JPssMenuItem();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssRadioButtonMenuItem createRadioButtonMenuItem(boolean zIsSkineable) {
////    JPssRadioButtonMenuItem oMenu = new JPssRadioButtonMenuItem();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssCheckBoxMenuItem createCheckBoxMenuItem(boolean zIsSkineable) {
////    JPssCheckBoxMenuItem oMenu = new JPssCheckBoxMenuItem();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssMenu createMenu(boolean zIsSkineable) {
////    JPssMenu oMenu = new JPssMenu();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssPopupMenu createPopUpMenu(boolean zIsSkineable) {
////    JPssPopupMenu oMenu = new JPssPopupMenu();
////    this.configureDefaults(oMenu, zIsSkineable);
////    return oMenu;
////  }
////  public JPssSeparator createMenuSeparator(boolean zIsSkineable) {
////    JPssSeparator oSeparator = new JPssSeparator();
////    this.configureDefaults(oSeparator, zIsSkineable);
////    return oSeparator;
////  }
////  public JToolBarContainer createToolBarContainer(boolean zScrollable, boolean zIsSkineable) {
////    JToolBarContainer container = zScrollable ?
////      new JScrollableToolBarContainer() :
////      new JToolBarContainer();
////    this.configureDefaults(container, zIsSkineable);
////    return container;
////  }
////  public JPssInfoPanel createInfoPanel(boolean zIsSkineable) {
////    JPssInfoPanel oInfoPanel = new JPssInfoPanel();
////    this.configureDefaults(oInfoPanel, zIsSkineable);
////    return oInfoPanel;
////  }
////  public JBotonBar createToolBar(boolean zIsSkineable) throws Exception {
////    JBotonBar oBar = new JBotonBar();
////    this.configureDefaults(oBar, zIsSkineable);
////    return oBar;
////  }
////  public JUserShortcutsBar createUserShortcutsToolBar(boolean zIsSkineable) throws Exception {
////    JUserShortcutsBar oBar = new JUserShortcutsBar();
////    this.configureDefaults(oBar, zIsSkineable);
////    return oBar;
////  }
//  public JFormFiltro createFilterBar(JWins zWins, boolean zIsSkineable) throws Exception {
//    JFormFiltro oBar = new JFormFiltro(zWins);
//    return oBar;
//  }
////  public JPssDesktop createPssDesktop(boolean zIsSkineable) throws Exception {
////    JPssDesktop oDesktop = new JPssDesktop();
////    this.configureDefaults(oDesktop, zIsSkineable);
////    return oDesktop;
////  }
//
//
//  //
//  //   inquiring methods
//  //
//  /**
//   * Answers whether the given component is marked to be skineable or not.
//   */
//  public boolean isSkineable(Component zComponent) {
//    boolean result = false;
//    if (zComponent instanceof JComponent) {
//      JComponent comp = (JComponent) zComponent;
//      Boolean isSkineableProperty = (Boolean) comp.getClientProperty(PROPERTY_IS_SKINEABLE);
//      result = isSkineableProperty == null || isSkineableProperty.booleanValue();
//    } else if (zComponent instanceof PssRootPaneWindow) {
//      result = ((PssRootPaneWindow)zComponent).isSkineable();
//    } else if (zComponent instanceof RootPaneContainer) {
//      result = this.isSkineable( ((RootPaneContainer)zComponent).getRootPane() );
//    }
//    return result;
//  }
//
//
//  //
//  //   internal configuration methods
//  //
//  private void basicConfigureWindow(RootPaneContainer zWindow, boolean zIsSkineable) {
//    // root pane
//    this.configureDefaults(zWindow.getRootPane(), false);
//    // layered pane
//    JPssLayeredPane layeredPane = new JPssLayeredPane();
//    this.configureDefaults(layeredPane, false);
//    // content pane
//    JPssPanel contentPane = this.createPanel(zIsSkineable);
//    // link components
//    zWindow.setLayeredPane(layeredPane);
//    zWindow.setContentPane(contentPane);
//  }
//  private void basicConfigureLayeredWindow(RootPaneContainer zWindow, boolean zIsSkineable) {
//    // root pane
//    this.configureDefaults(zWindow.getRootPane(), false);
//    // layered pane
//    JPssLayeredPane layeredPane = new JPssLayeredPane();
//    this.configureDefaults(layeredPane, false);
//    // content pane
//    JPssLayeredPanel contentPane = this.createLayeredPanel(zIsSkineable);
//    // link components
//    zWindow.setLayeredPane(layeredPane);
//    zWindow.setContentPane(contentPane);
//  }





}
