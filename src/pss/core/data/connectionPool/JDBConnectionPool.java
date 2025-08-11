/*
 * Created on 25/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package pss.core.data.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ibm.icu.util.Calendar;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.connections.JDatabaseNotFound;
import pss.core.data.interfaces.connections.JPssConnection;
import pss.core.data.interfaces.connections.JPssDriver;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;


public class JDBConnectionPool {

	private JPssDriver oDriverImpl;
//	private int iCheckedOut=0;
	private JList<ConnectionsFree> oFreeConnections = JCollectionFactory.createList(0);
	private JMap<String,ConnectionsCheckedOut> oCheckedOutConnections = JCollectionFactory.createMap(0);
	private int iMaxConnections=0;
	private String sName;
	
	private int iMaxFreeConnectionsTaken = 0;
	private int iMaxCheckedOutConnectionsTaken = 0;
	private String sMaxFreeDate = null;
	private String sMaxCheckOutDate = null;

	class ConnectionsFree {
		Connection conn;
		Date entrance;
	}
	
	class ConnectionsCheckedOut {
		Connection conn;
		Date entrance;
		Thread th;
	}
	
	/**
	 * Crea un nuevo pool
	 */
	public JDBConnectionPool(String zName, int zMaxConnections, JPssDriver zDriverImpl) throws Exception {
		this.sName = zName;
		this.iMaxConnections = zMaxConnections;
		this.oDriverImpl = zDriverImpl;
	}

//////////////////////////////////////////////////////////////////////////////
// Setter & Getter methods
//////////////////////////////////////////////////////////////////////////////

//	public int getCheckedOut() {
//		return iCheckedOut;
//	}
	public int getMaxConnections() {
		return iMaxConnections;
	}
	public String getName() {
		return sName;
	}

	public JPssDriver getDriverImpl() {
		return this.oDriverImpl;
	}

//////////////////////////////////////////////////////////////////////////////
// Connection methods
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Some connection is free ?
	 */
	private boolean isSomeConnectionFree() {
		return this.oFreeConnections.size() > 0;
	}
	public long howManyConnectionFree() {
		return this.oFreeConnections.size();
	}
	
	public synchronized Connection manageConnection(Connection conn) throws Exception {
		PssLogger.logWait("manage connection");
		if (conn==null)	return this.getConnection();
		this.freeConnection(conn);
		PssLogger.logWait("connection is free");
		return null;
	}

	public synchronized void revalidateFreeConnection(int minute) {
		JIterator<ConnectionsFree> it = oFreeConnections.getIterator();
		while (it.hasMoreElements()) {
			ConnectionsFree connFree = it.nextElement();
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(connFree.entrance);
			GregorianCalendar limit = new GregorianCalendar();
			limit.setTime(new Date());
			limit.add(Calendar.MINUTE, -minute);
			if (cal.before(limit)) {
				PssLogger.logWait("try to expire old free connection "+sName);
				closeConnection(connFree.conn);
				PssLogger.logInfo("Expire old free connection "+sName);
				it.remove();
			}
		}
	}
	
	/**
	 * Obtiene una conexión del pool
	 */
	private Connection getConnection() throws Exception {

//		this.iCheckedOut++;
		if (this.isSomeConnectionFree()) { // Hay conexiones libres?
//			PssLogger.logInfo("Taking connection from POOL ("+this.getPoolStatus()+")");
			Connection conn = getFreeConnection();
			if (!conn.isClosed()) return checkOutConnection(conn);;
			try {
				PssLogger.logWait("Trying to reconnect " + conn.toString() );
				return checkOutConnection(this.newConnection());
			} catch (Exception e) {
				PssLogger.logInfo("Reconnect FAILED " + conn.toString() );
				this.freeConnection(conn);
				return null;
			}
		} 
		
		// SIEMPRE DOY NUEVAS CONEXIONES (HASTA QUE LA BASE LO PERMITA,OBVIAMENTE), 
		// EL POOL ES LA CANTIDAD DE CONEXIONES VIVAS QUE MANTENGO
//		return checkOutConnection(this.newConnection());
		return null;
		
	}

	/**
	 * @return
	 */
	private Connection getFreeConnection() {
		ConnectionsFree connFree = oFreeConnections.getElementAt(0); // Toma la primer conexion libre
		Connection conn = connFree.conn;
		this.oFreeConnections.removeElementAt(0);
		return conn;
	}

	
	/**
	 * @param conn
	 */
	public synchronized Connection checkOutConnection(Connection conn) {
		ConnectionsCheckedOut connOut = new ConnectionsCheckedOut();
		connOut.conn = conn;
		connOut.entrance = new Date();
		connOut.th = Thread.currentThread();
		this.oCheckedOutConnections.addElement(Thread.currentThread().getName(),connOut);
		return conn;
	}
	
	public void updateCheckOutConnection(Connection conn) {
		ConnectionsCheckedOut connOut = this.oCheckedOutConnections.getElement(Thread.currentThread().getName());
		if (connOut==null) return;
		connOut.conn = conn;
	}
	
	/**
	 * Devuelve una conexión al pool
	 */
	private void freeConnection(Connection zConnection) {
		// Para maxConn establecida en -1 no hay funcionalidad de conexiones disponibles
		PssLogger.logWait("Releasing connection from POOL ("+this.getPoolStatus()+") max (" + iMaxConnections + ")");
		if (iMaxConnections >= oCheckedOutConnections.size()) {
			addFreeConnection(zConnection);
		} else {
			this.closeConnection(zConnection);
		}
		removeCheckedOutConnection();
//		this.iCheckedOut--;
		this.notifyAll();
	}

	private void removeCheckedOutConnection() {
		oCheckedOutConnections.removeElement(Thread.currentThread().getName());
	}

	/**
	 * @param zConnection
	 */
	private void addFreeConnection(Connection zConnection) {
		ConnectionsFree freeCon = new ConnectionsFree();
		freeCon.conn = zConnection;
		freeCon.entrance = new Date();
		this.oFreeConnections.addElement(freeCon);
	}
	
	public String getPoolStatus() {
		int free     = this.oFreeConnections.size();
		int out      = this.oCheckedOutConnections.size();
		if ( free > iMaxFreeConnectionsTaken ) {
			iMaxFreeConnectionsTaken = free;
			try { sMaxFreeDate = JDateTools.CurrentDateTime(); } catch (Exception e1) {}
		}
		if ( out > iMaxCheckedOutConnectionsTaken ) {
			iMaxCheckedOutConnectionsTaken = out;
			try { sMaxCheckOutDate = JDateTools.CurrentDateTime();} catch (Exception e2) {}
		}
		return "["+this.getName()+"] - "+Thread.currentThread().getName() + " curr(" + free + " - " + out + ") max("+iMaxFreeConnectionsTaken+" - "+iMaxCheckedOutConnectionsTaken+") max date("+sMaxFreeDate+" - " +sMaxCheckOutDate+")" ;
	}

	/**
	 * Devuelve una conexión del pool. Si no hay conexiones disponibles,
	 * se queda esperando a que una se libere (notifyAll en freeConnection) mientras
	 * no expire timeout.
	 */
