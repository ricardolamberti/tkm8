package pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiTemporalPasswordsBase extends JWins {



	/*
	* Constructor de la Clase
	*/
	public GuiTemporalPasswordsBase() throws Exception {
	}


	public int     GetNroIcono() throws Exception  { return 10074; } 
	public String  GetTitle()    throws Exception  { return "Claves Temporales"; }
	public Class<? extends JWin> GetClassWin() { return GuiTemporalPassword.class; }
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
		zLista.AddIcono("");
		zLista.AddColumnaLista("PASSWORD");
		zLista.AddColumnaLista("MAIL");
		zLista.AddColumnaLista("START_DATE");
		zLista.AddColumnaLista("END_DATE");
	}

}
