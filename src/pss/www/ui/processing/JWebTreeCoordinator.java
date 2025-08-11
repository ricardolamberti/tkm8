/*
 * Created on 07-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

import pss.core.services.records.JRecords;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.controller.JWebSessionOwnedObject;

/**
 * 
 * 
 * Created on 07-jul-2003
 * 
 * @author PSS
 */

public interface JWebTreeCoordinator extends JWebSessionOwnedObject {

	public abstract JWebApplicationSession getSession();

	public abstract JWebTreeSelection getSelection();

	public abstract void setSelection(JWebTreeSelection selection);

	/**
	 * Applies the filters this tree represents to the given data object, using the field names provided by the given consumer.
	 * 
	 * @param zConsumer
	 *          the tree selection consumer to answer whether a selection is accepted or not
	 * @param oDataObject
	 *          the object to apply the filters
	 * @return whether the given consumer accepted to apply the filters or not
	 */
	@SuppressWarnings("unchecked")
	public boolean applyFilters(JWebTreeSelectionConsumer zConsumer, JRecords oDataObject) throws Exception;

	/**
	 * Creates the filters this tree represents for the given table, using the field names provided by the given consumer.
	 * 
	 * @param zConsumer
	 *          the tree selection consumer to answer whether a selection is accepted or not
	 * @param zTableName
	 *          the table to use for the filters
	 * @return the filters <code>String</code> or <code>null</code> if the given consumer rejected to accept the filters
	 */
	public String createFilters(JWebTreeSelectionConsumer zConsumer, String zTableName) throws Exception;

}
