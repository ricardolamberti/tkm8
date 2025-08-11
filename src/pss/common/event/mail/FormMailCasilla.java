package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormMailCasilla extends JBaseForm {
  
	public FormMailCasilla() throws Exception {
  }

  public GuiMailCasilla GetWin() { return (GuiMailCasilla) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    JFormPanelResponsive col = this.AddItemColumn(3);
    col.AddItemEdit("id", CHAR, OPT, "id" ).hide();
    col.AddItemEdit("company", CHAR, OPT, "company" ).hide();
    col.AddItemCombo("Servidor", UINT, REQ, "id_mail_conf",  new JControlCombo() { 
  		public JWins getRecords(boolean zOneRow) throws Exception {
    		return getMailConfs(zOneRow) ;};
    	});//.SetValorDefault(this.getMailConfs(false).getFirstValorItemClave());
    col.AddItemEdit("Nombre", CHAR, REQ, "nombre" );
    col.AddItemEdit("eMail", CHAR, REQ, "mail_From" );
    col.AddItemPassword("Clave", CHAR, REQ, "mail_password" );
 }

	/**
	 * @return
	 * @throws Exception|
	 */
	private GuiMailConfs getMailConfs(boolean one) throws Exception {
		GuiMailConfs w = new GuiMailConfs();
		w.getRecords().addFilter("company", this.GetWin().GetcDato().getCompany());
		if (one) {
			w.getRecords().addFilter("id", this.valueControl("id_mail_conf"));
		}
		return w;
	}

}  
