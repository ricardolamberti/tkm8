/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.www.platform.actions.requestBundle.JWebActionData;


public class JDoWinListReportActionResolver extends JDoPssActionResolver {

	@Override
	protected boolean isFirstAccess() throws Exception {
		return false;
	}
	
	
	@Override
	protected boolean isAjax() {
		return true;
	}
  @Override
	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
  	action.setSubmitedByUser(true);
		JAct act = action.getSubmit();
		if (!(act instanceof JActWins)) return act;
		final JWins wins = ((JActWins)act).getWinsResult();
		JFormFiltro formFiltros = new JFormFiltro(wins);
		formFiltros.initialize();
		formFiltros.applyFilterMap(action,false);
		wins.asignFiltersFromFilterBar(formFiltros);
//		wins.readAll();

		return wins.getActExport(getType() );
  }
  	
  private String getType() throws Exception {
		 JWebActionData p = this.getRequest().getData("export");
		 if (p==null) return "xlsx";
		 return p.get("type");
	 }

	
  
}

