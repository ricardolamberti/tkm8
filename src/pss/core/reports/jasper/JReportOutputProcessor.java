package pss.core.reports.jasper;


import java.io.OutputStream;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class JReportOutputProcessor {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private static JReportOutputProcessor INSTANCE;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC API
  //
  //////////////////////////////////////////////////////////////////////////////
  public static JReportOutputProcessor getInstance() {
    if (INSTANCE==null) {
      INSTANCE = new JReportOutputProcessor();
    }
    return INSTANCE;
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE API
  //
  //////////////////////////////////////////////////////////////////////////////


  public void print(JReportJasper zReport) throws Exception {
    JasperPrintManager.printReport(zReport.createPrint(), false);
  }

  public void printWithOptions(JReportJasper zReport) throws Exception {
    JasperPrintManager.printReport(zReport.createPrint(), true);
  }

  public void exportToHTML(JReportJasper zReport, String zOutputFile) throws Exception {
    JasperExportManager.exportReportToHtmlFile(zReport.createPrint(), zOutputFile);
  }
  public void exportToPDF(JReportJasper zReport, String zOutputFile) throws Exception {
    JasperExportManager.exportReportToPdfFile(zReport.createPrint(), zOutputFile);
  }
  public void exportToXML(JReportJasper zReport, String zOutputFile) throws Exception {
    JasperExportManager.exportReportToXmlFile(zReport.createPrint(), zOutputFile, false);
  }
  public void exportToExcel(JReportJasper zReport, String zOutputFile) throws Exception {
    JRXlsExporter oExporter = new JRXlsExporter();
    oExporter.setParameter(JRExporterParameter.JASPER_PRINT, zReport.createPrint());
    oExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, zOutputFile);
    oExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    oExporter.exportReport();
  }


  public void exportToHTML(JReportJasper zReport, OutputStream zOutput) throws Exception {
    JRHtmlExporter exporter = new JRHtmlExporter();
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, zReport.createPrint());
    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, zOutput);
    exporter.exportReport();
  }
  public void exportToPDF(JReportJasper zReport, OutputStream zOutput) throws Exception {
    JasperExportManager.exportReportToPdfStream(zReport.createPrint(), zOutput);
  }
  public void exportToXML(JReportJasper zReport, OutputStream zOutput) throws Exception {
    JasperExportManager.exportReportToXmlStream(zReport.createPrint(), zOutput);
  }
  public void exportToExcel(JReportJasper zReport, OutputStream zOutput) throws Exception {
    JRXlsExporter oExporter = new JRXlsExporter();
    oExporter.setParameter(JRExporterParameter.JASPER_PRINT, zReport.createPrint());
    oExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, zOutput);
    oExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    oExporter.exportReport();
  }


  public void preview(JReportJasper zReport) throws Exception {
    GuiReportPreview oPreview = new GuiReportPreview();
    oPreview.GetcDato().setReport(zReport);
    String sTitle = zReport.getTitle();
    if (sTitle != null && sTitle.trim().length() > 0) {
      oPreview.SetTitle(sTitle + " - Vista previa");
    }
    oPreview.showQueryForm();
  }



}
