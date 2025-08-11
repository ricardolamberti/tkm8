package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;

public class JDoWaitingResolver extends JBasicWebActionResolver {


	@Override
	protected String getBaseActionName() {
		return "waiting";
	}
	@Override
	protected boolean addDictionaryInfo() {return false;}
	protected boolean isStadistic(){  return false;};

	protected void attachToRunningThread() throws Exception {
//		this.getRequest().attachToRunningThreadWhitinBase(this.getSession());
	}

	@Override
	protected boolean isAjax() {return true;}
	@Override
	protected JWebActionResult perform() throws Throwable {
    this.addObjectToResult("ajax", "ajax");

  	return super.perform();
	}
}
