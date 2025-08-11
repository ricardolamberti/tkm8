package pss.bsp.interfaces.copa.datos;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCopas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCopas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10056; } 
  public String  GetTitle()    throws Exception  { return "Liquidaciones BSP"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCopa.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//   addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
  //  zLista.AddColumnaLista("idPDF");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("estado");
	

  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());

		super.ConfigurarFiltros(zFiltros);
  }

}
