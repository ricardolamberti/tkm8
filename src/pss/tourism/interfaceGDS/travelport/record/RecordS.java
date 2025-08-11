package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordS extends TravelPortRecord {

	public RecordS() {
		ID = SRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
