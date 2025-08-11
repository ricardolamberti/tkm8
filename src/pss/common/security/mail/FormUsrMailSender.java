package  pss.common.security.mail;

import pss.common.event.mail.GuiMailConfs;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormUsrMailSender extends JBaseForm {

  public FormUsrMailSender() throws Exception {
  }

  public GuiUsrMailSender GetWin() { return (GuiUsrMailSender) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "desc", CHAR, REQ, "descripcion" );
    AddItemCombo( "Estado", CHAR, OPT, "estado", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		
    		return JWins.createVirtualWinsFromMap(BizUsrMailSender.getEstadosMap());
    	}
    });
    AddItemEdit( "Mail_To", CHAR, OPT, "usuario" );
    AddItemEdit( "Mail_From", CHAR, REQ, "email" );
    AddItemEdit( "Mail_Usr", CHAR, REQ, "usuario_auth" );
    AddItemPassword( "Mail_Pwd", CHAR, REQ, "password" );
    AddItemCombo( "Servidor", UINT, REQ, "id_mail_conf",  new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {return getMailConfs(zOneRow) ;};});
    AddItemEdit( "Mail_Last" ,DATETIME, OPT, "last_mail" );
    
  }

	/**
	 * @return
	 * @throws Exception|
	 */
	private GuiMailConfs getMailConfs(boolean onerow) throws Exception {

		GuiMailConfs w = new GuiMailConfs();
		w.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return w;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
