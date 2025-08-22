package pss.common.event.device;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import pss.common.event.manager.BizEvent;
import pss.core.tools.JTools;

public class JLogicaDeviceTelegram  implements ILogicaDevice {

	@Override
	public void subscribe(BizDevice device, BizChannel channel) throws Exception {
		String chatID = detectChatID(device,channel);
		if (chatID!=null) {
			device.setUUID(chatID);
			device.processUpdate();
		}
	}
  public String getLinkSubscribe(BizTypeDevice typeDevice,BizChannel channel) throws Exception {
  	return "https://telegram.me/"+typeDevice.getBotId()+"?start="+channel.getUsuario();
  }
	
	@Override
	public void sendMessage(BizDevice device, String title, String info) throws Exception {

		URL msg = new URL("https://api.telegram.org/bot"
				+ device.getObjTypeDevice().getTagId() + "/sendMessage?chat_id=" + device.getUUID()
				+ "&text=\"" + title+" - "+info + "\"");
		BufferedReader in = new BufferedReader(new InputStreamReader(msg.openStream()));
		in.close();
		
		BizQueueMessage newMess = new BizQueueMessage();
  	newMess.setCompany(device.getCompany());
  	newMess.setIdDevice(device.getIdDevice());
  	newMess.setDate(new Date());
  	newMess.setTitle(title);
  	newMess.setInfo(info);
  	newMess.setType("INFO");
  	newMess.setSended(true);
  	newMess.processInsert();
		

	}
	
	public String detectChatID(BizDevice device,BizChannel channel) throws Exception {
		URL msg = new URL("https://api.telegram.org/bot"+ device.getObjTypeDevice().getTagId() + "/getUpdates");
		BufferedReader in = new BufferedReader(new InputStreamReader(msg.openStream()));
		String response ="";
		while (in.ready()) {
			response+=  in.readLine();
		}
		
		in.close();
		int pos = response.indexOf("/start "+channel.getUsuario());
		if (pos==-1) return null;
		int pos2 = response.lastIndexOf("\"chat\":", pos);
		if (pos2==-1) return null;
		int pos3 = response.indexOf("\"id\":", pos2);
		if (pos3==-1) return null;
		int pos4 = response.indexOf(",", pos2);
		if (pos4==-1) return null;
		String chatId= ""+JTools.getLongNumberEmbedded(response.substring(pos3+5,pos4));
	
		return chatId;
	}

 


	@Override
	public void sendMessage(BizDevice device, BizEvent e) throws Exception {
		sendMessage(device, e.getTitle(),e.getInfo());
	}
	
  public boolean requiredUser() {
  	return false;
  }
  public boolean requiredTagId() {
  	return true;
  }
}
