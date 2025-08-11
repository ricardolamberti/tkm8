package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class Record2 extends TravelPortRecord {

	private String CUST_ID = "CUST_ID";

	public Record2() {
		ID = TWO;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		
		if (line.length() <= 2)
			return line;

		addFieldValue(CUST_ID, line.substring(1));
		return line;
	}

	public String getCustomerId() {
		return getFieldValue(CUST_ID);
	}

}
