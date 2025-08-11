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

import javax.net.ssl.SSLServerSocketFactory;


public class SSLServer extends TcpServer {

    public void preStart() {
      System.setProperty("javax.net.ssl.keyStore", SSLServer.class.getResource(".").getFile()+"server" );
      System.setProperty("javax.net.ssl.keyStorePassword", "minime7167" );
      System.setProperty("javax.net.ssl.trustStore", SSLServer.class.getResource(".").getFile()+"server");
      //System.setProperty("javax.net.ssl.trustStore", "C:\\j2sdk1.4.2_09\\jre\\lib\\security\\cacerts");
      System.setProperty("javax.net.ssl.trustStorePassword", "minime7167");      
    }
    
    public ServerSocket createSocket(int port) throws Exception {
      return SSLServerSocketFactory.getDefault().createServerSocket(port);
    }
    

  }


