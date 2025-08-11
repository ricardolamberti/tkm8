package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.applications.JHistoryProvider;

public class JDoGraphRefreshActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "winlist-graph";
	}

	@Override
	protected boolean isAjax() {
		return true;
	}

	public boolean resetOldObjects() throws Exception {
		return false;
	}
	
	@Override
	protected boolean isResetRegisteredObjects() {
		return false;
	}

	@Override
	protected boolean isActionHistoryAffected() throws Exception {
		return false;
	}

	@Override
	protected boolean isNewRecordEnabled() {
		return false;
	}

	protected boolean isFirstAccess() throws Exception {
		return false;
	}

	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		JHistoryProvider hp = super.storeContext(actionOwner, action);
		if (hp == null)
			return null;
		return hp;
	}

	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		return baseWin;
	}

//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}

}
