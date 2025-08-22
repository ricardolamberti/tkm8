package  pss.bsp.pdf.col;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.StringTokenizer;

import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.bsp.pdf.col.cabecera.BizColCabecera;
import pss.bsp.pdf.col.detalle.BizColDetalle;
import pss.bsp.pdf.col.impuesto.BizColImpuesto;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.core.tools.PssLogger;

public class JParseoCol implements IParseo {

	IFinderCol finder;

	InputStream input;

	String idPdf;

	String iata = "0";

	Date fechaDesde;

	Date fechaHasta;

	boolean detectHeader = true;

	boolean detectHeader2 = false;

	boolean detectAerolinea = false;

	boolean saveHeader = false;
	boolean detectPeriodo = false;

	boolean detectSeccion = true;

	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public String getTableDetalle() throws Exception {
		return new BizColDetalle().GetTable();
	}
	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	BizColCabecera cabecera;

	BizColDetalle detalle;

	BizColImpuesto impuesto;
	BizColImpuesto impuesto2;

	String actual_idAerolinea = "";

	String actual_aerolinea = "";

	double actual_acumAerTotal = 0;

	String actual_tipoRuta = "";

	String actual_operacion = "";

	double actual_acumOpTotal = 0;

	long nextLinea = 0;

	double qn = 0;

	double impuestos_contado = 0;

	double impuestos_tarjeta = 0;

	String company;

	String owner;

	String idParser;

	boolean pendingDetail = false;

	List<BizColImpuesto> impuestos = null;

	public void setIdParseador(String idClase) throws Exception {
		idParser = idClase;
	}

	public String getIdPareador() {
		return idParser;
	}

	public void setCompany(String zCompany) throws Exception {
		company = zCompany;
	}

	public void setOwner(String zOwner) throws Exception {
		owner = zOwner;
	}

	public String getId() throws Exception {
		return idPdf;
	}

	public void setId(String zId) throws Exception {
		// ignorado, porque este parser genera su propio id
	}

	public void addListenerDetect(IFinder finder) {
		this.finder = (IFinderCol) finder;
	}

	private String getContent() {
		PDFTextParser parser = new PDFTextParser();
		return parser.pdftoText(input,true,true);
	}

	protected void privExecute() throws Exception {

		String content = getContent();

		if (content == null) throw new Exception("El archivo no parece ser una interfaz BSP");
		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for (String linea : content.split("\r\n|\r|\n")) {
			if (linea.length()<200) {
				parserLinea(linea);
			} else {
				for (int s=0;s<linea.length();s+=132) {
					if (s+132>=linea.length()) 
						parserLinea(linea.substring(s));
					else
						parserLinea(linea.substring(s,s+132));
				} 
			}
		}
		closePendingDetail();
	}

	protected void parserLinea(String linea) throws Exception {
		PssLogger.logDebug(linea);
		if (idPdf == null) detectId(linea);
		//System.out.println(linea);
		detectTipoRuta(linea);
		if (detectHeader2) detectHeader(linea,true);
		if (detectHeader) detectHeader(linea,false);
		if (detectSeccion) detectSeccion(linea);
		//System.out.println(linea);
		if (detectStuff(linea)) return;
		if (detectTotal(linea)) return;
		if (detectLinea(linea)) return;
	}

	private void detectSeccion(String linea) throws Exception {
		if (linea.indexOf("VENTAS CONTADO") != -1) {
			detectSeccion = false;
			return;
		}
		else if (linea.indexOf("VENTAS COMBINADAS") != -1	) {
			detectSeccion = false;
			return;
		}
		else if (linea.indexOf("VENTAS CREDITO") != -1) {
			detectSeccion = false;
			return;
		}
		else if (linea.indexOf("VOID") != -1) {
			detectSeccion = false;
			return;
		}
	}

	private boolean detectStuff(String linea) throws Exception {
	//	if (detectSeccion) return true;
		if (linea.startsWith("|")) return true;// ojo
		if (linea.startsWith("-")) return true;
		if (linea.indexOf("XXXXXXXXXX") != -1) return true;
		if (linea.indexOf("====") != -1) return true;
		if (linea.trim().equals("")) return true;

		return false;
	}

