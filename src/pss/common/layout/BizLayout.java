package pss.common.layout;

import java.util.Date;

import pss.JPath;
import pss.JPssVersion;
import pss.common.security.BizUsuario;
import pss.common.terminals.config.BizTerminal;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.drivers.HTMLFile.THTMLFile;
import pss.common.terminals.drivers.PDFFile.TPDFPrinter;
import pss.common.terminals.drivers.WinsGrid.TWinsCustom;
import pss.common.terminals.printGen.JPrintGen;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.JToolsDB;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class BizLayout extends JRecord {

	// ------------------------------------------------------------------------------
	// Object Variables
	// ------------------------------------------------------------------------------
	protected JFloat pLineSpacing=new JFloat();
	protected JString pPais=new JString();
	protected JString pCompany=new JString();
	protected JString pLayout=new JString();
	protected JString pIdent=new JString();
	protected JString pSubIdent=new JString();
	protected JString pCodigoId=new JString();
	protected JString pDescrip=new JString();
	protected JString pCamposSet=new JString();
	protected JInteger pAnchoMaximo=new JInteger();
	protected JInteger pMaxItems=new JInteger();
	protected JInteger pCopias=new JInteger();
	protected JInteger pCopiasXPag=new JInteger();
	protected JBoolean pPreconfigurado=new JBoolean();
	protected JBoolean pPrintEmptyLines=new JBoolean();
	protected JBoolean pToWins=new JBoolean();
	protected JString pSize=new JString();
	protected JString pCustomSize=new JString();
	protected JString pImageSize=new JString();
	protected JString pFontType=new JString();
	protected JInteger pFontSize=new JInteger();
	protected JString pBackground=new JString();
	protected JString pSectionOrden=new JString();
	protected JBoolean pRellenar=new JBoolean();
	protected JRecords<BizLayoutCampo> campos;
	protected JFieldInterface oInterface;
	// private String layoutSector;
	// private JTerminal termpPrinter;
//	private JMap<Integer, PrintedLine> vPrintedLines;
	private JList<String> linesRecorded;
//	private int iPrintedLinePointer;
//	private int iPrintCopyNumber=0;
//	private boolean bRecordPrintedLines;
//	private int sectorCount=0;
	private int pageNro=1;
	private int pageLines=0;
	private int headerLines=0;
	private int sectorLines=0;
	private int lastSectorLines=0;
	private int sectorStart=0;
	private int sectorMax=0;
	private int sectorStop=0;	
	protected String sectorCurrent=null;
	private String pageHeader=null;
	private String pageFooter=null;
	private Object pageSource=null;
	
	protected JString pDocId=new JString();
	protected JBoolean pGoogleDoc=new JBoolean();
	// ------------------------------------------------------------------------------
	// Class Constants
	// ------------------------------------------------------------------------------
	// layout hardcodeados
	public static final String FISCAL_HEADER="FISCAL";
	public static final String FISCAL_FANTASY="FISCAL_FANTASY";
	
	static final String CONTROL_NEW_LINE="NEW_LINE";
	static final String CONTROL_NEW_PAGE="NEW_PAGE";
	static final String CONTROL_BREAK_ON="CONTROL_BREAK_ON";
	static final String CONTROL_STOPSECTOR_ON="CONTROL_STOPSECTOR_ON";
	static final String CONTROL_SECTOR_LINES="CONTROL_SECTOR_LINES";
	static final String CONTROL_PAGE_LINES="CONTROL_PAGE_LINES";
	static final String CONTROL_HEADER_LINES="CONTROL_HEADER_LINES";
	static final String CONTROL_TAB="CONTROL_TAB";
	static final String CONTROL_CAMPO="CONTROL_CAMPO";
	
	static final String GENERAL_COPIA_LEYENDA="CopiaImpresaDesc";
	static final String GENERAL_COPIA_NRO="CopiaImpresaNro";
	static final String GENERAL_PAG_NRO="PAG_NRO";
	static final String GENERAL_FECHA_ACTUAL="FECHA_ACTUAL";
	static final String GENERAL_HORA_ACTUAL="HORA_ACTUAL";
	static final String GENERAL_OPERADOR_NOMBRE="OPERADOR_NOMBRE";
	static final String GENERAL_OPERADOR_ID="OPERADOR_ID";
	static final String GENERAL_SUCURSAL_RAZONSOCIAL="SUCURSAL_RAZONSOCIAL";
	static final String GENERAL_SUCURSAL_DIRECCION="DIRECCION";
	static final String GENERAL_SUCURSAL_CIUDADPCIA="CIUDADPCIA";
	static final String GENERAL_SUCURSAL_CPOSTAL="CODIGO_POSTAL";
	static final String GENERAL_SUCURSAL_TELEFONO="TELEFONO";
	static final String GENERAL_SUCURSAL_PAIS="PAIS";
	static final String GENERAL_SUCURSAL_DIVISION="DIVISION";
	static final String GENERAL_SUCURSAL_CIUDAD="CIUDAD";
	static final String GENERAL_SUCURSAL_LOCALIDAD="LOCALIDAD";
	static final String GENERAL_INICIO_ACTIVIDADES="INICIO_ACTIVIDADES";
	static final String GENERAL_LOGO="Logo";
	public static final String PARTICULAR_SIGNO="PARTICULAR_SIGNO";

	// ------------------------------------------------------------------------------
	// Getters & Setters
	// ------------------------------------------------------------------------------
	public String getCompany() throws Exception { return pCompany.getValue();	}
	
	public String getPais() throws Exception {
		return pPais.getValue();
	}

	public int getMaxItems() throws Exception {
		return pMaxItems.getValue();
	}

	public int getCopias() throws Exception {
		return pCopias.getValue();
	}

	public int getCopiasXPag() throws Exception {
		return pCopiasXPag.getValue();
	}

	public double getLineSpacing() throws Exception {
		return pLineSpacing.getValue();
	}

	public void setLineSpacing(int zValue) {
		pLineSpacing.setValue(zValue);
	}

	@Override
	public String GetTable() {
		return "RTL_LAYOUTS";
	}

	// public JTerminal getTerminalPrinter() throws Exception { return termpPrinter; }
	public String getLayoutDescription() throws Exception {
		return pDescrip.getValue();
	}

	public String getLayout() throws Exception {
		return pLayout.getValue();
	}

	public int getAnchoMaximo() throws Exception {
		return pAnchoMaximo.getValue();
	}

	public String getSectionOrden() throws Exception {
		return pSectionOrden.getValue();
	}

	public String getIdent() throws Exception {
		return pIdent.getValue();
	}

	// public void setTerminalPrinter(JTerminal value) { termpPrinter = value; }
	public void setFieldInterface(JFieldInterface zValue) {
		oInterface=zValue;
	}

	public void setPageSector(String header, Object source) {
		this.setPageSector(header, null, source);
	}
	public void setPageSector(String header, String footer, Object source) {
		pageHeader=header;
		pageFooter=footer;
		pageSource = source;
	}

	public String getCamposSet() throws Exception {
		return pCamposSet.getValue();
	}

	public String getBackground() throws Exception {
		return pBackground.getValue();
	}
	
	public String getSize() throws Exception {
		return pSize.getValue();
	}

	public void setSize(String value) throws Exception {
		pSize.setValue(value);
	}


	public String getCustomSize() throws Exception {
		return pCustomSize.getValue();
	}

	public String getImageSize() throws Exception {
		return pImageSize.getValue();
	}
	public String getFontType() throws Exception {
		return pFontType.getValue();
	}
	public int getFontSize() throws Exception {
		return pFontSize.getValue();
	}
	// Devuelve el numero de copia que se esta imprimiendo
//	public int getPrintCopyNumber() {
//		return iPrintCopyNumber;
//	}

//	private void setPrintCopyNumber(int zVal) {
//		iPrintCopyNumber=zVal;
//	}
	public void setCompany(String value) {
		pCompany.setValue(value);
	}
	public void setLayout(String value) {
		pLayout.setValue(value);
	}

	// ------------------------------------------------------------------------------
	// Class Constructors
	// ------------------------------------------------------------------------------
	public BizLayout() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("pais", pPais);
		addItem("layout", pLayout);
		addItem("descripcion", pDescrip);
		addItem("ident", pIdent);
		addItem("ancho_maximo", pAnchoMaximo);
		addItem("line_spacing", pLineSpacing);
		addItem("campos_set", pCamposSet);
		addItem("max_items", pMaxItems);
		addItem("copias", pCopias);
		addItem("copias_xpag", pCopiasXPag);
		addItem("preconfigurado", pPreconfigurado);
		addItem("print_empty_lines", pPrintEmptyLines);
		addItem("background", pBackground);
		addItem("page_size", pSize);
		addItem("custom_size", pCustomSize);
		addItem("image_size", pImageSize);
		addItem("font_type", pFontType);
		addItem("font_size", pFontSize);
		addItem("sub_ident", pSubIdent);
		addItem("rellenar", pRellenar);
		addItem("to_wins", pToWins);
		addItem("doc_id", pDocId);
		addItem("google_doc", pGoogleDoc);		
		addItem("section_orden", pSectionOrden);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "pais", "Pais", true, true, 3);
		addFixedItem(KEY, "layout", "Layout", true, true, 15);
		addFixedItem(FIELD, "descripcion", "Descripcion", true, true, 50);
		addFixedItem(FIELD, "ident", "Ident", true, false, 250);
		addFixedItem(FIELD, "ancho_maximo", "Ancho Maximo", true, true, 5, 2);
		addFixedItem(FIELD, "line_spacing", "Interlineado", true, false, 5, 2);
		addFixedItem(FIELD, "campos_set", "Tipo", true, true, 20);
		addFixedItem(FIELD, "max_items", "Items Maximo", true, true, 5);
		addFixedItem(FIELD, "copias", "Copias", true, true, 2);
		addFixedItem(FIELD, "copias_xpag", "Copias x Pag", true, false, 2);
		addFixedItem(FIELD, "preconfigurado", "Preconfigurado", true, false, 1, 0, "", "S");
		addFixedItem(FIELD, "print_empty_lines", "Imprimir lineas vacias", true, true, 1, 0, "", "S");
		addFixedItem(FIELD, "background", "Imagen de fondo", true, false, 150, 0);
		addFixedItem(FIELD, "page_size", "Size", true, false, 30);
		addFixedItem(FIELD, "custom_size", "Custom Size", true, false, 20);
		addFixedItem(FIELD, "image_size", "Image Size", true, false, 20);
		addFixedItem(FIELD, "font_type", "Font type", true, false, 20);
		addFixedItem(FIELD, "font_size", "Font Size", true, false, 20);
		addFixedItem(FIELD, "sub_ident", "Sub Ident", true, false, 200);
		addFixedItem(FIELD, "rellenar", "Rellenar", true, false, 1);
		addFixedItem(FIELD, "to_wins", "To Wins", true, false, 1);
		addFixedItem(FIELD, "doc_id", "Código de Documento", true, false, 500);
		addFixedItem(FIELD, "google_doc", "Es google Doc",true, false, 1, 0, "", "S");		
		addFixedItem(FIELD, "section_orden", "Section Orden", true, false, 20);
	}

	public boolean Read(String company, String zPais, String zLayout) throws Exception {
		addFilter("company", company);
		addFilter("pais", zPais);
		addFilter("layout", zLayout);
		return read();
	}

	public boolean ReadByIdent(String company, String zPais, String type, String ident, String subident) throws Exception {
		addFilter("company", company);
		addFilter("pais", zPais);
		addFilter("campos_set", type);
		addFilter("ident", ident);
		addFilter("sub_ident", subident);
		return read();
	}

	public boolean ReadByType(String company, String zPais, String type) throws Exception {
		addFilter("company", company);
		addFilter("pais", zPais);
		addFilter("campos_set", type);
		addFilter("ident", "");
		return read();
	}

	// public void print() throws Exception {
	// JPrintLines printLines = new JPrintLines();
	// printLines.setMode(this.sModalidad);
	// printLines.setFont(this.sFuente);
	// printLines.setLines(linesRecorded);
	// this.termpPrinter.process(printLines);
	// }
	private String getHTMLStyle() throws Exception {
		String style="background-image:url('/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/pss/images/image001.gif');"+"background-repeat:no-repeat;";
		return JTools.encodeString(style);
	}

	public String getLinesRecordedAsHTML() throws Exception {
		StringBuffer html=new StringBuffer();
		html.append("<html>");
		html.append("<head>");
		html.append("<title>"+JPssVersion.getPssTitle()+" - Impresion </title>");
		html.append("</head>");
		html.append("<body>");
		// pFile.altaln("<body onload='window.print();window.close();'>");
		// html.append("<body onload='window.print();'>");
		html.append("<div style=\""+this.getHTMLStyle()+"\">");
		JIterator<String> iter=this.linesRecorded.getIterator();
		while (iter.hasMoreElements()) {
			String line=iter.nextElement();
			html.append(JTools.encodeString(line)+"<br></br>");
		}
		html.append("</div></body></html>");
		return html.toString();
	}

	public String getLinesRecordedAsXML() throws Exception {
		if (this.linesRecorded==null) return "<xml></xml>";
		StringBuffer xml=new StringBuffer();
		xml.append("<xml>");
		JIterator<String> iter=this.linesRecorded.getIterator();
		while (iter.hasMoreElements()) {
			String line=iter.nextElement();
			xml.append("<section id=\"LINE\" line=\""+JTools.encodeString(line)+"\" />");
		}
		xml.append("</xml>");
		return xml.toString();
	}
	public String getLinesRecordedString() throws Exception {
		StringBuffer s=new StringBuffer();
		if (this.linesRecorded==null) return "";
		JIterator<String> iter=this.linesRecorded.getIterator();
		while (iter.hasMoreElements()) {
			String line=iter.nextElement();
			s.append(line+"\r\n");
		}
		return s.toString();
	}
	public JList<String> getLinesRecorded() throws Exception {
		if (linesRecorded==null) linesRecorded=JCollectionFactory.createList();
		return this.linesRecorded;
	}

	public void startRecord() throws Exception {
		this.linesRecorded=null;
		this.pageLines=0;
		this.headerLines=0;
		this.sectorLines=0;
	}

	public void cleanRecords() throws Exception {
		if (this.linesRecorded==null) return;
		this.linesRecorded.removeAllElements();
	}

	private void recordLine(String line) throws Exception {
//		JTools.checkXMLLine(line);
		this.getLinesRecorded().addElement(this.isRellenar()? line : JTools.rTrim(line));
	}
	
