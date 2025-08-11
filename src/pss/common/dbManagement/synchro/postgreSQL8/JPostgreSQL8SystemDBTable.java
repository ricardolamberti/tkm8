/**
 * 
 */
package pss.common.dbManagement.synchro.postgreSQL8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;

/**
 * 
 *
 */
public class JPostgreSQL8SystemDBTable extends JBaseSystemDBTable {

	/**
	 * @throws Exception
	 */
	public JPostgreSQL8SystemDBTable() throws Exception {
		// TODO Auto-generated constructor stub
		addFilter("TABLE_SCHEMA", "public");
	}

	/** 
	 * @see pss.core.services.records.JRecord#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		addItem("Table_Name", pTableName);
		addItem("TABLE_SCHEMA", pTableschema);
	}

	/**
	 * @see pss.core.services.records.JRecord#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "Table_Name", "Nombre de la Tabla", true, true, 100);
		addFixedItem(VIRTUAL, "TABLE_SCHEMA", "TABLE_SCHEMA", true, true, 100);
	}	
	
  /**
   * @see pss.core.services.records.JBaseRecord#GetTable()
   */
  @Override
	public String GetTable() {return "INFORMATION_SCHEMA.TABLES";}	
	
  
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
