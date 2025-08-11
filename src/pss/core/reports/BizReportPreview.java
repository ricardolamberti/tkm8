package pss.core.reports;

import pss.core.services.records.JRecord;

public class BizReportPreview extends JRecord {

  private JReportActuate oReport;
  private int activePage;

  public BizReportPreview() throws Exception {
  }

  public JReportActuate getReport() {
    return oReport;
  }
  public void setReport(JReportActuate zReport) {
    oReport = zReport;
    activePage = Math.max(0, zReport.getOutputProcessor().prv_getActivePage());
  }


  //
  //  methods to export
  //
  public void print() throws Exception {
    getReport().DoReportPrint(false, true);
  }
  public void printWithOptions() throws Exception {
    getReport().DoReportPrint(true, true);
  }
  public void exportToHTML() throws Exception {
    getReport().DoReportHTML();
  }
  public void exportToDHTML() throws Exception {
    getReport().DoReportDHTML();
  }
  public void exportToExcel() throws Exception {
    getReport().DoReportExcel();
  }
  public void exportToPDF() throws Exception {
    getReport().DoReportPDF();
  }

  //
  // refresh method
  //
  public void refreshReport() throws Exception {
    refreshReport(true);
  }
  public void refreshReport(boolean zRegenerate) throws Exception {
    getReport().refreshReport(zRegenerate);
    activePage = 0;
  }


  //
  //  methods to navigate the preview device
  //
  public int getActivePage() {
    return activePage;
  }
  public int getPageCount() {
    return 0;
    //getReport().getOutputProcessor().prv_getPageCount();
  }
  public boolean isAtEnd() {
    return getActivePage() >= (getPageCount()-1);
  }
  public boolean isAtBeginning() {
    return getActivePage() == 0;
  }
  public void goToNextPage() {
    if (isEmpty() || isAtEnd()) return;
    getReport().getOutputProcessor().prv_setNextPage();
    activePage++;
  }
  public void goToPreviousPage() {
    if (isEmpty() || isAtBeginning()) return;
    getReport().getOutputProcessor().prv_setPrevPage();
    activePage--;
  }
  public void goToLastPage() {
    if (isEmpty() || isAtEnd()) return;
    getReport().getOutputProcessor().prv_setActivePage(getPageCount()-1);
    activePage = getPageCount()-1;
  }
  public void goToFirstPage() {
    if (isEmpty() || isAtBeginning()) return;
    getReport().getOutputProcessor().prv_setActivePage(0);
    activePage = 0;
  }
  public boolean isEmpty() {
    return getPageCount()==0;
  }

  //
  //   zoom methods
  //
  public void zoomIn() {
    int percent = getZoomPercent();
    if (percent < 200) {
      getReport().getOutputProcessor().prv_setZoom(percent + 10);
    }
  }
  public void zoomOut() {
    int percent = getZoomPercent();
    if (percent > 10) {
      getReport().getOutputProcessor().prv_setZoom(percent - 10);
    }
  }
  public int getZoomPercent() {
    return getReport().getOutputProcessor().prv_getZoom();
  }

}
