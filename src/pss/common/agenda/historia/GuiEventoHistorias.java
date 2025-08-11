package  pss.common.agenda.historia;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiEventoHistorias extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEventoHistorias() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10058; } 
  public String  GetTitle()    throws Exception  { return "Eventos Historias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEventoHistoria.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    //addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("usuario");
    zLista.AddColumnaLista("texto");
 //   super.ConfigurarColumnasLista(zLista);
  }

}
