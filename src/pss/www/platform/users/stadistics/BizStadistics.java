package pss.www.platform.users.stadistics;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import pss.JPath;
import pss.JPssVersion;
import pss.common.event.sql.serie.GuiVirtualSeries;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptReloj;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.tasks.JLogMemoryTask;
import pss.www.platform.users.online.GuiOnlineUsers;
import pss.www.platform.users.threads.BizThread;
import pss.www.platform.users.threads.BizThreads;

public class BizStadistics extends JRecord {

		
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JLong pTotalSolicitudes = new JLong();
  private JLong pMaxSolicitudes = new JLong();
  private JDateTime pTimeMaxSolicitudes = new JDateTime();
  private JFloat pPromSolicitudes = new JFloat();
  private JLong pTotalBytes = new JLong();
  private JLong pMaxBytes = new JLong();
  private JDateTime pTimeMaxBytes = new JDateTime();
  private JFloat pPromBytes = new JFloat();
  private JLong pTotalUsers = new JLong();
  private JLong pMaxUsers = new JLong();
  private JDateTime pTimeMaxUsers = new JDateTime();
  private JFloat pPromUsers = new JFloat();
  private JFloat pMem = new JFloat() {
  	public void preset() throws Exception {
  		pMem.setValue(JLogMemoryTask.getMemory());
  	};
  };
  private JFloat pMaxMem = new JFloat() {
  	public void preset() throws Exception {
  		pMaxMem.setValue(JLogMemoryTask.getMaxMemory());
  	};
  };
  private JDateTime pTimeMaxMem = new JDateTime() {
  	public void preset() throws Exception {
  		pTimeMaxMem.setValue(JLogMemoryTask.getDateMax());
  	};
  };
  private JFloat pPromMem= new JFloat() {
  	public void preset() throws Exception {
  		pPromMem.setValue(JLogMemoryTask.getPromMemory());
  	};
  };
  private JHtml pInfoSystem = new JHtml() {
  	public void preset() throws Exception {
  		if (pInfoSystem.isRawNull())
  			pInfoSystem.setValue(getInfoSistema());
  	};
  };
  private JString pGraph1 = new JString() {
  	public void preset() throws Exception {
  		pGraph1.setValue(generateGraph1());
  		
  	};
  };
  private JString pGraphMemory = new JString() {
  	public void preset() throws Exception {
  		pGraphMemory.setValue(generateGraphMemory());
  		
  	};
  };

