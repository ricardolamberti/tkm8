package pss.common.event.mail;

import pss.common.documentos.docEmail.BizDocEmail;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloMail extends GuiModulo {

  public GuiModuloMail() throws Exception {
    super();
    SetModuleName( "Mail" );
    SetNroIcono  ( 63 );
  }
  

  @Override
	public void createActionMap() throws Exception {
		this.addAction(20, "Servidores de Mail", null, 63, true, true, true, "Group");
		this.addAction(30, "Configuración Mails", null, 63, true, false, true, "Group");
		this.addAction(40, "Start pop3", null, 63, true, true);

  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20) return new JActWins(getMailConfs());
		if (a.getId() == 30) return new JActWins(getMailSenders());
		if (a.getId() == 40)
			return new JActSubmit(this) {
				public void submit() throws Exception {
					BizDocEmail.startPollingThread();
				}
			};

		return null;
	}


	private GuiMailCasillas getMailSenders() throws Exception {
		
		GuiMailCasillas ms= new GuiMailCasillas();
		ms.getRecords().addFilter("company", BizUsuario.getUsr().getCompany() );
		
		return ms;
	}


	private GuiMailConfs getMailConfs() throws Exception {
		GuiMailConfs ms = new GuiMailConfs();
		ms.getRecords().addFilter("company", BizUsuario.getUsr().getCompany() );
		return ms;
	}
	
	


  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction( 1, "Mail", new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return GetThis(); }
    }, 63 , true, true, true, "Group" );
  }


}



