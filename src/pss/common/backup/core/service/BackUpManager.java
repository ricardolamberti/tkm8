/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     Maxi Rosatti - initial API and implementation
 *******************************************************************************/
package  pss.common.backup.core.service;

import java.util.ArrayList;

import pss.common.backup.settings.BizMainSetting;
import pss.common.security.BizLoginTrace;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;


public class BackUpManager {

	static Thread currthread;
	static boolean bLoop = true;
	
	//private Thread[] oImportThreads=null;
  private ArrayList<BackUpThread> oBackUpThreads=null;
	
	static Thread oThread;

	/**
	 * Message Header Length
	 */
	public static int MSG_HEADER = 5;

	/**
	 * Message Buffer Length
	 */
	public static int MSG_BUFFER_SIZE = 256;
	
	
	public static void doShutdown(String zVal[]) throws Exception {
		bLoop=false;
		BackUpManager.currthread.interrupt(); // Para interrumpir un sleep
	}

	/**
	 * Message Manager Main Startup
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			if (args.length >= 1) {
				if (args[0].equals("-install")) {
					WinServiceTools w = new WinServiceTools();
					w.setClassName("pss.common.backup.core.service.BackUpManager");
					w.setServiceName("BackUp Manager");
					w.install();
					System.exit(0);
				}
			}

			PssLogger.logDebug("BackUpManager.0"); //$NON-NLS-1$

			startSched();

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

	public static void startSched() throws Exception {
		try {
			// Intento quedarme levantando hasta que pueda conectarme a la base de datos
			while (BackUpManager.bLoop) {
				try {
					JAplicacion.AbrirSesion();
					JAplicacion.GetApp().openApp("BackUpManager",JAplicacion.GetApp().GetAppId(), true);
					JAplicacion.SetWaitForClose(false);		
					PssLogger.logWait("Iniciando BackUp Manager en host: "+ JTools.GetHostName() +", modo: servicio ...");					
					// Ejecuta el proceso
					try {
						BackUpManager oBackUp = new BackUpManager();
						oBackUp.Ejecutar();
					} catch (Exception e) {
						PssLogger.logError(e, "Al intentar ejecutar BackUp Manager");
					}
					PssLogger.logInfo("Fin ejecución");
				} catch (JConnectionBroken e) {
					// Si dio un error en el loop por BD
					// me quedo esperando para ver si se recupera
					PssLogger.logError("Error main :"+e.getMessage());
					JAplicacion.GetApp().closeApp();
					Thread.sleep(15000); // Espero 15 seg. y reintento levantar el servicio
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				JAplicacion.GetApp().closeApp();
				JAplicacion.cerrarSesion();						
			} catch (Exception ignored) {
				PssLogger.logError(ignored, "Al intentar cerrar bases de datos.");
			}
		}
	}					
					

	private void Ejecutar() throws Exception {
		//if (this.ifAlive()) JExcepcion.SendError("Dispatcher ya se encuentra en ejecución");

		// Para asegurarse de cerrar el socket control
		try {
			oThread=Thread.currentThread();
			oThread.setName("Pss-BackUpManager.1");
			this.LoguearUsuario() ;
			while ((bLoop) && (!oThread.isInterrupted())) {
				// Aisla el proceso de barrido
				try {
					JRecords<BizMainSetting> oBackUpSetting = new JRecords<BizMainSetting>(BizMainSetting.class);
					oBackUpSetting.addFilter("active", true);
					oBackUpSetting.addOrderBy("company");
					oBackUpSetting.readAll();
					oBackUpSetting.toStatic();

					// BackUp
					oBackUpThreads = new ArrayList<BackUpThread>();
					PssLogger.logInfo("Empieza el BackUp. Iterarando Empresas ") ;
					this.BackUp(oBackUpSetting);
					
					// Compactación
					while (true) {
						if (this.AllThreadsHasFinished()) break;
						Thread.yield();
						Thread.sleep(10000); //Espera 10 segundos y se fija si terminaron los threads
					}	
					
					// FINALIZAR 
					try {
						while (true) {
							if (this.AllThreadsHasFinished()) {
								oBackUpThreads.clear();
								PssLogger.logInfo("Terminé el BackUp y la compactación. Voy a dormir 1/2 hora ") ;
								Thread.sleep(1800000); // cada 1/2 hora 
								break;
							}
							Thread.yield();
							Thread.sleep(20000); //Espera 20 segundos y se fija si terminaron los threads
						}
					} catch (InterruptedException ignored) {
					}
				} catch (JConnectionBroken e) {
					try {
						JBDatos.GetBases().closeDatabases();
					} catch (Exception ignored) {
					}
					// Entra en loop hasta reconectar o hasta recibir la señal de baja del servicio
					while (!JBDatos.isDatabaseOpen()&&bLoop&&(Thread.currentThread().isInterrupted() == false)) {
						this.AbrirBases();
					}
				} catch (Exception e) {
					PssLogger.logError(e, "BackUp No pudo Leer Tabla de Seteos. "+e.getClass().getName()+" "+e.getMessage());
					// Should not be a repetitive exception other than JConnectionBroken, but will protect
					// ourselves anyway...
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignored) {
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
		PssLogger.logInfo("BackUp Manager interrumpido");
	}
	
  private void BackUp(JRecords<BizMainSetting> oBackUps) throws Exception {
  	JConnectionBroken oExcep=null;
		while (oBackUps.nextRecord()) {
			BizMainSetting oBackUp = oBackUps.getRecord();
			if (oBackUp.hasToRun()) {
				try {
					oBackUp.setShouldRun(true);
					BackUpThread th = new BackUpThread(oBackUp);
					th.setOperation(BackUpThread.BACKUP);
					th.setName("TH_IMP" + oBackUp.getCompany());
					th.start(); 
					oBackUpThreads.add(th);
					Thread.sleep(1000);
				} catch (Throwable eExec) {
					PssLogger.logError(eExec, "Exec Problem");
					if (eExec instanceof JConnectionBroken) oExcep=(JConnectionBroken) eExec;
				}
			}
		} // end while
		if (oExcep!=null) throw oExcep;
  }
	
	/*private void Exportar(JRecords<BizConfiguration> oConfs) throws Exception {
		this.oImportThreads.clear();
		oConfs.firstRecord();
		while (oConfs.nextRecord()) {
			BizConfiguration oConf = oConfs.getRecord();
			if (!oConf.getShouldRun()) continue;
			BackUpThread th = new BackUpThread(oConf);
			th.setOperation(BackUpThread.EXPORT);
			th.setName("TH_EXP" + oConf.getOrigenBase());
			th.start(); 
			oImportThreads.add(th);
			Thread.sleep(1000);
		}
  } */
	
