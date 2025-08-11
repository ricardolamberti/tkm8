package pss.www.platform.actions.resolvers;

public class JDoWinListReloadActionResolver extends JDoPssActionResolver {
	@Override
	protected String getBaseActionName() {
		return "winlist-reload";
	}

	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}
	@Override
	protected boolean isBackPageOnDelete() {return false;}
  
}
