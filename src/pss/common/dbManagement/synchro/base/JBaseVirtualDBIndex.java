/**
 * 
 */
package pss.common.dbManagement.synchro.base;

import pss.common.dbManagement.synchro.JDBClassIndex;
import pss.common.dbManagement.synchro.JDBClassIndexField;
import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

/**
 * 
 * 
 */
public abstract class JBaseVirtualDBIndex {

	/**
	 * Nombre del Indice
	 */
	protected String indexName = new String("");

	/**
	 * Lista con el nombre de las columnas que conforman el indice
	 */
	protected JList<String> indexColumns = null;

	/**
	 * Indica si el indice es primary Key de la tabla
	 */
	protected boolean indexIsPrimaryKey = false;

	/**
	 * Determina si el indice funciona como clustered o no.
	 */
	// TODO - Queda pendiente evaluar
	protected boolean indexIsClustered = false;

	/**
	 * Indica si la clave del indice es unica
	 */
	protected boolean indexIsUnique = true;

	/**
	 * Retorna el nombre de la clase que implementa un indice de la base de datos. El nombre de la
	 * clase depende del motor de base de datos configurado en el archivos pss.ini
	 * 
	 * @return Nombre de la clase que implementa un indice
	 * @throws Exception
	 */
	public static JBaseVirtualDBIndex VirtualCreate() throws Exception {
		JBaseVirtualDBIndex oIndex = (JBaseVirtualDBIndex) JBaseVirtualDBIndex.VirtualClass().newInstance();
		return oIndex;
	}

	/**
	 * Crea una instancia de la clase que implementa un indice dependiendo del motor de base de datos
	 * configurado en el archivo pss.ini
	 * 
	 * @return Instancia de una clase derivada de JDBBaseIndice
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getVirtualDBIndexImpl(); // .getMetadataIndexImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateCreatePrimaryKeySQL(String tableName) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateCreateIndexSQL(String tableName) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateDropPrimaryKeySQL(String tableName) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateDropIndexSQL(String tableName) throws Exception;

	/**
	 * @param systemDBIndex
	 */
	public void processSystemDBIndex(JBaseSystemDBIndex systemDBIndex) {
		checkIndexColumns();
		JIterator<String> columnNameIterator;
		
		columnNameIterator = systemDBIndex.getIndexColumns().getIterator();
		
		while (columnNameIterator.hasMoreElements()) {
			this.indexColumns.addElement(columnNameIterator.nextElement().toUpperCase());
		} // end while
		
		// this.indexColumns.addElements(systemDBIndex.getIndexColumns());
		this.indexName = systemDBIndex.getIndexName().toUpperCase();
		this.indexIsPrimaryKey = systemDBIndex.isIndexIsPrimaryKey();
		this.indexIsUnique = systemDBIndex.isIndexIsUnique();
		this.indexIsClustered = systemDBIndex.isIndexClustered();
	}

