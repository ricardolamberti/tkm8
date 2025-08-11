/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.GuiListExcludeCols;
import pss.www.platform.actions.JWebActionFactory;

/**
 * 
 * 
 * Created on 06-jun-2003
 * 
 * @author PSS
 */

public class JDoExcludeColumnsResolver extends JDoViewAreaActionResolver {

	@Override
	protected String getBaseActionName() {
		return "do.excludecolumns";
	}

	public JBaseWin resolveActionOwner() throws Exception {
		return null;
	}

	public BizAction resolveAction(JBaseWin owner) throws Exception {
		return null;
	}

	protected JAct getSubmit(JBaseWin actionOwner, BizAction action)
			throws Exception {
		JAct last = this.getSession().getHistoryManager().getLastHistory().getMainSumbit();
		JBaseWin w = last.getResult();
		if (w instanceof JWins) {
			GuiListExcludeCols lists = new GuiListExcludeCols();
			lists.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			lists.getRecords().addFilter("class_name", w.getClass().getName());
			lists.getRecords().addFilter("userid", BizUsuario.getUsr().GetUsuario());
			lists.getRecords().addOrderBy("col_order");
			return new JActWins(lists);
		}
		
		return null;

	}
	
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		return "resources/stylesheets/html/responsive_ajax.viewAreaAndTitle.xsl";
	}
	protected String getSerializer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) return "mobile";
		return "html";
	}

}
