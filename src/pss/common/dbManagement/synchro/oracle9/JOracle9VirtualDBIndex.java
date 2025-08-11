/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBIndex;
import pss.core.tools.collections.JIterator;

/**
 * 
 *
 */
public class JOracle9VirtualDBIndex extends JBaseVirtualDBIndex {

	/**
	 * 
	 */
	public JOracle9VirtualDBIndex() {
		// TODO Auto-generated constructor stub
	}

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

		while (indexFieldIterator.hasMoreElements()) {
			if (indexFieldName.isEmpty() == false) {
				indexFieldName += ",";
			}
			indexFieldName += indexFieldIterator.nextElement();
		} // end while

		sql += indexFieldName + ")";

		return null;
	}

	@Override
	protected String generateCreatePrimaryKeySQL(String tableName)
			throws Exception {
		String sql;
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ADD CONSTRAINT " + this.indexName + " PRIMARY KEY (";

		JIterator<String> indexFieldIterator;
		String indexFieldName = new String("");

		indexFieldIterator = this.indexColumns.getIterator();

		while (indexFieldIterator.hasMoreElements()) {
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

	@Override
	protected String generateDropPrimaryKeySQL(String tableName) {
		String sql;
		sql = "ALTER TABLE " + tableName + " ";
		sql += "DROP CONSTRAINT " + this.indexName;
		return sql;
	}

}
