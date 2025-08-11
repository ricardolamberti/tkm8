package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;


public class KNTFRecord extends KRecord {
	
		public KNTFRecord() {
			ID="KNTF";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}



}
