package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class M7Record extends SabreRecord {

	public static final String MESSAGE_ID = "IU7MID";
	public static final String REMARK_NUMBER = "IU7RKN";
	public static final String REMARK_TEXT = "IU7RMK";
	
	private JList<String> rmkItems = JCollectionFactory.createList();
	
	public M7Record() {
		ID = SabreRecord.M7;
	}

  //  ==========================================================================
  //  M7 Record - Passenger Itinerary Data Record
  //  ==========================================================================
  //  The information contained in the M7 record is only created if the TJR option is
  //  set to the ON position AND if the remarks section of the PNR contains remarks
  //  items that are entered with a "5#"
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		addFieldValue(REMARK_NUMBER, line.substring(i, i += 2));

		int rmi = getNumberOfRemarkItems(mRecords);
		
		String remarkData = "";
		int index = 1;
		getInput().mark(1000);

    while ( (line = getInput().readLine())!=null ) { 
			if (line.trim().equals("")) continue;

			if (line.startsWith("M7")==false) {
				getInput().reset();
				break;
			}
				
      if ( index > 1 ) {
        i=4;
      }
      index++;
      try {
	      rmkItems.addElement(line.substring(index));
	      remarkData+=line.substring(i)+"|";
      } catch (Exception eee) {
      	
      }
			getInput().mark(1000);
    }
		addFieldValue(REMARK_TEXT, remarkData);

		return line;

	}
	
	public String getRemarkItem(int i) {
		return rmkItems.getElementAt(i);
	}

	private int getNumberOfRemarkItems(JMap<String, Object> mRecords) {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM7();
	}

	

}
