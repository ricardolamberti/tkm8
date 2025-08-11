package pss.www.platform.content.generators;

import java.net.URLDecoder;

import org.apache.commons.lang.CharEncoding;

import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.responsiveControls.IRow;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.JWebWinFormLovComboResponsive;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasResponsiveEmptyView;

public class JComboResponsiveFormLovPageGenerator extends JXMLPageGenerator {

	private JAct submit = null;

	@Override
	protected String getBaseContentName() {
		return "console.comboFormLov";
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
		if (name == null || name.equals(""))
			return "win_form";
		String out = name;
		if (out.startsWith("dgf_"))
			out = out.substring(4);
		if (out.endsWith("_fd"))
			out = out.substring(0, out.length() - 3);
		else if (out.indexOf("_fd")!=-1)
			out = out.substring(0, out.indexOf("_fd"));
		return out;
	}
	protected JAct getAct() throws Exception {
//	if (getSession().getIdRequest()==0) {
//		return (JAct)getSession().getHistoryManager().getLastSubmit();
//	}
	return (JAct)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("action");
}
	
	
	@Override
	protected JWebView createView() throws Exception {

		JAct act = getAct();
		JAct actTab = (JAct) objectModel.get("Pss.act");
		String completeName = this.getRequest().getComboFormLovId();
		String sAction = getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_action");
		String sProvider = getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "table_provider");
		String sFormLovRowId = completeName.lastIndexOf("-") == -1 ? "" : completeName.substring(0, completeName.lastIndexOf("-"));
		String comboId = completeName.lastIndexOf("-") == -1 ? completeName : completeName.substring(completeName.lastIndexOf("-") + 1);

	  	if (sProvider.startsWith("form_")) sProvider = sProvider.substring(5);
		JHistoryProvider prov = this.getSession().getHistoryManager().getLastHistory().findProvider(sProvider);
		submit = prov.getActionSubmit();
		
		if (sFormLovRowId.indexOf("_row_") == -1)
			sFormLovRowId = null;
		else if (actTab instanceof JActNew)
			((JActNew) act).setNoWas(false);

		JBaseForm baseForm =null;
		JFormFiltro filtros = null;
		JFormControles oControles;
		JWebActionData oDataBundle;
		String tableProvider;
		IRow row = null;
		if (sAction == null || sAction.equals("")) {
			if ((act.getResult() instanceof JWins)) {
				filtros = new JFormFiltro(act.getWinsResult());
				filtros.initialize();
//				filtros.convertToResponsive();
				filtros.applyFilterMap(submit.getActionSource(),false);
				oControles = filtros.GetControles();
				oDataBundle = getRequest().getFormData("filter_pane_"+sProvider);
				tableProvider = "filter_pane_"+sProvider;
			} else if ((act instanceof JActWins)) {
				filtros = new JFormFiltro(act.getWinsResult());
				filtros.initialize();
//				filtros.convertToResponsive();
				filtros.applyFilterMap(submit.getActionSource(),false);
				oControles = filtros.GetControles();
				oDataBundle = getRequest().getFormData("filter_pane_"+sProvider);
				tableProvider = "filter_pane_"+sProvider;
			} else {
				baseForm = act.getForm().build();
//				if (sFormLovRowId!=null)
//					baseForm.generate();
				oControles = baseForm.getControles();
				
				row = oControles.findRow(sFormLovRowId);
				tableProvider = this.getRequest().hasTableProvider() ? /*getRequest().getTableProvider() */ ""+extractProvider(completeName) : extractProvider(sFormLovRowId);
				if (row != null) {
					baseForm = row.GetForm();
					baseForm.setName(row.getProvider());
					oDataBundle = getRequest().getFormData(row.getProvider());
				} else {
					baseForm.setName(tableProvider);
					oDataBundle = getRequest().getFormData(tableProvider);
				}
			}
		} else {
			baseForm = act.getForm().build();
//			if (sFormLovRowId!=null)
//				baseForm.generate();
			
			tableProvider = this.getRequest().hasTableProvider() ? getRequest().getTableProvider() : extractProvider(sFormLovRowId);
			baseForm.setName(tableProvider);
			oControles = baseForm.getControles();
			oDataBundle = getRequest().getFormData(tableProvider);
		}


		if (oControles.findControl(sFormLovRowId, comboId) == null && actTab != null && actTab instanceof JActWins) {
			filtros = new JFormFiltro(actTab.getWinsResult());
			filtros.initialize();
			oControles = filtros.GetControles();
			oDataBundle = getRequest().getFormData("filter_pane_"+sProvider);
		}
		// }
		 JFormControl ctrl;
		 String extraData;
		if (baseForm==null) {
//			JIterator<JWebActionDataField> oFieldIt = oDataBundle.getFieldIterator();
//			while (oFieldIt.hasMoreElements()) {
//				JWebActionDataField oField = oFieldIt.nextElement();
//				JFormControl ctrl = null;
//				if (baseForm!=null)
//					ctrl=baseForm.findControl( oField.getName());
//				else if (filtros!=null)
//					ctrl=filtros.findControl( oField.getName());
//				if (ctrl == null)
//					continue;
//				String extraData = null;
//				if (oField.getName().equals(comboId)) {
//					extraData = getRequest().getArgument("search");
//					if (extraData==null) {
//						if (row == null) {
//							if (act.isWin())
//								extraData = act.getWinResult().getRecord().getExtraData(comboId + "_text");
//						} else
//							extraData = row.getWin().getRecord().getExtraData(comboId + "_text");
//					}
//				}
//			}
			 ctrl=filtros.findControl( comboId);
		
		} else {
			 ctrl=baseForm.findControl( comboId);
			}
		 extraData = getRequest().getArgument("search");
		if (extraData==null) {
			if (row == null) {
				if (act.isWin())
					extraData = act.getWinResult().getRecord().getExtraData(comboId + "_text");
			} else
				extraData = row.getWin().getRecord().getExtraData(comboId + "_text");
		}
//			if (extraData != null && ctrl instanceof JFormLOV)
//				((JFormLOV) ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
//			if (extraData != null && ctrl instanceof JWinLOV)
//				((JWinLOV) ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
			if (extraData != null && ctrl instanceof JFormWinLOVResponsive) {
//				if (act instanceof JActFieldSwapWins) {
//					((JFormWinLOVResponsive) ctrl).setNeverUseCache(true);
//					((JFormWinLOVResponsive) ctrl).setUseID(false);
//				}
				((JFormWinLOVResponsive) ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
			} 

//			if (oField.getValue().equals(""))
//				continue;
//			ctrl.setForcedValue(oField.getValue());

		//}

		// Limpio el combo objetivo porque puede haberse seteado un DEFAULT y no se
		// recargará
		// oControles.findControl(comboId).clear();

		JWebWinFormLovComboResponsive oCombo = null;
		if(baseForm!=null) {
			oCombo = JWebWinFormLovComboResponsive.createCombo(baseForm, act, comboId);
		}	else if(filtros!=null) {
			oCombo = JWebWinFormLovComboResponsive.createCombo(filtros, act, comboId);
		} else { 
			oCombo = JWebWinFormLovComboResponsive.createCombo(oControles, act, comboId);
		}
		JWebCanvas oCanvas = oCombo.getRootPanel();
		JWebView oView = new JCanvasResponsiveEmptyView(oCanvas);
	
		return oView;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage = new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		return oPage;
	}

	@Override
	protected void cleanUp() {
		// this.submit.cleanAction();
		super.cleanUp();
	}

}
