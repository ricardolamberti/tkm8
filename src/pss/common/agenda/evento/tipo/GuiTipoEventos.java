package  pss.common.agenda.evento.tipo;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTipoEventos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTipoEventos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10051; } 
  public String  GetTitle()    throws Exception  { return "Tipos eventos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTipoEvento.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
