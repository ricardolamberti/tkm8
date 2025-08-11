package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordM extends TravelPortRecord {

	public RecordM() {
		ID = MRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
