package pss.tourism.pnr;

import java.util.Date;

import pss.JPath;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizPNRFilename extends JRecord {
  private JString pPnrFile = new JString();
  private JString pDescripcion = new JString();
	protected JString pArchivo=new JString();
	protected JString pDirectory=new JString();
	protected JString pCompany=new JString();
	protected JString pGDS=new JString();
	protected JLong pOrder=new JLong();
	protected JLong pOrderProcesamiento=new JLong();
	protected JLong pInterfaceId=new JLong();
	
	protected JDateTime pDate = new JDateTime();


	public void setCompany(String zValue)  {		pCompany.setValue(zValue);}
	public String getCompany() throws Exception {		return pCompany.getValue();	}
	public void setPNR(String zValue)  {		pPnrFile.setValue(zValue);}
	public String getPNR() throws Exception {		return pPnrFile.getValue();	}
	public void setGDS(String zValue)  {		pGDS.setValue(zValue);}
	public String getGDS() throws Exception {		return pGDS.getValue();	}
	public void setOrderProcesamiento(long zValue)  {		pOrderProcesamiento.setValue(zValue);}
	public long getOrderProcesamiento() throws Exception {		return pOrderProcesamiento.getValue();	}
	public void setInterfaceID(long zValue)  {		pInterfaceId.setValue(zValue);}
	public long getInterfaceID() throws Exception {		return pInterfaceId.getValue();	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setArchivo(String zValue) throws Exception {    pArchivo.setValue(zValue);  }
  public String getArchivo() throws Exception {     return pArchivo.getValue();  }
  public void setDirectory(String zValue) throws Exception {    pDirectory.setValue(zValue);  }
  public String getDirectory() throws Exception {     return pDirectory.getValue();  }
  public Date getDate() throws Exception {     return pDate.getValue();  }
  public void setDate(Date date) throws Exception {     pDate.setValue(date); }


  /**
   * Constructor de la Clase
   */
  public BizPNRFilename() throws Exception {
		addItem("company", pCompany);
    this.addItem( "pnr", pPnrFile );
    this.addItem( "filename", pArchivo );
    this.addItem( "directory", pDirectory );
    this.addItem( "creation_date", pDate );
    this.addItem( "gds", pGDS );
    this.addItem( "arrive_order", pOrder );
    this.addItem( "orden_proc", pOrderProcesamiento );
    this.addItem( "interface_id", pInterfaceId );

  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "empresa", true, false, 50);
    this.addFixedItem( KEY, "pnr", "pnr", true, false, 18);
    this.addFixedItem( KEY, "creation_date", "creation date", true, true, 550 );
    this.addFixedItem( KEY, "arrive_order", "order", false, false, 18 );
    this.addFixedItem( FIELD, "filename", "archivo", true, false, 550 );
    this.addFixedItem( FIELD, "directory", "directorio", true, false, 550 );
    this.addFixedItem( FIELD, "gds", "gds", true, false, 550 );
    this.addFixedItem( FIELD, "interface_id", "id", true, false, 550 );
    this.addFixedItem( FIELD, "orden_proc", "orden_proc", true, false, 64 );

  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "tur_pnr_file"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String   getPNRFilePath() throws Exception {
		String origen = JPath.PssPathInputProcessed()+"/"+ pDirectory.getValue()+"/"+ pArchivo.getValue();
		String dst = JPath.PssPathTempFiles()+"/"+pArchivo.getValue();
		JTools.copyFile(origen, dst);
		return pArchivo.getValue();
	}

}
