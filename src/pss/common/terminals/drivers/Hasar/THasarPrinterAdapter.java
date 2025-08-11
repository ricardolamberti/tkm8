package pss.common.terminals.drivers.Hasar;

import pss.common.layout.JFieldReq;
import pss.common.layout.JFieldSetTicket;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JPrinterException;
import pss.common.terminals.core.JRecordLine;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;


public class THasarPrinterAdapter extends JPrinterAdapter {

	public THasarPrinterAdapter(JTerminal terminal) throws Exception {
		super(terminal);
	}


//----------------------------------------------------------------------------------------------------------------------
//  public JRecords obtenerComprobantes() throws Exception {
//    JRecords oTipos = JRecords.createVirtualBDs();
//    oTipos.addItem( JRecord.virtualBD( JImpresoraFiscal.BOLETA_CHILE, "Tique Boleta Fiscal", 209 ) );
//    return oTipos;
//  }

//----------------------------------------------------------------------------------------------------------------------
  public void open() throws Exception {
    // Se abre la impresora en la primera impresión de documento
  }

//----------------------------------------------------------------------------------------------------------------------
  @Override
	public JTerminal getTerminal() throws Exception {
  	return this.getHasar();
  }
  public THasar getHasar() throws Exception {
  	((THasar)terminal).inicialize();
  	return (THasar)terminal;
  }
  
  @Override
	public boolean hasCierreZ() throws Exception {
  	return true;
  }
	public static final String TICKET_FACTURA_A = "A";
	public static final String TICKET_FACTURA_B = "B";
	public static final String FACTURA_A = "0";
	public static final String FACTURA_B = "1";
	public static final String TICKET_NOTA_DEBITO_A = "2";
	public static final String TICKET_NOTA_DEBITO_B = "3";
	public static final String NOTA_DEBITO_A = "D";
	public static final String NOTA_DEBITO_B = "E";
	public static final String RECIBO_A = "a";
	public static final String RECIBO_B = "b";
	public static final String TICKET_C = "T";
	public static final String TICKET_NOTA_CREDITO_A = "4";
	public static final String TICKET_NOTA_CREDITO_B = "5";
	public static final String NOTA_CREDITO_A = "R";
	public static final String NOTA_CREDITO_B = "S";
	public static final String REMITO = "r";
	public static final String TICKET_RECIBO_X = "6";
	public static final String RECIBO_X = "x";
	public static final String ORDEN_SALIDA = "s";
	public static final String RESUMEN_CUENTA = "t";
	public static final String CARGO_HABITACION = "U";
	public static final String COTIZACION = "u";
	public static final String CLAUSULA_CREDITO = ""+0x3a;
	public static final String CLAUSULA_SEGURO = ""+0x3b;
	public static final String TICKET_PAGARE = "7";
	public static final String PAGARE = ""+0x3c;
	public static final String POLIZA_SEGURO = ""+0x3d;
	public static final String RECORDATORIO = ""+0x3e;
	public static final String SOLICITUD_CREDITO = ""+0x3f;
	public static final String COMUNICACION_CLIENTE = ""+0x21;
	public static final String OFRECIMIENTO_CREDITO = ""+0x22;
	public static final String OFRECIMIENTO_TARJETA = ""+0x23;
	public static final String MINUTA_CREDITO = ""+0x24;
	public static final String OFRECIMIENTO_PASAPORTE = ""+0x25;
	public static final String RENOVACION_CREDITO = ""+0x26;
	public static final String ADELANTO_REMUNERACION = ""+0x27;
	public static final String SOLICITUD_TARJETA_DEBITO = ""+0x28;
	public static final String SOLICITUD_CLAVE_TARJETA = ""+0x29;
	public static final String RESCATE_MERCADERIA = ""+0x2a;
	public static final String INGRESO_EGRESO_SUCURSAL = ""+0x2b;
 
