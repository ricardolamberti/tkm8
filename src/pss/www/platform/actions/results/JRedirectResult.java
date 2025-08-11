/*
 * Created on 06-jun-2006 by PSS
 *
 * Copyright PuntoSur Soluciones 2006
 */

package pss.www.platform.actions.results;

import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Response;

import pss.core.tools.PssLogger;
import pss.www.platform.actions.JWebRedirector;
import pss.www.platform.actions.resolvers.JBasicWebActionResolver;



/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JRedirectResult extends JWebActionResult {


  private String sURL;
  private boolean bKeepSession;
  private JWebRedirector oRedirector;
  private boolean global;

  public JRedirectResult(String zURL, boolean zKeepSession, JWebRedirector zRedirector) {
    this.sURL = zURL;
    this.bKeepSession = zKeepSession;
    this.oRedirector = zRedirector;
    this.global=false;
  }

  public JRedirectResult(String zURL, boolean zKeepSession, JWebRedirector zRedirector, boolean global) {
    this.sURL = zURL;
    this.bKeepSession = zKeepSession;
    this.oRedirector = zRedirector;
    this.global = global;
  }  
  
  public void apply() throws ProcessingException {
    if (this.bKeepSession && this.oRedirector.getProcessor()==null) {
      throw new RuntimeException("Cannot redirect keeping session if there is no active session; lazy session creation is unsopported!");
    }
  
    try {
    	if (global)
        this.oRedirector.globalRedirect(this.bKeepSession, this.sURL);
    	else
    		this.oRedirector.redirect(this.bKeepSession, this.sURL);
    	
      this.oRedirector.getProcessor().clearResultsMap();
    } catch (Throwable e) {
//      PssLogger.logError(this.oRedirector.getProcessor().getApplication().getName(), e, "Error while redirecting to: " + this.sURL);
      PssLogger.logError(e, "Error while redirecting to: " + this.sURL);
      throw new ProcessingException("Error while redirecting to: " + this.sURL, e);
    }
  }

  
  @Override
	public void applyResultTo(JBasicWebActionResolver zResolver) throws Exception {
    if (this.bKeepSession && this.oRedirector.getProcessor()==null) {
      throw new RuntimeException("Cannot redirect keeping session if there is no active session; lazy session creation is unsopported!");
    }
    try {
  		setResponseHeaders(zResolver);
    	if (global)
        this.oRedirector.globalRedirect(this.bKeepSession, this.sURL);
    	else
    		this.oRedirector.redirect(this.bKeepSession, this.sURL);
      this.oRedirector.getProcessor().clearResultsMap();
    } catch (Exception e) {
      zResolver.logError(e, "Error while redirecting to: " + this.sURL);
      throw e;
    }
  }
  protected void setResponseHeaders(JBasicWebActionResolver zResolver) throws Exception {
  	Response zResponse = ObjectModelHelper.getResponse(zResolver.getObjectModel());
  	Request zRequest = ObjectModelHelper.getRequest(zResolver.getObjectModel());
  	zResponse.addHeader("Access-Control-Allow-Origin", zRequest.getHeader("Origin"));
		zResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Credentials,Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		zResponse.addHeader("Access-Control-Expose-Headers", "*");
		zResponse.addHeader("Access-Control-Allow-Credentials", "true");
		zResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
}

}
