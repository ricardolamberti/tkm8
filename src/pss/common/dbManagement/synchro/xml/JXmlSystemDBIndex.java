/**
 * 
 */
package  pss.common.dbManagement.synchro.xml;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndex;

/**
 * 
 * 
 */
public class JXmlSystemDBIndex extends JBaseSystemDBIndex {

	/**
	 * @throws Exception
	 * 
	 */
	public JXmlSystemDBIndex() throws Exception {
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
