package pss.core.connectivity.messageManager.client.connection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;

public class MessageProcessList {
	private Queue<MessageProcessElement> msgProcessList = new LinkedList<MessageProcessElement>();

	public void subscribeToProcessList(IMessageContent zMsgFilter, IProcessMsg zProcEntry) throws Exception {
		if (getProcEntryByMsg(zMsgFilter) != null) {
			return;
//			JExcepcion.SendError("Ya existe un EntryPoint para el mensaje [" + zMsgFilter.getMessageId() + "]");
		}
		MessageProcessElement oMPE = new MessageProcessElement(zMsgFilter, zProcEntry);
		this.msgProcessList.add(oMPE);
	}

	public IProcessMsg getProcEntryByMsg(IMessageContent zMsg) throws Exception {
		Iterator<MessageProcessElement> oIt = this.msgProcessList.iterator();
		MessageProcessElement oMPE;

		while (oIt.hasNext()) {
			oMPE = oIt.next();
			if (oMPE.getFilterMsg().isEqual(zMsg)) {
				return oMPE.getProcMsg();
			}
		} // end while
		return null;
	}

	public Queue<IMessageContent> getSubscriptedMsgList() throws Exception {
		Queue<IMessageContent> qMC = new LinkedList<IMessageContent>();

		java.util.Iterator<MessageProcessElement> oIt = this.msgProcessList.iterator();
		MessageProcessElement mpe;

		while (oIt.hasNext()) {
			mpe = oIt.next();
			qMC.add(mpe.getFilterMsg());
		} // end while

		return qMC;
	}

}
