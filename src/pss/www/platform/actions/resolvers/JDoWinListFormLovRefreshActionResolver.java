package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.www.platform.actions.requestBundle.JWebActionData;

 
public class JDoWinListFormLovRefreshActionResolver extends JDoWinListRefreshActionResolver {

	public boolean resetOldObjects() throws Exception {
		return false;
	}
	
	public void loadData(JBaseWin zActionOwner) throws Exception {
		String tableProvider="win_form";
		if (getRequest().hasTableProvider()) tableProvider=getRequest().getTableProvider();

		zActionOwner.GetBaseDato().clearDynamicFilters();
		if (zActionOwner instanceof JWins) { // deberia tender a desaparecer
//			addWinListFilters(zActionOwner, getFilters());
			return;
		}
		
		JWin win = (JWin) zActionOwner; 
		win.setEmbedded(winFactory.isEmbedded());
		JWebActionData data=this.getRequest().getFormData(tableProvider);
		if (!data.isNull())
			this.controlsToBD(data, win.getRecord());
	}
	
	protected void storeHistory(JAct submit) throws Exception {
		

		this.getHistoryManager().setLastSubmit(submit);

		return; 
	}
	
	protected BizAction createVirtualAction(JBaseWin owner) throws Exception {
		BizAction a = new BizAction();
		if (owner instanceof JWins) {
			a.setObjOwner(owner);
			a.setObjSubmit(new JActWins(owner,a.isMulti()));
			return a;
			//throw new Exception("Esa accion no es compatible con la seleccion realizada");
		}
		return null;
	}
	
	
}

