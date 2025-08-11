package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KRFWRecord extends AmadeusRecord {
	
	public KRFWRecord() {
		ID="KRFW";
	}
	
	// ==========================================================================
	// A Record 
	// ==========================================================================
	public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

		return line;
	}


}
