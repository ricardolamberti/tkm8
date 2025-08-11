package pss.bsp.excel;

import java.io.InputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.bsp.pdf.mex.cabecera.BizMexCabecera;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.mex.impuesto.BizMexImpuesto;
import pss.bsp.pdf.mex2.IFinderMex2;
import pss.core.tools.PssLogger;
import pss.tourism.carrier.BizCarrier;

public class JParseoExcelVinc implements IParseo {
	
	IFinderMex2 finder;

	InputStream input;
	
	String company;

	String owner;

	String idParser;

	Date fechaDesde;

	Date fechaHasta;
	
	String idPdf;

	String iata = "0";

	public void privExecute() throws Exception {
		Workbook workbook = WorkbookFactory.create(input);
		idPdf=null;
		long linea=1;
		int numHojas = workbook.getNumberOfSheets();
		for (int i = 0; i < 1/*numHojas*/; i++) {
		    Sheet sheet = workbook.getSheetAt(i);
		    String nombreHoja = sheet.getSheetName();;
		     if (sheet == null) continue;

		    for (Row row : sheet) {
		        if (getCellString(row, 1).equals("TACN"))
		            continue; // encabezado
		        if (!getCellString(row, 0).equals("DET")) continue;
		        if (idPdf==null) {
		        	idPdf=getCellString(row, 9)+"_"+getCellString(row, 7);
		        }
		        
		        BizMexDetalle detalle = new BizMexDetalle();
		        detalle.setCompany(company);
		        detalle.setOwner(owner);
		        detalle.setIdpdf(idPdf);
		        detalle.setLinea(linea++);

		        String tacn = getCellString(row, 1); // TACN
		        detalle.setIdAerolinea(tacn);
		        BizCarrier carr = new BizCarrier();
		        carr.dontThrowException(true);
		        if (carr.ReadIata(tacn))
		        	detalle.setAerolinea(carr.getCarrier());

		        detalle.setFecha(getCellDate(row,9)); // DAIS
		        if (detalle.getFecha()==null)
		        	PssLogger.logError("log point");
		        if (detalle.getFecha()!=null&&fechaDesde==null||detalle.getFecha().before(fechaDesde))
		        	fechaDesde = detalle.getFecha();
		        if (detalle.getFecha()!=null&&fechaHasta==null||detalle.getFecha().after(fechaHasta))
		        	fechaHasta = detalle.getFecha();
		        String boleto = getCellString(row, 7); // TDNR
		        detalle.setBoleto(boleto);
		        detalle.setBoletoLargo(boleto);
		        detalle.setAerolineaBoleto(tacn + " " + boleto);

		        String operacion = getCellString(row, 6);
//		        if (nombreHoja.equals("RFND") || nombreHoja.equals("ADM") || nombreHoja.equals("ACM")) {
//		            detalle.setOperacion(nombreHoja);
//		        } else {
		            detalle.setOperacion(operacion);
//		        }

		        detalle.setAnulado("CANX".equals(detalle.getOperacion()));
		        detalle.setTipoRuta(getCellString(row, 26)); // STAT

		        double cobl = getCellNumeric(row, 12);
		        double tax = getCellNumeric(row, 21);
		        double ivacom = getCellNumeric(row, 20);
		        double comision = getCellNumeric(row, 18);
		        double cash = getCellNumeric(row, 29);
		        double cred = getCellNumeric(row, 30);
		        double comover = 0;//getCellNumeric(row, 18);
		        double fee=getCellNumeric(row, 22);
		        detalle.setTarifa(cobl);
		        detalle.setBaseImponible(cobl);
		        detalle.setTotal(cash + ivacom + comision + comover);
		        detalle.setComision(comision);
		        detalle.setComisionOver(comover);
		        detalle.setComisionPorc(getCellNumeric(row, 17)*100);
		        detalle.setImpuesto1(tax);
		        detalle.setImpuesto2(fee);
		        detalle.setImpuestoAcum(tax+fee);
		        detalle.setImpSobrecom(ivacom);
		        detalle.setContadoBruto(cash );
		        detalle.setContado(cash-(cash != 0 ? tax : 0));
		        detalle.setTarjetaBruto(cred );
		        detalle.setTarjeta(cred- (cash == 0 ? tax : 0));
		        String pCIN = getCellString(row, 40);
		        detalle.setTipoTarjeta(pCIN.length() >= 4 ? pCIN.substring(2, 4) : "");
		        detalle.setNumeroTarjeta(pCIN.length() >= 4 ? pCIN.substring(pCIN.length() - 4) : "");
		        detalle.setObservaciones("");

		        // Columnas adicionales
		        detalle.setAGTN(getCellString(row, 2));
		        detalle.setRPSI(getCellString(row, 3));
		        detalle.setCAT(getCellString(row, 4));
		        detalle.setCLASS(getCellString(row, 5));
		        detalle.setCDGT(getCellString(row, 8));
		        detalle.setCPUI(getCellString(row, 10));
		        detalle.setCUTP(getCellString(row, 11));
		        detalle.setSPRT(getCellNumeric(row, 15)*100);
		        detalle.setEFRT(getCellNumeric(row, 17)*100);
		        detalle.setEFCO(getCellNumeric(row, 18));
		        detalle.setREMT(getCellString(row, 19));
		        detalle.setSPAN(getCellNumeric(row, 15));
			      detalle.setTOCA(getCellNumeric(row, 20));
		        detalle.setFEES(getCellNumeric(row, 22));
		        detalle.setPEN(getCellNumeric(row, 23));
		        detalle.setNRID(getCellString(row, 24));
		        detalle.setNRMETH(getCellString(row, 25));
		        detalle.setTOUR(getCellString(row, 27));
		        detalle.setWAVR(getCellString(row, 28));
		        detalle.setRELT1(getCellString(row, 31));
		        detalle.setRTDN1(getCellString(row, 32));
		        detalle.setRTCP1(getCellString(row, 33));
		        detalle.setRELT2(getCellString(row, 34));
		        detalle.setRTDN2(getCellString(row, 36));
		        detalle.setRTCP2(getCellString(row, 37));
		        detalle.setICDN(getCellString(row, 38));
		        detalle.setESAC(getCellString(row, 38));
		        detalle.setTXCALC(getCellString(row, 39));
		        detalle.setTRANSID(getCellString(row, 41));
		        detalle.setCCVR(getCellString(row, 42));
		        detalle.setTRNC(operacion);
		        detalle.setTDNR(boleto);
		        detalle.setDAIS(getCellString(row, 9));
		        detalle.setSTAT(getCellString(row, 26));
		        detalle.setTACN(getCellString(row, 1));
		        detalle.setCOBL(cobl);
		        detalle.setTAX(tax);
		        detalle.setCASH(getCellNumeric(row, 29));
		        detalle.setCRED(getCellNumeric(row, 30));
		        detalle.setDET(getCellString(row, 0));
		        BizMexImpuesto objTax = new BizMexImpuesto();
		        objTax.setCompany(company);
		        objTax.setIdpdf(idPdf);
		        objTax.setIso("TX");
		        objTax.setOwner(owner);
		        objTax.setLinea(detalle.getLinea());
		        objTax.setContado(detalle.getImpuesto1());
		        objTax.setTarjeta(detalle.getImpuesto2());
		        finder.detectDetail(detalle);
		        finder.detectTax(objTax);
		    }
		}
		cabecera.setCompany(company);
		cabecera.setIdpdf(idPdf);
		cabecera.setFullData(true);
		cabecera.setPeriodoDesde(fechaDesde);
		cabecera.setPeriodoHasta(fechaHasta);
		cabecera.setIata("");
    finder.detectHeader(cabecera);
  	
		workbook.close();
		
		
	  	
	}

