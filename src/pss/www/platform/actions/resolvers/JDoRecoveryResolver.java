package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.views.IActionPerform;
import pss.www.ui.views.JRecoveryView;

public class JDoRecoveryResolver   extends JAbstractLoginResolver {

  @Override
	protected String getBaseActionName() {
    return "do.recovery";
  }

  @Override
	protected JWebActionResult perform() throws Throwable {
    // retrieve the fields from the request
		//String className=  BizPssConfig.getPssConfig().classLogin();
//		IActionPerform view = (IActionPerform)Class.forName(className).newInstance();
  	IActionPerform view = new JRecoveryView();
		return view.perform(this);
  }


}
