package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.www.ui.views.JClosedView;


public class JClosedPageGenerator extends JFrontDoorPageGenerator {

	protected void attachToRunningThread() throws Exception {
		invalidateSessionIfAny(true, "close required by user");
		super.attachToRunningThread();
	}
	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
//		String className=  BizPssConfig.getPssConfig().classLogin();
//		return Class.forName(className);
		return JClosedView.class;
	}

	@Override
	protected String getBaseContentName() {
		return "closed.page";
	}

}