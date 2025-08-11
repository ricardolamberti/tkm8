package pss.bsp.contrato.series.calculado;

import java.util.Date;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiSerieCalculadas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSerieCalculadas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15010; } 
  public String  GetTitle()    throws Exception  { return "Estimaciones"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSerieCalculada.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }





  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Fecha", "fecha");
		Date desde=JDateTools.getFirstDayOfYear(new Date());
		Date hasta=JDateTools.getLastDayOfYear(new Date());
		if (((BizDetalle)	getRecords().getParent())!=null) 
			desde= JDateTools.StringToDate(getRecords().getFilterOperValue("fecha",">="));
		if (((BizDetalle)	getRecords().getParent())!=null) 
			hasta = (JDateTools.StringToDate(getRecords().getFilterOperValue("fecha","<=")));
		d.SetValorDefault(desde,hasta);

  	super.ConfigurarFiltros(zFiltros);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("valor");
    zLista.AddColumnaLista("valor_ant");
    zLista.AddColumnaLista("valor_estimado");
  }
	@Override
	public void readAll() throws Exception {
		if (((BizDetalle)	getRecords().getParent())!=null) ((BizDetalle)	getRecords().getParent()).setFDesde(JDateTools.StringToDate(getRecords().getFilterOperValue("fecha",">=")));
		if (((BizDetalle)	getRecords().getParent())!=null) ((BizDetalle)	getRecords().getParent()).setFHasta(JDateTools.StringToDate(getRecords().getFilterOperValue("fecha","<=")));
		super.readAll();
	}
}