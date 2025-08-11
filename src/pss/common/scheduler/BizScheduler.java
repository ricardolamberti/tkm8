package pss.common.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
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
	

	private static void startHealthCheckServer() {
	    Thread healthThread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            try {
	                // Levanta el servidor en el puerto 8081
	                HttpServer server = HttpServer.create(new InetSocketAddress(BizPssConfig.getHealthPort()), 0);
	                
	                server.createContext("/health", new HttpHandler() {
	                    @Override
	                    public void handle(HttpExchange exchange) throws IOException {
	                        // Puedes construir una respuesta que incluya el estado global y el de cada proceso
	                        StringBuilder response = new StringBuilder();
	                        response.append("OK\n");
	                        response.append("Scheduler Processes Status:\n");
	                        response.append(SchedulerHealth.getStatusReport());
	                        
	                        byte[] bytes = response.toString().getBytes();
	                        exchange.sendResponseHeaders(200, bytes.length);
	                        OutputStream os = exchange.getResponseBody();
	                        os.write(bytes);
	                        os.close();
	                    }
	                });
	                
	                server.setExecutor(null);
	                server.start();
	                PssLogger.logDebug("Health check server started on port "+BizPssConfig.getHealthPort());
	            } catch (IOException e) {
	                PssLogger.logError(e, "Error starting health check server");
	            }
	        }
	    });
	    healthThread.setDaemon(true);
	    healthThread.start();
	}

	public static String readStatus() {
    String serviceUrl = "http://localhost:"+BizPssConfig.getHealthPort()+"/health"; // Ajusta la URL si es necesario
    HttpURLConnection connection = null;
    try {
        URL url = new URL(serviceUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        connection.setConnectTimeout(2000); // Tiempo de conexión en milisegundos
        connection.setReadTimeout(2000);    // Tiempo de lectura en milisegundos
        
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Se lee la respuesta del endpoint
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            in.close();
            return response.toString();
        } else {
            return "Error en la respuesta: " + responseCode;
        }
    } catch (Exception e) {
        return "Service DOWN! -> " + e.getMessage();
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }
}

	public static void main(String[] args) {
		try {

			if (args.length >= 1) {
				WinServiceTools w = new WinServiceTools();
				w.setClassName("pss.common.scheduler.BizScheduler");
				w.setServiceName("PWG7 Process Scheduler");
				w.install();
				System.exit(0);
			}
	    
			startHealthCheckServer();

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
			  SchedulerHealth.setProcessDescription( h.getUID(), bp.getDescription());
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
