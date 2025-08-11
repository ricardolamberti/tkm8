package  pss.common.agenda.feriado;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiFeriados extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFeriados() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2003; } 
  public String  GetTitle()    throws Exception  { return "Feriados"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFeriado.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Feriado" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("fecha_inicio");
    zLista.AddColumnaLista("fecha_fin");
    zLista.AddColumnaLista("anual");
 //   super.ConfigurarColumnasLista(zLista);
  }

}
