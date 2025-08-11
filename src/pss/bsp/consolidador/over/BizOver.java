package pss.bsp.consolidador.over;

import java.util.Date;
import pss.core.services.records.JRecord;
import pss.core.services.fields.*;

public class BizOver extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JLong pLinea = new JLong();
  private JString pStatus = new JString();
  private JString pBoleto = new JString();
  private JDate pFecha = new JDate();
  private JString pIdaerolinea = new JString();
  private JFloat pOverPed = new JFloat();
  private JFloat pOverRec = new JFloat();
  private JFloat pDif = new JFloat();
  private JString pObservaciones = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 
  public void setIdpdf(String zValue) throws Exception {    pIdpdf.setValue(zValue);  }
  public String getIdpdf() throws Exception {     return pIdpdf.getValue();  }
  public boolean isNullIdpdf() throws Exception { return  pIdpdf.isNull(); } 
  public void setNullToIdpdf() throws Exception {  pIdpdf.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setStatus(String zValue) throws Exception {    pStatus.setValue(zValue);  }
  public String getStatus() throws Exception {     return pStatus.getValue();  }
  public boolean isNullStatus() throws Exception { return  pStatus.isNull(); } 
  public void setNullToStatus() throws Exception {  pStatus.setNull(); } 
  public void setBoleto(String zValue) throws Exception {    pBoleto.setValue(zValue);  }
  public String getBoleto() throws Exception {     return pBoleto.getValue();  }
  public boolean isNullBoleto() throws Exception { return  pBoleto.isNull(); } 
  public void setNullToBoleto() throws Exception {  pBoleto.setNull(); } 
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public boolean isNullFecha() throws Exception { return  pFecha.isNull(); } 
  public void setNullToFecha() throws Exception {  pFecha.setNull(); } 
  public void setIdaerolinea(String zValue) throws Exception {    pIdaerolinea.setValue(zValue);  }
  public String getIdaerolinea() throws Exception {     return pIdaerolinea.getValue();  }
  public boolean isNullIdaerolinea() throws Exception { return  pIdaerolinea.isNull(); } 
  public void setNullToIdaerolinea() throws Exception {  pIdaerolinea.setNull(); } 
  public void setOverPed(double zValue) throws Exception {    pOverPed.setValue(zValue);  }
  public double getOverPed() throws Exception {     return pOverPed.getValue();  }
  public boolean isNullOverPed() throws Exception { return  pOverPed.isNull(); } 
  public void setNullToOverPed() throws Exception {  pOverPed.setNull(); } 
  public void setOverRec(double zValue) throws Exception {    pOverRec.setValue(zValue);  }
  public double getOverRec() throws Exception {     return pOverRec.getValue();  }
  public boolean isNullOverRec() throws Exception { return  pOverRec.isNull(); } 
  public void setNullToOverRec() throws Exception {  pOverRec.setNull(); } 
  public void setDif(double zValue) throws Exception {    pDif.setValue(zValue);  }
  public double getDif() throws Exception {     return pDif.getValue();  }
  public boolean isNullDif() throws Exception { return  pDif.isNull(); } 
  public void setNullToDif() throws Exception {  pDif.setNull(); } 
  public void setObservaciones(String zValue) throws Exception {    pObservaciones.setValue(zValue);  }
  public String getObservaciones() throws Exception {     return pObservaciones.getValue();  }
  public boolean isNullObservaciones() throws Exception { return  pObservaciones.isNull(); } 
  public void setNullToObservaciones() throws Exception {  pObservaciones.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizOver() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idpdf", pIdpdf );
    this.addItem( "linea", pLinea );
    this.addItem( "status", pStatus );
    this.addItem( "boleto", pBoleto );
    this.addItem( "fecha", pFecha );
    this.addItem( "id_aerolinea", pIdaerolinea );
    this.addItem( "over_ped", pOverPed );
    this.addItem( "over_rec", pOverRec );
    this.addItem( "over_dif", pDif );
    this.addItem( "observaciones", pObservaciones );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idpdf", "Idpdf", true, true, 300 );
    this.addFixedItem( KEY, "linea", "Linea", true, true, 18 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "status", "Status", true, false, 50 );
    this.addFixedItem( FIELD, "boleto", "Boleto", true, false, 50 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 0 );
    this.addFixedItem( FIELD, "id_aerolinea", "Aerolinea", true, false, 50 );
    this.addFixedItem( FIELD, "over_ped", "Over pedido", true, false, 18,2 );
    this.addFixedItem( FIELD, "over_rec", "Over recibido", true, false, 18,2 );
    this.addFixedItem( FIELD, "over_dif", "Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "observaciones", "Observaciones", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_ANALISISOVER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zIdpdf, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idpdf",  zIdpdf ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 
  
	@Override
	public void processInsert() throws Exception {
		super.insert();
	}

}
