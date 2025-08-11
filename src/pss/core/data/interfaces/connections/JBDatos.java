package pss.core.data.interfaces.connections;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.util.Calendar;

import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.records.JRecordEvent;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.tools.submit.JEvent;
import pss.core.tools.submit.JSubmit;

public class JBDatos extends JRecords<JBDato> {

	public static final int EVENT_JBD_CHANGE = 0;
	public static final int EVENT_COMMIT = 1;
	public static final int EVENT_ROLLBACK = 2;

	private static InheritableThreadLocal<JBDatos> oGlobal = new InheritableThreadLocal<JBDatos>();
	private static JBDato oMasterDatabase;
	private String sBaseDefault;
	private String sTablasForzadas;

	private boolean bDatabaseOpen = false;
	private boolean bNotifyEvents = true;
	private static JSubmit oEventListener;
	private String sDatabaseSlave = "";
	
	public void setCurrentDatabase(String zValue) {
		sBaseDefault=zValue;
		JAplicacion.GetApp().setCurrentDatabase(sBaseDefault);
	}

	public synchronized void changeCurrentDatabase(String zValue) throws Exception{
		try {
			getDatabaseByName(zValue);
		} catch (JDatabaseNotFound e) {
			//forceCreationDatabase(zValue);
		}
		setCurrentDatabase(zValue);
		
	}


	public void SetDatabaseDefault(String primaria) {
		if (primaria == null)
			return;
		sBaseDefault = primaria;
		JAplicacion.GetApp().setDefaultDatabase(sBaseDefault);
	}

	static HashMap<String, Date> fails;

	static HashMap<String, Date> getFails() {
		if (fails != null)
			return fails;
		fails = new HashMap<String, Date>();
		return fails;
	}

