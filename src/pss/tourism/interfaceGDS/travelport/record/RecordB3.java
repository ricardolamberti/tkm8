package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.FareParser;

public class RecordB3 extends FareParser {

	public RecordB3() {
		ID = "B";
	}
	
	// ==========================================================================
		// A Record
		// ==========================================================================
		public String doProcess(JMap<String, Object> mRecords, String line)
				throws Exception {
			int i = 2;
			processFareLine(line.substring(i));
			return line;

		}

}
