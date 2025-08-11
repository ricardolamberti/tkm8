/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndex;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;

/**
 * 
 * 
 */
public class JOracle9SystemDBIndex extends JBaseSystemDBIndex {

	/**
	 * @throws Exception
	 */
	public JOracle9SystemDBIndex() throws Exception {
	}

	/**
	 * Lee toda la informacion relacionada con el indice indicado como parametro
	 * 
	 * @param indexName
	 *          Nombre del indice que se desea leer
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void readIndexInformation(String indexTableName, String indexName) throws Exception {
		String sql;
		String uniquenessIndex = null;
		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();

		// Obtengo informacion sobre el indice
		sql = "SELECT UNIQUENESS ";
		sql += " FROM USER_INDEXES ";
		sql += " WHERE INDEX_NAME = '" + indexName + "'";

		oReg.ExecuteQuery(sql);

		if (oReg.next()) {
			uniquenessIndex = oReg.CampoAsStr("UNIQUENESS");
		}

		if (uniquenessIndex == null || uniquenessIndex.isEmpty()) {
			JExcepcion.SendError("No se puede leer informacion sobre el indice: " + indexName);
		}

		if (uniquenessIndex.equalsIgnoreCase("UNIQUE")) {
			indexIsUnique = true;
		}

		oReg.close();

		// Leo los campos relacionados con el indice
		// TODO - El campo DESCEND por ahora se lee pero no se usa, e indica el orden ASCendente o
		// DESCendente de cada columna del indice
		String fieldName;

		sql = "SELECT COLUMN_NAME, DESCEND ";
		sql += " FROM ALL_IND_COLUMNS ";
		sql += " WHERE INDEX_NAME = '" + indexName + "' ";
		sql += " ORDER BY COLUMN_POSITION";

		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}

		oReg.ExecuteQuery(sql);

		while (oReg.next()) {
			fieldName = null;
			fieldName = oReg.CampoAsStr("COLUMN_NAME");
			if (fieldName == null || fieldName.isEmpty()) {
				JExcepcion.SendError("Nombre de campo invalido leyendo indice " + indexName);
			}
			this.indexColumns.addElement(fieldName);
		} // end while

		oReg.close();

		if (this.indexColumns.size() == 0) {
			//JExcepcion.SendError("No se encontraron campos leyendo el indice " + indexName);
		}

		// Lleno por default los campos que no puedo leer de las vistas del sistema
		this.indexIsClustered = false;
		this.indexIsPrimaryKey = false;
		this.indexName = indexName;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndex#readPrimaryKeyInformation(java.lang.String)
	 */
	@Override
	public boolean readPrimaryKeyInformation(String tableName) throws Exception {
		String sql;
		String primaryKeyName = null;
		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();

		// Obtengo informacion sobre primary Key
		sql = "SELECT CONSTRAINT_NAME  ";
		sql += " FROM USER_CONSTRAINTS ";
		sql += " WHERE TABLE_NAME = '" + tableName + "' ";
		sql += " AND CONSTRAINT_TYPE = 'P'";

		oReg.ExecuteQuery(sql);

		if (oReg.next()) {
			primaryKeyName = oReg.CampoAsStr("CONSTRAINT_NAME");
		}

		if (primaryKeyName == null || primaryKeyName.isEmpty()) {
			return false;
		}

		oReg.close();

		// Leo los campos relacionados con la primary key
		String fieldName;

		sql = "SELECT COLUMN_NAME ";
		sql += " FROM USER_CONS_COLUMNS ";
		sql += " WHERE CONSTRAINT_NAME = '" + primaryKeyName + "' ";
		sql += " ORDER BY POSITION";

		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}

		oReg.ExecuteQuery(sql);

		while (oReg.next()) {
			fieldName = null;
			fieldName = oReg.CampoAsStr("COLUMN_NAME");
			if (fieldName == null || fieldName.isEmpty()) {
				JExcepcion.SendError("Nombre de campo invalido leyendo primaryKey " + primaryKeyName);
			}
			this.indexColumns.addElement(fieldName);
		} // end while

		oReg.close();

		if (this.indexColumns.size() == 0) {
			JExcepcion.SendError("No se encontraron campos leyendo el indice " + indexName);
		}

		// Lleno por default los campos que no puedo leer de las vistas del sistema
		this.indexIsClustered = false;
		this.indexIsPrimaryKey = true;
		this.indexName = primaryKeyName;
		return true;
	}

}
