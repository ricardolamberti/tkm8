package pss.core.data.files;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;

import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class JCompresion {
  /*************************************************************************************
   * Margen que se le da al buffer para los datos a comprimir o descomprimir.  Se utiliza
   * tambien en la compresion, porque si hay pocos bytes, pude ocurrir que el buffer
   * comprimido pueda ocupar mas que el original
   *************************************************************************************/
  private final static int MARGEN_BUFFER    = 2000;
  /*****************************************************
   * Default size para comprimir o descomprimir mensajes
   *****************************************************/
  private final static int DEFAULT_SIZE     = 40000;
  // ---------------------- //
  //       Atributos        //
  // ---------------------- //
  private int iLenTotalMsg   = DEFAULT_SIZE;
  private int iTotalBytesOut = 0;
  private ZStream c_stream = new ZStream();
  private byte [] dataToUse;
  private byte [] compData;
  private byte [] uncompData;

  /***************************
   * Constructor por defecto
   * @throws Exception
   ****************************/
  public JCompresion() throws Exception { }
  /******************************************************************************
   * Setea el total de caracters maximos a ser usado por el buffer <br>
   * Cuidado en la descompresion.  Si no es suf. lanza Excepcion
   *
   * @param ipDataLength Indica la longitud del buffer
   ******************************************************************************/
  public void setTotalDataLength( int ipDataLength ) {
    iLenTotalMsg = ipDataLength;
  }
  /************************************
   * Retorna los datos ya comprimidos
   ************************************/
  public byte [] getcompData() {
    return compData;
  }
  /************************************
   * Retorna los datos descomprimidos
   ************************************/
  public byte [] getUncompData() {
    return uncompData;
  }
  /************************************************
   * Retorna los datos descomprimidos como String
   ************************************************/
  public String getUncompDataAsString() {
    String sBuffer = new String (getUncompData());
    return sBuffer.substring(0,sBuffer.indexOf('\0'));
  }
  /**************************
   * Descomprime los datos
   **************************/
  public void uncompress() throws Exception {
    int err;
    err = c_stream.inflateInit();
    if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Inicializando Descompresor");
    }
    while(c_stream.total_out < iLenTotalMsg && c_stream.total_in < iLenTotalMsg ) {
      c_stream.avail_in = c_stream.avail_out = 1; // force small buffers
      err = c_stream.inflate(JZlib.Z_NO_FLUSH);
      if( err == JZlib.Z_STREAM_END ) { break; }
      if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Descomprimiendo");
      }
    }
    err = c_stream.inflateEnd();
    if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Finalizando Descompresor");
    }
    iTotalBytesOut = (int)c_stream.total_out;
  }
  /***********************
   * Comprime los datos
   ***********************/
  public void compress() throws Exception {
    int err;
    err = c_stream.deflateInit(JZlib.Z_DEFAULT_COMPRESSION);
    if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Inicializando Compresor");
    }
    while( c_stream.total_in != c_stream.next_in.length  &&
  	  c_stream.total_out < iLenTotalMsg ) {
      c_stream.avail_in=c_stream.avail_out=1;
      err=c_stream.deflate(JZlib.Z_NO_FLUSH);
      if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Comprimiendo mensaje");
      }
    }

    while(true) {
      c_stream.avail_out = 1;
      err = c_stream.deflate(JZlib.Z_FINISH);
      if( err == JZlib.Z_STREAM_END) break;
      if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Comprimiendo mensaje");
      }
    }
    err = c_stream.deflateEnd();
    if ( !VerifyError(c_stream, err ) ) {
        JExcepcion.SendError("Error Finalizando Compresor");
    }
    iTotalBytesOut = (int)c_stream.total_out;
  }// end method
  /******************************************
   * Setea el Input para ser comprimido.
   * Antes setear el largo del buffer
   * @param sData Datos a comprimir
   *******************************************/
  public void setInputToComp ( String sData ) throws Exception {
    setInputToComp(sData.getBytes());
  }
  /******************************************
   * Setea el Input para ser comprimido.
   * Antes setear el largo del buffer
   * @param sData Datos a comprimir
   *******************************************/
  public void setInputToComp(byte []sData ) throws Exception {
    dataToUse = new byte [sData.length + 1 ];
    c_stream.next_in = dataToUse;
    System.arraycopy(sData,0,c_stream.next_in,0,sData.length);    // copio el source
    dataToUse[sData.length] = 0;
    compData = new byte[iLenTotalMsg + MARGEN_BUFFER];
    c_stream.next_in_index = 0;
    c_stream.next_out_index=0;
    c_stream.next_out=compData;
  }
  /********************************************************
   * Setea los datos que seran input para Descomprimirlos
   * @param sData Datos a descomprimir
   ********************************************************/
  public void setInputToUncomp(byte []sData ) throws Exception {
    dataToUse = new byte [sData.length];
    c_stream.next_in = dataToUse;
    System.arraycopy(sData,0,c_stream.next_in,0,sData.length);    // copio el source
    uncompData = new byte[iLenTotalMsg + MARGEN_BUFFER];
    c_stream.next_in_index = 0;
    c_stream.next_out_index=0;
    c_stream.next_out=uncompData;
  }
  /********************************
   * Verifica si hubo algun error
   ********************************/
  private boolean VerifyError(ZStream z, int err ) {
    if ( err == JZlib.Z_OK ) return true;
    if ( z != null ) {
      PssLogger.logError("Error [" + err + "]con Mensaje " + z.msg);
    }else{
      PssLogger.logError("Error [" + err + "] sin Mensaje");
    }
    return false;
  }

  /************************************************************************
   * retorna la cantidad de bytes usados por la compresion o descompresion
   ***********************************************************************/
  public int getTotalBytesOut() { return iTotalBytesOut; }

  public static void main(String[] args) throws Exception {
    JCompresion oComp = new JCompresion();
    oComp.setTotalDataLength("abcde".length());
    oComp.setInputToComp("abcde".getBytes());
    oComp.compress();
    byte [] CompData = oComp.getcompData();
    JCompresion oDesc = new JCompresion();
    oDesc.setInputToUncomp(CompData);
    oDesc.setTotalDataLength(50);
    oDesc.uncompress();
    System.out.println(oDesc.getUncompDataAsString());
  }
}
