package pss.common.terminals.drivers.EpsonFiscal;

import pss.common.layout.JFieldReq;
import pss.common.layout.JFieldSetTicket;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JPrinterException;
import pss.common.terminals.core.JRecordLine;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;


public class TEpsonPrinterAdapter extends JPrinterAdapter {

	public TEpsonPrinterAdapter(JTerminal terminal) throws Exception {
		super(terminal);
	}


//----------------------------------------------------------------------------------------------------------------------
  public void open() throws Exception {
    // Se abre la impresora en la primera impresión de documento
  }

//----------------------------------------------------------------------------------------------------------------------
  @Override
	public JTerminal getTerminal() throws Exception {
  	return this.getEpson();
  }
  
  public TEpson getEpson() throws Exception {
//  	((TEpson)terminal).inicialize();
  	return (TEpson)terminal;
  }
  
  @Override
	public boolean hasCierreZ() throws Exception {
  	return true;
  }
    
	public static final String TICKET_C = "TC";
	public static final String FACTURA_A = "FA";
	public static final String FACTURA_B = "FB";
	public static final String NOTA_CREDITO_A = "NA";
	public static final String NOTA_CREDITO_B = "NB";
 
  @Override
	protected void loadSupportedDocuments() throws Exception {
  	this.addSupportedDocs(TICKET_C, "Ticket C");
  	this.addSupportedDocs(FACTURA_A, "Factura A");
  	this.addSupportedDocs(FACTURA_B, "Factura B/C");
  	this.addSupportedDocs(NOTA_CREDITO_A, "Nota de Crédito A");
  	this.addSupportedDocs(NOTA_CREDITO_B, "Nota de Crédito B/C");
  }
  
    
  @Override
	public void generate(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.dataStartTicket(section, source);} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.dataPrintItemTicket(section, source);} else
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.dataPrintPayHeader(section, source);} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.dataPrintPay(section, source);} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.dataFinishTicket(section, source);}
  }
  @Override
	public Answer print(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.startTicket(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.printItemTicket(section, source); return new AwrOk();} else 
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.printPayHeader(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.printPay(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.finishTicket(section, source); return new AwrOk();} else 
  	return super.print(section, source);
  }

  public void dataStartTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.DIR_EMPRESA));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_TELEFONO));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_CPOSTAL));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_LOCALIDAD));
  	this.addField(rl, req.with(JFieldSetTicket.EMPRESA_IDENT_ADICIONAL));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_NOMBRE));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_TIPO_CUIT_FISCAL));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_CUIT));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_DOMICILIO));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_CIUDAD));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_COMUNA));
  	this.addField(rl, req.with(JFieldSetTicket.CLIENTE_RESPON_ID_FISCAL));
//  	rl.addField(req.with(JFieldSetTicket.NRO_COMPROBANTE));
  	this.addField(rl, req.with(JFieldSetTicket.NRO_COMPUESTO_ORIGINAL));  	
  	this.addField(rl, req.with(JFieldSetTicket.FISCAL_HEADER));
  	this.recordLine(JTools.replaceForeignChars(rl.getXmlLine()));
  }
  
	public void addField(JRecordLine rl, JFieldReq req) throws Exception {
		rl.addField(rl.getSection(), req.getId(), JTools.replaceForeignChars(String.valueOf(req.get())));
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
  	this.addField(rl, req.with(JFieldSetTicket.CANT_X_REAL));
  	this.addField(rl, req.with(JFieldSetTicket.CANT_X_BASICO));
  	this.recordLine(rl.getXmlLine());
  }

  public void dataPrintPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	this.addField(rl, req.with(JFieldSetTicket.PAYH_CANTIDAD_PAGOS));
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

////----------------------------------------------------------------------------------------------------------------------
//  private boolean isFiscal() throws Exception {
//  	String tipoDoc=this.getPrinterDocType();
//  	if (this.isFacturaA()) return true;
//  	if (this.isFacturaB()) return true;
//  	if (this.isNotaCreditoA()) return true;
//  	if (this.isNotaCreditoB()) return true;
//  	return false;
//  }
//  
//  private boolean isNoFiscalHomologado(String tipoDoc) {
//  	return false; 
//  }

  private void pushSaleHeader(JFieldReq req) throws Exception {
  	this.getEpson().getEpsonDriver().cleanHeaderInfo();
		String header = req.getString(JFieldSetTicket.FISCAL_HEADER);
		int i=0;
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(header, '\n');
		while (tk.hasMoreTokens()) {
			String t = tk.nextToken();
	  	this.getEpson().getEpsonDriver().setHeaderInfo(i, JTools.replaceForeignChars(t));
	  	i++;
	  	if (i>=10) break;
		}
		for(;i<10;i++) {
			this.getEpson().getEpsonDriver().setHeaderInfo( i, "");
		}
  }
