package pss.common.version.server;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public interface IVersionServerProcess {
	MessageEnvelope onProcess(MessageEnvelope req) throws Exception;
}
 