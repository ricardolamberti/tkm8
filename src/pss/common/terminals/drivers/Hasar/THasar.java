package pss.common.terminals.drivers.Hasar;

import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCashDrawerInterface;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrBoolean;
import pss.common.terminals.messages.answer.AwrError;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;


public abstract class THasar extends JTerminal implements JPrinterInterface, JCashDrawerInterface {

	HasarDriver hasarDriver = null;
	int handleHasar = -1;
	boolean automaticPay = true;
	private static final String FISCAL = "F";
	private static final String NFISCAL = "NF";
	private static final String NFHOMOL = "NFH";
	private static final String NONE = "";
	private String typeDocumentOpen = NONE;
	
	public static final int IF_P262_100 =1;
	public static final int IF_P272_100 =3;
	public static final int IF_P950_100 =4;
	public static final int IF_P951_100 =5;
	public static final int IF_PPR4_100 =6;
	public static final int IF_PPR5_100 =7;
	public static final int IF_PPR5_201 =8;
	public static final int IF_P614_100 =9;
	public static final int IF_P615_100 =10;
	public static final int IF_P715_100 =11;
	public static final int IF_P715_201 =12;
	public static final int IF_P320_100 =13;
	public static final int IF_PPL8_100 =14;
	public static final int IF_PPL8_201 =15;
	public static final int IF_PJ20_100 =16;
	public static final int IF_P321_100 =17;
	public static final int IF_P322_100 =18;
	public static final int IF_P322_201 =19;
	public static final int IF_PPL9_100 =20;
	public static final int IF_P330_100 =21;
	public static final int IF_P330_201 =22;
	public static final int IF_P425_100 =23;
	public static final int IF_P330_202 =24;
	public static final int IF_P425_201 =25;
	public static final int IF_P435_100 =26;
	public static final int IF_P435_101 =27;

