package pss.common.restJason.apiRestManualBuild;

import pss.core.tools.collections.JList;

public class apiJsonRecord extends apiJsonObject {

	
	public apiJsonRecord() throws Exception {
	}

	
	public apiJsonRecord(String title) throws Exception {
		this.setTitle(title);
	}

	public apiJsonRecord(String title, JList<apiJsonObject> jsonObjects) throws Exception {
		this.setTitle(title);
		this.jsonObjects = jsonObjects;
	}
	
}
