/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.connectivity.tcp;

import java.io.IOException;
import java.net.Socket;

import pss.core.tools.JTools;


public class TcpIO {

  public static int DEFAULT_BUFFER_SIZE = 256;

  public void send(Socket socket, String zMsg) throws Exception {
    send(socket, JTools.stringToByteArray(zMsg) );
  }


  public void send(Socket socket, char[] zMsg) throws Exception {
    send(socket, JTools.charArrayToByteArray(zMsg));
  }

  public void send(Socket socket, byte[] zMsg) throws Exception {
    int iMsgLen = zMsg.length;
    try {
      socket.getOutputStream().write(zMsg);
    } catch (IOException io) {
      throw io;
    }
  }

  public char[] recv(Socket socket) throws Exception {
    return recv(socket, DEFAULT_BUFFER_SIZE);
  }
/*
  public char[] recv(Socket socket, int recvBufferSize) throws Exception {
    byte[] byteArray = new byte[recvBufferSize];
    byte[] aux = new byte[recvBufferSize];
    char[] charArray;
    charArray = new char[recvBufferSize];
    try {
      int iReaded = 0;
      while (iReaded < recvBufferSize) {
        int iLen = socket.getInputStream().read(aux, 0,
            recvBufferSize - iReaded);
        for (int i = iReaded; i < iLen; i++) {
          byteArray[i] = aux[i - iReaded];
        }

        iReaded += iLen;
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    for (int i = 0; i < recvBufferSize; i++) {
      charArray[i] = (char) (byteArray[i]);
    }

    return charArray;
  }
*/
  
  public char[] recv(Socket socket, int recvBufferSize) throws Exception {
  	char[] byteArray = new char[recvBufferSize];
    byte[] aux = new byte[recvBufferSize];
     try {
      int iReaded = 0;
      while (iReaded < recvBufferSize) {
        int iLen = socket.getInputStream().read(aux, 0,recvBufferSize - iReaded);
        if (iLen<0) throw new Exception("Error de comunicación");
        
        for (int i = iReaded; i < iLen+iReaded; i++) {
          byteArray[i] = (char)aux[i - iReaded];
        }

        iReaded += iLen;
      }
    } catch (IOException ioe) {
      throw ioe;
    }


    return byteArray;
  }

  public boolean ifDataAvailable(Socket zSocket) throws Exception {
  	if (zSocket == null) return false;
  	if (zSocket.isConnected() == false) return false;
  	return (zSocket.getInputStream().available() != 0);
  }
  
  
}
