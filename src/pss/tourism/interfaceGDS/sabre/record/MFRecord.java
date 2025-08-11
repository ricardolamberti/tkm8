package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.collections.JMap;

public class MFRecord extends SabreRecord {

	public static final String MESSAGE_ID = "IUFMID"; //
	
	public MFRecord() {
		ID = SabreRecord.MF;
	}


	// ==========================================================================
	// M2 Record - Passenger Ticket Data Record
	// ==========================================================================
	// This record contains passenger ticket data. This data is needed for the
	// printing
	// of the ticket. There is one M2 record per passenger ticketed.
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID

		while(true) {
			this.getInput().mark(10000);
			line=this.getInput().readLine();
			if (line==null) return line;
			if (line.startsWith(SabreRecord.MX)||line.startsWith(SabreRecord.MG)) {
				this.getInput().reset();
				return line;
			}
				
		}

	}

	

}
