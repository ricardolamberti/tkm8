/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBFields;
import pss.core.tools.collections.JIterator;

/**
 * 
 *
 */
public class JInformixVirtualDBFields extends JBaseVirtualDBFields {

	/**
	 * @throws Exception
	 */
	public JInformixVirtualDBFields() throws Exception {
		// TODO Auto-generated constructor stub
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
