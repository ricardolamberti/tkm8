package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMailCasillas extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMailCasillas() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 63; } 
  @Override
	public String  getDescripField()                { return "Desc"; }
  @Override
	public String  GetTitle()    throws Exception  { return "Casillas"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiMailCasilla.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nueva Casilla" );
  }





  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
//	  zLista.AddColumnaLista("mail_subject");
//	  zLista.AddColumnaLista("mail_to");
	  zLista.AddColumnaLista("mail_from");
	  zLista.AddColumnaLista("activo");
	  zLista.AddColumnaLista("mensaje");
	  zLista.AddColumnaLista("last_mail");
	  
	  
//	  zLista.AddColumnaLista("ID_MAIL_CONF");
  }

}
