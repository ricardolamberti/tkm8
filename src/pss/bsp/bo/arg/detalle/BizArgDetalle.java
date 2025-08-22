package  pss.bsp.bo.arg.detalle;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizArgDetalle extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdinterfaz = new JLong();
  private JLong pLinea = new JLong();
  private JString pTipoRuta = new JString();
  private JString pAerolinea = new JString();
  private JString pOperacion = new JString();
  private JString pBoleto = new JString();
  private JDate pFecha =  new JDate();
  private JFloat pTarifa = new JFloat();
  private JFloat pContado = new JFloat();
  private JFloat pTarjeta = new JFloat();
  private JFloat pBaseImponible = new JFloat();
  private JFloat pImpuesto1 = new JFloat();
  private JFloat pImpuesto2 = new JFloat();
  private JFloat pComision = new JFloat();
  private JFloat pImpSobrecom = new JFloat();
  private JFloat pComisionOver = new JFloat();
  private JString pObservaciones = new JString();
  private JString pNumeroTarjeta = new JString();
  private JString pTipoTarjeta = new JString();


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
  public void setIdinterfaz(long zValue) throws Exception {    pIdinterfaz.setValue(zValue);  }
  public long getIdinterfaz() throws Exception {     return pIdinterfaz.getValue();  }
  public boolean isNullIdinterfaz() throws Exception { return  pIdinterfaz.isNull(); } 
  public void setNullToIdinterfaz() throws Exception {  pIdinterfaz.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setTipoRuta(String zValue) throws Exception {    pTipoRuta.setValue(zValue);  }
  public String getTipoRuta() throws Exception {     return pTipoRuta.getValue();  }
  public boolean isNullTipoRuta() throws Exception { return  pTipoRuta.isNull(); } 
  public void setNullToTipoRuta() throws Exception {  pTipoRuta.setNull(); } 
  public void setAerolinea(String zValue) throws Exception {    pAerolinea.setValue(zValue);  }
  public String getAerolinea() throws Exception {     return pAerolinea.getValue();  }
  public boolean isNullAerolinea() throws Exception { return  pAerolinea.isNull(); } 
  public void setNullToAerolinea() throws Exception {  pAerolinea.setNull(); } 
  public void setOperacion(String zValue) throws Exception {    pOperacion.setValue(zValue);  }
  public String getOperacion() throws Exception {     return pOperacion.getValue();  }
  public boolean isNullOperacion() throws Exception { return  pOperacion.isNull(); } 
  public void setNullToOperacion() throws Exception {  pOperacion.setNull(); } 
  public void setBoleto(String zValue) throws Exception {    pBoleto.setValue(zValue);  }
  public String getBoleto() throws Exception {     return pBoleto.getValue();  }
  public boolean isNullBoleto() throws Exception { return  pBoleto.isNull(); } 
  public void setNullToBoleto() throws Exception {  pBoleto.setNull(); } 
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public boolean isNullFecha() throws Exception { return  pFecha.isNull(); } 
  public void setNullToFecha() throws Exception {  pFecha.setNull(); } 
  public void setTarifa(double zValue) throws Exception {    pTarifa.setValue(zValue);  }
  public double getTarifa() throws Exception {     return pTarifa.getValue();  }
  public boolean isNullTarifa() throws Exception { return  pTarifa.isNull(); } 
  public void setNullToTarifa() throws Exception {  pTarifa.setNull(); } 
  public void setContado(double zValue) throws Exception {    pContado.setValue(zValue);  }
  public double getContado() throws Exception {     return pContado.getValue();  }
  public boolean isNullContado() throws Exception { return  pContado.isNull(); } 
  public void setNullToContado() throws Exception {  pContado.setNull(); } 
  public void setTarjeta(double zValue) throws Exception {    pTarjeta.setValue(zValue);  }
  public double getTarjeta() throws Exception {     return pTarjeta.getValue();  }
  public boolean isNullTarjeta() throws Exception { return  pTarjeta.isNull(); } 
  public void setNullToTarjeta() throws Exception {  pTarjeta.setNull(); } 
  public void setBaseImponible(double zValue) throws Exception {    pBaseImponible.setValue(zValue);  }
  public double getBaseImponible() throws Exception {     return pBaseImponible.getValue();  }
  public boolean isNullBaseImponible() throws Exception { return  pBaseImponible.isNull(); } 
  public void setNullToBaseImponible() throws Exception {  pBaseImponible.setNull(); } 
  public void setImpuesto1(double zValue) throws Exception {    pImpuesto1.setValue(zValue);  }
  public double getImpuesto1() throws Exception {     return pImpuesto1.getValue();  }
  public boolean isNullImpuesto1() throws Exception { return  pImpuesto1.isNull(); } 
  public void setNullToImpuesto1() throws Exception {  pImpuesto1.setNull(); } 
  public void setImpuesto2(double zValue) throws Exception {    pImpuesto2.setValue(zValue);  }
  public double getImpuesto2() throws Exception {     return pImpuesto2.getValue();  }
  public boolean isNullImpuesto2() throws Exception { return  pImpuesto2.isNull(); } 
  public void setNullToImpuesto2() throws Exception {  pImpuesto2.setNull(); } 
  public void setComision(double zValue) throws Exception {    pComision.setValue(zValue);  }
  public double getComision() throws Exception {     return pComision.getValue();  }
  public boolean isNullComision() throws Exception { return  pComision.isNull(); } 
  public void setNullToComision() throws Exception {  pComision.setNull(); } 
  public void setImpSobrecom(double zValue) throws Exception {    pImpSobrecom.setValue(zValue);  }
  public double getImpSobrecom() throws Exception {     return pImpSobrecom.getValue();  }
  public boolean isNullImpSobrecom() throws Exception { return  pImpSobrecom.isNull(); } 
  public void setNullToImpSobrecom() throws Exception {  pImpSobrecom.setNull(); } 
  public void setComisionOver(double zValue) throws Exception {    pComisionOver.setValue(zValue);  }
  public double getComisionOver() throws Exception {     return pComisionOver.getValue();  }
  public boolean isNullComisionOver() throws Exception { return  pComisionOver.isNull(); } 
  public void setNullToComisionOver() throws Exception {  pComisionOver.setNull(); } 
  public void setObservaciones(String zValue) throws Exception {    pObservaciones.setValue(zValue);  }
  public String getObservaciones() throws Exception {     return pObservaciones.getValue();  }
  public boolean isNullObservaciones() throws Exception { return  pObservaciones.isNull(); } 
  public void setNullToObservaciones() throws Exception {  pObservaciones.setNull(); } 
  public void setNumeroTarjeta(String zValue) throws Exception {    pNumeroTarjeta.setValue(zValue);  }
  public String getNumeroTarjeta() throws Exception {     return pNumeroTarjeta.getValue();  }
  public boolean isNullNumeroTarjeta() throws Exception { return  pNumeroTarjeta.isNull(); } 
  public void setNullToNumeroTarjeta() throws Exception {  pNumeroTarjeta.setNull(); } 
  public void setTipoTarjeta(String zValue) throws Exception {    pTipoTarjeta.setValue(zValue);  }
  public String getTipoTarjeta() throws Exception {     return pTipoTarjeta.getValue();  }
  public boolean isNullTipoTarjeta() throws Exception { return  pTipoTarjeta.isNull(); } 
  public void setNullToTipoTarjeta() throws Exception {  pTipoTarjeta.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizArgDetalle() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idInterfaz", pIdinterfaz );
    this.addItem( "linea", pLinea );
    this.addItem( "tipo_ruta", pTipoRuta );
    this.addItem( "aerolinea", pAerolinea );
    this.addItem( "operacion", pOperacion );
    this.addItem( "boleto", pBoleto );
    this.addItem( "fecha", pFecha );
    this.addItem( "tarifa", pTarifa );
    this.addItem( "contado", pContado );
    this.addItem( "tarjeta", pTarjeta );
    this.addItem( "base_imponible", pBaseImponible );
    this.addItem( "impuesto1", pImpuesto1 );
    this.addItem( "impuesto2", pImpuesto2 );
    this.addItem( "comision", pComision );
    this.addItem( "imp_sobre_com", pImpSobrecom );
    this.addItem( "comision_over", pComisionOver );
    this.addItem( "observaciones", pObservaciones );
    this.addItem( "numero_tarjeta", pNumeroTarjeta );
    this.addItem( "tipo_tarjeta", pTipoTarjeta );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idInterfaz", "Idinterfaz", true, true, 18 );
    this.addFixedItem( KEY, "linea", "Linea", true, true, 18 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "tipo_ruta", "Tipo ruta", true, true, 50 );
    this.addFixedItem( FIELD, "aerolinea", "Aerolinea", true, true, 50 );
    this.addFixedItem( FIELD, "operacion", "Operacion", true, true, 50 );
    this.addFixedItem( FIELD, "boleto", "Boleto", true, true, 50 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 0 );
    this.addFixedItem( FIELD, "tarifa", "Tarifa", true, true, 18,2 );
    this.addFixedItem( FIELD, "contado", "Contado", true, true, 18,2 );
    this.addFixedItem( FIELD, "tarjeta", "Tarjeta", true, true, 18,2 );
    this.addFixedItem( FIELD, "base_imponible", "Base imponible", true, true, 18,2 );
    this.addFixedItem( FIELD, "impuesto1", "Impuesto1", true, true, 18,2 );
    this.addFixedItem( FIELD, "impuesto2", "Impuesto2", true, true, 18,2 );
    this.addFixedItem( FIELD, "comision", "Comision", true, true, 18,2 );
    this.addFixedItem( FIELD, "imp_sobre_com", "Imp sobre com", true, true, 18,2 );
    this.addFixedItem( FIELD, "comision_over", "Comision over", true, true, 18,2 );
    this.addFixedItem( FIELD, "observaciones", "Observaciones", true, true, 250 );
    this.addFixedItem( FIELD, "numero_tarjeta", "Numero tarjeta", true, true, 50 );
    this.addFixedItem( FIELD, "tipo_tarjeta", "Tipo tarjeta", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_INTERFAZ_BO_ARG_DETALLE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdinterfaz, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idInterfaz",  zIdinterfaz ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 

	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

}
