package  pss.bsp.bo.gen;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.bsp.bo.gen.detalle.BizGenDetalle;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consola.config.BizBSPConfig;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspSititaParser;
import pss.bsp.pdf.BizPDF;
import pss.core.services.records.JRecords;
import pss.core.tools.ExcelTextParser;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.core.tools.PssLogger;
import pss.core.tools.XExcelTextParser;
import pss.core.tools.collections.JIterator;

public class JParseoGenBO implements IParseo {

	static final int CANT_CAMPOS=50;
	static final int CANT_CONOCIMIENTO=1000;
	IFinderGenBO finder;
	InputStream input;
	String idPdf;
	Date fechaDesde;
	Date fechaHasta;
	String company;
	String owner;
	long idInterfaz;
	String idParser;
	String typeFile="TXT";
	String formato;
	String conciliableKey;
	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	boolean generateHeader=true;
	boolean autodetectFormato=false;
	BizFormato formatoActual;

	public void setTypeFile(String zTypeFile) throws Exception {
		typeFile=zTypeFile;
	}

	public String getTableDetalle() throws Exception {
		return new BizGenDetalle().GetTable();
	}
	public void setFormat(String zFormat) throws Exception {
		formato=zFormat;
	}

	public void setIdParseador(String idClase) throws Exception {
		idParser=idClase;
	}

	public String getIdPareador() {
		return idParser;
	}

	public void setCompany(String zCompany) throws Exception {
		company=zCompany;
	}

	public void setId(String zidInterfaz) throws Exception {
		idInterfaz=Long.parseLong(zidInterfaz);
	}

	public void setOwner(String zOwner) throws Exception {
		owner=zOwner;
	}

	String filename;
	public void setFilename(String input) throws Exception {
		this.filename = input;
	}

	public void setInput(InputStream input) throws Exception {
		this.input=input;
	}

	public String getId() throws Exception {
		return ""+idInterfaz;
	}

	public Date getPeriodoDesde() throws Exception {
		return fechaDesde;
	}

	public Date getPeriodoHasta() throws Exception {
		return fechaHasta;
	}

	public void setFechaDesde(Date zFecha) throws Exception {
		fechaDesde = zFecha;
	}

	public void setFechaHasta(Date zFecha) throws Exception {
		fechaHasta = zFecha;
	}
	
	public String getFormat() throws Exception {
		return formato;
	}

	public void addListenerDetect(IFinder finder) {
		this.finder=(IFinderGenBO) finder;
	}

	private String getContent() throws Exception {
		if (typeFile.equals("RTF")) {

			RTFEditorKit kit = new RTFEditorKit();
			Document doc = kit.createDefaultDocument();
			kit.read(input, doc, 0);

			return  doc.getText(0, doc.getLength());
		}
		if (typeFile.equals("XLSX")) {
			XExcelTextParser parser=new XExcelTextParser();
			return parser.toCSV(input);
		}
		if (typeFile.equals("XLS")) {
			ExcelTextParser parser=new ExcelTextParser();
			return parser.toCSV(input);
		}
		if (typeFile.equals("PDF")) {
			PDFTextParser parser=new PDFTextParser();
			return parser.pdftoText(input,false,true);
		}
		if (typeFile.equals("TXT")||typeFile.equals("CSV")||typeFile.equals("TAB")||typeFile.equals("COMA")) {
			BufferedReader dataIn=new BufferedReader(new InputStreamReader(input));
			String content;
			content="";
			String line;
			while ((line=dataIn.readLine())!=null)
				content+=line+"\r\n";

			return content;
		}
		throw new Exception("Tipo ["+typeFile+"]Desconocido");
	}

	protected void privExecute() throws Exception {
		autodetectFormato=formato.equals("")||formato.equals("NEW");
		if (autodetectFormato) formato=createNewFormato();
		
		String content=getContent();

		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for(String linea : content.split("\r\n|\r|\n")) {
			PssLogger.logDebug(linea);
				parserLinea(linea);
		}
		getLearning().finalizeParser(autodetectFormato);
	}

