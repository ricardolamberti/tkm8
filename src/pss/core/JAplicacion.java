
package pss.core;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import pss.JPath;
import pss.JPssVersion;
import pss.common.help.BizQuestion;
import pss.common.publicity.BizCampaign;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.tools.submit.JSubmit;

public class JAplicacion  {

	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	private static InheritableThreadLocal<JAplicacion> oApp = new InheritableThreadLocal<JAplicacion>();
	public String sTipoAplicacion;
	public String sAplicacionID;

	private String sForzedDatabase = null;
	private String sDefaultDatabase = null; // persistencia de la base default entre seccion
	BizPssConfig oPssConfig = null;
	private JSubmit closeListener = null;
	private boolean activeHelp=false;
	private BizQuestion questionHelp = null;
	private BizCampaign publicityCampaign = null;
	
	private String logFileName = null;
	private String logName = null;
	private String company = null;
	public String sIniSeccion="GENERAL";
	public String getIniSeccion() {
		return sIniSeccion;
	}
	public void setIniSeccion(String sIniSeccion) {
		this.sIniSeccion = sIniSeccion;
	}
	public  static JAplicacion GetApp() {
		return oApp.get();
	}
	public  static void SetApp(JAplicacion zApp) {
		oApp.set(zApp);
	}
	
	/**
	 * @return the sAplicacionID
	 */
	private String getInternalAppId() {
		return sAplicacionID;
	}

	/**
	 * @param aplicacionID
	 *          the sAplicacionID to set
	 */
	private void setInternalAppId(String aplicacionID) throws Exception {
		this.setInternalAppId(null, aplicacionID);
	}
	
  private void setInternalAppId(String company, String aplicacionID) throws Exception {
    sAplicacionID = aplicacionID;
    this.company = company;
    this.obtainLogName();
  }

	public BizPssConfig GetPssConfig() {
		return oPssConfig;
	}

  public void setAppId(String appid) throws Exception {
    this.setAppId(null,appid);
  }
  
  public void setAppId(String company, String appid) throws Exception {
    setInternalAppId(company, appid);
  }
  
  @Deprecated
	public void SetAppId(String zVal) throws Exception {
    setAppId(zVal);
	}
	
	public String GetAppId() {
		return getInternalAppId();
	}

	public void SetTipoApp(String zsTipo) {
		this.sTipoAplicacion = zsTipo;
	}

	static boolean bWaitForClose = true;

	public static void SetWaitForClose(boolean zWait) {
		bWaitForClose = zWait;
	}
	public void setForzedDatabase(String zValue) {
		sForzedDatabase = zValue;
	}
	public String getForzedDatabase() {
		return sForzedDatabase;
	}
	public void setCurrentDatabase(String zValue) {
		sDefaultDatabase = zValue;
	}
	public String getCurrentDatabase() {
		return sDefaultDatabase;
	}
	//-------------------------------------------------------------------------//
	// Constantes de Aplicacion
	//-------------------------------------------------------------------------//

	static final String APLICACION_ID_FRONTEND_WIN = "FRONTEND_WIN";
	static final String APLICACION_ID_PROCESO = "PROCESO";
	static final String APLICACION_ID_DISPACHER = "DISPACHER";
	static final String APLICACION_ID_SHADOW_MENSAJE = "SHADOW_MENSAJE";
	static final String APLICACION_ID_SHADOW_APLICACION = "SHADOW_APLICACION";
	static final String APLICACION_ID_SHADOW_VIDEO = "SHADOW_VIDEO";
	static final String APLICACION_ID_MONITOREO = "MONITOREO";
	static final String APLICACION_ID_TERMINAL_POS = "TERMINAL_POS";
	static final String APLICACION_ID_FRONTEND_WEB = "FRONTEND_WEB";
	static final String APLICACION_ID_LISTENER_SERVICE = "LISTENER";
	static final String APLICACION_ID_INTERNAL_SERVICE = "INTERNAL";
	static final String APLICACION_ID_EXTERNAL_SERVICE = "EXTERNAL";
	static final String APLICACION_ID_PROC_TRANS_SERVICE = "PROCESS_TRANSACTIONS";
	static final String APLICACION_ID_SHADOW_SERVICE = "SHADOW";
	static final String APLICACION_ID_SETUP = "SETUP";
	static final String APLICACION_ID_BACKUP_DATABASE = "BACKUP_DATABASE";
	static final String APLICACION_ID_RESTORE_DATABASE = "RESTORE_DATABASE";
	static final String APLICACION_ID_AUTOFACTURADOR_HE = "AUTOFACTURADOR_HE";

