package pss.common.terminals.drivers.EpsonTM88III;

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

public class TEpsonT88IIIPrinterAdapter extends JPrinterAdapter {
	
	public static final String BF = "BF";

//------------------------------------------------------------------------------
// Class Constructors
//------------------------------------------------------------------------------
  public TEpsonT88IIIPrinterAdapter(JTerminal terminal) throws Exception {
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
  	return this.getEpson();
  }
  public TEpsonT88III getEpson() throws Exception {
  	if (((TEpsonT88III)terminal).inicialize()) {
  		this.actualizarFormasPago();
  	}
  	return (TEpsonT88III)terminal;
  }
  
  @Override
	public boolean hasCierreZ() throws Exception {
  	return true;
  }
  
  @Override
	protected void loadSupportedDocuments() throws Exception {
  	this.addSupportedDocs(BF, "Boleta Fiscal");
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
    if( getEpson().isFiscalOpenStatus() ) {
//      JRecords oItems = oPos.ObtenerVentaActiva().ObtenerVentasPos();
//      oItems.firstRecord();
//      while( oItems.nextRecord() ) {
//        BizItem oVentaPos = (BizItem) oItems.getRecord();
//        if( !oVentaPos.isAnulado() && oVentaPos.GetCantidad() > 0 ) {
//          getMessage().fiscalCancelItem(oVentaPos);
//        }
//      }
    	this.getEpson().fiscalCancel();
    }
    return super.cancelDocument();
  }
    
  @Override
	public void generate(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.dataStartTicket(section, source);} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.dataPrintItemTicket(section, source);} else
    if (section.equals(JFieldSetTicket.BODY_ITEM)) {	this.dataPrintItemTicket(section, source);} else
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.dataPrintPayHeader(section, source);} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.dataPrintPay(section, source);} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.dataFinishTicket(section, source);}
  }
  @Override
	public Answer print(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) { this.startTicket(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.BODY_TAX)) {	this.printItemTicket(section, source); return new AwrOk();} else 
  	if (section.equals(JFieldSetTicket.BODY_ITEM)) {	this.printItemTicket(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.PAY_HEADER)) { this.printPayHeader(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.PAY_BODY)) { this.printPay(section, source); return new AwrOk();} else
  	if (section.equals(JFieldSetTicket.REPORT_FOOTER)) { this.finishTicket(section, source); return new AwrOk();} else 
  	return super.print(section, source);
  }
  
  public void dataStartTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	rl.addField(req.with(JFieldSetTicket.TIPO_COMPROBANTE_DESC));
  	rl.addField(req.with(JFieldSetTicket.NRO_COMPROBANTE));
//  	rl.addField(req.with(JFieldSetTicket.SUPPORT_DOCUMENT));
//  	rl.addField(req.with(JFieldSetTicket.SUPPORT_DOCUMENT));
  	this.recordLine(rl.getXmlLine());
  }
  public void dataPrintItemTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	rl.addField(req.with(JFieldSetTicket.ITEM_CODIGO));
  	rl.addField(req.with(JFieldSetTicket.ITEM_DESCR));
  	rl.addField(req.with(JFieldSetTicket.ITEM_CANT));
  	rl.addField(req.with(JFieldSetTicket.ITEM_P_UNIT));
  	rl.addField(req.with(JFieldSetTicket.ITEM_RATENATTAX));
  	rl.addField(req.with(JFieldSetTicket.ITEM_P_UNIT_ORIG));
  	rl.addField(req.with(JFieldSetTicket.ITEM_PRICE_PRESITION));
  	rl.addField(req.with(JFieldSetTicket.ITEM_NEGATIVE));
  	this.recordLine(rl.getXmlLine());
  }
  
  public void dataPrintPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	rl.addField(req.with(JFieldSetTicket.PAY_MONTO_ORIGINAL));
  }
  
  public void dataPrintPay(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	rl.addField(req.with(JFieldSetTicket.PAY_FORMA_PAGO_PADRE));
  	rl.addField(req.with(JFieldSetTicket.PAY_MONTO_ORIGINAL));
  	this.recordLine(rl.getXmlLine());
  }
  
  public void dataFinishTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	JRecordLine rl = new JRecordLine(section);
  	rl.addField(req.with(JFieldSetTicket.VUELTO_CONSUMIDO));
  	rl.addField(req.with(JFieldSetTicket.VUELTO_CONSUMIDO_DESCR));
  	this.recordLine(rl.getXmlLine());
  }

