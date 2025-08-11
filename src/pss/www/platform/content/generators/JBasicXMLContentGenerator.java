/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

/**
 * The base class for a Web XML content generator.<br>
 * Generators can build XML contents with the SAX event model, using the intenral utility methods this class provides to do that.<br>
 * Also, component generators may be composed by using the <code>embed()</code> method.<br>
 * A web content generator, therefore, generates only XML content which must be then transformed to HTML or any other browser-known output to be shown to the user. The association between generators and the transformer XSL stylesheets must be done in the webapp's <b>sitemap.xmap</b> file.
 * 
 * <i>Created on 05-jun-2003</i>
 * 
 * @author PSS
 */

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.Cookie;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.generation.AbstractGenerator;
import org.apache.cocoon.xml.EmbeddedXMLPipe;
import org.apache.cocoon.xml.dom.DOMStreamer;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.www.platform.JConstantes;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebPipelineActionProcessor;
import pss.www.platform.actions.JWebRedirector;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.results.JRedirectResult;
import pss.www.platform.applications.JApplicationContext;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebServer;
import pss.www.ui.processing.JXMLContentGenerator;
import pss.www.ui.skins.JPssWebSkinProvider;

@SuppressWarnings("deprecation")
public abstract class JBasicXMLContentGenerator extends AbstractGenerator  implements JXMLContentGenerator, JWebPipelineActionProcessor {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////
	AttributesImpl oAttributes;
	String sStartPendingNode;
	int iOpenNodes;
	Response oResponse;
	JWebApplication oApplication;
	JWebApplicationContext oContext;
//	JWebApplicationSession oSession;

	JWebRedirector oRedirector;
	JXMLContent oXMLContent;
	JWebRequest oRequest;
	String requestId;

  public JWebApplicationSession getSession()  {
    return JWebActionFactory.getCurrentRequest().getSession();

  }

