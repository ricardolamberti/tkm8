package pss.bsp.consolid.model.liquidacion.acumulado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.services.JExec;
import pss.core.services.records.JRecords;
import pss.core.tools.JConcatenar;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLiqAcums extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiLiqAcums() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 10003;
	}

	public String GetTitle() throws Exception {
		return "Liquidaciones";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiLiqAcum.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
//		if (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant())
//			addActionNew(1, "Nuevo Registro");
		addAction(20, "Exportar todos", null, 17, true, true);
		addAction(30, "Backup todos", null, 17, true, true).setNuevaVentana(true);
		
		addAction(50, "Marcar todos", null, 17, true, true);
		addAction(60, "Desmarcar todos", null, 17, true, true);
		BizAction  a=addAction(40, "Enviar mail", null, 17, true, true);
		a.setConfirmMessageDescription("Se enviaran mails a los clientes");

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==1) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		if (a.getId()==20) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		if (a.getId()==30) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		if (a.getId()==40) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		if (a.getId()==50) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		if (a.getId()==60) return !GetVision().equals("DK_ADMIN") &&  (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant());
		return super.OkAction(a);
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {

		if (!GetVision().equals("DK_ADMIN")) {
			if (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
				zLista.AddColumnaLista("pendiente");
		  	zLista.AddColumnaLista("Org.","organization");
				zLista.AddColumnaLista("dk");
				zLista.AddColumnaLista("descripcion");
			} else {
		  	zLista.AddColumnaLista("Org.","organization");
				zLista.AddColumnaLista("fecha_desde");
				zLista.AddColumnaLista("fecha_hasta");
				zLista.AddColumnaLista("fecha_liq");
			}
		} else {
			zLista.AddColumnaLista("fecha_desde");
			zLista.AddColumnaLista("fecha_hasta");
			zLista.AddColumnaLista("fecha_liq");		
		}

		zLista.AddColumnaLista("moneda");
		zLista.AddColumnaLista("total_contado");
		zLista.AddColumnaLista("total_tarjeta");
		zLista.AddColumnaLista("total_fiscal");
		zLista.AddColumnaLista("total_nofiscal");
		zLista.AddColumnaLista("total_comision");
		zLista.AddColumnaLista("total_facturado");
		zLista.AddColumnaLista("comision_no_devengada");
		zLista.AddColumnaLista("comision");
		zLista.AddColumnaLista("iva_comision");
		if (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			zLista.AddColumnaLista("estado");
		}

	}

	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {

		if (control.getIdControl().equals("fecha_desde") && control.hasValue()) {
			this.getRecords().addFilter("fecha_hasta", control.getValue(), ">=").setDynamic(true);
			this.getRecords().addFilter("fecha_desde", control.getValue(), "<=").setDynamic(true);
			return;
		}

		super.asignFilterByControl(control);
	}

	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20)
			return new JActFileGenerate(this, a.getId()) {
				@Override
				public String generate() throws Exception {
					return exportarAExcel();
				}
			};
		if (a.getId() == 30)
			return new JActFileGenerate(this, a.getId()) {
				@Override
				public String generate() throws Exception {
					return exportarAZip();
				}
			};
		if (a.getId() == 40)
			return new JActSubmit(this) {
				public void submit() throws Exception {
					enviarMail();
					setMessage("Mails enviados");
				}
			};
			if (a.getId() == 50)
				return new JActSubmit(this) {
					public void submit() throws Exception {
						setMessage(marcarTodos());
						
					}
				};
				if (a.getId() == 60)
					return new JActSubmit(this) {
						public void submit() throws Exception {
							desmarcarTodos();
							
						}
					};
		return super.getSubmitFor(a);

	}

	public String exportarAExcel() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addOrderBy("dk", "ASC");
		int i = 0;
		String fileNameOutput = BizUsuario.getUsr().getCompany() + "/full.pdf";
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + BizUsuario.getUsr().getCompany());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();
		String[] files = new String[recs.getStaticItems().size()];
		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			JBDReportes report = (JBDReportes) liq.createDataSource().getRecord();
			String fileName = BizUsuario.getUsr().getCompany() + "/" + liq.getOrganization() + "_" + liq.getDK() + "_" + report.toString() + ".pdf";
			fileName = fileName.replace("[]null", "");
			JTools.createDirectories(JPath.PssPathTempFiles(), fileName);
			report.setType(JReport.REPORTES_PDF);
			report.setOutputFile(JPath.PssPathTempFiles() + "/" + fileName);
			report.execProcessInsert();
			files[i++] = JPath.PssPathTempFiles() + "/" + fileName;
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando documento  " + fileName, i, files.length, false, null);

		}
		JConcatenar.concatenarPDF(files, JPath.PssPathTempFiles() + "/" + fileNameOutput);
		return fileNameOutput;
	}

	public void enviarMail() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addFilter("pendiente", "S");
		recs.addOrderBy("dk", "ASC");
		int i = 0;
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + BizUsuario.getUsr().getCompany());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();

		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liq.sendMail();
		}
	}	
	
	public String marcarTodos() throws Exception {
		String output="";
		String company = BizUsuario.getUsr().getCompany();
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addFixedFilter("where pendiente = 'N' or pendiente is null ");
		recs.addOrderBy("dk", "ASC");
		int i = 0;
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + BizUsuario.getUsr().getCompany());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();

		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liq.setPendiente(true);
			String sal=liq.execMarcar();
			if (sal!=null) output+=(output.equals("")?"":", ")+sal;
		}
		return output;
	}
	public void desmarcarTodos() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addFilter("pendiente", "S");
		recs.addOrderBy("dk", "ASC");
		int i = 0;
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + BizUsuario.getUsr().getCompany());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();

		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liq.setPendiente(true);
		  liq.execDesmarcar();
		}

	}
	public String exportarAZip() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		String liqdesde = "", liqhasta = "";
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addOrderBy("dk", "ASC");
		int i = 0;
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" + BizUsuario.getUsr().getCompany());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();
		String[] files = new String[recs.getStaticItems().size()];
		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liqdesde = JDateTools.DateToString(liq.getFechaDesde(), "yyyyMMdd");
			liqhasta = JDateTools.DateToString(liq.getFechaHasta(), "yyyyMMdd");

			JBDReportes report = (JBDReportes) liq.createDataSource().getRecord();

			String fileName = company + "/" + liq.getOrganization()+"_" + liq.getDK() + "_" + company + "_" + liqdesde + "_" + liqhasta + "_" + report.toString() + ".pdf";
			fileName = fileName.replace("[]null", ""); // Limpieza de nombre si es
																									// necesario

			JTools.createDirectories(JPath.PssPathTempFiles(), fileName);

			report.setType(JReport.REPORTES_PDF);
			report.setOutputFile(JPath.PssPathTempFiles() + "/" + fileName);
			report.execProcessInsert();

			files[i++] = JPath.PssPathTempFiles() + "/" + fileName;

			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando documento " + fileName, i, files.length, false, null);
		}

		String zipFileName = company + "_" + liqdesde + "_" + liqhasta + "_backup.zip";
		String zipFullPath = JPath.PssPathTempFiles() + "/" + zipFileName;

		zipFiles(files, zipFullPath);

		return zipFileName;
	}

	/**
	 * Método auxiliar para comprimir varios archivos en un ZIP.
	 */
	private void zipFiles(String[] srcFiles, String zipFilePath) throws IOException {
		byte[] buffer = new byte[1024];

		try (FileOutputStream fos = new FileOutputStream(zipFilePath); ZipOutputStream zos = new ZipOutputStream(fos)) {

			for (String filePath : srcFiles) {
				File f = new File(filePath);

				// Creamos una "entrada" en el ZIP con el nombre del archivo
				ZipEntry ze = new ZipEntry(f.getName());
				zos.putNextEntry(ze);

				try (FileInputStream fis = new FileInputStream(f)) {
					int len;
					while ((len = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				}
				zos.closeEntry();
			}
		}
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			zFiltros.addEditResponsive("DK", "dk").setOperator("=");
			zFiltros.addEditResponsive("Descripcion", "descripcion").setOperator("ilike");
		}
		zFiltros.addCDateResponsive("Vigentes en fecha", "fecha_desde");

		super.ConfigurarFiltros(zFiltros);
	}

}
