package pss.core.services.fields;

/**
 * Description: Se utiliza como propiedad en el BD contraparte del control JFormMultiple,
 * una lista de selección múltiple.
 *
 *
 */

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.core.tools.JTools;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JObjRecord extends JBaseObject {
	
	private JMap<String, JBaseObject> map = null;
	
  public JObjRecord() {
  }

  @Override
	protected boolean serializeAsXML() {
  	return true;
  }
  public JMap<String, JBaseObject> getMap() throws Exception {
  	if ( this.map != null ) return this.map;
  	this.loadFieldMap();
  	if ( this.map == null ) this.map = JCollectionFactory.createMap();
  	//super.setValue(JCollectionFactory.createMap());
  	return map;
  }

  public void addValue( String id, JBaseObject value ) throws Exception {
    if ( this.map == null ) this.map = JCollectionFactory.createMap();
    this.map.addElement(id, value);
  }
  public void addValue(JBaseObject value) throws Exception {
  	this.addValue(String.valueOf(this.size()), value);
  }

  public void setValue( JMap<String, JBaseObject> value ) throws Exception {
    this.map = value;
  }

  public JIterator<JBaseObject> getValueIterator() throws Exception {
    return this.getMap().getValueIterator();
  }
  public JIterator<String> getKeyIterator() throws Exception {
    return this.getMap().getKeyIterator();
  }

  public boolean hasValues() throws Exception {
    return !this.getMap().isEmpty();
  }

  public int size() throws Exception { 
  	return this.getMap().size(); 
  }

  public String getObjectType() { return JObject.JCONTAINER; }
  
  public String serializeDocument() throws Exception {
  	StringBuffer doc = new StringBuffer();
  	doc.append("<document class=\""+this.getClass().getName()+"\">");
  	doc.append(this.serialize());
  	doc.append("</document>");
  	return doc.toString();
  }
  @Override
	protected String serialize() throws Exception {
  	StringBuffer doc = new StringBuffer();
  	doc.append("<record>");
  	JIterator<String> iter = this.getKeyIterator();
  	while (iter.hasMoreElements()) {
  		String id = iter.nextElement();
  		doc.append("<obj id=\""+id+"\" ");
  		doc.append(this.createValueField(id));
  		doc.append("</obj>");
  	}
  	doc.append("</record>");
  	return doc.toString();
  }

	public static JObjRecord unserializeDocument(String xml) throws Exception{
		Element element = JXMLElementFactory.getInstance().createElementFromString(xml);
		String className = element.getAttribute("class");
		JObjRecord record = (JObjRecord)Class.forName(className).newInstance();
		record.unserialize(element);
		return record;
	}
	
  @Override
	protected void unserialize(Element e) throws Exception {
  	Element xml = (Element)e.getElementsByTagName("record").item(0);
  	NodeList nodes = xml.getChildNodes();
  	int len = nodes.getLength();
  	for (int i=0;i<len;i++) {
  		Element row = (Element) nodes.item(i);
  		JBaseObject obj = this.findField(row);
  		if (obj==null) continue;
  		obj.unserialize(row);
  	}
  }
  
 	public void loadFieldMap() throws Exception {}
	
	public JBaseObject findField(Element e) throws Exception {
		return this.getField(e.getAttribute("id"));
	}
	public JBaseObject getField(String id) throws Exception {
		return this.getMap().getElement(id);
	}
		
	private String createValueField(String key) throws Exception {
		JBaseObject obj = this.getField(key);
		String str = "class=\""+obj.getClass().getName()+"\" ";
		if (obj.serializeAsXML())
			return str + ">" + obj.serialize();
		else
			return str + "value=\""+ JTools.encodeString(obj.serialize())+"\">";
	}
	
//	protected String getXMLClassName(JBaseObject obj) throws Exception {
//		return "";
//	}
	

    

}
