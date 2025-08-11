package pss.core.connectivity.tcp;

import java.net.Socket;

import pss.core.connectivity.message.TcpMessage;
import pss.core.tools.PssLogger;

public class JTcpStream {

	private Socket oSocket = null;
	@SuppressWarnings("unused")
	private int Espera = 0;
	private String address;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public JTcpStream(Socket zSocket, int zEspera) throws Exception {
		oSocket = zSocket;
		Espera = zEspera;
	}

	public String getLocalAddress() {
		if (this.address == null) {
			this.address = this.oSocket.getLocalAddress().getHostName() + ":" + this.oSocket.getLocalPort();
		}
		return this.address;
	}

	public String getAddress() {
		if (this.address == null) {
			this.address = this.oSocket.getInetAddress().getHostName() + ":" + this.oSocket.getPort();
		}
		return this.address;
	}

	public boolean isDataAvailable() throws Exception {
		if (oSocket == null)
			return false;
		if (oSocket.isConnected() == false)
			return false;
		return (oSocket.getInputStream().available() != 0);
	}

	public StringBuffer receiveBytes() throws Exception {
		TcpMessage msg = new TcpMessage();
		msg.setSocket(oSocket);
		return msg.recvMessage();
	}

	public byte[] receiveByteArray() throws Exception {
		TcpMessage msg = new TcpMessage();
		msg.setSocket(oSocket);
		return msg.recvByteArrayMessage();
	}

	/*************************************************************************************************
	 * 
	 ************************************************************************************************/

	public boolean sendBytes(String outputData) throws Exception {
		TcpMessage msg = new TcpMessage();
		msg.setSocket(oSocket);
		msg.sendMessage(outputData);
		return true;
	}

	public boolean sendBytes(byte outputData[]) throws Exception {
		TcpMessage msg = new TcpMessage();
		msg.setSocket(oSocket);
		msg.sendMessage(outputData);
		return true;
	}

	public void finish() throws Exception {
		oSocket.getOutputStream().write(0x20);
		if (oSocket.isInputShutdown() == false) {
			try {
			oSocket.shutdownInput();
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}
		if (oSocket.isOutputShutdown() == false) {
			try {
				oSocket.shutdownOutput();
				} catch (Exception e) {
					PssLogger.logError(e);
				}
		}
		oSocket.close();
		oSocket = null;
	}

	public Socket getSocket() {
		return oSocket;
	}

	public boolean isConnected() {
		if (oSocket == null)
			return false;

		return oSocket.isConnected();
	}
}
