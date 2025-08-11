package  pss.common.backup.settings;

import pss.common.event.manager.JEventOperations;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class JBackUpEventOperations extends JEventOperations {
	
	public static String BACKUP_END = "1" ;

//	public static String DIFF_CTZ_DEBIT = "DIFF_CTZ_DEBIT";
//	public static String DIFF_CTZ_CREDIT = "DIFF_CTZ_CREDIT";
	
	@Override
	public JRecords<BizVirtual> getEventOperations() throws Exception {
		JRecords<BizVirtual> records = JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(BACKUP_END, "Back Up - Finalización", 1));
		return records;
	}

}
