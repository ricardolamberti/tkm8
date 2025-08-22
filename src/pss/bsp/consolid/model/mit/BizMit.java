package pss.bsp.consolid.model.mit;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import pss.bsp.consolid.model.mit.conciliacion.BizMitConciliacion;
import pss.bsp.consolid.model.mit.detail.BizMitDetail;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.biblioteca.BizOldBiblioteca;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.tourism.pnr.BizPNRTicket;

public class BizMit extends JRecord {
	public static final String ESTADO_OK = "OK";
	public static final String ESTADO_ERROR = "NO_ENCONTRADO";
	public static final String ESTADO_MONTO_DISTINTO = "MONTO_DISTINTO";
	public static final String ESTADO_FALTA_CANCELACION = "FALTA_CANCELACION";
	public static final String ESTADO_FALTA_VOID = "FALTA_VOID";
	public static final String ESTADO_VOID_EN_RECHAZADO = "VOID_EN_RECHAZADO";
	public static final String ESTADO_VOID_EN_APROBADO = "VOID_EN_APROBADO";
	
	
	static JMap<String,String> estados;
  public static JMap<String,String> getEstados() throws Exception {
  	if (estados!=null) return estados;
  	JMap<String,String> maps = JCollectionFactory.createOrderedMap();
  	maps.addElement(ESTADO_OK, "Ok");
  	maps.addElement(ESTADO_ERROR, "No encontrado en TKM");
   	maps.addElement(ESTADO_MONTO_DISTINTO, "Monto Diferente");
  	maps.addElement(ESTADO_FALTA_CANCELACION, "Falta Cancelación");
   	maps.addElement(ESTADO_FALTA_VOID, "Falta Void");
  	maps.addElement(ESTADO_VOID_EN_RECHAZADO, "Void y rechazado");
  	maps.addElement(ESTADO_VOID_EN_APROBADO, "Void y Aprobado");
  	 return estados=maps;
  }
  // -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pOriginalReportFile = new JString();
	private JLong pOriginalReport = new JLong();
	private JDate pDateFrom = new JDate();
	private JDate pDateTo = new JDate();
	
	public void setId(long id) throws Exception {
		pId.setValue(id);
	}
	public long getId() throws Exception {
		return pId.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}

	public String getOriginalReportFile() throws Exception {
		return pOriginalReportFile.getValue();
	}

	public void setOriginalReportFile(String originalReportFile) throws Exception {
		pOriginalReportFile.setValue(originalReportFile);
	}

	public long getOriginalReport() throws Exception {
		return pOriginalReport.getValue();
	}

	public void setOriginalReport(long originalReport) throws Exception {
		pOriginalReport.setValue(originalReport);
	}

	

	public java.util.Date getDateFrom() throws Exception {
		return pDateFrom.getValue();
	}

	public void setDateFrom(java.util.Date dateFrom) throws Exception {
		pDateFrom.setValue(dateFrom);
	}

	public java.util.Date getDateTo() throws Exception {
		return pDateTo.getValue();
	}

