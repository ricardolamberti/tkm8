package pss.common.layoutWysiwyg.parametros;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDocListadoParams extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDocListadoParams() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Parametros"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDocListadoParam.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Tipo" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("descripcion");
  	zLista.AddColumnaLista("value");
  }


}
