package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class EMQRecord extends AmadeusRecord {
	
		public EMQRecord() {
			ID="EMQ-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			while (line.startsWith("EMQ")) {
		
				getInput().mark(1000);
				line = getInput().readLine();
				if (!line.startsWith("EMQ")) {
					getInput().reset();
					break;
				}
			}
		
			return line;
		}


}
