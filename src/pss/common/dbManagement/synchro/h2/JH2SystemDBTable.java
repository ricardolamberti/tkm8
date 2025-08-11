/**
 * 
 */
package  pss.common.dbManagement.synchro.h2;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;

/**
 * @author gpuig
 *
 */
public class JH2SystemDBTable extends JBaseSystemDBTable {

	/**
	 * @throws Exception
	 */
	public JH2SystemDBTable() throws Exception {
	}
	@Override
	public boolean isStatic() throws Exception {
		return true;
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
	 * Sobreescribe la el metodo para indicar que la tabla representada por esta clase es del tipo de tabla de sistema
	 * 
	 * @return true	  
	 */
	@Override
	public boolean isSystemTable() {
		return true;
	}	  
}
