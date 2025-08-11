package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.core.tools.collections.JStringTokenizer;

public class MRecord extends AmadeusRecord {
	
		JOrderedMap<Integer,String> map = JCollectionFactory.createOrderedMap();
	
		public MRecord() {
			ID="M-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			
			line = line.substring(2);
			
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line, ';');
			tok.skipEmptyTokens(false);
			int i=1;
			while (tok.hasMoreTokens()) {
				String fareBasis = tok.nextToken();
				map.addElement(i++, fareBasis);
			}
			return line;
			
		}

		
		public String getFareBasic(int seg) {
			return map.getElement(seg);
		}

}
