/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.views.IActionPerform;
import pss.www.ui.views.JLoginView;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JDoLoginResolver extends JAbstractLoginResolver {

  @Override
	protected String getBaseActionName() {
    return "do.login";
  }

  @Override
	protected JWebActionResult perform() throws Throwable {
    // retrieve the fields from the request
		IActionPerform view = new JLoginView();
		return view.perform(this);
  }

	@Override
//protected void onPerform() throws Exception {
	protected void attachToRunningThread() throws Exception {
		super.attachToRunningThread();
	}



}
