package pss.core.graph.analize;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;


public class Value {
	private Object data;
	private String categorie;
	private JMap<String,String> attributes;
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data=data;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public void setCategorie(String categorie) {
		this.categorie=categorie;
	}

	public void addAttribute(String k,String v) {
		getAttributes().addElement(k,v);
	}

	public void addAttributes(JMap<String,String> att) {
		attributes = att;
	}

	public JMap<String,String> getAttributes() {
		if (attributes==null) {
			attributes = JCollectionFactory.createMap();
		}
		return attributes;
	}
	
	public String getAttributesAsString() {
		if (attributes==null) return "";
		String out="";
		JIterator<String> iatt = attributes.getKeyIterator();
		while(iatt.hasMoreElements()) {
			String key = iatt.nextElement();
			String value = attributes.getElement(key);
			out+=" "+key+"='"+value+"'";
		}
		
		return out;
	}

}
