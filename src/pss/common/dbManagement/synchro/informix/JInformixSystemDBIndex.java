/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndex;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;

/**
 * 
 * 
 */
public class JInformixSystemDBIndex extends JBaseSystemDBIndex {

	/**
	 * @throws Exception
	 * 
	 */
	public JInformixSystemDBIndex() throws Exception {
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

		// Para PostgreSQL es necesario tener seteado el nombre de la tabla para obtener el nombre de
		// los campos. Para esto es necesario primero haber intentado leer la primary key
		if (indexTableName == null || indexTableName.isEmpty()) {
			JExcepcion.SendError("No hay nombre de tabla seteado, no se puede acceder a la informacion de los indices.");
		}

		String sql;
		String uniquenessIndex = null;
		int idxFieldsQty = 0; // Cantidad de campos que tiene el indice
		String idxFieldsList = null; // Numero de orden del campo que esta declarado en la tabla
		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();

		// Obtengo informacion sobre el indice
		sql = "SELECT INDISUNIQUE, INDNATTS, INDKEY, INDISCLUSTERED ";
		sql += "FROM PG_CATALOG.PG_INDEX ";
		sql += "JOIN PG_CATALOG.PG_CLASS ";
		sql += "ON (PG_CATALOG.PG_INDEX.INDEXRELID = PG_CATALOG.PG_CLASS.RELFILENODE ";
		sql += "AND PG_CATALOG.PG_CLASS.RELNAME = '" + indexName + "')";

		oReg.ExecuteQuery(sql);

		if (oReg.next() == false) {
			JExcepcion.SendError("No existe informacion sobre el indice: " + indexName);
		}

		uniquenessIndex = oReg.CampoAsStr("INDISUNIQUE");
		idxFieldsQty = oReg.CampoAsInt("INDNATTS");
		idxFieldsList = oReg.CampoAsStr("INDKEY");

		if (uniquenessIndex == null || uniquenessIndex.isEmpty()) {
			JExcepcion.SendError("Error leyendo informacion sobre el indice: " + indexName);
		}

		if (uniquenessIndex.equalsIgnoreCase("t")) {
			this.indexIsUnique = true;
		}

		oReg.close();

		if (idxFieldsQty == 0 || idxFieldsList == null || idxFieldsList.isEmpty()) {
			JExcepcion.SendError("Error obteniendo referencia sobre los campos del indice: " + indexName);
		}

		// Leo los campos relacionados con el indice
		// TODO - El campo DESCEND por ahora se lee pero no se usa, e indica el orden ASCendente o
		// DESCendente de cada columna del indice

		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}

		String fieldName;
		// int idxFieldIdxBegin = 0;
		int idxFieldIdxEnd = 0;
		int fieldOrdinalNr;

		while (true) {
			// Busco el proximo separador de ordinales
			idxFieldIdxEnd = idxFieldsList.indexOf(' ');
			// No encontro ningun separador
			if (idxFieldIdxEnd == -1) {
				// Si el indice de inicio es menor que la longitud de la cadena
				// es que todavia puede haber un valor final
				// if (idxFieldIdxBegin < idxFieldsList.length()) {
					idxFieldIdxEnd = idxFieldsList.length();
				// }
				// break;
			}

			try {
				// fieldOrdinalNr = Integer.parseInt(idxFieldsList.substring(idxFieldIdxBegin, idxFieldIdxEnd));
				fieldOrdinalNr = Integer.parseInt(idxFieldsList.substring(0, idxFieldIdxEnd));
			} catch (Exception e) {
				PssLogger.logError(e, "Error obteniendo ordinal de campo para indice: " + indexName);
				break;
			}

			sql = "SELECT COLUMN_NAME ";
			sql += "FROM INFORMATION_SCHEMA.COLUMNS ";
			sql += "WHERE TABLE_NAME = '" + indexTableName + "' ";
			sql += "AND ORDINAL_POSITION = " + String.valueOf(fieldOrdinalNr);

			oReg.ExecuteQuery(sql);

			if (oReg.next() == false) {
				JExcepcion.SendError("Error no se puede leer campo con numero de orden: " + String.valueOf(fieldOrdinalNr) + " en el indice " + indexName);
			} // end if

			fieldName = null;
			fieldName = oReg.CampoAsStr("COLUMN_NAME");
			if (fieldName == null || fieldName.isEmpty()) {
				JExcepcion.SendError("Nombre de campo invalido leyendo indice " + indexName);
			}

			this.indexColumns.addElement(fieldName);

			oReg.close();

			--idxFieldsQty;

			if (idxFieldsQty == 0) {
				break;
			}

			// idxFieldIdxBegin = idxFieldIdxEnd + 1;
			idxFieldsList = idxFieldsList.substring(idxFieldIdxEnd+1);
		} // end while

		if (idxFieldsQty > 0) {
			JExcepcion.SendError("Error obteniendo ordinal de campo para indice: " + indexName);
		}

		if (this.indexColumns.size() == 0) {
			JExcepcion.SendError("No se encontraron campos leyendo el indice " + indexName);
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
		String indexFieldName;

		sql = "SELECT INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_NAME";
		sql += ", INFORMATION_SCHEMA.KEY_COLUMN_USAGE.COLUMN_NAME ";
		sql += "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE, INFORMATION_SCHEMA.TABLE_CONSTRAINTS ";
		sql += "WHERE INFORMATION_SCHEMA.KEY_COLUMN_USAGE.CONSTRAINT_NAME = INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_NAME ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'PRIMARY KEY' ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.TABLE_NAME = '" + tableName + "'";

		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
		oReg.ExecuteQuery(sql);

		if (oReg.next() == false) {
			PssLogger.logDebug("No se encontro primary key declarada para la tabla:" + tableName);
			return false;
		}

		this.indexName = oReg.CampoAsStr("CONSTRAINT_NAME");

		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}	
		
		while (true) {
			indexFieldName = oReg.CampoAsStr("COLUMN_NAME");
			
			if (indexFieldName == null || indexFieldName.isEmpty()) {
				JExcepcion.SendError("Error leyendo campos de la primary key para tabla: " + tableName);
			}
			
			indexColumns.addElement(indexFieldName);
			if (oReg.next() == false) {
				break;
			}

			indexFieldName = null;
		} // end while

		oReg.close();		
		
		this.indexIsPrimaryKey = true;
		this.indexIsClustered = false;
		this.indexIsUnique = true;			
		
		return true;
	}

}