//----------------------------------------------------------------------------------------------------------------------
  public void startTicket(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
		this.getEpson().cleanHeader();
		this.getEpson().setHeaderInfo( JTools.centerString(req.getString(JFieldSetTicket.TIPO_COMPROBANTE_DESC), 42,' '), true );
		this.getEpson().fiscalOpen();
		//     this.setIsLayoutRequired(false);
		
		long lCurTicket = this.getEpson().getCurrFiscalTicket( true );
		if( lCurTicket == 0 ) {
		  JPrinterException.SendError( "Error capturando número de boleta fiscal de la impresora" );
//		} else if( lCurTicket != req.getLong(JFieldSetTicket.NRO_COMPROBANTE) ) {//.oComprobantePrincipal.lNroComprobante ) {
//		 JPrinterException.SendError( "Error en número de boleta fiscal informada por impresora - cancele la operación" );
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
    int iPresition    = req.getInt(JFieldSetTicket.ITEM_PRICE_PRESITION);
    boolean negative  = req.getBool(JFieldSetTicket.ITEM_NEGATIVE);

    PssLogger.logFiscal( "Fiscal Printer - Item fiscal:" + sCodigo + " cantidad:" + dCantidad + " monto unitario: " + dMontoUnit + " iva prct:" + dPrctImp );

    if (negative)
    	JPrinterException.SendError("Anulación de item no implementada");
    this.getEpson().fiscalItem( false, sArtDescr, iPresition, sCodigo, dCantidad, dMontoUnit, dPrecioOrig, dPrctImp );
  }

  public void printPayHeader(String section, Object source) throws Exception {
  	JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
  	int cantPayments = req.getInt(JFieldSetTicket.PAY_MONTO_ORIGINAL);
    if( cantPayments >= 5 ) {
      JPrinterException.SendError("Mas de 5 pagos enviados");
    }
  }
  
	public void printPay(String section, Object source) throws Exception {
		JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
		
		String sDesc  = req.getString(JFieldSetTicket.PAY_FORMA_PAGO_PADRE);
		double dMonto = req.getDouble(JFieldSetTicket.PAY_MONTO_ORIGINAL);

		this.getEpson().fiscalPay( false, sDesc, dMonto);

    // Si no queda nada por pagar, salgo
    if( Long.parseLong( this.getEpson().getAnswerField( 5 ) ) == 0 ) {
      return;
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  public void finishTicket(String section, Object source) throws Exception {
    JFieldReq req = new JFieldReq(this.getFieldInterface(), section, source);
//    printPayments(req);
    this.printDonation(req);      
    this.setSaleFooter();
    getEpson().fiscalClose( false, true );
  }

//----------------------------------------------------------------------------------------------------------------------
  private void actualizarFormasPago() throws Exception {
  	this.getEpson().leerFormasPago();
    this.getEpson().agregarFormaPago( "Efectivo" );
//    JRecords oFormasPago = new JRecords(BizFormaPago.class);
//    oFormasPago.addFilter("pais", BizUsuario.getUsr().getCountry());
//    oFormasPago.addFilter("dependiente", "N");
//    oFormasPago.readAll();
//    oFormasPago.firstRecord();
//    while( oFormasPago.nextRecord() ) {
//      BizFormaPago oPago = (BizFormaPago)oFormasPago.getRecord();
//      getEpson().agregarFormaPago(oPago.GetDescrip());
//    }
    this.getEpson().loadPaymentFiscalJournal();
  }
//----------------------------------------------------------------------------------------------------------------------
  @Override
	public long getCurrentNum() throws Exception {
    if( !this.supportDocument() ) return 0L;
    long lCurrTicket = this.getEpson().getCurrFiscalTicket( true );

    if( lCurrTicket > 0 ) {
      return lCurrTicket + 1;
    }
    else {
      return getEpson().getNextFiscalTicket();
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  private void setSaleFooter() throws Exception {
    getEpson().cleanFooter();
//    JList oMsgs = oPos.ObtenerVentaActiva().GetLeyendasConsignatarios();
//    JIterator oIterator = oMsgs.getIterator();
//    while(oIterator.hasMoreElements()) {
//      getEpson().setFooterInfo( (String)oIterator.nextElement(), false );
//    }
    getEpson().setFooterInfo( JTools.centerString("GRACIAS POR SU COMPRA", 42, ' '), true );
  }
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
  

  @Override
	public boolean checkDocOpen() throws Exception {
  	if( !this.supportDocument() ) return false;
    try { // primero se intenta cominucar con la impresora
      return getEpson().isFiscalOpenStatus();
    } catch (Exception e) {
      return getEpson().isFiscalOpen();
    }
  }

  private void printDonation(JFieldReq req) throws Exception {
  	double monto = req.getDouble(JFieldSetTicket.VUELTO_CONSUMIDO);
  	if (monto==0d) return;
  	String donacionDescr = req.getString(JFieldSetTicket.VUELTO_CONSUMIDO_DESCR);
  	getEpson().printDonation(donacionDescr, monto );
  }

}

