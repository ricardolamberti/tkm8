package pss.common.scheduler;

import java.util.Date;

import pss.core.JAplicacion;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizScheduler extends JRecord {

	static Thread oThread;

	public static boolean bLoopScheduler = true;

	public BizScheduler() throws Exception {
	}

	public static void main(String[] args) {
		try {

			if (args.length >= 1) {
				WinServiceTools w = new WinServiceTools();
				w.setClassName("pss.common.scheduler.BizScheduler");
				w.setServiceName("PWG6 Process Scheduler");
				w.install();
				System.exit(0);
			}

			BizScheduler sched = new BizScheduler();
			sched.executeScheduler();

		} catch (Throwable e) {
			try {
				PssLogger.logError(e, "Error Main: " + e.getClass().getName() + " - " + e.getMessage() + " Host: " + JTools.GetHostName());
			} catch (Exception ex) {
			}
			System.exit(1);
		}
	}

	private void executeScheduler() throws Exception {

		oThread = Thread.currentThread();
		while (bLoopScheduler) {

			boolean open = false;
			JAplicacion.openSession();
			try {
				JAplicacion.GetApp().openApp("scheduler", JAplicacion.AppService(), true);
				open = true;

				// read all the processes defined every 60 secs
				JRecords<BizProcess> oProcesos = new JRecords<BizProcess>(BizProcess.class);
				oProcesos.addOrderBy("pid");
				oProcesos.toStatic();
				JIterator<BizProcess> it = oProcesos.getStaticIterator();
				while (it.hasMoreElements()) {
					BizProcess proc = it.nextElement();
					// check if the process has to start in the current host
					checkProcessToStart(proc);
				}

			} catch (Exception eee) {
				System.out.println("Error :" + eee.getMessage());
			} finally {
				if (open)
					JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				Thread.sleep(10000);
			}

		}

	}
	
	static JMap<Long,JRecords<BizProcessHost>> cache=null;
	static Date expireTime=null;
	
	private JRecords<BizProcessHost>  getCacheProcess(long id) throws Exception {
		if (expireTime!=null) {
			if (expireTime.before(new Date()))
				cache=null;
		}
		
		if (cache==null) cache = JCollectionFactory.createMap();
		
		JRecords<BizProcessHost> ph = cache.getElement(id);
		if (ph!=null) {
			return ph;
		}
				
		ph=new JRecords<BizProcessHost>(BizProcessHost.class);
		ph.dontThrowException(true);
		ph.addFilter("pid", id);
		ph.addFixedFilter("(host='"+ JTools.GetHostName().toLowerCase()+"' or host='*')");
		ph.addOrderBy("pid");
		expireTime = JDateTools.addDays(new Date(), 1);
		cache.addElement(id,ph);
		return ph;
	}

	private void checkProcessToStart(BizProcess proc) throws Exception, InterruptedException {
		try {
			JIterator<BizProcessHost> it = getCacheProcess(proc.getPID()).getStaticIterator();
			while (it.hasMoreElements()) {
				BizProcessHost h = it.nextElement();
				BizProcess bp = new BizProcess();
				bp.copyProperties(proc);
				if (h.getParams().equals("") == false)
					bp.setClassName(proc.getClassName() + "|" + h.getParams());
				if (bp.hasToExecuteProcess(h)) {
					PssLogger.logDebug(" Starting process => " + bp.getPID() + " - " + bp.description.getValue() + " " + h.getParams());
					bp.executeInThread(h);
					Thread.sleep(500);
				}
			}
		} catch (Throwable eExec) {
			PssLogger.logError(eExec, "Exec Problem");
		}

	}

	public static void doShutdown(String zVal[]) throws Exception {
		bLoopScheduler = false;
		BizScheduler.oThread.interrupt(); // Para interrumpir un sleep
	}

}
