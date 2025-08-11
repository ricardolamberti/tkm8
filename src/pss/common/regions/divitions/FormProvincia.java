package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormProvincia extends JBaseForm {

  public FormProvincia() throws Exception {
  }

  public GuiProvincia GetWin() { return (GuiProvincia) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "Pais", CHAR, REQ, "pais", new GuiPaises() );
    AddItemEdit( "provincia", CHAR, OPT, "provincia" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Abrev", CHAR, OPT, "descrip_abrev" );
    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(30 );
  }

}
