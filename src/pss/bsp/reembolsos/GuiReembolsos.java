package pss.bsp.reembolsos;

import pss.core.win.JWin;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiReembolsos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiReembolsos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Reembolsos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiReembolso.class; }
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
  	zLista.AddColumnaLista("fecha");
  	zLista.AddColumnaLista("tipo");
  	zLista.AddColumnaLista("boleto");
   	zLista.AddColumnaLista("carrier");
   	zLista.AddColumnaLista("fecha");
   	zLista.AddColumnaLista("periodo");
   	zLista.AddColumnaLista("forma_pago");
   	zLista.AddColumnaLista("tarifa");
  	zLista.AddColumnaLista("tax");
  	zLista.AddColumnaLista("total");
	 

  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Boleto", "boleto");
  	zFiltros.addEditResponsive("Aerolinea", "carrier");
   	super.ConfigurarFiltros(zFiltros);
  }

}
