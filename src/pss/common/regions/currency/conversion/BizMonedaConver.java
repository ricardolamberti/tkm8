package pss.common.regions.currency.conversion;

import java.util.Date;

import pss.common.regions.company.BizCompany;
import pss.common.regions.currency.BizMoneda;
import pss.common.regions.currency.BizMonedaPais;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class BizMonedaConver extends JRecord {
	
	public static final String VISION_VIEW="1";
	
  private JString pCompany = new JString();
  private JString pPais = new JString();
  private JString pMonedaSource = new JString();
  private JString pMonedaTarget = new JString();
  private JBoolean pModoFecha = new JBoolean();
  private JHtml pDescrConverHtml = new JHtml() {
  	public void preset() throws Exception {
  		setValue(getDescrConverHtml());
  	}
  };

  private JCurrency pCotizVenta = new JCurrency(true) {
  	@Override
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  	public int getPrecision() throws Exception {
  		return 4;
  	};
  };
  private JCurrency pCotizCompra = new JCurrency(true) {
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  	public int getPrecision() throws Exception {
  		return 4;
  	};
  };
  private JCurrency pFlatCotiz = new JCurrency(true) {
  	public String getCurrencyId() throws Exception {
  		return pMonedaSource.getValue();
  	}
  };
  private JCurrency pCotizContab = new JCurrency(true);
  
  
	private static JMap<String, BizMonedaConver> cache;
	private BizMonedaConver fixedSource;
	private BizMonedaConver fixedTarget;
	private BizMonedaConver fixedContab;
	private BizMonedaConver converParent;	
	private boolean bWasUsed=false;
	
	private int id=-1;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public void setMonedaSource(String zValue) throws Exception {    pMonedaSource.setValue(zValue);  }
  public String getMonedaSource() throws Exception {     return pMonedaSource.getValue();  }
  public void setMonedaTarget(String zValue) throws Exception {    pMonedaTarget.setValue(zValue);  }
  public String getMonedaTarget() throws Exception {     return pMonedaTarget.getValue();  }
//  public void setDbid(String zValue) throws Exception {    pDbid.setValue(zValue);  }
//  public String getDbid() throws Exception {     return pDbid.getValue();  }
  public void setCotizVenta(double zValue) throws Exception {    pCotizVenta.setValue(zValue);  }
  public double getCotizVenta() throws Exception {     return pCotizVenta.getValue();  }
  public void setCotizContab(double zValue) throws Exception {    pCotizContab.setValue(zValue);  }
  public double getCotizContab() throws Exception {     return pCotizContab.getValue();  }
  public void setCotizCompra(double zValue) throws Exception {    pCotizCompra.setValue(zValue);  }
  public double getCotizCompra() throws Exception {     return pCotizCompra.getValue();  }
  public void setId(int value) throws Exception { this.id=value;  }
  public int getId() throws Exception { return this.id;  }
  public void setFlatCtz(double value) throws Exception { this.pFlatCotiz.setValue(value);  }
  public void setUsed(boolean value) throws Exception { this.bWasUsed=value;  }
  public void setConverParent(BizMonedaConver value) throws Exception {    this.converParent=value;  }
  public void setModoFecha(boolean v) throws Exception {    this.pModoFecha.setValue(v);  }
  public boolean isModoFecha() throws Exception {    return this.pModoFecha.getValue();  }
  public boolean isModoHorario() throws Exception {    return !this.isModoFecha();  }
  /**
   * Constructor de la Clase
   */
  public BizMonedaConver() throws Exception {
  }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Properties methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Adds the object properties
   */
  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "pais", pPais );
    this.addItem( "moneda_source", pMonedaSource );
    this.addItem( "moneda_target", pMonedaTarget );
    this.addItem( "cotiz_venta", pCotizVenta );
    this.addItem( "cotiz_compra", pCotizCompra );
    this.addItem( "cotiz_contab", pCotizContab );
    this.addItem( "descr_conver_html", pDescrConverHtml );
    this.addItem( "modo_fecha", pModoFecha );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Empresa", true, true, 15 );
    this.addFixedItem( KEY, "pais", "Pais", true, true, 2 );
    this.addFixedItem( KEY, "moneda_source", "Moneda Origen", true, true, 15 );
    this.addFixedItem( KEY, "moneda_target", "Moneda Destino", true, true, 15 );
    this.addFixedItem( FIELD, "cotiz_venta", "Cotiz. Venta", true, false, 10,4);
    this.addFixedItem( FIELD, "cotiz_compra", "Cotiz. Compra", true, false, 10,4);
    this.addFixedItem( FIELD, "cotiz_contab", "Cotiz. Contab", true, false, 10,4);
    this.addFixedItem( FIELD, "modo_fecha", "Modo FEeha", true, false, 1);
