package pss.common.training.level1;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLevels1 extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLevels1() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 208; } 
  public String  GetTitle()    throws Exception  { return "Nivel 1 - Entrenamiento"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLevel1.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }


	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Usuario", "descripcion").setOperator("ILIKE");
	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
