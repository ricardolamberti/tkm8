package pss.common.training.level3;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLevels3 extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLevels3() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 208; } 
  public String  GetTitle()    throws Exception  { return "Nivel 3 - Entrenamiento"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLevel3.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }


	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Detalle", "descripcion").setOperator("ILIKE");
	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("descripcion");
  	zLista.AddColumnaLista("ddcombo");
  	zLista.AddColumnaLista("ddwinlov");
  	zLista.AddColumnaLista("desde");
  	zLista.AddColumnaLista("hasta");
  	zLista.AddColumnaLista("listbox");
  	zLista.AddColumnaLista("multicheck");
  	zLista.AddColumnaLista("checker");
  }

}
