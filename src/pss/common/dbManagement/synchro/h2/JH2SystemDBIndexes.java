/**
 * 
 */
package  pss.common.dbManagement.synchro.h2;

import pss.common.dbManagement.synchro.base.JBaseSystemDBIndexes;

/**
 * @author gpuig
 * 
 */
public class JH2SystemDBIndexes extends JBaseSystemDBIndexes {

	/**
	 * @throws Exception
	 */
	public JH2SystemDBIndexes() throws Exception {
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
