/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinListFormLov;
import pss.www.ui.JWebWinListResponsive;

public class JFormLovRefreshPageGenerator extends JWinListRefreshPageGenerator {

	


	protected JAct getAct() throws Exception {
//	if (getSession().getIdRequest()==0) {
//		return (JAct)getSession().getHistoryManager().getLastSubmit();
//	}
	return (JAct)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("action");
}
	
	@Override
	protected JWebWinGenericResponsive createWinList(JAct oAct, JHistoryProvider hp) throws Exception {
		JActWins act = (JActWins) oAct;
		JWebWinGenericResponsive oWinList=new JWebWinListFormLov(act.getActionSource());
	  oWinList.setToolbar(JBaseWin.TOOLBAR_TOP);
	  oWinList.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		oWinList.setPreviewFlag(JWins.PREVIEW_NO);
//		oWinList.setToolTip(JBaseWin.TOOLBAR_NONE);
		oWinList.setMultipleSelection(act.isMultiple());
		
//		oWinList.setShowFilterBar(isHideStatusBar() );
		oWinList.ensureIsBuilt();
		if (getRequest().hasTableProvider()) oWinList.setName(getRequest().getTableProvider());

		return oWinList;
	}
	

}
