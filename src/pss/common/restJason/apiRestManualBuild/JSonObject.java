package pss.common.restJason.apiRestManualBuild;

import com.google.gson.JsonObject;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JSonObject {

	protected int order;
	protected String title;
	protected JList<JSonObject> jsonObjects =  JCollectionFactory.createList();
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int value) {
		this.order = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		this.title = value;
	}
	

	public void add(JSonObject object) throws Exception {
		this.jsonObjects.addElement(object);
	}
	
	public void add(String title, String sobj) throws Exception {
		this.add(title, new JSonElement(title, sobj));
	}	
	public void add(String title, JSonObject object) throws Exception {
		object.setTitle(title);
		this.jsonObjects.addElement(object);
	}
	
	
	public JList<JSonObject> getObjects() throws Exception {
		return this.jsonObjects;
	}

	
	public JIterator<JSonObject> getObjectsIterator() throws Exception {
		return this.jsonObjects.getIterator();
	}
	
	public JsonObject getConvertedJasonObject() throws Exception {
    JsonObject ret = new JsonObject();
    JIterator<JSonObject> iter = this.getObjectsIterator();
    while (iter.hasMoreElements()) {
    	JSonObject apiObject = iter.nextElement();
    	if (apiObject instanceof JSonElement ) {
    		JSonElement el = ((JSonElement) apiObject);
    		ret.addProperty(el.getKey(), el.getValue().toString());
    	} else 	if (apiObject instanceof JSonRecord ) {
    		JSonRecord el = ((JSonRecord) apiObject);
    		ret.add(el.getTitle(), el.getConvertedJasonObject());
    	} else if (apiObject instanceof JSonRecords ) {
    		JSonRecords el = ((JSonRecords) apiObject);
    		ret.add(el.getTitle(), el.getConvertedJasonArray());
    	}
    }
    
    return ret;
	}

}
