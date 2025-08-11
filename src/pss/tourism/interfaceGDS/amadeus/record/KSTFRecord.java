package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;


public class KSTFRecord extends KRecord {
	
		public KSTFRecord() {
			ID="KSTF";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}



}
