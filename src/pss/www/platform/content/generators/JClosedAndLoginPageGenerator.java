package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Response;

import pss.core.data.BizPssConfig;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplication;
import pss.www.ui.controller.JWebClientConfiguration;

public class JClosedAndLoginPageGenerator extends JLoginByIniPageGenerator {

	protected void attachToRunningThread() throws Exception {
		invalidateSessionIfAny(true, "close required by user (relogin)");
		super.attachToRunningThread();

	}
	protected Class getViewClass() throws Exception {
	
		return super.getViewClass();
	}
	
	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
		if (JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("mobile") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("html") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("graph") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("map")) {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
		}
		if (getRequest().getArgument("persistence")==null) {
	  	if (getRequest().isMobile()) {
	  		zResponse.addHeader("set-mobile-persistent-cookie", "JPERSISTENTSESSIONID=; Max-Age=0 ;Path=/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"; Secure; HttpOnly");
			}
	  	zResponse.addHeader("set-cookie", "JPERSISTENTSESSIONID=; Max-Age=0 ;Path=/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"; Secure; HttpOnly");
		}
  	zResponse.addHeader("Access-Control-Allow-Origin", getRequest().getHeader("Origin")==null?"*":getRequest().getHeader("Origin"));
		zResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Credentials,Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		zResponse.addHeader("Access-Control-Expose-Headers", "*");
		zResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
  	zResponse.addHeader("Access-Control-Allow-Credentials", "true");
		
	}
}
