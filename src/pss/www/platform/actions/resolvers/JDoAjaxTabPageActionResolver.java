package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.applications.JHistoryProvider;



public class JDoAjaxTabPageActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "ajax-action";
	}

	@Override
	protected boolean isAjax() {return true;}

//	public JBaseWin resolveActionOwner() throws Exception {
//		JAct last = this.getSession().getHistoryManager().getLastAction();
//		JBaseWin w = last.getResult();
//		w.GetBaseDato().clearDynamicFilters();
//		return w;
//	}
	
//	public BizAction resolveAction(JBaseWin owner) throws Exception {
//		BizAction a = super.resolveAction(owner);
//		this.storeSelectedTab(a);
//		return a;
//	}
	
//	private void storeSelectedTab(BizAction action) throws Exception {
//		if (!this.getSession().getHistoryManager().hasHistory()) return;
//		if (!getRequest().hasTableProvider()) return; 
////		if (!this.getRequest().getTableProvider().equals("win_form")) return;
//		if (action==null) return;
//		this.getSession().getHistoryManager().getLastHistory().setSelectedTab(action);
//	}
	

  @Override
	protected boolean isActionHistoryAffected() throws Exception {
  	return false;
  }

  @Override
	protected boolean isResetRegisteredObjects() throws Exception {
		return false;
	}
  
	protected void storeFilterMap(JHistoryProvider hp) throws Exception {
	}

  @Override
  protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
  	JHistoryProvider hp = super.storeContext(actionOwner, action);
  	if (hp==null) return null;
  	JHistoryProvider stored = this.getHistoryManager().getLastHistory().findProvider(action); // usa el guardado que tiene los filtros
  	if (stored!=null) action.setObjFilterMap(stored.getAction());
//  	hp.setSelectedTab(action);
  	return hp;
  }

//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}

//	protected void touchHistory(BizAction newAction) throws Exception {
//		this.getSession().getHistoryManager().getLastAction().getActionSubmit().getActionSource().setSubAction(newAction); 		
//	}
	
}
