/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.avalon.framework.thread.SingleThreaded;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.acting.AbstractAction;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;

import pss.core.data.BizPssConfig;
import pss.core.services.fields.IContext;
import pss.core.services.records.JRecord;
import pss.core.tools.JExceptionAndRefresh;
import pss.core.tools.JExceptionRunAction;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.www.platform.JConstantes;
import pss.www.platform.actions.IControlToBD;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebPipelineActionProcessor;
import pss.www.platform.actions.JWebRedirector;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebWinFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.results.JEndRequestResult;
import pss.www.platform.actions.results.JGoOnWithRequestResult;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JApplicationContext;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.users.history.BizUserHistory;
import pss.www.platform.users.stadistics.GuiStadistics;

/**
 * The base class for action resolvers.<br>
 * Action resolvers are components which are linked to URL patterns in the <b>sitemap.xmap</b> file.
 * 
 * Created on 06-jun-2003
 * 
 * @author PSS
 */

public abstract class JBasicWebActionResolver extends AbstractAction implements SingleThreaded, JWebPipelineActionProcessor,IControlToBD {

	// made SingleThreaded to be stateful, so that it can hold objects during
	// the
	// reqeust processing to make easier the API of this class to process
	// actions

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////
	private JWebRedirector oRedirector;
	// private Parameters oInvokationParameters;
	private Map<String, Object> oResultMap;

	private JWebApplication oApplication;
	private JWebApplicationContext oContext;
	private JWebApplicationSession oSession;

	private JWebRequest oRequest;
	
	transient protected JWebWinFactory winFactory = new JWebWinFactory(this);

	public JWebWinFactory getWinFactory() {
		return winFactory;
	}
	Map oObjectModel;
//	public static long requestGenerate=0;
//	public long request=0;
  


