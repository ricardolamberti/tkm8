package pss.common.terminals.drivers.EpsonFiscal;

import pss.common.terminals.core.JPrinterException;
import pss.core.connectivity.client.serial.JSerialClient;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class EpsonDriver {

	private static final int FISCAL = 1;
	private static final int NFISCAL = 2;
	private static final int NFHOMOL = 3;
	private static final int NONE = 0;	

	private JSerialClient serial;
	private boolean isOpen = false;
	private int docOpen = NONE;
  private char SequenceNumber = 0x20;
//  private String ComPort = "";
  private String LastMessageReceived = "";
//  private char LastCommandSent = 0x00;
  private String LastStatusReceived = "";
  private static final String SEP = String.valueOf( ( char ) 0x1c );
  private static final String STX = String.valueOf( ( char ) 0x02 );
  private static final String ETX = String.valueOf( ( char ) 0x03 );
  private static final char NACK = ( char ) 0x15;
  private static final char DC2 = ( char ) 0x12;
  private static final char DC4 = ( char ) 0x14;

  private String aHeaderLines[] = new String[10];
  private String aFooterLines[] = new String[10];

// CODIGOS DE LOS COMANDOS PARA LA IMPRESORA FISCAL EPSON
  private static final char COMANDO_STATUS = 42;
  private static final char COMANDO_CIERRE = 57;
  private static final char COMANDO_OPEN_DRAWER = 123;
  private static final char COMANDO_DATE_TIME = 88;
  private static final char COMANDO_OPEN_INVOICE = 96;
  private static final char COMANDO_CLOSE_NO_FISCAL = 74;
  private static final char COMANDO_SEND_INVOICE_PAYMENT = 100;
  private static final char COMANDO_SEND_TICKET_PAYMENT = 68;
  private static final char COMANDO_DNFH_CREDIT_CARD = 79;
  private static final char COMANDO_SEND_NO_FISCAL_TEXT = 73;
  private static final char COMANDO_OPEN_TICKET = 64;
  private static final char COMANDO_OPEN_NO_FISCAL = 72;
  private static final char COMANDO_SEND_TICKET_ITEM = 66;
  private static final char COMANDO_SEND_INVOICE_ITEM = 98;
  private static final char COMANDO_CLOSE_TICKET = 69;
  private static final char COMANDO_CLOSE_INVOICE = 101;
  private static final char COMANDO_GET_TICKET_SUBTOTAL = 67;
  private static final char COMANDO_GET_INVOICE_SUBTOTAL = 99;
  private static final char COMANDO_SLIP_USER_PREFS = 90;
  private static final char COMANDO_SELECT_SLIP = 92;
  private static final char COMANDO_DATOS_FIJOS = 93;
  private static final char COMANDO_PRINT_FISCAL_TEXT = 65;
  private static final char COMANDO_ADVANCE_TICKET = 80;
  private static final char COMANDO_ADVANCE_JOURNAL = 81;
  private static final char COMANDO_ADVANCE_TICKET_JOURNAL = 82;
  private static final char COMANDO_ADVANCE_SLEEP = 81;
  private static final char COMANDO_PERCEPT = 102;
//----------------------------------------------------------------------------------------------------------------------
//  public JEpsonSerialMessage(String Port, int baudrate) throws Exception {
//    oSerial = new JSerialClient();
//    oSerial.setPort( this.ComPort = Port );
//    oSerial.setBauds( baudrate );
//    oSerial.setDataBits( 8 );
//    oSerial.setParity( "NONE" );
//    oSerial.setStopBit( 1 );
//  }
  

  

  public EpsonDriver() throws Exception {
  }
  
  
//----------------------------------------------------------------------------------------------------------------------
  public void open(String port, int baud) throws Exception {
    serial = new JSerialClient();
    serial.setPort( port );
    serial.setBauds( baud );
    serial.setDataBits( 8 );
    serial.setParity("NONE" );
    serial.setStopBit( 1 );
    if (!serial.connect())
    	JExcepcion.SendError("Error abriendo el puerto serial "+ port);
  }
  

//----------------------------------------------------------------------------------------------------------------------
  private String getBcc( String Message ) {
    int Resultado = 0x0000;
    StringBuffer BccResult = new StringBuffer();

    for( int a = 0; a < Message.length(); a++ )
      Resultado = ( Resultado + Message.charAt( a ) ) & 0xffff;

    if( ( Resultado & 0xf000 ) > 0x9000 )
      BccResult.append( String.valueOf( ( char ) ( ( ( Resultado & 0xf000 ) >> 12 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0xf000 ) >> 12 ) );
    if( ( Resultado & 0x0f00 ) > 0x0900 )
      BccResult.append( String.valueOf( ( char ) ( ( ( Resultado & 0x0f00 ) >> 8 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0x0f00 ) >> 8 ) );
    if( ( Resultado & 0x00f0 ) > 0x0090 )
      BccResult.append( String.valueOf( ( char ) ( ( ( Resultado & 0x00f0 ) >> 4 ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( ( Resultado & 0x00f0 ) >> 4 ) );
    if( ( Resultado & 0x000f ) > 0x0009 )
      BccResult.append( String.valueOf( ( char ) ( ( Resultado & 0x000f ) + 55 ) ) );
    else
      BccResult.append( String.valueOf( Resultado & 0x000f ) );
    return BccResult.toString();
  }
//----------------------------------------------------------------------------------------------------------------------
  public void enviarRecibirComando( char CommandNumber, String Parameters ) throws Exception {
    if( !this.enviarRecibirComandoUnico( CommandNumber, 3, Parameters ) ) {
      if( !this.enviarRecibirComandoUnico( CommandNumber, 1, Parameters ) ) {
        this.sleep(5000);
        if( !this.enviarRecibirComandoUnico( CommandNumber, 1, Parameters ) ) {
          JExcepcion.SendError("Error: maxima cantidad de intentos exedido");
        }
      }
    }
    
    if(this.fiscalStateNotValidForCurrentReply(LastMessageReceived)) 
      JExcepcion.SendError("ERROR - " + this.getLastError());    
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean enviarRecibirComandoUnico( char CommandNumber, int iNacksRetries, String Parameters ) {
    String MessageOut;
    boolean MessageOK = false;
    String MessageIn = "";

    MessageOut = STX + String.valueOf(SequenceNumber) + String.valueOf(CommandNumber);
    if( !Parameters.equals("") )  MessageOut = MessageOut + SEP + Parameters;
    MessageOut = MessageOut + ETX;
    MessageOut = MessageOut + getBcc( MessageOut );

    serial.write(MessageOut);
    PssLogger.logDebug( "SND: "+ MessageOut );

    boolean bEndLoop = false;
    for( int iNacks = 0; !bEndLoop && iNacks < iNacksRetries; iNacks++ ) {
      if( iNacks != 0 ) {
        PssLogger.logDebug( "Falla en recepción de mensaje - Envío de NACK" );
        serial.write( String.valueOf( NACK ) );
        PssLogger.logDebug( "SND: "+ String.valueOf( NACK ) );
        MessageIn = "";
      }

      int LastLen = 0, CommandWaitRetries = 0, CommandResendRetries = 0;
      while( !bEndLoop ) {
        sleep( 100 );
        MessageIn += serial.recv();

        if( MessageIn.length() == LastLen ) {
          if( MessageIn.length() > 1 ) {
            if( MessageIn.charAt( MessageIn.length() - 1 ) != DC2
                || MessageIn.charAt( MessageIn.length() - 1 ) == DC4 ) {
              break;
            } else {
              CommandWaitRetries++;
              if( CommandWaitRetries > 10 )
                break;
            }
          } else {
            CommandWaitRetries++;
            if( CommandWaitRetries > 10 )
              break;
          }
        } else {
          if( MessageIn.length() > 50
              && ( MessageIn.charAt( MessageIn.length() - 1 ) == DC2
                   || MessageIn.charAt( MessageIn.length() - 1 ) == DC4 ) ) {
            return false; // Excedió los 50 CommandWaits
          }
          CommandWaitRetries = 0;
        }

        LastLen = MessageIn.length();
        if( MessageIn.length() > 5 ) {
          if( MessageIn.charAt( MessageIn.length() - 5 ) == ETX.charAt( 0 ) ) {
            MessageOK = true;
            bEndLoop = true;
          }
        } else if( MessageIn.length() > 0 ) {
          if( MessageIn.charAt( MessageIn.length() - 1 ) == NACK ) {
            serial.write( MessageOut );
//            PssLogger.logDebug( "EpsonSerial: Reenvío del mensaje por recepción de NACK" );
            PssLogger.logDebug( "SND: "+MessageOut );
            CommandWaitRetries = 0;
          } else if( MessageIn.charAt( MessageIn.length() - 1 ) == DC2
                     || MessageIn.charAt( MessageIn.length() - 1 ) == DC4 ) {
            sleep( 300 );
          } else {
            sleep( 50 );
          }
        } else {
          sleep( 200 );
        }
      }

      PssLogger.logDebug( "RCV: " + MessageIn.toString() );
      int MessageStart = MessageIn.indexOf( STX.charAt( 0 ) );
      if( !MessageOK ) {
        PssLogger.logDebug( "ERROR - Mensaje incompleto" );
        bEndLoop = false;
      } else if( MessageStart < 0 ) {
        PssLogger.logDebug( "ERROR - No se encuentra inicio del mensaje" );
        bEndLoop = false;
      } else {
        MessageIn = MessageIn.substring( MessageStart );
        String BccRecibido = MessageIn.substring( MessageIn.length() - 4, MessageIn.length() );
        if( !BccRecibido.equals( getBcc( MessageIn.substring( 0, MessageIn.length() - 4 ) ) ) ) {
          PssLogger.logDebug( "ERROR - Falla en BCC" );
          bEndLoop = false;
        } else if( MessageIn.charAt(1) != SequenceNumber ) {
          PssLogger.logDebug( "ERROR - Falla en secuencia del comando" );
          bEndLoop = false;
        }
      }
    }
    LastMessageReceived = MessageIn;
//    LastCommandSent = CommandNumber;
    SequenceNumber++;
    if( SequenceNumber > 0x7f )  SequenceNumber = 0x20;
    return bEndLoop;
  }
  
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
  public void closePort() {
    serial.disconnect();
  }
  public void openPort() {
    serial.connect();
  }
//----------------------------------------------------------------------------------------------------------------------
  private boolean fiscalStateNotValidForCurrentReply(String msg) throws Exception {
    String reply = getAnswerField(2, msg);
    if( reply.equals("") )  return false;
    return isBitOn((int)JTools.hexaToDecimal(reply), 15);
  }
//----------------------------------------------------------------------------------------------------------------------
  public String getLastError() throws Exception {
    String reply = this.getAnswerField(2, LastMessageReceived);

    if( isBitOn(reply,0) ) return "Error de Comprobación de Memoria Fiscal";
    if( isBitOn(reply,1) ) return "Error de Comprobación de Memoria de Trabajo";
    if( isBitOn(reply,2) ) return "Error de Batería";
    if( isBitOn(reply,3) ) return "Comando Desconocido";
    if( isBitOn(reply,4) ) return "Campos del Comando no Válidos";
    if( isBitOn(reply,5) ) return "Comando no Válido para Estado Fiscal";
    if( isBitOn(reply,6) ) return "Error de Desbordamiento de Total";
    if( isBitOn(reply,7) ) return "Error de Memoria Fiscal Llena";
    if( isBitOn(reply,8) ) return "Error de Memoria Fiscal Casi Llena";
    if( isBitOn(reply,11) ) return "Necesita hacer un Cierre Z";

    reply = getAnswerField(1, LastMessageReceived);

    if( isBitOn(reply,14) ) return "Impresora sin Papel";
    if( isBitOn(reply,2) ) return "Error de Impresora";
    if( isBitOn(reply,3) ) return "Impresora Fuera de Línea";
    if( isBitOn(reply,4) ) return "Poco Papel en Cinta Auditora";
    if( isBitOn(reply,5) ) return "Poco Papel en Cinta de Comprobantes";
    if( isBitOn(reply,6) ) return "Buffer de Impresora Lleno";

    return "";
  }
//----------------------------------------------------------------------------------------------------------------------
  private boolean isBitOn( int status, int bit) throws Exception {
    int a = 0x0001 << bit;
    return (a & status) != 0;
  }
  
  private boolean isBitOn( String status, int bit) throws Exception {
  	return this.isBitOn((int)JTools.hexaToDecimal(status), bit);
  }
//----------------------------------------------------------------------------------------------------------------------
  public String getAnswerField( int FieldNumber ) {
    String FieldResponse = "";
    int StartingAt = 0;
    int EndingAt = 0;
    for( int a = 0; a<FieldNumber; a++ ) {
      StartingAt = LastMessageReceived.indexOf(SEP, StartingAt);
      if( StartingAt < 0 )  return FieldResponse;
      StartingAt++;
    }
    EndingAt = LastMessageReceived.indexOf(SEP, StartingAt);
    if( EndingAt < 0 ) {
      EndingAt = LastMessageReceived.indexOf(ETX, StartingAt);
      if( EndingAt < 0 )  return FieldResponse;
    }
    FieldResponse = LastMessageReceived.substring(StartingAt, EndingAt);
    return FieldResponse;
  }
//----------------------------------------------------------------------------------------------------------------------
  private String getAnswerField(int FieldNumber, String msg) {
    int startingAt = 0;
    int endingAt   = 0;
    for(int a = 0; a<FieldNumber; a++) {
      startingAt = msg.indexOf(SEP, startingAt);
      if( startingAt < 0 )  return "";
      startingAt++;
    }
    endingAt = msg.indexOf(SEP, startingAt);
    if( endingAt < 0 ) {
      endingAt = msg.indexOf(ETX, startingAt);
      if( endingAt < 0 )  return "";
    }
    return msg.substring(startingAt, endingAt);
  }
//----------------------------------------------------------------------------------------------------------------------
  public String getPrinterStatus() {
    LastMessageReceived = LastStatusReceived;
    return getAnswerField( 1 );
  }
//----------------------------------------------------------------------------------------------------------------------
  public String getFiscalStatus() {
    LastMessageReceived = LastStatusReceived;
    return getAnswerField( 2 );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void status( String Type ) throws Exception {
    this.enviarRecibirComando( COMANDO_STATUS, Type );
    LastStatusReceived = LastMessageReceived;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean isSlipReady() throws Exception {
    this.status( "D" );
    int iError = ( int ) JTools.hexaToDecimal( getAnswerField( 1, LastMessageReceived ) );
//    System.out.println(getAnswerField( 1, LastMessageReceived )+ " - "+getAnswerField( 2, LastMessageReceived ) );
    return isBitOn( iError, 9 );
  }
  
//----------------------------------------------------------------------------------------------------------------------
  public void closeJournal( String TipoCierre, String TipoReporte ) throws Exception {
    this.enviarRecibirComando( COMANDO_CIERRE, TipoCierre + SEP + TipoReporte );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void openCashDrawer() throws Exception {
    this.enviarRecibirComando( COMANDO_OPEN_DRAWER, "" );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void setDateTime( String Date, String Time ) throws Exception {
    this.enviarRecibirComando( COMANDO_DATE_TIME, Date + SEP + Time );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void printFiscalText( String Texto ) throws Exception {
    Texto = JTools.replaceForeignChars( Texto );
    this.enviarRecibirComando( COMANDO_PRINT_FISCAL_TEXT, Texto );
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean cleanHeaderInfo() {
    for( int i = 0; i < 10; i++ ) {
      if( !setHeaderInfo( i, "" ) ) {
        return false;
      }
    }
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean cleanFooterInfo() {
    for( int i = 0; i < 10; i++ ) {
      if( !setFooterInfo( i, "" ) ) {
        return false;
      }
    }
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean setHeaderInfo( int lLineNumber, String line ) {
    line = JTools.replaceForeignChars( line );
    if( !line.equals( aHeaderLines[lLineNumber] ) ) {
      aHeaderLines[lLineNumber] = line;
      if( line.trim().length() == 0 ) {
        line = String.valueOf((char)0x7f);
      }
      if( !enviarRecibirComandoUnico( COMANDO_DATOS_FIJOS, 1, String.valueOf( lLineNumber + 1 ) + SEP + line ) )
        return false;
    }
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean setFooterInfo( int lLineNumber, String line ) {
    line = JTools.replaceForeignChars( line );
    if( !line.equals( aFooterLines[lLineNumber] ) ) {
      aFooterLines[lLineNumber] = line;
      if( line.length() == 0 ) {
        line = String.valueOf((char)0x7f);
      }
      if( !enviarRecibirComandoUnico( COMANDO_DATOS_FIJOS, 1, String.valueOf( lLineNumber + 11 ) + SEP + line ) )
        return false;
    }
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void openInvoice( String[] InvoiceData  ) throws Exception {
    StringBuffer params = new StringBuffer(200);
    for( int a = 0; a < InvoiceData.length; a++ ) {
      params.append( JTools.replaceForeignChars( InvoiceData[a] ) );
      if( a < InvoiceData.length - 1 ) {
        params.append(SEP);
      }
    }
    this.enviarRecibirComando( COMANDO_OPEN_INVOICE, params.toString() );
    this.isOpen=true;
    this.docOpen=FISCAL;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void closeNoFiscal( String TipoCorte ) throws Exception {
    this.enviarRecibirComando( COMANDO_CLOSE_NO_FISCAL, TipoCorte );
    this.isOpen=false;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendInvoicePayCanDis( String Descripcion, String Monto, String TipoPago ) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.enviarRecibirComando( COMANDO_SEND_INVOICE_PAYMENT, Descripcion + SEP + Monto + SEP + TipoPago );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendInvoiceCancel() throws Exception {
    this.sendInvoicePayCanDis(" ", "100", "C");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendInvoicePayment(String Descripcion, String Monto) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.sendInvoicePayCanDis(Descripcion, Monto, "T");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendInvoiceRecargo(String Descripcion, String Monto) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.sendInvoicePayCanDis(Descripcion, Monto, "R");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendTicketPayCanDis( String Descripcion, String Monto, String TipoPago ) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.enviarRecibirComando( COMANDO_SEND_TICKET_PAYMENT, Descripcion + SEP + Monto + SEP + TipoPago );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendTicketCancel() throws Exception {
    this.sendTicketPayCanDis("", "", "C");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendTicketPayment(String Descripcion, String Monto) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.sendTicketPayCanDis(Descripcion, Monto, "T");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendTicketRecargo(String Descripcion, String Monto) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.sendTicketPayCanDis(Descripcion, Monto, "R");
  }
//----------------------------------------------------------------------------------------------------------------------
  public void printDNFHCreditCard( String[] VoucherData, int howManyCpopies ) throws Exception {
    StringBuffer params = new StringBuffer( 2000 );
    params.append( "01" + SEP );
    for( int a = 0; a < VoucherData.length; a++ ) {
      params.append( VoucherData[a] );
      if( a < VoucherData.length - 1 ) {
        params.append( SEP );
      }
    }
    this.enviarRecibirComando( COMANDO_DNFH_CREDIT_CARD, params.toString()); 
    this.enviarRecibirComando( COMANDO_DNFH_CREDIT_CARD, params.toString());
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendNoFiscalText( String Texto ) throws Exception {
    Texto = JTools.replaceForeignChars( Texto );
    this.enviarRecibirComando( COMANDO_SEND_NO_FISCAL_TEXT, Texto );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void openTicket( String TipoAlmacenamiento ) throws Exception {
    this.enviarRecibirComando( COMANDO_OPEN_TICKET, TipoAlmacenamiento );
    this.isOpen=true;
    this.docOpen=FISCAL;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void openNoFiscal( boolean isSlip ) throws Exception {
    this.enviarRecibirComando( COMANDO_OPEN_NO_FISCAL, isSlip ? "U"+SEP+"O" : "" );
    this.isOpen=true;
    this.docOpen=NFISCAL;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendTicketItem( Object[] ItemData  ) throws Exception {
    StringBuffer params = new StringBuffer(200);
    for( int a = 0; a < ItemData.length; a++ ) {
      params.append(ItemData[a].toString());
      if( a < ItemData.length - 1 ) {
        params.append(SEP);
      }
    }
    this.enviarRecibirComando( COMANDO_SEND_TICKET_ITEM, params.toString() );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendInvoiceItem( String[] ItemData  ) throws Exception {
    StringBuffer params = new StringBuffer(200);
    for( int a = 0; a < ItemData.length; a++ ) {
      params.append( JTools.replaceForeignChars( ItemData[a] ) );
      if( a < ItemData.length - 1 ) {
        params.append( SEP );
      }
    }
    this.enviarRecibirComando( COMANDO_SEND_INVOICE_ITEM, params.toString());
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendPercept( String sDesc, long value ) throws Exception {
    sDesc = JTools.replaceForeignChars( sDesc );
    this.enviarRecibirComando( COMANDO_PERCEPT, sDesc + SEP + "O" + SEP + String.valueOf(value));
  }
//----------------------------------------------------------------------------------------------------------------------
  public void sendIVAPercept( String sDesc, long lTasaIVA ) throws Exception {
    sDesc = JTools.replaceForeignChars( sDesc );
    this.enviarRecibirComando( COMANDO_PERCEPT, sDesc + SEP + "T" + SEP + String.valueOf(lTasaIVA) );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void closeTicket() throws Exception {
    this.enviarRecibirComando( COMANDO_CLOSE_TICKET, "T" );
    this.isOpen=false;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void closeInvoice( String TipoDocumento, String LetraDocumento, String Descripcion ) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.enviarRecibirComando( COMANDO_CLOSE_INVOICE, TipoDocumento + SEP + LetraDocumento + SEP + Descripcion );
    this.isOpen=false;
  }
//----------------------------------------------------------------------------------------------------------------------
  public void getTicketSubtotal( String Imprimir, String Descripcion ) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.enviarRecibirComando( COMANDO_GET_TICKET_SUBTOTAL, Imprimir + SEP + Descripcion );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void getInvoiceSubtotal( String Imprimir, String Descripcion ) throws Exception {
    Descripcion = JTools.replaceForeignChars( Descripcion );
    this.enviarRecibirComando( COMANDO_GET_INVOICE_SUBTOTAL, Imprimir + SEP + Descripcion );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void setSlipUserPrefs( String TipoSeteo, int CantidadLineas, int CantidadColumnas ) throws Exception {
    String Parametros;
    String Campo4 = TipoSeteo.equals("D") ? "O" : "U";
    Parametros = "P" + SEP + TipoSeteo + SEP + "S" + SEP + Campo4;
    if( Campo4.equals("U") ) {
      Parametros = Parametros + SEP + String.valueOf(CantidadLineas) + SEP + String.valueOf(CantidadColumnas);
    }
    this.enviarRecibirComando( COMANDO_SLIP_USER_PREFS, Parametros );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void setSlipUserPrefs( String TipoSeteo ) throws Exception {
    this.setSlipUserPrefs(TipoSeteo, 0, 0);
  }
//----------------------------------------------------------------------------------------------------------------------
  public void selectSlip() throws Exception {
    this.enviarRecibirComando( COMANDO_SELECT_SLIP, "D" + SEP + "P" + SEP + "P" + SEP + "U" + SEP + "O" );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void advanceTicket( int iNumeroLineas ) throws Exception {
    this.enviarRecibirComando( COMANDO_ADVANCE_TICKET, String.valueOf(iNumeroLineas) );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void advanceJournal( int iNumeroLineas ) throws Exception {
    this.enviarRecibirComando( COMANDO_ADVANCE_JOURNAL, String.valueOf(iNumeroLineas));
  }
//----------------------------------------------------------------------------------------------------------------------
  public void advanceTicketJournal( int iNumeroLineas ) throws Exception {
    this.enviarRecibirComando( COMANDO_ADVANCE_TICKET_JOURNAL, String.valueOf(iNumeroLineas) );
  }
//----------------------------------------------------------------------------------------------------------------------
  public void advanceSleep( int iNumeroLineas ) throws Exception {
    this.enviarRecibirComando( COMANDO_ADVANCE_SLEEP, String.valueOf(iNumeroLineas) );
  }
//----------------------------------------------------------------------------------------------------------------------
  public String getCommandDesc( char cCommand ) {
    switch( cCommand ) {
    case COMANDO_STATUS:
      return "Consultar Estado de la impresora";
    case COMANDO_CIERRE:
      return "Realizar Cierre";
    case COMANDO_OPEN_DRAWER:
      return "Abrir cajón";
    case COMANDO_DATE_TIME:
      return "Realizar Cambio de Fecha y Hora";
    case COMANDO_OPEN_INVOICE:
      return "Abrir Ticket Factura";
    case COMANDO_CLOSE_NO_FISCAL:
      return "Cerrar Documento no Fiscal";
    case COMANDO_SEND_INVOICE_PAYMENT:
      return "Imprimir Pago de Ticke Factura";
    case COMANDO_SEND_TICKET_PAYMENT:
      return "Imprimir Pago de Ticke Fiscal";
    case COMANDO_DNFH_CREDIT_CARD:
      return "Imprimir Voucher de Tarjeta";
    case COMANDO_SEND_NO_FISCAL_TEXT:
      return "Imprimir Texto no Fiscal";
    case COMANDO_OPEN_TICKET:
      return "Abrir Ticke Fiscal";
    case COMANDO_OPEN_NO_FISCAL:
      return "Abrir Documento no Fiscal";
    case COMANDO_SEND_TICKET_ITEM:
      return "Imprimir Item en Ticke Fiscal";
    case COMANDO_SEND_INVOICE_ITEM:
      return "Imprimir Item en Ticke Factura";
    case COMANDO_CLOSE_TICKET:
      return "Cerrar Ticke Fiscal";
    case COMANDO_CLOSE_INVOICE:
      return "Cerrar Ticke Factura";
    case COMANDO_GET_TICKET_SUBTOTAL:
      return "Capturar Subtotal de Ticke Fiscal";
    case COMANDO_GET_INVOICE_SUBTOTAL:
      return "Capturar Subtotal de Ticke Factura";
    case COMANDO_SLIP_USER_PREFS:
      return "Configurar Impresora Slip";
    case COMANDO_SELECT_SLIP:
      return "Seleccionar Impresora Slip";
    case COMANDO_DATOS_FIJOS:
      return "Configurar Footer";
    case COMANDO_PRINT_FISCAL_TEXT:
      return "Imprimir Texto Fiscal";
    case COMANDO_ADVANCE_TICKET:
      return "Avanzar Papel del Ticket";
    case COMANDO_ADVANCE_JOURNAL:   // COMANDO_ADVANCE_SLEEP
      return "Avanzar Papel del Rollo Diario o Slip";
    case COMANDO_ADVANCE_TICKET_JOURNAL:
      return "Avanzar Papel del Ticket y del Rollo Diario";
    case COMANDO_PERCEPT:
      return "Configurar Precepciones";
    default:
      return "Desconocido";
    }
  }

  private void sleep( int iMillis ) {
    try {Thread.sleep(iMillis); } catch(InterruptedException ie) { }
  }
  
//  public long getCurrentDocNumber() throws Exception {
//    this.status("A");
//    this.verifyFlags();
//    return Long.parseLong(this.getAnswerField(5))+1;
//  }
//  
  public void checkState() throws Exception {
    this.status("A");
    this.verifyFlags();
  }

  public void verifyFlags() throws Exception {
    String binFiscalStatus = this.getFiscalStatus();
    String binPrinterStatus = this.getPrinterStatus();

    if( binPrinterStatus.equals( "0" ) || binFiscalStatus.equals( "0" ) ) {
      JPrinterException.SendError( "No se pudo checkear el estado de la Impresora" );
    }

    if( this.isBitOn( binFiscalStatus, 11 ) ) {
      closeDay();
    }

    boolean bFiscal15 = isBitOn( binFiscalStatus, 15 );
    boolean bPrinter14 = isBitOn( binPrinterStatus, 14 );
    boolean bPrinter15 = isBitOn( binPrinterStatus, 15 );

    this.isOpen = isBitOn( binFiscalStatus, 13 );
    if (this.isOpen) {
    	this.docOpen = isBitOn( binFiscalStatus, 12 ) ? FISCAL : NFISCAL; 
    }
    if( bFiscal15 || bPrinter14 || bPrinter15 ) {
      StringBuffer fatalMessage = new StringBuffer();
      if( isBitOn( binFiscalStatus, 2 ) )
        fatalMessage.append( "Poca Bateria. " );
      if( isBitOn( binFiscalStatus, 8 ) )
        fatalMessage.append( "Memoria Fiscal casi llena. Menos de 40 Cierres para llenarse. " );
      if( isBitOn( binPrinterStatus, 4 ) )
        fatalMessage.append( "Poco Papel para la Cinta de Auditoria. " );
      if( isBitOn( binPrinterStatus, 5 ) )
        fatalMessage.append( "Poco Papel para Comprobantes o Tickets. " );
      if( bPrinter14 && !bPrinter15 )
        fatalMessage.append( "Solucionar falta de Papel antes de imprimir nuevamente. " );
      if( bPrinter14 && bPrinter15 )
        fatalMessage.append( "Impresora sin Papel. " );
      if( isBitOn( binFiscalStatus, 7 ) )
        fatalMessage.append( "Memoria Fiscal llena. LLamar Tecnico Fiscal. " );
      if( isBitOn( binFiscalStatus, 0 ) )
        fatalMessage.append( "Error de comprobacion de Memoria Fiscal. " );
      if( isBitOn( binFiscalStatus, 1 ) )
        fatalMessage.append( "Error de comprobacion de Memoria de Trabajo. " );
      if( isBitOn( binPrinterStatus, 2 ) )
        fatalMessage.append( "Error y/o Falla de Impresora. " );
      if( isBitOn( binPrinterStatus, 3 ) )
        fatalMessage.append( "Impresora fuera de Linea. " );
      if( isBitOn( binPrinterStatus, 6 ) )
        fatalMessage.append( "Buffer de Impresora lleno. " );

      if( fatalMessage.length() != 0 )
        JPrinterException.SendError( fatalMessage.toString() );
    }
  }
  
  public void closeDay() throws Exception {
    this.cleanFooterInfo();
    this.cleanHeaderInfo();
    this.closeJournal( "Z", "P" );
    this.assingDateAndTime();
  }
  
//----------------------------------------------------------------------------------------------------------------------
  private void assingDateAndTime() throws Exception {
    this.setDateTime( JDateTools.CurrentDate( "yyMMdd" ), JDateTools.CurrentTime( "HHmmss" ) );
  }

//----------------------------------------------------------------------------------------------------------------------
  private boolean isOpen() throws Exception {
  	return this.isOpen;
//    isOpen = this.isDocumentOpen();
//    return isOpen;
  }
  
  private boolean isFiscal() throws Exception {
  	return this.docOpen==FISCAL;
  }

  private boolean isNoFiscal() throws Exception {
  	return this.docOpen==NFISCAL;
  }

  //----------------------------------------------------------------------------------------------------------------------
  public boolean isDocumentOpen() throws Exception {
  	this.checkState();
  	return this.isOpen();
//    this.status( "A" );
//
//    String binFiscalStatus = getFiscalStatus();
//    String binPrinterStatus = getPrinterStatus();
//    if( binPrinterStatus.equals( "0" ) || binFiscalStatus.equals( "0" ) ) {
//      JPrinterException.SendError( "No se pudo checkear el estado de la Impresora" );
//    }
//
//    return isBitOn( binFiscalStatus, 13 );
  }

//----------------------------------------------------------------------------------------------------------------------
  public void cancelAllDocs() throws Exception {
    if( !this.isOpen() )
      return;
    if (this.isNoFiscal()) this.closeNoFiscal( "T" );
    if (this.isFiscal()) this.sendInvoiceCancel();
    //this.sendTicketCancel();
    isOpen = false;
  }
  
//  public void closeAllDocs() throws Exception {
//    if( !this.isOpen() )
//      return;
//    if (this.isNoFiscal()) this.closeNoFiscal( "T" );
//    if (this.isFiscal()) this.closeInvoice(this, LetraDocumento, Descripcion);
//    //this.sendTicketCancel();
//    isOpen = false;
//  }

//----------------------------------------------------------------------------------------------------------------------
//  public long getCurrentNum() throws Exception {
//    long nro = this.getCurrentNumReal();
//    return nro < 0 ? 0 : nro + 1;
//  }

  public long getNextFiscalNumber(int pos) throws Exception {
//    try {
      this.checkState();
      String n = this.getAnswerField(pos);
      if (n==null || n.equals("")) return -1L;
      return Long.parseLong(n);
//
//    } catch( Exception e ) {
//      JPrinterException.SendError( "No se pudo obtener informacion del puerto serial. " + e.getMessage() );
//    }
//    return -1;
  }

}
