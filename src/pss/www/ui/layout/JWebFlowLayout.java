/*
 * Created on 18-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.ui.layout.VerticalFlowLayout;
import pss.core.winUI.controls.JFormControl;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;

/**
 * 
 * 
 * Created on 18-jun-2003
 * 
 * @author PSS
 */

public class JWebFlowLayout extends JWebCompositeLayout {

	private int iOrientation;
	private int iHAlignment;
	private int iVAlignment;
	private int iExtraGap;
	private int iRowOrColumnAlignment;
	private boolean rotate=false;
	private boolean useScroll=false;
	
	public void setOrigentation(int v) {
		this.iOrientation=v;
	}
	public void setHAlignment(int v) {
		this.iHAlignment=v;
	}
	public void setVAlignment(int v) {
		this.iVAlignment=v;
	}
	public void setRowOrColumnAlignment(int v) {
		this.iRowOrColumnAlignment=v;
	}

	public JWebFlowLayout() {
		this.iOrientation=ORIENTATION_HORIZONTAL;
		this.iHAlignment=JFormControl.HALIGN_LEFT;
		this.iVAlignment=JFormControl.VALIGN_TOP;
		this.iRowOrColumnAlignment=JFormControl.VALIGN_MIDDLE;
	}

	public JWebFlowLayout(int zOrientation, int zHAlignment, int zVAlignment, int zRowOrColumnAlignment) {
		super();
		this.iOrientation=zOrientation;
		this.iHAlignment=zHAlignment;
		this.iVAlignment=zVAlignment;
		this.iRowOrColumnAlignment=zRowOrColumnAlignment;
	}
	

	public JWebFlowLayout(int zOrientation, int zHAlignment, int zVAlignment, int zRowOrColumnAlignment, int zHGap, int zVGap) {
		super(zHGap, zVGap);
		this.iOrientation=zOrientation;
		this.iHAlignment=zHAlignment;
		this.iVAlignment=zVAlignment;
		this.iRowOrColumnAlignment=zRowOrColumnAlignment;
	}

	public void setRotate(boolean zRotate) {
		this.rotate=zRotate;
	}

	@Override
	protected void doLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception {
		if (this.iOrientation==ORIENTATION_HORIZONTAL) {
			this.doHLayout(zComposite, zLayoutArea, zComposite.getChildrenCount());
		} else {
			this.doVLayout(zComposite, zLayoutArea, zComposite.getChildrenCount());
		}
	}

	private void doHLayout(JWebViewComposite zComposite, Rectangle zLayoutArea, int zChildCount) throws Exception {
		int maxwidth=isUseScrollX(zComposite)?100000:zLayoutArea.width;
		int x=0, y=zLayoutArea.y;
		int rowh=0, start=0;

		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		int i=0;
		while (oChildrenIt.hasMoreElements()) {
			JWebViewComponent oComp=oChildrenIt.nextElement();
			if (oComp.isVisible()) {
				Dimension d=JWebLayouts.getLayoutSizeOf(oComp);
				oComp.setSize(d.width, d.height);

				if (!oComp.isBreak()&&((x==0)||((x+d.width)<=maxwidth))) {
					x+=d.width+this.getHGap();
					rowh=Math.max(rowh, d.height);
				} else {
					this.moveHComponents(zComposite, zLayoutArea.x, y, maxwidth-x, rowh, start, i);
					y+=this.getVGap()+rowh;
					rowh=d.height;
					start=i;
					if (!oComp.isBreak()) {
						x=d.width+this.getHGap();
					} else {
						// x = this.getHGap();
						x=0;
					}
				}
			}
			i++;
		}
		this.moveHComponents(zComposite, zLayoutArea.x, y, maxwidth-x, rowh, start, zChildCount);
		y+=this.getExtraGap();
		
		if (this.rotate) this.rotateLines(zComposite);

		int maxVOffset=y+rowh;
		if (maxVOffset>(zLayoutArea.y+zLayoutArea.height)) {
			if (zComposite.getBorder()!=null) {
				//HGK, esto es porque en createLayoutArea le saca los bordes y aca se lo vuelvo a incluir
				maxVOffset+=zComposite.getBorder().bottom+zComposite.getBorder().top;
			}
			if (zComposite.getMargin()!=null) {
				//HGK, esto es porque en createLayoutArea le saca los margenes y aca se lo vuelvo a incluir
				maxVOffset+=zComposite.getMargin().bottom+zComposite.getMargin().top;
			}
			zComposite.setFixHight(maxVOffset);
//			zComposite.markParentToLayout();
		} else {
			if (this.iVAlignment==JFormControl.VALIGN_MIDDLE) {
				int iDeltaY=(zLayoutArea.height-maxVOffset)/2; // middle
				this.applyVDelta(zComposite, iDeltaY);
			} else if (this.iVAlignment==JFormControl.VALIGN_BOTTOM) {
				int iDeltaY=zLayoutArea.height-maxVOffset; // bottom
				this.applyVDelta(zComposite, iDeltaY);
			}
		}
	}

