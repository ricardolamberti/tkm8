package pss.bsp.pdf.chl;

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
import pss.bsp.pdf.chl.cabecera.BizChileCabecera;
import pss.bsp.pdf.chl.detalle.BizChileDetalle;
import pss.bsp.pdf.chl.impuesto.BizChileImpuesto;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.core.tools.PssLogger;
import pss.tourism.carrier.BizCarrier;

public class JParserChile implements IParseo {

	IFinderChile finder;

	InputStream input;

	String idPdf;

	String iata = "0";

	Date fechaDesde;

	Date fechaHasta;

	boolean detectHeader = true;

	boolean detectMoneda = true;

	boolean detectPeriodo = true;

	boolean detectSeccion = true;

	boolean detectTax = false;

	String moneda = null;
	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	public String getTableDetalle() throws Exception {
		return new BizChileDetalle().GetTable();
	}
	BizChileCabecera cabecera;

	BizChileDetalle detalle;

	BizChileImpuesto impuesto;
	BizChileImpuesto impuesto2;
	BizChileImpuesto impuesto3;
	boolean tarjeta=false;

	boolean finalizar = false;

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

	List<BizChileImpuesto> impuestos = null;

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
		this.finder = (IFinderChile) finder;
	}

	private String getContent() {
		PDFTextParser parser = new PDFTextParser();
		return parser.pdftoText(input, false, true);
	}

	protected void privExecute() throws Exception {

		String content = getContent();

		if (content == null)
			throw new Exception("El archivo no parece ser una interfaz BSP");
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
		System.out.println("[" + linea + "]");
		
		if (idPdf == null)
			detectId(linea);
		if (detectHeader)
			detectHeader(linea);
	//	if (detectMoneda)
			detectMoneda(linea);
		if (detectPeriodo)
			detectPeriodo(linea);
		if (detectSeccion)
			detectSeccion(linea);
		if (detectTotal(linea))
			return;
		if (detectStuff(linea))
			return;
		if (detectOperacion(linea))
			return; // esta es inclusiva
		if (detectLinea(linea))
			return;
		if (detectTax(linea))
			return;
	}

	private void detectSeccion(String linea) throws Exception {
		if (linea.toUpperCase().indexOf("SCOPE") == -1 && (linea.toUpperCase().indexOf("DOMESTIC") == -1||linea.toUpperCase().indexOf("INTERNATIONAL") == -1))
			return;
		detectSeccion = false;
	}

	private boolean detectStuff(String linea) throws Exception {
		if (detectSeccion)
			return true;
		if (linea.startsWith("|"))
			return true;// ojo
		if (linea.startsWith("-"))
			return true;
		if (linea.indexOf("XXXXXXXXXX") != -1)
			return true;
		if (linea.indexOf("====") != -1)
			return true;
		if (linea.indexOf(" Page") != -1)
			return true;
		if (linea.indexOf("AGENT BILLING DETAILS") != -1)
			return true;
		if (linea.equals(
				"NO.  DE FECHA DE CODIGO VALOR VALOR IMP, TASAS & CARGOS  ---COMISION STD--- ---COMISION SUPP--- IMPUESTO NETO A"))
			return true;
		if (linea.equals(
				"CIA TRNC DOCUMENTO EMISION CPN NR STAT FOP TRANSACCION TARIFA IMP T&C PEN COBL % VALOR % VALOR S/ COMISION PAGAR"))
			return true;
		if (linea.equals("PAGAR"))
			return true;
// 		if (linea.equals("FECHA DE ---COMISION STD---")) return true;
// 		if (linea.equals("FECHA DE ---COMISION STD---")) return true;
		if (linea.equals("% VALOR S/ COMISION"))
			return true;
		if (linea.equals("CODIGO"))
			return true;
		if (linea.equals("NR"))
			return true;
		if (linea.equals("NO.  DE"))
			return true;
		if (linea.equals("DOCUMENTO CPN"))
			return true;
		if (linea.equals("VALOR"))
			return true;
		if (linea.equals("EMISION"))
			return true;
		if (linea.equals("IMP, TASAS & CARGOS"))
			return true;
		if (linea.equals("T&C"))
			return true;
		if (linea.equals("IMPUESTO"))
			return true;
		if (linea.equals("TARIFATRANSACCION COBL"))
			return true;
		if (linea.indexOf("SCOPE INTERNATIONAL") != -1)
			return true;
		if (linea.indexOf("SCOPE DOMESTIC") != -1)
			return true;
		if (linea.indexOf("ISSUES") != -1) {
			closePendingDetail();
			
			return true;
		}
		if (linea.indexOf("GRAND TOTAL") != -1)
			return true;
		if (linea.indexOf("GRAND  TOTAL") != -1)
			return true;
		if (linea.indexOf("SCOPE") != -1)
			return true;
		  		  
		if (linea.trim().equals(""))
			return true;
		return false;
	}

	public void detectTipoRuta(String linea) throws Exception {
		// | R2139 01/03/07 |
		if (linea.length() < 20)
			return;
		Pattern p = Pattern
				.compile("\\|\\p{Blank}\\p{Alpha}\\d\\d\\d\\d\\p{Blank}\\p{Blank}\\d\\d\\/\\d\\d\\/\\d\\d\\p{Blank}\\|");
		// Pattern p = Pattern.compile("\\.*");
		Matcher m = p.matcher(linea);
		boolean b = m.find();

		if (b) {
			int pos = linea.indexOf("        ", m.start());
			if (pos < 20)
				return;
			String ruta = linea.substring(m.start() + 20, pos).trim();
			if (ruta.length() > 0)
				actual_tipoRuta = ruta;

		}
	}

	public boolean detectAerolinea(String linea) throws Exception {
		// 044 AEROLINEAS ARGENTINA
		Pattern p = Pattern.compile("(\\d){3}\\p{Blank}(\\p{Print}){1,35}(\\p{Blank})*");
		Matcher m = p.matcher(linea);
		boolean b = m.matches();

		if (!b)
			return false;
		closePendingDetail();
		String idAerolinea = linea.substring(0, linea.indexOf(" ")).trim();
		String aerolinea = linea.substring(linea.indexOf(" ") + 1).trim();
		actual_idAerolinea = idAerolinea;
		actual_aerolinea = aerolinea;
		actual_acumAerTotal = 0;
		actual_acumOpTotal = 0;
		return true;
	}

	public boolean detectOperacion(String linea) throws Exception {
		return false;
		// E TICKET
//		Pattern p = Pattern.compile("\\p{Alpha}(\\p{Print})++(\\p{Blank})*");
//		Matcher m = p.matcher(linea);
//		boolean b = m.matches();
//
//		if (!b)
//			return false;
//		String operacion = linea.trim();
//		actual_operacion = operacion;
//		actual_acumOpTotal = 0;
//		return b;
	}

	
	private static int a = 0;
	private enum LINE_FIELDS {
		FIELD_CIA,
		FIELD_TRNC,
		FIELD_NRO_DE_DOC,
		FIELD_FECHA_DE_EMISION,
		FIELD_CODIGO_CPN,
		FIELD_CODIGO_NR,
		FIELD_CODIGO_STAT,
		FIELD_CODIGO_FOP,
		FIELD_VALOR_TRANSACCION,
		FIELD_VALOR_TARIFA,
		FIELD_IMP,
		FIELD_IMP_ID,
		FIELD_TASAS_CARGOS,
		FIELD_TASAS_CARGOS_ID,
		FIELD_PEN,
		FIELD_PEN_ID,
		FIELD_COMISION_STD_COBL,
		FIELD_COMISION_STD_PORC,
		FIELD_COMISION_STD_VALOR,
		FIELD_COMISION_SUPP_PORC,
		FIELD_COMISION_SUPP_VALOR,
		FIELD_IMPUESTO_S_COMISION,
		FIELD_PAGAR,
		END	{
      @Override
      public LINE_FIELDS next() {
          return null; // see below for options for this line
      };
	  };

	  static public LINE_FIELDS first() {
      return values()[0];
	  }
		
	  public LINE_FIELDS next() {
	      // No bounds checking required here, because the last instance overrides
	      return values()[ordinal() + 1];
	  }		
		
	};
	
