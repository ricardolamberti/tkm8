package pss.core.reports.jasper;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import pss.JPss;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.XML.JXMLTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;


public class JDynamicReport  {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////

	private DynamicReport oDReport=null;
  //private DynamicReportBuilder  oFReport=null;
  private FastReportBuilder  oFReport=null;
  private JWinList oWinList=null; 
  private JWins oWins=null;
  private Style detailStyle=null;
  private Style headerStyle=null;
  private Style oddRowStyle=null;
  private Style numberStyle=null;
  private Style booleanStyle=null;
  private Style headerVariables=null;
  private Map<String, Object> oParameters;
  private int charCounter=0;
  
  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  
  public JDynamicReport(JWins wins) {
  		this.setWins(wins);
  }
	
  public JDynamicReport(JWinList winList) throws Exception {
		this.setWins(winList.getWins());
		this.setWinList(winList);
}
  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE METHODS
  //
  //////////////////////////////////////////////////////////////////////////////
 
  
  protected JasperReport CreateJasper() throws Exception {
  	JasperReport jr = null;
  	try {
  		jr = this.FastReport();
    } catch (Exception ex) {
      PssLogger.logDebug(ex, "Could not create Jasper instance " );
    }
    return jr;
 	}
	
  public void setWins(JWins zValue) {	this.oWins=zValue; }  
  public JWins getWins() throws Exception { return this.oWins; } 
  public void setWinList(JWinList zValue) {	this.oWinList=zValue; }  
  
  private void CreateStyles() throws Exception {

  	this.detailStyle = new StyleBuilder(false)
  			.setTransparency(Transparency.OPAQUE)
  			.setName("detailStyle")
  			.setFont(new  Font(7,Font._FONT_ARIAL,false))
  			.setTransparency(Transparency.OPAQUE)
  			.build();
  	
  	this.detailStyle.setBlankWhenNull(true);
		
  	this.oddRowStyle = new Style();
  	this.oddRowStyle.setName("oddRowStyle");
  	this.oddRowStyle.setBorder(Border.NO_BORDER);
		//Color veryLightGrey = new Color(230,230,230);
		Color veryLightYellow=new Color(255,255,204);
		//this.oddRowStyle.setBackgroundColor(veryLightGrey);
		this.oddRowStyle.setBackgroundColor(veryLightYellow);
		this.oddRowStyle.setTransparency(Transparency.OPAQUE);
		
  	this.booleanStyle = Style.createBlankStyle("booleanStyle", "detailStyle");
  	this.booleanStyle.setHorizontalAlign(HorizontalAlign.CENTER);

		
		this.numberStyle = Style.createBlankStyle("numberStyle", "detailStyle");
		this.numberStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		
		this.headerStyle = new Style();
		this.headerStyle.setName("headerStyle");
		this.headerStyle.setFont(new  Font(8,Font._FONT_ARIAL,true));
		this.headerStyle.setBorderTop(Border.PEN_2_POINT);
		this.headerStyle.setBorderBottom(Border.THIN);
		this.headerStyle.setBackgroundColor(Color.gray);
		this.headerStyle.setTransparency(Transparency.OPAQUE);
		this.headerStyle.setTextColor(Color.BLACK);
		this.headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		this.headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

		this.headerVariables = Style.createBlankStyle("headerVariables", "detailStyle");
		this.headerVariables.setFont(new  Font(8,Font._FONT_ARIAL,true));
		//this.headerVariables.setBorderTop(Border.THIN);
		//this.headerVariables.setBorderBottom(Border.THIN);
		this.headerVariables.setBackgroundColor(Color.WHITE);
		this.headerVariables.setTransparency(Transparency.OPAQUE);
		this.headerVariables.setTextColor(Color.BLACK);
		this.headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		this.headerVariables.setVerticalAlign(VerticalAlign.MIDDLE);
  }
  
  
  private DynamicReport buildReport() throws Exception {
		//this.oFReport = new DynamicReportBuilder();
  	this.oFReport = new FastReportBuilder();
		this.oFReport.setUseFullPageWidth(true);
		this.oFReport.setQuery("/data/line", DJConstants.QUERY_LANGUAGE_XPATH);
		this.CreateStyles();
		this.oFReport.setDefaultStyles(null,null,null, this.detailStyle);
		this.oFReport.addStyle(this.detailStyle);
		this.oFReport.setHeaderHeight(15);
		this.oFReport.setDetailHeight(10);
		this.FastColumnBuilder();
		this.BuildFilters();
		this.oFReport.setPrintBackgroundOnOddRows(true);
 	 	this.oFReport.setOddRowBackgroundStyle(oddRowStyle); 	 	 	
 	 	this.oFReport.setGrandTotalLegend("Totales");
 	 	this.oFReport.setGrandTotalLegendStyle(this.headerVariables);

	 	
		String templ = this.AssignTemplate();
		this.oFReport.setTemplateFile(templ);
		this.oFReport.setUseFullPageWidth(true);
		DynamicReport dr = this.oFReport.build();
		//GroupLayout hj = new GroupLayout(false, false, false, false, false);
		//this.oFReport.setGroupLayout(1, hj);
		return dr;
  }