//	public void skeepLines(int lines) throws Exception {
//		for(int i=0; i<lines; i++) {
//			this.recordLine("^n");
//		}
//	}
	public void newLine() throws Exception {
		this.recordLine("^n");
	}
	public void newPage() throws Exception {
		this.recordLine("^p");
		this.pageNro++;
		this.headerLines=0;
	}

	public void newHeader() throws Exception {
		this.recordLine("^h");
		this.headerLines=0;
	}

	// ----------------------------------------------------------------------------------------------------------------------
	public void generate(String layoutSector, Object source) throws Exception {
		this.printZone(layoutSector, source);
	}

	// ----------------------------------------------------------------------------------------------------------------------
	private void printZone(String layoutSector, Object zSource) throws Exception {
		if (layoutSector==null) return;
		if (!layoutSector.equals(sectorCurrent)) {
			this.lastSectorLines=this.sectorLines;
			this.sectorStart=this.pageLines;
			this.sectorLines=0;
			this.sectorMax=0;
			this.sectorStop=0;			
		} 
		this.sectorCurrent = layoutSector;
//		this.checkPage();
		JIterator<String> oIt=this.getPrintZone(layoutSector, zSource).getIterator();
		while (oIt.hasMoreElements()) {
			this.checkPage();
			this.checkSector();
			if (this.sectorStop!=0 && this.sectorLines>=this.sectorStop) break;
			
			this.sectorLines+=1;
			this.pageLines+=1;
			this.headerLines+=1;
			String line = oIt.nextElement();
			this.recordLine(line);
		}
	}
	
	
