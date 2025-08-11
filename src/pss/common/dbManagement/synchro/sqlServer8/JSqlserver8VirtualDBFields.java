/**
 * 
 */
package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBFields;
import pss.core.tools.collections.JIterator;


/**
 * 
 *
 */
public class JSqlserver8VirtualDBFields extends JBaseVirtualDBFields {

	/**
	 * 
	 */
	public JSqlserver8VirtualDBFields() {
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBFields#generateSQLFilesDescription()
	 */
	@Override
	public String generateFieldsDeclarationSQL() throws Exception {
		String sql = new String("");
		JIterator<JBaseVirtualDBField> virtualDBFieldIterator;
		JBaseVirtualDBField virtualDBField;
		
		virtualDBFieldIterator = this.oFields.getValueIterator();
		
		while (virtualDBFieldIterator.hasMoreElements()) {
			virtualDBField = virtualDBFieldIterator.nextElement();
			if (sql.isEmpty() == false) {
				sql += ", ";
			}
			sql += virtualDBField.getFieldDeclarationSQL();
		} // end while
		
		return sql;
	}	
	
}