	protected void parserLinea(String linea) throws Exception {
		String parsedLine=linea;
		if (typeFile.equals("TAB")) {
			parsedLine=convertTabbedToCSV(linea);
		}
		if (typeFile.equals("COMA")) {
			parsedLine=convertCommaToCSV(linea);
		}
		else if (!typeFile.equals("CSV")&&!typeFile.equals("XLS")&&!typeFile.equals("XLSX")) {
			parsedLine=convertToCSV(linea);
		}
		// aqui todo es casi como un csv
		getLearning().processLine(parsedLine, autodetectFormato);
		getLearning().evaluate(parsedLine);
		// System.out.println(parsedLine);
	}

	public void execute() throws Exception {
		try {
			finder.start();
			privExecute();
			finder.stop();
		} catch (Exception e) {
			finder.error(e);
		}
	}

	private String createNewFormato() throws Exception {
		String formato="FORM_"+idInterfaz;
		String valorDefault=IConciliable.NO_CHEQUEAR;
		BizFormato f=new BizFormato();
		f.setCompany(company);
		f.setOwner(owner);
		f.setDescripcion("Formato para interfaz "+idInterfaz);
		f.setIdformato(formato);
		f.setD1(valorDefault);
		f.setD2(valorDefault);
		f.setD3(valorDefault);
		f.setD4(valorDefault);
		f.setD5(valorDefault);
		f.setD6(valorDefault);
		f.setD7(valorDefault);
		f.setD8(valorDefault);
		f.setD9(valorDefault);
		f.setD10(valorDefault);
		f.setD11(valorDefault);
		f.setD12(valorDefault);
		f.setD13(valorDefault);
		f.setD14(valorDefault);
		f.setD15(valorDefault);
		f.setD16(valorDefault);
		f.setD17(valorDefault);
		f.setD18(valorDefault);
		f.setD19(valorDefault);
		f.setD20(valorDefault);
		f.setD21(valorDefault);
		f.setD22(valorDefault);
		f.setD23(valorDefault);
		f.setD24(valorDefault);
		f.setD25(valorDefault);
		f.setD26(valorDefault);
		f.setD27(valorDefault);
		f.setD28(valorDefault);
		f.setD29(valorDefault);
		f.setD30(valorDefault);
		f.setD31(valorDefault);
		f.setD32(valorDefault);
		f.setD33(valorDefault);
		f.setD34(valorDefault);
		f.setD35(valorDefault);
		f.setD36(valorDefault);
		f.setD37(valorDefault);
		f.setD38(valorDefault);
		f.setD39(valorDefault);
		f.setD40(valorDefault);
		formatoActual=f;
		return formato;
	}

	class Columna {

		String tipo;
		int columna;
		long cantFechas;
		long cantNumeric;
		long cantStrings;
		long cantBoletos;
		long cantEmptys;

		Columna(int col) {
			columna=col;
		}

		void determinarTipoColumna() throws Exception {
			if (cantBoletos>cantNumeric&&cantBoletos>cantStrings&&cantBoletos>cantEmptys&&cantBoletos>cantFechas) tipo="BOLETO";
			if (cantFechas>cantNumeric&&cantFechas>cantStrings&&cantFechas>cantEmptys&&cantFechas>cantBoletos) tipo="FECHA";
			if (cantNumeric>cantFechas&&cantNumeric>cantStrings&&cantNumeric>cantEmptys&&cantNumeric>cantBoletos) 
				if (cantNumeric-cantStrings>10)
					tipo="NUMERIC";
			if (cantStrings>cantNumeric&&cantStrings>cantFechas&&cantStrings>cantEmptys&&cantStrings>cantBoletos) tipo="STRING";
			if (cantEmptys>cantNumeric&&cantEmptys>cantStrings&&cantEmptys>cantFechas&&cantEmptys>cantBoletos) tipo="EMPTY";

		}
		// compatibiliza formato de boletos con los de BSP
	  String adjustBoleto(String dato) {
	  	String d = specialBoletosConvert(dato);
	  	d = d.trim().replaceAll("-", "");
	  	while (d.startsWith("0") && d.length()>10) d = d.substring(1);
	  	return d;
	  }

