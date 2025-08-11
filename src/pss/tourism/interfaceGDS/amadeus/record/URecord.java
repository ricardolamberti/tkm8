package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class URecord extends HRecord {

	public URecord() {
		ID = "U-";
	}

	// ==========================================================================
	// A Record
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {
		if (line.indexOf("SEGMENTO DE PROTECCION")!=-1) {
			return line;
			
		}
		if (line.indexOf("SEGMENTO DE PROTECCION")!=-1) {
			return line;
			
		}
		
		return super.doProcess(mRecords, line);
	}

	public boolean hasSpecialSubRecord() {
		try {
			getInput().mark(14);
			char cbuf[] = new char[14];
				getInput().read(cbuf, 0, 14);
				
				String line = new String (cbuf);
		
				if (line.indexOf("EMD")>0) {
					getInput().reset();
					getInput().read(cbuf, 0, 10);
					return true;
				}
				
				getInput().reset();

		} catch (Exception ee) {
		}
		return false;
	}

}
