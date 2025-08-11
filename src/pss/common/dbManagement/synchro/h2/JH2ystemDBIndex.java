/**
 * 
 */
package  pss.common.dbManagement.synchro.h2;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndex;

/**
 * 
 * 
 */
public class JH2ystemDBIndex extends JBaseSystemDBIndex {

	/**
	 * @throws Exception
	 * 
	 */
	public JH2ystemDBIndex() throws Exception {
	}

	/**
	 * Lee toda la informacion relacionada con el indice indicado como parametro
	 * 
	 * @param indexName
	 *          Nombre del indice que se desea leer
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void readIndexInformation(String indexTableName, String indexName) throws Exception {
	}

	/**
	 * @see pss.common.dbManagement.synchro.JBaseSystemDBIndex#readPrimaryKeyInformation(java.lang.String)
	 */
	@Override
	public boolean readPrimaryKeyInformation(String tableName) throws Exception {
	
		return true;
	}

}
