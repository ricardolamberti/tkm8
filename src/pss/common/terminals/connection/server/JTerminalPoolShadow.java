package pss.common.terminals.connection.server;

import org.w3c.dom.Element;

import pss.common.terminals.config.BizTerminalPool;
import pss.common.terminals.core.JTerminalPool;
import pss.core.JAplicacion;
import pss.core.connectivity.tcp.JTcpShadow;
import pss.core.connectivity.tcp.JTcpStream;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.XML.JXMLElementFactory;

public class JTerminalPoolShadow extends JTcpShadow {
	
	private JTerminalPool pool=null;
	private String msgToSend;
	private String msgResponse;
	private Object notifyLock = new Object();
//	private boolean bRegistered = false;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public JTerminalPoolShadow(JTcpStream zStream) throws Exception {
    super(zStream);
  }
  
	public JTerminalPool getTerminalPool() {
		return pool;
	}
	
  @Override
	protected String getThreadName() throws Exception {
  	return "TerminalPoolShadow";
  }
  
	public String sendMessage(String value) throws Exception {
		this.msgResponse = null;
		this.msgToSend = value;
		this.waitResponse();
		this.msgToSend = "ECHO";
		return this.msgResponse;
	}
	
	public String getMsgToSend() {
		return this.msgToSend;
	}

  @Override
	public void onStart() throws Exception {
    JAplicacion.AbrirSesion();
    JAplicacion.GetApp().openApp("TPOOL_SHADOW", JAplicacion.AppTipoThread(), true );
  	this.pool = null;
  }

  @Override
	public void onStop() throws Exception {
  	JTerminalPoolServer.removeTerminalShadow(this);
  	JAplicacion.GetApp().closeApp();
  }

  @Override
//	public byte[] onProcess(byte[] msg) throws Exception {
//  	String res = this.process(JTools.byteVectorToString(msg));
//  	return JTools.stringToByteVector(res);
//  }
	public String onProcess(StringBuffer msg) throws Exception {
		return this.process(msg.toString());
	}
  
  @Override
//  public byte[] onError(byte[] msg, Exception e) throws Exception { 
//  	return JTools.stringToByteVector(e.getMessage());
//  }
  
  public String onError(StringBuffer msg, Exception e) throws Exception { 
		PssLogger.logError(e);
  	return "ERROR:"+e.getMessage();
  }

  private String process(String msg) throws Exception {
  	if (this.pool==null) return this.register(msg);
  	this.msgResponse=msg;
  	this.msgToSend="ECHO";
		this.waitCommand();
  	return this.msgToSend;
  }
  
  private String register(String clientIdent) throws Exception {
  	try {
	  	Element root = JXMLElementFactory.getInstance().createElementFromString(clientIdent);
	  	String macAddress = root.getAttribute("mac_address").toUpperCase();
	//  	String node = root.getAttribute("node");
	//  	long poolId = Long.parseLong(root.getAttribute("pool_id"));
	  	PssLogger.logDebug("Registrando Terminal Pool : " + macAddress);
	  	BizTerminalPool termPool = new BizTerminalPool();
	  	termPool.dontThrowException(true);
	  	if (!termPool.readByMacAddress(macAddress)) {
	  	  	PssLogger.logDebug("Terminal Pool inexistente : " + macAddress);
	  		JExcepcion.SendError("Terminal Pool no configurado");
	  	}
	  	this.pool = termPool.createTerminalPoolByConfig();
	  	JTerminalPoolServer.addTerminalShadow(this);
	  	return pool.serialize();
  	} catch (Exception e) {
  		Thread.currentThread().interrupt();
  		throw e;
  	}
  }

  
  private void waitCommand() throws Exception {
  	synchronized(notifyLock) {
  		notifyLock.notify();
  		notifyLock.wait(5000);
  	}
  }  

  private void waitResponse() throws Exception {
  	synchronized(notifyLock) {
  		notifyLock.notify();
  		notifyLock.wait(180000);
  	}
  }  

}
