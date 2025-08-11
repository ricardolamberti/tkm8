/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.www.ui.JWebViewsConstants;
import pss.www.ui.views.JMessageView;

public class JMessageGenerator extends JXMLPageGenerator {

	@Override
	protected String getPageLayoutStereotype() throws Exception {
		if (this.getSession()==null) {
			return JWebViewsConstants.PAGE_LAYOUT_APP_FRONT_DOOR;
		} else {
			return JWebViewsConstants.PAGE_LAYOUT_APP_INDOORS;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() {
		return JMessageView.class;
	}

	@Override
	protected String getBaseContentName() {
		return "show.message";
	}
	
	
	@Override
	protected void postEndDocument() throws Exception {
	}
	


}
