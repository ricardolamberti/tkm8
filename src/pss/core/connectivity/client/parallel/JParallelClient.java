package pss.core.connectivity.client.parallel;

import pss.core.tools.JExcepcion;
import pss.core.tools.JObjectArrayPool;
import pss.core.tools.jnidisp.CFunc;
import pss.core.tools.jnidisp.CMalloc;

public class JParallelClient {

  public static int PARALLEL_INIT          = 0x1;
  public static int PARALLEL_AUTOFEED      = 0x2;
  public static int PARALLEL_PAPER_EMPTY   = 0x4;
  public static int PARALLEL_OFF_LINE      = 0x8;
  public static int PARALLEL_POWER_OFF     = 0x10;
  public static int PARALLEL_NOT_CONNECTED = 0x20;
  public static int PARALLEL_BUSY          = 0x40;
  public static int PARALLEL_SELECTED      = 0x80;

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
  private String port;
  private boolean bConnected = false;

  private CFunc cOpenPort;
  private CFunc cWritePort;
  private CFunc cReadPort;
  private CFunc cClosePort;
  private CFunc cControlPort;

  /* JNI TRANSMIT-RECEIVE BUFFER */
  private CMalloc cBufferIn;
//  private CMalloc cBufferOut;

  private Object[] obj;
  private final JObjectArrayPool oArray = new JObjectArrayPool(5);

  private static String dllFile;

  public JParallelClient() throws Exception {
  if( System.getProperty("os.name").startsWith("Windows") )
    dllFile = "PssParallel.dll";
  else
    dllFile = "libPssParallel.so";
  }

//------------------------------------------------------------------------------
// Getters & Setters
//------------------------------------------------------------------------------
  public String getPort() { return port; }
  public void setPort(String value) { this.port=value; }


//----------------------------------------------------------------------------------------------------------------------
  public void connect() throws Exception {
    this.loadLibrary();
    cBufferIn  = new CMalloc(1024);
//    cBufferOut = new CMalloc(1024);
    if (this.openPort()!= 0)
    	JExcepcion.SendError("Error al abrir el Puerto");
    bConnected = true;
  }
  
//----------------------------------------------------------------------------------------------------------------------
  public boolean write(byte[] zMsg) {
//    int len = zMsg.length();
//    int i;
//    if( cBufferIn == null ) return false;
//    for( i=0; i<len; i++) {
//      cBufferIn.setByte( i, (byte)(zMsg.charAt(i)&0x00FF));
//    } 
  	if (zMsg.length==0) return true;
//    obj = oArray.getObjectArray(1);
//    obj[0] = port;
    obj = oArray.getObjectArray(3);
    obj[0] = port;
    obj[1] = new String(zMsg);
    obj[2] = new Integer(zMsg.length);
    return cWritePort.callInt(obj) == 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public int status() throws Exception {
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    return cControlPort.callInt(obj);
  }
//----------------------------------------------------------------------------------------------------------------------
  public int status( int flags ) throws Exception {
    int iStat = status();
    if( ( iStat & flags ) == 0 ) {
      return 0;
    } else {
      return 1;
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  public String recv() {
    int i;
//    if( cBufferOut == null ) return "";
    obj = oArray.getObjectArray(2);
    obj[0] = port;
    obj[1] = cBufferIn;
    int receivedchars = cReadPort.callInt(obj);
    char cReply[] = new char[receivedchars];
    for (i = 0; i < receivedchars; i++) {
      cReply[i] = (char)(cBufferIn.getByte(i) & 0x00FF);
    }
    return new String(cReply);
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean disconnect() {
    if (!bConnected) return true;
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    cClosePort.callInt(obj);
    cBufferIn.free();
//    cBufferOut.free();
    bConnected=false;
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  private void loadLibrary() {
    cOpenPort      = new CFunc(dllFile, "OpenPort");
    cWritePort     = new CFunc(dllFile, "WritePort");
    cReadPort      = new CFunc(dllFile, "ReadPort");
    cClosePort     = new CFunc(dllFile, "ClosePort");
    cControlPort   = new CFunc(dllFile, "ControlPort");
  }
//----------------------------------------------------------------------------------------------------------------------
  private int openPort() {
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    return cOpenPort.callInt(obj);
  }
//----------------------------------------------------------------------------------------------------------------------
  @Override
	public void finalize() throws Throwable {
    if( cBufferIn != null ) cBufferIn.free();
//    if( cBufferOut != null ) cBufferOut.free();
    super.finalize();
  }
}
