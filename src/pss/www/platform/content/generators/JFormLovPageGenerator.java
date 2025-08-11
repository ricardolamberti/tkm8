/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.JWebWinListFormLov;
import pss.www.ui.JWebWinListResponsive;

public class JFormLovPageGenerator extends JIndoorsPageGenerator {

	 
	@Override
	protected String getBaseContentName() {
		return "console.formLov";
	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_INDOORS;
	}

	@Override
	protected boolean isSessionDependent() {
		return true;
	}

	public String extractProvider(String name) throws Exception {
		if (name==null || name.equals("")) return "win_form";
		String out = name;
		if (out.startsWith("dgf_")) out = out.substring(4);
		if (out.endsWith("_fd")) out = out.substring(0,out.length()-3);
		return out;
	}
	
	protected JAct findAct() throws Exception {
//	if (getSession().getIdRequest()==0) {
//		return (JAct)getSession().getHistoryManager().getLastSubmit();
//	}
	return (JAct)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("action");
}
	@Override
	protected JAct getAct() {
		try {
			JAct act =findAct();
			
			JHistoryProvider p = this.getSession().getHistoryManager().getLastHistory().findProvider(this.getRequest().getTableProvider());
			JAct actTab=p==null?null:p.getActionSubmit();
			String sFormLovControlId=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"formLov_id");
			String sFormLovFilter=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"formLov_filter");
			String sProvider = getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "table_provider");
			String sAction=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"object_action");
			boolean multipleSelection=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"multiple").equalsIgnoreCase("true");
			String sFormLovRowId=sFormLovControlId.indexOf(".")==-1?"":sFormLovControlId.substring(0,sFormLovControlId.indexOf("."));
			sFormLovControlId=sFormLovControlId.substring(sFormLovControlId.indexOf(".")+1);

			if (sFormLovRowId.indexOf("_row_")==-1) sFormLovRowId=null;
//			else act=actTab;
			
						
			JFormControles oControles;
			JWebActionData oDataBundle;
			JWins wins;
			if (sAction==null || sAction.equals("")) {
				if ((act instanceof JActWins)) {
					JFormFiltro filtros=new JFormFiltro(act.getWinsResult());
					filtros.initialize();
					oControles=filtros.GetControles();
					oDataBundle=getRequest().getFormData("filter_pane_"+sProvider);
				} else {
					oControles=act.getForm().build().getControles();
					String tableProvider = this.getRequest().hasTableProvider()? getRequest().getTableProvider():extractProvider(sFormLovRowId);
					oDataBundle=getRequest().getFormData(tableProvider);
					if (oControles.findControl(sFormLovRowId,sFormLovControlId)==null && actTab!=null && actTab instanceof JActWins) {
						JFormFiltro filtros=new JFormFiltro(actTab.getWinsResult());
						filtros.initialize();
						oControles=filtros.GetControles();
						oDataBundle=getRequest().getFormData("filter_pane_"+sProvider);
					}
				}
	
				JIterator<JWebActionDataField> oFieldIt=oDataBundle.getFieldIterator();
				while (oFieldIt.hasMoreElements()) {
					JWebActionDataField oField=oFieldIt.nextElement();
					if (oField.getValue().equals("")) continue;
					JFormControl ctrl=oControles.findControl(sFormLovRowId,oField.getName());
					if (ctrl==null)
						continue;
					ctrl.setForcedValue(oField.getValue());
				}
				JFormWinLOVResponsive oFormLovControl=(JFormWinLOVResponsive) oControles.findControl(sFormLovRowId,sFormLovControlId);
				wins=oFormLovControl.getControlWins().getRecords(false);
			}
			else {
				//wins = act.getResult().getSubmit(act.getResult().findAction(Integer.parseInt(sAction))).getWinsResult();
				act.setActionSource(act.getResult().findAction(Integer.parseInt(sAction)));
				return act.getActionSource().getObjSubmit();
			}
			
			if (!getRequest().isArgumentEmpty(sFormLovFilter)) {
				JWin winClass=wins.createWin();
				wins.getRecords().addFilter(winClass.getDescripField(), sFormLovFilter.trim()+"%", "LIKE").setDynamic(true);
			}
			
			BizAction action = new BizAction();
			action.setObjOwner(wins);
			action.setObjSubmit(new JActWins(wins,multipleSelection));
			return action.getObjSubmit();
		} catch (Exception e) {
			PssLogger.logError(e);
			return null;
		}
	}

	@Override
	protected JWebView createView() throws Exception {
		JWebView oView=super.createView();
		oView.setSize(getRequest().getContainerWidth(), getRequest().getContainerHeight());
		return oView;
	}

	@Override
	protected JWebWinListResponsive createWinList(JAct oAct, JHistoryProvider hp) throws Exception {
		JActWins act = (JActWins) oAct;
		JWebWinListResponsive oWinList=new JWebWinListFormLov(act.getActionSource());
		oWinList.setPreviewFlag(JWins.PREVIEW_NO);
		oWinList.setMultipleSelection(act.isMultiple());
//		oWinList.setShowFilterBar(isHideStatusBar() );
		oWinList.ensureIsBuilt();
		return oWinList;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		String sFormLovControlId=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"formLov_id");
		JWebPage oPage=super.createPage();
		oPage.addHeaderComponent(new JFormLovInfoGenerator(sFormLovControlId));
		return oPage;
	}
	
	protected String getSkinStereotypeView() throws Exception {
		return "canvas_formlov";
	}
	
	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_NO;
	}

	
	@Override
	protected void postEndDocument() throws Exception {
		
	}
}
