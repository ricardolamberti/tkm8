package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class Record6 extends TravelPortRecord {

	public Record6() {
		ID = SIX;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		return line;
	}

}
