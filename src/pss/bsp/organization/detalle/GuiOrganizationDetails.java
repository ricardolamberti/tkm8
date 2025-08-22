package pss.bsp.organization.detalle;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiOrganizationDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiOrganizationDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Iatas de la Org."; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiOrganizationDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	addActionNew( 1, "Nuevo Iata" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("iata");
  }

}
