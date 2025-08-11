/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_ypf;

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

	
	private static final String SKIN_PATH="skins/sbadmin";

	public String getSkinPath() {
		return SKIN_PATH;
	}

	public String menuPosPrincipal()  throws Exception {
		return "SIDEBAR";
  }

}
