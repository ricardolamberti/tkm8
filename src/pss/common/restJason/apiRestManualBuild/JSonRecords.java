package pss.common.restJason.apiRestManualBuild;

import com.google.gson.JsonArray;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JSonRecords extends JSonObject {

	private JList<JSonRecord> records =  JCollectionFactory.createList();

	public JSonRecords() throws Exception {
	}

	
	public JSonRecords(String title) throws Exception {
		this.setTitle(title);
	}

	public JSonRecords(String title, JList<JSonRecord> records) throws Exception {
		this.setTitle(title);
		this.records = records;
	}
	
	
	public void addRecord(JSonRecord record) throws Exception {
		this.records.addElement(record);
	}
	
	public JList<JSonRecord> getRecords() throws Exception {
		return this.records;
	}

	
	public JIterator<JSonRecord> getRecordsIterator() throws Exception {
		return this.records.getIterator();
	}
	
	public JsonArray getConvertedJasonArray() throws Exception {
		JsonArray ret = new JsonArray();
	    JIterator<JSonRecord> iter = this.getRecordsIterator();
	    while (iter.hasMoreElements()) {
	    	JSonRecord record = iter.nextElement();
	    	ret.add(record.getConvertedJasonObject());
	    }
	    
	    return ret;
	}
	
}
