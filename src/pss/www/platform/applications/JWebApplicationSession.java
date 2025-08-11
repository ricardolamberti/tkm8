package pss.www.platform.applications;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.cocoon.environment.Session;

import pss.JPath;
import pss.common.event.device.BizChannel;
import pss.common.security.BizLoginTrace;
import pss.common.security.BizUsuario;
import pss.common.security.BizWebUserProfile;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.IInMemory;
import pss.core.win.JBaseWin;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.users.history.BizUserHistory;
import pss.www.ui.controller.JBasicWebUICoordinator;
import pss.www.ui.controller.JIndoorsUICoordinator;
import pss.www.ui.processing.JWebUICoordinator;
import pss.www.ui.skins.JWebSkin;

public class JWebApplicationSession implements Serializable {

	private static final long serialVersionUID = -4287596469154178344L;

	public static final int NEVER_EXPIRES = -1;

	private String sId;
	private String sSubsession = null;
	private String sIpAddress;
	private String traceId;
	private String persitentKey;
	private boolean logoutPerMultiUser = false;
	private boolean logoutAdmin = false;

//	private Map<Long, Map<String, Serializable>> oRegisteredObjects;
//	private Map<String, String> oNameDictionary;
//	private JWebHistoryManager oHistoryManager;

//	private transient JMap<Long, JMap<String, Object>> oRegisteredRequest;
	private transient JOrderedMap<Long, BizUserHistory> stadistics = null;
	private transient JWebSkin skin;

	private transient BizUsuario userSession;
	private transient JAplicacion applSession;
	private transient JBDatos databaseSession;

	// session expiration handling variables
	private long lLastHit;
	private int lMaxInactiveInterval = NEVER_EXPIRES; // in minutes
	private Date loginTime;

	private boolean bIsOpen;
	private boolean bIsOpening;
	private transient JWaitMessage lastMessage;
	private transient WeakReference<Thread> lastThread;
	private transient HttpSession oServletSession;

	private transient JBasicWebUICoordinator oUICoordinator;

	private transient long currentThreadUsingSession;


	
	public void clearSkin() {
		skin = null;
	}

	public void resetWinId() {
		currentThreadUsingSession = 0;
	}

	public boolean isOpening() {
		return this.bIsOpening;
	}

	public boolean isOpen() {
		return this.bIsOpen;
	}

	public void setOpen() {
		this.bIsOpen = true;
		this.bIsOpening = false;
	}

	public void setOpening() {
		this.bIsOpening = true;
	}

	public void setClose() {
		this.bIsOpen = false;
		this.bIsOpening = false;
	}

	public void logoutAdmin() {
		logoutAdmin = true;

	}

	public boolean isLogoutAdmin() {
		return logoutAdmin;
	}

