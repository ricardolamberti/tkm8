package pss.www.platform.actions.resolvers;

import pss.common.event.device.BizChannel;
import pss.core.tools.PssLogger;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.results.JGoOnWithRequestResult;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoGetChannelActionResolver  extends JDoPssActionResolver {


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
		JWebActionFactory.getCurrentRequest().winRegisteredObjects(getWinFactory(),true,false);
//		this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());

		try {
			String uuid=getRequest().getArgument("mobile_uuid");
			String type=getRequest().getArgument("mobile_type");

			BizChannel channel = new BizChannel();
			channel.execProcessAddChannel(uuid, type);
		
			JWebActionFactory.getCurrentRequest().setRegisteredRequestObject("channel", channel.getUUIDChannel());
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return new JGoOnWithRequestResult();
	}
	
}

