package pss.core.graph.analize;

import java.awt.Color;

import pss.core.tools.GeoPosition;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;


public class Dataset {
	private String name;
	private String colname;
	private String color;
	private String type;
	private String link;

	private JMap<String,Value> values;
	private String parentYAxis = "P";
	private JMap<String,String> attributes;
	private long refNumerica;
	private GeoPosition position;
	
	public GeoPosition getGeoPosition() {
		return position;
	}

	public void setGeoPosition(GeoPosition position) {
		this.position = position;
	}

	public long getRefNumerica() {
		return refNumerica;
	}

	public void setRefNumerica(long refNumerica) {
		this.refNumerica = refNumerica;
	}

	public Dataset() {
		
	}
	
	public Dataset(String zName) {
		name = JTools.encodeString(zName);
		colname = name;
	}
	public Dataset(String zName,String zColName) {
		name = JTools.encodeString(zName);
		colname = JTools.encodeString(zColName);
	}
	public Dataset(String zName,String zColName,  String zColor) {
		name = JTools.encodeString(zName);
		colname = JTools.encodeString(zColName);
		color = zColor;
	}
	public Dataset(String zName,String zColName, String zColor, GeoPosition zGPosition) {
		name = JTools.encodeString(zName);
		colname = JTools.encodeString(zColName);
		color = zColor;
		position = zGPosition;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=JTools.encodeString(name);
	}
	static int it =0;
	
	public String getColor() {
		String Colors[]= { "a6cee3","1f78b4","b2df8a","33a02c","fb9a99","e31a1c","fdbf6f","ff7f00","cab2d6","6a3d9a","ffff99","b15928","8dd3c7","ffffb3","bebada","fb8072","80b1d3","fdb462","b3de69","fccde5","d9d9d9","bc80bd","ccebc5","ffed6f","1b9e77","d95f02","7570b3","e7298a","66a61e","e6ab02","a6761d","666666","fbb4ae","b3cde3","ccebc5","decbe4","fed9a6","ffffcc","e5d8bd","fddaec","f2f2f2" };
		if (color==null || color.equals("")) color=Colors[it++];
		if (it>=Colors.length) it=0;
		return color;
	}

	public String getColorDegrade(int valor) {
		String Colors[]= { "fd04dd", "d803bc", "b902a1", "9a0286", "7b026b", "68025b", "4c0142", "32012c", "1b0118", "050004" };
		color=Colors[valor>Colors.length-1?Colors.length-1:valor];
		return color;
	}
	
	public String getColor(Color color1,Color color2,int porc) throws Exception  {
    Color color3;
    

    float ratio = (float) porc / (float) 100;
    int red = (int) (color2.getRed() * ratio + color1.getRed() * (1 - ratio));
    int green = (int) (color2.getGreen() * ratio + color1.getGreen() * (1 - ratio));
    int blue = (int) (color2.getBlue() * ratio + color1.getBlue() * (1 - ratio));
    color3 = new Color(red,green,blue);
		return JTools.ColorToString(color3);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setColor(String color) {
		this.color=color;
	}
	
	public JMap<String, Value> getValues() {
		if (values==null)
			values = JCollectionFactory.createOrderedMap();
		return values;
	}
	
	public void setValues(JMap<String, Value> values) {
		this.values=values;
	}

	
	public String getParentYAxis() {
		return parentYAxis;
	}

	
	public void setParentYAxis(String parentYAxis) {
		this.parentYAxis=parentYAxis;
	}

	
	public String getColname() {
		return colname;
	}

	
	public void setColname(String colname) {
		this.colname=JTools.encodeString(colname);
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
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
