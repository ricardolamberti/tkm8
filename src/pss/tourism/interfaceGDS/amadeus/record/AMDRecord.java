package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class AMDRecord extends AmadeusRecord {
	
		public static final String RETRANSMITED="RETRANSMITED";
		public static final String PNRFILE="PNRFILE";
		
		private boolean voided = false;
		private String voidDate;
		public AMDRecord() {
			ID=AmadeusRecord.AMD;
		}
		
		// ==========================================================================
		// AMD Record - Retrans && PNRLocator
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			
			int i=0;
			
			if ( line.substring(i+=3).length() < 1 ) return line;
			
			addFieldValue(RETRANSMITED, line.substring(i,i+=1).equals("R") ? "1" : "0" );
			addFieldValue(PNRFILE, line.substring(i,i+=10));
			
			if ( line.substring(i).indexOf("VOID") >= 0 ) {
				voided = true;
				voidDate = line.substring(line.indexOf("VOID")+4,line.indexOf("VOID")+9);
			}
			return line.substring(i);
		}
		public String doProcess(JMap<String,Object>mRecords, String line, long year) throws Exception {
			
			int i=0;
			
			if ( line.substring(i+=3).length() < 1 ) return line;
			
			addFieldValue(RETRANSMITED, line.substring(i,i+=1).equals("R") ? "1" : "0" );
			addFieldValue(PNRFILE, line.substring(i,i+=10));
			
			if ( line.substring(i).indexOf("VOID") >= 0 ) {
				voided = true;
				voidDate = line.substring(line.indexOf("VOID")+4,line.indexOf("VOID")+9);
			}
			
			return line.substring(i);
		}

		
		public boolean isVoid() {
			boolean ret = voided;
			return ret;
		}
		public String getVoidDate() {
			return voidDate;
		}
		public String getFile() {
			return getFieldValue(PNRFILE);
		}

}
