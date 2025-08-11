package pss.common.layout;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public abstract class JFieldSet  {

	
	private BizLayout layout;
	public boolean includeRecords = false;
	public boolean includeHeaders = true;

  public static final String REPORT_FISCAL_HEADER = "REPORT_FISCAL_HEADER";
  public static final String REPORT_FISCAL_FOOTER = "REPORT_FISCAL_FOOTER";

  public static final String TICKET_SET   = "Ticket";
  //public static final String CASHDROP_SET = "CashDrop";
  public static final String ARQUEO_CAJA_SET = "ArqueoCaja";
  public static final String CIERRE_TURNO_SET = "CierreTurno";
  public static final String CTACTE_FACT_ENTREGAS  = "FactEntregas";
  public static final String CIERRE_RETAIL = "CierreRetail";
  public static final String CIERRE_X = "CierreX";
  public static final String CIERRE_Z = "CierreZ";
  public static final String CAJACLOSE_SET = "CajaClose";
  public static final String PUMPCLOSE_SET = "PumpClose";
  public static final String PUMPCOUNTER_SET = "PumpCounterClose";
  public static final String TANKMEASUREMENT_SET = "TankMeasurementClose";
  public static final String PUMP_PRESET_SET = "PumpPreset";
  public static final String CUENTA_MOV_SET   = "CuentaMov";
  public static final String ITINERARIO_SET   = "Itinerario";
//  public static final String LINKMOV_SET   = "LinkMov";
  public static final String TAX_CERTIF_SET   = "TaxCertif";
  public static final String BOLETOS_SET   = "Boletos";
  public static final String VOUCHER_SET   = "Voucher";
  public static final String NEGOCIO_SET   = "Negocio";
  public static final String CAJADIA_SET   = "CajaDia";
  public static final String WINS_SET   = "WinsSet";

  
  private static JMap<String, JFieldSet> fieldSetMap=null;
  private static JMap<String, String> modules=null;
  
  private JMap<String, JMap<String,String>> fieldsMap =null;
  
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public JFieldSet() throws Exception {
  }

  public static boolean isInstalled(String set) throws Exception {
    return virtualCreate(set)!=null;
  }
  public static JFieldSet virtualCreate(String set) throws Exception {
  try {
     return  (JFieldSet)Class.forName(getMap().getElement(set)).newInstance();
  } catch ( ClassNotFoundException e ) {
    return null;
  }}
	public String ident;
	public String filter;

  public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getIdent() {
		return ident==null?"":ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}
/*
  public static JWins getWinSections(String zSet) throws Exception {
    return VirtualCreate(zSet).getWinSections();
  }
  public static JWins getWinFields(String zSet, String zSection) throws Exception {
    return VirtualCreate(zSet).getWinFields(zSection);
  }
  public static JBDs getSections(String zSet) throws Exception {
    return VirtualCreate(zSet).getSections();
  }
  public static JBDs getFields(String zSet, String zSection) throws Exception {
    return VirtualCreate(zSet).getFields(zSection);
  }
*/


  public static JMap<String, String> getFixedSections() throws Exception {
    JMap<String, String> map = JCollectionFactory.createOrderedMap();
    map.addElement(REPORT_FISCAL_HEADER, "Report Fiscal Header");
    map.addElement(REPORT_FISCAL_FOOTER, "Report Fiscal Footer");
    return map;
  }

  protected JMap<String, String> getSections() throws Exception { return null; }
  
  public final JMap<String, String> getSections(String section) throws Exception {
   	if (this.fieldsMap==null) this.fieldsMap = JCollectionFactory.createMap();
  	JMap<String,String> fields = this.fieldsMap.getElement(section);
  	if (fields!=null) return fields;
  	JMap<String,String> map = this.getFields(section);
  	this.fieldsMap.addElement(section, map);
  	return map;
  }
  public final JMap<String, String> getAllSections() throws Exception {
    JMap<String, String> map = this.getSections();
    return map;
  }

  public JMap<String,String> getContabSections() throws Exception { return null; }

  public JMap<String,String> getAllFields(String section) throws Exception {
  	if (this.fieldsMap==null) this.fieldsMap = JCollectionFactory.createMap();
  	JMap<String,String> fields = this.fieldsMap.getElement(section);
  	if (fields!=null) return fields;
  	JMap<String,String> map = this.getFields(section);
  	this.fieldsMap.addElement(section, map);
  	return map;
  }
  protected abstract JMap<String,String> getFields(String section) throws Exception;
  
//  public static String getFieldDescr(String fieldSet, String section, String field) throws Exception {
//  	return JFieldSet.getSet(fieldSet).getAllFields(section).getElement(field);
//  }


  public static JMap<String, String> getFieldSets() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
    if ( isInstalled(TICKET_SET)) map.addElement(TICKET_SET, "Comprobante");
    //if ( isInstalled(CASHDROP_SET)) map.addElement(CASHDROP_SET, "Cash Drop");
    if ( isInstalled(ARQUEO_CAJA_SET)) map.addElement(ARQUEO_CAJA_SET, "Arqueo Caja");
    if ( isInstalled(CIERRE_TURNO_SET)) map.addElement(CIERRE_TURNO_SET, "Cierre Grupo");
    if ( isInstalled(CTACTE_FACT_ENTREGAS)) map.addElement(CTACTE_FACT_ENTREGAS, "Detalle de Entregas");
    if ( isInstalled(CIERRE_RETAIL)) map.addElement(CIERRE_RETAIL, "Cierre Retail");
    if ( isInstalled(CIERRE_X)) map.addElement(CIERRE_X, "Cierre Fiscal X");
    if ( isInstalled(CIERRE_Z)) map.addElement(CIERRE_Z, "Cierre Fiscal Z");
    if ( isInstalled(CAJACLOSE_SET)) map.addElement(CAJACLOSE_SET, "Cierre Caja");
    if ( isInstalled(CUENTA_MOV_SET)) map.addElement(CUENTA_MOV_SET, "Movimiento de Cuenta");
    if ( isInstalled(ITINERARIO_SET)) map.addElement(ITINERARIO_SET, "Itinerario Viaje");    
//    if ( isInstalled(LINKMOV_SET)) map.addElement(LINKMOV_SET, "Links Ctacte");
    if ( isInstalled(TAX_CERTIF_SET)) map.addElement(TAX_CERTIF_SET, "Certificado Impuestos");
    if ( isInstalled(BOLETOS_SET)) map.addElement(BOLETOS_SET, "Boletos");
    if ( isInstalled(VOUCHER_SET)) map.addElement(VOUCHER_SET, "Voucher");
    if ( isInstalled(NEGOCIO_SET)) map.addElement(NEGOCIO_SET, "Negocio");
    if ( isInstalled(CAJADIA_SET)) map.addElement(CAJADIA_SET, "CajaDia");
    if ( isInstalled(WINS_SET)) map.addElement(WINS_SET, "Wins");
    return map;
  }
  public static JMap<String, String> getFieldSetContab() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
    if ( isInstalled(TICKET_SET)) map.addElement(TICKET_SET, "Comprobante");
    if ( isInstalled(CUENTA_MOV_SET)) map.addElement(CUENTA_MOV_SET, "Movimiento de Cuenta");
