/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

import pss.www.platform.content.generators.JXMLContent;


public interface JXMLRepresentable {
  
  
  public void toXML(JXMLContent zContent) throws Exception;
  public void destroy();

}