//    this.addFixedItem( FIELD, "usuario", "Usuario", true, false, 15 );
//    this.addFixedItem( FIELD, "fecha_hora", "Fecha/Hora", true, true, 10 );
    this.addFixedItem( VIRTUAL, "descr_conver_html", "Conversión", true, true, 10 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "MON_CONVER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read(String company, String pais, String ms, String mt) throws Exception {
    addFilter( "company", company);
    addFilter( "pais",  pais);
    addFilter( "moneda_source",  ms);
    addFilter( "moneda_target",  mt);
    return read();
  }
//
//  @Override
//	public void processInsert()throws Exception{
////
////    if(this.pCotizVenta.getValue()<=0)
////      JExcepcion.SendError("El valor de la tasa de Venta debe ser mayor a cero");
////    if(this.pCotizCompra.getValue()<=0)
////      JExcepcion.SendError("El valor de la tasa de Compra debe ser mayor a cero");
//
//    super.processInsert();
//  }



  
  public String getDescrCotizHtml() throws Exception {
  	return this.getDescrConverHtml()+" "+this.pFlatCotiz.getValue();
//  	return this.getDescrConverHtml()+" "+this.pFlatCotiz.toFormattedString();
//  	return "url(\"pss_icon/"+BizMoneda.obtenerMoneda(this.getMonedaSource(), true).getIcono()+"\")";
//  	return JTools.encodeString2(this.getDescrConver())+": "+this.pFlatCotiz.toFormattedString();//pCotizCompra.toFormattedString() + "/" + pCotizVenta.toFormattedString();  
  }