  @Override
	protected void loadSupportedDocuments() throws Exception {
  	this.addSupportedDocs(TICKET_FACTURA_A, "Ticket Factura A");
  	this.addSupportedDocs(TICKET_FACTURA_B, "Ticket Factura B/C");
  	this.addSupportedDocs(FACTURA_A, "Factura A");
  	this.addSupportedDocs(FACTURA_B, "Factura B/C");
  	this.addSupportedDocs(TICKET_NOTA_DEBITO_A, "Ticket Nota de Débito A");
  	this.addSupportedDocs(TICKET_NOTA_DEBITO_B, "Ticket Nota de Débito B/C");
  	this.addSupportedDocs(NOTA_DEBITO_A, "Nota de Débito A");
  	this.addSupportedDocs(NOTA_DEBITO_B, "Nota de Débito B/C");
  	this.addSupportedDocs(RECIBO_A, "Recibo A");
  	this.addSupportedDocs(RECIBO_B, "Recibo B/C");
  	this.addSupportedDocs(TICKET_C, "Ticket B/C");
  	this.addSupportedDocs(TICKET_NOTA_CREDITO_A, "Ticket Nota de Crédito A");
  	this.addSupportedDocs(TICKET_NOTA_CREDITO_B, "Ticket Nota de Crédito B/C");
  	this.addSupportedDocs(NOTA_CREDITO_A, "Nota de Crédito A");
  	this.addSupportedDocs(NOTA_CREDITO_B, "Nota de Crédito B/C");
  	this.addSupportedDocs(REMITO, "Remito");
  	this.addSupportedDocs(TICKET_RECIBO_X, "Ticket Recibo X");
  	this.addSupportedDocs(RECIBO_X, "Recibo X");
  	this.addSupportedDocs(ORDEN_SALIDA, "Orden de Salida");
  	this.addSupportedDocs(RESUMEN_CUENTA, "Resumen de Cuenta");
  	this.addSupportedDocs(CARGO_HABITACION, "Cargo a la Habitación");
  	this.addSupportedDocs(COTIZACION, "Cotización");
  	this.addSupportedDocs(CLAUSULA_CREDITO, "Cláusulas de Crédito en Cuota Fija");
  	this.addSupportedDocs(CLAUSULA_SEGURO, "Cláusulas de Segura de Desempleo");
  	this.addSupportedDocs(TICKET_PAGARE, "Ticket Pagaré");
  	this.addSupportedDocs(PAGARE, "Pagaré");
  	this.addSupportedDocs(POLIZA_SEGURO, "Póliza de Seguro de Garantía Complementaria");
  	this.addSupportedDocs(RECORDATORIO, "Recordatorio");
  	this.addSupportedDocs(SOLICITUD_CREDITO, "Solicitud de Crédito");
  	this.addSupportedDocs(COMUNICACION_CLIENTE, "Comunicación con Nuestros Clientes");
  	this.addSupportedDocs(OFRECIMIENTO_CREDITO, "Ofrecimiento de Crédito en Efectivo");
  	this.addSupportedDocs(OFRECIMIENTO_TARJETA, "Ofrecimiento de Tarjeta de Crédito");
  	this.addSupportedDocs(MINUTA_CREDITO, "Minuta de Crédito");
  	this.addSupportedDocs(OFRECIMIENTO_PASAPORTE, "Ofrecimiento de Pasaporte");
  	this.addSupportedDocs(RENOVACION_CREDITO, "Renovación de Crédito");
  	this.addSupportedDocs(ADELANTO_REMUNERACION, "Adelanto de Remuneración");
  	this.addSupportedDocs(SOLICITUD_TARJETA_DEBITO, "Solicitud de Tarjeta de Débito");
  	this.addSupportedDocs(SOLICITUD_CLAVE_TARJETA, "Solicitud de Clave de Tarjeta");
  	this.addSupportedDocs(RESCATE_MERCADERIA, "Rescate de Mercadería");
  	this.addSupportedDocs(INGRESO_EGRESO_SUCURSAL, "Ingresos y Egresos Internos de la Sucursal");
  }
  
//  public boolean supportDocument() throws Exception {
//  	JFieldReq req = new JFieldReq(this.getFieldInterface(), JFieldSetTicket.REPORT_HEADER, "0");
//  	return req.getBool(JFieldSetTicket.SUPPORT_DOCUMENT);
//  }
  
//----------------------------------------------------------------------------------------------------------------------
  @Override
	public Answer openDocument() throws Exception {
  	if (this.supportDocument()) return new AwrOk();
  	return super.openDocument();
  }
  @Override
	public Answer closeDocument() throws Exception {
  	if (this.supportDocument()) return new AwrOk();
  	return super.closeDocument();
  }
  @Override
	public Answer cancelDocument() throws Exception {
    if( getHasar().isFiscalOpenStatus() ) {
    	this.getHasar().fiscalCancel();
    }
    return super.cancelDocument();
    
  }
    
