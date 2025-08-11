package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDepartamento extends JBaseForm {


  public FormDepartamento() throws Exception {
  }

  public GuiDepartamento getWin() { return (GuiDepartamento) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "pais", CHAR, REQ, "pais", new GuiPaises() );
    AddItemCombo( "provincia", CHAR, REQ, "provincia" , new GuiProvincias());
    AddItemEdit( "nombre", CHAR, REQ, "nombre" );
    AddItemEdit( "dpto_id", UINT, OPT, "dpto_id" ).setVisible(false);
    AddItemTabPanel().AddItemTab(10);
  }

}
