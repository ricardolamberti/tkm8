package pss.common.terminals.connection.client;

import pss.JPath;
import pss.common.terminals.core.JTerminalPool;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrError;
import pss.common.terminals.messages.requires.JRequire;
import pss.core.connectivity.message.TcpMessage;
import pss.core.connectivity.tcp.TcpClient;
import pss.core.data.files.JIniFile;
import pss.core.services.fields.JObjRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;

public class JTerminalPoolClient {

	private TcpClient tcpClient;
	private JTerminalPool terminalPool;
	private JIniFile ini;
	
	public JTerminalPoolClient() {
	}
	
	public static void install() throws Exception {
		// execute the command with feedback
		WinServiceTools w = new WinServiceTools();
		w.setClassName("pss.common.terminals.connection.client.JTerminalPoolClient");
		w.setServiceName("PSS Terminal Client");
		w.install();
	}

	
	public static void main(String[] argvs) throws Exception {
		if (argvs.length<1) {
			run();
			return;
		}
		if (argvs[0].equals("-install") || argvs[0].equals("install")) {
			install();
			return;
		}
		JExcepcion.SendError("Error en Parámentros");
	}
	
	public static void run() throws Exception {
		JTerminalPoolClient poolClient = new JTerminalPoolClient();
		poolClient.start();
	}
	
	public void start() throws Exception {
		this.tcpClient = new TcpClient();
		this.tcpClient.setSocketTimeout(0);
		this.socketConnection();
		this.commandLoop();
	}

	private JIniFile getIniFile() throws Exception {
		if (this.ini!=null) return this.ini;
		return (ini = new JIniFile(JPath.PssPathData() + "/pss.ini"));
  }

	private String getHost() throws Exception {
		return this.getIniFile().GetParamValue("WEBSERVER", "HOST");
	}
	
	private int getPort() throws Exception {
		return Integer.parseInt(this.getIniFile().GetParamValue("WEBSERVER", "PORT"));
	}
	
	private String getMacAddress() throws Exception {
		return this.getIniFile().GetParamValue("WEBSERVER", "MAC_ADDRESS");
	}
	
	public void socketConnection() throws Exception {
		PssLogger.logDebug("Intentando Conectar: "+this.getHost()+"-"+this.getPort());
		PssLogger.logDebug("Address ID:"+this.getMacAddress());
		while (true) {
			if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
			try {
				this.tcpClient.connect(this.getHost(), this.getPort());
				PssLogger.logDebug("Conectado");
				this.regiterInServer();
				return;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}
	
	private void regiterInServer() throws Exception {
		TcpMessage msg = new TcpMessage();
		msg.setSocket(this.tcpClient.getSocket());
		msg.setEncrypted(false);
		msg.sendMessage(this.getRegisterMessage());
		this.register(msg.recvMessage().toString());
		PssLogger.logDebug("Registrado");
		msg.sendMessage("waiting_commnad");
	}
	
	private String getWaitingCommand() {
		return "waiting_commnad";
	}
	
	private String getRegisterMessage() throws Exception {
		return "<register mac_address=\""+this.getMacAddress()+"\" />";
	}
//	private String getWaitingCommandMessage() throws Exception {
//		return "<req id=\"waiting_command\"><msg pool_id=\""+terminalPool.getCount()+"\" /></req>";
//	}
	
	
	private void commandLoop() throws Exception {
		while(true) {
			try {
				if (Thread.currentThread().isInterrupted()) break;
				TcpMessage msg = new TcpMessage();
				msg.setSocket(this.tcpClient.getSocket());
				String xmlCommand = msg.recvMessage().toString();
				String rta = this.processRequire(xmlCommand);
				msg.sendMessage(rta);
			} catch (Exception e) {
				this.socketConnection();
			}
		}
		tcpClient.disconnect();
	}
	
	private String processRequire(String xml) throws Exception {
		try {
			if (xml.equals("ECHO")) return this.getWaitingCommand();
			PssLogger.logDebug("Require: " + xml);
			JRequire require = (JRequire)JObjRecord.unserializeDocument(xml);
			require.captureTerminals(this.terminalPool);
			Answer answer = require.executeLocal();
			String rta = answer.serializeDocument(); 
			PssLogger.logDebug("Answer: " + rta);
			return rta;
		} catch (Exception e) {
			PssLogger.logError(e);
			return new AwrError(e).serializeDocument();
		}
	}
	
	private void register(String xml) throws Exception {
		if (xml.startsWith("ERROR:")) {
			PssLogger.logError(xml);
			JExcepcion.SendError(xml);
		}
		PssLogger.logDebug("Mensaje Configuración");
		PssLogger.logDebug(xml);
  	this.terminalPool = JTerminalPool.unserialize(xml);
	}

}
