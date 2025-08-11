package pss.bsp.consolid.model.diferenciaLiq;

import pss.bsp.consolidador.IConsolidador;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDiferenciaLiq extends JRecord {

  private JString pCompany = new JString();
  private JLong pIdLiq = new JLong();
  private JLong pLinea = new JLong();
  private JString pStatus = new JString();
  private JString pBoleto = new JString();
  private JString pOperacion = new JString();
  private JString pTipoRuta = new JString();
  private JString pIdaerolinea = new JString();
  private JString pTarifaLiq = new JString();
  private JString pContadoLiq = new JString();
  private JString pCreditoLiq = new JString();
  private JString pComisionLiq = new JString();
  private JString pImpuestoLiq = new JString();
  private JString pTotalLiq = new JString();
  private JString pFormaPagoLiq = new JString();



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
 
  public void setIdLiq(long zValue) throws Exception {    pIdLiq.setValue(zValue);  }
  public long getIdLiq() throws Exception {     return pIdLiq.getValue();  }
  public boolean isNullIdLiq() throws Exception { return  pIdLiq.isNull(); } 
  public void setNullToIdLiq() throws Exception {  pIdLiq.setNull(); } 
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
  public boolean isNullLiqleto() throws Exception { return  pBoleto.isNull(); } 
  public void setNullToLiqleto() throws Exception {  pBoleto.setNull(); } 
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
   public void setTarifa(String zValue) throws Exception {    pTarifaLiq.setValue(zValue);  }
  public String getTarifa() throws Exception {     return pTarifaLiq.getValue();  }
  public boolean isNullTarifa() throws Exception { return  pTarifaLiq.isNull(); } 
  public void setNullToTarifa() throws Exception {  pTarifaLiq.setNull(); } 
  public void setContado(String zValue) throws Exception {    pContadoLiq.setValue(zValue);  }
  public String getContado() throws Exception {     return pContadoLiq.getValue();  }
  public boolean isNullContado() throws Exception { return  pContadoLiq.isNull(); } 
  public void setNullToContado() throws Exception {  pContadoLiq.setNull(); } 
  public void setCredito(String zValue) throws Exception {    pCreditoLiq.setValue(zValue);  }
  public String getCredito() throws Exception {     return pCreditoLiq.getValue();  }
  public boolean isNullCredito() throws Exception { return  pCreditoLiq.isNull(); } 
  public void setNullToCredito() throws Exception {  pCreditoLiq.setNull(); } 
  public void setComision(String zValue) throws Exception {    pComisionLiq.setValue(zValue);  }
  public String getComision() throws Exception {     return pComisionLiq.getValue();  }
  public boolean isNullComision() throws Exception { return  pComisionLiq.isNull(); } 
  public void setNullToComision() throws Exception {  pComisionLiq.setNull(); } 
  public void setImpuesto(String zValue) throws Exception {    pImpuestoLiq.setValue(zValue);  }
  public String getImpuesto() throws Exception {     return pImpuestoLiq.getValue();  }
  public boolean isNullImpuesto() throws Exception { return  pImpuestoLiq.isNull(); } 
  public void setNullToImpuesto() throws Exception {  pImpuestoLiq.setNull(); } 
  public void setTotal(String zValue) throws Exception {    pTotalLiq.setValue(zValue);  }
  public String getTotal() throws Exception {     return pTotalLiq.getValue();  }
  public boolean isNullTotal() throws Exception { return  pTotalLiq.isNull(); } 
  public void setNullToTotal() throws Exception {  pTotalLiq.setNull(); } 

  public void setFormaPago(String zValue) throws Exception {    pFormaPagoLiq.setValue(zValue);  }
  public String getFormaPago() throws Exception {     return pFormaPagoLiq.getValue();  }
  public boolean isNullFormaPago() throws Exception { return  pFormaPagoLiq.isNull(); } 
  public void setNullToFormaPago() throws Exception {  pFormaPagoLiq.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizDiferenciaLiq() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_liq", pIdLiq );
    this.addItem( "linea", pLinea );
    this.addItem( "status", pStatus );
    this.addItem( "boleto", pBoleto );
    this.addItem( "operacion", pOperacion );
    this.addItem( "tipo_ruta", pTipoRuta );
    this.addItem( "id_aerolinea", pIdaerolinea );
    this.addItem( "tarifa", pTarifaLiq );
    this.addItem( "contado", pContadoLiq );
    this.addItem( "credito", pCreditoLiq );
    this.addItem( "comision", pComisionLiq );
    this.addItem( "impuesto", pImpuestoLiq );
    this.addItem( "total", pTotalLiq );
    this.addItem( "formapago", pFormaPagoLiq );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "id_liq", "Id liq", true, true, 64 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 64 );
    this.addFixedItem( FIELD, "status", "Status", true, false, 50 );
    this.addFixedItem( FIELD, "boleto", "Boleto", true, false, 50 );
    this.addFixedItem( FIELD, "operacion", "Operacion", true, false, 50 );
    this.addFixedItem( FIELD, "tipo_ruta", "Tipo ruta", true, false, 50 );
    this.addFixedItem( FIELD, "id_aerolinea", "Aerolinea", true, false, 50 );
    this.addFixedItem( FIELD, "tarifa", "Tarifa ", true, false, 18,2 );
    this.addFixedItem( FIELD, "contado", "Contado ", true, false, 50 );
    this.addFixedItem( FIELD, "credito", "Credito ", true, false, 50 );
    this.addFixedItem( FIELD, "comision", "Comision ", true, false, 50 );
    this.addFixedItem( FIELD, "impuesto", "Impuesto ", true, false, 50 );
    this.addFixedItem( FIELD, "total", "total ", true, false, 50 );
    this.addFixedItem( FIELD, "formapago", "Forma Pago ", true, false, 50 );
       }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_DIFERENCIA_LIQ"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany,long zIdliq, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_liq",  zIdliq ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 

  public String getFieldForeground(String zColName) throws Exception {
  	String valor = getPropAsStringNoExec(zColName);
  	if (getStatus().equals(IConsolidador.ONLY_BSP))	return "FF0000"; 
  	if (getStatus().equals(IConsolidador.ONLY_LIQ))	return "FF0000"; 
   	if (valor.startsWith("[")) return "0000FF";
  	if (valor.indexOf(" <> ")!=-1) return "FF0000";
  	return "000000";
  }
	@Override
	public void processInsert() throws Exception {
		super.insert();
	}

}
