package pss.core.ui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

public class JPssTextLabel extends JPssLabel {

  private ImageIcon backgroundImage = null;
  private boolean antialiasing = true;

  public JPssTextLabel() {
    super();
  }

  @Override
	public void setBackgroundImage(ImageIcon zIcon) {
    backgroundImage = zIcon;
  }

  @Override
	public void setAntialiasing(boolean zAntialias) {
    antialiasing = zAntialias;
  }

  @Override
	public void paintComponent(Graphics g) {

    if (backgroundImage != null) {
      int imageWidth = backgroundImage.getIconWidth();
      int imageHeight = backgroundImage.getIconHeight();

      int w = this.getWidth();
      int h = this.getHeight();

      for (int i = 0; i < h + imageHeight; i = i + imageHeight ) {
        for (int j = 0; j < w + imageWidth; j = j + imageWidth) {
          backgroundImage.paintIcon(this, g, j, i);
        }
      }
    }

    Graphics2D g2D = (Graphics2D)g;

    if (antialiasing)
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    else
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

    super.paintComponent(g2D);
  }
}
