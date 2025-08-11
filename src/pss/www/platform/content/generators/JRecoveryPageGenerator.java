package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.www.ui.views.JRecoveryView;

public class JRecoveryPageGenerator extends JFrontDoorPageGenerator {

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
//		String className=  BizPssConfig.getPssConfig().classLogin();
//		return Class.forName(className);
		return JRecoveryView.class;
	}
	

	@Override
	protected String getBaseContentName() {
		return "recovery.page";
	}

}