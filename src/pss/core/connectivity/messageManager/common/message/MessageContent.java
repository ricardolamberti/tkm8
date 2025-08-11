package pss.core.connectivity.messageManager.common.message;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class MessageContent implements IMessageContent {

	/**
   * 
   */
	private static final long serialVersionUID = 4016769086069035685L;

	protected ArrayList<MessageComponent> pComponents = null;
	String sourceSessionId = null;

	@SuppressWarnings("unchecked")
	public String dumpMessage() {
		String out = "";
		Field[] oFields;
		
		Class class2Dump;
		class2Dump = this.getClass();
		
		while (true) {
			if (class2Dump == null) {
				break;
			}
			// Solo para debug, descomentar de ser necesario
			// System.out.println("dumping class: " + class2Dump.getSimpleName());
			oFields = class2Dump.getDeclaredFields();
			out += dumpClassFields(oFields);
			class2Dump = class2Dump.getSuperclass();
		}
		
		return out;
	}

	protected String dumpClassFields(Field[] zFields) {
		String out = "";
		for (Field f : zFields) {
			try {
				out += f.getName() + ": " + (f.get(this) == null ? "null" : f.get(this).toString()) + "\n";
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			}
		}
		return out;
	}
	
	
	public String getMessageId() {
		return String.valueOf(this.getClass().getSimpleName());
	}

	public boolean isEmpty() {
		return false;
	}

	public MessageComponent getMessageComponent(Class<? extends MessageComponent> zClass) {
		if (this.pComponents == null) {
			return null;
		}

		for (MessageComponent m : pComponents) {
			if (m.getClass() == zClass)
				return m;
		}
		return null;
	}

	public MessageContent addMessageComponent(MessageComponent zMsgComponent) {
		if (this.pComponents == null) {
			this.pComponents = new ArrayList<MessageComponent>();
			this.pComponents.add(zMsgComponent);
			return this;
		}

		int iIndex = this.pComponents.indexOf(zMsgComponent);
		if (iIndex != -1) {
			this.pComponents.set(iIndex, zMsgComponent);
			return this;
		}

		this.pComponents.add(zMsgComponent);
		return this;
	}

	@Override
	public boolean isEqual(IMessageContent zMsg) {
		if (zMsg.getClass() != this.getClass())
			return false;
	  return true;
	}
	
	@Override
	public String getSourceSessionId() {
	  return this.sourceSessionId;
	}
	
	@Override
	public void setSourceSessionId(String zSessionId) {
		this.sourceSessionId = zSessionId;
	}
}
