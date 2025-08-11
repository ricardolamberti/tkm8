package pss.common.documentos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormDocum extends JBaseForm {


private static final long serialVersionUID = 1218220601500L;


  /**
   * Constructor de la Clase
   */
  public FormDocum() throws Exception {
  }

  public GuiDocum GetWin() { return (GuiDocum) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, OPT, "id_doc" ).setHide(true);
    AddItemEdit( "Tipo", CHAR, REQ, "descr_tipodoc" );
    AddItemEdit( "Sección", CHAR, OPT, "descr_seccion" );
//    AddItem( clave, CHAR, OPT, "clave" );
//    AddItem( titulo, CHAR, OPT, "titulo" );
    AddItemDateTime( "Fecha", DATETIME, OPT, "fecha" );
    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.AddItemTab( 40 );
    tabs.AddItemTab( 30 );
 
  }

	
}  //  @jve:decl-index=0:visual-constraint="10,10" 
