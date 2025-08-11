package pss.core.ui.components;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;


public class JPssSplitPane extends JSplitPane {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private ImageIcon backgroundImage;
  private ImageIcon dividerImage;
  private ImageIcon focusedDividerImage;
  private boolean dividerLocatesProportionally;
  
  public final static int MINIMIZE_A = 0;
  public final static int NORMAL = 1;
  public final static int MINIMIZE_B = 2;
  
  private int estado=NORMAL;
  public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
		this.getLeftComponent().setVisible(true);
		this.getRightComponent().setVisible(true);
		if (estado==MINIMIZE_A) this.getLeftComponent().setVisible(false);
		if (estado==MINIMIZE_B) this.getRightComponent().setVisible(false);
	}
	public boolean isDividerLocatesProportionally() {
		return dividerLocatesProportionally;
	}
	private double proportionalLocation;

  public double getProportionalLocation() {
		return proportionalLocation;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssSplitPane() {
    super();
  }
  public JPssSplitPane(int newOrientation) {
    super(newOrientation);
  }
  public JPssSplitPane(int newOrientation, boolean newContinuousLayout) {
    super(newOrientation, newContinuousLayout);
  }
  public JPssSplitPane(int newOrientation, Component newLeftComponent,
                    Component newRightComponent) {
    super(newOrientation, newLeftComponent, newRightComponent);
  }
  public JPssSplitPane(int newOrientation, boolean newContinuousLayout,
                      Component newLeftComponent, Component newRightComponent) {
    super(newOrientation, newContinuousLayout,
                newLeftComponent, newRightComponent);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public ImageIcon getDividerImage() {
    return dividerImage;
  }
  public void setDividerImage(ImageIcon dividerImage) {
    this.dividerImage = dividerImage;
  }
  public ImageIcon getFocusedDividerImage() {
    return focusedDividerImage;
  }
  public void setFocusedDividerImage(ImageIcon focusedDividerImage) {
    this.focusedDividerImage = focusedDividerImage;
  }
  public ImageIcon getBackgroundImage() {
    return backgroundImage;
  }
  public void setBackgroundImage(ImageIcon backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

//  @Override
//	protected void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    UITools.imaging().paintBackgroundImage(this, this.getBackgroundImage(), g);
//  }

	public String getParentName() {
		Component comp=getParent();
		if (comp==null) return ""; 
		while (comp.getName()==null) {
			if (comp.getParent()==null) return ""; 
			if (comp.getParent().getName()!=null) return comp.getParent().getName();
			comp=comp.getParent();
		}
		return "";
	}

  @Override
  public String getName() {
  	if (super.getName()==null)
  		setName("splitpane-"+getParentName());
  	return super.getName();
  }
  @Override
  public int getDividerLocation() {
  	if (dividerLocatesProportionally) {
  		setDividerLocation(proportionalLocation);
  	}
  	return super.getDividerLocation();
  }
  
  @Override
	public void setDividerLocation(int location) {
//    if (this.getWidth() > this.getDividerSize() && location >= 0) {
      super.setDividerLocation(location);
//      this.proportionalLocation =
//        this.getOrientation() == HORIZONTAL_SPLIT ?
//          ((double) Math.max(this.getWidth(), location)) / (double) this.getWidth() :
//          ((double) Math.max(this.getHeight(), location)) / (double) this.getHeight();
      this.dividerLocatesProportionally = false;
//    }
  }
  @Override
	public void setDividerLocation(double newProportionalLocation) {
    super.setDividerLocation(newProportionalLocation);
    this.proportionalLocation = newProportionalLocation;
    this.dividerLocatesProportionally = true;
  }
  @Override
	public void reshape(int x, int y, int w, int h) {
    if (this.dividerLocatesProportionally) {
      super.reshape(x, y, w, h);
      this.setDividerLocation(this.proportionalLocation);
    } else {
      super.reshape(x, y, w, h);
    }
  }



}
