/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.connectivity.tcp;

import java.net.ServerSocket;
import java.net.Socket;


public class TcpServer extends TcpIO {

    private ServerSocket server;

    public void preStart() {
      // overrided
    }
    
    public ServerSocket createSocket(int port) throws Exception {
      return new ServerSocket(port);
    }
    
    public ServerSocket getServerSocket() { return server; }
    
    public boolean start(int port) throws Exception {
      preStart();
      server = createSocket(port);
      return true;
    }

    public Socket listen(int timeout) throws Exception {
      server.setSoTimeout(timeout);
      return server.accept();
    }

    public boolean stop() throws Exception {
      if ( server == null ) return true;
      server.close();
      server = null;
      return true;
    }

  }


