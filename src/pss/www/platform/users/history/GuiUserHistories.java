package pss.www.platform.users.history;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUserHistories extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUserHistories() throws Exception {
  }
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiUserHistory.class; }
  @Override
	public int GetNroIcono() throws Exception { return 745; }
  @Override
	public String GetTitle() throws Exception { return "Historial"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }
  
  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("fecha_hora");
  	zLista.AddColumnaLista("user");
  	zLista.AddColumnaLista("clase");
  	zLista.AddColumnaLista("accion");
   	zLista.AddColumnaLista("finalize");
    zLista.AddColumnaLista("Start","time_start");
  	zLista.AddColumnaLista("Rver","time_resolver");
  	zLista.AddColumnaLista("Gtor","time_generator");
  	zLista.AddColumnaLista("Trans","time_tr");
  	zLista.AddColumnaLista("Limbo","time_unknow");
//   	zLista.AddColumnaLista("Response", "time_pt");
//  	zLista.AddColumnaLista("Ftp","time_dt"); // no sirve lo que viene
  	zLista.AddColumnaLista("Total Serv", "time_ajax");
  	zLista.AddColumnaLista("time_build");
  	zLista.AddColumnaLista("time_js");
    zLista.AddColumnaLista("total");
  	zLista.AddColumnaLista("size");
  	zLista.AddColumnaLista("error_msg");
  }


}
