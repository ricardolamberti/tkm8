/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JDoSecurityResolver extends JDoViewAreaActionResolver {

  @Override
	protected String getBaseActionName() {
    return "do.security";
  }

	public JBaseWin resolveActionOwner() throws Exception {
		return null;
	}
	
	public BizAction resolveAction(JBaseWin owner) throws Exception {
		return null;
	}
	
	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
		JAct last = this.getSession().getHistoryManager().getLastHistory().getMainSumbit();
		JBaseWin w = last.getResult();
		return new JActQuery(w.getObjWinProperty());
	}
	protected void storeHistory(JAct submit) throws Exception {

		submit.getActionSource().setModal(false);
		super.storeHistory(submit);
}

	

}
