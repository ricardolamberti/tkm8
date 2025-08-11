package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.FareParser;

public class QRecord extends FareParser {

	int mode = 1;
	
	
	public QRecord() {
		ID = "Q-";
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
