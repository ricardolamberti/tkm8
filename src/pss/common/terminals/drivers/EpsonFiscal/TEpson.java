package pss.common.terminals.drivers.EpsonFiscal;

import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;

public abstract class TEpson extends JTerminal implements JPrinterInterface {

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
//  private boolean bInitialized=false;
  private EpsonDriver epsonDriver=null;

//  private String[] voucherObjectSavedForRePrints;
//  private long lLastFiscalDoc = 0;
//  private boolean bMustOpenNoFiscal = false;

  public static final String TICKET = "TICKET";
  public static final String SLIP   = "SLIP";

//	private static final String FISCAL = "F";
//	private static final String NFISCAL = "NF";
//	private static final String NFHOMOL = "NFH";
//	private static final String NONE = "";	
//	private String typeDocumentOpen = NONE;
//	private String lastTipoDocumento = null;
	
	public static final int LX300F =1;
  
  public Answer closeDay() throws Exception {
    this.getEpsonDriver().closeDay();
    return new AwrOk();
  }

//  private boolean isFiscal() throws Exception {
//  	return typeDocumentOpen.equals(FISCAL);
//  }
//  
//  private boolean isNoFiscal() throws Exception {
//  	return typeDocumentOpen.equals(NFISCAL);
//  }
//  
//  private boolean isNoFiscalHomo() throws Exception {
//  	return typeDocumentOpen.equals(NFHOMOL);
//  }

//------------------------------------------------------------------------------
// Getters & Setters
//------------------------------------------------------------------------------
//  private long getBaseIvaTg( long lMonto ) throws Exception {
//    double dMontoIva = JbTipoImpuesto.getTipoImpuesto(JbTipoImpuesto.ARG_IVA_TASA_GENERAL, oPos.ObtenerShop().GetProvincia()).GetValor();
//    return Math.round( (double)lMonto / ( 1.0 + dMontoIva/100 ) );
//  }

//  private String getIvaTg()              throws Exception { return JTools.justifyStrings("", String.valueOf((long)(JbTipoImpuesto.getTipoImpuesto(JbTipoImpuesto.ARG_IVA_TASA_GENERAL, oPos.ObtenerShop().GetProvincia()).GetValor()*100)), 4 , '0'); }
//  private String getIvaTg(JFieldReq req) throws Exception { return JTools.justifyStrings("", String.valueOf((long)(req.getDouble(JFieldSetTicket.ITEM_RATETAX, JbTipoImpuesto.ARG_IVA_TASA_GENERAL)*100)), 4 , '0'); }
//  private String getIvaNi()              throws Exception { return JTools.justifyStrings("", String.valueOf((long)(JbTipoImpuesto.getTipoImpuesto(JbTipoImpuesto.ARG_IVA_NO_INSCRIPTO, oPos.ObtenerShop().GetProvincia()).GetValor()*100)), 4 , '0'); }
//  private String getIvaNi(JFieldReq req) throws Exception { return JTools.justifyStrings("", String.valueOf((long)(req.getDouble(JFieldSetTicket.ITEM_RATETAX, JbTipoImpuesto.ARG_IVA_NO_INSCRIPTO)*100)), 4 , '0'); }

//------------------------------------------------------------------------------
// Class Constructors
//------------------------------------------------------------------------------
  public TEpson() throws Exception {
  }
  
//  public boolean inicialize() throws Exception {
//  	if (this.bInitialized) return false;
//  	this.create();
//  	this.openPort();
//  	try {
//      this.fiscalCancel();
//      this.noFiscalCancel();
//      this.cleanFooter();
//	  } catch( Exception e ) {
//	    close();
//	    throw e;
//	  }
//    this.bInitialized = true;
//    return true;
//	}	
  
	
//	public EpsonDriver getEpsonDriver() throws Exception {
//		if (this.epsonDriver==null) this.epsonDriver= new EpsonDriver();
//		return epsonDriver;
//	}



//------------------------------------------------------------------------------
// Boolean Methods
//------------------------------------------------------------------------------
  /*
  public boolean isParalelo() throws Exception {
    return supportDocument() ? true : this.bParalelo;
  } */
//----------------------------------------------------------------------------------------------------------------------
//  private boolean isCardVoucher() throws Exception {
//    return oTipoComp != null
//        && oTipoComp.isCardVoucher();
//  }


//----------------------------------------------------------------------------------------------------------------------
  public Answer printLine( String zLinea ) throws Exception {
//    if( bMustOpenNoFiscal ) {
//      bMustOpenNoFiscal = false;
//      openNoFiscal();
//    }
    this.getEpsonDriver().sendNoFiscalText( zLinea );
    return new AwrOk();
  }

//----------------------------------------------------------------------------------------------------------------------
  public void newLine() throws Exception {
    this.getEpsonDriver().sendNoFiscalText(" ");
  }

