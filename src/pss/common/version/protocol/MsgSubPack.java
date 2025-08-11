package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageComponent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgSubPack  extends MessageComponent {

		private static final long serialVersionUID = 167819782534446274L;
		public MsgSubPack(){;}
  	public String key;
  	public String pack;
		
  	public char getMessageType() {
  		return MessageEnvelope.MSG_TYPE_RESPONSE;
  	} 

}
 