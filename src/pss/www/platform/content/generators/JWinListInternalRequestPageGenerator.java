package pss.www.platform.content.generators;

import org.apache.cocoon.environment.ObjectModelHelper;

import pss.common.security.BizUsuario;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.resolvers.JDoInternalRequestResolver;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebServer;

public class JWinListInternalRequestPageGenerator extends JWinListRefreshPageGenerator {
	
	@Override
	protected boolean isSessionDependent() {
		return false;
	}

	JAct act;
	
	protected JAct getAct() throws Exception {
		return act;
	}

	protected void takeApplicationData() throws Throwable {
		this.oApplication=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(this.objectModel));
		this.oContext=(JWebApplicationContext) oApplication.getApplicationContext();
		String id= getRequest().getArgument("id");
		BizUsuario usuario = JDoInternalRequestResolver.getInternaRequestUsuario(null,id);
		
//		JApplicationSessionManager sessionManager =  JWebServer.getInstance().getWebApplication(null).getSessionManager();
//		setSession( sessionManager.hasAnySessionWithUser(usuario.GetUsuario()));
//		getSession().resetWinId();
		act= JDoInternalRequestResolver.getInternaRequestAct(null,id);
		JDoInternalRequestResolver.removeInternaRequest(id);

	}

}
