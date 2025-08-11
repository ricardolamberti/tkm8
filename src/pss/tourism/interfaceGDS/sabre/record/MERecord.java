package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.collections.JMap;

public class MERecord extends SabreRecord {
	
		public MERecord() {
			ID="ME";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			while(true) {
				this.getInput().mark(10000);
				line=this.getInput().readLine();
				if (line==null) return line;
				if (line.startsWith(SabreRecord.MX)||line.startsWith(SabreRecord.MG)) {
					this.getInput().reset();
					return line;
				}
					
			}
		}


}
