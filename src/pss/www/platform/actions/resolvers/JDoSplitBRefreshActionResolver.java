/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

public class JDoSplitBRefreshActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "splitB-refresh";
	}

	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {
  	return false;
  } 
  
	protected boolean isFirstAccess() throws Exception {
		return false;
	}
	

}

