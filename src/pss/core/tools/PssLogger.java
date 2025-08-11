package pss.core.tools;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.DOMConfigurator;

import pss.JPath;
import pss.JPssVersion;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;

public class PssLogger {

	public static final int LOG_ERROR = 0x01;
	public static final int LOG_INFO = 0x02;
	public static final int LOG_WAIT = 0x04;
	public static final int LOG_DEBUG = 0x08;
	public static final int LOG_DEBUG_SQL = 0x10;
	public static final int LOG_DEBUG_XML = 0x20;
	public static final int LOG_FISCAL = 0x40;
	public static final int LOG_MM = 0x80;

	private static boolean logEnable[];

	private static boolean isLogEnable(int type) {
		if (logEnable == null) {
			logEnable = new boolean[255];
			for (int i = 0; i < 255; i++) {
				String sType = getIniForLevel(i);
				if (sType == null) {
					logEnable[i] = false;
					continue;
				}
				logEnable[i] = BizPssConfig.activeDebug(sType);

			}
		}
		if (BizUsuario.getUsr()==null || BizUsuario.getUsr().getLogEnabledValue(type)==-1)
			return logEnable[type];

		return logEnable[type];

	}

	public static String getIniForLevel(int zLogLevel) {
		switch (zLogLevel) {
		case LOG_ERROR:
			return "LOG_ERROR";
		case LOG_INFO:
			return "LOG_INFO";
		case LOG_WAIT:
			return "LOG_WAIT";
		case LOG_DEBUG:
			return "LOG_DEBUG";
		case LOG_DEBUG_SQL:
			return "LOG_DEBUG_SQL";
		case LOG_DEBUG_XML:
			return "LOG_DEBUG_XML";
		case LOG_FISCAL:
			return "LOG_DEBUG_FISCAL";
		case LOG_MM:
			return "LOG_DEBUG_MM";
		default:
			return null;
		}
	}