	static final String APLICACION_TIPO_WINDOWS = "WINDOWS";
	static final String SERVICE_APLICATION = "NT_SERVICE";
	static final String APLICACION_TIPO_THREAD = "THREAD";
	static final String APLICACION_TIPO_WEB = "FRONTEND_WEB";
	static final String APLICACION_TIPO_MOVIL = "MOVIL";

	static final String APLICACION_ID = "WINDOWS_POS";
	//  oLanguage            : TLanguage;

	public static String AppId() {
		return APLICACION_ID;
	}

	public static String AppFrontEndWin() {
		return APLICACION_ID_FRONTEND_WIN;
	}
	public static String AppProceso() {
		return APLICACION_ID_PROCESO;
	}
	public static String AppDispacher() {
		return APLICACION_ID_DISPACHER;
	}
	public static String AppMonitoreo() {
		return APLICACION_ID_MONITOREO;
	}
	public static String AppShadowMensaje() {
		return APLICACION_ID_SHADOW_MENSAJE;
	}
	public static String AppShadowAplicacion() {
		return APLICACION_ID_SHADOW_APLICACION;
	}
	public static String AppShadowVideo() {
		return APLICACION_ID_SHADOW_VIDEO;
	}
	public static String AppTerminalPos() {
		return APLICACION_ID_TERMINAL_POS;
	}
  public static String AppAutofacturadorHE() {
    return APLICACION_ID_AUTOFACTURADOR_HE;
  }

	public static String AppTipoWindows() {
		return APLICACION_TIPO_WINDOWS;
	}
	public static String AppTipoThread() {
		return APLICACION_TIPO_THREAD;
	}
	public static String AppService() {
		return SERVICE_APLICATION;
	}
	public static String AppTipoNTService() {
		return SERVICE_APLICATION;
	}
	

	public static String AppFrontEndWeb() {
		return APLICACION_ID_FRONTEND_WEB;
	}
	public static String AppTipoWeb() {
		return APLICACION_TIPO_WEB;
	}
	
	public static String AppTipoMovil() {
		return APLICACION_TIPO_MOVIL;
	}
	
	public static String AppSetup() {
		return APLICACION_ID_SETUP;
	}
	public static String AppBackupDatabase() {
		return APLICACION_ID_BACKUP_DATABASE;
	}
	public static String AppRestoreDatabase() {
		return APLICACION_ID_RESTORE_DATABASE;
	}

	//  public String GetLanguageID()  { return GetApp().oConfig.sLanguageID;   }

	public void setCloseListener(JSubmit zListener) {
		this.closeListener = zListener;
	}
	public JSubmit getCloseListener() {
		return this.closeListener;
	}

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public JAplicacion() {
//		bIfDatabaseAvailable = false;
//		oConfig = new RConfig();
	}

	public static void Exit() {
		try {
			PssLogger.logWait("Cerrando aplicación...");
			GetApp().shutdownApp();
			GetApp().closeApp();
			GetApp().ExitApp();
		} catch (Exception ex) {
			PssLogger.logInfo("No se pudo cerrar la aplicación");
			PssLogger.logError(ex);
		}
	}
	
	@Deprecated
	public static synchronized void cerrarSesion() throws Exception {
		// use closeSession();
		JAplicacion.closeSession();
	}

	public static synchronized void closeSession() {
		JAplicacion.SetApp(null);
		JBDatos.SetGlobal(null);
		BizUsuario.SetGlobal(null);
	}
	
