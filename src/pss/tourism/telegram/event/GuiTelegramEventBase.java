package pss.tourism.telegram.event;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiTelegramEventBase extends JWin {



	/*
	* Constructor de la Clase
	*/
	public GuiTelegramEventBase() throws Exception {
	}


	public static int ICON = 1116;
	public JRecord ObtenerDato()   throws Exception { return new BizTelegramEvent(); }
	public int GetNroIcono() throws Exception  { return ICON; } 
	public String GetTitle()   throws Exception { return "Evento Telegram"; }
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTelegramEvent.class; }
	public String  getKeyField() throws Exception { return BizTelegramEvent.ID; }
	public String  getDescripField() { return BizTelegramEvent.ID; }
	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
	}


	public BizTelegramEvent GetcDato() throws Exception { return (BizTelegramEvent) this.getRecord(); }

 
}
