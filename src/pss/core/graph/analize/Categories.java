package pss.core.graph.analize;

import pss.core.tools.GeoPosition;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;


public class Categories {
	private String name;
	private JMap<String,String> attributes;
	private GeoPosition position;
	
	public GeoPosition getGeoPosition() {
		return position;
	}

	public void setGeoPosition(GeoPosition position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name=name;
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
