package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JActWins;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;

public class JDoWinListEditActionResolver  extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "winlist-refresh";
	}

	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;} 
//  @Override
//	protected boolean isRefreshHistory() throws Exception {
//		return true;
//	}

  @Override
	protected boolean isNewRecordEnabled() {return false;}
  
	protected boolean isFirstAccess() throws Exception {
		return false;
	}
	

	
//	private void storeSelectedItem() throws Exception {
//		if (this.getSession().getHistoryManager().hasHistory() && getRequest().hasTableProvider()) {
//				this.getSession().getHistoryManager().getLastHistory().setSelectedItem(getRequest().getPssObjectOwner());
//			this.getSession().getHistoryManager().getLastHistory().setScroller(getRequest().getScroller());
//		}
//
//	}

	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		return baseWin;
	}
	@Override
	public JBaseWin resolveActionOwner() throws Exception {
		JBaseWin actionOwner=this.resolveMainActionOwner();
		if (this.getRequest().hasMultipleObjectOwnerList()) {
			if (getRequest().getClearSelect())
				this.getSession().getHistoryManager().getLastHistory().findProvider(this.getRequest().getTableProvider()).setMultipleSelect(null);
			
			JBaseWin actionSelect=resolveMainActionSelect();
			if (actionSelect!=null) {
				JWins newWin=(JWins) actionSelect.getClass().newInstance();
				this.addMultipleActionOwners(newWin);
				this.getSession().getHistoryManager().getLastHistory().findProvider(this.getRequest().getTableProvider()).setMultipleSelect(newWin);
			}
		}
		if (actionOwner!=null && this.getRequest().hasSelectedCell()) actionOwner.setSelectedCell(this.getRequest().getSelectedCell());
		return actionOwner;
	}
	public JBaseWin resolveMainActionOwner() throws Exception {
		if (this.getRequest().hasPssObjectOwner()) {
			JBaseWin actionOwner=this.processObjectRegistered(true);
			if (actionOwner!=null) return actionOwner;
		}
		String bundle=this.loadWinBundle();
		if (bundle==null) return null;
		return winFactory.getBaseWinFromBundle(bundle);
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
	public BizAction findAction(JBaseWin zActionOwner) throws Exception {
		JWebActionData  oData=this.getRequest().getData("action");
		String idAction=oData.get("act");
		if (idAction==null || idAction.equals("") ) return null;

		if (zActionOwner==null||zActionOwner instanceof GuiModulo) {
			return JWebActionFactory.getPssAction(idAction);
		}
		
		
//		zActionOwner.clearActions();
//		zActionOwner.generateFullActionMap();
		return zActionOwner.findActionByUniqueId(idAction);
	}

	protected BizAction createVirtualAction(JBaseWin owner) throws Exception {
		BizAction a = new BizAction();
		if (owner instanceof JWins) {
			a.setObjOwner(owner);
			a.setObjSubmit(new JActWins(owner,a.isMulti()));
			return a;
	//		throw new Exception("Esa accion no es compatible con la seleccion realizada");
		}
		return null;
	}
}

