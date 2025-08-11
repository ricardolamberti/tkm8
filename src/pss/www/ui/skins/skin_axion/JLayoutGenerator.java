/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_axion;

import pss.www.ui.skins.ILayoutGenerator;
import pss.www.ui.skins.JBasicLayoutGenerator;

/**
 * 
 * 
 * Created on 09-jun-2003
 * 
 * @author PSS
 */

public class JLayoutGenerator extends JBasicLayoutGenerator implements ILayoutGenerator {

	
	private static final String SKIN_PATH="skin/sbadmin";

	public String getSkinPath() {
		return SKIN_PATH;
	}

	public String menuPosPrincipal()  throws Exception {
		return "SIDEBAR";
  }
	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/purple.css";
	}
	public String source() throws Exception {
		return "skins/sbadmin/css/sb-admin-2.css";
	}
	
}
