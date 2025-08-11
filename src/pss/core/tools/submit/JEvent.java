package pss.core.tools.submit;


public class JEvent  {

  Object object = null;
  int id = 0;
  String msg = null;
  boolean bConsumed = false;
  private String sSecondaryMessage;

  public JEvent(int zId) {
    id = zId;
  }
  public JEvent(int zId, String zMsg) {
    id = zId;
    msg = zMsg;
  }
  public JEvent(Object zObject, int zId, String zMsg) {
    object = zObject;
    id = zId;
    msg = zMsg;
  }

  public JEvent(Object zObject, int zId, String zMsg, String zSecondaryMessage) {
	object = zObject;
	id = zId;
	msg = zMsg;
	this.sSecondaryMessage = zSecondaryMessage;
  }

  public Object getObject() { return object; }
  public int getId() { return id; }
  public String getMsg() { return msg; }
  public boolean isConsumed() { return bConsumed; }
  public void consume() { bConsumed = true; }
  public void setObject(Object zSource) { object = zSource; }

  public Object getSource() { return object; }
  public void setSource(Object zSource) { object = zSource; }

  public String getSecondarMessage() {
	return this.sSecondaryMessage;
  }

}
