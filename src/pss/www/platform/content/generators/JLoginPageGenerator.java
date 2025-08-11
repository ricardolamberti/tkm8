/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.www.ui.views.JLoginView;

public class JLoginPageGenerator extends JFrontDoorPageGenerator {

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
//		String className=  BizPssConfig.getPssConfig().classLogin();
//		return Class.forName(className);
		return JLoginView.class;
	}
	

	@Override
	protected String getBaseContentName() {
		return "login.page";
	}

}
