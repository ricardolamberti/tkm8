/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.common.security.BizUsuario;
import pss.www.platform.actions.requestBundle.JWebActionData;
public class JSessionTimeOutGenerator extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "ajax.SessionTimeOut";
	}

//	@Override
//	protected void doGenerate() throws Exception {
//		JWebActionData oData=this.oRequest.getData("msg_attrs");
//		String sMessage=oData.get("msg");
//		this.startNode("message");
//		this.setAttribute("type", "SessionTimeOut");
//		this.addTextNode("text", sMessage);		this.endNode("message");	}

	@Override
	protected void doGenerate() throws Exception {
		JWebActionData oData=this.oRequest.getData("msg_attrs");
		String sMessage=oData.get("msg");
		this.startNode("message");
		this.setAttribute("type", "SessionTimeOut");
		this.setAttribute("loginpage", BizUsuario.getCurrentLoginPage());
		this.addTextNode("text",sMessage);
		this.endNode("message");
	}
}