	public void logoutPerMultiUser() {
		logoutPerMultiUser = true;

	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public boolean isLogoutPerMultiUser() {
		return logoutPerMultiUser;
	}

	public boolean isLogout() {
		return logoutAdmin || logoutPerMultiUser;
	}

	/*
	 * private synchronized InheritableThreadLocal getMultithreadedDatabase() { if
	 * (this.oMultithreadedDatabase==null) { this.oMultithreadedDatabase = new
	 * InheritableThreadLocal(); } return this.oMultithreadedDatabase; }
	 * 
	 * public void clearMultithreadedDatabase() { this.oMultithreadedDatabase =
	 * null; }
	 */
	/*
	 * public JBDatos getDatabase() { JBDatos oDB; if (this.isMultiThreaded()) { oDB
	 * = (JBDatos) this.getMultithreadedDatabase().get(); } else { oDB =
	 * this.oDatabase; } if (oDB==null) { throw new
	 * RuntimeException("Session must be attached to current thread before asking for its database"
	 * ); } return oDB; }
	 */
	public JBDatos getDatabase() {
		return this.databaseSession;
	}

	public BizUsuario getUser() {
		return this.userSession;
	}

	public String getId() {
		return this.sId + "|" + (sSubsession == null ? "" : sSubsession);
	}

	public String getSessionId() {
		return this.sId;
	}

	public String getIdSubsession() {
		return sSubsession;
	}

	public boolean isSubsession() {
		return !(sSubsession == null || sSubsession.equals(""));
	}

	public JAplicacion getOldAplicacionObject() {
		return this.applSession;
	}


	// public JApplication getApplication() {
	// return this.oApplication;
	// }

	/*
	 * protected void setDatabase(JBDatos zDB) { if (this.isMultiThreaded()) {
	 * this.getMultithreadedDatabase().set(zDB); } else { this.oDatabase = zDB; } }
	 */


	private String ipAddress = null;
	public void logIn(String zUser, String zPassword, String ipaddress, String loginClass,boolean persistent, JMap<String,Object>  attributes) throws Exception {

		
		// try {
		JAplicacion.openSession();
		BizLoginTrace login = new BizLoginTrace();
		if (loginClass == null) {
			loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		}
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.SetLogin(zUser);
		login.setPersistent(persistent);
		login.SetPassword(zPassword);
		login.setIpAddress(ipaddress);
		login.setAttributes(attributes);
		login.SetIdApp(JAplicacion.AppFrontEndWeb());
		login.SetTipoApp(JAplicacion.AppTipoWeb());

		login.execProcessInsert();

		if (login.isOutAccess() && !BizUsuario.getUsr().canOutAccess())
			throw new Exception("No tiene autorización para loguearse desde fuera de la intranet. Si lo necesita comuniquese con el administrador.");
	
		
		traceId = login.getTraceId();
		persitentKey = login.getPersistentKey();
		
		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();
		setIpAddress(ipaddress);
		fillConfig(zUser);
	}

	public void logSubsession(String zUser, String zTraceId) throws Exception {

		// try {
		JAplicacion.openSession();
		BizLoginTrace login = new BizLoginTrace();
		String loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.read(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), "", zTraceId, zUser);

		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();

		// load the user's web configuration
//		fillConfig(zUser);
	}
	public void initialize() throws Exception {

		// try {
		JAplicacion.openSession();
		JAplicacion.GetApp().openApp(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), true);

		
		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();
			// load the user's web configuration
//		setIpAddress(ipaddress);
//			fillConfig(zUser);
		
	}
	
	public void initializeUser(String zUser) throws Exception {

	 try {
		JAplicacion.openSession();
		JAplicacion.GetApp().openApp(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), true);
		
		BizUsuario usr = new BizUsuario();
		usr.Read(zUser);
		BizUsuario.SetGlobal(usr);
		
		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();
			// load the user's web configuration
//		setIpAddress(ipaddress);
//			fillConfig(zUser);
	 } finally {
			finalize();
		}
	}
	
	public void finalize() throws Exception {
		JAplicacion.GetApp().closeApp();
		JAplicacion.closeSession();
	}
	public String getFileUploadSession() throws Exception {
		String path = JPath.PssMainPath() + "../../work/upload-dir";
		File[] files = JTools.listFilesAsArray(new File(path), new FilenameFilter() {

			public boolean accept(File dir, String name) {

				return name.startsWith(getSessionId());
			}
		}, false);
		return files != null && files.length > 0 ? files[0].getAbsolutePath() : null;
	}

	public JWebApplicationSession(String zClientAddress, Session zServletSession, String subsession) {
		this.oServletSession = zServletSession;
		this.setId(zServletSession.getId(), subsession);
	}

	// public JApplication getApplication() {
	// return this.oApplication;
	// }

	/*
	 * protected void setDatabase(JBDatos zDB) { if (this.isMultiThreaded()) {
	 * this.getMultithreadedDatabase().set(zDB); } else { this.oDatabase = zDB; } }
	 */
	protected void setUser(BizUsuario usuario) {
		this.userSession = usuario;
	}

	private String buildSubsession(String subsession) {
		return subsession == null || subsession.equals("undefined") ? "" : subsession;
	}

	protected void setId(String string, String subsession) {
		this.sId = string;
		this.sSubsession = buildSubsession(subsession);
	}

	public void updateLastHit() {
		this.lLastHit = System.currentTimeMillis();
	}

	long getLastHit() {
		return this.lLastHit;
	}

	public boolean hasExpired() {
		if (this.logoutPerMultiUser)
			return true;
		if (this.logoutAdmin)
			return true;
		if (this.isOpening()) {
			return false;
		} else if (!this.isOpen()) {
			return true;
		}
		long lMaxIntervalMinutes = this.getTimeout();
		if (this.isCacheInDisk()) {
			lMaxIntervalMinutes = 60 * 24; // si pasa un dia la mato
		}

		return (lMaxIntervalMinutes != NEVER_EXPIRES)
				&& System.currentTimeMillis() > (this.lLastHit + (lMaxIntervalMinutes * 60 * 1000));
	}

	public int getTimeout() {
		return this.lMaxInactiveInterval;
	}

	public void setTimeout(int zMinutes) {

		if (zMinutes == NEVER_EXPIRES) {
			this.lMaxInactiveInterval = NEVER_EXPIRES;
			return;
		}
		if (this.lMaxInactiveInterval != NEVER_EXPIRES) {
			throw new RuntimeException("Maximum inactive interval can be set only once for a session");
		} else if (zMinutes < 1) {
			zMinutes=10;
		}
		this.lMaxInactiveInterval = zMinutes;
	}

	public JWebApplicationSession(String zClientAddress, HttpSession zServletSession, String session, String subsession) {
		this.oServletSession = zServletSession;
		this.setIpAddress(zClientAddress);
		this.setId(session == null ? this.oServletSession.getId() : session, subsession);
	}

	public static JWebApplicationSession getCurrent() {
		if (!hasCurrent())
			return null;
		return JWebActionFactory.getCurrentRequest().getSession();
	}

	public static boolean hasCurrent() {
		return JWebActionFactory.getCurrentRequest() != null;
	}

	public boolean isCurrent() {
		return getCurrent() != null && getCurrent().getSessionId().equals(this.getSessionId())
				&& getCurrent().getIdSubsession().equals(this.getIdSubsession());
	}

	public static boolean canAbort() throws Exception {
		JWebActionData p = JWebActionFactory.getCurrentRequest().getData("export");
		if (p == null)
			return true;
		return p.get("range") == null;
	}

	public HttpSession getServletSession() {
		return this.oServletSession;
	}

	public void logIn(String zUser, String zPassword, String ipaddress, String loginClass,boolean persistent) throws Exception {

		// try {
		JAplicacion.openSession();
		JAplicacion.GetApp().openApp(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), true);

		BizLoginTrace login = new BizLoginTrace();
		if (loginClass == null) {
			loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		}
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.SetLogin(zUser);
		login.setPersistent(persistent);
		login.SetPassword(zPassword);
		login.setIpAddress(ipaddress);
		login.SetIdApp(JAplicacion.AppFrontEndWeb());
		login.SetTipoApp(JAplicacion.AppTipoWeb());

		login.execProcessInsert();
		BizUsuario.SetGlobal(login.getObjUsuario());

		if (login.isOutAccess() && !BizUsuario.getUsr().canOutAccess())
			throw new Exception("No tiene autorización para loguearse desde fuera de la intranet. Si lo necesita comuniquese con el administrador.");
	
		
		traceId = login.getTraceId();
		persitentKey = login.getPersistentKey();
		
		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();
		
		setIpAddress(ipaddress);
		fillConfig(zUser);
	}



	public void fillConfig(String zUser) throws Exception {
		try {
			setLoginTime(new Date());

			this.oServletSession.setMaxInactiveInterval(-1);

	
		} catch (Exception eT) {
			PssLogger.logError(eT);

		}
	}

	public String logInRemote(String zTraceId, String ipaddress) throws Exception {

		// try {
		JAplicacion.openSession();

		BizLoginTrace login = new BizLoginTrace();
		String loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.read(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), "", zTraceId, null);

		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();

		fillConfig(login.getLogin());

		return login.getLogin();
	}

	public String logInPeristent(String zKey, String ipaddress) throws Exception {

		// try {
		JAplicacion.openSession();

		BizLoginTrace login = new BizLoginTrace();
		String loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.readPerisistent(JAplicacion.AppFrontEndWeb(), JAplicacion.AppTipoWeb(), "", zKey);

		this.applSession = JAplicacion.GetApp();
		this.userSession = BizUsuario.getUsr();
		this.databaseSession = JBDatos.GetBases();

		fillConfig(login.getLogin());

		return login.getLogin();
	}

	public void close() throws Exception {
		this.attachGlobalsToCurrentRequest();
		this.getUser().execRegistrarEgreso();
		try {// RJL, invalidar es por un tema de seguridad, pero como lo reutilizo en
				// subsessiones y tabs, si lo mato mueren todas, solo lo dejo en el modelo mas
				// simple
			if (BizPssConfig.getPssConfig().isOnlyOneSessionByBrowser())
				if (this.oServletSession != null)
					this.oServletSession.invalidate();
		} catch (IllegalStateException e) {
			// session could have been invalidated by server
		}
		this.setClose();
		this.cleanUpAfterClose();
		detachGlobals();

	}

	//
	//
	// UTILITY METHODS TO SET GLOBAL DATA FOR REQUEST PROCESSING
	//
	//

	public synchronized void attachGlobalsToCurrentRequestWithinBase() throws Exception {
		try {
			JAplicacion.SetApp(this.getOldAplicacionObject());
			BizUsuario.SetGlobal(this.getUser());
		} catch (Exception e) {
			try {
				PssLogger.logError(e);
				JAplicacion.closeSession();
			} catch (RuntimeException e1) {
				PssLogger.logError(e1);
			}
			throw e;
		}
	}

	public synchronized void attachGlobalsToCurrentRequest() throws Exception {
		try {
			JAplicacion.SetApp(this.getOldAplicacionObject());
			JBDatos.SetGlobal(this.getDatabase().cloneDatabases(JAplicacion.GetApp().getDefaultDatabase()));
			JBDatos.openAllDatabases();
			if (JBDatos.GetBases().GetDatabaseDefault() == null && JAplicacion.GetApp().getDefaultDatabase() != null)
				JBDatos.GetBases().useNewDatabaseDefault(JAplicacion.GetApp().getDefaultDatabase());
			BizUsuario.SetGlobal(this.getUser());
		} catch (Exception e) {
			try {
				PssLogger.logError(e);
				JAplicacion.closeSession();
			} catch (RuntimeException e1) {
				PssLogger.logError(e1);
			}
			throw e;
		}
	}
