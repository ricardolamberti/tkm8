package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordQ extends TravelPortRecord {

	public RecordQ() {
		ID = QRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
