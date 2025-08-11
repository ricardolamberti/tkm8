package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.collections.JMap;

public class M9Record extends SabreRecord {
	
		public M9Record() {
			ID="M9";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			if (line.startsWith("M9")!=true)
				return line;
			
			while (true) {
					getInput().mark(10000);
					line = getInput().readLine();
					if (line==null)
						return null;
					if (line.trim().equals("")) continue;
					if (line.startsWith("M9")!=true&&line.startsWith("@@")!=true) {
						getInput().reset();
						return line;
					}
			}
		}


}