//----------------------------------------------------------------------------------------------------------------------
  private void startTicket( String section, Object source ) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	this.pushSaleHeader(req);

    String respIVACliente = req.getString( JFieldSetTicket.CLIENTE_RESPON_ID_FISCAL);
    String nombre = req.getString( JFieldSetTicket.CLIENTE_NOMBRE );
    nombre = nombre.length() != 0 ? nombre : req.getString( JFieldSetTicket.CLIENTE_RESPON );
    String tipoDocCliente = req.getString( JFieldSetTicket.CLIENTE_TIPO_CUIT_FISCAL);
    String nroDoc = req.getString( JFieldSetTicket.CLIENTE_CUIT );
    String domicilio = req.getString( JFieldSetTicket.CLIENTE_DOMICILIO );
    String ciudad = req.getString( JFieldSetTicket.CLIENTE_CIUDAD );
    String comuna = req.getString( JFieldSetTicket.CLIENTE_COMUNA );
//    long docNumber = req.getLong( JFieldSetTicket.NRO_COMPROBANTE);

    if( tipoDocCliente.equals( "CUIT" ) || tipoDocCliente.equals( "CUIL" ) ) {
      if( nroDoc.length() == 0 ) {
        JPrinterException.SendError( "Identificador fiscal de cliente no ingresado" );
      }
    }

    if( this.isFacturaA() || this.isNotaCreditoA() ) {
      if( !tipoDocCliente.equals( "CUIT" ) )
        JPrinterException.SendError( "Debe tener un CUIT para abrir un comprobante A" );
    }

//    PssLogger.logFiscal( "loadOpenInvoiceArgs: " + respIVACliente + " - " + nombre + " - " + tipoDocCliente
//                           + " - " + nroDoc + " - " + domicilio );

    String tipoDoc = this.getPrinterDocType();
    String tipoComp = tipoDoc.substring(0,1);
    String tipoLetra = tipoDoc.substring(1,2);
//    checkInconsistentData(respIVACliente, tipoDocCliente, tipoLetra);
    String[] argumentos = new String[19];
    argumentos[0] = tipoComp;
    argumentos[1] = "C";
    argumentos[2] = tipoLetra;
    argumentos[3] = "3"; // hardcoded 3 copias
    argumentos[4] = "F";
    argumentos[5] = "10";
    argumentos[6] = "I";
    argumentos[7] = respIVACliente;
    argumentos[8] = nombre == null ? "" : JTools.replaceForeignChars(nombre);
    argumentos[9] = "";
    argumentos[10] = tipoDocCliente;
    argumentos[11] = nroDoc;
    argumentos[12] = "N";
    argumentos[13] = domicilio == null ? "" : JTools.replaceForeignChars(domicilio);
    argumentos[14] = ciudad == null ? "" : JTools.replaceForeignChars(ciudad);
    argumentos[15] = comuna == null ? "" : JTools.replaceForeignChars(comuna) ;
    argumentos[16] = "";
    argumentos[17] = "";
    argumentos[18] = "C";

    // El domicilio es campo requerido para nota de crédito
    if( tipoComp.equals("N") ) {
      argumentos[13] = argumentos[13].length() > 0 ? argumentos[13] : "Domicilio no Especificado";
      argumentos[16] = req.getString( JFieldSetTicket.NRO_COMPUESTO_ORIGINAL );
      argumentos[16] = argumentos[16].length() == 0 ? "Devolución" : argumentos[16];
    }

    this.getEpson().fiscalOpen(tipoDoc, argumentos);
  }

//----------------------------------------------------------------------------------------------------------------------
  public String getResponIdFiscal(String id) throws Exception { 
    if( id.equals( "RI" )) return "I";
    if( id.equals( "RNI" )) return "R";
    if( id.equals( "EX" )) return "E";
    if( id.equals( "NR" )) return "N";
    if( id.equals( "CF" )) return "F";
    if( id.equals( "MT" )) return "M";
    JPrinterException.SendError( "No se pudo determinar el tipo contribuyente del cliente" );
    return null;
  }

  public String getTipoCuitFiscal(String id) throws Exception { 
    if( id.equals("CU")) return "CUIT";
    if( id.equals("LE")) return "L ENRO";
    if( id.equals("LC")) return "L CIVI";
    if( id.equals("DU")) return "DNI";
    if( id.equals("DNI")) return "DNI";
    if( id.equals("PT")) return "PASPTE";
    if( id.equals("PS")) return "PASPTE";
    if( id.equals("CI")) return "CED ID";
    JPrinterException.SendError( "No se pudo determinar el tipo documento" );
    return null;

  }