		String adjust(String dato) throws Exception {
			dato = dato.trim();
			if (dato.equals(""))
				dato = null;
			if (tipo==null)
				tipo = "EMPTY" ;
			
			if (dato==null&&!tipo.equals("EMPTY")) return null;
			if (dato==null&&tipo.equals("EMPTY")) return null;
			if (tipo.equals("NUMERIC")&&JTools.isNumber(adjustToNumericFormat(dato))) {
				return adjustToNumericFormat(dato);
			}
			if (tipo.equals("BOLETO")&&isBoleto(dato)) {
				return adjustBoleto(dato);
			}
			if (tipo.equals("FECHA")&&isDate(dato)) {
				return adjustToDateFormat(dato);
			}
			if (tipo.equals("STRING")) return null;
			if (tipo.equals("EMPTY")) return null;
			return null;
		}

		public boolean isBoleto(String dato) throws Exception {
			String d = dato.trim().replaceAll("-", "");
			if (JTools.isNumber(d) && d.length()>=10 && d.length()<=13) return true;
			Pattern p=Pattern.compile("(\\d){10}.\\d");
			Matcher m=p.matcher(dato);
			return m.matches();
		}
		
		boolean cumple(String dato) throws Exception {
			if (dato.toLowerCase().indexOf("Total".toLowerCase())!=-1) return false;
			if (dato.toLowerCase().indexOf("Tarifa".toLowerCase())!=-1) return false;
			if (dato.toLowerCase().indexOf("conexi".toLowerCase())!=-1) return false;
			if (dato.toLowerCase().indexOf("void".toLowerCase())!=-1) return false;
			
			if (tipo==null) tipo="EMPTY";
			if (dato==null&&!tipo.equals("EMPTY")) return true;
			if (dato==null&&tipo.equals("EMPTY")) return true;
			if (tipo.equals("BOLETO")&&isBoleto(dato)) return true;
//			if (tipo.equals("NUMERIC")&&JTools.isNumber(adjustToNumericFormat(dato))) return true;
//			if (tipo.equals("NUMERIC")&&dato.equals("")) return true;
//			if (tipo.equals("FECHA")&&isDate(dato)) return true;
			if (tipo.equals("NUMERIC")) return true;
			if (tipo.equals("FECHA")) return true;
			if (tipo.equals("STRING")) return true;
			if (tipo.equals("EMPTY")) return true;
			return false;
		}
		boolean cumpleNoDetectEmpty(String dato) throws Exception {
			if (dato.trim().equals("")) return true;
			return cumple(dato);
		}
		void evaluarColumna(String dato) throws Exception {
			if (dato==null||dato.trim().equals("") || dato.isEmpty()) cantEmptys++;
			else if (isBoleto(dato)) cantBoletos++;
			else if (isDate(dato)) cantFechas++;
			else if (JTools.isNumber(adjustToNumericFormat(dato))) cantNumeric++;
			else cantStrings++;
		}

		String adjustToNumericFormat(String dato) {
				try {
					dato = dato.trim();
					if (dato.indexOf("E-")!=-1) return "0";
					if (dato.indexOf(".")!=-1) {
						if (dato.indexOf(".")!=-1&&dato.indexOf(",")!=-1) { // ambos signos estan
							if (dato.lastIndexOf(".")>dato.lastIndexOf(",")) { // cual primero
								dato=dato.replaceAll(",", ""); // borrar las comas
							}
							else { // borrar los . y transformar las ,
								dato=dato.replaceAll("\\.", "");
								dato=dato.replace(",", ".");
							}
						}
						else if (dato.substring(dato.indexOf(".")).indexOf(".")!=-1) { // el punto es separador
							dato=dato.replace(",", ".");
						}
						else { // hay muchos puntos. los borro
							dato=dato.replaceAll("\\.", "");
						}
					}
					else {
						dato=dato.replace(",", ".");
					}
					if (!testNumeric(dato)) return "no numeric";
					if (dato.length()>12) return "no numeric";
					boolean neg = dato.indexOf("-")!=-1;
					if (neg && dato.trim().indexOf("-")!=0) 
						return "no numeric";
					
					dato = JTools.getNumberEmbeddedWithSep(dato);
					if (dato.trim().equals("")) return "0";
					return ""+(neg?-1:1)*Double.parseDouble(dato);
				} catch (Exception e) {
					return "no numeric";
				} 
		}
		
	}
	public boolean testNumeric(String sAlfaNumeric) throws Exception {
		Pattern p=Pattern.compile("(\\p{Punct})*(\\d)*\\p{Punct}*(\\d)*(\\p{Punct})*");
		Matcher m=p.matcher(sAlfaNumeric);
		return m.matches();
	}
	String adjustToDateFormat(String dato) {
		try {
			int start, s1;
			if ((start=dato.indexOf("/"))==-1) 
				if ((start=dato.indexOf("-"))==-1) 
						return dato;
			if ((s1=dato.indexOf("/", start+1))==-1) 
				if ((s1=dato.indexOf("-", start+1))==-1) 
					return dato;
			int dia = Integer.parseInt(dato.substring(0, start).trim());
			int mes = Integer.parseInt(dato.substring(start+1, s1).trim());
			int anio = Integer.parseInt(dato.substring(s1+1).trim());
			if (anio<70) anio+=2000;
			else if (anio<100) anio+=1900;
			if (BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getFormatoFecha().equals(BizBSPConfig.FORMATO_FECHA_MM))
				return JTools.LPad(""+mes,2,"0")+"-"+JTools.LPad(""+dia,2,"0")+"-"+JTools.LPad(""+anio,4,"0");
			return JTools.LPad(""+dia,2,"0")+"-"+JTools.LPad(""+mes,2,"0")+"-"+JTools.LPad(""+anio,4,"0");
		} catch (Exception e) {
			return "no date";
		} 
}

