package  pss.common.agenda.turno.ausencias;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTurnosAusencias extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTurnosAusencias() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2003; } 
  public String  GetTitle()    throws Exception  { return "Ausencias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTurnoAusencia.class; }
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
    zLista.AddColumnaLista("razon");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
 //   super.ConfigurarColumnasLista(zLista);
  }

}