  @Override
	public void generate(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.dataStartTicket(section, source);} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.dataPrintItemTicket(section, source);} else
//    if (section.equals(JFieldSetTicket.BODY_ITEM)) {	this.dataPrintItemTicket(section, source);} else // configurar para que este no vaya
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.dataPrintPayHeader(section, source);} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.dataPrintPay(section, source);} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.dataFinishTicket(section, source);}
  }
  @Override
	public Answer print(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.startTicket(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.printItemTicket(section, source); return new AwrOk();} else 
//    if (section.equals(JFieldSetTicket.BODY_ITEM)) {	this.printItemTicket(section, source); return new AwrOk();} else 
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.printPayHeader(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.printPay(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.finishTicket(section, source); return new AwrOk();} else 
  	return super.print(section, source);
  }
  
  public String getTipoCuitPasaporte() throws Exception {
  	return "3";
  }
  
  public String getTipoCuitFiscal(String id) throws Exception { 
		if (id.equalsIgnoreCase("CU")) return "C";
		if (id.equalsIgnoreCase("LE")) return "0";
		if (id.equalsIgnoreCase("LC")) return "1";
		if (id.equalsIgnoreCase("DU")) return "2";
		if (id.equalsIgnoreCase("DNI")) return "2";
		if (id.equalsIgnoreCase("PT")) return "3";
		if (id.equalsIgnoreCase("PS")) return "3";
		if (id.equalsIgnoreCase("CI")) return "4";
		return "";
  }

  public String getResponIdFiscal(String id) throws Exception {
//		if (id.equalsIgnoreCase("RI")) // negrada porque la Hasar no acepta en una FB a los RI y por las RG 3668 es necesario que lo acepte
//			return this.getPrinterDocType().equals(THasarPrinterAdapter.FACTURA_B)?"C":"I";
  	if (id.equalsIgnoreCase("RI"))  return "I";
		if (id.equalsIgnoreCase("RNI")) return "N";
		if (id.equalsIgnoreCase("EX")) return "E";
		if (id.equalsIgnoreCase("NR")) return "A";
		if (id.equalsIgnoreCase("CF")) return "C";
		if (id.equalsIgnoreCase("BU")) return "B";
		if (id.equalsIgnoreCase("MT")) return "M";
		return "C";
  }  
  
	public String replaceForeignChars(String s) {
		int len=s.length();
		char[] temp=new char[len];
		for(int i=0; i<len; i++) {
			temp[i]=this.replaceForeignChar(s.charAt(i));
		}
		return new String(temp);
	}

	public char replaceForeignChar(char msg) {
		if (msg=='¿') return ' ';
		if (msg=='¡') return ' ';
		if (msg=='%') return ' ';
		if (msg=='&') return ' ';
		if (msg=='"') return ' ';
		if (msg=='\'') return ' ';
		return msg;
	}


	public void addField(JRecordLine rl, JFieldReq req) throws Exception {
		rl.addField(rl.getSection(), req.getId(), this.replaceForeignChars(String.valueOf(req.get())));
	}
  
  public void dataStartTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
//  	rl.addField(req.with(JFieldSetTicket.TIPO_COMPROBANTE_DESC));
//  	rl.addField(req.with(JFieldSetTicket.NRO_COMPROBANTE));
  	this.addField(rl, req.with(JFieldSetTicket.DIR_EMPRESA));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_TELEFONO));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_CPOSTAL));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_LOCALIDAD));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_IDENT_ADICIONAL));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_NOMBRE));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_TIPO_CUIT_FISCAL));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_CUIT));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_DOMICILIO));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_RESPON_ID_FISCAL));
  	this.addField(rl, req.with(JFieldSetTicket.NRO_COMPUESTO_ORIGINAL));  	
