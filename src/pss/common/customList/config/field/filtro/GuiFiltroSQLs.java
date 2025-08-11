package  pss.common.customList.config.field.filtro;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JWinList;

public class GuiFiltroSQLs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFiltroSQLs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10063; } 
  public String  GetTitle()    throws Exception  { return "Filtros SQL"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFiltroSQL.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Filtro" );
  }

  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }

  
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_filtro");
  }

}
