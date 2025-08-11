/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.connectivity.tcp;

import java.net.Socket;

public class SocketThread extends Thread  {

  protected Socket socket = null;
  protected Object    params[]=null;
  protected String ip = null;
  
  public SocketThread( Socket zSocket ) {
    socket = zSocket;
    ip = socket.getInetAddress().toString();
  }
  
  public SocketThread( Socket zSocket, Object zParams[] ) {
    params = zParams;
    socket = zSocket;
    ip = socket.getInetAddress().toString();
  }
  
  public Socket getSocket() { return socket ;  }
  
  public Object[] getParams() { return params; }
  
  public String getIpAddress() { return ip; } 



}
