package pss.common.dbManagement.synchro.base;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

/**
 * Clase que lee y almancena la informacion de todos los indices relacionados con una tabla fisica
 * de la base de datos.
 * 
 */
// public abstract class JBaseSystemDBIndexes extends JRecords<JBaseSystemDBIndex> {
public abstract class JBaseSystemDBIndexes {
	// Declaracion de attributos

	/**
	 * Lista con los nombres de todos los indices de la tabla
	 */
	protected JList<String> indexesNames;

	/**
	 * Nombre de la tabla con la que estan relacionados los indices
	 */
	protected String tableName;

	/**
	 * Guarda las instancias de los indices
	 */
	protected JMap<String, JBaseSystemDBIndex> indexesMap;

	// Declaracion de metodos abstractos
	/**
	 * Retorna el SQL con el que se van a obtener los nombres de todos los indices de una base de
	 * datos.
	 * 
	 * 
	 * @return SQL para leer los nombres de los indices
	 * @throws Exception
	 */
	protected abstract String getSQLReadIndexesNames() throws Exception;

	/**
	 * Retorna el nombre del campo que se espera del que se espera obtener el nombre del indice El
	 * valor retornado por este metodo depende de la implementacion de cada motor de base de datos
	 * 
	 * @return Nombre del campo que tiene el nombre del indice
	 */
	protected abstract String getNameOfIndexField();

	/**
	 * @throws Exception
	 */
	public JBaseSystemDBIndexes() throws Exception {
	}

	/**
	 * Retorna el nombre de la clase que implementan leen y manejan los indices de la base de datos.
	 * El nombre de la clase depende del motor de base de datos configurado en el archivos pss.ini
	 * 
	 * @return Nombre de la clase que lee todos los indices.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getSystemDBIndexesImpl(); // .getMetadataIndexesImpl();
		return Class.forName(sClassName);
	}

	/**
	 * Crea una instancia de la clase que va a manejar los indices dependiendo del motor de base de
	 * datos configurado en el archivo pss.ini
	 * 
	 * @return Instancia de una clase derivada de JDBBaseIndices
	 * @throws Exception
	 */
	public static JBaseSystemDBIndexes VirtualCreate() throws Exception {
		JBaseSystemDBIndexes oIndex = (JBaseSystemDBIndexes) JBaseSystemDBIndexes.VirtualClass().newInstance();
		return oIndex;
	}

	/**
	 * Setea el nombre de la tabla al que estan relacionados los indices
	 * 
	 * @param tableName
	 *          the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Lee todos los indices en base a la tabla configurada como filtro. Para ello hay que llamar
	 * previamente la metodo {@link #setTableName(String)} para indicar el nombre de la tabla al cual
	 * estan relacionados los indices
	 * 
	 * @throws Exception
	 * 
	 */
	// @Override
	public void readAll() throws Exception {
		// Primero leo la primary Key
		JBaseSystemDBIndex primaryKey = JBaseSystemDBIndex.VirtualCreate();

		if (primaryKey.readPrimaryKeyInformation(this.tableName)) {
			checkIndexesMap();
			this.indexesMap.addElement(primaryKey.getIndexName(), primaryKey);
		}

		// Ahora intento leer los demas indices
		this.readIndexNames();
		this.loadDBIndexesInformation();
	}

	/**
	 * Chequea si el mapa de indices existe, sino lo crea
	 */
	protected void checkIndexesMap() {
		if (this.indexesMap == null) {
			this.indexesMap = JCollectionFactory.createMap();
		}
	}

