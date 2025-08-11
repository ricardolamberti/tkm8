package  pss.bsp.pdf.pan;

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
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.pan.cabecera.BizPanCabecera;
import pss.bsp.pdf.pan.detalle.BizPanDetalle;
import pss.bsp.pdf.pan.impuesto.BizPanImpuesto;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.core.tools.PssLogger;
import pss.tourism.carrier.BizCarrier;

public class JParseoPan implements IParseo {

	IFinderPan finder;

	InputStream input;

	String idPdf;

	String iata = "0";

	Date fechaDesde;

	Date fechaHasta;

	boolean detectHeader = true;

	boolean detectMoneda = true;

	boolean detectPeriodo = true;

	boolean detectSeccion = true;

	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}
	public String getTableDetalle() throws Exception {
		return new BizPanDetalle().GetTable();
	}
	BizPanCabecera cabecera;

	BizPanDetalle detalle;

	boolean finalizar=false;
	BizPanImpuesto impuesto = null;
	BizPanImpuesto impuesto2 = null;
	BizPanImpuesto impuesto3 = null;
	
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

	double contado = 0;

	double tarjeta = 0;

	double total = 0;

	String company;

	String owner;

	String idParser;

	boolean pendingDetail = false;
	boolean pendingMulti = false;
	int lineaMulti=0;
	List<BizPanImpuesto> impuestos = null;

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
		this.finder = (IFinderPan) finder;
	}

	private String getContent() {
		PDFTextParser parser = new PDFTextParser();
		return parser.pdftoText(input,false,true);
	}

	protected void privExecute() throws Exception {

		String content = getContent();
		//JTools.writeStringToFile(content, "/production/a.txt");

		if (content == null) throw new Exception("El archivo no parece ser una interfaz BSP");
		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for (String linea : content.split("\r\n|\r|\n")) {
			parserLinea(linea);
			if (finalizar)
				break;
		}
		closePendingDetail();
	}

	protected void parserLinea(String linea) throws Exception {
		if (idPdf == null) detectId(linea);
		System.out.println("["+linea+"]");
		if (detectHeader) detectHeader(linea);
		if (detectMoneda) detectMoneda(linea);
		if (detectPeriodo) detectPeriodo(linea);
		if (detectSeccion) detectSeccion(linea);
		if (detectTotal(linea)) return;
		if (detectStuff(linea)) return;
		if (detectOperacion(linea)) return; // esta es inclusiva
		if (detectLinea(linea)) return;
		if (detectTax(linea)) return;
	}

	private void detectSeccion(String linea) throws Exception {
		if (linea.indexOf("CATEGORY BSP") == -1) return;
		detectSeccion = false;
	}

	private boolean detectStuff(String linea) throws Exception {
		if (detectSeccion) return true;
		if (linea.startsWith("|")) return true;// ojo
		if (linea.startsWith("-")) return true;
		if (linea.indexOf("XXXXXXXXXX") != -1) return true;
		if (linea.indexOf("====") != -1) return true;
		if (linea.indexOf(" Page") != -1) return true;
		if (linea.indexOf("AGENT BILLING DETAILS") != -1) return true;
		if (linea.equals("CIA TRNC STAT FOP IMP PEN % VALOR")) return true;
		if (linea.equals("---COMISION SUPP---VALOR NETO A")) return true;
		if (linea.equals("PAGAR")) return true;
		if (linea.equals("FECHA DE ---COMISION STD---")) return true;
		if (linea.equals("FECHA DE ---COMISION STD---")) return true;
		if (linea.equals("% VALOR S/ COMISION")) return true;
		if (linea.equals("CODIGO")) return true;
		if (linea.equals("NR")) return true;
		if (linea.equals("NO.  DE")) return true;
		if (linea.equals("DOCUMENTO CPN")) return true;
		if (linea.equals("VALOR")) return true;
		if (linea.equals("EMISION")) return true;
		if (linea.equals("IMP, TASAS & CARGOS")) return true;
		if (linea.equals("T&C")) return true;
		if (linea.equals("IMPUESTO")) return true;
		if (linea.equals("TARIFATRANSACCION COBL")) return true;
		if (linea.trim().equals("")) return true;
			return false;
	}

	public void detectTipoRuta(String linea) throws Exception {
		// | R2139 01/03/07 |
		Pattern p = Pattern.compile("\\|\\p{Blank}\\p{Alpha}\\d\\d\\d\\d\\p{Blank}\\p{Blank}\\d\\d\\/\\d\\d\\/\\d\\d\\p{Blank}\\|");
		// Pattern p = Pattern.compile("\\.*");
		Matcher m = p.matcher(linea);
		boolean b = m.find();

		if (b) {
			String ruta = linea.substring(m.start()+20, linea.indexOf("        ", m.start())).trim();
			if (ruta.length() > 0) actual_tipoRuta = ruta;

		}
	}


	public boolean detectOperacion(String linea) throws Exception {
		// E TICKET
		String ss = linea.trim();
		if (ss.equals("DOMESTICO"))
			detalle.setTipoRuta(ss);

		Pattern p = Pattern.compile("(\\p{Space}){7}\\p{Alpha}(\\p{Print})++(\\p{Blank})*");
		Matcher m = p.matcher(linea);
		boolean b = m.matches();

		if (!b)
			return false;
		String operacion = linea.trim();
		actual_operacion = operacion;
		actual_acumOpTotal = 0;
		return b;

	}

	public String findAerolinea(String id) throws Exception {
		BizCarrier c = BizCarrier.findCarrierByCod(id);
		if (c==null) return null;
		return c.getDescription();
	}

	public boolean detectLinea(String linea) throws Exception {
		if (linea.indexOf("+RTDN")!=-1)
			return false;
		Pattern p = Pattern.compile("(\\d){3}\\p{Blank}(\\p{Alpha}){4}\\p{Blank}(\\d){10}\\p{Blank}(\\d){2}(\\p{Alpha}){3}(\\d){2}\\p{Blank}(\\p{Alpha}){4}(\\p{Print})*");
	
		Matcher m = p.matcher(linea);
		boolean b = m.find();
		if (!b) return false;
		closePendingDetail();
		
		actual_acumAerTotal = 0;
		actual_acumOpTotal = 0;		

		startNewDetail();

		String boleto = "";
		int c=0;
		
	  impuesto = new BizPanImpuesto();
	  impuesto2 = new BizPanImpuesto();
	  impuesto3 = new BizPanImpuesto();
	  boolean tarjeta =false;
	  boolean ex =false;
	  boolean shortest=false;
	  
		//782 TKTT 3127588397 25FEB19 FFVV I CA  374.31  53.56 44.40 AJ 175.00 YQ  50.35 6.00 3.21 0.00  0.00  371.10
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
				actual_idAerolinea = sCont;
				actual_aerolinea=findAerolinea(actual_idAerolinea);
			}
			if (c==1) {
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
			if (c==2) {
				boleto = sCont;
				if (sCont.equals("3417550742"))
					PssLogger.logInfo("");
				detalle.setBoleto(sCont);
				detalle.setAerolineaBoleto(this.actual_idAerolinea + " " + sCont);
				detalle.setBoletoLargo(boleto);
			}
			if (c==3) {
		  	detalle.setFecha(getFechaDDMMMAASinSep(sCont));
			}
			if (c==4) {
			}			
			if (c==5) {
				c++;
				if (sCont.indexOf("NR:")!=-1) {
					continue;
				} 
			}
			if (c==6) {
				if (sCont.equals("I"))
					detalle.setTipoRuta("I");
				else if (sCont.equals("D"))
					detalle.setTipoRuta("D");
			}
			if (c==7) {
					if (sCont.equals("CC")) tarjeta=true;
					if (sCont.equals("CA")) tarjeta=false;
					if (sCont.equals("EX")) ex=true;
			}
			if (c==8) {
			}			
			if (c==9) {
				if(sCont.trim().equals("?")) {
					PssLogger.logInfo(sCont);
					shortest=true;
				} else {
//				detalle.setTotal(getDouble(sCont));
				}
			}		
			if (c==10) {
			}
			if (c==11) {
				if (tarjeta)
					detalle.setTarjeta(getDouble(sCont));
		  	else
					detalle.setContado(getDouble(sCont));
			}
			if (c==12) {
				if (sCont.equals("?")) {
					c+=2;
					continue;
				} else {
					if (nextToken.length()!=2){
						c+=2;
					} else {
					impuesto.setTarjeta(0);
					impuesto.setContado(0);
			  	if (tarjeta)
				  	impuesto.setTarjeta(getDouble(sCont));
			  	else
			  		impuesto.setContado(getDouble(sCont));
					}
				}
			}
			if (c==13) {
				if (sCont.equals("")){
					c++;
					continue;
				}
				else	
					impuesto.setIso(sCont);
			}
			if (c==14) {
				if (sCont.equals("?")) {
					c+=2;
					continue;
				} else {
					if (nextToken.length()!=2){
						c+=2;
						
					} else{ 
						impuesto2.setTarjeta(0);
						impuesto2.setContado(0);
				  	if (tarjeta)
					  	impuesto2.setTarjeta(getDouble(sCont));
				  	else
				  		impuesto2.setContado(getDouble(sCont));
					}
				}
			}
			if (c==15) {
				impuesto2.setIso(sCont);
			}
			if (c==16) {
				if (sCont.equals("?")) {
					c+=2;
					continue;
				} else {
					if (nextToken.length()!=2){
						c+=2;
						
					} else{ 
						impuesto3.setTarjeta(0);
						impuesto3.setContado(0);
				  	if (tarjeta)
					  	impuesto3.setTarjeta(getDouble(sCont));
				  	else
				  		impuesto3.setContado(getDouble(sCont));
					}
				}
			}
			if (c==17) {
				impuesto3.setIso(sCont);
			}
			if (c==18) {
				if (sCont.equals("?")) {
				} else {
					detalle.setTarifaSinComision(getDouble(sCont));
				}
			}
			if (c==19) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComisionPorc(getDouble(sCont));
				}
			}
			if (c==20) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComision(getDouble(sCont));
				}
			}
			if (c==21) {
				if (sCont.equals("?")) {
				} else {
				//	detalle.setComisionOver(getDouble(sCont));
				}
			}
			if (c==22) {
				if (sCont.equals("?")) {
				} else {
					detalle.setComisionOver(getDouble(sCont));
				}
			}
			if (c==23) {
				if (sCont.equals("?")) {
				} else {
					detalle.setImpSobrecom(getDouble(sCont));
				}
			}
			if (c==24) {
				if (sCont.equals("?")) {
				} else {
					detalle.setTotal(getDouble(sCont));
				}
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
		contado=0;
		tarjeta=0;
		pendingMulti=false;
		impuestos_contado = 0;
		impuestos_tarjeta = 0;
		impuesto =null;
		impuesto2 =null;
		impuesto3 =null;
		impuestos = new ArrayList<BizPanImpuesto>();
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
			readyTax();
			readyDetail();
			for (BizPanImpuesto impuesto : impuestos)
				finder.detectTax(impuesto);
		}
		pendingDetail = false;
	}

	public boolean detectTax(String linea) throws Exception {
		// QO 31,10
		Pattern p = Pattern.compile("(((\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2})|((\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2}\\p{Blank}(\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2})))))(\\p{Print})*");
		Matcher m = p.matcher(linea);
		boolean b = m.matches();
		if (!b) return false;
	  impuesto = new BizPanImpuesto();
	  impuesto2 = new BizPanImpuesto();
	  impuesto3 = new BizPanImpuesto();
	  int c=0;
		StringTokenizer toks = new StringTokenizer(linea.replaceAll("  "," ? ")," ");
		while (toks.hasMoreElements()) {
			String sCont = toks.nextToken();
			switch (c) {
			case 0:
				impuesto.setTarjeta(0);
				impuesto.setContado(0);
				if (detalle.getTarjeta()>0)
					impuesto.setTarjeta(getDouble(sCont));
				else
					impuesto.setContado(getDouble(sCont));
				break;// impuesto contado
			case 1:
				impuesto.setIso(sCont);
				break;// iso impuesto
			case 2:
				impuesto2.setTarjeta(0);
				impuesto2.setContado(0);
				if (detalle.getTarjeta()>0)
					impuesto2.setTarjeta(getDouble(sCont));
				else
					impuesto2.setContado(getDouble(sCont));
				break;// iso impuesto
			case 3:
				impuesto2.setIso(sCont);
				break;// impuesto credito
			}
			c++;
		}
		readyTax();
		return b;
	}

	private void readyTax() throws Exception {
			if (detalle.getOperacion().equals("CANN")||detalle.getOperacion().equals("CANX")) {
				impuesto2=null;
				impuesto=null;
				impuesto3=null;
					return;
			}
			if (impuesto!=null && (!impuesto.getIso().equals(""))) {
				impuesto.setCompany(company);
				impuesto.setOwner(owner);
				impuesto.setIdpdf(idPdf);
				impuesto.setLinea(nextLinea);
				impuestos_contado += impuesto.getContado();
				impuestos_tarjeta += impuesto.getTarjeta();
				impuestos.add(impuesto);
				impuesto=null;
			}

			if (impuesto2!=null && (!impuesto2.getIso().equals(""))) {
				impuesto2.setCompany(company);
				impuesto2.setOwner(owner);
				impuesto2.setIdpdf(idPdf);
				impuesto2.setLinea(nextLinea);
				impuestos_contado += impuesto2.getContado();
				impuestos_tarjeta += impuesto2.getTarjeta();
				impuestos.add(impuesto2);
				impuesto2=null;
			}
			if (impuesto3!=null && (!impuesto3.getIso().equals(""))) {
				impuesto3.setCompany(company);
				impuesto3.setOwner(owner);
				impuesto3.setIdpdf(idPdf);
				impuesto3.setLinea(nextLinea);
				impuestos_contado += impuesto3.getContado();
				impuestos_tarjeta += impuesto3.getTarjeta();
				impuestos.add(impuesto3);
				impuesto3=null;
			}
	}


	private void readyDetail() throws Exception {
		if (detalle.getOperacion().equals("CANN")||detalle.getOperacion().equals("CANX")) {
			detalle = new BizPanDetalle();
			return;
		}
		detalle.setCompany(company);
		detalle.setOwner(owner);
		detalle.setIdpdf(idPdf);
		detalle.setLinea(nextLinea);
		detalle.setAerolinea(actual_aerolinea);
		detalle.setIdAerolinea(actual_idAerolinea);
//		detalle.setOperacion(actual_operacion);
//		detalle.setTipoRuta(actual_tipoRuta);
		detalle.setTarifa(detalle.getContado() + detalle.getTarjeta());
		detalle.setBaseImponible(detalle.getTarifa() + qn);
		detalle.setImpuesto1(this.impuestos_contado);
		detalle.setImpuesto2(this.impuestos_tarjeta);
		detalle.setContadoBruto(detalle.getContado() + impuestos_contado);
		detalle.setTarjetaBruto(detalle.getTarjeta() + impuestos_tarjeta);
		if (!detalle.hasAnulado()) detalle.setAnulado(false);
//		double suma = JTools.rd(detalle.getContadoBruto() + detalle.getTotalComision());
//		if (Math.abs(suma) < Math.abs(detalle.getTotal())) detalle.setContadoBruto(detalle.getContadoBruto() + detalle.getIva());
//		else detalle.setTarjetaBruto(detalle.getTarjetaBruto() + detalle.getIva());
		detalle.setImpuestoAcum(impuestos_contado + impuestos_tarjeta );
		if (detalle.getAnulado())
			detalle.setOperacion("REFUND");
		finder.detectDetail(detalle);
		actual_acumOpTotal += detalle.getTotal();
		actual_acumAerTotal += detalle.getTotal();
		detalle = new BizPanDetalle();
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0) return 0;
		String num =  JTools.getNumberEmbeddedWithSep(linea);
		boolean neg = linea.indexOf("-") != -1;
		return (neg ? -1 : 1) * Double.parseDouble(JTools.replace(num,",",""));
	}

	public boolean detectTotal(String linea) throws Exception {
		boolean b = linea.indexOf("ISSUES TOTAL")!=-1;

		if (!b) return false;
		closePendingDetail();
		finalizar=true;
/*
		String sOp = linea.substring(4, 25);
		if (!(sOp.trim().equals(actual_operacion) || sOp.trim().equals(actual_aerolinea))) return true;
		String sTotal = linea.substring(133, 143);
		if (sTotal.trim().length() == 0) return true;
		double total = getDouble(sTotal);

		if (sOp.trim().equals(actual_operacion)) {
			if (Math.abs(total - actual_acumOpTotal) > 0.01) throw new Exception("Error Totalizador [" + actual_aerolinea + "] Op: [" + actual_operacion + "] doc:" + total + " calc:" + actual_acumOpTotal);

		} else if (sOp.trim().equals(actual_aerolinea)) {
			if (Math.abs(total - actual_acumAerTotal) > 0.01) throw new Exception("Error Totalizador: [" + actual_aerolinea + "] doc:" + total + " calc:" + actual_acumAerTotal);
		}
*/
		return b;
	}

	public void execute() throws Exception {
		cabecera = new BizPanCabecera();
		detalle = new BizPanDetalle();

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
		int start;
		if ((start = linea.indexOf("REFERENCE:")) == -1) return;
		String id = linea.substring(start + 10);
		if (id.length() > 20) return;
		idPdf = JTools.getNumberEmbedded(id);
		cabecera.setIdpdf(idPdf);
		readyHeader();
	}

	private void detectHeader(String linea) throws Exception {
		// QO 31,10
		boolean b;

		b = linea.indexOf("REFERENCE:") != -1;
		if (b) {
		    int pos =linea.indexOf("REFERENCE:")+10;
				iata = linea.substring(pos,pos+9).trim();
				cabecera.setIata(iata.trim());
				detectHeader = false;
			readyHeader();
		}
		b = linea.indexOf("AGENTE ") != -1;
		if (b) {
			int a = linea.indexOf("  ");
			String sCont = linea.substring(7,a); 
			cabecera.setDescripcion(sCont);
		}

	}

	private void detectPeriodo(String linea) throws Exception {
		// 23-FEB-2019 to 28-FEB-2019
		boolean b = linea.indexOf(" to ")!=-1&&linea.indexOf("-")!=-1;

		if (!b) return;
		int start, s1, s2;
		if ((start = linea.indexOf(" to ")) == -1) return;
		if ((s1 = linea.indexOf("-", start)) == -1) return;

		fechaDesde = getFechaDDMMMAA(linea.substring(start-11, start).trim());
		fechaHasta = getFechaDDMMMAA(linea.substring(start+4,start+15).trim());
		cabecera.setPeriodoDesde(fechaDesde);
		cabecera.setPeriodoHasta(fechaHasta);

		detectPeriodo = false;
		readyHeader();
		
	}
	public void setFechaDesde(Date zFecha) throws Exception {

	}

	public void setFechaHasta(Date zFecha) throws Exception {
		
	}
	private void detectMoneda(String linea) throws Exception {
//		int start, s1;
//		if ((start = linea.indexOf("MONEDA ")) == -1) return;
//		if ((s1 = linea.indexOf("    ", start)) == -1) return;

		cabecera.setMoneda("USD");

		detectMoneda = false;
		readyHeader();
	}

	private void readyHeader() throws Exception {
		if (!(!detectHeader && !detectMoneda && !detectPeriodo && idPdf != null)) return;

		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		finder.detectHeader(cabecera);
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
	 public  boolean isFechaDDMMMAA(String linea) throws Exception {
		 if (linea.length()<7) return false;
		 String fecha = JTools.clearNumbers(linea);
			if (fecha.indexOf("ENE")!=-1)      return true;
	  	else if (fecha.indexOf("FEB")!=-1) return true;
	  	else if (fecha.indexOf("MAR")!=-1) return true;
	  	else if (fecha.indexOf("ABR")!=-1) return true;
	  	else if (fecha.indexOf("APR")!=-1) return true;
	 	 else if (fecha.indexOf("MAY")!=-1) return true;
	  	else if (fecha.indexOf("JUN")!=-1) return true;
	  	else if (fecha.indexOf("JUL")!=-1) return true;
	  	else if (fecha.indexOf("AGO")!=-1) return true;
	  	else if (fecha.indexOf("AUG")!=-1) return true;
	  	else if (fecha.indexOf("SEP")!=-1) return true;
	  	else if (fecha.indexOf("OCT")!=-1) return true;
	  	else if (fecha.indexOf("NOV")!=-1) return true;
	  	else if (fecha.indexOf("DIC")!=-1) return true;
	  	else if (fecha.indexOf("DEC")!=-1) return true;
			return false;

	 }
	 public  Date getFechaDDMMMAA(String linea) throws Exception {
			int start, s1, s2, s3, s4, s5, s6, s7;
			if ((start = linea.indexOf("-")) == -1) throw new Exception("Fecha invalida: " + linea);
			if ((s1 = linea.indexOf("-", start + 1)) == -1) throw new Exception("Fecha invalida: " + linea);
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
	  	int mes=0;
	  	String fecha = linea.substring(start + 1, s1).trim();
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
			int anio = Integer.parseInt(linea.substring(s1 + 1).trim());
			cal.set(Calendar.YEAR, anio < 80 ? 2000 + anio : anio<1900? 1900 + anio:anio); // en el 2080 que me vengan a buscar
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	  }
	 public  Date getFechaDDMMMAASinSep(String linea) throws Exception {
			int start, s1, s2, s3, s4, s5, s6, s7;
			start=2;
			s1=5;
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
	  	int mes=0;
	  	String fecha = linea.substring(start , s1).trim();
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
			int anio = Integer.parseInt(linea.substring(s1 ).trim());
			cal.set(Calendar.YEAR, anio < 80 ? 2000 + anio : anio<1900? 1900 + anio:anio); // en el 2080 que me vengan a buscar
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	  }
	private Date getFechaDDMM(String linea) throws Exception {
		int start;
		int start2;
		if ((start = linea.indexOf("/")) == -1) throw new Exception("Fecha invalida: " + linea);
		if ((start2 = linea.indexOf("/",start+1)) == -1) throw new Exception("Fecha invalida: " + linea);
		GregorianCalendar cal = new GregorianCalendar();

		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));

		int mes = Integer.parseInt(linea.substring(start + 1,start2).trim());
		cal.set(Calendar.MONTH, mes - 1);

		int anio = Integer.parseInt(linea.substring(start2+1).trim());
		cal.set(Calendar.YEAR, 2000+anio);


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
		JParseoPan parser = new JParseoPan();
		IFinderPan finder = new IFinderPan() {

			public void detectDetail(BizPanDetalle detail) throws Exception {
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

			public void detectHeader(BizPanCabecera header) throws Exception {
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

			public void detectTax(BizPanImpuesto tax) throws Exception {
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
