/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes;
import pss.core.tools.JExcepcion;

/**
 * @author gpuig
 * 
 */
public class JInformixSystemDBIndexes extends JBaseSystemDBIndexes {

	/**
	 * @throws Exception
	 */
	public JInformixSystemDBIndexes() throws Exception {
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes#getNameOfIndexField()
	 */
	@Override
	protected String getNameOfIndexField() {
		return "INDEXNAME";
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes#getSQLReadIndexesNames()
	 */
	@Override
	protected String getSQLReadIndexesNames() throws Exception {
		if (this.tableName == null || this.tableName.isEmpty()) {
			JExcepcion.SendError("El nombre de la tabla esta vacio o es nulo");
		}
		
		String sql;

		sql = "SELECT PG_CATALOG.PG_INDEXES." + this.getNameOfIndexField() + " "; 
		sql += "FROM PG_CATALOG.PG_INDEXES "; 
		sql += "WHERE	PG_CATALOG.PG_INDEXES.TABLENAME = '" + this.tableName + "' "; 
		sql += "EXCEPT SELECT INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_NAME ";
		sql += "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS ";
		sql += "WHERE INFORMATION_SCHEMA.TABLE_CONSTRAINTS.TABLE_NAME = '" + this.tableName + "' "; 
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'PRIMARY KEY'";		
		
		return sql;
	}

}