	private void rotateLines(JWebViewComposite zComposite) throws Exception {
		int currY=-1;
		JList<String> differentsY=JCollectionFactory.createList();
		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		while (oChildrenIt.hasMoreElements()) {
			JWebViewComponent oComp=oChildrenIt.nextElement();
			if (!oComp.isVisible()) continue;
			if (oComp.getLocation().y!=currY) {
				currY=oComp.getLocation().y;
				differentsY.addElement(String.valueOf(currY));
			}
		}

		int lines=differentsY.size();
		currY=-1;
		int newY=0;
		oChildrenIt=zComposite.getChildren();
		while (oChildrenIt.hasMoreElements()) {
			JWebViewComponent oComp=oChildrenIt.nextElement();
			if (!oComp.isVisible()) continue;
			if (oComp.getLocation().y!=currY) {
				lines--;
				newY=Integer.parseInt(differentsY.getElementAt(lines));
				currY=oComp.getLocation().y;
			}
			oComp.setLocation(oComp.getLocation().x, newY);
		}
	}

	private void doVLayout(JWebViewComposite zComposite, Rectangle zLayoutArea, int zChildCount) throws Exception {
		int maxheight=isUseScrollY(zComposite)?100000:zLayoutArea.height;
		int y=0, x=zLayoutArea.x;
		int colw=0, start=0;

		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		int i=0;
		while (oChildrenIt.hasMoreElements()) {
			JWebViewComponent oComp=oChildrenIt.nextElement();
			if (oComp.isVisible()) {
				Dimension d=JWebLayouts.getLayoutSizeOf(oComp);
				oComp.setSize(d.width, d.height);

				if (!oComp.isBreak()&&((y==0)||((y+d.height)<=maxheight))) {
					y+=d.height+this.getVGap();
					colw=Math.max(colw, d.width);
				} else {
					this.moveVComponents(zComposite, x, zLayoutArea.y, colw, maxheight-y, start, i);
					x+=this.getHGap()+colw;
					colw=d.width;
					start=i;
					if (!oComp.isBreak()) {
						y=d.height+this.getVGap();
					} else {
						// y = this.getVGap();
						y=0;
					}
				}
			}
			i++;
		}
		this.moveVComponents(zComposite, x, zLayoutArea.y, colw, maxheight-y, start, zChildCount);
		x+=this.getExtraGap();
		int maxHOffset=x+colw;

		if (maxHOffset>(zLayoutArea.x+zLayoutArea.width)) {
			//HGK, esto es porque en createLayoutArea le saca los bordes y aca se lo vuelvo a incluir
			if (zComposite.getBorder()!=null) {
				maxHOffset+=zComposite.getBorder().left+zComposite.getBorder().right;
			}
			//HGK, esto es porque en createLayoutArea le saca los margenes y aca se lo vuelvo a incluir
			if (zComposite.getMargin()!=null) {
				maxHOffset+=zComposite.getMargin().left+zComposite.getMargin().right;
			}
			zComposite.setFixWidth(maxHOffset);
//			zComposite.markParentToLayout();
		} else if (this.iHAlignment==JFormControl.HALIGN_CENTER) {
			int iDeltaX=(zLayoutArea.width-maxHOffset)/2; // center
			this.applyHDelta(zComposite, iDeltaX);
		} else if (this.iHAlignment==JFormControl.HALIGN_RIGHT) {
			int iDeltaX=zLayoutArea.width-maxHOffset; // right
			this.applyHDelta(zComposite, iDeltaX);
		}
	}

