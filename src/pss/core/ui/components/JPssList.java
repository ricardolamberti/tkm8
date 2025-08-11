package pss.core.ui.components;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.border.Border;

public class JPssList extends JList {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private ImageIcon backgroundImage = null;
  private Border cellBorder = null;
  private Border cellFocusBorder = null;
  private ImageIcon cellBackgroundImage = null;
  private ImageIcon cellFocusBackgroundImage = null;
  private ImageIcon cellSelectedBackgroundImage = null;
  private Font selectedFont = null;
  private boolean antialiasing = false;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssList() {
    super();
    this.createDefaultRenderer();
  }
  public JPssList(ListModel dataModel) {
    super(dataModel);
    this.createDefaultRenderer();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public ImageIcon getBackgroundImage() {
    return this.backgroundImage;
  }
  public void setBackgroundImage(ImageIcon zBackgroudImage) {
    this.backgroundImage = zBackgroudImage;
    //
    this.repaint();
  }
  public Border getCellFocusBorder() {
    return this.cellFocusBorder;
  }
  public void setCellFocusBorder(Border zBorder) {
    this.cellFocusBorder = zBorder;
    //
    this.repaint();
  }
  public ImageIcon getCellSelectedBackgroundImage() {
    return this.cellSelectedBackgroundImage;
  }
  public void setCellSelectedBackgroundImage(ImageIcon zImage) {
    this.cellSelectedBackgroundImage = zImage;
    //
    this.repaint();
  }
  public ImageIcon getCellFocusBackgroundImage() {
    return this.cellFocusBackgroundImage;
  }
  public void setCellFocusBackgroundImage(ImageIcon zImage) {
    this.cellFocusBackgroundImage = zImage;
    //
    this.repaint();
  }
  public boolean isAntialiasing() {
    return this.antialiasing;
  }
  public void setAntialiasing(boolean zAntialiasing) {
    this.antialiasing = zAntialiasing;
    //
    this.repaint();
  }
  public ImageIcon getCellBackgroundImage() {
    return this.cellBackgroundImage;
  }
  public void setCellBackgroundImage(ImageIcon zIcon) {
    this.cellBackgroundImage = zIcon;
    //
    this.repaint();
  }
  public void setSelectedFont(Font zFont) {
    this.selectedFont = zFont;
    //
    this.repaint();
  }
  public Font getSelectedFont() {
    return this.selectedFont != null ? this.selectedFont : this.getFont();
  }
  public void setCellBorder(Border zBorder) {
    this.cellBorder = zBorder;
    //
    this.repaint();
  }
  public Border getCellBorder() {
    return this.cellBorder;
  }

  protected void createDefaultRenderer() {
//    this.setCellRenderer(new PssListCellRenderer());
  }
//
//  @Override
//	protected void paintComponent(Graphics g) {
//    UITools.imaging().paintBackgroundImage(this, this.getBackgroundImage(), g);
//    super.paintComponent(g);
//  }

}
