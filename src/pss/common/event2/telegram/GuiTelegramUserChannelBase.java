package pss.common.event2.telegram;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiTelegramUserChannelBase extends JWin {



	/*
	* Constructor de la Clase
	*/
	public GuiTelegramUserChannelBase() throws Exception {
	}


	public static int ICON = 1116;
	public JRecord ObtenerDato()   throws Exception { return new BizTelegramUserChannel(); }
	public int GetNroIcono() throws Exception  { return ICON; } 
	public String GetTitle()   throws Exception { return "Canal de Telegram"; }
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTelegramUserChannel.class; }
	public String  getKeyField() throws Exception { return BizTelegramUserChannel.ID; }
	public String  getDescripField() { return BizTelegramUserChannel.ID; }
	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
	}


	public BizTelegramUserChannel GetcDato() throws Exception { return (BizTelegramUserChannel) this.getRecord(); }

 
}
