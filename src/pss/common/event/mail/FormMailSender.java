package  pss.common.event.mail;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormMailSender extends JBaseForm {


  public FormMailSender() throws Exception {
  }

  public GuiMailSender GetWin() { return (GuiMailSender) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "COMPANY", CHAR, OPT, "company" );
    AddItemEdit( "desc", CHAR, REQ, "Mail_subject" );
    AddItemEdit( "Mail_To", CHAR, OPT, "Mail_To" );
    AddItemEdit( "Mail_From", CHAR, REQ, "Mail_From" );
    AddItemEdit( "Mail_Pwd", CHAR, REQ, "Mail_password" );
    AddItemCombo( "IdMailConf", UINT, REQ, "ID_Mail_Conf",  new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {return getMailConfs(zOneRow) ;};});
  }

	private GuiMailConfs getMailConfs(boolean onerow) throws Exception {
		GuiMailConfs w = new GuiMailConfs();
		if (this.GetWin().GetcDato().getCompany().equals(""))
			w.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		else
			w.getRecords().addFilter("company", this.GetWin().GetcDato().getCompany());
		return w;
	}

}
