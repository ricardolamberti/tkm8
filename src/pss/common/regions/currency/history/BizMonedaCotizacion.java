package pss.common.regions.currency.history;

import java.util.Calendar;
import java.util.Date;

import pss.common.regions.currency.conversion.BizMonedaConver;
import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizMonedaCotizacion extends JRecord {
	
  private JLong pCotizacionId = new JLong();
  private JString pCompany = new JString();
  private JString pPais = new JString();
  private JString pMonedaSource = new JString();
  private JString pMonedaTarget = new JString();
  private JString pCotizDolar = new JString() {
  	public boolean forcePresetForDefault() {return true;};
  	public void preset() throws Exception {
  		setValue(findHtml());
  	}
  };
  private JCurrency pCotizVenta = new JCurrency() {
  	@Override
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  	public int getPrecision() throws Exception {
  		return 4;
  	};
  };
  private JCurrency pCotizCompra = new JCurrency() {
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  	public int getPrecision() throws Exception {
  		return 4;
  	};
  };
  private JCurrency pCotizContab = new JCurrency(true);
  private JString pUsuario = new JString();
  private JDateTime pFecha = new JDateTime();
  private JDateTime pFechaHora = new JDateTime();
  private JString pDescrCotiz = new JString() {@Override
	public void preset() throws Exception {pDescrCotiz.setValue(getDescrCotiz());}};

	BizMonedaConver monedaConver;


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setMonedaSource(String zValue) throws Exception {    pMonedaSource.setValue(zValue);  }
  public String getMonedaSource() throws Exception {     return pMonedaSource.getValue();  }
  public void setMonedaTarget(String zValue) throws Exception {    pMonedaTarget.setValue(zValue);  }
  public String getMonedaTarget() throws Exception {     return pMonedaTarget.getValue();  }
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
//  public void setDbid(String zValue) throws Exception {    pDbid.setValue(zValue);  }
//  public String getDbid() throws Exception {     return pDbid.getValue();  }
  public void setCotizVenta(double zValue) throws Exception {    pCotizVenta.setValue(zValue);  }
  public void setCotizContab(double zValue) throws Exception {    pCotizContab.setValue(zValue);  }  
  public void setFechaHora(Date zValue) throws Exception {    pFechaHora.setValue(zValue);  }
  public double getCotizVenta() throws Exception {     return pCotizVenta.getValue();  }
  public void setCotizCompra(double zValue) throws Exception {    pCotizCompra.setValue(zValue);  }
  public Date getFechaHora() throws Exception {     return pFechaHora.getValue();  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public double getCotizCompra() throws Exception {     return pCotizCompra.getValue();  }
  public double getCotizContab() throws Exception {     return pCotizContab.getValue();  }
  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public void setCotizacionId(long zValue) throws Exception {    pCotizacionId.setValue(zValue);  }
  public long getCotizacionId() throws Exception {     return pCotizacionId.getValue();  }
  
  /**
   * Constructor de la Clase
   */
  public BizMonedaCotizacion() throws Exception {
  }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Properties methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Adds the object properties
   */
  @Override
	public void createProperties() throws Exception {
    this.addItem( "cotizacion_id", pCotizacionId );
    this.addItem( "company", pCompany);
    this.addItem( "pais", pPais );
    this.addItem( "moneda_source", pMonedaSource );
    this.addItem( "moneda_target", pMonedaTarget );
    this.addItem( "cotiz_venta", pCotizVenta );
    this.addItem( "cotiz_compra", pCotizCompra );
    this.addItem( "cotiz_contab", pCotizContab);
    this.addItem( "usuario", pUsuario );
    this.addItem( "fecha_hora", pFechaHora );
    this.addItem( "fecha", pFecha );
    this.addItem( "descr_cotiz", pDescrCotiz );
    this.addItem( "cotiz_dolar", pCotizDolar );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "cotizacion_id", "Cotizacion id", false, false, 8 );
    this.addFixedItem( FIELD, "company", "Empresa", true, true, 15 );
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 2 );
    this.addFixedItem( FIELD, "moneda_source", "Moneda Origen", true, true, 15 );
    this.addFixedItem( FIELD, "moneda_target", "Moneda Destino", true, true, 15 );
    this.addFixedItem( FIELD, "cotiz_venta", "Venta", true, true, 18,6);
    this.addFixedItem( FIELD, "cotiz_compra", "Compra", true, true, 18,6);
    this.addFixedItem( FIELD, "cotiz_contab", "Contable", true, true, 18,6);
    this.addFixedItem( FIELD, "usuario", "Usuario", true, false, 15 );
    this.addFixedItem( FIELD, "fecha_hora", "Fecha/Hora", true, true, 10 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 10 );
    this.addFixedItem( VIRTUAL, "descr_cotiz", "Cotización", true, true, 30 );
    this.addFixedItem( VIRTUAL, "cotiz_dolar", "Cotización", true, false, 500 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "MON_COTIZACION"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read(long zCotizacionId ) throws Exception {
    addFilter( "cotizacion_id",  zCotizacionId );
    return read();
  }

// 	public void processInsertSimple()throws Exception{
//
//     if(this.pCotizVenta.getValue()<=0)
//       JExcepcion.SendError("El valor de la tasa de Venta debe ser mayor a cero");
//     if(this.pCotizCompra.getValue()<=0)
//       JExcepcion.SendError("El valor de la tasa de Compra debe ser mayor a cero");
//
//     if ( pUsuario.isNull()) pUsuario.setValue(BizUsuario.getCurrentUser());
//
//     // fecha y hora de proceso
//     if (this.pFechaHora.isNull())
//     	this.pFechaHora.setValue(BizUsuario.getUsr().todayGMT());
//
//
//     super.processInsert();
//     pCotizacionId.setValue(this.getIdentity("cotizacion_id"));
//     
//     
//   }
  
	public void loadDefault()throws Exception{
	  this.pCotizCompra.setValue(this.getObjMonedaConver().getCotizCompra());
	  this.pCotizVenta.setValue(this.getObjMonedaConver().getCotizVenta());
	}
	
  @Override
	public void processInsert()throws Exception{

    if(this.pCotizVenta.getValue()<=0)
      JExcepcion.SendError("El valor de la tasa de Venta debe ser mayor a cero");
    if(this.pCotizCompra.getValue()<=0)
      JExcepcion.SendError("El valor de la tasa de Compra debe ser mayor a cero");

    if ( pUsuario.isNull()) pUsuario.setValue(BizUsuario.getCurrentUser());

    if (this.isModoFecha()) {
	    this.verifyRepetido();
    }

    // fecha y hora de proceso
    if (this.pFechaHora.isNull())
    	this.pFechaHora.setValue(BizUsuario.getUsr().todayGMT());


    if (this.pCotizContab.isNull()) this.pCotizContab.setValue(0d);
    
    super.processInsert();
    pCotizacionId.setValue(this.getIdentity("cotizacion_id"));

    this.touchConver();
    
  }
  
  @Override
  public void processUpdate() throws Exception {
  	if (this.isModoHorario())
  		JExcepcion.SendError("No se puede modificar una cotización histórica");
  	super.processUpdate();
    this.touchConver();
  }

  public BizMonedaCotizacion findLast() throws Exception {
  	if (this.isModoHorario()) 
  		return this;

  	BizMonedaCotizacion max = new BizMonedaCotizacion();
		max.addFilter("company", this.getCompany());
		max.addFilter("pais", this.getPais());
		max.addFilter("moneda_source", this.getMonedaSource());
		max.addFilter("moneda_target", this.getMonedaTarget());
		Date maxDate = max.SelectMaxDate("fecha");
  	max = new BizMonedaCotizacion();
		max.addFilter("company", this.getCompany());
		max.addFilter("pais", this.getPais());
		max.addFilter("moneda_source", this.getMonedaSource());
		max.addFilter("moneda_target", this.getMonedaTarget());
		max.addFilter("fecha", maxDate);
		max.dontThrowException(true);
		if (!max.read()) return null;
  	return max;
  }

  public void verifyRepetido() throws Exception {
  	BizMonedaCotizacion m = new BizMonedaCotizacion();
		m.addFilter("company", this.getCompany());
		m.addFilter("pais", this.getPais());
		m.addFilter("moneda_source", this.getMonedaSource());
		m.addFilter("moneda_target", this.getMonedaTarget());
		m.addFilter("fecha", this.getFecha());
		m.dontThrowException(true);
		if (m.read()) 
			JExcepcion.SendError("Ya existe cotización para esa fecha");
  }

  public void touchConver() throws Exception {
  	//solo si es la ultima del historico
  	BizMonedaCotizacion last= this.findLast();
  	BizMonedaConver conver = new BizMonedaConver();
    conver.dontThrowException(true);
    conver.Read(last.getCompany(), last.getPais(), last.getMonedaSource(), last.getMonedaTarget());
    conver.setCotizCompra(last.getCotizCompra());
    conver.setCotizVenta(last.getCotizVenta());
    conver.setCotizContab(last.getCotizContab());
    conver.update();
    BizMonedaConver.clearCache(conver);
  }
  
  public boolean isModoFecha() throws Exception {
  	return this.getObjMonedaConver().isModoFecha();
  }
  public boolean isModoHorario() throws Exception {
  	return this.getObjMonedaConver().isModoHorario();
  }
  
  public BizMonedaConver getObjMonedaConver() throws Exception {
  	if (this.monedaConver!=null) return this.monedaConver;
    BizMonedaConver conver = new BizMonedaConver();
    conver.Read(this.pCompany.getValue(), this.pPais.getValue(), this.pMonedaSource.getValue(), this.pMonedaTarget.getValue());
  	return (this.monedaConver=conver);
  }

  
  public String getDescrCotiz() throws Exception {     
  	return  pCotizVenta.toFormattedString() + "/" + pCotizCompra.toFormattedString();  
  }
  

  public String findHtml() throws Exception {
  	// para argentina
  	return "<a href=\"http://www.dolarsi.com\" target=\"_blank\"> <img src=\"http://www.dolarsi.com/cotizador/cotizador_blanco_full.asp\"></a>";
  }
  
  
}
