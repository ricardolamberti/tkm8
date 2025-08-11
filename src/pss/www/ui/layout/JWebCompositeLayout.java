/*
 * Created on 18-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.layout;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

import pss.core.tools.collections.JIterator;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewsConstants;

/**
 * 
 * 
 * Created on 18-jun-2003
 * 
 * @author PSS
 */

public abstract class JWebCompositeLayout implements JWebViewsConstants {

	private int iHGap;
	private int iVGap;
//  boolean calculeBorderInternal=true;

	public JWebCompositeLayout() {
	}

	public JWebCompositeLayout(int zHGap, int zVGap) {
		this.iHGap=zHGap;
		this.iVGap=zVGap;
	}

	public int getHGap() {
		return this.iHGap;
	}

	public int getVGap() {
		return this.iVGap;
	}

	public void setHGap(int i) {
		this.iHGap=i;
	}

	public void setVGap(int i) {
		this.iVGap=i;
	}

	
	public void layout(JWebViewComposite zComposite) throws Exception {
//		Rectangle area = zComposite.createLayoutArea();
		// perform the layout
//		this.doLayout(zComposite, area);
	//	JDebugPrint.logDebug("layouteando: " + zComposite.getName()+ "-- " + (System.currentTimeMillis()-start));
	}

	protected void applyHDelta(JWebViewComposite zComposite, int zDelta) throws Exception {
		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		JWebViewComponent oComp;
		Point oLoc;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
			oLoc=oComp.getLocation();
			if (oLoc!=null) {
				oComp.setX(oLoc.x+zDelta);
			} else {
				oComp.setX(zDelta);
			}
		}

	}

	protected void applyVDelta(JWebViewComposite zComposite, int zDelta) throws Exception {
		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		JWebViewComponent oComp;
		Point oLoc;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
			oLoc=oComp.getLocation();
			if (oLoc!=null) {
				oComp.setY(oLoc.y+zDelta);
			} else {
				oComp.setY(zDelta);
			}
		}
	}

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	protected abstract void doLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception;

	public void takeLayoutAttributesFrom(LayoutManager layout) throws Exception {
	}

  public void pushIntoLayout(JWebViewComponent webcomp, Component comp) throws Exception {
	}

}
