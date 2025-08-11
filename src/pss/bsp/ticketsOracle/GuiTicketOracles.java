package pss.bsp.ticketsOracle;

import java.util.Date;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiTicketOracles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTicketOracles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10056; } 
  public String  GetTitle()    throws Exception  { return "Tickets Oracle"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTicketOracle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	addActionNew( 1, "Nuevo período" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
  //  zLista.AddColumnaLista("idPDF");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("estado");
	

  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Fecha", "fecha_desde");
		d.SetValorDefault(JDateTools.getFirstDayOfYear(new Date()),JDateTools.getLastDayOfYear(new Date()));

		super.ConfigurarFiltros(zFiltros);
  }

}
