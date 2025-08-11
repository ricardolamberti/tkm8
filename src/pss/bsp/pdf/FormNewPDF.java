package  pss.bsp.pdf;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormNewPDF extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  /**
   * Constructor de la Clase
   */
  public FormNewPDF() throws Exception {
  }

  public GuiPDF getWin() { return (GuiPDF) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "idPDF" ).setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "Descripcion", CHAR, OPT, "descripcion" ).setSizeColumns(12);

    row = AddItemRow();
    row.AddItemFile( "Archivo PDF o ZIP:", CHAR, OPT, "pdffilename" ).setSizeColumns(12);
  }
}
