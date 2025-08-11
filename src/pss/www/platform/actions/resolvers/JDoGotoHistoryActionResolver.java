package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;

public class JDoGotoHistoryActionResolver  extends JDoRefreshActionResolver  {

	
	@Override
	protected String getBaseActionName() {
		return "refresh";
	}

	@Override
	protected JWebActionResult perform() throws Throwable {
		if (this.getSession()!=null) {
//			this.getSession().winRegisteredObjects(getRequest().getPssIdDictionary(),false,true);
			JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(),false,true);
//			this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
		}
		JWebActionData  oData=this.getRequest().getData("action");
		String idHist=oData.get("hist");
		if (idHist==null  || idHist.equals("")) return null;
		JHistory h = this.getRequest().getSession().getHistoryManager().gotoHistory(idHist);
		if (h==null) return null;

		h.getFirstProvider().getAction().getObjSubmit().getObjSubmitTarget();

		return super.perform();
  }
	
//	protected void assignStoredTab(JHistory target) throws Exception {
//		this.getRequest().addModelObject("Pss.selectedTab", target.getSubAction());
//	}

}