//	public static BizMonedaConver findConver(String source) throws Exception {
//		return BizMonedaConver.findConver(source, BizMoneda.getMonedaLocal());
//	}
	public static BizMonedaConver findConver(String source, String target) throws Exception {
		return BizMonedaConver.findConver(null, null, source, target);
	}
	public static void clearCache() throws Exception {
		cache=null;
	}
	
	public BizCompany getObjCompany() throws Exception {
		return BizCompany.getCompany(this.getCompany());
	}
	
	public String findMonedaMain() throws Exception {
		return this.getObjCompany().findPais(this.getPais()).findLocalCurrency();
	}
	
	
	public BizMonedaConver findConver(Date fecha) throws Exception {
		if (!fecha.before(BizUsuario.getUsr().todayGMT(false)))
			return this;
		BizMonedaCotizacion ctz = this.findHistConver(fecha);
		if (ctz==null) return this;
		BizMonedaConver hist =  (BizMonedaConver)this.createDefaultClone();
		hist.setCotizVenta(ctz.getCotizVenta());
		hist.setCotizCompra(ctz.getCotizCompra());
		hist.setCotizContab(ctz.getCotizContab());
		return hist;
//		return hist.getFixed(this.getMonedaTarget(), false);
	}
	
	public BizMonedaCotizacion findHistConver(Date fecha) throws Exception {
		if (this.isModoFecha())
			return this.findFechaConver(fecha);
		else
			return this.findFechaHoraConver(fecha);
	}
	
	public BizMonedaCotizacion findFechaHoraConver(Date fecha) throws Exception {
		BizMonedaCotizacion max = new BizMonedaCotizacion();
		max.addFilter("company", this.getCompany());
		max.addFilter("pais", this.getPais());
		max.addFilter("moneda_source", this.getMonedaSource());
		max.addFilter("moneda_target", this.getMonedaTarget());
		max.addFilter("fecha_hora", JDateTools.getDateEndDay(fecha), "<=", true);
		long maxId = max.SelectMaxLong("cotizacion_id"); // esto se puede asi porque el historico es cronologico rigido
		if (maxId==0L) return null;
		BizMonedaCotizacion conver = new BizMonedaCotizacion();
		conver.Read(maxId);
		return conver;
	}	

	public BizMonedaCotizacion findFechaConver(Date fecha) throws Exception {
		BizMonedaCotizacion max = new BizMonedaCotizacion();
		max.addFilter("company", this.getCompany());
		max.addFilter("pais", this.getPais());
		max.addFilter("moneda_source", this.getMonedaSource());
		max.addFilter("moneda_target", this.getMonedaTarget());
		max.addFilter("fecha", fecha, "<=", true);
		Date maxDate = max.SelectMaxDate("fecha"); // esto se puede asi porque el historico es cronologico rigido
		if (maxDate==null) return null;
		BizMonedaCotizacion conver = new BizMonedaCotizacion();
		conver.addFilter("company", this.getCompany());
		conver.addFilter("pais", this.getPais());
		conver.addFilter("moneda_source", this.getMonedaSource());
		conver.addFilter("moneda_target", this.getMonedaTarget());
		conver.addFilter("fecha", maxDate);
		conver.dontThrowException(true);
		if (!conver.read()) return null;
		return conver;
	}	

	
	public static BizMonedaConver findConver(String company, String pais, String source, String target) throws Exception {
		BizMonedaConver p = BizMonedaConver.findInCache(company, pais, source, target);
		return p.getFixed(target, false);
	}
	
	public static void clearCache(BizMonedaConver p) throws Exception {
		if (cache==null) return;
		String key=p.getCompany()+p.getPais()+p.getMonedaSource()+p.getMonedaTarget();
		cache.removeElement(key);
		p.clean();
	}
	
	public static synchronized BizMonedaConver findInCache(String company, String pais, String source, String target) throws Exception {
		if (source.equals(target)) 
			return BizMonedaConver.getTrivialConver(target);
		if (cache==null) cache=JCollectionFactory.createMap();

		if (company==null) company=BizUsuario.getUsr().getCompany();
		if (pais==null) pais= BizUsuario.getUsr().getCountry();
		String key1=company+pais+source+target;
		String key2=company+pais+target+source;
		
		BizMonedaConver p;
		p=(BizMonedaConver) cache.getElement(key1);
		if (p!=null) return p;
		p=(BizMonedaConver) cache.getElement(key2);
		if (p!=null) return p;
		p = new BizMonedaConver();
		p.dontThrowException(true);
		if (!p.Read(company, pais, source, target)) {
			p.clearFilters();
			if (!p.Read(company, pais, target, source)) {
					JExcepcion.SendError("No existe conversion: "+source+"->"+target);
			}
		}
		String key=p.getCompany()+p.getPais()+p.getMonedaSource()+p.getMonedaTarget();
		cache.addElement(key,  p);
		return p;
	}
	

	public String getDescrMonedaSource() throws Exception {
		return this.getObjMonedaSource().GetDescrip();
	}

	public String getDescrMonedaTarget() throws Exception {
		return this.getObjMonedaTarget().GetDescrip();
	}

	public String getDescrConver() throws Exception {
		return this.getDescrMonedaSource()+" -> "+this.getDescrMonedaTarget();
	}
	
	private BizMoneda source;
	public BizMoneda getObjMonedaSource() throws Exception {
		if (this.source!=null) return this.source;
		return (this.source=BizMoneda.obtenerMoneda(this.getMonedaSource(), true));
	}

	private BizMoneda target;
	public BizMoneda getObjMonedaTarget() throws Exception {
		if (this.target!=null) return this.target;
		return (this.target=BizMoneda.obtenerMoneda(this.getMonedaTarget(), true));
	}
	
	public String getDescrConverHtml() throws Exception {
		String html="";
		BizMoneda source = this.getObjMonedaSource();
		if (source==null) return html;
		html+=!source.hasIcono()?this.getDescrMonedaSource():"<img src=\"pss_icon/"+source.getIcono()+"\" />";
		BizMoneda target = this.getObjMonedaTarget();
		if (target==null) return html;
		html+=" &#8594; ";
		html+=!target.hasIcono()?this.getDescrMonedaTarget():"<img src=\"pss_icon/"+target.getIcono()+"\" />";
		return html;
	}