	// ////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//
	// ////////////////////////////////////////////////////////////////////////////
	public JBasicWebActionResolver() {
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC API METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	private void sendContextToForaignFrameworks( Map objectModel ) throws Exception {
		try {
			setObjectModel(objectModel);
			Class cl = Class.forName("pss.rot.JCompanyBusinessRot");
			if (cl!=null) {
				((IContext)cl.newInstance()).setContext(  ObjectModelHelper.getContext(objectModel).getContext("/"+BizPssConfig.getPssConfig().getAppURLPrefix()) );
				
			}
		} catch (Exception e) {

		}
	}
	/**
	 * Cocoon Action interface implementation.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> act(Redirector redirector, SourceResolver resolver, Map objectModel, String source, Parameters par) throws Exception {
		try {
			Thread.currentThread().setName(this.getClass().getSimpleName());
			Request oServletReq=ObjectModelHelper.getRequest(objectModel);
			// get the request

			this.oRedirector=new JWebRedirector(redirector, this);
			this.oRequest=new JWebRequest(oServletReq, this);
			// this.oInvokationParameters = par;
			BizPssConfig.getPssConfig().setAppURL(this.oRequest.getURI());
			BizPssConfig.getPssConfig().setAppURLTotal(this.oRequest.getArgument("dg_url"));
			objectModel.put("redirector", this.oRedirector);
			sendContextToForaignFrameworks(objectModel);
			//ObjectModelHelper.getContext(objectModel).getContext("/"+BizPssConfig.getPssConfig().getAppURLPrefix()).getRealPath("/WEB-INF");
			// encoding
			// get the request
			this.oRequest.setEncoding(this.getCharacterEncoding());
			this.oRequest.setObjectMap(objectModel);
			this.oRequest.setIdRequestJS(JTools.getLongNumberEmbedded(getRequest().getArgument("vrs")));
	


			// take application data from the object model
			this.takeApplicationData(objectModel);
			//Stadistics
			this.startStadistics();
			// will start using Pss
			this.attachToRunningThread();
			this.endStart();

			// procesa la accion y generar el result
			this.processAction();

			Map<String, Object> oFinalResultMap=this.getFinalResultMap();
			return oFinalResultMap;

		} catch (Throwable e) {
			PssLogger.logError(e);
			throw new ProcessingException("Uncaught error while processing action '"+this.getActionName()+"'", e);
		} finally {
			this.cleanUp();
		}
	}

	protected boolean isStadistic(){  return true;};
	protected void attachToRunningThread() throws Exception {
//		this.oRequest.attachToRunningThread(this.getSession());
	}



	private void startStadistics() throws Exception {
		if (!isStadistic()) return;
		GuiStadistics.getStat().GetcDato().addTotSol();
		if (this.getSession()==null) return;
		BizUserHistory last = this.getSession().getStadistic(getRequest().getStadistics());
		if (last!=null) {
			last.assignStadistics(getRequest().getStadistics());
			this.getSession().killUnfinish(last.getIdReq());
		}
		//uh.setStadistics(getRequest().getStadistics());
		BizUserHistory uh = BizUserHistory.createStad(getRequest().getIdRequestJS());
		uh.setReferenceThread(getSession().getDatabase());
		uh.setUser(getSession().getUser().GetUsuario());
		this.getSession().addStadistics(uh.getIdReq(),uh);
		
		
		
	}
	
	private void endStart() throws Exception {
		if (this.getSession()==null) return;
		BizUserHistory last = this.getSession().getStadistic(getRequest().getIdRequestJS());
		if (last==null) return;
		last.setTimeStart(last.getDelta());
	}

	private void processAction() throws Exception {
		long startTime=System.currentTimeMillis();
		if (  log())
			this.logWait("Processing of action '"+this.getActionName());
	
		JWebActionResult oResult=this.generateResult();
		if (oResult==null) return;
		oResult.applyResultTo(this);
		if (  log())
			this.logWait("**Took ms = "+(System.currentTimeMillis()-startTime));
	}
	private boolean log() throws Exception {
		return  !this.getActionName().startsWith("upload")&&!this.getActionName().startsWith("waiting");
	}
	private Map<String, Object> getFinalResultMap() throws Exception {
		if (this.oResultMap==null) return null;
		Map<String, Object> finalResultMap=new HashMap<String, Object>();
		finalResultMap.putAll(this.oResultMap);
		return finalResultMap;
	}

	public JWebRequest getRequest() {
		return this.oRequest;
	}

	protected boolean isAjax() {
		return false;
	}

	private JWebActionResult generateResult() throws Exception {
		try {
			oRequest.setAjax(isAjax());
			if (oRequest.getServletRequest()==null) {
				this.logError(this.getClass().getName()+": there is no request, pipeline will be finished");
				return this.endRequest();
			} else if ((this.isSessionDependent()&&(this.oSession==null||this.oSession.isLogout()))) {
				return this.getRedirector().goSessionTimeOut(this.oSession!=null&&this.oSession.isLogoutPerMultiUser());
			} else {
				this.onPerform();
				return this.perform();
			}

		} catch (Throwable e) {
			this.restoreErrorSituation(e);
			if (JTools.isExceptionLog(e)) 	// ya fue logueado
					this.logError(e, "Error processing action '"+this.getActionName()+"'");
	  	if (e instanceof JExceptionRunAction) {
	  		JExceptionRunAction ex = (JExceptionRunAction)e;
	  		return this.getRedirector().goTo( JWebActionFactory.createRedirectExceptionAction( oRequest, ex));
	  	}
	  	if (e instanceof JExceptionAndRefresh)
				return this.getRedirector().goShowMessage(JWebActionFactory.getErrorMessageTitle(this.oRequest),((JExceptionAndRefresh)e).getError(),true,((JExceptionAndRefresh)e).isInfo());
			return this.getRedirector().goShowMessage(JWebActionFactory.getErrorMessageTitle(this.oRequest), e,false,false);
		}
	}

	protected void restoreErrorSituation(Throwable e) {
	}
	
	protected Request getServletRequest() {
		if (this.oRequest==null) {
			return null;
		} else {
			return this.oRequest.getServletRequest();
		}
	}

	/**
	 * Returns the web application this generator is generating content for, which is resolved from the incoming request.<br>
	 * The application should never be <code>null</code>.
	 * 
	 * @return the web application this generator is generating content for
	 */
	public JSessionedApplication getApplication() {
		return this.oApplication;
	}

	/**
	 * Returns the context of the web application this generator is generating content for, which is resolved from the incoming request.<br>
	 * The application context should never be <code>null</code>.
	 * 
	 * @return the context of the web application this generator is generating content for
	 */
	public JApplicationContext getContext() {
		return this.oContext;
	}

	/**
	 * Returns the web application session this generator is generating content in, which is resolved from the incoming request.<br>
	 * The current application session may be <code>null</code> if there user has not logged in yet.
	 * 
	 * @return the web application session this generator is generating content in
	 */
	public JWebApplicationSession getSession() {
		JWebApplicationSession session= JWebActionFactory.getCurrentRequest().getSession();
		if (session!=null) session.retrieveSession();
		return session;
	}

	/**
	 * Returns the name of the action this resolver performs.<br>
	 * If there is an active session, put its id between brackets. Otherwise, puts a "no session" string between brackets.
	 * 
	 * @return the name of the action this resolver performs
	 */
	public final String getActionName() {
		if (this.oSession==null) {
			return this.getBaseActionName()+"["+JConstantes.Pss_SESSION_ID_UNDEFINED+"]";
		} else {
			return this.getBaseActionName()+"["+this.oSession.getId()+"]";
		}
	}

	public JWebRedirector getRedirector() {
		return this.oRedirector;
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INTERNAL UTILITY METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	//
	// possible action results
	//

	protected JWebActionResult goOn() {
		return new JGoOnWithRequestResult();
	}

	protected JWebActionResult endRequest() {
		return new JEndRequestResult();
	}

	//
	// action processing utility methods
	//

	protected void addObjectToResult(String zKey, Object zResultObject) {
		if (this.oResultMap==null) this.oResultMap=new HashMap<String, Object>();
		this.oResultMap.put(zKey, zResultObject);
	}

	protected boolean hasDirectory() {
		if (this.oResultMap==null) this.oResultMap=new HashMap<String, Object>();
		return this.oResultMap.get("__dictionary")!=null;
	}

	//
	// trace methods
	//

	public void logError(Throwable zEx, String zHeadingMessage) {
//		if (this.oApplication==null) {
//			JDebugPrint.logError(JWebServer.getInstance().getName(), zEx, zHeadingMessage);
//		} else {
//			JDebugPrint.logError(this.oApplication.getName(), zEx, zHeadingMessage);
//		}
		PssLogger.logError(zEx, zHeadingMessage);
	}

	public void logError(String zMessage) {
//		if (this.oApplication==null) {
//			JDebugPrint.logError(JWebServer.getInstance().getName(), zMessage);
//		} else {
//			JDebugPrint.logError(this.oApplication.getName(), zMessage);
//		}
		PssLogger.logError(zMessage);
	}

	protected void logInfo(String zMessage) {
//		if (this.oApplication==null) {
//			JDebugPrint.logInfo(JWebServer.getInstance().getName(), zMessage);
//		} else {
//			JDebugPrint.logInfo(this.oApplication.getName(), zMessage);
//		}
		PssLogger.logInfo(zMessage);
	}

	protected void logDebug(String zMessage) {
//		if (this.oApplication==null) {
//			JDebugPrint.logDebug(JWebServer.getInstance().getName(), zMessage);
//		} else {
//			JDebugPrint.logDebug(this.oApplication.getName(), zMessage);
//		}
		PssLogger.logDebug(zMessage);
	}

	protected void logWait(String zMessage) {
//	if (this.oApplication==null) {
//		JDebugPrint.logDebug(JWebServer.getInstance().getName(), zMessage);
//	} else {
//		JDebugPrint.logDebug(this.oApplication.getName(), zMessage);
//	}
	PssLogger.logWait(zMessage);
}
	// ////////////////////////////////////////////////////////////////////////////
	//
	// INTERNAL METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	public void clearResultsMap() {
		this.oResultMap=null;
	}

//	protected void setSession(JWebApplicationSession zSession) throws Exception {
//		this.oSession=zSession;
//		this.oRequest.setSession(zSession);
//	}
//	protected void setOnlySession(JWebApplicationSession zSession) throws Exception {
//		this.oSession=zSession;
//	}
//
//	protected void setSessionToNull() throws Exception {
//		this.oSession=null;
//		this.oRequest.setSession(null);
//	}

	protected void takeApplicationData(Map<String, Object> zObjectModel) throws Throwable {
		// resolve the application, context and session for the request
		this.oApplication=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(zObjectModel));
		this.oContext=(JWebApplicationContext) oApplication.getApplicationContext();
		this.oSession=JWebActionFactory.getCurrentRequest().getSession();
	}

