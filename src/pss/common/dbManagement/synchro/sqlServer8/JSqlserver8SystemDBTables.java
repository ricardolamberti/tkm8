package  pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;

/**
 * 
 *
 */
public class JSqlserver8SystemDBTables extends JBaseSystemDBTables {

	/**
	 * @throws Exception
	 */
	public JSqlserver8SystemDBTables() throws Exception {
		super();
		addFilter("TABLE_TYPE", "BASE TABLE");
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBTables#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBTable> getBasedClass() {
		return JSqlserver8SystemDBTable.class;
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBTables#GetTable()
	 */
	@Override
	public String GetTable() {
		return "INFORMATION_SCHEMA.TABLES";
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