	protected void checkIndexColumns() {
		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		}
	}

	/**
	 * @param dbClassIndex
	 */
	public void processClassIndex(JDBClassIndex dbClassIndex) {
		JIterator<JDBClassIndexField> oIt;
		JDBClassIndexField indexField;

		oIt = dbClassIndex.getIndexFieldIterator();

		while (oIt.hasMoreElements()) {
			indexField = oIt.nextElement();
			checkIndexColumns();
			this.indexColumns.addElement(indexField.getFieldName().toUpperCase());
		} // end while

		this.indexName = dbClassIndex.getIndexName();
		this.indexIsPrimaryKey = dbClassIndex.isIndexIsPrimaryKey();
		this.indexIsUnique = dbClassIndex.isIndexIsUnique();
		this.indexIsClustered = dbClassIndex.isIndexIsClustered();
	}

	/**
	 * @return
	 */
	public boolean isPrimaryKey() {
		return this.indexIsPrimaryKey;
	}

	/**
	 * @return
	 */
	public String getIndexName() {
		return this.indexName;
	}

	/**
	 * Compara el campo actual con el campo que se pasa por parametro. Si los campos son diferentes
	 * retorna el sql action con el alter table correspondiente para actualizar el campo al campo
	 * actual
	 * 
	 * @param slaveVirtualDbIndex
	 * @param sqlAction
	 * @return true si los campos son diferentes.
	 * @throws Exception
	 */
	public boolean getModifyIndexAction(String tableName, JBaseVirtualDBIndex slaveVirtualDbIndex, JDBSqlAction sqlAction) throws Exception {
		boolean bHasDiff = false;

		JIterator<String> indexColumnsIterator;
		String columnName;

		// Busco campos nuevos en el indice Master que no esten en el Slave
		indexColumnsIterator = this.indexColumns.getIterator();
		while (indexColumnsIterator.hasMoreElements()) {
			columnName = indexColumnsIterator.nextElement();

			if (slaveVirtualDbIndex.indexColumns.containsElement(columnName) == false) {
				PssLogger.logDebug("Nuevo campo en el indice Nuevo ["+ columnName +"]");
				bHasDiff = true;
			}
		}

		// Busco campos que ya no esten en el indice slave
		indexColumnsIterator = slaveVirtualDbIndex.indexColumns.getIterator();
		while (indexColumnsIterator.hasMoreElements()) {
			columnName = indexColumnsIterator.nextElement();

			if (this.indexColumns.containsElement(columnName) == false) {
				PssLogger.logDebug("El Campo en el indice actual ["+ columnName +"]");
				bHasDiff = true;
			}
		}
	
		if (this.indexIsUnique != slaveVirtualDbIndex.indexIsUnique) {
			PssLogger.logDebug("Cambio la condicion de indice unico en el nuevo [" + this.indexIsUnique + "] vs en el actual [" + slaveVirtualDbIndex.indexIsUnique + "]");
			bHasDiff = true;			
		}

		if (bHasDiff == false) {
			return false;
		}

		// sqlAction = new JDBSqlAction();
		if (this.indexIsPrimaryKey) {
			sqlAction.setAction(JDBSqlAction.ADD_PRIMARY_KEY);
			sqlAction.setPreSql(slaveVirtualDbIndex.generateDropPrimaryKeySQL(tableName));
			sqlAction.setSql(slaveVirtualDbIndex.generateCreatePrimaryKeySQL(tableName));
		} else {
			sqlAction.setAction(JDBSqlAction.ADD_INDEX);
			sqlAction.setPreSql(slaveVirtualDbIndex.generateDropIndexSQL(tableName));
			sqlAction.setSql(slaveVirtualDbIndex.generateCreateIndexSQL(tableName));
		}

		// TODO - Faltan los pre y los postsql
		// TODO - Falta evaluar si generar el indice es valido o tengo que hacer alguna transformacion o
		// correr algun sql

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	//	
	// /**
	// * Retorna el nombre de la columna de la vista de sistema que contiene el nombre del primary key
	// de la tabla
	// *
	// * @return Nombre de la columna de la vista de sistema que contiene el nombre del primary key de
	// la tabla
	// */
	// protected abstract String getColumnNameOfPrimaryKey();
	//	
	// /**
	// * Retorna sentecia SQL que borrar un indice. El valor retornado depende de
	// * la implementacion del motor de la base de datos
	// *
	// * @return Sentencia SQL que borrar un indice
	// * @throws Exception
	// */
	// protected abstract String addDropSentence() throws Exception;
	//	
	// /**
	// *
	// *
	// * @return
	// * @throws Exception
	// */
	// protected abstract String getIndexStatement() throws Exception;
	//	
	// /**
	// *
	// * Busca en la clase los campos clave y genera el codigo SQL para crear un indice unico
	// * basado en esta informacion
	// *
	// * @return
	// * @throws Exception
	// */
	// public abstract String createMainIndex(JDBClassTable pTableClass) throws Exception;
	//	
	// /**
	// * Constructor
	// *
	// * @throws Exception
	// */
	// public JBaseSystemDBIndex() throws Exception {
	// }
	//	
	// /**
	// * Setea el nombre de la tabla relacionada con el indice
	// *
	// * @param zTable - Nombre de la tabla
	// * @throws Exception
	// */
	// public void setTabla(String zTable) throws Exception {
	// pTabla=zTable;
	// }
	//	
	// /**
	// * Retorna el nombre de la tabla a la que hace referencia el indice
	// *
	// * @return Nombre de la tabla a la que hace referencia el indice
	// * @throws Exception
	// */
	// public String getTable() throws Exception {
	// return pTabla;
	// }
	//
	// /**
	// * Retorna el nombre del indice
	// *
	// * @return Nombre del indice
	// */
	// public String getIndexName() {
	// return sIndexName;
	// }
	//	
	// /**
	// * Setea el nombre del indice
	// *
	// * @param sName - Nombre del indice
	// */
	// public void setIndexName(String sName) {
	// sIndexName=sName;
	// }
	//	
	// /**
	// * Agrega el nombre de una columna que forma parte del indice
	// *
	// * @param name - Nombre del campo
	// * @throws Exception
	// */
	// public void addColumnName(String name) throws Exception {
	// if (oColNames==null) {
	// oColNames=JCollectionFactory.createList();
	// }
	// oColNames.addElement(name);
	// }
	//	
	// // ----------------------------------------------------------------- //
	// // CODIGOS DE LOS ATRIBUTOS DE LOS INDICES //
	// // ----------------------------------------------------------------- //
	// public static long IGNORE_DUPLICATE_KEY=1;
	// public static long UNIQUE_INDEX=2;
	// public static long UNIQUE_CONSTRAINT=4098;
	// public static long CLUSTERED=16;
	//
	// // ----------------------------------------------------------- //
	// // IGNORE_DUPLICATE_KEY ==========>00000001 //
	// // UNIQUE_INDEX ==========>00000010 //
	// // UNIQUE_CONSTRAINT ==========>00010000_00000010 //
	// // CLUSTERED ==========>00010000 //
	// // ----------------------------------------------------------- //
	//
	//
	// /**
	// * @throws Exception
	// */
	// @Deprecated
	// public void dropPK() throws Exception {
	// if (!hasPrimaryKey()) return;
	// JBaseRegistro oReg=JBaseRegistro.VirtualCreate();
	// oReg.executeTransaction(this.getDropPrimaryKeyStatement());
	// }
	//
	// /**
	// * @return
	// * @throws Exception
	// */
	// public boolean hasPrimaryKey() throws Exception {
	// return this.getPKName()!=null;
	// }
	//
	// /**
	// * Returns the primary key columns
	// * @return
	// * @throws Exception
	// */
	// @SuppressWarnings("deprecation")
	// public JList<String> getColumnsOfPrimaryKeys() throws Exception {
	// if (!hasPrimaryKey()) return null;
	// String sql="";
	// String sColumnName=this.getColumnNameOfPrimaryKey();
	// if (this.spkName!=null&&this.oColNames!=null) {
	// return this.oColNames;
	// }
	// if (this.spkName==null) {
	// this.getPrimaryKeyName();
	// }
	//
	// sql+=this.getPrimaryKeyColumnsStatement();
	//
	// JBaseRegistro oReg=JBaseRegistro.VirtualCreate();
	// oReg.ExecuteQuery(sql);
	//
	// while (oReg.next()) {
	// this.addColumnName(oReg.CampoAsStr(sColumnName));
	// }
	// oReg.close();
	// sql=null;
	// oReg=null;
	// // ---------- //
	// // GET STATUS //
	// // ---------- //
	// if (this.lStatus==NO_STATUS) {
	// this.getStatus();
	// }
	// return this.oColNames;
	// }
	//
	// /**
	// * @return
	// * @throws Exception
	// */
	// public String getPKName() throws Exception {
	// if (this.spkName==null) {
	// return this.getPrimaryKeyName();
	// }
	// return spkName;
	// }
	//
	// /**
	// * @param name
	// * @return
	// * @throws Exception
	// */
	// public String dropIndex(String name) throws Exception {
	// sIndexName=name;
	// return dropIndex();
	// }
	//
	//
	// /**
	// * @return
	// * @throws Exception
	// */
	// public String addIndex() throws Exception {
	// return getIndexStatement();
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public String dropIndex() throws Exception {
	// return addDropSentence();
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void createIndex() throws Exception {
	// String sql=getIndexStatement();
	// JRegJDBC oReg=(JRegJDBC) JBaseRegistro.VirtualCreate();
	//
	// oReg.QueryInit();
	// oReg.executeTransaction(sql);
	// // JBDatos.GetBases().Commit();
	// }
	//
	// // ----------------------------------------------------------------------------------//
	// protected String getIndexFieldName() {
	// return "";
	// }
	//
	// // ----------------------------------------------------------------------------------//
	// protected String getPrimaryKeyField() {
	// return "";
	// }
	//
	// // ----------------------------------------------------------------------------------//
	// public String getPrimaryKeyName() throws Exception {
	// String sql=this.getPrimaryKeyNameStatement();
	// String sCampoOfIndexName=this.getPrimaryKeyField();
	//
	// JBaseRegistro oReg=JBaseRegistro.VirtualCreate();
	// oReg.ExecuteQuery(sql);
	// if (oReg.next()) {
	// this.spkName=oReg.CampoAsStr(sCampoOfIndexName);
	// }
	// oReg.close();
	// // ---------- //
	// // GET STATUS //
	// // ---------- //
	// getStatus();
	// sql=null;
	// oReg=null;
	// return this.spkName;
	// }
	//
	// // ------------------------------------------------------------------------------- //
	// public String getDropPrimaryKeyStatement() {
	// return "ALTER TABLE "+pTabla+" DROP CONSTRAINT "+spkName;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public long getStatus() throws Exception {
	// return lStatus;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void obtenerIndiceColumns() throws Exception {
	// String sql=this.getIndexesColumnNamesStatement();
	// String sCampoOfIndexName=this.getIndexFieldName();
	//
	// JBaseRegistro oReg=JBaseRegistro.VirtualCreate();
	// oReg.ExecuteQuery(sql);
	// while (oReg.next()) {
	// if (oColNames==null) {
	// oColNames=JCollectionFactory.createList();
	// }
	// oColNames.addElement(oReg.CampoAsStr(sCampoOfIndexName));
	// }
	// oReg.close();
	// // ---------- //
	// // GET STATUS //
	// // ---------- //
	// getStatus();
	// oReg=null;
	// sql=null;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public JList<String> getIndexColumns() throws Exception {
	// if (this.oColNames==null) {
	// this.obtenerIndiceColumns();
	// }
	// return oColNames;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isIndexPK() {
	// return spkName!=null;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isUnique() {
	// return false;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setUnique() {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setColNames(JList<String> oNames) {
	// oColNames=oNames;
	// }
	//
	// // ------------------------------------------------------------------------------------- //
	// // FUNCIONES A SOBREESCRIBIR //
	// // ------------------------------------------------------------------------------------- //
	// protected String getPrimaryKeyColumnsStatement() throws Exception {
	// return "";
	// }
	//
	// // ------------------------------------------------------------------------------------- //
	// protected String getPrimaryKeyNameStatement() throws Exception {
	// return "";
	// }
	//
	// // ------------------------------------------------------------------------------------- //
	// protected String getIndexesColumnNamesStatement() throws Exception {
	// return "";
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void ClearColumns() {
	// oColNames=null;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// protected String addClusterWord() {
	// return "";
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// protected String addFillFactorWord() {
	// return "";
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public String basicAlter() {
	// return "ALTER TABLE "+pTabla;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public String getPrimaryKeyStatement() throws Exception {
	// String sql=basicAlter()+" ADD CONSTRAINT ";
	//
	// if (spkName==null) {
	// spkName="PK_"+pTabla;
	// }
	//
	// sql+=spkName+" PRIMARY KEY "+addClusterWord()+" ( ";
	//
	// JIterator<String> oIt=oColNames.getIterator();
	// while (oIt.hasMoreElements()) {
	// String Campo=oIt.nextElement();
	// sql+=Campo;
	// if (oIt.hasMoreElements()) {
	// sql+=",";
	// }
	// } // end while
	// sql+=")";
	// sql+=addFillFactorWord();
	// return sql;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void createPrimaryKey(boolean throwException) throws Exception {
	// if (!hasPrimaryKey()) return;
	// String sql=getPrimaryKeyStatement();
	// try {
	// JRegJDBC oReg=(JRegJDBC) JBaseRegistro.VirtualCreate();
	//
	// oReg.QueryInit();
	// oReg.executeTransaction(sql);
	// // JBDatos.GetBases().Commit();
	// } catch (Exception e) {
	// if (throwException) {
	// throw (e);
	// }
	// }
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isClustered() {
	// return false;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setClustered() {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isUniqueConstraint() {
	// return false;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setUniqueConstraint() {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isUniqueIndex() {
	// return false;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setUniqueIndex() {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public boolean isIgnoreDuplicateKey() {
	// return false;
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setIgnoreDuplicateKey() {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public void setFillFactor(long lFillFactor) {
	// }
	//
	// // -----------------------------------------------------------------------------------//
	// public long getFillFactor() {
	// return NO_STATUS;
	// }
	// // -----------------------------------------------------------------------------------//

}
