package pss.core.ui.components;

/**
 * An interface with the protocol of both a RootPaneContainer and a Window, to
 * be implemented polymorphically by either a JWindow or a JFrame.
 *
 * @author
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.WindowListener;

import javax.swing.Icon;
import javax.swing.RootPaneContainer;

public interface PssRootPaneWindow extends RootPaneContainer {

  public void addWindowListener(WindowListener l);
  public void removeWindowListener(WindowListener l);
  public void setDefaultCloseOperation(int operation);

  public void setCursor(Cursor cursor);

  public void hide();
  public void show();
  public void setVisible(boolean zVisible);
  public void dispose();

  public boolean isShowing();
  public boolean isVisible();

  public void pack();

  public void doLayout();

  public int getWidth();
  public int getHeight();
  public void setSize(int width, int height);

  public void toFront();
  public void toBack();

  public void setSkineable(boolean zIsSkineable);
  public boolean isSkineable();

  public Icon getIcon();
  public void setIcon(Icon zIcon);

  public Color getForeground();
  public void setForeground(Color zForeground);
  public Color getInactiveForegroundColor();
  public void setInactiveForegroundColor(Color zInactiveForeground);

  public Font getFont();
  public void setFont(Font zFont);
  public Font getInactiveFont();
  public void setInactiveFont(Font zInactiveFont);

  public void setIgnoreRepaint(boolean zIgnore);
  public boolean getIgnoreRepaint();


}
