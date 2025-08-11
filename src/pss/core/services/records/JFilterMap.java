package pss.core.services.records;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;


public class JFilterMap implements Serializable {

  /**
	 * 
	 */
	static final long serialVersionUID = 7178408074053706489L;
	private String fieldChange;
	private JMap<String, Serializable> filterMap = null;
//  private boolean bAssigned=false;

  public JFilterMap() throws Exception {
  }
  
	public void addFilterMap(String filter, boolean value) throws Exception {
		this.addFilterMap(filter, (value?"S":"N"));
	}
  
	public void addFilterMap(String filter, Date value) throws Exception {
		this.addFilterMap(filter, JDateTools.DateToString(value));
	}
	public void addFilterMap(String filter, String value) throws Exception {
		if (this.filterMap==null) this.filterMap = JCollectionFactory.createOrderedMap();
		this.filterMap.addElement(filter.toLowerCase(), value);
	}

	public void addFilterMap(String filter, Serializable value) throws Exception {
		if (this.filterMap==null) this.filterMap = JCollectionFactory.createOrderedMap();
		this.filterMap.addElement(filter.toLowerCase(), value);
	}
	public boolean hasFilters() {
		return this.filterMap!=null;
	}
	
	public JMap<String, Serializable> getMap() throws Exception {
		return this.filterMap;
	}
	
	public boolean hasFilter(String f) throws Exception {
		if (!this.hasFilters()) return false;
		return this.filterMap.containsKey(f);
	}
	
	public void clearFilter(String f,String op) throws Exception {
		if (!this.hasFilters()) return;
		this.filterMap.removeElement(f+"|"+op);
	}
	
	public String getFilterAsStr(String f, String def) throws Exception {
		return (String)this.getFilterValue(f, def);
	}

	public String getFilterAsStr(String f) throws Exception {
		return (String)this.getFilterValue(f,false);
	}
	public Object getFilterValue(String f) throws Exception {
		return this.getFilterValue(f, true);
	}
	public Object getFilterValue(String f,boolean acceptURL) throws Exception {
		if (!this.hasFilter(f)) return null;
		Object data=this.filterMap.getElement(f);
		if (data instanceof String) {
			String d =(String)data;
			if (acceptURL)
				d=JTools.decodeURLIfNecesary(d);
//			if (d.indexOf("%3A")!=-1) d=JTools.replace(d,"%3A", ":");
			return d;
		}
		return data;
	}
	public String getFilterOp(String f) throws Exception {
		if (!this.hasFilter(f)) return null;
		Object data=this.filterMap.getElement(f);
		if (data instanceof String) {
			String d =(String)data;
			d = d.substring(d.lastIndexOf("|")+1);
			return d;
		}
		return "=";
	}
	
	public boolean getFilterBoolean(String f) throws Exception {
		if (!this.hasFilter(f)) return false;
		return getFilterValue(f,false).equals("S");
	}

//	public boolean isAssigned() {
//		return this.bAssigned;
//	}
//	
//	public void setAssigned(boolean value) {
//		this.bAssigned=value;
//	}

	public Object getFilterValue(String f, String def) throws Exception {
		if (!this.hasFilter(f)) return def;
		return this.getFilterValue(f,false);
	}

	public String serialize() throws Exception {
		HashMap<String,Serializable> map = new HashMap<String,Serializable>();
		JIterator<String> it = filterMap.getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			Object obj = filterMap.getElement(key);
			if (obj instanceof Serializable)
				map.put(key, (Serializable)obj);
		}
		return JTools.AsciiToHex(JTools.serialize(map));
	}
	
	static public JFilterMap unserialize(String cadena) throws Exception {
		JFilterMap map = new JFilterMap();
		map.filterMap = JCollectionFactory.createOrderedMap();
		if (!cadena.equals(""))
			map.filterMap.setMap((HashMap)JTools.unserialize(JTools.HexToAscii(cadena)));
		return map;
	}

	public String getFieldChange() {
		return fieldChange;
	}

	public void setFieldChange(String fieldChange) {
		this.fieldChange = fieldChange;
	}

}
