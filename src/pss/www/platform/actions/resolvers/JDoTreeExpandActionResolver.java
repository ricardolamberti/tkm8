/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

public class JDoTreeExpandActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "tree-expand";
	}

	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}
  @Override
	protected boolean isNewRecordEnabled() {return false;}
  
}

