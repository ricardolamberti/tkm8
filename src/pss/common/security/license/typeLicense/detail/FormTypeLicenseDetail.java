package  pss.common.security.license.typeLicense.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTypeLicenseDetail extends JBaseForm {


private static final long serialVersionUID = 1246199723480L;

  /**
   * Propiedades de la Clase
   */

  /**
   * Constructor de la Clase
   */
  public FormTypeLicenseDetail() throws Exception {
  }

  public GuiTypeLicenseDetail getWin() { return (GuiTypeLicenseDetail) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "id_typeLicense" ).setHide(true);
    AddItemEdit( "Campo", CHAR, REQ, "field" );
    AddItemEdit( "Valor", CHAR, REQ, "value" );

  } 
} 
