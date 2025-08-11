package pss.tourism.pnr;

import java.io.File;

import pss.JPath;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizPNRFile extends JRecord {
  private JString pPnrFile = new JString();
  private JString pDescripcion = new JString();
	protected JString pArchivo=new JString();
	protected JString pDirectory=new JString();
	protected JString pCompany=new JString();


	public void setCompany(String zValue)  {		pCompany.setValue(zValue);}
	public String getCompany() throws Exception {		return pCompany.getValue();	}
	public void setPNR(String zValue)  {		pPnrFile.setValue(zValue);}
	public String getPNR() throws Exception {		return pPnrFile.getValue();	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setArchivo(String zValue) throws Exception {    pArchivo.setValue(zValue);  }
  public String getArchivo() throws Exception {     return pArchivo.getValue();  }
  public void setDirectory(String zValue) throws Exception {    pDirectory.setValue(zValue);  }
  public String getDirectory() throws Exception {     return pDirectory.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPNRFile() throws Exception {
		addItem("company", pCompany);
    this.addItem( "pnr", pPnrFile );
    this.addItem( "archivo", pArchivo );
    this.addItem( "directory", pDirectory );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "empresa", true, false, 50);
    this.addFixedItem( KEY, "pnr", "pnr", false, false, 18);
    this.addFixedItem( KEY, "archivo", "archivo", true, true, 550 );
    this.addFixedItem( KEY, "directory", "directory", true, true, 550 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 550 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String   getPNRFilePath() throws Exception {
		String origen = JPath.PssPathInputProcessed()+"/"+ pDirectory.getValue()+"/"+ pArchivo.getValue();
		if (GetVision().equals("ERROR"))
			origen = JPath.PssPathInputError()+"/"+ pDirectory.getValue()+"/"+ pArchivo.getValue();
			
		File f = new File(origen);
		if (!f.exists()) {
			String newOrigen = JTools.replace(origen,"/NDC/","/AMADEUS/");
			f = new File(newOrigen);
			if (!f.exists()) {
				 newOrigen = JTools.replace(origen,"/NDC/","/SABRE/");
				 f = new File(newOrigen);
				 if (f.exists()) {
				  origen = newOrigen;
				 }
			} else {
			  origen = newOrigen;

			}
		}
		
		String dst = JPath.PssPathTempFiles()+"/"+pArchivo.getValue();
		JTools.copyFile(origen, dst);
		return pArchivo.getValue();
	}

}
