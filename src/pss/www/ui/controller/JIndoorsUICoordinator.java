/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import java.lang.ref.WeakReference;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplicationSession;


public class JIndoorsUICoordinator extends JBasicWebUICoordinator {

	  
	  public JWebApplicationSession getSession()  {
	  	return JWebActionFactory.getCurrentRequest().getSession();
	  }

	  void setSession(JWebApplicationSession s ) {
	  }
	  
  public JIndoorsUICoordinator(JWebApplicationSession zOwnerSession) {
//    this.setSession(zOwnerSession);
  }

  public boolean hasSession() {
    return true;
  }

 

}
