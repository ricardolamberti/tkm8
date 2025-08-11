package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class Record4 extends TravelPortRecord {

	public Record4() {
		ID = FOUR;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