//	public double getCotizVenta(String target) throws Exception {
//		if (target.equals(this.getMonedaSource())) return this.getCotizVenta();
//		if (target.equals(this.getMonedaTarget())) return 1;
//		JExcepcion.SendError("Moneda invalida");
//		return 0d;
//	}
//	public double getCotizCompra(String target) throws Exception {
//		if (target.equals(this.getMonedaSource())) return 1;
//		if (target.equals(this.getMonedaTarget())) return this.getCotizCompra();
//		JExcepcion.SendError("Moneda invalida");
//		return 0d;
//	}
	public double getConverRate(double v) throws Exception {
		if (this.isMultiplicar()) return v;
		return 1/v;
	}

	public double toFlat(double v) throws Exception {
		if (this.isMultiplicar()) return v;
		return 1/v;
	}

	public double getConverRate() throws Exception {
//		this.bWasUsed=true;
		return this.getCotizVenta()/this.getCotizCompra();
	}
	
	public double getUnConverRate() throws Exception {
//		this.bWasUsed=true;
		return 1/this.getConverRate();
	}

	public String getCtzAsString(double value) throws Exception {
		return BizMoneda.getCotizacionAsString(this.isMultiplicar()?value:1d, this.isMultiplicar()?1d:value);
	}

	public String getCtzAsString() throws Exception {
		return BizMoneda.getCotizacionAsString(this.getCotizVenta(), this.getCotizCompra());
//		if (target.equals(this.getMonedaSource())) return BizMoneda.getCotizacionAsString(this.getCotizVenta(),1);
//		if (target.equals(this.getMonedaTarget())) return BizMoneda.getCotizacionAsString(1,this.getCotizCompra());
//		return "";
	}	

	public String getCtzRateAsString() throws Exception {
		return BizMoneda.getCotizRateAsString(this.getCotizVenta(), this.getCotizCompra());
	}	

	public String getCtzRateInvAsString() throws Exception {
		return BizMoneda.getCotizRateAsString(this.getCotizCompra(), this.getCotizVenta());
	}	

	public static BizMonedaConver getTrivialConver(String moneda) throws Exception {
		BizMonedaConver c = new BizMonedaConver();
		c.setMonedaSource(moneda);
		c.setMonedaTarget(moneda);
		c.setCotizCompra(1d);
		c.setCotizVenta(1d);
		c.setCotizContab(1d);
		c.setFlatCtz(1d);
		return c;
	}
	
//	public boolean wasUsed() throws Exception {
//		return this.bWasUsed;
//	}
		
	public double conver(double value) throws Exception {
//		this.bWasUsed=true;
		return JTools.rd(value*this.getConverRate(), BizMonedaConver.getPresicion(this.getMonedaTarget()));
	}

	public double unconver(double value) throws Exception {
//		this.bWasUsed=true;
		return JTools.rd(value*(this.getUnConverRate()), BizMonedaConver.getPresicion(this.getMonedaSource()));
	}

//	public double smartConver(double value, String monConvergente) throws Exception {
////		if (monConvergente.equals(this.getMonedaTarget()))
////			this.bWasUsed=true;
//		return JTools.rd(value*this.getConverRate(), BizMonedaConver.getPresicion(this.getMonedaTarget()));
//	}

	
	public static int getPresicion(String moneda) throws Exception {
		return JRegionalFormatterFactory.getAbsoluteBusinessFormatter().getCurrencyFractionDigits(moneda);
	}
	public BizMonedaConver getRelative(boolean c) throws Exception {
		return this.getRelative(this.getMonedaSource(), c);
	}
	public BizMonedaConver getRelative(String target, boolean c) throws Exception {
		if (!this.hasContab(c)) return this; 
		return this.getObjFixedContab();
	}
