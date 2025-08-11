/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.www.platform.applications.JHistoryProvider;




public class JDoFormLovActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "formLov";
	}

  @Override
	protected boolean isResetRegisteredObjects() {return false;}

  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}

  @Override
	protected boolean isNewRecordEnabled() {return false;}
  
//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}

	protected BizAction createVirtualAction(JBaseWin owner) throws Exception {
		return getSession().getHistoryManager().getLastHistoryAction();

	}
	
	@Override
	protected boolean isAjax() {return true;}

	protected boolean isFirstAccess() throws Exception {
		return false;
	}

	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		JHistoryProvider hp = super.storeContext(actionOwner, action);
		if (hp==null) return null;
		hp.setSelectedItem("-1");
		if (this.getRequest().hasMultipleObjectOwnerList()) {
			if (getRequest().getClearSelect())
				hp.setMultipleSelect(null);
			
			JBaseWin actionSelect=resolveMainActionSelect();
			if (actionSelect!=null) {
				JWins newWin=(JWins) actionSelect.getClass().newInstance();
				this.addMultipleActionOwners(newWin);
				hp.setMultipleSelect(newWin);
			}
		}
		return hp;
	};



	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		return baseWin;
	}
	

	public JBaseWin resolveMainActionSelect() throws Exception {
		if (this.getRequest().hasPssObjectSelect()) {
			JBaseWin actionOwner=this.processObjectRegisteredSelect(true);
			if (actionOwner!=null) return actionOwner;
		}
		String bundle=this.loadWinBundle();
		if (bundle==null) return null;
		return winFactory.getBaseWinFromBundle(bundle);
	}

}