	private void LoguearUsuario() throws Exception {
		BizLoginTrace oLogin = new BizLoginTrace(); 
		oLogin.SetLogin("BACKUP"); 
		oLogin.SetValidarUsuario(false);
		JBDatos.GetBases().beginTransaction();
		oLogin.processInsert();
		JBDatos.GetBases().commit();		
	}

	private void AbrirBases() throws Exception {
		try {
			JBDatos.GetBases().openDatabases();
		} catch (JConnectionBroken broken) {
			// Lo hago dos veces, porque suma dos conexiones por cada opendatabases
			// realizado
			if (JBDatos.isDatabaseOpen()) 
				JBDatos.GetBases().closeDatabases();
			if (JBDatos.isDatabaseOpen())
				JBDatos.GetBases().closeDatabases();
		}	catch (Exception e2) {
				try {
					if (JBDatos.isDatabaseOpen())
						JBDatos.GetBases().closeDatabases();
				} catch (Exception ignored) {
				}
				;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}
				;
		}
	}
	private boolean AllThreadsHasFinished() {
		if (this.oBackUpThreads != null) {
			int count = this.oBackUpThreads.size();
			for (int i=0;i < count;i++) {
				Thread th = this.oBackUpThreads.get(i);
				if (th.isAlive()) 
					return false ;
			}
		}
		return true;
	}
	

}