//	public BizMonedaConver getInverse(String target, String monConvergente) throws Exception {
//		if (monConvergente==null) return this;
//		if (target.equals(monConvergente)) 
//		target = this.getMonedaInv(target);
//	}
	
	public BizMonedaConver getFixed(String target) throws Exception {
		return this.getFixed(target, false);
	}
	public BizMonedaConver getFixed(String target, boolean c) throws Exception {
		BizMonedaConver rel = this.getRelative(target, c);
		if (target.equals(this.getMonedaSource())) return rel.getObjFixedSource();
		if (target.equals(this.getMonedaTarget())) return rel.getObjFixedTarget();
		return null;
	}
//	public String getMonedaInv(String target) throws Exception {
//		if (target.equals(this.getMonedaTarget())) return  this.getMonedaSource();
//		if (target.equals(this.getMonedaSource())) return this.getMonedaTarget();
//		return null;
//	}
	
	public boolean hasContab(boolean c) throws Exception {
		if(!c) return false;
//		if (!target.equals(this.getMonedaSource())) return false;
		if (this.getCotizContab()==0d) return false;
		if (this.getCotizContab()==this.getCotizVenta()) return false;
		return true;
	}
	
	private BizMonedaConver getObjFixedContab() throws Exception {
		if (this.fixedContab!=null) return this.fixedContab;
		BizMonedaConver c = new BizMonedaConver();
		c.setMonedaSource(this.getMonedaTarget());
		c.setMonedaTarget(this.getMonedaSource());
		c.setCotizVenta(this.getCotizContab());
		c.setCotizCompra(this.getCotizCompra());
		c.setCotizContab(0d);
		c.setConverParent(this);
		return (this.fixedContab=c);
	}

	private BizMonedaConver getObjFixedSource() throws Exception {
		if (this.fixedSource!=null) return this.fixedSource;
		if (this.getFlatCotiz()==0d) this.setFlatCtz(this.getCotizVenta());
		BizMonedaConver c = new BizMonedaConver();
		c.setCompany(this.getCompany());
		c.setPais(this.getPais());
		c.setMonedaSource(this.getMonedaTarget());
		c.setMonedaTarget(this.getMonedaSource());
//		c.setCotizVenta(this.getCotizVenta());
		c.setCotizVenta(this.getFlatCotiz());
		c.setCotizCompra(1d);
		c.setFlatCtz(this.getFlatCotiz());
		c.setConverParent(this);
		c.setModoFecha(this.isModoFecha());
//		c.setCotizContab(this.getCotizContab());
		return (this.fixedSource=c);
	}
	
	private BizMonedaConver getObjFixedTarget() throws Exception {
		if (this.fixedTarget!=null) return this.fixedTarget;
		if (this.getFlatCotiz()==0d) this.setFlatCtz(this.getCotizCompra());
		BizMonedaConver c = new BizMonedaConver();
		c.setCompany(this.getCompany());
		c.setPais(this.getPais());
		c.setMonedaSource(this.getMonedaSource());
		c.setMonedaTarget(this.getMonedaTarget());
		c.setCotizVenta(1d);
//		c.setCotizCompra(this.getCotizCompra());
		c.setCotizCompra(this.getFlatCotiz());
		c.setFlatCtz(this.getFlatCotiz());
		c.setConverParent(this);
		c.setModoFecha(this.isModoFecha());
//		c.setCotizCompra(this.flatCotiz);
//		c.setCotizContab(this.getCotizContab());
		return (this.fixedTarget=c);
	}
	
	public BizMonedaConver getObjConverParent() throws Exception {
		if (this.converParent==null) return this;
		return this.converParent;
	}
	
	
//	public double getCotizacion() throws Exception {
//		return this.getCotizVenta()*this.getCotizVenta();
//	}

	public boolean isCtzContab() throws Exception {
		return this.pCotizContab.getValue()==0;
//		return this.pCotizVenta.getValue()!=this.pCotizContab.getValue();
	}
	
	public String getKeyString() throws Exception {
		return this.getMonedaSource()+this.getMonedaTarget();
	}
	
	public BizMonedaConver getObjUsed(boolean contab) throws Exception {
		BizMonedaConver c;
		c = this.getFixed(this.getMonedaSource(), contab);
		if (c!=null && c.bWasUsed) return c;
		c = this.getFixed(this.getMonedaTarget(), contab);
		if (c!=null && c.bWasUsed) return c;
		return null;
	}
	
	
