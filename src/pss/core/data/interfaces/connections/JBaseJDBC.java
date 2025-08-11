package pss.core.data.interfaces.connections;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pss.core.data.BizPssConfig;
import pss.core.data.connectionPool.JDBConnectionPools;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.sentences.JRegJDBC;
import pss.core.tools.JEncryptation;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public abstract class JBaseJDBC extends JBDato {

	public final static String ALIAS = "ALIAS";
	public final static String DRIVER_NAME = "DRIVER_NAME";
	public final static String CONNECTION_URL = "CONNECTION_URL";
	public final static String CONNECTION_INTERFACE = "CONNECTION_INTERFACE";
	public final static String DRIVER_INTERFACE = "DRIVER_INTERFACE";
	public final static String USER = "USER";
	public final static String PASSWORD = "PASSWORD";
	public final static String AUTHENTICATION = "AUTHENTICATION";
	public final static String USERSERVICE = "USER_SERVICE";

	public final static long LOCK_TIME_OUT_SECONDS_DEFAULT = 10;
	public final static long ANTI_BLOCKING_TIME_OUT_SECONDS_DEFAULT = 300;

	public final static int ADMIN_USER = 0;
	public final static int COMMON_USER = 1;

	public final static String CODE_CONN_UNESTABLISHED = "01000";
	public final static String CODE_CONN_BROKEN = "08S01";
	public final static String CODE_CONN_BROKEN_JTDS = "HY010";
	public final static String CODE_CONN_UNABLE_TO_ESTABLISH = "08001";
	public final static String CODE_CONN_REJECTED = "08004";

	// DBConnectionPool parameter
	public final static String MAX_CONNECTIONS = "MAX_CONNECTIONS";
	public final static String AUTH_SYSOP = "SYSOP";

	public final static String DRIVER_JDATA = "jdataconnect";
	public final static String DRIVER_JSQL = "jsqlconnect";
	public final static String DRIVER_MSSQL = "microsoft";
	public final static String DRIVER_ORACLE = "oracle";

	protected String sAlias = "";
	protected String sDriverName = "";
	protected String sConnectionUrl = "";
	protected String sUsername = "";
	protected String sPassword = "";
	protected String sAuth = "";
	protected String sConnectionInterface = "";
	protected String sDriverInterface = "";
	protected String sUserService = "";
	// protected String sBaseDatos; // Nombre de la base
	protected long lLockTimeoutSeconds = 0;
	protected long lAntiBlockingSystemTimeoutSeconds = 0;
	protected JDBConnectionPools oConnectionPools;
	protected Connection connection;
	protected JPssDriver oDriverImpl;
	protected JMap<String, Statement> vTransactionCursors = null;
	private boolean bReportDatabase = false;

	public int countOpenCursors() {
		if (vTransactionCursors == null)
			return 0;
		return vTransactionCursors.size();
	}

	public void removeTransactionCursor(Statement st) {
		if (vTransactionCursors != null)
			vTransactionCursors.removeElement(st.toString());
	}

	/**
	 * Constructor
	 */
	public JBaseJDBC() throws Exception {
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Getter & Setter methods
	// ////////////////////////////////////////////////////////////////////////////

	@Override
	public Connection GetConnection() throws Exception {
		return connection;
	}

	@Override
	public String getConnectionURL() {
		return this.sConnectionUrl;
	}

	@Override
	public String GetDriverName() {
		return sDriverName;
	}

	@Override
	public String GetUsername() {
		return sUsername;
	}

	@Override
	public String getPassword() {
		return sPassword;
	}

	@Override
	public String getAuthenticationMode() {
		return sAuth;
	}

	protected String getPassoword() {
		return sPassword;
	}

	@Override
	public String getDriverInterface() {
		return this.sDriverInterface;
	}

	@Override
	public String getConnectionInterface() {
		return this.sConnectionInterface;
	}

	@Override
	public String getDatabaseName() {
		return this.oDriverImpl.getDatabaseName();
	}

	public JRegJDBC createRowImpl() throws Exception {
		return (JRegJDBC) Class.forName(this.getRowInterface()).newInstance();
	}

	public JPssDriver getDriver() {
		return this.oDriverImpl;
	}

	/**
	 * Obtiene el usuario que levanta el servicio JDataServer. Utilizado para
	 * conectarse a una base de datos. En caso que el usuario este vacío, se le
	 * asigna el default 'dbaccess'.
	 * 
	 * @return El nombre del usuario del servicio // TODO PASAR ESTO A
	 *         JDATACONNECT
	 */
	@Override
	public String GetUserService() {
		if (sUserService.equals("")) {
			sUserService = "dbaccess"; // setea el usuario default
		}
		return sUserService;
	}

	@Override
	public boolean isAuthenticationMode() {
		return sAuth.equalsIgnoreCase("Yes") || sAuth.equalsIgnoreCase("Y");
	}

	/**
	 * Synchronize the system and the database date time
	 */
	@Override
	public void synchronizeSystemAndDataBaseTime() throws Exception {
	}

	/**
	 * Seteo los parametros para la clase JDBC Se convirtió en algo más que set.
	 * Está cargando el driver y creando el pool. Lo que no hace es pedir
	 * conexiones... eso lo hará abrir()
	 */
	@Override
	public void asignParams(int zUserType) throws Exception {
		BizPssConfig PssIni = BizPssConfig.getPssConfig();

		// UserService contiene el usuario que levantará el servicio JDataConnect
		this.sUserService = PssIni.getCachedValue(this.GetName(), USERSERVICE, "");
		this.sAlias = PssIni.getCachedValue(this.GetName(), ALIAS, "");
		this.sAuth = PssIni.getCachedValue(this.GetName(), AUTHENTICATION, "");
		this.sDriverName = PssIni.getCachedStrictValue(this.GetName(), DRIVER_NAME);
		this.sConnectionUrl = PssIni.getCachedStrictValue(this.GetName(), CONNECTION_URL);
		// this.SetDatabaseType(zParam.GetValor(sBaseDatos, DATABASE_TYPE));
		this.sConnectionInterface = this.getConnectionInterface();
		this.sDriverInterface = PssIni.getCachedStrictValue(this.GetName(), DRIVER_INTERFACE);
		this.sUsername = PssIni.getCachedStrictValue(this.GetName(), USER, "");
		this.sPassword = PssIni.getCachedStrictValue(this.GetName(), PASSWORD, "");
		String sLockTimeoutSeconds = PssIni.getCachedValue(this.GetName(), BizPssConfig.LOCK_TIME_OUT_SECONDS, "0");
		if (!JTools.isNumber(sLockTimeoutSeconds))
			JExcepcion.SendError("Incorrect Lock Timeout Parameter");
		else
			this.lLockTimeoutSeconds = Long.parseLong(sLockTimeoutSeconds);

		String sAntiBlockingTimeoutSeconds = PssIni.getCachedValue(this.GetName(), BizPssConfig.ANTI_BLOCKING_TIME_OUT_SECONDS, String.valueOf(ANTI_BLOCKING_TIME_OUT_SECONDS_DEFAULT));
		if (!JTools.isNumber(sAntiBlockingTimeoutSeconds))
			JExcepcion.SendError("Incorrect Anti-Blocking Timeout Parameter");
		else
			this.lAntiBlockingSystemTimeoutSeconds = Long.parseLong(sAntiBlockingTimeoutSeconds);

		// Verifico si el tipo de conexion es autenticada
		if (!isAuthenticationMode() && this.sUsername.equals("") == true) {
			// seteo los datos del usuario y password de la base de datos
			if (this.isCommonUser(zUserType)) {
				this.setCommonUser();
			} else if (this.isAdminUser(zUserType)) {
				this.setAdminUser();
				// le cambio el nombre al tag de base para tener un nuevo
				// pool conectado con el usuario administrador de la base
				this.SetName(this.GetName() + "__ADMIN");
				this.asignMasterParams();

			}
		}

		this.oConnectionPools = JDBConnectionPools.getInstance();
		// Agregar el driver
		// oConnectionPools.addDriver(this.sDriverName, this.sConnectionInterface);

		// Agregar el pool
		int iMaxConnections = -1;

		String sMaxConnections = PssIni.getCachedValue(this.GetName(), MAX_CONNECTIONS, "-1");
		if (sMaxConnections == null || sMaxConnections.length() == 0 || !JTools.isNumber(sMaxConnections)) {
			sMaxConnections = "-1";
		}

		iMaxConnections = Integer.parseInt(sMaxConnections);

		this.oDriverImpl = this.createDriverImpl(this.sConnectionUrl, this.sUsername, this.sPassword, this.sDriverName, this.sConnectionInterface, this.sDriverInterface, this.lLockTimeoutSeconds);
		this.oConnectionPools.addPool(this.GetName(), iMaxConnections, this.oDriverImpl);

	}

	protected void asignMasterParams() throws Exception {
	}

	/**
	 * Creates the driver implementation
	 */
	protected JPssDriver createDriverImpl(String zUrl, String zUser, String zPassword, String zDriverName, String zConnectionInterface, String zDriverInterface, long zLockTimeoutSeconds) throws Exception {
		// check the paremeters
		if (!this.isCorrect(zUrl)) {
			JExcepcion.SendError("Pss Driver Manager: El parametro CONNECTION_URL no es correcto");
		}
		if (!this.isCorrect(zDriverName)) {
			JExcepcion.SendError("Pss Driver Manager: El parametro DRIVER_NAME no es correcto");
		}
		if (!this.isCorrect(zDriverInterface)) {
			JExcepcion.SendError("Pss Driver Manager: El parametro DRIVER_INTERFACE no es correcto");
		}
		if (zLockTimeoutSeconds == 0)
			zLockTimeoutSeconds = LOCK_TIME_OUT_SECONDS_DEFAULT;

		JPssDriver oDriverManager = (JPssDriver) Class.forName(zDriverInterface).newInstance();
		oDriverManager.initialize(zUrl, zUser, zPassword, zConnectionInterface, zDriverName, zLockTimeoutSeconds);

		return oDriverManager;
	}

	/**
	 * Ask if the parameter is not null or is distinct of empty string
	 */
	private boolean isCorrect(String zParameter) {
		return (zParameter != null && zParameter.length() != 0);
	}

	protected int timeoutValidConnection() throws Exception {
		return BizPssConfig.getPssConfig().getTimeoutFreeConections();
	}

	/**
	 * Abro la base para la clase JDBC
	 */
	@Override
	public synchronized void open() throws Exception {
		oConnectionPools = JDBConnectionPools.getInstance();
		PssLogger.logWait("try open connection " + this.GetName());

		// Le pide una conexión del pool zBaseDatos
		this.connection = oConnectionPools.getConnection(this.GetName(), timeoutValidConnection());
		if (this.connection == null)
			JExcepcion.SendError("No se pudo obtener una conexión a^ " + this.GetName());

		
		for (int i = 0; i < 5; i++) {
			try {
				if (this.isSQLServer()) {
					// JDebugPrint.logDebug("Base de Datos abierta: " + this.GetName());
					JBaseRegistro oRegistro = this.createRowInterface();
					oRegistro.ExecuteQuery("select @@spid spid");
					oRegistro.next();
					PssLogger.logWait("ConnectionSPID: " + oRegistro.CampoAsStr("spid"));
				}
				if (this.isPostGreSQL()) {
					JBaseRegistro oRegistro = this.createRowInterface();
					oRegistro.ExecuteQuery("SELECT * FROM pg_stat_activity ");
					oRegistro.next();
					if (i > 0)
						PssLogger.logDebug("Conexion Restablecida: " + this.GetName());
					break;
				}
				if (i > 0)
					PssLogger.logDebug("Conexion Restablecida: " + this.GetName());
				break;
			} catch (JConnectionBroken broken) {
				PssLogger.logDebug("Conexion Rota: " + this.GetName());
				this.connection.close();
				if (i == 4) {
					this.delete();
					throw broken;
				}
				this.connection = oConnectionPools.reConnection(this.GetName(), this.connection, timeoutValidConnection());
			}
		}
		
	
	}
	

	
	public void onConnectionBroken(Exception broken) throws Exception {
		PssLogger.logDebug("Conexion Rota: " + this.GetName());
		try { this.connection.close(); } catch (Exception ignored) {}
		this.connection = oConnectionPools.reConnection(this.GetName(), this.connection, timeoutValidConnection());
	}
	
	public void testConnection() throws Exception {
	}

	/**
	 * Cierro la base de datos
	 */
	@Override
	public synchronized void close() throws Exception {
		if (connection != null) {
//			PssLogger.logWait("try to close open cursors: " + this.GetName());
//			PssLogger.logWait("open cursors closed: " + (this.closeTransactionCursors() > 0));
			oConnectionPools.freeConnection(this.GetName(), connection);
			connection = null;
		}
		// Libera la instancia
		// oConnectionPools.release();
	}
	public synchronized void cancel() throws Exception {
		if (connection != null) {
			PssLogger.logWait("try to close open cursors: " + this.GetName());
			PssLogger.logWait("open cursors cancel: " + (this.cancelTransactionCursors() > 0));
			PssLogger.logWait("open cursors closed: " + (this.closeTransactionCursors() > 0));
			oConnectionPools.freeConnection(this.GetName(), connection);
			connection = null;
		}
		// Libera la instancia
		// oConnectionPools.release();
	}

	@Override
	public boolean isTransactionInProgress() throws Exception {
		return vTransactionCursors != null;
	}

	/**
	 * Comienzo una transaccion de Base de Datos
	 */
	@Override
	public void beginTransaction() throws Exception {
		if (vTransactionCursors != null)
			JExcepcion.SendError("Error: Transacción anidada");

		vTransactionCursors = JCollectionFactory.createMap();

		try {
			this.GetConnection().setAutoCommit(false);
		} catch (SQLException e) {
			this.throwConnectionBroken(e);
		}
	}

	/**
	 * Commit de una transaccion de Base de Datos
	 */

	public boolean isAutoCommit() {
		return false;
	}

	@Override
	public void commit() throws Exception {
		try {
			if (vTransactionCursors == null)
				JExcepcion.SendError("Commit fuera de transaccion");
			connection.commit();
			this.closeTransactionCursors();
		} catch (SQLException e) {
			this.throwConnectionBroken(e);
		}
	}

	/**
	 * @see pss.core.data.interfaces.connections.JBDato#clearAllTransactions()
	 */
	@Override
	public void clearAllTransactions() throws Exception {
		this.closeTransactionCursors();
	}

	/**
	 * Rollback de una transaccion de Base de Datos
	 */
	@Override
	public void rollback() throws Exception {
		try {
			if (vTransactionCursors == null)
				JExcepcion.SendError("Rollback fuera de transaccion");
			connection.rollback();
			closeTransactionCursors();
		} catch (SQLException e) {
			closeTransactionCursors();
			this.throwConnectionBroken(e);
		}
	}

	@Override
	protected void setCommonUser() throws Exception {
		JStringTokenizer oTok = this.readUserPasswd(JBaseJDBC.COMMON_USER);
		sUsername = oTok.nextToken();
		sPassword = oTok.nextToken();
	}

	@Override
	protected void setAdminUser() throws Exception {
		JStringTokenizer oTok = this.readUserPasswd(JBaseJDBC.ADMIN_USER);
		sUsername = oTok.nextToken();
		sPassword = oTok.nextToken();
	}

	/**
	 * Lee un archivo el usuario y clave de la base de datos
	 * 
	 * @return Un JTokenizer con usuario=clave.
	 */
	public JStringTokenizer readUserPasswd(int zUserType) throws Exception {
		try {
			// String key = new String("98765779ABBCDFF3");
			// key.toCharArray();
			String key = "98765779ABBCDFF3";

			JEncryptation password = new JEncryptation();
			password.HDesSetKey(key);

			String sFilename = null;
			if (zUserType == JBaseJDBC.COMMON_USER) {
				// sFilename = "auth.dat";
				sFilename = this.getCommonUserFileName();
			} else if (zUserType == JBaseJDBC.ADMIN_USER) {
				// sFilename = "authadm.dat";
				sFilename = this.getAdminUserFileName();
			} else {
				JExcepcion.SendError("Tipo de usuario incorrecto al leer del archivo usuario-clave");
			}
			
			
			String sLine = JTools.byteVectorToString(JTools.readResourceAsArrayByte( JBaseJDBC.class.getResource(sFilename)));

			String sData = password.DesEncryptString(JTools.HexStringToBin(sLine, sLine.length()));
			// DesEncriptacion

			JStringTokenizer oTok = JCollectionFactory.createStringTokenizer(sData, '=');
			if (oTok.countTokens() != 2)
				JExcepcion.SendError("Error al desencriptar usuario y clave de la base de datos");
			return oTok;

		} catch (Exception e) {
			String errMsg = "Error al desencriptar usuario y clave de la base de datos";
			PssLogger.logDebug(e, errMsg);
			JExcepcion.SendError(errMsg);
			return null;
		}
	}
	/**
	 * Devuelve el nombre de la Base de Datos del objeto Nota: este metodo es
	 * dependendiente del Driver //
	 */
	/*
	 * public String getDatabaseName() throws Exception { String sBeginMachine =
	 * this
	 * .GetConnectionName().substring(this.GetConnectionName().lastIndexOf("//") +
	 * 2); String sMachine = sBeginMachine.substring(0,
	 * sBeginMachine.indexOf("/")); String sBeginBase =
	 * sBeginMachine.substring(sBeginMachine.indexOf("/") + 1); int iUseCursors =
	 * sBeginBase.indexOf("/"); String sDatabase; if (iUseCursors == -1) {
	 * sDatabase = sBeginBase; // no encontro useCursors... } else { sDatabase =
	 * sBeginBase.substring(0, iUseCursors); // lo encontro... } return sDatabase;
	 */

	protected abstract String getAdminUserFileName();

	protected abstract String getCommonUserFileName();

	public long getAntiBlockingTimeoutSeconds() {
		return lAntiBlockingSystemTimeoutSeconds;
	}

	/**
	 * Devuelve el nombre de la máquina
	 */
	/*
	 * public String getHostName() throws Exception { return
	 * BizPssConfig.getDataBaseHostName(sBaseDatos); }
	 */

	public boolean isCommonUser(int zUserType) throws Exception {
		return (zUserType == JBaseJDBC.COMMON_USER);
	}

	public boolean isAdminUser(int zUserType) throws Exception {
		return (zUserType == JBaseJDBC.ADMIN_USER);
	}

	/**
	 * Establece los valores de un JBDato para usar el JReport, y agrega una
	 * conexión al pool
	 */
	@Override
	public void setParamsReport() throws Exception {
		JBDato oDef = JBDatos.GetBases().getDefaultDatabase();
		this.setReportDatabase();
		this.SetName(oDef.GetName() + "_REPORT");
		this.SetInterface(oDef.GetInterface());
		// this.SetDatabaseType(oDef.GetDatabaseType());
		this.SetClassInterface(oDef.GetClassInterface());
		this.sConnectionInterface = oDef.getConnectionInterface();
		this.sDriverInterface = oDef.getDriverInterface();

		this.SetName(oDef.GetName() + "_REPORT");
		sUsername = "";
		sPassword = "";
		sUserService = oDef.GetUserService();
		sAuth = "Yes";
		sDriverName = oDef.GetDriverName();

		// Fuerza uso de cursores
		sConnectionUrl = oDef.getConnectionURL() + "/useCursors=true";
		int iMaxConnections = -1;
		oConnectionPools = JDBConnectionPools.getInstance();

		JPssDriver oDriver = this.createDriverImpl(this.sConnectionUrl, this.sUsername, this.sPassword, this.sDriverName, this.sConnectionInterface, this.sDriverInterface, this.lLockTimeoutSeconds);
		// Agregar el pool
		oConnectionPools.addPool(this.GetName(), iMaxConnections, oDriver);
	}

	protected void setReportDatabase() {
		this.bReportDatabase = true;
	}

	public boolean isReportDatabase() {
		return this.bReportDatabase;
	}

	/**
	 * Establece los valores de un JBDato para usar el JReport, y agrega una
	 * conexión al pool
	 */
	/*
	 * public void setParamsMaster(JBDato zBaseSource) throws Exception {
	 * 
	 * 
	 * this.SetName(zBaseSource.GetName() + "_CONFIG");
	 * this.SetInterface(zBaseSource.GetInterface());
	 * this.SetClassInterface(zBaseSource.GetClassInterface()); this.sBaseDatos =
	 * zBaseSource.GetName() + "_CONFIG"; this.sUsername =
	 * zBaseSource.GetUsername(); this.sPassword = zBaseSource.getPassword();
	 * this.sUserService = zBaseSource.GetUserService(); this.sAuth =
	 * zBaseSource.getAuthenticationMode(); this.sDriverName =
	 * zBaseSource.GetDriverName(); this.sDriverInterface =
	 * zBaseSource.getDriverInterface(); this.sConnectionInterface =
	 * zBaseSource.getConnectionInterface(); int iMaxConnections = -1; // Cambia
	 * la base Default por la base Master jdbc:JSQLConnect://Dev2/Pss String
	 * sUntilMachine = zBaseSource.getConnectionURL().substring(0,
	 * zBaseSource.getConnectionURL().lastIndexOf("/") + 1); sConnectionUrl =
	 * sUntilMachine + "Master"; oConnectionPools =
	 * JDBConnectionPools.getInstance(); // Agregar el pool
	 * this.oConnectionPools.addPool(this.sBaseDatos, this.sConnectionUrl,
	 * sUsername, sPassword, iMaxConnections, }
	 */

	/**
	 * Arroja una exception JConnectionBroken en caso que el error corresponda a
	 * una périda de conexión con la base de datos. Caso contrario, lanza la
	 * exception original(el parámetro)
	 * 
	 * @param zSQLex
	 *          Exception que se evaluará para saber si es una falla de conexión
	 */
	public void throwConnectionBroken(SQLException zException) throws Exception {
		JDBConnectionPools.getInstance().getDriverManager(this.GetName()).throwConnectionBroken(zException);
	}

	/*
	 * if (this.isDriverJSQL() || this.isDriverMSSQL() || this.isDriverJDATA()) {
	 * if (zSQLex.getSQLState() != null) { if
	 * (zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN) ||
	 * zSQLex
	 * .getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNABLE_TO_ESTABLISH) ||
	 * zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNESTABLISHED) ||
	 * zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_REJECTED)) { {
	 * JDebugPrint.logDebug("Error de conexión con la base [" + zSQLex + "]");
	 * throw new JConnectionBroken(zSQLex); } } } } else if
	 * (this.isDriverOracle()) { if (zSQLex.getErrorCode() == 17002) { // Socket
	 * write error exception
	 * JDebugPrint.logDebug("Error de conexión con la base [" + zSQLex + "]");
	 * throw new JConnectionBroken(zSQLex); } } throw zSQLex;
	 */

	/**
	 * Arroja una exception JConnectionBroken en caso que el error corresponda a
	 * una périda de conexión con la base de datos. Caso contrario, lanza la
	 * exception original(el parámetro)
	 * 
	 * @param zSQLex
	 *          Exception que se evaluará para saber si es una falla de conexión
	 * @author CJG
	 */
	public void throwConnectionBroken(SocketException zException) throws Exception {
		if (this.isTransactionInProgress()) {
			this.vTransactionCursors.removeAllElements();
			this.vTransactionCursors = null;
		}
		JDBConnectionPools.getInstance().getDriverManager(this.GetName()).throwConnectionBroken(zException);
		// if (this.isDriverJSQL() || this.isDriverMSSQL() || this.isDriverJDATA()
		// || this.isDriverOracle()) {
		// JDebugPrint.logError(zException);
		// throw new JConnectionBroken(zException);
		// } else
		// throw zSE;
	}

	/**
	 * Informa si la excepción es por perdida de conexión con la base de datos
	 * 
	 * @return true si es por perdida de conexión
	 * @return false en caso que el driver JDBC no sea JDataConnect, o que la
	 *         excepcion no sea por pérdida de conexión
	 */
	public boolean isConnectionBroken(SQLException zException) throws Exception {
		return JDBConnectionPools.getInstance().getDriverManager(this.GetName()).isConnectionBroken(zException);
	}

	/*
	 * if ((this.isDriverJSQL() || this.isDriverMSSQL() || this.isDriverJDATA())
	 * == false) return false; if (zSQLex.getSQLState() == null) return false;
	 * JDebugPrint.logDebug("Verificando si se perdió la conexión: [" + zSQLex +
	 * "]"); if (zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN)
	 * ||
	 * zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNABLE_TO_ESTABLISH
	 * ) ||
	 * zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNESTABLISHED) ||
	 * zSQLex.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_REJECTED)) {
	 * JDebugPrint.logDebug("Conexión perdida"); return true; } return false;
	 */
	public boolean isSQL92() {
		return false;
	}

	public String convertSqlToANSISql86(String zSql) throws Exception {
		/*
		 * if (isSQL92()) return zSql;
		 * 
		 * int iPosLeftJoin=zSql.toLowerCase().indexOf(" left join "); while
		 * (iPosLeftJoin>=0) { iPosLeftJoin++; int iPosTable=iPosLeftJoin+10; int
		 * iPosOn=zSql.toLowerCase().indexOf(" on ", iPosLeftJoin)+1; int
		 * iPosWhere=zSql.toLowerCase().indexOf(" where ", iPosOn)+1;
		 * 
		 * int iPosNextLeftJoin=zSql.toLowerCase().indexOf(" left join ", iPosOn);
		 * int iPosEndWhere=getSqlEndWhere(zSql); int iPosComa=getSqlPosComa(zSql,
		 * iPosOn, iPosEndWhere);
		 * 
		 * int iPosEndJoin; if (iPosWhere<0&&iPosComa<0&&iPosNextLeftJoin<0)
		 * iPosEndJoin=iPosEndWhere; else { if (iPosNextLeftJoin<0)
		 * iPosEndJoin=(iPosWhere>iPosComa&&iPosComa>0&&iPosComa>iPosOn) ? iPosComa
		 * : iPosWhere; else if (iPosComa<0)
		 * iPosEndJoin=(iPosWhere>iPosNextLeftJoin) ? iPosNextLeftJoin : iPosWhere;
		 * else iPosEndJoin=(iPosComa>iPosNextLeftJoin) ? iPosNextLeftJoin :
		 * iPosComa; }
		 * 
		 * String sJoinCondition=getSqlJoinCondition(zSql.substring(iPosOn+2,
		 * iPosEndJoin), getSqlJoinAlias(zSql, iPosOn));
		 * 
		 * String zSqlAux=zSql; zSql=zSqlAux.substring(0,
		 * iPosLeftJoin)+","+zSqlAux.substring(iPosTable,
		 * iPosOn)+" "+" "+zSqlAux.substring(iPosEndJoin,
		 * iPosEndWhere)+((zSqlAux.toLowerCase().indexOf("where")>0) ? " and " :
		 * " where ")+sJoinCondition+" "+zSqlAux.substring(iPosEndWhere);
		 * 
		 * iPosLeftJoin=zSql.toLowerCase().indexOf(" left join "); }
		 */
		// Ahora siempre es AnsiSQL92, porque Oracle ya soporta
		// Hay que ver en otras bases...
		// MRO 18-09-2009
		return zSql;
	}

	public String getSqlJoinCondition(String zSql, String zSqlAlias) throws Exception {

		int iPosCampoAlias = zSql.indexOf(zSqlAlias + ".");
		int iPosSpace = 0;
		int iPosEqual = 0;
		int iPosSearchFrom = 0;

		String zNewSqlByAlias = zSql;
		while (iPosCampoAlias > 0) {
			iPosSpace = zNewSqlByAlias.indexOf(" ", iPosCampoAlias);
			iPosEqual = zNewSqlByAlias.indexOf("=", iPosCampoAlias);

			if ((iPosSpace > 0) && ((iPosSpace < iPosEqual) || (iPosEqual < 0)))
				iPosSearchFrom = iPosSpace;
			if ((iPosEqual > 0) && ((iPosEqual < iPosSpace) || (iPosSpace < 0)))
				iPosSearchFrom = iPosEqual;

			String zAliasA = zNewSqlByAlias.substring(iPosCampoAlias, iPosSearchFrom);
			String zAliasB = zNewSqlByAlias.substring(iPosCampoAlias, iPosSearchFrom) + "(+)";

			if ((zAliasA.indexOf("(+)")) < 0) {
				zNewSqlByAlias = zNewSqlByAlias.replaceAll(zAliasA, zAliasB);
				iPosCampoAlias = zNewSqlByAlias.indexOf(zSqlAlias + ".", iPosSearchFrom + 3);
			} else
				iPosCampoAlias = zNewSqlByAlias.indexOf(zSqlAlias + ".", iPosSearchFrom);

		}

		return zNewSqlByAlias;
	}

	public int getSqlEndWhere(String zSql) throws Exception {
		int iPosOrder = zSql.toLowerCase().indexOf("order by");
		int iPosGroup = zSql.toLowerCase().indexOf("group by");
		if (iPosOrder < 0 && iPosGroup < 0)
			return zSql.length();
		if (iPosOrder > 0 && iPosGroup < 0)
			return iPosOrder;
		if (iPosOrder < 0 && iPosGroup > 0)
			return iPosGroup;
		return iPosGroup;
	}

	public String getSqlJoinAlias(String zSql, int zPosOn) {
		int iPosAlias = zSql.substring(1, zPosOn - 1).lastIndexOf(" ");
		String zAlias = zSql.substring(iPosAlias + 1, zPosOn - 1).trim();
		return zAlias;
	}

	public int getSqlPosComa(String zSql, int zPosOn, int zPosEndWhere) throws Exception {

		int iCounterChar1 = zPosOn, iCounterCharAux;
		int iParser = 0;
		String zSqlAux = zSql.toLowerCase();
		boolean flag = true;

		for (int iCounterChar = iCounterChar1; iCounterChar < zPosEndWhere && flag; iCounterChar++) {
			switch (zSqlAux.charAt(iCounterChar)) {
			case '(':
				iParser++;
				break;
			case ')':
				iParser--;
				break;
			case '\'':
				iCounterChar = zSqlAux.indexOf("'", iCounterChar);
				break;
			case ',':
				if (iParser == 0) {
					iCounterChar1 = iCounterChar;
					flag = false;
				}
				break;
			case 'a':
				iCounterCharAux = zSqlAux.indexOf("and", iCounterChar--);
				if (iCounterCharAux == iCounterChar)
					iCounterChar += 3;
				else
					iCounterChar++;
				break;
			case 'o':
				iCounterCharAux = zSqlAux.indexOf("or", iCounterChar--);
				if (iCounterCharAux == iCounterChar)
					iCounterChar += 2;
				else
					iCounterChar++;
				break;
			case 'i':
				iCounterCharAux = zSqlAux.indexOf("in", iCounterChar--);
				if (iCounterCharAux == iCounterChar)
					iCounterChar += 2;
				else
					iCounterChar++;
				break;
			default:
			}

		}

		return iCounterChar1;
	}

	private int closeTransactionCursors() {
		if (vTransactionCursors != null) {
			try {
				JIterator<Statement> iCursors = vTransactionCursors.getValueIterator();
				while (iCursors.hasMoreElements()) {
					Statement oCursor = iCursors.nextElement();
					oCursor.close();
				}
			} catch (Exception e) {
				PssLogger.logError(e);
			}
			int closed = vTransactionCursors.size();
			PssLogger.logDebugSQL("closed cursors= " + closed);
			PssLogger.logDebugSQL("");
			vTransactionCursors = null;
			return closed;
		}
		return 0;
	}

	private int cancelTransactionCursors() {
		if (vTransactionCursors != null) {
			try {
				JIterator<Statement> iCursors = vTransactionCursors.getValueIterator();
				while (iCursors.hasMoreElements()) {
					Statement oCursor = iCursors.nextElement();
					oCursor.cancel();
				}
			} catch (Exception e) {
				PssLogger.logError(e);
			}
			int closed = vTransactionCursors.size();
			PssLogger.logDebugSQL("closed cursors= " + closed);
			PssLogger.logDebugSQL("");
			vTransactionCursors = null;
			return closed;
		}
		return 0;
	}
	public void addTransactionCursors(Statement oCursor) throws Exception {
		if (vTransactionCursors == null)
			return;
		vTransactionCursors.addElement(oCursor.toString(), oCursor);
	}

}
