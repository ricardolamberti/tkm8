/*
 * Created on 28-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.core.tools.collections.JIterator;
/** @deprecated */
public interface JWebReportWins {

	public JIterator<String> getMarkedColumns() throws Exception;

	public boolean isColumnMarked(String zResultColumn) throws Exception;

	public boolean isColumnWordWrap(String zResultColumn) throws Exception;

	public void cleanUp();

}
