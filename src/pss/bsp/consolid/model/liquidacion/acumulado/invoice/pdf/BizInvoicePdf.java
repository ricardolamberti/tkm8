package pss.bsp.consolid.model.liquidacion.acumulado.invoice.pdf;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.CharEncoding;

import com.ibm.icu.util.Calendar;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.BizInvoice;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail.BizInvoiceDetail;
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
import pss.core.tools.JTools;

public class BizInvoicePdf extends JBDReportes  {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	protected JString pCompany = new JString();
	protected JDate pFechaDesde = new JDate();
	protected JDate pFechaHasta = new JDate();
	protected JString pDK = new JString();
	protected JLong pRowCount = new JLong();
	protected JLong pId = new JLong();
	protected JLong pLiquidationId = new JLong();
	protected JLong pHoja = new JLong();

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

	public void setId(long zValue) throws Exception { pId.setValue(zValue); }
	public long getId() throws Exception { return pId.getValue(); }
	public void setLiquidationId(long zValue) throws Exception { pLiquidationId.setValue(zValue); }
	public long getLiquidationId() throws Exception { return pLiquidationId.getValue(); }
	public void setHoja(long zValue) throws Exception { pHoja.setValue(zValue); }
	public long getHoja() throws Exception { return pHoja.getValue(); }

