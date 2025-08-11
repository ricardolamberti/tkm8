package pss.core.ui.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pss.common.regions.multilanguage.JLanguage;

public class JPssLabel extends JLabel {

  private boolean antialiasing;
  private ImageIcon backgroundImage = null;
  private String sOriginalText;

  /*********************************************************
  * CONSTRUCTOR
  ********************************************************/
  public JPssLabel() {
    super();
  }

  public JPssLabel(Icon zIcon) {
    super(zIcon);
  }

  public JPssLabel(String zText) {
    super(zText);
    sOriginalText = zText;
  }

  public JPssLabel(String zText, Icon zIcon, int zAlign ) {
    super(JLanguage.translate(zText),zIcon,zAlign);
    sOriginalText=zText;
  }

  public JPssLabel(String zText, int zAlign ) {
    super(JLanguage.translate(zText),zAlign);
  }

  @Override
	public void setText( String zLabel ) {
    sOriginalText = zLabel;
    super.setText(JLanguage.translate(zLabel));
  }
  public void setUntranslatedText(String zText) {
    super.setText(zText);
  }

  @Override
	public void setToolTipText( String zTip ) {
    super.setToolTipText(JLanguage.translate(zTip));
  }

  public String getOriginalText() {
    return sOriginalText;
  }


  /*********************************************************
  * SETTER METHODS
  ********************************************************/
  public void setAntialiasing(boolean aValue) {
    antialiasing = aValue;
  }
  public boolean isAntialiasing() {
    return this.antialiasing;
  }

  public ImageIcon getBackgroundImage() {
    return backgroundImage;
  }
  public void setBackgroundImage(ImageIcon anIcon) {
    backgroundImage = anIcon;
  }

  /*********************************************************
  * UTILITY PUBLIC METHODS
  ********************************************************/
  public void clear() {
    this.setText("");
  }
  /**
   * Clear the skin properties
   */
  public void cleanUpSkinProperties() {
    this.clear();
    this.backgroundImage = null;
  }

/*  public void backspace() {
    if (this.getText().length() == 0) return;
    this.setText(this.getText().substring(0,this.getText().length()-1));
  }*/

//  /*****************************************************************
//  * OVERRIDE OF PAINT METHOD
//  ******************************************************************/
//  @Override
//	public void paintComponent(Graphics g) {
//
//    UITools.imaging().paintBackgroundImage(this, backgroundImage, g);
//
//    Graphics2D g2D = (Graphics2D)g;
//    if (antialiasing) {
//      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//    } else {
//      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//    }
//
//    super.paintComponent(g2D);
//  }

	public int getButtomY() {
		return this.getY()+this.getHeight();
	}


}
