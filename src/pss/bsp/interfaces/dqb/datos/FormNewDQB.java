package  pss.bsp.interfaces.dqb.datos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewDQB extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;


  /**
   * Constructor de la Clase
   */
  public FormNewDQB() throws Exception {
   }
 
  public GuiDQB getWin() { return (GuiDQB) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "idPDF" ).setHide(true);
    AddItemDateTime( "Fecha desde" , DATE, REQ, "fecha_desde" ).setSizeColumns(6);
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" ).setSizeColumns(6);
    AddItemEdit( "Descripcion" , CHAR, OPT, "descripcion" );
    AddItemFile( "Archivo Interfaz", CHAR, OPT, "pdffilename" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
