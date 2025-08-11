/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import pss.www.platform.applications.JWebApplicationSession;


/**
 * 
 * 
 * Created on 25-jun-2003
 * @author PSS
 */

public interface JWebSessionOwnedObject {

  /**
   * Returns the session which owns this object.<br>
   * It should never be <code>null</code>.
   * 
   * @return the session which owns this object
   */
  public JWebApplicationSession getSession();

}
