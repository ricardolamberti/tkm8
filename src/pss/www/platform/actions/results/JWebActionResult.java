/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.results;

import pss.www.platform.actions.resolvers.JBasicWebActionResolver;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public abstract class JWebActionResult {


  JWebActionResult() {
  }


  public abstract void applyResultTo(JBasicWebActionResolver zResolver) throws Exception;

}
