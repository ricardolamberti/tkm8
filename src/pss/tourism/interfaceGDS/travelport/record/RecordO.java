package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordO extends TravelPortRecord {

	public RecordO() {
		ID = ORECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
