/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.providers;

import java.io.IOException;

import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.reading.AbstractReader;
import org.xml.sax.SAXException;

import pss.core.tools.PssLogger;
import pss.www.platform.JConstantes;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebWinFactory;
import pss.www.platform.applications.JApplicationContext;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebServer;
import pss.www.ui.processing.JWebActionRequestProcessor;
import pss.www.ui.skins.JPssWebSkinProvider;
/**
 * The base class for a web resource generator, like *.jpg or *.pdf files.<br>
 * A web resource generator generates directly MIME content, and not XML to be
 * transformed. Then, it and must specify its MIME type through the
 * #getMimeType() method.<br>
 * Resource generators must be referenced as readers in the webapp's
 * <b>sitemap.xmap</b> file.
 * 
 * <i>Created on 09-jun-2003</i>
 * @author PSS
 */

public abstract class JBasicWebResourceProvider extends AbstractReader implements JWebActionRequestProcessor {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  Response oResponse;
  JWebApplication oApplication;
  JWebApplicationContext oContext;
  JWebApplicationSession oSession;

  JWebRequest oRequest;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JBasicWebResourceProvider() {
    //this.logDebug("New content generator instance: '" + this.getClass().getName() + "'");
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   PUBLIC API METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Returns the web application this generator is generating content for, which
   * is resolved from the incoming request.<br>
   * The application should never be <code>null</code>.
   * 
   * @return the web application this generator is generating content for
   */
  public JSessionedApplication getApplication() {
    return this.oApplication;
  }

  /**
   * Returns the context of the web application this generator is generating
   * content for, which is resolved from the incoming request.<br>
   * The application context should never be <code>null</code>.
   * 
   * @return the context of the web application this generator is generating
   *         content for
   */
  public JApplicationContext getContext() {
    return this.oContext;
  }

  /**
   * Returns the web application session this generator is generating content
   * in, which is resolved from the incoming request.<br>
   * The current application session may be <code>null</code> if there user has
   * not logged in yet.
   * 
   * @return the web application session this generator is generating content in
   */
  public JWebApplicationSession getSession() {
    return JWebActionFactory.getCurrentRequest().getSession();
  }


  /**
   * Returns the name of the content this generator generates.<br>
   * If there is an active session, put its id between brackets. Otherwise, puts
   * a "no session" string between brackets. 
   * 
   * @return the name of the content this generator generates
   */
  public final String getResourceName() {
    if (this.oSession==null) {
      return this.getBaseResourceName() + "[" + JConstantes.Pss_SESSION_ID_UNDEFINED + "]";
    } else {
      return this.getBaseResourceName() + "[" + this.oSession.getId() + "]";
    }
  }


  /* (non-Javadoc)
   * @see org.apache.cocoon.reading.Reader#generate()
   */
  public void generate() throws IOException, SAXException, ProcessingException {
    Throwable oOccurredError = null;
    long startTime = System.currentTimeMillis();
    try {
      this.initializeGeneration();
      // perform the generation and consistency checks
      this.doGenerate();
      // log the elapsed time
      this.logInfo("Generation of [" + this.getResourceName() + "] took " + (System.currentTimeMillis() - startTime) + " ms");
    } catch (IOException e) {
      oOccurredError = e;
      throw e;
    } catch (SAXException e) {
      oOccurredError = e;
      throw e;
    } catch (ProcessingException e) {
      oOccurredError = e;
      throw e;
    } catch (Throwable e) {
      oOccurredError = e;
      throw new ProcessingException("Error generating resource", e);
    } finally {
      if (oOccurredError != null) {
        this.logError(oOccurredError, "Error while generating [" + this.getResourceName() + "]");
      }
      this.cleanUp();
    }
  }

  public JWebRequest getRequest() {
    return this.oRequest; 
  }

  protected Request getServletRequest() {
    if (this.oRequest==null) {
      return null;
    } else {
      return this.oRequest.getServletRequest();
    }
  }

  public JPssWebSkinProvider getSkinProvider() {
    return this.oContext.getSkinProvider();
  }


  //////////////////////////////////////////////////////////////////////////////  
  //
  //  INTERNAL UTILITY METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  //
  //  trace methods
  //

  protected void logError(Throwable zEx, String zHeadingMessage) {
//    if (this.oApplication==null) {
//      JDebugPrint.logError(JWebServer.getInstance().getName(), zEx, zHeadingMessage);
//    } else {
//      JDebugPrint.logError(this.oApplication.getName(), zEx, zHeadingMessage);
//    }
  	PssLogger.logError(zEx, zHeadingMessage);
  }
  protected void logError(String zMessage) {
//    if (this.oApplication==null) {
//      JDebugPrint.logError(JWebServer.getInstance().getName(), zMessage);
//    } else {
//      JDebugPrint.logError(this.oApplication.getName(), zMessage);
//    }
  	PssLogger.logError(zMessage);
  }
  protected void logInfo(String zMessage) {
//    if (this.oApplication==null) {
//      JDebugPrint.logInfo(JWebServer.getInstance().getName(), zMessage);
//    } else {
//      JDebugPrint.logInfo(this.oApplication.getName(), zMessage);
//    }
  	PssLogger.logInfo(zMessage);
  }
  protected void logDebug(String zMessage) {
//    if (this.oApplication==null) {
//      JDebugPrint.logDebug(JWebServer.getInstance().getName(), zMessage);
//    } else {
//      JDebugPrint.logDebug(this.oApplication.getName(), zMessage);
//    }
  	PssLogger.logDebug(zMessage);
  }




  //////////////////////////////////////////////////////////////////////////////  
  //
  //  INTERNAL METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  private void initializeGeneration() throws Throwable {
    // initialize control variables

    // create the response to be sent
    this.oResponse = ObjectModelHelper.getResponse(this.objectModel);
    setResponseHeaders(this.oResponse);
  
    // get the request
    Request oServletReq = ObjectModelHelper.getRequest(this.objectModel);
    if (oServletReq != null) {
      this.oRequest = new JWebRequest(oServletReq, this);
    }

    this.takeApplicationData();

    // will start using Pss
//    this.oRequest.attachToRunningThread(this.oSession);
  }
  
  private void takeApplicationData() throws Throwable {
    // resolve the application, context and session for the request
    this.oApplication = JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(this.objectModel));
    this.oContext = (JWebApplicationContext) oApplication.getApplicationContext();
//    this.oSession = oApplication.getWebApplicationSession(ObjectModelHelper.getRequest(objectModel));
  }

  private void cleanUp() {
    try {
      if (this.oRequest != null) {
        this.oRequest.detachFromRunningThread();
      }
    } catch( Exception ignored ) {
      this.logError( ignored, "Error while finishing a Pss request" );
    }
    this.oResponse = null;
    this.oSession = null;
    this.oContext = null;
  }

  protected void setResponseHeaders(Response zResponse) throws Exception {
			zResponse.addHeader("Access-Control-Allow-Origin", "*");
			zResponse.addHeader("Access-Control-Allow-Headers", "*");
			zResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
	}

  //////////////////////////////////////////////////////////////////////////////  
  //
  //  METHODS TO OVERRIDE IN SUBCLASSES
  //
  //////////////////////////////////////////////////////////////////////////////

  protected abstract void doGenerate() throws Throwable;
  protected abstract String getBaseResourceName();


}
