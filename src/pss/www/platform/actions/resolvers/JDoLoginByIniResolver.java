/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import org.apache.cocoon.environment.Request;

import pss.core.data.BizPssConfig;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.views.IActionPerform;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JDoLoginByIniResolver extends JAbstractLoginResolver {

  @Override
	protected String getBaseActionName() {
    return "do.login";
  }

  @Override
	protected JWebActionResult perform() throws Throwable {
    // retrieve the fields from the request
		Request oCocoonRequest=this.getServletRequest();
		String ipaddress = oCocoonRequest.getRemoteAddr();

		String className=  BizPssConfig.getPssConfig().classLogin();
		IActionPerform view = (IActionPerform)Class.forName(className).newInstance();
		return view.perform(this);
  }


}
