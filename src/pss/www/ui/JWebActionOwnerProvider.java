/*
 * Created on 01-oct-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.www.platform.actions.JWebAction;


public interface JWebActionOwnerProvider {

  public String getProviderName() throws Exception;
  public String getRegisteredObjectId() throws Exception;
  public JWebAction getWebSourceAction() throws Exception;
  public String getTitle() throws Exception;
  public boolean isModal() throws Exception;

}
