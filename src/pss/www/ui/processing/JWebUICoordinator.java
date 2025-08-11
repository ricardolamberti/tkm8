/*
 * Created on 07-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

import pss.www.platform.actions.JWebRequest;
import pss.www.ui.controller.JWebClientConfiguration;
import pss.www.ui.controller.JWebSessionOwnedObject;


public interface JWebUICoordinator extends JWebSessionOwnedObject {

  public void initialize(JWebRequest zRequest) throws Exception;
  public void refresh(JWebRequest zRequest) throws Exception;
  public JWebClientConfiguration getClientConfiguration();

  public JWebTreeCoordinator getTreeCoordinator();

  public boolean hasSession();

}
