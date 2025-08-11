package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.sabre.record.SabreRecord;

public class MFRecord extends AmadeusRecord {
	
		public MFRecord() {
			ID="MF";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			int i = 2;
			//addFieldValue(MESSAGE_ID, ID); // MESSAGE ID

			while(true) {
				this.getInput().mark(1000);
				line=this.getInput().readLine();
				if (line==null) return line;
				if (!line.startsWith(SabreRecord.MF)) {
					this.getInput().reset();
					return line;
				}
					
			}

			//return line;
		}


}
