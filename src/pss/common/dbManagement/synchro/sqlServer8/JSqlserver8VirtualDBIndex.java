/**
 * 
 */
package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex;
import pss.core.tools.collections.JIterator;

/**
 * 
 *
 */
public class JSqlserver8VirtualDBIndex extends JBaseVirtualDBIndex {

	/**
	 * 
	 */
	public JSqlserver8VirtualDBIndex() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateCreateIndexSQL(java.lang.String)
	 */
	@Override
	protected String generateCreateIndexSQL(String tableName) throws Exception {
		String sql = new String("");
		
		sql = "CREATE ";
		if (this.indexIsUnique) {
			sql += "UNIQUE ";
		}
		if (this.indexIsClustered) {
			sql += "CLUSTERED ";
		} else {
			sql += "NONCLUSTERED ";
		}
		sql += "INDEX " + this.indexName + " ";
		sql += "ON  " + tableName + " ";
		
		JIterator<String> indexFieldsIterator;
		String indexFields = new String("");
		
		indexFieldsIterator = this.indexColumns.getIterator();
		
		while (indexFieldsIterator.hasMoreElements()) {
			if (indexFields.isEmpty() == false) {
				indexFields += ", "; 
			}
			indexFields += indexFieldsIterator.nextElement();
		} // end while
		
		sql += indexFields;
		sql += ")";
		
		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateCreatePrimaryKeySQL(java.lang.String)
	 */
	@Override
	protected String generateCreatePrimaryKeySQL(String tableName) throws Exception {
		String sql = new String("");
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ADD CONSTRAINT " + this.indexName + " PRIMARY KEY ";
		if (this.indexIsClustered) {
			sql += "CLUSTERED ";
		}
		sql += "( ";
		
		JIterator<String> indexFieldsIterator;
		String indexFields = new String("");
		
		indexFieldsIterator = this.indexColumns.getIterator();
		
		while (indexFieldsIterator.hasMoreElements()) {
			if (indexFields.isEmpty() == false) {
				indexFields += ", "; 
			}
			indexFields += indexFieldsIterator.nextElement();
		} // end while
		
		sql += indexFields;
		sql += ")";		
			
		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateDropIndexSQL(java.lang.String)
	 */
	@Override
	protected String generateDropIndexSQL(String tableName) throws Exception {
		String sql = new String("");
		
		sql = "DROP INDEX " + this.indexName + " ";
		sql += "ON " + tableName;
		
		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateDropPrimaryKeySQL(java.lang.String)
	 */
	@Override
	protected String generateDropPrimaryKeySQL(String tableName) throws Exception {
		String sql = new String("");
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "DROP CONSTRAINT " + this.indexName;
		
		return sql;
	}

}
