package pss.common.regions.currency.history;

import java.util.Calendar;
import java.util.Date;

import pss.common.regions.currency.conversion.BizMonedaConver;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
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
  		pCotizDolar.setValue(  "<a href=\"http://www.dolarsi.com\" target=\"_blank\">" +
		"<img src=\"http://www.dolarsi.com/cotizador/cotizador_blanco_full.asp\"></a>");
  	}
  };
  private JCurrency pCotizVenta = new JCurrency() {
  	@Override
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  };
  private JCurrency pCotizCompra = new JCurrency() {
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  };
  private JCurrency pCotizContab = new JCurrency(true);
  private JString pUsuario = new JString();
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

  public boolean Read(String company,Date fecha ) throws Exception {
    addFilter( "company",  company );
    addFilter( "fecha_hora",  JDateTools.getDateStartDay(fecha), ">=" );
    addFilter( "fecha_hora",  JDateTools.getDateEndDay(fecha), "<=" );
    return read();
  }
 	public void processInsertSimple()throws Exception{

     if(this.pCotizVenta.getValue()<=0)
       JExcepcion.SendError("El valor de la tasa de Venta debe ser mayor a cero");
     if(this.pCotizCompra.getValue()<=0)
       JExcepcion.SendError("El valor de la tasa de Compra debe ser mayor a cero");

     if ( pUsuario.isNull()) pUsuario.setValue(BizUsuario.getCurrentUser());

     // fecha y hora de proceso
     if (this.pFechaHora.isNull())
     	this.pFechaHora.setValue(BizUsuario.getUsr().todayGMT());


     super.processInsert();
     pCotizacionId.setValue(this.getIdentity("cotizacion_id"));
     
     
   }
  @Override
	public void processInsert()throws Exception{

    if(this.pCotizVenta.getValue()<=0)
      JExcepcion.SendError("El valor de la tasa de Venta debe ser mayor a cero");
    if(this.pCotizCompra.getValue()<=0)
      JExcepcion.SendError("El valor de la tasa de Compra debe ser mayor a cero");

    if ( pUsuario.isNull()) pUsuario.setValue(BizUsuario.getCurrentUser());

    // fecha y hora de proceso
    if (this.pFechaHora.isNull())
    	this.pFechaHora.setValue(BizUsuario.getUsr().todayGMT());


    super.processInsert();
    pCotizacionId.setValue(this.getIdentity("cotizacion_id"));
    
    BizMonedaConver conver = BizMonedaConver.findConver(this.pMonedaSource.getValue(), this.pMonedaTarget.getValue());
    conver.setCompany(this.getCompany());
    conver.setPais(this.getPais());
    conver.setCotizCompra(this.getCotizCompra());
    conver.setCotizVenta(this.getCotizVenta());
    conver.setCotizContab(this.getCotizContab());
    conver.update();
    
  }
  
  public BizMonedaConver getObjMonedaConver() throws Exception {
  	if (this.monedaConver!=null) return this.monedaConver;
    BizMonedaConver conver = new BizMonedaConver();
    conver.Read(this.pCompany.getValue(), this.pPais.getValue(), this.pMonedaSource.getValue(), this.pMonedaTarget.getValue());
  	return (this.monedaConver=conver);
  }


