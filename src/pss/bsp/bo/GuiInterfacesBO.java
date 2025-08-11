package  pss.bsp.bo;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiInterfacesBO extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiInterfacesBO() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10058; } 
  public String  GetTitle()    throws Exception  { return "Interfaces BackOffice"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiInterfazBO.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
  //  zLista.AddColumnaLista("idInterfaz");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("descripcion");
 //   zLista.AddColumnaLista("estado");
	  }

}