	@Deprecated
	public static synchronized void AbrirSesion() throws Exception {
		// use openSession();
		JAplicacion.openSession();
	}
	
	public static synchronized void openSession() throws Exception {
		JAplicacion.SetApp(new JAplicacion());
		JBDatos.SetGlobal(new JBDatos());
		BizUsuario.SetGlobal(new BizUsuario());
	}
	
	public void openApp(String zAppID, String zTipo, boolean bAbrirBases) throws Exception {
		this.setInternalAppId(zAppID);
		this.SetTipoApp(zTipo);
		if (bAbrirBases)
			this.openDatabasesByIni();
	}
	
	public void openApp(String zAppID, String zTipo, boolean bAbrirBases,String iniSession) throws Exception {
		this.setInternalAppId(zAppID);
		this.SetTipoApp(zTipo);
		this.setIniSeccion(iniSession);
		if (bAbrirBases)
			this.openDatabasesByIni();
	}

	@Deprecated
	public void AbrirApp(String zAppID, String zTipo, boolean bAbrirBases) throws Exception {
		this.openApp(zAppID, zTipo, bAbrirBases);
	}

	public void fillGlobalDatabaseMap() throws Exception {
		// base default
		String defaultDatabase = BizPssConfig.getPssConfig().GetDatabaseNameDefault();
		JBDato bDato = JBDatos.createBaseFromConfig(defaultDatabase, JBaseJDBC.COMMON_USER);
		JBDatos.GetBases().addDatabase(bDato, true);

		// el resto de las bases			
		String databases = BizPssConfig.getPssConfig().getCachedStrictValue(getIniSeccion(), JBDato.DATABASES);
		JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(databases, ',');
		while (tokens.hasMoreTokens()) {
			String base = tokens.nextToken().trim();
			if (base.equalsIgnoreCase(defaultDatabase)) continue;
			JBDato oBDato = JBDatos.createBaseFromConfig(base, JBaseJDBC.COMMON_USER);
			JBDatos.GetBases().addDatabase(oBDato, false);
		}
	}
	
	//-------------------------------------------------------------------------//
	// Obtengo la configuracion del archivo ini
	//-------------------------------------------------------------------------//
	public void openDatabasesByIni() throws Exception {
		if (this.hasForzedDatabase()) {
			// si tiene base de datos forzada
			JBDato bDato = JBDatos.createBaseFromConfig(this.getForzedDatabase(), JBaseJDBC.COMMON_USER);
			JBDatos.GetBases().addDatabase(bDato, true);
		} else {
			fillGlobalDatabaseMap();
		}
			
		JBDatos.openAllDatabases();
		
//		String title   = BizPssConfig.getPssConfig().getTitle();//rjl no tenia sentido este seteo aqui
//		if ( title != null )
//		  JPssVersion.setPssTitle(title);
//		
//		String version = BizPssConfig.getPssConfig().getVersion();
//		if ( version != null )
//		  JPssVersion.setPssVersion(version);

	}
	
	public boolean hasForzedDatabase() throws Exception {
		if (sForzedDatabase == null)
			return false;
		if (sForzedDatabase.equals(""))
			return false;
		return true;
	}

	public void closeApp() throws Exception {
		if (JBDatos.GetBases() == null)
			return;
		try {
			if (BizUsuario.getUsr() != null) {
				BizUsuario.LogOffUsuario();
			}
			JBDatos.closeAllDatabases();
		} catch (JConnectionBroken eCB) {
			PssLogger.logInfo("No se cierra la Base de Datos porque no hay conexión");
		}
	}
	
	@Deprecated
	public void CerrarApp() throws Exception {
		this.closeApp();
	}

