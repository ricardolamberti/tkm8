package pss.bsp.consolid.model.liquidacion.terrestres;

import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.core.tools.ExcelTextParser;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class JParseoTerrestre implements IParseo {

	IFinderTerrestre finder;

	InputStream input;

	String idPdf;


	Date fechaDesde;

	Date fechaHasta;

	boolean detectHeader = true;


	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}
  BizLiqHeader cabecera;
 
	BizLiqDetail detalle;

	long nextLinea = 0;

	String actual_aerolinea=null;
	String company;

	String owner;

	String idParser;

	boolean pendingDetail = false;

	public String getTableDetalle() throws Exception {
		return new BizLiqDetail().GetTable();
	}

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
		idPdf = zId;
	}

	public void addListenerDetect(IFinder finder) {
		this.finder = (IFinderTerrestre) finder;
	}

	private String getContent() {
		
		ExcelTextParser parser=new ExcelTextParser();
		return parser.toCSV(input);
	}

	protected void privExecute() throws Exception {

		String content = getContent();

		if (content == null) throw new Exception("El archivo no parece ser una interfaz BSP");
		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for (String linea : content.split("\r\n|\r|\n")) {
			PssLogger.logInfo(linea);
			parserLinea(linea);
		}
		closePendingDetail();
	}

	protected void parserLinea(String linea) throws Exception {
		if (detectHeader) detectHeader(linea);
		detectLinea(linea);
		readyHeader();
	}

	
	public boolean detectLinea(String linea) throws Exception {
		// 5992039919 5 VVVV 21/02
		if (!JTools.isNumber(linea.charAt(0))&&!linea.trim().startsWith("; ; ; ; ; ; ;")&&!linea.trim().startsWith(";")) {
			return false;
		}
		if (linea.indexOf("T029180535")!=-1)
			PssLogger.logInfo("log point");
		closePendingDetail();
		startNewDetail();
		int c=0;
		PssLogger.logInfo("Terrestre: "+linea);
		StringTokenizer toks = new StringTokenizer(linea,";");
		if (toks.countTokens()==17) {
			while (toks.hasMoreTokens()) {
				String sCont = toks.nextToken();
				switch (c) {
				case 0: //FECHA_PERIODO
					detalle.setFechaDoc(JDateTools.StringToDate(JDateTools.convertMonthLiteralToNumber(sCont.substring(3,5)+"/"+sCont.substring(5)),"dd-MM-yy"));
					break;
				case 1: //AEROLINEA
					detalle.setPrestador(sCont);
					break;
				case 2: //NUMEROBOLETO
					detalle.setNroBoleto(sCont);
					break;
				case 3: //SERVICIO
					detalle.setTipoServicio(sCont);
					break;
				case 4: //DK
					detalle.setDk(sCont);
					break;
				case 5: //SERIE
					detalle.setTipoDoc(sCont);
					break;
				case 6: //NUMERO
					detalle.setNroDoc(sCont);
					break;
				case 7: //FECHA
					try {
						detalle.setFechaDoc(JDateTools.StringToDate(sCont, "d/M/YYYY"));
					} catch (Exception e) {
							detalle.setFechaDoc(JDateTools.StringToDate(JDateTools.convertMonthLiteralToNumber(sCont), "dd/MM/YYYY"));
					
													}
					break;
				case 8: //RUTA
					detalle.setRuta(sCont);
					break;
				case 9: //CLASE
					detalle.setClase(sCont);
					break;
				case 10: //CLASES
					detalle.setClases(sCont);
					break;
				case 11: //LINEAS
					detalle.setLineas(sCont);
					break;
				case 12: //TARIFABASE
					detalle.setTarifa(JTools.getDoubleNumberEmbedded(sCont));
					break;
			case 13: //FORMADEPAG
					detalle.setFormaPago(sCont);
					break;
				case 14: //PCT
					detalle.setPorcComision(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 15: //COMISIONBASE
					detalle.setComision(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 16: //trx id
					detalle.setCustomerTrxId(JTools.getLongNumberEmbedded(sCont));
					break;
				case 17: //org
					if (JTools.isNumberPure(sCont)) {
						if (sCont.equals("345"))
							detalle.setOrganization("CNS");
						else
							detalle.setOrganization("TVF");
						}
					else
						detalle.setOrganization(sCont);
					break;
				}
				c++;
			}
		} else if (toks.countTokens()==29) {
			while (toks.hasMoreTokens()) {
				String sCont = toks.nextToken();
				switch (c) {
				case 0: //FECHA_PERIODO
					try {
						detalle.setFechaDoc(JDateTools.StringToDate(sCont,"d/MM/yyyy"));
					} catch (Exception e) {
						detalle.setFechaDoc(JDateTools.StringToDate(JDateTools.convertMonthLiteralToNumber(sCont),"dd-MM-yy"));
					}
					break;
				case 1: //DK
					detalle.setDk(sCont);
					break;
				case 2: //trkid
					
					break;
				case 3: //loca
					
					break;
				case 4: //trx
				
					break;
				case 5: //moneda
						break;
				case 6: //forma de pago
					detalle.setFormaPago(sCont);
					break;
				case 7: //tarj
					break;
				case 8: //correo
					break;
				case 9: //tipo camb 
					break;
				case 10: //tarifa baseASE
					detalle.setTarifa(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 11: //fecha cupon
					break;
				case 12: //reserva
					break;
				case 13: //TARIFABASE
					detalle.setPorcComision(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 14: //comision
					if (sCont.length()>7) 
						PssLogger.logInfo("log point");
					detalle.setComision(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 15: //Origen
					break;
				case 16: //Archivo
					break;
				case 17: //factura
					detalle.setNroDoc(sCont);
					break;
				case 18: //servicio
					detalle.setTipoServicio(sCont);
					break;
				case 19: //pasajero
					detalle.setPasajero(sCont);
					break;
				case 20: //descrip
					detalle.setRuta(sCont);
					break;
				case 21: //iva
					detalle.setIva(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 22: //monto
					detalle.setTarifa(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 23: //monto
					detalle.setImporte(JTools.getDoubleNumberEmbedded(sCont.replace(",", ".")));
					break;
				case 24: //destino
					break;
				case 25: //ncl
					break;
				case 26: //fecha serv
					break;
				case 27: //doc tipo
					detalle.setTipoDoc(sCont);;
					break;
				case 28: //COMItrx idSIONBASE
					detalle.setCustomerTrxId(JTools.getLongNumberEmbedded(sCont));
					break;
				case 29: //org
					if (JTools.isNumberPure(sCont)) {
						if (sCont.equals("345"))
							detalle.setOrganization("CNS");
						else
							detalle.setOrganization("TVF");
						}
					else
						detalle.setOrganization(sCont);
					break;		
				
				}
				c++;
			}
		} else {
			while (toks.hasMoreTokens()) {
				String sCont = toks.nextToken();
				switch (c) {
				case 0: //FECHA_PERIODO
					//detalle.setOrigen(sCont);
					break;
				case 1: //AEROLINEA
					detalle.setPrestador(sCont);
					break;
				case 2: //NUMEROBOLETO
					detalle.setNroBoleto(sCont);
					break;
				case 3: //SERVICIO
					detalle.setTipoServicio(sCont);
					break;
				case 4: //DK
					detalle.setDk(sCont);
					break;
				case 5: //SERIE
					detalle.setTipoDoc(sCont);
					break;
				case 6: //NUMERO
					detalle.setNroDoc(sCont);
					break;
				case 7: //FECHA
					try {
						detalle.setFechaDoc(JDateTools.StringToDate(sCont, "d/MM/yyyy"));
					} catch (Exception e) {
							detalle.setFechaDoc(JDateTools.StringToDate(JDateTools.convertMonthLiteralToNumber(sCont), "dd/MM/yyyy"));
					}
					break;
				case 8: //RUTA
					detalle.setRuta(sCont);
					break;
				case 9: //PASAJERO 
					detalle.setPasajero(sCont);
					break;
				case 10: //CLASE
					detalle.setClase(sCont);
					break;
				case 11: //CLASES
					detalle.setClases(sCont);
					break;
				case 12: //LINEAS
					detalle.setLineas(sCont);
					break;
				case 13: //TARIFABASE
					detalle.setTarifa(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 14: //IVA
					detalle.setIva(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 15: //TUA
					detalle.setTua(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 16: //TOTAL
					detalle.setImporte(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 17: //FORMADEPAG
					detalle.setFormaPago(sCont);
					break;
				case 18: //PCT
					detalle.setPorcComision(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 19: //COMISIONBASE
					detalle.setComision(JTools.getDoubleNumberEmbedded(sCont));
					break;
				case 20: //CLAVERESERVACION
					detalle.setReserva(sCont);
					break;
				case 21: //TIPO
					detalle.setTipo(sCont);
					break;
				case 22: //UO
					break;
				case 23: //trx id
					detalle.setCustomerTrxId(JTools.getLongNumberEmbedded(sCont));
					break;
				case 24: //org
					if (JTools.isNumberPure(sCont)) {
						if (sCont.equals("345"))
							detalle.setOrganization("CNS");
						else
							detalle.setOrganization("TVF");
						}
					else
						detalle.setOrganization(sCont);
					break;
			
				}
				c++;
			}
		}
		
		return true;
	}
	
	public void startNewDetail() throws Exception {
		pendingDetail = true;
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
//			if (actual_operacion.indexOf("TICKET")!=-1 && actual_aerolinea.indexOf("IATA")==-1) {
				readyDetail();
		}
		pendingDetail = false;
	}



	private void readyDetail() throws Exception {
		detalle.setCompany(company);
		detalle.setLiquidacionId(Long.parseLong(idPdf));
		detalle.setOrigen("XSLX");
		finder.detectDetail(detalle);
		detalle = new BizLiqDetail();
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0) return 0;
		String num = linea.replaceAll("\\,", "");
		try {
			boolean neg = linea.indexOf("-") != -1;
			num = num.replace(",", ".");
			num = JTools.getNumberEmbeddedWithSep(num);
			return (neg ? -1 : 1) * Double.parseDouble(num);
		} catch (Exception e) {
		  PssLogger.logError("Parser Error "+num+" en "+linea);
			throw e;
		}
	}


	public void execute() throws Exception {
		cabecera = new BizLiqHeader();
		detalle = new BizLiqDetail();
		try {
			finder.start();
			privExecute();
			finder.stop();
		} catch (Exception e) {
			PssLogger.logError(e);
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
		fechaDesde=zFecha;
	}

	public void setFechaHasta(Date zFecha) throws Exception {
		fechaHasta=zFecha;
	}

	public String getFormat() throws Exception {
		// no es de interes para este parseador
		return null;
	}



	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HEADER
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void detectHeader(String linea) throws Exception {
		detectHeaderSingle(linea);
	}
	private void detectHeaderSingle(String linea) throws Exception {
		if (!JTools.isNumber(linea.charAt(0))) detectHeader=true;
			return;
	}

	
	private void readyHeader() throws Exception {

	}

	@Override
	public Date getPeriodoDesde() throws Exception {
		return null;
	}

	@Override
	public Date getPeriodoHasta() throws Exception {
		return null;
	}

	@Override
	public String getIATA() throws Exception {
		return null;
	}



}
