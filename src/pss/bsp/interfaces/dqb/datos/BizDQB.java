package  pss.bsp.interfaces.dqb.datos;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Date;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.interfaces.dqb.detalle.BizDQBDetalle;
import pss.bsp.pais.BizPais;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspParseo;
import pss.bsp.parseo.bspParseo.IInterfazParseo;
import pss.common.security.BizUsuario;
import pss.core.data.files.JStreamGZip;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizPNRTicket;

public class BizDQB extends JRecord implements IHeaderDQB {

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

	/**
	 * Constructor de la Clase
	 */
	public BizDQB() throws Exception {
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
		this.addItem("pdffilename", pPdfFilename);
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
		this.addFixedItem(VIRTUAL, "pdffilename", "pdffilename", true, true, 200);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_DQB";
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
		String idParser="PARSEO_DQB";
		IParseo[] parseos=JParseoFactory.getInstance(idParser,"XLSX");
		IParseo lastParser=null;
		for (IParseo parseo:parseos) {
			try {
				lastParser=parseo;
				parseo.setCompany(getCompany());
				parseo.setFechaDesde(getPeriodoDesde());
				parseo.setFechaHasta(getPeriodoHasta());
				parseo.setOwner(getOwner());
				FileInputStream file=new FileInputStream(filename);
				try {
					parseo.setFilename(filename);
					parseo.setInput(file);
					parseo.execute();
				} finally {
					file.close();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lastParser;
	}

	@Override
	public void processDelete() throws Exception {
		IInterfazParseo parseo = (IInterfazParseo)JParseoFactory.getInstanceFromClass(this.getParseo());
		parseo.eliminar(getCompany(), getOwner(), getIdpdf());

		super.processDelete();
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
//		setFechaDesde(parseo.getPeriodoDesde()==null?new Date():parseo.getPeriodoDesde());
//		setFechaHasta(parseo.getPeriodoHasta()==null?new Date():parseo.getPeriodoHasta());
//		
		if (this.isNullDescripcion()) {
			File f = new File(filename);
			this.setDescripcion(f.getName());
		}
		setEstado("PENDIENTE");
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
		procConsolidar();
	}
	
	
	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

	public void execConsolidar() throws Exception {
		JExec oExec = new JExec(null, "procConsolidar") {

			@Override
			public void Do() throws Exception {
				procConsolidar();
			}
		};
		oExec.execute();
	}
	
	public void procConsolidar() throws Exception {
		JRecords<BizDQBDetalle> recs = new JRecords<BizDQBDetalle>(BizDQBDetalle.class);
		recs.addFilter("idPDF", getIdpdf());
		JIterator<BizDQBDetalle> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDQBDetalle det = iter.nextElement();
			if (det.getPnr().trim().isEmpty()) {
				det.setConsolicion("No se chequea (boleto sin archivo)");
				det.setCodeCons(BizDQBDetalle.OK);
				det.update();
				continue;
			}
			if (det.getStatus().trim().equals("R") ||det.getStatus().trim().equals("RFND")) {
				det.setConsolicion("No se chequea (boleto reembolsado)");
				det.setCodeCons(BizDQBDetalle.OK);
				det.update();
				continue;
			}
			BizPNRTicket ticket  = new BizPNRTicket();
			String boleto = det.getBoleto();
			if (boleto.indexOf("-")!=-1)
				boleto = boleto.substring(0,boleto.indexOf("-"));
			ticket.addFilter("company", getCompany());
			ticket.addFilter("numeroboleto", boleto);
			ticket.dontThrowException(true);
			if (ticket.read()) {
				det.setConsolicion("Existe archivo");
				if (det.getStatus().equals("V")||
						det.getStatus().equals("CANX")||
						det.getStatus().equals("VOID")||
						det.getStatus().equals("CANN")) {
					if (ticket.isVoided()) {
						det.setConsolicion("Existe Void");
						det.setCodeCons(BizDQBDetalle.OK);
				
										
					}else {
						det.setConsolicion("Faltante Void");
						det.setCodeCons(BizDQBDetalle.ERROR);
					}
				} else {
					if (det.getFecha().equals(ticket.getCreationDate())) {
						det.setConsolicion("Existe archivo");
						det.setCodeCons(BizDQBDetalle.OK);
					
					} else {
						det.setConsolicion("No coincide fecha emision [tkm:"+JDateTools.DateToString(ticket.getCreationDate(),"dd/MM/yyyy")+"]");
						det.setCodeCons(BizDQBDetalle.ERROR);
					
					}

				}
					



				det.setInterfaceid(ticket.getId());
			} else {
				det.setConsolicion("No existe archivo");
				det.setCodeCons(BizDQBDetalle.ERROR);

			}
			det.processUpdate();
			
		}
		setEstado("CONSOLIDADO");
		update();
	}
}