  private JString pGraphCPU = new JString() {
  	public void preset() throws Exception {
  		pGraphCPU.setValue(generateGraphCPU());
  		
  	};
  };
  private JString pGraphHard = new JString() {
  	public void preset() throws Exception {
  		pGraphHard.setValue(generateGraphHard());
  		
  	};
  };
  private JObjBDs<BizThread> pThreads = new JObjBDs<BizThread> () {
  	public void preset() throws Exception {
  		pThreads.setValue(getThreads());
  		
  	};
  };
  private JString pGraphMemoryHist = new JString() {
  	public void preset() throws Exception {
  		pGraphMemoryHist.setValue(generateGraphMemoryHist());
  		
  	};
  };
	public String getFileName(String tipo) throws Exception {
		return BizUsuario.getUsr().getCompany() + "/" + this.toString() + "." + tipo;
	}
  long acumSol=0;
  long startTime=-1;
  Calendar lastSol=null;
  public void addTotSol() throws Exception {
  	pTotalSolicitudes.setValue(pTotalSolicitudes.getValue()+1);
  	Calendar now =Calendar.getInstance();now.setTime(new Date());
  	if (lastSol!=null && (now.get(Calendar.YEAR)!=lastSol.get(Calendar.YEAR) ||
  			now.get(Calendar.DAY_OF_YEAR)!=lastSol.get(Calendar.DAY_OF_YEAR))) {
  		pTotalSolicitudes.setValue(1);
  		acumSol=1;
			setMaxByt(acumSol);
	 		setTimeByt(now.getTime());
  	}if (lastSol!=null && now.get(Calendar.YEAR)==lastSol.get(Calendar.YEAR) &&
  			now.get(Calendar.DAY_OF_YEAR)==lastSol.get(Calendar.DAY_OF_YEAR) && 
  			now.get(Calendar.MINUTE)==lastSol.get(Calendar.MINUTE) && 
  			now.get(Calendar.SECOND)==lastSol.get(Calendar.SECOND)) {
  		acumSol++;
   	} else {
  		if (acumSol>pMaxSolicitudes.getValue()) {
  			setMaxSol(acumSol);
  	 		setTimeSol(lastSol.getTime());
  		}
  		acumSol=1;
  	}
  	lastSol=now;
 		if (startTime==-1) startTime=System.currentTimeMillis()/1000;
		float segs= (System.currentTimeMillis()/1000-startTime);
		if (pTotalSolicitudes.getValue()!=0) setPromSol((float)JTools.forceRd(segs/pTotalSolicitudes.getValue(),4));
  }
  public void setTotSol(long value) throws Exception {pTotalSolicitudes.setValue(value);}
  public void setMaxSol(long value) throws Exception {pMaxSolicitudes.setValue(value);}
  public void setPromSol(float value) throws Exception {pPromSolicitudes.setValue(value);}
  public void setTimeSol(Date value) throws Exception {pTimeMaxSolicitudes.setValue(value);}
  long acumByt=0;
  long acumAcumByt=0;
  long cantAcumByt=0;
  Calendar lastByt=null;
  public void addTotByt(long size) throws Exception {
  	pTotalBytes.setValue(pTotalBytes.getValue()+size);
  	Calendar now =Calendar.getInstance();now.setTime(new Date());
  	if (lastByt!=null && (now.get(Calendar.YEAR)!=lastByt.get(Calendar.YEAR) ||
  			now.get(Calendar.DAY_OF_YEAR)!=lastByt.get(Calendar.DAY_OF_YEAR))) {
    	acumAcumByt=size;
    	cantAcumByt=1;
  		setPromByt((float)JTools.forceRd(acumAcumByt/cantAcumByt,4));
  		acumByt=size;
			setMaxByt(acumByt);
	 		setTimeByt(now.getTime());
  	}else	if (lastByt!=null && now.get(Calendar.YEAR)==lastByt.get(Calendar.YEAR) &&
  			now.get(Calendar.DAY_OF_YEAR)==lastByt.get(Calendar.DAY_OF_YEAR) && 
  			now.get(Calendar.MINUTE)==lastByt.get(Calendar.MINUTE) && 
  			now.get(Calendar.SECOND)==lastByt.get(Calendar.SECOND)) {
  		acumByt+=size;
   	} else {
  		if (acumByt>pMaxBytes.getValue()) {
  			setMaxByt(acumByt);
  	 		setTimeByt(lastByt.getTime());
  		}
    	acumAcumByt+=acumByt;
    	cantAcumByt++;
  		setPromByt((float)JTools.forceRd(acumAcumByt/cantAcumByt,4));
  		acumByt=size;
  	}
  	lastByt=now;
  }
  public void setTotByt(long value) throws Exception {pTotalBytes.setValue(value);}
  public void setMaxByt(long value) throws Exception {pMaxBytes.setValue(value);}
  public void setPromByt(float value) throws Exception {pPromBytes.setValue(value);}
  public void setTimeByt(Date value) throws Exception {pTimeMaxBytes.setValue(value);}
  long acumUsr=0;
   long cantAcumUsr=0;
  Calendar lastUsr=null;
  public void addTotUsr(long cantidad) {
  	try {
			pTotalUsers.setValue(cantidad);
			Calendar now =Calendar.getInstance();now.setTime(new Date());
	  	if (lastUsr!=null && (now.get(Calendar.YEAR)!=lastUsr.get(Calendar.YEAR) ||
	  			now.get(Calendar.DAY_OF_YEAR)!=lastUsr.get(Calendar.DAY_OF_YEAR))) {
				acumUsr=0;
				cantAcumUsr=0;
	  	}
	  	if (cantidad>pMaxUsers.getValue()) {
				setMaxUsr(cantidad);
				setTimeUsr(new Date());
			}
			acumUsr+=cantidad;
			cantAcumUsr++;
			setPromUsr((float)JTools.forceRd(acumUsr/cantAcumUsr,2));
			acumUsr=1;
			lastUsr=now;
		} catch (Exception e) {
			e.printStackTrace();
		}

  	
  }
  

