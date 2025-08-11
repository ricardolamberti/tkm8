/**
 * 
 */
package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndex;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;

/**
 * 
 *
 */
public class JSqlserver8SystemDBIndex extends JBaseSystemDBIndex {

	/**
	 * @throws Exception
	 */
	public JSqlserver8SystemDBIndex() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndex#readIndexInformation(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void readIndexInformation(String indexTableName, String indexName) throws Exception {
		String sqlIndex;
		String sqlField;
		
		sqlIndex = "SELECT SYS.INDEX_COLUMNS.COLUMN_ID";
		sqlIndex += ", SYS.INDEXES.TYPE_DESC";
		sqlIndex += ", SYS.INDEXES.IS_UNIQUE ";
		sqlIndex += "FROM SYS.INDEX_COLUMNS";
		sqlIndex += ", SYS.INDEXES "; 
		sqlIndex += "WHERE SYS.INDEX_COLUMNS.OBJECT_ID = SYS.INDEXES.OBJECT_ID "; 
		sqlIndex += "AND SYS.INDEX_COLUMNS.INDEX_ID = SYS.INDEXES.INDEX_ID ";
		sqlIndex += "AND SYS.INDEXES.NAME = '" + indexName + "' ";
		sqlIndex += "ORDER BY KEY_ORDINAL";
		
		JBaseRegistro oIndexReg = JBaseRegistro.VirtualCreate();
		JBaseRegistro oFieldReg = JBaseRegistro.VirtualCreate();

		oIndexReg.ExecuteQuery(sqlIndex);

		if (oIndexReg.next() == false) {
			JExcepcion.SendError("No existe informacion sobre el indice: " + indexName);
		}

		String uniquenessIndex;
		String clusteredIndex;
		int fieldOrdinal;
		String fieldName;
		
		uniquenessIndex = oIndexReg.CampoAsStr("IS_UNIQUE");
		clusteredIndex = oIndexReg.CampoAsStr("TYPE_DESC");

		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}		
		
		while (true) {
			fieldOrdinal = oIndexReg.CampoAsInt("COLUMN_ID");
			
			sqlField = "SELECT COLUMN_NAME "; 
			sqlField += "FROM INFORMATION_SCHEMA.COLUMNS ";
			sqlField += "WHERE TABLE_NAME = '" + indexTableName + "' "; 
			sqlField += "AND ORDINAL_POSITION = " + String.valueOf(fieldOrdinal);
			
			oFieldReg.ExecuteQuery(sqlField);
			
			if (oFieldReg.next() == false) {
				JExcepcion.SendError("Error leyendo informacion de los campos del indice: " + indexName);
			}
			
			fieldName = oFieldReg.CampoAsStr("COLUMN_NAME");
			
			this.indexColumns.addElement(fieldName);
			
			if (oIndexReg.next() == false) {
				break;
			}
			
			fieldName = null;
		} // end while

		oIndexReg.close();
		
		if (uniquenessIndex.equalsIgnoreCase("1")) {
			this.indexIsUnique = true;
		}
		
		if (clusteredIndex.equalsIgnoreCase("CLUSTERED") == true ) {
			this.indexIsClustered = true;
		}
		
		this.indexName = indexName;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndex#readPrimaryKeyInformation(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean readPrimaryKeyInformation(String tableName) throws Exception {
		String sql;
		
		sql = "SELECT INFORMATION_SCHEMA.KEY_COLUMN_USAGE.CONSTRAINT_NAME ";
		sql += ", INFORMATION_SCHEMA.KEY_COLUMN_USAGE.COLUMN_NAME "; 
		sql += "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE ";
		sql += ",INFORMATION_SCHEMA.TABLE_CONSTRAINTS ";
		sql += "WHERE INFORMATION_SCHEMA.KEY_COLUMN_USAGE.CONSTRAINT_NAME = INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_NAME ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'PRIMARY KEY'";		
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.TABLE_NAME = '" + tableName + "' ";
		
		JBaseRegistro oIndexReg = JBaseRegistro.VirtualCreate();

		oIndexReg.ExecuteQuery(sql);

		if (oIndexReg.next() == false) {
			// JExcepcion.SendError("No existe informacion sobre el indice: " + indexName);
			return false;
		}
		
		indexName = oIndexReg.CampoAsStr("CONSTRAINT_NAME");

		if (indexName == null || indexName.isEmpty()) {
			JExcepcion.SendError("Error leyendo campos de la primary key de la tabla: " + tableName);
		}		
		
		if (this.indexColumns == null) {
			this.indexColumns = JCollectionFactory.createList();
		} else {
			this.indexColumns.removeAllElements(); // Limpio la lista de campos
		}		

		String fieldName;
		
		while (true) {
			fieldName = oIndexReg.CampoAsStr("COLUMN_NAME");

			if (fieldName == null || fieldName.isEmpty()) {
				JExcepcion.SendError("Error leyendo campos de la primary key de la tabla: " + tableName);
			}
			
			indexColumns.addElement(fieldName);
			
			if (oIndexReg.next() == false) {
				break;
			}
		} // end while
		
		return true;
	}

}
