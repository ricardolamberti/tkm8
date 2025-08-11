package pss.common.event.sql.datos;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiSqlEventDatos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEventDatos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15012; } 
  public String  GetTitle()    throws Exception  { return "Evento datos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSqlEventDato.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }


  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Fecha", "fecha");
		d.SetValorDefault(JDateTools.getFirstDayOfYear(new Date()),JDateTools.getDateEndDay(new Date()));
  	
  	super.ConfigurarFiltros(zFiltros);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("value");
    zLista.AddColumnaLista("var_val");
    zLista.AddColumnaLista("var_porc");
    zLista.AddColumnaLista("tendenciaview");
  }
	@Override
	public void readAll() throws Exception {
//		((BizSqlEvent)	getRecords().getParent()).setFDesde(JDateTools.StringToDate(getRecords().getFilterOperValue("fecha",">=")));
//	  ((BizSqlEvent)	getRecords().getParent()).setFHasta(JDateTools.StringToDate(getRecords().getFilterOperValue("fecha","<=")));
		super.readAll();
	}
}
