package pss.tourism.interfaceGDS.amadeus.record;


public class KNRecord extends KRecord {
	
		public KNRecord() {
			ID=KN;
		}
		
		public boolean isReIssued() {
			return getFieldValue(ISSUE_TYPE).equals("Y");
		}


}