//----------------------------------------------------------------------------------------------------------------------
//  private double calcularImpIntPorc( JFieldReq req ) throws Exception {
//    return ( ( Double )req.get( JFieldSetTicket.ITEM_GTAX, JbGrupoFiscal.ARG_INTERNOS, JbImpuestoRegion.PORCENTAJE ) ).doubleValue();
//  }
  
//  private double calcularTasaAjusteVariable( JFieldReq req ) throws Exception {
//    double tasaAjusteVariable;
//    double impIntPorc = ( ( Double )req.get( JFieldSetTicket.ITEM_GTAX, JbGrupoFiscal.ARG_INTERNOS, JbImpuestoRegion.PORCENTAJE ) ).doubleValue();
//    if( impIntPorc != 0 ) {
//      if( isDocument("A") ) {
//        double baseIntPorc = ( ( Double )req.get( JFieldSetTicket.ITEM_BASEGTAX, JbGrupoFiscal.ARG_INTERNOS, JbImpuestoRegion.PORCENTAJE ) ).doubleValue();
//        tasaAjusteVariable = baseIntPorc / ( baseIntPorc + impIntPorc );
//      } else {
//        double ivaTotal = ( ( Double )req.get( JFieldSetTicket.ITEM_GTAX_TOTAL, JbGrupoFiscal.ARG_IVA, JbImpuestoRegion.PORCENTAJE ) ).doubleValue();
//        double pvp = ( JTools.rd( req.getDouble( JFieldSetTicket.CANT_X_P_UNIT ), 2 ) );
//        tasaAjusteVariable = ivaTotal / pvp;
//      }
//      if( Double.isInfinite( tasaAjusteVariable ) || Double.isNaN( tasaAjusteVariable ) ) {
//        tasaAjusteVariable = 0d;
//      }
//    } else {
//      tasaAjusteVariable = 0d;
//    }
//    return tasaAjusteVariable;
//  }

  
  public void printItemTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);

  	String modo = "M";
	  String sBasico = "";
	  String consignados1 = "";
	  String consignados2 = "";
	  String consignados3 = "";
//	  String sTasaIVATG = "2100";
//	  String sTasaIVANI = "1050";
	
	  double dReal = req.getDouble( JFieldSetTicket.CANT_X_REAL );
	  double dBasico = req.getDouble( JFieldSetTicket.CANT_X_BASICO);
	  double dMonto = this.isDocumentWithTax() ? dBasico : dReal;
	  if (dMonto==0d) dMonto=dReal;
	  double dTasa = req.getDouble( JFieldSetTicket.ITEM_RATENATTAX);
	  String sTasaIVA = JTools.LPad(String.valueOf((int)(dTasa*100)), 4, "0");
	
	  String sTexto = req.getString( JFieldSetTicket.ITEM_DESCR);
	
	  double dImpIntFijos = 0d;
	  double dImpIntPorc = 0d;
	
	  double dCantidad = req.getDouble( JFieldSetTicket.ITEM_CANT );
	
	  sBasico = String.valueOf( Math.round( dMonto * 100 ) );
	  String sCantidad = String.valueOf( Math.abs( ( long ) ( dCantidad * 1000 ) ) );
	
	  String sImpIntFijo = JTools.justifyStrings( "", String.valueOf( ( long ) ( dImpIntFijos * 100000000 ) ), 17, '0' );
	  String sImpIntPorc = JTools.justifyStrings( "", String.valueOf( ( long ) ( dImpIntPorc  * 100000000 ) ), 8, '0' );
	  
    String[] args = new String[12];
    args[0] = sTexto.length() > 23? sTexto.substring(0,23) : sTexto; // DESCRIPCION
    args[1] = sCantidad; // CANTIDAD x 1000
    args[2] = sBasico; // PRECIO UNITARIO x 100
    args[3] = sTasaIVA; // IVA x 100
    args[4] = modo; // M == VENTA      m == ANULACION VENTA
    args[5] = "00001"; // CANTIDAD DE BULTOS
    args[6] = sImpIntPorc; // IMPUESTOS INTERNOS PORCENTUALES
    args[7] = consignados1; // COMENTARIO 1
    args[8] = consignados2; // COMENTARIO 2
    args[9] = consignados3; // COMENTARIO 3
    args[10] = "0000"; // IVA RESPONSABLE N.I.
    args[11] = sImpIntFijo; // CONCEPTOS NO GRAVADOS  (IMPUESTOS INTERNOS FIJOS)
    
    this.getEpson().getEpsonDriver().sendInvoiceItem(args);
	}
  

  public void printPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	int cantPayments = req.getInt(JFieldSetTicket.PAYH_CANTIDAD_PAGOS);
    if( cantPayments >= 5 ) {
      JPrinterException.SendError("Mas de 5 pagos enviados");
    }
  }
  
	public void printPay(String section, Object source) throws Exception {
		JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
		
		String sDesc  = req.getString(JFieldSetTicket.PAY_DESCRIP);
		double dMonto = req.getDouble(JFieldSetTicket.PAY_MONTO_ORIGINAL);

		//double vuelto = 
		this.getEpson().fiscalPay(  sDesc, dMonto);
  }
	
