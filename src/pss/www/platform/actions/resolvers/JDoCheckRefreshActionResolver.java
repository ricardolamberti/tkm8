package pss.www.platform.actions.resolvers;


public class JDoCheckRefreshActionResolver  extends JDoAjaxActionResolver {

	@Override
	protected String getBaseActionName() {
		return "check-refresh";
	}

	@Override
	protected boolean isAjax() {return true;}
	
	@Override
	protected void onPerform() throws Exception {
	//	
	}
	protected boolean isStadistic(){  return false;};

	protected boolean addDictionaryInfo( ) {
		return false;
	}

}