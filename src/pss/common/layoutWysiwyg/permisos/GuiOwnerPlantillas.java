package  pss.common.layoutWysiwyg.permisos;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiOwnerPlantillas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiOwnerPlantillas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Dueños Documentos Locales "; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiOwnerPlantilla.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo dueño" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("nombre");
    zLista.AddColumnaLista("modificar");
    zLista.AddColumnaLista("borrar");
  }


}
