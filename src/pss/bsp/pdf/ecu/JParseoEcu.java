package  pss.bsp.pdf.ecu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.bsp.pdf.cri.detalle.BizCRiDetalle;
import pss.bsp.pdf.ecu.cabecera.BizEcuCabecera;
import pss.bsp.pdf.ecu.detalle.BizEcuDetalle;
import pss.bsp.pdf.ecu.impuesto.BizEcuImpuesto;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.tourism.carrier.BizCarrier;

import com.ibm.icu.util.Calendar;

public class JParseoEcu implements IParseo {

	IFinderEcu finder;

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
		return new BizEcuDetalle().GetTable();
	}
	BizEcuCabecera cabecera;

	BizEcuDetalle detalle;

	BizEcuImpuesto impuesto;

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

	List<BizEcuImpuesto> impuestos = null;

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
		this.finder = (IFinderEcu) finder;
	}

	private String getContent() {
		PDFTextParser parser = new PDFTextParser();
		return parser.pdftoText(input);
	}

	protected void privExecute() throws Exception {

		String content = getContent();

		if (content == null) throw new Exception("El archivo no parece ser una interfaz BSP");
		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for (String linea : content.split("\r\n|\r|\n")) {
			parserLinea(linea);
		}
		closePendingDetail();
	}

	protected void parserLinea(String linea) throws Exception {
		if (idPdf == null) detectId(linea);
		//System.out.println(linea);
		if (detectHeader) detectHeader(linea);
		if (detectMoneda) detectMoneda(linea);
		if (detectPeriodo) detectPeriodo(linea);
		if (detectSeccion) detectSeccion(linea);
		if (linea.trim().startsWith("|")||linea.trim().startsWith("_")) detectTipoRuta(linea);
		if (detectStuff(linea)) return;
		if (detectTotal(linea)) return;
		if (detectOperacion(linea)) return; // esta es inclusiva
		if (detectLinea(linea)) return;
	}

	private void detectSeccion(String linea) throws Exception {
		if (linea.indexOf("ANALISIS DE VENTAS") == -1) return;
		detectSeccion = false;
	}

	private boolean detectStuff(String linea) throws Exception {
		if (detectSeccion) return true;
		if (linea.startsWith("|")) return true;// ojo
		if (linea.startsWith("-")) return true;
		if (linea.indexOf("XXXXXXXXXX") != -1) return true;
		if (linea.indexOf("====") != -1) return true;
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
		// 5992039919 5 VVVV 21/02
		// 044 AEROLINEAS ARGENTINA
		Pattern p = Pattern.compile("\\p{Blank}(\\d){3}\\p{Blank}(\\d){10}\\p{Blank}(\\d)\\p{Blank}.*");
		Matcher m = p.matcher(linea);
		boolean b = m.matches();

		if (!b) return false;
		closePendingDetail();
		actual_acumAerTotal = 0;
		actual_acumOpTotal = 0;		
//		Pattern p = Pattern.compile("(\\d){10}\\p{Blank}(\\p{Blank}|\\d)\\p{Blank}(\\p{Blank}|\\p{Alpha}){4}\\p{Blank}\\p{Blank}\\d\\d\\/\\d\\d");
//	
//		Matcher m = p.matcher(linea);
//		boolean b = m.find();
//		if (!b) return false;
		startNewDetail();
		boolean hasTax=false;
		int[] columnas = { 1,5, 16, 18, 23, 32, 42, 54, 62, 69, 78, 92, 102, 112};
		String boleto = "";
		for (int c = 0; c < columnas.length; c++) {
			String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
			switch (c) {
			case 0:
				actual_idAerolinea = sCont;
				actual_aerolinea=findAerolinea(sCont);
				break;
			case 1:
				boleto = sCont;
				detalle.setBoleto(sCont);
//				if (boleto.indexOf("4997061927")!=-1) 
//					detalle.setAerolineaBoleto(this.actual_idAerolinea + " " + sCont);
				detalle.setAerolineaBoleto(this.actual_idAerolinea + " " + sCont);
				break;// boleto
			case 2:
				detalle.setBoletoLargo(boleto + sCont);
				;
				break;// digito
			case 3:
				break;// cupon
			case 4:
				detalle.setFecha(getFechaDDMM(sCont));
				break;// fecha
			case 5:
				detalle.setContado(getDouble(sCont));
				break;// contado
			case 6:
				detalle.setTarjeta(getDouble(sCont));
				break;// tarjeta
			case 7:
				detalle.setImpuesto1(getDouble(sCont));
				hasTax=true;
				break;// iva 10,5%
			case 8:
				detalle.setComisionPorc(getDouble(sCont));
				break;// % comision
			case 9:
				detalle.setComision(getDouble(sCont));
				break;// comision
			case 10:
				detalle.setImpSobrecom(getDouble(sCont));
				break;// impuesto sobre comision
			case 11:
				detalle.setImpuesto2(getDouble(sCont));
				hasTax=true;
				break;// iva 21 %
			case 12:
				detalle.setTotal(getDouble(sCont));
				break;// impuesto sobre comision
			case 13:
				detalle.setObservaciones(sCont);
				if (sCont.startsWith("CC")) {
					detalle.setTipoTarjeta(sCont.substring(2, 4));
					detalle.setNumeroTarjeta(sCont.substring(4));
				}
				if (sCont.indexOf("ANULAC")!=-1)
					detalle.setAnulado(true);

				break;// observaciones
			}
		}
		if (hasTax)
			readyTax();
		return b;
	}

	public void startNewDetail() throws Exception {
		pendingDetail = true;
		impuestos_contado = 0;
		impuestos_tarjeta = 0;
		impuestos = new ArrayList<BizEcuImpuesto>();
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
			readyDetail();
			for (BizEcuImpuesto impuesto : impuestos)
				finder.detectTax(impuesto);
		}
		pendingDetail = false;
	}

