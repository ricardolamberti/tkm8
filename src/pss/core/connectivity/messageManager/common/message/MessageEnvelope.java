package pss.core.connectivity.messageManager.common.message;

import java.io.Serializable;

import pss.core.tools.JExcepcion;

public class MessageEnvelope implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7852897284010647659L;
	// Messages Types
	public static final char MSG_TYPE_ECHO = 'E';
	public static final char MSG_TYPE_LOGIN = 'L';
	public static final char MSG_TYPE_LOGIN_ERROR = 'M';
	public static final char MSG_TYPE_REQUEST_SUBSCRIBE = 'P';
	public static final char MSG_TYPE_REQUEST = 'R';
	public static final char MSG_TYPE_EVENT_SUBSCRIBE = 'S';
	public static final char MSG_TYPE_RESPONSE = 'T';
	public static final char MSG_TYPE_EVENT = 'V';
	public static final char MSG_TYPE_SYSTEM_EVENT = 'W';
	public static final char MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE = 'X';
	public static final char MSG_TYPE_ERROR = 'Z';

	// private static String MSG_TYPE_FIELD_NAME = "#MSG_TYPE";
	// private static String MSG_SOURCE_FIELD_NAME = "#MSG_SOURCE";
	// private static String MSG_TARGET_FIELD_NAME = "#MSG_TARGET";
	// private static String MSG_ID_FIELD_NAME = "#MSG_ID";
	// private static String MSG_CONTENT_FIELD_NAME = "#MSG_CONTENT";

	private char pMsgType = 0;
	private String pMsgSource = null;
	private String pMsgTarget = null;
	private String pMsgId = null;
	private IMessageContent pMsgContent = null;

	/**
	 * @return the pMsgType
	 */
	public char getMsgType() {
		return pMsgType;
	}

	/**
	 * @param zMsgType
	 *          the pMsgType to set
	 * @throws Exception
	 */
	public void setMsgType(char zMsgType) throws Exception {

		switch (zMsgType) {
		case MSG_TYPE_ECHO:
		case MSG_TYPE_ERROR:
		case MSG_TYPE_LOGIN:
		case MSG_TYPE_LOGIN_ERROR:
		case MSG_TYPE_REQUEST_SUBSCRIBE:
			// case MSG_TYPE_PREPROCESS:
		case MSG_TYPE_REQUEST:
		case MSG_TYPE_EVENT_SUBSCRIBE:
		case MSG_TYPE_RESPONSE:
		case MSG_TYPE_EVENT:
		case MSG_TYPE_SYSTEM_EVENT:
		case MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE:
			break;

		default:
			JExcepcion.SendError("El tipo de mensaje [" + zMsgType + "] no corresponde a ninguno de los tipos de mensaje validos");
		} // end switch

		pMsgType = zMsgType;
	}

	/**
	 * @return the pMsgSource
	 */
	public String getMsgSource() {
		return pMsgSource;
	}

	/**
	 * @param zMsgSource
	 *          the pMsgSource to set
	 */
	public void setMsgSource(String zMsgSource) {
		pMsgSource = zMsgSource;
	}

	/**
	 * @return the pMsgTarget
	 */
	public String getMsgTarget() {
		if (pMsgTarget == null)
			return "";
		return pMsgTarget;
	}

	/**
	 * @param zMsgTarget
	 *          the pMsgTarget to set
	 */
	public void setMsgTarget(String zMsgTarget) {
		pMsgTarget = zMsgTarget;
	}

	/**
	 * @return the pMsgId
	 */
	public String getMsgId() {
		return pMsgId;
	}

	/**
	 * @param zMsgId
	 *          the pMsgId to set
	 * @throws Exception
	 */
	public void setMsgId(String zMsgId) throws Exception {
		if (zMsgId == null)
			JExcepcion.SendError("El msg id no puede ser nulo");

		if (zMsgId.isEmpty())
			JExcepcion.SendError("El msg id no puede ser vacio");

		pMsgId = zMsgId;
	}

	public void setMsgContent(IMessageContent zMsgContent) throws Exception {
		this.pMsgContent = zMsgContent;
	}

	public IMessageContent getMessageContent() {
		return this.pMsgContent;
	}

	public String getMessageTypeDescription() {
		return getMessageTypeDescription(this.pMsgType);
	}

	public static String getMessageTypeDescription(char zMsgType) {
		String strMsgTypeDesc = new String();
		switch (zMsgType) {
		case MSG_TYPE_ECHO:
			strMsgTypeDesc = "ECHO";
			break;
		case MSG_TYPE_LOGIN:
			strMsgTypeDesc = "LOGIN";
			break;
		case MSG_TYPE_LOGIN_ERROR:
			strMsgTypeDesc = "LOGIN ERROR";
			break;
		case MSG_TYPE_REQUEST_SUBSCRIBE:
			strMsgTypeDesc = "REQUEST SUBSCRIBE";
			break;
		// case MSG_TYPE_PREPROCESS:
		// strMsgTypeDesc = "PREPROCESS";
		// break;
		case MSG_TYPE_REQUEST:
			strMsgTypeDesc = "REQUEST";
			break;
		case MSG_TYPE_EVENT_SUBSCRIBE:
			strMsgTypeDesc = "EVENT SUBSCRIBE";
			break;
		case MSG_TYPE_RESPONSE:
			strMsgTypeDesc = "RESPONSE";
			break;
		case MSG_TYPE_EVENT:
			strMsgTypeDesc = "EVENT";
			break;
		case MSG_TYPE_ERROR:
			strMsgTypeDesc = "ERROR";
			break;
		case MSG_TYPE_SYSTEM_EVENT:
			strMsgTypeDesc = "SYSTEM EVENT";
			break;
		case MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE:
			strMsgTypeDesc = "SYSTEM EVENT SUBSCRIBE";
			break;
		default:
			strMsgTypeDesc = "UNKNOWN [" + zMsgType + "]";
		}

		return strMsgTypeDesc;
	}

	public String dumpMessage() {
		String strDumpMsg = new String();

		strDumpMsg = "***** MESSAGE DUMP ******" + String.valueOf('\n');
		strDumpMsg += "Message Header:" + String.valueOf('\n');
		strDumpMsg += "---------------" + String.valueOf('\n');
		strDumpMsg += "MESSAGE TYPE:" + this.getMessageTypeDescription() + String.valueOf('\n');
		strDumpMsg += "MESSAGE SOURCE:" + this.getMsgSource() + String.valueOf('\n');
		strDumpMsg += "MESSAGE TARGET:" + this.getMsgTarget() + String.valueOf('\n');
		strDumpMsg += "MESSAGE ID:" + this.getMsgId() + String.valueOf('\n');
		strDumpMsg += "Message Fields:" + String.valueOf('\n');
		strDumpMsg += "---------------" + String.valueOf('\n');

		if (this.pMsgContent == null) {
			strDumpMsg += "<empty>" + String.valueOf('\n');
		} else {
			strDumpMsg += this.pMsgContent.dumpMessage();
		}

		return strDumpMsg;
	}

}