	@Override
	public Answer open() throws Exception {
  	return new AwrOk();
	}

	@Override
	public Answer close() throws Exception {
  	return new AwrOk();
	}

	public Answer flush() throws Exception {
		return new AwrOk();
	}

//----------------------------------------------------------------------------------------------------------------------
  public Answer openDoc() throws Exception {
//    this.getEpsonDriver().checkState();
//    this.getEpsonDriver().cancelAllDocs();
//    if( !this.supportDocument()) {// && ( isCardVoucher() || oInterface instanceof JbVentaActiva ) ) {
//      bMustOpenNoFiscal = false;
//    } else {
//      bMustOpenNoFiscal = true;
//    }
    return new AwrOk();
  }

//----------------------------------------------------------------------------------------------------------------------
//  private void openNoFiscal() throws Exception {
//    if( SLIP.equals( getModalidad() ) ) {
//      this.getEpsonDriver().selectSlip();
//      for( int iCount = 0; !getEpsonDriver().isSlipReady() && iCount < 30 * 4; iCount++ ) {
//        Thread.sleep( 250 );
//        getEpsonDriver().selectSlip();
//      }
//      if( !getEpsonDriver().isSlipReady() ) {
//        JPrinterException.SendError( "No se ha ingreado papel para realizar la impresión" );
//      }
//      this.getEpsonDriver().openNoFiscal(true);
//    } else {
//      getEpsonDriver().openNoFiscal(false);
//    }
//  }

////----------------------------------------------------------------------------------------------------------------------
//  public void print( String zSeccion, Object zSource ) throws Exception {
//    if( bMustOpenNoFiscal ) {
//      bMustOpenNoFiscal = false;
//      setFixedHeaderData( zSource );
//      openNoFiscal();
//    }
//    setFixedFooterData( zSource );
//    super.print( zSeccion, zSource );
//  }


//----------------------------------------------------------------------------------------------------------------------


////----------------------------------------------------------------------------------------------------------------------
//  public void cancelItemTicket( String zSection, Object zSource ) throws Exception {
//    if( supportDocument() ) {
//      JFieldReq req = new JFieldReq( oInterface, zSection, zSource );
//      if( oTipoComp.isTicketC() ) {
//        String[] argumentos = loadSendTicketItemArgs( req, true );
//        if( !getMessage().sendTicketItem( argumentos ) ) {
//          JPrinterException.SendError( "No se pudo Anular el ultimo Item del Ticket C - "
//                                       + getMessage().getLastError() );
//        }
//      } else if( isDocument("A") || isDocument("B") ) {
//        String[] argumentos = loadSendInvoiceItemArgs( req, true );
//        if( !getMessage().sendInvoiceItem( argumentos ) ) {
//          JPrinterException.SendError( "No se pudo Anular el ultimo Item del Tique Fiscal - "
//                                       + getMessage().getLastError() );
//        }
//      }
//    } else if( isLayoutRequired() ) {
//      super.cancelItemTicket( zSection, zSource );
//    }
//  }

//----------------------------------------------------------------------------------------------------------------------
  public Answer cancelDoc() throws Exception {
    this.getEpsonDriver().cancelAllDocs();
    return new AwrOk();
  }
  
