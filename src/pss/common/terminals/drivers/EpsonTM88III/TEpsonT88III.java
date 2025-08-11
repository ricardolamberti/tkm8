package pss.common.terminals.drivers.EpsonTM88III;

import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JPrinterException;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCashDrawerInterface;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrBoolean;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.connectivity.client.serial.JSerialClient;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.formatters.JNumberFormatter;

public class TEpsonT88III extends JTerminal implements JPrinterInterface,
																											 JCashDrawerInterface {

  private JSerialClient serial;
  private char SequenceNumber = 0x0081;
  private String LastMessageReceived = "";
  private String sLogoLoaded = "";
  private int iHeaderLinesUsed = 5;
  private int iFooterLinesUsed = 10;
  private String[]  asPays = new String[20];
  private boolean[] abPays = new boolean[20];
  private boolean bFiscalOpen=false;
  private int version =1;
  private JList<String> bufferLines = null;
  private boolean bInitialized = false;

  private static final String ESC = String.valueOf( (char) 0x1b );
  private static final String SEP = String.valueOf( (char) 0x1c );
  private static final String STX = String.valueOf( (char) 0x02 );
  private static final String ETX = String.valueOf( (char) 0x03 );
  private static final String ENQ = String.valueOf( (char) 0x05 );
  private static final String ACK = String.valueOf( (char) 0x06 );
  private static final char SEQUENCE_WAIT = (char) 0x80;

  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Sistema
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_GET_STATUS = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
//  private static final String CMD_GET_INIT_ERROR = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x03 );
//  private static final String CMD_GET_PROCESS_ERROR = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x04 );
//  private static final String CMD_SET_BAUD_RATE = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x0A );
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Diagnóstico
  //----------------------------------------------------------------------------------------------------------------------
//  private static final String CMD_PRINTER_DIAGNOSTIC = String.valueOf( (char) 0x02 ) + String.valueOf( (char) 0x01 );
//  private static final String CMD_RIPPLE_TEST = String.valueOf( (char) 0x02 ) + String.valueOf( (char) 0x04 );
  private static final String CMD_GET_FISCAL_DETAILS = String.valueOf( (char) 0x02 ) + String.valueOf( (char) 0x0A );
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Inicialización
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_SERIALIZE = String.valueOf( (char) 0x04 ) + String.valueOf( (char) 0x01 );
  private static final String CMD_GET_SERIAL = String.valueOf( (char) 0x04 ) + String.valueOf( (char) 0x02 );
  private static final String CMD_FISCALIZE = String.valueOf( (char) 0x04 ) + String.valueOf( (char) 0x03 );
//  private static final String CMD_LOCK = String.valueOf( (char) 0x04 ) + String.valueOf( (char) 0x04 );
//  private static final String CMD_UNLOCK = String.valueOf( (char) 0x04 ) + String.valueOf( (char) 0x05 );
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Configuracion
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_SET_DATE_TIME = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x01 );
  private static final String CMD_SET_FISCAL_INFO = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x03 );
//  private static final String CMD_GET_DATE_TIME = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x02 );
//  private static final String CMD_SET_MAX_DISCOUNT = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x06 );
//  private static final String CMD_GET_FISCAL_INFO = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x07 );
  private static final String CMD_SET_HEADER_LINES = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x08 );
//  private static final String CMD_GET_HEADER_LINES = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x09 );
  private static final String CMD_SET_FOOTER_LINES = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x0A );
//  private static final String CMD_GET_FOOTER_LINES = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x0B );
  private static final String CMD_SET_PAY_TYPE = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x0C );
  private static final String CMD_GET_PAY_TYPE = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x0D );

  private static final String CMD_LOGO_START = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x30 );
  private static final String CMD_LOGO_DATA = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x31 );
  private static final String CMD_LOGO_END = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x32 );
  private static final String CMD_LOGO_CANCEL = String.valueOf( (char) 0x05 ) + String.valueOf( (char) 0x33 );

  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Control de Impresora
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_NEW_LINE = String.valueOf( (char) 0x07 ) + String.valueOf( (char) 0x01 );
  private static final String CMD_CUT_PAPER = String.valueOf( (char) 0x07 ) + String.valueOf( (char) 0x02 );
  private static final String CMD_OPEN_CASH_DRAWER = String.valueOf( (char) 0x07 ) + String.valueOf( (char) 0x07 );
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Jornada Fiscal
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_CIERRE_Z = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x01 );
  private static final String CMD_CIERRE_X = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x02 );
  private static final String CMD_CIERRE_INFO = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x0A );
  private static final String CMD_PAGOS_JORNADA = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x0C );
  private static final String CMD_CIERRE_Z_INFO = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x0F );
  private static final String CMD_CANCEL_REPORT = String.valueOf( (char) 0x08 ) + String.valueOf( (char) 0x22 );
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Boleta Fiscal
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_FISCAL_OPEN = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x01 );
  private static final String CMD_FISCAL_ITEM = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x02 );
  private static final String CMD_FISCAL_SUBTOTAL = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x03 );
  private static final String CMD_FISCAL_DISCOUNT = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x04 );
  private static final String CMD_FISCAL_PAY = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x05 );
  private static final String CMD_FISCAL_CLOSE = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x06 );
  private static final String CMD_FISCAL_INFO = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x0A );
  private static final String CMD_FISCAL_PREFERENCES = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x08 );
  private static final String CMD_FISCAL_DONATION = String.valueOf( (char) 0x0A ) + String.valueOf( (char) 0x10 );  
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Documentos No Fiscales
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_OPEN_NOT_FISCAL = String.valueOf( (char) 0x0E ) + String.valueOf( (char) 0x01 );
  private static final String CMD_PRN_NOT_FISCAL = String.valueOf( (char) 0x0E ) + String.valueOf( (char) 0x02 );
  private static final String CMD_CLOSE_NOT_FISCAL = String.valueOf( (char) 0x0E ) + String.valueOf( (char) 0x06 );
  private static final String CMD_PRN_NO_FISCAL_FAST = String.valueOf( (char) 0x0E ) + String.valueOf( (char) 0x30 );  
  //----------------------------------------------------------------------------------------------------------------------
  // Comandos de Documentos No Fiscales Medio de Pago
  //----------------------------------------------------------------------------------------------------------------------
  private static final String CMD_OPEN_NOT_FISCAL_PAGO = String.valueOf( (char) 0x30 ) + String.valueOf( (char) 0x01 );
  private static final String CMD_PRN_NOT_FISCAL_PAGO = String.valueOf( (char) 0x30 ) + String.valueOf( (char) 0x02 );
  private static final String CMD_CLOSE_NOT_FISCAL_PAGO = String.valueOf( (char) 0x30 ) + String.valueOf( (char) 0x06 );
  //----------------------------------------------------------------------------------------------------------------------
  // Extensiones
  //----------------------------------------------------------------------------------------------------------------------
  private static final String EXT_NULL = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x00 );
  private static final String EXT_FLAG1 = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );  
//  private static final String EXT_FLAG3 = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x03 );  
  private static final String EXT_CORTE_PAPEL = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
