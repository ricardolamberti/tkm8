package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.applications.JHistoryProvider;

public class JDoPartialRefreshPreviewFormActionResolver extends JDoPartialRefreshFormActionResolver {

	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		JHistoryProvider hp =	super.storeContext(actionOwner, action);

		//si queda uno preview guardado le saco el select tab porque tiene datos viejos
//		JHistory h = this.getSession().getHistoryManager().getLastHistory();
//		JHistoryProvider hpp =	h.findProvider(action.getProviderName());
//		if (hpp!=null && hpp.getSelectedTab()!=null) 
//			hpp.getSelectedTab().setFirstAccess(true);
//		

		return hp;
	}

	@Override
	public boolean calculeIsModal() throws Exception {
		return false;
	}

	protected String getTransformer() throws Exception {
		return "resources/stylesheets/html/responsive_ajax.viewarea.xsl";
	}
	
	

}