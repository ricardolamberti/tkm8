/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JDoHomePageResolver extends JBasicWebActionResolver {

  @Override
	protected String getBaseActionName() {
    return "do.homePage";
  }

  @Override
	protected JWebActionResult perform() throws Throwable {
  	return this.getRedirector().goHomePage(null);
  }


}
