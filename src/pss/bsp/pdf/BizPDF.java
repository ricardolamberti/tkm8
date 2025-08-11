package  pss.bsp.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Date;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.pais.BizPais;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspParseo;
import pss.bsp.reembolsos.BizReembolso;
import pss.common.event.sql.BizSqlEvent;
import pss.common.security.BizUsuario;
import pss.core.data.files.JStreamGZip;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWins;
import pss.tourism.pnr.BizPNRTicket;

public class BizPDF extends JRecord implements IHeaderPDF {

	private JString pCompany=new JString();
	private JString pOwner=new JString();
	private JString pIdpdf=new JString();
	private JString pDescripcion=new JString();
	private JString pEstado=new JString();
	private JString pPais=new JString();
	private JDate pFechaDesde=new JDate();
	private JDate pFechaHasta=new JDate();
	private JLOB pPdf=new JLOB();
	private JString pPdfFilename=new JString();
	private JString pIdParseo=new JString();
	private JString pIata=new JString();
	private JBoolean pPendingBack =new JBoolean();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	BizPais pais;

	public BizPais getObjPais() throws Exception {
		if (pais!=null) return pais;
		BizPais p=new BizPais();
		p.read(getPais());
		return (pais=p);
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setPais(String zValue) throws Exception {
		pPais.setValue(zValue);
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setOwner(String zValue) throws Exception {
		pOwner.setValue(zValue);
	}

	public String getOwner() throws Exception {
		return pOwner.getValue();
	}

	public boolean isNullOwner() throws Exception {
		return pOwner.isNull();
	}

	public void setNullToOwner() throws Exception {
		pOwner.setNull();
	}

	public void setIdpdf(String zValue) throws Exception {
		pIdpdf.setValue(zValue);
	}

	public String getIdpdf() throws Exception {
		return pIdpdf.getValue();
	}

	public boolean isNullIdpdf() throws Exception {
		return pIdpdf.isNull();
	}

	public void setNullToIdpdf() throws Exception {
		pIdpdf.setNull();
	}

	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public boolean isNullDescripcion() throws Exception {
		return pDescripcion.isNull();
	}

	public void setNullToDescripcion() throws Exception {
		pDescripcion.setNull();
	}

	public void setEstado(String zValue) throws Exception {
		pEstado.setValue(zValue);
	}

	public String getEstado() throws Exception {
		return pEstado.getValue();
	}

	public boolean isNullEstado() throws Exception {
		return pEstado.isNull();
	}

	public void setNullToEstado() throws Exception {
		pEstado.setNull();
	}

	public void setFechaDesde(Date zValue) throws Exception {
		pFechaDesde.setValue(zValue);
	}

	public Date getPeriodoDesde() throws Exception {
		return pFechaDesde.getValue();
	}

	public boolean isNullFechaDesde() throws Exception {
		return pFechaDesde.isNull();
	}

	public void setNullToFechaDesde() throws Exception {
		pFechaDesde.setNull();
	}

	public void setFechaHasta(Date zValue) throws Exception {
		pFechaHasta.setValue(zValue);
	}

	public Date getPeriodoHasta() throws Exception {
		return pFechaHasta.getValue();
	}

	public boolean isNullFechaHasta() throws Exception {
		return pFechaHasta.isNull();
	}

	public void setNullToFechaHasta() throws Exception {
		pFechaHasta.setNull();
	}

	public void setPdf(String zValue) throws Exception {
		pPdf.setValue(zValue);
	}

	public String getPdf() throws Exception {
		return pPdf.getValue();
	}

	public boolean isNullPdf() throws Exception {
		return pPdf.isNull();
	}

	public void setNullToPdf() throws Exception {
		pPdf.setNull();
	}

	public void setPdfFilename(String zValue) throws Exception {
		pPdfFilename.setValue(zValue);
	}

	public String getPdfFilename() throws Exception {
		return pPdfFilename.getValue();
	}
	public void setParseo(String zValue) throws Exception {
		pIdParseo.setValue(zValue);
	}

	public String getParseo() throws Exception {
		return pIdParseo.getValue();
	}
	public void setIata(String zValue) throws Exception {
		pIata.setValue(zValue);
	}

	public String getIata() throws Exception {
		return pIata.getValue();
	}
	
	public boolean getPendingBack() throws Exception {
		return pPendingBack.getValue();
	}
	public void setPendingBack(boolean zValue) throws Exception {
		pPendingBack.setValue(zValue);
	}

	/**
	 * Constructor de la Clase
	 */
	public BizPDF() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("idPDF", pIdpdf);
		this.addItem("descripcion", pDescripcion);
		this.addItem("estado", pEstado);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);
		this.addItem("pdf", pPdf);
		this.addItem("pais", pPais);
		this.addItem("id_parseo", pIdParseo);
		this.addItem("iata", pIata);
		this.addItem("pdffilename", pPdfFilename);
		this.addItem("pending_back", pPendingBack);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "idPDF", "Idpdf", true, true, 300);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 300);
		this.addFixedItem(FIELD, "estado", "Estado", true, false, 50);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha desde", true, false, 20);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha hasta", true, false, 20);
		this.addFixedItem(FIELD, "pdf", "Pdf", true, false, 0);
		this.addFixedItem(FIELD, "pais", "pais", true, true, 50);
		this.addFixedItem(FIELD, "id_parseo", "Parseador", true, true, 50);
		this.addFixedItem(FIELD, "iata", "Iata", true, true, 50);
		this.addFixedItem(VIRTUAL, "pdffilename", "pdffilename", true, true, 200);
		this.addFixedItem(FIELD, "pending_back", "pendiente back", true, false, 1);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_PDF";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany,  String zIdpdf) throws Exception {
		addFilter("company", zCompany);