	public boolean isDate(String dato) {
		Pattern p=Pattern.compile("(\\d){1,2}(-|\\/)(\\d){2}(-|\\/)(\\d){2,4}");
		Matcher m=p.matcher(dato);
		return m.matches();

	}

	private class Learning {

		String[][] learningTable=new String[CANT_CONOCIMIENTO][CANT_CAMPOS];
		int[] sizes=new int[CANT_CONOCIMIENTO];
		int[] cantCampos=new int[CANT_CAMPOS];
		Columna[] columnas=new Columna[CANT_CAMPOS];
		int numLinea=0;
		boolean learningMode=true;
		String[][] learningFormat=new String[CANT_CONOCIMIENTO][CANT_CAMPOS];
		int numLearFor=0;

		private boolean evaluateColumnas() throws Exception {
			long max=0;
			long campos=0;
			for(int i=0; i<numLinea; i++) {
				for(int s=0; s<CANT_CAMPOS; s++) {
					if (columnas[s]==null) columnas[s]=new Columna(s);
					columnas[s].evaluarColumna(sizes[i]>=s ? learningTable[i][s] : null);
				}
			}
			for(int s=0; s<CANT_CAMPOS; s++) {
				if (columnas[s]==null) continue;
				columnas[s].determinarTipoColumna();
			}
			return true;
		}
		
		private int cumpleTiposColumnas(int linea) throws Exception {
			boolean cum=true;
			int errores=0;
			for(int s=0; s<CANT_CAMPOS; s++) {
				if (s>=sizes[linea]) continue;
				cum&=columnas[s].cumpleNoDetectEmpty(learningTable[linea][s]);
				if (!cum) 
					errores++;
			}
			return errores;
		}
		private void adjustTiposColumnas(int linea) throws Exception {
				for(int s=0; s<CANT_CAMPOS; s++) {
				if (s>=sizes[linea]) continue;
				String newDato = columnas[s].adjust(learningTable[linea][s]);
				if (newDato!=null) {
					learningTable[linea][s] = newDato;
				}
			}
		}
		private void adjustTiposColumnas(String[] row, int cantCampos) throws Exception {
			for(int s=0; s<CANT_CAMPOS; s++) {
				if (s>=cantCampos) continue;
				String newDato = columnas[s].adjust(row[s]);
				if (newDato!=null) {
					row[s] = newDato;
				}
			}
		}

		private boolean cumpleTiposColumnas(String[] row, int cantCampos) throws Exception {
			boolean cum=true;
			if (cantCampos<3) return false; //ignore lines
			if (emptyFields(row)<3) return false;

				for(int s=0; s<CANT_CAMPOS; s++) {
				if (s>=cantCampos) continue;
				cum&=columnas[s].cumple(row[s]);
			}
			return cum;
		}

		
		private void printLearningResult() throws Exception {
			System.out.println("ACEPTADOS<----------------------------------------------------------");
			for(int i=0; i<numLinea; i++) {
				if (cumpleTiposColumnas(i)==0) {
					adjustTiposColumnas(i);
					generateDetail(i, learningTable[i], sizes[i]);
					if (numLearFor<CANT_CONOCIMIENTO) learningFormat[numLearFor++]=learningTable[i];
					// printData(learningTable[i], sizes[i]);
				}
			}
	
			  System.out.println("NO ACEPTADOS<----------------------------------------------------------"); 
			  for(int i=0; i<numLinea; i++) {
			   if (cumpleTiposColumnas(i)>0) 
			  	 printData(learningTable[i], sizes[i]); 
			  } 
			  System.out.println("FIN<----------------------------------------------------------");
			 
		}

