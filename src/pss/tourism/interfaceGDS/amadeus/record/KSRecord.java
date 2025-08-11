package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KSRecord extends KRecord {
	
		public KSRecord() {
			ID="KS-";
		}
		
		public boolean isReIssued() {
			return getFieldValue(ISSUE_TYPE).equals("Y");
		}


}
