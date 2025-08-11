package pss.core.data.interfaces.sentences;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pss.JPath;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.files.JStreamFile;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

/**
 * CJG - Comenté los métodos GetBase y SetBase porque estaban sobreescritos en
 * JBaseRegistro
 * 
 * @version 1.0
 */

public class JRegJDBC extends JRegSQL {

	protected ResultSet oResultSet;
	protected Object oObject;
	protected String oString;
	protected boolean bEndOfFile = false;
	protected Statement oStatement = null;

	public JRegJDBC() {
	}

	public ResultSet GetResultSet() {
		return oResultSet;
	}

	/**
	 * Initializes the query
	 */
	@Override
	public void QueryInit() throws Exception {
	}

	/**
	 * Executes the query
	 */
	@Override
	public void Execute(String zSQL) throws Exception {
		this.sSQL = zSQL;
		// JDebugPrint.logDebugSQL("Execute SQL = " + sSQL);
		resexec = this.QueryExec();
	}

	int resexec = 0;

	public int getResult() {
		return resexec;
	}

	/**
	 * Involve the action of begin, commin and rollback in case of error. Also,
	 * closes the statement
	 * 
	 * @param zSql
	 * @throws Exception
	 */
	@Override
	public void executeTransaction(String zSql) throws Exception {
		try {
			this.getDatabase().beginTransaction();
			this.Execute(zSql);
		} catch (Exception e) {
			this.getDatabase().rollback();
			throw e;
		}
	}

	@Override
	public void executeWithoutTransaction(String zSql) throws Exception {
		this.getDatabase().GetConnection().setAutoCommit(true);
		try {
			this.executeStatement(zSql);
		} finally {
			this.getDatabase().GetConnection().setAutoCommit(false);
		}
	}

	@Override
	public void executeQueryWithoutTransaction(String sql) throws Exception {
		this.getDatabase().GetConnection().setAutoCommit(true);
		try {
			PssLogger.logDebugSQL("(" + this.getBaseJDBC().countOpenCursors() + ") Execute SQL = " + sql);
			this.createStatement().execute(sql);
		} catch (SQLException e) {
			this.manageSQLException(e);
		} finally {
			this.getDatabase().GetConnection().setAutoCommit(false);
		}
	}

	/**
	 * Ejecuta una sentencia SELECT
	 */
	@Override
	public void ExecuteQuery(String zSQL) throws Exception {
		sSQL = zSQL;
		QueryOpen();
	}

	/**
	 * Ejecuta una sentencia SELECT
	 * 
	 * @throw JConnectionBroken En caso que no haya conexión con la base
	 */
	protected Statement getQueryOpenStatement(JBaseJDBC oDatabaseImpl) throws Exception {
		return null;
	}

	@Override
	protected void QueryOpen() throws Exception {
		try {
			oStatement = getQueryOpenStatement(this.getBaseJDBC());
			PssLogger.logDebugSQL("(" + this.getBaseJDBC().countOpenCursors() + ") Open SQL = " + sSQL);
			long startTime = System.currentTimeMillis();
			oResultSet = oStatement.executeQuery(sSQL);
			long time = System.currentTimeMillis() - startTime;
			if (time > 20)
				PssLogger.logDebugSQL("Low Query = " + time);
			this.getBaseJDBC().addTransactionCursors(oStatement);
		} catch (SQLException e) {
			this.manageSQLException(e);
		}
	}

	/**
	 * Ejecuta una sentencia UPDATE, DELETE, INSERT, etc.
	 * 
	 * @throw JConnectionBroken En caso que no haya conexión con la base
	 */
	@Override
	protected int QueryExec() throws Exception {
		if (!this.getBaseJDBC().isTransactionInProgress()) {
			JExcepcion.SendError("Sentencia fuera de transacción");
		}
		return executeStatementWithResult(sSQL);
	}

	private void needUpdateCatalog() throws Exception {
		if (this.oDato.getCatalog() != null && !this.isUpdatingCatalog()) {
			this.updateCatalog();
		}
	}

	private void executeStatement(String zSql) throws Exception {
		try {
			PssLogger.logDebugSQL("(" + this.getBaseJDBC().countOpenCursors() + ") Execute SQL = " + zSql);
			long startTime = System.currentTimeMillis();
			this.createStatement().executeUpdate(zSql);
			// this.needUpdateCatalog(); don't work!
			long time = System.currentTimeMillis() - startTime;
			if (time > 20)
				PssLogger.logDebugSQL("Low Exec = " + time);
		} catch (SQLException e) {
			manageSQLException(e);
		}
	}

	private int executeStatementWithResult(String zSql) throws Exception {
		int result = -1;
		try {
			PssLogger.logDebugSQL("(" + this.getBaseJDBC().countOpenCursors() + ") Execute SQL = " + zSql);
			long startTime = System.currentTimeMillis();
			result = this.createStatement().executeUpdate(zSql);
			// this.needUpdateCatalog(); don't work!
			long time = System.currentTimeMillis() - startTime;
			if (time > 20)
				PssLogger.logDebugSQL("Low Exec = " + time);
		} catch (SQLException e) {
			manageSQLException(e);
		}
		return result;
	}

	private Statement createStatement() throws Exception {
		if (oStatement != null)
			JExcepcion.SendError("Statement ya creado");
		oStatement = this.getBaseJDBC().GetConnection().createStatement();
		return oStatement;
	}

	/**
	 * Get the next row from a recordset
	 */
	@Override
	public boolean next() throws Exception {
		if (oResultSet == null)
			return false;
		try {
			bEndOfFile = !oResultSet.next();
			if (bEndOfFile)
				this.close();
		} catch (SQLException e) {
			manageSQLException(e);
		}
		return !bEndOfFile;
	}

