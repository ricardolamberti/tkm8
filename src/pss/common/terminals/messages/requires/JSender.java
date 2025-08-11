package pss.common.terminals.messages.requires;

import pss.common.terminals.connection.server.JTerminalPoolServer;
import pss.common.terminals.connection.server.JTerminalPoolShadow;
import pss.common.terminals.messages.answer.Answer;
import pss.core.services.fields.JObjRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class JSender implements ISender {

	public Answer send(String macAddress, String message) throws Exception {
		JTerminalPoolShadow shadow=JTerminalPoolServer.getTerminalPoolShadow(macAddress);
		if (shadow==null) 
			JExcepcion.SendError("La terminal no esta registrada en el servidor");
		
		PssLogger.logDebug("Envio Mensaje: " + message);
		String response=shadow.sendMessage(message);
		if (response==null)
			JExcepcion.SendError("Sin respuesta de la terminal");
		PssLogger.logDebug("Respuesta: " + response);
		
		return (Answer) JObjRecord.unserializeDocument(response);
	}
}
