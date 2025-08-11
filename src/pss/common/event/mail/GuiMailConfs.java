package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMailConfs extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMailConfs() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 10; } 
  @Override
	public String  getDescripField()                { return "Description"; }
  @Override
	public String  GetTitle()    throws Exception  { return "Servidores de Mail"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiMailConf.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }





  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
	  zLista.AddColumnaLista("DESCRIPTION");
	  zLista.AddColumnaLista("TRANSPORT");
	  zLista.AddColumnaLista("SMTP_SERVER");
	  zLista.AddColumnaLista("SMTP_PORT");
//	  zLista.AddColumnaLista("MAIL_FORMAT");
  }

}