	private static final byte ESTABLECER_PUERTO_SERIE='a';
	private static final byte ESTABLECER_SOCKET_TCP='b';
	private static final byte ESTABLECER_SOCKET_UDP='c';
	private static final byte OBTENER_VERSION='d';
	private static final byte OBTENER_DATOS_INICIALIZACION='e';
	private static final byte OBTENER_DATOS_MEMORIA_TRABAJO='f';
	private static final byte CAMBIAR_RESPONSABILIDAD='g';
	private static final byte ESTADO_IMPRESOR='h';
	private static final byte DESCRIPCION_ESTADO_IMPRESOR='i';
	private static final byte DESCRIPCION_BITS_ESTADO_IMPRESOR='j';
	private static final byte ESTADO_FISCAL='k';
	private static final byte DESCRIPCION_ESTADO_FISCAL='l';
	private static final byte DESCRIPCION_BITS_ESTADO_FISCAL='m';
	private static final byte ULTIMO_DOCUMENTO_FISCAL_BC='n';
	private static final byte PAGINAS_DE_ULTIMO_DOCUMENTO='o';
	private static final byte CAI_ULTIMO_DOCUMENTO_A='p';
	private static final byte CAJON_ABIERTO='q';
	private static final byte CORTAR_PAPEL='r';
	private static final byte MOSTRAR_MENSAJE_DISPLAY='s';
	private static final byte ESTABLECER_FECHA_Y_HORA_FISCAL='t';
	private static final byte IMPRIMIR_CODIGO_DE_BARRAS='u';
	private static final byte REIMPRIMIR_COMPROBANTE='v';
	private static final byte FINALIZAR_COMUNICACIONES='w';
	private static final byte ESPECIFICAR_NOMBRE_FANTASIA='z';
	private static final byte ESPECIFICAR_ENCABEZADO_COLA='A';
	private static final byte OBTENER_ENCABEZADO_COLA='B';
	private static final byte CONFIGURAR_CONTROLADOR='C';
	private static final byte REPORTE_Z='D';
	private static final byte REPORTE_X='E';
	private static final byte REPORTE_Z_POR_FECHAS='F';
	private static final byte REPORTE_Z_POR_NUMEROS='G';
	private static final byte CAPACIDAD_RESTANTE='H';
	private static final byte REPORTE_TABLAS_IVA='I';
	private static final byte ABRIR_DOCUMENTO_FISCAL='J';
	private static final byte IMPRIMIR_TEXTO_FISCAL='K';
	private static final byte IMPRIMIR_ITEM='L';
	private static final byte DESCUENTO_ULTIMO_ITEM='M';
	private static final byte DESCUENTO_GENERAL='N';
	private static final byte DEVOLUCION_DESCUENTO='O';
	private static final byte SUB_TOTAL='P';
	private static final byte IMPRIMIR_PAGO='Q';
	private static final byte ABRIR_DOCUMENTO_NO_FISCAL='R';
	private static final byte IMPRIMIR_TEXTO_NO_FISCAL='S';
	private static final byte IMPRIMIR_ITEM_EN_REMITO='T';
	private static final byte IMPRIMIR_ITEM_EN_CUENTA='U';
	private static final byte IMPRIMIR_ITEM_EN_COTIZACION='V';
	private static final byte DETALLE_RECIBO='W';
	private static final byte IMPRIMIR_INFO_EN_DNFH='X';
	private static final byte ULTIMO_NOTA_CREDITO_A='Z';
	private static final byte ULTIMO_NOTA_CREDITO_BC='1';
	private static final byte ULTIMO_DOCUMENTO_FISCAL_A='2';
	private static final byte ULTIMO_CANCELADO='3';
	private static final byte ULTIMO_REMITO='4';
	private static final byte IMPRIMIR_VOUCHER='5';
	private static final byte ESPECIFICAR_IMP_INTERNOS='6';
	private static final byte ESPECIFICAR_MOD_DESCRIP_LARGAS='7';
	private static final byte ESPECIFICAR_MOD_PRECIO_BASE='8';
	private static final byte ESPECIFICAR_MOD_DISPLAY='9';
	private static final byte ESPECIFICAR_INFO_REMITO_COMPR_ORIGINAL='0';
	private static final byte ESPECIFICAR_IVA_NO_INSCRIPTO='+';
	private static final byte ESPECIFICAR_PERCEPCION_GLOBAL='*';
	private static final byte ESPECIFICAR_PERCEPCION_POR_IVA=']';
	private static final byte ABRIR_DNFH='[';
	private static final byte ABRIR_CAJON='-';
	private static final byte AVANZAR_PAPEL='.';
	private static final byte CERRAR_DF='?';
	private static final byte CERRAR_DNF='!';
	private static final byte CERRAR_DNFH='$';
	private static final byte DATOS_CLIENTE='=';
	private static final byte PRIMER_NUMERO_DOCUMENTO_ACTUAL='&';
	private static final byte CANCELAR_COMPROBANTE_FISCAL='#';
	private static final byte CANCELAR_COMPROBANTE=',';

	private boolean bInitialized = false;
  private int iHeaderLinesUsed = 5;
  private int iFantasyLinesUsed = 2;
  
  private int iFooterLinesUsed = 10;
	private String lastTipoDocumento = "";

  public boolean inicialize() throws Exception {
  	if (this.bInitialized) return false;
  	create();
  	this.openPort();
  	try {
      this.fiscalCancel();
      this.noFiscalCancel();
      this.cleanFooter();
	  } catch( Exception e ) {
	    close();
	    throw e;
	  }
    this.bInitialized = true;
    return true;
	}	

  public THasar() throws Exception {
	}

	public abstract void create() throws Exception;
	
	protected void create(int model) throws Exception {
		handleHasar = getHasarDriver().crearImpresora(model);
	}
 
  
	@Override
	public Answer close() throws Exception {
		closePort();
		return null;
	}

	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
		this.addDriver(JTerminal.D_CASH_DRAWER);
	}

	@Override
	public Answer open() throws Exception {
  	return new AwrOk();
	}

	public Answer cancelDoc() throws Exception {
		this.cleanHeader();
		this.cleanFooter();
		this.fiscalCancel(); 
		this.noFiscalCancel(); 
		return new AwrOk();
	}

	public Answer closeDay() throws Exception {
		if (!cierreZ()) 
			return new AwrError();
		this.cleanHeader();
		this.cleanFooter();
		this.setDateAndTime();
		return new AwrOk();
	}

	public Answer closeDoc() throws Exception {
		cerrarDocumento(1); 
		return new AwrOk();
	}

	public Answer closeShift() throws Exception {
		this.cleanHeader();
		this.cleanFooter();
		if (!cierreX()) 
			return new AwrError();
		return new AwrOk();
	}

	public Answer flush() throws Exception {
		
		return null;
	}

	public Answer openDoc() throws Exception {
    this.fiscalCancel();
    return new AwrOk();
	}

  public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new THasarPrinterAdapter(this);
  }
  