//  	rl.addField(req.with(JFieldSetTicket.NRO_COMPROBANTE_ORIGINAL));
  	this.addField(rl, req.with(JFieldSetTicket.FISCAL_FANTASY));
  	this.addField(rl, req.with(JFieldSetTicket.FISCAL_HEADER));
  	this.recordLine(rl.getXmlLine());
  }
  
  public void dataPrintItemTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_CODIGO));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_DESCR));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_CANT));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_P_UNIT));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_RATENATTAX));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_P_UNIT_ORIG));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_PRICE_PRESITION));
  	this.addField(rl, req.with(JFieldSetTicket.ITEM_NEGATIVE));
  	this.recordLine(rl.getXmlLine());
  }

  public void dataPrintPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.PAY_MONTO_ORIGINAL));
  }
  
  public void dataPrintPay(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.PAY_FORMA_PAGO_PADRE));
  	this.addField(rl, req.with(JFieldSetTicket.PAY_DESCRIP));
  	this.addField(rl, req.with(JFieldSetTicket.PAY_MONTO_ORIGINAL));
  	this.recordLine(rl.getXmlLine());
  }
  
  public void dataFinishTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.VUELTO_CONSUMIDO));
  	this.addField(rl, req.with(JFieldSetTicket.VUELTO_CONSUMIDO_DESCR));
  	this.addField(rl, req.with(JFieldSetTicket.COMENTARIO2));
  	this.recordLine(rl.getXmlLine());
  }

//----------------------------------------------------------------------------------------------------------------------
  private boolean isFiscal(String tipoDoc) {
  	return 
  	tipoDoc.equals(FACTURA_A) ||
  	tipoDoc.equals(TICKET_FACTURA_A) ||	
  	tipoDoc.equals(TICKET_FACTURA_B) ||		
  	tipoDoc.equals(FACTURA_A) ||						
  	tipoDoc.equals(FACTURA_B) ||						
  	tipoDoc.equals(TICKET_NOTA_DEBITO_A) ||	
  	tipoDoc.equals(TICKET_NOTA_DEBITO_B) ||	
  	tipoDoc.equals(NOTA_DEBITO_A) ||			
  	tipoDoc.equals(NOTA_DEBITO_B) ||			
  	tipoDoc.equals(RECIBO_A) ||					
  	tipoDoc.equals(RECIBO_B) ||					
  	tipoDoc.equals(TICKET_C);				
  }
  private boolean isNoFiscalHomologado(String tipoDoc) {
  	return 
  	tipoDoc.equals(TICKET_NOTA_CREDITO_A) ||
		tipoDoc.equals(TICKET_NOTA_CREDITO_B) ||
		tipoDoc.equals(NOTA_CREDITO_A) ||
		tipoDoc.equals(NOTA_CREDITO_B) ||
		tipoDoc.equals(REMITO) ||
		tipoDoc.equals(TICKET_RECIBO_X) ||
		tipoDoc.equals(RECIBO_X) ||
		tipoDoc.equals(ORDEN_SALIDA) ||
		tipoDoc.equals(RESUMEN_CUENTA) ||
		tipoDoc.equals(CARGO_HABITACION) ||
		tipoDoc.equals(COTIZACION) ||
		tipoDoc.equals(CLAUSULA_CREDITO) ||
		tipoDoc.equals(CLAUSULA_SEGURO) ||
		tipoDoc.equals(TICKET_PAGARE) ||
		tipoDoc.equals(PAGARE) ||
		tipoDoc.equals(POLIZA_SEGURO) ||
		tipoDoc.equals(RECORDATORIO) ||
		tipoDoc.equals(SOLICITUD_CREDITO) ||
		tipoDoc.equals(COMUNICACION_CLIENTE) ||
		tipoDoc.equals(OFRECIMIENTO_CREDITO) ||
		tipoDoc.equals(OFRECIMIENTO_TARJETA) ||
		tipoDoc.equals(MINUTA_CREDITO) ||
		tipoDoc.equals(OFRECIMIENTO_PASAPORTE) ||	
		tipoDoc.equals(RENOVACION_CREDITO) ||			
		tipoDoc.equals(ADELANTO_REMUNERACION) ||		
		tipoDoc.equals(SOLICITUD_TARJETA_DEBITO) ||	
		tipoDoc.equals(SOLICITUD_CLAVE_TARJETA) ||	
		tipoDoc.equals(RESCATE_MERCADERIA) ||			
		tipoDoc.equals(INGRESO_EGRESO_SUCURSAL);	
  	
  }
  
  public void startTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	this.getHasar().fiscalCancel();
  	this.getHasar().noFiscalCancel();
  	
  	this.getHasar().cleanFantasy();
  	String fantasy = req.getString(JFieldSetTicket.FISCAL_FANTASY);
  	fantasy=fantasy.replace("\\n", "\n");
  	int j=0;
  	JStringTokenizer tk = JCollectionFactory.createStringTokenizer(fantasy, '\n');
  	while (tk.hasMoreTokens()) {
  		String t = tk.nextToken();
  		if (t.equals("^n")) t="";
    	this.getHasar().setFantasyInfo(t);
    	j++;
  	}
  	for(;j<5;j++) {
  		this.getHasar().setFantasyInfo( JTools.centerString(req.getString(""), 42,' ') );
  	}
