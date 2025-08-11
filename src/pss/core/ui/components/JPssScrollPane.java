package pss.core.ui.components;

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class JPssScrollPane extends JScrollPane {

  private ImageIcon oBackground = null;

  public JPssScrollPane() {
  }
  public JPssScrollPane(Component view) {
    super(view);
  }
  public JPssScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
    super(view, vsbPolicy, hsbPolicy);
  }

  public void setBackgroundImage(ImageIcon anImage) {
    this.oBackground = anImage;
  }
  public ImageIcon getBackgroundImage() {
    return this.oBackground;
  }

//  @Override
//	public void paintComponent(Graphics g){
//    UITools.imaging().paintBackgroundImage(this, this.getBackgroundImage(), g);
//    super.paintComponent(g);
//  }
  @Override
	public JScrollBar createHorizontalScrollBar() {
    return new ScrollBar(Adjustable.HORIZONTAL);
  }
  @Override
	public JScrollBar createVerticalScrollBar() {
    return new ScrollBar(Adjustable.VERTICAL);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INNER CLASSES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  protected class ScrollBar extends JPssScrollBar implements UIResource {
//    private boolean unitIncrementSet;
//    private boolean blockIncrementSet;
//    public ScrollBar(int orientation) {
//      super(orientation);
//    }
//    @Override
//		public void setUnitIncrement(int unitIncrement) {
//      unitIncrementSet = true;
//      super.setUnitIncrement(unitIncrement);
//    }
//    @Override
//		public int getUnitIncrement(int direction) {
//      JViewport vp = getViewport();
//      if (!unitIncrementSet && (vp != null) &&
//            (vp.getView() instanceof Scrollable)) {
//        Scrollable view = (Scrollable)(vp.getView());
//        Rectangle vr = vp.getViewRect();
//        return view.getScrollableUnitIncrement(vr, getOrientation(), direction);
//      } else {
//        return super.getUnitIncrement(direction);
//      }
//    }
//    @Override
//		public void setBlockIncrement(int blockIncrement) {
//        blockIncrementSet = true;
//        super.setBlockIncrement(blockIncrement);
//    }
//    @Override
//		public int getBlockIncrement(int direction) {
//      JViewport vp = getViewport();
//      if (blockIncrementSet || vp == null) {
//        return super.getBlockIncrement(direction);
//      } else if (vp.getView() instanceof Scrollable) {
//        Scrollable view = (Scrollable)(vp.getView());
//        Rectangle vr = vp.getViewRect();
//        return view.getScrollableBlockIncrement(vr, getOrientation(), direction);
//      } else if (getOrientation() == VERTICAL) {
//        return vp.getExtentSize().height;
//      } else {
//        return vp.getExtentSize().width;
//      }
//    }
//  }
  
	public int getButtomY() {
		return this.getY()+this.getHeight();
	}


}
