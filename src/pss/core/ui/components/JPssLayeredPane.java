package pss.core.ui.components;

import javax.swing.JLayeredPane;


public class JPssLayeredPane extends JLayeredPane {

  public JPssLayeredPane() {
  }
/*
  protected void addImpl(Component comp, Object constraints, int index) {
    if (comp instanceof JPopupMenu) {
      this.add(comp, POPUP_LAYER);
    } else {
      super.addImpl(comp, constraints, index);
    }
  }

  /*
  public void paint(Graphics g) {
    synchronized (this) {
      Graphics copyGraphics = g.create();
      super.paint(copyGraphics);
      Rectangle clipRect = copyGraphics.getClipBounds();
      //
      Image i = this.createImage(this.getWidth(), this.getHeight());
      g.setClip(
        clipRect.x,
        clipRect.y,
        clipRect.width,
        clipRect.height
      );
      new ImageIcon(i).paintIcon(this, g, 0, 0);
    }

    /*
    CropImageFilter f = new CropImageFilter(
      clipRect.x,
      clipRect.y,
      clipRect.width,
      clipRect.height
      );
//		ImageProducer p = new java.awt.image.MemoryImageSource(
//		FilteredImageSource s = new FilteredImageSource();

  }
*/
}
