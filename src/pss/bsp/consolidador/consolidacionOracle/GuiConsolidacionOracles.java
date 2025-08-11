package pss.bsp.consolidador.consolidacionOracle;

import java.util.Date;

import pss.bsp.consola.referencia.GuiReferencias;
import pss.bsp.consolidador.IConciliable;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiConsolidacionOracles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiConsolidacionOracles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10027; } 
  public String  GetTitle()    throws Exception  { return "Conciliaciones"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiConsolidacionOracle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  	this.addAction(10, "Ver referencias", null, 10055, true, true, true, "Group");
   	
  }
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(GuiReferencias.getReferenciasConsol());
		return null;
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		
		if (GetVision().equals(BizConsolidacionOracle.VISION_LIBRE)) {
			JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "fecha");
			d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
		}
		JFormComboResponsive c = zFiltros.addComboResponsive(
				"Resultados",
				"status", this.getTiposStatus(), true);
	//	c.setRefreshForm(true);
		//c.setPreferredWidth(400);
		zFiltros.addCheckResponsive("Solo tickets", "only_tickets");
		zFiltros.addEditResponsive("Boleto", "d1").setOperator("like");

		
	}
	
	private JWins getTiposStatus() throws Exception {
		return JWins.CreateVirtualWins(BizConsolidacionOracle.ObtenerResultadosConsolidacion());
	}

	@Override
	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_NO;
	}
	
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    super.ConfigurarColumnasLista(zLista);
  	zLista.AddIcono("");
  	zLista.AddColumnaLista(IConciliable.BOLETOS, "d1");
  	zLista.AddColumnaLista(IConciliable.FECHA, "d2");
  	zLista.AddColumnaLista(IConciliable.OPERACION,"d3");
  	zLista.AddColumnaLista(IConciliable.ID_AEROLINEA, "d4");
  	zLista.AddColumnaLista(IConciliable.AEROLINEA, "d5");
  	zLista.AddColumnaLista(IConciliable.TARIFA, "d6");
  	zLista.AddColumnaLista(IConciliable.CONTADO,"d7");
  	zLista.AddColumnaLista(IConciliable.TARJETA, "d8");
  	zLista.AddColumnaLista(IConciliable.TIPO_RUTA, "d9");
  	zLista.AddColumnaLista(IConciliable.BASE_IMPONIBLE, "d10");
  	zLista.AddColumnaLista(IConciliable.COMISION,"d11" );
  	zLista.AddColumnaLista(IConciliable.COMISION_OVER, "d12");
  	zLista.AddColumnaLista(IConciliable.COMISION_PORC,"d13" );
  	zLista.AddColumnaLista(IConciliable.IMP_SOBRE_COMISION, "d14");
  	zLista.AddColumnaLista(IConciliable.IMPUESTO_1, "d15");
  	zLista.AddColumnaLista(IConciliable.IMPUESTO_2, "d16");
  	zLista.AddColumnaLista(IConciliable.NETO, "d17");
  	zLista.AddColumnaLista(IConciliable.TOTALAPAGAR, "d18");
  	zLista.AddColumnaLista(IConciliable.TIPO_TARJETA, "d19");
  	zLista.AddColumnaLista(IConciliable.NUMERO_TARJETA, "d20");
 // 	zLista.AddColumnaLista(IConciliable.OBSERVACION, "d20");
  }
  
@Override
protected void asignFilterByControl(JFormControl control) throws Exception {
	if (control.getName().equals("only_tickets") && control.hasValue()) {
		if (control.getValue().equals("S"))
			getRecords().addFilter("d3", "OPET","like");
		return;
	}
	super.asignFilterByControl(control);
}

}
