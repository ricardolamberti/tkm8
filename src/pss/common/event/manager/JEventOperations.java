package pss.common.event.manager;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;

public class JEventOperations {
	
	private static String[] aModules = {
		  "pss.common.backup.settings.JBackUpEventOperations"
	};
	
	private static JRecords<BizVirtual> operations=null;
	
	public JEventOperations() {
	}
	
  public static synchronized JRecords<BizVirtual> getAllEventOperations() throws Exception {
  	if (operations!=null) return operations;
  	JRecords<BizVirtual> records = JRecords.createVirtualBDs();
  	for(int i=0; i<aModules.length; i++) {
  		JEventOperations event = (JEventOperations) Class.forName(aModules[i]).newInstance();
  		records.appendRecords(event.getEventOperations());
  	}
  	return (operations=records);
  }
  
  
  public JRecords<BizVirtual> getEventOperations() throws Exception {
  	return null;
  }


}
