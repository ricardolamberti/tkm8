package pss.core.connectivity.client.serial;

import pss.core.tools.JObjectArrayPool;
import pss.core.tools.jnidisp.CFunc;
import pss.core.tools.jnidisp.CMalloc;

public class JSerialClient {

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
	public static final String PAR_NONE = "NONE";
	public static final String PAR_PAR = "PAR";
	public static final String PAR_EVEN = "EVEN";
	
  private String port;
  private String parity = PAR_NONE;
  private int bauds = 9600;
  private int dataBits = 8;
  private int stopBit = 1;
  private boolean bConnected = false;

  public static final int CTS  = 0x0010;
  public static final int DSR  = 0x0020;
  public static final int RING = 0x0040;
  public static final int RLSD = 0x0080;

  private CFunc cSetProperties;
  private CFunc cOpenPort;
  private CFunc cWritePort;
  private CFunc cReadPort;
  private CFunc cFlushPort;
  private CFunc cClosePort;
  private CFunc cGetStatus;

  /* JNI TRANSMIT-RECEIVE BUFFER */
  private CMalloc cBufferIn  = new CMalloc(1024*10);
  private CMalloc cBufferOut = new CMalloc(1024*10);

  private Object[] obj;
  private final JObjectArrayPool oArray = new JObjectArrayPool(5);

//------------------------------------------------------------------------------
// DLL File Handle for the class
//------------------------------------------------------------------------------
  private static String dllFile;

  public JSerialClient() throws Exception {
  if( System.getProperty("os.name").startsWith("Windows") )
    dllFile = "Psscomm.dll";
  else
    dllFile = "libPsscomm.so";
  }

//------------------------------------------------------------------------------
// Getters & Setters
//------------------------------------------------------------------------------
  public void setPort(String zValue) { port = zValue; }
  public void setBauds(int zValue) { bauds = zValue; }
  public void setDataBits(int zValue) { dataBits = zValue; }
  public void setStopBit(int zValue) { stopBit = zValue; }
  public void setParity(String zValue) { parity = zValue; }
  public String getPort() { return port;}
  

//----------------------------------------------------------------------------------------------------------------------
  public boolean connect() {
    this.loadLibrary();
    if (!this.setPortProperties()) return false;
    if (!this.openPort()) return false;
    return (bConnected = true);
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean flush() {
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    return cFlushPort.callInt(obj) == 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized boolean write(String zMsg) {
    char cArray[] = zMsg.toCharArray();
    int i;
    for( i=0; i<cArray.length; i++) {
      cBufferOut.setByte( i, (byte)(cArray[i]&0x00FF));
    }
    flush();
    obj = oArray.getObjectArray(3);
    obj[0] = port;
    obj[1] = cBufferOut;
    obj[2] = new Integer(cArray.length);
    return cWritePort.callInt(obj) == 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized boolean write( byte[] zMsg ) {
    cBufferOut.copyIn( 0, zMsg, 0, zMsg.length );
    flush();
    obj = oArray.getObjectArray( 3 );
    obj[0] = port;
    obj[1] = cBufferOut;
    obj[2] = new Integer( zMsg.length );
    return cWritePort.callInt( obj ) == 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized String recv() {
    int i;
    obj = oArray.getObjectArray(2);
    obj[0] = port;
    obj[1] = cBufferIn;
    int receivedchars = cReadPort.callInt(obj);
    StringBuffer reply = new StringBuffer( receivedchars > 0 ? receivedchars : 16 );
    for (i = 0; i < receivedchars; i++) {
      reply.append( (char)(cBufferIn.getByte(i) & 0x00FF) );
    }
    return reply.toString();
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized int status(int flags) {
    obj = oArray.getObjectArray(2);
    obj[0] = port;
    obj[1] = new Integer(flags);
    return cGetStatus.callInt(obj);
  }
//----------------------------------------------------------------------------------------------------------------------
  public int status() throws Exception {
    return 0;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean disconnect() {
    if (!bConnected) return true;
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    cClosePort.callInt(obj);
    bConnected=false;
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  private void loadLibrary() {
    cSetProperties = new CFunc(dllFile, "SetProperties");
    cOpenPort      = new CFunc(dllFile, "OpenPort");
    cWritePort     = new CFunc(dllFile, "WritePort");
    cReadPort      = new CFunc(dllFile, "ReadPort");
    cFlushPort     = new CFunc(dllFile, "FlushPort");
    cClosePort     = new CFunc(dllFile, "ClosePort");
    cGetStatus     = new CFunc(dllFile, "GetStatus");
  }
//----------------------------------------------------------------------------------------------------------------------
  private boolean setPortProperties() {
    obj = oArray.getObjectArray(5);
    obj[0] = port;
    obj[1] = new Integer(bauds);
    obj[2] = new Integer(dataBits);
    obj[3] = new Integer(stopBit);
    obj[4] = parity == "" ? "N" : parity;
    return cSetProperties.callInt(obj)==0;
 }
//----------------------------------------------------------------------------------------------------------------------
  private boolean openPort() {
    obj = oArray.getObjectArray(1);
    obj[0] = port;
    return cOpenPort.callInt(obj)==0;
  }
//----------------------------------------------------------------------------------------------------------------------
  @Override
	protected void finalize() throws Throwable {
    cBufferIn.free();
    cBufferOut.free();
    super.finalize();
  }
//----------------------------------------------------------------------------------------------------------------------
}
