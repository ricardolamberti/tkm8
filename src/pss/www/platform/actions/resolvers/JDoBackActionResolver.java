/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.common.security.BizUsuario;
import pss.core.tools.JMessageInfo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;

public class JDoBackActionResolver extends JDoRefreshActionResolver  {

	
	@Override
	protected String getBaseActionName() {
		return "refresh";
	}

	@Override
	protected JWebActionResult perform() throws Throwable {
		if (this.getSession()!=null) {
			JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(),false,true);
		//	this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
		}
		JAct last = this.getRequest().getSession().getHistoryManager().getLastHistory().getMainSumbit();
		BizUsuario.clearEventInterfaz();
		
		JWebActionResult newResult = checkPerform(last);
		if (newResult!=null) return newResult;

		this.backToHistory();
		JAct newlast = this.getRequest().getSession().getHistoryManager().getLastHistory().getMainSumbit();
		newlast.setMessage((JMessageInfo)null);
		newlast.markFieldChanged(null, null, "cancel_action");
		if (newlast.hasIdControlListener() && last instanceof JActWinsSelect) {
		//	if (newlast.getResult() instanceof JWin)
				newlast.getResult().setSubmitListener(null);
				newlast.getResult().clearDropControlIdListener();
		}
		this.addObjectToResult("transformer", this.getTransformer());
		this.addObjectToResult("serializer", this.getSerializer());
		return super.perform();
  }
	protected JWebActionResult checkPerform(JAct last ) throws Throwable {
		return null;
	}
	
	protected JHistory findHistory(JAct act) throws Exception {
		return this.getRequest().getSession().getHistoryManager().backToTargetHistory(act);
	}
//	protected void assignStoredTab(JHistory target) throws Exception {
//		this.getRequest().addModelObject("Pss.selectedTab", target.getSubAction());
//	}
//	
//  protected void setFilters() throws Throwable {
//		if (this.getRequest().getSession().getHistoryManager().hasHistory())
//			this.getRequest().addModelObject("Pss.filters", this.getRequest().getSession().getHistoryManager().getLastHistory().getSelectedFilters());
//  }	
  private boolean isParentModal() throws Exception {
  	return getRequest().hasBackToModal();
//  		JHistory last = this.getRequest().getSession().getHistoryManager().getLastHistory();
//  		if (last==null) return false;
//  		return last.getMainSumbit().getActionSource().isModal();
 
  }
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		if (isParentModal()) return "resources/stylesheets/html/responsive_ajax.modalviewarea.xsl";
		return "resources/stylesheets/html/responsive_ajax.viewAreaAndTitle.xsl";
	}
	private String getSerializer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) return "mobile";
		return "html";
	}
	protected void storeScroller() throws Exception {
	// (getHistoryManager().getLastHistory().getFirstProvider()==null) return;
	//	getHistoryManager().getLastHistory().getFirstProvider().setScroller(this.getRequest().getScroller());
	}	
}

