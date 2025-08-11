/**
 * 
 */
package  pss.common.dbManagement.synchro.xml;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes;

/**
 * @author gpuig
 * 
 */
public class JXmlSystemDBIndexes extends JBaseSystemDBIndexes {

	/**
	 * @throws Exception
	 */
	public JXmlSystemDBIndexes() throws Exception {
	}

	/**
	 * @see pss.common.dbManagement.synchro.JBaseSystemDBIndexes#getNameOfIndexField()
	 */
	@Override
	protected String getNameOfIndexField() {
		return "INDEXNAME";
	}

	/**
	 * @see pss.common.dbManagement.synchro.JBaseSystemDBIndexes#getSQLReadIndexesNames()
	 */
	@Override
	protected String getSQLReadIndexesNames() throws Exception {
		
		return null;
	}

}
