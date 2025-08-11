package pss.bsp.consolid.model.liquidacion.acumulado.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.organization.BizOrganization;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;

public class BizReporteResumen extends JBDReportes  {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	protected JString pCompany = new JString();
	protected JDate pFechaDesde = new JDate();
	protected JDate pFechaHasta = new JDate();
	protected JString pDK = new JString();
	protected JLong pRowCount = new JLong();
	protected JLong pLiquidationId = new JLong();

	protected BizCompany oCompany = null;
	// JString pFleetAccount = new JString();

	protected JString sFechaDesde = new JString() {
		@Override
		public void preset() throws Exception {
			sFechaDesde.setValue(JDateTools.DateToString(pFechaDesde.getValue(), "dd-MM-yyyy"));
		}
	};
	protected JString sFechaHasta = new JString() {
		@Override
		public void preset() throws Exception {
			sFechaHasta.setValue(JDateTools.DateToString(pFechaHasta.getValue(), "dd-MM-yyyy"));
		}
	};

	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}

	public void setFechaDesde(Date value) throws Exception {
		pFechaDesde.setValue(value);
	}

	public void setFechaHasta(Date value) throws Exception {
		pFechaHasta.setValue(value);
	}

	public void setFechaDesdeString(String value) throws Exception {
		sFechaDesde.setValue(value);
	}

	public void setFechaHastaString(String value) throws Exception {
		sFechaHasta.setValue(value);
	}

	public void setDK(String zValue) throws Exception { pDK.setValue(zValue); }
	public String getDK() throws Exception { return pDK.getValue(); }

	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setLiquidacionId(Long zValue) throws Exception { pLiquidationId.setValue(zValue); }
	public Long getLiquidacionId() throws Exception { return pLiquidationId.getValue(); }
	
	public long getRowCount() throws Exception {
		return pRowCount.getValue();
	};

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizReporteResumen() throws Exception {
	}

	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("fechadesde", pFechaDesde);
		addItem("fechahasta", pFechaHasta);
		addItem("sfechadesde", sFechaDesde);
		addItem("sfechahasta", sFechaHasta);
		addItem("row_count", pRowCount);
		addItem("dk", pDK);
	}

	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "company", "Empresa", true, false, 50);
		addFixedItem(VIRTUAL, "fechadesde", "Fecha Desde", true, false, 15);
		addFixedItem(VIRTUAL, "fechahasta", "Fecha Hasta", true, false, 15);
		addFixedItem(VIRTUAL, "sfechadesde", "Fecha Desde", true, false, 35);
		addFixedItem(VIRTUAL, "sfechahasta", "Fecha Hasta", true, false, 35);
		addFixedItem(VIRTUAL, "row_count", "Row count", true, false, 15);
		addFixedItem(VIRTUAL, "dk", "Dk", true, false, 15);

	}

	public String GetTable() {
		return "";
	}

  @Override
  protected JReport obtenerReporte() throws Exception {
  	String sPath = "";
  	sPath = "bsp/consolid/model/liquidacion/acumulado/report/resumen.jasper";
  	
   	 		
 		JReport oRep = JReport.createReport(sPath,createDataSource()) ;
  	oRep.SetConfiguracionGeneral(true) ;
  	//oRep.setType(JReport.REPORTES_EXCEL);
  	return oRep ;


  }
  

      public static void main(String[] args) {
          try {
              // Cargar el reporte compilado
              JasperReport reporteCompilado = (JasperReport) JRLoader.loadObject(new File("/dev/tkm8/tkm8/src/pss/bsp/consolid/model/liquidacion/acumulado/report/resumen.jasper"));

              // Parámetros del reporte
              Map<String, Object> parametros = new HashMap<>();
              parametros.put("titulo", "Reporte de Prueba");

              // Crear una lista vacía para los datos
              List<Map<String, Object>> listaDeDatos = new ArrayList<>();
              listaDeDatos.add(new HashMap<>()); // Al menos un registro vacío
              JRDataSource dataSource = new JRBeanCollectionDataSource(listaDeDatos);

              // Llenar el reporte
              JasperPrint jasperPrint = JasperFillManager.fillReport(reporteCompilado, parametros, dataSource);

              // Exportar el reporte a PDF
              JasperExportManager.exportReportToPdfFile(jasperPrint, "/dev/tkm8/reporte.pdf");

              System.out.println("Reporte generado exitosamente.");
          } catch (Exception e) {
              e.printStackTrace();
              System.err.println("Error al generar el reporte: " + e.getMessage());
          }
      }
  

	public JRDataSource createDataSource() throws Exception{
		BizReporteResumen a = this;
    a.getRecords();
    return a;
 }
	protected void verificarRestricciones() throws Exception {
	}

	protected void configurarFormatos() throws Exception {
	}


  private void configurarSecciones(boolean zValor) throws Exception {
  }
  
  protected void configurarControles() throws Exception {
  	configurarSecciones(true);
  	String title = "Listado conciliación";
    
  	String logoFile ;
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  			BizOrganization objOrg = getHeader().getObjOrganization();
  			String logo = null;
  			if (objOrg!=null) {
  				logo = objOrg.getLogo();
  			}
  			if (logo==null)
  				logo = BizBSPCompany.getObjBSPCompany(BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getParentCompany()).getLogo();
  			logoFile  = BizPssConfig.getPssConfig().getAppURLPreview()+ "/pssdata_resource/files/logos/" + logo;
  	} else {
			BizOrganization objOrg = getHeader().getObjOrganization();
			String logo = null;
			if (objOrg!=null) {
				logo = objOrg.getLogo();
			}
			if (logo==null)
				logoFile  = BizPssConfig.getPssConfig().getAppURLPreview()+ "/pssdata_resource/files/logos/" + BizUsuario.getUsr().getObjCompany().getLogo();
			else
	 			logoFile  = BizPssConfig.getPssConfig().getAppURLPreview()+ "/pssdata_resource/files/logos/" + logo;

  	}
    getReport().setImage("LOGO_INC", logoFile);
  	getReport().setLabel("TITLE",title);
  	getReport().setLabel("LBL_PERIODO", "Período de Facturación");
  	getReport().setLabel("LBL_DK", getHeader().getDK());
  	getReport().setLabel("FECHA_DESDE", JDateTools.DateToString(getHeader().getFechaDesde(), "dd-MMM-YYYY"));
  	getReport().setLabel("FECHA_HASTA", JDateTools.DateToString(getHeader().getFechaHasta(), "dd-MMM-YYYY"));
  	getReport().setLabel("FECHA_LIQ", JDateTools.DateToString(getHeader().getFechaLiquidacion()==null?new Date():getHeader().getFechaLiquidacion(), "d 'de' MMMM 'del' yyyy"));
  	getReport().setLabel("LBL_DK", getHeader().getDK());
  	getReport().setLabel("LBL_DESCRIPCION", getHeader().getDescripcion());
  	getReport().setLabelCurrency("LBL_TOTAL_CONTADO", getHeader().getTotalContado());
  	getReport().setLabelCurrency("LBL_TOTAL_COMISION", getHeader().getTotalComision());
  	getReport().setLabelCurrency("LBL_TOTAL_TARJETA", getHeader().getTotalTarjeta());
  	getReport().setLabelCurrency("LBL_TOTAL_DOC_FISCALES", getHeader().getTotalFiscales());
  	getReport().setLabelCurrency("LBL_TOTAL_DOC_NO_FISCALES", getHeader().getTotalNoFiscales());
  	getReport().setLabel("LBL_COMPANY", getHeader().getCompany());
  	getReport().setLabel("LBL_ESTADO", getHeader().getEstado());
  	getReport().setLabel("LBL_MONEDA", getHeader().getMoneda());
  	getReport().setLabelCurrency("LBL_COMISION_NETO", getHeader().getComisionNeto());
  	getReport().setLabelCurrency("LBL_COMISION_NO_DEVENGADA", getHeader().getComisionNoDevengada());
  	getReport().setLabelCurrency("LBL_COMISION", getHeader().getComision());
  	getReport().setLabelCurrency("LBL_PORC_IVA_COMISION", getHeader().getPorcIvaComision());
  	getReport().setLabelCurrency("LBL_IVA_COMISION", getHeader().getIvaComision());
  	getReport().setLabelCurrency("LBL_TOTAL_FACTURADO", getHeader().getTotalFacturado());
  	getReport().setLabel("LBL_FECHA_PAGO", JDateTools.DateToString(getHeader().getFechaPago(), "dd-MMM-YYYY"));
  	getReport().setLabel("LBL_FECHA_COMISION", JDateTools.DateToString(getHeader().getFechaComision(), "dd-MMM-YYYY"));
  	getReport().setLabel("LBL_CUENTA", getHeader().getCuenta());
  	getReport().setLabel("LBL_CBU", getHeader().getCBU());
  	getReport().setLabel("LBL_INFO", getHeader().getInfo());
  	getReport().setLabel("LBL_REF", getHeader().getDKCode());
  	
		
		getReport().setLabel("SUBREPORT_DIR", getReport().getPath());
		

	}
  
	protected void configurarSQL() throws Exception {
	}

	protected BizCompany getObjCompany() throws Exception {
		if (this.oCompany != null)
			return this.oCompany;
		BizCompany record = new BizCompany();
		record.Read(this.pCompany.getValue());
		return (this.oCompany = record);

	}

	public String getMonedaLocal() throws Exception {
		return this.getHeader().getMoneda();
	}

	private JRecords<BizLiqDetail> oLiqDetail = null;
	private BizLiqAcum oLiqAcum = null;

	public JRecords<BizLiqDetail> getRecords() throws Exception {
		if (oLiqDetail!=null) return oLiqDetail;
		JRecords<BizLiqDetail> recs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		recs.addFilter("dk", pDK.getValue());
		recs.addFilter("liquidacion_id", pLiquidationId.getValue());
		recs.addFilter("company", pCompany.getValue());
		recs.addOrderBy("fecha_doc","asc");
		recs.addOrderBy("nro_doc","asc");
		recs.addOrderBy("tipo_doc","asc");	
		recs.toStatic();
		return oLiqDetail = recs;

	}

	public BizLiqAcum getHeader() throws Exception {
		if (oLiqAcum!=null) return oLiqAcum;
		BizLiqAcum rec = new BizLiqAcum();
		rec.addFilter("dk", pDK.getValue());
		rec.addFilter("company", pCompany.getValue());
		rec.addFilter("liquidacion_id", pLiquidationId.getValue());

		rec.read();
		return oLiqAcum = rec;

	}
	
	
}
