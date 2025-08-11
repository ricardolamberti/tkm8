package pss.tourism.telegram.event;

import pss.core.winUI.forms.JBaseForm;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
public class FormTelegramEventBase extends JBaseForm {


	private static final long serialVersionUID = 1752694001415L;



	/*
	* Constructor de la Clase
	*/
	public FormTelegramEventBase() throws Exception {
	}

	public GuiTelegramEvent getWin() { return (GuiTelegramEvent) getBaseWin(); }

	/*
	* Linkeo los campos con la variables del form
	*/
	@Override
	public void InicializarPanel( JWin zWin ) throws Exception {
		JFormEditResponsive edit;
		JFormCDatetimeResponsive date;
		edit = AddItemEdit( BizTelegramEventBase.D_ID,  UINT, OPT, BizTelegramEventBase.ID );
		edit.hide();
		edit = AddItemEdit( BizTelegramEventBase.D_COMPANY,  CHAR, OPT, BizTelegramEventBase.COMPANY );
		edit.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramEventBase.D_EVENT_CODE_ID,  CHAR, OPT, BizTelegramEventBase.EVENT_CODE_ID );
		edit.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramEventBase.D_EVENT_CODE,  UINT, OPT, BizTelegramEventBase.EVENT_CODE );
		edit.setSizeColumns(3);
		date = AddItemDateTime( BizTelegramEventBase.D_EVENT_DATE,  DATETIME, OPT, BizTelegramEventBase.EVENT_DATE );
		date.setSizeColumns(3);
	}



}
