package pss.core.connectivity.tcp;

import pss.core.tools.PssLogger;

public class JTcpShadow  {

  protected JTcpStream stream = null;
  Thread oThread           = null;
//  String sMensaje          = null;
//  byte [] sMensajeInBytes  = null;
//  String sRespuesta        = null;
//  byte [] sRespuestaInBytes = null;
  Thread oParentThread     = null;


//  public String GetMensaje() {
//    return JTools.byteVectorToString(sMensajeInBytes);
//  }

  //public byte[] GetMensajeInBytes() { return sMensajeInBytes; }

/*  public void SetRespuesta(String zValue) {
    //sRespuestaInBytes = new byte [zValue.length()];
    //for(int i=0 ; i < zValue.length() ; i++)  sRespuestaInBytes[i] = (byte)(zValue.charAt(i) & 0x00ff);
    sRespuestaInBytes = JTools.stringToByteVector(zValue);

  }
*/ 
  //public void SetRespuestaInBytes( byte [] zValue ) { sRespuestaInBytes = zValue; }


  public JTcpShadow(JTcpStream value) throws Exception {
    stream = value;
  }
  
  protected Thread getThread() {
  	return this.oThread;
  }

  public void executeShadow() throws Exception {
    oParentThread = Thread.currentThread();
    oThread = new Thread(new Runnable () {public void run() { shadowThread(); }});
    oThread.setName(this.getThreadName()+ "-"+this.getStreamAddress());
    oThread.start();
  }

  protected String getThreadName() throws Exception {
  	return "ShadowThread";
  }
  
  public String getStreamAddress() {
    return this.stream.getAddress();
  }

  public void shadowThread() {
  try {
    boolean connected = true;
    this.onStart();
    try {
      while ( connected ) {
        StringBuffer msg = stream.receiveBytes();
        if (msg == null ) break;
        if (msg.length() == 0) break;
        String rta;
        try {
        	rta = this.onProcess(msg);
        } catch (Exception e) {
        	rta = this.onError(msg, e);
        }
        stream.sendBytes(rta);
        this.onProcessed();
        if ( Thread.currentThread().isInterrupted() || oParentThread.isInterrupted() ) connected = false;
      }
    } catch ( Exception e ) {
      PssLogger.logError(e);
      this.onError(null, e);
    }
    this.finishStream();
    this.onStop();
  } catch ( Exception e ) {
    PssLogger.logError(e);
    this.finishStream();
  } }

  private void finishStream() {
  	try { 
  		stream.finish(); 
  	} catch ( Exception ignore ) {}
  }


  public void onStart() throws Exception {}
  public void onStop() throws Exception {}
  public String onProcess(StringBuffer msg) throws Exception { return null;}
  public void onProcessed() throws Exception {}
  public String onError(StringBuffer msg, Exception e) throws Exception { return null;}

}

