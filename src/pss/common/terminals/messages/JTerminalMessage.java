package pss.common.terminals.messages;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.core.services.fields.JObject;
import pss.core.services.fields.JXMLString;
import pss.core.tools.JTools;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public abstract class JTerminalMessage {

	private long lTerminalId=0L;
	private JMap<String, JObject> fields=null;

	public void setTerminalId(long term) {
		this.lTerminalId=term;
	}
	public long getTerminalId() {
		return this.lTerminalId;
	}
	
	public void addField(String field, JObject value) throws Exception {
		fields.addElement(field, value);
	}
	public JMap<String, JObject> getFields() throws Exception {
		if (this.fields!=null) return this.fields;
		this.createFieldsMap();
		return this.fields;
	}
	public void createFieldsMap() throws Exception {
		if (this.fields==null) this.fields = JCollectionFactory.createMap();
		this.loadFieldMap();
	}
	public void loadFieldMap() throws Exception {}
	
	public JObject getField(String field) throws Exception {
		return this.getFields().getElement(field);
	}
	
	public String serialize() throws Exception{
		StringBuffer xml = new StringBuffer();
		xml.append("<termMsg");
		xml.append(" class=\""+this.getClass().getName()+"\" ");
		xml.append(" terminal_id=\""+this.getTerminalId()+"\" >");
		JIterator<String> iter = this.getFields().getKeyIterator();
		while (iter.hasMoreElements()) {
			String field = iter.nextElement();
			xml.append("<field id=\""+field+"\" ");
			xml.append(createValueField(field));
		}
		xml.append("</termMsg>");
		return xml.toString();
	}
	
	private String createValueField(String field) throws Exception {
		JObject obj = this.fields.getElement(field);
		if (obj instanceof JXMLString)
			return "> " + obj.toString() + "</field>";
		else 
			return "value=\""+JTools.encodeString(obj.toString())+"\" />";
	}
	
	public static JTerminalMessage unserialize(String xml) throws Exception{
		Element element = JXMLElementFactory.getInstance().createElementFromString(xml);
		String answerClass = element.getAttribute("class");
		JTerminalMessage termMessage = (JTerminalMessage)Class.forName(answerClass).newInstance();
		String terminalId = element.getAttribute("terminal_id");
		termMessage.setTerminalId(Long.parseLong(terminalId));
		NodeList fields = element.getElementsByTagName("field");
		int len = fields.getLength();
		for (int i=0;i<len;i++) {
			Element e = (Element)fields.item(i);
			JObject obj = termMessage.getField(e.getAttribute("id"));
			if (obj instanceof JXMLString ) {
				obj.setValue(e.getElementsByTagName("document").item(0));
			} else
				obj.setValue(e.getAttribute("value"));
		}
		return termMessage;
	}
	
}