  private void BuildFilters() throws Exception {
		if (this.getWins().getFilters() != null) {
			JOrderedMap<String, ArrayList<Object>> oFilters=this.getWins().getFilters();
			Iterator<String> iter = oFilters.keyIterator() ;
			int h = 0;
			while (iter.hasNext() ) {
				String i = iter.next();
				ArrayList<Object> filterValue = oFilters.getElement(i);
				if (filterValue.get(1).equals(true) ){
					h += 1;
					this.setParameter("FILTER_" + h, i + ": " + filterValue.get(0).toString()) ;
				}
			}
		}
  }
	private String AssignTemplate() throws Exception {
		String tmptempl = "";
		if (this.charCounter > 150) 
			 tmptempl = "core/reports/templates/GenericWinsTemplateLandscape.jrxml" ;
			else
			 tmptempl = "core/reports/templates/GenericWinsTemplatePortrait.jrxml" ;
		URL resource =	JPss.class.getResource(tmptempl);
		tmptempl = resource.getPath();
		return tmptempl;
	}
		
  private void FastColumnBuilder() throws Exception {
  	JTotalizer oTotalizer = this.getWinList().getTotalizer();
  	JIterator<JColumnaLista> iter = this.getWinList().GetColumnasLista().getIterator();
 	 	while (iter.hasMoreElements()) {
 	 		JColumnaLista i = iter.nextElement(); 
 	 		if (i.getFixedProp() != null ) {
 	 			AbstractColumn oCol = BuildColumn(i);
 	 			this.oFReport.addColumn(oCol);
 				if (oTotalizer != null) {
	 					this.AddTotilizer(oTotalizer, i, oCol);
	 			}
 	 			//this.oFReport.addColumn(i.GetColumnaTitulo(),i.GetCampo(),i.GetClase().getName(),30,false,"",detailStyle,i.GetCampo());
 	 		}
 	 	}
  }
  private void AddTotilizer(JTotalizer oTot, JColumnaLista i, AbstractColumn oCol) throws Exception {
  	if (oTot.getProp(i.GetCampo()) != null) {
	  	String oper = oTot.getOperation(i.GetCampo());
	  	Boolean hasRecognizedOper=false;
	  	ColumnsGroupVariableOperation colOper=ColumnsGroupVariableOperation.NOTHING;
	  	if (oper.equals(JTotalizer.OPER_SUM)) {
	  			colOper = ColumnsGroupVariableOperation.SUM;
	  			hasRecognizedOper = true;
	  	}
	  	if (oper.equals(JTotalizer.OPER_COUNT)) {
				colOper = ColumnsGroupVariableOperation.COUNT;
				hasRecognizedOper = true;
	  	}
	  	if (oper.equals(JTotalizer.OPER_MAX)) {
				colOper = ColumnsGroupVariableOperation.HIGHEST;
				hasRecognizedOper = true;
	  	}
	  	
	  	if (hasRecognizedOper) {
	  		//this.oFReport.addGlobalFooterVariable(oCol, colOper);
	  		//this.oFReport.addGlobalHeaderVariable(oCol,colOper ,this.headerVariables);
	  		this.oFReport.addGlobalFooterVariable(oCol,colOper ,this.headerVariables);
	  	} else {
	  		//this.oFReport.addGlobalFooterVariable(oCol,ColumnsGroupVariableOperation.SYSTEM ,this.headerVariables);
	  	}
  	}
  }
  
  private AbstractColumn BuildColumn(JColumnaLista i) throws Exception {
  	String sClassSimpleName = i.GetClase().getSimpleName();
  	String sClassName = i.GetClase().getName();
  	if (sClassSimpleName.equals("Boolean"))
  			sClassName = String.class.getName() ;
  	if (sClassSimpleName.equals("Date") || sClassSimpleName.toLowerCase().equals("jdatetime"))
			sClassName = String.class.getName() ;

  	int colWidth = 10;
  	String pattern="";
		AbstractColumn col = ColumnBuilder.getInstance().setColumnProperty(i.GetCampo(), sClassName,i.GetCampo()).build();
 			col.setTitle(i.GetColumnaTitulo());
 			if (sClassSimpleName.equals("Long") ) {
 				colWidth = 10;		
 				col.setStyle(this.numberStyle);
 				pattern="##0";
 			}
 			if (sClassSimpleName.equals("String") ) {
 				colWidth = 30;		
 			}
 			if (sClassSimpleName.equals("Double") ) {
 				colWidth = 18;		
 				col.setStyle(this.numberStyle);
 				pattern="#,##0.00"; 
 			}
 			if (sClassSimpleName.equals("Boolean") ) {
 				colWidth = 10;
 				col.setStyle(this.booleanStyle);
 			} 			
 			this.charCounter += colWidth;
 			col.setWidth(colWidth);
 			col.setHeaderStyle(headerStyle);
 			col.setPattern(pattern);
 			col.setBlankWhenNull(true);
 			return col;
  }
  

