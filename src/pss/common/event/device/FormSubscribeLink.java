package pss.common.event.device;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSubscribeLink extends JBaseForm {

  public GuiSubscribeLink getWin() { return (GuiSubscribeLink) getBaseWin(); }


	@Override
	public void InicializarPanel(JWin zBase) throws Exception {

		AddItemEdit(null, CHAR, OPT, "usuario").setHide(true);

		if (getWin().GetcDato().hasLink()) {
			AddItemLabel("Abra su aplicativo, y presione 'INICIAR'");
			return;
		}
			
		AddItemCombo("Subscribir a", CHAR,REQ ,"id_typedevice");
		AddItemButton("Suscribir", 10, true);
		AddItemEdit(null,CHAR,OPT,"link").setHide(true);
	}
	
	
	
}
