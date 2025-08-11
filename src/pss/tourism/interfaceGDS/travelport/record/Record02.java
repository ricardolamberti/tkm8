package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class Record02 extends TravelPortRecord {

	public Record02() {
		ID = ZEROTWO;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
