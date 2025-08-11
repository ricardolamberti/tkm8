package  pss.common.agenda.turno.laborables;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTurnosLaborables extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTurnosLaborables() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2003; } 
  public String  GetTitle()    throws Exception  { return "Horario laboral"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTurnoLaborable.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Horario" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("hora_desde");
    zLista.AddColumnaLista("hora_hasta");
    zLista.AddColumnaLista("duracion");
    zLista.AddColumnaLista("separacion");
 //   super.ConfigurarColumnasLista(zLista);
  }

}