	public long getRowCount() throws Exception {
		return pRowCount.getValue();
	};

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizInvoicePdf() throws Exception {
	}

	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("fechadesde", pFechaDesde);
		addItem("fechahasta", pFechaHasta);
		addItem("sfechadesde", sFechaDesde);
		addItem("sfechahasta", sFechaHasta);
		addItem("row_count", pRowCount);
		addItem("dk", pDK);
		addItem("hoja", pHoja);
	}

	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "company", "Empresa", true, false, 50);
		addFixedItem(VIRTUAL, "fechadesde", "Fecha Desde", true, false, 15);
		addFixedItem(VIRTUAL, "fechahasta", "Fecha Hasta", true, false, 15);
		addFixedItem(VIRTUAL, "sfechadesde", "Fecha Desde", true, false, 35);
		addFixedItem(VIRTUAL, "sfechahasta", "Fecha Hasta", true, false, 35);
		addFixedItem(VIRTUAL, "row_count", "Row count", true, false, 15);
		addFixedItem(VIRTUAL, "dk", "Dk", true, false, 15);
		addFixedItem(VIRTUAL, "hoja", "hoja", true, false, 15);

	}

	public String GetTable() {
		return "";
	}

  @Override
  protected JReport obtenerReporte() throws Exception {
  	String sPath = "";
  	sPath = "bsp/consolid/model/liquidacion/acumulado/invoice/pdf/invoice_hoja"+getHoja()+".jasper";
  	
   	 		
 		JReport oRep = JReport.createReport(sPath,createDataSource()) ;
  	oRep.SetConfiguracionGeneral(true) ;
  	//oRep.setType(JReport.REPORTES_EXCEL);
  	return oRep ;


  }
  

      public static void main(String[] args) {
          try {
              // Cargar el reporte compilado
              JasperReport reporteCompilado = (JasperReport) JRLoader.loadObject(new File("/dev/tkm8/tkm8/src/pss/bsp/consolid/model/liquidacion/acumulado/invoice/invoice_hoja1.jasper"));

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
              JasperExportManager.exportReportToPdfFile(jasperPrint, "/dev/tkm8/invoice.pdf");

              System.out.println("Reporte generado exitosamente.");
          } catch (Exception e) {
              e.printStackTrace();
              System.err.println("Error al generar el reporte: " + e.getMessage());
          }
      }
  

	public JRDataSource createDataSource() throws Exception{
		BizInvoicePdf a = this;
    a.getRecords();
    return a;
 }
	protected void verificarRestricciones() throws Exception {
	}

	protected void configurarFormatos() throws Exception {
	}


  private void configurarSecciones(boolean zValor) throws Exception {
  }
  private static String getDaySuffix(int day) {
    if (day >= 11 && day <= 13) return "th";
    switch (day % 10) {
        case 1:  return "st";
        case 2:  return "nd";
        case 3:  return "rd";
        default: return "th";
    }
}
  
  public String decode(String input) throws Exception {
  
  	String cleaned = input.replaceAll("%E1b", "%E1%62");

    Pattern unicodePattern = Pattern.compile("%u([0-9A-Fa-f]{4})");
    Matcher matcher = unicodePattern.matcher(cleaned);
    StringBuffer unicodeReplaced = new StringBuffer();

    while (matcher.find()) {
        int codePoint = Integer.parseInt(matcher.group(1), 16);
        matcher.appendReplacement(unicodeReplaced, Character.toString((char) codePoint));
    }
    matcher.appendTail(unicodeReplaced);

    // Paso 2: decodificamos lo restante con URLDecoder (maneja %20, %3C, etc.)
    String decoded = URLDecoder.decode(unicodeReplaced.toString(), CharEncoding.ISO_8859_1);


    return decoded;
}
  protected void configurarControles() throws Exception {
  	configurarSecciones(true);
  	String title = "Invoice";
    
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
    getReport().setImage("PIE_INC",  BizPssConfig.getPssConfig().getAppURLPreview()+ "/pss_icon/logos/pie.png");
    getReport().setImage("HEADER_INC", BizPssConfig.getPssConfig().getAppURLPreview()+ (getHoja()==5?"/pss_icon/logos/header2.png":"/pss_icon/logos/header.png"));
    	getReport().setLabel("TITLE",title);
  	getReport().setLabel("LBL_PERIODO", "Período de Facturación");
  	getReport().setLabel("FECHA_DESDE", JDateTools.DateToString(getHeader().getFechaDesde(), "dd-MMM-YYYY"));
  	getReport().setLabel("FECHA_HASTA", JDateTools.DateToString(getHeader().getFechaHasta(), "dd-MMM-YYYY"));
  	Date fechaPago = getHeader().getFechaLiquidacion()==null?new Date():getHeader().getFechaLiquidacion();
  	Calendar cal = Calendar.getInstance();
  	cal.setTime(fechaPago);
  	Date fechaInvoice = getHeader().getFechaInvoice()==null?new Date():getHeader().getFechaInvoice();
  	Calendar cal2 = Calendar.getInstance();
  	cal2.setTime(fechaInvoice);
  	getReport().setLabel("FECHA_LIQ", "Mexico City, "+JDateTools.DateToString(fechaPago, "MMMM d")+getDaySuffix(cal.get(Calendar.DAY_OF_MONTH)));
  	getReport().setLabel("FECHA_LIQUIDACION", JDateTools.DateToString(fechaPago, "d")+getDaySuffix(cal.get(Calendar.DAY_OF_MONTH))+JDateTools.DateToString(fechaPago, " MMMM yyyy"));
  	getReport().setLabel("FECHA_INVOICE", JDateTools.DateToString(fechaInvoice, "d")+getDaySuffix(cal.get(Calendar.DAY_OF_MONTH))+JDateTools.DateToString(fechaInvoice, " MMMM yyyy"));
  	getReport().setLabel("LBL_ADDRESS", getHeader().getDireccion());
  	getReport().setLabel("LBL_DK", getHeader().getDk());
  	getReport().setLabel("LBL_DKNAME", getHeader().getDkName());
  	getReport().setLabel("LBL_DESCRIPCION", ""+getHeader().getNumero());
  	getReport().setLabel("LBL_ADDRESS_CUST",  decode(getHeader().getDireccionFrom()));
  	getReport().setLabelCurrency("LBL_TOTAL", JTools.rd(getHeader().getTotal(),2));
  	getReport().setLabelCurrency("LBL_PAY",  JTools.rd(getHeader().getPay(),2));
  	getReport().setLabelCurrency("LBL_TAX",  JTools.rd(getHeader().getTax(),2));
  	getReport().setLabelCurrency("LBL_COMISION",  JTools.rd(getHeader().getComision(),2));
  	getReport().setLabelCurrency("LBL_TOTALANDTAX",  JTools.rd(getHeader().getTotalAndTax(),2));
  	getReport().setLabelCurrency("LBL_TOTALAMOUNT",  JTools.rd(getHeader().getTotalAmount(),2));
  	getReport().setLabelCurrency("LBL_BILLING",  JTools.rd(getHeader().getBilling(),2));
  	getReport().setLabel("LBL_VAT", getHeader().getVat());
  	getReport().setLabel("LBL_COMPANY", getHeader().getCompany());
  	getReport().setLabel("LBL_MONEDA", getHeader().getCurrency());
  	
  	getReport().setLabel("LBL_BANK_NAME", getHeader().getBankName());
  	getReport().setLabel("LBL_BANK_ADDR", getHeader().getBankAddress());
   	getReport().setLabel("LBL_ACC_MONEDA", getHeader().getAccountCurrency());
   	getReport().setLabel("LBL_ACC_NUMBER", getHeader().getAccountNumber());
  	getReport().setLabel("LBL_ACC_CLABE", getHeader().getClabe());
  	getReport().setLabel("LBL_ACC_NAME", getHeader().getAccountName());
    getReport().setLabel("LBL_SWIFT", getHeader().getSwift());
 	

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
		return this.getHeader().getCurrency();
	}

	private JRecords<BizLiqDetail> oLiqDetail = null;
	private JRecords<BizInvoiceDetail> oInvoiceDetail = null;
	private BizInvoice oInvoice = null;

	public JRecords getRecords() throws Exception {
		if (getHoja()==1||getHoja()==6||getHoja()==7) {
			if (oLiqDetail!=null) return oLiqDetail;
			JRecords<BizLiqDetail> recs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
			recs.addFilter("dk", pDK.getValue());
			recs.addFilter("liquidacion_id", pLiquidationId.getValue());
			recs.addFilter("company", pCompany.getValue());
			recs.addOrderBy("fecha_doc","asc");
			recs.addOrderBy("nro_doc","asc");
			recs.addOrderBy("tipo_doc","asc");
			recs.setTop(1);
			recs.toStatic();
			return oLiqDetail = recs;	
		}
		if (oInvoiceDetail!=null) 
			return oInvoiceDetail;
		JRecords<BizInvoiceDetail> recsD = new JRecords<BizInvoiceDetail>(BizInvoiceDetail.class);
		recsD.addFilter("company", pCompany.getValue());
		recsD.addFilter("invoice_id", pId.getValue());
		recsD.toStatic();
		return oInvoiceDetail = recsD;

	}
	public BizInvoice getHeader() throws Exception {
		if (oInvoice!=null) return oInvoice;
		BizInvoice rec = new BizInvoice();
		rec.addFilter("id", pId.getValue());
		rec.addFilter("company", pCompany.getValue());
		
		rec.read();
		return oInvoice = rec;

	}
	
	
}
