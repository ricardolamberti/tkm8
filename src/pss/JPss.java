package pss;

import java.applet.Applet;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import pss.core.JAplicacion;
import pss.core.tools.JNativeTools;
import pss.core.tools.JOutputStreamNull;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;

public class JPss {
	

	
	private final static String APP_NAME="Pss(R) Start Up";
	private final static String APP_COPYRIGHT="Copyright Pentaware Solution , 2008";

	public static String getPssEXEName() {
		return "Pss.exe";
	}

	public static String getPssConsoleEXEName() {
		return "Pss_console.exe";
	}

	public static String getPssVersion() {
		return JPssVersion.getPssVersion();
	}

	public JPss() {
	}

	/**
	 * Starts Pss parsing the command line, which must come as follows:
	 * 
	 * java Pss.JPss [-version] [-?|-help] [-nodesktop|-nomenu|N] [-nodispatcher] [<JWin_class_full_name> <JWin_action_id>] [-u <username> <password>]
	 * 
	 * Where: -nodesktop|-nomenu|N : indicates to open without the Pss menu -nodispatcher : indicates to open without starting the Pss dispatcher in Console mode <JWin_class_full_name> : the name of the JWin class for which to invoke an action at start up <JWin_action_id> : the id of the action to be invoked -nodatabase|-u : indicates to open without opening any databases ('-nodatabase') or to log in to the default database ('-u') without log in dialog; both <username> and <password> are required in the last case -?|-help : shows this information -version : shows the application version
	 */
	public static void main(String[] args) throws Exception {

		// show Pss info
		logln("");
		logln(APP_NAME);
		logln(APP_COPYRIGHT);
		// try to start an application

		if (tryToStartPssApplication(args)) {
			return;
		}

		// if no application was specified, start as usual!

		setDefaultSystemProperties();
		// parse parameters
		boolean withMenu=true;
		boolean withLogin=true;
		boolean withDatabase=true;
		boolean withDispatcher=true;
		String username=null;
		String password=null;
		String winClassName=null;
		String winActionId=null;
		String priority=null;
		boolean expectingActionId=false;
		boolean expectingUser=false;
		boolean expectingPriority=false;
		boolean expectingPassword=false;
		boolean expectingSomething=false;

		for(int i=0; i<args.length; i++) {
			String arg=args[i];
			if (isHelpOption(arg)) {
				showHelp();
				waitToExit(false);
			} else if (isVersionOption(arg)) {
				showVersion();
				waitToExit(false);
//			} else if (isInstallDispatcherOption(arg)) {
//				installDispatcher();
//				waitToExit(false);
			}
			if (!expectingSomething) {
				if (isNoMenuOption(arg)) { // flag menu
					withMenu=false;
				} else if (isLoginOption(arg)) { // flag usuario y password
					withLogin=false;
					expectingUser=true;
				} else if (isNoDatabaseOption(arg)) {
					withDatabase=false;
					withLogin=false;
					expectingUser=false;
				} else if (isNoDispatcherOption(arg)) {
					withDispatcher=false;
				} else if (isPriorityOption(arg)) {
					expectingPriority=true;
					priority=null;
				} else { // es clase JWin
					winClassName=arg;
					expectingActionId=true;
				}
			} else {
				checkIfAnotherOption(arg);
				if (expectingUser) {
					username=arg;
					expectingUser=false;
					expectingPassword=true;
				} else if (expectingPassword) {
					password=arg;
					expectingPassword=false;
				} else if (expectingActionId) {
					winActionId=arg;
					expectingActionId=false;
				} else if (expectingPriority) {
					priority=arg;
					expectingPriority=false;
				}
			}
			expectingSomething=expectingActionId||expectingUser||expectingPassword||expectingPriority;
		}
		if (!withDatabase) {
			if (username!=null||password!=null) {
				exitWithCommandLineError("'-nodatabase' and '-u' options are exclusive");
			}
		}
		if (expectingActionId) {
			exitWithCommandLineError("Expecting action id after '"+winClassName+"'");
		} else if (expectingUser) {
			exitWithCommandLineError("Expecting user name after '-u'");
		} else if (expectingPassword) {
			exitWithCommandLineError("Expecting password after '"+username+"'");
		}
		// start up
		if (System.getProperty("os.name").startsWith("Windows")) {
			if (priority==null) {
				JNativeTools.setMyPriority(JNativeTools.IDLE_PRIORITY_CLASS); // por default en baja
			} else if (priority.trim().equalsIgnoreCase("high")) {
				JNativeTools.setMyPriority(JNativeTools.HIGH_PRIORITY_CLASS);
			} else if (priority.trim().equalsIgnoreCase("idle")) {
				JNativeTools.setMyPriority(JNativeTools.IDLE_PRIORITY_CLASS);
			} else if (priority.trim().equalsIgnoreCase("normal")) {
				JNativeTools.setMyPriority(JNativeTools.NORMAL_PRIORITY_CLASS);
			}
		}
		startUp(withMenu, false, withLogin, withDatabase, withDispatcher, username, password, winClassName, winActionId);
	}

