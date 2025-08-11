package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class Record8 extends TravelPortRecord {

	private String PASSTYPE="PASSTYPE";
	private String COMMISION_PERCENTAJE="COMMISION_PERCENTAJE";
	private String COMMISIONS="COMMISIONS";
	

	
	public Record8() {
		ID = EIGHT;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String passtype = tok.nextToken();
		addFieldValue(PASSTYPE, passtype);
		
		while ( tok.hasMoreTokens() ) {
			String comm = tok.nextToken();
			int idx = comm.indexOf("-"); 
			if ( idx > 0 ) { 
				String field = comm.substring(0,idx);
				String value = comm.substring(idx+1);
				addFieldValue(field+passtype,value);
			}
			
		}
		
		return line;
		
	}

	public String getCommissionAmount(String paxtype) {
		return getFieldValueAsNumber("CA"+paxtype);
	}

	public String getCommissionPercentaje(String paxtype) {
		return getFieldValueAsNumber("CP"+paxtype);
	}



}
