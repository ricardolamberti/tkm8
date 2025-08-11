package pss.core.connectivity.messageManager.client.connectionChannel;

import java.net.SocketTimeoutException;

import pss.core.connectivity.messageManager.common.connectionChannel.BaseTcpConnectionChannel;
import pss.core.connectivity.tcp.JTcpStream;
import pss.core.connectivity.tcp.TcpClient;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class TcpClientConnectionChannel extends BaseTcpConnectionChannel implements IClientConnectionChannel {
	@Override
	public boolean connect(String zConnectionString) throws Exception {
		int iAux;
		iAux = zConnectionString.indexOf(':');

		if (iAux == -1) {
			JExcepcion.SendError("La Cadena de conexion [" + zConnectionString + "] es invalida para el tipo " + this.getClass().getName());
		}

		this.hostId = zConnectionString.substring(0, iAux);
		this.port = Integer.valueOf(zConnectionString.substring(iAux + 1));

		try {
			TcpClient oSocketClient = new TcpClient();
			oSocketClient.connect(hostId, port);
			this.tcpStream = new JTcpStream(oSocketClient.getSocket(), 1000);
			return true;
		} catch (SocketTimeoutException ste) {
			PssLogger.logDebug(ste, "Timeout exception waiting for sever connection");
			return false;
		}
	}

}
