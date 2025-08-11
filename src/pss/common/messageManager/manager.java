/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.common.messageManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;

import pss.JPath;
import pss.core.JAplicacion;
import pss.core.connectivity.message.TcpMessage;
import pss.core.connectivity.tcp.SocketThread;
import pss.core.connectivity.tcp.TcpServer;
import pss.core.data.files.JStreamFile;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.tools.collections.JStringTokenizer;

/**
 * Receive the messages from clients and opens a new Thread for each one. Each
 * instanciates a TransactionManager that process the message and send the
 * respond to the open socket.
 * 
 * @author sgalli
 * 
 */
public class manager {

	protected boolean sslserver = false;
	protected boolean smartprotocol = false;
	Thread currthread;
	boolean bLoop = true;
	ServerSocket srvSocket = null;

	public ServerSocket getSrvSocket() {
		return srvSocket;
	}

	public void setSrvSocket(ServerSocket srvSocket) {
		this.srvSocket = srvSocket;
	}

	private static final String MSG_RESET_SERVICE = "12340001";

	/**
	 * Message Header Length
	 */
	public static int MSG_HEADER = 5;

	/**
	 * Message Buffer Length
	 */
	public static int MSG_BUFFER_SIZE = 256;

	public static String getInfo( String line, String pattern, String pattern2 , int len ) {
		String info;
		int idx1 = line.indexOf(pattern);
    	int idx2 = line.indexOf(pattern2);
    	info = line.substring(idx1+len,idx2);
    	return info;
	}
	
//	public static void main(String[] args) {
//		try {
//			String distrito = "";
//			String porc_escrutado = "";
//			String uc="";
//			String cambiemos="";
//			String porc_part="";
//			
//			int i = 1;
//			
//			
//			while (true) { 
//				boolean first = true;
//				String si = JTools.LPad(i+"", 3, "0");
//				
//			  URL oracle = new URL("http://www.resultados.gob.ar/escrutinio/dat02/DSN02"+si+"A.htm");
//		        BufferedReader in = new BufferedReader(
//		        new InputStreamReader(oracle.openStream()));
//
//		        String inputLine;
//		        while ((inputLine = in.readLine()) != null) {
//		        	first = false;
//		            //System.out.println(inputLine);
//		            if (inputLine.indexOf("literalsubtitulo")>0) {
//		            	inputLine = in.readLine();
//		            	distrito = getInfo(inputLine, "<p>", "</p>", 3);
//		            	//System.out.println(distrito);
//		            } else 
//			        if (inputLine.indexOf(" sobre escrutado: ")>0) {
//		            	//inputLine = in.readLine();
//			        	porc_part = getInfo(inputLine, "<span>", "</span>", 6).replace('(', ' ').replace(')', ' ').replace('%', ' ').trim();
//			        } else
//		            if (inputLine.indexOf("mesasEscrutadas")>0) {
//		            	inputLine = in.readLine();
//		            	porc_escrutado = getInfo(inputLine, "<span>", "</span>", 6).replace('(', ' ').replace(')', ' ').replace('%', ' ').trim();
//		            	//System.out.println(porc_escrutado);
//		            } else 
//	                if (inputLine.indexOf("r1 agrup")>0) {
//		            	inputLine = in.readLine();
//		            	String agrup = getInfo(inputLine, "<td class=\"denom\">", "<span",  "<td class=\"denom\">".length());
//		            	inputLine = in.readLine();
//			            String voto = getInfo(inputLine, "td class=\"vot\">", "</td>",  "td class=\"vot\">".length());
//		            	inputLine = in.readLine();
//			            String pvot = getInfo(inputLine, "td class=\"pvot\">", "</td>",  "td class=\"pvot\">".length());
//		            	
//		            	if (agrup.startsWith("CAMBIEMOS") ) {
//		            		cambiemos = agrup+"|"+voto+"|"+pvot.replace('%', ' ').trim();
//		            	} else 
//		            	if (agrup.startsWith("UNIDAD CIUDADANA") ) {
//		            		uc = agrup+"|"+voto+"|"+pvot.replace('%', ' ').trim();
//		            	}
//		            	//if ()
//		            	//porc_escrutado = getInfo(inputLine, "<span>", "</span>", 6).replace('(', ' ').replace(')', ' ').replace('%', ' ').trim();
//		            	//System.out.println(porc_escrutado);
//		            } else 
//		            if (inputLine.indexOf("Electores totales:")>0) {
////		            	inputLine = in.readLine();
////			            String voto = getInfo(inputLine, "td class=\"vot\">", "</td>",  "td class=\"vot\">".length());
////		            	inputLine = in.readLine();
////			            String pvot = getInfo(inputLine, "td class=\"pvot\">", "</td>",  "td class=\"pvot\">".length());
//		            	
//		            	String total = getInfo(inputLine, "<span>", "</span>", 6).replace('(', ' ').replace(')', ' ').replace('%', ' ').trim();
//		            	
//		            	String tt = JTools.rd((Double.parseDouble(total.replace(".","").trim())*Double.parseDouble(porc_part.replace(',', '.'))/100),0)+"";
//			            
//	            		System.out.println(distrito + "|"+porc_escrutado+"|"+ uc + "|" + tt);
//	            		System.out.println(distrito + "|"+porc_escrutado+"|"+ cambiemos + "|" + tt);
//          		
//		            }
//        
//		            
//		            
//		        }
//		        in.close();
//		        if (first)
//		        	break;
//		        i++;
//		        
//			}
			
			
			public static void main(String[] args) {
				try {
					
					
					String passwd = JTools.PasswordToString("*304C67337466527455384A6C763048337445634541513D3D");
					
					
					String distrito = "";
					String porc_escrutado = "";
					String uc="";
					String cambiemos="";
					String onepais="";
					String porc_part="";
					
					JStreamFile file  = new JStreamFile();
					file.CreateNewFile(JPath.PssPathLog()+"/mesas.csv");


					JStreamFile file2  = new JStreamFile();
					file2.CreateNewFile(JPath.PssPathLog()+"/cambiemos0.csv");

					JStreamFile file3  = new JStreamFile();
					file3.CreateNewFile(JPath.PssPathLog()+"/cfk0.csv");

					JStreamFile file4  = new JStreamFile();
					file4.CreateNewFile(JPath.PssPathLog()+"/todos0.csv");
					
					JStreamFile file5  = new JStreamFile();
					file5.CreateNewFile(JPath.PssPathLog()+"/cambiemos50.csv");
				
					String seccion = "";
					
					//seccion = "069";  // matanza
					seccion = "113";  // pilar

					int i = 1;	
					
					JOrderedMap <String,String> circuitos = JCollectionFactory.createOrderedMap();
//					circuitos.addElement("0626", "00001-00154");
//					circuitos.addElement("0626A", "00155-00266");
//					circuitos.addElement("0627", "00267-00317");
//					circuitos.addElement("0628", "00318-00463");
//					circuitos.addElement("0629", "00464-00550");
//					circuitos.addElement("0629A", "00551-00699");
//					circuitos.addElement("0629B", "00700-00809");
//					circuitos.addElement("0630", "00810-00911");
//					circuitos.addElement("0631", "00912-00998");
//					circuitos.addElement("0631A", "00999-01117");
//					circuitos.addElement("0631B", "01118-01141");
//					circuitos.addElement("0631C", "01142-01192");
//					circuitos.addElement("0631D", "01193-01298");
//					circuitos.addElement("0632", "01299-01329");
//					circuitos.addElement("0632A", "01330-01363");
//					circuitos.addElement("0632B", "01364-01373");
//					circuitos.addElement("0633", "01374-01534");
//					circuitos.addElement("0634", "01535-01621");
//					circuitos.addElement("0635", "01622-01893");
//					circuitos.addElement("0635A", "01894-02157");
//					circuitos.addElement("0635B", "02158-02454");
//					circuitos.addElement("0635C", "02455-02602");
//					circuitos.addElement("0635D", "02603-02712");
//					circuitos.addElement("0635F", "02834-02836");
					
//					circuitos.addElement("0768", "00001-00204");
//					circuitos.addElement("0769", "00205-00230");
//					circuitos.addElement("0770", "00231-00344");
//					circuitos.addElement("0770A", "00345-00375");
//					circuitos.addElement("0771", "00376-00576");
//					circuitos.addElement("0772", "00577-00656");
					
					circuitos.addElement("0530", "00001-00179");
					circuitos.addElement("0530", "09001-09009");
					
					circuitos.addElement("0533", "00180-00195");

					circuitos.addElement("0534", "00196-00280");
					circuitos.addElement("0534", "09010-09022");
					
					circuitos.addElement("0534A", "00281-00441");
					
					circuitos.addElement("0534A", "09028-09036");
					
					circuitos.addElement("0535", "00442-00569");
					
					circuitos.addElement("0535A", "00570-00730");
					
					circuitos.addElement("0536", "00731-00836");
					
					circuitos.addElement("0536", "09023-09027");

					circuitos.addElement("0536A", "00837-00849");
					
					

					
//					circuitos.addElement("0769", "00205-00230");
//					circuitos.addElement("0770", "00231-00344");
//					circuitos.addElement("0770A", "00345-00375");
//					circuitos.addElement("0771", "00376-00576");
//					circuitos.addElement("0772", "00577-00656");

					
					
					Iterator<String> ic = circuitos.keyIterator();
				
					while ( ic.hasNext()) {
						String circ = ic.next();
						
						String mesas = circuitos.getElement(circ);
						
						JStringTokenizer grupoMesas = JCollectionFactory.createStringTokenizer(mesas, ',');
						while (grupoMesas.hasMoreTokens()) {
							String grupo = grupoMesas.nextToken().trim();
							
							JStringTokenizer rangoMesas = JCollectionFactory.createStringTokenizer(grupo, '-');
							int ini = Integer.parseInt(rangoMesas.nextToken().trim());
							int fin = Integer.parseInt(rangoMesas.nextToken().trim());
							
							for ( int ii = ini ; ii <= fin ; ii++ ) {
								  String ucirc = circ;
								  if (ucirc.length()==4) {
									  ucirc += "_";
								  }
								  String mesa = JTools.LPad(ii+"", 5, "0");
								  String url = "http://www.resultados.gob.ar/99/resu/content/telegramas/02/"+seccion+"/"+circ+"/02"+seccion+ucirc+mesa+".htm";
								  URL oracle = new URL(url);
							        BufferedReader in = new BufferedReader(
							        new InputStreamReader(oracle.openStream()));
							        
							        String inputLine;
							        while ((inputLine = in.readLine()) != null) {
							            //System.out.println(inputLine);
							            if (inputLine.indexOf("CELESTE Y BLANCA U<")>0) {
							            	inputLine = in.readLine();
							            	uc = getInfo(inputLine, "<td class='colorborde_colsn'>", "</td>", "<td class='colorborde_colsn'>".length());
								        	inputLine = in.readLine();
							            	uc += "|"+ getInfo(inputLine, "<td class='colorborde_coldn'>", "</td>", "<td class='colorborde_colsn'>".length());
							            } else 
								        if (inputLine.indexOf("CAMBIANDO JUNTOS")>0) {
							            	inputLine = in.readLine();
								        	cambiemos = getInfo(inputLine, "<td class='colorborde_colsn'>", "</td>", "<td class='colorborde_colsn'>".length());
								        	inputLine = in.readLine();
								        	cambiemos += "|" + getInfo(inputLine, "<td class='colorborde_coldn'>", "</td>", "<td class='colorborde_colsn'>".length());
								        } 
								        else 
									        if (inputLine.indexOf("UN PAIS UNIDO HOY Y SIEMPRE")>0) {
								            	inputLine = in.readLine();
									        	onepais = getInfo(inputLine, "<td class='colorborde_colsn'>", "</td>", "<td class='colorborde_colsn'>".length());
									        	inputLine = in.readLine();
									        	onepais += "|"+getInfo(inputLine, "<td class='colorborde_coldn'>", "</td>", "<td class='colorborde_colsn'>".length());
									        	inputLine = in.readLine();
									        	onepais += "|"+getInfo(inputLine, "<td class='colorborde_colsp'>", "</td>", "<td class='colorborde_colsn'>".length());
									        	inputLine = in.readLine();
									        	onepais += "|"+getInfo(inputLine, "<td class='colorborde_colco'>", "</td>", "<td class='colorborde_colsn'>".length());
									        } else
									            if (inputLine.indexOf("CELESTE Y BLANCA U2")>0) {
									            	inputLine = in.readLine();
									            	inputLine = in.readLine();
									            	inputLine = in.readLine();
									            	uc += "|"+ getInfo(inputLine, "<td class='colorborde_colsp'>", "</td>", "<td class='colorborde_colsn'>".length());
										        	inputLine = in.readLine();
									            	uc += "|"+ getInfo(inputLine, "<td class='colorborde_colco'>", "</td>", "<td class='colorborde_colsn'>".length());
									            } else 
										        if (inputLine.indexOf("1A AMARILLO")>0) {
									            	inputLine = in.readLine();
									            	inputLine = in.readLine();
									            	inputLine = in.readLine();
										        	cambiemos += "|"+ getInfo(inputLine, "<td class='colorborde_colsp'>", "</td>", "<td class='colorborde_colsn'>".length());
										        	inputLine = in.readLine();
										        	cambiemos += "|" + getInfo(inputLine, "<td class='colorborde_colco'>", "</td>", "<td class='colorborde_colsn'>".length());
										        } 

					          
							        }
							        in.close();
							        
//							        int iuc = Integer.parseInt(uc);
//							        int icambiemos = Integer.parseInt(cambiemos);
							        
							        String cfk = circ + "|"+mesa+"|UC|"+ uc ;
							        String cbm = circ + "|"+mesa+"|CAMBIEMOS|"+ cambiemos ;
							        String maz = circ + "|"+mesa+"|1PAIS|"+ onepais ;
							        System.out.println(cfk);
							        System.out.println(cbm);
							        System.out.println(maz);
							        file.altaln(cfk);
							        file.altaln(cbm);
							        file.altaln(maz);
							        
//							        if (icambiemos==0 && iuc>0) {
//							        	 file2.altaln(cbm);
//							        }
//							        
//							        
//							        if (icambiemos>0 && iuc==0) {
//							        	 file3.altaln(cfk);
//							        }
//							        
//							        
//							        if (icambiemos==0 && iuc==0) {
//							        	 file4.altaln(cfk);
//							        	 file4.altaln(cbm);
//							        }
							        
							        
								
							
						}
					}
				}	
					
					file.Close();
					file2.Close();
					file3.Close();
					file4.Close();
						
							
  		
					
			
			

		        System.exit(0);
		        
			if (args.length >= 1) {
				if (args[0].equals("install")) {
					WinServiceTools w = new WinServiceTools();
					w.setClassName("pss.common.MessageManager.manager");
					w.setServiceName("Pss Message Manager");
					w.install();
					System.exit(0);
				}
			}

			PssLogger.logDebug("starting message manager service ..."); //$NON-NLS-1$

			manager mgr = new manager();

			mgr.start(9999, "S");

			while (Thread.currentThread().isInterrupted() == false) {
				Thread.sleep(5000);
			}

			Thread.sleep(2000);

		} catch (Exception e) {
			// nothing to do
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Start SSL Thread
	 * 
	 * @return SSL thread
	 */
	public Thread startSSL() {
		// Transit SSL Transaction Server
		Thread oth1 = new Thread() {
			public void run() {
				manager mgr = new manager();
				mgr.startSSLServer(9999);
			}
		};
		oth1.start();
		return oth1;
	}

	public Thread start() {
		return start(9999, "N");
	}

	/**
	 * Start Socket Thread
	 * 
	 * @return Socket thread
	 */
	public Thread start(final int port, final String issmart) {
		// Transit Transaction Server
		Thread oth2 = new Thread() {
			public void run() {
				startServer(port + "", issmart);
			}
		};
		oth2.start();
		return oth2;
	}

	/**
	 * Start a SSL Socket Message Server
	 * 
	 * @param port
	 *            Port Number
	 */

	/**
	 * Start a SSL Socket Message Server
	 * 
	 * @param port
	 *            Port Number
	 */
	public void startSSLServer(int port) {
		setSSLServer(true);
		startServer(port + "", "N");
	}

	public static void start(String port, String smartprotocol) {

		manager mgr = new manager();
		Thread t = mgr.start(Integer.parseInt(port), smartprotocol);

		while (Thread.currentThread().isInterrupted() == false) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				try {
					if (mgr.srvSocket != null)
						mgr.srvSocket.close();
				} catch (Exception ignored) {
				}
				t.interrupt();
				PssLogger.logDebug("Interruption received");
				break;
			}
		}

	}