	private static Logger initialize() throws Exception {
		try {
			String logFileName = getDebugFileName();
			String logName = getDebugName();
			Logger logger = Logger.getLogger(logName);
			RollingFileAppender appender = (RollingFileAppender) logger.getAppender(logFileName);
			if (appender == null) {
				configureLog4j();
				RollingFileAppender appndr = (RollingFileAppender) Logger.getRootLogger().getAppender(BizPssConfig.getLog4J()); // compatibilidad
				Layout pat = pat = new PatternLayout("%d [%t] %-5p - %m%n");
				appender = new RollingFileAppender(pat, logFileName);
				appender.setName(logFileName);
				appender.setMaxBackupIndex(appndr.getMaxBackupIndex());
				appender.setMaximumFileSize(appndr.getMaximumFileSize());
				logger.addAppender(appender);
			}
			appender.setAppend(true);
			appender.activateOptions();
			return logger;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private static void configureLog4j() throws Exception {
		if (new File(JPath.PssPathData() + "/log4j.properties").exists())
			PropertyConfigurator.configure(JPath.PssPathData() + "/log4j.properties");
		else
			DOMConfigurator.configure(JPath.PssPathData() + "/log4j.xml");
	}
	public static void endLogs() throws Exception {
		String logId = getDebugFileName();
		Logger logger = Logger.getLogger(logId);
		logger.removeAllAppenders();
	}

	public static void logDebug(String zMessage) {
		printWithLevel(PssLogger.LOG_DEBUG, zMessage);
	}

	public static void logDebug(Throwable zException) {
		printWithLevel(PssLogger.LOG_ERROR, zException, true);
	}

	public static void logDebug(Throwable zException, String zHeading) {
		logDebug(zHeading);
		logDebug(zException);
	}

	public static void logError(Throwable zException, String zHeading,
			boolean zDumpStack) {
		logError(zHeading);
		logError(zException, zDumpStack);
	}

	public static void logError(Throwable zException, String zHeading) {
		logError(zException, zHeading, true);
	}

	public static void logError(Throwable zException, boolean zDumpStack) {
		printWithLevel(PssLogger.LOG_ERROR, zException, zDumpStack);
	}

	public static void logError(Throwable zException) {
		logError(zException, true);
	}

	public static void logError(String zMessage) {
		printWithLevel(PssLogger.LOG_ERROR, zMessage);
	}

	public static void logDebugSQL(String zMessage) {
		printWithLevel(PssLogger.LOG_DEBUG_SQL, zMessage);
	}

	public static void logDebugXML(String zMessage) {
		printWithLevel(PssLogger.LOG_DEBUG_XML, zMessage);
	}

	public static void logInfo(String zMessage) {
		printWithLevel(PssLogger.LOG_INFO, zMessage);
	}

	public static void logWait(String zMessage) {
		printWithLevel(PssLogger.LOG_WAIT, zMessage);
	}

	public static void logFiscal(String zMessage) {
		printWithLevel(PssLogger.LOG_FISCAL, zMessage);
	}

	public static void logMM(String zMessage) {
		printWithLevel(PssLogger.LOG_MM, zMessage);
	}

	public static void logFiscalMessage(String sTitulo, String zMessage) {
		int len = zMessage.length();
		StringBuffer sHexa = new StringBuffer(10 + (len * 3) + 2);
		sHexa.append(sTitulo);
		sHexa.append('[');
		for (int iCont = 0; iCont < len; iCont++) {
			if (iCont != 0) sHexa.append(' ');
			sHexa.append(JTools.CharToHex(zMessage.charAt(iCont)));
		}
		sHexa.append(']');
		printWithLevel(PssLogger.LOG_FISCAL, sHexa.toString());
	}

	private static void printWithLevel(int Log_Id, String zMensaje) {
		if (!isLogEnable(Log_Id))
			return;
		printWithLevel(Log_Id, zMensaje, null, false);
	}

	private static void printWithLevel(int Log_Id, Throwable t,
			boolean dumpStack) {
		if (!isLogEnable(Log_Id))
			return;
		printWithLevel(Log_Id, null, t, dumpStack);
	}

	private static void printWithLevel(int Log_Id, String message, Throwable t,
			boolean dumpStack) {
		if (!isLogEnable(Log_Id))
			return;
		try {
			Logger logger = initialize();
			if (logger == null) {
				System.out.println(message);
				return;
			}
			printStartInfo();
			if (message != null) {
				try {
					String sText = message;
					printDebugPrint(logger, Log_Id, sText);
				} catch (Exception e) {
					System.out.println(message);
				}
			} else {
				if (dumpStack) {
					String sStackMsg = "";
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					t.printStackTrace(pw);
					sStackMsg = sw.toString();
					//int level = Math.max(Log_Id, LOG_DEBUG);
					printDebugPrint(logger, Log_Id, sStackMsg);
				} else {
					printDebugPrint(logger, Log_Id, t.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("Error en debug print de excepción:");
			e.printStackTrace();
		}
	}

	private static void printDebugPrint(Logger logger, int level,
			String zMensaje) throws Exception {
		if (!isLogEnable(level))
			return;

		switch (level) {
		case LOG_ERROR:
			logger.error(zMensaje);
			break;
		case LOG_INFO:
			logger.info(zMensaje);
			break;
		case LOG_DEBUG:
			logger.debug(zMensaje);
			break;
		case LOG_MM:
			logger.debug(zMensaje);
			break;
		case LOG_DEBUG_SQL:
			logger.debug("[DB:"+JBDatos.getDefaultDatabase().GetName()+"] "+zMensaje);
			break;
		default:
			logger.debug(zMensaje);
			break;
		}
	}


	public static int getLevelForPrefix(String zPrefix) {
		if (zPrefix == null) {
			return 0;
		} else if (zPrefix.equalsIgnoreCase("ERROR")) {
			return LOG_ERROR;
		} else if (zPrefix.equalsIgnoreCase("INFO")) {
			return LOG_INFO;
		} else if (zPrefix.equalsIgnoreCase("WAI")) {
			return LOG_WAIT;
		} else if (zPrefix.equalsIgnoreCase("DEBUG")) {
			return LOG_DEBUG;
		} else if (zPrefix.equalsIgnoreCase("SQL")) {
			return LOG_DEBUG_SQL;
		} else if (zPrefix.equalsIgnoreCase("XML")) {
			return LOG_DEBUG_XML;
		} else {
			return 0;
		}
	}

	private static String getDebugFileName() throws Exception {
		Class<?> application;
		Method getAppMethod;
		try {
			application = Class.forName("pss.core.JAplicacion");
		} catch (Exception e) {
			return JPath.PssPathLog() + "/" + "default.log";
		}
		if (application == null)
			return JPath.PssPathLog() + "/" + "default.log";
		try {
			getAppMethod = application.getMethod("GetLogFileName", new Class[] {});
		} catch (Exception e) {
			return JPath.PssPathLog() + "/" + "default.log";
		}
		return (String) getAppMethod.invoke(null, new Object[] {});

	}
	private static String getDebugName() throws Exception {
		Class<?> application;
		Method getAppMethod;
		try {
			application = Class.forName("pss.core.JAplicacion");
		} catch (Exception e) {
			return JPath.PssPathLog() + "/" + "default.log";
		}
		if (application == null)
			return JPath.PssPathLog() + "/" + "default.log";
		try {
			getAppMethod = application.getMethod("GetLogName", new Class[] {});
		} catch (Exception e) {
			return JPath.PssPathLog() + "/" + "default.log";
		}
		return (String) getAppMethod.invoke(null, new Object[] {});

	}

	static boolean showStartInfo = false;
	public static void printStartInfo() {
		if (showStartInfo) return;
		showStartInfo=true;
		PssLogger.logInfo("______________________________________________________________________________________________________________"); //$NON-NLS-1$ //$NON-NLS-2$
		PssLogger.logInfo(" "); //$NON-NLS-1$ //$NON-NLS-2$
		PssLogger.logInfo(" Pentaware Group - Copyright 2017 ");
		PssLogger.logInfo(" All rights Reserved - www.pentaware.com.ar      ");
		PssLogger.logInfo(" "); //$NON-NLS-1$ //$NON-NLS-2$
		PssLogger.logInfo(" Starting date : " + new Date()); //$NON-NLS-1$ //$NON-NLS-2$
		PssLogger.logInfo(" Java version  : " + java.lang.System.getProperty("java.vm.version") + " - " + java.lang.System.getProperty("java.vm.vendor")); //$NON-NLS-1$ //$NON-NLS-2$
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		PssLogger.logInfo(" Total Memory  : " +  JTools.formatFileSize(heapSize));
		PssLogger.logInfo(" Max Memory    : " +  JTools.formatFileSize(heapMaxSize));
		PssLogger.logInfo(" Free Memory   : " + JTools.formatFileSize( heapFreeSize));
		
		try {
			InetAddress ip= Inet4Address.getLocalHost();
			PssLogger.logInfo(" IP host       : " + ip.getHostAddress()+" ("+ ip.getHostName()+")");
	    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	    
	    byte[] mac = network.getHardwareAddress();

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < mac.length; i++) {
	        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));     
	    }
			PssLogger.logInfo( " MAC           : "+ sb.toString());
		} catch (Throwable e) {
			PssLogger.logInfo(" IP host       : (exception)" +e.getMessage());
		}

		PssLogger.logInfo( " OS Name       : "+ System.getProperty("os.name"));
		PssLogger.logInfo( " OS Version    : "+ System.getProperty("os.version"));
		PssLogger.logInfo( " OS Arch       : "+ System.getProperty("os.arch"));
		
		PssLogger.logInfo( " CPU id        : "+ System.getenv("PROCESSOR_IDENTIFIER"));
		PssLogger.logInfo( " CPU arch      : "+ System.getenv("PROCESSOR_ARCHITECTURE"));
		PssLogger.logInfo( " CPU arch bits : "+ System.getenv("PROCESSOR_ARCHITEW6432"));
		PssLogger.logInfo( " CPU nro       : "+ Runtime.getRuntime().availableProcessors());

		try {
//			File[] paths;
//			FileSystemView fsv = FileSystemView.getFileSystemView();
//			paths = File.listRoots();
//			for(File path:paths)
//				PssLogger.logInfo(" Drive Name    : "+path+" - "+fsv.getSystemTypeDescription(path)+" - Total ["+ JTools.formatFileSize(path.getTotalSpace())+"] Free ["+  JTools.formatFileSize(path.getFreeSpace())+ "] Usable ["+ JTools.formatFileSize(path.getUsableSpace())+"]");
			
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			for (PrintService printer : printServices)
				PssLogger.logInfo(" Printer       : "+ printer.getName());
		} catch (Exception e1) {
			PssLogger.logInfo( " Drive Name    : (error)"+ e1.getMessage());
		} 

		PssLogger.logInfo( " APP name      : "+ JPssVersion.getPssTitle());
		PssLogger.logInfo( " APP version   : "+ JPssVersion.getPssVersion());
		PssLogger.logInfo( " APP date      : "+ JPssVersion.getPssDate());

		try {
			PssLogger.logInfo( " Path pssData  : "+ JPath.PssPathData());
			PssLogger.logInfo( " Path bin      : "+ JPath.PssPathBin());
			PssLogger.logInfo( " Path temp     : "+ JPath.PssPathTempFiles());
			PssLogger.logInfo( " Path input    : "+ JPath.PssPathInput());
			PssLogger.logInfo( " Path output   : "+ JPath.PssPathOutput());
			PssLogger.logInfo( " Path log      : "+ JPath.PssPathLog());
			PssLogger.logInfo( " Path resource : "+ JPath.PssPathResource());
		} catch (Exception e) {
			PssLogger.logInfo( " Path          : (error)"+ e.getMessage());
		}

		try {
			PssLogger.logInfo( " CFG nr.user conc: "+ BizPssConfig.GetMaximumConcurrentSessions());
			PssLogger.logInfo( " CFG language    : "+ BizPssConfig.GetDefaultLanguage());
			PssLogger.logInfo( " CFG prefix      : "+ BizPssConfig.getPssConfig().getAppURLPrefix());
			PssLogger.logInfo( " CFG use captcha : "+ BizPssConfig.getPssConfig().getUseCaptcha());
			PssLogger.logInfo( " CFG s.timeout df: "+ BizPssConfig.getPssConfig().getSessionTimeout());
			PssLogger.logInfo( " CFG business    : "+ BizPssConfig.getPssConfig().getBusinessDefault());
			PssLogger.logInfo( " CFG autologin   : "+ BizPssConfig.getPssConfig().getAutoLogin());
			PssLogger.logInfo( " CFG databases   : "+ BizPssConfig.getPssConfig().getNameDatabases());
			PssLogger.logInfo( " CFG skin df     : "+ BizPssConfig.getPssConfig().getSkinDefault());
			PssLogger.logInfo( " CFG skin mobile : "+ BizPssConfig.getPssConfig().getSkinMobileDefault());
			PssLogger.logInfo( " CFG DB Name     : "+ BizPssConfig.getPssConfig().GetDatabaseNameDefault());
			PssLogger.logInfo( " CFG DB driver   : "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "DRIVER_NAME", "Desconocido"));
			PssLogger.logInfo( " CFG DB auth     : "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "AUTHENTICATION", "No"));
			PssLogger.logInfo( " CFG DB conn     : "+ BizPssConfig.getPssConfig().getCachedStrictValue(BizPssConfig.getPssConfig().GetDatabaseNameDefault(), "CONNECTION_URL", "Desconocido"));
		} catch (Exception e) {
			PssLogger.logInfo( " CFG             : (error)"+ e.getMessage());
		}
/*
		try {
			File n = new File("\\\\34.214.94.33\\homes");
			for (File arch:n.listFiles()) {
				PssLogger.logInfo( arch.getName() );
			}
		} catch (Exception e) {
			PssLogger.logInfo( " MAP             : (error)"+ e.getMessage());
		}
		*/
		PssLogger.logInfo("______________________________________________________________________________________________________________"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static boolean getNoSQLLog() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void habilitarSQLLog() {
		// TODO Auto-generated method stub
		
	}

	public static void deshabilitarSQLLog() {
		// TODO Auto-generated method stub
		
	}

}