		private boolean isDetailLine(String linea) throws Exception {
			boolean cum=true;
			for(int s=0; s<CANT_CAMPOS; s++) {
				cum&=columnas[s].cumple(linea);
			}
			return cum;
		}

		private int fillLearningRow(String[] row, String linea) {
			int j=0;
			int first=0;
			int pos = 0;
			while (pos !=-1 && j<CANT_CAMPOS-1) {
				pos = linea.indexOf(";",first);
				if (pos==-1) pos = linea.indexOf("\r",first);
			  if (pos==-1) pos = linea.indexOf("\n",first);
			  
			  String dato;
			  if (pos!=-1) dato=linea.substring(first,pos);
			  else dato=linea.substring(first);
				row[j++]=dato;
				first = pos+1;
			}
			return j;
//			int j=0;
//			StringTokenizer tokenizer=new StringTokenizer(linea, ";\r\n");
//			while (tokenizer.hasMoreElements()&&j<CANT_CAMPOS) {
//				String dato=tokenizer.nextToken();
//				row[j++]=dato;
//			}
//			return j;
		}

		private int emptyFields(String row[]) {
			int qty = 0;
			for (String d :row) {
				if (d==null) continue;
				if (d.trim().equals("")) continue;
				qty++;
			}
			return qty;
		}
		
		private boolean fillLearningTable(String linea) {
			if (numLinea>=CANT_CONOCIMIENTO) return false;
			int j=fillLearningRow(learningTable[numLinea], linea);
			if (j==-1) return false;
			if (j<3) return true; //ignore lines
			if (emptyFields(learningTable[numLinea])<3) return true;
			cantCampos[j]++;
			sizes[numLinea]=j;
			numLinea++;
			return true;
		}

		private void printData(String[] row, int cantCampos) {
			for(int j=0; j<cantCampos-1; j++)
				System.out.print(row[j]+";");
			System.out.println(row[cantCampos-1]);
		}


		public void processLine(String linea, boolean autodetect) throws Exception {
			if (!learningMode) return;
			int j=0;
//			if (linea.indexOf("4997061992")!=-1)
//				j++;
			if (!getLearning().fillLearningTable(linea)) {
				evaluateColumnas();
				printLearningResult();
				if (autodetect) autodetectFormato();
				learningMode=false;
			}
		}

		public void evaluate(String linea) throws Exception {
			if (learningMode) return;
			String[] row=new String[CANT_CAMPOS];
			int cantCampos=getLearning().fillLearningRow(row, linea);
			if (cantCampos==-1) return;
			if (cumpleTiposColumnas(row, cantCampos)) {
				adjustTiposColumnas(row,cantCampos);
				generateDetail(numLinea, row, cantCampos);
				numLinea++;
			}
		}

		public void finalizeParser(boolean autodetect) throws Exception {
			if (!learningMode) return;
			evaluateColumnas();
			printLearningResult();
			if (autodetect) autodetectFormato();
			learningMode=false;
		}

		private JRecords<BizPDF> getPdfs() throws Exception {
			JRecords<BizPDF> pdfs=new JRecords<BizPDF>(BizPDF.class);
			pdfs.addFilter("company", company);
//			pdfs.addFilter("owner", owner);
			pdfs.addFilter("fecha_desde", getPeriodoHasta(),"<=");
			pdfs.addFilter("fecha_hasta", getPeriodoDesde(),">=");
			pdfs.addOrderBy("fecha_desde", "DESC");
			pdfs.toStatic();
			return pdfs;
		}