//
//  public static BizMonedaCotizacion readCotizacionCorriente(String company, String zPais, String zMoneda ) throws Exception {
//    return readCotizacionCorrienteAt(company, zPais, zMoneda, new Date());
//  }
  static JMap<String,CotizacionHistorico> cotizacionCache;
  static Date fechaCache;
  
  
  public static synchronized JMap<String,CotizacionHistorico> getCotizacionCache() throws Exception {
  	if (fechaCache!=null && !JDateTools.getDateStartDay(fechaCache).equals(JDateTools.getDateStartDay(new Date()))) {
  		cotizacionCache=null;
  	}
  	if (cotizacionCache!=null) return cotizacionCache;
  	JMap<String,CotizacionHistorico>cache=JCollectionFactory.createMap();
  	
    String sql =" select min(cotizacion_id) as cotizacion_id,min(usuario) as usuario,company,pais,moneda_target,moneda_source,MIN(fecha_hora) as fecha_hora,MIN(cotiz_contab) as cotiz_contab,MIN(cotiz_compra)as cotiz_compra,MIN(cotiz_venta)as cotiz_venta"
  	+" from MON_COTIZACION "
  	+" group by company,pais,moneda_target,moneda_source"
  	+" order by company,pais,moneda_target,moneda_source";
  	
		JRecords<BizMonedaCotizacion> regs=new JRecords<BizMonedaCotizacion>(BizMonedaCotizacion.class);
		regs.SetSQL(sql);
		JIterator<BizMonedaCotizacion> it=regs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizMonedaCotizacion rec = it.nextElement();
			CotizacionHistorico cot =new CotizacionHistorico();
			if (rec.getCompany().equals("CTS")&&rec.pMonedaSource.getValue().equals("INR")&&rec.pMonedaTarget.getValue().equals("MXN"))
				PssLogger.logDebug("INR");
			cache.addElement(rec.getCompany()+"_"+rec.getPais()+"_"+rec.getMonedaSource()+"_"+rec.getMonedaTarget(), cot);
	    String sqld =" select min(cotizacion_id) as cotizacion_id,min(usuario) as usuario,company,pais,moneda_target,moneda_source,fecha_hora,AVG(cotiz_contab) as cotiz_contab,AVG(cotiz_compra)as cotiz_compra,AVG(cotiz_venta)as cotiz_venta "
	    							+" from MON_COTIZACION "
	    							+" where company='"+rec.getCompany()+"' and pais ='"+rec.getPais()+"' and moneda_target ='"+rec.getMonedaTarget()+"' and moneda_source ='"+rec.getMonedaSource()+"'"
	    							+" group by company,pais,moneda_target,moneda_source,fecha_hora"
	    							+" order by company,pais,moneda_target,moneda_source,fecha_hora";
	      	
  		JRecords<BizMonedaCotizacion> regsD=new JRecords<BizMonedaCotizacion>(BizMonedaCotizacion.class);
  		regsD.SetSQL(sqld);
  		double valInicContab= rec.getCotizContab();
  		double valInicCompra= rec.getCotizCompra();
  		double valInicVenta= rec.getCotizVenta();
			double valHastaContab = rec.getCotizContab();
			double valHastaCompra = rec.getCotizCompra();
			double valHastaVenta = rec.getCotizVenta();
  		Calendar cal = Calendar.getInstance();
  		cal.setTime( JDateTools.getDateStartDay(rec.getFechaHora()) );
  		cot.fechaIni=cal.getTime();
  		cot.history=JCollectionFactory.createMap();
  		cot.cotizIni=new Cotizacion(valInicContab,valInicCompra,valInicVenta);
  		JIterator<BizMonedaCotizacion> itD=regsD.getStaticIterator();
  		while (itD.hasMoreElements()) {
  			BizMonedaCotizacion recD = itD.nextElement();
  			Calendar fechaHasta = Calendar.getInstance();
  			fechaHasta.setTime(recD.getFechaHora());
  			
  			valHastaContab = recD.getCotizContab();
  			valHastaCompra = recD.getCotizCompra();
  			valHastaVenta = recD.getCotizVenta();
  			
  			double dias = JDateTools.getDaysBetween(rec.getFechaHora(), recD.getFechaHora());
  			if (dias!=0) {
  				double pasoContab = (valHastaContab-valInicContab)/dias;
  				double pasoCompra = (valHastaCompra-valInicCompra)/dias;
  				double pasoVenta = (valHastaVenta-valInicVenta)/dias;
    			while (cal.before(fechaHasta)) {
    				valInicContab+=pasoContab;
    				valInicCompra+=pasoCompra;
    				valInicVenta+=pasoVenta;
    				cot.history.addElement(JDateTools.getDateStartDay(cal.getTime()), new Cotizacion(valInicContab,valInicCompra,valInicVenta));
      			cal.add(Calendar.DAY_OF_YEAR, 1);
    			}
  			}
				cot.history.addElement(JDateTools.getDateStartDay(fechaHasta.getTime()), new Cotizacion(valHastaContab,valHastaCompra,valHastaVenta));
				valInicContab=valHastaContab;
				valInicCompra=valHastaCompra;
				valInicVenta=valHastaVenta;
  			cal.add(Calendar.DAY_OF_YEAR, 1);
  			
  		}	
  		cot.fechaFin=cal.getTime();
  		cot.cotizFin=new Cotizacion(valHastaContab,valHastaCompra,valHastaVenta);
		}
		
  	fechaCache=new Date();
  	return cotizacionCache=cache;
  }
  
  public static double readCotizacionCorrienteAt(String company, String pais, String zMonedaSource, String zMonedaTarget, Date zDatetime) throws Exception {
 
    CotizacionHistorico hist= getCotizacionCache().getElement(company+"_"+pais+"_"+zMonedaSource+"_"+zMonedaTarget);
    if (hist==null) return 0;
    
    if (JDateTools.dateEqualOrBefore(zDatetime, hist.fechaIni)) return hist.cotizIni.cotizVenta;
    if (JDateTools.dateEqualOrAfter(zDatetime, hist.fechaFin)) return hist.cotizFin.cotizVenta;

    Cotizacion cot = hist.history.getElement(JDateTools.getDateStartDay(zDatetime));
    if (cot==null) {
    	for (int j=-1;j>-30;j--) {
        cot = hist.history.getElement(JDateTools.getDateStartDay(JDateTools.addDays(zDatetime,j)));
        if (cot!=null)    		
          return cot.cotizVenta;
    	}
      return 0;
    }
    return cot.cotizVenta;
  }

  
  public String getDescrCotiz() throws Exception {     
  	return  pCotizVenta.toFormattedString() + "/" + pCotizCompra.toFormattedString();  
  }
  
//  public double findCotizContab() throws Exception {
//  	if (pCotizContab.isNotNull()) return this.pCotizContab.getValue();
//  	return this.pCotizVenta.getValue();
//  }
  
//  public BizMonedaPais getObjMonedaPais() throws Exception {
//  	if (monedaPais!=null) return monedaPais;
//  	BizMonedaPais m = new BizMonedaPais();
//  	m.Read(this.getCompany(), this.getMoneda(), this.getPais());
//  	return (this.monedaPais=m);
//  }


}