//----------------------------------------------------------------------------------------------------------------------
  public Answer printLine(String zLinea) throws Exception {
    this.noFiscalSendText(zLinea);
    return new AwrOk();
  }


	public Answer skeepLines(int lines) throws Exception {
		this.paperAdvance(lines);
    return new AwrOk();
	}

	
  private void setDateAndTime() throws Exception {
    getHasarDriver().enviarComandoFiscal( handleHasar, ESTABLECER_FECHA_Y_HORA_FISCAL, "" +JDateTools.CurrentDate("dd-MM-yyyy") + HasarDriver.SEP + JDateTools.CurrentTime("HH:mm:ss") );
  }

  public Answer openCashDrawer() throws Exception {
    try {
    	getHasarDriver().enviarComandoFiscal(handleHasar, ABRIR_CAJON, "");
   } catch( Exception e ) {}
    return new AwrOk();
  }
	public Answer isCashDrawerOpen() throws Exception {
		boolean ok=false; 
    try {
      String resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_IMPRESOR, "E"+HasarDriver.SEP+"S" );
    	ok=HasarDriver.getParamBoolean(resp, 1);
    } catch( Exception e ) {}
    return new AwrBoolean(ok);
  }

//----------------------------------------------------------------------------------------------------------------------
	public void cleanFantasy() throws Exception {
		for (int i = 1; i <= iFantasyLinesUsed && i <= 2; i++) {
			setFantasyInfo(i, "");
		}
		iFantasyLinesUsed = 0;
	}

	public void cleanHeader() throws Exception {
    for( int i = 1; i <= iHeaderLinesUsed && i <= 5; i++ ) {
      setHeaderInfo( i, "" );
    }
    iHeaderLinesUsed = 0;
  }

  public void cleanFooter() throws Exception {
    for( int i = 1; i <= iFooterLinesUsed; i++ ) {
      setFooterInfo( i, " " );
    }
    iFooterLinesUsed = 0;
  }
  public boolean setFantasyInfo(String sLine) throws Exception {
		iFantasyLinesUsed++;
		if (iFantasyLinesUsed <= 5) {
			return setFantasyInfo(iFantasyLinesUsed, sLine);
		} else {
			return false;
		}
	}
  public boolean setHeaderInfo( String sLine) throws Exception {
    iHeaderLinesUsed++;
    if( iHeaderLinesUsed <= 5 ) {
      return setHeaderInfo( iHeaderLinesUsed, sLine );
    } else {
      return false;
    }
  }
  public boolean setFooterInfo( String sLine ) throws Exception {
  	iFooterLinesUsed++;
    if( iFooterLinesUsed <= 5 ) {
      return setFooterInfo( iFooterLinesUsed, sLine );
    } else {
      return false;
    }
  } 


  public static String refill( double fNumber, int iLeft, int iRight ) {
    String sNumberLeft = String.valueOf( (int) ( Math.abs( fNumber ) ) );
    String sNumberRight = String.valueOf( Math.round( ( fNumber - (int) fNumber ) * Math.pow( 10, iRight ) ) );
    sNumberLeft = JTools.LPad( sNumberLeft, iLeft, "0" );
    if( iRight != 0 ) {
      sNumberRight = JTools.LPad( sNumberRight, iRight, "0" );
    } else {
      sNumberRight = "";
    }
    return ( fNumber < 0 ? "-" : "" ) + sNumberLeft + sNumberRight;
  }
	public HasarDriver getHasarDriver() throws Exception {
		if (this.hasarDriver==null) {
			this.hasarDriver= new HasarDriver();
			this.inicialize();
		}
//		if (handleHasar==-1) throw new Exception("Impresora no debidamente inicializada");
		return hasarDriver;
	}
	
	public void setHasarDriver(HasarDriver value) {
		this.hasarDriver=value;
	}


	//----------------------------------------------------------------------------------------------------------------------
	boolean item( String descr, int presition, String Codigo, double dCantidad, double dPrecio, double dPrecioOrig, double dTasaImp, double dImpInterno ) throws Exception {
		if (typeDocumentOpen.equals(FISCAL) || typeDocumentOpen.equals(NFHOMOL)) {
			return fiscalItem(  descr,  presition,  Codigo,  dCantidad,  dPrecio,  dPrecioOrig,  dTasaImp,  dImpInterno );
		}
		return false;
	}
  boolean fiscalItem( String descr, int presition, String Codigo, double dCantidad, double dPrecio, double dPrecioOrig, double dTasaImp, double dImpInterno ) throws Exception {
   	String enNegativo;
  	String sCantidad = ""+ Math.abs( dCantidad );
    String sPrecio = ""+ Math.abs(dPrecio);
    String sTasa = ""+ dTasaImp;
    String sImpInterno = ""+ dImpInterno;
    
   	enNegativo = ( dCantidad < 0 )?"S":"N";
   	boolean comoDescuento = ( dPrecio < 0 );

//    String sLineaExtra = "";
    String sTexto = descr==null ? "Mercaderia" : descr;
//    if( JTools.rd( dPrecioOrig ) != JTools.rd( dPrecio ) ) {
//      String sCambioDescrip="";
//      if(dPrecioOrig<dPrecio) sCambioDescrip = "Recargo";
//      else
//        if(dPrecioOrig>dPrecio) sCambioDescrip = "Descuento";
//      String sPrecOrig = JNumberFormatter.formatNumberToString(dPrecioOrig);
//      String sPrecFinal = JNumberFormatter.formatNumberToString(dPrecio);
//      sLineaExtra = sCambioDescrip + ": $" + sPrecOrig + " => $" + sPrecFinal;
//    }
    String resp="";
    if (!comoDescuento)
    	resp= getHasarDriver().enviarComandoFiscal( handleHasar, IMPRIMIR_ITEM, sTexto + HasarDriver.SEP + sCantidad + HasarDriver.SEP + sPrecio + HasarDriver.SEP + sTasa + HasarDriver.SEP + sImpInterno + HasarDriver.SEP + enNegativo);
    else
    	resp= getHasarDriver().enviarComandoFiscal( handleHasar, DESCUENTO_GENERAL, sTexto + HasarDriver.SEP + sPrecio + HasarDriver.SEP + " " + HasarDriver.SEP + " " + HasarDriver.SEP + " " + HasarDriver.SEP +  "S");
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal IMPRIMIR ITEM " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	return true;
  }
  
	double CantidadItemsVendidos;
	double MontoVentas;
	double MontoIVA;
	double MontoPagado;
	double MontoIVANoInscripto;
	double MontoImpuestosInternos;

	double fiscalSubTotal(boolean print ) throws Exception {
  	automaticPay = false;
    String resp= getHasarDriver().enviarComandoFiscal( handleHasar, SUB_TOTAL, print?"S":"N");
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal SUB TOTAL " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	
  	CantidadItemsVendidos = HasarDriver.getParamDouble(resp, 1);
  	MontoVentas = HasarDriver.getParamDouble(resp, 2);
  	MontoIVA = HasarDriver.getParamDouble(resp, 3);
  	MontoPagado = HasarDriver.getParamDouble(resp, 4);
  	MontoIVANoInscripto = HasarDriver.getParamDouble(resp, 5);
  	MontoImpuestosInternos = HasarDriver.getParamDouble(resp, 6);

  	return MontoVentas;
  }  
  
  double fiscalPay( String sMop, double fMonto ) throws Exception {
  	if (! isFiscal()) return 0;
  	automaticPay = false;
    String sMonto = ""+fMonto;
    String resp= getHasarDriver().enviarComandoFiscal( handleHasar, IMPRIMIR_PAGO, sMop + HasarDriver.SEP + sMonto );
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal IMPRIMIR_PAGO " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	return HasarDriver.getParamDouble(resp, 1);
  }
  
  private boolean isFiscal() throws Exception {
  	return typeDocumentOpen.equals(FISCAL);
  }
  
  private boolean isNoFiscal() throws Exception {
  	return typeDocumentOpen.equals(NFISCAL);
  }
  
  private boolean isNoFiscalHomo() throws Exception {
  	return typeDocumentOpen.equals(NFHOMOL);
  }
  
  private boolean fiscalClose( boolean bCutPaper, int copies ) throws Exception {
  	if (automaticPay && this.isFiscal()) {
  		fiscalSubTotal(false);
  		double vuelto = fiscalPay("CREDITO",MontoVentas);
  		if (vuelto!=0)
  			fiscalPay("CREDITO",vuelto);
  	}
  	String resp= getHasarDriver().enviarComandoFiscal( handleHasar, CERRAR_DF, ""+copies );
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal CERRAR_DF " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	try {
  		if (bCutPaper) this.getHasarDriver().enviarComandoFiscal( handleHasar, CORTAR_PAPEL, "" );
  	} catch (RuntimeException e) {}
    return true;
  }
  boolean setFooterInfo( int lineNumber, String sLine  ) throws Exception {
    String sLineNumber = ""+ (lineNumber+10);
    String resp= getHasarDriver().enviarComandoFiscal( handleHasar, ESPECIFICAR_ENCABEZADO_COLA, sLineNumber + HasarDriver.SEP + sLine );
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error de footer " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	return true;
  }
  boolean setFantasyInfo( int lineNumber, String sLine ) throws Exception {
	    String sLineNumber = ""+ lineNumber;
	    String resp= getHasarDriver().enviarComandoFiscal( handleHasar, ESPECIFICAR_NOMBRE_FANTASIA, sLineNumber + HasarDriver.SEP + sLine );
	  	if (!HasarDriver.getParamBoolean(resp, 0))
	  		JExcepcion.SendError("Error de fantasy " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
	  	return true;
  }
  boolean setHeaderInfo( int lineNumber, String sLine ) throws Exception {
    String sLineNumber = ""+ lineNumber;
    String resp= getHasarDriver().enviarComandoFiscal( handleHasar, ESPECIFICAR_ENCABEZADO_COLA, sLineNumber + HasarDriver.SEP + sLine );
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error de header " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  	return true;
  }
  boolean status() throws Exception {
    String resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_IMPRESOR, "B"+HasarDriver.SEP+"N" );
  	return HasarDriver.getParamBoolean(resp, 1);
  }
  boolean isFiscalOpenStatus() throws Exception {
    String resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_FISCAL, "C"+HasarDriver.SEP+"S" );
  	return HasarDriver.getParamBoolean(resp, 1);
  }
  boolean statusFiscal() throws Exception {
    String resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_FISCAL, "F"+HasarDriver.SEP+"N" );
  	boolean error = !HasarDriver.getParamBoolean(resp, 1);
    resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_FISCAL, "8"+HasarDriver.SEP+"N" ); // es de casi llena?
  	boolean casiLlena = HasarDriver.getParamBoolean(resp, 1);
  	if (casiLlena) {
  		cierreZ();
  		resp = getHasarDriver().enviarComandoFiscal( handleHasar, ESTADO_FISCAL, "F"+HasarDriver.SEP+"N" );
  		error = !HasarDriver.getParamBoolean(resp, 1);
  	}
    return error;
  }
  boolean fiscalCancel() throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, CANCELAR_COMPROBANTE_FISCAL, "");
  	return HasarDriver.getParamBoolean(resp, 0);
  }
  boolean noFiscalCancel() throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, CANCELAR_COMPROBANTE, "");
  	return HasarDriver.getParamBoolean(resp, 0);
  }
  boolean cierreZ() throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, REPORTE_Z, "");
  	return HasarDriver.getParamBoolean(resp, 0);
  }
  boolean cierreX() throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, REPORTE_X, "");
  	return HasarDriver.getParamBoolean(resp, 0);
  }
  /**
   * 
   * @return el id de comprobante
   * @throws Exception
   */
  void  cerrarDocumento(int copias) throws Exception {
		if (this.isFiscal()) {
			this.fiscalClose(false, copias);
//			String resp = getHasarDriver().enviarComandoFiscal(handleHasar, CERRAR_DF, ""+copias);
//			if (resp.charAt(0)=='0') {
//				JExcepcion.SendError(HasarDriver.getParamString(resp,1)+"["+getHasarDriver().getLastOperation()+"]");
//			}
//	  	return HasarDriver.getParamInt(resp,1);
		}
		else if (this.isNoFiscal()) {
			String resp = getHasarDriver().enviarComandoFiscal(handleHasar, CERRAR_DNF, ""+copias);
			if (resp.charAt(0)=='0') {
				JExcepcion.SendError("Error fiscal Cerrar DNF:" +HasarDriver.getParamString(resp,1)+"["+getHasarDriver().getLastOperation()+"]");
			}
	  	return;
		} if (this.isNoFiscalHomo()) {
			String resp = getHasarDriver().enviarComandoFiscal(handleHasar, CERRAR_DNFH, ""+copias);
			if (resp.charAt(0)=='0') {
				JExcepcion.SendError("Error fiscal Cerrar DNFH:" +HasarDriver.getParamString(resp,1)+"["+getHasarDriver().getLastOperation()+"]");
			}
	  	//return HasarDriver.getParamInt(resp,1);
			return;
		}
		return;
  }
  
	public void openPort() throws Exception {
  	this.close();
  	JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.getConnectinString(), ';');
    String typeConnection = tokens.nextToken();
    if (typeConnection.equalsIgnoreCase("SERIAL")) {
    	String idPort=  tokens.nextToken();
    	String baud=  tokens.nextToken();
    	if(idPort.toUpperCase().startsWith("COM")) 
    		idPort = idPort.substring(3);
  		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ESTABLECER_PUERTO_SERIE, "" + idPort + HasarDriver.SEP + baud);
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error de conexion SERIE: " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    }
    else if (typeConnection.equalsIgnoreCase("TCP")) {
    	String tcp=  tokens.nextToken();
    	String port=  tokens.nextToken();
  		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ESTABLECER_SOCKET_TCP, "" + tcp + HasarDriver.SEP + port);
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error de conexion TCP " + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    }
    else if (typeConnection.equalsIgnoreCase("UDP")) {
    	String tcp=  tokens.nextToken();
    	String port=  tokens.nextToken();
  		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ESTABLECER_SOCKET_UDP, "" + tcp + HasarDriver.SEP + port);
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error de conexion UDP" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    }
    else
  		JExcepcion.SendError("Modo de conexion no manejado ["+typeConnection+"]" );
  }

	void fiscalCliente(String zRazonSocial,  String zNroDocumento, String zTipoDocumento, String zResponsabilidadIVA, String zDireccion) throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, DATOS_CLIENTE, zRazonSocial + HasarDriver.SEP +	zNroDocumento + HasarDriver.SEP + zTipoDocumento + HasarDriver.SEP + zResponsabilidadIVA + HasarDriver.SEP +	zDireccion);
		if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal CLIENTE:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  }
	
	void closePort() throws Exception {
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, FINALIZAR_COMUNICACIONES, "");
		bInitialized = false;
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error de cierre" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
	}
  boolean fiscalOpen(String tipoDoc) throws Exception {
  	automaticPay = true;
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ABRIR_DOCUMENTO_FISCAL, ""+tipoDoc);
		if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal OPEN :" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
		lastTipoDocumento=tipoDoc;
		typeDocumentOpen=FISCAL;
  	return true;
  }
  boolean noFiscalOpen(String tipoEstacion) throws Exception {
  	automaticPay = true;
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ABRIR_DOCUMENTO_NO_FISCAL, ""+tipoEstacion);
		if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal OPEN NO FISCAL :" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
		typeDocumentOpen=NFISCAL;
  	return true;
  }
  boolean noFiscalHomologadoOpen(String tipoDoc,long numero) throws Exception {
  	automaticPay = true;
		String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ABRIR_DNFH, ""+tipoDoc);//+ HasarDriver.SEP + numero);
		if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal OPEN NO HOM:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
		typeDocumentOpen=NFHOMOL;
  	return true;
  }
  
  void originalDoc(String tipoDoc, String origDoc) throws Exception {
    if (!this.hasToSendOriginalDoc(tipoDoc)) return;
  	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ESPECIFICAR_INFO_REMITO_COMPR_ORIGINAL, "1"+ HasarDriver.SEP+ origDoc);
		if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal COMPR ORIG:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  }
  
  private boolean hasToSendOriginalDoc(String tipoDoc) throws Exception {
    if (tipoDoc.equals(THasarPrinterAdapter.TICKET_NOTA_CREDITO_A)) return true; 
    if (tipoDoc.equals(THasarPrinterAdapter.NOTA_CREDITO_A)) return true;
    if (tipoDoc.equals(THasarPrinterAdapter.TICKET_NOTA_CREDITO_B)) return true;
    if (tipoDoc.equals(THasarPrinterAdapter.NOTA_CREDITO_B)) return true;
    return false;
  }
  
  long getCurrFiscalTicket() throws Exception {
    long lRet;
    if (typeDocumentOpen.equals(FISCAL) || typeDocumentOpen.equals(NFISCAL)) {
	    if (!isFiscalOpenStatus()) return 0;
			String resp = getHasarDriver().enviarComandoFiscal(handleHasar, PRIMER_NUMERO_DOCUMENTO_ACTUAL, "");
	  	if (!HasarDriver.getParamBoolean(resp, 0))
	  		return 0;
	  	return HasarDriver.getParamInt(resp, 1);
    }
    return 0;
  }
  
  long getNextFiscalTicket(String tipoDocumento) throws Exception {
    long lRet = 0;

    
    if (tipoDocumento.equals(THasarPrinterAdapter.TICKET_FACTURA_A) || tipoDocumento.equals(THasarPrinterAdapter.FACTURA_A) ||
    		tipoDocumento.equals(THasarPrinterAdapter.TICKET_NOTA_DEBITO_A) || tipoDocumento.equals(THasarPrinterAdapter.NOTA_DEBITO_A)) {
    	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ULTIMO_DOCUMENTO_FISCAL_A, "");
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error fisca ULTIMO FISCAL A:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    	lRet = HasarDriver.getParamInt(resp, 1)+1;
    }
    else if (tipoDocumento.equals(THasarPrinterAdapter.TICKET_FACTURA_B) || tipoDocumento.equals(THasarPrinterAdapter.FACTURA_B) ||
    		     tipoDocumento.equals(THasarPrinterAdapter.TICKET_NOTA_DEBITO_B) || tipoDocumento.equals(THasarPrinterAdapter.NOTA_DEBITO_B) ) {
    	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ULTIMO_DOCUMENTO_FISCAL_BC, "");
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error fiscal ULTIMO FISCAL BC:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    	lRet = HasarDriver.getParamInt(resp, 1)+1;
    }
    else if (tipoDocumento.equals(THasarPrinterAdapter.TICKET_NOTA_CREDITO_A) || tipoDocumento.equals(THasarPrinterAdapter.NOTA_CREDITO_A)) {
    	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ULTIMO_NOTA_CREDITO_A, "");
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error fiscal ULTIMO FISCAL NCA:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    	lRet = HasarDriver.getParamInt(resp, 1)+1;
    }
    else if (tipoDocumento.equals(THasarPrinterAdapter.TICKET_NOTA_CREDITO_B) || tipoDocumento.equals(THasarPrinterAdapter.NOTA_CREDITO_B)) {
    	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ULTIMO_NOTA_CREDITO_BC, "");
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error fiscal ULTIMO NCB:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    	lRet = HasarDriver.getParamInt(resp, 1)+1;
    }
    else if (tipoDocumento.equals(THasarPrinterAdapter.REMITO)) {
    	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, ULTIMO_REMITO, "");
    	if (!HasarDriver.getParamBoolean(resp, 0))
    		JExcepcion.SendError("Error fiscal ULTIMO REM:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
    	lRet = HasarDriver.getParamInt(resp, 1)+1;
    }
    return lRet;
  }
  void noFiscalSendText(String text) throws Exception {
  	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, IMPRIMIR_TEXTO_NO_FISCAL, text);
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		JExcepcion.SendError("Error fiscal TEXTO NO FISCAL:" + HasarDriver.getParamString(resp, 1)+"["+getHasarDriver().getLastOperation()+"]");
  }
  
  boolean paperAdvance( int iLines ) throws Exception {
  	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, AVANZAR_PAPEL, "2" + HasarDriver.SEP + "" + iLines);
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		return false;
  	return true;
  }

  boolean paperAdvance( ) throws Exception {
  	String resp = getHasarDriver().enviarComandoFiscal(handleHasar, AVANZAR_PAPEL, "2"+ HasarDriver.SEP + "1");
  	if (!HasarDriver.getParamBoolean(resp, 0))
  		return false;
  	return true;
  }
	
	public String getLastTipoDocumento() {
		return lastTipoDocumento;
	}

}