	private static boolean isHelpOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-?")||zCommandLineOption.equalsIgnoreCase("-help");
	}

	private static boolean isVersionOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-version");
	}

	private static boolean isInstallDispatcherOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-installdispatcher");
	}

	private static boolean isNoMenuOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("N")||zCommandLineOption.equalsIgnoreCase("-nodesktop")||zCommandLineOption.equalsIgnoreCase("-nomenu");
	}

	private static boolean isNoDatabaseOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-nodatabase");
	}

	private static boolean isNoDispatcherOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-nodispatcher");
	}

	private static boolean isLoginOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-u");
	}

	private static boolean isPriorityOption(String zCommandLineOption) {
		return zCommandLineOption.equalsIgnoreCase("-priority");
	}

	public static void startUp(boolean withDesktop, boolean withEmbeddableDesktop, boolean withLogin, boolean withDatabase, boolean withDispatcher, String username, String password, final String winClassName, final String winActionId) throws Exception {
		startUp(withDesktop, withEmbeddableDesktop, withLogin, withDatabase, withDispatcher, username, password, winClassName, winActionId, JAplicacion.AppFrontEndWin(), true);
	}

	public static void startUp(boolean withDesktop, boolean withEmbeddableDesktop, boolean withLogin, boolean withDatabase, boolean withDispatcher, String username, String password, final String winClassName, final String winActionId, boolean bOutpuStrem) throws Exception {
		startUp(withDesktop, withEmbeddableDesktop, withLogin, withDatabase, withDispatcher, username, password, winClassName, winActionId, JAplicacion.AppFrontEndWin(), bOutpuStrem);
	}

	public static void startUp(boolean withDesktop, boolean withEmbeddableDesktop, boolean withLogin, boolean withDatabase, boolean withDispatcher, String username, String password, final String winClassName, final String winActionId, String zAppId, boolean bOutputStream) throws Exception {

		// levantar desde PssMain por exe un Pos
		if (!bOutputStream) {
			PrintStream oPW=new PrintStream(new JOutputStreamNull(), true);
			System.setOut(oPW);
		}

		try {
			Applet.newAudioClip(JPss.class.getResource("core/ui/Images/Pss.wav")).play();
		} catch (Exception ex) {
		}

		// set up the configuration
		final JPssStartUpConfiguration conf=JPssStartUpConfiguration.createInstance(withDesktop, withEmbeddableDesktop, withLogin, withDatabase, withDispatcher, null, null, username, password);
		JPssStartUpConfiguration.registerDesktopInstance(conf);

		if (!withDesktop) {
			logln("Starting Pss without desktop");
		}
		if (withEmbeddableDesktop) {
			logln("Starting Pss with embeddable desktop");
		}
		if (!withDatabase) {
			logln("Starting Pss without opening databases");
		}
		if (withDatabase&&!withLogin) {
			logln("Starting Pss without login dialog");
		}
		if (!withDispatcher) {
			logln("Starting Pss without Dispatcher in Console Mode");
		}

	
	}

	@SuppressWarnings("unchecked")
	private static void performStartUpAction(JPssStartUpConfiguration conf, String winClassName, String winActionId) throws Exception {

		Class winClass=null;
		JBaseWin win=null;

		try {
			winClass=Class.forName(winClassName);
			win=(JBaseWin) winClass.newInstance();
			if (JTools.isNumber(winActionId)) {
				performNormalStartup(conf, win, winClass, winActionId);
			} else {
				performCustomStartup(win, winActionId);
			}
		} catch (ClassNotFoundException ex) {
			exitWithError("Win class not found: "+winClassName);
		}

	}

	private static void performCustomStartup(JBaseWin win, String winActionId) throws Exception {
		Method method=win.getClass().getMethod(winActionId, (Class<?>[]) null);
		method.invoke(win, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	private static void performNormalStartup(JPssStartUpConfiguration conf, JBaseWin win, Class winClass, String winActionId) throws Exception {
		BizAction winAction=null;
		if (winActionId.startsWith("Pss")) {
			winAction=win.findActionByUniqueId(winActionId);
		} else {
			winAction=win.findAction(Integer.parseInt(winActionId));
		}
		if (winAction==null) {
//			exitWithError("No action found in class "+win.getClass().getName()+" with id "+winActionId);
		}
		;
		conf.setStartUpWinClass(winClass);
		conf.setStartUpAction(winAction);
		// invoke the win action
		if (winAction!=null) {
			log("Invoking win "+winClass.getName()+", action "+winActionId+"...");
			winAction.getSubmit().Do();
			logln(" DONE");
		}
	}

	private static void log(String zMessage) {
		System.out.print(zMessage);
	}

	private static void logln(String zMessage) {
		System.out.println(zMessage);
	}

	private static void exitWithError(String zErrorDescription) {
		System.out.println();
		System.out.println("Exiting with error:");
		System.out.println(zErrorDescription);
		System.out.println();
//		UITools.MostrarMensaje("Error", zErrorDescription, JOptionPane.ERROR_MESSAGE);
		waitToExit();
	}

	private static void showHelp() {
		System.out.println();
		System.out.println("JPss command line:");
		System.out.println();
		System.out.println("    java Pss.JPss [-version] [-?|-help] [-nodesktop|-nomenu|N] [-nodatabase|-u <username> <password>] [<JWin_class_full_name> <JWin_action_id>]");
		System.out.println();
		System.out.println("    Where:");
		System.out.println("        -nodesktop|-nomenu|N    : indicates to open without the Pss desktop");
		System.out.println("        -nodispatcher           : indicates to open without starting the Pss");
		System.out.println("                                  dispatcher in Console mode");
		System.out.println("        <JWin_class_full_name>  : the name of the JWin class for which to");
		System.out.println("                                  invoke an action at start up");
		System.out.println("        <JWin_action_id>        : the id of the action to be invoked");
		System.out.println("        -nodatabase|-u          : indicates to open without opening any");
		System.out.println("                                  databases ('-nodatabase') or to log in");
		System.out.println("                                  to the default database ('-u') without");
		System.out.println("                                  log in dialog; both <username> and");
		System.out.println("                                  <password> are required in the last case");
		System.out.println("        -?|-help                : shows this information");
		System.out.println("        -version                : shows the application version");
		System.out.println();
	}

	private static void showVersion() {
		StringBuffer info=new StringBuffer();
		info.append("    Application version:  "+JPssVersion.getPssTitle()+" - "+JPssVersion.getPssVersion()+"\n");
		String jPssPath=JPath.PssMainPath()+"/JPssVersion.class";
		String sTimestamp=new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss").format(new Date(new File(jPssPath).lastModified()));
		;
		info.append("    Release timestamp:  "+sTimestamp+"\n\n");
		JOptionPane.showMessageDialog(null, info.toString(), "Pss", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void exitWithCommandLineError(String zErrorDescription) {
		System.out.println();
		System.out.println("Exiting with error in arguments:");
		System.out.println(zErrorDescription);
		showHelp();
		waitToExit();
	}

	private static void waitToExit() {
		System.out.println("PRESS ANY KEY TO EXIT...");
		try {
			System.in.read();
		} catch (Exception ex) {
		}
		System.exit(1);
	}

	private static void setDefaultSystemProperties() {
		System.setProperty("sun.java2d.noddraw", "true");
	}

	//
	// NEW APPLICATION MODEL UTILITY METHODS
	//

	@SuppressWarnings("unchecked")
	private static boolean tryToStartPssApplication(String[] args) throws Exception {
		if (args.length<1) {
			return false;
		}
		String sPossibleAppName=args[0];
		try {
			Class oAppClass=Class.forName(sPossibleAppName);
			if (isApplicationClass(oAppClass)) {
				String[] appArgs=new String[args.length-1];
				if (appArgs.length>0) {
					System.arraycopy(args, 1, appArgs, 0, appArgs.length);
				}
				startApplication(oAppClass, appArgs);
				return true;
			}
		} catch (ClassNotFoundException e) {
			// ignore it to return false
		} catch (Exception e) {
			throw e;
		} catch (Throwable e) {
			throw new Exception("JVM Error", e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static boolean isApplicationClass(Class zClass) {
		Class oClass=zClass;
		while (oClass!=null) {
			if (oClass.getName().equals("pss.core.applications.JAbstractApplication")) {
				return true;
			}
			oClass=oClass.getSuperclass();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static void startApplication(Class zAppClass, String[] zAppArgs) throws Throwable {
		Object oAppObject=zAppClass.newInstance();
		Class oStringArrClass=String[].class;
		Method oStartUpMethod=Class.forName("pss.core.applications.JAbstractApplication").getMethod("startUp", new Class[] { oStringArrClass });
		try {
			oStartUpMethod.invoke(oAppObject, new Object[] { zAppArgs });
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	private static void checkIfAnotherOption(String arg) throws Exception {
		if (isNoMenuOption(arg)||isLoginOption(arg)||isNoDatabaseOption(arg)||isPriorityOption(arg)) exitWithCommandLineError("'"+arg+"' option found where user name expected");
	}

//	private static void installDispatcher() throws Exception {
//		logln("Starting Pss Dispatcher Installation");
//		try {
//			BizScheduler.installDispatcher();
//		} catch (Exception e) {
//			logln("Error: "+e.getMessage());
//		}
//	}

	private static void waitToExit(boolean bMayuscula) {
		if (bMayuscula) System.out.println("PRESS ANY KEY TO EXIT...");
		else System.out.println("press any key to exit...");

		try {
			System.in.read();
		} catch (Exception ex) {
		}
		System.exit(0);
	}

}
