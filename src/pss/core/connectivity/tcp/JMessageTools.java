package pss.core.connectivity.tcp;

import pss.core.tools.JTools;


public class JMessageTools {

  public static final int     FIRST_COMPRESSED_BYTE       =   120;
  public static final int     SECOND_COMPRESSED_BYTE      =  -100;
  public static final int     CHAR_HEADER                 = -68;
  public static final String  CHAR_HEADER_AS_STRING       = "¼";
  public static final char    CHAR_HEADER_AS_CHAR         = '¼';
  public static final String  ZIP_HEADER_AS_STRING        = "ZIP";
  public static final String  DES_HEADER_AS_STRING        = "DES";
  public static final char    ZIP_HEADER_AS_CHAR          = 'Z';
  public static final char    DES_HEADER_AS_CHAR          = 'D';

//  public static int LONG_PAQUETE = (int) 2048;
  //public static int LONG_PAQUETE = (int) 1460;
  /** DOGUI - Lo tuve que achicar para que funcione bien en WAN */
  public static int LONG_PAQUETE = 512;
  /**************************************************
   * Buffer para almacenar los bytes que se reciben *
   **************************************************/
  private static final int    LONG_BUFFER_PAQUETE     = 2048;

  public static byte [] makeMsgHeader(int iTotData, boolean bCrypted, boolean bZipped ) throws Exception {
    StringBuffer sHeader = new StringBuffer();
    sHeader.append(CHAR_HEADER_AS_STRING);
    if ( bZipped ) {
      sHeader.append(ZIP_HEADER_AS_STRING);
    }
    if ( bCrypted ){
      sHeader.append(DES_HEADER_AS_STRING);
    }
    sHeader = new StringBuffer(JTools.RPad(sHeader.toString(),14," "));
    sHeader.append(CHAR_HEADER_AS_STRING);
    sHeader.append(JTools.IntToStringBigEndian(iTotData));
    byte [] aRta = JTools.stringToByteVector(sHeader.toString());
    return aRta;
  }

  public static byte [] makeShortHeaderMsg(int iTotData) throws Exception {
    StringBuffer sHeader = new StringBuffer();
    sHeader.append(CHAR_HEADER_AS_STRING);
    sHeader.append(JTools.IntToStringBigEndian(iTotData));
    sHeader.append(CHAR_HEADER_AS_STRING);
    byte [] aRta = JTools.stringToByteVector(sHeader.toString());
    return aRta;
  }

  /*************************************************************************************
   * Retorna si el encabezado pasado por parametro indica que el mensaje viene Zipeado *
   *************************************************************************************/
  public static boolean isMsgZipped(byte [] IncomingMsg ) throws Exception {
    if ( !hasMsgHeader(IncomingMsg) ) return false;
    String sCabecera = new String(IncomingMsg,0,17);
    return sCabecera.substring(1).toUpperCase().startsWith(ZIP_HEADER_AS_STRING);
  }
  /****************************************************************************************
   * Retorna si el encabezado pasado por parametro indica que el mensaje viene encriptado *
   ****************************************************************************************/
  public static boolean isMsgCrypted(byte [] IncomingMje) throws Exception {
    if ( !hasMsgHeader(IncomingMje) ) return false;
    String sCabecera = new String(IncomingMje,0,17);
    return sCabecera.substring(1).toUpperCase().indexOf(DES_HEADER_AS_STRING) != -1;
  }

  public static boolean hasMsgHeader(byte [] IncomingMsg) throws Exception {
    return ( (IncomingMsg[0]  == JMessageTools.CHAR_HEADER )&& ( IncomingMsg[14] == JMessageTools.CHAR_HEADER ) );
  }

  /**************************************************
   * Copia los datos que estan un Buffer a un Array *
   *        nuevo LONG_BUFFER_PAQUETE + grande      *
   **************************************************/
  public static byte[] BufferearNuevoArray(byte []Buffer, int BufferAssigned ) {
    byte []Aux = new byte[BufferAssigned + LONG_BUFFER_PAQUETE];
    // Grabo los datos en un array auxiliar
    for ( int i = 0 ; i < BufferAssigned ; i++ ) {
      Aux[i] = Buffer[i];
    }
    return Aux;
  }

  /**********************************************
   *      Obtiene los bytes del IncomingMsg     *
   **********************************************/
  public static int getBytesFromMsg(byte [] Msg) {
   return JMessageTools.getBytesFromMsg(Msg, 15);
  }