//
//	public void cleanUpRegisteredObjects() throws Exception {
//		Iterator<Map<String, Serializable>> it = getRegisteredObjects().values().iterator();
//		while (it.hasNext()) {
//			Map<String, Serializable> m = it.next();
//			Iterator<Serializable> it2 = m.values().iterator();
//			while (it2.hasNext()) {
//				Serializable s = it2.next();
//				if (s instanceof IInMemory)
//					((IInMemory) s).cleanUpToLeaveInMemory();
//			}
//		}
//	}

	public void cleanUpRegisteredHistory() throws Exception {
//		getHistoryManager().cleanUpToLeaveInMemory();
	}

	public void cleanUpToLeaveInMemory() {
		try {
//			cleanUpRegisteredObjects();
			cleanUpRegisteredHistory();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		// for testing cache all request
//		cacheSession();

	}

	public static synchronized void detachGlobals() throws Exception {
		try {
			JBDatos.closeAllDatabases();
			JAplicacion.closeSession();
		} catch (Exception e) {
			JAplicacion.closeSession();
			throw e;
		}
	}

	// public synchronized void detachFromCurrentRequest() throws Exception {
	// try {
	// JBDatos oGlobalBDatos = JBDatos.GetBases();
	// if (oGlobalBDatos != null) {
	// oGlobalBDatos.closeDatabases();
	// }
	// this.cleanUpGlobalData();
	// } catch (Exception e) {
	// this.cleanUpGlobalData();
	// throw e;
	// }
	// }
	//
	// private void cleanUpGlobalData() throws Exception {
	// BizUsuario.SetGlobal(null);
	// JAplicacion.SetApp(null);
	// JBDatos.SetGlobal(null);
	// }

	private synchronized void cleanUpAfterClose() {
		if (this.oUICoordinator != null) {
			this.oUICoordinator.cleanUp();
			this.oUICoordinator = null;
		}
		this.oServletSession = null;
		this.setUser(null);
	}

	public JWebUICoordinator getUICoordinator() {
		if (this.oUICoordinator == null) {
			this.oUICoordinator = new JIndoorsUICoordinator(this);
		}
		return this.oUICoordinator;
	}

	public BizWebUserProfile getUserProfile() throws Exception {
		BizUsuario usr = BizUsuario.getUsr();
		if (usr == null)
			return null;
		return usr.getUserProfile();
	}

	public boolean isMultiThreaded() {
		return true;
	}

	String homePage;

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getHomePageAction() throws Exception {
		if (homePage != null)
			return homePage;
		return this.getUserProfile().getHomePageAction();
	}

//	public JMap<String,Object> getRegisterHistoryProvider(String provider) throws Exception {
//		JMap<String,Object> map = getTempHistoryObjects().getElement(provider);
//		if (map==null) {
//			map= JCollectionFactory.createMap();
//			getTempHistoryObjects().addElement(provider, map);
//		}
//		return map;
//	}
//	public synchronized String registerHistoryObject(String provider,String pos, Object zBaseWin) throws Exception {
//		getRegisterHistoryProvider(provider).addElement(pos, zBaseWin);
//		return pos;
//			
//	}
//	public synchronized JMap<String,Object> cleanRegisterHistoryObject(String provider) throws Exception {
//		JMap<String,Object> map = getRegisterHistoryProvider(provider);
//		getTempHistoryObjects().removeElement(provider);
//		return map;
//			
//	}

//	public synchronized void fillHistoryObject() throws Exception {
//		Enumeration<String> en=getHistoryManager().getLastHistory().getProviders().keys();
//		while (en.hasMoreElements()) {
//				String provider = en.nextElement();
//				getHistoryManager().getLastHistory().findProvider(provider).addHistoryObject(cleanRegisterHistoryObject(provider));
//		}
//		cleanTempHistoryObjects();
//	}

//	public synchronized String registerObject(String pos, JBaseWin zBaseWin) throws Exception {
//		String id = pos;
//		if (zBaseWin.forceCleanHistory())
//			resetWinId();
//		Serializable obj = this.getRegisteredObjectsInActualDictionary().get(pos);
//		if (obj != null)
//			this.getRegisteredObjectsInActualDictionary().remove(pos);
////		PssLogger.logDebug("THREAD ------------------------------> Register "+getIdDictionary()+"("+id+")"+zBaseWin.toString());
//		this.getRegisteredObjectsInActualDictionary().put(pos, JWebActionFactory.baseWinToSession(zBaseWin));
//		return id;
//	}
//
//	public synchronized String registerObjectObj(Serializable zObject) throws Exception {
//		return registerObjectObj(zObject, false);
//	}
//
//	public synchronized String registerObjectTemp(Serializable zObject) throws Exception {
//		return registerObjectObj(zObject, true);
//	}

//	public synchronized String registerObjectObj(Serializable zObject, boolean temp) throws Exception {
//		String id = "obj_" + (temp ? "t" : "p") + "_" + zObject.hashCode();// +
//																			// this.getRegisteredObjectsInActualDictionary().size();
//		Serializable obj = this.getRegisteredObjectsInActualDictionary().get(id);
//		if (obj != null)
//			this.getRegisteredObjectsInActualDictionary().remove(id);
////		PssLogger.logDebug("THREAD ------------------------------> Register Obj"+getIdDictionary()+"("+id+")"+zObject.toString());
//		this.getRegisteredObjectsInActualDictionary().put(id, zObject);
//		return id;
//	}

//	public Serializable getRegisterObject(String key) {
////		PssLogger.logDebug("THREAD ------------------------------> try getRegisterObject "+getOldIdDictionary()+"("+key+"): ");
//		if (getRegisteredObjectsInOldDictionary() == null)
//			PssLogger.logDebug("THREAD ------------------------------> try getRegisterObject " + getOldIdDictionary()
//					+ "(" + key + "): ");
//
//		Serializable obj = getRegisteredObjectsInOldDictionary().get(key);
////		PssLogger.logDebug("THREAD ------------------------------> getRegisterObject "+getOldIdDictionary()+"("+key+"): "+(obj==null?"NO ENCONTRADO":"OK"));
//		return obj;
//	}

//	public synchronized String registerObject(JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin == null)
//			return null;
//		if (zBaseWin.forceCleanHistory())
//			resetWinId();
////		String id = "rd_" + this.getRegisteredObjectsInActualDictionary().size();
////		String id="obj_p_"+zBaseWin.hashCode();
//		return this.registerObject(zBaseWin.getUniqueId(), zBaseWin);
//	}

//	public Map<Long, Map<String, Serializable>> getRegisteredObjects() {
//		if (oRegisteredObjects == null) {
//			oRegisteredObjects = new HashMap<Long, Map<String, Serializable>>();
//		}
//		return oRegisteredObjects;
//
//	}

//	public void cleanTempHistoryObjects() {
//		tempHistoryObjects=null;
//	}
//	public JMap<String,JMap<String, Object>> getTempHistoryObjects() {
//		if (tempHistoryObjects==null) {
//			tempHistoryObjects=JCollectionFactory.createMap();
//		}
//		return tempHistoryObjects;
//	
//	}
//	public JMap<Long, JMap<String, Object>> getRegisteredRequest() {
//		if (oRegisteredRequest == null) {
//			oRegisteredRequest = JCollectionFactory.createMap();
//		}
//		return oRegisteredRequest;
//
//	}

	static long idDictionaryGenerator = 0;
	private long idOldDictionary = 0;
	private long idNewDictionary = 0;

//	public Map<String, String> getNameDictionary() {
//		if (oNameDictionary == null) {
//			oNameDictionary = new HashMap<String, String>();
//		}
//		return oNameDictionary;
//
//	}

	static long id = 0;

//	public String getNameDictionary(String s) {
//		if (s.indexOf("anonimus_") != -1)
//			return s;
//		String k = getNameDictionary().get(s);
//		if (k != null)
//			return k;
//		String key = "k" + (id++) + "";
//		getNameDictionary().put(s, key);
//		return key;
//	}
//
//	public String getNameDictionary(long id) {
//		String key = "k" + id;
//		Iterator<String> it = getNameDictionary().keySet().iterator();
//		while (it.hasNext()) {
//			String tkey = it.next();
//			String tval = getNameDictionary().get(tkey);
//			if (!tval.equals(key))
//				continue;
//			return tkey;
//		}
//
//		return null;
//	}

	public long getOldIdDictionary() {
		return idOldDictionary;
	}

	public long getIdDictionary() {
		return idNewDictionary;
	}

	public void setOldDictionary(long value) {
		idOldDictionary = value;
	}

	public synchronized long generateIdDictionary() {
		return idNewDictionary = (++idDictionaryGenerator);
	}

	public Long getIdRequest() {
		if (JWebActionFactory.getCurrentRequest().getRequestid() == null)
			return new Long(0);
		if (JWebActionFactory.getCurrentRequest().getRequestid().equals(""))
			return new Long(0);
		return Long.parseLong(JWebActionFactory.getCurrentRequest().getRequestid());
	}

//	public Map<String, Serializable> getRegisteredObjectsInOldDictionary() {
//		return getRegisteredObjects().get(getOldIdDictionary());
//	}
//
//	public JMap<String, Object> getRegisteredRequestObject(long requestId) {
//		JMap<String, Object> map = getRegisteredRequest().getElement(requestId);
//		if (map == null) {
//			map = JCollectionFactory.createMap();
//			getRegisteredRequest().addElement(requestId, map);
//		}
//		return map;
//	}

//	public Object getRegisteredRequestObject(long requestId, String key) {
//		return getRegisteredRequestObject(requestId).getElement(key);
//	}
//
//	public Object setRegisteredRequestObject(long requestId, String key, Object obj) {
//		return getRegisteredRequestObject(requestId).addElement(key, obj);
//	}
//
//	public Object removeRegisteredRequestObject() {
//		return getRegisteredRequest().removeElement(getIdRequest());
//	}
//
//	public Map<String, Serializable> getRegisteredObjectsInActualDictionary() throws Exception {
//// con los json pidiendo muchos reques simultaneos este control, falla RJL
////		if (Thread.currentThread().getId()!=currentThreadUsingSession&&currentThreadUsingSession!=0) 
////			throw new Exception("Transaccion Abortada por el usuario");
//		Map<String, Serializable> map = getRegisteredObjects().get(getIdDictionary());
//		if (map != null)
//			return map;
////		PssLogger.logDebug("THREAD ------------------------------> BUILD NE MAP "+getIdDictionary());
//		map = new HashMap<String, Serializable>();
//		getRegisteredObjects().put(getIdDictionary(), map);
//		if (getRegisteredObjects().get(getIdDictionary()) == null)
//			getRegisteredObjects().put(getIdDictionary(), map);
//		return map;
//	}
//
//	public void resetOldRegisteredObjects(long idDict) {
//		Iterator<Long> it = getRegisteredObjects().keySet().iterator();
//		while (it.hasNext()) {
//			Long value = it.next();
//			if (value < idDict) {
//				it.remove();
////				PssLogger.logDebug("THREAD ------------------------------> REMOVE OLD "+value);
//			}
//		}
//	}
//
//	public void resetOneRegisteredObjects(long idDict) {
//		getRegisteredObjects().remove(idDict);
////		PssLogger.logDebug("THREAD ------------------------------> RESET OUT "+idDict);
//	}

	public void restoreErrorSituation() {
//		  if (getIdDictionary()==winId)
//		  	winId=0;
		// resetRegisteredObjects();
		try {
			restoreInHistoryErrorSituation();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void restoreInHistoryErrorSituation() throws Exception {
		// pner una marca de completed, y luego volever hasta el ultimo completed
//    	getHistoryManager().backOneHistory();
	}

//	public synchronized long winRegisteredObjects(long id, boolean preserve, boolean resetObjects) {
//		currentThreadUsingSession = Thread.currentThread().getId();
//		long winId = 0;
//		if (id == 0) {
//			winId = generateIdDictionary(); // genero registro nuevo
//			Map<String, Serializable> map = new HashMap<String, Serializable>();
//			getRegisteredObjects().put(winId, map); // genero uno nuevo
////			PssLogger.logDebug("THREAD ------------------------------> SIN ID");
//			return getIdDictionary();
//		}
////		PssLogger.logDebug("THREAD ------------------------------> WIN "+id+" "+(preserve?"preserve":""));
//		setOldDictionary(id);
//		Map<String, Serializable> oldMap = getRegisteredObjectsInOldDictionary(); // busco registro original
//		if (oldMap != null) {
//			winId = generateIdDictionary(); // genero registro nuevo
//			resetOneRegisteredObjects(winId); // libero por si esta lleno
//			if (preserve)
//				getRegisteredObjects().put(winId, oldMap); // debo conservar, entonces los copio al nuevo
//			else {
//				Map<String, Serializable> map = new HashMap<String, Serializable>();
//				getRegisteredObjects().put(winId, map); // NO debo conservar, genero uno nuevo
//			}
//		}
//		if (resetObjects)
//			resetOldRegisteredObjects(getOldIdDictionary());
//		return getIdDictionary();
//	}

	// la idea era en el metodo anterior poenr getRegisteredObjects().put(winId,
	// preserveObjects(oldMap)); y depurar algunos pero no funciono
	// porque el formLov tiene un refresh que no rearma todos los otros componentes
	// y se pierden objetos
//	public Map<String, Serializable> preserveObjects(Map<String, Serializable> oldMap) {
//		Map<String, Serializable> newOldMap = new HashMap<String, Serializable>();
//
//		Iterator<String> it = oldMap.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			if (key.startsWith("obj_t_"))
//				continue; // no se preservan temporales
//			newOldMap.put(key, oldMap.get(key));
//		}
//		return newOldMap;
//	}

	public JWebHistoryManager getHistoryManager() {
//		if (oHistoryManager == null)
//			oHistoryManager = new JWebHistoryManager(this);
//		return oHistoryManager;
		return JWebActionFactory.getCurrentRequest().getHistoryManager();
	}

	public String getTraceId() {
		return traceId;
	}

	public String getPersistentKey() {
		return persitentKey;
	}

	public JMap<Long, BizUserHistory> getStadistics() {
		if (this.stadistics == null)
			this.stadistics = JCollectionFactory.createOrderedMap();
		return stadistics;
	}

	public void killUnfinish(long limit) throws Exception {
		JIterator<BizUserHistory> it = getStadistics().getValueIterator();
		while (it.hasMoreElements()) {
			BizUserHistory userH = it.nextElement();
			if (userH.isFinalize())
				continue;
			if (userH.getIdReq() >= limit)
				continue;
			if (userH.getReferenceThread() == null)
				continue;
			userH.getReferenceThread().cancelDatabases();
		}

	}

	public BizUserHistory getStadistic(long key) throws Exception {
		return (BizUserHistory) this.getStadistics().getElement(key);
	}

	public BizUserHistory getStadistic(String value) throws Exception {
		if (value == null)
			return null;
		String k;
		long key = -1;
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(value, '|');
		if (tk.hasMoreTokens()) {
			k = tk.nextToken();
			key = JTools.getLongNumberEmbedded(k.substring(k.indexOf('=') + 1));
		}
		if (key == -1)
			return null;
		return (BizUserHistory) this.getStadistics().getElement(key);
	}

	public void addStadistics(long key, BizUserHistory value) {
		this.getStadistics().addElement(key, value);
	}

	public JWebSkin getSkin() throws Exception {
		if (skin != null)
			return skin;

		String sSkin = null;
		BizWebUserProfile user = getUserProfile();
		if (user != null) {
			if (JWebActionFactory.isMobile())
				sSkin = user.getSkinMobileName();
			else
				sSkin = user.getSkinName();
		}
		if (sSkin == null || sSkin.equals("")) {
			if (JWebActionFactory.isMobile())
				sSkin = BizPssConfig.getPssConfig().getSkinMobileDefault();
			else
				sSkin = BizPssConfig.getPssConfig().getSkinDefault();
		}
		// sSkin = "skin_siti"; // hardcoded

		String sSkinDirAbsoluteURL = JPath.normalizePath(JPath.PssMainPath()) + "/www/ui/skins/" + sSkin;
		skin = new JWebSkin(sSkinDirAbsoluteURL, "pss.www.ui.skins." + sSkin + ".JLayoutGenerator");
		return skin;
	}

	public void setSkin(JWebSkin skin) {
		this.skin = skin;
	}

	public static void clearWaitMessage() throws Exception {
		JWebApplicationSession ss = getCurrent();
		if (ss == null)
			return;
		JWaitMessage msg = new JWaitMessage(null, false, false, -1, -1, "");
		ss.criticalSetWaitMessage(false, true, msg, null);
	}

	public static void sendWaitMessage(String icon, boolean progress, boolean acceptCancel, long porcentaje, long total,
			String mensaje) throws Exception {
		JWebApplicationSession ss = getCurrent();
		if (ss == null)
			return;
		JWaitMessage msg = new JWaitMessage(icon, progress, acceptCancel, porcentaje, total, mensaje);
		ss.criticalSetWaitMessage(false, false, msg, null);
	}

	public static boolean hasWaitMessage() throws Exception {
		JWebApplicationSession ss = getCurrent();
		if (ss == null)
			return false;
		return ss.criticalSetWaitMessage(true, false, null, null) != null;
	}

	public static JWaitMessage consumeWaitMessage() throws Exception {
		JWebApplicationSession ss = getCurrent();
		if (ss == null)
			return null;
		return ss.criticalSetWaitMessage(false, false, null, null);
	}

	public static JWaitMessage cancelWaitMessage(String idRequest) throws Exception {
		JWebApplicationSession ss = getCurrent();
		if (ss == null)
			return null;
		return ss.criticalSetWaitMessage(false, false, null, idRequest);
	}

	public Thread getLastThread() {
		if (lastThread == null)
			return null;
		return lastThread.get();
	}

	public void setLastThread(Thread lastThread) {
		this.lastThread = new WeakReference<Thread>(lastThread);
	}

	private boolean cancelLastThread() throws Exception {
		Thread t = getLastThread();
		if (t == null)
			return false;
		t.interrupt();
		return true;
	}

	private synchronized JWaitMessage criticalSetWaitMessage(boolean check, boolean consume, JWaitMessage msg,
			String cancelRequest) throws Exception {
		if (cancelRequest != null) {
			if (lastMessage != null) {
				if (lastMessage.getWait_idrequest().equals(cancelRequest)) {
					lastMessage.setCancel(true);
					cancelLastThread();
				}
			}
			return lastMessage;
		}
		if (check)
			return lastMessage;
		if (msg != null) {
			if (lastMessage != null && lastMessage.cancel
					&& lastMessage.getWait_idrequest().equals(msg.getWait_idrequest())) {
				cancelLastThread();
				throw new Exception(BizUsuario.getMessage("Cancelado por el usuario", null));
			}
		}
		JWaitMessage msgAnt = lastMessage;
		lastMessage = consume || msg != null ? msg : lastMessage;
		if (msg != null)
			setLastThread(Thread.currentThread());
		return msgAnt;
	}

	public String getIpAddress() {
		return sIpAddress;
	}

	public void setIpAddress(String sIp) {
		this.sIpAddress = sIp;
	}

	public boolean isCacheInDisk() {
		return (storeKey != null);
	}

	String storeKey;

	public boolean cacheSession() {
		try {
//			if (!BizPssConfig.getPssConfig().useSessionDiskCache()) return;
			String filename = JPath.PssPathDataFiles() + "/cacheSessions/" + this.hashCode();
			String serialize = JTools.serialize(this);
			JTools.writeStringToFile(serialize, filename);
			this.storeKey = filename;
//			this.oHistoryManager = null;
//			this.oRegisteredObjects = null;
//			this.oRegisteredRequest = null;
			this.stadistics = null;
		} catch (Exception e) {
			PssLogger.logError(e);
			return false;
		}
		return true;
	}

	public void retrieveSession() {
		try {
			if (storeKey == null)
				return;
			synchronized (storeKey) {
				if (storeKey == null)
					return;
				String filename = this.storeKey;
				PssLogger.logInfo("session, trying to regenerate: " + getId() + " file:" + this.storeKey);

				String serialize = JTools.readStringFromFile(filename);
				JWebApplicationSession session = (JWebApplicationSession) JTools.unserialize(serialize);// serialize);
				this.storeKey = null;
//				this.oNameDictionary = session.getNameDictionary();
//				this.oHistoryManager = session.getHistoryManager();
//				this.oRegisteredObjects = session.getRegisteredObjects();
				PssLogger.logInfo("Session regenerated: " + getId());
				JTools.DeleteFile(filename);
			}

		} catch (Exception e) {
			PssLogger.logError(e);

		}
	}

}