//	private static final int FIELD_INDEX_CIA = 0; //  (Id Aerolinea)
//	private static final int FIELD_INDEX_TRNC 					= 1; // (Tipo Operacion)
//	private static final int FIELD_INDEX_NRO_DE_DOC 			= 2; // (Boleto)
//	private static final int FIELD_INDEX_FECHA_DE_EMISION 		= 3; 
//	private static final int FIELD_INDEX_CODIGO_CPN				= 4;	
//	private static final int FIELD_INDEX_CODIGO_NR 				= 5;
//	private static final int FIELD_INDEX_CODIGO_STAT 			= 6; // (Tipo Ruta)
//	private static final int FIELD_INDEX_CODIGO_FOP				= 7;
//	private static final int FIELD_INDEX_UNKNOWN_1				= 8;
//	private static final int FIELD_INDEX_VALOR_TRANSACCION		= 9;
//	private static final int FIELD_INDEX_VALOR_TARIFA			= 10;
//	private static final int FIELD_INDEX_IMP					= 11;
//	private static final int FIELD_INDEX_TASAS_CARGOS			= 12;
//	private static final int FIELD_INDEX_PEN					= 13;
//	private static final int FIELD_INDEX_COMISION_STD_COBL		= 14;
//	private static final int FIELD_INDEX_COMISION_STD_PORC		= 15;
//	private static final int FIELD_INDEX_COMISION_STD_VALOR		= 16;
//	private static final int FIELD_INDEX_COMISION_SUPP_PORC		= 17;
//	private static final int FIELD_INDEX_COMISION_SUPP_VALOR	= 18;
//	private static final int FIELD_INDEX_IMPUESTO_S_COMISION	= 19;
//	private static final int FIELD_INDEX_PAGAR					= 20;
	
