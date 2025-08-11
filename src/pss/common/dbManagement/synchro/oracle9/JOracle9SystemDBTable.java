/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;

/**
 * 
 *
 */
public class JOracle9SystemDBTable extends JBaseSystemDBTable {

	/**
	 * @throws Exception
	 */
	public JOracle9SystemDBTable() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see pss.core.services.records.JRecord#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		addItem("Table_Name", pTableName);
	}

	/**
	 * @see pss.core.services.records.JRecord#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "Table_Name", "Nombre de la Tabla", true, true, 100);
	}	
	
  /**
   * @see pss.core.services.records.JBaseRecord#GetTable()
   */
  @Override
	public String GetTable() {return "USER_TABLES";}
  
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