//----------------------------------------------------------------------------------------------------------------------
  public void finishTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);  	
  	String footer = req.getString(JFieldSetTicket.COMENTARIO2);

    this.pushSaleFooter(footer);
    String tipoDoc = this.getPrinterDocType();
    String tipoComp = tipoDoc.substring(0,1);
    String tipoLetra = tipoDoc.substring(1,2);

    getEpson().getEpsonDriver().closeInvoice(tipoComp, tipoLetra, "  ");
  }

	public boolean checkNumeration() throws Exception {
		return this.supportDocument();
	}

	private int getPositionDocNumber() throws Exception {
    if( this.isFacturaB()) return 5;
    if( this.isFacturaA()) return 7;
    if( this.isNotaCreditoB()) return 12;
    if( this.isNotaCreditoA()) return 11;
    return 0;
  }

	@Override
	public long getCurrentNum() throws Exception {
		int pos = this.getPositionDocNumber();
		if (pos==0) return 0;
    long lCurrTicket = this.getEpson().getEpsonDriver().getNextFiscalNumber(pos);

    if( lCurrTicket < 0 ) return 0;
    return lCurrTicket + 1;
//    return getEpson().getNextFiscalTicket(this.getPrinterDocType());
  }
//----------------------------------------------------------------------------------------------------------------------
  private void pushSaleFooter(String footer) throws Exception {
    getEpson().getEpsonDriver().cleanFooterInfo();
    if (footer==null) return;
    if (footer.equals("")) return;
    
    int i = 0;
    footer=footer.replace("\\n", "\n");
    JStringTokenizer token = JCollectionFactory.createStringTokenizer(footer, '\n');
    while (token.hasMoreTokens()) {
    	String t = token.nextToken();
      getEpson().getEpsonDriver().setFooterInfo(i, JTools.replaceForeignChars(t));
      i++;
    }
  }

  @Override
	public boolean checkDocOpen() throws Exception {
    if (this.isDocOpen()) return true;
    try { 
    	if (!this.getEpson().hasEpsonDriver()) return false;
	  	return this.getEpson().getEpsonDriver().isDocumentOpen();
    } catch (Exception e) {
      return true;
    }
  }
  
	public boolean isRequiereIdentFiscal() throws Exception {
		return true;
	}

  public boolean hasMontoMaximo() throws Exception {
//    if (this.isTicketC() )
//      return true;
//    if (this.isTicketFacturaA())
//      return true;
//    if (this.isTicketFacturaB())
//      return true;
    return false;
  }
  
  public double getMontoMaximo() throws Exception {
    if ( this.isTicketC() )
      return 1000;
    else
      return 5000;
  }
  public boolean isTicketC() throws Exception {
    return this.getPrinterDocType().equals(TEpsonPrinterAdapter.TICKET_C); 
  }
  public boolean isFacturaA() throws Exception {
    return this.getPrinterDocType().equals(TEpsonPrinterAdapter.FACTURA_A);
  }
  public boolean isFacturaB() throws Exception {
    return this.getPrinterDocType().equals(TEpsonPrinterAdapter.FACTURA_B);
  }
  public boolean isNotaCreditoA() throws Exception {
    return this.getPrinterDocType().equals(TEpsonPrinterAdapter.NOTA_CREDITO_A);
  }
  public boolean isNotaCreditoB() throws Exception {
    return this.getPrinterDocType().equals(TEpsonPrinterAdapter.NOTA_CREDITO_B);
  }
  
  private boolean isDocumentWithTax() throws Exception {
  	return this.isFacturaA() || this.isNotaCreditoA();
  }

  private EpsonDriver getEpsonDriver() throws Exception {
  	return this.getEpson().getEpsonDriver();
  }
  
//----------------------------------------------------------------------------------------------------------------------
//  public Answer finishTicket( String section, Object source ) throws Exception {
//  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
//		String footer = req.getString(JFieldSetTicket.COMENTARIO2);
//  	this.pushSaleFooter(footer);
//
//    printPayments( req );
// 
//    if( this.isTicketC())
//      this.getEpsonDriver().closeTicket();
//    else if( this.isFacturaA())
//      this.getEpsonDriver().closeInvoice( "T", "A", " ");
//    else if( this.isFacturaB())
//      this.getEpsonDriver().closeInvoice( "T", "B", " ");
//    else if( this.isNotaCreditoA())
//      this.getEpsonDriver().closeInvoice( "M", "A", " ");
//    else if( this.isNotaCreditoB())
//      this.getEpsonDriver().closeInvoice( "M", "B", " ");
//
//    return new AwrOk();
//  }


}
