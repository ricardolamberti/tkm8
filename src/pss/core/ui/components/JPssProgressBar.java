package pss.core.ui.components;

/**
 * A Pss customized progrss bar.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

public class JPssProgressBar extends JProgressBar {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private Color textColor;
  private Color filledAreaTextColor;
  private ImageIcon backgroundImage;
  private ImageIcon progressImage;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssProgressBar() {
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public ImageIcon getBackgroundImage() {
    return this.backgroundImage;
  }
  public Color getTextColor() {
    return this.textColor;
  }
  public ImageIcon getProgressImage() {
    return this.progressImage;
  }
  public void setBackgroundImage(ImageIcon backgroundImage) {
    this.backgroundImage = backgroundImage;
  }
  public void setTextColor(Color textColor) {
    this.textColor = textColor;
  }
  public void setProgressImage(ImageIcon progressImage) {
    this.progressImage = progressImage;
  }
  public Color getFilledAreaTextColor() {
    return filledAreaTextColor;
  }
  public void setFilledAreaTextColor(Color filledAreaTextColor) {
    this.filledAreaTextColor = filledAreaTextColor;
  }

}
