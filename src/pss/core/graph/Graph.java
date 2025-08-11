package pss.core.graph;

import java.io.Serializable;

import pss.common.security.BizUsuario;
import pss.core.graph.analize.AnalizeDataGraph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.model.ModelBullet;
import pss.core.graph.model.ModelGraph;
import pss.core.graph.model.ModelGrid;
import pss.core.graph.model.ModelGridAndLine;
import pss.core.graph.model.ModelMatrix;
import pss.core.graph.model.ModelMatrixAndLine;
import pss.core.graph.model.ModelPie;
import pss.core.graph.model.ModelVector;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
/**
 * Graph: clase principal del modelo de graficos, se encarga de procesar la informacion de un JWin, y contenerla, 
 * para que luego JWebWinList genere un XML, para que page.img.filter.xsl genere un FusionChart.
 * @author ricardo.lamberti
 *
 */

public abstract class Graph implements Serializable {
	public static final String GRAPH_ATTR_XAXISNAME="xAxisName";
	public static final String GRAPH_ATTR_YAXISNAME="yAxisName";
	public static final String GRAPH_ATTR_PYAXISNAME="PYAxisName";
	public static final String GRAPH_ATTR_SYAXISNAME="SYAxisName";
	public static final String GRAPH_ATTR_DECIMALPRECISON="decimalPrecision";
	public static final String GRAPH_ATTR_FORMATNUMBERSCALE="formatNumberScale";
	public static final String GRAPH_ATTR_CHARTRIGHTMARGIN="chartRightMargin";
	public static final String GRAPH_ATTR_CANVASBODERCOLOR="canvasBorderColor";
	public static final String GRAPH_ATTR_CANVSBGCOLOR="canvasBgColor";
	public static final String GRAPH_ATTR_BGCOLOR="bgColor";
	public static final String GRAPH_ATTR_NUMDIVLINES="numDivLines";
	public static final String GRAPH_ATTR_DIVLINECOLOR="divLineColor";
	public static final String GRAPH_ATTR_NUMBERPREFIX="numberPrefix";
	public static final String GRAPH_ATTR_BEANBORDERCOLOR="bearBorderColor";
	public static final String GRAPH_ATTR_BEARFILLCOLOR="bearFillColor";
	public static final String GRAPH_ATTR_BULLBORDERCOLOR="bullBorderColor";
	public static final String GRAPH_ATTR_BASEFONTCOLOR="baseFontColor";
	public static final String GRAPH_ATTR_OUTCNVBASEFONTCOLOR="outCnvBaseFontColor";
	public static final String GRAPH_ATTR_HOVERCAPBORDERCOLOR="hoverCapBorderColor";
	public static final String GRAPH_ATTR_HOVERCAPBGCOLOR="hoverCapBgColor";
	public static final String GRAPH_ATTR_ROTATENAMES="rotateNames";
	public static final String GRAPH_ATTR_SUBCAPTION="subcaption";
	public static final String GRAPH_ATTR_ANCHORSIDES="anchorSides";
	public static final String GRAPH_ATTR_ANCHORRADIUS="anchorRadius";
	public static final String GRAPH_ATTR_ANCHORBORDERCOLOR="anchorBorderColor";
	public static final String GRAPH_ATTR_SLICINGDISTANCE="slicingDistance";
	public static final String GRAPH_ATTR_ISSLICED="isSliced";
	public static final String GRAPH_ATTR_ALPHA="alpha";
	public static final String GRAPH_ATTR_AREAALPHA="areaAlpha";
	public static final String GRAPH_ATTR_HOVERTEXT="hoverText";
	public static final String GRAPH_ATTR_SHOWAREABORDER="showAreaBorder";
	public static final String GRAPH_ATTR_SHOWAREABORDERTHICKNESS="areaBorderThickness";
	public static final String GRAPH_ATTR_AREABORDERCOLOR="areaBorderColor";
	public static final String GRAPH_ATTR_SHOWLEGENDS="showLegend";
	public static final String GRAPH_ATTR_EXPORTENABLED="exportEnabled";
	
	
	public static final String GRAPH_TYPE_Area2D = "GraphArea2D";
	public static final String GRAPH_TYPE_Bar2D = "GraphBar2D";
	public static final String GRAPH_TYPE_Col2DLineDY = "GraphCol2DLineDY";
	public static final String GRAPH_TYPE_Col3DLineDY = "GraphCol3DLineDY";
	public static final String GRAPH_TYPE_Column2D = "GraphColumn2D";
	public static final String GRAPH_TYPE_Column3D = "GraphColumn3D";
	public static final String GRAPH_TYPE_Doughnut2D = "GraphDoughnut2D";
	public static final String GRAPH_TYPE_Funnel = "GraphFunnel";
	public static final String GRAPH_TYPE_Line2D = "GraphLine2D";
	public static final String GRAPH_TYPE_MSArea2D = "GraphMSArea2D";
	public static final String GRAPH_TYPE_MSBar2D = "GraphMSBar2D";
	public static final String GRAPH_TYPE_MSColumn3D = "GraphMSColumn3D";
	public static final String GRAPH_TYPE_MSLine = "GraphMSLine";
	public static final String GRAPH_TYPE_Pie2D = "GraphPie2D";
	public static final String GRAPH_TYPE_Pie3D = "GraphPie3D";
	public static final String GRAPH_TYPE_StArea2D = "GraphStArea2D";
	public static final String GRAPH_TYPE_StBar2D = "GraphStBar2D";
	public static final String GRAPH_TYPE_StCol2D = "GraphStCol2D";
	public static final String GRAPH_TYPE_StCol3D = "GraphStCol3D";
	public static final String GRAPH_TYPE_XYLines = "GraphXYLine";
	public static final String GRAPH_TYPE_SCRIPT_BAR3D = "GraphScriptBar3D";
	public static final String GRAPH_TYPE_SCRIPT_RELOJ = "GraphScriptReloj";
	public static final String GRAPH_TYPE_SCRIPT_MEDIORELOJ = "GraphScriptMedioReloj";
	public static final String GRAPH_TYPE_SCRIPT_MUNDOARC = "GraphScriptWorldArc";
	public static final String GRAPH_TYPE_SCRIPT_MAP = "GraphScriptMap";
	public static final String GRAPH_TYPE_SCRIPT_ARCOSMAP = "GraphScriptArcosMap";
	public static final String GRAPH_TYPE_SCRIPT_ASTERPLOT = "GraphScriptAsterPlot";
	public static final String GRAPH_TYPE_SCRIPT_SERIETEMP = "GraphScriptSerieTemporal";
	public static final String GRAPH_TYPE_SCRIPT_MULTICHAR = "GraphScriptMultiChart";
	public static final String GRAPH_TYPE_SCRIPT_PIE = "GraphScriptPie";
	public static final String GRAPH_TYPE_SCRIPT_STACKED = "GraphScriptStacked";
	public static final String GRAPH_TYPE_SCRIPT_DONUT = "GraphScriptDonut";
	public static final String GRAPH_TYPE_SCRIPT_BULLET = "GraphScriptBullet";
	public static final String GRAPH_TYPE_SCRIPT_LIQUID = "GraphScriptLiquid";
	public static final String GRAPH_TYPE_SCRIPT_SPIRAL = "GraphScriptSpiral";
	public static final String GRAPH_TYPE_SCRIPT_RADIALBAR = "GraphScriptRadialBar";
	