/*
case 1: // TRNC (Tipo Operacion)
case 2: // NO. DE DOCUMENTO (Boleto)
case 3: // FECHA DE EMISION
case 4: // CODIGO CPN (Ignored)
case 5: // CODIGO NR (Ignored)
case 6: // CODIGO STAT (Tipo Ruta)
case 7: // CODIGO FOP
case 8: // VALOR TRANSACCION
case 9: // VALOR TRANSACCION
case 10: // VALOR TARIFA
case 11: // IMP
case 12: // TASAS & CARGOS
case 13: // PEN
case 14: // ---COMISION STD--- COBL
case 15: // ---COMISION STD--- %
case 16: // ---COMISION STD--- VALOR
case 17: // ---COMISION SUPP--- %
case 18: // ---COMISION SUPP--- VALOR
case 19: // IMPUESTO S/COMISION
case 20: // PAGAR
*/	
	public boolean detectLinea2(String linea) throws Exception {
		Pattern p = Pattern.compile("(\\p{Blank}*)(\\p{Alpha}{2})(\\p{Blank})(((\\p{Digit}{1,3})\\p{Punct})*)(\\p{Digit}{2})"	);
		Matcher m = p.matcher(linea);
		boolean b = m.matches();
		if (!b)		return false;
		String linea2 = JTools.removeTwoOrMoreSpaces(linea);
	  PssLogger.logInfo("PRE PROCESO LINEA : " + linea2);
		StringTokenizer toks = new StringTokenizer(linea2, " ");
		int pos=1;
		boolean tarjeta = false;
		while (toks.hasMoreTokens()) {
			String nextToken = toks.nextToken();
			if (pos==1) {
				if (nextToken.indexOf("CC")!=-1) tarjeta=true;
				if (nextToken.indexOf("CA")!=-1) tarjeta=false;
			}
			if (pos==2) {
				if (tarjeta)
					detalle.setTarjeta(getDouble(nextToken));
				else
					detalle.setContado(getDouble(nextToken));
			}
			pos++;
		}
		return true;
	}	
	public boolean detectLinea3(String linea) throws Exception {
		String boleto = "";
		int c = 0;
		LINE_FIELDS fieldIdx = LINE_FIELDS.FIELD_CODIGO_FOP;
		boolean tarjeta = false;
		boolean ex = false;
		boolean shortest = false;

		// 782 TKTT 3127588397 25FEB19 FFVV I CA 374.31 53.56 44.40 AJ 175.00 YQ 50.35
		// 6.00 3.21 0.00 0.00 371.10
		String linea2 = JTools.removeTwoOrMoreSpaces(linea.substring(linea.indexOf("**")).replaceAll("                   ", " ? "));
	  PssLogger.logInfo("PRE PROCESO LINEA : " + linea2);
		StringTokenizer toks = new StringTokenizer(linea2, " ");
		String nextToken = null;
		boolean first = true;
		boolean exit = false;
		while (!exit && fieldIdx != LINE_FIELDS.END) {
			String sCont;
			if (first) {
				sCont = toks.nextToken();
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else
					nextToken = null;
				first = false;
			} else {
				sCont = nextToken;
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else
					nextToken = null;
			}

			if (nextToken == null)
				exit = true;

			if (shortest)
				break;

// 			switch (c) {
			switch (fieldIdx) {


			case FIELD_CODIGO_FOP: // CODIGO FOP
				if (sCont.equals("CC")) {
					tarjeta = true;
					break;
				}
				if (sCont.equals("CA")) {
					tarjeta = false;
					break;
				}
				if (sCont.equals("EX")) {
					ex = true;
				}
				break;

		

			case FIELD_VALOR_TRANSACCION: // VALOR TRANSACCION
				if (sCont.trim().equals("?")) {
					// PssLogger.logInfo(sCont);
					shortest = true;
					break;
				}
				// detalle.setTotal(getDouble(sCont));
				break;

				
			case FIELD_VALOR_TARIFA: // VALOR TARIFA
				if (sCont.trim().equals("?")) {
					// shortest = true;
					break;
				}

				if (tarjeta)
					detalle.setTarjeta(getDouble(sCont));
				else
					detalle.setContado(getDouble(sCont));
				break;

			case FIELD_IMP: // IMP
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_COMISION_STD_COBL;
					continue;
				}
				impuesto.setTarjeta(0);
				impuesto.setContado(0);
				if (tarjeta)
					impuesto.setTarjeta(getDouble(sCont));
				else
					impuesto.setContado(getDouble(sCont));
				impuesto.setIso("XX");
				break;

			case FIELD_IMP_ID:
				impuesto.setIso(sCont);
				break;
				
			case FIELD_TASAS_CARGOS: // TASAS & CARGOS
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_PEN;
					continue;
				}
				impuesto2.setTarjeta(0);
				impuesto2.setContado(0);
				if (tarjeta)
					impuesto2.setTarjeta(getDouble(sCont));
				else
					impuesto2.setContado(getDouble(sCont));
				impuesto2.setIso("TC");
				break;

			case FIELD_TASAS_CARGOS_ID:
				impuesto2.setIso(sCont);
				break;

			case FIELD_PEN: // PEN
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_COMISION_STD_COBL;
					continue;
				}
				
				if (nextToken.length() <= 5) {
					break;
				}
				impuesto3.setTarjeta(0);
				impuesto3.setContado(0);
				if (tarjeta)
					impuesto3.setTarjeta(getDouble(sCont));
				else
					impuesto3.setContado(getDouble(sCont));
				impuesto3.setIso("PN");
				break;

			case FIELD_PEN_ID:
				break;
				
			case FIELD_COMISION_STD_COBL: // ---COMISION STD--- COBL
				if (sCont.equals("?")) {
					break;
				}
				detalle.setTarifaSinComision(getDouble(sCont));
				break;

			case FIELD_COMISION_STD_PORC: // ---COMISION STD--- %
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComisionPorc(getDouble(sCont));
				break;

			case FIELD_COMISION_STD_VALOR: // ---COMISION STD--- VALOR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComision(getDouble(sCont));
				break;

			case FIELD_COMISION_SUPP_PORC: // ---COMISION SUPP--- %
				break;

			case FIELD_COMISION_SUPP_VALOR: // ---COMISION SUPP--- VALOR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComisionOver(getDouble(sCont));
				break;

			case FIELD_IMPUESTO_S_COMISION: // IMPUESTO S/COMISION
				if (sCont.equals("?")) {
					break;
				}
				detalle.setImpSobrecom(getDouble(sCont));
				break;

			case FIELD_PAGAR: // PAGAR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setTotal(getDouble(sCont));
				break;

			default:
				break;
			} // end switch

