package pss.common.restJason;

import java.util.Objects;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

public class JSon {

	Object obj;
	
	public JSon(Object v) {
		this.obj=v;
	}

	public boolean isNull() {
		return this.obj==null;
	}
	
	public String asStr() {
		if (this.obj==null) return null;
		if (!(this.obj instanceof String)) return this.obj.toString();
		return (String) this.obj;
	}
	
	public long asLong() {
		if (obj instanceof String) return Long.parseLong(this.asStr());
		if (obj instanceof Integer) return (long)this.asInt();
		return ((Long)this.obj).longValue();
	}

	public double asDouble() {
		if (obj instanceof String) return Double.parseDouble(this.asStr().replace(",", "."));
		if (obj instanceof Integer) return (double)((Integer)this.obj);
		return ((Double)this.obj).doubleValue();
	}

	public boolean asBool() {
		if (this.obj==null) return false;
		if ((this.obj instanceof String)) return this.asStr().equalsIgnoreCase("true");
		if (!(this.obj instanceof Boolean)) return false;
		return ((Boolean)this.obj).booleanValue();
	}

	public int asInt() {
		if (obj instanceof String) return Integer.parseInt(this.asStr());
		return ((Integer)this.obj).intValue();
	}

	public String findKeyAsStr(String key) {
		return this.findKey(key).asStr();
	}

	public long findKeyAsLong(String key) {
		return this.findKey(key).asLong();
	}

	public int findKeyAsInt(String key) {
		return this.findKey(key).asInt();
	}

	public double findKeyAsDouble(String key) {
		return this.findKey(key).asDouble();
	}

	public boolean findKeyAsBool(String key) {
		return this.findKey(key).asBool();
	}

	public boolean hasKey(String key) {
		return this.find(key)!=null;
	}
	
	public JSon findKey(String key) {
		return new JSon(this.find(key));
	}
	
	private Object find(String key) {
    if (obj instanceof JSONObject) {
      JSONObject jsonObject = (JSONObject) obj;

	    if (jsonObject.has(key)) {
	    	return jsonObject.get(key);
	    }

      return jsonObject.keySet().stream()
              .map(childKey -> new JSon(jsonObject.get(childKey)).find(key))
              .filter(Objects::nonNull)
              .findFirst()
              .orElse(null);
      
    } else if (obj instanceof JSONArray) {
      JSONArray jsonArray = (JSONArray) obj;

      return IntStream.range(0, jsonArray.length())
              .mapToObj(jsonArray::get)
              .map(o -> new JSon(o).find(key))
              .filter(Objects::nonNull)
              .findFirst()
              .orElse(null);
    } else {
      return null;
    }
	}

	public JList<JSon> getList(String key) throws Exception {
		JList<JSon> retList = JCollectionFactory.createList();
		JSONArray jsonArray = this.getJSonArray(key);
		for (int i=0; i<jsonArray.length();i++) {
			retList.addElement(new JSon(jsonArray.get(i)));
		}
		return retList;
	}
	
	public JSONArray getJSonArray(String key) {
		JSONArray jsonArray = new JSONArray();
		if (obj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) obj;
			if (jsonObject.has(key)) {
				if (jsonObject.get(key) instanceof JSONArray) {
					jsonArray = (JSONArray) jsonObject.get(key);
				}
			}
		}
		return jsonArray;
	}

	
}