  public void setTotUsr(long value) throws Exception {pTotalUsers.setValue(value);}
  public void setMaxUsr(long value) throws Exception {pMaxUsers.setValue(value);}
  public void setPromUsr(float value) throws Exception {pPromUsers.setValue(value);}
  public void setTimeUsr(Date value) throws Exception {pTimeMaxUsers.setValue(value);}
   //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizStadistics() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("total_solic", pTotalSolicitudes);
    addItem("max_solic", pMaxSolicitudes);
    addItem("time_solic", pTimeMaxSolicitudes);
    addItem("prom_solic", pPromSolicitudes);
    addItem("total_bytes", pTotalBytes);
    addItem("max_bytes", pMaxBytes);
    addItem("time_bytes", pTimeMaxBytes);
    addItem("prom_bytes", pPromBytes);
    addItem("total_users", pTotalUsers);
    addItem("max_users", pMaxUsers);
    addItem("time_users", pTimeMaxUsers);
    addItem("prom_users", pPromUsers);
    addItem("mem", pMem);
    addItem("max_mem", pMaxMem);
    addItem("time_mem", pTimeMaxMem);
    addItem("prom_mem", pPromMem);
    addItem("graph1", pGraph1);
    addItem("graphMemory", pGraphMemory);
    addItem("graphCpu", pGraphCPU);
    addItem("graphHard", pGraphHard);
    addItem("graphMemoryHist", pGraphMemoryHist);
    addItem("threads", pThreads);
    addItem("info", pInfoSystem);
    
  }
  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem(FIELD, "total_solic", "Total solicitudes", true, false, 18);
    addFixedItem(FIELD, "max_solic", "Max solicitudes en 1 seg", true, false, 18);
    addFixedItem(FIELD, "time_solic", "Momento Max solicitudes en 1 seg", true, false, 18);
    addFixedItem(FIELD, "prom_solic", "Promedio solicitudes", true, false, 18,2);
    addFixedItem(FIELD, "total_bytes", "Total Bytes", true, false, 18);
    addFixedItem(FIELD, "max_bytes", "Max Bytes en 1 seg", true, false, 18);
    addFixedItem(FIELD, "time_bytes", "Momento Max Bytes en 1 seg", true, false, 18);
    addFixedItem(FIELD, "prom_bytes", "Promedio Bytes", true, false, 18,2);
    addFixedItem(FIELD, "total_users", "Total Users", true, false, 18);
    addFixedItem(FIELD, "max_users", "Max Users en 1 seg", true, false, 18);
    addFixedItem(FIELD, "time_users", "Momento Max Users en 1 seg", true, false, 18);
    addFixedItem(FIELD, "prom_users", "Promedio Users", true, false, 18,2);
    addFixedItem(FIELD, "mem", "Memoria", true, false, 18,2);
    addFixedItem(FIELD, "max_mem", "Max Memoria", true, false, 18,2);
    addFixedItem(FIELD, "time_mem", "Momento Max Memoria", true, false, 18,2);
    addFixedItem(FIELD, "prom_mem", "Promedio Memoria", true, false, 18,2);
    addFixedItem(VIRTUAL, "graph1", "Grafico 1", true, false, 100000);
    addFixedItem(VIRTUAL, "graphMemory", "Grafico Memory", true, false, 100000);
    addFixedItem(VIRTUAL, "graphCpu", "Grafico Cpu", true, false, 100000);
    addFixedItem(VIRTUAL, "graphHard", "Grafico Hard", true, false, 100000);
    addFixedItem(VIRTUAL, "graphMemoryHist", "Grafico MemoryHist", true, false, 100000);
    addFixedItem(RECORDS, "threads", "Threads", true, false, 100000).setClase(BizThread.class);
    addFixedItem(VIRTUAL, "info", "Info", true, false, 100000);
  }

  @Override
	public String GetTable() {
    return "";
  }
	public String generateGraph1() throws Exception {
		
		JWins w = new GuiOnlineUsers();
		JWinList wl = new JWinList(w);
    w.ConfigurarColumnasLista(wl);
    w.ConfigurarGraficos(wl);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(711, 342);
		}
		return null;
	}
	public String generateGraphMemory() throws Exception {
		GraphScriptReloj gr = new GraphScriptReloj();
		long heapSize = Runtime.getRuntime().totalMemory()/(1024*1024);
		long heapMaxSize = Runtime.getRuntime().maxMemory()/(1024*1024);
		long heapFreeSize = Runtime.getRuntime().freeMemory()/(1024*1024);
	
		gr.setTitle("Memory MB");
		gr.setValor(heapSize);
		gr.setMax(heapMaxSize);
		gr.setAnimated(false);
    
		return gr.getImage(200, 200);
	}
	public String generateGraphMemoryHist() throws Exception {
		GuiVirtualSeries w = JLogMemoryTask.getRegistroMemories();

	 JWinList wl = new JWinList(w);

		wl.AddColumnaLista("descripcion");
		wl.AddColumnaLista("valor");
		wl.AddColumnaLista("fecha");

		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
	  gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Memoria GB");
	
		gr.setTitle("Memory usage");
		gr.setDateTime(true);
		gr.getModel().addColumn("fecha", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);

		wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(900, 900);
		}
		return null;

	}
	public String generateGraphCPU() throws Exception {
		GraphScriptReloj gr = new GraphScriptReloj();
	  OperatingSystemMXBean bean =  ManagementFactory.getOperatingSystemMXBean();

		gr.setTitle("CPU");
		gr.setValor((long)(bean.getSystemLoadAverage()*100));
		gr.setAnimated(false);
    
		return gr.getImage(200, 200);
	}
	public String generateGraphHard() throws Exception {
		GraphScriptReloj gr = new GraphScriptReloj();
    File file = new File(JPath.PssPathData());
    long totalSpace = file.getTotalSpace() / (1024 * 1024 * 1024);
    long freeSpace = file.getFreeSpace() / (1024 * 1024 * 1024);

		gr.setTitle("Hard drive GB");
		gr.setValor((totalSpace-freeSpace));
		gr.setMax(totalSpace);
		gr.setAnimated(false);
    
		return gr.getImage(200, 200);
	}
	public BizThreads getThreads() throws Exception {
		return  new BizThreads();
	}
	String showStartInfo=null;
	public String getInfoSistema() {
//		if (showStartInfo!=null) return showStartInfo;
		showStartInfo ="<div style=\"font-size:10px;    font-family: monospace;\">";
		showStartInfo+=("<b> Info date :</b> " + new Date()); //$NON-NLS-1$ //$NON-NLS-2$
		showStartInfo+=("<BR/><b> Java version  :</b> " + java.lang.System.getProperty("java.vm.version") + " - " + java.lang.System.getProperty("java.vm.vendor")); //$NON-NLS-1$ //$NON-NLS-2$
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		showStartInfo+=("<BR/><b> Total Memory  :</b> " +  JTools.formatFileSize(heapSize));
		showStartInfo+=("<BR/><b> Max Memory    :</b> " +  JTools.formatFileSize(heapMaxSize));
		showStartInfo+=("<BR/><b> Free Memory   :</b> " + JTools.formatFileSize( heapFreeSize));
		
		try {
			InetAddress ip= Inet4Address.getLocalHost();
			showStartInfo+=("<BR/><b> IP host       :</b> " + ip.getHostAddress()+" ("+ ip.getHostName()+")");
	    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	    
	    byte[] mac = network.getHardwareAddress();

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < mac.length; i++) {
	        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));     
	    }
			showStartInfo+=("<BR/><b> MAC           :</b> "+ sb.toString());
		} catch (Throwable e) {
			showStartInfo+=("<BR/><b> IP host       : (exception)" +e.getMessage());
		}

		showStartInfo+=("<BR/><b> OS Name       :</b> "+ System.getProperty("os.name"));
		showStartInfo+=("<BR/><b> OS Version    :</b> "+ System.getProperty("os.version"));
		showStartInfo+=("<BR/><b> OS Arch       :</b> "+ System.getProperty("os.arch"));
		
		showStartInfo+=("<BR/><b> CPU id        :</b> "+ System.getenv("PROCESSOR_IDENTIFIER"));
		showStartInfo+=("<BR/><b> CPU arch      :</b> "+ System.getenv("PROCESSOR_ARCHITECTURE"));
		showStartInfo+=("<BR/><b> CPU arch bits :</b> "+ System.getenv("PROCESSOR_ARCHITEW6432"));
		showStartInfo+=("<BR/><b> CPU nro       :</b> "+ Runtime.getRuntime().availableProcessors());

		try {
			File[] paths;
//			FileSystemView fsv = FileSystemView.getFileSystemView();
//			paths = File.listRoots();
//			for(File path:paths)
//				showStartInfo+=("<BR/><b> Drive Name    :</b> "+path+" - "+fsv.getSystemTypeDescription(path)+" - Total ["+ JTools.formatFileSize(path.getTotalSpace())+"] Free ["+  JTools.formatFileSize(path.getFreeSpace())+ "] Usable ["+ JTools.formatFileSize(path.getUsableSpace())+"]");
			
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			for (PrintService printer : printServices)
				showStartInfo+=("<BR/><b> Printer       :</b> "+ printer.getName());
		} catch (Exception e1) {
			showStartInfo+=("<BR/><b> Drive Name    : (error)"+ e1.getMessage());
		} 

		showStartInfo+=("<BR/><b> APP name      :</b> "+ JPssVersion.getPssTitle());
		showStartInfo+=("<BR/><b> APP version   :</b> "+ JPssVersion.getPssVersion());
		showStartInfo+=("<BR/><b> APP date      :</b> "+ JPssVersion.getPssDate());

		try {
			showStartInfo+=("<BR/><b> Path pssData  :</b> "+ JPath.PssPathData());
			showStartInfo+=("<BR/><b> Path bin      :</b> "+ JPath.PssPathBin());
			showStartInfo+=("<BR/><b> Path temp     :</b> "+ JPath.PssPathTempFiles());
			showStartInfo+=("<BR/><b> Path input    :</b> "+ JPath.PssPathInput());
			showStartInfo+=("<BR/><b> Path output   :</b> "+ JPath.PssPathOutput());
			showStartInfo+=("<BR/><b> Path log      :</b> "+ JPath.PssPathLog());
			showStartInfo+=("<BR/><b> Path resource :</b> "+ JPath.PssPathResource());
		} catch (Exception e) {
			showStartInfo+=("<BR/><b> Path          : (error)"+ e.getMessage());
		}

		try {
			showStartInfo+=("<BR/><b> CFG nr.user conc:</b> "+ BizPssConfig.GetMaximumConcurrentSessions());
			showStartInfo+=("<BR/><b> CFG language    :</b> "+ BizPssConfig.GetDefaultLanguage());
			showStartInfo+=("<BR/><b> CFG prefix      :</b> "+ BizPssConfig.getPssConfig().getAppURLPrefix());
			showStartInfo+=("<BR/><b> CFG use captcha :</b> "+ BizPssConfig.getPssConfig().getUseCaptcha());
			showStartInfo+=("<BR/><b> CFG s.timeout df:</b> "+ BizPssConfig.getPssConfig().getSessionTimeout());
			showStartInfo+=("<BR/><b> CFG business    :</b> "+ BizPssConfig.getPssConfig().getBusinessDefault());
			showStartInfo+=("<BR/><b> CFG autologin   :</b> "+ BizPssConfig.getPssConfig().getAutoLogin());
			showStartInfo+=("<BR/><b> CFG databases   :</b> "+ BizPssConfig.getPssConfig().getNameDatabases());
			showStartInfo+=("<BR/><b> CFG skin df     :</b> "+ BizPssConfig.getPssConfig().getSkinDefault());
			showStartInfo+=("<BR/><b> CFG DB Name     :</b> "+ BizPssConfig.getPssConfig().GetDatabaseNameDefault());
			showStartInfo+=("<BR/><b> CFG DB driver   :</b> "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "DRIVER_NAME", "Desconocido"));
			showStartInfo+=("<BR/><b> CFG DB auth     :</b> "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "AUTHENTICATION", "No"));
			showStartInfo+=("<BR/><b> CFG DB conn     :</b> "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "CONNECTION_URL", "Desconocido"));
		} catch (Exception e) {
			showStartInfo+=("<BR/><b> CFG             : (error)"+ e.getMessage());
		}
		showStartInfo+="</div>";
		return showStartInfo;
	}

  
  
}