package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormSegConfiguracion extends JBaseForm {

  public FormSegConfiguracion() throws Exception {
  }

  public GuiSegConfiguracion GetWin() { return (GuiSegConfiguracion) GetBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn();
    col.AddItemEdit( "Company", CHAR, REQ, "company").hide();
//    col.AddItemEdit( "Long Min User", UINT, REQ, "long_min_user", 1 );
//    col.AddItemEdit( "Long Max User", UINT, REQ, "long_max_user", 1 );
//    col.AddItemEdit( "Long Min Clave", UINT, REQ, "long_min_password", 1 );
//    col.AddItemEdit( "Long Max Clave", UINT, REQ, "long_max_password", 1 );
//    col.AddItemEdit( "Espera Clave Inv", UINT, REQ, "user_idle_time", 1 );
//    col.AddItemEdit( "Maxima Cve Inv.", UINT, REQ, "max_password_retries", 1 );
//    col.AddItemEdit( "Caducidad Clave", UINT, REQ, "password_expired", 1 );
    JFormTabPanelResponsive tab = col.AddItemTabPanel();
    tab.AddItemListOnDemand(30);
    tab.AddItemListOnDemand(10);
    tab.AddItemListOnDemand(20);
    tab.AddItemListOnDemand(40);
    tab.AddItemListOnDemand(50);
    tab.AddItemListOnDemand(70);
  }

}