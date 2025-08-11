package pss.bsp.consolid.model.diferenciaLiq;

import pss.bsp.consola.referencia.GuiReferencias;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormSwingCombo;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.win.JWins;

public class GuiDiferenciaLiqs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDiferenciaLiqs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10046; } 
  public String  GetTitle()    throws Exception  { return "Diferencias Liq."; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDiferenciaLiq.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	 //   addActionNew( 1, "Nuevo Registro" );
  	this.addAction(10, "Ver referencias", null, 10055, true, true, true, "Group");
  	this.addAction(20, "Conciliar", null, 10055, true, true, true, "Group");
    	   	
  	  }
  		public JAct getSubmitFor(BizAction a) throws Exception {
  			if (a.getId() == 10)	return new JActWins(GuiReferencias.getReferenciasDifer());
  			if (a.getId()==20) return new JActSubmit(this) {
  				public void submit() throws Exception {
  					conciliar();
  				};
  			};
  	  			return null;
  		}
  		@Override
  		public String getPreviewFlag() throws Exception {
  			return JWins.PREVIEW_NO;
  		}
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {

		JFormComboResponsive c = zFiltros.addComboResponsive(
				"Resultados conciliacion",
				"status", this.getTiposStatus(), true);
		zFiltros.addEditResponsive("Operacion", "operacion").setOperator("ilike");
  	zFiltros.addEditResponsive("Boleto", "boleto").setOperator("=");
  	zFiltros.addEditResponsive("Forma de pago", "formapago").setOperator("ilike");
  	zFiltros.addEditResponsive("Aerolinea", "id_aerolinea").setOperator("ilike");
 	}
	private void conciliar() throws Exception {
		long id = Long.parseLong(getFilterValue("id_liq"));
		BizLiqHeader liq = new BizLiqHeader();
		liq.read(id);
		liq.execProcConsolidarLiquidacion();
	}
	private JWins getTiposStatus() throws Exception {
		return JWins.CreateVirtualWins(ObtenerResultadosConsolidacion());
	}
	public static JRecords<BizVirtual> ObtenerResultadosConsolidacion() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(IConsolidador.OK, "Correctos", 943));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.DISTINCT, "Con diferencias", 940));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.ONLY_BSP, "Solo en BSP", 939));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.ONLY_LIQ, "Solo en LIQ", 941));
		return oBDs;
	}
//	public void createTotalizer(JWinList oLista) throws Exception {
//		oLista.addTotalizerText("boleto", "Totales"); // un texto
//		oLista.addTotalizer("tarifa", 2, JTotalizer.OPER_SUM); // la suma del campo
//		oLista.addTotalizer("contado", 2, JTotalizer.OPER_SUM); // la suma del campo
//		oLista.addTotalizer("credito", 2, JTotalizer.OPER_SUM); // la suma del campo
//		oLista.addTotalizer("impuesto", 2, JTotalizer.OPER_SUM); // la suma del campo
//		oLista.addTotalizer("comision", 2, JTotalizer.OPER_SUM); // la suma del campo
//		}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("Boleto","boleto");
  	zLista.AddColumnaLista("status");
  	zLista.AddColumnaLista("operacion");
  	zLista.AddColumnaLista("tipo_ruta");
  	zLista.AddColumnaLista("id_aerolinea");
  	zLista.AddColumnaLista("tarifa");
  	zLista.AddColumnaLista("contado");
  	zLista.AddColumnaLista("credito");
  	zLista.AddColumnaLista("comision");
  	zLista.AddColumnaLista("impuesto");
  	zLista.AddColumnaLista("total");
  	zLista.AddColumnaLista("formapago");

   }
  
}