	public void shutdownApp() throws Exception {
		if (this.getCloseListener() != null) {
			this.getCloseListener().submit();
		}
		Thread[] aThread = new Thread[100];
		int len = Thread.enumerate(aThread);
		for (int i = 0; i < len; i++) {
			if (aThread[i] == Thread.currentThread())
				continue;
			//      if ( !aThread[i].getName().startsWith("Pss") ) continue;
			if (!aThread[i].getName().startsWith("Pss-Process-"))
				continue;
			PssLogger.logDebug("Shutdown Thread: " + aThread[i].getName());
			aThread[i].interrupt();
		}
		if (JAplicacion.bWaitForClose) {
			PssLogger.logDebug("Waiting seconds...");
			Thread.sleep(4000);
		}
	}
	
	@Deprecated
	public void TerminarApp() throws Exception {
		this.shutdownApp();
	}
	
	/**
	 * Exit the JVM.
	 */
	public void ExitApp() {
		System.exit(0);
	}


	/**
	 * Aks if the application is a web application
	 */
	public boolean ifAppFrontEndWeb() throws Exception {
		return this.getInternalAppId().equals(AppFrontEndWeb()) || this.ifAppNewFrontEndWeb();
	}
	private boolean ifAppNewFrontEndWeb() throws Exception {
	   return this.sTipoAplicacion.equals("web.application") || this.sTipoAplicacion.equals("web.server") || this.sTipoAplicacion.equals("WEB");
	}
	public boolean ifAppFrontEndWindows() throws Exception {
	    return this.getInternalAppId().equals(AppTipoWindows());
	}
	public boolean isActiveHelp() {
		return activeHelp;
	}
	public void setActiveHelp(boolean activeHelp) {
		this.activeHelp = activeHelp;
	}
	public BizQuestion getQuestionHelp() {
		return questionHelp;
	}
	public void setQuestionHelp(BizQuestion questionHelp) {
		this.questionHelp = questionHelp;
	}

	public BizCampaign getPublicityCampaign() {
		if (publicityCampaign==null)
			publicityCampaign = BizCampaign.getDefaultCampaign();
		return publicityCampaign;
	}
	public void setPublicityCampaign(BizCampaign publicityCampaign) {
		this.publicityCampaign = publicityCampaign;
	}
	/// usado por el pssLogger para obtener su nombre, lo movi aca para que pssLogger pudiera no incluir JApplication
	public static String GetLogFileName() throws Exception {
		if (JAplicacion.GetApp() == null)	
			return JPath.PssPathLog() + "/" +"default.log";
		return JAplicacion.GetApp().getLogFileName();
	}
	public static String GetLogName() throws Exception {
		if (JAplicacion.GetApp() == null)	
			return "astor.start";
		return JAplicacion.GetApp().getLogName();
	}

	public String getLogName() throws Exception {
		if ( logName != null ) return logName;
		return  "astor.start";
	}
	
	public String getLogFileName() throws Exception {
		if ( logFileName != null ) return logFileName;
		return JPath.PssPathLog() + "/" +"default.log";
	}
	
	public void resetLogName() throws Exception {
		obtainLogName();
	}
	
	private void obtainLogName() throws Exception {
		String sApp=this.GetAppId();
		String sAppName=this.GetAppId();
		String curruser = BizUsuario.getUsr() == null ? "" : BizUsuario.getUsr().GetUsuario().trim();
		if (curruser.equalsIgnoreCase("") == false) {
			sApp = (BizPssConfig.getPssConfig().getAppURLPrefix()+"/"+BizUsuario.getUsr().getCompany() + "/" + curruser + "." + sApp).toLowerCase();
			sAppName = "astor."+sAppName+"."+BizUsuario.getUsr().getCompany() +"."+curruser;
		} else 
		if ( this.company != null ) {
		  sApp = this.company + "/" + sApp;
			sAppName = "astor."+this.company + "." + sAppName;
		}
		logFileName = JPath.PssPathLog() + "/" + sApp.toLowerCase() + ".log";
		logName = sAppName.toLowerCase();
	}
	public static void endLogs() throws Exception {
		String logId = getDebugName();
		Logger logger = Logger.getLogger(logId);
		logger.removeAllAppenders();
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

	public void setDefaultDatabase(String zValue) {
		sDefaultDatabase = zValue;
	}
	public String getDefaultDatabase() {
		return sDefaultDatabase;
	}
}
