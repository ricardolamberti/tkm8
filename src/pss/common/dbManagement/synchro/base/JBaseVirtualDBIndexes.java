/**
 * 
 */
package  pss.common.dbManagement.synchro.base;

import pss.common.dbManagement.synchro.JDBClassIndex;
import pss.common.dbManagement.synchro.JDBClassIndexes;
import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

/**
 * 
 *
 */
public abstract class JBaseVirtualDBIndexes {
	// Declaracion de attributos
	
	/**
	 * Nombre de la tabla a la que pertenecen los indices contenidos en esta clase
	 */
	protected String tableName;
	
	/**
	 * 
	 */
	protected JMap<String, JBaseVirtualDBIndex> indexesMap = null;
	

	/**
	 * Retorna el nombre de la clase que implementan leen y manejan los indices de
	 * la base de datos. El nombre de la clase depende del motor de base de datos
	 * configurado en el archivos pss.ini
	 * 
	 * @return Nombre de la clase que lee todos los indices.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getVirtualDBIndexesImpl(); // .getMetadataIndexesImpl();
		return Class.forName(sClassName);
	}

	/**
	 * Crea una instancia de la clase que va a manejar los indices dependiendo del
	 * motor de base de datos configurado en el archivo pss.ini
	 * 
	 * @return Instancia de una clase derivada de JDBBaseIndices
	 * @throws Exception
	 */
	public static JBaseVirtualDBIndexes VirtualCreate() throws Exception {
		JBaseVirtualDBIndexes oIndex = (JBaseVirtualDBIndexes) JBaseVirtualDBIndexes.VirtualClass().newInstance();
		return oIndex;
	}
	
	/**
	 * @param indexName 
	 * @return
	 * @throws Exception
	 */
	public JBaseVirtualDBIndex getFieldByName(String indexName) throws Exception {
		if (this.indexesMap == null || this.indexesMap.isEmpty()) {
			return null;
		}
	
		if (indexName == null || indexName.isEmpty()) {
			return null;
		}
		
		if (this.indexesMap.containsKey(indexName) == false) {
			return null;
		}

		JBaseVirtualDBIndex virtualDBIndex;
		
		virtualDBIndex = this.indexesMap.getElement(indexName);
		return virtualDBIndex;
	}	
	
	/**
	 * Llena los datos del campo en base a lo leido de las tablas de sistema del motor de la base de
	 * datos
	 * 
	 * @param systemDBIndexes 
	 * @throws Exception
	 */
	public void processSystemDBIndex(JBaseSystemDBIndexes systemDBIndexes) throws Exception {
		JIterator<JBaseSystemDBIndex> systemDBIndexesIt;
		
		JBaseSystemDBIndex systemDBIndex = null;
		JBaseVirtualDBIndex virtualDBIndex;
		
		try {
			systemDBIndexesIt = systemDBIndexes.getIndexesIterator();
			while(systemDBIndexesIt.hasMoreElements()) {
				systemDBIndex = systemDBIndexesIt.nextElement();
				virtualDBIndex = JBaseVirtualDBIndex.VirtualCreate();
				virtualDBIndex.processSystemDBIndex(systemDBIndex);
				checkIndexesMap();
				this.indexesMap.addElement(virtualDBIndex.indexName, virtualDBIndex);
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "Error procesando campo :" + systemDBIndex.getIndexName());
		}
	}
	
	/**
	 * Llena los datos del campo en base a lo leido de la clase que representa un campo

	 * @param classIndexes 
	 * @throws Exception
	 */
	public void processClassIndexes(JDBClassIndexes classIndexes) throws Exception {
		JIterator<JDBClassIndex> classIndexIt;
		JDBClassIndex classIndex = null;
		classIndexIt = classIndexes.getDBFieldsIterator();
		JBaseVirtualDBIndex virtualDBIndex;
		
		try {
			while(classIndexIt.hasMoreElements()) {
				classIndex = classIndexIt.nextElement();
				virtualDBIndex = JBaseVirtualDBIndex.VirtualCreate();
				virtualDBIndex.processClassIndex(classIndex);
				checkIndexesMap();
				this.indexesMap.addElement(virtualDBIndex.indexName, virtualDBIndex);
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "Error procesando campo :" + classIndex.getIndexName());
		}		
	}

