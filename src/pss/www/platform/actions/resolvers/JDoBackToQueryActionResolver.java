/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;


public class JDoBackToQueryActionResolver extends JDoBackActionResolver {

	

	protected JWebActionResult checkPerform(JAct last ) throws Throwable {
		if (last instanceof JActFileGenerate) {
			JActFileGenerate action=(JActFileGenerate) last.getFinalSubmit();
			action.doSubmit(true);
			backToHistory();
			return this.getRedirector().goWebFile(action.getProvider()+"/"+action.getFileName());
		} 
		return null;
  }

	protected boolean isBack() throws Exception {
		return true;
	}


	@Override
	protected void backToHistory() throws Exception {
		JHistory h = this.getRequest().getSession().getHistoryManager().backToQueryHistory();
//		h.getFirstAction().setFirstAccess(false);
		h.returnning();
		this.getHistoryManager().setLastSubmit(h.getMainSumbit());
	}

}

