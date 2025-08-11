package pss.bsp.monitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pss.bsp.monitor.log.BizBspLog;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.tools.PssLogger;

public class GlobalStatsManager {
	private static final ConcurrentHashMap<String, ThreadStats> threads = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, Long> companyTotals = new ConcurrentHashMap<>();

	public static ThreadStats getStatsForThread() {
		String name = Thread.currentThread().getName();
		return threads.computeIfAbsent(name, ThreadStats::new);
	}

	public static void logSnapshot() {

		Map<String, Long> totals = new HashMap<>();
		for (ThreadStats ts : threads.values()) {
			Map<String, Long> companyPartial = ts.getCompanySnapshotIncludingCurrent();
			for (Map.Entry<String, Long> e : companyPartial.entrySet()) {
				companyTotals.merge(e.getKey(), e.getValue(), Long::sum);
			}

			Map<String, Long> partial = ts.getSnapshotIncludingCurrent();
			for (Map.Entry<String, Long> e : partial.entrySet()) {
				totals.merge(e.getKey(), e.getValue(), Long::sum);
			}
		}
		long total = totals.values().stream().mapToLong(Long::longValue).sum();
		PssLogger.logInfo("----- CPU lógico (último intervalo) -----");

		StringBuilder js = new StringBuilder();
		js.append("[\n");

		Iterator<Map.Entry<String, Long>> it = totals.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Long> e = it.next();
			double percent = (100.0 * e.getValue() / total);
			PssLogger.logInfo(String.format("%-25s : %6.2f%% (%d ms)%n", e.getKey(), percent, e.getValue()));

			js.append(String.format(Locale.US, "  { \"task\": \"%s\", \"ms\": %d, \"percent\": %.2f }", e.getKey(), e.getValue(), percent));

			if (it.hasNext()) {
				js.append(",\n");
			} else {
				js.append("\n");
			}
		}

		js.append("]");

		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_TIMES, BizBspLog.BSPLOG_MODULO_TIMES, js.toString(), null, null, null, 0, 0, 0);
		long totalCompany = companyTotals.values().stream().mapToLong(Long::longValue).sum();
		PssLogger.logInfo("----- CPU por compañía (último intervalo) -----");

		StringBuilder jsCompany = new StringBuilder();
		jsCompany.append("[");

		Iterator<Map.Entry<String, Long>> it2 = companyTotals.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry<String, Long> e = it2.next();
			double percent = (100.0 * e.getValue() / totalCompany);
			PssLogger.logInfo(String.format("Company %-10s : %6.2f%% (%d ms)", e.getKey(), percent, e.getValue()));
			jsCompany.append(String.format(Locale.US, "  { \"company\": \"%s\", \"ms\": %d, \"percent\": %.2f }", e.getKey(), e.getValue(), percent));
			if (it2.hasNext())
				jsCompany.append(",\n");
		}
		jsCompany.append("];");
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_TIMESCOMPANY, BizBspLog.BSPLOG_MODULO_TIMESCOMPANY, jsCompany.toString(), null, null, null, 0, 0, 0);
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_TIMESFULL, BizBspLog.BSPLOG_MODULO_TIMESFULL, generateThreadStatsJson(), null, null, null, 0, 0, 0);
	}

	public static String generateThreadStatsJson() {
		Collection<ThreadStats> allStats = threads.values();
		StringBuilder json = new StringBuilder();
		json.append("{\n  \"timestamp\": \"").append(java.time.LocalDateTime.now()).append("\",\n  \"threads\": [\n");

		Iterator<ThreadStats> it = allStats.iterator();
		while (it.hasNext()) {
			ThreadStats ts = it.next();
			Map<String, Long> snapshot = ts.getSnapshotIncludingCurrent();
			long total = snapshot.values().stream().mapToLong(Long::longValue).sum();
			if (total == 0)
				total = 1;

			json.append("    {\n");
			json.append("      \"thread\": \"").append(ts.getThreadName()).append("\",\n");
			json.append("      \"tasks\": [\n");

			Iterator<Map.Entry<String, Long>> taskIt = snapshot.entrySet().iterator();
			while (taskIt.hasNext()) {
				Map.Entry<String, Long> task = taskIt.next();
				double percent = (100.0 * task.getValue()) / total;

				json.append(String.format(Locale.US, "        { \"task\": \"%s\", \"ms\": %d, \"percent\": %.2f }", task.getKey(), task.getValue(), percent));
				if (taskIt.hasNext())
					json.append(",\n");
			}

			json.append("\n      ]\n    }");
			if (it.hasNext())
				json.append(",\n");
		}

		json.append("\n  ]\n}");
		return json.toString();
	}

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

				GlobalStatsManager.logSnapshot();
			
			} catch (InterruptedException e) {
				PssLogger.logDebug("Interruption received");
				// break;
			} catch (Exception e) {
				PssLogger.logError(e);
			} finally {
				try {
					JAplicacion.GetApp().closeApp();
					JAplicacion.closeSession();
					Thread.sleep(1 * 60 * 1000);
				} catch (InterruptedException e) {
				} catch (Exception e) {
				}

			
	

			}

	}
}
