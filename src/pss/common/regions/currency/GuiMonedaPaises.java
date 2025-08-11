package pss.common.regions.currency;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMonedaPaises extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMonedaPaises() throws Exception {
//    SetVision(GuiMonedaPais.COUNTRY);
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 5041; }
  @Override
	public String  GetTitle()    throws Exception  { return "Conversiones"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiMonedaPais.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_moneda");
//    zLista.AddColumnaLista("cotiz_compra");
//    zLista.AddColumnaLista("cotiz_venta");
//    if (!this.GetVision().equals(BizMonedaPais.VISION_VIEW))
//    	zLista.AddColumnaLista("cotiz_contab");
  }

  
  
//  @Override
//  public boolean hasNavigationBar() throws Exception {
//  	return !this.GetVision().equals(BizMonedaPais.VISION_VIEW);
//  }

}
