/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;


public class JDoAjaxActionResolver extends JIndoorsActionResolver {

	@Override
	protected String getBaseActionName() {
		return "ajax-action";
	}

  @Override
	protected JWebActionResult perform() throws Throwable {
    this.addObjectToResult("ajax", "ajax");

//    this.setFilters();
    return super.perform();
	}
  
//  protected void setFilters() throws Throwable {
//		JWebActionData filters=this.getRequest().getFormData("filter_pane");
//		if (!(filters==null||filters.isNull()||filters.isEmpty())) {
//			this.getRequest().addModelObject("Pss.filters", filters);
//		}
//  }
	protected void storeScroller() throws Exception {
		if (getHistoryManager().getLastHistory().getFirstProvider()==null) return;
		getHistoryManager().getLastHistory().getFirstProvider().setScroller(this.getRequest().getScroller());
	}	
	protected JHistoryProvider findFirstProvider() throws Exception {
		if (!getRequest().hasTableProvider()) return null;		
		JHistory h = this.getSession().getHistoryManager().getLastHistory();
		return h.getFirstProvider();
	}	

	@Override
	protected void onPerform() throws Exception {
  }

	@Override
	protected boolean isAjax() {return true;}


}

