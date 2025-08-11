package pss.core.reports.jasper;

import java.awt.event.KeyEvent;

import pss.core.win.JWin;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseKeys;
import pss.core.winUI.icons.GuiIcon;

public class GuiReportPreview extends JWin {

  private static final int ICON_EXCEL   = 524;
  private static final int ICON_HTML    = 926;
  private static final int ICON_PDF     = 928;
  private static final int ICON_PRINTER = GuiIcon.PRINTER_ICON;
  private static final int ICON_PRINTER_WITH_OPTIONS = 935;

  private static final int ICON_GO_NEXT = 929;
  private static final int ICON_GO_PREVIOUS = 930;
  private static final int ICON_GO_LAST = 931;
  private static final int ICON_GO_FIRST = 932;

  private static final int ICON_ZOOM_IN = 933;
  private static final int ICON_ZOOM_OUT = 934;


  public GuiReportPreview() throws Exception {
    setRecord           ( new BizReportPreview() );
    SetNroIcono       ( GuiIcon.REPORT_ICON );
    SetFormBase       ( FormReportPreview.class );
    SetTitle          ( "Vista previa" );
  }

  @Override
	public void createActionMap() throws Exception {

//    // acciones de output
//
//    addAction(11, "Imprimir", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_I, new JAct() {
//      @Override
//			public void Do() throws Exception {
//        GetcDato().print();
//      }
//    }, ICON_PRINTER, false, true );
//
//
//    addAction(16, "Imprimir con opciones", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_O, new JAct() {
//      @Override
//			public void Do() throws Exception {
//        GetcDato().printWithOptions();
//      }
//    }, ICON_PRINTER_WITH_OPTIONS, false, true );
//
//
//    addAction(12, "Exportar a HTML", new JAct() {
//      @Override
//			public void Do() throws Exception {
//        String sFileName = getOutputFilename("Exportar a HTML", ICON_HTML, "html");
//        if (sFileName==null) return;
//        GetcDato().exportToHTML(sFileName);
//      }
//    }, ICON_HTML, false, true );
//
//    addAction(14, "Exportar a Excel", new JAct() {
//      @Override
//			public void Do() throws Exception {
//        String sFileName = getOutputFilename("Exportar a Excel", ICON_EXCEL, "xls");
//        if (sFileName==null) return;
//        GetcDato().exportToExcel(sFileName);
//      }
//    }, ICON_EXCEL, false, true );
//
//    addAction(15, "Exportar a PDF", new JAct() {
//      @Override
//			public void Do() throws Exception {
//        String sFileName = getOutputFilename("Exportar a PDF", ICON_PDF, "pdf");
//        if (sFileName==null) return;
//        GetcDato().exportToPDF(sFileName);
//      }
//    }, ICON_PDF, false, true );
//


    // acciones de navegación

    addAction(23, "Ir a primera página", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_B, new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().goToFirstPage();
        refreshCurrentPageInfo();
      }
    }, ICON_GO_FIRST, false, true);

    addAction(22, "Ir a página anterior", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_P, new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().goToPreviousPage();
        refreshCurrentPageInfo();
      }
    }, ICON_GO_PREVIOUS, false, true);

    addAction(21, "Ir a página siguiente", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_N, new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().goToNextPage();
        refreshCurrentPageInfo();
      }
    }, ICON_GO_NEXT, false, true);

    addAction(24, "Ir a última página", JBaseKeys.Pss_CTRL_MASK | KeyEvent.VK_E, new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().goToLastPage();
        refreshCurrentPageInfo();
      }
    }, ICON_GO_LAST, false, true);


    // zoom

    addAction(30, "Zoom in", new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().zoomIn();
        refreshZoomInfo();
      }
    }, ICON_ZOOM_IN, false, true);

    addAction(31, "Zoom out", new JAct() {
      @Override
			public void Do() throws Exception {
        GetcDato().zoomOut();
        refreshZoomInfo();
      }
    }, ICON_ZOOM_OUT, false, true);

  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizReportPreview GetcDato() throws Exception {
    return (BizReportPreview) this.getRecord();
  }


