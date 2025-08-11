package  pss.bsp.interfaces.copa.datos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewCopa extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;



  /**
   * Constructor de la Clase
   */
  public FormNewCopa() throws Exception {
   }
 
  public GuiCopa getWin() { return (GuiCopa) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setVisible(false);
    AddItemEdit( null, CHAR, OPT, "owner" ).setVisible(false);
    AddItemEdit( null, CHAR, OPT, "idPDF" ).setVisible(false);
    AddItemDateTime( "Fecha desde", DATE, REQ, "fecha_desde" );
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" );
    AddItemEdit( "Descripcion", CHAR, OPT, "descripcion" );
    AddItemFile( "Archivo Interfaz" , CHAR, OPT, "pdffilename" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
