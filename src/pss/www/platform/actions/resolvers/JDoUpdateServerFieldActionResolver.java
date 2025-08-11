package pss.www.platform.actions.resolvers;

import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.results.JGoOnWithRequestResult;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoUpdateServerFieldActionResolver  extends JDoPssActionResolver {


	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}
  @Override
	protected boolean isNewRecordEnabled() {return false;}

	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("__requestid", ""+getRequest().getIdRequestJS());
//		this.addObjectToResult("__dictionary", ""+JWebActionFactory.getCurrentRequest().winRegisteredObjects(getRequest().getPssIdDictionary(),true,false));
		JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(),true,false);
//		this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
		try {
			String providerParent=getRequest().getArgument("obj_provider_parent");
			String provider=getRequest().getArgument("obj_provider");
			String field=getRequest().getArgument("field");
			String value=getRequest().getArgument("value");

			JWins wins = (JWins)JWebActionFactory.getCurrentRequest().getRegisterObject(providerParent);
			if (wins==null)
				throw new Exception("");
			JWin win = (JWin)JWebActionFactory.getCurrentRequest().getRegisterObject(provider);
			if (wins==null)
				throw new Exception("");
			win.getRecord().getProp(field).setValueFormUI(value);
			JWebActionFactory.getCurrentRequest().setRegisteredRequestObject( "result", "OK");
		} catch (Exception e) {
			JWebActionFactory.getCurrentRequest().setRegisteredRequestObject( "result",  e.getMessage());
			PssLogger.logError(e);
		}
		return new JGoOnWithRequestResult();
	}
}

