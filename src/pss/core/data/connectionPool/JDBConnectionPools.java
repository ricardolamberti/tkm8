package pss.core.data.connectionPool;

/**
 * Title: DBConnectionPools
 * Description: Singleton para administrar una serie de pools de conexiones a base de datos
 *
 * addDriver() agrega un driver a la lista y lo registra
 * addPool() crea un pool y lo agrega a la lista de pools
 *
 * maxconn
 *  al pool se le piden conexiones getConnection y se liberan freeConnection()
 *
 *  si maxconn es > 0
 *    el pool entrega conexiones a traves de getConnection con maxconn como límite
 *    de conexiones abiertas simultaneamente.
 *    freeConnection devuelve la conexion al pool, preservandola abierta
 *
 *  si maxconn == 0
 *    getConnection siempre devuelve una conexión sin límite. Si no tiene conexiones
 *    disponibles, las crea.
 *    freeConnection la devuelve al pool para ser reutilizada, preservandola abierta
 *
 *  si maxconn == -1
 *    getConnection siempre devuelve una nueva conexión.
 *    freeConnection la cierra, no la preserva abierta.
 *
 */

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;

import pss.core.data.interfaces.connections.JPssDriver;
import pss.core.tools.PssLogger;

public class JDBConnectionPools {

	static private JDBConnectionPools oInstance=null;
//	static private int oClients=0;

	// private Vector oDrivers = new Vector();
	private Hashtable<String, JDBConnectionPool> oPools=new Hashtable<String, JDBConnectionPool>();

	// Devuelve la instancia única de esta clase
	static synchronized public JDBConnectionPools getInstance() {
		if (oInstance==null) {
			oInstance=new JDBConnectionPools();
		}
//		oClients++;
		//JDebugPrint.logInfo("Clientes de Pool " + oClients);
		return oInstance;
	}

//	static synchronized public boolean isInstantiated() {
//		if (oInstance!=null) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * @return the current number of clients
	 */
//	static synchronized public int getNumberOfClients() {
//		return oClients;
//	}

	private JDBConnectionPools() {
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////
	/*
	 * public Enumeration getDrivers() { return oDrivers.elements(); }
	 */
//	public Enumeration<JDBConnectionPool> getPools() {
//		return oPools.elements();
//	}

	public JPssDriver getDriverManager(String zName) {
		JDBConnectionPool pool=oPools.get(zName);
		if (pool!=null) { // Si el pool existe
			return pool.getDriverImpl(); // Le pide una conexión
		}
		PssLogger.logError("The pool "+zName+" does not exist!");
		return null;
	}

	/**
	 * Devuelve una conexión abierta de un pool determinado
	 * 
	 * @param name
	 *          Nombre del pool al que se le solicita la conexión
	 * @return la conexión abierta
	 * @return null si el pool name no existe o no tiene conexiones disponibles
	 */
	public Connection getConnection(String name, int timeoutMinute) throws Exception {
		if (name==null) name="";
		JDBConnectionPool pool=oPools.get(name);
		if (pool==null) {
			PssLogger.logError("The pool "+name+" does not exist!");
			return null;
		}
		if (timeoutMinute!=-1) 
			pool.revalidateFreeConnection(timeoutMinute);
		Connection conn = pool.manageConnection(null);
		if ( conn == null ) { // no hay mas conexiones en el pool
		  pool.checkOutConnection(null);
			conn = pool.newConnection();
		  pool.updateCheckOutConnection(conn);
		}
		return conn;
	}

	public long howManyConnection(String name) throws Exception {
		JDBConnectionPool pool=oPools.get(name);
		if (pool==null) return -1;
		return pool.howManyConnectionFree();
	}
	
	/**
	 * Devuelve una conexión abierta del pool name null si el pool name no existe Si el pool no tiene conexiones disponibles, se queda esperando a que se libere una. null si pasó timeout sin que se libere una conexión
	 */
//	public Connection getConnection(String name, long timeout) throws JConnectionBroken {
//		JDBConnectionPool pool=oPools.get(name);
//		if (pool!=null) { // Si el pool existe
//			return pool.getConnection(timeout); // Le pide una conexión
//		}
//		PssLogger.logError("The pool "+name+" does not exist!");
//		return null;
//	}

	/**
	 * Libera una conexión del pool name
	 */
	public void freeConnection(String zName, Connection zConnection) throws Exception  {
		JDBConnectionPool oPool=oPools.get(zName);

		if (oPool!=null) {
			oPool.manageConnection(zConnection);
		}
	}
	public void killConnection(String zName, Connection zConnection) throws Exception  {
		JDBConnectionPool oPool=oPools.get(zName);

		if (oPool!=null) {
			oPool.manageConnection(zConnection);
			oPool.killAllFreeConnections();
		}
	}

	public Connection reConnection(String zName, Connection zConnection, int timeoutMinute) throws Exception  {
		PssLogger.logDebug("try to reconnect "+zName);
		killConnection(zName,zConnection);
		Connection c = getConnection(zName,timeoutMinute);
		PssLogger.logDebug("Reconnecting "+zName);
		return c;
	}

	
	/**
	 * Cuando la llama el último cliente, cierra todos los pools y conexiones, deregistra todos los drivers
	 */
//	public synchronized void release() {
//		// Espera a que la llame el último cliente
////		if (--oClients!=0) {
////			PssLogger.logInfo("Connection released");
////			return;
////		}
//		Enumeration<JDBConnectionPool> e=oPools.elements();
//		while (e.hasMoreElements()) {
//			JDBConnectionPool pool=e.nextElement();
//			pool.release();
//		}
//		PssLogger.logInfo("DBConnectionPools closed");
//		oInstance=null;
//		PssLogger.logInfo("Connection closed");
//	}

	/**
	 * Adds a database connection pool
	 */
	public synchronized void addPool(String zPoolname, int zMaxconn, JPssDriver zDriverImpl) throws Exception {
		Enumeration<String> e;
		String sAux;

		if (zPoolname==null) zPoolname="";
		e=oPools.keys(); // Busca el pool entre los creados
		while (e.hasMoreElements()) {
			sAux=e.nextElement();
			if (sAux.equals(zPoolname)) {
				return;// Ya está, vieja...
			}
		}
		// Crea un nuevo pool
		JDBConnectionPool oConnectionPool=new JDBConnectionPool(zPoolname, zMaxconn, zDriverImpl);
		oPools.put(zPoolname, oConnectionPool);
	}

}
