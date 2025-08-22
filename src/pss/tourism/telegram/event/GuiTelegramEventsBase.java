package pss.tourism.telegram.event;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiTelegramEventsBase extends JWins {



	/*
	* Constructor de la Clase
	*/
	public GuiTelegramEventsBase() throws Exception {
	}


	public static int ICON = 1116;
	public int     GetNroIcono() throws Exception  { return ICON; } 
	public String  GetTitle()    throws Exception  { return "Eventos Telegram"; }
	public Class<? extends JWin> GetClassWin() { return GuiTelegramEvent.class; }
	/*
	* Mapeo las acciones con las operaciones
	*/
	public void createActionMap() throws Exception {
		addActionNew( 1, "Nuevo Registro" );
	}



	/*
	* Configuro las columnas que quiero mostrar en la grilla
	*/
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		JColumnaLista list=null;
		zLista.AddIcono("");
		zLista.AddColumnaLista(BizTelegramEvent.ID).setVisible(false);
		list = zLista.AddColumnaLista(BizTelegramEvent.COMPANY);
		list = zLista.AddColumnaLista(BizTelegramEvent.EVENT_CODE_ID);
		list = zLista.AddColumnaLista(BizTelegramEvent.EVENT_CODE);
		list = zLista.AddColumnaLista(BizTelegramEvent.EVENT_DATE);
	}

}
