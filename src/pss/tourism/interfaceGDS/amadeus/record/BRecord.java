package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class BRecord extends AmadeusRecord {
	
		public static final String COMANDOCRS="COMANDOCRS";

		public BRecord() {
			ID="B-";
		}
		
		// ==========================================================================
		// AMD Record - Retrans && PNRLocator
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			
			int i=0;
			
			if ( line.substring(i+=2).length() < 2 ) return line;

			addFieldValue(COMANDOCRS, line.substring(i) );
			
			return line.substring(i);
		}
		
		public String getCommand() {
			String val = getFieldValue(COMANDOCRS);
			if ( val == null) return "";
			return val;
		}
		
		public boolean isBT() {
			String val = getCommand();
			if ( val.equals("BT")) return true;
			return false;
		}
		
		public boolean isRefund() {
			String val = getCommand();
			if ( val.equals("TRFP")) return true;
			return false;
		}

	


}