//    if ( isInstalled(LINKMOV_SET)) map.addElement(LINKMOV_SET, "Links Ctacte");
    return map;
  }
  
  public static JFieldSet getSet(BizLayout lay) throws Exception {
  	JFieldSet set = getSet(lay.getCamposSet(), lay.getIdent());
  	set.setLayout(lay);
  	return set;
  }
  public static JFieldSet getSet(String set) throws Exception {
  	return getSet(set, "");
  }
  public static JFieldSet getSet(String set, String ident) throws Exception {
  	if (fieldSetMap==null) fieldSetMap = JCollectionFactory.createMap();
  	String key = set+"|"+ident;
  	if (fieldSetMap.containsKey(key))
  		return fieldSetMap.getElement(key);
  	JFieldSet fieldSet = JFieldSet.virtualCreate(set);
  	fieldSetMap.addElement(key, fieldSet);
  	return fieldSet;
  }

  private static JMap<String, String> getMap() throws Exception {
  	if (modules!=null) return modules;
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement(TICKET_SET, "pss.common.layout.JFieldSetTicket");
  	//map.addElement(CASHDROP_SET, "pss.erp.cashDrawer.CashDrop.JFieldSetCashDrop");
  	map.addElement(ARQUEO_CAJA_SET, "pss.erp.cashDrawer.JFieldSetCaja");
  	map.addElement(CIERRE_TURNO_SET, "pss.common.cierres.testigos.JFieldSetCierreTurno");
  	map.addElement(CTACTE_FACT_ENTREGAS, "pss.erp.ctacte.AddInCtacte.JFieldSetFactEntregas");
  	map.addElement(CIERRE_RETAIL, "pss.erp.shift.print.JFieldSetCierreRetail");
  	map.addElement(CIERRE_X, "pss.erp.shift.print.JFieldSetCierreX");
  	map.addElement(CIERRE_Z, "pss.erp.shift.print.JFieldSetCierreZ");
    map.addElement(CAJACLOSE_SET, "pss.erp.cashDrawer.TestigosCierre.JFieldSetCajaClose");
    map.addElement(CUENTA_MOV_SET , "pss.erp.ctacte.posInterface.JFieldSetCuentaMov");
//    map.addElement(LINKMOV_SET , "pss.erp.ctacte.posInterface.JFieldSetLinkMov");
    map.addElement(ITINERARIO_SET , "pss.tourism.travel.itinerario.JFieldSetItinerario");
    map.addElement(TAX_CERTIF_SET, "pss.erp.taxes.taxCertif.JFieldSetTaxCertif");
    map.addElement(BOLETOS_SET, "pss.tourism.products.air.cccf.JFieldSetAir");
    map.addElement(VOUCHER_SET, "pss.tourism.vouchers.JFieldSetVoucher");
    map.addElement(NEGOCIO_SET, "pss.tourism.travel.JFieldSetNegocio");
    map.addElement(CAJADIA_SET, "pss.erp.cashDrawer.transaction.JFieldSetCajaMov");
    map.addElement(WINS_SET, "pss.common.layout.JFieldSetWins");
    return (modules=map);
  }

	public void setLayout(BizLayout value) throws Exception {
		this.layout=value;
		setIdent(this.layout.getIdent());
	}
	
	public BizLayout getLayout() throws Exception {
		return this.layout;
	}

  public static String getFieldDescr(String fieldSet, String section, String field) throws Exception {
  	return JFieldSet.getSet(fieldSet).getAllFields(section).getElement(field);
  }

	
}
