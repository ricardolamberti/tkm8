/*
 * Created on 26-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import pss.core.win.submits.JAct;
import pss.www.ui.controller.JBusinessNodesWebTreeConstants;

public class JTreeExpandGenerator extends JTreeGenerator implements JBusinessNodesWebTreeConstants {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////////////////////////
	//
	// SUPERCLASS OVERRIDING
	//
	// ////////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean isSessionDependent() {
		return true;
	}

	@Override
	protected String getBaseContentName() {
		return "tree";
	}

	@Override
	protected void doGenerate() throws Exception {
		// embed the skin
		this.getSkinProvider().getSkinFor(this.getSession()).toXML().toXML(this.asXMLContent());
		// then, build the regional and user defined trees

		JAct submit=(JAct) objectModel.get("Pss.act");

		this.startNode("tree_expand");
		this.expandAction(submit, true);
		this.endNode("tree_expand");
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}

	@Override
	public Serializable getKey() {
		return null;
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INTERNAL IMPLEMENTATION METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

}
