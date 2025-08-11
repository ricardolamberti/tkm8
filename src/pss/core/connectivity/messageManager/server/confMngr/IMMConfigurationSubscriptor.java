/**
 * 
 */
package pss.core.connectivity.messageManager.server.confMngr;

import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.connectivity.messageManager.common.core.JMMWin;

/**
 * @author 
 *
 */
public interface IMMConfigurationSubscriptor {
	public JMMWin getNewMMGui() throws Exception;
	public JMMRecord getNewMMRecord() throws Exception;
	public JMMBaseForm getNewMMForm() throws Exception;

}
