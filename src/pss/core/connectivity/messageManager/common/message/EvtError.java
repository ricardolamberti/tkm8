package pss.core.connectivity.messageManager.common.message;


public class EvtError extends MessageContent {
	public enum EvtErrorCode {
		/**
		 * Sin codigo de error
		 */
		EC00000,
		// -----------------------------------------------------------------------------------------------------
		// Desde 00001 a 09999 son Warnings
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		// Desde 10000 a 10999 son Errores de conexión
		// -----------------------------------------------------------------------------------------------------
		/**
		 * La conexión con el servidor esta establecida pero no esta validada
		 */
		EC10000,
		/**
		 * Ningun "Cliente de mensajes" procesa el mensaje solicitado
		 */
		EC10001,
		// -----------------------------------------------------------------------------------------------------
		// Desde 11000 a 12000 son errores de SURTIDORES
		// -----------------------------------------------------------------------------------------------------
		/**
		 * El surtidor no puede procesar el requerimiento porque se encuentra en un estado inválido para dicho procesamiento
		 */
		EC11000,
		/**
		 * La manguera leida no esta configurada
		 */
		EC11001,
		/**
		 * El comando no puede ser ejecutado porque tiene uno o mas parametros invalidos.
		 */
		EC11002,
		/**
		 * El Surtidor tiene un comando de authorización o preset pendiente de ejecución
		 */
		EC11003,
		/**
		 * El Surtidor no puede aceptar una autorización porque esta en un estado inválido para procesar este comando
		 */
		EC11004,
		/**
		 * Comando de authorización o preset inválido
		 */
		EC11005,
		/**
		 * Error de configuracion
		 */
		EC11006,
		

		// -----------------------------------------------------------------------------------------------------
		// Desde 99000 hasta 99999 son los errores de sistema, errores no
		// contemplados, etc.
		// -----------------------------------------------------------------------------------------------------
		/**
		 * Codigo de error desconocido
		 */
		EC99998,
		/**
		 * Fatal Error Code
		 */
		EC99999
	};

	private static final long serialVersionUID = 81652604422506809L;

	// static final String MESSAGE_TYPE = "ERROR";

	protected EvtErrorCode pErrorCode = EvtErrorCode.EC00000;
	protected String pErrorMsg = null;
	protected MessageEnvelope pMsgEnvWithError = null;

	public EvtError() {

	}

	public EvtError(EvtErrorCode zErrorCode, String zErrorMsg, MessageEnvelope zMsgEnvWithError) {
		pErrorCode = zErrorCode;
		pErrorMsg = zErrorMsg;
		pMsgEnvWithError = zMsgEnvWithError;
	}

	public EvtError(EvtErrorCode zErrorCode, Exception zException, MessageEnvelope zMsgEnvWithError) {
		pErrorCode = zErrorCode;
		pErrorMsg = zException.getMessage();
		if (pErrorMsg == null || pErrorMsg.isEmpty()) {
			if (zException.getCause() != null)
				pErrorMsg = zException.getCause().getMessage();
			if (pErrorMsg == null || pErrorMsg.isEmpty())
				pErrorMsg = "unknown";
		}
		pMsgEnvWithError = zMsgEnvWithError;
	}

	public EvtError(String zErrorMsg, MessageEnvelope zMsgEnvWithError) {
		pErrorMsg = zErrorMsg;
		pMsgEnvWithError = zMsgEnvWithError;
	}

	public EvtError(Exception zException, MessageEnvelope zMsgEnvWithError) {
		pErrorMsg = zException.getMessage();
		pMsgEnvWithError = zMsgEnvWithError;
	}

	public String getErrorMsg() {
		return pErrorMsg;
	}

	public MessageEnvelope getMsgEnvWithError() {
		return this.pMsgEnvWithError;
	}

	@Override
	public String getMessageId() {
		String strRC = null;
		if (this.pMsgEnvWithError != null) {
			strRC = pMsgEnvWithError.getMsgId();
		}

		if (strRC == null) {
			strRC = new String("*");
		}

		return strRC;
	}

