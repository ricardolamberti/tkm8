/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex;
import pss.core.tools.collections.JIterator;

/**
 * 
 *
 */
public class JInformixVirtualDBIndex extends JBaseVirtualDBIndex {

	/**
	 * 
	 */
	public JInformixVirtualDBIndex() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateCreateIndexSQL(java.lang.String)
	 */
	@Override
	protected String generateCreateIndexSQL(String tableName) throws Exception {
		String sql;
		
		sql = "CREATE ";
		if (this.indexIsUnique) {
			sql += "UNIQUE ";
		}
		sql += "INDEX " + this.indexName + " ON " + tableName + " (";
		
		JIterator<String> indexFieldIterator;
		String indexFieldName = new String("");
		
		indexFieldIterator = this.indexColumns.getIterator();
		
		while(indexFieldIterator.hasMoreElements()) {
			if (indexFieldName.isEmpty() == false) {
				indexFieldName += ",";
			}
			indexFieldName += indexFieldIterator.nextElement();
		} // end while
		
		sql += indexFieldName + ")";
		
		return null;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateCreatePrimaryKeySQL(java.lang.String)
	 */
	@Override
	protected String generateCreatePrimaryKeySQL(String tableName) throws Exception {
		String sql;
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ADD CONSTRAINT " + this.indexName + " PRIMARY KEY (";
		
		JIterator<String> indexFieldIterator;
		String indexFieldName = new String("");
		
		indexFieldIterator = this.indexColumns.getIterator();
		
		while(indexFieldIterator.hasMoreElements()) {
			if (indexFieldName.isEmpty() == false) {
				indexFieldName += ",";
			}
			indexFieldName += indexFieldIterator.nextElement();
		} // end while
		
		
		sql += indexFieldName + ")";	
		
		return sql;
	}

	@Override
	protected String generateDropIndexSQL(String tableName) throws Exception {
		String sql;
		
		sql = "DROP INDEX " + this.indexName;
		
		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex#generateDropPrimaryKeySQL(java.lang.String)
	 */
	@Override
	protected String generateDropPrimaryKeySQL(String tableName) throws Exception {
		String sql;
		sql = "ALTER TABLE " + tableName + " ";
		sql += "DROP CONSTRAINT " + this.indexName;
		return sql;
	}

}
