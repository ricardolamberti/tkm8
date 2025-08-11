package  pss.common.logviewer;

import pss.JPath;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizLog extends JRecord {

  private JString pFileName = new JString();
  private JString pFullPath = new JString() {
	public void preset() throws Exception {
  	this.setValue(getFullFileName());}
  };


//  private JBoolean pIsCurrent = new JBoolean() {
//  	public void preset() throws Exception {
//  		pIsCurrent.setValue(isCurrentLog());
//  	}
//	};

  public BizLog() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "file_name", pFileName);
    addItem( "full_path", pFullPath );
//    addItem( "is_current", pIsCurrent);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "file_name", "Archivo", true, false, 50 );
    addFixedItem( VIRTUAL, "full_path", "Ruta completa", true, false, 500);
//    addFixedItem( VIRTUAL, "is_current", "Log actual", true, false, 500 );
  }

  @Override
	public String GetTable() { return ""; }

  public String getFileName() throws Exception {
    return this.pFileName.getValue();
  }
  public String getFullFileName() throws Exception {
    return JPath.PssPathLog() + "/" + this.getFileName();
  }
  public void setFileName(String zFileName) throws Exception {
    this.pFileName.setValue(zFileName);
  }

//  public boolean isCurrentLog() throws Exception {
//    return PssLogger.getCurrentLogFile().trim().toUpperCase().endsWith(this.pFileName.getValue().trim().toUpperCase());
//  }


}// end class
