/**
 * 
 */
package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes;

/**
 * 
 * 
 */
public class JSqlserver8SystemDBIndexes extends JBaseSystemDBIndexes {

	/**
	 * @throws Exception
	 */
	public JSqlserver8SystemDBIndexes() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes#getNameOfIndexField()
	 */
	@Override
	protected String getNameOfIndexField() {
		return "NAME";
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes#getSQLReadIndexesNames()
	 */
	@Override
	protected String getSQLReadIndexesNames() throws Exception {
		String sql;

		sql = "SELECT SYS.INDEXES." + this.getNameOfIndexField() + " ";
		sql += "FROM SYS.INDEXES";
		sql += ", SYS.ALL_OBJECTS";
		sql += ", INFORMATION_SCHEMA.TABLE_CONSTRAINTS ";
		sql += "WHERE SYS.INDEXES.OBJECT_ID = SYS.ALL_OBJECTS.OBJECT_ID ";
		sql += "AND SYS.ALL_OBJECTS.NAME = '" + this.tableName + "' ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.TABLE_NAME = SYS.ALL_OBJECTS.NAME ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'PRIMARY KEY' ";
		sql += "AND INFORMATION_SCHEMA.TABLE_CONSTRAINTS.CONSTRAINT_NAME != SYS.INDEXES.NAME ";
		
		return sql;
	}
}