	public static final String TIME_IN_DATASET = "TD";
	public static final String TIME_IN_CATEGORY = "TC";
	public static final String TIME_NO = "XX";

	private String title;

	private JMap<String,TrendLines> mapTrendLines;
	private JMap<String,Categories> categories;
	private JMap<String,Dataset> datasets;
	private JMap<String,String> atributtes;
	private double maxValue=0;
	private ModelGraph modelGraph=null;
	private boolean hasExtractDataIntegred=true;
	private int refresh=-1;
	private int sizeOthers=10;

	
	
	public int getSizeOthers() {
		return sizeOthers;
	}


	public void setSizeOthers(int sizeOthers) {
		this.sizeOthers = sizeOthers;
	}


	public int getRefresh() {
		return refresh;
	}

	
	public void setRefresh(int refresh) {
		this.refresh=refresh;
	}

	private JWins alternativeData;
	
	
	public boolean hasExtractDataIntegred() throws Exception {
		return hasExtractDataIntegred;
	}
	
	public String getTitle() {
		return title;
	}
	
	public abstract ModelGraph createModelGraph();
	
	public ModelGraph getModel() {
		if (modelGraph==null)
			modelGraph = createModelGraph();
		return modelGraph;
	}
	
	public  JMap<String,TrendLines> getMapTrendLines() {
		if (mapTrendLines==null) 
			mapTrendLines = JCollectionFactory.createMap();
		return mapTrendLines;
	}


	
	public void setTitle(String title) {
		this.title=title;
	}
	public Graph() {
	}
	public Graph(String zTitle) {
		title = zTitle;
	}
	public abstract String getSWF();

