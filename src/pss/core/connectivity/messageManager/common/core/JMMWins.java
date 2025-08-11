package pss.core.connectivity.messageManager.common.core;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;

public class JMMWins extends JWins  {

	public JMMWins() throws Exception {
	  super();
  }


  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
  	// agregar acciones extras
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	// procesar acciones
		return super.getSubmitFor(a);
  }
  
  


}
