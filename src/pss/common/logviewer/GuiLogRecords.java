package  pss.common.logviewer;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLogRecords extends JWins {

	private GuiLog logParent;
	
  public GuiLogRecords() throws Exception {
  }
  
  public void setLog(GuiLog v) {
  	logParent=v;
  }
  
  @Override
	public int     GetNroIcono() throws Exception  { return 1083; }
  @Override
	public String  GetTitle()    throws Exception  { return logParent.GetcDato().getFileName(); }
  @Override
	public Class<? extends JWin>   GetClassWin()  { return  GuiLogRecord.class; }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("Tipo");
    zLista.AddColumnaLista("timestamp");
//    zLista.setColumnAlignment("time", JColumnaLista.ALIGNMENT_CENTER);
    zLista.AddColumnaLista("thread");
    zLista.AddColumnaLista("message");
  }

  @Override
  public void ConfigurarFiltros(JFormFiltro filtros) throws Exception {
    JFormControl c = filtros.addCDateTimeResponsive("Desde", "timestamp" );
    c.setIdControl("desde");
    c.setOperator(">=");
    c=filtros.addCDateTimeResponsive("Hasta", "timestamp" );
    c.setIdControl("hasta");
    c.setOperator("<=");
    filtros.addCheckResponsive("Show Error", "show_error" ).SetValorDefault(true);
    filtros.addCheckResponsive("Show Info", "show_info" ).SetValorDefault(true);
    filtros.addCheckResponsive("Show Debug", "show_debug" ).SetValorDefault(true);
    filtros.addCheckResponsive("Show SQL", "show_sql" ).SetValorDefault(true);
  }

}
