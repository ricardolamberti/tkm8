/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.core.win.submits.JAct;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinListResponsive;

public class JWinListExpandPageGenerator extends JWinListRefreshPageGenerator {

	@Override
	protected JWebWinGenericResponsive createWinList(JAct act,  JHistoryProvider hp) throws Exception {
		JWebWinGenericResponsive wl = super.makeWinList(act, hp);
		wl.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		wl.setForceSelected(true);
		wl.ensureIsBuilt();
		return wl;
	}
	protected JWebWinGenericResponsive createWinListJSon(JAct oAct,  JHistoryProvider hp) throws Exception {
		JWebWinGenericResponsive wl = super.makeWinListJSon(oAct, hp);
		wl.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		wl.setForceSelected(true);
		wl.ensureIsBuilt();
		return wl;
	}
}