	// protected String getInvokationParameter(String zParam) throws Exception {
	// if (this.oInvokationParameters==null) {
	// return null;
	// }
	// if (this.oInvokationParameters.isParameter(zParam)) {
	// return this.oInvokationParameters.getParameter(zParam);
	// } else {
	// return null;
	// }
	// }
	// protected void setInvokationParameter(String zParam, String zValue)
	// throws Exception {
	// if (this.oInvokationParameters==null) {
	// return;
	// }
	// this.oInvokationParameters.setParameter(zParam, zValue);
	// }

	protected void cleanUp() {
		try {
			if (this.oRequest!=null) {
				this.oRequest.detachFromRunningThread();
			}
		} catch (Exception ignored) {
			this.logError(ignored, "Error while finishing an action processing");
		}
		this.oRedirector=null; // the redirector is de-referenced but not
		// discarded, because it may be being used
		// for a redirection or may be needed by a
		// subsequent generator
		// this.oInvokationParameters = null;
		this.oResultMap=null; // the map must stay with its objets to be used
		// in the pipeline
		this.oApplication=null;
		this.oContext=null;
		this.oSession=null;
		if (this.oRequest!=null) {
			this.oRequest.setObjectMap(null);
			this.oRequest=null;
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	// ////////////////////////////////////////////////////////////////////////////
	protected void onPerform() throws Exception {
		// do nothing by default
	}
	
	protected boolean addDictionaryInfo( ) {
		return true;
	}
	
	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("__requestid", ""+oRequest.getIdRequestJS());
		if (this.getSession()!=null && addDictionaryInfo( )) {
			JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(), false,true);
//			this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
	}
		return this.goOn();
	}

