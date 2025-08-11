/**
 * 
 */
package  pss.common.dbManagement.synchro.xml;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;

/**
 *
 *
 */
public class JXmlSystemDBTables extends JBaseSystemDBTables {

	/**
	 * @throws Exception
	 */
	public JXmlSystemDBTables() throws Exception {
		super();
	}

	@Override
	public String GetTable() {
		return "TABLES";
	}
	
	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBTables#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBTable> getBasedClass() {
		return JXmlSystemDBTable.class;
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