	public void setDateTo(java.util.Date dateTo) throws Exception {
		pDateTo.setValue(dateTo);
	}

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizMit() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("original_report_file", pOriginalReportFile);
		addItem("original_report", pOriginalReport);
		addItem("date_from", pDateFrom);
		addItem("date_to", pDateTo);
		
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(VIRTUAL, "original_report_file", "Archivo original file", true, false, 1000);
		addFixedItem(FIELD, "original_report", "Archivo original", true, false, 64);
		addFixedItem(FIELD, "date_from", "Fecha desde", true, false, 25);
		addFixedItem(FIELD, "date_to", "Fecha hasta", true, false, 25);
	  

	}


	@Override
	public String GetTable() {
		return "BSP_MIT";
	}

  BizOldBiblioteca biblioOriginal;
  public BizOldBiblioteca getObjBibliotecaOriginal() throws Exception {
  	if (this.pOriginalReport.isNull()) return null;
  	if (this.biblioOriginal!=null) return this.biblioOriginal;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pOriginalReport.getValue());
  	return (this.biblioOriginal=b);
  }
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processDelete() throws Exception {
		BizMitDetail det  = new BizMitDetail();
		det.addFilter("company", getCompany());
		det.addFilter("id_report", getId());
		det.deleteMultiple();
		BizMitConciliacion detC  = new BizMitConciliacion();
		detC.addFilter("company", getCompany());
		detC.addFilter("id_mit", getId());
		detC.deleteMultiple();
		getObjBibliotecaOriginal().processDelete();
		super.processDelete();
	}

	@Override
	public void processInsert() throws Exception {
		String filename = getOriginalReportFile();
		if (filename.equals(""))
			throw new Exception("No se especifico archivo");
		filename = URLDecoder.decode(filename);
		String source = filename;
		int lastPunto = source.lastIndexOf('.') + 1;
		String type = source.substring(lastPunto);
		BizOldBiblioteca biblo = new BizOldBiblioteca();
		biblo.setContentByFilename(getCompany(), source, type);
		biblo.processInsert();
		
		this.setOriginalReport(biblo.getId());
		super.processInsert();
		this.setId(getIdentity("id"));
		readExcel(biblo.getContentAsByteArray(getCompany()));
		super.processUpdate();
		
	}
	

	public void readExcel(byte[] contenido) throws Exception {
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(contenido));
		Sheet sheet = workbook.getSheet("cPagos");
		if (sheet == null) throw new Exception("No se encontró la hoja 'cPagos'");
	// Buscar fila con texto tipo "Fecha: 18/04/2025 al 18/04/2025"
		for (int i = 0; i < 4; i++) {
			Row row = sheet.getRow(i);
			if (row == null) continue;

			Cell cell = row.getCell(0);
			if (cell == null || cell.getCellType() != CellType.STRING) continue;

			String texto = cell.getStringCellValue().trim();
			if (texto.startsWith("Fecha:")) {
				String rango = texto.substring(6).trim(); // quitar "Fecha:"
				String[] partes = rango.split("al");
				if (partes.length == 2) {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
					this.setDateFrom(sdf.parse(partes[0].trim()));
					this.setDateTo(sdf.parse(partes[1].trim()));
				}
				break;
			}
		}
		int rowStart = 4; // Los datos empiezan en la fila 5 (índice 4)
		for (int i = rowStart; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) continue;
			if (isEmpty(row)) continue;
			String tipoOp = getString(row, 2);
			if ("Tipo Operación".equalsIgnoreCase(tipoOp)) continue; // Saltar encabezado si accidentalmente se repite

			BizMitDetail det = new BizMitDetail();
			det.setCompany(this.getCompany());
			det.setReportId(this.getId());

			det.setFecha(getDate(row, 0));
			det.setNorOp(getString(row, 1));
			det.setTipoOp(getString(row, 2));
			det.setPnr(getString(row, 3));
			det.setNroTarjeta(getString(row, 4));
			det.setAgencia(getString(row, 5));
			det.setNombreAgencia(getString(row, 6));
			det.setImporte(getDouble(row, 7));
			det.setAerolinea(getString(row, 8));
			det.setStatus(getString(row, 9));
			det.setAutorizacion(getString(row, 10));
			det.setGlobalizador(getString(row, 11));

			for (int b = 0; b < 10; b++) {
				String val = getString(row, 12 + b);
				if (val != null && !val.isEmpty()) {
					det.setBoleto(b + 1, val);
				}
			}

			det.processInsert();
		}
		processConciliar();
	}

	private boolean isEmpty(Row row) {
		for (int i = 0; i <= 5; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != CellType.BLANK)
				return false;
		}
		return true;
	}

	private String getString(Row row, int index) {
		try {
			Cell cell = row.getCell(index);
			if (cell == null) return null;
			cell.setCellType(CellType.STRING);
			String val = cell.getStringCellValue();
			if (val == null) return null;
			// Eliminar espacios normales y NO-BREAK SPACE (U+00A0)
			return val.replace('\u00A0', ' ').trim();
		} catch (Exception e) {
			return null;
		}
	}
	private java.util.Date getDate(Row row, int index) {
		try {
			Cell cell = row.getCell(index);
			if (cell == null) return null;
			if (cell.getCellType() == CellType.NUMERIC) {
				return cell.getDateCellValue();
			} else {
				String val = cell.getStringCellValue().trim();
				if (val.startsWith(" ")) val = val.substring(1); // eliminar espacio no imprimible
				return new java.text.SimpleDateFormat("dd/MM/yyyy").parse(val);
			}
		} catch (Exception e) {
			return null;
		}
	}

	private double getDouble(Row row, int index) {
		try {
			Cell cell = row.getCell(index);
			if (cell == null) return 0;
			if (cell.getCellType() == CellType.NUMERIC) {
				return cell.getNumericCellValue();
			} else {
				String val = cell.getStringCellValue().replace(",", "").trim();
				if (val.startsWith(" ")) val = val.substring(1); // eliminar espacio no imprimible
				return Double.parseDouble(val);
			}
		} catch (Exception e) {
			return 0;
		}
	}
	public void clearConciliar() throws Exception {
		BizMitConciliacion det = new BizMitConciliacion();
		det.addFilter("Company", getCompany());
		det.addFilter("id_mit", getId());
		det.deleteMultiple();
	}
	private boolean tarjetasCoinciden(String a, String b) {
		if (a == null || b == null) return false;

		int len = Math.min(a.length(), b.length());

		for (int i = 0; i < len; i++) {
			char ca = a.charAt(i);
			char cb = b.charAt(i);

			// Si ambos caracteres son enmascarados, se ignoran
			if (isMasked(ca) || isMasked(cb)) {
				continue;
			}

			// Si son dígitos diferentes, no coinciden
			if (Character.isDigit(ca) && Character.isDigit(cb) && ca != cb) {
				return false;
			}
		}

		return true;
	}

	private boolean isMasked(char c) {
		return c == '*' || c == 'X' || c == 'x' || c == '0';
	}
	private void procesarGrupoConciliacion(List<BizMitDetail> grupo, String codPNR) throws Exception {
		double totalMIT = 0;
		double totalMITRech = 0;
		double totalPNR = 0;
		String estado = ESTADO_OK;
		StringBuilder detalle = new StringBuilder();
		boolean findBoleto = false;
		boolean tieneCancelacion = false;

		Set<String> boletosMIT = new HashSet<>();
		boolean tieneStatusCancelado = false;
		boolean tieneStatusRechazado = false;
		boolean tieneStatusAprobado = false;

		for (BizMitDetail det : grupo) {
			if (det.getNorOp().equals("133713779"))
				PssLogger.logDebug("log point");
			if (!det.getAutorizacion().equals("rechazada") && !tieneCancelacion)
				totalMIT += det.getImporte();
			else 
				totalMITRech =  det.getImporte();
			String status = det.getStatus();
			if ("CANCELACION".equalsIgnoreCase(det.getTipoOp())) {
				tieneCancelacion = true;
				totalMIT = det.getImporte();
			}
			if ("D".equalsIgnoreCase(status)) tieneStatusRechazado = true;
			if ("A".equalsIgnoreCase(status)) tieneStatusAprobado = true;

			for (int i = 1; i <= 10; i++) {
				if (det.hasBoleto(i)) {
					String boleto = det.getBoleto(i);
					if (boleto != null && boleto.length() >= 4)
						boletosMIT.add(boleto.substring(3,boleto.length()-1).trim());
				}
			}
		}

		if (totalMIT==0)
			totalMIT=totalMITRech;
		JRecords<BizPNRTicket> tkts = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		tkts.addFilter("company", getCompany());
		tkts.addFilter("codigopnr", codPNR);
		JIterator<BizPNRTicket> itTkt = tkts.getStaticIterator(); // Para iterar más de una vez

		boolean hayVoid = false;

		while (itTkt.hasMoreElements()) {
			BizPNRTicket tkt = itTkt.nextElement();
			if (boletosMIT.contains(tkt.getNumeroboleto())) {
				findBoleto = true;
				totalPNR += tkt.getMontoTarjeta();
				if (Boolean.TRUE.equals(tkt.isVoided())) hayVoid = true;

				for (BizMitDetail det : grupo) {
					String boleto = det.getBoleto(1);
					if (boleto.indexOf(tkt.getNumeroboleto())==-1) 
						continue;

					if (tkt.getObjCarrier() != null && tkt.getObjCarrier().getCodIata() != null &&
						(JTools.getLongFirstNumberEmbedded(tkt.getObjCarrier().getCodIata())!=JTools.getLongFirstNumberEmbedded(det.getAerolinea()))) {
						detalle.append("Placa distinta. cupones=["+tkt.getObjCarrier().getCodIata()+"] Mit=["+det.getAerolinea()+"]");
					}
					if (!tkt.getGDS().contains(det.getGlobalizador()) && !det.getGlobalizador().contains(tkt.getGDS())) {
						if (!(tkt.getGDS().equals("GALILEO") && det.getGlobalizador().equals("TRAVELPORT2")) &&
								!(tkt.getGDS().equals("TRAVELPORT") && det.getGlobalizador().equals("TRAVELPORT2")) )
							detalle.append("Globalizador distinta. cupones=["+tkt.getGDS()+"] Mit=["+det.getGlobalizador()+"]");
					}
					if (!tkt.getNroIata().contains(det.getAgencia())) {
						detalle.append("Iata distinta. cupones=["+tkt.getNroIata()+"] Mit=["+det.getAgencia()+"]");
					}
					if (!tarjetasCoinciden(tkt.getNumeroTarjetaEnmascarada(),det.getNroTarjeta()) ) {
						String str ="Nro.Tarjeta distinta. cupones=["+tkt.getNumeroTarjetaEnmascarada()+"] Mit=["+det.getNroTarjeta()+"]";
						if (detalle.indexOf(str)==-1)
							detalle.append(str);
					}
				}
			}
		}

			
		if (!findBoleto) {
			if (tieneStatusRechazado) {
				estado = ESTADO_OK;
				detalle.append("Rechazado");
			} else {
				estado = ESTADO_ERROR;
				detalle.append("No se encontró ningún cupón correspondiente.");
			}
		} else {
			if (Math.abs(Math.abs(totalMIT )- Math.abs( totalPNR)) > 0.1) {

				estado = ESTADO_MONTO_DISTINTO;
				detalle.append("Importe MIT=").append(""+totalMIT).append(" ≠ Cupones=").append(""+totalPNR).append(".");
			}
			if (tieneCancelacion && totalMIT >= 0) {
				estado = ESTADO_FALTA_CANCELACION;
				detalle.append("Cancelación sin reverso.");
			}
			if (tieneCancelacion && !hayVoid) {
				estado = ESTADO_FALTA_VOID;
				detalle.append("MIT indica CANCELADO pero no hay ningún boleto VOID.");
			}
			if (!tieneCancelacion && hayVoid) {
				estado = ESTADO_VOID_EN_APROBADO;
				detalle.append("MIT indica APROBADO pero hay boleto VOID.");
			}

		}
		double dif = Math.abs(totalMIT)-Math.abs(totalPNR);
		insertarConciliacion(grupo.get(0), totalMIT,totalPNR, dif, estado, detalle.toString());
	}
	public void execProcessGenerate() throws Exception {
		JExec oExec = new JExec(this, "processGenerate") {

			@Override
			public void Do() throws Exception {
				processConciliar();
			}
		};
		oExec.execute();
	}

	public void processConciliar() throws Exception {
		clearConciliar();
		JRecords<BizMitDetail> dets = new JRecords<BizMitDetail>(BizMitDetail.class);
		dets.addFilter("company", getCompany());
		dets.addFilter("id_report", getId());
		dets.addOrderBy("pnr", "ASC");

		JIterator<BizMitDetail> itMit = dets.getStaticIterator();

		String lastPnrKey = null;
		List<BizMitDetail> grupo = new ArrayList<>();

		while (itMit.hasMoreElements()) {
			BizMitDetail det = itMit.nextElement();

			String pnrFull = det.getPnr();
			if (pnrFull == null || !pnrFull.contains("/")) continue;
			String pnrKey = pnrFull.substring(pnrFull.indexOf("/") + 1).trim();
			if (pnrKey.equals("CX0JS9"))
				PssLogger.logDebug("log point");

			// Nuevo PNR => procesar grupo anterior
			if (lastPnrKey != null && !lastPnrKey.equals(pnrKey)) {
				procesarGrupoConciliacion(grupo, lastPnrKey);
				grupo.clear();
			}

			grupo.add(det);
			lastPnrKey = pnrKey;
		}

		// Procesar el último grupo
		if (!grupo.isEmpty()) {
			procesarGrupoConciliacion(grupo, lastPnrKey);
		}
	}

	private void insertarConciliacion(BizMitDetail det, double importeMIT,double importePNR,
      double diferencia, String estado, String detalle) throws Exception {
			BizMitConciliacion conc = new BizMitConciliacion();
			conc.setCompany( getCompany());
			conc.setIdMit(getId());
			conc.setNorOp(det.getNorOp());
			conc.setTipoOp( det.getTipoOp());
			conc.setPnr( det.getPnr());
			conc.setFecha( det.getFecha());
			conc.setImporteMit( importeMIT);
			conc.setImportePnr( importePNR);
			conc.setDiferencia( diferencia);
			conc.setEstadoConciliacion( estado);
			conc.setDetalle(detalle);
			conc.setFechaConciliacion(new Date());
			conc.processInsert();
	}


}
