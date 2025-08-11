package pss.core.reports.jasper;

import net.sf.jasperreports.view.JRViewer;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

public class BizReportPreview extends JRecord {

  private JReportJasper oReport;
  private int activePage;
  private JRViewer oViewer;

  public BizReportPreview() throws Exception {
  }

  public JReportJasper getReport() {
    return this.oReport;
  }
  public void setReport(JReportJasper zReport) throws Exception {
    this.oReport = zReport;
    this.activePage = Math.max(0, this.getViewer().getPageIndex());
  }

  //
  //  methods to export
  //
  public void print() throws Exception {
//    UITools.contexts().performInAWTDetachedContext(new JAct() {
//      @Override
//			public void Do() throws Exception {
//        showWaitMessage("Enviando a la impresora");
//        JReportOutputProcessor.getInstance().print(getReport());
//      }
//    });
  }
  public void printWithOptions() throws Exception {
//    UITools.contexts().performInAWTDetachedContext(new JAct() {
//      @Override
//			public void Do() throws Exception {
//        showWaitMessage("Enviando a la impresora");
//        JReportOutputProcessor.getInstance().printWithOptions(getReport());
//      }
//    });
  }
  public void exportToHTML(final String zFileName) throws Exception {
//    UITools.contexts().performInAWTDetachedContext(new JAct() {
//      @Override
//			public void Do() throws Exception {
//        showWaitMessage("Exportando a HTML");
//        JReportOutputProcessor.getInstance().exportToHTML(getReport(), zFileName);
//      }
//    });

  }
  public void exportToExcel(final String zFileName) throws Exception {
//    UITools.contexts().performInAWTDetachedContext(new JAct() {
//      @Override
//			public void Do() throws Exception {
//        showWaitMessage("Enviando a Excel");
//        JReportOutputProcessor.getInstance().exportToExcel(getReport(), zFileName);
//      }
//    });
//
  }
  public void exportToPDF(final String zFileName) throws Exception {
//    UITools.contexts().performInAWTDetachedContext(new JAct() {
//      @Override
//			public void Do() throws Exception {
//        showWaitMessage("Exportando a PDF");
//        JReportOutputProcessor.getInstance().exportToPDF(getReport(), zFileName);
//      }
//    });
  }

  //
  // refresh method
  //
  public void refreshReport() throws Exception {
    //this.getViewer().goToPage(0);
    this.getReport().getPrint().getPages().clear();
    this.activePage = 0;
  }


  //
  //  methods to navigate the preview device
  //
  public int getActivePage() {
    return activePage;
  }
  public int getPageCount() throws Exception {
    return this.getReport().getPrint().getPages().size();
    
  }
  public boolean isAtEnd() throws Exception {
    return this.getActivePage() >= (this.getPageCount()-1);
  }
  public boolean isAtBeginning() {
    return this.getActivePage() == 0;
  }
  public void goToNextPage() throws Exception {
    /*if (this.isEmpty() || this.isAtEnd()) return;
    this.getViewer().goToNextPage(); */
    this.activePage++;
  }
  public void goToPreviousPage() throws Exception {
    /*if (this.isEmpty() || this.isAtBeginning()) return;
    this.getViewer().goToPreviousPage(); */
    this.activePage--;
  }
  public void goToLastPage() throws Exception {
    /*if (this.isEmpty() || this.isAtEnd()) return;
    this.getViewer().goToPage(this.getPageCount()-1); */
    this.activePage = this.getPageCount()-1;
  }
  public void goToFirstPage() throws Exception {
   /* if (this.isEmpty() || this.isAtBeginning()) return;
    this.getViewer().goToPage(0); */
    this.activePage = 0;
  }
  public boolean isEmpty() throws Exception {
    return this.getPageCount()==0;
  }

  //
  //   zoom methods
  //
  public void zoomIn() throws Exception {
   // int iPercent = Math.max(10, this.getViewer().getZoomPercent() + 10);
   // this.getViewer().setZoomPercent(iPercent);
  }
  public void zoomOut() throws Exception {
   // int iPercent = Math.max(10, this.getViewer().getZoomPercent() - 10);
   // this.getViewer().setZoomPercent(iPercent);
  }
  public int getZoomPercent() throws Exception {
   // return this.getViewer().getZoomPercent();
  	return 0;
  }


  public JRViewer getViewer() throws Exception {
    if (this.oViewer==null) {
      this.oViewer = new JRViewer(this.getReport().getPrint());
    }
    return this.oViewer;
  }

 
  public void cleanUp() {
    this.getReport().cleanUp();
    this.oViewer = null;
  }


  private void showWaitMessage(String zCurrentTask) {
    String sTitle = this.getReport().getTitle();
    if (sTitle != null && sTitle.length() > 0) {
      PssLogger.logWait(zCurrentTask + ": " + sTitle + " . . .   Por favor espere . . .");
    } else {
      PssLogger.logWait(zCurrentTask + " . . .   Por favor espere . . .");
    }
  }


}
