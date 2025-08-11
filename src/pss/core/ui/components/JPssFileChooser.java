package pss.core.ui.components;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * An overridden version of a JFileChooser, which may be shown in the Pss
 * desktop.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

public class JPssFileChooser extends JFileChooser {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private int returnValue = ERROR_OPTION;
  private ImageIcon backgroundImage;
  private ImageIcon dialogIcon;
  private boolean allowsFilenameEdit = true;
  private boolean allowsNewFolder = true;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssFileChooser() {
    this((File) null, (FileSystemView) null);
  }
  public JPssFileChooser(String currentDirectoryPath) {
    this(currentDirectoryPath, (FileSystemView) null);
  }
  public JPssFileChooser(File currentDirectory) {
    this(currentDirectory, (FileSystemView) null);
  }
  public JPssFileChooser(FileSystemView fsv) {
    this((File) null, fsv);
  }
  public JPssFileChooser(File currentDirectory, FileSystemView fsv) {
    super(currentDirectory, fsv);
  }
  public JPssFileChooser(String currentDirectoryPath, FileSystemView fsv) {
    super(currentDirectoryPath, fsv);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public ImageIcon getBackgroundImage() {
    return backgroundImage;
  }
  public void setBackgroundImage(ImageIcon backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  /**
   * Overridden from the superclass to configure input
   * @param view
   */
//  protected void setup(FileSystemView view) {
//    if(view == null) {
//      view = FileSystemView.getFileSystemView();
//    }
//    setFileSystemView(view);
//    updateUI();
//    if(isAcceptAllFileFilterUsed()) {
//      setFileFilter(getAcceptAllFileFilter());
//    }
//  }

//  @Override
//	public int showDialog(Component parent, String approveButtonText) {
//    // if no desktop is working...
//    if (!UITools.desktop().isDesktopActive()) {
//      return super.showDialog(parent, approveButtonText);
//    }
//    // ...otherwise...
//    try {
//      // set the custom approve text
//      if(approveButtonText != null) {
//        setApproveButtonText(approveButtonText);
//        setDialogType(CUSTOM_DIALOG);
//      }
//      // get the title
//      String title = getUI().getDialogTitle(this);
//      // configure input properties
//      this.configureInput();
//      // open in the Pss desktop
////      this.openInDesktop(title);
//      // remember the last directory used
//      this.rescanCurrentDirectory();
//      // answers the user return value
//      return this.returnValue;
//    } catch (Exception ex) {
//      PssLogger.logDebug(ex, "Couldn't open a file chooser using Pss Desktop");
//      return super.showDialog(parent, approveButtonText);
//    }
//  }
//
  private void configureInput() {
    // acá me tengo que fijar qué permisos tiene el usuario para ver carpetas o
    // archivos del sistema operativo
    this.setAllowsFilenameEdit(false);
    this.setAllowsNewFolder(false);
  }

  @Override
	public void approveSelection() {
    this.returnValue = APPROVE_OPTION;
//    this.closeFromDesktop();
    super.approveSelection();
  }
  @Override
	public void cancelSelection() {
    this.returnValue = CANCEL_OPTION;
//    this.closeFromDesktop();
    super.cancelSelection();
  }

//  private void closeFromDesktop() {
//    try {
//      if (UITools.desktop().isDesktopActive()) {
//        UITools.desktop().getCurrentDesktop().closeForm(this);
//      }
//    } catch (Exception ex) {
//      PssLogger.logDebug(ex, "Couldn't close a file chooser using Pss Desktop");
//    }
//  }

//  private void openInDesktop(String zTitle) throws Exception {
//    // configure the chooser with the desktop skin
//    JDesktopSkin.getDefault().configureDesktopFileChooser(this);
//
//    ImageIcon icon = getDialogIcon();
//    if (icon == null) {
//      // then, open it in adesktop frame
//      int iconNumber;
//      if (this.getDialogType()==SAVE_DIALOG) {
//        iconNumber = 13;
//      } else if (this.getDialogType()==OPEN_DIALOG) {
//        iconNumber = 9;
//      } else {  // CUSTOM_DIALOG
//        iconNumber = 1;
//      }
//      icon = GuiIconGalerys.GetGlobal().getIcon(GuiIconos.GetGlobal().buscarIcono(iconNumber).GetFile());
//    }
//
//    UITools.desktop().getCurrentDesktop().openForm(this, null, true, zTitle, icon, null);
//  }

//  @Override
//	protected void paintComponent(Graphics g) {
//    UITools.imaging().paintBackgroundImage(this, this.backgroundImage, g);
//    super.paintComponent(g);
//  }

//  @Override
//	public void setLayout(LayoutManager zLayoutManager) {
//    if (zLayoutManager instanceof BorderLayout &&
//        !UITools.layout().areEqual(zLayoutManager, this.getLayout()) ) {
//      super.setLayout(zLayoutManager);
//      if (this.getUI() instanceof PssFileChooserUI) {
//        ((PssFileChooserUI)this.getUI()).layoutFileChooser();
//      }
//    }
//  }
  public boolean isAllowsFilenameEdit() {
    return allowsFilenameEdit;
  }
  public boolean isAllowsNewFolder() {
    return allowsNewFolder;
  }
  public void setAllowsNewFolder(boolean allowsNewFolder) {
    boolean old = this.allowsNewFolder;
    this.allowsNewFolder = allowsNewFolder;
    firePropertyChange("allowsNewFolder", old, allowsNewFolder);
  }
  public void setAllowsFilenameEdit(boolean allowsFilenameEdit) {
    boolean old = this.allowsFilenameEdit;
    this.allowsFilenameEdit = allowsFilenameEdit;
    firePropertyChange("allowsFilenameEdit", old, allowsFilenameEdit);
  }
  public ImageIcon getDialogIcon() {
    return this.dialogIcon;
  }
  public void setDialogIcon(ImageIcon dialogIcon) {
    this.dialogIcon = dialogIcon;
  }

}
