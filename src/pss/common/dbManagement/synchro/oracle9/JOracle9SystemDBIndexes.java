/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes;
import pss.core.tools.JExcepcion;

/**
 * 
 *
 */
public class JOracle9SystemDBIndexes extends JBaseSystemDBIndexes {

	/**
	 * @throws Exception
	 */
	public JOracle9SystemDBIndexes() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes#getNameOfIndexField()
	 */
	@Override
	protected String getNameOfIndexField() {
		return "INDEX_NAME";
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
		sql = "SELECT " + getNameOfIndexField();
		sql += " FROM USER_INDEXES ";
		sql += " WHERE TABLE_NAME = '" + this.tableName + "'";
		return sql;
	}

}