//  public boolean isMonedaLocal() throws Exception {
//  	return this.getMonedaSource().equals(BizMoneda.getMonedaLocal());
//  }
//  
  public JRecords<BizMonedaCotizacion> getHistorico() throws Exception {
  	JRecords<BizMonedaCotizacion> r = new JRecords<BizMonedaCotizacion>(BizMonedaCotizacion.class);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("pais", this.getPais());
  	r.addFilter("moneda_source", this.getMonedaSource());
  	r.addFilter("moneda_target", this.getMonedaTarget());
  	r.readAll();
  	return r;
  }

  @Override
  public void processDelete() throws Exception {
  	this.getHistorico().processDeleteAll();
  	super.processDelete();
		BizMonedaConver.clearCache();
  }
  
	@Override
	public void processInsert() throws Exception {
		BizMonedaConver c = new BizMonedaConver();
		c.dontThrowException(true);
		if (c.Read(this.getCompany(), this.getPais(), this.getMonedaTarget(), this.getMonedaSource()))
			JExcepcion.SendError("La conversion de moneda ya existe");
		super.processInsert();
		BizMonedaConver.clearCache();
	}  

	public double getFlatCotiz() throws Exception {
		return this.pFlatCotiz.getValue();
	}
//	public double getFlatCotiz() throws Exception {
//		if (this.getCotizCompra()!=1) return this.getCotizCompra();
//		return this.getCotizVenta();	
//	}
	
	public boolean hasMoneda(String moneda) throws Exception {
		if (this.getMonedaSource().equals(moneda)) return true;
		if (this.getMonedaTarget().equals(moneda)) return true;
		return false;
	}
	
	public BizMonedaConver assignFlatCotiz(double value) throws Exception {
//		if (value==0d) return this;
		this.setFlatCtz(value);
		this.clean();
		return this;
	}
	
//	public void pushFlatCotiz(double value) throws Exception {
//		if (this.getCotizCompra()!=1d) this.setCotizCompra(value);
//		if (this.getCotizVenta()!=1d) this.setCotizVenta(value);
//		this.setFlatCtz(value);
//	}
	
	public BizMonedaConver takeValues(BizMonedaConver target) throws Exception {
		this.setCotizCompra(target.getCotizCompra());
		this.setCotizVenta(target.getCotizVenta());
		this.setFlatCtz(target.getFlatCotiz());
		return this;
	}
	
	public void clean() throws Exception {
		this.fixedSource=null;
		this.fixedTarget=null;
		this.fixedContab=null;
	}
	
	public BizMonedaConver convinar(BizMonedaConver target) throws Exception {
		double sc=this.getCotizCompra();
		double sv=this.getCotizVenta();
		double tc=target.getCotizCompra();
		double tv=target.getCotizVenta();
		if (sc==sv) { sc=1d; sv=1d;}  
		if (tc==tv) { tc=1d; tv=1d;}  
		BizMonedaConver c = new BizMonedaConver();
		c.setMonedaSource(this.getMonedaSource());
		c.setMonedaTarget(target.getMonedaTarget());
		c.setCotizCompra(sc*tc);
		c.setCotizVenta(sv*tv);
		if (c.getCotizVenta()==1d)
			c.setFlatCtz(c.getCotizCompra());
		else if (c.getCotizCompra()==1d) 
			c.setFlatCtz(c.getCotizVenta());
		else
			c.setFlatCtz(JTools.rd(c.getCotizVenta()/c.getCotizCompra()));
		return c;
	}


	
	public void fixSameMoneda() throws Exception {
		if (!this.getMonedaSource().equals(this.getMonedaTarget())) return;
		this.setCotizCompra(1d);
		this.setCotizVenta(1d);
	}

	public boolean isMultiplicar() throws Exception {
		return this.getCotizVenta()!=1d;
	}
	
	@Override
	public String toString() {
		try {
			return this.getDescrMonedaSource()+"->"+this.getDescrMonedaTarget()+": " + this.getCotizVenta()+"/"+this.getCotizCompra();// .getFlatCotiz();
		} catch (Exception e) {
			return "Error";
		}
	}

	
}