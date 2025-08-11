/*
 * Created on 26-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

import pss.www.platform.actions.JWebRequest;
import pss.www.platform.applications.JApplicationContext;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.platform.applications.JWebApplicationSession;

/**
 * 
 * 
 * Created on 26-jun-2003
 * @author PSS
 */

public interface JWebActionRequestProcessor {


  /**
   * Returns the web application this generator is generating content for, which
   * is resolved from the incoming request.<br>
   * The application should never be <code>null</code>.
   * 
   * @return the web application this generator is generating content for
   */
  public JSessionedApplication getApplication();

  /**
   * Returns the context of the web application this generator is generating
   * content for, which is resolved from the incoming request.<br>
   * The application context should never be <code>null</code>.
   * 
   * @return the context of the web application this generator is generating
   *         content for
   */
  public JApplicationContext getContext();

  /**
   * Returns the web application session this generator is generating content
   * in, which is resolved from the incoming request.<br>
   * The current application session may be <code>null</code> if there user has
   * not logged in yet.
   * 
   * @return the web application session this generator is generating content in
   */
  public JWebApplicationSession getSession();


  /**
   * Returns the request which originated this process object invokation.<br>
   * The request has information like the URI and arguments it came with,
   * useful information for data negotiation between the clients and the web
   * server.
   * 
   * @return the request which originated this process object invokation
   */
  public JWebRequest getRequest();
  
  /**
   * Returns the name of the action this resolver performs.<br>
   * If there is an active session, put its id between brackets. Otherwise, puts
   * a "no session" string between brackets. 
   * 
   * @return the name of the action this resolver performs
   */
  public String getActionName();  

}
