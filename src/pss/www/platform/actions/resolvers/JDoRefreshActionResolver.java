/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import java.io.Serializable;
import java.util.Iterator;

import pss.core.tools.JMessageInfo;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;

public class JDoRefreshActionResolver extends JDoAjaxActionResolver {

	@Override
	protected String getBaseActionName() {
		return "back";
	}

	protected boolean isFirstAccess() throws Exception {
		return false;
	}
	protected boolean isBack() throws Exception {
		return false;
	}

	@Override
	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("__requestid", ""+getRequest().getIdRequestJS());
		if (this.getSession()!=null && addDictionaryInfo( )) {
//			this.addObjectToResult("__dictionary", ""+this.getSession().winRegisteredObjects(getRequest().getPssIdDictionary(),true,false));
			JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(),true,false);
//			this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
		}
		BizAction action = getHistoryManager().getLastHistoryAction();
		action.getObjSubmit().setMessage((JMessageInfo)null);
//		act.setSubmitedByUser(false);
		
		if (isBack())
			this.restoreMeta(action);
		else
			this.storeMeta(action);		

		this.attachDynamicFilters();
		this.assignTarget(null);
		this.storeScroller();
		return super.perform();
	}

 
	private JWebActionData getFilters(String provider) throws Exception {
		return this.getRequest().getFormData("filter_pane_"+provider);
	}
	
	protected void attachDynamicFilters() throws Exception {
		JHistory h = this.getRequest().getSession().getHistoryManager().getLastHistory();
		Iterator<JHistoryProvider> it=h.getProviders().values().iterator();
		while (it.hasNext()) {
			JHistoryProvider provider= it.next();
			BizAction action = provider.getAction();
			if (action==null) continue;
			action.setFirstAccess(false); 
			JWebActionData webFilterBar = this.getFilters(provider.getAction().getProviderName());
			if (webFilterBar==null) continue;
			if (webFilterBar.isNull()) continue;
			if (webFilterBar.isEmpty()) continue;
			JIterator<JWebActionDataField> iter=webFilterBar.getFieldIterator();
			while (iter.hasMoreElements()) {
				JWebActionDataField filter=iter.nextElement();
				if (filter.getName().endsWith("_time") || filter.getName().endsWith("_date")) continue;
				if (filter.getValue().startsWith("obj_")) {
					Serializable obj = this.getRequest().getRegisterObject(filter.getValue());
					action.addFilterMap(filter.getName(), obj);
					continue;
				}
				action.addFilterMap(filter.getName(), filter.getValue());
			}
			provider.setFilterMap(action.getFilterMap());
		}
		
	}

	
}
