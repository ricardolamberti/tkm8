package pss.www.platform.actions.resolvers;

import pss.common.event.device.BizChannel;
import pss.core.JAplicacion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.www.platform.actions.results.JGoOnWithRequestResult;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoPushNotificationActionResolver  extends JBasicFrontDoorActionResolver {



	protected JWebActionResult perform() throws Throwable {

		try {
		
			String channel=getRequest().getArgument("mobile_channel");
			if (channel==null) throw new Exception("Invalid Channel");
			String decryptChannel = JTools.decryptMessage(channel);
			String[] compChannel = decryptChannel.split("_\\|_");
			if (compChannel.length!=7) throw new Exception("Invalid Channel");
			String usuario = compChannel[0];
			String company = compChannel[1];
			String uuid = compChannel[2];
			long deviceID = JTools.getLongNumberEmbedded(compChannel[3]);
			String type = compChannel[4];
			long channelID = JTools.getLongNumberEmbedded(compChannel[5]);
			
			if (deviceID<=0)  throw new Exception("Invalid Channel");
			if (channelID<=0)  throw new Exception("Invalid Channel");
			try {
				JAplicacion.GetApp().openSession();
				JAplicacion.GetApp().openApp("push_notification", "Web", true);
				if (!checkChannel(channel, usuario, company, uuid,deviceID, type, channelID))
					 throw new Exception("Invalid Channel");
				JAplicacion.GetApp().closeApp();
				JAplicacion.GetApp().closeSession();
			} catch (Exception e) {
				JAplicacion.GetApp().closeApp();
				JAplicacion.GetApp().closeSession();
				throw e;
			}
			
			addObjectToResult("__deviceid", deviceID);
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return new JGoOnWithRequestResult();
	}
	
	public boolean checkChannel(String uuidChannel,String usuario,String company,String uuid,long deviceID,String type , long channelID) throws Exception {
		BizChannel channel = new BizChannel();
		channel.dontThrowException(true);
		if (!channel.read(channelID)) return false;
		return channel.checkChannel(uuidChannel,usuario,company,uuid,deviceID,type);
		
	}
}

