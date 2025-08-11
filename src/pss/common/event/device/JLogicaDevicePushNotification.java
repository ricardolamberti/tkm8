package pss.common.event.device;

import java.util.Date;

import pss.common.event.manager.BizEvent;

public class JLogicaDevicePushNotification implements ILogicaDevice {


	@Override
	public void sendMessage(BizDevice device,String title, String info) throws Exception {
		BizQueueMessage newMess = new BizQueueMessage();
  	newMess.setCompany(device.getCompany());
  	newMess.setIdDevice(device.getIdDevice());
  	newMess.setDate(new Date());
  	newMess.setTitle(title);
  	newMess.setInfo(info);
  	newMess.setType("INFO");
  	newMess.processInsert();
	
	}
	@Override
	public void sendMessage(BizDevice device,BizEvent e) throws Exception {
		BizQueueMessage queue = BizQueueMessage.buildFromEvent(e);
		queue.setCompany(device.getCompany());
		queue.setIdDevice(device.getIdDevice());
		queue.processInsert();
	
	}
	
	 public boolean requiredUser() {
	  	return false;
	  }
	  public boolean requiredTagId() {
	  	return false;
	  }
	  public void subscribe(BizDevice device,BizChannel channel) throws Exception {
	  	
	  }
	  public String getLinkSubscribe(BizTypeDevice typeDevice,BizChannel channel) throws Exception {
	  	return null;
	  }
}