//	private void skeepSectorLines() throws Exception {
//		for (int i=sectorLines; i<sectorMax;i++) { 
//			this.recordLine("^n");
//		}
//	}
	
	private void checkSector() throws Exception {
		if (sectorMax==0) return;
		if(sectorLines<sectorMax) return;
		this.changePage();
	}
	
	public void addCampo(BizLayoutCampo campo) throws Exception {
		if (this.campos==null) {
			this.campos = new JRecords<BizLayoutCampo>(BizLayoutCampo.class);
			this.campos.setStatic(true);
		}
		this.campos.addItem(campo);
	}
	
	public JRecords<BizLayoutCampo> getObjCampos() throws Exception {
		if (this.campos!=null) return this.campos;
		JRecords<BizLayoutCampo> records=new JRecords<BizLayoutCampo>(BizLayoutCampo.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("pais", pPais.getValue());
		records.addFilter("layout", pLayout.getValue());
		records.addOrderBy("sector");
		records.addOrderBy("y");
		records.addOrderBy("x");
		records.readAll();
		records.toStatic();
		return (this.campos=records);
	}
	
	

	// ----------------------------------------------------------------------------------------------------------------------
	public JList<String> getPrintZone(String layoutSector, Object zSource) throws Exception {
		JList<String> lineasList=JCollectionFactory.createList();
		int last_Y=0;
		int anchoLine= this.getAnchoMaximo();
		StringBuffer linea=new StringBuffer();//, sLineaVacia;

		JIterator<BizLayoutCampo> iter = this.getObjCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizLayoutCampo campo=iter.nextElement();
		
			if (!campo.getSector().equals(layoutSector)) 
				continue;
			
			try {
				if (last_Y!=0&&last_Y!=campo.pY.getValue()) {
					this.addPrintLine(lineasList, this.fillLinea(linea), anchoLine);
					linea = new StringBuffer();
				}
				if (!this.okCondicion(campo, zSource)) {
					last_Y=campo.pY.getValue();
					continue;
				}
				if (campo.ifControl()) {
					if (this.controlLines(lineasList, campo)) {
						last_Y=0;
						continue;
					}
				}
				Object reply=formatField(campo, zSource);
				this.addText(reply, linea, campo);
				if (this.mustWrap(campo, linea)) {
					anchoLine=campo.getWrap();
					last_Y=campo.pY.getValue();
					break;
				}

			} catch (Exception e) {
				continue;
			}
			// else sLinea = sReply; // esto creo que esta mal, lo saco y veo quien se queja
			last_Y=campo.pY.getValue();
		}
		if (last_Y!=0) {
			this.addPrintLine(lineasList, this.fillLinea(linea), anchoLine);
		}
		return lineasList;
	}
	
	private boolean mustWrap(BizLayoutCampo campo, StringBuffer linea) throws Exception {
		if (campo.getWrap()==0) return false;
		return linea.length()>campo.getWrap();
	}
	
	private String fillLinea(StringBuffer l) throws Exception {
		if (!this.isRellenar())
			return l.toString();
		return JTools.RPad(l.toString(), this.getAnchoMaximo(), " ", true);
	}

	private void addText(Object reply, StringBuffer linea, BizLayoutCampo campo) throws Exception {
		if (this.isToWins()) return;
		if (reply.equals("")) return;
		if (!(reply instanceof String)) return;
		
		String sReply= (String)reply;
//		if (sReply.indexOf("\n")!=-1) {
//			String sLines[]=sReply.split("\n");
//			for(int i=0; i<sLines.length; i++) {
//				this.addText(sLines[i].trim(), linea, lineasList, campo);
//				this.addPrintLine(lineasList, linea.toString());
//				linea = new StringBuffer();
//			}
//			return;
//		}

//		int styleCount = sReply.length()-this.removeStyle(sReply).length();
//		sLinea+=JTools.RPad("", styleCount, " ");

		String sLineaLimpia=this.removeStyle(linea.toString());
		int totalStyleCount = linea.length()-sLineaLimpia.length();
		int start = campo.isAlFinal()?JTools.rTrim(sLineaLimpia).length()+1:campo.pX.getValue();
		if (campo.hasPosition()) start=0; // con posicion no me importa la posiciones en el line
		start+=totalStyleCount;
		this.appendString(linea, JTools.rTrim(sReply), start);
	}
	
	
