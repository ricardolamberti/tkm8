/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import pss.www.platform.applications.JWebApplicationSession;


public class JFrontDoorUICoordinator extends JBasicWebUICoordinator {

  public JWebApplicationSession getSession() {
    throw new RuntimeException("Front Door UI Coordinators are not owned by any session");
  }

  public boolean hasSession() {
    return false;
  }

/*  public void initialize(JWebActionRequest zRequest) throws Exception {
    throw new RuntimeException("Front Door UI Coordinators cannot be initialized because they are unsessioned, so they are shared");
  }
  public void refresh(JWebActionRequest zRequest) throws Exception {
    throw new RuntimeException("Front Door UI Coordinators cannot be refreshed because they are unsessioned, so they are shared");
  }
*/
}