//  private String getOutputFilename(String zDialogTitle, int zIconNumber, String zExtension) throws Exception {
//    String sFileName;
//    try {
//      if ((sFileName = askForFileNameIfNeeded(zDialogTitle, zIconNumber, zExtension)) != null
//          && checkFileOverwrite(sFileName)) {
//        return sFileName;
//      }
//    } catch (CanceledByUserException ex) {
//      // si el usuario canceló el cuadro de selección de archivo, no hago nada
//    }
//    return null;
//  }
//  private String askForFileNameIfNeeded(String zDialogTitle, int zIconNumber, String zExtension) throws Exception {
//    // check if it has to force the direcotry configured in the node as target
//    // directory for report exports
//    String sConfiguredExportDir = BizUsuario.getUsr().getObjNodo().getReportDir();
//    boolean bForceConfiguredExportDir = sConfiguredExportDir!=null && sConfiguredExportDir.trim().length() > 0;
//    File oConfiguredExportDir = null;
//    if (!bForceConfiguredExportDir) {
//      sConfiguredExportDir = JPath.PssPathReport();
//    } else {
//      oConfiguredExportDir = new File(sConfiguredExportDir);
//      if (!oConfiguredExportDir.exists()) {
//        JExcepcion.SendError("No existe directorio de exportación de reportes: " + sConfiguredExportDir);
//      }
//    }
//    // if has to force the configured dir, asks only the file name
//    if (bForceConfiguredExportDir) {
//      String fileName = JPssOptionPane.showInputDialog(null,
//        "Ingrese el nombre del archivo que se guardará en el directorio '" + sConfiguredExportDir + "':", zDialogTitle, JOptionPane.INFORMATION_MESSAGE);
//      if (fileName != null && fileName.length() > 0) {
//        sConfiguredExportDir = sConfiguredExportDir.trim().replace('\\', '/');
//        if (!sConfiguredExportDir.endsWith("/")) {
//          sConfiguredExportDir += "/";
//        }
//        return ensureExtension(sConfiguredExportDir + fileName, zExtension);
//      }
//    } else {
//      // create the file chooser
//      JPssFileChooser oChooser = new JPssFileChooser(sConfiguredExportDir);
//      // opens the file chooser and set the answer
//      oChooser.setMultiSelectionEnabled(false);
//      oChooser.setDialogTitle(zDialogTitle);
//      oChooser.setDialogIcon(getPssIcon(zIconNumber));
//      int answer = oChooser.showSaveDialog(this.GetFormActivo(getFormBase()));
//      if (answer == JFileChooser.APPROVE_OPTION) {
//        return ensureExtension(oChooser.getSelectedFile().getAbsolutePath(), zExtension);
//      }
//    }
//    throw new CanceledByUserException("No se seleccionó ningún archivo");
//  }

  private String ensureExtension(String zFullFileName, String zExtension) {
    String sExpectedEnding = ("." + zExtension).toLowerCase();
    if (!zFullFileName.toLowerCase().endsWith(sExpectedEnding)) {
      return zFullFileName + sExpectedEnding;
    } else {
      return zFullFileName;
    }
  }

  private boolean checkFileOverwrite(String zFullFileName) throws Exception {
//    if (new File(zFullFileName).exists()) {
//      return UITools.MostrarYesNoMensaje("Reemplazar archivo",
//        "El archivo seleccionado ya existe. ¿Desea reemplazarlo?") == JOptionPane.YES_OPTION;
//    }
    return true;
  }


//  private ImageIcon getPssIcon(int zIconNumber) throws Exception {
//    return GuiIconGalerys.GetGlobal().getIcon(GuiIconos.GetGlobal().buscarIcono(zIconNumber).GetFile());
//  }

  private void refreshCurrentPageInfo() throws Exception {
//    FormReportPreview oForm = (FormReportPreview) this.GetFormActivo(getFormBase());
//    if (oForm==null) return;
//
//    oForm.refreshPageInfo();
  }
  private void refreshZoomInfo() throws Exception {
//    FormReportPreview oForm = (FormReportPreview) this.GetFormActivo(getFormBase());
//    if (oForm==null) return;
//
//    oForm.refreshZoomInfo();
  }




}