	protected abstract String getBaseActionName();

	protected String getCharacterEncoding() {
		return "ISO-8859-1";
	}

	protected boolean isSessionDependent() {
		return false;
	}

	@Override
	public void controlsToBD(JWebActionData data, JRecord record) throws Exception {
		
	}
	public static String getSessionID(Request cocoonRequest,HttpSession oServletSession) throws Exception {
		if (cocoonRequest==null) return null;
		if (cocoonRequest.getCookies()==null) 
			return oServletSession!=null?oServletSession.getId():null;
		for(Cookie c:cocoonRequest.getCookies()) {
			if (c.getName().equals("TABID"))
				return c.getValue();
		}
		return cocoonRequest.getRequestedSessionId();
	}
	public JWebHistoryManager getHistoryManager() {
		return this.getSession().getHistoryManager();
	}
	
	public void storeMeta(BizAction action) throws Exception {
		String provider = getRequest().getTableProvider();
		if (provider==null) return;
		if (provider.startsWith("form_"))
			provider = provider.substring(5);
		JHistoryProvider hp = this.getSession().getHistoryManager().getLastHistory().findProvider(provider);
		if (hp == null)
			hp = this.getSession().getHistoryManager().getLastHistory().findProviderWithinLevel(provider);
		storeMeta(action,hp);

	}
	public void storeMeta(BizAction action,JHistoryProvider hp) throws Exception {
//		BizAction act = action;
		if (hp==null) return;
//		JAct lastAction = (JAct) hp.getActionSubmit();
//		BizAction actionProvider = lastAction.getActionSource();
//		if (actionProvider==null) return;
    if (action==null) action=hp.getAction();
    JIterator<String> it = getRequest().getAllArgumentNames();
		while (it.hasMoreElements()) {
			String arg = it.nextElement();
	    if (!arg.startsWith("meta_")) continue;
	    hp.getExtraData().addElement(arg.substring(arg.indexOf("-")+1), getRequest().getArgument(arg));//backup, para el preview
	    action.getExtraData().addElement(arg.substring(arg.indexOf("-")+1), getRequest().getArgument(arg));
		}

	}
	

	public void restoreMeta(BizAction action) throws Exception { //restore, para el preview
		BizAction act=action;
		String provider = getRequest().getTableProvider();
		if (provider.startsWith("form_"))
			provider = provider.substring(5);
		JHistoryProvider hp = this.getSession().getHistoryManager().getLastHistory().findProvider(provider);
		if (hp == null)
			hp = this.getSession().getHistoryManager().getLastHistory().findProviderWithinLevel(provider);
		if (hp==null)
			return;
		BizAction actionProvider=getHistoryManager().getLastHistoryAction();
		if (action==null)
			act=actionProvider;
//		JBaseWin bwin = act.getResult();
		act.getExtraData().addElements(hp.getExtraData());
	}
	public Map getObjectModel() {
		return oObjectModel;
	}

	public void setObjectModel(Map oObjectModel) {
		this.oObjectModel = oObjectModel;
	}

}
