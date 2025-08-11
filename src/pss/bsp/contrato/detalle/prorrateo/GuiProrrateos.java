package pss.bsp.contrato.detalle.prorrateo;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiProrrateos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiProrrateos() throws Exception {
  }
 

  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Prorrateos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiProrrateo.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Prorrateo" );

   }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("cliente");
    zLista.AddColumnaLista("valor");
  }
  
  @Override
  public JWin createJoinWin() throws Exception {
  	GuiProrrateo w = (GuiProrrateo) super.createJoinWin();
  	w.GetcDato().addFilter( "company", getFilterValue("company"));
  	w.GetcDato().addFilter( "contrato",( getFilterValue("contrato")));
  	w.GetcDato().addFilter( "detalle",( getFilterValue("detalle")) );
  
  	return w;
  }
}
