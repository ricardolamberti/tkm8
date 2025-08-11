package pss.common.training.level3.sub;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiSubLevels3 extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSubLevels3() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 208; } 
  public String  GetTitle()    throws Exception  { return "Sub Nivel 3 - Entrenamiento"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSubLevel3.class; }
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
