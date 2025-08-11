package pss.common.mail;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;


public class GuiModuloMailing extends GuiModulo {

  public GuiModuloMailing() throws Exception {
    super();
    SetModuleName( "Correo"      );
    SetNroIcono  ( 10036 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction(1, "Correo", null, 1111, true, true, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
		addAction(10, "Correo", null, 10036 , true, true);
		addAction(11, "Ocultar Correo Emergente", null, 953, true, false);
    this.loadDynamicActions(null);
  }

  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==10 ) return new JActQuery(new GuiConsolaMail());
		if (a.getId()==11) return new JActSubmit(this, a.getId()) {
			public void execSubmit() throws Exception {
				JMail.getMailServer().markUrgentReaded();
			}
		};
  	return null;
  	
  }


  
  
}