	/**
	 * Start a Standard Socket Message Server
	 * 
	 * @param port
	 *            Port Number
	 */
	public void startServer(String port, String smartprotocol) {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("messageManager",
					JAplicacion.AppService(), false);
			JAplicacion.SetWaitForClose(false);

			PssLogger.logDebug("listener startup on port " + port + " ..."); //$NON-NLS-1$ //$NON-NLS-2$
			TcpServer srv = null;
			PssLogger.logDebug("starting smart Server ..."); //$NON-NLS-1$ //$NON-NLS-2$

			srv = new TcpServer();
			srv.start(Integer.parseInt(port));
			srvSocket = srv.getServerSocket();

			while (Thread.currentThread().isInterrupted() == false) {

				Socket oSocket = srv.listen(0);

				PssLogger
						.logInfo("receive message from => " + oSocket.getInetAddress().toString()); //$NON-NLS-1$

				Object params[] = new Object[1];
				params[0] = new Boolean(true);

				SocketThread oth = new SocketThread(oSocket, params) {
					public void run() {
						try {
							boolean issmart = false;
							if (this.params.length > 0)
								issmart = ((Boolean) this.params[0])
										.booleanValue();
							JAplicacion.openSession();

							TcpMessage msg = new TcpMessage();
							this.socket.setSoTimeout(10000);
							msg.setSocket(this.socket);

							StringBuffer oBuff = msg.recvMessage();

							JAplicacion.GetApp().openApp("messageManager",
									JAplicacion.AppService(), true);

							PssLogger.logInfo("going to manage message ..."); //$NON-NLS-1$

							String sResponse = processMessage(oBuff);

							PssLogger
									.logInfo("response len: " + sResponse.length()); //$NON-NLS-1$

							if (sResponse != null)
								msg.sendMessage(sResponse);
							msg.closeMessage();

							JAplicacion.GetApp().closeApp();
							JAplicacion.closeSession();

						} catch (Exception e) {
							try {
								JAplicacion.GetApp().closeApp();
								JAplicacion.closeSession();
								JAplicacion.openSession();
								JAplicacion.GetApp().openApp("socketerror",
										JAplicacion.AppService(), false);
								PssLogger.logError(e, "mainthread"); //$NON-NLS-1$ //$NON-NLS-2$
								JAplicacion.GetApp().closeApp();
								JAplicacion.closeSession();
							} catch (Exception error) {
							}
						}
					}

				};
				oth.start();
			}
			PssLogger.logDebug("shutting down message manager ..."); //$NON-NLS-1$ //$NON-NLS-2$

			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();

		} catch (Exception e) {
			PssLogger.logError(e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected String processMessage(StringBuffer oBuff) throws Exception {
		String strAux = null;
		return strAux;
	}


	/**
	 * @return Returns the sslserver.
	 */
	public boolean isSSLServer() {
		return this.sslserver;
	}

	/**
	 * @param sslserver
	 *            The sslserver to set.
	 */
	public void setSSLServer(boolean sslserver) {
		this.sslserver = sslserver;
	}

}