	/**
	 * @param dbSqlActionList
	 * @throws Exception
	 */
	public void getCreateIndexesActionList(JDBSqlActionList dbSqlActionList) throws Exception {
		JIterator<JBaseVirtualDBIndex> virtualDBIndexIterator; 
		JDBSqlAction sqlAction;
		JBaseVirtualDBIndex virtualDBIndex;
		String sqlActionReason;
		
		checkIndexesMap();
		virtualDBIndexIterator = this.indexesMap.getValueIterator();
			
		while (virtualDBIndexIterator.hasMoreElements()) {
			virtualDBIndex = virtualDBIndexIterator.nextElement();
			sqlAction = new JDBSqlAction();
			
			if (virtualDBIndex.isPrimaryKey()) {
				sqlAction.setAction(JDBSqlAction.ADD_PRIMARY_KEY);
				sqlAction.setSql(virtualDBIndex.generateCreatePrimaryKeySQL(this.tableName));
				sqlActionReason = "La Primary Key [" + virtualDBIndex.getIndexName() + "] no existe en la tabla actual";
			} else {
				sqlAction.setAction(JDBSqlAction.ADD_INDEX);
				sqlAction.setSql(virtualDBIndex.generateCreateIndexSQL(this.tableName));
				sqlActionReason = "El indice [" + virtualDBIndex.getIndexName() + "] no existe en la tabla actual";
			}
			
			PssLogger.logDebug(sqlActionReason);
			dbSqlActionList.addSqlActionReason(sqlActionReason);				
			dbSqlActionList.addAction(sqlAction);
		} // end while
	}
	