//	String sLineaLimpia=this.removeStyle(sLinea);
//	String sReplyLimpia=this.removeStyle(sReply);
//	int styleCount = sReply.length()-sReplyLimpia.length();
//	sLinea+=JTools.RPad("", styleCount, " ");
//	int totalStyleCount = sLinea.length()-sLineaLimpia.length();
//	int start = campo.isAlFinal()?sLineaLimpia.trim().length():campo.pX.getValue();
//	start+=totalStyleCount;
//	return this.replaceTruncating(sLinea, sReply, this.getAnchoMaximo()+totalStyleCount+styleCount, start);

	
	private String removeStyle(String line) throws Exception {
		JStringTokenizer tks = JCollectionFactory.createStringTokenizer(line, '~');
		if (tks.countTokens()==0 || tks.countTokens()==1)
			return line;
		String newline="";
		boolean first = line.charAt(0)!='~';
		while (tks.hasMoreTokens()) {
			if (!first) tks.nextToken();
			newline+=tks.nextToken();
			first=false;
		}
		return newline;
	}
	
	public void appendString(StringBuffer originalString, String newString, int whereToInsert) {
	//	int originalLen=originalString.length();
		for (int i=originalString.length();i<whereToInsert;i++) {
			originalString.append(' ');
		}
//		int newLen=newString.length();
	//	if (whereToInsert==0) whereToInsert=1;
//		char[] cArray=newString.toCharArray();
//		StringBuffer aux=new StringBuffer(originalString);
		int len = newString.length();
		for(int i=0; i<len; i++) {
//			if ((i+whereToInsert)>=max) break;
			originalString.append(newString.charAt(i));
		}
//		return aux.toString();
	}

	
	// ----------------------------------------------------------------------------------------------------------------------
