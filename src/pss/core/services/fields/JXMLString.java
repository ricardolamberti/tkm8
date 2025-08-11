package pss.core.services.fields;

import org.w3c.dom.Element;

import pss.core.tools.XML.JXMLElementFactory;

public class JXMLString extends JObject {

  public Element getValue() throws Exception { preset(); return getRawValue(); };
  public Element getRawValue() { 	return (Element)this.getObjectValue(); }

  public JXMLString() {
  }
  
	public JXMLString(Element value) {
		super.setValue(value);
	}
	@Override
	public boolean serializeAsXML() {
		return true;
	}
  @Override
	public String getObjectType() { return JObject.JXMLSTRING; }
  @Override
	public Class getObjectClass() { return Element.class; }	
	
//  public void setValue(String value) throws Exception {
//  	super.setValue(JXMLElementFactory.getInstance().createElementFromString(value));
//  }
  
  @Override
  public void unserialize(Element e) throws Exception {
  	super.setValue(e.getElementsByTagName("xml").item(0));
  }

	public String scapeLine(String line) throws Exception {
		line = line.replace("\r", "");
		line = line.replace("\n", "\\n");
		return line;
	}

  @Override
	public void setValue(String value) throws Exception {
//  	JTools.checkXMLLine(value);
  	value=this.scapeLine(value);
  	super.setValue(JXMLElementFactory.getInstance().createElementFromString(value));
  }
  
  @Override
  public String serialize() throws Exception {
  	return JXMLElementFactory.getInstance().serialize(this.getValue()); 
  }
  

  
}


