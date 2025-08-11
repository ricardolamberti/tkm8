package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class Record03 extends TravelPortRecord {

	public Record03() {
		ID = ZEROTHREE;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
