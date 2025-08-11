package pss.common.event.device;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTypeDevice extends JBaseForm {

  public GuiTypeDevice getWin() { return (GuiTypeDevice) getBaseWin(); }


	@Override
	public void InicializarPanel(JWin zBase) throws Exception {

		AddItemEdit(null, CHAR,OPT ,"company").setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
		AddItemEdit(null, LONG,OPT ,"id_typedevice").setHide(true);
		AddItemEdit("Description", CHAR,REQ ,"description");
		AddItemCombo("Tipo", CHAR,REQ ,"logica").setRefreshForm(true);
		AddItemCombo("Usuario emisor", CHAR,OPT ,"sender").setVisible(false);
		AddItemEdit("Tag Identificador", CHAR,OPT ,"tagid").setVisible(false);
		AddItemEdit("Bot Identificador", CHAR,OPT ,"botid").setVisible(false);
		
	
	}
	
	@Override
	public void checkControls(String sControlId) throws Exception {
	if (getWin().GetcDato().getRawLogicaDevice().requiredUser())
			findControl("sender").show();
		else
			findControl("sender").hide();
		
		if (getWin().GetcDato().getRawLogicaDevice().requiredTagId()) {
			findControl("tagid").show();
			findControl("botid").show();
		} else {
			findControl("tagid").hide();
			findControl("botid").hide();
		}
		super.checkControls();
	}
}
