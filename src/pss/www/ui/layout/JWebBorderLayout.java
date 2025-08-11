/*
 * Created on 14-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

import java.awt.Component;

import pss.core.winUI.controls.JFormControl;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;

/**
 * 
 * 
 * Created on 14-jul-2003
 * @author PSS
 */

public class JWebBorderLayout extends JWebCompositeLayout {


	private int topAligment = JFormControl.HALIGN_LEFT;
	private int bottomAligment = JFormControl.HALIGN_LEFT;
  private JWebViewComponent oTopComponent;
  private JWebViewComponent oBottomComponent;
  private JWebViewComponent oLeftComponent;
  private JWebViewComponent oRightComponent;
  private JWebViewComponent oCenterComponent;
  
//  private int extraAdjust = 0;
//
//  
//	public int getExtraAdjust() {
//		return extraAdjust;
//	}
//
//	
//	public void setExtraAdjust(int extraAdjust) {
//		this.extraAdjust=extraAdjust;
//	}
	public int getTopAligment() {
		return topAligment;
	}


	public void setTopAligment(int topAligment) {
		this.topAligment = topAligment;
	}


	public int getBottomAligment() {
		return bottomAligment;
	}


	public void setBottomAligment(int bottomAligment) {
		this.bottomAligment = bottomAligment;
	}


	public JWebBorderLayout() {
    super();
  }

  public JWebBorderLayout(int zHGap, int zVGap) {
    super(zHGap, zVGap);
  }

  @Override
	protected void doLayout(JWebViewComposite zComposite, Rectangle area) throws Exception {
  	this.doLayout(zComposite, area, true);
  }
  protected void doLayout(JWebViewComposite zComposite, Rectangle area, boolean verifyScroll) throws Exception {
    // layout top component
    int iTopOffset = 0;
    if (this.oTopComponent != null) {
      iTopOffset = JWebLayouts.getLayoutSizeOf(this.oTopComponent).height;
      if (iTopOffset==-1) iTopOffset=0;
      this.oTopComponent.setLocation(topAligment==JFormControl.HALIGN_LEFT||this.oTopComponent.getSize()==null?area.x:topAligment==JFormControl.HALIGN_RIGHT?area.width-this.oTopComponent.getSize().width:area.width-this.oTopComponent.getSize().width/2, area.y);
      this.oTopComponent.tryChangeSize(area.width, iTopOffset);
    }
    // layout bootom component
    int iBottomOffset = 0;
    if (this.oBottomComponent != null) {
      iBottomOffset = JWebLayouts.getLayoutSizeOf(this.oBottomComponent).height;
      if (iBottomOffset==-1) iBottomOffset=0;
      this.oBottomComponent.setLocation(topAligment==JFormControl.HALIGN_LEFT||this.oTopComponent.getSize()==null?area.x:topAligment==JFormControl.HALIGN_RIGHT?area.width-this.oTopComponent.getSize().width:area.width-this.oTopComponent.getSize().width/2, area.y + area.height - iBottomOffset);
      this.oBottomComponent.tryChangeSize(area.width, iBottomOffset);
    }
    // layout left component
    int iLeftOffset = 0;
    if (this.oLeftComponent != null) {
      iLeftOffset = JWebLayouts.getLayoutSizeOf(this.oLeftComponent).width;
      if (iLeftOffset==-1) iLeftOffset=0;
      this.oLeftComponent.setLocation(area.x, area.y + iTopOffset);
      this.oLeftComponent.tryChangeSize(iLeftOffset, area.height - iTopOffset - iBottomOffset);
    }
    // layout right component
    int iRightOffset = 0;
    if (this.oRightComponent != null) {
      iRightOffset = JWebLayouts.getLayoutSizeOf(this.oRightComponent).width;
      if (iRightOffset==-1) iRightOffset=0;
      this.oRightComponent.setLocation(area.x + area.width - iRightOffset, area.y + iTopOffset);
      this.oRightComponent.tryChangeSize(iRightOffset, area.height - iTopOffset - iBottomOffset);
    }
    // layout center component
    if (this.oCenterComponent==null && zComposite.getChildrenCount()==1
        && (iTopOffset==0 && iLeftOffset==0 && iBottomOffset==0 && iRightOffset==0)) {
      this.oCenterComponent = zComposite.getChild(0);
    }
    iTopOffset += this.getVGap();
    iBottomOffset += this.getVGap();
    iLeftOffset += this.getHGap();
    iRightOffset += this.getHGap();
    
    if (this.oCenterComponent != null) {
      this.oCenterComponent.setLocation(area.x + iLeftOffset, area.y + iTopOffset);
      this.oCenterComponent.tryChangeSize(area.width - iLeftOffset - iRightOffset , area.height - iTopOffset - iBottomOffset);
    }
//    if (verifyScroll) this.verifyScroll(area, zComposite);
    
  }

  private void verifyScroll(Rectangle area, JWebViewComposite c) throws Exception {
	  if (this.totalHeight()>area.height) {
	  	area.height=this.totalHeight();
	  	area.width=area.width-17;
	  	this.clearAllSize();
  		this.doLayout(c, area, false);
  		return;
	  } 
	  if (this.totalWidth()>area.width) {
	  	area.width=this.totalWidth();
	  	area.height=area.height-17;
	  	this.clearAllSize();
  		this.doLayout(c, area, false);
  		return;
	  }
  }
  
