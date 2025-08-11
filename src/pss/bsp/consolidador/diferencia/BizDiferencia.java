package pss.bsp.consolidador.diferencia;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDiferencia extends JRecord {

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
  private JFloat pContadoBsp = new JFloat();
  private JFloat pCreditoBsp = new JFloat();
  private JFloat pComisionBsp = new JFloat();
  private JFloat pImpuestoBsp = new JFloat();
  private JFloat pTarifaBo = new JFloat();
  private JFloat pContadoBo = new JFloat();
  private JFloat pCreditoBo = new JFloat();
  private JFloat pComisionBo = new JFloat();
  private JFloat pImpuestoBo = new JFloat();
  private JFloat pTarifaDif = new JFloat();
  private JFloat pContadoDif = new JFloat();
  private JFloat pCreditoDif = new JFloat();
  private JFloat pComisionDif = new JFloat();
  private JFloat pImpuestoDif = new JFloat();
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
  public void setContadoBsp(double zValue) throws Exception {    pContadoBsp.setValue(zValue);  }
  public double getContadoBsp() throws Exception {     return pContadoBsp.getValue();  }
  public boolean isNullContadoBsp() throws Exception { return  pContadoBsp.isNull(); } 
  public void setNullToContadoBsp() throws Exception {  pContadoBsp.setNull(); } 
  public void setCreditoBsp(double zValue) throws Exception {    pCreditoBsp.setValue(zValue);  }
  public double getCreditoBsp() throws Exception {     return pCreditoBsp.getValue();  }
  public boolean isNullCreditoBsp() throws Exception { return  pCreditoBsp.isNull(); } 
  public void setNullToCreditoBsp() throws Exception {  pCreditoBsp.setNull(); } 
  public void setComisionBsp(double zValue) throws Exception {    pComisionBsp.setValue(zValue);  }
  public double getComisionBsp() throws Exception {     return pComisionBsp.getValue();  }
  public boolean isNullComisionBsp() throws Exception { return  pComisionBsp.isNull(); } 
  public void setNullToComisionBsp() throws Exception {  pComisionBsp.setNull(); } 
  public void setImpuestoBsp(double zValue) throws Exception {    pImpuestoBsp.setValue(zValue);  }
  public double getImpuestoBsp() throws Exception {     return pImpuestoBsp.getValue();  }
  public boolean isNullImpuestoBsp() throws Exception { return  pImpuestoBsp.isNull(); } 
  public void setNullToImpuestoBsp() throws Exception {  pImpuestoBsp.setNull(); } 
  public void setTarifaBo(double zValue) throws Exception {    pTarifaBo.setValue(zValue);  }
  public double getTarifaBo() throws Exception {     return pTarifaBo.getValue();  }
  public boolean isNullTarifaBo() throws Exception { return  pTarifaBo.isNull(); } 
  public void setNullToTarifaBo() throws Exception {  pTarifaBo.setNull(); } 
  public void setContadoBo(double zValue) throws Exception {    pContadoBo.setValue(zValue);  }
  public double getContadoBo() throws Exception {     return pContadoBo.getValue();  }
  public boolean isNullContadoBo() throws Exception { return  pContadoBo.isNull(); } 
  public void setNullToContadoBo() throws Exception {  pContadoBo.setNull(); } 
  public void setCreditoBo(double zValue) throws Exception {    pCreditoBo.setValue(zValue);  }
  public double getCreditoBo() throws Exception {     return pCreditoBo.getValue();  }
  public boolean isNullCreditoBo() throws Exception { return  pCreditoBo.isNull(); } 
  public void setNullToCreditoBo() throws Exception {  pCreditoBo.setNull(); } 
  public void setComisionBo(double zValue) throws Exception {    pComisionBo.setValue(zValue);  }
  public double getComisionBo() throws Exception {     return pComisionBo.getValue();  }
  public boolean isNullComisionBo() throws Exception { return  pComisionBo.isNull(); } 
  public void setNullToComisionBo() throws Exception {  pComisionBo.setNull(); } 
  public void setImpuestoBo(double zValue) throws Exception {    pImpuestoBo.setValue(zValue);  }
  public double getImpuestoBo() throws Exception {     return pImpuestoBo.getValue();  }
  public boolean isNullImpuestoBo() throws Exception { return  pImpuestoBo.isNull(); } 
  public void setNullToImpuestoBo() throws Exception {  pImpuestoBo.setNull(); } 
  public void setTarifaDif(double zValue) throws Exception {    pTarifaDif.setValue(zValue);  }
  public double getTarifaDif() throws Exception {     return pTarifaDif.getValue();  }
  public boolean isNullTarifaDif() throws Exception { return  pTarifaDif.isNull(); } 
  public void setNullToTarifaDif() throws Exception {  pTarifaDif.setNull(); } 
  public void setContadoDif(double zValue) throws Exception {    pContadoDif.setValue(zValue);  }
  public double getContadoDif() throws Exception {     return pContadoDif.getValue();  }
  public boolean isNullContadoDif() throws Exception { return  pContadoDif.isNull(); } 
  public void setNullToContadoDif() throws Exception {  pContadoDif.setNull(); } 
  public void setCreditoDif(double zValue) throws Exception {    pCreditoDif.setValue(zValue);  }
  public double getCreditoDif() throws Exception {     return pCreditoDif.getValue();  }
  public boolean isNullCreditoDif() throws Exception { return  pCreditoDif.isNull(); } 
  public void setNullToCreditoDif() throws Exception {  pCreditoDif.setNull(); } 
  public void setComisionDif(double zValue) throws Exception {    pComisionDif.setValue(zValue);  }
  public double getComisionDif() throws Exception {     return pComisionDif.getValue();  }
  public boolean isNullComisionDif() throws Exception { return  pComisionDif.isNull(); } 
  public void setNullToComisionDif() throws Exception {  pComisionDif.setNull(); } 
  public void setImpuestoDif(double zValue) throws Exception {    pImpuestoDif.setValue(zValue);  }
  public double getImpuestoDif() throws Exception {     return pImpuestoDif.getValue();  }
  public boolean isNullImpuestoDif() throws Exception { return  pImpuestoDif.isNull(); } 
  public void setNullToImpuestoDif() throws Exception {  pImpuestoDif.setNull(); } 
  public void setObservacion(String zValue) throws Exception {    pObservacion.setValue(zValue);  }
  public String getObservacion() throws Exception {     return pObservacion.getValue();  }
  public boolean isNullObservacion() throws Exception { return  pObservacion.isNull(); } 
  public void setNullToObservacion() throws Exception {  pObservacion.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizDiferencia() throws Exception {
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
    this.addItem( "contado_bsp", pContadoBsp );
    this.addItem( "credito_bsp", pCreditoBsp );
    this.addItem( "comision_bsp", pComisionBsp );
    this.addItem( "impuesto_bsp", pImpuestoBsp );
    this.addItem( "tarifa_bo", pTarifaBo );
    this.addItem( "contado_bo", pContadoBo );
    this.addItem( "credito_bo", pCreditoBo );
    this.addItem( "comision_bo", pComisionBo );
    this.addItem( "impuesto_bo", pImpuestoBo );
    this.addItem( "tarifa_dif", pTarifaDif );
    this.addItem( "contado_dif", pContadoDif );
    this.addItem( "credito_dif", pCreditoDif );
    this.addItem( "comision_dif", pComisionDif );
    this.addItem( "impuesto_dif", pImpuestoDif );
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
    this.addFixedItem( FIELD, "contado_bsp", "Contado BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "credito_bsp", "Credito BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_bsp", "Comision BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "impuesto_bsp", "Impuesto BSP", true, false, 18,2 );
    this.addFixedItem( FIELD, "tarifa_bo", "Tarifa BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "contado_bo", "Contado BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "credito_bo", "Credito BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_bo", "Comision BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "impuesto_bo", "Impuesto BO", true, false, 18,2 );
    this.addFixedItem( FIELD, "tarifa_dif", "Tarifa Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "contado_dif", "Contado Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "credito_dif", "Credito Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "comision_dif", "Comision Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "impuesto_dif", "Impuesto Diferencia", true, false, 18,2 );
    this.addFixedItem( FIELD, "observacion", "Observacion", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_DIFERENCIA"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany,String zIdpdf, long zLinea ) throws Exception { 
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
