package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.collections.JMap;

public class MBRecord extends SabreRecord {
	
		public MBRecord() {
			ID="MB";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			if (line.startsWith("ME"))
				return line;
			
			while (true) {
					getInput().mark(10000);
					line = getInput().readLine();
					if (line==null)
						return null;
					if (line.trim().equals("")) continue;

					if (line.startsWith("ME")) {
						getInput().reset();
						return line;
					}
					if (line.startsWith("MX")) {
						getInput().reset();
						return line;
					}

			}

			
		}


}