	@Override
	public char getMessageType() {
		if (this.pMsgEnvWithError != null) {
			if (this.pMsgEnvWithError.getMsgType() == MessageEnvelope.MSG_TYPE_LOGIN) {
				return MessageEnvelope.MSG_TYPE_LOGIN_ERROR;
			}
		}
		return MessageEnvelope.MSG_TYPE_ERROR;
	}

	@Override
	public boolean isEmpty() {
		if (this.pErrorMsg.isEmpty())
			return true;
		return false;
	}

	public EvtErrorCode getErrorCode() {
		return pErrorCode;
	}

	public String getErrorCodeDescription() {
		return getErrorCodeDescription(this.pErrorCode);
	}

	public static String getErrorCodeDescription(EvtErrorCode zEC) {
		String strErrorCodeDescr = null;
		switch (zEC) {
		case EC00000:
			strErrorCodeDescr = "No Error Code";
			break;
		// -----------------------------------------------------------------------------------------------------
		// Desde 00001 a 09999 son Warnings
		// -----------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------
		// Desde 10000 a 10999 son Errores de conexión
		// -----------------------------------------------------------------------------------------------------
		case EC10000:
			strErrorCodeDescr = "La conexión con el servidor esta establecida pero no esta validada";
			break;
		case EC10001:
			strErrorCodeDescr = "Ningun \"Cliente de mensajes\" procesa el mensaje solicitado";
			break;

		// -----------------------------------------------------------------------------------------------------
		// Desde 11000 a 12000 son errores de SURTIDORES
		// -----------------------------------------------------------------------------------------------------
		case EC11000:
			strErrorCodeDescr = "El surtidor no puede procesar el requerimiento porque se encuentra en un estado inválido para dicho procesamiento";
			break;
		case EC11001:
			strErrorCodeDescr = "La manguera leida no esta configurada";
			break;
		case EC11002:
			strErrorCodeDescr = "El comando no puede ser ejecutado porque tiene uno o mas parámetros inválidos.";
			break;
		case EC11003:
			strErrorCodeDescr = "El Surtidor tiene un comando de authorización o preset pendiente de ejecución";
			break;
		case EC11004:
			strErrorCodeDescr = "El Surtidor no puede aceptar una autorización porque esta en un estado inválido";
			break;
		case EC11005:
			strErrorCodeDescr = "Comando de autorización o preset inválido";
			break;

			// -----------------------------------------------------------------------------------------------------
			// Desde 99000 hasta 99999 son los errores de sistema, errores no
			// contemplados, etc.
			// -----------------------------------------------------------------------------------------------------
		case EC99998: // Unknown Error Code
			strErrorCodeDescr = "Error desconocido";
			break;
		case EC99999:
			strErrorCodeDescr = "Fatal Error Code";
			break;

		}
		return strErrorCodeDescr;
	}
	
	static protected long errorCode2Long(EvtErrorCode zErrorCode) {
		long errorCode = Long.valueOf(zErrorCode.toString().substring(2));
		return errorCode;
	}
	
	static public boolean ifWarning(EvtErrorCode zErrorCode) {
		long errorCodeValue = errorCode2Long(zErrorCode);
		if (errorCodeValue >= 1 && errorCodeValue <= 9999) 
			return true;
		return false;
	}
	
	static public boolean ifError(EvtErrorCode zErrorCode) {
		long errorCodeValue = errorCode2Long(zErrorCode);
		if (errorCodeValue >= 10000) 
			return true;
		return false;
	}
//	RJL referencia indevida fuera del paquete common
//	static public PumpErrorTypes getErrorType(EvtErrorCode zErrorCode) {
//		if (ifWarning(zErrorCode)) return PumpErrorTypes.WARNING;
//		if (ifError(zErrorCode)) return PumpErrorTypes.ERROR;
//		return null;
//	}
}
