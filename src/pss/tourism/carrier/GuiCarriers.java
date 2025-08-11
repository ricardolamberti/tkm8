package pss.tourism.carrier;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCarriers extends JWins {


  /**
   * Constructor de la Clase
   */
  public GuiCarriers() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 1113; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Lineas Aereas"; }
  @Override
	public Class<? extends JWin>    GetClassWin()                   { return GuiCarrier.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
    zFiltros.addEditResponsive( "ID"  ,"CHAR", "carrier").setOperator("ilike");
    zFiltros.addEditResponsive( "Descripción"  ,"CHAR", "description").setOperator("ilike");
    zFiltros.addEditResponsive( "Cod IATA"  ,"CHAR", "cod_iata").setOperator("ilike");
  }
}
