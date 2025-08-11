package  pss.common.logviewer;

import java.io.File;

import pss.JPath;
import pss.core.services.records.JRecords;


public class BizLogs extends JRecords<BizLog> {

	@Override
	public Class<BizLog> getBasedClass() { return BizLog.class;}
	
	
  @Override
	public boolean readAll() throws Exception {
    this.endStatic();
    File oFile = new File(JPath.PssPathLog());
    File aFile[] = oFile.listFiles();
    for ( int i=0; i < aFile.length; i++ ) {
      File oEntry = aFile[i];
      if (!oEntry.isDirectory()) {
        BizLog oLog = new BizLog();
        oLog.setFileName(oEntry.getName());
        this.addItem(oLog);
      }
    }
    this.setStatic(true);
    return true;
  }

}
