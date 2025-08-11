package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Request;

import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.views.JMessageView;

public class JVerifiedPageGenerator extends JFrontDoorPageGenerator {

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() throws Exception {
		Request oCocoonRequest=this.getServletRequest();
		setIpaddress(oCocoonRequest.getRemoteAddr());
	
		JAplicacion.openSession();
		JAplicacion.GetApp().openApp("verified", JAplicacion.AppService(), true);


		//BizAttorney.processEncontrarYVerificar(oCocoonRequest.getParameter("code"));

		JAplicacion.GetApp().closeApp();
		JAplicacion.closeSession();
		
    
	  this.getRequest().addModelObject("title", "Verificaci�n");
	  this.getRequest().addModelObject("msg", BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "MENSAJE_VERIFICACION_MAIL", "Se verific� el correo electr�nico") );
	  this.getRequest().addModelObject("type", JMessageView.getMessageTypeString(JWebViewsConstants.MESSAGE_TYPE_INFO));
	  
		return JMessageView.class;//getRedirector().goShowMessage(JWebViewsConstants.MESSAGE_TYPE_INFO,"Mail verificado", "Mail verificado");
		
	}
	


	@Override
	protected String getBaseContentName() {
		return "verified.page";
	}

}