	private String getCellString(Row row, int index) {
    Cell cell = row.getCell(index);
    if (cell == null) return "";
    
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue().trim();
        case NUMERIC:
            return String.valueOf((long) cell.getNumericCellValue()); // fuerza sin notación científica
        case FORMULA:
            return cell.getCellFormula();
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        default:
            return cell.toString().trim();
    }
}
	private Date getCellDate(Row row, int index) {
    Cell cell = row.getCell(index);
    if (cell == null) return null;

    if (cell.getCellType() == CellType.NUMERIC && org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
        return cell.getDateCellValue();
    }

    String text = getCellString(row, index).replaceAll("[^0-9]", ""); // quita / o -

    if (text.length() == 6) {
        // Formato YYMMDD
        int year = Integer.parseInt(text.substring(0, 2));
        int month = Integer.parseInt(text.substring(2, 4));
        int day = Integer.parseInt(text.substring(4, 6));

        year = (year < 80) ? 2000 + year : 1900 + year;

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year);
        cal.set(java.util.Calendar.MONTH, month - 1);
        cal.set(java.util.Calendar.DAY_OF_MONTH, day);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Si viene como texto completo dd/MM/yyyy o yyyy-MM-dd, intentar parseo estándar
    String[] patterns = { "dd/MM/yyyy", "yyyy-MM-dd", "dd-MM-yyyy", "yyyy/MM/dd", "dd/MM/yy", "yy/MM/dd" };
    for (String pattern : patterns) {
        try {
            return new java.text.SimpleDateFormat(pattern).parse(text);
        } catch (Exception e) {
            // probar el siguiente patrón
        }
    }

    return null;
}



	private double getCellNumeric(Row row, int index) {
    Cell cell = row.getCell(index);
    if (cell == null) return 0.0;

    switch (cell.getCellType()) {
        case NUMERIC:
            return cell.getNumericCellValue();
        case STRING:
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return 0.0; // o lanzar excepción si preferís
            }
        case FORMULA:
            switch (cell.getCachedFormulaResultType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    try {
                        return Double.parseDouble(cell.getStringCellValue().trim());
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                default:
                    return 0.0;
            }
        default:
            return 0.0;
    }
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
		// ignorado, porque este parser genera su propio id
	}

	public void addListenerDetect(IFinder finder) {
		this.finder = (IFinderMex2) finder;
	}



	// Implementar métodos faltantes de IParseo...
	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	public String getTableDetalle() throws Exception {
		return new BizMexDetalle().GetTable();
	}
	BizMexCabecera cabecera;

	BizMexDetalle detalle;

	boolean finalizar=false;
	BizMexImpuesto impuesto = null;
	BizMexImpuesto impuesto2 = null;
	BizMexImpuesto impuesto3 = null;
	public void execute() throws Exception {
		cabecera = new BizMexCabecera();
		detalle = new BizMexDetalle();

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

	public void setFechaDesde(Date zFecha) throws Exception {

	}

	public void setFechaHasta(Date zFecha) throws Exception {
		
	}


	@Override
	public String getIATA() throws Exception {
		return iata;
	}

}