//  private static final String EXT_PRN_HEADER = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
//  private static final String EXT_9600_BPS = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x02 );
//  private static final String EXT_19200_BPS = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
//  private static final String EXT_38400_BPS = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x00 );
  private static final String EXT_VENTA = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x00 );
  private static final String EXT_VENTA_ANUL = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
//  private static final String EXT_DEVOLUCION = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x04 );
//  private static final String EXT_DEVOLUCION_ANUL = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x05 );
//  private static final String EXT_CAJON_1 = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x00 );
  private static final String EXT_CAJON_2 = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x01 );
  private static final String EXT_INFO_PAGOS_JORNADA = String.valueOf( (char) 0x00 ) + String.valueOf( (char) 0x02 );
  //----------------------------------------------------------------------------------------------------------------------
  // Atributos de Texto Enriquecido
  //----------------------------------------------------------------------------------------------------------------------
//  private static char RT_NEGRITA = 0x01;
//  private static char RT_SUBRAYADO = 0x02;
//  private static char RT_DOBLE_ANCHO = 0x04;
//  private static char RT_DOBLE_ALTO = 0x08;
  private static char RT_INVERTIDO = 0x10;
//  private static char RT_COLOR = 0x20;
//  private static char RT_COD_BARRA = 0x80;

  
//----------------------------------------------------------------------------------------------------------------------
  public TEpsonT88III() throws Exception {
  }

	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
		this.addDriver(JTerminal.D_CASH_DRAWER);
	}

  public Answer open() throws Exception {
  	return new AwrOk();
  }
  
  public void openPort() throws Exception {
  	this.close();
    this.serial = this.createSerialConnection();
    this.serial.setDataBits(8);
    this.serial.setParity(JSerialClient.PAR_NONE);
    this.serial.setStopBit( 1 );
    PssLogger.logFiscal( "Inicializando Impresora" );
    if (!serial.connect())
   	 JExcepcion.SendError("No se pudo abrir el Puerto: " + serial.getPort());
  }

  //----------------------------------------------------------------------------------------------------------------------
  public Answer close() throws Exception {
    if( serial != null ) {
    	PssLogger.logFiscal( "Cierre de Puerto" );
    	serial.disconnect();
    	serial=null;
    }
    return new AwrOk();
  }
  
  public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new TEpsonT88IIIPrinterAdapter(this);
  }
  
