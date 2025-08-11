package pss.common.version.protocol;

import java.util.List;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqNewTransactions extends MessageContent {
	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}

	private static final long serialVersionUID = 8263529741065225772L;

	public long idPack;
	public String store;
	public String licence;
	public List<ReqNewTransaction> trxs;
}
 