package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class ATCRecord extends AmadeusRecord {
	
	public ATCRecord() {
		ID="ATC-";
	}
	
	// ==========================================================================
	// A Record 
	// ==========================================================================
	public String doProcess(JMap<String,Object>mRecords, String line, long year) throws Exception {

		return line;
	}
}
