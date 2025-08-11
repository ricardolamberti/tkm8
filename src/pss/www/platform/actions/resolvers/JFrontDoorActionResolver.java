/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

/**
 * 
 * 
 * Created on 27-jun-2003
 * @author PSS
 */

public abstract class JFrontDoorActionResolver extends JBasicWebActionResolver {

  @Override
//	protected void onPerform() throws Exception {
  protected void attachToRunningThread() throws Exception {
    super.attachToRunningThread();
  }

}
