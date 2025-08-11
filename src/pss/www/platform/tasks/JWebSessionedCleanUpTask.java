/*
 * Created on 22-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.tasks;

import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.controller.JWebSessionOwnedObject;

public abstract class JWebSessionedCleanUpTask extends JWebCleanUpTask implements JWebSessionOwnedObject {

  private JWebApplicationSession oSession;


  public JWebSessionedCleanUpTask(long zDelay, JWebApplicationSession zSession) {
    super(zDelay);
    this.oSession = zSession;
  }


  @Override
	protected boolean shouldContinue() {
    return this.getSession()!=null && this.getSession().isOpen();
  }
  @Override
	protected void taskFinished() {
    this.oSession=null;
  }

  public JWebApplicationSession getSession() {
    return this.oSession;
  }

}
