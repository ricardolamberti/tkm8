/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.tools.collections.JIterator;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.JWebWinFormCombo;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasWrapperView;

public class JComboPageGenerator extends JXMLPageGenerator {

	private JAct submit=null;

	@Override
	protected String getBaseContentName() {
		return "console.combo";
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

		submit=this.getAct();
		String completeName=this.getRequest().getComboId();
		String sFormLovRowId=completeName.indexOf(".")==-1?"":completeName.substring(0,completeName.indexOf("."));
		String comboId=completeName.indexOf(".")==-1?completeName:completeName.substring(completeName.indexOf(".")+1);

		if (sFormLovRowId.indexOf("_row_")==-1) sFormLovRowId=null;

		JWebWinFormCombo oCombo=JWebWinFormCombo.createCombo(submit, comboId);

		String tableProvider = this.getRequest().hasTableProvider()? getRequest().getTableProvider():extractProvider(sFormLovRowId);

		JFormControles oControles=oCombo.getControls();
		JWebActionData oDataBundle=getRequest().getFormData(tableProvider);
		JIterator<JWebActionDataField> oFieldIt=oDataBundle.getFieldIterator();
		while (oFieldIt.hasMoreElements()) {
			JWebActionDataField oField=oFieldIt.nextElement();
			if (oField.getValue().equals("")) continue;
			JFormControl ctrl=oControles.findControl(sFormLovRowId,oField.getName());
			if (ctrl==null) continue;
			ctrl.setForcedValue(oField.getValue());
		}
		// Limpio el combo objetivo porque puede haberse seteado un DEFAULT y no se recargará
		oControles.findControl(comboId).clear();

		JWebCanvas oCanvas=oCombo.getRootPanel();
		JWebView oView=new JCanvasWrapperView(oCanvas);
		
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
//		this.submit.cleanAction();
		super.cleanUp();
	}

}
