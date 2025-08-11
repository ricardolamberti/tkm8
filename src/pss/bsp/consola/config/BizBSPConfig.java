package pss.bsp.consola.config;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.clase.BizClase;
import pss.bsp.config.airportGroups.BizAirportGroup;
import pss.bsp.config.carrierGroups.BizCarrierGroup;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.familia.BizFamilia;
import pss.bsp.market.BizMarket;
import pss.bsp.regions.BizRegion;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.common.security.license.ILicense;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizBSPConfig extends JRecord  {

	public BizBSPConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

  private JString pUsuario = new JString();
  private JString pCompany= new JString();
	JString pConciliableKey = new JString();
	JString pFormatoFecha = new JString();
	JFloat pFactorBO = new JFloat();
	JLong pReservas = new JLong();
	JFloat pPrecision = new JFloat();
	JString pDefaultInterfaz = new JString();
	JString pDefaultFormato = new JString();
	JString pTipoImpuesto = new JString();
	JFloat pPorcIvaOver = new JFloat();
	JString pEmail = new JString();
  JString pFondo= new JString();
	JLong pEvent = new JLong();
  private JString pLicencia= new JString() {
  	@Override
  	public void preset() throws Exception {
  		ILicense lic = getObjLicense();
  		if (lic==null) 
  			pLicencia.setValue("");
  		else
  			pLicencia.setValue(lic.getDescription());
  	}
  };

	public static final String FORMATO_FECHA_DD = "dia mes año";
	public static final String FORMATO_FECHA_MM = "mes dia año";
  static JMap<String,String>  formatosFecha; 
	public static JMap<String,String> getFormatosFecha() {
		if (formatosFecha!=null) return formatosFecha;
		formatosFecha = JCollectionFactory.createMap();
		formatosFecha.addElement(FORMATO_FECHA_DD, FORMATO_FECHA_DD);
		formatosFecha.addElement(FORMATO_FECHA_MM, FORMATO_FECHA_MM);
		return formatosFecha;
	} 


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setLicencia(String zValue) throws Exception {    pLicencia.setValue(zValue);  }
  public String getLicencia() throws Exception {     return pLicencia.getValue();  }
  public void setConciliableKey(String zValue) throws Exception {    pConciliableKey.setValue(zValue);  }
  public String getConciliableKey() throws Exception {     return pConciliableKey.getValue();  }
  public void setPrecision(double zValue) throws Exception {    pPrecision.setValue(zValue);  }
  public double getPrecision() throws Exception {     return pPrecision.getValue();  }
  public void setFactorBO(double zValue) throws Exception {    pFactorBO.setValue(zValue);  }
  public double getFactorBo() throws Exception {     return pFactorBO.getValue();  }
  public void setExpireReservas(long zValue) throws Exception {    pReservas.setValue(zValue);  }
  public long getExpireReservas() throws Exception {    return pReservas.getValue();  }
  public void setFormatoFecha(String zValue) throws Exception {    pFormatoFecha.setValue(zValue);  }
  public String getFormatoFecha() throws Exception {     return pFormatoFecha.getValue();  }
  public void setDefaultFormato(String zValue) throws Exception {    pDefaultFormato.setValue(zValue);  }
  public String getDefaultFormato() throws Exception {     return pDefaultFormato.getValue();  }
  public void setDefaultInterfaz(String zValue) throws Exception {    pDefaultInterfaz.setValue(zValue);  }
  public String getDefaultInterfaz() throws Exception {     return pDefaultInterfaz.getValue();  }
  public void setTipoImpuesto(String zValue) throws Exception {    pTipoImpuesto.setValue(zValue);  }
  public String getTipoImpuesto() throws Exception {     return pTipoImpuesto.getValue();  }
  public void setEmail(String zValue) throws Exception {    pEmail.setValue(zValue);  }
  public String getEmail() throws Exception {     return pEmail.getValue();  }
  public void setEvent(long zValue) throws Exception {    pEvent.setValue(zValue);  }
  public long getEvent() throws Exception {     return pEvent.getValue();  }
  public void setPorcsIvaOver(double zValue) throws Exception {    pPorcIvaOver.setValue(zValue);  }
  public double getPorcsIvaOver() throws Exception {     return pPorcIvaOver.getValue();  }


  public String getFondo() throws Exception {     
  	if (pFondo.isNull()) return "foto3.jpg";
  	return pFondo.getValue();  
  }
  


  public void createProperties() throws Exception {
    this.addItem( "usuario", pUsuario );
    this.addItem( "company", pCompany );
    this.addItem( "licencia", pLicencia );
  	this.addItem( "conciliableKey", pConciliableKey);
  	this.addItem( "precision", pPrecision);
  	this.addItem( "factor", pFactorBO);
  	this.addItem( "expire_reservas", pReservas);
  	this.addItem( "formato_fecha", pFormatoFecha);
  	this.addItem( "defa_interfaz", pDefaultInterfaz);
  	this.addItem( "defa_formato", pDefaultFormato);
  	this.addItem( "tipo_imp", pTipoImpuesto);
   	this.addItem( "porc_iva_over", pPorcIvaOver);
    this.addItem( "email", pEmail);
  	this.addItem( "fondo", pFondo);
  	this.addItem( "event", pEvent);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "usuario", "Usuario", true, true, 15 );
    this.addFixedItem( KEY, "company", "Compania", true, true, 15 );
  	this.addFixedItem( FIELD, "conciliableKey", "Clave de conciliacion", true, false, 50);
   	this.addFixedItem( FIELD, "factor", "Factor", true, false, 15);
   	this.addFixedItem( FIELD, "expire_reservas", "Expire reservas", true, false, 15);
    this.addFixedItem( FIELD, "precision", "Precision", true, false, 15);
  	this.addFixedItem( FIELD, "formato_fecha", "Formato Fecha BO", true, false, 50);
   	this.addFixedItem( FIELD, "defa_interfaz", "Interfaz por defecto", true, false, 50);
   	this.addFixedItem( FIELD, "defa_formato", "Formato por defacto", true, false, 50);
   	this.addFixedItem( FIELD, "tipo_imp", "Tipo impuesto", true, false, 50);
   	this.addFixedItem( FIELD, "porc_iva_over", "Porc Iva Over", true, false, 18,2);
    this.addFixedItem( FIELD, "email", "Email", true, false, 450);
   	this.addFixedItem( FIELD, "fondo", "Fondo", true, false, 450);
   	this.addFixedItem( FIELD, "event", "Event", true, false, 18);
    this.addFixedItem( VIRTUAL, "licencia", "Licencia", true, true, 18 );
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_USUARIO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   

  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zUsuario ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "usuario",  zUsuario ); 
    return read(); 
  } 
  
  ILicense objLicense;
  public  ILicense getObjLicense() throws Exception {
//  	if (objLicense!=null) return objLicense;
//  	if (BizUsuario.getUsr().getObjLicense()==null)
//  		throw new Exception("No tiene otorgada una licencia, comuniquese con su administrador");
//  	ILicense l=BizUsuario.getUsr().getObjLicense().getLicense();
//  	return (objLicense=l);
	  return null;
  }
  public void ExecGenerateUser(final String zCompany,final String usuario,final String email) throws Exception {
  		JExec oExec = new JExec(this, "processGenerateUser") {

  			@Override
  			public void Do() throws Exception {
  				processGenerateUser(zCompany,usuario,email);
  			}
  		};
  		oExec.execute();
  	}

  public void processGenerateUser(String zCompany,String usuario,String email) throws Exception {
  	setCompany(zCompany);
  	setUsuario(usuario);
  	setConciliableKey(IConciliable.AEROLINEA_BOLETOS);
  	setFormatoFecha(FORMATO_FECHA_DD);
  	setPrecision(1);
  	setEmail(email);
  	processInsert();
 	}
  JRecords<BizCustomList> objFavoritos;
  public JRecords<BizCustomList> getObjFavoritos() throws Exception {
  //	if (this.objFavoritos!=null) return this.objFavoritos;
  	JRecords<BizCustomList> r =new JRecords<BizCustomList>(BizCustomList.class);
  	r.addFilter("company", getCompany());
		r.addJoin("LST_CUSTOM_LISTV2_FAV");
		r.addFixedFilter("where LST_CUSTOM_LISTV2_FAV.company=LST_CUSTOM_LISTV2.company"+
				" and LST_CUSTOM_LISTV2_FAV.list_id=LST_CUSTOM_LISTV2.list_id and " +
				" LST_CUSTOM_LISTV2_FAV.usuario = '"+BizUsuario.getUsr().GetUsuario()+"' ");
  	return (this.objFavoritos=r);
  }
  public BizCustomList getFavorito(int id) throws Exception {
  	int i=0;
  	JIterator<BizCustomList> inf = getObjFavoritos().getStaticIterator();
  	while (inf.hasMoreElements()) {
  		BizCustomList informe=inf.nextElement();
  		i++;
  		if (i==id) return informe;
  		
  	}
  	return null;
  }

  
  @Override
  public void processUpdate() throws Exception {
  	if (pPrecision.isNull())
  		pPrecision.setValue(1);
  	super.processUpdate();
//  	crearAvisosEmail("informe");
  	BizBSPUser.getUsrBSP().getBspConsola().clean();
  }

  public void crearAvisosEmail(String plantilla) throws Exception {
  	BizSqlEventAction action = new BizSqlEventAction();
  	action.dontThrowException(true);
  	action.read(getCompany(), BizBSPCompany.class.getCanonicalName(), getCompany(), BizSqlEventAction.MENSUAL);
  	action.setCompany(getCompany());
  	action.setIdevento(getCompany());
  	action.setClassevento(BizBSPCompany.class.getCanonicalName());
  	action.setTipoPeriodicidad(BizSqlEventAction.MENSUAL);
  	action.setUltimoDia(true);
  	action.setDescripcion("Informe Peri�dico");
  	action.setAction(BizSqlEventAction.EMAIL);
  	action.setCorreo("default");
  	if (action.isNullIdPlantilla()) {
    	BizPlantilla pl = BizCompany.getObjPlantilla(getCompany(), plantilla);
    	if (pl!=null) action.setIdplantilla(pl.getId());
  	}
  	action.processUpdateOrInsertWithCheck();
  }

	public void execProcessCopiarHijos() throws Exception {
		JExec oExec = new JExec(this, "processCopiarHijos") {

			@Override
			public void Do() throws Exception {
				processCopiarHijos();
			}
		};
		oExec.execute();
	}
	 public void processCopiarHijos() throws Exception {
			JRecords<BizBSPCompany> lista = new JRecords<BizBSPCompany>(BizBSPCompany.class);
			lista.addFilter("parent_company", getCompany());
			JIterator<BizBSPCompany> it = lista.getStaticIterator();
			while (it.hasMoreElements()) {
				BizBSPCompany comp = it.nextElement();
				BizCarrierGroup.processIgualarParent(getCompany(), comp.getCompany());
				BizAirportGroup.processIgualarParent(getCompany(), comp.getCompany());
				BizClase.processIgualarParent(getCompany(), comp.getCompany());
				BizRegion.processIgualarParent(getCompany(), comp.getCompany());
				BizFamilia.processIgualarParent(getCompany(), comp.getCompany());
				BizMarket.processIgualarParent(getCompany(), comp.getCompany());
				
			
			
			
			
			
			}
			
	 
	 }
	 
	public void execProcessRefrescar() throws Exception {
		JExec oExec = new JExec(this, "processRefrescar") {

			@Override
			public void Do() throws Exception {
				processRefrescar();
			}
		};
		oExec.execute();
	}
  
  public void processRefrescar() throws Exception {
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		String SQL ="select customer_id ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by customer_id " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("customer_id");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.CLIENTE, getCompany(), codigo);
  	}
		SQL ="select customer_id_reducido ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by customer_id_reducido " ;
		regs.ExecuteQuery(SQL);
	  	while (regs.next()) {
	  		String codigo = regs.CampoAsStr("customer_id_reducido");
	  		if (codigo!=null && !codigo.equals(""))
	  			BizMailingPersona.addPersona(BizMailingPersona.CLIENTE_REDUCIDO, getCompany(), codigo);
	  	}
		SQL ="select codigo_cliente ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by codigo_cliente " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("codigo_cliente");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.CLIENTERMK, getCompany(), codigo);
  	}
		SQL ="select office_id ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by office_id " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("office_id");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.SUCURSAL, getCompany(), codigo);
  	}
		SQL ="select centro_costos ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by centro_costos " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("centro_costos");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.CCOSTO, getCompany(), codigo);
  	}
		SQL ="select vendedor ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by vendedor " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("vendedor");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.VENDEDOR, getCompany(), codigo);
  	}
		SQL ="select nro_iata ";
		SQL += " from TUR_PNR_BOLETO " ;
		SQL += " where TUR_PNR_BOLETO.company='"+getCompany()+"' " ;
		SQL += " group by nro_iata " ;
		regs.ExecuteQuery(SQL);
  	while (regs.next()) {
  		String codigo = regs.CampoAsStr("nro_iata");
  		if (codigo!=null && !codigo.equals(""))
  			BizMailingPersona.addPersona(BizMailingPersona.IATA, getCompany(), codigo);
  	}
//  	tickets.addGroupBy("office_id");
//  	tickets.addGroupBy("centro_costos");
//  	tickets.addGroupBy("vendedor");
  	
  }

  
	

}

