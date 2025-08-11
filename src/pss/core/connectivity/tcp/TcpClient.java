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
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;



public class TcpClient extends TcpIO {

    protected Socket client;
    protected String host;
    protected int port;
    protected int recvBufferSize = 64 * 1024; 
    protected int timeout=30000;                    
    protected int connectionTimeout=30000;
    protected int bytesRead;
    protected String lastReply;
    protected char[] lastMsg;
    protected Proxy proxy = null;
    public static final int DEFAULT_BUFFER = 256; 

    public void setConnnectionTimeout(int zTimeout) throws Exception {
      connectionTimeout = zTimeout ; 
    }
    
    public void setSocketTimeout(int zTimeout) throws Exception {
      timeout = zTimeout;
    }
    
    public void setProxy(String host, int port) {
      InetSocketAddress oAddress = new InetSocketAddress(host,port);
    	proxy = new Proxy(Proxy.Type.SOCKS, oAddress );
    }
    
    
    public void setRecvBufferSize(int zBufferSize) { recvBufferSize = zBufferSize; }
    public int getSocketTimeout() { return timeout; }
    public int getRecvBufferSize() { return recvBufferSize; }
    public String getLastReply() { return lastReply; }
    public String getLastMsg() { return new String(lastMsg); }
    public int getBytesRead() { return bytesRead; }
    public Socket getSocket() { return client; }
    
    public int getLocalPort() { return client.getLocalPort(); }

    public void preConnect() {
      // to be override 
    }
    
    public Socket createSocket() throws Exception {
    	if ( proxy == null )
        return new Socket();
    	else
    		return new Socket(proxy);
    }
    
    
    public void connect(String zHost, int zPort) throws Exception {
      host = zHost;
      port = zPort;
      try {
        preConnect();
        InetSocketAddress oAddress = new InetSocketAddress(host,port);
        client = createSocket();
        client.connect(oAddress,connectionTimeout);
        client.setSoTimeout(timeout); 
      }
      catch(UnknownHostException uhe) {
        //SessionManager.getLogger().error( "could not connect to host: " + host , uhe );
        throw uhe;
      }
      catch(IOException ioe) {
        //SessionManager.getLogger().error( "I/O creating socket on port: " + port, ioe );
        throw ioe;
      }
      catch(SecurityException se) {
        //SessionManager.getLogger().error( "Security Manager does not allow creation on port: " + port , se );
        throw se;
      }
    }

    public void send(String zMsg) throws Exception {
      send(zMsg.toCharArray());
    }

    public void send(char[] zMsg) throws Exception {
      send(client,zMsg);
    }
    
    public void send(byte[] zMsg) throws Exception {
      send(client,zMsg);
    }

    public char[] recv(int buffer) throws Exception {
      return recv(client,buffer);
    }
    
    public char[] recv() throws Exception {
      return recv(client,DEFAULT_BUFFER);
    }
    
    

    public void disconnect() throws Exception {
      try {
        if( client != null ) {
          client.shutdownOutput();
          client.close();
          client = null;
        }
      }
      catch(IOException ioe) {
        throw ioe;
      }
    }

    /**
     * @return Returns the connectionTimeout.
     */
    public int getConnectionTimeout() {
      return connectionTimeout;
    }

  }


