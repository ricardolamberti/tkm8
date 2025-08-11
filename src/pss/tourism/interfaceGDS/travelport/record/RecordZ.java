package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordZ extends TravelPortRecord {

	public RecordZ() {
		ID = ZRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