//		addFilter("owner", zOwner);
		addFilter("idPDF", zIdpdf);
		return read();
	}

	public IParseo tryParser(String filename) throws Exception {
		BizPais pais=BizBSPUser.getUsrBSP().getObjBSPPais();
		setPais(pais.getPais());
		IParseo[] parseos=JParseoFactory.getInstance(pais.getIdparserPdf(),determineTipoInterfaz(filename));
		IParseo lastParser=null;
		for(IParseo parseo:parseos) {
			try {
				parseo.setCompany(getCompany());
				parseo.setOwner(getOwner());
				FileInputStream file=new FileInputStream(filename);
				try {
					lastParser=parseo;
					parseo.setFilename(filename);
					parseo.setInput(file);
					parseo.execute();
					if (parseo.getId().equals(""))
						continue;
				} finally {
					file.close();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}			
		}

		return lastParser;
	}
	private String determineTipoInterfaz(String filename) throws Exception {
			if (filename.toLowerCase().indexOf(".pdf")!=-1) return "PDF";
		if (filename.toLowerCase().indexOf(".csv")!=-1) return "CSV";
		if (filename.toLowerCase().indexOf(".tab")!=-1) return "TAB";
		if (filename.toLowerCase().indexOf(".comma")!=-1) return "COMA";
		if (filename.toLowerCase().indexOf(".txt")!=-1) return "TXT";
		if (filename.toLowerCase().indexOf(".xlsx")!=-1) return "XLSX";
		if (filename.toLowerCase().indexOf(".xls")!=-1) return "XLS";
		if (filename.toLowerCase().indexOf(".rtf")!=-1) return "RTF";
		throw new Exception("Tipo de archivo deconocido");
	}
	@Override
	public void processDelete() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(this.getParseo());
		parseo.eliminar(getCompany(), getOwner(), getIdpdf());
		processBorrarReembolsos();
		super.processDelete();
	}
	public void processBorrarReembolsos() throws Exception {
		BizReembolso reems = new BizReembolso();
		reems.addFilter("company", getCompany());
		reems.addFilter("origen", getIdpdf());
		reems.deleteMultiple();
	}
	static long id=0;
	@Override
	public void processInsert() throws Exception {
		String filename=getPdfFilename();
		if (filename.equals("")) throw new Exception("No se especifico archivo");
		filename=URLDecoder.decode(filename);
		id++;
		if (filename.toLowerCase().endsWith(".zip")) {
				JTools.DeleteFiles(JPath.PssPathInputZIP()+"/unzipped"+(id));
				JStreamGZip.unzipFileForDirectory(filename, null, JPath.PssPathInputZIP()+"/unzipped"+(id));
				File oFile=new File( JPath.PssPathInputZIP()+"/unzipped"+(id));
				generarDirBSP(oFile);
				JTools.DeleteFiles(JPath.PssPathInputZIP()+"/unzipped"+(id));
				oFile.delete();
		} else {
			generarBSP(filename);
		}
	}
	public void generarDirBSP(File dir) throws Exception {
		File[] oFiles=dir.listFiles();
		if (oFiles != null) {
			for (int lIndex = 0; lIndex < oFiles.length; lIndex++) {
				if (oFiles[lIndex].isDirectory()) {
					generarDirBSP(oFiles[lIndex]);
					continue;
				}
				if (oFiles[lIndex].isFile()) {
					generarBSP(oFiles[lIndex].getAbsolutePath());
				}
			}
		}
	}
	public void generarBSP(String filename) throws Exception {
		PssLogger.logDebug("Procesando: "+filename);
		IParseo parseo=tryParser(filename);
		setIdpdf(parseo.getId());
		setParseo(parseo.getIdPareador());
		setFechaDesde(parseo.getPeriodoDesde());
		setFechaHasta(parseo.getPeriodoHasta());
		
		if (this.isNullDescripcion()) {
			File f = new File(filename);
			this.setDescripcion(f.getName());
		}
		setPendingBack(true);
		setEstado("PENDIENTE");
		setIata(parseo.getIATA());
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
//		tomarReembolsos();
//		autoCompleteDK();
	}
	
	public void execProcessToPNRs() throws Exception {
		JExec oExec = new JExec(this, "processToPNRs") {

			@Override
			public void Do() throws Exception {
				processToPNRs();
			}
		};
		oExec.execute();
	}
	public void execProcessRepoblate() throws Exception {
		try {
			JRecords<BizSqlEvent> records = new JRecords<BizSqlEvent>(BizSqlEvent.class);
			records.addFilter("company",getCompany());
			records.addFilter("activo",true);
			JIterator<BizSqlEvent> ite = records.getStaticIterator();
			while (ite.hasMoreElements()) {
				BizSqlEvent ev =ite.nextElement();
				ev.execProcessPopulate(getPeriodoDesde(),null);
			}
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		

	}
	public void processToPNRs() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(getParseo());
		JWins wins= parseo.getGuisDetail(getCompany(), getOwner(), getIdpdf());
		JIterator<JRecord> it = wins.getRecords().getStaticIterator();
		while (it.hasMoreElements()) {
			JRecord rec= it.nextElement();
			if (!(rec instanceof IConciliable)) continue;
			IConciliable bsp = (IConciliable) rec;
			String boleto = bsp.getStringValue(IConciliable.BOLETOS, true);
			if (boleto==null) continue;
			BizPNRTicket ticket = new BizPNRTicket();
			ticket.dontThrowException(true);
			if (!ticket.ReadByBoleto(getCompany(), boleto)) {
				//RJL! OJO!
				continue;// deberia crearlo y luego ver los conflicts de entrada?
			} else {
/*				ticket.setCreationDate(bsp.getDateValue(IConciliable.FECHA));
 				ticket.setTarifaFactura(bsp.getDoubleValue(IConciliable.TARIFA));
 				ticket.setImpuestosTotalFactura(bsp.getDoubleValue(IConciliable.IMPUESTO_ACUM));
 				ticket.setImpuestoFactura(bsp.getDoubleValue(IConciliable.IMPUESTO_ACUM)-ticket.getIvaFactura());
 				
 				// tema con las comisiones a veces vienen negativas y a veces positivas... no se si esta bien esta solucion
 				if ((bsp.getDoubleValue(IConciliable.COMISION)>0 && bsp.getDoubleValue(IConciliable.TARIFA)>0) ||
 						(bsp.getDoubleValue(IConciliable.COMISION)<0 && bsp.getDoubleValue(IConciliable.TARIFA)<0)) {
	 				ticket.setComisionFactura(bsp.getDoubleValue(IConciliable.COMISION));
					ticket.setNetoFactura(bsp.getDoubleValue(IConciliable.TARIFA)-(bsp.getDoubleValue(IConciliable.COMISION)+bsp.getDoubleValue(IConciliable.COMISION_OVER)));
 				} else {
	 				ticket.setComisionFactura(-bsp.getDoubleValue(IConciliable.COMISION));
					ticket.setNetoFactura(bsp.getDoubleValue(IConciliable.TARIFA)+bsp.getDoubleValue(IConciliable.COMISION)-bsp.getDoubleValue(IConciliable.COMISION_OVER));
 				}
 				ticket.setComisionPerc(bsp.getDoubleValue(IConciliable.COMISION_PORC));
				ticket.setImporteover(""+bsp.getDoubleValue(IConciliable.COMISION_OVER));
*/				ticket.setVoid(bsp.getStringValue(IConciliable.OPERACION, true).indexOf("REFUND")!=-1);
			  if (ticket.isVoided()) {
			  	ticket.processAnular(null);
			  } else
			  	ticket.update();
			}
		}

		
		
		
		
	}
	public void execPendingBack() throws Exception {
		JExec oExec = new JExec(this, "procPendingBack") {

			@Override
			public void Do() throws Exception {
				procPendingBack();
			}
		};
		oExec.execute();
	}
	public void procPendingBack() throws Exception {
			tomarReembolsos();
			autoCompleteDK();
			setPendingBack(false);
			update();
	}
	public void execProcPendientes() throws Exception {
		JRecords<BizPDF> pdfs = new JRecords<BizPDF>(BizPDF.class);
		pdfs.addFilter("pending_back", true);
		JIterator<BizPDF> it=pdfs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPDF pdf = it.nextElement();
			pdf.execPendingBack();
		}
	}
	public void procPendientes(Date fdesde,Date fhasta) throws Exception {
		JRecords<BizPDF> pdfs = new JRecords<BizPDF>(BizPDF.class);
		pdfs.addFilter("pending_back", true);
		if (fdesde!=null && fhasta !=null) {
			pdfs.addFilter("fecha_desde", fhasta,"<=");
			pdfs.addFilter("fecha_hasta", fdesde,">=");
		} else if (fdesde!=null) {
			pdfs.addFilter("fecha_desde", fdesde,"<=");
			pdfs.addFilter("fecha_hasta", fdesde,">=");
			
		}
		JIterator<BizPDF> it=pdfs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPDF pdf = it.nextElement();
			pdf.procPendingBack();
		}
	}
	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}
	
	public void autoCompleteDK() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(getParseo());
		parseo.processDks(getCompany());
	}

	public void tomarReembolsos() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(getParseo());
		if (!parseo.supportRefunds()) return;
		JWins wins= parseo.getGuisDetail(getCompany(), getOwner(), getIdpdf());
		JIterator<JRecord> it = wins.getRecords().getStaticIterator();
		while (it.hasMoreElements()) {
			JRecord rec= it.nextElement();
			if (!(rec instanceof IReembolsables)) continue;
			IReembolsables bsp = (IReembolsables) rec;
			if (!bsp.isRefund()) continue;
		
			BizReembolso reem = new BizReembolso();
			reem.dontThrowException(true);
			if (reem.readByBoletoRfnd(getCompany(), bsp.getBoletoRfnd())) continue;
			reem.setCompany(getCompany());
			reem.setBoletoRfnd(bsp.getBoletoRfnd());
			reem.setBoleto(bsp.getBoleto());
			reem.setCarrier(bsp.getCarrier());
			reem.setFecha(bsp.getCreationDate());
			reem.setPeriodo(JDateTools.buildPeriod(bsp.getCreationDate()));
			reem.setFolio(bsp.getFolio());
			reem.setTax(bsp.getTax());
			reem.setTipo(bsp.getTipo());
			reem.setFolio(bsp.getFolio());
			reem.setTarifa(bsp.getTarifa());
			reem.setTotal(bsp.getTotalReembolso());
			reem.setFormaPago(bsp.getFormaPago());
			reem.setOrigen(getIdpdf());
			reem.processInsert();
			
			BizPNRTicket pnr = new BizPNRTicket();
			pnr.dontThrowException(true);
			if (!pnr.ReadByBoleto(getCompany(), bsp.getBoletoRfnd())) continue;
			JList<String> contratos = pnr.getContratoBack();
			JIterator<String> itC = contratos.getIterator();
			while (itC.hasMoreElements()) {
				String idContrato = itC.nextElement();
				BizDetalle det = pnr.getObjContrato(Long.parseLong(idContrato));
				if (det==null) continue;
				det.setRecalculeReembolsoAuto(true);
				det.update();
			}
			pnr.setRefund(true);
			pnr.update();
		}

		
		
		
		
	}
}
