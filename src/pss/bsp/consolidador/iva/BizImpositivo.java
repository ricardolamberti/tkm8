package pss.bsp.consolidador.iva;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizImpositivo extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JString pIdpdf = new JString();
  private JLong pLinea = new JLong();
  private JString pStatus = new JString();
  private JString pBoleto = new JString();
  private JString pOperacion = new JString();
  private JString pTipoRuta = new JString();
  private JString pIdaerolinea = new JString();
  private JFloat pTarifaBsp = new JFloat();
  private JFloat pIvaBsp = new JFloat();
  private JFloat pIvacomisionBsp = new JFloat();
  private JFloat pComisionBsp = new JFloat();
  private JFloat pTarifaBo = new JFloat();
  private JFloat pIvaBo = new JFloat();
  private JFloat pIvacomisionBo = new JFloat();
  private JFloat pComisionBo = new JFloat();
  private JFloat pTarifaDif = new JFloat();
  private JFloat pIvaDif = new JFloat();
  private JFloat pIvacomisionDif = new JFloat();
  private JFloat pComisionDif = new JFloat();
  private JString pObservacion = new JString();


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
  public void setOperacion(String zValue) throws Exception {    pOperacion.setValue(zValue);  }
  public String getOperacion() throws Exception {     return pOperacion.getValue();  }
  public boolean isNullOperacion() throws Exception { return  pOperacion.isNull(); } 
  public void setNullToOperacion() throws Exception {  pOperacion.setNull(); } 
  public void setTipoRuta(String zValue) throws Exception {    pTipoRuta.setValue(zValue);  }
  public String getTipoRuta() throws Exception {     return pTipoRuta.getValue();  }
  public boolean isNullTipoRuta() throws Exception { return  pTipoRuta.isNull(); } 
  public void setNullToTipoRuta() throws Exception {  pTipoRuta.setNull(); } 
  public void setIdaerolinea(String zValue) throws Exception {    pIdaerolinea.setValue(zValue);  }
  public String getIdaerolinea() throws Exception {     return pIdaerolinea.getValue();  }
  public boolean isNullIdaerolinea() throws Exception { return  pIdaerolinea.isNull(); } 
  public void setNullToIdaerolinea() throws Exception {  pIdaerolinea.setNull(); } 
  public void setTarifaBsp(double zValue) throws Exception {    pTarifaBsp.setValue(zValue);  }
  public double getTarifaBsp() throws Exception {     return pTarifaBsp.getValue();  }
  public boolean isNullTarifaBsp() throws Exception { return  pTarifaBsp.isNull(); } 
  public void setNullToTarifaBsp() throws Exception {  pTarifaBsp.setNull(); } 
  public void setIvaBsp(double zValue) throws Exception {    pIvaBsp.setValue(zValue);  }
  public double getIvaBsp() throws Exception {     return pIvaBsp.getValue();  }
  public boolean isNullIvaBsp() throws Exception { return  pIvaBsp.isNull(); } 
  public void setNullToIvaBsp() throws Exception {  pIvaBsp.setNull(); } 
  public void setIvacomisionBsp(double zValue) throws Exception {    pIvacomisionBsp.setValue(zValue);  }
  public double getIvacomisionBsp() throws Exception {     return pIvacomisionBsp.getValue();  }
  public boolean isNullIvacomisionBsp() throws Exception { return  pIvacomisionBsp.isNull(); } 
  public void setNullToIvacomisionBsp() throws Exception {  pIvacomisionBsp.setNull(); } 
  public void setComisionBsp(double zValue) throws Exception {    pComisionBsp.setValue(zValue);  }
  public double getComisionBsp() throws Exception {     return pComisionBsp.getValue();  }
  public boolean isNullComisionBsp() throws Exception { return  pComisionBsp.isNull(); } 
  public void setNullToComisionBsp() throws Exception {  pComisionBsp.setNull(); } 
  public void setTarifaBo(double zValue) throws Exception {    pTarifaBo.setValue(zValue);  }
  public double getTarifaBo() throws Exception {     return pTarifaBo.getValue();  }
  public boolean isNullTarifaBo() throws Exception { return  pTarifaBo.isNull(); } 
  public void setNullToTarifaBo() throws Exception {  pTarifaBo.setNull(); } 
  public void setIvaBo(double zValue) throws Exception {    pIvaBo.setValue(zValue);  }
  public double getIvaBo() throws Exception {     return pIvaBo.getValue();  }
  public boolean isNullIvaBo() throws Exception { return  pIvaBo.isNull(); } 
  public void setNullToIvaBo() throws Exception {  pIvaBo.setNull(); } 
  public void setIvacomisionBo(double zValue) throws Exception {    pIvacomisionBo.setValue(zValue);  }
  public double getIvacomisionBo() throws Exception {     return pIvacomisionBo.getValue();  }
  public boolean isNullIvacomisionBo() throws Exception { return  pIvacomisionBo.isNull(); } 
  public void setNullToIvacomisionBo() throws Exception {  pIvacomisionBo.setNull(); } 
  public void setComisionBo(double zValue) throws Exception {    pComisionBo.setValue(zValue);  }
  public double getComisionBo() throws Exception {     return pComisionBo.getValue();  }
  public boolean isNullComisionBo() throws Exception { return  pComisionBo.isNull(); } 
  public void setNullToComisionBo() throws Exception {  pComisionBo.setNull(); } 
  public void setTarifaDif(double zValue) throws Exception {    pTarifaDif.setValue(zValue);  }
  public double getTarifaDif() throws Exception {     return pTarifaDif.getValue();  }
  public boolean isNullTarifaDif() throws Exception { return  pTarifaDif.isNull(); } 
  public void setNullToTarifaDif() throws Exception {  pTarifaDif.setNull(); } 
  public void setIvaDif(double zValue) throws Exception {    pIvaDif.setValue(zValue);  }
  public double getIvaDif() throws Exception {     return pIvaDif.getValue();  }
  public boolean isNullIvaDif() throws Exception { return  pIvaDif.isNull(); } 
  public void setNullToIvaDif() throws Exception {  pIvaDif.setNull(); } 
  public void setIvacomisionDif(double zValue) throws Exception {    pIvacomisionDif.setValue(zValue);  }
  public double getIvacomisionDif() throws Exception {     return pIvacomisionDif.getValue();  }
  public boolean isNullIvacomisionDif() throws Exception { return  pIvacomisionDif.isNull(); } 
  public void setNullToIvacomisionDif() throws Exception {  pIvacomisionDif.setNull(); } 
  public void setComisionDif(double zValue) throws Exception {    pComisionDif.setValue(zValue);  }
  public double getComisionDif() throws Exception {     return pComisionDif.getValue();  }
  public boolean isNullComisionDif() throws Exception { return  pComisionDif.isNull(); } 
  public void setNullToComisionDif() throws Exception {  pComisionDif.setNull(); } 
  public void setObservacion(String zValue) throws Exception {    pObservacion.setValue(zValue);  }
  public String getObservacion() throws Exception {     return pObservacion.getValue();  }
  public boolean isNullObservacion() throws Exception { return  pObservacion.isNull(); } 
  public void setNullToObservacion() throws Exception {  pObservacion.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizImpositivo() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idpdf", pIdpdf );
    this.addItem( "linea", pLinea );
    this.addItem( "status", pStatus );
    this.addItem( "boleto", pBoleto );
    this.addItem( "operacion", pOperacion );
    this.addItem( "tipo_ruta", pTipoRuta );
    this.addItem( "id_aerolinea", pIdaerolinea );
    this.addItem( "tarifa_bsp", pTarifaBsp );
    this.addItem( "iva_bsp", pIvaBsp );
    this.addItem( "ivacomision_bsp", pIvacomisionBsp );
    this.addItem( "comision_bsp", pComisionBsp );
    this.addItem( "tarifa_bo", pTarifaBo );
    this.addItem( "iva_bo", pIvaBo );
    this.addItem( "ivacomision_bo", pIvacomisionBo );
    this.addItem( "comision_bo", pComisionBo );
    this.addItem( "tarifa_dif", pTarifaDif );
    this.addItem( "iva_dif", pIvaDif );
    this.addItem( "ivacomision_dif", pIvacomisionDif );
    this.addItem( "comision_dif", pComisionDif );
    this.addItem( "observacion", pObservacion );
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
    this.addFixedItem( FIELD, "operacion", "Operacion", true, false, 50 );
    this.addFixedItem( FIELD, "tipo_ruta", "Tipo ruta", true, false, 50 );
    this.addFixedItem( FIELD, "id_aerolinea", "Aerolinea", true, false, 50 );
    this.addFixedItem( FIELD, "tarifa_bsp", "Tarifa BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "iva_bsp", "Iva bsp", true, false, 18,2 );
    this.addFixedItem( FIELD, "ivacomision_bsp", "Iva Comision BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_bsp", "Comision BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "tarifa_bo", "Tarifa BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "iva_bo", "Iva BO", true, true, 18,2 );
    this.addFixedItem( FIELD, "ivacomision_bo", "Iva Comision BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_bo", "Comision BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "tarifa_dif", "Tarifa Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "iva_dif", "Iva Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "ivacomision_dif", "Ivacomision Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_dif", "Comision Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "observacion", "Observacion", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_IVA"; }


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
  
  public String getFieldForeground(String zColName) throws Exception {
  	if (zColName.toLowerCase().indexOf("dif")==-1) return null;
  	String valor = getPropAsString(zColName);
  	if (Double.parseDouble(valor)!=0) return "FF0000";
  	return "000000";
  }
	@Override
	public void processInsert() throws Exception {
		super.insert();
	}

}
