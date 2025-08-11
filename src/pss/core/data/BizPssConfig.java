package pss.core.data;

import java.io.File;

import pss.JPath;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.data.files.JIniFile;
import pss.core.data.files.JStreamFile;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.tools.JEncryptation;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class BizPssConfig {

	public final static String LOCK_TIME_OUT_SECONDS = "LOCK_TIME_OUT_SECONDS";
	public final static String ANTI_BLOCKING_TIME_OUT_SECONDS = "ANTI_BLOCKING_TIME_OUT_SECONDS";

	private static String databaseNameDefaultForAllThread;

	public static void setDatabaseNameDefaultForAllThread(String databaseNameDefaultForAllThread) {
		BizPssConfig.databaseNameDefaultForAllThread = databaseNameDefaultForAllThread;
	}

	public JIniFile oIni;
	private JMap<String, String> hData = null;
	private String appURLTotal;
	private String appURL;
	private static BizPssConfig oPssConfig = null;

	public static final String SITEMASTER = "sitemaster";

	public static void resetPssConfig() {
		oPssConfig = null;
	}

	public static String jarSignerDir() throws Exception {
		return getPssConfig().getCachedStrictValue("GENERAL", "JARSIGNER_DIR", "/Program Files/Java/jdk1.7.0_02/bin");
	}

	public String getLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_LOGIN", "Abogado");
	}

	public String appType() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "");
		if (secc.isEmpty())
			return "";
		String val = this.getCachedStrictValue(secc, "APP_TYPE", "");
		return val;
	}

	private synchronized void setStrictProperty(String section, String sPar, String sVal) {
		if (hData == null)
			hData = JCollectionFactory.createMap();
		hData.addElement(section + "_" + sPar, sVal);
	}

	private String getStrictProperty(String section, String sPar) {
		if (hData == null)
			return null;
		return hData.getElement(section + "_" + sPar);
	}

	private synchronized void setProperty(String sPar, String sVal) {
		if (hData == null)
			hData = JCollectionFactory.createMap();
		hData.addElement(sPar, sVal);
	}

	private String getProperty(String sPar) {
		if (hData == null)
			return null;
		return hData.getElement(sPar);
	}

	public static BizPssConfig getPssConfig() throws Exception {
		if (oPssConfig == null)
			oPssConfig = new BizPssConfig();
		return oPssConfig;
	}

	private BizPssConfig() throws Exception {
	}

	private JIniFile findPssIni() throws Exception {
//		JIniFile ini= this.findPssIni("pss_ok.ini");
//		if (ini!=null) return ini; // para que dos versiones compartan el pssData
		return this.findPssIni("pss.ini");
	}

	private JIniFile findPssIni(String name) throws Exception {
		/* Se pasa el Pss.ini de Pss a PssData */
		File oDir = new File(JPath.PssPathData());
		if (!oDir.isDirectory())
			oDir.mkdirs();
		File PssIni = new File(JPath.PssMainPath() + "/" + name);
		File PssDataIni = new File(JPath.PssPathData() + "/" + name);
		if (!PssDataIni.exists() && !JPath.PssMainPath().equals(JPath.PssPathData())) {
			if (!PssIni.exists())
				return null;// JExcepcion.SendError("Error leyendo pss.ini");
			JTools.MoveFile(JPath.PssMainPath() + "/" + name, JPath.PssPathData() + "/" + name);
		}
		if (PssIni.exists())
			PssIni.delete();
		return new JIniFile(JPath.PssPathData() + "/" + name);
	}

	/**
	 * Devuelve un parametro del ini, el mismo se cachea. Si no se encuentra en la
	 * seccion solicitada se busca en GENERAL
	 * 
	 * @param sSeccion
	 * @param sParametro
	 * @return
	 * @throws Exception
	 */
	public String getCachedValue(String sSeccion, String sParametro) throws Exception {
		return getCachedValue(sSeccion, sParametro, null, true);
	}

	/**
	 * Devuelve un parametro del ini, el mismo se cachea. Si no se encuentra en la
	 * seccion solicitada se busca en GENERAL
	 * 
	 * @param sSeccion
	 * @param sParametro
	 * @param sDefault
	 * @return
	 * @throws Exception
	 */
	public String getCachedValue(String sSeccion, String sParametro, String sDefault) throws Exception {
		return getCachedValue(sSeccion, sParametro, sDefault, true);
	}

	private String getCachedValue(String sSeccion, String sParametro, String sDefault, boolean exc) throws Exception {
		String value = this.getProperty(sParametro);
		if (value == null)
			value = this.getValueInSection((sSeccion == null ? "" : sSeccion), sParametro);
		if (value == null)
			value = sDefault;
		if (value == null) {
			if (exc)
				JExcepcion.SendError("No se encontró el parámetro^" + " " + sParametro);
			return null;
		}
		this.setProperty(sParametro, value);
		return value;
	}

	/**
	 * Devuelve un parametro del ini, el mismo se cachea teniendo en cuenta la
	 * seccion
	 * 
	 * @param sSeccion
	 * @param sParametro
	 * @return
	 * @throws Exception
	 */
	public String getCachedStrictValue(String sSeccion, String sParametro) throws Exception {
		return getCachedStrictValue(sSeccion, sParametro, null, true);
	}

	/**
	 * Devuelve un parametro del ini, el mismo se cachea teniendo en cuenta la
	 * seccion
	 * 
	 * @param sSeccion
	 * @param sParametro
	 * @param sDefault
	 * @return
	 * @throws Exception
	 */
	public String getCachedStrictValue(String sSeccion, String sParametro, String sDefault) throws Exception {
		return getCachedStrictValue(sSeccion, sParametro, sDefault, true);
	}

	private String getCachedStrictValue(String sSeccion, String sParametro, String sDefault, boolean exc)
			throws Exception {
		String value = this.getStrictProperty(sSeccion, sParametro);
		if (value == null)
			value = this.getValueInSection((sSeccion == null ? "" : sSeccion), sParametro);
		if (value == null)
			value = sDefault;
		if (value == null) {
			if (exc)
				JExcepcion.SendError("No se encontró el parámetro^" + " " + sParametro);
			return null;
		}
		this.setStrictProperty(sSeccion, sParametro, value);
		return value;
	}

	private JIniFile getIniFile() throws Exception {
		if (oIni != null)
			return oIni;
		oIni = findPssIni();
		return oIni;
	}

	private String getValueInSection(String sSeccion, String sParametro) throws Exception {
		String valor = getIniFile().GetParamValue(sSeccion, sParametro);
		if (valor == null)
			valor = getIniFile().GetParamValue("GENERAL", sParametro);
		return valor;
	}

	public static String GetDBid() throws Exception {
		return getPssConfig().getDBid();
	}

	private String getDBid() throws Exception {
		String dbid = this.getCachedStrictValue(JBDatos.GetBases().getCurrentDatabase(), "DB_ID",
				this.getCachedValue(JBDatos.GetBases().getCurrentDatabase(), "NODO_LOCAL", "000"));
		return dbid;
	}

	public static String GetDefaultLanguage() throws Exception {
		return getPssConfig().getDefaultLanguage();
	}

	public boolean getCheckIPLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_CHECKIPLOGIN", "N").equals("S");
	}

	public boolean getRegisterLogOut() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_REGISTERLOGOUT", "N").equals("N");
	}

	private String getDefaultLanguage() throws Exception {
		return this.getCachedValue("GENERAL", "DEFAULT_LANGUAGE", JLanguage.Pss_DEFAULT_LANGUAGE);
	}

	public String getDefaulCountry() throws Exception {
		return this.getCachedValue("GENERAL", "DEFAULT_COUNTRY", "AR");
	}

	public static long getRegisterMemoryUssage() {
		try {
			return JTools.getLongFirstNumberEmbedded(
					BizPssConfig.getPssConfig().getCachedValue("GENERAL", "LOG_MEMORY_MS", "0"));
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean getLogMemoryUssage() {
		try {
			return !BizPssConfig.getPssConfig().getCachedValue("GENERAL", "LOG_MEMORY", "N").equals("N");
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean useBrowserLanguage() {
		try {
			return getPssConfig().internalUseBrowserLanguage();
		} catch (Exception e) {
			return true;
		}
	}

	public boolean internalUseBrowserLanguage() throws Exception {
		return this.getCachedValue("GENERAL", "USE_BROWSER_LANGUAGE", "S").equals("S");
	}

	public String getImportExportFilesLocal() throws Exception {
		return this.getCachedValue("GENERAL", "USE_IMPORT_EXPORT_PATH_LOCAL", "");
	}

	public String getImportExportFilesRemote() throws Exception {
		return this.getCachedValue("GENERAL", "USE_IMPORT_EXPORT_PATH_REMOTE", "");
	}

	public boolean getImportExportCopy() throws Exception {
		return !this.getCachedValue("GENERAL", "USE_IMPORT_EXPORT_COPY", "Y").equals("N");
	}

	public String getInputFiles() throws Exception {
		return this.getCachedValue("GENERAL", "INPUT_FILE", "");
	}

	public String getTempFiles() throws Exception {
		return this.getCachedValue("GENERAL", "TEMPFILES", "");
	}

	public String getFTPFiles() throws Exception {
		return this.getCachedValue("GENERAL", "FTP", JPath.PssPathInputOriginal());
	}

	public static boolean activeDebug(String mode) {
		try {
			return !(getPssConfig().getCachedStrictValue("DEBUG", mode, mode.equals("LOG_ERROR") ? "S" : "N")
					.equalsIgnoreCase("N"));
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return true;
	}

	public static String getLog4J() {
		try {
			return (getPssConfig().getCachedStrictValue("GENERAL", "LOG4J", "R"));
		} catch (Exception e) {

		}
		return "R";
	}

	public static boolean OnlyOneSessionPerUser() {
		try {
			return getPssConfig().getCachedStrictValue("GENERAL", "ONLY_ONE_SESSION_PER_USER", "S")
					.equalsIgnoreCase("S");
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return true;
	}

	public boolean isAcceptNullInBoolean() throws Exception {
		return this.getCachedValue("GENERAL", "ACCEPT_NULL_IN_BOOLEAN", "N").equals("S");
	}

	public boolean isUseImportExportDB() throws Exception {
		return this.getCachedValue("GENERAL", "USE_IMPORT_EXPORT_DB", "S").equals("S");
	}

	public boolean isUseImportExportFile() throws Exception {
		return this.getCachedValue("GENERAL", "USE_IMPORT_EXPORT_DB", "S").equals("F");
	}

	private String getLocalIDLicence() throws Exception {
		return this.getCachedValue("GENERAL", "LICENSE", "1");
	}

	public static int GetMaximumConcurrentSessions() {
		try {
//			String value = getPssConfig().getPrivateData("MAXIMUM_CONCURRENT_SESSIONS");
//			if (value!=null) return Integer.parseInt(value);
			return Integer
					.parseInt(getPssConfig().getCachedStrictValue("GENERAL", "MAXIMUM_CONCURRENT_SESSIONS", "1000"));
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return 1000;
	}
	////

	public String GetDriverName() throws Exception {
		return this.getCachedStrictValue(JBDatos.GetBases().getCurrentDatabase(), "DRIVER_NAME");
	}

	public String getDBModel() throws Exception {
		return this.getCachedStrictValue(JBDatos.GetBases().getCurrentDatabase(), "MODEL");
	}

	public String GetConnectionName() throws Exception {
		return this.getCachedStrictValue(JBDatos.GetBases().getCurrentDatabase(), "CONNECTION_NAME");
	}

	public String getSiteMasterHost() throws Exception {
		return this.getCachedValue(JBDatos.GetBases().getCurrentDatabase(), "SITEMASTER", "");
	}

	public int GetProcessTxsSleep() throws Exception {
		String sSleep = this.getCachedValue(JBDatos.GetBases().getCurrentDatabase(), "SLEEP", "0");
		if (JTools.isNumber(sSleep)) {
			return Integer.parseInt(sSleep);
		} else
			return 0;
	}

	public boolean hasGoogleMaps() {
		try {
			return this.getCachedValue("GOOGLE_MAPS", "KEY", "").equalsIgnoreCase("") == false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean hasMercadoPagoPay() {
		try {
			return !this.getCachedStrictValue("MERCADOPAGO", "ACTIVE_PAY", "N").equalsIgnoreCase("N");
		} catch (Exception e) {
			return false;
		}
	}

	public String getMercadoPagoPayPublicKey() {
		try {
			return this.getCachedStrictValue("MERCADOPAGO", "PUBLIC_KEY_PAY", "YOUR_PUBLIC_KEY");
		} catch (Exception e) {
			return "YOUR_PUBLIC_KEY";
		}
	}

	public boolean hasGooglePay() {
		try {
			return !this.getCachedStrictValue("GOOGLE_PAY", "ACTIVE_PAY", "N").equalsIgnoreCase("N");
		} catch (Exception e) {
			return false;
		}
	}

	public String getGooglePayEnvironment() {
		try {
			return this.getCachedValue("GOOGLE_PAY", "ENVIRONMENT", "TEST");
		} catch (Exception e) {
			return "TEST";
		}
	}

	public String getGooglePayGateway() {
		try {
			return this.getCachedValue("GOOGLE_PAY", "GATEWAY", "allpayments");
		} catch (Exception e) {
			return "allpayments";
		}

	}
	public String getGoogleCaptcha() {
		try {
			return this.getCachedValue("GOOGLE", "CAPTCHA_KEY", "");
		} catch (Exception e) {
			return "";
		}

	}
	public String getGoogleCaptchaServer() {
		try {
			return this.getCachedValue("GOOGLE", "CAPTCHA_SERVER_KEY", "");
		} catch (Exception e) {
			return "";
		}

	}
	public String getGooglePayGatewayMerchantId() {
		try {
			return this.getCachedValue("GOOGLE_PAY", "GATEWAYMERCHANTID", "exampleGatewayMerchantId");
		} catch (Exception e) {
			return "exampleGatewayMerchantId";
		}
	}

	public boolean hasHelpEditor() {
		try {
			return this.getCachedValue("HELP", "EDITOR_AYUDA", "N").equalsIgnoreCase("S");
		} catch (Exception e) {
			return false;
		}
	}

	public String GetDatabaseType() throws Exception {
		return this.getCachedStrictValue("GENERAL", "DATABASE_TYPE");
	}

	public String GetDatabaseNameDefault() throws Exception {
		if (databaseNameDefaultForAllThread != null)
			return databaseNameDefaultForAllThread;
		return this.getCachedStrictValue("GENERAL", JBDato.DATABASE_DEFAULT);
	}

//	public boolean canAutoCreateGuest() throws Exception {
//		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION","SITI");
//		return this.getCachedValue(secc, "APP_AUTOCREATE_GUEST", "N").equalsIgnoreCase("S");
//	}

	public String getAutoLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_AUTOLOGIN", "");
	}

	public String getPublicWebLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_PUBLICWEBLOGIN", "WEBUSR");
	}

	public String getLoginTraceClass() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_LOGINTRACE", "");
	}

	public String getInitializedData() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_DATA", "default");
	}

	public boolean useCustomColumns() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !this.getCachedValue(secc, "CUSTOM_COLUMNS", "S").equals("N");
	}

	public boolean acceptLoginWithMail() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !this.getCachedValue(secc, "ACCEPT_LOGINWITHMAIL", "S").equals("N");
	}

	public boolean isWinTitleDebug() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "WIN_DEBUG", "0");
		return secc.equals("1");
	}

	public String getUseCaptcha() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "CAPTCHA", "N");
	}

	public boolean getAdmitirRegistracion() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !this.getCachedValue(secc, "REGISTER_USERS", "N").equals("N");
	}

	public boolean askPersistentLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !this.getCachedValue(secc, "PERSISTENT_LOGIN", "S").equals("N");
	}

	public boolean getAdmitirRecuperacionClave() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !this.getCachedValue(secc, "RECOVERY_PASS", "N").equals("N");
	}

	public boolean getUseDatabase() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "DATABASE", "S").equals("S");
	}

	public String getVersion() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_VERSION");
	}

	public String getDateVersion() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_DATE_VERSION");
	}

	public String getTitle() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_TITLE");
	}

	public String getNameDatabases() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "APP_NAME_DBS", "Base");
	}

	static String versionJS = null;

	public static String getVersionJS() throws Exception {
		if (versionJS != null)
			return versionJS;

		if (JPath.isJar()) {
			return versionJS = "" + JTools.getLastModified(new File(JPath.PssPathJAR())).getTime();
		}

		String secc = getPssConfig().getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return getPssConfig().getCachedValue(secc, "APP_VERSION");
	}

	public String classLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedStrictValue(secc, "APP_LOGIN_CLASS", "pss.www.ui.views.JLoginView");
		// pss.www.ui.views.JExtendedLoginView
		return val;
	}
	public String classRegisterLogin() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedStrictValue(secc, "APP_REGISTER_CLASS", "pss.www.ui.views.JRegistrationView");
		// pss.www.ui.views.JExtendedLoginView
		return val;
	}

	
	
	public String getBusinessDefault() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedStrictValue(secc, "APP_BUSINESS", "XXXXXX");
		if (val.equals("XXXXXX"))
			val = this.getCachedStrictValue("GENERAL", "APP_BUSINESS", "DEFAULT");
		return val;
	}

	public int getTimeoutFreeConections() {
		try {
			String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
			String val = this.getCachedStrictValue(secc, "TIMEOUT_FREE_CONNECTION", "10");
			return Integer.parseInt(val);
		} catch (Exception e) {
			PssLogger.logError(e);
			return 30;
		}
	}

	public String getAppURLPreview() throws Exception {
		if (appURL != null)
			return appURL;
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedStrictValue(secc, "APP_URL_PREVIEW", "http://127.0.0.1:8080/" + getAppURLPrefix());
		return val;
	}

	public String getAppURLPrefix() throws Exception {
		if (appURL != null)
			return appURL;
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedStrictValue(secc, "APP_URL", "astor");
		return val;
	}

	public String getTreeModel() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		String val = this.getCachedValue(secc, "TREE", "S");
		return val;
	}

	public boolean withTree() throws Exception {
		return getTreeModel().equalsIgnoreCase("S");
	}

	public long getPublicityCampaign() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");

		String val = this.getCachedValue(secc, "PUBLICITY", "-1");
		return Long.parseLong(val);
	}

	public String getSkinSiteMasterDefault() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "SKIN_SITEMASTER_DEFAULT", "blue");
	}

	public String getSkinDefault() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "SKIN_DEFAULT", "skin_default");
	}

	public String getSkinMobileDefault() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "SKINMOBILE_DEFAULT", "skin_mobile");
	}

	public String getLogo() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "LOGO", "company.logo.gif");
	}

	public String getLink() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "LINK", "http://www.pentaware.com.ar");
	}

	public String getManufacturerLogo() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return this.getCachedValue(secc, "MANUFACTURER_LOGO", "manufacturer.banner.gif");
	}

	public String getClusteringLocal() throws Exception {
		String value = getIniFile().GetParamValue("CLUSTERING", "LOCAL");
		return value == null ? "" : value;
	}

	public String getClusteringAddress(String id) throws Exception {
		String value = getIniFile().GetParamValue("CLUSTERING", id);
		return value == null ? "" : value;
	}

	public long getDepurateDays() throws Exception {
		String value = getIniFile().GetParamValue("DEPURATE", "DAYS", "10");
		return JTools.getLongNumberEmbedded(value);
	}

	public String getDepurateFiles() throws Exception {
		String value = getIniFile().GetParamValue("DEPURATE", "FILES", "tempfile");
		return value;
	}

	public boolean activeDepurateFiles() throws Exception {
		boolean value = !getIniFile().GetParamValue("DEPURATE", "ACTIVE", "N").equals("N");
		return value;
	}

	public static boolean iAmSiteMaster() throws Exception {
		String siteMaster = getPssConfig().getSiteMasterHost();
		if (siteMaster.equalsIgnoreCase(JTools.GetHostName()))
			return true;
		if (siteMaster.equalsIgnoreCase(JTools.GetHost()))
			return true;
		return false;
	}

	public void setAppURL(String appURL) {
		if (this.appURL != null)
			return;
		String url = appURL.substring(1, appURL.indexOf("/", 1));
		this.appURL = url;
	}

	public void setAppURLTotal(String zAppURL) {
		if (zAppURL == null)
			return;
		if (this.appURLTotal != null && !this.appURLTotal.equals(""))
			return;
		String url = zAppURL;
		this.appURLTotal = url;
	}

	public String getUrlTotal() throws Exception {
		return this.appURLTotal;
	}

	public String getPrivateData(String data) throws Exception {
		JStreamFile oFile = null;
		try {
			String key = "98765779ABBCDFF3";

			JEncryptation file = new JEncryptation();
			file.HDesSetKey(key);

			String sFilename = "private.dat";

			String sFile = JBaseJDBC.class.getResource("").getPath() + sFilename;
			oFile = new JStreamFile();
			oFile.Open(sFile);
			String sLine = "";
			while ((sLine = oFile.ReadLine()) == null) {

				JStringTokenizer oTok = JCollectionFactory.createStringTokenizer(sLine, '=');
				if (oTok.countTokens() != 2)
					JExcepcion.SendError("Error al datos");
				String keyValue = oTok.nextToken();
				String value = oTok.nextToken();
				if (keyValue.equalsIgnoreCase(data)) {
					oFile.Close();
					oFile = null;
					return value;
				}
			}

			oFile.Close();
			oFile = null;
			return null;

		} catch (Exception e) {
			PssLogger.logError(e, "Error al buscar datos privados");
			return null;
		} finally {
			if (oFile != null && oFile.isFileOpen())
				oFile.Close();
		}
	}

	public boolean isOnlyOneSessionByBrowser() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !(this.getCachedValue(secc, "APP_ONLY_ONE_SESSION_BY_BROWSER", "N").equals("N"));
	}

	public boolean useSessionDiskCache() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return !(this.getCachedValue(secc, "APP_SESSION_DISK_CACHE", "N").equals("N"));
	}

	public int getSessionTimeout() throws Exception {
		String secc = this.getCachedStrictValue("GENERAL", "APPLICATION", "SITI");
		return Integer.parseInt(this.getCachedValue(secc, "APP_SESSION_TIMEOUT", "60"));
	}

	public String getClusteringByCompany(String company) throws Exception {
		return getIniFile().GetParamValue("CLUSTERING", company);
	}
    // ---------------------------------------------------------------------
    // Cache replication configuration

    private static String getPropEnvOrIni(String folder,String key, String def) {
        String v = System.getProperty(key);
        if (v == null) v = System.getenv(key.replace('.', '_').toUpperCase());
        if (v == null) {
            try {
                v = BizPssConfig.getPssConfig().getCachedValue(folder, key, null);
            } catch (Exception e) {
                v = null;
            }
        }
        return v != null ? v : def;
    }

    public static boolean isCacheReplicationEnabled() {
        return Boolean.parseBoolean(getPropEnvOrIni("CACHE","pss.cache.replication.enabled", "false"));
    }

    public static String getMemcachedHost() {
        return getPropEnvOrIni("CACHE","pss.cache.memcached.host", "localhost");
    }

    public static int getMemcachedPort() {
        return Integer.parseInt(getPropEnvOrIni("CACHE","pss.cache.memcached.port", "11211"));
    }

    public static int getMemcachedDefaultTtlSeconds() {
        return Integer.parseInt(getPropEnvOrIni("CACHE","pss.cache.memcached.ttl.default.seconds", "3600"));
    }

    public static int getMemcachedTimeoutMs() {
        return Integer.parseInt(getPropEnvOrIni("CACHE","pss.cache.memcached.timeout.ms", "100"));
    }

}