//  	this.getHasar().setFantasyInfo( f1 );
//  	this.getHasar().setFantasyInfo( "  " );
//  	this.getHasar().setFantasyInfo( "  " );
//  	this.getHasar().setFantasyInfo( "  " );
//  	this.getHasar().setFantasyInfo( "  " );
  		
  	this.getHasar().cleanHeader();
  	String header = req.getString(JFieldSetTicket.FISCAL_HEADER);
  	header=header.replace("\\n", "\n");
  	int i=0;
  	tk = JCollectionFactory.createStringTokenizer(header, '\n');
  	while (tk.hasMoreTokens()) {
  		String t = tk.nextToken();
    	this.getHasar().setHeaderInfo(t);
    	i++;
  	}
  	for(;i<5;i++) {
  		this.getHasar().setHeaderInfo( JTools.centerString(req.getString(""), 42,' ') );
  	}
		String tipoDoc = getPrinterDocType();
		
		String zRazonSocial = req.getString(JFieldSetTicket.CLIENTE_NOMBRE);
		String zTipoDocumento = req.getString(JFieldSetTicket.CLIENTE_TIPO_CUIT_FISCAL);
		String zNroDocumento = req.getString(JFieldSetTicket.CLIENTE_CUIT);
		String zDireccion = req.getString(JFieldSetTicket.CLIENTE_DOMICILIO);
		String zResponsabilidadIVA = req.getString(JFieldSetTicket.CLIENTE_RESPON_ID_FISCAL);
		String zOrigDoc = req.getString(JFieldSetTicket.NRO_COMPUESTO_ORIGINAL);
		
		// negrada para poder hacer FB o NCB a Responsables inscriptos
		if (zResponsabilidadIVA.equals("I") && (tipoDoc.equals(THasarPrinterAdapter.FACTURA_B) || tipoDoc.equals(THasarPrinterAdapter.NOTA_CREDITO_B)))
			zResponsabilidadIVA="C";	
		
		this.getHasar().fiscalCliente(zRazonSocial, zNroDocumento, zTipoDocumento, zResponsabilidadIVA, zDireccion);
		
		if (this.isFiscal(tipoDoc)) {
			this.getHasar().fiscalOpen(tipoDoc);
			long lCurTicket = this.getHasar().getCurrFiscalTicket();
			if( lCurTicket == 0 ) {
			  JPrinterException.SendError( "Error capturando número de boleta fiscal de la impresora" );
			}
		}	else if (this.isNoFiscalHomologado(tipoDoc)) {
			this.getHasar().originalDoc(tipoDoc, zOrigDoc);
			this.getHasar().noFiscalHomologadoOpen(tipoDoc, this.getPrinterDocNumber());
		}	else {
			this.getHasar().noFiscalOpen(tipoDoc);
		}
  }
  
	
