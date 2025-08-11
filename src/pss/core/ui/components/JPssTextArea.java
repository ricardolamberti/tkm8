package pss.core.ui.components;

/**
 * A JTextArea which supports antialiasing.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextArea;

public class JPssTextArea extends JTextArea {

  private boolean antialiasing;

  public JPssTextArea() {
  }
  public boolean isAntialiasing() {
    return antialiasing;
  }
  public void setAntialiasing(boolean antialiasing) {
    this.antialiasing = antialiasing;
  }

  @Override
	public void paintComponent(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    if (this.isAntialiasing()) {
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    } else {
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    super.paintComponent(g);
  }
}