  public static int getBytesFromMsg(byte [] Msg, int zPos) {
   int aux = 0;
   aux = (Msg[zPos+1]& 0x00ff);
   aux <<= 8;
   aux |= (Msg[zPos]& 0x00ff) & 0xff;
   return aux;
  }

/*
  public static byte[] ReceiveBytes( Socket oSocket, int Espera, JInteger zBytesReceived ) throws Exception {
    int bytesRecieved = 0;
    zBytesReceived.setValue( bytesRecieved );
    int iHeaderLength = 17;
    byte[] MjeIncoming = new byte[LONG_PAQUETE];
    byte[] Buffer      = new byte[LONG_BUFFER_PAQUETE+iHeaderLength];
    byte[] AuxMsg      = new byte[iHeaderLength];
    int len;
    int BufferAssigned = LONG_BUFFER_PAQUETE; // Me indica los bytes que llevo asignados
    int iLenMsg = 0;
    boolean bLenMode = false;
    boolean bCrypted = false;
    if ( Espera == 0 ) Espera = 10000;
    JDebugPrint.logDebug("Espero leer "  );
    oSocket.setSoTimeout(Espera);
    oSocket.getInputStream().read(AuxMsg, 0, iHeaderLength);
    if ((AuxMsg[0] & 0xff) == JMessageTools.CHAR_HEADER_AS_CHAR ) {
      bLenMode = true;
      if ((AuxMsg[4] & 0xff) == JMessageTools.DES_HEADER_AS_CHAR ) {
        bCrypted = true;
        iHeaderLength += 28;
      }
      iLenMsg = getBytesFromMsg(AuxMsg);
      Buffer      = new byte[iLenMsg + iHeaderLength + 100];
      MjeIncoming = new byte[iLenMsg + 100 ];
      BufferAssigned = iLenMsg + iHeaderLength + 1;
      for (int i = 0 ; i < AuxMsg.length; i++) {
        Buffer[i] = AuxMsg[i];
      }
      bytesRecieved += AuxMsg.length;
      if (bCrypted) {
        AuxMsg = new byte[28];
        oSocket.getInputStream().read(AuxMsg, 0, 28);
        for (int i = 0; i < AuxMsg.length; i++) {
          Buffer[i + 17] = AuxMsg[i];
        }
        bytesRecieved += AuxMsg.length;
      }
    } else {
      for (int i = 0 ; i < AuxMsg.length; i++) {
        Buffer[i] = AuxMsg[i];
      }
      bytesRecieved += AuxMsg.length;
    }
    JDebugPrint.logDebug("Voy a recibir " + iLenMsg );
    while ( true ) {
      try {
        len = oSocket.getInputStream().read(MjeIncoming);
      } catch ( java.io.InterruptedIOException e) {
        len = -1;
        JDebugPrint.logDebug( "Interrupted Exception : " + e.getMessage() );
        oSocket.getOutputStream().write(String.valueOf("").getBytes());
        zBytesReceived.setValue(bytesRecieved);
        return "".getBytes();
      }
      for ( int i = 0 ; i < len ; i++ ) {
        Buffer[bytesRecieved + i ] = MjeIncoming[i];
      }
      bytesRecieved += len;
      if ( bytesRecieved >= BufferAssigned ) {
        byte []Aux = BufferearNuevoArray(Buffer, BufferAssigned );
        Buffer = null;
        Buffer = Aux;
        BufferAssigned += LONG_BUFFER_PAQUETE;
      }
      if ( bLenMode && bytesRecieved >= (iLenMsg + iHeaderLength) )
        break;

      if (oSocket.getInputStream().available() == 0 && bLenMode == false ) break;
      Thread.sleep(1);

    }
    zBytesReceived.setValue(bytesRecieved);
    //JDebugPrint.DebugPrint("Mje raw TCP: " + Mensaje + "***");
    return (JTools.cloneByteArrayWithIndex(Buffer,0,bytesRecieved));
 }
 
  
 protected static void writeMsg( BizTcpClient zTcp, String zMje) throws Exception {
   // set the send timeout
   int timeoutEnvio = 10000;
   if ( zTcp.getTimeoutEnvio() > 0 ) {
     timeoutEnvio = zTcp.getTimeoutEnvio();
   }
   zTcp.getSocket().setSoTimeout(timeoutEnvio);

   // send message
   byte[] mjeaux = new byte [zMje.length()];
     for (int i = 0; i < zMje.length() ; i++){
       mjeaux[i] = (byte)((zMje.charAt(i)) & 0xff);
   }
   zTcp.getSocket().getOutputStream().write(mjeaux);
 }
 
 protected static void writeMsg( BizTcpClient zTcp, byte[] zMje) throws Exception {

   // set the send timeout
   int timeoutEnvio = 10000;
   if ( zTcp.getTimeoutEnvio() > 0 ) {
     timeoutEnvio = zTcp.getTimeoutEnvio();
   }
   zTcp.getSocket().setSoTimeout(timeoutEnvio);
   zTcp.getSocket().getOutputStream().write(zMje);

 }

 public static boolean Enviar( BizTcpClient zTcp, String zMje ) throws Exception {
   try {
     // Send the Message
     JDebugPrint.logDebug("Voy a enviar mensaje ... " + zMje );
     byte[] aReq = JTools.concatByteArray(JMessageTools.makeMsgHeader(zMje.length(),false,false),17,zMje.getBytes(),zMje.length());
     //String sReq = new String(aReq);

     int iLong = JMessageTools.getBytesFromMsg(aReq);
     JDebugPrint.logDebug("Longitud en el mensaje " + iLong );

     writeMsg( zTcp, aReq );
     JDebugPrint.logDebug("Envie mensaje ... " + aReq.length + " Original " + zMje.length() );

     byte[] aRta = ReceiveBytes( zTcp.getSocket(), zTcp.getTimeoutEnvio(), new JInteger() );
     JDebugPrint.logDebug("Recibi respuesta ..." );
     String sRta = new String(aRta);
     zTcp.SetRespuesta(sRta.substring(17));
     return true;

   } catch (Exception e) {
     throw e;
   }

 }
*/
/* public static boolean SendShortMessage( BizTcpClient zTcp, String zMje ) throws Exception {
   try {
     // Send the Message
     JDebugPrint.logDebug("Voy a enviar mensaje ... " + zMje );
     byte[] aReq = JTools.concatByteArray(JMessageTools.makeShortHeaderMsg(zMje.length()),4,zMje.getBytes(),zMje.length());
     //String sReq = new String(aReq);

     int iLong = JMessageTools.getBytesFromMsg(aReq,1);
     JDebugPrint.logDebug("Longitud en el mensaje " + iLong );

     writeMsg( zTcp, aReq );
     JDebugPrint.logDebug("Envie mensaje ... " + aReq.length + " Original " + zMje.length() );

     byte[] aRta = ReceiveBytes( zTcp.getSocket(), zTcp.getTimeoutEnvio(), new JInteger() );
     JDebugPrint.logDebug("Recibi respuesta ..." );
     String sRta = new String(aRta);
     zTcp.SetRespuesta(sRta.substring(4));
     return true;

   } catch (Exception e) {
     throw e;
   }

 }
*/
 
/* public static void main(String[] args) throws Exception {

   JAplicacion.AbrirSesion();
   JAplicacion.GetApp().AbrirApp(JAplicacion.AppShadowAplicacion(), JAplicacion.AppTipoThread(), true );
   BizTcpClient oClient = new BizTcpClient();
   oClient.SetHost("dev2");
   oClient.SetPort(9900);
   oClient.Conectar();

   JMessageTools.Enviar(oClient,"aadsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsalfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsjk;fdskljdsfkjlfdsajklfdsjkl;fdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdskl;fjsadkl;jdsafkl;adsgjsafkl;gjfdsak;gajfdsgkldsaffdsaljk;fdskljdsfkjlfdsajklfdsjkl;fdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdskl;fjsadkl;jdsafkl;adsgjsafkl;gjfdsak;gajfdsgkldsaffdsaljk;fdskljdsfkjlfdsajkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdskl;fjsadkl;jdsafkl;adsgjsafkl;gjfdsak;gajfdsgkldsafasdfjkadsfhdsajkfahdsgjklfadsghjfdkghfdkljfadsghjkfdsagjklhgfsahjgjkhgfsjkhfgdskhjlgfdsjkhlfdkldsjkfdsjklfdjklkjlljkfdsljkdsfl;jkfdsaljk;fdsaljk;dsjkl;fdsakl;jfdsajkl;fdsajkl;dfkjlfdsajklfdsajkl;fdsajkl;fdsajkl;fdsajkl;ajkl;fdljk;fdsaljk;fdskljdsfkjlfdsajklfdsjkl;ffdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsfdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdsdsajkldfsajkldsajkl;fdsljkfdsjklfdjklfdjklsjkldsafjkl;dsajkl;gfdjafdkgl;hfdg;klfdsjghkl;sdfjdskl;fjsadkl;jdsafkl;adsgjsafklfdsaljk;fdskljdsfkjlfdsajklfdsjkdsajkldf012345678123456789011291234556789sajkldsajkl;");

   System.exit(0);

 }*/



}