		private boolean fillDetailsPdfs(BizPDF pdf) throws Exception {
			IBspSititaParser parseo=(IBspSititaParser) JParseoFactory.getInstanceFromClass(pdf.getParseo());;
			boolean result =  parseo.fillFormat(pdf, learningFormat, formatoActual);
			formatoActual.setConciliableKey(parseo.getConciliableKey());
			setConciliableKey(parseo.getConciliableKey());
			return result;
		}

		public void autodetectFormato() throws Exception {
			boolean exito=false;
			JIterator<BizPDF> pdfs=getPdfs().getStaticIterator();
			
			while (pdfs.hasMoreElements()) {
				BizPDF pdf=pdfs.nextElement();
				if (exito=fillDetailsPdfs(pdf)) break;
			}

	//		if (exito) {
			finder.detectFormat(formatoActual);
	//		}
		}

	};

	Learning glearning=null;
	Learning getLearning() {
		if (glearning!=null) return glearning;
		return glearning=new Learning();
	}

	private String convertToCSV(String linea) {
		String superTrim=linea.replaceAll("(\\p{Blank}){2,1000}", ";");
		superTrim=superTrim.replaceAll("\t", ";");
		superTrim=superTrim.replaceAll("(\\p{Alpha})\\p{Blank}(\\p{Digit})", "$1;$2");
		superTrim=superTrim.replaceAll("(\\p{Digit})\\p{Blank}(\\p{Digit})", "$1;$2");
		superTrim=superTrim.replaceAll("(\\p{Digit})\\p{Blank}(\\.\\p{Digit})", "$1;$2");
		// superTrim = superTrim.replaceAll("\\p{Alpha}\\p{Blank}\\p{Digit}", ";");
		superTrim=superTrim.replaceAll("(\\p{Digit})\\p{Blank}(\\p{Alpha})", "$1;$2");

		return superTrim;
	}
	private String convertTabbedToCSV(String linea) {
		String superTrim=linea.replaceAll("\t", ";");
		superTrim=superTrim.replaceAll("(\\p{Blank}){0,1000};(\\p{Blank}){0,1000}", ";");
		return superTrim;
	}
	private String convertCommaToCSV(String linea) {
		String superTrim=linea.replaceAll(",", ";");
		superTrim=superTrim.replaceAll("(\\p{Blank}){0,1000};(\\p{Blank}){0,1000}", ";");
		return superTrim;
	}

	public void generateHeader() throws Exception {
		if (!generateHeader) return;

		BizGenCabecera cabecera=new BizGenCabecera();
		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		cabecera.setIdinterfaz(idInterfaz);
		cabecera.setIdformato(formato);
		finder.detectHeader(cabecera);
		generateHeader=false;
	}
	// intenta detectar algunas transformaciones necesarias, como ver que los boletos no incluyan un digito verificador desconocido
	private String specialBoletosConvert(String data) {
		if (data == null) return data;
		if (data.length() < 10) return data; // muy pequeño
		Pattern p = Pattern.compile("(\\d){4}(-|\\p{Blank}|)(\\d){6}(-|-(\\d)|)");
		Matcher m = p.matcher(data);
		boolean b = m.find();
		if (!b) return data;
		if (data.substring(data.length() - 2, data.length() - 1).equals("-")) {
			data = data.substring(0, data.length() - 1); // elimina el digito verficador
		}
		data = data.replaceAll("-", "");
		p=Pattern.compile("(\\d){10}.\\d");
		m=p.matcher(data);
		if (m.matches()) {
			data=data.substring(0,data.length()-2);
		}
		return data;
	}

