package pss.www.platform.actions.resolvers;

import pss.core.data.BizPssConfig;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.views.IActionPerform;

public class JDoRegisterResolver  extends JAbstractLoginResolver {

  @Override
	protected String getBaseActionName() {
    return "do.register";
  }

  @Override
	protected JWebActionResult perform() throws Throwable {
    // retrieve the fields from the request
		String className=  BizPssConfig.getPssConfig().classRegisterLogin();
		IActionPerform view = (IActionPerform)Class.forName(className).newInstance();
//  	IActionPerform view = new JRegistrationView();
		return view.perform(this);
  }


}
