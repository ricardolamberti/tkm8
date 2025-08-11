package pss.www.platform.tasks;

import java.util.Date;

import pss.common.event.sql.serie.BizVirtualSerie;
import pss.common.event.sql.serie.GuiVirtualSeries;
import pss.core.data.BizPssConfig;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class JLogMemoryTask  extends JWebCleanUpTask {

	private static GuiVirtualSeries registroMemories;
	private static double maxMemory = 0;
	public static double getMaxMemory() {
		return maxMemory;
	}

	public static double getPromMemory() {
		return promMemory;
	}

	public static Date getDateMax() {
		return dateMax;
	}


	public static double getMemory() {
		return JTools.getMemoryUsage();
	}
	private static double promMemory = 0;
	private static long muestras = 0;
	private static double summuestras = 0;
	private static Date dateMax = null;
	
	public static GuiVirtualSeries getRegistroMemories() throws Exception {
		if (registroMemories==null) {
			registroMemories=new GuiVirtualSeries();
			registroMemories.getRecords().setStatic(true);
		}
		return registroMemories;
	}

	public static void setRegistroMemories(GuiVirtualSeries registroMemories) {
		JLogMemoryTask.registroMemories = registroMemories;
	}
	public static void addRegisterLog(String info,double usage,double usageM) throws Exception {
		if (BizPssConfig.getPssConfig().getLogMemoryUssage())
			PssLogger.logInfo( JTools.logMemoryUsage());
		
		if (muestras>100) {
			muestras=0;
			summuestras+=usage;
		}
		muestras++;
		summuestras+=usage;
		if (promMemory==0 || muestras>10)
			promMemory=JTools.rd(summuestras/muestras,2);
		
		if (usage>maxMemory) {
			dateMax = new Date();
			maxMemory = usage;
		}
		
		Date fecha = new Date();
		BizVirtualSerie log = new BizVirtualSerie();
		log.setSerie("Memory usage");
		log.setFecha(fecha);
		log.setValor(""+usage);
		getRegistroMemories().getRecords().addItem(log);
		
		BizVirtualSerie logM = new BizVirtualSerie();
		logM.setSerie("Max Memory");
		logM.setFecha(fecha);
		logM.setValor(""+usageM);
		getRegistroMemories().getRecords().addItem(logM);
			
		if (getRegistroMemories().getRecords().selectCount()>1000) {
			getRegistroMemories().getRecords().getStaticItems().removeElementAt(0);
		}
	}
	public JLogMemoryTask(long zDelay) {
		super(zDelay);
	}

	@Override
	public String getName() {
			return "logMemory";
	}

	@Override
	protected void doCleanUp() {
		log();

	}
	
	public void log() {
		try {
			addRegisterLog(JTools.logMemoryUsage(),JTools.getMemoryUsage(),JTools.getMaxMemory());
		} catch (Exception e) {
		}
	}


}
