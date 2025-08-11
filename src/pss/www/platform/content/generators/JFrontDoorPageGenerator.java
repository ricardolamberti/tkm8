package pss.www.platform.content.generators;

import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebServer;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebViewsConstants;

public abstract class JFrontDoorPageGenerator extends JXMLPageGenerator {



	@Override
	protected void attachToRunningThread() throws Exception {
		this.invalidateSessionIfAny(false,null);
		super.attachToRunningThread();
	}

	protected void invalidateSessionIfAny(boolean force,String forceReason) throws Exception {
		// as this is a front door page, ensure there is no active session before
		// invoking the page generation
//		Request oCocoonRequest=this.getServletRequest();
//		HttpSession oServletSession=oCocoonRequest.getSession(false);
//		if (oServletSession==null) {
//			this.setSessionToNull();
//			return;
//		}

//		JApplicationSessionManager oManager=this.getApplication().getSessionManager();
//		String sId=JBasicWebActionResolver.getSessionID(oCocoonRequest, oServletSession);
//		if (oManager.hasSessionWithId(sId,oCocoonRequest.getParameter("subsession"))) {
//			if (force || BizPssConfig.getPssConfig().isOnlyOneSessionByBrowser())
//				oManager.closeSessionById(sId,oCocoonRequest.getParameter("subsession"),force?forceReason:" only one session by browser");
//		} else {
////			this.logInfo("Invalidating servlet session '"+sId+"'");
////			oServletSession.invalidate();
//		}
//		this.setSessionToNull();
		JWebApplication app =JWebServer.getInstance().getWebApplication(null);
		app.getStadistics().removeInfoSession(getSession());
	

	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_FRONT_DOOR;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=super.createPage();
		// oPage.addHeaderComponent(new JWaitingPane(this.getSkinProvider().getSkinFor(this.getSession())));
		return oPage;
	}

}
