package  pss.common.pki.ejbca;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPKIConfiguracion extends JBaseForm {

  public FormPKIConfiguracion() throws Exception {
  }

  public GuiPKIConfiguracion getWin() { return (GuiPKIConfiguracion) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "pki_cmd", CHAR, REQ, "pki_cmd" );
    AddItemEdit( "pki_address", CHAR, REQ, "pki_address" );
    AddItemEdit( "pki_certificateprofile", CHAR, REQ, "pki_certificateprofile" );
    AddItemEdit( "pki_userprofile", CHAR, REQ, "pki_userprofile" );
    AddItemEdit( "pki_keystore", CHAR, REQ, "pki_keystore" );
    AddItemEdit( "pki_keystorepsw", CHAR, REQ, "pki_keystorePsw" );
    AddItemTabPanel().AddItemTab(10);
  }

}