  void setSession(JWebApplicationSession s ) {
  	
  }
	// ////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//
	// ////////////////////////////////////////////////////////////////////////////
	public JBasicXMLContentGenerator() {
		// this.logDebug("New content generator instance: '" +
		// this.getClass().getName() + "'");
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC API METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	public boolean isPageGenerator() {
		return false;
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
//	public JWebApplicationSession getSession() {
//		return this.oSession;
//	}

	/**
	 * Returns the name of the content this generator generates.<br>
	 * If there is an active session, put its id between brackets. Otherwise, puts a "no session" string between brackets.
	 * 
	 * @return the name of the content this generator generates
	 */
	public final String getContentName() {
		if (this.getSession()==null) {
			return this.getBaseContentName()+"["+JConstantes.Pss_SESSION_ID_UNDEFINED+"]";
		} else {
			return this.getBaseContentName()+"["+this.getSession().getId()+"]";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		try {
			
			Thread.currentThread().setName(this.getClass().getSimpleName());
//			requestId = this.parameters.getParameter("requestid","0");
	    this.initializeGeneration();
		} catch (Throwable e) {
			this.logError(e, "Error on setup of ["+this.getContentName()+"]");
			if (this.oRedirector!=null) {
				((JRedirectResult) this.oRedirector.goShowMessage(JWebActionFactory.getErrorMessageTitle(this.oRequest), e)).apply();
				this.cleanUp();
			} else {
				String sContName=this.getContentName();
				this.cleanUp();
				throw new ProcessingException("Error on setup of ["+sContName+"]", e);
			}
		}
	}

	/**
	 * Builds the XML content for the web page or component this generator generates.<br>
	 * The XML content is generated using the SAX event model using this class' internal utility methods to do that.
	 */
	public void generate() throws IOException, SAXException, ProcessingException {
    	Throwable oOccurredError=null;
			long startTime = System.currentTimeMillis();
			try {
				Request oCocoonRequest=this.getServletRequest();
				if (oCocoonRequest==null) {
					Request oServletReq=ObjectModelHelper.getRequest(this.objectModel);
					if (oServletReq.getMethod().equalsIgnoreCase("OPTIONS"))
//						this.oResponse=ObjectModelHelper.getResponse(this.objectModel);
//						oResponse.addHeader("Access-Control-Allow-Origin", "*");
//						oResponse.addHeader("Access-Control-Allow-Headers", "*");
//						oResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");

						return;
				}
//				this.attachToRunningThread();
				// start the document
				this.contentHandler.startDocument();
				// perform the generation and consistency checks
				this.doGenerateWithChecks();
				
				  
				// end the document
				long endTime = System.currentTimeMillis();
				this.contentHandler.endDocument();
				this.postEndDocument();
				// log the elapsed time
				if ( log())
					this.logDebug("Generation of '" + this.getContentName() + "' took " + (System.currentTimeMillis() - startTime) + " ms (" + (endTime - startTime) + ")");
			} catch (Throwable e) {
				if (getSession()!=null) this.getSession().restoreErrorSituation();
		//		if (JTools.isExceptionLog(e))
					PssLogger.logError(e);
//				if (getSession() != null)
//					this.getSession().resetRegisteredObjects();
				//				if (getSession() != null)
//					this.getSession().restoreRegisteredObjects();
				oOccurredError = e;
			} finally {
					if (oOccurredError != null) {
						this.logError(oOccurredError, "Error while generating [" + this.getContentName() + "]");
					if (this.oRedirector != null) {
						((JRedirectResult) this.oRedirector.goShowMessage(JWebActionFactory.getErrorMessageTitle(this.oRequest), oOccurredError)).apply();
					}
					this.cleanUp();
				} else {
					this.cleanUp();
				}
			}

	}
	private boolean log() throws Exception {
		return  !this.getContentName().startsWith("upload")&&!this.getContentName().startsWith("waiting");
	}
	protected void postEndDocument() throws Exception {
//		if (getSession()!=null) getSession().convertOutRegisteredObjectsToIn();
	}


	protected void attachToRunningThread() throws Exception {
//		this.oRequest.attachToRunningThread(this.getSession());
	}

	public JWebRequest getRequest() {
		return this.oRequest;
	}

	public JPssWebSkinProvider getSkinProvider() {
		return this.oContext.getSkinProvider();
	}

	protected Request getServletRequest() {
		if (this.oRequest==null) {
			return null;
		} else {
			return this.oRequest.getServletRequest();
		}
	}

	protected JWebRedirector getRedirector() {
		return this.oRedirector;
	}

	//
	// response building methods
	//
	protected JXMLContent asXMLContent() {
		return this.oXMLContent;
	}

	protected void addTextNode(String zNodeName, String zValue) throws SAXException {
		this.startNode(zNodeName);
		this.setNodeText(zValue);
		this.endNode(zNodeName);
	}

	protected void addTextNodeNLS(String zNode, String zValue) throws Exception {
		this.addTextNode(zNode, JLanguage.translate(zValue));
	}

	protected void startNode(String zNodeName) throws SAXException {
		this.checkStartPending();
		this.sStartPendingNode=zNodeName;
		this.oAttributes.clear();
		this.iOpenNodes++;
	}

	protected void endNode(String zNodeName) throws SAXException {
		if (this.iOpenNodes<1) {
			throw new RuntimeException("No node was started with name: "+zNodeName);
		}
		this.checkStartPending();
		this.contentHandler.endElement("", zNodeName, zNodeName);
		this.iOpenNodes--;
	}

	protected void setAttribute(String zAttributeName, String zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", zValue);
	}

	protected void setAttributeEscape(String zAttributeName, String zValue) throws Exception {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", JTools.encodeString( JLanguage.translate(zValue)));//StringEscapeUtils.escapeJava(JLanguage.translate(zValue)));
	}

	protected void setAttributeNLS(String zAttributeName, String zValue) throws Exception {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", JLanguage.translate(zValue));//StringEscapeUtils.escapeJava(JLanguage.translate(zValue)));
	}

	protected void setAttribute(String zAttributeName, int zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", String.valueOf(zValue));
	}

	protected void setAttribute(String zAttributeName, long zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", String.valueOf(zValue));
	}

	protected void setAttribute(String zAttributeName, float zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", String.valueOf(zValue));
	}

	protected void setAttribute(String zAttributeName, double zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", String.valueOf(zValue));
	}

	protected void setAttribute(String zAttributeName, boolean zValue) {
		this.oAttributes.addAttribute("", zAttributeName, zAttributeName, "CDATA", String.valueOf(zValue));
	}

	protected void setNodeText(String zValue) throws SAXException {
		this.checkStartPending();
		if (zValue!=null&&zValue.length()>0) {
				this.contentHandler.characters(zValue.toCharArray(), 0, zValue.length());
		}
	}

	protected void addCookie(Cookie zCookie) {
		this.oResponse.addCookie(zCookie);
	}

	protected void embed(Node zDOMNode) throws SAXException {
		this.checkStartPending();
		EmbeddedXMLPipe pipe=new EmbeddedXMLPipe(this.contentHandler);
		DOMStreamer streamer=new DOMStreamer(pipe, this.lexicalHandler);
		streamer.stream(zDOMNode);
	}

	protected void embed(JXMLComponentGenerator zGenerator) throws Exception {
		// pass cocoon variables
		// zGenerator.manager = this.manager;
		zGenerator.contentHandler=this.contentHandler;
		zGenerator.lexicalHandler=this.lexicalHandler;
		zGenerator.parameters=this.parameters;
		zGenerator.resolver=this.resolver;
		zGenerator.source=this.source;
		zGenerator.xmlConsumer=this.xmlConsumer;
		// initialize control variables
		zGenerator.iOpenNodes=0;
		zGenerator.sStartPendingNode=null;
		// pass Pss generation variables
		zGenerator.oAttributes=this.oAttributes;
		zGenerator.oResponse=this.oResponse;
		zGenerator.oApplication=this.oApplication;
		zGenerator.oContext=this.oContext;
		zGenerator.setSession(this.getSession());
		// generate !!!!
		this.checkStartPending();
		zGenerator.doGenerateWithChecks();
	}

	//
	// trace methods
	//

	protected void logError(Throwable zEx, String zHeadingMessage) {
//		if (this.oApplication==null) {
//			JDebugPrint.logError(JWebServer.getInstance().getName(), zEx, zHeadingMessage);
//		} else {
//			JDebugPrint.logError(this.oApplication.getName(), zEx, zHeadingMessage);
//		}
		PssLogger.logError(zEx, zHeadingMessage);
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

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INTERNAL METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	void doGenerateWithChecks() throws Exception {
		// perform the generation (subclass responsibility)
		this.doGenerate();
	

  
		// check there are no end-pending nodes
		if (this.sStartPendingNode!=null) {
			throw new RuntimeException("Node pending of end: "+this.sStartPendingNode);
		} else if (this.iOpenNodes>0) {
			throw new RuntimeException(String.valueOf(this.iOpenNodes)+" nodes pending of end");
		}
	}

	@SuppressWarnings("unchecked")
	public void initializeGeneration() throws Throwable {
		this.oXMLContent=new JXMLContent(this);

		// initialize control variables

		this.sStartPendingNode=null;
		this.iOpenNodes=0;

		// initialize Pss generation variables

		this.oAttributes=new AttributesImpl();

		// get the request
		Request oServletReq=ObjectModelHelper.getRequest(this.objectModel);
		this.oRequest=new JWebRequest(oServletReq, this);
		this.oRequest.setObjectMap(this.objectModel);
		// take the redirector
		JWebRedirector oPipeRed=(JWebRedirector) this.getRequest().getModelObject("redirector");
		if (oPipeRed!=null) {
			this.oRedirector=oPipeRed.copyFor(this);
		}

		// take application data
		this.takeApplicationData();

		// will start using Pss
		// this.oRequest.attachoRunningThread(this.oSession);

		// create the response to be sent
		this.oResponse=ObjectModelHelper.getResponse(this.objectModel);
		this.setResponseHeaders(oResponse);
	}

	protected void takeApplicationData() throws Throwable {
		// resolve the application, context and session for the request
		this.oApplication=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(this.objectModel));
		this.oContext=(JWebApplicationContext) oApplication.getApplicationContext();
		
//		this.setSession(oApplication.getWebApplicationSession(ObjectModelHelper.getRequest(objectModel)));
//		if (this.isSessionDependent()&&this.getSession()==null) {
//			JExcepcion.SendError("La página no se puede mostrar porque la sesión es inválida");
//		}
	}

//	protected void setSessionToNull() throws Exception {
//		this.setSession(null);
//		if (this.oRequest!=null) {
//			this.oRequest.setSession(null);
//		}
//	}

	protected void cleanUp() {
		long startClean = System.currentTimeMillis();
		try {
			if (this.oRequest!=null) {
				this.oRequest.detachFromRunningThread();
			}
		} catch (Exception ignored) {
			this.logError(ignored, "Error while finishing a content generation");
		}
		this.oAttributes=null;
		this.sStartPendingNode=null;
		this.iOpenNodes=0;
		this.oResponse=null;
		if (this.oRequest!=null) {
			this.oRequest.setObjectMap(null);
			this.oRequest=null;
		}
		this.setSession(null);
		this.oContext=null;
		this.oApplication=null;
		this.oRedirector=null; // the redirector is de-referenced but not
		// discarded, because it may be being used
		// for a redirection
		if (this.oXMLContent!=null) {
			this.oXMLContent.discard();
			this.oXMLContent=null;
		}
	}

	private void checkStartPending() throws SAXException {
		if (this.sStartPendingNode!=null) {
			this.contentHandler.startElement("", sStartPendingNode, sStartPendingNode, this.oAttributes);
			this.sStartPendingNode=null;
		}
	}

	public void clearResultsMap() {
		// do nothing here
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	// ////////////////////////////////////////////////////////////////////////////

	protected  void setResponseHeaders(Response zResponse) throws Exception{
		zResponse.addHeader("Access-Control-Allow-Origin", getRequest().getHeader("Origin")==null?"*":getRequest().getHeader("Origin"));
		zResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Credentials,Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		zResponse.addHeader("Access-Control-Expose-Headers", "*");
		zResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
  	zResponse.addHeader("Access-Control-Allow-Credentials", "true");
		
		}

	protected abstract void doGenerate() throws Exception;

	protected abstract String getBaseContentName();

	protected boolean isServerSideCacheable() {
		return false;
	}

	protected boolean isSessionDependent() {
		return false;
	}



	public String getActionName() {
		return "";
	}

}
