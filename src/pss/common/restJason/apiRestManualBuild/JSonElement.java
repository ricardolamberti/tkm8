package pss.common.restJason.apiRestManualBuild;

public class JSonElement extends JSonObject {

	private String	key;
	private Object	value;

	public JSonElement(String key, Object value) throws Exception {
		this.key = key;
		this.value = "";
		if (value!=null)
			this.value = value;
			
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String value) {
		this.key = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}


}
