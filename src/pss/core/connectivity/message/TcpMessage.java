/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.connectivity.message;

import java.net.Socket;

import pss.core.connectivity.tcp.TcpIO;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.tools.crypt.AESCrypt;
import pss.core.tools.crypt.TripleDES;


/**
 * TcpMessage - Send and Receive transaction messages
 * 
 * @author sgalli
 * 
 */
public class TcpMessage extends TcpIO {

	private Socket socket;
	private boolean encrypted = false;
	private String algorithm=ALG_DES;

	private byte wkkey[] = null;

	/**
	 * Message Header Length
	 */
	
	public static String ALG_DES="DES";
	public static String ALG_AES="AES";
	
	
	public static char MSG_PROXY = 'P';
	public static char MSG_PLAIN = 'U';
	public static char MSG_ENCRYPTED = 'E';
	public static char MSG_AES = 'A';
	public static char MSG_FILE = 'F';

	public static int MSG_TYPE = 1;

	public static int MSG_HEADER = 8;

	public static int MSG_HEADER_FILE = 10;

	public static int MSG_HEADER_ID_REQ = 8;

	/**
	 * Message Buffer Length
	 */
	public static int MSG_BUFFER_SIZE = 256;
	
	public static String PROXYIP = "PROXY";

	/**
	 * @param value
	 */
	public void setSocket(Socket value) {
		this.socket = value;
	}

	public void setEncrypted(boolean value) {
		this.encrypted = value;
	}
	
	public void setAlgorithm(String value) {
		this.algorithm = value;
	}
	
	public String getProxyHeader() {
		return msgProxy;
	}
	
	public String getProxyReailIp() {
		return msgProxyIP;
	}
	
	private String msgProxy=null;
	private String msgProxyIP=null;
	
	

	private int recvHeader() throws Exception {
		try {
		char msgtype = this.recv(this.socket, MSG_TYPE)[0];
		int offset = 1;
		int lheader = MSG_HEADER;
		if (msgtype == MSG_PROXY) {
			char precv=' ';
			StringBuffer msgprox = new StringBuffer();
			while (precv!='\n') {
				precv = this.recv(this.socket, MSG_TYPE)[0];
				msgprox.append(precv);
			}
			msgProxy = msgprox.toString();
			try {
				JStringTokenizer tok = JCollectionFactory.createStringTokenizer(msgProxy, ' '  );
				tok.nextToken();
				tok.nextToken();
				msgProxyIP = tok.nextToken();
			} catch (Exception eee) {
			}
			msgtype = this.recv(this.socket, MSG_TYPE)[0];
		}

		
		if (msgtype == MSG_ENCRYPTED) {
			this.encrypted = true;
			algorithm=ALG_DES;
			offset = 0;
			msgtype = ' ';
		} else if (msgtype == MSG_AES) {
			offset = 0;
			algorithm=ALG_AES;
			this.encrypted = true;
			msgtype = ' ';
		} else

		if (msgtype == MSG_FILE) {
			lheader = MSG_HEADER_FILE;
			this.encrypted = true;

		}
		char header[] = this.recv(this.socket, lheader - offset);
		int iLen = Integer.parseInt((msgtype + new String(header)).trim());
		return iLen;
		} catch (Exception eee) {
			
			throw eee;
		}
	}

	/**
	 * @return the response of the transaction
	 * @throws Exception
	 */
	public StringBuffer recvMessage() throws Exception {
		int iLen = recvHeader();
		char aux[] = null;
		char msg[] = {};
		int iReaded = 0;
		StringBuffer oBuff = new StringBuffer();
		while (iReaded < iLen) {
			aux = this.recv(this.socket, iLen);
			iReaded += aux.length;
			msg = JTools.concat(msg, aux);
		}
		if (this.encrypted) {
			if (algorithm.equals(ALG_DES))
				oBuff = new StringBuffer(decryptMessage(msg));
			else if (algorithm.equals(ALG_AES)) {
				PssLogger.logDebug( "AES Protocol" ); 
				oBuff = new StringBuffer(AESDecryptMessage(msg));
			}
		} else {
			oBuff = new StringBuffer(String.valueOf(msg));
		}

		return oBuff;
	}
	

	
	private String AESDecryptMessage(char buff[]) throws Exception {
		byte resp[] = AESCrypt.decrypt( JTools.charArrayToByteArray(buff)  ) ;
		return new String(JTools.byteToChar(resp));
	}

