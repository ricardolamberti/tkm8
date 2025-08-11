/*
 * Created on 23-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import pss.www.platform.content.generators.JXMLContent;

/**
 * 
 * 
 * Created on 23-jun-2003
 * @author PSS
 */

public class JWebDisabledAction extends JWebServerAction {

  @Override
	public boolean isEnabled() {
    return false;
  }

  public void toXML(JXMLContent zContent) throws Exception {
    // do nothing; it is used only to indicate an action view must not be shown,
    // but it must not generate an action node
  }
  
  
  @Override
	public String asURL() {
    return "";
  }
  

}
