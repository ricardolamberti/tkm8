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
import pss.core.win.submits.JActModify;
import pss.www.platform.applications.JHistoryProvider;



public class JDoPreviewAreaActionResolver extends JDoViewAreaActionResolver {

	@Override
	protected String getBaseActionName() {
		return "viewArea";
	}

	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}

  @Override
  protected boolean isResetRegisteredObjects() throws Exception {
  	return false;
  }

	protected boolean isActionHistoryAffected() throws Exception {
		return false;
	}
	
//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}
	
	protected void storeNavigation(JHistoryProvider hp) throws Exception {
	}
	
	protected void storeFilterMap(JHistoryProvider hp) throws Exception {
	}


	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		restoreMeta(action);
		JHistoryProvider hp =	super.storeContext(actionOwner, action);

		//si queda uno preview guardado le saco el select tab porque tiene datos viejos
//		JHistory h = this.getSession().getHistoryManager().getLastHistory();
//		JHistoryProvider hpp =	h.findProvider(action.getProviderName());
//		if (hpp!=null && hpp.getSelectedTab()!=null) 
//			hpp.getSelectedTab().setFirstAccess(true);
//		

		return hp;
	}

	
	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		if (!this.getRequest().hasMultipleObjectOwnerList() ) {
			JHistoryProvider hp =this.findHistoryProvider();
			if (hp!=null && this.getRequest().getClearSelect())
				hp.setMultipleSelect(null);
			return baseWin;
		}
		
		JWins newWin=(JWins) baseWin.getClass().newInstance();
		JHistoryProvider hp =this.findHistoryProvider();
		if (this.getRequest().getClearSelect() && hp!=null)
			hp.setMultipleSelect(null);
			
//		hp.setMultipleSelect(newWin);
//		newWin=hp.getMultipleSelect();
		newWin.setDropListener(baseWin.getDropListener());
		newWin.SetVision(baseWin.GetVision());
		if (hp!=null && hp.getMultipleSelect()!=null)
			newWin.AppendDatos(hp.getMultipleSelect());
		this.addMultipleActionOwners(newWin);
		newWin.setShowFilters(false);
//		hp.setMultipleSelect(null);
		return newWin;
	}

	protected void storeHistory(JAct submit) throws Exception {
		super.storeHistory(submit);
		
		// los datos de navegacion vienen en el simple click, solo el embedded por ahora
		JHistoryProvider hp = this.getHistoryManager().getLastHistory().findProvider(submit);
		hp.setNavigation(this.getRequest().getNavigation());
		
	}
	
  @Override
	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
//  	JWin win = (JWin)actionOwner;
  	JAct lastAction = super.getSubmit(actionOwner, action);
  	if (lastAction instanceof JActModify)
  	  ((JActModify)lastAction).markFieldChanged(null,null,getRequest().getTableProvider());//es una card actualizandose dentro de un formulario, pongo en el campo su nombre
  	return lastAction;
  }
}