//	public boolean detectTax(String linea) throws Exception {
//		// QO 31,10
//		Pattern p = Pattern.compile("(\\p{Blank}{40,50})(\\p{Alpha}{2})(\\p{Blank})*(\\p{Blank}|\\p{Digit}|,|-)*(\\p{Blank}{40})(\\p{Print})*");
//		Matcher m = p.matcher(linea);
//		boolean b = m.matches();
//		if (linea.indexOf("ZAAMAA/406204") != -1) b = b & b;
//		if (!b) return false;
//		int[] columnas = { 49, 52, 61, 72 };
//		for (int c = 0; c < columnas.length; c++) {
//			String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
//			switch (c) {
//			case 0:
//				impuesto.setIso(sCont);
//				break;// iso impuesto
//			case 1:
//				impuesto.setContado(getDouble(sCont));
//				break;// impuesto contado
//			case 2:
//				impuesto.setTarjeta(getDouble(sCont));
//				break;// impuesto credito
//			}
//		}
//		readyTax();
//		return b;
//	}

	private void readyTax() throws Exception {
		impuesto = new BizEcuImpuesto();
		impuesto.setCompany(company);
		impuesto.setOwner(owner);
		impuesto.setIdpdf(idPdf);
		impuesto.setLinea(nextLinea);
		impuesto.setContado(detalle.getContado() == 0 ? 0 : detalle.getImpuesto1());
		impuesto.setTarjeta(detalle.getTarjeta() == 0 ? 0 : detalle.getImpuesto1());
		impuesto.setIso("OTROS");
		impuestos_contado += impuesto.getContado();
		impuestos_tarjeta += impuesto.getTarjeta();
		impuestos.add(impuesto);

		impuesto = new BizEcuImpuesto();
		impuesto.setCompany(company);
		impuesto.setOwner(owner);
		impuesto.setIdpdf(idPdf);
		impuesto.setLinea(nextLinea);
		impuesto.setContado(detalle.getContado() == 0 ? 0 : detalle.getImpuesto2());
		impuesto.setTarjeta(detalle.getTarjeta() == 0 ? 0 : detalle.getImpuesto2());
		impuesto.setIso("EC");
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
		detalle.setOperacion(actual_operacion);
		detalle.setTipoRuta(actual_tipoRuta);
		detalle.setTarifa(detalle.getContado() + detalle.getTarjeta());
		detalle.setBaseImponible(detalle.getTarifa() + qn);
		detalle.setContadoBruto(detalle.getContado() + impuestos_contado);
		detalle.setTarjetaBruto(detalle.getTarjeta() + impuestos_tarjeta);
		if (!detalle.hasAnulado()) detalle.setAnulado(false);
		double suma = JTools.rd(detalle.getContadoBruto() + detalle.getTotalComision());
		if (Math.abs(suma) < Math.abs(detalle.getTotal())) detalle.setContadoBruto(detalle.getContadoBruto() + detalle.getIva());
		else detalle.setTarjetaBruto(detalle.getTarjetaBruto() + detalle.getIva());
		detalle.setImpuestoAcum(impuestos_contado + impuestos_tarjeta );
		if (detalle.getAnulado())
			detalle.setOperacion("REFUND");
		finder.detectDetail(detalle);
		actual_acumOpTotal += detalle.getTotal();
		actual_acumAerTotal += detalle.getTotal();
		detalle = new BizEcuDetalle();
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0) return 0;
		String num =  JTools.getNumberEmbeddedWithSep(linea);
		boolean neg = linea.indexOf("-") != -1;
		return (neg ? -1 : 1) * Double.parseDouble(num);
	}

	public boolean detectTotal(String linea) throws Exception {
		boolean b = linea.startsWith("TOT ");

		if (!b) return false;
		closePendingDetail();

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

		return b;
	}

	public void execute() throws Exception {
		cabecera = new BizEcuCabecera();
		detalle = new BizEcuDetalle();
		impuesto = new BizEcuImpuesto();
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
		if ((start = linea.indexOf("PERIODO:")) == -1) return;
		String id = linea.substring(start + 8);
		if (id.length() > 20) return;
		idPdf = JTools.getNumberEmbedded(id);
		cabecera.setIdpdf(idPdf);
		readyHeader();
	}

	private void detectHeader(String linea) throws Exception {
		// QO 31,10
		boolean b;
/*		b = linea.indexOf("BSP - ECUADOR.") != -1;
		if (b) {
			int[] columnas = { 7, 21, 54, 78, 84, 109, 130 };
			for (int c = 0; c < columnas.length; c++) {
				String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
				switch (c) {
				case 0:
					cabecera.setIata(sCont);
					iata = sCont;
					break;// iso impuesto
				case 1:
					cabecera.setDescripcion(sCont);
					break;// iso impuesto
				case 2:
					cabecera.setDireccion(sCont);
					break;// iso impuesto
				case 3:
					cabecera.setCodigoPostal(sCont);
					break;// iso impuesto
				case 4:
					cabecera.setLocalidad(sCont);
					break;// iso impuesto
				case 5:
					cabecera.setCif(sCont);
					break;// iso impuesto
				}
			}			
		}
	*/	
		b = linea.indexOf("CODIGO ") != -1;
		if (b) {
			int[] columnas = { 7,17};
			for (int c = 0; c < columnas.length; c++) {
				String sCont = (c + 1 >= columnas.length) ? linea.substring(columnas[c]).trim() : linea.substring(columnas[c], columnas[c + 1]).trim();
				switch (c) {
				case 0:
					cabecera.setIata(sCont);
					iata = sCont;
					break;
				}
			}
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
		// | DE 19/02/07 AL 25/02/07 |
		Pattern p = Pattern.compile("R\\d\\d\\d\\d(\\p{Blank})++\\d\\d\\/\\d\\d\\/\\d\\d(\\p{Blank})++DEL(\\p{Blank})++\\d\\d\\/\\d\\d\\/\\d\\d(\\p{Blank})++AL(\\p{Blank})++\\d\\d\\/\\d\\d\\/\\d\\d");
		Matcher m = p.matcher(linea);
		boolean b = m.find();

		if (!b) return;
		int start, s1, s2;
		if ((start = linea.indexOf(" DEL")) == -1) return;
		if ((s1 = linea.indexOf(" AL ", start)) == -1) return;

		fechaDesde = getFecha(linea.substring(start + 4, s1).trim());
		fechaHasta = getFecha(linea.substring(s1 + 4).trim());
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
		JParseoEcu parser = new JParseoEcu();
		IFinderEcu finder = new IFinderEcu() {

			public void detectDetail(BizEcuDetalle detail) throws Exception {
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

			public void detectHeader(BizEcuCabecera header) throws Exception {
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

			public void detectTax(BizEcuImpuesto tax) throws Exception {
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
