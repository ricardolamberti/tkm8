/**
 * 
 */
package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;

/**
 * @author gpuig
 *
 */
public class JSqlserver8SystemDBFields extends JBaseSystemDBFields {

	/**
	 * @throws Exception
	 */
	public JSqlserver8SystemDBFields() throws Exception {
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBFields#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBField> getBasedClass() {
		return JSqlserver8SystemDBField.class;
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBFields#GetTable()
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
	
}
