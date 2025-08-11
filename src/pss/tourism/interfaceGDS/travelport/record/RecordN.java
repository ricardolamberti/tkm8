package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class RecordN extends TravelPortRecord {
	
	private String REMARKS="REMARKS";
	private String REMARKS_COUNT="REMARKS_COUNT";
	
	public RecordN() {
		ID = NRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 1).length() < 2)
			return line;
		
		int count = 0;
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);
		
		while ( tok.hasMoreTokens() ) {
			 String val = tok.nextToken();
			 if ( val.startsWith("RM-") == false ) continue;
			 count++;
		   addFieldValue(REMARKS+count, val.substring(3) );
		}
		
		addFieldValue(REMARKS_COUNT, count);

		return line;
		
	}

	public int getRemarkCount() {
		return Integer.parseInt(getFieldValue(REMARKS_COUNT));
	}

	public String getRemarkItem(int i) {
		return getFieldValue(REMARKS+i);
	}




}
