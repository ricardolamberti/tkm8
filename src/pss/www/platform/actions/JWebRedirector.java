/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import org.apache.cocoon.environment.Redirector;
import org.apache.commons.codec.net.URLCodec;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.tools.PssLogger;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.results.JRedirectResult;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.JWebViewsConstants;
import pss.www.ui.views.JMessageView;


public class JWebRedirector {

  private Redirector oRedirector;
  private JWebPipelineActionProcessor oProcessor;
  
  public JWebRedirector(Redirector zRedirector, JWebPipelineActionProcessor zProcessor) {
    this.oRedirector = zRedirector;
    this.oProcessor = zProcessor;
  }

  //
  //  PUBLIC REDIRECTION METHODS
  //

  public JWebActionResult goSessionTimeOut(boolean multiuser) throws Exception {
  	if (!this.getProcessor().getRequest().isAjax())
  		 return goLoginPage();
    
  	JWebAction oAction;
  	if (this.getProcessor().getRequest().isMobile())
    	oAction = JWebActionFactory.createRedirect("mobile-sessionTimeOut", false, this.oProcessor.getRequest());
  	else
  		oAction = JWebActionFactory.createRedirect("ajax-sessionTimeOut", false, this.oProcessor.getRequest());
  	
    JWebActionData oData = oAction.addData("msg_attrs");
    if (multiuser)
    	 oData.add("msg",BizUsuario.getMessage("Multiples usuarios, la sesion ha caducado, debe reingresar su usuario y clave", null));
    else
    	 oData.add("msg",BizUsuario.getMessage("La sesion ha expirado, debe reingresar su usuario y clave", null));
  	 oData.add("type","error");
 	 
    return this.goTo(false, oAction.asURL());
  }
  public JWebActionResult goPersistentLogin() throws Exception {
  	if (getProcessor().getRequest().getURI().indexOf("mobile")!=-1)
      return this.goTo(false, "persistentLogin?is_mobile=Y");
    return this.goTo(false, "persistentLogin");
  }
  public JWebActionResult goLoginPage() throws Exception {
    return this.goTo(false, BizUsuario.getCurrentLoginPage());
  }
  public JWebActionResult goPasswordChangePage(String zUsername) {
  	JWebAction oAction;
    try {
      JWebActionData oFormData = this.oProcessor.getRequest().getFormData("login_form");
      if (oFormData==null || oFormData.isNull()) 
        throw new RuntimeException("Password change request didn't come from login page");
     	oAction = JWebActionFactory.createRedirect("password.change", false, this.oProcessor.getRequest());
    } catch (Exception e) {
    	PssLogger.logError(e, "Error going to password change");
      oAction = JWebActionFactory.create("password.change", false);
    }
    return this.goTo(false, oAction.asURL());
  }
  public JWebActionResult goPasswordChangePageByMail(String zEmail) {
  	JWebServerAction oAction;
    try {
    	
      oAction = (JWebServerAction)JWebActionFactory.createRedirect("password.change", false, this.oProcessor.getRequest());
      oAction.setRedirectorParam("&dgf_login_form_fd-username="+new URLCodec().encode(zEmail));
    } catch (Exception e) {
    	PssLogger.logError(e, "Error going to password change");
      oAction = JWebActionFactory.create("password.change", false);
    }
    return this.goTo(false, oAction.asURL());
  }
  
  public boolean isErrorApplication(Throwable zException) {

  	if (!(zException instanceof RuntimeException))  return false;
  	if (BizUsuario.getUsr()==null) return false;
   	try {
			return BizUsuario.getUsr().getObjBusiness().isErrorApplication(zException);
		} catch (Exception e) {
			return false;
		}
     	
  	
  }
  
  public JWebActionResult goShowMessage(String zTitle, Throwable zException) {
  	return goShowMessage(zTitle, zException,false,false);
  } 
  public JWebActionResult goShowMessage(String zTitle, Throwable zException,boolean reload,boolean isInfo) {
    String sMsg;
    if (isErrorApplication(zException)) {
      sMsg = JLanguage.translate(BizUsuario.getMessage("Error de la aplicación", null))+": {" + zException.getMessage() + "}";
    } else {
      sMsg = zException.getMessage();
    }
    

    return this.basicGoShowMessage(isInfo?JWebViewsConstants.MESSAGE_TYPE_INFO:JWebViewsConstants.MESSAGE_TYPE_ERROR, zTitle, sMsg, reload);
  }
  public JWebActionResult goShowMessage(int zMessageType, String zTitle, String zMessage) throws Exception {
    return this.goShowMessage(zMessageType, zTitle, zMessage,false);
  }
  public JWebActionResult goShowMessage(int zMessageType, String zTitle, String zMessage,boolean reload) throws Exception {
    return this.basicGoShowMessage(zMessageType, zTitle, zMessage, reload);
  }
  public JWebActionResult goTo(JWebServerAction zAction) throws Exception {
//    JWebActions.fillSourceActionDataFor(this.oProcessor.getRequest(), zAction);
    return this.goTo(this.oProcessor.getSession()!=null, zAction.asURL());
  }
//  public JWebActionResult goPssAction(String zWinClass, int zActionNumber) throws Exception {
//    return this.goTo(true, JWebActionFactory.createRedirect(zWinClass, zActionNumber, true, this.oProcessor.getRequest()).asURL());
//  }
  public JWebActionResult goHomePage(String zurl) throws Exception {
//    String sActionId = this.getProcessor().getSession().getUserProfile().getHomePageAction().GetIdAccion();
//    String sActionId = this.getProcessor().getSession().getUserProfile().getHomePageAction();
//    String className = sActionId.substring(0, sActionId.indexOf('_'));`
  	String url = zurl!=null?zurl:this.getProcessor().getRequest().getSession().getUserProfile().getHomePageAction(true);
   	JWebServerAction action = (JWebServerAction)JWebActionFactory.createRedirectByAction(url, true, this.oProcessor.getRequest());
  //	this.getProcessor().getSession().setHomePage(url);
    return this.goTo(true, action.asURL());
  }