	/**
	 * Lee la informacion de cada uno de los indices indicados en la lista pasada como parametro
	 * 
	 * @param pIndexesNames -
	 *          Lista con los nombres de los indices a buscar y a cargar en memoria
	 * @throws Exception
	 */
	public void loadDBIndexesInformation() throws Exception {
		if (this.indexesNames == null || this.indexesNames.isEmpty())
			return;

		String indexName;
		JIterator<String> oIterator = this.indexesNames.getIterator();

		while (oIterator.hasMoreElements()) {
			indexName = oIterator.nextElement();
			JBaseSystemDBIndex systemDBIndex = JBaseSystemDBIndex.VirtualCreate();
			systemDBIndex.readIndexInformation(this.tableName, indexName);
			checkIndexesMap();
			this.indexesMap.addElement(systemDBIndex.getIndexName(), systemDBIndex);
		} // end while

	}

	/**
	 * Ejecuta el SQL generado por el metodo {@link #getSQLReadIndexesNames() getSQLReadIndexesNames}
	 * para leer los nombres de todos los indices relacionados con la tabla configurada.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void readIndexNames() throws Exception {
		String sql = "";

		sql = this.getSQLReadIndexesNames();

		if (sql == null || sql.isEmpty()) {
			return;
		}

		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
		oReg.ExecuteQuery(sql);

		while (oReg.next()) {
			if (this.indexesNames == null) {
				this.indexesNames = JCollectionFactory.createList();
			}

			this.indexesNames.addElement(oReg.CampoAsStr(this.getNameOfIndexField()));
		}
		oReg.close();
		sql = null;
		oReg = null;

	}

	/**
	 * Busca un indice entre los leidos de la base de datos y retorna la informacion correspondiente
	 * 
	 * @param name -
	 *          Nombre del indice que se esta buscando
	 * @return Informacion del indice especificado en el parametro. null si no lo encuentra.
	 * @throws Exception
	 */
	public JBaseSystemDBIndex findIndex(String name) throws Exception {
		if (this.indexesMap == null || this.indexesMap.isEmpty()) {
			return null;
		}

		if (this.indexesMap.containsKey(name) == false) {
			return null;
		}

		JBaseSystemDBIndex systemDBIndex;
		systemDBIndex = this.indexesMap.getElement(name);
		return systemDBIndex;
	}

	/**
	 * @return
	 */
	public JIterator<JBaseSystemDBIndex> getIndexesIterator() {
		this.checkIndexesMap();
		return this.indexesMap.getValueIterator();
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	// /**
	// * @return
	// */
	// // @Deprecated
	// public int getIndicesQty() {
	// //return iCantidadIndices;
	// return this.getStaticItems().size();
	// }
	//
	//
	// /**
	// * @param bContinueIfError
	// * @throws Exception
	// */
	// @Deprecated
	// public void createIndexes(boolean bContinueIfError) throws Exception {
	// // if (iCantidadIndices == 0) {
	// if (getIndicesQty() == 0) {
	// return;
	// }
	//
	// firstRecord();
	// while (nextRecord()) {
	// JBaseSystemDBIndex oIndice = getRecord();
	// try {
	// oIndice.createIndex();
	// } catch (Exception e) {
	// if (!bContinueIfError) {
	// throw (e);
	// }
	//
	// PssLogger.logInfo("Error al crear el indice [" + oIndice.getIndexName() + "]");
	// continue;
	// }
	// } // end while
	// }
	//
	// /**
	// * LLAMAR A READALL ANTES DE DROPEAR LOS INDICES. NO HACE NADA POR EL MOMENTO
	// *
	// * @throws Exception
	// */
	// @Deprecated
	// public void dropIndices() throws Exception {
	// if (this.getIndicesQty() == 0) { // iCantidadIndices == 0) {
	// return;
	// }
	// JList<String> oSQL = JCollectionFactory.createList();
	// this.firstRecord();
	// while (this.nextRecord()) {
	// JBaseSystemDBIndex oIndex = getRecord();
	// oSQL.addElement(oIndex.dropIndex());
	// }
	// JIterator<String> oIt = oSQL.getIterator();
	// while (oIt.hasMoreElements()) {
	// String sSql = oIt.nextElement();
	// JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
	// oReg.executeTransaction(sSql);
	// Thread.sleep(500);
	// }
	// }
	//

} // end class
