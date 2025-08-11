package pss.core.connectivity.messageManager.common.message;

import java.io.Serializable;

public interface IMessageContent extends Serializable {
	public String getSourceSessionId();
	public void setSourceSessionId(String zSessionId);
	public char getMessageType();
	public String getMessageId();
	public boolean isEmpty();
	public String toString();
	public String dumpMessage();
	public boolean isEqual(IMessageContent zMsg);
}