	/**
	 * Closes the recordset
	 */
	@Override
	public void close() throws Exception {
		if (this.oResultSet != null) {
			try {
				this.oResultSet.close();
			} catch (SQLException e) {
			}
			this.oResultSet = null;
		}
		if (this.oStatement != null) {
			try {
				this.getBaseJDBC().removeTransactionCursor(oStatement);
				this.oStatement.close();
			} catch (SQLException e) {
			}
			this.oStatement = null;
		}
	}

	@Override
	public void first() throws Exception {
		try {
			oResultSet.first();
		} catch (SQLException e) {
			manageSQLException(e);
		}
	}

	// --------------------------------------------------------------------------
	// //
	// Obtengo siguiente registro de un recordset
	// --------------------------------------------------------------------------
	// //
	@Override
	public boolean EOF() throws Exception {
		return bEndOfFile;
	}

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como String
	// --------------------------------------------------------------------------
	// //
	@Override
	public String CampoAsStr(String zCampo) throws Exception {
		String oStr = oResultSet.getString(zCampo);
		if (oResultSet.wasNull())
			return null;
		return oStr;
	} // CampoAsStr

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como long
	// --------------------------------------------------------------------------
	// //
	@Override
	public Long CampoAsLong(String zCampo) throws Exception {
		long iRes = oResultSet.getLong(zCampo);
		if (oResultSet.wasNull())
			return null;
		return new Long(iRes);
	} // CampoAsLong

	public String fMod(String zFieldname, int zDivisor) throws Exception {
		return null;
	}

	public String ArmarAutonumericRecalcule() throws Exception {
		return null;
	}

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como int
	// --------------------------------------------------------------------------
	// //
	@Override
	public Integer CampoAsInt(String zCampo) throws Exception {
		int iRes = oResultSet.getInt(zCampo);
		if (oResultSet.wasNull())
			return null;
		return new Integer(iRes);
	} // CampoAsInt

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como float
	// --------------------------------------------------------------------------
	// //
	@Override
	public Double CampoAsFloat(String zCampo) throws Exception {
		double iRes = oResultSet.getDouble(zCampo);
		if (oResultSet.wasNull())
			return null;
		return new Double(iRes);
	} // CampoAsFloat

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como Date
	// --------------------------------------------------------------------------
	// //
	@Override
	public java.util.Date CampoAsDate(String zCampo) throws Exception {
		java.util.Date oDate = oResultSet.getDate((zCampo));
		if (oResultSet.wasNull())
			return null;
		return oDate;
	} // CampoAsDate

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como DateTime
	// --------------------------------------------------------------------------
	// //
	@Override
	public java.sql.Time CampoAsTime(String zCampo) throws Exception {
		java.sql.Time oTime = oResultSet.getTime((zCampo));
		if (oResultSet.wasNull())
			return null;
		return oTime;
	} // CampoAsDate

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como Object
	// --------------------------------------------------------------------------
	// //
	@Override
	public Object CampoAsObject(String zCampo) throws Exception {
		Object oObj = oResultSet.getObject((zCampo));
		if (oResultSet.wasNull())
			return null;
		return oObj;
	} // CampoAsObject
	
	
	@Override
	public String CampoAsBlob(String zCampo) throws Exception {
		String oStr = oResultSet.getString(zCampo);
		if (oResultSet.wasNull())
			return null;

		return oStr;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Valida si la excepcion es por fecha fuera de rango.
	 * 
	 * @author CJG
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	protected void manageSQLException(SQLException zSQLExe) throws Exception {
		this.close();
		checkSpecialErrors(zSQLExe);
		registerDatabaseEvent(zSQLExe);
		((JBaseJDBC) this.getDatabase()).throwConnectionBroken(zSQLExe);
	}

	private void registerDatabaseEvent(SQLException exe) {
		try {
			if (BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "SQLERR", "N").equalsIgnoreCase("S")) {
				String msg = exe.getErrorCode() + " - " + exe.getMessage();
				JStreamFile file = new JStreamFile();
				file.CreateNewFile(JPath.PssPathData() + "/sqlerr.log", false);
				file.WriteLn(JDateTools.CurrentDateTime() + " <-> " + JAplicacion.GetApp().getLogName() + " <-> Error: "
						+ msg);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exe.printStackTrace(pw);
				String sStackMsg = sw.toString();
				file.WriteLn(sStackMsg);
				file.Close();

			}
		} catch (Exception ignored) {
		}
	}

	protected void checkSpecialErrors(SQLException zSQLExe) throws Exception {
	}

	/**
	 * Obtiene una nueva Statement luego de intentar una nueva conexión con la Base
	 * de datos.
	 * 
	 * @return la statement nueva
	 * @throw JConnectionBroken si no pudo establecer una nueva conexión
	 */
	/*
	 * private Statement getNewStatement() throws Exception { if
	 * (!JBDatos.retryEstablishConnection()) { throw new
	 * JConnectionBroken("No se puede reestablecer la conexión"); } return
	 * getBaseJDBC().GetConnection().createStatement(); }
	 */
	public JBaseJDBC getBaseJDBC() {
		return (JBaseJDBC) this.getDatabase();
	}

	public JIterator<String> getFieldNameIterator() throws Exception {
		JList<String> vItems;
		vItems = JCollectionFactory.createList();
		for (int i = 1; i <= oResultSet.getMetaData().getColumnCount(); i++) {
			vItems.addElement(oResultSet.getMetaData().getColumnName(i));
		}
		return vItems.getIterator();
	}

}
