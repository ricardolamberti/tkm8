/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoReportActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "report";
	}

  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}
  
  @Override
	protected JWebActionResult performAction(JBaseWin actionOwner, BizAction action) throws Throwable {
    JAct act = this.getSubmit(actionOwner, action);
		this.assignTarget(act);
    this.addObjectToResult("report", "report");
    return null;
  }
}