//----------------------------------------------------------------------------------------------------------------------
  public void printItemTicket(String section, Object source) throws Exception {
    JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);

    String sCodigo    = req.getString(JFieldSetTicket.ITEM_CODIGO);
    String sArtDescr  = req.getString(JFieldSetTicket.ITEM_DESCR);
    double dCantidad  = req.getDouble(JFieldSetTicket.ITEM_CANT);
    double dMontoUnit = req.getDouble(JFieldSetTicket.ITEM_P_UNIT);
    double dPrctImp   = req.getDouble(JFieldSetTicket.ITEM_RATENATTAX);
    double dPrecioOrig= req.getDouble(JFieldSetTicket.ITEM_P_UNIT_ORIG);
    double dImpInterno= 0d;//req.getDouble(JFieldSetTicket.ITEM_TOTAL_TAX);
    int iPresition    = req.getInt(JFieldSetTicket.ITEM_PRICE_PRESITION);
    boolean negative  = req.getBool(JFieldSetTicket.ITEM_NEGATIVE);

    PssLogger.logFiscal( "Fiscal Printer - Item fiscal:" + sCodigo + " cantidad:" + dCantidad + " monto unitario: " + dMontoUnit + " iva prct:" + dPrctImp );

    this.getHasar().item( sArtDescr, iPresition, sCodigo, dCantidad, dMontoUnit, dPrecioOrig, dPrctImp , dImpInterno);
  }

  public void printPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	int cantPayments = req.getInt(JFieldSetTicket.PAY_MONTO_ORIGINAL);
    if( cantPayments >= 4 ) {
      JPrinterException.SendError("Mas de 4 pagos enviados");
    }
  }
  
	public void printPay(String section, Object source) throws Exception {
		JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
		
		String sDesc  = req.getString(JFieldSetTicket.PAY_DESCRIP);
		double dMonto = req.getDouble(JFieldSetTicket.PAY_MONTO_ORIGINAL);

		double vuelto = this.getHasar().fiscalPay(  sDesc, dMonto);

  }
//----------------------------------------------------------------------------------------------------------------------
  public void finishTicket(String section, Object source) throws Exception {
//    JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);  	
  	String footer = req.getString(JFieldSetTicket.COMENTARIO2);

    this.pushSaleFooter(footer);
    getHasar().closeDoc();
  }

	public boolean checkNumeration() throws Exception {
		return this.supportDocument();
	}

	@Override
	public long getCurrentNum() throws Exception {
    if( !this.supportDocument() ) return 0L;
    long lCurrTicket = this.getHasar().getCurrFiscalTicket();

    if( lCurrTicket > 0 ) {
      return lCurrTicket;
    }
//  	if (tipoDocumento.equals("_LAST_"))
//      return getHasar().getNextFiscalTicket(getHasar().getLastTipoDocumento());
    return getHasar().getNextFiscalTicket(this.getPrinterDocType());
  }
//----------------------------------------------------------------------------------------------------------------------
  private void pushSaleFooter(String footer) throws Exception {
    getHasar().cleanFooter();
    if (footer==null) return;
    if (footer.equals("")) return;
    
    footer=footer.replace("\\n", "\n");
    JStringTokenizer token = JCollectionFactory.createStringTokenizer(footer, '\n');
    while (token.hasMoreTokens()) {
    	String t = token.nextToken();
      getHasar().setFooterInfo(t);
    }
  }
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
  

  @Override
	public boolean checkDocOpen() throws Exception {
    if (this.isDocOpen()) return true;
    try { 
	  	if( !this.supportDocument() ) {
	  		return false;
	  	} else {
	  		return this.getHasar().isFiscalOpenStatus();
	  	}
    } catch (Exception e) {
      return true;
    }
  }
  
	public boolean isRequiereIdentFiscal() throws Exception {
		return true;
	}

  

}
