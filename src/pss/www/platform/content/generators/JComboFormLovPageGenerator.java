/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.net.URLDecoder;

import org.apache.commons.lang.CharEncoding;

import pss.core.tools.collections.JIterator;
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
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.JWebWinFormLovCombo;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasResponsiveEmptyView;

public class JComboFormLovPageGenerator extends JXMLPageGenerator {

	private JAct submit=null;

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
		if (name==null || name.equals("")) return "win_form";
		String out = name;
		if (out.startsWith("dgf_")) out = out.substring(4);
		if (out.endsWith("_fd")) out = out.substring(0,out.length()-3);
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

		JAct act=getAct();
		JAct actTab=(JAct)objectModel.get("Pss.act");
		String completeName=this.getRequest().getComboFormLovId();
		String sAction=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"object_action");
		String sProvider=getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"table_provider");
		String sFormLovRowId=completeName.indexOf(".")==-1?"":completeName.substring(0,completeName.indexOf("."));
		String comboId=completeName.indexOf(".")==-1?completeName:completeName.substring(completeName.indexOf(".")+1);

		if (sFormLovRowId.indexOf("_row_")==-1) sFormLovRowId=null;
		else if (actTab instanceof JActNew) ((JActNew)act).setNoWas(false);
		
		JBaseForm baseForm=act.getForm().build();
//
//		String tableProvider = this.getRequest().hasTableProvider()? getRequest().getTableProvider():extractProvider(sFormLovRowId);

		JFormControles oControles;
		JWebActionData oDataBundle;
		JWins wins;
		if (sAction==null || sAction.equals("")) {
//			if ((act instanceof JActWins)) {
//				JFormFiltro filtros=new JFormFiltro(act.getWinsResult());
//				filtros.initialize();
//				oControles=filtros.GetControles();
//				oDataBundle=getRequest().getFormData("filter_pane");
//			} else {
				oControles=baseForm.getControles();
				IRow row=oControles.findRow(sFormLovRowId);
				String tableProvider = this.getRequest().hasTableProvider()? getRequest().getTableProvider():extractProvider(sFormLovRowId);
				if (row!=null) {
					baseForm=row.GetForm();
					baseForm.setName(row.getProvider());
				} else {
					baseForm.setName(tableProvider);
				}
				oDataBundle=getRequest().getFormData(tableProvider);
				if (oControles.findControl(sFormLovRowId,comboId)==null && actTab!=null && actTab instanceof JActWins) {
					JFormFiltro filtros=new JFormFiltro(actTab.getWinsResult());
					filtros.initialize();
					oControles=filtros.GetControles();
					oDataBundle=getRequest().getFormData("filter_pane_"+sProvider);
				}
		//	}
			JIterator<JWebActionDataField> oFieldIt=oDataBundle.getFieldIterator();
			while (oFieldIt.hasMoreElements()) {
				JWebActionDataField oField=oFieldIt.nextElement();
				JFormControl ctrl=oControles.findControl(sFormLovRowId,oField.getName());
				if (ctrl==null)
					continue;
				String extraData =null;
				if  (oField.getName().equals(comboId)) {
					if (row==null)
						 extraData =  act.getWinResult().getRecord().getExtraData(comboId+"_text");
					else
						 extraData =  row.getWin().getRecord().getExtraData(comboId+"_text");
				}
//				if (extraData!=null && ctrl instanceof JFormLOV) ((JFormLOV)ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
//				if (extraData!=null && ctrl instanceof JWinLOV) ((JWinLOV)ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
				if (extraData!=null && ctrl instanceof JFormWinLOVResponsive) ((JFormWinLOVResponsive)ctrl).setSearchString(URLDecoder.decode(extraData, CharEncoding.ISO_8859_1));
				if (oField.getValue().equals("")) continue;
				ctrl.setForcedValue(oField.getValue());
							
			}
		}
		
		// Limpio el combo objetivo porque puede haberse seteado un DEFAULT y no se recargará
		// oControles.findControl(comboId).clear();

		JWebWinFormLovCombo oCombo=JWebWinFormLovCombo.createCombo(baseForm, comboId);
		JWebCanvas oCanvas=oCombo.getRootPanel();
		JWebView oView=new JCanvasResponsiveEmptyView(oCanvas);
		return oView;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		return oPage;
	}

	@Override
	protected void cleanUp() {
		// this.submit.cleanAction();

		super.cleanUp();
	}

}
