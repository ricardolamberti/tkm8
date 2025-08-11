/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.layout;

import java.awt.Rectangle;

import pss.core.tools.collections.JIterator;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;

/**
 * A layout which expands components to fit with the same size based on an axis, which may be vertical or horizontal.
 * 
 * Created on 21-jul-2003
 * 
 * @author PSS
 */

public class JWebStretchLayout extends JWebCompositeLayout {

	private int iOrientation;

	public JWebStretchLayout() {
		this.iOrientation=ORIENTATION_VERTICAL;
	}

	public JWebStretchLayout(int zOrientation) {
		super();
		this.iOrientation=zOrientation;
	}

	public JWebStretchLayout(int zOrientation, int zHGap, int zVGap) {
		super(zHGap, zVGap);
		this.iOrientation=zOrientation;
	}

	@Override
	protected void doLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception {
		if (this.iOrientation==ORIENTATION_VERTICAL) {
			this.doVLayout(zComposite, zLayoutArea);
		} else {
			this.doHLayout(zComposite, zLayoutArea);
		}
	}

	private void doVLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception {
		int iChildCount=0;
		JWebViewComponent oComp;
		JIterator<JWebViewComponent> oChildIt=zComposite.getChildren();
		while (oChildIt.hasMoreElements()) {
			oComp=oChildIt.nextElement();
			if (oComp.isVisible()) {
				iChildCount++;
			}
		}
		int iGap=this.getVGap();
		int iChildWidth=zLayoutArea.width;
		int iChildHeight=(zLayoutArea.height-(iGap*(iChildCount-1)))/iChildCount;
		oChildIt=zComposite.getChildren();
		int iOffset=zLayoutArea.y;
		while (oChildIt.hasMoreElements()) {
			oComp=oChildIt.nextElement();
			oComp.setLocation(zLayoutArea.x, iOffset);
			if (oChildIt.hasMoreElements()) {
				oComp.setSize(iChildWidth, iChildHeight);
				iOffset+=(iChildHeight+iGap);
			} else {
				oComp.setSize(iChildWidth, zLayoutArea.y+zLayoutArea.height-iOffset);
			}
		}
	}

	private void doHLayout(JWebViewComposite zComposite, Rectangle zLayoutArea) throws Exception {
		int iChildCount=0;
		JWebViewComponent oComp;
		JIterator<JWebViewComponent> oChildIt=zComposite.getChildren();
		while (oChildIt.hasMoreElements()) {
			oComp=oChildIt.nextElement();
			if (oComp.isVisible()) {
				iChildCount++;
			}
		}
		int iGap=this.getHGap();
		int iChildWidth=(zLayoutArea.width-(iGap*(iChildCount-1)))/iChildCount;
		int iChildHeight=zLayoutArea.height;
		oChildIt=zComposite.getChildren();
		int iOffset=zLayoutArea.x;
		while (oChildIt.hasMoreElements()) {
			oComp=oChildIt.nextElement();
			oComp.setLocation(iOffset, zLayoutArea.y);
			if (oChildIt.hasMoreElements()) {
				oComp.setSize(iChildWidth, iChildHeight);
				iOffset+=(iChildWidth+iGap);
			} else {
				oComp.setSize(zLayoutArea.x+zLayoutArea.width-iOffset, iChildHeight);
			}
		}
	}

}