  public JWebActionResult goHomePage() throws Exception {
//    String sActionId = this.getProcessor().getSession().getUserProfile().getHomePageAction().GetIdAccion();
//    String sActionId = this.getProcessor().getSession().getUserProfile().getHomePageAction();
//    String className = sActionId.substring(0, sActionId.indexOf('_'));
    return this.goTo(true, JWebActionFactory.createRedirectByAction(this.getProcessor().getSession().getUserProfile().getHomePageAction(), true, this.oProcessor.getRequest()).asURL());
  }
  //
  //  internal methods for redirection
  //

  
  public void redirect(boolean zKeepSession, String zURL) throws Exception {
    this.oRedirector.redirect(zKeepSession, zURL);
  }
  
  public void globalRedirect(boolean zKeepSession, String zURL) throws Exception {
    this.oRedirector.globalRedirect(zKeepSession, zURL);
  }  
  
  public JWebRedirector copyFor(JWebPipelineActionProcessor zProcessor) {
    return new JWebRedirector(this.oRedirector, zProcessor);
  }
  private String getShowMessageEntry() {
   	if (oProcessor.getRequest().isMobile()) 
  		return "show.message.mobile"; 
   	else if (oProcessor.getRequest().isAjax()) 
  		return "show.message.ajax"; 
 		else
  		return "show.message"; 
  }

  protected JWebActionResult basicGoShowMessage(int zMessageType, String zTitle, String zMessage, boolean zReload) {
    String sType = JMessageView.getMessageTypeString(zMessageType);
    JWebAction oAction=null;//JWebActionFactory.getCurrentRequest().getSession().getHistoryManager()
    try {
    	if (zReload) {
      	oAction= JWebActionFactory.createRefresh();
	    } else {
	      oAction = JWebActionFactory.createRedirect(getShowMessageEntry(), false, this.oProcessor.getRequest());
	    }
    } catch (Exception e) {
    	PssLogger.logError(e, "Error going to show message");
      oAction = JWebActionFactory.create(getShowMessageEntry(), false);
    }    	

    oAction.clear();
    JWebActionData oData = oAction.addData("msg_attrs");
    if (zTitle != null) {
       oData.add("title", JLanguage.translate(zTitle)); 
    }
    oData.add("msg", JLanguage.translate(zMessage));
    oData.add("type", sType);
    oData.add("reload", zReload?"S":"N");

    return this.goTo(this.oProcessor.getSession()!=null, oAction.asURL());
  }
  /**
   * Returns an action result to redirect the client to the given URL.<br>
   * 
   * @param zKeepSession whether to send the session id in the redirection
   * @param zURL the url to go to
   */
  private JWebActionResult goToExternal(boolean zKeepSession, String zURL) {
    return new JRedirectResult(zURL, zKeepSession, this , true);
  }

  private JWebActionResult goTo(boolean zKeepSession, String zURL) {
    return new JRedirectResult(zURL, zKeepSession, this );
  }

  public JWebPipelineActionProcessor getProcessor() {
    return this.oProcessor;
  }


  //
  //  trace methods
  //
  public JWebActionResult basicGoShowMessage(int zMessageType, String zTitle, String zMessage) {
	    String sType = JMessageView.getMessageTypeString(zMessageType);
	    JWebAction oAction;
	    try {
	      oAction = JWebActionFactory.createRedirect(getShowMessageEntry(), false, this.oProcessor.getRequest());
	    } catch (Exception e) {
	    	PssLogger.logError(e, "Error going to show message");
	      oAction = JWebActionFactory.create(getShowMessageEntry(), false);
	    }
	    JWebActionData oData = oAction.addData("msg_attrs");
	    if (zTitle != null) {
	      //zTitle = JTools.replaceForeignCharsForWeb(JLanguage.translate(zTitle));
	      oData.add("title", zTitle);
	    }
	    //zMessage = JTools.replaceForeignCharsForWeb(JLanguage.translate(zMessage));
	    oData.add("msg", zMessage);
	    oData.add("type", sType);
	    return this.goTo(this.oProcessor.getSession()!=null, oAction.asURL());
	  }
 
  public JWebActionResult goExternalLink(String url) throws Exception {
    return this.goToExternal(false, url);
  }

  public JWebActionResult goWebFile(String fileName) throws Exception {
  	return this.goTo(false, fileName);
  }
/*
  public JWebActionResult goDoReport(String fileName) throws Exception {
    //JWebAction action = JWebActionFactory.createRedirect("do-WebPrinter", false, this.oProcessor.getRequest());
//  	JWebAction action = JWebActionFactory.create("do-WebPrinter", false);
    return this.goTo(false, "report/"+fileName);
  }
*/
}
