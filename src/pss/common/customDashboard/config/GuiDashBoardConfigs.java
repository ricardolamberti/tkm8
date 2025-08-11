package pss.common.customDashboard.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDashBoardConfigs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDashBoardConfigs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 5012; } 
  public String  GetTitle()    throws Exception  { return "Configuración Dashboard"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDashBoardConfig.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
//    zLista.AddColumnaLista("dash_order");
    zLista.AddColumnaLista("dash_descr").setActionOnClick(2);
    zLista.AddColumnaLista("descr_historico");
    zLista.AddColumnaLista("descr_size");
    //    zLista.AddColumnaLista("dias_atras");    
  }
  
//  public void fill() throws Exception {
//  	String company = this.getFilterValue("company");
//  	String userid = this.getFilterValue("userid");
//		JIterator<DashBoardData> iter= DashBoardModule.getMap().getValueIterator();
//		while (iter.hasMoreElements()) {
//			DashBoardData d = iter.nextElement();
//			BizDashBoardConfig c = new BizDashBoardConfig();
//			c.setCompany(company);
//			c.setUserid(userid);
//			c.setDashDescription(d.getId());
//			this.getRecords().addItem(c);
//		}
//		this.getRecords().setStatic(true);
//  }
  
  @Override
  public String getPreviewFlag() throws Exception {
  	return JWins.PREVIEW_MAX;
  }

}
