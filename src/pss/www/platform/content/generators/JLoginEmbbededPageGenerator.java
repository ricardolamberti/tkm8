package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.www.ui.views.JLoginEmbbededView;

public class JLoginEmbbededPageGenerator extends JFrontDoorPageGenerator {

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
//		String className=  BizPssConfig.getPssConfig().classLogin();
//		return Class.forName(className);
		return JLoginEmbbededView.class;
	}

	@Override
	protected String getBaseContentName() {
		return "login.page";
	}

}
