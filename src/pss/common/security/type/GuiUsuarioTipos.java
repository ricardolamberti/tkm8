package  pss.common.security.type;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUsuarioTipos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiUsuarioTipos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10048; } 
  public String  GetTitle()    throws Exception  { return "Tipos de usuarios"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiUsuarioTipo.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }

  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("descripcion").setActionOnClick(1);
  }




}
