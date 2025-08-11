package pss.common.event.device;

import java.util.Date;

import pss.common.documentos.docEmail.BizDocEmail;
import pss.common.event.manager.BizEvent;
import pss.core.tools.JTools;

public class JLogicaDeviceMail implements ILogicaDevice {

	@Override
	public void sendMessage(BizDevice device, String title, String info) throws Exception {
		BizDocEmail n = new BizDocEmail();
		n.setCompany(device.getCompany());
		n.setObjMailCasilla(device.getObjTypeDevice().getObjUserSender().getObjMailCasilla());
		n.setTitulo(title);
		n.setBody(JTools.encodeIso(JTools.encodeString2(info)));
		n.setMailTo(device.getUUID());
		n.setEnviar(true);
		n.processInsert();
//		n.processEnviar();
		
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

	@Override
	public void sendMessage(BizDevice device, BizEvent e) throws Exception {
		if (!e.getObjSenderUser().hasMail()) return;
		BizDocEmail n = new BizDocEmail();
		n.setCompany(e.getCompany());
		n.setObjMailCasilla(e.getObjSenderUser().getObjMailCasilla());
		n.setTitulo(e.getTitle());
		n.setBody(JTools.encodeIso(JTools.encodeString2(e.getInfo())));
		n.setMailTo(device.getUUID());
		n.setEnviar(true);
		n.processInsert();
//		n.processEnviar();
		
		BizQueueMessage newMess = new BizQueueMessage();
  	newMess.setCompany(device.getCompany());
  	newMess.setIdDevice(device.getIdDevice());
  	newMess.setDate(new Date());
  	newMess.setTitle(e.getTitle());
  	newMess.setInfo(e.getInfo());
  	newMess.setType("INFO");
  	newMess.setSended(true);
  	newMess.processInsert();
	}
	
  public boolean requiredUser() {
  	return true;
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
