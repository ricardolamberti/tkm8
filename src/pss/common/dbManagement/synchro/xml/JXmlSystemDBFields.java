/**
 * 
 */
package  pss.common.dbManagement.synchro.xml;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;

/**
 * 
 *
 */
public class JXmlSystemDBFields extends JBaseSystemDBFields {

	/**
	 * @throws Exception
	 */
	public JXmlSystemDBFields() throws Exception {
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBFields#GetTable()
	 */
	@Override
	public String GetTable() {
		return "COLUMNS";
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBFields#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBField> getBasedClass() {
		return JXmlSystemDBField.class;
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