	public void detectTipoRuta(String linea) throws Exception {
		boolean internacional = linea.indexOf("INTERNACIONAL") != -1;
		boolean domestico = linea.indexOf("DOMESTICO") != -1;
		if (internacional)
			actual_tipoRuta =  "INTERNACIONAL";
		if (domestico)
			actual_tipoRuta =  "DOMESTICO";
	}

	public boolean detectAerolinea(String linea) throws Exception {
		int[] columnas = { 1, 12,57 };
		for (int c = 0; c < columnas.length; c++) {
			String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
			switch (c) {
			case 0:
				actual_idAerolinea = JTools.removeSpaces(JTools.getFirstNumbersEmbedded(sCont));
				actual_acumAerTotal = 0;
				actual_acumOpTotal = 0;
				break;// id aerolinea
			case 1:
				actual_aerolinea = JTools.removeSpaces(sCont);
				break;// aerolinea
			}
		}
		detectAerolinea=false;
		detectPeriodo=true;
		return true;
	}

	public  Date getFechaDDMMMSinSep(String linea) throws Exception {
		int start, s1, s2, s3, s4, s5, s6, s7;
		start=2;
		s1=5;
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
  	int mes=0;
  	String fecha = linea.substring(start).trim();
		if (fecha.indexOf("ENE")!=-1)      mes=Calendar.JANUARY;
  	else if (fecha.indexOf("FEB")!=-1) mes=Calendar.FEBRUARY;
  	else if (fecha.indexOf("MAR")!=-1) mes=Calendar.MARCH;
  	else if (fecha.indexOf("ABR")!=-1) mes=Calendar.APRIL;
  	else if (fecha.indexOf("APR")!=-1) mes=Calendar.APRIL;
  	else if (fecha.indexOf("MAY")!=-1) mes=Calendar.MAY;
  	else if (fecha.indexOf("JUN")!=-1) mes=Calendar.JUNE;
  	else if (fecha.indexOf("JUL")!=-1) mes=Calendar.JULY;
   	else if (fecha.indexOf("AGO")!=-1) mes=Calendar.AUGUST;
   	else if (fecha.indexOf("AUG")!=-1) mes=Calendar.AUGUST;
    else if (fecha.indexOf("SEP")!=-1) mes=Calendar.SEPTEMBER;
  	else if (fecha.indexOf("OCT")!=-1) mes=Calendar.OCTOBER;
  	else if (fecha.indexOf("NOV")!=-1) mes=Calendar.NOVEMBER;
  	else if (fecha.indexOf("DIC")!=-1) mes=Calendar.DECEMBER;
  	else if (fecha.indexOf("DEC")!=-1) mes=Calendar.DECEMBER;

		cal.set(Calendar.MONTH, mes);
		cal.set(Calendar.YEAR, 1900+fechaDesde.getYear()); 
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
	public boolean detectLinea(String linea) throws Exception {
		if (linea.indexOf("+RTDN")!=-1)
			return false;
		if ((linea.indexOf("CANX")!=-1)||linea.indexOf("CANN")!=-1){
			closePendingDetail();
			return false;
		}
		Pattern p = Pattern.compile("(\\p{Alpha}){4}\\p{Blank}(\\d){10}\\p{Blank}(\\d){2}(\\p{Alpha}){3}\\p{Blank}(\\p{Alpha}){2}(\\p{Print})*");
	
		Matcher m = p.matcher(linea.trim());
		boolean b = m.find();
		if (!b) return false;
		closePendingDetail();
		
		actual_acumAerTotal = 0;
		actual_acumOpTotal = 0;		

		startNewDetail();

		String boleto = "";
		int c=0;
		
	  impuesto = new BizColImpuesto();
	  impuesto2 = new BizColImpuesto();
	  boolean tarjeta =false;
	  boolean ex =false;
	  boolean salto=false;
	  boolean shortest=false;
	  
	  //TKTT 5244841278 16JUL CC FVVV ? 0 ? 487,700 CA  ? 1.00  ? 4,877  ? 42,590  ?  0  ? 927 -536  ?  0  ?  0 -5,268
	  //TKTT 5244841283 16JUL CA FFVV ?     482,800  ? 0 ? 1.00 ? 4,828  ? 59,940  ?  0  ? 917 -531  ?  0 ? 0 ? 537,526
	  //   0          1     2  3    4 5 6 7       8  9 10   11 12    13 14     15 16 17 18  19   20 21 22 23 24     25
	  String linea2=linea.replaceAll("  "," ? ");
		StringTokenizer toks = new StringTokenizer(linea2," ");
		String nextToken =null;
		boolean first=true;
		boolean exit=false;
		while (!exit) {
			String sCont;
			if (first) {
				sCont = toks.nextToken();
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else 
					nextToken=null;
				first=false;
			} else {
				sCont=nextToken;
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else 
					nextToken=null;
								
			}
			if (nextToken==null)
				exit=true;
			
			if (shortest) break;
			
			if (c==0) {
				if (sCont.equals("TKTT"))
					detalle.setOperacion("ETR");
				else if (sCont.equals("EMDS"))
					detalle.setOperacion("EMD");
				else if (sCont.equals("CANX")) {
					detalle.setOperacion("CANX");
					shortest=true;
				}
				else if (sCont.equals("RFND")) {
					detalle.setAnulado(true);
					detalle.setOperacion("RFND");
				}
				else if (sCont.equals("EMDA"))
					detalle.setOperacion("EMD");
				else if (sCont.equals("CANN")) {
					detalle.setOperacion("CANN");
					shortest=true;
				}				else
					detalle.setOperacion(sCont);
				
			}
			if (c==1) {
				boleto = sCont;
				if (sCont.equals("5244841283"))
					PssLogger.logInfo("");
				detalle.setBoleto(sCont);

			}

			if (c==2) {
		  	detalle.setFecha(getFechaDDMMMSinSep(sCont));
			}
			if (c==3) {
				if (sCont.equals("CC")) tarjeta=true;
				if (sCont.equals("CA")) tarjeta=false;
				if (sCont.equals("EX")) ex=true;
		}

			if (c==4) {
			}			
			if (c==5) {

			}
			if (c==6) {
				detalle.setContado(getDouble(sCont));
			}
			if (c==7) {
			}

			if (c==8) {
				detalle.setTarjeta(getDouble(sCont));
			}			
			if (c==9) {
				if (sCont.equals("?")) {
				} else {
				detalle.setTipoTarjeta(sCont);
				}
				}		
			if (c==10) {
				if (getDouble(sCont)<5) {
					c=11;
				}
			}
			if (c==11) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComisionPorc(getDouble(sCont));
				}
			}
			if (c==12) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComision(getDouble(sCont));
					
				}

			}
			
		
			if (c==13) {
				if (!salto) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComision(getDouble(sCont));
				}
				}
			}
			if (c==14) {
			}
			if (c==15) {
				if (sCont.equals("?")) {
				} else {
					detalle.setImpuestoAcum(getDouble(sCont));
					detalle.setImpuesto1(getDouble(sCont));
				}
			}
			if (c==16) {

			}
			if (c==17) {
			}
			if (c==18) {

			}
			if (c==19) {
				if (sCont.equals("?")) {
				} else {
					detalle.setImpSobrecom(getDouble(sCont));
				}
			}
			if (c==20) {
			}
			if (c==21) {
			}
			if (c==22) {
			}
			if (c==23) {
			}
			if (c==24) {
			}
			if (c==25) {
				if (sCont.equals("?")) {
				} else {
					detalle.setTotal(getDouble(sCont));
				}
			}			
			c++;
		}
		readyTax();
	
		return b;
	}

	public void startNewDetail() throws Exception {
		pendingDetail = true;
		impuestos_contado = 0;
		impuestos_tarjeta = 0;
		impuestos = new ArrayList<BizColImpuesto>();
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
//			if (actual_operacion.indexOf("TICKET")!=-1 && actual_aerolinea.indexOf("IATA")==-1) {
				readyDetail();
				for (BizColImpuesto impuesto : impuestos)
					finder.detectTax(impuesto);
//			}
		}
		pendingDetail = false;
	}



	private void readyTax() throws Exception {
		impuesto = new BizColImpuesto();
		impuesto.setCompany(company);
		impuesto.setOwner(owner);
		impuesto.setIdpdf(idPdf);
		impuesto.setLinea(nextLinea);
		impuesto.setContado(detalle.getContado() == 0 ? 0 : detalle.getImpuesto1());
		impuesto.setTarjeta(detalle.getContado() != 0 ? 0 : detalle.getImpuesto1());
		impuesto.setIso("IMPUESTOS");
		impuestos_contado += impuesto.getContado();
		impuestos_tarjeta += impuesto.getTarjeta();
		impuestos.add(impuesto);


	}

	private void readyDetail() throws Exception {
		detalle.setCompany(company);
		detalle.setOwner(owner);
		detalle.setIdpdf(idPdf);
		detalle.setLinea(nextLinea);
		detalle.setAerolinea(actual_aerolinea);
		detalle.setIdAerolinea(actual_idAerolinea);
//		detalle.setOperacion(actual_operacion);
		detalle.setTipoRuta(actual_tipoRuta);
		detalle.setTarifa(detalle.getContado() + detalle.getTarjeta());
		detalle.setBaseImponible(detalle.getTarifa() + qn);
		detalle.setContadoBruto(detalle.getContado() + impuestos_contado);
		detalle.setTarjetaBruto(detalle.getTarjeta() + impuestos_tarjeta);
		detalle.setImpuestoAcum(detalle.getImpuesto1() + detalle.getImpuesto2());

		if (!detalle.hasAnulado()) detalle.setAnulado(false);
		if (detalle.getAnulado())	detalle.setOperacion("REFUND");
		finder.detectDetail(detalle);
		actual_acumOpTotal += detalle.getTotal();
		actual_acumAerTotal += detalle.getTotal();
		detalle = new BizColDetalle();
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0) return 0;
		String num = linea.replaceAll("\\,", "");
		try {
			boolean neg = linea.indexOf("-") != -1;
			num = JTools.getNumberEmbeddedWithSep(num);
			return (neg ? -1 : 1) * Double.parseDouble(num);
		} catch (Exception e) {
		  PssLogger.logError("Parser Error "+num+" en "+linea);
			throw e;
		}
	}

	public boolean detectTotal(String linea) throws Exception {
		boolean b = linea.startsWith("*TOTAL");

		if (!b) return false;
		closePendingDetail();
		detectSeccion=true;

		String sOp = linea.substring(4, 25);
		if (/*actual_aerolinea.trim().indexOf("IATA")!=-1 ||actual_operacion.trim().indexOf("TICKET")==-1 || */
				!(sOp.trim().equals(actual_operacion) || sOp.trim().equals(actual_aerolinea))) return true;
		String sTotal = linea.substring(133, 143);
		if (sTotal.trim().length() == 0) return true;
		double total = getDouble(sTotal);

		if (sOp.trim().equals(actual_operacion)) {
			if (Math.abs(total - actual_acumOpTotal) > 0.01) 
				throw new Exception("Error Totalizador [" + actual_aerolinea + "] Op: [" + actual_operacion + "] doc:" + total + " calc:" + actual_acumOpTotal);

		} else if (sOp.trim().equals(actual_aerolinea)) {
			if (Math.abs(total - actual_acumAerTotal) > 0.01) 
				throw new Exception("Error Totalizador: [" + actual_aerolinea + "] doc:" + total + " calc:" + actual_acumAerTotal);
		}

		return b;
	}

	public void execute() throws Exception {
		cabecera = new BizColCabecera();
		detalle = new BizColDetalle();
		impuesto = new BizColImpuesto();
		try {
			finder.start();
			privExecute();
			finder.stop();
		} catch (Exception e) {
			finder.error(e);
		}
	}
	String filename;
	public void setFilename(String input) throws Exception {
		this.filename = input;
	}

	public void setInput(InputStream input) throws Exception {
		this.input = input;
	}

	public void setTypeFile(String zTypeFile) throws Exception {
		// no es de interes para este parseador
	}

	public String getFormat(String zFormat) throws Exception {
		return null; // no es de interes para este parseador
	}

	public void setFormat(String zFormat) throws Exception {
		// no es de interes para este parseador
	}

	public void setFechaDesde(Date zFecha) throws Exception {
		// no es de interes para este parseador
	}

	public void setFechaHasta(Date zFecha) throws Exception {
		// no es de interes para este parseador
	}

	public String getFormat() throws Exception {
		// no es de interes para este parseador
		return null;
	}

	public Date getPeriodoDesde() throws Exception {
		return fechaDesde;
	}

	public Date getPeriodoHasta() throws Exception {
		return fechaHasta;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HEADER
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void detectId(String linea) throws Exception {
		int start;//Billing Period:220703(16-JUL-2022 to 23-JUL-2022) REFERENCE: 76526741 - 220703

		
		if ((start = linea.indexOf("REFERENCE: ")) == -1) return;
		detectPeriodo(linea);
		String id = linea.substring(start + ("REFERENCE: ").length(),start + ("REFERENCE: ").length()+17);
		//if (id.length() > 20) return;
		int pos=0;
		String extraID="";
		if ((pos=filename.lastIndexOf("\\"))!=-1) {
			extraID=filename.substring(pos+1);
			if (extraID.length()>10)
				extraID=extraID.substring(0, 10);
		}
		String s=JTools.replace(filename, "/", "");
		s=JTools.replace(s, ":", "");
		s=JTools.replace(s, "\\", "");
		s=JTools.replace(s, ".", "");
	  idPdf = extraID+JTools.getNumberEmbedded(id+s);
	  if (idPdf.length()>49)
  	idPdf=idPdf.substring(0,49);
		cabecera.setIdpdf(idPdf);
		readyHeader();
	}

	private void detectHeader(String linea,boolean detect) throws Exception {
			detectHeaderSingle(linea,detect);
	}
	private void detectHeaderSingle(String linea, boolean detect) throws Exception {
		// QO 31,10

		if (!detect) {
			
			boolean b = linea.indexOf("CODIGOS") != -1;

			if (b) 
				detectHeader2=true;
			else
				detectHeader2=false;
			
			return;
		}
		if (linea.indexOf("NIT")==-1 ||linea.indexOf(" ")==-1 ) return;
		cabecera.setIata(linea.substring(0,linea.indexOf(" ")));
		cabecera.setDescripcion(linea.substring(linea.indexOf(" "),linea.indexOf("NIT")));
		cabecera.setCif(linea.substring(linea.indexOf("NIT")));
//		int[] columnas = { 1, 12, 57, 103,142 };
//		for (int c = 0; c < columnas.length; c++) {
//			String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
//			switch (c) {
//			case 0:
//				iata = JTools.removeSpaces(sCont);
//				cabecera.setIata(iata);
//				break;// iso impuesto
//			case 1:
//				cabecera.setDescripcion(sCont);
//				break;// iso impuesto
//			case 2:
//				cabecera.setDireccion(sCont);
//				break;// iso impuesto
//			case 3:
//				cabecera.setLocalidad(sCont);
//				break;// iso impuesto
//			case 4:
//				cabecera.setCif(sCont);
//				break;// iso impuesto
//			}
//		}
		detectHeader2 = false;
		detectSeccion=true;
		readyHeader();
	}
  public  void setFechaFromPeriodo(String fecha,boolean desde) throws Exception {
  	String mes="";
  	if (fecha.indexOf("ENE")!=-1)      mes="01";
  	else if (fecha.indexOf("FEB")!=-1) mes="02";
  	else if (fecha.indexOf("MAR")!=-1) mes="03";
  	else if (fecha.indexOf("ABR")!=-1) mes="04";
  	else if (fecha.indexOf("APR")!=-1) mes="04";
  	else if (fecha.indexOf("MAY")!=-1) mes="05";
  	else if (fecha.indexOf("JUN")!=-1) mes="06";
  	else if (fecha.indexOf("JUL")!=-1) mes="07";
  	else if (fecha.indexOf("AGO")!=-1) mes="08";
  	else if (fecha.indexOf("AUG")!=-1) mes="08";
  	else if (fecha.indexOf("SEP")!=-1) mes="09";
  	else if (fecha.indexOf("OCT")!=-1) mes="10";
  	else if (fecha.indexOf("NOV")!=-1) mes="11";
   	else if (fecha.indexOf("DIC")!=-1) mes="12";
   	else if (fecha.indexOf("DEC")!=-1) mes="12";
     	else return ;
  	int posDia = fecha.indexOf("-");
  	if (posDia==-1) return ;
  	int posAnio = fecha.indexOf("-",posDia+1);
  	if (posAnio==-1) return ;
  	String sem = JTools.LPad(fecha.substring(0,posDia),2,"0");
  	String anio = fecha.substring(posAnio+1);
  	
  	Calendar c= Calendar.getInstance();
  	c.set(Calendar.YEAR, Integer.parseInt(anio));
   	c.set(Calendar.MONTH, Integer.parseInt(mes)-1);
   	c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sem)-1);
   	c.set(Calendar.HOUR,0);
   	c.set(Calendar.MINUTE,0);
   	c.set(Calendar.SECOND,0);
   	c.set(Calendar.MILLISECOND,0);
    if (desde) 
    	fechaDesde=c.getTime();
    else
    	fechaHasta=c.getTime();
  }
  public static String convertFecha(String fecha) throws Exception {
  	String mes="";
  	if (fecha.indexOf("ENE")!=-1)      mes="01";
  	else if (fecha.indexOf("FEB")!=-1) mes="02";
  	else if (fecha.indexOf("MAR")!=-1) mes="03";
  	else if (fecha.indexOf("ABR")!=-1) mes="04";
  	else if (fecha.indexOf("MAY")!=-1) mes="05";
  	else if (fecha.indexOf("JUN")!=-1) mes="06";
  	else if (fecha.indexOf("JUL")!=-1) mes="07";
  	else if (fecha.indexOf("AGO")!=-1) mes="08";
  	else if (fecha.indexOf("SEP")!=-1) mes="09";
  	else if (fecha.indexOf("OCT")!=-1) mes="10";
  	else if (fecha.indexOf("NOV")!=-1) mes="11";
  	else if (fecha.indexOf("DIC")!=-1) mes="12";
  	else if (fecha.indexOf("DEC")!=-1) mes="12";
  	else return "";
  	int posDia = fecha.indexOf("/");
  	if (posDia==-1) return "";
  	int posAnio = fecha.indexOf("/",posDia+1);
  	if (posAnio==-1) return "";
  	String dia = JTools.LPad(fecha.substring(posDia+1, posAnio),2,"0");
  	String anio = fecha.substring(posAnio+1);
  	
  	
  	return dia+"/"+mes+"/"+anio;
  }
	private void detectPeriodo(String linea) throws Exception {
	//Billing Period:220703(16-JUL-2022 to 23-JUL-2022) REFERENCE: 76526741 - 220703

		int start, s1, s2, s3;
		if ((start = linea.indexOf("Billing Period:")) == -1) return;
		if ((s1 = linea.indexOf("(", start)) == -1) return;
		if ((s2 = linea.indexOf(" to ", start)) == -1) return;
		if ((s3 = linea.indexOf(")", start)) == -1) return;

		setFechaFromPeriodo(linea.substring(s1+1,s2),true);		
		setFechaFromPeriodo(linea.substring(s2+4,s3),false);		
		detectPeriodo=true;
	}



	private void readyHeader() throws Exception {
		if (saveHeader) return;
		if (!detectPeriodo && idPdf != null) return;

		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		finder.detectHeader(cabecera);
		saveHeader=true;
	}

	private Date getFecha(String linea) throws Exception {
		int start, s1, s2, s3, s4, s5, s6, s7;
		if ((start = linea.indexOf("/")) == -1) throw new Exception("Fecha invalina: " + linea);
		if ((s1 = linea.indexOf("/", start + 1)) == -1) throw new Exception("Fecha invalina: " + linea);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
		cal.set(Calendar.MONTH, Integer.parseInt(linea.substring(start + 1, s1).trim()) - 1);
		int anio = Integer.parseInt(linea.substring(s1 + 1).trim());
		cal.set(Calendar.YEAR, anio < 80 ? 2000 + anio : 1900 + anio); // en el 2080 que me vengan a buscar
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private Date getFechaDDMM(String linea) throws Exception {
		int start;
		if ((start = linea.indexOf("/")) == -1) throw new Exception("Fecha invalida: " + linea);
		GregorianCalendar cal = new GregorianCalendar();

		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));

		int mes = Integer.parseInt(linea.substring(start + 1).trim());
		cal.set(Calendar.MONTH, mes - 1);

		GregorianCalendar fd = new GregorianCalendar();
		GregorianCalendar fh = new GregorianCalendar();
		fd.setTime(fechaDesde);
		fh.setTime(fechaHasta);
		int anioDesde = fd.get(Calendar.YEAR);
		int anioHasta = fh.get(Calendar.YEAR);
		if (anioDesde != anioHasta && mes < 6) anioDesde = anioHasta;
		cal.set(Calendar.YEAR, anioDesde);

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public String getIATA() throws Exception {
		return iata;
	}

	public static void main(String[] args) {
		JParseoCol parser = new JParseoCol();
		IFinderCol finder = new IFinderCol() {

			public void detectDetail(BizColDetalle detail) throws Exception {
				System.out.print("ID: " + detail.getIdpdf());
				System.out.print("| Tipo Ruta: " + detail.getTipoRuta());
				System.out.print("| Aerolinea: " + detail.getAerolinea());
				System.out.print("| Operacion: " + detail.getOperacion());
				System.out.print("| Total: " + detail.getTotal());
				System.out.print("| Tarifa: " + detail.getTarifa());
				System.out.print("| Contado: " + detail.getContado());
				System.out.print("| Tarjeta: " + detail.getTarjeta());
				System.out.print("| Boleto: " + detail.getBoleto());
				System.out.print("| Base Imponible: " + detail.getBaseImponible());
				System.out.print("| Impuesto1: " + detail.getImpuesto1());
				System.out.print("| Impuesto2: " + detail.getImpuesto2());
				System.out.print("| Comision: " + detail.getComision());
				System.out.print("| Over: " + detail.getComisionOver());
				System.out.print("| Imp.s/com: " + detail.getImpSobrecom());
				System.out.print("| Tipo Tarjeta: " + detail.getTipoTarjeta());
				System.out.println("| Numero Tarjeta: " + detail.getNumeroTarjeta());

			}

			public void detectHeader(BizColCabecera header) throws Exception {
				System.out.print("ID: " + header.getIdpdf());
				System.out.print("| IATA: " + header.getIata());
				System.out.print("| Direccion: " + header.getDireccion());
				System.out.print("| CodPostal: " + header.getCodigoPostal());
				System.out.print("| Moneda: " + header.getMoneda());
				System.out.print("| Localidad: " + header.getLocalidad());
				System.out.print("| CIF: " + header.getCif());
				System.out.println("| Descripcion: " + header.getDescripcion());

			}

			public void error(Exception e) throws Exception {
				System.out.println("Error" + e.getMessage());
				e.printStackTrace();

			}

			public void start() throws Exception {
				System.out.println("inicio");

			}

			public void stop() throws Exception {
				System.out.println("fin");

			}

			public void detectTax(BizColImpuesto tax) throws Exception {
				System.out.print("TAX " + tax.getIdpdf());
				System.out.print("| ISO: " + tax.getIso());
				System.out.print("| Contado: " + tax.getContado());
				System.out.println("| Credito: " + tax.getTarjeta());
			}

		};
		try {
			parser.setInput(new FileInputStream("c:\\a3.pdf"));
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

}