	public static void addFail(String base) {
		if (getFails().get(base) != null)
			return;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, 1);
		getFails().put(base, cal.getTime());
	}

	public static boolean test(String base) {
		Date d = getFails().get(base);
		if (d == null)
			return true;
		return d.before(new Date());
	}

	private String forceCreationDatabase(String zbasePrimaria,
			String zBaseSecundaria, boolean forceRetry) throws Exception {
		String out = null;
		if (zBaseSecundaria == null || test(zbasePrimaria)) {
			try {
				JBDato db;
				out = zbasePrimaria;
				if (!test(zbasePrimaria) && !forceRetry)
					throw new Exception(
							"Disculpe las molestias. Base no disponible ["
									+ zbasePrimaria + "] reintente mas tarde.");
				db = createBaseFromConfig(zbasePrimaria, JBaseJDBC.COMMON_USER);
				db.open();
				JBDatos.GetBases().addDatabase(db, true);
				return out;
			} catch (Exception e) {
				addFail(out);
				if (zBaseSecundaria == null)
					throw e;
			}
		}
		try {
			out = zBaseSecundaria;
			if (!test(zBaseSecundaria) && !forceRetry)
				throw new Exception(
						"Disculpe las molestias. Base no disponible ["
								+ zbasePrimaria + "] reintente mas tarde.");
			getDatabaseByName(zBaseSecundaria);
		} catch (Exception e1) {
			JBDato db;
			db = createBaseFromConfig(zBaseSecundaria, JBaseJDBC.COMMON_USER);
			db.open();
			JBDatos.GetBases().addDatabase(db, true);
		}
		return out;
	}

	private boolean checkDatabase(String zbasePrimaria) throws Exception {
		try {
			JBDato db;
			db = createBaseFromConfig(zbasePrimaria, JBaseJDBC.COMMON_USER);
			db.open();
			db.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public synchronized void useNewDatabaseDefault(String zPrimaria)
			throws Exception {
		useNewDatabaseDefault(zPrimaria, null, true);
	}

	public synchronized void useNewDatabaseDefault(String zPrimaria,
			String zSecundaria, boolean forceRetry) throws Exception {
		String defa = zPrimaria;
		try {
			getDatabaseByName(zPrimaria);
		} catch (JDatabaseNotFound e) {
			defa = forceCreationDatabase(zPrimaria, zSecundaria, forceRetry);
		}
		PssLogger.logDebugSQL("Changing database to " + zPrimaria);
		SetDatabaseDefault(defa);

	}

	public synchronized boolean testDatabase(String zPrimaria) throws Exception {
		try {
			getDatabaseByName(zPrimaria);
			return true;
		} catch (JDatabaseNotFound e) {
			return checkDatabase(zPrimaria);
		}
	}

	public String GetDatabaseDefault() {
		return sBaseDefault;
	}

	public static JBDatos GetBases() {
		return oGlobal.get();
	}

	public void setNotifyEvents(boolean zValue) {
		bNotifyEvents = zValue;
	}

	public String GetDatabaseSlave() {
		return sDatabaseSlave;
	}

	public JBDatos() throws Exception {
		this.setStatic(true);
		this.sTablasForzadas = "";
	}

	public JBDatos cloneDatabases(String baseDefault) throws Exception {
		JBDatos newBDatos = new JBDatos();
		JIterator<JBDato> it = this.getStaticIterator();
		while (it.hasMoreElements()) {
			JBDato dato = it.nextElement();
			JBDato newDato = dato.getClass().newInstance();
			newDato.copyProperties(dato);
			if (baseDefault == null)
				newBDatos.addDatabase(newDato, false);
			else
				newBDatos.addDatabase(newDato,baseDefault.equals(dato.GetName()));
		}
		return newBDatos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getBasedClass() {
		return JBDato.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	public static synchronized void SetGlobal(JBDatos zoBDatos) {
		if (oGlobal == null) {
			PssLogger.logError("JBDatos.SetGlobal() -----------------------------------> Problema fatal! oGlobal desaparecio!!!");
		}
		oGlobal.set(zoBDatos);
	}

	/*
	 * public static synchronized JBDatos GetGlobal() { return
	 * (JBDatos)oGlobal.get(); }
	 */
	/*
	 * public static synchronized void CrearGlobal() throws Exception {
	 * oGlobal.set(new JBDatos()); }
	 */
	public void addDatabase(JBDato zBase) throws Exception {
		this.addDatabase(zBase, false);
	}

	public void addDatabase(JBDato zBase, boolean bDefault) throws Exception {
		// this.removeAnyWithName(zBase.GetName());
		this.addItem(zBase);
		if (bDefault)
			SetDatabaseDefault(zBase.GetName());
	}

	public void removeBases() throws Exception {
		this.clearStaticItems();
	}

	/**
	 * Lee del Pss.ini el nombre de la base default
	 * 
	 * @return El nombre de la base default
	 */
	public static JBDato createDatabaseDefaultFromConfig() throws Exception {
		return JBDatos.createBaseFromConfig(null, JBaseJDBC.COMMON_USER);
	}

	/**
	 * Lee del Pss.ini la base especificada.
	 * 
	 * @param zBase
	 *            Es el nombre del label de la configuracion de base de datos.
	 *            Si zBase esta vacio, see lee de la base default.
	 */
	public static JBDato createBaseFromConfig(String zBase, int zUserType)
			throws Exception {
		BizPssConfig PssIni = BizPssConfig.getPssConfig();
		// Si no se refiere a ninguna base, busca la default
		if (zBase == null) {
			zBase = PssIni.GetDatabaseNameDefault();
		}

		String sInterface = PssIni.getCachedStrictValue(zBase,
				JBDato.INTERFACE, null);

		JBDato oBase = (JBDato) Class.forName(sInterface.trim()).newInstance();
		oBase.SetName(zBase);
		oBase.SetInterface(sInterface);
		oBase.SetClassInterface(Class.forName(oBase.getRowInterface()));
		oBase.asignParams(zUserType);
		return oBase;
	}

	/**
	 * Arma a partir de la base default una nueva configuración para el JReport
	 * 
	 * @return Un JBDato configurado para usar el JReport
	 */
	public static JBDato getBaseReport() throws Exception {
		JBDato oDef = JBDatos.GetBases().GetBaseDefault();
		JBDato oBase = (JBDato) Class.forName(oDef.GetInterface())
				.newInstance();
		oBase.setParamsReport();
		return oBase;
	}

	/**
	 * Arma a partir de la base default una nueva configuración para conectarse
	 * a la base Master(SQLServer)
	 * 
	 * @return Un JBDato configurado para conectarse a Master
	 */
	public static synchronized JBDato getBaseMaster() throws Exception {
		if (oMasterDatabase != null) {
			return oMasterDatabase;
		}
		oMasterDatabase = JBDatos.createBaseFromConfig(null,
				JBaseJDBC.COMMON_USER);
		// JBDatos.GetBases().addDatabase(oMasterDatabase, false);
		PssLogger.logDebugSQL("Intenta abrir base Master");
		oMasterDatabase.open();
		PssLogger.logDebugSQL("Base Master abierta OK.");

		// JBDato oBase =
		// (JBDato)Class.forName(oDef.GetInterface()).newInstance();
		// oBase.setParamsMaster(oDef);
		// oMasterDatabase = oDef;
		return oMasterDatabase;
	}

	// public void openDatabasesFromPool() throws Exception {
	//
	// // Le pide una conexión del pool zBaseDatos
	// this.openDatabases();
	// }

	/**
	 * Abro todas las bases de datos
	 */
	public static void openAllDatabases() throws Exception {
		if (JBDatos.GetBases() == null)
			return;
		JBDatos.GetBases().openDatabases();
	}

	public synchronized void openDatabases() throws Exception {
		this.firstRecord();
		while (this.nextRecord()) {
			JBDato oBase = this.getRecord();
			oBase.open();
		}
		bDatabaseOpen = true;
	}

	public static void inicializeDB(BizUsuario usr) throws Exception {
		if (usr==null)
			return;
		if (JBDatos.GetBases() == null)
			return;
		JIterator<JBDato>it=JBDatos.GetBases().getStaticIterator();
		while (it.hasMoreElements()) {
			JBDato oBase = it.nextElement();
			oBase.initializeConnectionByUser(usr);
		}
	}
	/**
	 * Cierro todas las bases de datos
	 * 
	 * @throws Exception
	 */

	public static void closeAllDatabases() {
		if (JBDatos.GetBases() == null)
			return;
		JBDatos.GetBases().closeDatabases();
	}

	public synchronized void closeDatabases() {
		// JDebugPrint.logDebug("Cerrando Bases de datos...");
		try {
			this.firstRecord();
			while (this.nextRecord()) {
				JBDato oBase = this.getRecord();
				oBase.close();
			}
			bDatabaseOpen = false;
		} catch (Exception ignore) {
		}
		// JDebugPrint.logDebug("Bases de datos cerradas");
	}

	public synchronized void cancelDatabases() {
		// JDebugPrint.logDebug("Cerrando Bases de datos...");
		try {
			this.firstRecord();
			while (this.nextRecord()) {
				JBDato oBase = this.getRecord();
				oBase.cancel();
			}
			bDatabaseOpen = false;
		} catch (Exception ignore) {
		}
		// JDebugPrint.logDebug("Bases de datos cerradas");
	}
	// /**
	// * Commit todas las bases de datos
	// */
	// public void commit() throws Exception {
	// if (this.bDatabaseOpen==false) return;
	// PssLogger.logDebugSQL("COMMIT");
	// this.firstRecord();
	// while (this.nextRecord()) {
	// JBDato oBase=this.getRecord();
	// oBase.commit();
	// }
	// if (bNotifyEvents) notifyCommit();
	//
	// }
	//
	// /**
	// * Rollback todas las bases de datos
	// */
	// public void rollback() throws Exception {
	// if (this.bDatabaseOpen==false) return;
	// PssLogger.logDebugSQL("ROLLBACK");
	// this.firstRecord();
	// while (this.nextRecord()) {
	// JBDato oBase=this.getRecord();
	// oBase.rollback();
	// }
	// if (bNotifyEvents) notifyRollback();
	// }
	
	
	public String getCurrentDatabase() {
		return sBaseDefault;
	}

	public JBDato getPrivateCurrentDatabase() throws Exception {
		return this.getDatabaseByName(this.getCurrentDatabase());
	}
	
	public void pacialCommit() throws Exception {
		if (!JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress()) 
			return;
		JBDatos.GetBases().commit();
		JBDatos.GetBases().beginTransaction();
	}


	public void commit() throws Exception {
		if (this.bDatabaseOpen == false)
			return;
		PssLogger.logDebugSQL("[" + this.getCurrentDatabase() + "] commit");
		this.getPrivateCurrentDatabase().commit();
		JMap<String, String> dbs = getAllDatabaseByTable();
		if (dbs==null) return;
		JIterator<String> keys = dbs.getKeyIterator();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equalsIgnoreCase(this.getCurrentDatabase()))
				continue;
			PssLogger.logDebugSQL("[" + key + "] commit");
			this.getDatabaseByName(key).commit();
		}
		if (bNotifyEvents)
			notifyCommit();
	}

	/**
	 * Rollback todas las bases de datos
	 */
	public void rollback() throws Exception {
		if (this.bDatabaseOpen == false)
			return;
		PssLogger.logDebugSQL("[" + this.getCurrentDatabase() + "] rollback");
		this.getPrivateCurrentDatabase().rollback();
		JMap<String, String> dbs = getAllDatabaseByTable();
		if (dbs==null) return;
		JIterator<String> keys = dbs.getKeyIterator();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equalsIgnoreCase(this.getCurrentDatabase()))
				continue;
			PssLogger.logDebugSQL("[" + key + "] rollback");
			this.getDatabaseByName(key).rollback();
		}
		if (bNotifyEvents)
			notifyRollback();
	}

	/**
	 * BeginTransaction todas las bases de datos
	 */
	public void beginTransaction() throws Exception {
		if (this.bDatabaseOpen == false)
			return;
		PssLogger.logDebugSQL("[" + this.getCurrentDatabase() + "] begintran");
		this.getPrivateCurrentDatabase().beginTransaction();
		JMap<String, String> dbs = getAllDatabaseByTable();
		if (dbs == null)
			return;
		JIterator<String> keys = dbs.getKeyIterator();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equalsIgnoreCase(this.getCurrentDatabase()))
				continue;
			PssLogger.logDebugSQL("[" + key + "] begintran");
			this.getDatabaseByName(key).beginTransaction();
		}
	}

	/**
	 * Obtengo la base por la tabla
	 */
	public JBDato GetBaseByTable(String zTable) throws Exception {
		String sBaseForzada = this.getDatabaseByTable(zTable);

		if (sBaseForzada != null)
			return this.getDatabaseByName(sBaseForzada);
		if (sBaseDefault != null)
			return this.getDatabaseByName(sBaseDefault);
		return null;

	}

	/**
	 * Obtengo la base por el nombre
	 */
	public JBDato getDatabaseByName(String zName) throws Exception {
		if (zName==null) return null;
		this.firstRecord();
		while (this.nextRecord()) {
			JBDato oBase = this.getRecord();
			if (oBase.GetName().trim().equals(zName.trim()))
				return oBase;
		}
		JDatabaseNotFound.sendError("No hay Bases abiertas");
		return null;
	}

	/**
	 * Obtengo la lista de tablas
	 */
	static JMap<String, String> databaseByList;

	public static JMap<String, String> getDatabaseList() throws Exception {
		if (databaseByList != null)
			return databaseByList;
		JMap<String, String> dbl = JCollectionFactory.createMap();

		String sDBList = BizPssConfig.getPssConfig().getCachedStrictValue(
				JAplicacion.GetApp().getIniSeccion(), JBDato.DATABASE_LIST, "");
		if (sDBList == null || sDBList.equals(""))
			return null;
		JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(
				sDBList, ',');
		while (tokens.hasMoreTokens()) {
			String id = tokens.nextToken().trim();
			String descr = BizPssConfig.getPssConfig().getCachedStrictValue(id,
					"DESCRIPTION", id);
			if (descr.equals(""))
				continue;
			dbl.addElement(id, descr);
		}
		return (databaseByList = dbl);
	}

	public static boolean isSimpleDatabase() throws Exception {
		return JBDatos.getDatabaseList() == null;
	}

	JMap<String, String> allDatabaseByTable;
	JMap<String, String> databaseByTable;

	private JMap<String, String> getAllDatabaseByTable() throws Exception {
		if (allDatabaseByTable != null)
			return allDatabaseByTable;
		String db;
		allDatabaseByTable = JCollectionFactory.createMap();

		String tables = BizPssConfig.getPssConfig().getCachedStrictValue(
				JAplicacion.GetApp().getIniSeccion(), JBDato.DATABASE_TABLES, "");
		if (tables == null || tables.equals(""))
			return null;
		JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(
				tables, ',');
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			int sep = token.lastIndexOf(':');
			db = token.substring(sep + 1);
			if (allDatabaseByTable.getElement(db) == null)
				allDatabaseByTable.addElement(db, "");
		}

		return allDatabaseByTable;
	}

	private JMap<String, String> getObjDatabaseByTable() {
		if (databaseByTable == null)
			databaseByTable = JCollectionFactory.createMap();
		return databaseByTable;
	}

	/**
	 * Obtengo la base de datos default para una tabla
	 */
	private String getDatabaseByTable(String zTable) throws Exception {
		String db = getObjDatabaseByTable().getElement(zTable);
		if (db != null)
			return db;

		this.sTablasForzadas = BizPssConfig.getPssConfig().getCachedStrictValue(JAplicacion.GetApp().getIniSeccion(), JBDato.DATABASE_TABLES, "");
		if (this.sTablasForzadas == null || this.sTablasForzadas.equals(""))
			return null;
		JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.sTablasForzadas, ',');
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			int sep = token.lastIndexOf(':');
			Pattern p = Pattern.compile(token.substring(0, sep));
			Matcher m = p.matcher(zTable);
			if (!m.matches())
				continue;
			db = token.substring(sep + 1);
			getObjDatabaseByTable().addElement(zTable, db);
			return db;
		}

		return null;
	}

	public static boolean isDatabaseOpen() throws Exception {
		if (JBDatos.GetBases() == null)
			return false;
		return JBDatos.GetBases().bDatabaseOpen;
	}

	// @Deprecated
	// public static boolean ifBasesAbiertas() throws Exception {
	// return isDatabaseOpen();
	// }

	/**
	 * Obtengo la base default
	 */
	public JBDato GetBaseDefault() throws Exception {
		return this.getDatabaseByName(this.GetDatabaseDefault());
	}

	public static JBDato getDefaultDatabase() throws Exception {
		return JBDatos.GetBases().GetBaseDefault();
	}

	/**
	 * Reintenta la conexión de la base de datos. Cierra y abre las bases de
	 * Datos.
	 * 
	 * @throw JConnectionBroken si no hay conexión con la base de datos
	 */
	public static boolean retryEstablishConnection() {
		PssLogger.logDebug("Intentanto reestablecer conexión con la Base...");
		try {
			if (JBDatos.isDatabaseOpen())
				JBDatos.closeAllDatabases();

			JBDatos.openAllDatabases();
			return true;
		} catch (Exception ex) {
			PssLogger.logError("Intento de reconexión fallido.");
		}
		return false;
	}

	// public static boolean changeToAutonomous() {
	// PssLogger.logDebug("Intentando cambiar a Base de datos Autónoma...");
	// try {
	// if (JBDatos.isDatabaseOpen()) JBDatos.GetBases().closeDatabases();
	//
	// JBDatos.GetBases().removeBases();
	// String sSection=JBDatos.GetBases().GetDatabaseSlave();
	// JBDato oSlave=JBDatos.createBaseFromConfig(sSection,
	// JBaseJDBC.COMMON_USER);
	// JBDatos.GetBases().addDatabase(oSlave, true);
	// // JBDatos.GetBases().openDatabases();
	// return true;
	// } catch (Exception ex) {
	// PssLogger.logError("Falló intentando cambiar a Autónomo.");
	// }
	// return false;
	// }

	//
	// event handling methods
	//
	public static void setEventListener(JSubmit zEventListener) {
		oEventListener = zEventListener;
	}

	public static void notifyJBDEvent(JRecordEvent e) throws Exception {
		if (oEventListener == null)
			return;
		if (!GetBases().bNotifyEvents)
			return;
		oEventListener.submit(new JEvent(e, EVENT_JBD_CHANGE, null));
	}

	public static void notifyCommit() throws Exception {
		if (oEventListener == null)
			return;
		try {
			oEventListener.submit(new JEvent(null, EVENT_COMMIT, null));
		} catch (Exception e) {
			PssLogger.logDebug("Error en JRefresh");
			PssLogger.logDebug(e);
		}

	}

	private static void notifyRollback() throws Exception {
		if (oEventListener == null)
			return;
		oEventListener.submit(new JEvent(null, EVENT_ROLLBACK, null));
	}

	public boolean isNotifyEvents() {
		return bNotifyEvents;
	}

}
