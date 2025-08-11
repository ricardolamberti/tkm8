package pss.common.restJason.apiRestManualBuild;

import com.google.gson.JsonArray;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class apiJsonRecords extends apiJsonObject {

	private JList<apiJsonRecord> records =  JCollectionFactory.createList();

	public apiJsonRecords() throws Exception {
	}

	
	public apiJsonRecords(String title) throws Exception {
		this.setTitle(title);
	}

	public apiJsonRecords(String title, JList<apiJsonRecord> records) throws Exception {
		this.setTitle(title);
		this.records = records;
	}
	
	
	public void addRecord(apiJsonRecord record) throws Exception {
		this.records.addElement(record);
	}
	
	public JList<apiJsonRecord> getRecords() throws Exception {
		return this.records;
	}

	
	public JIterator<apiJsonRecord> getRecordsIterator() throws Exception {
		return this.records.getIterator();
	}
	
	public JsonArray getConvertedJasonArray() throws Exception {
		JsonArray ret = new JsonArray();
	    JIterator<apiJsonRecord> iter = this.getRecordsIterator();
	    while (iter.hasMoreElements()) {
	    	apiJsonRecord record = iter.nextElement();
	    	ret.add(record.getConvertedJasonObject());
	    }
	    
	    return ret;
	}
	
}
