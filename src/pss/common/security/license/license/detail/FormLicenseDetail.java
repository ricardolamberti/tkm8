package  pss.common.security.license.license.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLicenseDetail extends JBaseForm {


private static final long serialVersionUID = 1246199462619L;

  /**
   * Propiedades de la Clase
   */


  /**
   * Constructor de la Clase
   */
  public FormLicenseDetail() throws Exception {
  }

  public GuiLicenseDetail getWin() { return (GuiLicenseDetail) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, REQ, "id_license" ).setHide(true);
    AddItemEdit( "Campo", CHAR, REQ, "field" );
    AddItemEdit( "Cantidad", UINT, REQ, "count" );

  } 
} 
