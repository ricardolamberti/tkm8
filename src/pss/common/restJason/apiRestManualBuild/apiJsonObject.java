package pss.common.restJason.apiRestManualBuild;

import com.google.gson.JsonObject;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class apiJsonObject {

	protected int order;
	protected String title;
	protected JList<apiJsonObject> jsonObjects =  JCollectionFactory.createList();
	
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
	

	public void addJsonObject(apiJsonObject object) throws Exception {
		this.jsonObjects.addElement(object);
	}
	
	
	public void addJsonObject(String title, apiJsonObject object) throws Exception {
		object.setTitle(title);
		this.jsonObjects.addElement(object);
	}
	
	
	public JList<apiJsonObject> getObjects() throws Exception {
		return this.jsonObjects;
	}

	
	public JIterator<apiJsonObject> getObjectsIterator() throws Exception {
		return this.jsonObjects.getIterator();
	}
	
	public JsonObject getConvertedJasonObject() throws Exception {
	    JsonObject ret = new JsonObject();
	    JIterator<apiJsonObject> iter = this.getObjectsIterator();
	    while (iter.hasMoreElements()) {
	    	apiJsonObject apiObject = iter.nextElement();
	    	if (apiObject instanceof apiJsonElement ) {
	    		apiJsonElement el = ((apiJsonElement) apiObject);
	    		ret.addProperty(el.getKey(), el.getValue().toString());
	    	} else 	if (apiObject instanceof apiJsonRecord ) {
	    		apiJsonRecord el = ((apiJsonRecord) apiObject);
	    		ret.add(el.getTitle(), el.getConvertedJasonObject());
	    	} else if (apiObject instanceof apiJsonRecords ) {
	    		apiJsonRecords el = ((apiJsonRecords) apiObject);
	    		ret.add(el.getTitle(), el.getConvertedJasonArray());
	    	}
	    }
	    
	    return ret;
	}

}
