package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;

public class JDoEchoResolver extends JIndoorsActionResolver {


	@Override
	protected String getBaseActionName() {
		return "echo";
	}
	
	@Override
	protected boolean addDictionaryInfo() {return false;}
	protected boolean isStadistic(){  return false;};

	@Override
	protected boolean isAjax() {return true;}
	@Override
	protected JWebActionResult perform() throws Throwable {
    this.addObjectToResult("ajax", "ajax");

  	return super.perform();
	}
}