//	private void addTextWrap(JList<String> oLineas, String reply, BizLayoutCampo campo) throws Exception {
//		int max=campo.findLongMax();
//		int xPos=campo.pX.getValue();
//		String aux="";
//		if (xPos>0) for(int j=0; j<xPos; j++)
//			aux+=" ";
//		String texto=aux+reply;
//		int fromTo=texto.length()>=max ? max : texto.length();
//		this.addPrintLine(oLineas, texto.substring(0, fromTo));
//		if (texto.substring(fromTo).length()<1) return;
//		String nextLine=aux+texto.substring(fromTo);
//		for (int i=0;i>3;i++) {
//			int to=max>nextLine.length() ? nextLine.length() : max;
//			this.addPrintLine(oLineas, JTools.RPad(nextLine.substring(0, to), max, " "));
//			fromTo=to;
//			nextLine=nextLine.substring(fromTo).trim();
//			if (nextLine.length()<1) return;
//			nextLine=aux+nextLine;
//		}
//	}

	// ----------------------------------------------------------------------------------------------------------------------
	private void addPrintLine(JList<String> lineasList, String line, int anchoMax) throws Exception {
		if (line.trim().length()<=0) {
			if (this.isPrintEmptyLines())
				lineasList.addElement("^n");
			return;
		} 
		if (line.indexOf('\n')!=-1 && line.indexOf("pos=")==-1) { // PARCHE si tiene posiscion fija no hace multilinea
			JStringTokenizer tk = JCollectionFactory.createStringTokenizer(line, '\n');
			tk.skipEmptyTokens(false);
			if (tk.countTokens()>0) {
				int sangria = line.length()-JTools.lTrim(line).length();
				while (tk.hasMoreTokens()) {
					this.addPrintLine(lineasList, JTools.RPad("", sangria, " ")+JTools.lTrim(tk.nextToken()), anchoMax);
					if (this.getLineSpacing()==1) lineasList.addElement("^n");
				}
				return;
			}
		}

		String linew = this.removeStyle(line);
//		int anchoMax=this.getAnchoMaximo();
//		if (anchoLine!=0)
//			anchoMax=campo.getWrap();
		if (linew.length()>anchoMax && line.indexOf("pos=")==-1) { // PARCHE si tiene posiscion fija no hace multilinea
			int sangria = linew.length()-JTools.lTrim(linew).length();
			int lastSpace = linew.substring(0, anchoMax).lastIndexOf(' ');
			if (lastSpace<sangria) lastSpace=anchoMax;
			if (lastSpace==0) lastSpace=anchoMax;
			if (lastSpace>anchoMax) lastSpace=anchoMax;
			lineasList.addElement(JTools.justificar(linew.substring(0, lastSpace), anchoMax));
			if (sangria>anchoMax) sangria=0;
			if (this.getLineSpacing()==1) lineasList.addElement("^n");
			String texto = JTools.RPad("", sangria, " ")+linew.substring(lastSpace+1);
			this.addPrintLine(lineasList, texto, anchoMax);
			return;
		}
		lineasList.addElement(line);
	}

	
	private String findControl(BizLayoutCampo campo) throws Exception {
		StringBuffer c= new StringBuffer();
		if (campo.pCampo.getValue().equals(CONTROL_TAB)) {
			if (campo.pParametro.isNull()) {
				c.append("^t");
			} else {
				int cant = Integer.parseInt(campo.pParametro.getValue());
				for (int i=0; i<cant;i++) c.append("^t"); 
			}
		}
		return c.toString();
	}

	// ----------------------------------------------------------------------------------------------------------------------
	protected Object formatField(BizLayoutCampo oLayoutCampo, Object zSource) throws Exception {
		if (oInterface==null) {
			JExcepcion.SendError("No se ha seteado la interface");
		}
		Object oResult;
		if (oLayoutCampo.ifControl()) {
			return this.findControl(oLayoutCampo);
		}
		if (oLayoutCampo.ifConstante()) 
			oResult=oLayoutCampo.pConstante.getValue();
		else if (oLayoutCampo.ifDatoGeneral()) 
			oResult=this.getGeneralField(oLayoutCampo.pCampo.getValue());
		else 
			oResult=this.getDatoParticular(oLayoutCampo, zSource);
		
		if (oResult==null) oResult=JTools.justifyStrings("", "", oLayoutCampo.pLongMax.getValue(), ' ');
		if (oResult instanceof JCurrency && !oLayoutCampo.ifMonedaConSimbolo()) 
			oResult=new Double(((JCurrency) oResult).getValue());
		if (oLayoutCampo.ifNumericoConSigno() && oResult instanceof Number) 
			oResult=new Double(((Number) oResult).doubleValue()*getSigno(zSource));

		String sResult="";
		
		if (!(oResult instanceof String)&&oLayoutCampo.ifMonedaPais()) {
			sResult=JRegionalFormatterFactory.getAbsoluteBusinessFormatter().formatCurrencyRedondeoSimetrico(((Double) oResult).doubleValue());
		} else if (!(oResult instanceof String)&&oLayoutCampo.ifNumericoPais()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().formatNumber(((Double) oResult).doubleValue());
//		} else if ((oResult instanceof String)&&oLayoutCampo.isImage()) {
//			sResult="~ img="+oResult.toString();
		} else if (oResult instanceof Date&&oLayoutCampo.ifFechaCorta()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().formatShortDate(((Date) oResult));
		} else if (oResult instanceof Date&&oLayoutCampo.ifFecha()) {
			sResult=JDateTools.DateToString((Date) oResult, oLayoutCampo.pFormato.getValue());
		} else if (oResult instanceof Date&&oLayoutCampo.ifFechaLarga()) {
			sResult=JDateTools.formatFechaLarga((Date) oResult);
		} else if ((oResult instanceof Long)&&oLayoutCampo.ifNumerico()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().getCustomNumberFormat(oLayoutCampo.pFormato.getValue()).format(((Long) oResult).longValue());
		} else if ((oResult instanceof Double)&&oLayoutCampo.ifNumerico()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().getCustomNumberFormat(oLayoutCampo.pFormato.getValue()).format(((Double) oResult).doubleValue());
		} else if (!(oResult instanceof String)&&oLayoutCampo.ifNumerico()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().getCustomNumberFormat(oLayoutCampo.pFormato.getValue()).format(((Double) oResult).doubleValue());
		} else if ((oResult instanceof String)&&oLayoutCampo.ifNumerico()&&!((String) oResult).equals("")) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().getCustomNumberFormat(oLayoutCampo.pFormato.getValue()).format(Double.parseDouble((String) oResult));
		} else if (oResult instanceof Date&&oLayoutCampo.ifHoraCorta()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().formatShortTime(((Date) oResult));
		} else if (oResult instanceof Date&&oLayoutCampo.ifHoraLarga()) {
			sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().formatLongTime(((Date) oResult));
		} else if (oResult instanceof String&&oLayoutCampo.ifHoraCustom()) {
			sResult=JDate.TimeToString(JDateTools.DateTimeToDate(BizUsuario.getUsr().todayGMT(), (String) oResult, "HHmmss"), oLayoutCampo.pFormato.getValue());
		} else if (oResult instanceof JCurrency&&oLayoutCampo.ifMonedaConSimboloAdelante()) {
			JCurrency c = (JCurrency)oResult;
			c.setSimbolo(true);
			sResult=c.toFormattedString();
		} else if (oResult instanceof JCurrency&&oLayoutCampo.ifMonedaConSimboloAtras()) {
			JCurrency c = (JCurrency)oResult;
			c.setSimbolo(true);
			c.setSimboloAtras(true);
			sResult=c.toFormattedString();
		} else if (oLayoutCampo.ifTextoEnmascarado()) {
			sResult=enmascarar(oLayoutCampo.pFormato.getValue(), oResult.toString());
		}
		// emilio mexico else if( oResult instanceof Double && oLayoutCampo.ifMonedaPalabra() ) { sResult = JTools.convertCurrencyToWords(((Double)oResult).doubleValue(),"peso","")+" M.N."; }
		else if (oResult instanceof Double&&oLayoutCampo.ifMonedaPalabra()) {
			sResult=JToolsDB.convertNumberToWords((Double)oResult);
			if (!oLayoutCampo.pFormato.isEmpty())
				sResult+=" "+this.getDatoParticularCondicion(oLayoutCampo, zSource);
		} else if (oResult instanceof JList) {
			return oResult;
		} else {
			sResult=oResult.toString();
		}
		sResult = oLayoutCampo.alinear(sResult);
		sResult = oLayoutCampo.assignStyle(sResult);
		return sResult;
	}
	

	// ----------------------------------------------------------------------------------------------------------------------
	// private String formatValues(String zValue, int zLargo) throws Exception {
	// int i = zValue.lastIndexOf('.');
	// if( i == -1 ) return zValue;
	// String parteDecimal = JTools.RPad(String.valueOf(zValue.substring(i+1, zValue.length())), zLargo, "0");
	// return (zValue.substring(0, i) + "." + parteDecimal);
	// }
	// ----------------------------------------------------------------------------------------------------------------------
	private boolean controlLines(JList<String> oLines, BizLayoutCampo zLayoutCampo) throws Exception {
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_NEW_LINE)) {
			if (zLayoutCampo.pParametro.isNull()) {
				oLines.addElement("^n");
				return true;
			} else {
				if (zLayoutCampo.pParametro.getValue().startsWith("f")) {
					oLines.addElement("^n-"+zLayoutCampo.pParametro.getValue().substring(1));
				} else {
					int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
					for (int i=0; i<cant;i++) oLines.addElement("^n");
				}
			}
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_NEW_PAGE)) {
			oLines.addElement("^p");
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_BREAK_ON)) {
			int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
			sectorMax=cant;
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_STOPSECTOR_ON)) {
			int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
			sectorStop=cant;
			return true;
		}

		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_SECTOR_LINES)) {
			int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
			for (int i=lastSectorLines; i<cant;i++) { 
				oLines.addElement("^n");
			}
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_PAGE_LINES)) {
			int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
			for (int i=pageLines; i<cant;i++) { 
				oLines.addElement("^n");
			}
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_HEADER_LINES)) {
			int cant = Integer.parseInt(zLayoutCampo.pParametro.getValue());
			for (int i=headerLines; i<cant;i++) { 
				oLines.addElement("^n");
			}
			return true;
		}
		if (zLayoutCampo.pCampo.getValue().equals(CONTROL_CAMPO)) {
			oLines.addElement("^f");
		}

		return false;
	}
	

	// ----------------------------------------------------------------------------------------------------------------------
	@Override
	public void processDelete() throws Exception {
		if (!isViolateIntegrity()) {
			if (JRecords.existsComplete("pss.erp.documentType.LayoutConfig.BizLayoutDetalle", "company", pCompany.getValue(), "pais", pPais.getValue(), "layout", pLayout.getValue())) JExcepcion.SendError("Este layout está usado por Platino, no se podrá eliminar");
		}
		this.getCampos().processDeleteAll();
		super.processDelete();
	}

	// ----------------------------------------------------------------------------------------------------------------------
	public JRecords<BizLayoutCampo> getCampos() throws Exception {
		JRecords<BizLayoutCampo> records=new JRecords<BizLayoutCampo>(BizLayoutCampo.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("pais", pPais.getValue());
		records.addFilter("layout", pLayout.getValue());
		records.readAll();
		return records;
	}

	// ------------------------------------------------------------------------------
	// Administrador de la impresión de copias
	// ------------------------------------------------------------------------------
//	public void inicializePrintCopies() {
//		vPrintedLines=JCollectionFactory.createMap();
//		linesRecorded=JCollectionFactory.createList();
//		iPrintedLinePointer=0;
//		bRecordPrintedLines=true;
//		setPrintCopyNumber(0);
//	}

//		setPrintCopyNumber(zPrintCopyNumber);
//		bRecordPrintedLines=false;
//		for(int i=1; i<=iPrintedLinePointer; i++) {
//			PrintedLine oPrintedLine=vPrintedLines.getElement(new Integer(i));
//			if ("REPORT_FOOTER".equals(oPrintedLine.sSeccion)) {
//				for(int j=0; j<linesToSkip; j++) {
//				}
//			}
//			printZone(oPrintedLine.sSeccion, oPrintedLine.oSource);
//		}
//	}

//	private class PrintedLine {
//
//		Object oSource;
//		String sSeccion;
//
//		PrintedLine(String sSeccion, Object oSource) {
//			this.oSource=oSource;
//			this.sSeccion=sSeccion;
//		}
//	}

	// ------------------------------------------------------------------------------
	// Campos Generales
	// ------------------------------------------------------------------------------
	public static JRecords<BizVirtual> getGeneralFields() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(GENERAL_FECHA_ACTUAL, "Fecha Actual", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_HORA_ACTUAL, "Hora Actual", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_COPIA_LEYENDA, "Leyenda de Copia Impresa", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_COPIA_NRO, "Nro. Copia Impresa", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_PAG_NRO, "Nro.Pagina", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_OPERADOR_NOMBRE, "Operador Nombre", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_OPERADOR_ID, "Operador ID", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_RAZONSOCIAL, "Sucursal Razón Social", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_DIRECCION, "Sucursal - Dirección", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_CIUDADPCIA, "Sucursal - Ciudad, Pcia ", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_CPOSTAL, "Sucursal - Código Postal ", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_TELEFONO, "Teléfono Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_PAIS, "País Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_DIVISION, "División Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_CIUDAD, "Ciudad Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_SUCURSAL_LOCALIDAD, "Localidad Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_INICIO_ACTIVIDADES, "Inicio Actividades", 1));
		oBDs.addItem(JRecord.virtualBD(GENERAL_LOGO, "Logo", 1));
		return oBDs;
	}

	private Object getGeneralField(String zField) throws Exception {
//		if (zField.equals(GENERAL_COPIA_LEYENDA)) return getNroCopiaImpresa(getPrintCopyNumber());
//		if (zField.equals(GENERAL_COPIA_NRO)) return String.valueOf(getPrintCopyNumber()).trim();
		if (zField.equals(GENERAL_PAG_NRO)) return this.pageNro;
		if (zField.equals(GENERAL_FECHA_ACTUAL)) return BizUsuario.getUsr().todayGMT();
		if (zField.equals(GENERAL_HORA_ACTUAL)) return JDateTools.StringToDate(JDateTools.DateToString(BizUsuario.getUsr().todayGMT(), "yyyy-MM-dd")+JDateTools.CurrentTime("HH:mm:ss"), "yyyy-MM-ddHH:mm:ss");
		if (zField.equals(GENERAL_OPERADOR_NOMBRE)) return BizUsuario.getCurrentUserName();
		if (zField.equals(GENERAL_OPERADOR_ID)) return BizUsuario.getUsr().GetUsuario();
		if (zField.equals(GENERAL_SUCURSAL_RAZONSOCIAL)) return BizUsuario.getUsr().getObjNodo().getObjPerson().GetApellido();
		if (zField.equals(GENERAL_SUCURSAL_CPOSTAL)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCodPostal();
		if (zField.equals(GENERAL_SUCURSAL_DIRECCION)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDomicilioCompleto();
		if (zField.equals(GENERAL_SUCURSAL_CIUDADPCIA)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCiudad().trim()+", "+BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDescrProv();
		if (zField.equals(GENERAL_SUCURSAL_TELEFONO)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getTelComercial();
		if (zField.equals(GENERAL_SUCURSAL_PAIS)) return BizUsuario.getUsr().getObjNodo().ObtenerPais().getDescrPaisLista();
		if (zField.equals(GENERAL_SUCURSAL_DIVISION)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDescrProv();
		if (zField.equals(GENERAL_SUCURSAL_CIUDAD)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCiudad();
		if (zField.equals(GENERAL_SUCURSAL_LOCALIDAD)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getLocalidad();
		if (zField.equals(GENERAL_INICIO_ACTIVIDADES)) return BizUsuario.getUsr().getObjNodo().getObjPerson().getFechaNacimiento();
		if (zField.equals(GENERAL_LOGO)) return this.findLogo();
		return "";
	}
	
	public String findLogo() throws Exception {
		return JPath.PssPathLogos()+"/"+BizUsuario.getUsr().getObjCompany().getLogo();
	}


	private String getNroCopiaImpresa(int xCopia) throws Exception {
		String sCopia="";
		switch (xCopia) {
		case 0:
			sCopia="";
			break;
		case 1:
			sCopia="Duplicado";
			break;
		case 2:
			sCopia="Triplicado";
			break;
		case 3:
			sCopia="Cuadriplicado";
			break;
		case 4:
			sCopia="Quintuplicado";
			break;
		}
		return sCopia;
	}

	private static String enmascarar(String sformat, String sResult) {
		if (sformat.length()>0&&(sformat.endsWith("X")||sformat.startsWith("X"))&&sformat.indexOf('*')>=0) {
			int hideBegin=0;
			int hideEnd=sformat.length()-1;
			for(; hideBegin<sformat.length()&&sformat.charAt(hideBegin)=='X'; hideBegin++)
				;
			for(; hideEnd>=0&&sformat.charAt(hideEnd)=='X'; hideEnd--)
				;
			hideEnd=sformat.length()-hideEnd-1;
			int len=sResult.length();
			hideEnd=len>hideEnd ? len-hideEnd : 0;
			sResult=JTools.RPad(JTools.LPad(sResult.substring(hideBegin), len, "X").substring(0, hideEnd), len, "X");
		}
		int nFilter=sformat.indexOf("F");
		if (nFilter>=0) {
			int nFilterLen;
			for(nFilterLen=0; nFilter+nFilterLen<sformat.length()&&sformat.charAt(nFilter+nFilterLen)=='F'; nFilterLen++)
				;
			nFilterLen=nFilterLen>sResult.length() ? sResult.length() : nFilterLen;
			sResult=sResult.substring(0, nFilterLen);
		}
		nFilter=sformat.indexOf("L");
		if (nFilter>=0) {
			int nFilterLen;
			for(nFilterLen=0; nFilter+nFilterLen<sformat.length()&&sformat.charAt(nFilter+nFilterLen)=='L'; nFilterLen++)
				;
			nFilterLen=nFilterLen>sResult.length() ? sResult.length() : nFilterLen;
			sResult=sResult.substring(sResult.length()-nFilterLen);
		}
		return sResult;
	}

	public boolean okCondicion(BizLayoutCampo zLayoutCampo, Object zSource) throws Exception {
		if (!zLayoutCampo.isCondicionado()) return true;
		JFieldReq oReq=new JFieldReq(oInterface, zLayoutCampo.pSector.getValue(), zSource);
		Object value=oReq.get(zLayoutCampo.pCampoCondicion.getValue(), zLayoutCampo.pParamCondicion.isNull() ? null : zLayoutCampo.pParamCondicion.getValue());
		if (value==null) value=getGeneralField(zLayoutCampo.pCampoCondicion.getValue());
		if (value instanceof JList) {
			JExcepcion.SendError("Los campos de longitud variable no pueden ser una condicion ("+zLayoutCampo.getDescrCampoCondicion()+")");
			return false;
		}
		return JTools.compareCond(value, zLayoutCampo.pOperCondicion.getValue(), zLayoutCampo.pCondicion.getValue());
	}

	public static JRecords<BizVirtual> getOperadores() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("=", "igual", 1));
		oBDs.addItem(JRecord.virtualBD("<>", "distinto", 1));
		oBDs.addItem(JRecord.virtualBD(">", "mayor", 1));
		oBDs.addItem(JRecord.virtualBD("<", "menor", 1));
		oBDs.addItem(JRecord.virtualBD(">=", "mayor o igual", 1));
		oBDs.addItem(JRecord.virtualBD("<=", "menor o igual", 1));
		oBDs.addItem(JRecord.virtualBD("like", "Contenga", 1));
		oBDs.addItem(JRecord.virtualBD("NOT_NULL", "No Nulo", 1));
		return oBDs;
	}

	private Object getDatoParticular(BizLayoutCampo zLayoutCampo, Object zSource) throws Exception {
		JFieldReq oReq=new JFieldReq(oInterface, zLayoutCampo.pSector.getValue(), zSource);
		oReq.setId(zLayoutCampo.pCampo.getValue());
		oReq.setParam1(zLayoutCampo.pParametro.getValue());
		if (!zLayoutCampo.pParametro2.isEmpty())
			oReq.setParam2(zLayoutCampo.pParametro2.getValue());
		try {
			return oInterface.getField(oReq);
		} catch (Exception e) {
			PssLogger.logError(e);
			return JTools.justifyStrings("ERR-"+oReq.id, "", zLayoutCampo.pLongMax.getValue(), ' ');
		}
	}

	private Object getDatoParticularCondicion(BizLayoutCampo zLayoutCampo, Object zSource) throws Exception {
		JFieldReq oReq=new JFieldReq(oInterface, zLayoutCampo.pSector.getValue(), zSource);
		oReq.setId(zLayoutCampo.pFormato.getValue());
		try {
			return oInterface.getField(oReq);
		} catch (Exception e) {
			PssLogger.logError(e);
			return JTools.justifyStrings("ERR-"+oReq.id, "", zLayoutCampo.pLongMax.getValue(), ' ');
		}
	}
	private Object getDatoParticularFijo(String zCampoParticular, Object zSource) throws Exception {
		JFieldReq oReq=new JFieldReq(oInterface, this.sectorCurrent, zSource);
		oReq.id=zCampoParticular;
		return oInterface.getField(oReq);
	}

	@Override
	public void validateConstraints() throws Exception {
		if (pCopias.isNull()) pCopias.setValue(0);
	}

	public boolean isPrintEmptyLines() throws Exception {
		return pPrintEmptyLines.getValue();
	}

	public boolean isRellenar() throws Exception {
		return pRellenar.getValue();
	}

	public boolean hasLayoutSector(String zLayoutSector) throws Exception {
		JRecords<BizLayoutCampo> oZona=new JRecords<BizLayoutCampo>(BizLayoutCampo.class);
		oZona.addFilter("company", pCompany.getValue());
		oZona.addFilter("pais", pPais.getValue());
		oZona.addFilter("layout", pLayout.getValue());
		oZona.addFilter("sector", zLayoutSector);
		return oZona.exists();
	}

	public double getSigno(Object zSource) throws Exception {
		return ((Double) getDatoParticularFijo(PARTICULAR_SIGNO, zSource)).doubleValue();
	}

	public String getBackgroundFile() throws Exception {
		return "/"+pCompany.getValue()+"/"+pBackground.getValue();
	}

	private void checkPage() throws Exception {
		if (this.pMaxItems.getValue()==0) return;
		if (pageLines<=this.pMaxItems.getValue()) return;
		this.changePage();
	}
	
	private void changePage() throws Exception {
		int secStart = sectorStart;
		int secMax = sectorMax;
		String secCurrent = sectorCurrent;
		pageLines=0; // deberia calcular el tamaño del footer y restarlo a la cantidad de lineas
		if (this.pageFooter!=null) this.generate(this.pageFooter, pageSource);
		pageLines=0;
		this.newPage();
		this.generate(this.pageHeader, this.pageSource);
		// salto hasta comienzo del sector corriente, solo cuando hay un breakon
//		if (secStart==0) return;
		if (secMax==0) return;
		for (int i=pageLines; i<secStart;i++) { 
			this.newLine();
		}
		this.sectorStart=secStart;
		this.sectorCurrent=secCurrent;
		this.sectorLines=0;
		this.headerLines=0;
		this.pageLines=this.sectorStart;
	}

	public JPrintGen createPrintGen(String name, String fiscalDoc) throws Exception {
		BizTerminal termPrinter=this.createPDFTerminal(name);
		JPrintGen printGen=new JPrintGen();
		printGen.setDocType(fiscalDoc);
		printGen.takeAttributesFrom(this);
		printGen.setTerminal(termPrinter);
		return printGen;
	}

	public JPrintGen createPrintGenHTML(String name) throws Exception {
		BizTerminal termPrinter=this.createHTMLTerminal(name);
		JPrintGen printGen=new JPrintGen();
		printGen.takeAttributesFrom(this);
		printGen.setTerminal(termPrinter);
		return printGen;
	}
	
	public JPrintGen createPrintGenWins() throws Exception {
		BizTerminal termPrinter=this.createWinsTerminal();
		JPrintGen printGen=new JPrintGen();
		printGen.takeAttributesFrom(this);
		printGen.setTerminal(termPrinter);
		return printGen;
	}
	
	private BizTerminal createWinsTerminal() throws Exception {
		JTerminal terminalPointer=new TWinsCustom();
		terminalPointer.setCompany(this.pCompany.getValue());
		BizTerminal terminal=new BizTerminal();
		terminal.setObjTerminalPointer(terminalPointer);
		return terminal;
	}
	
	private BizTerminal createHTMLTerminal(String name) throws Exception {
		JTerminal terminalPointer=new THTMLFile();
		terminalPointer.setConnectionString(name);
		terminalPointer.setCompany(this.pCompany.getValue());
		BizTerminal terminal=new BizTerminal();
		terminal.setObjTerminalPointer(terminalPointer);
		return terminal;
	}
	
	private BizTerminal createPDFTerminal(String name) throws Exception {
		JTerminal terminalPointer=new TPDFPrinter();
		terminalPointer.setConnectionString(name);
		terminalPointer.setCompany(this.pCompany.getValue());
		BizTerminal terminal=new BizTerminal();
		terminal.setObjTerminalPointer(terminalPointer);
		return terminal;
	}
	

	public String getUniqueFileName() throws Exception {
		return this.pCompany.getValue()+"/"+this.toString()+".pdf";
	}
	
  public final JMap<String, String> getAllSections() throws Exception {
  	return JFieldSet.getSet(this).getAllSections();
  }

  public boolean isFieldSetTicket() throws Exception {
  	return this.getCamposSet().equals(JFieldSet.TICKET_SET);
  }

  public boolean isFieldSetTaxCertif() throws Exception {
  	return this.getCamposSet().equals(JFieldSet.TAX_CERTIF_SET);
  }
  
  public boolean isToWins() throws Exception {
  	return this.pToWins.getValue();
  }
  
	public JList<LayoutField> generateObjects(String layoutSector, Object source, boolean format) throws Exception {
		JList<LayoutField> list=JCollectionFactory.createList();

		JIterator<BizLayoutCampo> iter = this.getObjCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizLayoutCampo campo=iter.nextElement();
			if (!campo.getSector().equals(layoutSector)) 
				continue;
			
			JObject reply; String sreply;
			if (campo.ifConstante()) {
				reply=new JString(campo.pConstante.getValue());
				sreply=campo.pConstante.getValue();
			} else {
				reply=this.createObject(campo, this.getDatoParticular(campo, source));
				sreply=this.formatField(campo, source).toString();
			}
			list.addElement(new LayoutField(campo, reply, sreply));
		}
		return list;
		
	}
	
  private JObject createObject(BizLayoutCampo campo, Object obj) throws Exception {
  	if (obj instanceof JCurrency) return (JCurrency)obj;
  	if (campo.ifMonedaPais()) return new JCurrency((Double)obj);
  	if (obj instanceof Double) return new JFloat((Double)obj);
  	if (obj instanceof String) return new JString((String)obj);
  	if (obj instanceof Boolean) return new JBoolean((Boolean)obj);
  	if (obj instanceof Date) return new JDate((Date)obj);
  	return new JString((String)obj);
  }

	
	public class LayoutField {
		BizLayoutCampo campo;
		JObject valor;
		String svalor;
		public LayoutField(BizLayoutCampo c, JObject v, String sv) {
			this.campo=c;
			this.valor=v;
			this.svalor=sv;
		}
		public JObject getValor() {
			return this.valor;
		}
		
		public String getValorString() {
			return this.svalor;
		}

		public BizLayoutCampo getCampo() {
			return this.campo;
		}
	}
	
	public boolean isGoogleDoc() throws Exception {return pGoogleDoc.getValue();}
  


}

