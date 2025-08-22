package pss.bsp.consolid.model.trxseg;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiArTrxDKsSeg extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiArTrxDKsSeg() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Trxs Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiArTrxDKSeg.class; }
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
  	zLista.AddColumnaLista("factura");
 


  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("factura", "factura").setOperator("=");
   	super.ConfigurarFiltros(zFiltros);
  }

}
