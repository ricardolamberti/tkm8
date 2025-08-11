package pss.bsp.bspBusiness;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.ibm.icu.util.Calendar;

import pss.JPath;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.contrato.BizContrato;
import pss.bsp.event.BizBSPSqlEvent;
import pss.bsp.gds.BizInterfazNew;
import pss.bsp.monitor.GlobalStatsManager;
import pss.bsp.monitor.ThreadStats;
import pss.bsp.monitor.log.BizBspLog;
import pss.bsp.pdf.BizPDF;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.sql.BizSqlEvent;
import pss.common.regions.company.BizCompany;
import pss.common.scheduler.BizProcess;
import pss.common.scheduler.BizProcessHost;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;
import pss.tourism.pnr.PNRCache;

public class BSPService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length >= 1) {
				WinServiceTools w = new WinServiceTools();
				w.setClassName("pss.bsp.bspBusiness.BSPService");
				w.setServiceName("TKM6 Ticket Manager");
				w.install();
				System.exit(0);
			}
		} catch (Exception ee) {

		}
		try {
				process();

			} catch (Exception e) {
			PssLogger.logError(e);
		} finally {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			} catch (Exception e) {
			}

		}
		BSPService.process();
	}

	static ConcurrentHashMap<String, Date> companyAccess;

	private static void addAccess(String company) {
		ConcurrentHashMap<String, Date> companies = getCompaniesAccess();
		if (companies.get(company) != null)
			return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, JTools.randInt(1, 5));
		companies.put(company, cal.getTime());
	}

	private static boolean hasProcesseCompany(String company) {
		ConcurrentHashMap<String, Date> companies = getCompaniesAccess();
		Date d = companies.get(company);
		boolean has;
		if (d == null) {
			has = true;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, JTools.randInt(5, 20));
			companies.put(company, cal.getTime());
		} else {
			has = new Date().after(d);
			if (has) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MINUTE, JTools.randInt(5, 20));
				companies.remove(company);
				companies.put(company, cal.getTime());
			}
		}
		return has;

	}

	private static ConcurrentHashMap<String, Date> getCompaniesAccess() {
		if (companyAccess == null)
			companyAccess = new ConcurrentHashMap<String, Date>();

		return companyAccess;
	}

	static ConcurrentHashMap<String, String> companiesAcumulado;
	int filesProc;

	private static void addCompanies(String company) {
		ConcurrentHashMap<String, String> companies = getCompanies();
		if (companies.get(company) != null)
			return;
		companies.put(company, company);
	}

	private boolean hasAndRemoveCompany(String company) {
		ConcurrentHashMap<String, String> companies = getCompanies();
		boolean has = companies.get(company) != null;
		if (!has)
			return false;
		companies.remove(company);
		return true;

	}

	private static ConcurrentHashMap<String, String> getCompanies() {
		if (companiesAcumulado == null)
			companiesAcumulado = new ConcurrentHashMap<String, String>();

		return companiesAcumulado;
	}

	static int ciclos = 0;

	public static void process() {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("BSPService", "process", true);
			Thread.currentThread().setName("Process Files");
			if (BizUsuario.getUsr().GetUsuario().equals("")) {
				BizUsuario usuario = new BizUsuario();
				usuario.dontThrowException(true);
				if (!usuario.Read(BizUsuario.C_ADMIN_USER))
					return;
				BizUsuario.SetGlobal(usuario);
			}
			
			BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio Operación", null, null, null, 0, 0, 0);
			ThreadStats stats = GlobalStatsManager.getStatsForThread();
			stats.start(null,"Check Scheduler");
			checkScheduler();
	
			// levanta archivos
			stats.start(null,"Procesando Archivos");
			Vector<String> companies = new Vector<String>();
			BizInterfazNew interfazNew = new BizInterfazNew();
			companies = interfazNew.execProcessFiles();

			Iterator<String> it = companies.iterator();
			while (it.hasNext()) {
				String idComp = it.next();
				addCompanies(idComp);
				addAccess(idComp);
			}
			
			stats.start(null,"Procesando Procesar Pendientes");
			new GuiPNRTickets().execReprocesarPendientes();
			PNRCache.clean();
			BizBspLog.clear();
			BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin Operacion: "+stats.getFullStatusLog(), null, null, null, 0, 0, 0);
			

		

			stats.stop();
		} catch (InterruptedException e) {
			PssLogger.logDebug("Interruption received");
			BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e.getMessage(), null, null, null, 0, 0, 0);
			// break;
		} catch (Exception e) {
			PssLogger.logError(e);
			BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e.getMessage(), null, null, null, 0, 0, 0);
		} finally {
			try {
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				Thread.sleep(10 * 60 * 1000);
			} catch (InterruptedException e) {
			} catch (Exception e) {
			}

		}

	}

	public void updateInterfazNew(String company) throws Exception {
		BizBSPCompany comp = BizBSPCompany.getObjBSPCompany(company);
		if (comp == null)
			return;
		if (comp.getObjExtraData().getInactiva())
			return;
		BizInterfazNew news = new BizInterfazNew();
		news.dontThrowException(true);
		if (!news.read(comp.getCompany())) {
			news.setCompany(comp.getCompany());
			try {
				news.setLastSqlEvent(getLastSQL(comp.getCompany()));
			} catch (Exception e1) {
				news.setLastSqlEvent(null);

			}
			try {
				Date f = getLastPNR(comp.getCompany());
				Calendar c = Calendar.getInstance();
				c.setTime(f);
				news.setLastPnr(c.after(new Date()) ? new Date() : f);
			} catch (Exception e) {
				news.setLastPnr(null);
			}
			news.setLastupdate(new Date());
			news.execProcessInsert();
		} else {
			news.execActualizar(getLastSQL(comp.getCompany()), getLastPNR(comp.getCompany()), new Date());
		}

	}

	private Date getLastPNR(String company) throws Exception {
		BizPNRTicket last = new BizPNRTicket();
		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			last.addFilter("company", BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getParentCompany());
			last.addFilter("customer_id_reducido", BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente());
		} else
			last.addFilter("company", company);
		return last.SelectMaxDate("creation_date");
	}

	private Date getLastSQL(String company) throws Exception {
		BizSqlEvent last = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		last.addFilter("company", company);
		return last.SelectMaxDate("fecha_update");
	}
	// static Thread procesaCompany(final String company) throws Exception {
	// Thread thread=new Thread() {
	//
	// @Override
	// public void run() {
	// process(company);
	//// processCicle(company);
	// }
	// };
	// thread.setName("BSPService "+company);
	// thread.start();
	// return thread;
	// }

	public static void checkScheduler() throws Exception {
		JRecords<BizCompany> companies = new JRecords<BizCompany>(BizCompany.class);
		JIterator<BizCompany> it = companies.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCompany company = it.nextElement();
			if (company.getCompany().equals("DEFAULT"))
				continue;
			if (company.getParentCompany().equals("DEFAULT"))
				continue;
			BizBSPCompany bsp = BizBSPCompany.getObjBSPCompany(company.getCompany());
			boolean inactiva = bsp.getObjExtraData().getInactiva();
			boolean isDependant = bsp.isDependant();
			String id = "BSPService_" + company.getCompany();
			String clase = "pss.bsp.bspBusiness.BSPService|process|" + company.getCompany();
			BizProcess sch = new BizProcess();
			sch.dontThrowException(true);
			boolean exist = sch.readByClassname(clase);
			if (!exist && !inactiva && !isDependant) {
				sch.setDescription(id);
				sch.setClassName(clase);
				sch.execProcessInsert();
				BizProcessHost host = new BizProcessHost();
				host.setPID(sch.getPID());
				host.setHost("*");
				host.setStatus("S");
				host.execProcessInsert();
			}
			if (exist && (inactiva || isDependant)) {
				sch.execProcessDelete();
			}
		}
		String id = "GlobalStatMnager";
		String clase = "pss.bsp.monitor.GlobalStatsManager|process|" ;
		BizProcess sch = new BizProcess();
		sch.dontThrowException(true);
		boolean exist = sch.readByClassname(clase);
		if (!exist) {
			sch.setDescription(id);
			sch.setClassName(clase);
			sch.execProcessInsert();
			BizProcessHost host = new BizProcessHost();
			host.setPID(sch.getPID());
			host.setHost("*");
			host.setStatus("S");
			host.execProcessInsert();
		}
	}

	public void cleanReprocesando(String company) throws Exception {
		JRecords<BizBSPSqlEvent> events = new JRecords<BizBSPSqlEvent>(BizBSPSqlEvent.class);
		events.addFilter("company", company);
		events.addFilter("estado", BizSqlEvent.REPROCESANDO);
		JIterator<BizBSPSqlEvent> it = events.getStaticIterator();
		while (it.hasMoreElements()) {
			BizBSPSqlEvent event = it.nextElement();
			event.setEstado(BizSqlEvent.REPROCESAR);
			event.execProcessUpdate();
		}
	}

	public synchronized void clearBandera(String bandera) throws Exception {
		JTools.MakeDirectory(JPath.PssPathMarks());
		File f = new File(JPath.PssPathMarks() + "/" + bandera);

		if (f.exists()) {
			f.delete();
		}
	}

	public static synchronized void addBandera(String bandera) throws Exception {
		JTools.MakeDirectory(JPath.PssPathMarks());
		File f = new File(JPath.PssPathMarks() + "/" + bandera);

		if (f.exists()) {
			return;
		}
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(JDateTools.CurrentDate().getBytes());
		fo.close();
	}

	public boolean checkBandera(String bandera) throws Exception {

		File f = new File(JPath.PssPathMarks() + "/" + bandera);
		if (f.exists()) {
			return true;
		}
		return false;
	}

	// public static void processCicle(String company) {
	// while(!Thread.currentThread().interrupted())
	// new BSPService().process(company);
	// }

	static long opens = 0;

	public static void process(String company) {
		new BSPService().processOneCompany(company);

	}
	
	public void processOneCompany(String company) {
		// if (!company.equals("CTS")) return;
		Thread.currentThread().setName("BSPService [" + company + "] Inactiva");
		ThreadStats stats = GlobalStatsManager.getStatsForThread();

		opens++;
		//stats.start(company,"Esperando Bandera");

		boolean consumeBandera = false;
		try {
			while (!Thread.currentThread().isInterrupted()) {
				if (checkBandera(company)) {
					consumeBandera = true;
					break;
				}
				Thread.sleep(1000);
				if (hasProcesseCompany(company))
					break;
			}
			if (Thread.currentThread().isInterrupted())
				return;

		} catch (Exception e) {
			// TODO: handle exception
		}
		stats.start(company,"Analisis company");

		if (company.equals("BIBAM"))
			PssLogger.logInfo("debug point");
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("BSPService [" + company + "] Activa" + (consumeBandera ? " bandera" : " archivos"), "process", true);
			if (BizUsuario.getUsr().GetUsuario().equals("")) {
				BizUsuario usuario = new BizUsuario();
				usuario.Read(BizUsuario.C_ADMIN_USER + "." + company);
				BizUsuario.SetGlobal(usuario);
			}
			BizBSPCompany bsp = BizBSPCompany.getObjBSPCompany(company);
			long version = bsp.getObjExtraData().getVersion();

			if (bsp.isDependant()) {
				consumeBandera = false;
				return;
			}
			if (version != 6 && version != 4) {
				consumeBandera = false;
				return;
			}
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio Operación", null, null, null, 0, 0, 0);

			JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + company);

			if (consumeBandera) {
				processBandera(stats,company);
			} else {
				if (hasAndRemoveCompany(company)) {
					BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Novedades para " + company, null, null, null, 0, 0, 0);

					processNewFilesPerCompany(stats,company);

				} else {
					stats.start(company,"Procesando Contratos - Check for events");
					BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio ExecCheckForEvents", null, null, null, 0, 0, 0);
					BizContrato.ExecCheckForEvents(company, false);
			
				}

			}
			
			stats.start(company,"Procesando Contrato calcule");
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Calcule Contratos", null, null, null, 0, 0, 0);
			BizContrato.ExecCalcule(company);

			stats.start(company,"Procesando Ticket - Check calcule over");
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Calcule Comisiones", null, null, null, 0, 0, 0);
			BizPNRTicket.ExecCalculeOver(company,null,null);
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Mensajeria", null, null, null, 0, 0, 0);
			// Mensajeria
			stats.start(company,"Procesando Sql event action - Check for events");
			BizSqlEventAction.ExecCheckForEvents(company);

			stats.start(company,"Procesando BSP");
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Calcule BSP", null, null, null, 0, 0, 0);
			new BizPDF().execProcPendientes();

			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin Operacion: "+stats.getFullStatusLog(), null, null, null, 0, 0, 0);
			stats.stop();
		} catch (InterruptedException e) {
			PssLogger.logDebug("Interruption received");
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e.getMessage(), null, null, null, 0, 0, 0);
		} catch (Exception e) {
			PssLogger.logError("Company " + company);
			PssLogger.logError(e);
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e.getMessage(), null, null, null, 0, 0, 0);
		} finally {
			opens--;

			try {
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				if (consumeBandera)
					clearBandera(company);
			} catch (Exception e) {
			}
		}
	}

	// }
	public void processBandera(ThreadStats stats, String company) throws Exception {
		cleanReprocesando(company);

		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio UpdateInterfaz", null, null, null, 0, 0, 0);
		stats.start(company,"Procesando Interfaz news");
		updateInterfazNew(company);
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin UpdateInterfaz", null, null, null, 0, 0, 0);

		// procesamiento y reprocesamiento de indicadores
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio ExecCheckForEvents", null, null, null, 0, 0, 0);
		stats.start(company,"Procesando Check for Events");
		BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().ExecCheckForEvents(company);
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin ExecCheckForEvents", null, null, null, 0, 0, 0);

	}

	public synchronized void processNewFilesPerCompany(ThreadStats stats,String company) throws Exception {
		// actualiza sql eventos
		if (company.equals("CTS"))
			PssLogger.logInfo("logpoint");
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio ExecCheckForEventsNewFiles", null, null, null, 0, 0, 0);
		stats.start(company,"Procesando Check for event new files");

		try {
			BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().ExecCheckForEventsNewFiles(company);
		} catch (Exception e1) {
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e1.getMessage(), null, null, null, 0, 0, 0);
			PssLogger.logError(e1);
		}
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin ExecCheckForEventsNewFiles", null, null, null, 0, 0, 0);

		stats.start(company,"Procesando Sleep");
		Thread.sleep(1000);

		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio ExecCheckForEvents", null, null, null, 0, 0, 0);
		stats.start(company,"Procesando Contratos - Check for events");
		
		try {
			// actualiza contratos
			BizContrato.ExecCheckForEvents(company, true);
		} catch (Exception e) {
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR, e.getMessage(), null, null, null, 0, 0, 0);
			PssLogger.logError(e);
		}
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin ExecCheckForEvents", null, null, null, 0, 0, 0);

		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio UpdateInterfaz", null, null, null, 0, 0, 0);
		stats.start(company,"Procesando Interfaz news");
		updateInterfazNew(company);
		BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin UpdateInterfaz", null, null, null, 0, 0, 0);

		// BizBspLog.log(company, "PRINCIPAL_"+company,
		// BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Inicio
		// calculeOver", null, null, null, 0, 0, 0);
		// new BizPNRTicket().execCalculeOver(company,null, null);
		// BizBspLog.log(company, "PRINCIPAL_"+company,
		// BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Fin
		// calculeOver", null, null, null, 0, 0, 0);

		if (BizBSPCompany.getObjBSPCompany(company).isCNS()) {
			stats.start(company,"Procesando Completar trx faltante");
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Inicio Completar faltantes", null, null, null, 0, 0, 0);
			new BizLiqHeader().execCompletarTrxFaltantes(company);
			BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG, "Fin Completar faltantes", null, null, null, 0, 0, 0);

		}

	  // BizBspLog.log(company, "PRINCIPAL_" + company, BizBspLog.BSPLOG_MODULO_TIMES,BizBspLog.BSPLOG_MODULO_TIMES, stats.getSnapshotAsJson(), null, null, null, 0, 0, 0);

	}

}