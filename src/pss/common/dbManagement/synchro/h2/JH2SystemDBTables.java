/**
 * 
 */
package  pss.common.dbManagement.synchro.h2;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;
import pss.common.dbManagement.synchro.xml.JXmlSystemDBTable;

/**
 *
 *
 */
public class JH2SystemDBTables extends JBaseSystemDBTables {

	/**
	 * @throws Exception
	 */
	public JH2SystemDBTables() throws Exception {
		super();
	}
	@Override
	public String GetTable() {
		return "INFORMATION_SCHEMA.TABLES";
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
