package pss.bsp.contrato.total;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTotales extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTotales() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Totales"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTotal.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    	zLista.AddColumnaLista("grupo");
			zLista.AddColumnaLista("moneda");
  		zLista.AddColumnaLista("base_comisionable");
  		zLista.AddColumnaLista("ganancia");


  }
  
  @Override
  public long selectSupraCount() throws Exception {
  	return -1;
  }
		


}