  private void clearAllSize() throws Exception {
  	if (this.oTopComponent!=null) this.oTopComponent.clearSize();
  	if (this.oBottomComponent!=null) this.oBottomComponent.clearSize();
  	if (this.oCenterComponent!=null) this.oCenterComponent.clearSize();
  	if (this.oLeftComponent!=null) this.oLeftComponent.clearSize();
  	if (this.oRightComponent!=null) this.oRightComponent.clearSize();
  }

//  private int verifyWidth(int w, JWebViewComponent c) throws Exception {
//  	if (c==null) return w;
//  	if (!c.isFixWidth()) return w;
//  	if (c.getSize().getWidth()>w)
//  		return c.getSize().width;
//  	return w;
//  }

//  private int verifyHeight(int h, JWebViewComponent c) throws Exception {
//  	if (c==null) return h;
//  	if (!c.isFixHight()) return h;
//  	if (c.getSize().getHeight()>h)
//  		return c.getSize().height;
//  	return h;
//  }
//
//  private boolean verifyFixWidth(Rectangle area) throws Exception {
//  	// si son compponentes con size fijo me quedo con el mayor
//  	int w=area.width;
//  	w=this.verifyWidth(w, this.oTopComponent);
//  	w=this.verifyWidth(w, this.oCenterComponent);
//  	w=this.verifyWidth(w, this.oBottomComponent);
//  	if (w<=area.width) return false;
//  	area.width=w;
//  	area.height=area.height-17;
//  	return true;
//  }
//
//  private boolean verifyFixHeight(Rectangle area) throws Exception {
//  	// si son compponentes con size fijo me quedo con el mayor
//  	int h=area.height;
//  	h=this.verifyHeight(h, this.oLeftComponent);
//  	h=this.verifyHeight(h, this.oRightComponent);
//  	h=this.verifyHeight(h, this.oCenterComponent);
//  	if (this.oTopComponent!=null) h+=JWebLayouts.getLayoutSizeOf(this.oTopComponent).height;
//  	if (this.oBottomComponent!=null) h+=JWebLayouts.getLayoutSizeOf(this.oBottomComponent).height;
//  	if (h<=area.height) return false;
//  	area.height=h;
//  	area.width=area.width-17;
//  	return true;
//  }
  private int totalHeight() throws Exception {
  	int t=0;
  	if (this.oTopComponent!=null) t+=this.oTopComponent.getSize().height;
  	if (this.oCenterComponent!=null) t+=this.oCenterComponent.getSize().height;
  	if (this.oBottomComponent!=null) t+=this.oBottomComponent.getSize().height;
  	return t;
  }
  
  private int totalWidth() throws Exception {
  	int t=0;
  	if (this.oLeftComponent!=null) t+=this.oLeftComponent.getSize().width;
  	if (this.oCenterComponent!=null) t+=this.oCenterComponent.getSize().width;
  	if (this.oRightComponent!=null) t+=this.oRightComponent.getSize().width;
  	return t;
  }
  
//	public void fixWidthScroll(JWebViewComponent c) throws Exception {
//		if (c==null) return;
//		c.getSize().width=c.getSize().width-c.getAjusteScrollInverso();
//	}
//	
//	public void fixHeightScroll(JWebViewComponent c) throws Exception {
//		if (c==null) return;
//		c.getSize().height=c.getSize().height-c.getAjusteScrollInverso();
//	}
//
  
//	public void verifyScrollWidth(Rectangle area) throws Exception {
//  	this.fixWidthScroll(this.oTopComponent);
//  	this.fixWidthScroll(this.oCenterComponent);
//  	this.fixWidthScroll(this.oBottomComponent);
//	}
//
//	public void verifyScrollHeight(Rectangle area) throws Exception {
//  	this.fixHeightScroll(this.oLeftComponent);
//  	this.fixHeightScroll(this.oCenterComponent);
//  	this.fixHeightScroll(this.oRightComponent);
//	}


  public JWebViewComponent getBottomComponent() {
    return this.oBottomComponent;
  }

  public JWebViewComponent getCenterComponent() {
    return this.oCenterComponent;
  }

  public JWebViewComponent getLeftComponent() {
    return this.oLeftComponent;
  }

  public JWebViewComponent getRightComponent() {
    return this.oRightComponent;
  }

  public JWebViewComponent getTopComponent() {
    return this.oTopComponent;
  }

  public void setBottomComponent(JWebViewComponent component) {
    this.oBottomComponent = component;
  }

  public void setCenterComponent(JWebViewComponent component) {
    this.oCenterComponent = component;
  }

  public void setLeftComponent(JWebViewComponent component) {
    this.oLeftComponent = component;
  }

  public void setRightComponent(JWebViewComponent component) {
    this.oRightComponent = component;
  }

  public void setTopComponent(JWebViewComponent component) {
    this.oTopComponent = component;
  }

  @Override
  public void takeLayoutAttributesFrom(LayoutManager layout) throws Exception {
  	BorderLayout lay = (BorderLayout)layout;
		this.setHGap(lay.getHgap());
		this.setVGap(lay.getVgap());
  }

  public void pushIntoLayout(JWebViewComponent webcomp, Component comp) throws Exception {
		JPanel panel = (JPanel) comp.getParent();
		if (panel==null) return; 
  	if (!(panel.getLayout() instanceof BorderLayout)) return;
  	
		BorderLayout lay = (BorderLayout) panel.getLayout();
		if (lay.getLayoutComponent(panel, BorderLayout.SOUTH)==comp)
				this.setBottomComponent(webcomp);
		if (lay.getLayoutComponent(panel, BorderLayout.NORTH)==comp)
			this.setTopComponent(webcomp);
		if (lay.getLayoutComponent(panel, BorderLayout.CENTER)==comp)
			this.setCenterComponent(webcomp);
		if (lay.getLayoutComponent(panel, BorderLayout.EAST)==comp)
			this.setLeftComponent(webcomp);
		if (lay.getLayoutComponent(panel, BorderLayout.WEST)==comp)
			this.setRightComponent(webcomp);
	}

}
