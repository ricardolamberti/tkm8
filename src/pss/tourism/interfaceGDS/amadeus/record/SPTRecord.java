package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class SPTRecord extends AmadeusRecord {
	
	public SPTRecord() {
		ID="SPT";
	}
	
	// ==========================================================================
	// A Record 
	// ==========================================================================
	public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

		return line;
	}


}