//Columna	Significado
//DET	Tipo de documento (ej: TKT, EMD).
//TACN	Código IATA de la aerolínea.
//AGTN	Número del agente de viajes (IATA code).
//RPSI	Indicador de reporte (ej. original, duplicado).
//CAT	Categoría del boleto (ej. 001 – normal).
//CLASS	Clase de servicio del boleto (Y, J, C, etc.).
//TRNC	Tipo de transacción (TKT = emisión, RFND = reembolso, CANX = cancelación).
//TDNR	Número del billete (ej: 3127588397).
//CDGT	Código del punto de venta (Office ID).
//DAIS	Fecha de emisión (normalmente YYMMDD).
//CPUI	Código del punto de emisión.
//CUTP	Tipo de cliente (ej. empresa, individual).
//COBL	Tarifa base del boleto (sin impuestos).
//CORT	Porcentaje de comisión.
//COAM	Comisión neta para el agente.
//SPRT	Soporte adicional o descuento aplicado.
//SPAM	Impuesto sobre la comisión.
//EFRT	Código de esfuerzo comercial.
//EFCO	Comisión extra o override.
//REMT	Medio de reembolso o método de pago (ej. efectivo, tarjeta).
//TOCA	Código de tipo de cobro.
//TAX	Total de impuestos aplicados.
//FEES	Cargos adicionales (ej: fee de emisión).
//PEN	Penalidades por cambios o devoluciones.
//NRID	ID del número de remisión.
//NRMETH	Método de remisión (manual, electrónico, etc.).
//STAT	Estado de la ruta (D = doméstico, I = internacional).
//TOUR	Código de tour o promoción asociada.
//WAVR	Códigos de exención o waiver.
//CASH	Monto pagado en efectivo.
//CRED	Monto pagado con tarjeta.
//RELT1	Relación de vuelo 1 (relación leg 1).
//RTDN1	Ruta destino 1.
//RTCP1	Código de tarifa 1.
//RELT2	Relación de vuelo 2.
//RTDN2	Ruta destino 2.
//RTCP2	Código de tarifa 2.
//ICDN	Código interno del documento.
//ESAC	Código de exención o cargo especial.
//TXCALC	Tipo de cálculo de impuestos (manual, automático).
//PCIN	Código parcial de tarjeta de crédito (usado para identificar tipo y últimos dígitos).
//TRANSID	ID de la transacción (número único por transacción).
//CCVR	Código de verificación de tarjeta de crédito (o CVR log).
