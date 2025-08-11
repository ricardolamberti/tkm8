package  pss.common.pki.ejbca;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPKICertificado extends JBaseForm {

  public FormPKICertificado() throws Exception {
  }

  public GuiPKICertificado getWin() { return (GuiPKICertificado) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "firma", CHAR, REQ, "usuario" );
    AddItemEdit( "fecha", DATE, REQ, "fecha" );
    AddItemEdit( "estado", CHAR, REQ, "estado" );

  } 
} 