//----------------------------------------------------------------------------------------------------------------------
  public Answer printLine(String zLinea) throws Exception {
    this.noFiscalSendText(zLinea);
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer openDoc() throws Exception {
    if (this.noFiscalOpen()) return new AwrOk();
    this.fiscalCancel();
    if(!this.noFiscalOpen())
      JPrinterException.SendError("No se pudo abrir Comprobante No Fiscal");
    return new AwrOk();
  }
  
  public Answer closeDoc() throws Exception  {
  	this.noFiscalCancel();
  	return new AwrOk();
  }

//----------------------------------------------------------------------------------------------------------------------
  public Answer cancelDoc() throws Exception {
    this.noFiscalCancel();
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer closeShift() throws Exception {
    this.cleanHeader();
    this.cleanFooter();
    this.cierreX();
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer closeDay() throws Exception {
  	this.cleanHeader();
  	this.cleanFooter();
  	this.cierreZ();
    this.setDateAndTime();
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer flush() throws Exception {
  	this.printBuffer();
  	return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer skeepLines(int lines) throws Exception {
    this.paperAdvance(lines);
    return new AwrOk();
  }
  public void newLine() throws Exception {
    this.paperAdvance(1);
  }

  //----------------------------------------------------------------------------------------------------------------------
  // Calcula el Checksum del mensaje pasado como parámetro
  //----------------------------------------------------------------------------------------------------------------------
  private static String Bcc( String Message ) {
    int Resultado = 0x0000;
    StringBuffer BccResult = new StringBuffer();
    int iMsgLen = Message.length();
    for( int a = 0; a < iMsgLen; a++ )
      Resultado = ( Resultado + Message.charAt( a ) ) & 0xffff;
    if( ( Resultado & 0xf000 ) > 0x9000 )
      BccResult.append( String.valueOf( (char) ( ( ( Resultado & 0xf000 ) >> 12 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0xf000 ) >> 12 ) );
    if( ( Resultado & 0x0f00 ) > 0x0900 )
      BccResult.append( String.valueOf( (char) ( ( ( Resultado & 0x0f00 ) >> 8 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0x0f00 ) >> 8 ) );
    if( ( Resultado & 0x00f0 ) > 0x0090 )
      BccResult.append( String.valueOf( (char) ( ( ( Resultado & 0x00f0 ) >> 4 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0x00f0 ) >> 4 ) );
    if( ( Resultado & 0x000f ) > 0x0009 )
      BccResult.append( String.valueOf( (char) ( ( Resultado & 0x000f ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( Resultado & 0x000f ) );
    return BccResult.toString();
  }

  //----------------------------------------------------------------------------------------------------------------------
  // Agrega Caracter de Escape a los bytes reservados del string
  //----------------------------------------------------------------------------------------------------------------------
  private static String escape( String Field ) {
    char zField[] = Field.toCharArray();
    StringBuffer ParsedField = new StringBuffer();
    for( int iCont = 0; iCont < zField.length; iCont++ ) {
      switch ( zField[iCont] ) {
        case 0x02:
        case 0x03:
        case 0x1a:
        case 0x1b:
        case 0x1c:
        case 0x1d:
        case 0x1e:
        case 0x1f:
          ParsedField.append( ESC );
        default:
          ParsedField.append( String.valueOf( zField[iCont] ) );
      }
    }
    return ParsedField.toString();
  }

  //----------------------------------------------------------------------------------------------------------------------
  private boolean enviarRecibirComando( boolean bCommandRecovery, String Command ) throws Exception {
    return enviarRecibirComando( bCommandRecovery, Command, EXT_NULL, "" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  private boolean enviarRecibirComando( boolean bCommandRecovery, String Command, String Parameters ) throws Exception {
    return enviarRecibirComando( bCommandRecovery, Command, EXT_NULL, Parameters );
  }

  //----------------------------------------------------------------------------------------------------------------------
  private boolean enviarRecibirComando( boolean bCommandRecovery, String Command, String Extension, String Parameters ) throws Exception {
    boolean bRet;
    PssLogger.logFiscal( "Comando " + getCommandDesc( getInt( Command ) ) );
    StringBuffer MessageOut = new StringBuffer();
    MessageOut.append( STX );
    MessageOut.append( escape( String.valueOf( SequenceNumber ) ) );
    MessageOut.append( escape( Command ) );
    MessageOut.append( SEP );
    MessageOut.append( escape( Extension ) );
    if( !Parameters.equals( "" ) ) {
      MessageOut.append( SEP );
      MessageOut.append( Parameters );
    }
    MessageOut.append( ETX );
    MessageOut.append( Bcc( MessageOut.toString() ) );
    String sAnswer = enviarRecibirMensaje( MessageOut.toString() );
    if( sAnswer.length() == 0 ) {
      JPrinterException.SendError( "No se recibe respuesta de la Impresora Fiscal" );
    }
    if( getInt( sAnswer ) != 0 ) {
      if( !bCommandRecovery ) {
        String sMessageReceived = LastMessageReceived;
        if( recuperarEstadoFiscal( getInt( sAnswer ) ) ) {
          if( !enviarRecibirComando( true, Command, Extension, Parameters ) ) {
            bRet = false;
            JPrinterException.SendError( "Error \"" + getErrorDesc( getInt( getAnswerField( 3, LastMessageReceived ) ) ) + "\" en comando \"" + getCommandDesc( getInt( Command ) ) + "\"" );
          } else {
            bRet = true;
          }
        } else {
          bRet = false;
          JPrinterException.SendError( "Error \"" + getErrorDesc( getInt( getAnswerField( 3, sMessageReceived ) ) ) + "\" en comando \"" + getCommandDesc( getInt( Command ) ) + "\"" );
        }
      } else {
        bRet = false;
//        JPrinterException.SendError( "Error \"" + getErrorDesc( getInt( getAnswerField( 3, LastMessageReceived ) ) ) + "\" en comando \"" + getCommandDesc( getInt( Command ) ) + "\"" );
      }
    } else {
      bRet = true;
    }
    return bRet;
  }
//----------------------------------------------------------------------------------------------------------------------
  private String enviarRecibirMensaje( String MessageOut ) {
    try {
      StringBuffer MessageIn = new StringBuffer();
      int LastLen = 0, CommandWaitRetries = 0, end = 0, start = 0;
      boolean MessageOK = false;
      LastMessageReceived = "";

      while( !MessageOK && CommandWaitRetries < 10 ) {
        // Desfasaje de protocolo - Impresora Fiscal: Puede estar esperando un ACK
        PssLogger.logFiscalMessage( "SND: ", ACK );
        this.serial.write( ACK );
        MessageIn = new StringBuffer();
        PssLogger.logFiscalMessage( "SND: ", MessageOut );
        this.serial.write( MessageOut );
        while( true ) {
        	try { Thread.sleep(50); } catch (Exception ignore) {}
          MessageIn.append( this.serial.recv() );
          if( MessageIn.length() == 0 || MessageIn.charAt( 0 ) != ACK.charAt( 0 ) ) {
            return "";
          }
          if( MessageIn.length() == LastLen ) {
            if( MessageIn.length() > 1 && MessageIn.charAt( 2 ) != SEQUENCE_WAIT ) {
              break;
            } else {
              CommandWaitRetries++;
              if( CommandWaitRetries % 3 == 0 ) {
                break;
              }
            }
          } else {
            CommandWaitRetries = 0;
          }
          end = MessageIn.lastIndexOf( ETX );
          start = MessageIn.indexOf( String.valueOf( SequenceNumber ) ) - 1;
          if( end >= 0 && start >= 0 && end - start > 6 && end + 4 < MessageIn.length() ) {
            if( MessageIn.charAt( start ) == STX.charAt( 0 ) ) {
              MessageOK = true;
              break;
            }
          }
          LastLen = MessageIn.length();
          try {
            Thread.sleep( 100 );
          } catch( Exception e ) {
          }
        }
      }
      String Message = "";
      if( !MessageOK )
        return "";
      PssLogger.logFiscalMessage( "RCV: ", MessageIn.toString() );
      PssLogger.logFiscalMessage( "SND: ", ACK );
      this.serial.write( ACK );
      for( int i = start; i <= end; i++ ) {
        Message += MessageIn.charAt( i );
      }
      String BccRecibido = "";
      for( int i = end + 1; i <= end + 4; i++ ) {
        BccRecibido += MessageIn.charAt( i );
      }
      if( !BccRecibido.equalsIgnoreCase( Bcc( Message ) ) ) {
        return "";
      }
      LastMessageReceived = Message;
      return getAnswerField( 3, LastMessageReceived );
    } finally {
      if( SequenceNumber == 0xFF ) {
        SequenceNumber = 0x81;
      } else {
        SequenceNumber++;
      }
    }
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean controlarVinculo() {
    PssLogger.logFiscal( "Control de Vínculo" );
    String MessageIn = "";
    PssLogger.logFiscalMessage( "SND: ", ENQ );
    this.serial.write( ENQ );
    try {
      Thread.sleep( 5 );
    } catch( Exception e ) {
    }
    MessageIn += this.serial.recv();
    PssLogger.logFiscalMessage( "RCV: ", MessageIn );
    return MessageIn.length() > 0 && MessageIn.charAt( 0 ) == ACK.charAt( 0 );
  }

  //----------------------------------------------------------------------------------------------------------------------
  protected boolean recuperarEstadoFiscal( int State ) throws Exception {
    boolean bRet = false;
    switch (State) {
      case 0x0801:
        PssLogger.logFiscal( "recuperarEstadoFiscal: Enviando Cierre X forzado" );
        bRet = cierreX();
        break;
      case 0x0804:
      case 0x0802:
        PssLogger.logFiscal( "recuperarEstadoFiscal: Enviando Cierre Z forzado" );
        bRet = cierreZ();
        break;
      case 0x0102:
        PssLogger.logFiscal( "recuperarEstadoFiscal: Cerrando Documento no Fiscal" );
        if( noFiscalClose( true, true ) || fiscalCancel() ) {
          bRet = true;
        }
        break;
      case 0x0E01:
        PssLogger.logFiscal( "recuperarEstadoFiscal: Documento no fiscal con mas de 30 líneas" );
        bRet = noFiscalClose( true, false ) && noFiscalOpen();
        break;
      case 0x0108:
        PssLogger.logFiscal( "recuperarEstadoFiscal: Error desconocido (modo de carga de logos?)" );
        bRet = enviarRecibirComando( true, CMD_LOGO_CANCEL );
        break;
    }
    return bRet;
  }

////----------------------------------------------------------------------------------------------------------------------
//  protected boolean openPort() throws Exception {
//    JDebugPrint.logFiscal( "Apertura de Puerto" );
//    return this.serial.connect();
//  }


  //----------------------------------------------------------------------------------------------------------------------
  public String getAnswerField( int FieldNumber ) {
    String FieldResponse = "";
    int StartingAt = 0;
    int EndingAt = 0;
    for( int a = 0; a < FieldNumber; a++ ) {
      StartingAt = LastMessageReceived.indexOf( SEP, StartingAt );
      if( StartingAt < 0 )
        return FieldResponse;
      StartingAt++;
    }
    EndingAt = LastMessageReceived.indexOf( SEP, StartingAt );
    if( EndingAt < 0 ) {
      EndingAt = LastMessageReceived.indexOf( ETX, StartingAt );
      if( EndingAt < 0 )
        return FieldResponse;
    }
    FieldResponse = LastMessageReceived.substring( StartingAt, EndingAt );
    return FieldResponse;
  }

  //----------------------------------------------------------------------------------------------------------------------
  private String getAnswerField( int FieldNumber, String msg ) {
    String sRet = "";
    int iPos = 2;
    for( int iContField = 0; iContField < FieldNumber; iContField++ ) {
      for( ; iPos < msg.length(); iPos++ ) {
        if( msg.charAt( iPos ) == SEP.charAt( 0 ) && msg.charAt( iPos - 1 ) != ESC.charAt( 0 ) ) {
          iPos++;
          break;
        }
      }
    }
    for( ; iPos < msg.length(); iPos++ ) {
      switch (msg.charAt( iPos )) {
        case 0x1b:
          sRet += msg.charAt( ++iPos );
          break;
        case 0x03:
        case 0x1c:
          return sRet;
        default:
          sRet += msg.charAt( iPos );
      }
    }
    return sRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean getPrinterStatus( int iBit ) {
    int i = getInt( getAnswerField( 0, LastMessageReceived ) );
    return ( i & ( 0x8000 >> iBit ) ) != 0 ;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean GetFiscalStatus( int iBit ) {
    int i = getInt( getAnswerField( 1, LastMessageReceived ) );
    return ( i & ( 0x8000 >> iBit ) ) != 0 ;
  }

  //----------------------------------------------------------------------------------------------------------------------
/*  public boolean setCommSpeed( int baudios ) throws Exception {
    switch (baudios) {
      case 9600:
        initSerial( ComPort, 38400 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_9600_BPS, "" ) )
          return true;
        initSerial( ComPort, 19200 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_9600_BPS, "" ) )
          return true;
        break;
      case 19200:
        initSerial( ComPort, 38400 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_19200_BPS, "" ) )
          return true;
        initSerial( ComPort, 19200 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_19200_BPS, "" ) )
          return true;
        break;
      case 38400:
        initSerial( ComPort, 19200 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_38400_BPS, "" ) )
          return true;
        initSerial( ComPort, 9600 );
        if( openPort() && enviarRecibirComando( true, CMD_SET_BAUD_RATE, EXT_38400_BPS, "" ) )
          return true;
        break;
      default:
        return false;
    }
    initSerial( ComPort, baudios );
    openPort();
    return true;
  }
*/
  //----------------------------------------------------------------------------------------------------------------------
  public boolean status() throws Exception {
    if( !enviarRecibirComando( false, CMD_GET_STATUS ) )
      return false;
//    LastStatusReceived = LastMessageReceived;
    return true;
  }


  //----------------------------------------------------------------------------------------------------------------------
  public boolean setDateTime( String Date, String Time ) throws Exception {
    return enviarRecibirComando( false, CMD_SET_DATE_TIME, Date + SEP + Time );
  }

//  //----------------------------------------------------------------------------------------------------------------------
//  public boolean fiscalCancelItem( BizItem oVentaPos ) throws Exception {
//    JDebugPrint.logFiscal( "Cancelacion de item vendido" );
//    return fiscalItem( true, oVentaPos, oVentaPos.GetArticulo(), -oVentaPos.GetCantidad(), oVentaPos.GetPrecioUnit(), oVentaPos.getPrecioUnitOriginal(), oVentaPos.ObtenerImpuestosCalculados().GetTotalImpuesto( "IVACH" )[2] );
//  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalOpen() throws Exception {
    bFiscalOpen=true;
    return enviarRecibirComando( false, CMD_FISCAL_OPEN, "000" + SEP + "0" );
  }

  public boolean isFiscalOpen() { return bFiscalOpen; }

  public boolean printDonation(String descripcion, double monto) throws Exception {
  	String sMonto = refill(monto, 15, 0); 
  	return enviarRecibirComando( false, CMD_FISCAL_DONATION, descripcion + SEP + sMonto );
  }
  
  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalItem( boolean bCommandRecovery, String descr, int presition, String Codigo, double dCantidad, double dPrecio, double dPrecioOrig, double dTasaImp ) throws Exception {
    String sCantidad = refill( Math.abs( dCantidad ), 5, 4 );
    String sPrecio = refill( dPrecio, 7, 4 );
    String sTasa = refill( dTasaImp, 2, 2 );
    String sLineaExtra = "";
    String sExtension;
    if( dCantidad > 0 ) {
      sExtension = EXT_VENTA;
    } else {
      sExtension = EXT_VENTA_ANUL;
    }
    String sTexto = descr==null ? "Mercaderia" : descr;
    if( JTools.rd( dPrecioOrig ) != JTools.rd( dPrecio ) ) {
//      int iDecimales = presition;
      String sCambioDescrip="";
      if(dPrecioOrig<dPrecio) sCambioDescrip = "Recargo";
      else
        if(dPrecioOrig>dPrecio) sCambioDescrip = "Descuento";

//      String sPrecOrig = JRegionalFormatterFactory.getRegionalFormatter().formatNumber( dPrecioOrig, iDecimales );
//      String sPrecFinal = JRegionalFormatterFactory.getRegionalFormatter().formatNumber( dPrecio, iDecimales );
	    String sPrecOrig = JNumberFormatter.formatNumberToString(dPrecioOrig);
	    String sPrecFinal = JNumberFormatter.formatNumberToString(dPrecio);
      sLineaExtra = sCambioDescrip + ": $" + sPrecOrig + " => $" + sPrecFinal;
    }
    return enviarRecibirComando( bCommandRecovery, CMD_FISCAL_ITEM, sExtension, sLineaExtra + SEP + SEP + SEP + SEP + SEP + sTexto + SEP + sCantidad + SEP + sPrecio + SEP + sTasa );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalSubtotal( boolean bCommandRecovery ) throws Exception {
    return enviarRecibirComando( bCommandRecovery, CMD_FISCAL_SUBTOTAL, EXT_CORTE_PAPEL, "" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean verifyVersion() throws Exception {
  	boolean ret =  enviarRecibirComando( true, CMD_GET_FISCAL_DETAILS, EXT_FLAG1, "");
  	String versionMayor = getAnswerField( 7, LastMessageReceived );
  	version=1;
  	if (versionMayor.equals("2")) version=2;
  	return ret;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalPreferences( boolean bPrintPays, boolean sPrintQuantXPrice ) throws Exception {
    char bExt = 0x00;
    if( bPrintPays ) {
      bExt |= (char)0x01;
    }
    if( sPrintQuantXPrice ) {
      bExt |= (char)0x02;
    }
    return enviarRecibirComando( false, CMD_FISCAL_PREFERENCES, String.valueOf( (char) 0x00 ) + String.valueOf( bExt ), "" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalDiscount( long lMonto ) throws Exception {
    String sMonto = refill( lMonto, 15, 0 );
    return enviarRecibirComando( false, CMD_FISCAL_DISCOUNT, "Descuento" + SEP + sMonto );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalPay( boolean bCommandRecovery, String sDescMoneda, double fMonto ) throws Exception {
    String sMonto = refill( fMonto, 15, 0 );
    String sMoneda = refill( getPayment( sDescMoneda ), 2, 0 );
    return enviarRecibirComando( bCommandRecovery, CMD_FISCAL_PAY, sMoneda + SEP + sMonto );
  }
  

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalClose( boolean bCommandRecovery, boolean bCutPaper ) throws Exception {
    boolean ok = enviarRecibirComando( bCommandRecovery, CMD_FISCAL_CLOSE, EXT_CORTE_PAPEL, SEP + SEP + SEP + SEP + SEP );
    if (ok) bFiscalOpen=false;
    return ok;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalCancel() throws Exception {
    PssLogger.logFiscal( "Cancelación comprobante no fiscal" );
    return noFiscalClose( true, true );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalCancel() throws Exception {
    if( isFiscalOpenStatus() ) {
      cleanFooter();
      setFooterInfo( JTools.centerString( " CANCELACION DE TICKET ", 42, '*' ), true );
      long lMonto = getCurrFiscalTotal();
      PssLogger.logFiscal( "Cancelación Documento Fiscal - Monto: " + lMonto );
      if( lMonto == 0 ) {
        if( this.fiscalItem( true, null, 0, "", 1, 1, 1, 0 ) ) {
          PssLogger.logFiscal( "Impresión de item dummy para cancelación de ticket" );
          lMonto++;
        }
      } else {
        PssLogger.logFiscal("Cancelación Documento Fiscal - Monto: " + lMonto);
      }
      fiscalSubtotal( true );
      fiscalPay( true, "Efectivo", lMonto );
      boolean bRet = fiscalClose( true, true );
      cleanFooter();
      return bRet;
    } else {
      return true;
    }
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean cancelReport() throws Exception {
    return enviarRecibirComando( true, CMD_CANCEL_REPORT );
  }

//----------------------------------------------------------------------------------------------------------------------
  public boolean setHeaderInfo( String sLine, boolean bInvertido ) throws Exception {
    iHeaderLinesUsed++;
    if( iHeaderLinesUsed <= 5 ) {
      return setHeaderInfo( iHeaderLinesUsed, sLine, bInvertido );
    } else {
      return false;
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  private boolean setHeaderInfo( int lineNumber, String sLine, boolean bInvertido ) throws Exception {
    String sLineNumber = refill( lineNumber, 3, 0 );
    if( bInvertido ) {
      sLine = ESC + String.valueOf( RT_INVERTIDO ) + sLine;
    }
    return enviarRecibirComando( false, CMD_SET_HEADER_LINES, sLineNumber + SEP + escape( sLine ) );
  }


//----------------------------------------------------------------------------------------------------------------------
  public boolean setLogo( char cLogo, String sDataFile ) throws Exception {
    if( sDataFile.equals(sLogoLoaded) ) {
      if( !enviarRecibirComando( false, CMD_LOGO_START ) ) {
        enviarRecibirComando( false, CMD_LOGO_CANCEL );
        return false;
      }
      String sPrueba = String.valueOf( ( char )0x01 )

                     + String.valueOf( ( char )0x08 )
                     + String.valueOf( ( char )0x00 )
                     + String.valueOf( ( char )0x08 )
                     + String.valueOf( ( char )0x00 )
                     ;

      for( int i = 0; i < 64; i ++ ) {
        sPrueba += String.valueOf( ( char )0xFF )
          + String.valueOf( ( char )0x00 )
          + String.valueOf( ( char )0xFF )
          + String.valueOf( ( char )0x00 )
          + String.valueOf( ( char )0x00 )
          + String.valueOf( ( char )0x00 )
          + String.valueOf( ( char )0x00 )
          + String.valueOf( ( char )0x00 );
      }
      sPrueba += String.valueOf( ( char )0x01 );

      if( !enviarRecibirComando( false, CMD_LOGO_DATA, escape( sPrueba ) ) ) {
        enviarRecibirComando( false, CMD_LOGO_CANCEL );
        return false;
      }
      if( !enviarRecibirComando( false, CMD_LOGO_END ) ) {
        enviarRecibirComando( false, CMD_LOGO_CANCEL );
        return false;
      }
    }
    sLogoLoaded = sDataFile;
    return true;
  }


//----------------------------------------------------------------------------------------------------------------------
  public boolean setFooterInfo( String sLine, boolean bInvertido ) throws Exception {
    return setFooterInfo( iFooterLinesUsed++, sLine, bInvertido );
  }
//----------------------------------------------------------------------------------------------------------------------
  private boolean setFooterInfo( int lineNumber, String sLine, boolean bInvertido ) throws Exception {
    String sLineNumber = refill( lineNumber, 3, 0 );
    if( bInvertido ) {
      sLine = ESC + String.valueOf( RT_INVERTIDO ) + sLine;
    }
    return enviarRecibirComando( false, CMD_SET_FOOTER_LINES, sLineNumber + SEP + escape( sLine ) );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean SetPay( int number, String Description ) throws Exception {
    String sNumber = refill( number, 2, 0 );
    return enviarRecibirComando( false, CMD_SET_PAY_TYPE, sNumber + SEP + Description );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public String getPayDescription( int number ) throws Exception {
    String sRet;
    String sNumber = refill( number, 2, 0 );
    if( enviarRecibirComando( true, CMD_GET_PAY_TYPE, sNumber ) ) {
      sRet = getAnswerField( 5, LastMessageReceived );
    } else {
      sRet = null;
    }
    return sRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public long getCurrFiscalTotal() throws Exception {
    long lRet;
    if( enviarRecibirComando( true, CMD_FISCAL_INFO ) ) {
      lRet = Long.parseLong( getAnswerField( 6, LastMessageReceived ) );
    } else {
      lRet = 0;
    }
    return lRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public long getCurrFiscalTicket( boolean bCommandRecovery ) throws Exception {
    long lRet;
    PssLogger.logFiscal( "getCurrFiscalTicket" );
    if( enviarRecibirComando( bCommandRecovery, CMD_FISCAL_INFO ) ) {
      lRet = Long.parseLong( getAnswerField( 5, LastMessageReceived ) );
      bFiscalOpen = true;
    } else {
      lRet = 0;
      bFiscalOpen = false;
    }
    return lRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public long getNextFiscalTicket() throws Exception {
    long lRet;
    if( enviarRecibirComando( true, CMD_CIERRE_INFO ) && getAnswerField( 9, LastMessageReceived ).length() > 0 ) {
      lRet = Long.parseLong( getAnswerField( 9, LastMessageReceived ) ) + 1;
    } else {
      if( enviarRecibirComando( true, CMD_CIERRE_Z_INFO, "00001" + SEP + "99999" ) && getAnswerField( 10, LastMessageReceived ).length() > 0 ) {
        lRet = Long.parseLong( getAnswerField( 10, LastMessageReceived ) ) + 1;
      } else {
        lRet = 1;
      }
    }
    return lRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public void cleanHeader() throws Exception {
    for( int i = 1; i <= iHeaderLinesUsed && i <= 5; i++ ) {
      setHeaderInfo( i, "", false );
    }
    iHeaderLinesUsed = 1;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public void cleanFooter() throws Exception {
    for( int i = 1; i <= iFooterLinesUsed; i++ ) {
      setFooterInfo( i, "", false );
    }
    iFooterLinesUsed = 1;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean paperAdvance( int iLines ) throws Exception {
    String sLines = refill( iLines, 2, 0 );
    return enviarRecibirComando( false, CMD_NEW_LINE, sLines );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean paperCut( boolean bCommandRecovery ) throws Exception {
    return enviarRecibirComando( bCommandRecovery, CMD_CUT_PAPER );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalOpen() throws Exception {
//    return enviarRecibirComando( false, CMD_OPEN_NOT_FISCAL, EXT_PRN_HEADER, "001" + SEP + "3" );
  	if (version==2) {
  		bufferLines = JCollectionFactory.createList();
  		return true;
  	}
    return enviarRecibirComando( false, CMD_OPEN_NOT_FISCAL, "00" + SEP + "1" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalSendText( String Texto ) throws Exception {
  	if (version==2) {
  		bufferLines.addElement(JTools.replaceForeignChars( Texto ));
  		return true;
  	}
    return enviarRecibirComando( false, CMD_PRN_NOT_FISCAL, JTools.replaceForeignChars( Texto ) );
  }
  
  public void printBuffer() throws Exception {
  	if (bufferLines==null) return;
  	String cmdOpen = "00" + SEP + "1";
  	String cmdClose = "001" + SEP + "Fin doc" + SEP + "001" + SEP + "Fin doc1" + SEP + "001" + SEP + "Fin doc2";
  	String cmdLines = "";
  	int i=0;
  	JIterator<String> iter = bufferLines.getIterator();
  	while ( iter.hasMoreElements() ) {
  	  String line = iter.nextElement();
  	  cmdLines+=line+SEP;
  	  i++;
  	  if (i==30) {
  		  enviarRecibirComando(false, CMD_PRN_NO_FISCAL_FAST, cmdOpen + SEP + cmdLines + cmdClose );
  		  cmdLines="";
  		  i=0;
  	  }
  	}
  	if (i>0) {
  		for (int j=i+1;j<=30;j++) {
  			cmdLines+=SEP;
  		}
  	  enviarRecibirComando(false, CMD_PRN_NO_FISCAL_FAST, cmdOpen + SEP + cmdLines + cmdClose );  	  
  	}
  	bufferLines=null;
  	paperCut( true );
  	return;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalClose( boolean bCommandRecovery, boolean CortePapel ) throws Exception {
  	boolean bRet = enviarRecibirComando( bCommandRecovery, CMD_CLOSE_NOT_FISCAL, "001" + SEP + "Fin doc" + SEP + "001" + SEP + "Fin doc1" + SEP + "001" + SEP + "Fin doc2" );
  	if( CortePapel && bRet )
  		paperCut( bCommandRecovery );
  	return bRet;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalPagoOpen() throws Exception {
    return enviarRecibirComando( false, CMD_OPEN_NOT_FISCAL_PAGO, "00" + SEP + "1" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalPagoSendText( String Texto ) throws Exception {
    return enviarRecibirComando( false, CMD_PRN_NOT_FISCAL_PAGO, JTools.replaceForeignChars( Texto ) );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean noFiscalPagoClose( boolean CortePapel ) throws Exception {
    return enviarRecibirComando( false, CMD_CLOSE_NOT_FISCAL_PAGO, EXT_CORTE_PAPEL, "" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean cierreX() throws Exception {
    return enviarRecibirComando( false, CMD_CIERRE_X );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean cierreZ() throws Exception {
    if( enviarRecibirComando( false, CMD_CIERRE_Z ) ) {
      for( int i=0; i<20; i++ ) {
        abPays[i] = false;
      }
      return true;
    } else {
      return false;
    }
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean fiscalizar() throws Exception {
    return enviarRecibirComando( false, CMD_FISCALIZE );
  }

  //----------------------------------------------------------------------------------------------------------------------
  private static int getInt( String sSec ) {
    return (int) JTools.hexaToDecimal( JTools.BinToHexString( sSec, sSec.length() ) );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean serializar( long lSerialNumber ) throws Exception {
    String sSerialNumber = refill( lSerialNumber, 10, 0 );
    return enviarRecibirComando( false, CMD_SERIALIZE, sSerialNumber );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean reporteSerializacion() throws Exception {
    return enviarRecibirComando( false, CMD_GET_SERIAL, EXT_CORTE_PAPEL, "" );
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean setearInfoFiscal( String sRazonSocial, String sRut, long lCaja, String sRubro, String sDom1, String sDom2, String sDom3 ) throws Exception {
    String sCaja = refill( lCaja, 4, 0 );
    if( sRazonSocial.length() > 40 )
      sRazonSocial = sRazonSocial.substring( 0, 40 );
    if( sRubro.length() > 40 )
      sRubro = sRubro.substring( 0, 40 );
    if( sDom1.length() > 40 )
      sDom1 = sDom1.substring( 0, 40 );
    if( sDom2.length() > 40 )
      sDom2 = sDom2.substring( 0, 40 );
    if( sDom3.length() > 40 )
      sDom3 = sDom3.substring( 0, 40 );
    if( sRut.length() > 9 )
      sRut = sRut.substring( 0, 9 );
    return enviarRecibirComando( false, CMD_SET_FISCAL_INFO, sRazonSocial + SEP + sRut + SEP + sCaja + SEP + sRubro + SEP + sDom1 + SEP + sDom2 + SEP + sDom3 );
  }

  //----------------------------------------------------------------------------------------------------------------------
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
//----------------------------------------------------------------------------------------------------------------------
  public void leerFormasPago() throws Exception {
    for( int i=0; i<20; i++ ) {
      asPays[i] = getPayDescription(i+1);
      if( asPays[i] == null ) return;
      PssLogger.logFiscal("Fiscal Printer - Forma de pago "+i+":"+asPays[i]);
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  public void agregarFormaPago(String sDesc) throws Exception {
    int i;
    for( i=0; i<20 && asPays[i] != null; i++ ) {
      if( asPays[i].equalsIgnoreCase(sDesc) ) {
        return;
      }
    }

    if( i < 20 ) {
      if( SetPay(i+1,sDesc) ) {
        asPays[i] = sDesc;
        return;
      } else {
        JPrinterException.SendError("No se pudo ingresar forma de pago "+sDesc);
      }
    } else {
      PssLogger.logFiscal("Fiscal Printer - No se pudo ingresar forma de pago por falta de espacio: "+sDesc);
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  public int getPayment( String sDesc ) throws Exception {
    int lFormaPago = this.getFormaPago( sDesc );
    if( lFormaPago == 0 || !this.setPaymentFiscalJournal(lFormaPago-1) ) {
      PssLogger.logFiscal( "Fiscal Printer - No se puede utilizar la moneda: " + sDesc );
      //lFormaPago = getFormaPago( BizFormaPago.obtenerFormaPagoEfectivoMonedaLocal().GetDescrip() );
      lFormaPago = 1;
      if( lFormaPago == 0 || !setPaymentFiscalJournal(lFormaPago-1) ) {
        PssLogger.logFiscal( "Fiscal Printer - No se puede utilizar la moneda: Efectivo" );
        for( int i=0; i<20 && lFormaPago == 0; i++ ) {
          if( abPays[i] ) {
            lFormaPago = i+1;
          }
        }
        if( lFormaPago == 0 ) {
          lFormaPago = 1;
        }
      }
    }
    return lFormaPago;
  }
//----------------------------------------------------------------------------------------------------------------------
  private int getFormaPago(String sDesc) throws Exception {
    for( int i=0; i<20 && asPays[i]!=null; i++ ) {
      if( asPays[i].equalsIgnoreCase(sDesc) ) {
        return i+1;
      }
    }
    return 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean isFiscalOpenStatus() throws Exception {
    if( status() ) {
      return !GetFiscalStatus( 12 )
          && !GetFiscalStatus( 13 )
          && !GetFiscalStatus( 14 )
          &&  GetFiscalStatus( 15 );
    }
    return false;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void loadPaymentFiscalJournal() throws Exception {
    PssLogger.logFiscal( "loadPaymentFiscalJournal" );
    for( int i=0; i<20 ; i++ ) {
      abPays[i] = false;
    }

    if( enviarRecibirComando( false, CMD_PAGOS_JORNADA, EXT_INFO_PAGOS_JORNADA, "" ) ) {
      for( int i = 0; i < 5; i++ ) {
        String sMoneda = getAnswerField( 7 + 2 * i, LastMessageReceived );
        if( sMoneda.length() > 0 ) {
          abPays[Integer.parseInt( sMoneda ) - 1] = true;
        }
      }
    }
  }

  public boolean setPaymentFiscalJournal(int iPos) {
    if( !abPays[iPos] ) {
      int iPayments = 0;
      for( int i=0; i<20; i++ ) {
        if( abPays[i] ) {
          iPayments++;
        }
        if( iPayments >= 5 ) {
          return false;
        }
      }
    }
    abPays[iPos] = true;
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  protected String getCommandDesc( int iCommand ) {
    switch (iCommand) {
      case 0x0001:
        return "Obtener Estado";
      case 0x0003:
        return "Obtener Error de Inicio";
      case 0x0004:
        return "Obtener Error de Proceso Interno";
      case 0x000A:
        return "Configurar velocidad de comunicación (host port)";
      case 0x0201:
        return "Documento de diagnóstico del mecanismo impresor";
      case 0x0204:
        return "Ripple Test";
      case 0x020A:
        return "Obtener Características Fiscales";
      case 0x0401:
        return "Serializar";
      case 0x0402:
        return "Obtener Datos de Serialización";
      case 0x0403:
        return "Fiscalizar";
      case 0x0404:
        return "Bloquear";
      case 0x0405:
        return "Desbloquear";
      case 0x0501:
        return "Configurar Fecha y Hora";
      case 0x0502:
        return "Obtener Configuración de Fecha y Hora";
      case 0x0503:
        return "Configurar Datos de Fiscalización";
      case 0x0506:
        return "Configurar máximo porcentaje de descuento";
      case 0x0507:
        return "Obtener datos de Fiscalización";
      case 0x0508:
        return "Configurar Líneas de Encabezado";
      case 0x0509:
        return "Obtener Configuración de Líneas de Encabezado";
      case 0x050A:
        return "Configurar Líneas de Cola";
      case 0x050B:
        return "Obtener Configuración de Líneas de Cola";
      case 0x050C:
        return "Configurar Tipo de pago";
      case 0x050D:
        return "Obtener Tipo de Pago";
      case 0x0520:
        return "Configurar Claves de Firma Digital";
      case 0x0521:
        return "Generar Claves de Firma Digital";
      case 0x0522:
        return "Obtener Clave Pública de Firma Digital";
      case 0x0530:
        return "Iniciar Carga de Logos del Usuario";
      case 0x0531:
        return "Enviar Datos de Logos del Usuario";
      case 0x0532:
        return "Terminar Carga de Logos del Usuario";
      case 0x0533:
        return "Cancelar Carga de Logos del Usuario";
      case 0x0701:
        return "Avanzar Papel";
      case 0x0702:
        return "Cortar Papel";
      case 0x0707:
        return "Abrir Cajón de Dinero";
      case 0x0801:
        return "Cierre Z";
      case 0x0802:
        return "Informe Cierre Cajero";
      case 0x0803:
        return "Iniciar Informe de Cierres Z por rango de fechas";
      case 0x0804:
        return "Iniciar Informe de Cierres Z por rango de cierres Z";
      case 0x080A:
        return "Información de Jornada Fiscal";
      case 0x080B:
        return "Información de Impuestos de Jornada Fiscal";
      case 0x080C:
        return "Información de Pagos de Jornada Fiscal";
      case 0x080E:
        return "Información por rango de fechas de Jornada Fiscal";
      case 0x080F:
        return "Información por rango de cierres Z";
      case 0x0810:
        return "Iniciar Informe de Transacciones por rango de fechas";
      case 0x0811:
        return "Iniciar Informe de Transacciones por rango de cierres Z";
      case 0x0820:
        return "Obtener Siguientes Datos de Informes de Jornada Fiscal";
      case 0x0821:
        return "Finalizar Informe de Jornada Fiscal";
      case 0x0822:
        return "Cancelar Informe de Jornada Fiscal";
      case 0x0A01:
        return "Abrir Boleta Fiscal";
      case 0x0A02:
        return "Item Boleta Fiscal";
      case 0x0A03:
        return "Subtotal Boleta Fiscal";
      case 0x0A04:
        return "Descuentos/Recargos Boleta Fiscal";
      case 0x0A05:
        return "Pago Boleta Fiscal";
      case 0x0A06:
        return "Cerrar Boleta Fiscal";
      case 0x0A08:
        return "Configurar Preferencias Boleta Fiscal";
      case 0x0A09:
        return "Obtener Configuración de Preferencias Boleta Fiscal";
      case 0x0A0A:
        return "Información Boleta Fiscal";
      case 0x0A0B:
        return "Información de Impuestos Boleta Fiscal";
      case 0x0A0C:
        return "Información de Pagos Boleta Fiscal";
      case 0x0A10:
        return "Donaciones Boleta Fiscal";
      case 0x0E01:
        return "Abrir Documento No Fiscal";
      case 0x0E02:
        return "Imprimir Texto no Fiscal";
      case 0x0E06:
        return "Cerrar Documento No Fiscal";
      case 0x0E0A:
        return "Información Documento No Fiscal";
      case 0x3001:
        return "Abrir Documento No Fiscal de Medio de Pago";
      case 0x3002:
        return "Imprimir Texto no Fiscal  de Medio de Pago";
      case 0x3006:
        return "Cerrar Documento No Fiscal de Medio de Pago";
      case 0x300A:
        return "Información Documento No Fiscal de Medio de Pago";
    }
    return "Desconocido";
  }

  protected String getErrorDesc( int iError ) {
    switch (iError) {
      case 0x0000:
        return "Resultado exitoso";
      case 0x0001:
        return "Error interno";
      case 0x0002:
        return "Error de inicialización del equipo";
      case 0x0003:
        return "Error de proceso interno";
      case 0x0101:
        return "Comando inválido para el estado actual";
      case 0x0102:
        return "Comando inválido para el documento actual";
      case 0x0103:
        return "Comando sólo aceptado en modo técnico";
      case 0x0104:
        return "Comando sólo aceptado con jumper de desbloqueo";
      case 0x0105:
        return "Comando sólo aceptado sin jumper de desbloqueo";
      case 0x0106:
        return "Comando sólo aceptado sin switch de servicio";
      case 0x0107:
        return "Comando sólo aceptado con switch de servicio";
      case 0x0109:
        return "Demasiadas intervenciones técnicas";
      case 0x0201:
        return "El frame no contiene el largo mínimo aceptado";
      case 0x0202:
        return "Comando inválido";
      case 0x0203:
        return "Campos en exceso";
      case 0x0204:
        return "Campos en defecto";
      case 0x0205:
        return "Campo no opcional";
      case 0x0206:
        return "Campo alfanumérico inválido";
      case 0x0207:
        return "Campo alfabético inválido";
      case 0x0208:
        return "Campo numérico inválido";
      case 0x0209:
        return "Campo binario inválido";
      case 0x020A:
        return "Campo imprimible inválido";
      case 0x020B:
        return "Campo hexadecimal inválido";
      case 0x020C:
        return "Campo fecha inválido";
      case 0x020D:
        return "Campo hora inválido";
      case 0x020E:
        return "Campo fiscal rich text inválido";
      case 0x020F:
        return "Campo booleano inválido";
      case 0x0210:
        return "Largo del campo inválido";
      case 0x0211:
        return "Extensión del comando inválida";
      case 0x0212:
        return "Código de barra no permitido";
      case 0x0213:
        return "Atributos de impresión no permitidos";
      case 0x0214:
        return "Atributos de impresión inválidos";
      case 0x0215:
        return "Código de barras incorrectamente definido";
      case 0x0301:
        return "Error de hardware";
      case 0x0302:
        return "Impresora fuera de línea";
      case 0x0303:
        return "Error de Impresión";
      case 0x0304:
        return "Falta de papel";
      case 0x0305:
        return "Poco papel disponible";
      case 0x0307:
        return "Característica de impresora no soportada";
      case 0x0401:
        return "Número de serie inválido";
      case 0x0402:
        return "Deben configurarse los datos de fiscalización";
      case 0x0501:
        return "Fecha / Hora no configurada";
      case 0x0502:
        return "Error en cambio de fecha";
      case 0x0503:
        return "Fecha fuera de rango";
      case 0x0505:
        return "Número de caja inválido";
      case 0x0506:
        return "RUT inválido";
      case 0x0508:
        return "Número de línea de Encabezado/Cola inválido";
      case 0x0509:
        return "Demasiadas fiscalizaciones";
      case 0x050B:
        return "Demasiados cambios de datos de fiscalización";
      case 0x050C:
        return "Demasiados tipos de pagos definidos";
      case 0x050D:
        return "Tipo de pago definido previamente";
      case 0x050E:
        return "Número de pago inválido";
      case 0x050F:
        return "Descripción de pago inválida";
      case 0x0510:
        return "Máximo porcentaje de descuento inválido";
      case 0x0511:
        return "Claves de firma digital inválidas";
      case 0x0512:
        return "Claves de firma digital no configuradas";
      case 0x0523:
        return "Time out en generación de claves";
      case 0x0601:
        return "Memoria de transacciones llena";
      case 0x0801:
        return "Comando inválido fuera de la jornada fiscal";
      case 0x0802:
        return "Comando inválido dentro de la jornada fiscal";
      case 0x0803:
        return "Memoria fiscal llena. Imposible la apertura de la jornada fiscal";
      case 0x0804:
        return "Más de 24 hs desde la apertura de la jornada fiscal. Se requiere cierre Z";
      case 0x0805:
        return "Pagos no definidos";
      case 0x0806:
        return "Demasiados pagos utilizados en la jornada fiscal";
      case 0x0807:
        return "Periodo auditado sin datos";
      case 0x0901:
        return "Overflow";
      case 0x0902:
        return "Underflow";
      case 0x0903:
        return "Demasiados ítems involucrados en la transacción";
      case 0x0904:
        return "Demasiadas tasas de impuesto utilizadas";
      case 0x0905:
        return "Demasiados descuentos/recargos sobre subtotal involucradas en la transacción";
      case 0x0906:
        return "Demasiados pagos involucrados en la transacción";
      case 0x0907:
        return "Item no encontrado";
      case 0x0908:
        return "Pago no encontrado";
      case 0x0909:
        return "El total debe ser mayor a cero";
      case 0x090C:
        return "Tipo de pago no definido";
      case 0x090D:
        return "Demasiadas donaciones";
      case 0x090E:
        return "Donación no encontrada";
      case 0x0A01:
        return "No permitido luego de descuentos/recargos sobre el subtotal";
      case 0x0A02:
        return "No permitido luego de iniciar la fase de pago";
      case 0x0A03:
        return "Tipo de ítem inválido";
      case 0x0A04:
        return "Línea de descripción en blanco";
      case 0x0A05:
        return "Cantidad resultante menor que cero";
      case 0x0A06:
        return "Cantidad resultante mayor a lo permitido";
      case 0x0A07:
        return "Precio total del ítem mayor al permitido";
      case 0x0A08:
        return "No permitido antes de iniciar la fase pago";
      case 0x0A09:
        return "Fase de pago no finalizada";
      case 0x0A0A:
        return "Fase de pago finalizada";
      case 0x0A0B:
        return "Monto de pago no permitido";
      case 0x0A0C:
        return "Monto de descuento no permitido";
      case 0x0A0D:
        return "Monto de donación no permitido";
      case 0x0A0E:
        return "Vuelto no mayor a cero";
      case 0x0A0F:
        return "No permitido antes de un ítem";
      case 0x0E01:
        return "Se llegó al limite de 30 líneas";
      case 0xFFFF:
        return "Desconocido";
    }
    return "No Documentado";
  }
  
  public boolean inicialize() throws Exception {
  	if (this.bInitialized) return false;	  
  	this.openPort();
  	try {
    	this.verifyVersion();
      this.fiscalPreferences( true, true );
      this.cleanHeader();
      this.cleanFooter();
      this.fiscalCancel();
      this.noFiscalCancel();
	  } catch( Exception e ) {
	    close();
	    throw e;
	  }
    this.bInitialized = true;
    return true;
	}
  
  private void setDateAndTime() throws Exception {
    this.setDateTime(JDateTools.CurrentDate("ddMMyy"), JDateTools.CurrentTime("HHmmss"));
  }

  public Answer openCashDrawer() throws Exception {
    try {
      this.enviarRecibirComando( true, CMD_OPEN_CASH_DRAWER, EXT_CAJON_2, "" );
    } catch( Exception e ) {}
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
	public Answer isCashDrawerOpen() throws Exception {
		boolean ok=false; 
    try {
      if(this.status()) ok=this.getPrinterStatus( 3 );
    } catch( Exception e ) {}
    return new AwrBoolean(ok);
  }


}
