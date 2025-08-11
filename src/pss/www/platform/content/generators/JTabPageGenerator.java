/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebTabResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.JWebWinFormNotebook;
import pss.www.ui.views.JCanvasWrapperView;

public class JTabPageGenerator extends JIndoorsPageGenerator {

	@Override
	protected String getBaseContentName() {
		return "console.tab";
	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_INDOORS;
	}
	
	// RJL: esta clase era para actuualizar el tab sin refrescar toda la pantalla, ahora no se esta usando, solo no lo lo borro por si lo volvemos a necesitar

	@Override
	protected JWebView createView() throws Exception {
		JAct submit = this.getAct();
		JBaseWin baseWin = this.getBaseWin(submit);

		JHistoryProvider hp = this.getSession().getHistoryManager().getLastHistory().findProvider(this.getRequest().getTableProvider().replace("form_", ""));
		
		JWebWinFormNotebook formNotebook=JWebWinFormNotebook.createFormNotebook(hp);
		formNotebook.setSourceAction(hp.getAction());

//		formNotebook.setProviderName(baseWin.getTableProvider()); // para que no pierda el provider original
		JWebTabResponsive notebook = formNotebook.findTab(submit.getActionSource().getProviderName());
		notebook.setParent(null);
		JWebView oView=new JCanvasWrapperView(notebook);
		
		return oView;
	}
	
//	protected JWebActionData getFilters() {
//		return (JWebActionData) objectModel.get("Pss.filters");
//	}
	
	@Override
	protected boolean isSessionDependent() {
		return true;
	}


}
