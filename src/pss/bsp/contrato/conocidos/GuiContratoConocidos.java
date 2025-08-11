package pss.bsp.contrato.conocidos;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiContratoConocidos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConocidos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Contratos conocidos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiContratoConocido.class; }
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
    zLista.AddColumnaLista("company");
    zLista.AddColumnaLista("pais");
    zLista.AddColumnaLista("aerolineas");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("periodo");
    
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Compania", "Company").setOperator("ilike");
  	zFiltros.addEditResponsive("pais", "pais").setOperator("ilike");
  	zFiltros.addEditResponsive("Aerolinea", "aerolineas").setOperator("ilike");
  	zFiltros.addEditResponsive("descripcion", "descripcion").setOperator("ilike");
  	super.ConfigurarFiltros(zFiltros);
  }

}