  public void fiscalPay( String sMop, double fMonto ) throws Exception {
  	String sImporte = String.valueOf( Math.round( fMonto * 100 ) );
  	this.getEpsonDriver().sendInvoicePayment( sMop, sImporte );
  }

//----------------------------------------------------------------------------------------------------------------------
//  public double fiscalPay( String sMop, double fMonto ) throws Exception {
//    
//    calcularRedondeo( percepcionesIVA( false ) );
//    percepcionesIVA( true );
//    String description = "";
//    String sImporte = "";
//
//    JIterator oIter = oEnum.getIterator();
//    while( oIter.hasMoreElements() ) {
//      JInterfasePago oPago = ( JInterfasePago )oIter.nextElement();
//      sImporte = String.valueOf( Math.round( oPago.GetMontoVirtualCotizVenta() * 100 ) );
//      description = oPago.GetDescrip();
//      if( oPago.GetCotizacion() != 1d ) {
//        String simbol = oPago.ObtenerMonedaPais().ObtenerMoneda().GetSimbolo();
//        String temp = " (" + simbol + String.valueOf( oPago.GetMontoElegido() ) + ")";
//        int tempLen = temp.length();
//        int descLen = 24 - tempLen;
//        if( description.length() > descLen )
//          description = description.substring( 0, descLen );
//        description = description + temp;
//      }
//      if( description.length() > 24 )
//        description = description.substring( 0, 25 );
//      if( getDocType().isTicketC() ) {
//        if( !getMessage().sendTicketPayment( description, sImporte ) ) {
//          JPrinterException.SendError( "No se pudo enviar Pago de Tique Fiscal - "
//                                       + getMessage().getLastError() );
//        }
//      } else {
//        if( !getMessage().sendInvoicePayment( description, sImporte ) ) {
//          JPrinterException.SendError( "No se pudo enviar Pago de Tique Fiscal - "
//                                       + getMessage().getLastError() );
//        }
//      }
//    }
//  }

  
//  private void printPayments( JFieldReq req ) throws Exception {
//    if( !oPos.ObtenerVentaActiva().ObtenerVentasPos().ifRecordFound() ) {
//      JPrinterException.SendError( "No se puede Cerrar un Tique sin items" );
//    }
//
//    if( oPos.ObtenerVentaActiva().ObtenerTotalPos().GetMontoTotal() > 5000 ) {
//      JPrinterException.SendError( "No se puede Cerrar un Tique con monto mayor a $5000" );
//    }
//    JList oEnum = oPos.ObtenerVentaActiva().ObtenerPagos();
//    if( oEnum.size() > 5 ) {
//      this.oPos.ObtenerVentaActiva().ObtenerPagos().removeAllElements();
//      JPrinterException.SendError( "Mas de 5 pagos enviados. Todos han sido eliminados" );
//    }
//
//    calcularRedondeo( percepcionesIVA( false ) );
//    percepcionesIVA( true );
//    String description = "";
//    String sImporte = "";
//
//    JIterator oIter = oEnum.getIterator();
//    while( oIter.hasMoreElements() ) {
//      JInterfasePago oPago = ( JInterfasePago )oIter.nextElement();
//      sImporte = String.valueOf( Math.round( oPago.GetMontoVirtualCotizVenta() * 100 ) );
//      description = oPago.GetDescrip();
//      if( oPago.GetCotizacion() != 1d ) {
//        String simbol = oPago.ObtenerMonedaPais().ObtenerMoneda().GetSimbolo();
//        String temp = " (" + simbol + String.valueOf( oPago.GetMontoElegido() ) + ")";
//        int tempLen = temp.length();
//        int descLen = 24 - tempLen;
//        if( description.length() > descLen )
//          description = description.substring( 0, descLen );
//        description = description + temp;
//      }
//      if( description.length() > 24 )
//        description = description.substring( 0, 25 );
//      if( getDocType().isTicketC() ) {
//        if( !getMessage().sendTicketPayment( description, sImporte ) ) {
//          JPrinterException.SendError( "No se pudo enviar Pago de Tique Fiscal - "
//                                       + getMessage().getLastError() );
//        }
//      } else {
//        if( !getMessage().sendInvoicePayment( description, sImporte ) ) {
//          JPrinterException.SendError( "No se pudo enviar Pago de Tique Fiscal - "
//                                       + getMessage().getLastError() );
//        }
//      }
//    }
//  }

//----------------------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------------------
  public Answer closeDoc() throws Exception {
//    this.getEpsonDriver().cancelAllDocs();
    return new AwrOk();
  }


//----------------------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------------------
//  private boolean setFixedFooterData( Object zSource ) throws Exception {
//    int i = 0;
//    if( oLayout != null ) {
//      JIterator oItLineas = oLayout.getPrintZone( JFieldSet.REPORT_FISCAL_FOOTER, zSource ).getIterator();
//      for( ; i < 10 && oItLineas.hasMoreElements(); i++ ) {
//        if( !getMessage().setFooterInfo( i, ( String )oItLineas.nextElement() ) )
//          return false;
//      }
//    }
//    for( ; i < 10; i++ ) {
//      if( !getMessage().setFooterInfo( i, "" ) )
//        return false;
//    }
//    return true;
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  private void calcularRedondeo( long lPercepciones ) throws Exception {
//    for( int i = 0; i < 10; i++ ) {
//      if( getDocType().isTicketC() ) {
//        if( !getMessage().getTicketSubtotal( "N", " " ) ) {
//          JPrinterException.SendError( "No se pudo obtener el Subtotal para Calcular Redondeo - "
//                                       + getMessage().getLastError() );
//        }
//      } else {
//        if( !getMessage().getInvoiceSubtotal( "N", " " ) ) {
//          JPrinterException.SendError( "No se pudo obtener el Subtotal para Calcular Redondeo - "
//                                       + getMessage().getLastError() );
//        }
//      }
//      long totalImpresora = Long.parseLong( getMessage().getAnswerField( 5 ) ) + lPercepciones;
//      long totalPos = Math.round( ( oPos.ObtenerVentaActiva().ObtenerTotalPos().GetMontoTotal() * 100 ) );
//      JDebugPrint.logFiscal( "Redondeo: Total Pos: " + totalPos + " - Total Impresora: " + totalImpresora );
//      if( totalPos == totalImpresora ) {
//        JDebugPrint.logFiscal( "Redondeo: No Requerido" );
//        return;
//      } else if( totalPos > totalImpresora ) {
//        long redondeo = totalPos - totalImpresora;
//        JDebugPrint.logFiscal( "Redondeo Positivo: " + redondeo );
//        redondeoPositivo( redondeo );
//      } else {
//        long redondeo = totalImpresora - totalPos;
//        JDebugPrint.logFiscal( "Redondeo Negativo: " + redondeo );
//        redondeoNegativo( redondeo );
//      }
//    }
//    JPrinterException.SendError( "Total de Impresora != Total Pos." );
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  private void redondeoPositivo( long redondeo ) throws Exception {
//    String sTasaIVATG = "2100";
//    String sTasaIVANI = "1050";
//    if( getDocType().isTicketC() ) {
//      String[] rArg = new String[8];
//      rArg[0] = "AJUSTE POR REDONDEO"; // DESCRIPCION
//      rArg[1] = "00001000"; // CANTIDAD x 1000
//      rArg[2] = String.valueOf( redondeo ); // PRECIO UNITARIO x 100
//      rArg[3] = sTasaIVATG; // IVA x 100
//      rArg[4] = "M"; // M == VENTA
//      rArg[5] = "00001"; // CANTIDAD DE BULTOS
//      rArg[6] = "00000000"; // IMPUESTOS INTERNOS PORCENTUALES
//      rArg[7] = "00000000000000000"; // CONCEPTOS NO GRAVADOS  (IMPUESTOS INTERNOS FIJOS)
//      if( !getMessage().sendTicketItem( rArg ) ) {
//        JPrinterException.SendError( "No se pudo enviar Redondeo Positivo a la Impresora - "
//                                     + getMessage().getLastError() );
//      }
//    } else {
//      String sCNG, sPPU, sDesc;
//      if( isDocument("A") && redondeo == 1 ) {
//        sDesc = "REDONDEO NO GRAVADO";
//        redondeo *= ( long )1000000;
//        sCNG = String.valueOf( redondeo );
//        sPPU = "000000000";
//      } else {
//        sDesc = "AJUSTE POR REDONDEO";
//        if( isDocument("A") )
//          sPPU = String.valueOf( getBaseIvaTg( redondeo ) );
//        else
//          sPPU = String.valueOf( redondeo );
//        sCNG = "00000000000000000";
//      }
//
//      String[] rArg = new String[12];
//      rArg[0] = sDesc; // DESCRIPCION
//      rArg[1] = "00001000"; // CANTIDAD x 1000
//      rArg[2] = sPPU; // PRECIO UNITARIO x 100
//      rArg[3] = sTasaIVATG; // IVA x 100
//      rArg[4] = "M"; // M == VENTA
//      rArg[5] = "00001"; // CANTIDAD DE BULTOS
//      rArg[6] = "00000000"; // IMPUESTOS INTERNOS PORCENTUALES
//      rArg[7] = ""; // COMENTARIO 1
//      rArg[8] = ""; // COMENTARIO 2
//      rArg[9] = ""; // COMENTARIO 3
//      rArg[10] = sTasaIVANI; // IVA RESPONSABLE N.I.
//      rArg[11] = sCNG; // CONCEPTOS NO GRAVADOS
//
//      if( !getMessage().sendInvoiceItem( rArg ) ) {
//        JPrinterException.SendError( "No se pudo enviar Redondeo Positivo a la Impresora - "
//                                     + getMessage().getLastError() );
//      }
//    }
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  private void redondeoNegativo( long redondeo ) throws Exception {
//    String sRedondeo;
//
//    if( isDocument("A") )
//      sRedondeo = String.valueOf(getBaseIvaTg(redondeo));
//    else
//      sRedondeo = String.valueOf(redondeo);
//
//    String sTasaIVATG = getIvaTg();
//    String sTasaIVANI = getIvaNi();
//    if( getDocType().isTicketC() ) {
//      String[] rArg = new String[8];
//      rArg[0] = "POR REDONDEO"; // DESCRIPCION
//      rArg[1] = "00001000"; // CANTIDAD x 1000
//      rArg[2] = sRedondeo; // PRECIO UNITARIO x 100
//      rArg[3] = sTasaIVATG; // IVA x 100
//      rArg[4] = "R"; // R == BONIFICACION
//      rArg[5] = "00001"; // CANTIDAD DE BULTOS
//      rArg[6] = "00000000"; // IMPUESTOS INTERNOS PORCENTUALES
//      rArg[7] = "00000000000000000"; // CONCEPTOS NO GRAVADOS  (IMPUESTOS INTERNOS FIJOS)
//
//      if( !getMessage().sendTicketItem( rArg ) ) {
//        rArg[3] = "0000";
//        if( !getMessage().sendTicketItem( rArg ) ) {
//          JPrinterException.SendError( "No se pudo enviar Redondeo Negativo a la Impresora - "
//                                       + getMessage().getLastError() );
//        }
//      }
//    } else {
//      String[] rArg = new String[12];
//      rArg[0] = "POR REDONDEO"; // DESCRIPCION
//      rArg[1] = "00001000"; // CANTIDAD x 1000
//      rArg[2] = sRedondeo; // PRECIO UNITARIO x 100
//      rArg[3] = sTasaIVATG; // IVA x 100
//      rArg[4] = "R"; // R == BONIFICACION
//      rArg[5] = "00001"; // CANTIDAD DE BULTOS
//      rArg[6] = "00000000"; // IMPUESTOS INTERNOS PORCENTUALES
//      rArg[7] = ""; // COMENTARIO 1
//      rArg[8] = ""; // COMENTARIO 2
//      rArg[9] = ""; // COMENTARIO 3
//      rArg[10] = sTasaIVANI; // IVA RESPONSABLE N.I.
//      rArg[11] = "00000000000000000"; // CONCEPTOS NO GRABADOS x 1000000
//
//      if( !getMessage().sendInvoiceItem( rArg ) ) {
//        rArg[10] = "0000"; // Pruebo sin IVA NI
//        if( !getMessage().sendInvoiceItem( rArg ) ) {
//          rArg[3] = "0000"; // Pruebo sin IVA TG
//          if( isDocument("A") )
//            rArg[2] = String.valueOf( redondeo ); // Ahora es exento
//
//          if( !getMessage().sendInvoiceItem( rArg ) ) {
//            JPrinterException.SendError( "No se pudo enviar Redondeo Negativo a la Impresora - "
//                                         + getMessage().getLastError() );
//          }
//        }
//      }
//    }
//  }

////----------------------------------------------------------------------------------------------------------------------
//  public long percepcionesIVA( boolean bImprimir ) throws Exception {
//    long lPercepIVA = 0;
//    JbPosImpuesto oPosImp = oPos.ObtenerVentaActiva().ObtenerTotalPos().ObtenerImpuestoTotal( JbTipoImpuesto.ARG_IVA_PERCEPCION );
//    if( oPosImp != null ) {
//      lPercepIVA = Math.round( oPosImp.GetValor() * 100 );
//      if( lPercepIVA != 0 && bImprimir ) {
//        if( isDocument("A") ) {
//          if( !getMessage().sendPercept( oPosImp.ObtenerTipoImpuesto().GetDescrip(), lPercepIVA ) ) {
//            JExcepcion.SendError( getMessage().getLastError() );
//          }
//        } else {
//          JExcepcion.SendError( "No se pueden percibir percepciones sin un Ticket Factura A" );
//        }
//      }
//    }
//    return lPercepIVA;
//  }



//----------------------------------------------------------------------------------------------------------------------
  public Answer closeShift() throws Exception {
    this.getEpsonDriver().cleanFooterInfo();
    this.getEpsonDriver().cleanHeaderInfo();
    this.getEpsonDriver().closeJournal( "X", "P" );
    return new AwrOk();
  }

//----------------------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------------------
//  private void openReceipt( JFieldReq req, String tipoComp, String tipoPapel, String tipoLetra ) throws Exception {
//    this.getEpsonDriver().openInvoice( this.loadOpenInvoiceArgs( req, tipoComp, tipoPapel, tipoLetra ));
//  }

//----------------------------------------------------------------------------------------------------------------------
//  private String verifyFormat( Object ob ) {
//    String temp = ob.toString();
//    return temp.length() > 20 ? temp.substring( 0, 20 ) : temp;
//  }

//----------------------------------------------------------------------------------------------------------------------
//  public void printCardVoucher( String zSection, Object zSource ) throws Exception {
//    if( !supportDocument() ) {
//      super.printCardVoucher( zSection, zSource );
//      return;
//    }
//
//    JFieldReq req = new JFieldReq( oInterface, zSection, zSource );
//
//    String[] params = new String[21];
//    params[0] = verifyFormat( req.get( "NOMBRE TARJETA" ) );
//    params[1] = verifyFormat( req.get( "NROTARJETA" ) );
//    params[2] = verifyFormat( req.get( "NOMBRE CLIENTE" ) );
//    params[3] = "000000"; // Exp. Date
//    params[4] = verifyFormat( req.get( "COMERCE_CODE" ) );
//    params[5] = verifyFormat( req.get( "TICKET_NUM" ) );
//    params[6] = String.valueOf( ( char )0x7f );
//    params[7] = String.valueOf( ( char )0x7f );
//    params[8] = verifyFormat( req.get( "DESC.OPERACION" ) );
//    params[9] = JTools.LPad( String.valueOf( ( long ) ( req.getDouble( "MONTO_TOTAL" ) * 100 ) ), 11, "0" );
//    params[10] = verifyFormat( req.get( "CUOTAS" ) );
//    params[11] = verifyFormat( req.get( "SIMBOLO MONEDA" ) );
//    params[12] = verifyFormat( req.get( "TERM_ID" ) );
//    params[13] = verifyFormat( req.get( "NRO_LOTE" ) );
//    params[14] = String.valueOf( ( char )0x7f );
//    params[15] = String.valueOf( ( char )0x7f );
//    params[16] = String.valueOf( ( char )0x7f );
//    params[17] = String.valueOf( lLastFiscalDoc );
//    params[18] = "P";
//    params[19] = "P";
//    params[20] = "P";
//
//    if( oTipoComp.canRePrint() ) {
//      this.canRePrint = true;
//      voucherObjectSavedForRePrints = params;
//    }
//    printVoucher( params );
//  }

//----------------------------------------------------------------------------------------------------------------------
//  private void printVoucher( String[] zParams ) throws Exception {
//    printVoucher( zParams, 2 );
//  }

//----------------------------------------------------------------------------------------------------------------------
//  private void printVoucher( String[] zParams, int howManyCopies ) throws Exception {
//    if( !getMessage().printDNFHCreditCard( zParams, howManyCopies ) ) {
//      JPrinterException.SendError( "Error Imprimiendo Voucher - "
//                                   + getMessage().getLastError() );
//    }
//  }

//----------------------------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------------------------
//  private String getPrinterStatus() throws Exception {
//    return generateCompleteBinaryString( Integer.toBinaryString( ( int )hexaToDecimal( getMessage().getPrinterStatus() ) ) );
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  private String getFiscalStatus() throws Exception {
//    return generateCompleteBinaryString( Integer.toBinaryString( ( int )hexaToDecimal( getMessage().getFiscalStatus() ) ) );
//  }

//----------------------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//  private String[] loadSendInvoiceItemArgs( JFieldReq req, boolean anular ) throws Exception {
//
//    String[] args = new String[12];
//    args[0] = sTexto.length() > 23? sTexto.substring(0,23) : sTexto; // DESCRIPCION
//    args[1] = sCantidad; // CANTIDAD x 1000
//    args[2] = sBasico; // PRECIO UNITARIO x 100
//    args[3] = sTasaIVATG; // IVA x 100
//    args[4] = modo; // M == VENTA      m == ANULACION VENTA
//    args[5] = "00001"; // CANTIDAD DE BULTOS
//    args[6] = sImpIntPorc; // IMPUESTOS INTERNOS PORCENTUALES
//    args[7] = consignados1; // COMENTARIO 1
//    args[8] = consignados2; // COMENTARIO 2
//    args[9] = consignados3; // COMENTARIO 3
//    args[10] = sTasaIVANI; // IVA RESPONSABLE N.I.
//    args[11] = sImpIntFijo; // CONCEPTOS NO GRAVADOS  (IMPUESTOS INTERNOS FIJOS)
//    JDebugPrint.logFiscal( "Articulo: " + args[0] + " Cantidad: " + sCantidad + " PPU : " + sBasico
//                           + " IVA: " + sTasaIVATG + " IVANI: " + sTasaIVANI + " Modo: " + modo
//                           + " IIP: " + sImpIntPorc + " IIF: " + sImpIntFijo );
//    return args;
//  }

//----------------------------------------------------------------------------------------------------------------------
//  private String[] loadSendTicketItemArgs( JFieldReq req, boolean anular ) throws Exception {
//    String modo = anular ? "m" : "M";
//    String sTasaIVATG = getIvaTg( req );
//
//    double dImpIntFijos = calcularImpIntFijos( req );
//    double dImpIntPorc = calcularTasaAjusteVariable( req );
//    if( dImpIntFijos != 0 ) {
//      dImpIntFijos += calcularImpIntPorc( req );
//      dImpIntPorc = 0;
//    }
//
//    String sImpIntPorc = JTools.justifyStrings( "", String.valueOf( ( long ) ( dImpIntPorc * 100000000 ) ), 8, '0' );
//    String sImpIntFijo = JTools.justifyStrings( "", String.valueOf( ( long ) ( dImpIntFijos * 100000000 ) ), 17, '0' );
//
//    String sTexto = "Mercaderia";
//    try {
//      JbArticulo oArt = new JbArticulo();
//      oArt.Read( req.getString( JFieldSetTicket.ITEM_CODIGO ) );
//      sTexto = oArt.GetDescrAbrev();
//    } catch( Exception e ) {
//      sTexto = req.getString( JFieldSetTicket.ITEM_DESCR );
//    }
//
//    double dCantidad = req.getDouble( JFieldSetTicket.ITEM_CANT );
//    double dReal = req.getDouble( JFieldSetTicket.ITEM_MONTO_P_UNIT );
//
//    /* Si precio unitario tiene 3 decimales, mando total y cantidad uno */
//    if( ( Math.round( dReal * 1000 ) % 10 ) != 0 && itemPriceDecimals() == 2 ) {
//      sTexto = String.valueOf( Math.abs( dCantidad ) ) + " " + req.getString( JFieldSetTicket.ITEM_MEDIDA_ID )
//        + "x$" + String.valueOf( dReal ) + " " + sTexto;
//      dCantidad = dCantidad > 0 ? 1 : -1;
//      dReal = req.getDouble( JFieldSetTicket.CANT_X_P_UNIT );
//    }
//
//    String sCantidad = String.valueOf( Math.abs( ( long ) ( dCantidad * 1000 ) ) );
//    String sReal;
//    if( itemPriceDecimals() == 2 ) {
//      sReal = String.valueOf( Math.round( dReal * 100 ) );
//    } else {
//      sReal = String.valueOf( Math.round( dReal ) );
//      int iPos = sReal.indexOf(".");
//      while( iPos > 0 && iPos + itemPriceDecimals() <= sReal.length() ) {
//        sReal += "0";
//      }
//    }
//
//    String[] args = new String[8];
//    args[0] = sTexto.length() > 20 ? sTexto.substring( 0, 20 ) : sTexto; // DESCRIPCION
//    args[1] = sCantidad; // CANTIDAD x 1000
//    args[2] = sReal; // PRECIO UNITARIO x 100
//    args[3] = sTasaIVATG; // IVA x 100
//    args[4] = modo; // M == VENTA      m == ANULACION VENTA
//    args[5] = "00001"; // CANTIDAD DE BULTOS
//    args[6] = sImpIntPorc; // IMPUESTOS INTERNOS PORCENTUALES
//    args[7] = sImpIntFijo; // CONCEPTOS NO GRAVADOS  (IMPUESTOS INTERNOS FIJOS)
//    JDebugPrint.logFiscal( "Articulo: " + args[0] + " Cantidad: " + sCantidad + " PPU : " + sReal
//                           + " IVA: " + sTasaIVATG + " Modo: " + modo
//                           + " IIP: " + sImpIntPorc + " IIF: " + sImpIntFijo );
//    return args;
//  }

//----------------------------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------------------------




//----------------------------------------------------------------------------------------------------------------------
//  public void openDocument() throws Exception {
//    bInitializePrintCopies = true;
//    openDoc();
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  public void cancelDocument() throws Exception {
//    cancelDoc();
//  }
//
////----------------------------------------------------------------------------------------------------------------------
//  public void closeDocument() throws Exception {
//    closeDoc();
//    printCopies();
//  }


//	public void openPort() throws Exception {
//  	this.close();
//  	EpsonDriver driver = new EpsonDriver();
//  	JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.getConnectinString(), ';');
//    String typeConnection = tokens.nextToken();
//    if (typeConnection.equalsIgnoreCase("SERIAL")) {
//    	String idPort=  tokens.nextToken();
//    	String baud=  tokens.nextToken();
//  		this.getEpsonDriver().open(idPort, Integer.parseInt(baud));
//    }
//    else
//  		JExcepcion.SendError("Modo de conexion no manejado ["+typeConnection+"]" );
//  }

  public boolean hasEpsonDriver() {
  	return this.epsonDriver!=null;
  }

//----------------------------------------------------------------------------------------------------------------------
  protected EpsonDriver getEpsonDriver() throws Exception {
    if( this.epsonDriver != null ) return this.epsonDriver;
    EpsonDriver driver = new EpsonDriver();
  	JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.getConnectinString(), ';');
    String typeConnection = tokens.nextToken();
    if (!typeConnection.equalsIgnoreCase("SERIAL"))
    	JExcepcion.SendError("Tipo de coneccion invalida");
  	String idPort=  tokens.nextToken();
  	String baud=  tokens.nextToken();
		try {
			driver.open(idPort, Integer.parseInt(baud));
      driver.checkState();
//      driver.cleanFooterInfo();
//      driver.cleanHeaderInfo();
      driver.cancelAllDocs();
    } catch( Throwable ex ) {
      this.close();
      JExcepcion.SendError("Error al abrir el puerto: "+ex.getMessage());
    }
    
    return (this.epsonDriver=driver);
  }


//  protected int itemPriceDecimals() {
//    return 2;
//  }
//  protected boolean permiteNombreClienteVacio() {
//    return true;
//  }
  
  public void fiscalOpen(String tipoDoc, String [] args) throws Exception {
  	this.getEpsonDriver().checkState(); // por si hay un comp abierto
  	this.getEpsonDriver().cancelAllDocs();
		this.getEpsonDriver().openInvoice(args);

//		long currDoc = this.getEpsonDriver().getCurrentDocNumber();
//    if (currDoc!=docNumber)
//    	JExcepcion.SendError("Desincronización Nro Comprobante: " + currDoc);
//		lastTipoDocumento=tipoDoc;
  }

//  public void closeAny() throws Exception {
//  	if (this.isFiscal())
//  		this.getEpsonDriver().closeInvoice(lastTipoDocumento.substring(0,1), lastTipoDocumento.substring(1,2), "  ");
//  	if (this.isNoFiscal())
//  		this.getEpsonDriver().closeNoFiscal("T");
//		typeDocumentOpen=NONE;
//  }

	public Answer skeepLines(int lines) throws Exception {
//		this.paperAdvance(lines);
    return new AwrOk();
	}
	
	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}

  public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new TEpsonPrinterAdapter(this);
  }


}
