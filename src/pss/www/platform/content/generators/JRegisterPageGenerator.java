package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.core.data.BizPssConfig;

public class JRegisterPageGenerator extends JFrontDoorPageGenerator {

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
		String className=  BizPssConfig.getPssConfig().classRegisterLogin();
		return Class.forName(className);
//		return JRegistrationView.class;
	}
	

	@Override
	protected String getBaseContentName() {
		return "register.page";
	}

}