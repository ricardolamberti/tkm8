package  pss.common.agenda.participantes;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiEventosParticipantes extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEventosParticipantes() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15008; } 
  public String  GetTitle()    throws Exception  { return "Participantes"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEventoParticipante.class; }
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
   zLista.AddColumnaLista("nombre");
   zLista.AddColumnaLista("descr_Estado");
   zLista.AddColumnaLista("descr_rol");
  }

}
