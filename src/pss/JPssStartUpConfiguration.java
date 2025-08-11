package pss;

/**
 * <p>
 * A configuration of options Pss was started with.<br>
 * There must be registered instances of this class for each session started
 * with Pss.<br>
 * There is a desktop instance, which applies to the session created when Pss
 * runs in desktop mode, that is, using the window system of AWT and Swing.<br>
 * There might be also registered instances for any session started from the
 * Web.<br>
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Gilbarko Latin America</p>
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.actions.BizAction;

public class JPssStartUpConfiguration {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// STATIC VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////
	private static JMap<String, JPssStartUpConfiguration> INSTANCES_MAP;
	private static final String DESKTOP_INSTANCE_ID="Pss_DESKTOP";

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////
	private boolean withDesktop=true;
	private boolean withEmbeddableDesktop=true;
	private boolean withLogin=true;
	private boolean withDatabase=true;
	private boolean withDispatcher=true;
	@SuppressWarnings("unchecked")
	private Class startUpWinClass;
	private BizAction startUpAction;
	private String startUpUserId;
	private String startUpPassword;

	// ////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//
	// ////////////////////////////////////////////////////////////////////////////
	protected JPssStartUpConfiguration() {
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////
	//
	// methods for getting and registering instances
	//
	public static JPssStartUpConfiguration getDesktopInstance() {
		return getInstance(DESKTOP_INSTANCE_ID);
	}

	public static JPssStartUpConfiguration getInstance(String zInstanceId) {
		return getInstances().getElement(zInstanceId);
	}

	public static void registerInstance(String zInstanceId, JPssStartUpConfiguration zInstance) {
		getInstances().addElement(zInstanceId, zInstance);
	}

	protected static void registerDesktopInstance(JPssStartUpConfiguration zInstance) {
		getInstances().addElement(DESKTOP_INSTANCE_ID, zInstance);
	}

	@SuppressWarnings("unchecked")
	public static JPssStartUpConfiguration createInstance(boolean withDesktop, boolean withLogin, boolean withDatabase, boolean withDispatcher, Class startUpWinClass, BizAction startUpAction, String startUpUserId, String startUpPassword) {
		JPssStartUpConfiguration conf=new JPssStartUpConfiguration();
		conf.withDesktop=withDesktop;
		conf.withLogin=withLogin;
		conf.withDatabase=withDatabase;
		conf.withDispatcher=withDispatcher;
		conf.startUpWinClass=startUpWinClass;
		conf.startUpAction=startUpAction;
		conf.startUpUserId=startUpUserId;
		conf.startUpPassword=startUpPassword;
		return conf;
	}

	@SuppressWarnings("unchecked")
	public static JPssStartUpConfiguration createInstance(boolean withDesktop, boolean withEmbeddableDesktop, boolean withLogin, boolean withDatabase, boolean withDispatcher, Class startUpWinClass, BizAction startUpAction, String startUpUserId, String startUpPassword) {
		JPssStartUpConfiguration conf=createInstance(withDesktop, withLogin, withDatabase, withDispatcher, startUpWinClass, startUpAction, startUpUserId, startUpPassword);
		conf.withEmbeddableDesktop=withEmbeddableDesktop;
		return conf;
	}

	private static JMap<String, JPssStartUpConfiguration> getInstances() {
		if (INSTANCES_MAP==null) {
			INSTANCES_MAP=JCollectionFactory.createMap();
		}
		return INSTANCES_MAP;
	}

	//
	// methods for asking for start up configuration options
	//
	/**
	 * Answers the class of the JWin Pss was started with, or null if it was started loading Core module.<br>
	 * 
	 * @return the class of the JWin Pss was started with
	 */
	@SuppressWarnings("unchecked")
	public Class getStartUpWinClass() {
		return startUpWinClass;
	}

	/**
	 * Answers the action of the JWin Pss was started with, or null if it was started loading Core module.<br>
	 * 
	 * @return the action of the JWin Pss was started with
	 */
	public BizAction getStartUpAction() {
		return startUpAction;
	}

	/**
	 * Answers whether Pss was started connecting to a database or not.
	 */
	public boolean isWithDatabase() {
		return withDatabase;
	}

	/**
	 * Answers whether Pss was started connecting to a database or not.
	 */
	public boolean isWithDispatcher() {
		return withDispatcher;
	}

	/**
	 * Answers whether Pss was with a Pss Desktop or not.
	 */
	public boolean isWithDesktop() {
		return withDesktop;
	}

	/**
	 * Answers whether Pss was set start with an embeddable desktop, for a specific application.<br>
	 * Note that this is not a command line option, but a setting an application does.
	 */
	public boolean isWithEmbeddableDesktop() {
		return withEmbeddableDesktop;
	}

	/**
	 * Sets whether Pss will start with an embeddable desktop, for a specific application.<br>
	 * Note that this is not a command line option, but a setting an application does.
	 */
	public void setWithEmbeddableDesktop(boolean zIsWithEmbeddableDesktop) {
		withEmbeddableDesktop=zIsWithEmbeddableDesktop;
	}

	/**
	 * Answers whether Pss was started with a login dialog to the user or if it was started logging in specifying user and password in the command line.
	 */
	public boolean isWithLogin() {
		return withLogin;
	}

	/**
	 * Answers the user id Pss was started with, specified in the command line. Answers null if it was started with login dialog.
	 */
	public String getStartUpUserId() {
		return startUpUserId;
	}

	/**
	 * Answers the user password Pss was started with, specified in the command line. Answers null if it was started with login dialog.
	 */
	public String getStartUpPassword() {
		return startUpPassword;
	}

	protected void setWithLogin(boolean withLogin) {
		this.withLogin=withLogin;
	}

	protected void setWithDatabase(boolean withDatabase) {
		this.withDatabase=withDatabase;
	}

	@SuppressWarnings("unchecked")
	protected void setStartUpWinClass(Class startUpWinClass) {
		this.startUpWinClass=startUpWinClass;
	}

	protected void setWithDesktop(boolean withDesktop) {
		this.withDesktop=withDesktop;
	}

	protected void setStartUpAction(BizAction startUpAction) {
		this.startUpAction=startUpAction;
	}

	public void setStartUpPassword(String startUpPassword) {
		this.startUpPassword=startUpPassword;
	}

	public void setStartUpUserId(String startUpUserId) {
		this.startUpUserId=startUpUserId;
	}

}
