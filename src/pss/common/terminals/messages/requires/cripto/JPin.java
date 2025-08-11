package pss.common.terminals.messages.requires.cripto;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

public class JPin {
	
	private String msgConfirm;
	private String msgClave;
	private String msgProcess;
	private String amount;
	private String wKey;
	private int mKeyIndex;
	private int criptMethod;
	private String pan;
	private int pinLen;
	private int timeout;

  public JPin() {
  }

  public void setMsgConfirm(String value) { this.msgConfirm=value; }
	public void setMsgClave(String value) { this.msgClave=value; }
	public void setMsgProcess(String value) { this.msgProcess=value; }
	public void setAmount(String value) { this.amount=value; }
	public void setWorkingKey(String value) { this.wKey=value; }
	public void setMasterKeyIndex(int value) { this.mKeyIndex=value; }
	public void setCriptMethod(int value) { this.criptMethod=value; }
	public void setPan(String value) { this.pan=value; }
	public void setPinLen(int value) { this.pinLen=value; }
	public void setTimeout(int value) { this.timeout=value; }
	
	public String getMsgConfirm() { return this.msgConfirm; }
	public String getMsgClave() { return this.msgClave; }
	public String getMsgProcess() { return this.msgProcess; }
	public String getAmount() { return this.amount; }
	public String getWorkingKey() { return this.wKey; }
	public int getMasterKeyIndex() { return this.mKeyIndex; }
	public int getCriptMethod() { return this.criptMethod; }
	public String getPan() { return this.pan; }
	public int getPinLen() { return this.pinLen; }
	public int getTimeout() { return this.timeout; }
	
	public boolean hasMsgConfirm() { return this.msgConfirm!=null; }
  
}
