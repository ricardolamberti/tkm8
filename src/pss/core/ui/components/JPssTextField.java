package pss.core.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;



public class JPssTextField extends JPanel {

  private ImageIcon image = null;
  private JPssLabel label = new JPssLabel();

  public JPssTextField() throws Exception {

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setImage(ImageIcon zBackground) {
    image = zBackground;
  }

  public void setTextForeground(Color zColor) {
    label.setForeground(zColor);
  }

  public void setTextBackground(Color zColor) {
    label.setBackground(zColor);
  }

  public void setTextFont(Font zFont) {
    label.setFont(zFont);
  }

  @Override
	public void setBounds(int x, int y, int zWidth, int zHeight) {
    super.setBounds(x, y, zWidth, zHeight);
    label.setBounds(5,5,zWidth - 10, zHeight - 10);
  }

  public void setText(String zText) {
    label.setText(zText);
  }

  public String getText() {
    return label.getText();
  }

  public JPssLabel getTextField() {
    return label;
  }


  @Override
	public void paint(Graphics g) {
    super.paint(g);
    if (image != null) g.drawImage(image.getImage(), 0, 0 ,this);
	}

  private void jbInit() throws Exception {
    label.setBounds(new Rectangle(5, 7, 274, 40));
    label.setBorder(new CompoundBorder());
    label.setHorizontalAlignment(SwingConstants.RIGHT);
    this.setLayout(null);
    this.setOpaque(false);
    this.add(label, null);
  }
}