	private String decryptMessage(char buff[]) throws Exception {
		byte dec[] = TripleDES.decrypt(JTools.charArrayToByteArray(buff));
		return new String(JTools.byteToChar(dec));
	}

	private byte[] makeHeader(int len) throws Exception {
		String head = JTools.lpad(len + "", "0", MSG_HEADER);
		PssLogger.logDebug("longitud:" + head);
		if (this.encrypted) {
			if (this.algorithm.equals(TcpMessage.ALG_DES))
				return new String(TcpMessage.MSG_ENCRYPTED + head).getBytes();
			else
				if (this.algorithm.equals(TcpMessage.ALG_AES))
					return new String(TcpMessage.MSG_AES + head).getBytes();
			
		}
		return head.getBytes();
	}

	/**
	 * @param zResponse
	 * @throws Exception
	 */
	public void sendMessage(String zResponse) throws Exception {
		sendMessage(zResponse.toCharArray());
	}

	/**
	 * @param zResponse
	 * @throws Exception
	 */
	private void sendMessage(char zResponse[]) throws Exception {
		int len = 0;
		if (zResponse != null)
			len = zResponse.length;

		byte header[];
		byte msg[];
		if (this.encrypted) {
			byte[] dec = null;
			if (algorithm.equals(ALG_DES))
				dec = encryptMessage(zResponse);
			else if (algorithm.equals(ALG_AES)) 
				dec = AESEncryptMessage(zResponse);
			len = dec.length;
			msg = dec; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			msg = JTools.charArrayToByteArray(zResponse); //$NON-NLS-1$ //$NON-NLS-2$
		}
		header = makeHeader(len);

		PssLogger.logDebug("Send Header:" + header.length );
		PssLogger.logDebug("Send Message:" + msg.length );
		this.send(this.socket, JTools.concat( header, msg ) );
		
//				JTools.concat(JTools.byteToChar(header),
//				JTools.byteToChar(msg)));
		// this.send(this.socket, );
	}
	
	private byte[] AESEncryptMessage(char[] zResponse) throws Exception {
		// No padding , si no es multiplo de 8 lo completo a mano
		int reminder = zResponse.length % 16;
		byte ans[] = JTools.charArrayToByteArray(zResponse);
		if (reminder != 0) {
			byte rest[] = new byte[16 - reminder];
			java.util.Arrays.fill(rest, (byte) 0x00);
			ans = JTools.concat(ans, rest);
		}
		int len= ans.length;
		byte dec[] = AESCrypt.encrypt( ans ) ;
		
		System.out.println("ENC:"+ JTools.BinToHexString(new String(dec), dec.length) );
		byte resp[] = AESCrypt.decrypt( dec ) ;
		System.out.println("DEC:"+ JTools.BinToHexString(new String(resp), resp.length) );
		
		return dec;
	}


	private byte[] encryptMessage(char[] zResponse) throws Exception {
		// No padding , si no es multiplo de 8 lo completo a mano
		int reminder = zResponse.length % 8;
		byte ans[] = JTools.charArrayToByteArray(zResponse);
		if (reminder != 0) {
			byte rest[] = new byte[8 - reminder];
			java.util.Arrays.fill(rest, (byte) 0x30);
			ans = JTools.concat(ans, rest);
		}
		byte dec[] = TripleDES.encrypt(ans);
		return dec;
	}

	public void closeMessage() throws Exception {
		this.socket.close();
	}

	public byte[] recvByteArrayMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessage(byte[] outputData) {
		// TODO Auto-generated method stub

	}

}
