package pss.common.restJason.apiRestManualBuild;

import com.google.gson.Gson;

import pss.core.tools.collections.JList;

public class JSonRecord extends JSonObject {

	
	public JSonRecord() throws Exception {
	}

	
	public JSonRecord(String title) throws Exception {
		this.setTitle(title);
	}

	public JSonRecord(String title, JList<JSonObject> jsonObjects) throws Exception {
		this.setTitle(title);
		this.jsonObjects = jsonObjects;
	}
	
	public String convertToString() throws Exception {
		return new Gson().toJson(this.getConvertedJasonObject());
	}
	
}
