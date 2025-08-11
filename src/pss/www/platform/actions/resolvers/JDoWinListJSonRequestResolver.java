package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;

public class JDoWinListJSonRequestResolver extends JDoWinListRefreshActionResolver {

  @Override
	protected String getBaseActionName() {
    return "do.winlistjson.request";
  }

	@Override
	protected JWebActionResult perform() throws Throwable {
//		System.out.println(getRequest());	
		return super.perform();
	}
	
	protected boolean isResetRegisteredObjects() {
		return false;
	}
	
	@Override
	public boolean resetOldObjects() throws Exception {
		return false;
	}

}
