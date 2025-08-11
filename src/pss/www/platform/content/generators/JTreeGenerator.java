/*
 * Created on 26-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.GuiModuloPss;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.submits.JAct;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWinPackager;
import pss.www.ui.controller.JBusinessNodesWebTreeConstants;

public class JTreeGenerator extends JXMLComponentGenerator implements JBusinessNodesWebTreeConstants {

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
		this.startNode("trees");
		this.setAttribute("initial_selection", this.getSession().getUICoordinator().getTreeCoordinator().getSelection().asActionDataString());
		this.setAttributeNLS("description", "Explorador");
		this.createIconNode(GuiIcon.ARBOL_ICON);
		// create variables used to generate both trees
		// start a tree for a regional hierarchy

		this.setAttribute("tree_id", "PssTree");
		this.setAttribute("obj_provider", "PssTree");
		this.createDummyNode();

		// GuiModuloPss modulo = GuiModuloPss.getModuloPss(true);
		GuiModuloPss modulo=new GuiModuloPss();
		this.expandWinSubActions(modulo, modulo.getUserMenu(0));
		this.endNode("trees");
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INTERNAL IMPLEMENTATION METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	//
	// UTILITY METHODS TO GET DESCRIPTIONS AND ICONS
	//

	private String getIconFile(int zIcon) throws Exception {
		GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(zIcon);
		if (oIcon!=null) {
			return oIcon.GetFile();
		} else {
			return null;
		}
	}

	private void createIconNode(int zIcon) throws Exception {
		this.createIconNode(getIconFile(zIcon));
	}

	private void createIconNode(String zFile) throws Exception {
		if (zFile!=null&&zFile.trim().length()>0) {
			this.startNode("icon");
			this.setAttribute("file", zFile);
			this.endNode("icon");
		}
	}

	private void createNode(JWin win) throws Exception {
		if (win==null) return;
		this.startNode("tree_node");
		this.setAttribute("applies", true);
		this.setAttribute("has_children", win.getTreeActions().size()>0);
		this.setAttribute("levelValue", 0);
		this.setAttribute("expand", false);
		win.getRecord().keysToFilters();
                this.setAttribute("act_owner", new JWinPackager(null).baseWinToJSON(win));
		this.addTextNode("description", win.getDescripFieldValue());
		this.createIconNode(win.GetIconFile());
		// this.generateDummy(1);
		this.endNode("tree_node");
	}

	private void createNode(JBaseWin owner, BizAction action, boolean expanding) throws Exception {
		if (action==null) return;
		if (owner==null) return;

		this.startNode("tree_node");
		this.setAttribute("applies", true);
		this.setAttribute("has_children", !action.hasSubActions());
		this.setAttribute("levelValue", 0);
                this.setAttribute("act_owner", new JWinPackager(null).baseWinToJSON(owner));
		this.setAttribute("expand", !action.hasSubActions());
//		this.setAttribute("action", JWebActionFactory.idActionToURL(action.getIdAction()));
		this.setAttribute("action", action.getIdAction());
		this.addTextNode("description", action.GetDescr());
		this.createIconNode(action.getIconFile());
		if (action.hasSubActions()) this.expandWinSubActions(owner, action.GetSubAcciones());
		// this.generateDummy(1);
		this.endNode("tree_node");
	}

	private void createDummyNode() throws Exception {
		this.startNode("tree_node_dummy");
		this.addTextNode("description", "...");
		this.createIconNode(GuiIconos.GetGlobal().buscarIcono(GuiIcon.PROCESANDO).GetFile());
		this.endNode("tree_node_dummy");
	}

	public void expandWinSubActions(JBaseWin owner, BizActions actions) throws Exception {
		JIterator<BizAction> iter=actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			// JBaseWin result = action.getResult();
			// if (!(result instanceof JWin)) continue;
			this.createNode(owner, action, false);
			// this.expandTree((JWin)result, false);
		}
	}

	public void expandAction(JAct submit, boolean inverso) throws Exception {
		if (submit==null) {
			JExcepcion.SendError("No hay submit para la accion");
		}
		JBaseWin baseWin=submit.getResult();
		if (baseWin instanceof JWin) {
			this.expandTree((JWin) baseWin, inverso);
		} else {
			this.expandTree((JWins) baseWin);
		}
	}

	private void expandTree(JWin zWin, boolean inverso) throws Exception {
		JWin oWin=zWin.getRelativeWin();
		JList<BizAction> oActions=oWin.getTreeActions();
		int len=oActions.size();
		for(int i=0; i<len; i++) {
			BizAction action=oActions.getElementAt(inverso ? len-1-i : i);
			if (action.isListTypeUnique()) {
				JBaseWin result=action.getResult();
				if (result==null) continue;
				this.expandTree((JWins) result);
			} else {
				this.createNode(zWin, action, inverso);
			}
		}
	}

	// -------------------------------------------------------------------------- //
	// Expando el arbol
	// -------------------------------------------------------------------------- //
	private void expandTree(JWins zWins) throws Exception {
		if (!zWins.canExpandTree()) return;
		int i=0;
		zWins.readAll();
		zWins.firstRecord();
		while (zWins.nextRecord()) {
			JWin win=zWins.getRecord();
			if (!win.canExpandTree()) continue;
			this.createNode(win);
			if (++i>25) break;
		}
	}

}