// 			c++;
			fieldIdx = fieldIdx.next();
		}
		readyTax();

		return true;
	}	
	public boolean detectLinea(String linea) throws Exception {
		if (linea.indexOf("+RTDN") != -1)
			return false;
		if ((linea.indexOf("CANX") != -1) || linea.indexOf("CANN") != -1) {
			closePendingDetail();
			return false;
		}

		if (linea.indexOf("1861510442")!=-1)
			PssLogger.logInfo("X");

		Pattern p = Pattern.compile(
				"(\\p{Blank})*(\\d){3}(\\p{Blank})*(\\p{Alpha}){4}(\\p{Blank})*(\\d){10}(\\p{Blank})*(\\d){2}(\\p{Alpha}){3}(\\d){2}(\\p{Blank})*(\\p{Alpha}){4}(\\p{Print})*");

		Matcher m = p.matcher(linea);
		boolean b = m.find();
		if (!b) {
			if (!pendingDetail) return false;
			String linea2 = JTools.removeTwoOrMoreSpaces(linea);
			if (linea2.startsWith("CC ")) {
				detectLinea2(linea);
				return false;
			}
			if (linea2.startsWith("CA ")) {
				detectLinea2(linea);
				return false;
			}
			if (linea2.startsWith("** ")) {
				detectLinea3(linea);
				return false;
			}
			return false;
		}
		closePendingDetail();

		actual_acumAerTotal = 0;
		actual_acumOpTotal = 0;
		detectTax = true;

		startNewDetail();

		String boleto = "";
		int c = 0;
		LINE_FIELDS fieldIdx = LINE_FIELDS.first();
		impuesto = new BizChileImpuesto();
		impuesto2 = new BizChileImpuesto();
		impuesto3 = new BizChileImpuesto();
		boolean tarjeta = false;
		boolean ex = false;
		boolean shortest = false;

		// 782 TKTT 3127588397 25FEB19 FFVV I CA 374.31 53.56 44.40 AJ 175.00 YQ 50.35
		// 6.00 3.21 0.00 0.00 371.10
		String linea2 = JTools.removeTwoOrMoreSpaces(linea.replaceAll("                   ", " ? "));
	  PssLogger.logInfo("PRE PROCESO LINEA : " + linea2);
		StringTokenizer toks = new StringTokenizer(linea2, " ");
		String nextToken = null;
		boolean first = true;
		boolean exit = false;
		while (!exit && fieldIdx != LINE_FIELDS.END) {
			String sCont;
			if (first) {
				sCont = toks.nextToken();
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else
					nextToken = null;
				first = false;
			} else {
				sCont = nextToken;
				if (toks.hasMoreElements())
					nextToken = toks.nextToken();
				else
					nextToken = null;
			}

			if (nextToken == null)
				exit = true;

			if (shortest)
				break;

// 			switch (c) {
			switch (fieldIdx) {
			case FIELD_CIA: // CIA (Id Aerolinea)
				actual_idAerolinea = sCont;
				actual_aerolinea = findAerolinea(actual_idAerolinea);
				break;

			case FIELD_TRNC: // TRNC (Tipo Operacion)
				if (sCont.equals("TKTT")) {
					detalle.setOperacion("ELECTRONIC TICKET - OPET");
					break;
				}

				if (sCont.equals("EMDS")) {
					detalle.setOperacion("EMD");
					break;
				}

				if (sCont.equals("CANX")) {
					detalle.setOperacion("CANX");
					shortest = true;
					break;
				}

				if (sCont.equals("RFND")) {
					detalle.setAnulado(true);
					detalle.setOperacion("RFND");
					break;
				}

				if (sCont.equals("EMDA")) {
					detalle.setOperacion("EMD");
					break;
				}

				if (sCont.equals("CANN")) {
					detalle.setOperacion("CANN");
					shortest = true;
					break;
				}

				detalle.setOperacion(sCont);
				break;

			case FIELD_NRO_DE_DOC:  // NO. DE DOCUMENTO (Boleto)
				boleto = sCont;
				detalle.setBoleto(sCont);
				detalle.setAerolineaBoleto(this.actual_idAerolinea + " " + sCont);
				detalle.setBoletoLargo(boleto);
				break;

			case FIELD_FECHA_DE_EMISION: // FECHA DE EMISION
				detalle.setFecha(getFechaDDMMMAASinSep(sCont));
				break;

			case FIELD_CODIGO_CPN: // CODIGO CPN (Ignored)
				break;
				
			case FIELD_CODIGO_NR: // CODIGO NR (Ignored)
				// Este campo viene en el encabezado del pdf pero no contiene valor, ni siquiera un espacion en blanco.
				// El proximo campo es el CODIGO STAT (I/D) por ende si detecto dichos valores asumo que el campo leido es el siguiente
				// ajusto en indicador de campo y sigo adelante.
				if (sCont.equals("I") == false && sCont.equals("D") == false ) {
					break;
				}
				fieldIdx = fieldIdx.next();
				
			case FIELD_CODIGO_STAT: // CODIGO STAT (Tipo Ruta)
				if (sCont.equals("I") || sCont.startsWith("I")) {
					detalle.setTipoRuta("INTERNACIONAL");
					break;
				}
				if (sCont.equals("D")) {
					detalle.setTipoRuta("DOMESTICO");
				}
				break;

			case FIELD_CODIGO_FOP: // CODIGO FOP
				if (sCont.equals("CC")) {
					tarjeta = true;
					break;
				}
				if (sCont.equals("CA")) {
					tarjeta = false;
					break;
				}
				if (sCont.equals("EX")) {
					ex = true;
				}
				break;

		

			case FIELD_VALOR_TRANSACCION: // VALOR TRANSACCION
				if (sCont.trim().equals("?")) {
					// PssLogger.logInfo(sCont);
					shortest = true;
					break;
				}
				// detalle.setTotal(getDouble(sCont));
				break;

				
			case FIELD_VALOR_TARIFA: // VALOR TARIFA
				if (sCont.trim().equals("?")) {
					// shortest = true;
					break;
				}

				if (tarjeta)
					detalle.setTarjeta(getDouble(sCont));
				else
					detalle.setContado(getDouble(sCont));
				break;

			case FIELD_IMP: // IMP
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_TASAS_CARGOS;
					continue;
				}
				impuesto.setTarjeta(0);
				impuesto.setContado(0);
				if (tarjeta)
					impuesto.setTarjeta(getDouble(sCont));
				else
					impuesto.setContado(getDouble(sCont));
				impuesto.setIso("XX");
				break;

			case FIELD_IMP_ID:
				impuesto.setIso(sCont);
				break;
				
			case FIELD_TASAS_CARGOS: // TASAS & CARGOS
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_PEN;
					continue;
				}
				impuesto2.setTarjeta(0);
				impuesto2.setContado(0);
				if (tarjeta)
					impuesto2.setTarjeta(getDouble(sCont));
				else
					impuesto2.setContado(getDouble(sCont));
				impuesto2.setIso("TC");
				break;

			case FIELD_TASAS_CARGOS_ID:
				impuesto2.setIso(sCont);
				break;

			case FIELD_PEN: // PEN
				if (sCont.equals("?")) {
					fieldIdx =  LINE_FIELDS.FIELD_COMISION_STD_COBL;
					continue;
				}
				
				if (nextToken.length()!=2 && nextToken.length() <= 5) {
					break;
				}
				impuesto3.setTarjeta(0);
				impuesto3.setContado(0);
				if (tarjeta)
					impuesto3.setTarjeta(getDouble(sCont));
				else
					impuesto3.setContado(getDouble(sCont));
				impuesto3.setIso("PN");
				break;

			case FIELD_PEN_ID:
				break;
				
			case FIELD_COMISION_STD_COBL: // ---COMISION STD--- COBL
				if (sCont.equals("?")) {
					break;
				}
				detalle.setTarifaSinComision(getDouble(sCont));
				break;

			case FIELD_COMISION_STD_PORC: // ---COMISION STD--- %
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComisionPorc(getDouble(sCont));
				break;

			case FIELD_COMISION_STD_VALOR: // ---COMISION STD--- VALOR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComision(getDouble(sCont));
				break;

			case FIELD_COMISION_SUPP_PORC: // ---COMISION SUPP--- %
				break;

			case FIELD_COMISION_SUPP_VALOR: // ---COMISION SUPP--- VALOR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setComisionOver(getDouble(sCont));
				break;

			case FIELD_IMPUESTO_S_COMISION: // IMPUESTO S/COMISION
				if (sCont.equals("?")) {
					break;
				}
				detalle.setImpSobrecom(getDouble(sCont));
				break;

			case FIELD_PAGAR: // PAGAR
				if (sCont.equals("?")) {
					break;
				}
				detalle.setTotal(getDouble(sCont));
				break;

			default:
				break;
			} // end switch

