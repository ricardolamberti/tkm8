package  pss.bsp.organization;

import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormOrganization extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


/**
   * Constructor de la Clase
   */
  public FormOrganization() throws Exception {
  }

  public GuiOrganization getWin() { return (GuiOrganization) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Código", CHAR, REQ, "code" );
    AddItemFile( "Logo", CHAR, OPT, "logo" );
    JFormPanelResponsive panel = AddItemPanel("");
    panel.setSizeColumns(2);
    JFormImageResponsive i=panel.AddItemImage(null, "logo_inc");
     i.setSource(JPssImage.SOURCE_PSSDATA);
     AddItemTabPanel().AddItemTab(10);
 		
  }
  



}  //  @jve:decl-index=0:visual-constraint="24,19"
