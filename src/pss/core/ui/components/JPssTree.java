package pss.core.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class JPssTree extends JTree {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private Border cellBorder = null;
  private Border cellFocusBorder = null;
  private ImageIcon cellBackgroundImage = null;
  private ImageIcon cellFocusBackgroundImage = null;
  private Font selectedFont = null;
  private boolean antialiasing = false;
  private Color selectionForeground = null;
  private Color selectionBackground = null;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssTree() {
    super();
    this.initialize();
  }
  public JPssTree(Object[] value) {
    super(value);
    this.initialize();
  }
  public JPssTree(Vector value) {
    super(value);
    this.initialize();
  }
  public JPssTree(Hashtable value) {
    super(value);
    this.initialize();
  }
  public JPssTree(TreeNode root) {
    super(root);
    this.initialize();
  }
  public JPssTree(TreeNode root, boolean asksAllowsChildren) {
    super(root, asksAllowsChildren);
    this.initialize();
  }
  public JPssTree(TreeModel newModel) {
    super(newModel);
    this.initialize();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //  API
  //
  public Color getSelectionForeground() {
    return this.selectionForeground;
  }
  public Color getSelectionBackground() {
    return this.selectionBackground;
  }
  public void setSelectionForeground(Color zForeground) {
    this.selectionForeground = zForeground;
    //
    this.repaint();
  }
  public void setSelectionBackground(Color zBackground) {
    this.selectionBackground = zBackground;
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

  
//  //
//  //  initialization methods
//  //
  protected void initialize() {
//    this.setCellRenderer(new PssTreeCellRenderer());
  }

}