	/** un trendline es una linea en el grafico
	 * 
	 * @param name
	 * @param startValue
	 * @param endValue
	 * @param color
	 * @param displayValue
	 * @param thickness
	 * @param isTrendZone
	 * @param showOnTop
	 * @param alpha
	 * @return
	 */
	public TrendLines addTrendLines(String name,double startValue, double endValue, String color, String displayValue, int thickness, boolean isTrendZone, boolean showOnTop, int alpha) {
		TrendLines tl = new TrendLines(startValue, endValue,  color,  displayValue,  thickness,  isTrendZone,  showOnTop,  alpha);
		getMapTrendLines().addElement(name, tl);
		return tl;
	}


	
	public JMap<String,Categories> getCategories() {
		if (categories==null)
			categories = JCollectionFactory.createOrderedMap();
		return categories;
	}

	public void setCategories(JMap<String,Categories> categories) {
		this.categories=categories;
	}

	public JMap<String,Dataset> getDatasets() {
		if (datasets==null)
			datasets = JCollectionFactory.createOrderedMap();
		return datasets;
	}

	
	public void setDatasets(JMap<String,Dataset> datasets) {
		this.datasets=datasets;
	}

	public double getMaxValue() {
		return maxValue;
	}


	
	public void setMaxValue(double maxValue) {
		this.maxValue=maxValue;
	}

	public void addAttributes(JMap<String, String> attributes){
	}

	/** agrega atributos a todas los valores del grafico, ver espec FusionChart
	 * Si se quiere agregar un atributo a un Valor en particular, ver el metodo apropiado en JWin
	 * @param attributes map donde agregar los atributops
	 */
	
	public void addGraphValueAttributes(JMap<String, String> attributes) {
		
	}
	/** agrega atributos a todas los dataset del grafico, ver espec FusionChart
	 * Si se quiere agregar un atributo a un Dataset en particular, ver el metodo apropiado en JWin
	 * 
	 * @param attributes map donde agregar los atributops
	 */
	public void addGraphDatasetAttributes(JMap<String, String> attributes) {
		
	}
	/** agrega atributos a todas las categorias del grafico, ver espec FusionChart
	 * Si se quiere agregar un atributo a una Categoria en particular, ver el metodo apropiado en JWin
	 * 
	 * @param attributes map donde agregar los atributops
	 */
	public void addGraphCategorieAttributes(JMap<String, String> attributes) {
		
	}
	
	public JMap<String, String> getAtributtes() {
		if (atributtes==null) {
			atributtes = JCollectionFactory.createMap();
			atributtes.addElement("numberPrefix","$"); 
			atributtes.addElement("decimalPrecision","0"); 
			getModel().addAttributes(atributtes);
			addAttributes(atributtes);;
		}
		return atributtes;
	}

	public void addAtributtes(String key,String value) {
		getAtributtes().addElement(key, value);
	}	
	public void setAtributtes(JMap<String, String> atributtes) {
		this.atributtes=atributtes;
	}

	
	public JWins getAlternativeData() {
		return alternativeData;
	}

	/**
	 * Si no se especifica nada el grafico se realiza tomando los datos del Jwins asociado al JWebWinList.
	 * Pero si se llama a setAlternativeData, se utiliza este como fuente de los datos 
	 * @param alternativeData un conjunto de datos alternativos
	 */
	public void setAlternativeData(JWins alternativeData) {
		this.alternativeData=alternativeData;
		hasExtractDataIntegred=false;
	}

	
  /**
   * Cambio del modelo, Los diferentes graficos ya tienen asignado un grafico por default, 
   * pero si se lo quiere cambiar se puede utilizar este metodo
   * @param modelGraph Modelo de datos
   */
	public void setModel(ModelGraph modelGraph) {
		this.modelGraph=modelGraph;
	}
	
	public static JRecords<BizVirtual> getGraphTypes() throws Exception {
		JRecords<BizVirtual> records =JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Line2D, "Lineal 2D", 15024));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_MSLine, "Multi-Serie Lineal", 15025));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Area2D, "Area 2D", 15026));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_StArea2D, "Apilada Area 2D", 15027));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_MSArea2D, "Multi-Serie Area 2D", 15028));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Bar2D, "Bar 2D", 15029));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_StBar2D, "Apilada Barra 2D", 15030));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_MSBar2D, "Multi-Serie Barras 2D", 15031));