  private JWinList getWinList() throws Exception {
  	if (this.oWinList == null) {
  		this.oWinList = new JWinList(this.getWins());
  		this.getWins().ConfigurarColumnasListaInternal(this.oWinList);
  	}
  	return this.oWinList;
  }
    
  private JasperReport FastReport() throws Exception {
	 oDReport = this.buildReport();
	 return DynamicJasperHelper.generateJasperReport(oDReport, getLayoutManager(), this.getParametersMap());
  }
  
	private LayoutManager getLayoutManager() {
		ClassicLayoutManager a = new ClassicLayoutManager();
		//HashMap b = a.getReportStyles();
  	return a;
  }

	@SuppressWarnings("unchecked")
	public JRDataSource  getDataSourceFromWins() throws Exception {
		Document document;
		JRXmlDataSource ds = null ;
		Element pRoot = null;
		Element posInformation = null;
    pRoot = JXMLElementFactory.getInstance().createElement( "data" );
 	 	this.getWins().ReRead();
 	 	JIterator<JRecord> iter = this.getWins().getRecords().getStaticIterator();
 	 	while (iter.hasMoreElements()) {
 	 		JRecord i = iter.nextElement();
 	    posInformation = pRoot.getOwnerDocument().createElement("line");   
 	    pRoot.appendChild(posInformation);
 	 		JIterator<JColumnaLista> Listiter = this.getWinList().GetColumnasLista().getIterator();
 	 		while (Listiter.hasMoreElements()) {
 	 			JColumnaLista h = Listiter.nextElement(); 
 	 			if (h.getFixedProp() != null ) {
 	 				if  (i.getFixedProperties().containsKey(h.GetCampo()))  {
 	 					String FieldContent = i.getPropAsString(h.GetCampo()).replace(".", ",") ;
 	 					if (h.GetCampo().getClass().getSimpleName().equals("String")) {
 	 							if (i.getPropAsString(h.GetCampo()).equals("N"))
 	 								FieldContent = "No";
 	 							if (i.getPropAsString(h.GetCampo()).equals("S"))
 	 								FieldContent = "Si";
 	 					}
 	 					JXMLTools.addData(posInformation,h.GetCampo(), FieldContent, pRoot, JXMLTools.DATATYPE_ELEMENT);
 	 				}
 	 			}
 	 		}
 	 	} 
		//File oFile = new File(sPath + ".xml");
		//oFile.createNewFile();
		// llenar el xml.
       //GrabarArchivoXML(oFile, pRoot);
		
 	 	InputStream cString = parseStringToIS(pRoot.toString());
		
		document = JRXmlUtils.parse(cString);
		//document = JRXmlUtils.parse(JRLoader.getLocationInputStream(oFile.getPath()));
		ds = new JRXmlDataSource(document, "/data/line");

		return ds;
	}
  
 public InputStream parseStringToIS(String xml){
	 if(xml==null) return null;
	 xml = xml.trim();
	 java.io.InputStream in = null;
	 try{
	 in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
	 }catch(Exception ex){
	 }
	 return in;
	 } 
 
/* private synchronized void GrabarArchivoXML(File oFile, Element pRoot) throws Exception {
   FileOutputStream outFile = null;
   try {
     outFile = new FileOutputStream( oFile );
     JXMLSerializer writer = new JXMLSerializer();
     writer.serialize( new OutputStreamWriter( outFile ), pRoot );
     return;

   } catch( Exception e ) {
     JDebugPrint.logError( "ERROR al grabar el archivo: " + oFile.getName() +
                           ". [ " + e.getMessage() + " ]" );
     throw e;
   } finally {
     if( outFile != null ) outFile.close();
   }
 } */ 

 public Map<String, Object> getParametersMap() {
   if (this.oParameters==null) {
     this.oParameters = new HashMap<String, Object>();
   }
   return this.oParameters;
 }
	
 public void setParametersMap(Map<String, Object> zValue) {
    this.oParameters=zValue;
   }
 
 public void setParameter(String zParameterName, Object zParameterValue) {
   this.getParametersMap().put(zParameterName, zParameterValue);
 }		
	
}