/**
 * 
 */
package pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;

/**
 * 
 *
 */
public class JInformixSystemDBFields extends JBaseSystemDBFields {

	/**
	 * @throws Exception
	 */
	public JInformixSystemDBFields() throws Exception {
		// TODO Auto-generated constructor stub
		addFilter("TABLE_SCHEMA", "public");
  }

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBFields#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBField> getBasedClass() {
		return JInformixSystemDBField.class;
	}	
	
	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBFields#GetTable()
	 */
	@Override
	public String GetTable() {
		return "INFORMATION_SCHEMA.COLUMNS";
	}

	/**
	 * Sobreescribe la el metodo para indicar que la tabla representada por esta clase es del tipo de tabla de sistema
	 * 
	 * @return true	  
	 */
	@Override
	public boolean isSystemTable() {
		return true;
	}
	
	public void applyDefaultFilters() throws Exception {
		addFilter("TABLE_SCHEMA", "public");
	}
	
}
