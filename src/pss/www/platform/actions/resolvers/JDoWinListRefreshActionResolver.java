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
import pss.core.win.submits.JActFieldSwapWins;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JHistoryProvider;


public class JDoWinListRefreshActionResolver extends JDoPssActionResolver {

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


  @Override
	protected boolean isNewRecordEnabled() {return false;}
  
	protected boolean isFirstAccess() throws Exception {
		return false;
	}
	
	@Override
	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
//		action.setSubmitedByUser( this.isSearchButtonPress()); // tiene que estar antes del getSubmit HGK
		JAct act= super.getSubmit(actionOwner, action);
		if (act instanceof JActFieldSwapWins)
			winFactory.processSwapElement(act,"form_"+getRequest().getTableProvider());
		return act;
	}

	protected BizAction createVirtualAction(JBaseWin owner) throws Exception {
		BizAction a = super.createVirtualAction(owner);
		a.setForceProviderName(getRequest().getTableProvider());
		return a;
	}
	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		JHistoryProvider hp = super.storeContext(actionOwner, action);
		if (hp==null) return null;
		this.resetSelectItem(hp);
		this.checkMultiple(hp);
		return hp;
	}

	protected void resetSelectItem(JHistoryProvider hp) {
		hp.setSelectedItem("-1");
	}
	
	protected void checkMultiple(JHistoryProvider hp) throws Exception {
		// acumula multiples solo en las paginaciones
		
		if (!this.getRequest().hasMultipleObjectOwnerList()) 
			return;
		
		if (!this.getRequest().hasNavigation())
			return;
		
		if (this.getRequest().getClearSelect())
			hp.setMultipleSelect(null);
		
		JBaseWin actionSelect=this.resolveMainActionSelect();
		if (actionSelect!=null) {
			JWins newWin=(JWins) actionSelect.getClass().newInstance();
			this.addMultipleActionOwners(newWin);
			hp.setMultipleSelect(newWin);
		}

		return;
	};

	

	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		return baseWin;
	}
	

	public JBaseWin resolveMainActionSelect() throws Exception {
		if (this.getRequest().hasPssObjectSelect()) {
			JBaseWin actionOwner=this.processObjectRegisteredSelect(true);
			if (actionOwner!=null) return actionOwner;
		}
		String bundle=winFactory.loadWinBundle();
		if (bundle==null) return null;
		return winFactory.getBaseWinFromBundle(bundle);
	}
	
//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}

	@Override
	protected String getTransformer() throws Exception {
		// TODO Auto-generated method stubre
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		return "resources/stylesheets/html/ajax.winList.refresh.xsl";
	}




}

