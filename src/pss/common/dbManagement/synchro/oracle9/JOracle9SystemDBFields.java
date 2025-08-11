/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;

/**
 * @author gpuig
 *
 */
public class JOracle9SystemDBFields extends JBaseSystemDBFields {

	/**
	 * @throws Exception
	 */
	public JOracle9SystemDBFields() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBFields#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBField> getBasedClass() {
		return JOracle9SystemDBField.class;
	}	
	
	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBFields#GetTable()
	 */
	@Override
	public String GetTable() {
		return "ALL_TAB_COLUMNS";
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