	/**
	 * @param slaveVirtualDBIndexes
	 * @param sqlActionList
	 * @throws Exception
	 */
	public void getIndexesModificationActionList(JBaseVirtualDBIndexes slaveVirtualDBIndexes, JDBSqlActionList sqlActionList) throws Exception {
		JBaseVirtualDBIndex masterVirtualDBIndex;
		JBaseVirtualDBIndex slaveVirtualDBIndex;
		JIterator<JBaseVirtualDBIndex> virtualDBIndexIterator;
		JDBSqlAction sqlAction;
		String sql;
		String sqlActionReason;
		
		checkIndexesMap();
		virtualDBIndexIterator = this.indexesMap.getValueIterator();
		
		// Primero comparo los indices que estan en la tabla Master con los campos que vienen en la tabla Slave 
		while (virtualDBIndexIterator.hasMoreElements()) {
			masterVirtualDBIndex = virtualDBIndexIterator.nextElement();
			// sqlAction = null;
			sqlAction = new JDBSqlAction();
			
			slaveVirtualDBIndex = slaveVirtualDBIndexes.getFieldByName(masterVirtualDBIndex.getIndexName());
			
			if (slaveVirtualDBIndex == null) {
				if (masterVirtualDBIndex.isPrimaryKey()) {
					// RJL Esto no anda bien
					sql = "-- WARNING "+masterVirtualDBIndex.generateCreatePrimaryKeySQL(this.tableName);
					sqlAction.setAction(JDBSqlAction.ADD_PRIMARY_KEY);
					sqlActionReason = "La Primary Key [" + masterVirtualDBIndex.getIndexName() + "] difiere de la actual";
				} else {
					sql = masterVirtualDBIndex.generateCreateIndexSQL(this.tableName);
					sqlAction.setAction(JDBSqlAction.ADD_INDEX);
					sqlActionReason = "El indice [" + masterVirtualDBIndex.getIndexName() + "] difiere de la actual";
				}
				sqlAction.setSql(sql);
				sqlActionList.addAction(sqlAction);
				PssLogger.logDebug(sqlActionReason);
				sqlActionList.addSqlActionReason(sqlActionReason);				
				continue;
			}
			
			if (masterVirtualDBIndex.getModifyIndexAction(this.tableName, slaveVirtualDBIndex, sqlAction)) {
				sqlActionList.addAction(sqlAction);
			}
		} // end while
			
		// Ahora busco los indices que estan en la tabla slave q no estan en la tabla Master para borrarlos
		if (slaveVirtualDBIndexes.indexesMap != null && slaveVirtualDBIndexes.indexesMap.isEmpty() == false) {
			virtualDBIndexIterator = slaveVirtualDBIndexes.indexesMap.getValueIterator();
			
			while(virtualDBIndexIterator.hasMoreElements()) {
				slaveVirtualDBIndex = virtualDBIndexIterator.nextElement();
				
				if (this.indexesMap.containsKey(slaveVirtualDBIndex.getIndexName()) == false) {
					sqlAction = new JDBSqlAction();
					if (slaveVirtualDBIndex.isPrimaryKey()) {
						sqlAction.setAction(JDBSqlAction.DROP_PRIMARY_KEY);
						sql = "-- WARNING "+slaveVirtualDBIndex.generateDropPrimaryKeySQL(this.tableName);
					} else {
						sqlAction.setAction(JDBSqlAction.DROP_INDEX);
						sql = "-- WARNING "+slaveVirtualDBIndex.generateDropIndexSQL(this.tableName);
					}
					sqlAction.setSql(sql);
					sqlActionList.addAction(sqlAction);
				}
			} // end while
		}
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 
	 */
	protected void checkIndexesMap() {
		if (indexesMap == null) {
			indexesMap = JCollectionFactory.createMap();
		}
	}
	
//// ----------------------------------------------------------------------------------------------------------------
//// ----------------------------------------------------------------------------------------------------------------
//// ----------------------------------------------------------------------------------------------------------------
//// ----------------------------------------------------------------------------------------------------------------
//// ----------------------------------------------------------------------------------------------------------------	
	
//	/**
//	 * Lista con los nombres de todos los indices de la tabla
//	 */
//	protected JList<String> oIndexesNames;
//	
//
//	// Declaracion de metodos abstractos
//	/**
//	 * Retorna el SQL con el que se van a obtener los nombres de todos los indices
//	 * de una base de datos.
//	 * 
//	 * 
//	 * @return SQL para leer los nombres de los indices
//	 * @throws Exception
//	 */
//	protected abstract String getSQLIndexes() throws Exception;
//
//	/**
//	 * Retorna el SQL con el que se van a obtener el nombre de la primary key de
//	 * una tabla
//	 * 
//	 * 
//	 * @return SQL para leer la primary key de una tabla
//	 * @throws Exception
//	 */
//	protected abstract String SQLToGetPKs() throws Exception;
//
//	
//	/**
//	 * Retorna el nombre del campo que se espera del que se espera obtener el nombre del indice 
//	 * relacionado con la tabla configurada.
//	 * El valor retornado por este metodo depende de la implementacion de cada motor de base
//	 * de datos
//	 * 
//	 * @return Nombre del campo que tiene el nombre del indice
//	 */
//	protected abstract String campoWithNames();
//	
//	/**
//	 * @throws Exception
//	 */
//	public JDBBaseIndices() throws Exception {
//	}
//
//	/**
//	 * Retorna el nombre de la tabla. En este caso retorna siempre vacio porque es
//	 * una implementacion virtual de un JRecords
//	 * 
//	 * @return Siempre un String vacio
//	 * @see pss.core.services.records.JRecords#GetTable()
//	 */
//	@Override
//	public String GetTable() {
//		return "";
//	}
//
//	/**
//	 * Retorna el tipo de clase que almacena como Collection
//	 * 
//	 * @return Retorna un JDBBaseIndice o cualquier clase derivada de esta
//	 * @see pss.core.services.records.JRecords#getBasedClass()
//	 */
//	@Override
//	public Class<? extends JBaseSystemDBIndex> getBasedClass() {
//		return JBaseSystemDBIndex.class;
//	}
//
//	
//	/**
//	 * Lee todos los indices en base a la tabla configurada como filtro. 
//	 * Para ello hay que llamar previamente la metodo {@link pss.core.services.records.JBaseRecord#addFilter addFilter} 
//	 * utilizando el parametro "table_name" para indicar el nombre de la tabla al cual estan relacionados
//	 * los indices
//	 * 
//	 */
//	@Override
//	public boolean readAll() throws Exception {
//		this.oIndexesNames = this.getIndexesNames();
//		return this.loadDBIndexesInformation(this.oIndexesNames);
//	}
//
//	// --------------------------------------------------------------------------------
//	// //
//	// LEE LOS NOMBRES DE TODOS LOS INDICES DE LA TABLA Y ADEMAS LLENA LAS
//	// COLUMNAS QUE //
//	// CORRESPONDE A CADA INDICE //
//	// --------------------------------------------------------------------------------
//	// //
//	/**
//	 * Lee la informacion de cada uno de los indices indicados en la lista pasada como parametro
//	 * 
//	 * @param pIndexesNames - Lista con los nombres de los indices a buscar y a cargar en memoria 
//	 * @return true si se pudieron cargar los indices. false si la lista esta vacia o es nula
//	 * @throws Exception
//	 */
//	public boolean loadDBIndexesInformation(JList<String> pIndexesNames) throws Exception {
//		if (pIndexesNames == null || pIndexesNames.isEmpty())
//			return false;
//		int iQty = 0;
//		JIterator<String> oIterator = pIndexesNames.getIterator();
//
//		this.setStatic(true);
//		while (oIterator.hasMoreElements()) {
//			JBaseSystemDBIndex oIndice = JBaseSystemDBIndex.VirtualCreate();
//			oIndice.setIndexName(oIterator.nextElement());
//			oIndice.obtenerIndiceColumns();
//			oIndice.setTabla(getFilterValue("table_name"));
//			addItem(oIndice);
//			oIndice = null;
//			iQty++;
//		} // end while
//		//iCantidadIndices = iQty;
//		this.closeRecord();
//		return true;
//	}	
//	
//	/**
//	 * Ejecuta el SQL generado por el metodo {@link #getSQLIndexes() getSQLIndexes} para leer 
//	 * los nombres de todos los indices relacionados con la tabla configurada.
//	 * La lista la retorna en forma de un JList<String>. 
//	 * 
//	 * @return Lista de los nombres de todos los indices relacionados con al tabla configurada. 
//	 * Retorna null si no encuentra ninguno
//	 * @throws Exception
//	 */
//	@SuppressWarnings("deprecation")
//	public JList<String> getIndexesNames() throws Exception {
//		String sql = "";
//		boolean bFound = false;
//
//		JList<String> oIndicesNames = null;
//		sql = this.getSQLIndexes();
//
//		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
//		oReg.ExecuteQuery(sql);
//
//		while (oReg.next()) {
//			if (oIndicesNames == null) {
//				oIndicesNames = JCollectionFactory.createList();
//			}
//			oIndicesNames.addElement(oReg.CampoAsStr(this.campoWithNames()));
//			bFound = true;
//		}
//		oReg.close();
//		sql = null;
//		oReg = null;
//
//		return bFound ? oIndicesNames : null;
//	}
//
//
//	@Deprecated
//	protected int iCantidadIndices = 0;
//
//	/**
//	 * @return
//	 */
//	// @Deprecated
//	public int getIndicesQty() {
//		//return iCantidadIndices;
//		return this.getStaticItems().size();
//	}
//
//
//	/**
//	 * @param bContinueIfError
//	 * @throws Exception
//	 */
//	@Deprecated
//	public void createIndexes(boolean bContinueIfError) throws Exception {
//		// if (iCantidadIndices == 0) {
//		if (getIndicesQty() == 0) {
//			return;
//		}
//
//		firstRecord();
//		while (nextRecord()) {
//			JBaseSystemDBIndex oIndice = getRecord();
//			try {
//				oIndice.createIndex();
//			} catch (Exception e) {
//				if (!bContinueIfError) {
//					throw (e);
//				}
//
//				PssLogger.logInfo("Error al crear el indice [" + oIndice.getIndexName() + "]");
//				continue;
//			}
//		} // end while
//	}
//
//	/**
//	 * LLAMAR A READALL ANTES DE DROPEAR LOS INDICES. NO HACE NADA POR EL MOMENTO
//	 * 
//	 * @throws Exception
//	 */
//	@Deprecated
//	public void dropIndices() throws Exception {
//		if (this.getIndicesQty() == 0) { // iCantidadIndices == 0) {
//			return;
//		}
//		JList<String> oSQL = JCollectionFactory.createList();
//		this.firstRecord();
//		while (this.nextRecord()) {
//			JBaseSystemDBIndex oIndex = getRecord();
//			oSQL.addElement(oIndex.dropIndex());
//		}
//		JIterator<String> oIt = oSQL.getIterator();
//		while (oIt.hasMoreElements()) {
//			String sSql = oIt.nextElement();
//			JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
//			oReg.executeTransaction(sSql);
//			Thread.sleep(500);
//		}
//	}
//

}
