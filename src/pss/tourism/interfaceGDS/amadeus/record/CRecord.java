package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class CRecord extends AmadeusRecord {

	public static String VENDOR = "VENDOR";

	public CRecord() {
		ID = "C-";
	}

	// ==========================================================================
	// A Record
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 2).length() < 6)
			return line;

		try { 
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
				line.substring(i), '/');
		tok.nextToken();
		String create = tok.nextToken();

		tok = JCollectionFactory.createStringTokenizer(create, '-');
		tok.nextToken();
		String vendor = tok.nextToken();

		// if (y!=0) year=""+y;
		addFieldValue(VENDOR, vendor.substring(0,vendor.length()-2));
		
		} catch (Exception eee) {
			
		}

		return line.substring(i);
	}
	
	public String getVendor() {
		return getFieldValue(VENDOR);
	}

}