//	public synchronized Connection getConnection(long timeout) throws JConnectionBroken {
//		long startTime = new java.util.Date().getTime();
//		Connection cn;
//
//		while ((cn = getConnection()) == null) {
//			try {
//				wait(timeout);
//			} catch (InterruptedException e) {}
//
//			if ((new java.util.Date().getTime() - startTime) >= timeout)
//				return null;
//		}
//		return cn;
//	}
//	
	/**
	 * Cierra todas las conexiones del pool pero no mata los driverds
	 */
	public synchronized void killAllFreeConnections() {
		PssLogger.logDebug("try killing free connection in pool "+sName);
		JIterator<ConnectionsFree> oIterator = this.oFreeConnections.getIterator();
		while (oIterator.hasMoreElements()) {
			ConnectionsFree oConnection = oIterator.nextElement();
			this.closeConnection(oConnection.conn);
		}
		this.oFreeConnections.removeAllElements();
		PssLogger.logDebug("Killing free connection in pool "+sName);
	}

	/**
	 * Cierra todas las conexiones del pool
	 */
	public synchronized void release() {
//		PssLogger.logDebug("try release connection in pool "+sName);
		this.killAllFreeConnections();
		this.oDriverImpl.releaseDrivers();
		PssLogger.logDebug("release connection in pool "+sName);
	}

	/**
	 * Cierra una conexión.
	 * Antes de cerrarla siempre realiza un commit. Hecho basicamente para ODBC,
	 * porque sino no cierra la conexión y el proceso queda vivo.
	 **/
	private synchronized void closeConnection(Connection zConnection) {
		try {
//			PssLogger.logDebug("try closing connection in pool " + sName);
			// OJO. ¿Esto no va en freeConnection()?
			if (!zConnection.getAutoCommit())
				zConnection.commit(); // agregado para que cierre la conexión - ODBC Problem
			zConnection.close();
			PssLogger.logDebug("Closing connection in pool " + sName);
		} catch (SQLException e) {
			PssLogger.logError("Could not close connection in pool^ " + sName + " [" + e.getMessage() + "]");
		}
	}

	/**
	 * Crea una nueva conexión
	 */
	public Connection newConnection() throws Exception {
    JPssConnection oConnection = null;
//		PssLogger.logDebug("trying to create a new connection in pool ("+this.getPoolStatus()+")");
    try {
      JBaseJDBC oDatabase = ( JBaseJDBC ) JBDatos.GetBases().getDatabaseByName( this.sName );
      oConnection = this.oDriverImpl.createConnection( oDatabase.isReportDatabase() );
    } catch( JDatabaseNotFound e ) {
      oConnection = this.oDriverImpl.createConnection(false);
    }
		PssLogger.logDebug("Created, new connection in pool ("+this.getPoolStatus()+")");
		return oConnection.getConnection();
	}

}
