package pss.common.event.device;

import pss.common.event.manager.BizEvent;

public interface ILogicaDevice {

  public void sendMessage(BizDevice device,String title, String info) throws Exception;
  public void sendMessage(BizDevice device,BizEvent e) throws Exception;
  public void subscribe(BizDevice device,BizChannel channel) throws Exception;
  public String getLinkSubscribe(BizTypeDevice typeDevice,BizChannel channel) throws Exception;

  boolean requiredUser();
  public boolean requiredTagId();
}