	private void moveHComponents(JWebViewComposite zComposite, int x, int y, int width, int height, int rowStart, int rowEnd) throws Exception {
		switch (this.iHAlignment) {
		case JFormControl.HALIGN_CENTER:
			x+=width/2;
			break;
		case JFormControl.HALIGN_RIGHT:
			x+=width;
			break;
		}
		for(int i=rowStart; i<rowEnd; i++) {
			JWebViewComponent m=zComposite.getChild(i);
			Dimension oSize=m.getSize(); // was previously set
			if (m.isVisible()) {
				if (this.iRowOrColumnAlignment==JFormControl.VALIGN_TOP) {
					m.setLocation(x, y);
				} else if (this.iRowOrColumnAlignment==JFormControl.VALIGN_MIDDLE) {
					m.setLocation(x, y+(height-oSize.height)/2);
				} else if (this.iRowOrColumnAlignment==JFormControl.VALIGN_BOTTOM) {
					m.setLocation(x, y+height-oSize.height);
				}
				if (!m.isBreak()) {
					x+=oSize.width+this.getHGap();
				}
			}
		}
	}

	private void moveVComponents(JWebViewComposite zComposite, int x, int y, int width, int height, int colStart, int colEnd) throws Exception {
		switch (this.iVAlignment) {
		case JFormControl.VALIGN_MIDDLE:
			y+=height/2;
			break;
		case JFormControl.VALIGN_BOTTOM:
			y+=height;
			break;
		}
		for(int i=colStart; i<colEnd; i++) {
			JWebViewComponent m=zComposite.getChild(i);
			Dimension oSize=m.getSize(); // was previously set
			if (m.isVisible()) {
				if (this.iRowOrColumnAlignment==JFormControl.HALIGN_LEFT) {
					m.setLocation(x, y);
				} else if (this.iRowOrColumnAlignment==JFormControl.HALIGN_CENTER) {
					m.setLocation(x+(width-oSize.width)/2, y);
				} else if (this.iRowOrColumnAlignment==JFormControl.HALIGN_RIGHT) {
					m.setLocation(x+width-oSize.width, y);
				}
				if (!m.isBreak()) {
					y+=oSize.height+this.getVGap();
				}
			}
		}
	}

	
	private boolean isUseScrollX(JWebViewComposite zComposite) {
		return zComposite.getOverflowX()!=null && zComposite.getOverflowX()!="hidden";
	}

	private boolean isUseScrollY(JWebViewComposite zComposite) {
		return zComposite.getOverflowY()!=null && zComposite.getOverflowY()!="hidden";
	}

	
	public int getExtraGap() {
		return iExtraGap;
	}

	
	public void setExtraGap(int extraGap) {
		iExtraGap=extraGap;
	}
	
	private int findHAlignment(FlowLayout lay) throws Exception {
		if (lay.getAlignment()==FlowLayout.LEFT) return JFormControl.HALIGN_LEFT;
		if (lay.getAlignment()==FlowLayout.RIGHT) return JFormControl.HALIGN_RIGHT;
		return JFormControl.HALIGN_CENTER;
	}
	private int findVAlignment(VerticalFlowLayout lay) throws Exception {
		if (lay.getAlignment()==VerticalFlowLayout.BOTTOM) return JFormControl.VALIGN_BOTTOM;
		if (lay.getAlignment()==VerticalFlowLayout.TOP) return JFormControl.VALIGN_TOP;
		return JFormControl.VALIGN_MIDDLE;
	}
	
	public void takeLayoutAttributesFrom(LayoutManager layout) throws Exception {
		if (layout instanceof FlowLayout) 
			this.takeHorizontal((FlowLayout) layout);
		if (layout instanceof VerticalFlowLayout) 
			this.takeVertical((VerticalFlowLayout) layout);
	}
	public void takeHorizontal(FlowLayout lay) throws Exception {
		this.setOrigentation(JWebFlowLayout.ORIENTATION_HORIZONTAL);
		this.setHAlignment(JFormControl.VALIGN_MIDDLE);
		this.setVAlignment(this.findHAlignment(lay));
		this.setRowOrColumnAlignment(JFormControl.VALIGN_MIDDLE);
		this.setHGap(lay.getHgap());
		this.setVGap(lay.getVgap());
	}

	public void takeVertical(VerticalFlowLayout lay) throws Exception {
		this.setOrigentation(JWebFlowLayout.ORIENTATION_VERTICAL);
		this.setHAlignment(this.findVAlignment(lay));
		this.setVAlignment(JFormControl.HALIGN_CENTER);
		this.setRowOrColumnAlignment(JFormControl.HALIGN_CENTER);
		this.setHGap(lay.getHgap());
		this.setVGap(lay.getVgap());
	}

}
