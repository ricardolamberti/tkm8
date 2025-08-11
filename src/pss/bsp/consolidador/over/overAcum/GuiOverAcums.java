package pss.bsp.consolidador.over.overAcum;

import pss.bsp.consola.referencia.GuiReferencias;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.implementations.GraphBar2D;
import pss.core.graph.implementations.GraphCol3DLineDY;
import pss.core.graph.implementations.GraphPie3D;
import pss.core.graph.model.ModelGrid;
import pss.core.graph.model.ModelVector;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JWinList;

public class GuiOverAcums extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiOverAcums() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 10006;
	}

	public String GetTitle() throws Exception {
		return "Analisis Overs por aerolineas";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiOverAcum.class;
	}
	
	@Override
	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_NO;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
  public void createActionMap() throws Exception {
  	 //   addActionNew( 1, "Nuevo Registro" );
  	  	this.addAction(10, "Ver referencias", null, 10055, true, true, true, "Group");
  	   	
  	  }
  		public JAct getSubmitFor(BizAction a) throws Exception {
  			if (a.getId() == 10)	return new JActWins(GuiReferencias.getReferenciasDifer());
  			return null;
  		}


	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("id_aerolinea");
		zLista.AddColumnaLista("count_boletos");
		zLista.AddColumnaLista("sum_over_ped");
		zLista.AddColumnaLista("sum_over_rec");
		zLista.AddColumnaLista("sum_dif");
	}

	@Override
	public boolean hasSubDetail() {
		return true;
	}

	@Override
	public void readAll() throws Exception {
		this.getRecords().clearFields();
		this.getRecords().addField("company");
//		this.getRecords().addField("owner");
		this.getRecords().addField("idpdf");
		this.getRecords().addField("id_aerolinea");
		
		this.getRecords().getStructure().setTable("BSP_ANALISISOVER");
		
		this.getRecords().addFuntion("count(boleto) count_boletos");
		this.getRecords().addFuntion("sum(over_ped) sum_over_ped");
		this.getRecords().addFuntion("sum(over_rec) sum_over_rec");
		this.getRecords().addFuntion("sum(over_dif) sum_dif");
		
		this.getRecords().addGroupBy("BSP_ANALISISOVER","company");
//		this.getRecords().addGroupBy("BSP_ANALISISOVER","owner");
		this.getRecords().addGroupBy("BSP_ANALISISOVER","idpdf");
		this.getRecords().addGroupBy("BSP_ANALISISOVER","id_aerolinea");
		super.readAll();
	}

	public long selectSupraCount() throws Exception {
		// TODO Auto-generated method stub
		return -1;
	}
	@Override
	public void ConfigurarGraficos(JWinList lista) throws Exception {
		Graph gr;

		gr=new GraphCol3DLineDY("Analisis Over por aerolinea");
		lista.addGrafico(gr);
		ModelGrid model = new ModelGrid();
		model.addColumn("id_aerolinea",ModelGrid.GRAPH_FUNCTION_CATEGORIE);
		model.addColumn(new Dataset("Over Pedido"),"sum_over_ped",ModelGrid.GRAPH_FUNCTION_VALUE);
		model.addColumn(new Dataset("Over Recibido"),"sum_over_rec",ModelGrid.GRAPH_FUNCTION_VALUE);
		model.addColumn(new Dataset("Diferencia"),"sum_dif",ModelGrid.GRAPH_FUNCTION_VALUE);
		gr.setModel(model);
		gr.addAtributtes("formatNumberScale", "0");
		gr.addAtributtes("decimalPrecision", "2");
		gr.addAtributtes("numberPrefix", "");
		gr.addAtributtes("decimalSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getDecimalSeparator());
		gr.addAtributtes("thousandSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getGroupingSeparator());

		gr=new GraphPie3D("Analisis Over por aerolinea (cantidad boletos)");
		lista.addGrafico(gr);
		gr.getModel().addColumn("id_aerolinea", ModelVector.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("count_boletos", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.addAtributtes("formatNumberScale", "0");
		gr.addAtributtes("decimalPrecision", "2");
		gr.addAtributtes("numberPrefix", "");
		gr.addAtributtes("decimalSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getDecimalSeparator());
		gr.addAtributtes("thousandSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getGroupingSeparator());

		gr=new GraphPie3D("Analisis Over por aerolinea (monto over pedidos)");
		lista.addGrafico(gr);
		gr.getModel().addColumn("id_aerolinea", ModelVector.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("sum_over_ped", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.addAtributtes("formatNumberScale", "0");
		gr.addAtributtes("decimalPrecision", "2");
		gr.addAtributtes("numberPrefix", "");
		gr.addAtributtes("decimalSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getDecimalSeparator());
		gr.addAtributtes("thousandSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getGroupingSeparator());

		gr=new GraphPie3D("Analisis Over por aerolinea (monto over recibidos)");
		lista.addGrafico(gr);
		gr.getModel().addColumn("id_aerolinea", ModelVector.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("sum_over_rec", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.addAtributtes("formatNumberScale", "0");
		gr.addAtributtes("decimalPrecision", "2");
		gr.addAtributtes("numberPrefix", "");
		gr.addAtributtes("decimalSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getDecimalSeparator());
		gr.addAtributtes("thousandSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getGroupingSeparator());

		gr=new GraphBar2D("Analisis Over por aerolinea (diferencia)");
		lista.addGrafico(gr);
		gr.getModel().addColumn("id_aerolinea", ModelVector.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("sum_dif", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.addAtributtes("formatNumberScale", "0");
		gr.addAtributtes("decimalPrecision", "2");
		gr.addAtributtes("numberPrefix", "");
		gr.addAtributtes("decimalSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getDecimalSeparator());
		gr.addAtributtes("thousandSeparator", ""+JRegionalFormatterFactory.getBusinessFormatter().getGroupingSeparator());

	}

}
