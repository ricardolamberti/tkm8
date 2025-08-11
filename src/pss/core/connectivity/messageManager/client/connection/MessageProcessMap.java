package pss.core.connectivity.messageManager.client.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;

public class MessageProcessMap {
	HashMap<String, MessageProcessList> msgProcessMap = new HashMap<String, MessageProcessList>();
	
	public void subscribeToProcessList(IMessageContent zMsgFilter, IProcessMsg zProcEntry) throws Exception {
		MessageProcessList mpl;
		if (this.msgProcessMap.containsKey(zMsgFilter.getMessageId())) {
			mpl = this.msgProcessMap.get(zMsgFilter.getMessageId());
		} else {
			mpl = new MessageProcessList();
			this.msgProcessMap.put(zMsgFilter.getMessageId(), mpl);
		}
		
		mpl.subscribeToProcessList(zMsgFilter, zProcEntry);
	}
	
	public IProcessMsg getProcEntryByMsg(IMessageContent zMsg) throws Exception {

		if (this.msgProcessMap.containsKey(zMsg.getMessageId()) == false) {
			return null;
		}
		MessageProcessList mpl = this.msgProcessMap.get(zMsg.getMessageId());
		return mpl.getProcEntryByMsg(zMsg);
	}
	
	public Queue<IMessageContent> getSubscriptedMsgList() throws Exception {
		Queue<IMessageContent> qMC = new LinkedList<IMessageContent>();
		Iterator<MessageProcessList> oIt = this.msgProcessMap.values().iterator();
		MessageProcessList mpl;
		
		while (oIt.hasNext()) {
			mpl = oIt.next();
			qMC.addAll(mpl.getSubscriptedMsgList());
		} // end while
		
		return qMC;
	}
}
