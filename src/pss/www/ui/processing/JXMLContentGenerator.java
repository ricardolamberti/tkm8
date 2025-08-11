/*
 * Created on 21-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

/**
 * 
 * 
 * Created on 21-jun-2003
 * @author PSS
 */

public interface JXMLContentGenerator extends JWebActionRequestProcessor {


  /**
   * Returns the name of the content this generator generates.<br>
   * If there is an active session, put its id between brackets. Otherwise, puts
   * a "no session" string between brackets. 
   * 
   * @return the name of the content this generator generates
   */
  public String getContentName();


}
