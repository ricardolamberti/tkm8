/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;
import pss.www.ui.JWebView;

/**
 * 
 * 
 * Created on 09-jun-2003
 * 
 * @author PSS
 */

public class JFormLovInfoGenerator extends JXMLComponentGenerator {

	String controlId;

	public JFormLovInfoGenerator(String controlId) {
		this.controlId=controlId;
	}

	@Override
	protected void doGenerate() throws Exception {
		this.startNode("formLovInfo");
		this.setAttribute("controlId", this.controlId);
		this.endNode("formLovInfo");
	}

	@Override
	protected String getBaseContentName() {
		return "formLov.info";
	}
	protected void addingExtraViews(JWebView oWebView,JBaseWin oBWin, JAct oAct ) throws Exception {
	}
}
