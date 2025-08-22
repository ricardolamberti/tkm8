package pss.tourism.interfaceGDS.amadeus.record;

public class KSRecord extends KRecord {
	
		public KSRecord() {
			ID="KS-";
		}
		
		public boolean isReIssued() {
			return getFieldValue(ISSUE_TYPE).equals("Y");
		}


}
