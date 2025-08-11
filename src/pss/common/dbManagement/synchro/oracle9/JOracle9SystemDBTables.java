/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;



/**
 * 
 *
 */
public class JOracle9SystemDBTables extends JBaseSystemDBTables {

	/**
	 * @throws Exception
	 * 
	 */
	public JOracle9SystemDBTables() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBTables#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBTable> getBasedClass() {
		return JOracle9SystemDBTable.class;
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBTables#GetTable()
	 */
	@Override
	public String GetTable() {
		return "USER_TABLES";
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