//		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Column2D, "Columnas 2D", 15029));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Column3D, "Columnas 3D", 15032));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Col2DLineDY, "Columnas 2D y Linea", 15033));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Col3DLineDY, "Columnas 3D y Linea", 15034));
//		records.addItem(JRecord.virtualBD(GRAPH_TYPE_StCol2D, "Apilada Columna 2D", 15027));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_StCol3D, "Apilada Columna 3D", 15035));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_XYLines, "Apilada XY linea temporales", 15036));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_MSColumn3D, "Multi-Serie Columna 3D", 15037));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Pie2D, "Torta 2D", 15038));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Pie3D, "Torta 3D", 15039));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_Doughnut2D, "Dona 2D", 15040));
	//	records.addItem(JRecord.virtualBD(GRAPH_TYPE_Funnel, "Funnel", 15040));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_BAR3D, "Barras 3D", 15041));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_RELOJ, "Reloj progreso", 15042));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_MEDIORELOJ, "Medio Reloj progreso", 15201));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_MUNDOARC, "Arcos Mundo esferico", 15043));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_MAP, "Mapa", 15044));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_ARCOSMAP, "Arcos sobre el mapa", 15045));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_ASTERPLOT, "Torta Multiple", 15046));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_SERIETEMP, "Series temporales", 15047));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_MULTICHAR, "Multiples barras", 15048));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_PIE, "Torta interactiva", 15202));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_STACKED, "Multi Area", 15100));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_DONUT, "Multi donut", 15101));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_BULLET, "Multi bullet", 15049));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_LIQUID, "Multi Liquido", 15110));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_SPIRAL, "Espiral", 15214));
		records.addItem(JRecord.virtualBD(GRAPH_TYPE_SCRIPT_RADIALBAR, "Radial Barra", 15111));
		return records;
	}
	
	public static JMap<String, String> getGraphModelos(String tipo) throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement("A", "Automático");
		if (tipo.toLowerCase().indexOf("linedy")==-1)
			map.addElement(ModelGrid.class.getSimpleName(), "Grilla");
		if (tipo.toLowerCase().indexOf("linedy")!=-1)
			map.addElement(ModelGridAndLine.class.getSimpleName(), "Grilla y linea");
		if (tipo.toLowerCase().indexOf("linedy")==-1)
			map.addElement(ModelMatrix.class.getSimpleName(), "Matriz");
		if (tipo.toLowerCase().indexOf("linedy")!=-1)
			map.addElement(ModelMatrixAndLine.class.getSimpleName(), "Matriz y linea");
		map.addElement(ModelVector.class.getSimpleName(), "Vector");
		map.addElement(ModelPie.class.getSimpleName(), "Torta");
		map.addElement(ModelBullet.class.getSimpleName(), "Bala");
		return map;
	}
	
	
	
	protected JColumnaLista[] determineColumnsFromWinList(JWinList zWinList) throws Exception {
		JColumnaLista[] columns = new JColumnaLista[zWinList.GetColumnasLista().size()];
		JIterator<JColumnaLista> oColsIt = zWinList.GetColumnasLista().getIterator();
		int counter = 0;
		while (oColsIt.hasMoreElements()) {
			columns[counter] = oColsIt.nextElement();
			counter++;
		}
		return columns;
	}
	
	public void localFill(JWinList wlist, JFilterMap filterMap,JFormFiltro filterBar) throws Exception {
    AnalizeDataGraph adg = new AnalizeDataGraph();
    JWins wins = wlist.getWins();
    JColumnaLista[] columna = determineColumnsFromWinList(wlist);
    adg.analizeGraphs(columna);
    if (filterMap!=null) wins.asignFiltersFromFilterMap(filterMap);
    if (filterBar!=null) wins.asignFiltersFromFilterBar(filterBar);
    if (wins.GetVision().equals("PREVIEW")) {
      wins.setPagesize(10);
      wins.getRecords().setWithUse(true);
    } else
    	wins.setPagesize(-1);
    wins.setOffset(-1);
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando gráfico", -1, -1, false, null);

    wins.readAll();
    wins.firstRecord();
    JList<Graph> grs = JCollectionFactory.createList();
    grs.addElement(this);
    adg.startAnalize(columna, grs);
    int k=0;
    while (wins.nextRecord()) {
    	JWin win = wins.getRecord();
  		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando gráfico, analizando fila ", k++, 1000, false, null);
   	  adg.analizeRow(columna,win);
    }
    adg.endAnalize(columna,grs);
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando gráfico", 100, 100, false, null);
   
	}
	
	public String getImage(int width, int height, boolean xdate) throws Exception {
		return null;
	}

	public String getImage(int width, int height, boolean xdate, String prefixCateg) throws Exception {
		return null;
	}

	public String getImage(int width, int height) throws Exception {
		return null;
	}
	
	public boolean canUseWithinCategory() throws Exception {
		return false;
	}
	public int getGeoRequest() throws Exception {
		return 0;
	}
	public String isTimeLine() throws Exception {
		return TIME_IN_DATASET;
	}
	public boolean isMultiSerie() throws Exception {
		return false;
	}

}
