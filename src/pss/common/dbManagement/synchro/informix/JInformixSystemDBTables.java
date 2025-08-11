/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;

/**
 *
 *
 */
public class JInformixSystemDBTables extends JBaseSystemDBTables {

	/**
	 * @throws Exception
	 */
	public JInformixSystemDBTables() throws Exception {
		super();
		addFilter("TABLE_SCHEMA", "public");
		addFilter("TABLE_TYPE", "BASE TABLE");
		//addFilter("TABLE_SCHEMA", "information_schema", "<>");
		//addFilter("TABLE_SCHEMA", "pg_catalog", "<>");
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBTables#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBTable> getBasedClass() {
		return JInformixSystemDBTable.class;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBTables#GetTable()
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
	public void applyDefaultFilters() throws Exception {
		addFilter("TABLE_SCHEMA", "public");
	}
	
}
