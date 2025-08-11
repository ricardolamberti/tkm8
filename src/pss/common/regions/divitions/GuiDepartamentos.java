package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDepartamentos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDepartamentos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 911; } 
  public String  GetTitle()    throws Exception  { return "Departamentos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDepartamento.class; }
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
    zLista.AddColumnaLista("descr_pais");
    zLista.AddColumnaLista("descr_provincia");
    zLista.AddColumnaLista("nombre");
//    zLista.SetColumnaBusqueda("nombre");
    this.getRecords().addOrderBy("nombre");
  }

  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Descripcion", "nombre" ).setOperator("ilike");
	}

}