	public void generateDetail(int linea, String[] row, int cantCampos) throws Exception {
		generateHeader();
		BizGenDetalle detalle=new BizGenDetalle();
		detalle.setCompany(company);
		detalle.setOwner(owner);
		detalle.setIdinterfaz(idInterfaz);
		detalle.setLinea(linea);
		if (cantCampos>=1) detalle.setD1((row[0]));
		if (cantCampos>=2) detalle.setD2((row[1]));
		if (cantCampos>=3) detalle.setD3((row[2]));
		if (cantCampos>=4) detalle.setD4((row[3]));
		if (cantCampos>=5) detalle.setD5((row[4]));
		if (cantCampos>=6) detalle.setD6((row[5]));
		if (cantCampos>=7) detalle.setD7((row[6]));
		if (cantCampos>=8) detalle.setD8((row[7]));
		if (cantCampos>=9) detalle.setD9((row[8]));
		if (cantCampos>=10) detalle.setD10((row[9]));
		if (cantCampos>=11) detalle.setD11((row[10]));
		if (cantCampos>=12) detalle.setD12((row[11]));
		if (cantCampos>=13) detalle.setD13((row[12]));
		if (cantCampos>=14) detalle.setD14((row[13]));
		if (cantCampos>=15) detalle.setD15((row[14]));
		if (cantCampos>=16) detalle.setD16((row[15]));
		if (cantCampos>=17) detalle.setD17((row[16]));
		if (cantCampos>=18) detalle.setD18((row[17]));
		if (cantCampos>=19) detalle.setD19((row[18]));
		if (cantCampos>=20) detalle.setD20((row[19]));
		if (cantCampos>=21) detalle.setD21((row[20]));
		if (cantCampos>=22) detalle.setD22((row[21]));
		if (cantCampos>=23) detalle.setD23((row[22]));
		if (cantCampos>=24) detalle.setD24((row[23]));
		if (cantCampos>=25) detalle.setD25((row[24]));
		if (cantCampos>=26) detalle.setD26((row[25]));
		if (cantCampos>=27) detalle.setD27((row[26]));
		if (cantCampos>=28) detalle.setD28((row[27]));
		if (cantCampos>=29) detalle.setD29((row[28]));
		if (cantCampos>=30) detalle.setD30((row[29]));
		if (cantCampos>=31) detalle.setD31((row[30]));
		if (cantCampos>=32) detalle.setD32((row[31]));
		if (cantCampos>=33) detalle.setD33((row[32]));
		if (cantCampos>=34) detalle.setD34((row[33]));
		if (cantCampos>=35) detalle.setD35((row[34]));
		if (cantCampos>=36) detalle.setD36((row[35]));
		if (cantCampos>=37) detalle.setD37((row[36]));
		if (cantCampos>=38) detalle.setD38((row[37]));
		if (cantCampos>=39) detalle.setD39((row[38]));
		if (cantCampos>=40) detalle.setD40((row[39]));
		finder.detectDetail(detalle);
	}

	public static void main(String[] Gens) {
		JParseoGenBO parser=new JParseoGenBO();
		IFinderGenBO finder=new IFinderGenBO() {

			public void detectDetail(BizGenDetalle detail) throws Exception {
				System.out.print("ID: "+detail.getIdinterfaz());
				System.out.print("| D1: "+detail.getD1());
				System.out.print("| D2: "+detail.getD2());
				System.out.print("| D3: "+detail.getD3());
				System.out.print("| D4: "+detail.getD4());
				System.out.print("| D5: "+detail.getD5());
				System.out.print("| D6: "+detail.getD6());
				System.out.print("| D7: "+detail.getD7());
				System.out.print("| D8: "+detail.getD8());
				System.out.print("| D9: "+detail.getD9());
				System.out.print("| D10: "+detail.getD10());
				System.out.print("| D11: "+detail.getD11());
				System.out.print("| D12: "+detail.getD12());
				System.out.print("| D13: "+detail.getD13());
				System.out.print("| D14: "+detail.getD14());
				System.out.print("| D15: "+detail.getD15());
				System.out.print("| D16: "+detail.getD16());
				System.out.print("| D17: "+detail.getD17());
				System.out.print("| D18: "+detail.getD18());
				System.out.print("| D19: "+detail.getD19());
				System.out.println("| D20: "+detail.getD20());

			}

			public void detectHeader(BizGenCabecera header) throws Exception {
				System.out.print("ID: "+header.getIdinterfaz());
				System.out.println("| Formato: "+header.getIdformato());
			}

			public void error(Exception e) throws Exception {
				System.out.println("Error"+e.getMessage());
				e.printStackTrace();

			}

			public void start() throws Exception {
				System.out.println("inicio");

			}

			public void stop() throws Exception {
				System.out.println("fin");

			}

			public void detectFormat(BizFormato format) throws Exception {
				// TODO Auto-generated method stub

			}

		};
		try {
			parser.setInput(new FileInputStream("c:\\pssaction.txt"));
			parser.addListenerDetect(finder);
			parser.execute();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public String getIATA() throws Exception {
		return null;
	}

}
