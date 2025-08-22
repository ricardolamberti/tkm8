package pss.common.customList.config.carpetas;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormCarpeta extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;



	/**
   * Constructor de la Clase
   */
  public FormCarpeta() throws Exception {
   }

  public GuiCarpeta getWin() { return (GuiCarpeta) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null,  ULONG, OPT, "secuencia" ).setHide(true);
    AddItemEdit( null,  CHAR, OPT, "orden" ).setHide(true);
    AddItemEdit( null,  LONG, OPT, "padre" ).setHide(true);
    AddItemEdit( null,  LONG, OPT, "customlist" ).setHide(true);
    AddItemEdit( null,  CHAR, OPT, "descripcion" );
  	

    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.AddItemList(900);
    
    

  }
  @Override
  	public void OnPostShow() throws Exception {
  		super.OnPostShow();
  	}
 

}  //  @jve:decl-index=0:visual-constraint="-1,16"
