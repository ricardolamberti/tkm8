package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;


public class BLKRecord extends AmadeusRecord {
	
		public static final String VERSION="VERSION";
		public static final String TIPOAIR="TIPOAIR";
		

		public BLKRecord() {
			ID=BLK;
		}
		
		// ==========================================================================
		// AIR Record - Version and Air Type
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line, long year) throws Exception {
			
			int i=0;
			
			if ( line.substring(i+=3).length() < 5 ) return line;
			
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i),AmadeusRecord.FIELD_SEPARATOR);
			
			String version = tok.nextToken();
			String tipoair = tok.nextToken();
			
			addFieldValue(VERSION, version.substring(version.length()-3) );
			addFieldValue(TIPOAIR, tipoair);

			
			return line;
		}
		
		public String getVersion() {
			return this.getFieldValue(VERSION);
		}


}
