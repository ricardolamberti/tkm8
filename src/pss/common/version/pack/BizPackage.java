package pss.common.version.pack;

import java.util.Date;

import pss.common.version.JGenerateVersion.VersionStatus;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPackage  extends JRecord {

  private JLong pIdpackage = new JLong();
  private JDate pDateCreation = new JDate();
  private JString pStatus = new JString();
  private JDate pDateInstallation = new JDate();
  private JString pDescription = new JString();
  private JBoolean pFullVersion = new JBoolean();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
  public void setIdpackage(long zValue) throws Exception {    pIdpackage.setValue(zValue);  }
  public long getIdpackage() throws Exception {     return pIdpackage.getValue();  }
  public boolean isNullIdpackage() throws Exception { return  pIdpackage.isNull(); } 
  public void setNullToIdpackage() throws Exception {  pIdpackage.setNull(); } 
  public void setDateCreation(Date zValue) throws Exception {    pDateCreation.setValue(zValue);  }
  public Date getDateCreation() throws Exception {     return pDateCreation.getValue();  }
  public boolean isNullDateCreation() throws Exception { return  pDateCreation.isNull(); } 
  public void setNullToDateCreation() throws Exception {  pDateCreation.setNull(); } 
  public void setStatus(VersionStatus zValue) throws Exception {    pStatus.setValue(zValue.toString());  }
  public VersionStatus getStatus() throws Exception {     return VersionStatus.get(pStatus.getValue());  }
  public boolean isNullStatus() throws Exception { return  pStatus.isNull(); } 
  public void setNullToStatus() throws Exception {  pStatus.setNull(); } 
  public void setDateInstallation(Date zValue) throws Exception {    pDateInstallation.setValue(zValue);  }
  public Date getDateInstallation() throws Exception {     return pDateInstallation.getValue();  }
  public boolean isNullDateInstallation() throws Exception { return  pDateInstallation.isNull(); } 
  public void setNullToDateInstallation() throws Exception {  pDateInstallation.setNull(); } 
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
  public boolean isNullDescription() throws Exception { return  pDescription.isNull(); } 
  public void setNullToDescription() throws Exception {  pDescription.setNull(); } 
  public void setFullVersion(boolean zValue) throws Exception {    pFullVersion.setValue(zValue);  }
  public boolean getFullVersion() throws Exception {     return pFullVersion.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPackage() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_package", pIdpackage );
    this.addItem( "date_creation", pDateCreation );
    this.addItem( "status", pStatus );
    this.addItem( "date_installation", pDateInstallation );
    this.addItem( "description", pDescription );
    this.addItem( "full_version", pFullVersion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_package", "Id package", true, true, 18 );
    this.addFixedItem( FIELD, "date_creation", "Date creation", true, true, 10 );
    this.addFixedItem( FIELD, "status", "Status", true, true, 15 );
    this.addFixedItem( FIELD, "date_installation", "Date installation", true, false, 10 );
    this.addFixedItem( FIELD, "description", "Description", true, false, 250 );
    this.addFixedItem( FIELD, "full_version", "Full Version", true, true, 1 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "vrs_package"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void execProcessReadyToApply() throws Exception {
		JExec oExec = new JExec(this, "readyToApply") {

			@Override
			public void Do() throws Exception {
				ProcessReadyToApply();
			}
		};
		oExec.execute();
	}
  public void ProcessReadyToApply() throws Exception { 
  	setStatus(VersionStatus.READY_TO_APPLY);
  	processUpdate();
  }


	public void execProcessReadyToSend() throws Exception {
		JExec oExec = new JExec(this, "readyToSend") {

			@Override
			public void Do() throws Exception {
				ProcessReadyToSend();
			}
		};
		oExec.execute();
	}
  public void ProcessReadyToSend() throws Exception { 
  	setStatus(VersionStatus.READY_TO_SEND);
  	processUpdate();
  }

  /**
   * Default read() method
   */
  public boolean read( long zIdpackage ) throws Exception { 
    addFilter( "id_package",  zIdpackage ); 
    return read(); 
  } 
}