// 			c++;
			fieldIdx = fieldIdx.next();
		}
		readyTax();

		return b;
	}

	public String findAerolinea(String id) throws Exception {
		BizCarrier c = BizCarrier.findCarrierByCod(id);
		if (c == null)
			return null;
		return c.getDescription().toUpperCase();
	}

	public void startNewDetail() throws Exception {
		pendingDetail = true;
		impuestos_contado = 0;
		impuestos_tarjeta = 0;
		impuestos = new ArrayList<BizChileImpuesto>();
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
//			if (actual_operacion.indexOf("TICKET")!=-1 && actual_aerolinea.indexOf("IATA")==-1) {
			readyDetail();
			for (BizChileImpuesto impuesto : impuestos)
				finder.detectTax(impuesto);
//			}
		}
		pendingDetail = false;
	}

	public boolean detectTax(String linea) throws Exception {
		if (!detectTax)
			return false;
		// QO 31,10
		Pattern p = Pattern.compile(
				// "(((\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2})|((\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2}\\p{Blank}(\\p{Digit}|-)*.(\\p{Digit}){2}(\\p{Blank}(\\p{Alpha}|\\p{Digit}){2})))))(\\p{Print})*"
				"(\\p{Blank}*)(((\\p{Digit}{1,3})\\p{Punct})*)(\\p{Digit}{1,3})(\\p{Blank})(\\p{Alpha}{2}|\\p{Alpha}\\p{Digit})"
				+ "|(\\p{Blank}*)(((\\p{Digit}{1,3})\\p{Punct})*)(\\p{Digit}{1,3})(\\p{Blank})(\\p{Alpha}{2}|\\p{Alpha}\\p{Digit})(\\p{Blank}*)(((\\p{Digit}{1,3})\\p{Punct})*)(\\p{Digit}{1,3})(\\p{Blank})(\\p{Alpha}{2}|\\p{Alpha}\\p{Digit})"
				);
		Matcher m = p.matcher(linea);
		boolean b = m.matches();
		if (!b)
			return false;
		impuesto = new BizChileImpuesto();
		impuesto2 = new BizChileImpuesto();
		impuesto3 = new BizChileImpuesto();
		int c = 0;
		String linea2 = JTools.removeTwoOrMoreSpaces(linea);
		StringTokenizer toks = new StringTokenizer(linea2, " ");
		while (toks.hasMoreElements()) {
			String sCont = toks.nextToken();
			switch (c) {
			case 0:
				impuesto.setTarjeta(0);
				impuesto.setContado(0);
				if (detalle.getTarjeta() > 0)
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
				if (detalle.getTarjeta() > 0)
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
		boolean findImp1=false;
		boolean findImp2=false;
		BizChileImpuesto imp ;
		int i;
		for (i=0;i<impuestos.size();i++) {
			imp = impuestos.get(i);
			if (imp.getIso().equals(impuesto.getIso())) {
				impuestos_contado += impuesto.getContado();
				impuestos_tarjeta += impuesto.getTarjeta();
				imp.setContado(imp.getContado()+impuesto.getContado());
				imp.setTarjeta(imp.getTarjeta()+impuesto.getTarjeta());
				findImp1=true;
			}		
			if (imp.getIso().equals(impuesto2.getIso())) {
				impuestos_contado += impuesto2.getContado();
				impuestos_tarjeta += impuesto2.getTarjeta();
				imp.setContado(imp.getContado()+impuesto2.getContado());
				imp.setTarjeta(imp.getTarjeta()+impuesto2.getTarjeta());
				findImp2=true;
			}
			
		}
		if (!findImp1 && !impuesto.getIso().equals("")) {
			if (impuesto.getIso().equals("QN")) {
				qn = impuesto.getContado() + impuesto.getTarjeta();
			}
			impuesto.setCompany(company);
			impuesto.setOwner(owner);
			impuesto.setIdpdf(idPdf);
			impuesto.setLinea(nextLinea);
			impuestos_contado += impuesto.getContado();
			impuestos_tarjeta += impuesto.getTarjeta();
			impuestos.add(impuesto);
		}
		impuesto = new BizChileImpuesto();
		if (!findImp2 && !impuesto2.getIso().equals("")) {
			if (impuesto2.getIso().equals("QN")) {
				qn = impuesto2.getContado() + impuesto2.getTarjeta();
			}
			impuesto2.setCompany(company);
			impuesto2.setOwner(owner);
			impuesto2.setIdpdf(idPdf);
			impuesto2.setLinea(nextLinea);
			impuestos_contado += impuesto2.getContado();
			impuestos_tarjeta += impuesto2.getTarjeta();
			impuestos.add(impuesto2);
		}
		impuesto = new BizChileImpuesto();
		impuesto2 = new BizChileImpuesto();
		}

	private void readyDetail() throws Exception {
		detalle.setMoneda(moneda);
		detalle.setCompany(company);
		detalle.setOwner(owner);
		detalle.setIdpdf(idPdf);
		detalle.setLinea(nextLinea);
		detalle.setAerolinea(actual_aerolinea);
		detalle.setIdAerolinea(actual_idAerolinea);
	//	detalle.setOperacion(actual_operacion);
		//detalle.setTipoRuta(actual_tipoRuta);
		detalle.setTarifa(detalle.getContado() + detalle.getTarjeta());
		detalle.setBaseImponible(detalle.getTarifa() + qn);
		detalle.setContadoBruto(detalle.getContado() + impuestos_contado);
		detalle.setTarjetaBruto(detalle.getTarjeta() + impuestos_tarjeta);
		detalle.setImpuesto1(impuestos_contado);
		detalle.setImpuesto2(impuestos_tarjeta);
		detalle.setImpuestoAcum(impuestos_contado+impuestos_tarjeta);
//		double suma = JTools.rd(detalle.getContadoBruto() + detalle.getTotalComision());
//		if (Math.abs(suma) < Math.abs(detalle.getTotal()))
//			detalle.setContadoBruto(detalle.getContadoBruto() + detalle.getIva());
//		else
//			detalle.setTarjetaBruto(detalle.getTarjetaBruto() + detalle.getIva());
//		detalle.setImpuestoAcum(impuestos_contado + impuestos_tarjeta + detalle.getImpuesto1() + detalle.getImpuesto2());
		if (!detalle.hasAnulado())
			detalle.setAnulado(false);
		if (detalle.getAnulado())
			detalle.setOperacion("REFUND");
		finder.detectDetail(detalle);
		actual_acumOpTotal += detalle.getTotal();
		actual_acumAerTotal += detalle.getTotal();
		detalle = new BizChileDetalle();
	}

	public boolean detectTotal(String linea) throws Exception {
		boolean b = (linea.indexOf("INTERNATIONAL TOTALS") != -1 || linea.indexOf("DOMESTIC TOTALS") != -1);
		boolean t = linea.indexOf("ISSUES TOTAL") != -1;

		if (!b && !t)
			return false;

		if (t) {
			detectTax = false;
//			closePendingDetail();
//			finalizar=true;	
		} else {
			detectSeccion = true;
		}

		return true;
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0)
			return 0;
		String num = linea.replaceAll("\\,", "");
		try {
			boolean neg = linea.indexOf("-") != -1;
			num = JTools.getNumberEmbeddedWithSep(num);
			if (num.equals(""))
				return 0;
			return (neg ? -1 : 1) * Double.parseDouble(num);
		} catch (Exception e) {
			PssLogger.logError("Parser Error " + num + " en " + linea);
			throw e;
		}
	}

	public void execute() throws Exception {
		cabecera = new BizChileCabecera();
		detalle = new BizChileDetalle();
		impuesto = new BizChileImpuesto();
		impuesto2 = new BizChileImpuesto();
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
		int start;


		if ((start = linea.indexOf("REFERENCE:")) == -1)
			return;
		String id = linea.substring(start + 10);
		if (id.length() > 50)
			return;
		idPdf = JTools.getNumberEmbedded(id);
		cabecera.setIdpdf(idPdf);
		readyHeader();
	}

	private void detectHeader(String linea) throws Exception {
		// QO 31,10
		boolean b;
		
		b = linea.indexOf("REFERENCE:") != -1;
		if (b) {
			int pos = linea.indexOf("REFERENCE:") + 10;
			int pos2 = linea.indexOf("-",pos);
			iata = linea.substring(pos, pos2).trim();
			cabecera.setIata(iata.trim());
			detectHeader = false;
			readyHeader();
		}
		b = linea.indexOf("AGENTE ") != -1;
		if (b) {
			int a = linea.indexOf("  ");
			String sCont = linea.substring(7, a);
			cabecera.setDescripcion(sCont);
		}
	}

	private void detectPeriodo(String linea) throws Exception {
		boolean b = linea.indexOf(" to ") != -1 && linea.indexOf("-") != -1;

		if (!b)
			return;
		int start,to, s1, s2;
		if ((start = linea.indexOf("(")) == -1)
			return;
		if ((to = linea.indexOf(" to ", start)) == -1)
			return;
		if ((s1 = linea.indexOf(")", start)) == -1)
			return;

		fechaDesde = getFechaDDMMMAA(linea.substring(start+1, to).trim());
		fechaHasta = getFechaDDMMMAA(linea.substring(to + 4, s1).trim());
		cabecera.setPeriodoDesde(fechaDesde);
		cabecera.setPeriodoHasta(fechaHasta);

		detectPeriodo = false;
		readyHeader();
	}

	public Date getFechaDDMMMAA(String linea) throws Exception {
		int start, s1, s2, s3, s4, s5, s6, s7;
		if ((start = linea.indexOf("-")) == -1)
			throw new Exception("Fecha invalida: " + linea);
		if ((s1 = linea.indexOf("-", start + 1)) == -1)
			throw new Exception("Fecha invalida: " + linea);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
		int mes = 0;
		String fecha = linea.substring(start + 1, s1).trim();
		if ((fecha.indexOf("ENE") != -1)||(fecha.indexOf("JAN") != -1))
			mes = Calendar.JANUARY;
		else if (fecha.indexOf("FEB") != -1)
			mes = Calendar.FEBRUARY;
		else if (fecha.indexOf("MAR") != -1)
			mes = Calendar.MARCH;
		else if ((fecha.indexOf("ABR") != -1)||(fecha.indexOf("APR") != -1))
			mes = Calendar.APRIL;
		else if (fecha.indexOf("MAY") != -1)
			mes = Calendar.MAY;
		else if (fecha.indexOf("JUN") != -1)
			mes = Calendar.JUNE;
		else if (fecha.indexOf("JUL") != -1)
			mes = Calendar.JULY;
		else if ((fecha.indexOf("AGO") != -1)||(fecha.indexOf("AUG") != -1))
			mes = Calendar.AUGUST;
		else if (fecha.indexOf("SEP") != -1)
			mes = Calendar.SEPTEMBER;
		else if (fecha.indexOf("OCT") != -1)
			mes = Calendar.OCTOBER;
		else if (fecha.indexOf("NOV") != -1)
			mes = Calendar.NOVEMBER;
		else if ((fecha.indexOf("DIC") != -1)||(fecha.indexOf("DEC") != -1))
			mes = Calendar.DECEMBER;

		cal.set(Calendar.MONTH, mes);
		int anio = Integer.parseInt(linea.substring(s1 + 1).trim());
		cal.set(Calendar.YEAR, anio < 80 ? 2000 + anio : anio < 1900 ? 1900 + anio : anio); // en el 2080 que me vengan a
																																												// buscar
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Date getFechaDDMMMAASinSep(String linea) throws Exception {
		int start, s1, s2, s3, s4, s5, s6, s7;
		start = 2;
		s1 = 5;
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(linea.substring(0, start).trim()));
		int mes = 0;
		String fecha = linea.substring(start, s1).trim();
		if ((fecha.indexOf("ENE") != -1)||(fecha.indexOf("JAN") != -1))
			mes = Calendar.JANUARY;
		else if (fecha.indexOf("FEB") != -1)
			mes = Calendar.FEBRUARY;
		else if (fecha.indexOf("MAR") != -1)
			mes = Calendar.MARCH;
		else if ((fecha.indexOf("ABR") != -1)||(fecha.indexOf("APR") != -1))
			mes = Calendar.APRIL;
		else if (fecha.indexOf("MAY") != -1)
			mes = Calendar.MAY;
		else if (fecha.indexOf("JUN") != -1)
			mes = Calendar.JUNE;
		else if (fecha.indexOf("JUL") != -1)
			mes = Calendar.JULY;
		else if ((fecha.indexOf("AGO") != -1)||(fecha.indexOf("AUG") != -1))
			mes = Calendar.AUGUST;
		else if (fecha.indexOf("SEP") != -1)
			mes = Calendar.SEPTEMBER;
		else if (fecha.indexOf("OCT") != -1)
			mes = Calendar.OCTOBER;
		else if (fecha.indexOf("NOV") != -1)
			mes = Calendar.NOVEMBER;
		else if ((fecha.indexOf("DIC") != -1)||(fecha.indexOf("DEC") != -1))
			mes = Calendar.DECEMBER;

		cal.set(Calendar.MONTH, mes);
		int anio = Integer.parseInt(linea.substring(s1).trim());
		cal.set(Calendar.YEAR, anio < 80 ? 2000 + anio : anio < 1900 ? 1900 + anio : anio); // en el 2080 que me vengan a
																																												// buscar
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private void detectMoneda(String linea) throws Exception {
		if (linea.trim().startsWith("CHILE")&&linea.trim().endsWith("CLP"))
			moneda=("CLP");
		else if (linea.trim().startsWith("CHILE")&&linea.trim().endsWith("USD"))
			moneda=("USD");
		else 
			return;
		

		if (detectMoneda) {
			cabecera.setMoneda("CLP");
			detectMoneda = false;
			readyHeader();
			
		}
	}

	private void readyHeader() throws Exception {
		if (!(!detectHeader && !detectMoneda && !detectPeriodo && idPdf != null))
			return;

		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		finder.detectHeader(cabecera);
	}

	private Date getFecha(String linea) throws Exception {
		int start, s1, s2, s3, s4, s5, s6, s7;
		if ((start = linea.indexOf("/")) == -1)
			throw new Exception("Fecha invalina: " + linea);
		if ((s1 = linea.indexOf("/", start + 1)) == -1)
			throw new Exception("Fecha invalina: " + linea);
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
		if ((start = linea.indexOf("/")) == -1)
			throw new Exception("Fecha invalida: " + linea);
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
		if (anioDesde != anioHasta && mes < 6)
			anioDesde = anioHasta;
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
		JParserChile parser = new JParserChile();
		IFinderChile finder = new IFinderChile() {

			public void detectDetail(BizChileDetalle detail) throws Exception {
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

			public void detectHeader(BizChileCabecera header) throws Exception {
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

			public void detectTax(BizChileImpuesto tax) throws Exception {
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
