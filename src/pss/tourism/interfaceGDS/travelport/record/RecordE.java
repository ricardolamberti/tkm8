package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordE extends TravelPortRecord {
	
	private String ENDORSEMENT="ENDORSEMENT";
	
	public RecordE() {
		ID = ERECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;
		
		addFieldValue(ENDORSEMENT, line.substring(1) );


		return line;
		
	}





}
